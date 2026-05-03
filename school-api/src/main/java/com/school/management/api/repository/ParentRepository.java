package com.school.management.api.repository;

import com.school.management.api.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {

    Optional<Parent> findByMobile(String mobile);
}
