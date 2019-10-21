package com.hcl.insuranceclaimsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.insuranceclaimsystem.dto.AilmentData;
import com.hcl.insuranceclaimsystem.entity.AilmentDetail;
import com.hcl.insuranceclaimsystem.repository.AilmentRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains methods for ailment operations
 * 
 * @since 2019/10/21
 * @author Sreeshma S Menon
 */
@Slf4j
@Service
public class AilmentServiceImpl implements AilmentService {
	
	@Autowired
	AilmentRepository ailmentRepository;

	/**
	 * This method will retrieve all the ailment available from database.
	 * @return Optional<List<AilmentData>>
	 */
	@Override
	public Optional<List<AilmentData>> getAllAilment() {
		log.info(InsuranceClaimSystemConstants.AILMENT_DEBUG_START_SERVICE);
		List<AilmentData> ailmentList=new ArrayList<>();
		List<AilmentDetail> ailmentDetails=ailmentRepository.findAll();
		ailmentDetails.forEach(ailmentDetail->{
			AilmentData ailmentData=new AilmentData();
			BeanUtils.copyProperties(ailmentDetail, ailmentData);
			ailmentList.add(ailmentData);
			});
		log.info(InsuranceClaimSystemConstants.AILMENT_DEBUG_END_SERVICE);
		return Optional.of(ailmentList);
	}

}
