package com.hcl.insuranceclaimsystem.service;

import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.exception.CommonException;

public interface ClaimService {
	
	public ClaimEntryOutput claimEntry( ClaimEntryInput claimEntryInput) throws CommonException;
	public ClaimEntryOutput trackClaim( ClaimEntryInput claimEntryInput) throws CommonException;

}
