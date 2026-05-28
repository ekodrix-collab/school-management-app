package com.school.management.api.service.authService;

import com.school.management.api.entity.BlacklistedToken;
import com.school.management.api.entity.PasswordResetToken;
import com.school.management.api.entity.User;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.exception.ResourceNotFoundException;
import com.school.management.api.model.requstModel.ForgotPasswordRequest;
import com.school.management.api.model.requstModel.LoginRequest;
import com.school.management.api.model.requstModel.RegisterRequest;
import com.school.management.api.model.requstModel.ResetPasswordRequest;
import com.school.management.api.model.responseModel.AuthResponse;
import com.school.management.api.model.responseModel.ForgotPasswordResponse;
import com.school.management.api.model.responseModel.MessageResponse;
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

    // ─────────────────────────────────────────────────────────────
    //  LOGIN
    // ─────────────────────────────────────────────────────────────

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMobile(), request.getPassword()));

        String token = jwtTokenProvider.generateToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Date issuedAt = jwtTokenProvider.getIssuedAtFromToken(token);
        Date expiry = jwtTokenProvider.getExpiryFromToken(token);

        User user = userRepository.findByMobile(request.getMobile()).orElseThrow();

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

    // ─────────────────────────────────────────────────────────────
    //  REGISTER
    // ─────────────────────────────────────────────────────────────

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

    // ─────────────────────────────────────────────────────────────
    //  FORGOT PASSWORD  (step 1 — request reset token)
    // ─────────────────────────────────────────────────────────────

    @Transactional
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByMobile(request.getMobile())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No account found with mobile: " + request.getMobile()));

        // Invalidate any previous active tokens for this mobile
        passwordResetTokenRepository.invalidateAllActiveTokensForMobile(request.getMobile());

        String resetToken = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(RESET_TOKEN_TTL_MINUTES);

        PasswordResetToken prt = new PasswordResetToken(request.getMobile(), resetToken, expiresAt);
        passwordResetTokenRepository.save(prt);

        return ForgotPasswordResponse.builder()
                .message("Password reset token generated successfully. Use the reset_token to set your new password.")
                .resetToken(resetToken)
                .expiresInMinutes(RESET_TOKEN_TTL_MINUTES)
                .build();
    }

    // ─────────────────────────────────────────────────────────────
    //  RESET PASSWORD  (step 2 — submit new password)
    // ─────────────────────────────────────────────────────────────

    @Transactional
    public MessageResponse resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        PasswordResetToken prt = passwordResetTokenRepository.findByToken(request.getResetToken())
                .orElseThrow(() -> new BadRequestException("Invalid reset token"));

        if (Boolean.TRUE.equals(prt.getIsUsed())) {
            throw new BadRequestException("Reset token has already been used");
        }

        if (prt.isExpired()) {
            throw new BadRequestException("Reset token has expired. Please request a new one.");
        }

        User user = userRepository.findByMobile(prt.getMobile())
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

    // ─────────────────────────────────────────────────────────────
    //  LOGOUT  (blacklist current JWT)
    // ─────────────────────────────────────────────────────────────

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
            // Token is already invalid / expired — nothing to blacklist
        }

        return MessageResponse.builder()
                .message("Logged out successfully.")
                .success(true)
                .build();
    }
}