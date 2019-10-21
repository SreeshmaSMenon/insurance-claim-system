package com.hcl.insuranceclaimsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.insuranceclaimsystem.dto.ClaimResponse;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.RoleRepository;
import com.hcl.insuranceclaimsystem.repository.UserRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

@Service
public class ClaimServiceImpl implements ClaimService {

	@Autowired
	ClaimRepository claimRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<List<ClaimResponse>> getClaims(Integer userId) throws UserNotFoundException {

		Optional<String> role = userRepository.getUserRole(userId);
		role.orElseThrow(() -> new UserNotFoundException(InsuranceClaimSystemConstants.USER_NOT_FOUND));
		List<ClaimResponse> claimResponses = new ArrayList<>();
		List<Claim> claims = new ArrayList<>();

		if (role.get().equalsIgnoreCase(InsuranceClaimSystemConstants.FIRST_LEVEL_APPROVER)) {
			claims = claimRepository.findByClaimStatus(InsuranceClaimSystemConstants.PENDING);
		} else if (role.get().equalsIgnoreCase(InsuranceClaimSystemConstants.SECOND_LEVEL_APPROVER)) {
			claims = claimRepository.findByClaimStatus(InsuranceClaimSystemConstants.FIRST_LEVEL_APPROVED);
		}
		claims.forEach((claim) -> {

			claimResponses.add(new ClaimResponse(claim.getClaimId(), claim.getInsuranceNumber(), claim.getClaimDate(),
					claim.getHospitalName(), claim.getAdmissionDate(), claim.getDischargeDate(),
					claim.getTotalClaimAmount(), claim.getDischargeSummary(), claim.getDiagnosis(),
					claim.getAilmentNature(), claim.getDocumentsPath(), claim.getClaimStatus()));
		});

		return Optional.of(claimResponses);
	}

}
