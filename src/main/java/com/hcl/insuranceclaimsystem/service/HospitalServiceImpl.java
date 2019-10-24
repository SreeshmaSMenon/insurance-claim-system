package com.hcl.insuranceclaimsystem.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.insuranceclaimsystem.dto.HospitalDetail;
import com.hcl.insuranceclaimsystem.entity.Hospital;
import com.hcl.insuranceclaimsystem.repository.HospitalDetailRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of HospitalService for retrieve all hospitals.
 * 
 * @author KiruthikaK
 * @since 2019/10/21
 *
 */
@Slf4j
@Service
public class HospitalServiceImpl implements HospitalService {
	
	@Autowired
	HospitalDetailRepository hospitalDetailRepository;

	/**
	 * Method for get all the Hospital details With in the network.
	 * 
	 * @return List of HospitalDetail
	 */
	@Override
	public Optional<List<HospitalDetail>> getAllHospitals() {
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_START_SERVICE);
		List<Hospital> details = hospitalDetailRepository.findAll();
		List<HospitalDetail> hospitalDetailsList = new ArrayList<>();
		HospitalDetail hospitalDetail1 = new HospitalDetail();
		hospitalDetail1.setLabel("");
		hospitalDetail1.setValue("");
		hospitalDetailsList.add(hospitalDetail1);
		details.stream().forEach(detail -> {
			HospitalDetail hospitalDetail = new HospitalDetail();
			hospitalDetail.setLabel(detail.getHospitalName());
			hospitalDetail.setValue(detail.getHospitalName());
			hospitalDetailsList.add(hospitalDetail);
		});
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_END_SERVICE);
		return Optional.of(hospitalDetailsList);
	}
}
