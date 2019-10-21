package com.hcl.insuranceclaimsystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponse {
	private Integer claimId;
	private Integer insuranceNumber;
	private LocalDateTime claimDate;
	private String hospitalName;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	private Double totalClaimAmount;
	private String dischargeSummary;
	private String diagnosis;
	private String ailmentNature;
	private String documentsPath;
	private String claimStatus;
}
