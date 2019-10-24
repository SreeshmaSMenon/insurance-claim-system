package com.hcl.insuranceclaimsystem.service;

import java.util.List;
import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.HospitalDetail;
/**
 * 
 * Service interface to retrieve Hospital.
 * The preferred implementation is {@code HospitalServiceImpl}.
 * @author Kiruthika K
 *
 */
public interface HospitalService {
	/**
	 * Retrieve all hospital names.
	 * @return List of HospitalDetail which optional.
	 */
	public Optional<List<HospitalDetail>> getAllHospitals();

}
