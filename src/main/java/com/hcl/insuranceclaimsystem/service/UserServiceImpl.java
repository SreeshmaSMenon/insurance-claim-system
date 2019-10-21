package com.hcl.insuranceclaimsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.repository.UserRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;
import lombok.extern.slf4j.Slf4j;
/**
 * This class contains method for user operations
 * @since 2019/10/21
 * @author Sreeshma S Menon
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	
	/**
	  * This method will call respective service layer method in order to approve given claim.
	  * @param claimApproveRequest
	  * @param bindingResult
	  * @param userId
	  * @return Optional<String>
	 * @throws UserNotFoundException 
	  */
	@Override
	public Optional<String> approveClaim(Integer userId,ClaimApproveRequest claimApproveRequest) throws UserNotFoundException {
		 log.info(InsuranceClaimSystemConstants.APPROVE_DEBUG_START_SERVICE);
		 User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(InsuranceClaimSystemConstants.USER_NOT_FOUND));
		 Optional<String> role=userRepository.getUserRole(user.getRoleId());
		 if(role.isPresent()) {
			 if(role.equals("FIRST_LEVEL_APPROVER")) {
				 
			 }else if(role.equals("FIRST_LEVEL_APPROVER")) {
				 
			 }
		 }
		 log.info(InsuranceClaimSystemConstants.APPROVE_DEBUG_END_SERVICE);
		return null;
	}

}
