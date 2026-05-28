package com.school.management.api.repository;

import com.school.management.api.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    boolean existsByToken(String token);

    /**
     * Periodic cleanup — removes tokens whose JWT expiry has already passed.
     * They can no longer be used regardless, so keeping them wastes space.
     */
    @Modifying
    @Query("DELETE FROM BlacklistedToken b WHERE b.expiresAt < :now")
    void deleteExpiredTokens(@org.springframework.data.repository.query.Param("now") LocalDateTime now);
}
