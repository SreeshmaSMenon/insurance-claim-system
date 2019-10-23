package com.hcl.insuranceclaimsystem.service;

import java.util.List;
import java.util.Optional;
import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.dto.HospitalDetail;
import com.hcl.insuranceclaimsystem.exception.AilmentNotFoundException;
import com.hcl.insuranceclaimsystem.exception.ClaimException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;

public interface ClaimService {
	public Optional<List<ClaimDetailsResponse>> getClaims(Integer userId) throws UserNotFoundException;
	public ClaimEntryOutput claimEntry(ClaimEntryInput claimEntryInput) throws ClaimException,AilmentNotFoundException;
	public String trackClaim(Integer claimId);
	public Optional<List<HospitalDetail>> getAllHospitals();

}
