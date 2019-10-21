package com.hcl.insuranceclaimsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcl.insuranceclaimsystem.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer>{

	public List<Claim> findByClaimStatus(String pending);

}
