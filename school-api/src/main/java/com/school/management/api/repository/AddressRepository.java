package com.school.management.api.repository;

import com.school.management.api.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    
    Optional<Address> findByAddressId(String addressId);
    
    List<Address> findBySchoolId(String schoolId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Address a WHERE a.addressId = :addressId")
    void deleteByAddressId(@Param("addressId") String addressId);
}
