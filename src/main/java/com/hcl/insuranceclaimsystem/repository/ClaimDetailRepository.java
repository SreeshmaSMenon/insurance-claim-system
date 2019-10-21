package com.hcl.insuranceclaimsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
@Repository
public interface ClaimDetailRepository extends JpaRepository<ClaimDetail, Integer>{

	@Query("Select c.approvalStatus from ClaimDetail c where c.claimId=:claimId")
	public List<String> findByClaimId(Integer claimId);

}
