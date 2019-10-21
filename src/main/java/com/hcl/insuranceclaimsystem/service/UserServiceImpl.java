package com.hcl.insuranceclaimsystem.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.repository.ClaimDetailRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.UserRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * This class contains method for user operations
 * 
 * @since 2019/10/21
 * @author Sreeshma S Menon
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ClaimRepository claimRepository;
	@Autowired
	ClaimDetailRepository claimDetailRepository;

	/**
	 * This method will call respective service layer method in order to approve
	 * given claim.
	 * 
	 * @param claimApproveRequest
	 * @param bindingResult
	 * @param userId
	 * @return Optional<String>
	 * @throws UserNotFoundException
	 */
	@Override
	public Optional<ClaimDetail> approveClaim(Integer userId, ClaimApproveRequest claimApproveRequest)
			throws UserNotFoundException {
		log.info(InsuranceClaimSystemConstants.APPROVE_DEBUG_START_SERVICE);
		ClaimDetail claimDetail = new ClaimDetail();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(InsuranceClaimSystemConstants.USER_NOT_FOUND));
		Optional<String> role = userRepository.getUserRole(user.getRoleId());
		if (role.isPresent()) {
			Optional<Claim> claimOptional = claimRepository.findById(claimApproveRequest.getClaimId());
			String status = "";
			if (claimOptional.isPresent() ) {
				if (role.get().equals(InsuranceClaimSystemConstants.FIRST_LEVEL_APPROVER) &&  claimApproveRequest.getClaimStatus().equals(InsuranceClaimSystemConstants.APPROVE)) {
					status = InsuranceClaimSystemConstants.FIRST_LEVEL_APPROVED;
				}else if (role.get().equals(InsuranceClaimSystemConstants.FIRST_LEVEL_APPROVER) &&  claimApproveRequest.getClaimStatus().equals(InsuranceClaimSystemConstants.REJECT)) {
					status = InsuranceClaimSystemConstants.FIRST_LEVEL_REJECTED;
				}
				else if (role.get().equals(InsuranceClaimSystemConstants.SECOND_LEVEL_APPROVER)&&  claimApproveRequest.getClaimStatus().equals(InsuranceClaimSystemConstants.APPROVE)) {
					status = InsuranceClaimSystemConstants.SECOND_LEVEL_APPROVED;
				}else if (role.get().equals(InsuranceClaimSystemConstants.SECOND_LEVEL_APPROVER)&&  claimApproveRequest.getClaimStatus().equals(InsuranceClaimSystemConstants.REJECT)) {
					status = InsuranceClaimSystemConstants.SECOND_LEVEL_REJECTED;
				}
				claimRepository.updateStatus(status, claimOptional.get().getClaimId());
				claimDetail.setApprovalDate(LocalDateTime.now());
				claimDetail.setClaimId(claimOptional.get().getClaimId());
				claimDetail.setApprovalStatus(status);
				claimDetail.setApproverId(userId);
				claimDetail.setComments(claimApproveRequest.getComments());
				claimDetail=claimDetailRepository.save(claimDetail);
			}
			
		}
		log.info(InsuranceClaimSystemConstants.APPROVE_DEBUG_END_SERVICE);
		return Optional.of(claimDetail);
	}

}
