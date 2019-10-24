package com.hcl.insuranceclaimsystem.service;

import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.exception.AilmentNotFoundException;
import com.hcl.insuranceclaimsystem.exception.ClaimException;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
/**
 * Service interface for CRUD operations on claim repository.
 * The preferred implementation is {@code ClaimServiceImpl}.
 * @author Sairam
 *
 */
public interface ClaimService {
	/**
	 * Create a claim entry with given details.
	 * @param claimEntryInput which includes details required to create claim entry.
	 * @return ClaimEntryOutput which includes response details for the created claim entry.
	 * @throws AilmentNotFoundException will throw if ailment for the given claim is not present.
	 * @throws ClaimException will throw if given policy number is invalid, if given claim amount
	 *         is invalid, if given admission and discharge date range is invalid.
	 */
	public ClaimEntryOutput claimEntry(ClaimEntryInput claimEntryInput) throws ClaimException,AilmentNotFoundException;
	/**
	 * Method to track the status of the given claim.
	 * @param claimId this is the id of claim for which status to be return.
	 * @return String which is the current status of claim.
	 * @throws ClaimsNotFoundException will throw if requested claim not found.
	 */
	public String trackClaim(Integer claimId) throws ClaimsNotFoundException;
}
