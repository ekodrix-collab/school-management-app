package com.school.management.api.repository;

import com.school.management.api.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findTopByMobileOrderByCreatedAtDesc(String mobile);

    @Modifying
    @Query("UPDATE PasswordResetToken p SET p.isUsed = true WHERE p.mobile = :mobile AND p.isUsed = false")
    void invalidateAllActiveTokensForMobile(@Param("mobile") String mobile);
}
