package com.hcl.insuranceclaimsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.insuranceclaimsystem.dto.AilmentData;
import com.hcl.insuranceclaimsystem.entity.Ailment;
import com.hcl.insuranceclaimsystem.repository.AilmentRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of AilmentService which will retrieve all available ailments.
 * @since 2019/10/21
 * @author Sreeshma S Menon
 */
@Slf4j
@Service
public class AilmentServiceImpl implements AilmentService {

	@Autowired
	AilmentRepository ailmentRepository;

	/**
	 * Method to retrieve all the ailment available from repository.
	 * 
	 * @return List of AilmentData 
	 */
	@Override
	public Optional<List<AilmentData>> getAllAilment() {
		log.info(InsuranceClaimSystemConstants.AILMENT_INFO_START_SERVICE);
		List<AilmentData> ailmentList = new ArrayList<>();
		List<Ailment> ailmentDetails = ailmentRepository.findAll();
		AilmentData ailmentData1 = new AilmentData();
		ailmentData1.setLabel("");
		ailmentData1.setValue("");
		ailmentList.add(ailmentData1);
		ailmentDetails.forEach(ailmentDetail -> {
			AilmentData ailmentData = new AilmentData();
			ailmentData.setLabel(ailmentDetail.getNatureOfAilment());
			ailmentData.setValue(ailmentDetail.getNatureOfAilment());
			ailmentList.add(ailmentData);
		});
		log.info(InsuranceClaimSystemConstants.AILMENT_INFO_END_SERVICE);
		return Optional.of(ailmentList);
	}

}
