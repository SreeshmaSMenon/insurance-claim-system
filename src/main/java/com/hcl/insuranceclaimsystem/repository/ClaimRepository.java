package com.hcl.insuranceclaimsystem.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer>{
	public List<Claim> findByClaimStatus(String pending);
	@Transactional
	@Modifying
	@Query("UPDATE Claim c SET c.claimStatus=:status WHERE c.claimId=:claimId")
	public int updateStatus(String status,Integer claimId);

}
