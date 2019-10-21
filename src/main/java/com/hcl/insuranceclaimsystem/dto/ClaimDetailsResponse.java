package com.hcl.insuranceclaimsystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimDetailsResponse {
	
	private String ailmentNature;
	private String documentsPath;
	private Integer insuranceNumber;
	private LocalDateTime claimDate;
	private String hospitalName;
	private LocalDate admissionDate;
	private String diagnosis;
	private String claimStatus;
	private Integer claimId;
	private LocalDate dischargeDate;
	private Double totalClaimAmount;
	private String dischargeSummary;
}
