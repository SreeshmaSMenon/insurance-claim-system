package com.hcl.insuranceclaimsystem.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.Claim;

/**
 * Repository for generic CRUD operations for Claim.
 * @author Sairam
 */
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer>{
	/**
	 * Retrieve a claim for given claim status.
	 * @param claimStatus which is the status based on which claim will retrieve.
	 * @return List which includes claim entities.
	 */
	public List<Claim> findByClaimStatus(String claimStatus);
	/**
	 * Update the claim status for given claim id.
	 * @param status which is claim status to be update.
	 * @param claimId ,id based on update the cail status.
	 * @return int status of update.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Claim c SET c.claimStatus=:status WHERE c.claimId=:claimId")
	public int updateStatus(String status,Integer claimId);
	/**
	 * Retrieve eligible amount for given claim id.
	 * @param claimId which is the id based on which respective amount will retrieve.
	 * @return Double which eligible amount for given claim id.
	 */
	@Query("SELECT p.eligibleAmount FROM Policy p ,InsurancePolicy ip,Insurance i,Claim c WHERE p.policyNumber=ip.policyNumber AND ip.insuranceNumber=i.insuranceNumber AND i.insuranceNumber=c.insuranceNumber AND c.claimId=:claimId")
	public Optional<Double> getEligibleamount(Integer claimId);
  
}
