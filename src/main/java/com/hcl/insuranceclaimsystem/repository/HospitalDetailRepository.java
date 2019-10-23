package com.hcl.insuranceclaimsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.Hospital;

@Repository
public interface HospitalDetailRepository extends JpaRepository<Hospital, Integer> {
 public Optional<Hospital>findByHospitalName(String hospitalName);
}
