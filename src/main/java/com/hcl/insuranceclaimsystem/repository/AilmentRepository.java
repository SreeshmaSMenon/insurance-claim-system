package com.hcl.insuranceclaimsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hcl.insuranceclaimsystem.entity.AilmentDetail;

public interface AilmentRepository extends JpaRepository<AilmentDetail, Integer> {

}
