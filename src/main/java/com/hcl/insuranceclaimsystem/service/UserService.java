package com.hcl.insuranceclaimsystem.service;

import java.util.List;
import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
/**
 * 
 * Service interface for CRUD operations on user.
 * The preferred implementation is {@code UserServiceImpl}.
 * @author Sreeshma S Menon
 *
 */
public interface UserService {
	/**
	 * Method to call service method to retrieve all claim requests for given user id.
	 * @param userId which is the user id to retrieve respective claim requests.
	 * @return List of ClaimResponse which is  optional and includes claim requests for given user id.
	 * @throws UserNotFoundException will throw if the user is not found fot given user id.
	 */
	public Optional<List<ClaimDetailsResponse>> getClaims(Integer userId) throws UserNotFoundException;
	/**
	 *Method to call service method to approve claim with given clam id  for given user id. 
	 * @param claimApproveRequest which includes deatils required to approve the claim.
	 * @param userId which is id of the user who is going to approve the claim.
	 * @return Optional of ClaimDetail which consist of status code and status message.
	 * @throws UserNotFoundException will throw if the user with given id is not present.
	 * @throws ClaimsNotFoundException will throw if claim with given id is not present.
	 */
	Optional<ClaimDetail> approveClaim(Integer userId,ClaimApproveRequest claimApproveRequest)throws UserNotFoundException, ClaimsNotFoundException;


}
