package com.school.management.api.service.authService;

import com.school.management.api.entity.User;
import com.school.management.api.repository.UserRepository;
import com.school.management.api.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Spring Security calls this during login.
     * "username" here is the mobile number (our login field).
     */
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with mobile: " + mobile));
        return new CustomUserDetails(user);
    }

    /**
     * Used by JwtAuthFilter to reload user from the userId stored in the token.
     */
    public UserDetails loadUserByUserId(UUID userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with userId: " + userId));
        return new CustomUserDetails(user);
    }
}