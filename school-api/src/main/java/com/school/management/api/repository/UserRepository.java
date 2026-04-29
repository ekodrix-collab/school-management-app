package com.school.management.api.repository;

import com.school.management.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByMobile(String mobile);

    Optional<User> findByUserId(UUID userId);

    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);

}
