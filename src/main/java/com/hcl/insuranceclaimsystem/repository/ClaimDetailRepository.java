package com.hcl.insuranceclaimsystem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
/**
 * Repository for generic CRUD operations for ClaimDetail.
 * @author Sreeshma S Menon
 */
@Repository
public interface ClaimDetailRepository extends JpaRepository<ClaimDetail, Integer>{

	/**
	 * Retrieve a claim for given claimId.
	 * @param claimId which is the claimId to fetch the claim.
	 * @return List which includes claim status.
	 */
	@Query("Select c.approvalStatus from ClaimDetail c where c.claimId=:claimId")
	public List<String> findByClaimId(Integer claimId);
}
