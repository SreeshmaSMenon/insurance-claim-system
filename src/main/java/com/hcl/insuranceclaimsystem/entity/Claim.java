package com.hcl.insuranceclaimsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Claim {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
