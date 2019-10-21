package com.hcl.insuranceclaimsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.HospitalDetail;

@Repository
public interface HospitalDetailRepository extends JpaRepository<HospitalDetail, Integer> {

}
