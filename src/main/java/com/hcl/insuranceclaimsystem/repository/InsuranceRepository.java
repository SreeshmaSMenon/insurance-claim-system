package com.hcl.insuranceclaimsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.insuranceclaimsystem.entity.Insurance;

public interface InsuranceRepository  extends JpaRepository<Insurance, Integer>{

}
