package com.school.management.api.service.authService;

import com.school.management.api.entity.User;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.LoginRequest;
import com.school.management.api.model.requstModel.RegisterRequest;
import com.school.management.api.model.responseModel.AuthResponse;
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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static com.school.management.api.service.mapper.MapperService.generateUserId;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMobile(), request.getPassword()));
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
                .name(user.getName())
                .issuedAt(issuedAt)
                .expiryDate(expiry)
                .expiryTimeMs(expiry.getTime())
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
                .name(user.getName())
                .issuedAt(issuedAt)
                .expiryDate(expiry)
                .expiryTimeMs(expiry.getTime())
                .build();
    }

}