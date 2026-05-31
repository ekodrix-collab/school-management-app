package com.school.management.api.service.authService;

import com.school.management.api.entity.BlacklistedToken;
import com.school.management.api.entity.PasswordResetToken;
import com.school.management.api.entity.User;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.exception.ResourceNotFoundException;
import com.school.management.api.exception.UnauthorizedException;
import com.school.management.api.model.requstModel.LoginRequest;
import com.school.management.api.model.requstModel.RegisterRequest;
import com.school.management.api.model.responseModel.AuthResponse;
import com.school.management.api.model.responseModel.MessageResponse;
import com.school.management.api.model.responseModel.VerifyOtpResponse;
import com.school.management.api.model.requstModel.ForgotPasswordEmailRequest;
import com.school.management.api.model.requstModel.ResetPasswordEmailRequest;
import com.school.management.api.model.requstModel.VerifyOtpRequest;
import com.school.management.api.service.EmailService;
import com.school.management.api.repository.BlacklistedTokenRepository;
import com.school.management.api.repository.PasswordResetTokenRepository;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.security.CustomUserDetails;
import com.school.management.api.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static com.school.management.api.service.mapper.MapperService.generateUserId;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final int RESET_TOKEN_TTL_MINUTES = 15;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final EmailService emailService;

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMobile(), request.getPassword()));

        String token = jwtTokenProvider.generateToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Date issuedAt = jwtTokenProvider.getIssuedAtFromToken(token);
        Date expiry = jwtTokenProvider.getExpiryFromToken(token);

        User user = userRepository.findByMobile(request.getMobile()).orElseThrow();

        if(!user.getIsActive()){
            throw new UnauthorizedException("user not found");
        }

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(token)
                .userId(userDetails.getUserId())
                .role(userDetails.getRole())
                .schoolId(userDetails.getSchoolId())
                .name(user.getName())
                .issuedAt(issuedAt)
                .expiryDate(expiry)
                .expiryTimeMs(expiry.getTime())
                .isFirstLogin(user.getIsFirstLogin())
                .build();
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (request.getMobile() != null &&
                userRepository.findByMobile(request.getMobile()).isPresent()) {
            throw new BadRequestException("Mobile number already exists");
        }

        UUID userId = generateUserId();

        User user = new User();
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setSchoolId(request.getSchoolId());
        user.setIsFirstLogin(true);
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUserId(userId);

        userRepository.save(user);

        // Auto-login after registration
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMobile(), request.getPassword()));

        String token = jwtTokenProvider.generateToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Date issuedAt = jwtTokenProvider.getIssuedAtFromToken(token);
        Date expiry = jwtTokenProvider.getExpiryFromToken(token);

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(token)
                .userId(userDetails.getUserId())
                .role(userDetails.getRole())
                .schoolId(userDetails.getSchoolId())
                .name(user.getName())
                .issuedAt(issuedAt)
                .expiryDate(expiry)
                .expiryTimeMs(expiry.getTime())
                .isFirstLogin(true)
                .build();
    }

    @Transactional
    public MessageResponse sendEmailOtp(ForgotPasswordEmailRequest request) {
        userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No account found with email: " + request.getEmail()));

        passwordResetTokenRepository.invalidateAllActiveTokensForEmail(request.getEmail());

        String otp = String.format("%06d", new java.util.Random().nextInt(999999));
        String resetToken = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10); // 10 minutes expiry

        PasswordResetToken prt = new PasswordResetToken(request.getEmail(), otp, resetToken, expiresAt);
        passwordResetTokenRepository.save(prt);

        emailService.sendOtpEmail(request.getEmail(), otp);

        return MessageResponse.builder()
                .message("OTP sent to your email.")
                .success(true)
                .build();
    }

    @Transactional
    public VerifyOtpResponse verifyEmailOtp(VerifyOtpRequest request) {
        PasswordResetToken prt = passwordResetTokenRepository.findByEmailAndOtp(request.getEmail(), request.getOtp())
                .orElseThrow(() -> new BadRequestException("Invalid OTP"));

        if (Boolean.TRUE.equals(prt.getIsUsed())) {
            throw new BadRequestException("OTP has already been used");
        }

        if (prt.isExpired()) {
            throw new BadRequestException("OTP has expired. Please request a new one.");
        }

        prt.setOtpVerified(true);
        passwordResetTokenRepository.save(prt);

        return VerifyOtpResponse.builder()
                .message("OTP verified successfully")
                .token(prt.getToken())
                .build();
    }

    @Transactional
    public MessageResponse resetPasswordWithEmailOtp(ResetPasswordEmailRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        PasswordResetToken prt = passwordResetTokenRepository.findByEmailAndToken(request.getEmail(), request.getToken())
                .orElseThrow(() -> new BadRequestException("Invalid reset token"));

        if (!Boolean.TRUE.equals(prt.getOtpVerified())) {
            throw new BadRequestException("OTP was not verified");
        }

        if (Boolean.TRUE.equals(prt.getIsUsed())) {
            throw new BadRequestException("Reset token has already been used");
        }

        if (prt.isExpired()) {
            throw new BadRequestException("Reset token has expired.");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setIsFirstLogin(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        prt.setIsUsed(true);
        passwordResetTokenRepository.save(prt);

        return MessageResponse.builder()
                .message("Password reset successfully. Please login with your new password.")
                .success(true)
                .build();
    }

    @Transactional
    public MessageResponse logout(String bearerHeader) {
        if (!StringUtils.hasText(bearerHeader) || !bearerHeader.startsWith("Bearer ")) {
            return MessageResponse.builder()
                    .message("Logged out successfully.")
                    .success(true)
                    .build();
        }

        String token = bearerHeader.substring(7);

        // Skip if already blacklisted
        if (blacklistedTokenRepository.existsByToken(token)) {
            return MessageResponse.builder()
                    .message("Already logged out.")
                    .success(true)
                    .build();
        }

        try {
            Date expiry = jwtTokenProvider.getExpiryFromToken(token);
            LocalDateTime expiresAt = expiry.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            blacklistedTokenRepository.save(new BlacklistedToken(token, expiresAt));
        } catch (Exception e) {
            throw new UnauthorizedException("Token is expired or invalid");
        }

        return MessageResponse.builder()
                .message("Logged out successfully.")
                .success(true)
                .build();
    }
}