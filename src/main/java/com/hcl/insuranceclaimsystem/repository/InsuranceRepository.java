package com.hcl.insuranceclaimsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcl.insuranceclaimsystem.entity.Insurance;

/**
 * Repository for generic CRUD operations for Insurance.
 * @author Sairam
 */
@Repository
public interface InsuranceRepository  extends JpaRepository<Insurance, Integer>{

}
