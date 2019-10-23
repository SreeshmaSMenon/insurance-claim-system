package com.hcl.insuranceclaimsystem.service;

import java.util.List;
import java.util.Optional;
import com.hcl.insuranceclaimsystem.dto.AilmentData;

/**
 * 
 * Service interface to retrieve Ailment.
 * The preferred implementation is {@code AilmentServiceImpl}.
 * @author Sreeshma S Menon
 *
 */
public interface AilmentService {
	/**
	 * Retrieve all ailments.
	 * @return List of AilmentData.
	 */
	public Optional<List<AilmentData>> getAllAilment();
}
