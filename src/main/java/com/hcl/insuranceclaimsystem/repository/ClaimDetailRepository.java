package com.hcl.insuranceclaimsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
@Repository
public interface ClaimDetailRepository extends JpaRepository<ClaimDetail, Integer> {

}
