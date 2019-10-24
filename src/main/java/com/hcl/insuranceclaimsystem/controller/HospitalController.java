package com.hcl.insuranceclaimsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.insuranceclaimsystem.dto.HospitalDetail;
import com.hcl.insuranceclaimsystem.exception.HospitalNotFoundException;
import com.hcl.insuranceclaimsystem.service.HospitalService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for handling the requests and responses to retrieve hospiatl names.
 * @author Kiruthika K
 * @since 2019/10/21
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/hospitals")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class HospitalController {
	
	@Autowired
	HospitalService hospitalService;
	
	/**
	 * Method to call service for retrieve all hospital names.
	 * @return List of HospitalDetail
	 * @throws HospitalNotFoundException will throw if no hospital detail not found.
	 */
	@GetMapping("/")
	public ResponseEntity<List<HospitalDetail>> getAllHospitals() throws HospitalNotFoundException  {
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_START_CONTROLLER);
		Optional<List<HospitalDetail>> hospitalDetails = hospitalService.getAllHospitals();
		if (!hospitalDetails.isPresent()) {
			throw new HospitalNotFoundException(InsuranceClaimSystemConstants.HOSPITAL_NOT_FOUND);
		}
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_END_CONTROLLER);
		return new ResponseEntity<>(hospitalDetails.get(), HttpStatus.OK);
	}
	
	

}
