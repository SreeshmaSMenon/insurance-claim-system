package com.hcl.insuranceclaimsystem.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClaimEntryInput {

	private Integer insuranceNumber;
	private String customerName;
	private String hospitalName;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	private Double totalClaimAmount;
	private String dischargeSummary;
	private String diagnosis;
	private String ailmentNature;
}
