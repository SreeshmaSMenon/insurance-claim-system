package com.hcl.insuranceclaimsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hcl.insuranceclaimsystem.entity.Ailment;

public interface AilmentRepository extends JpaRepository<Ailment, Integer> {

}
