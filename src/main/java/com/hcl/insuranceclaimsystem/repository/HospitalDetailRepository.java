package com.hcl.insuranceclaimsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcl.insuranceclaimsystem.entity.Hospital;

/**
 * Repository for generic CRUD operations for Hospital.
 * @author Sairam
 */
@Repository
public interface HospitalDetailRepository extends JpaRepository<Hospital, Integer> {
	/**
	 * Retrieve the Hospital detail based on given hospital name. 
	 * @param hospitalName which is claim status to be update.
	 * @return Optional which contains Hospital entity if found or empty.
	 */
	public Optional<Hospital> findByHospitalName(String hospitalName);
}
