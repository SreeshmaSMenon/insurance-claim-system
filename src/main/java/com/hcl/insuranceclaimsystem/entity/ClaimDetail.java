package com.hcl.insuranceclaimsystem.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ClaimDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer claimDetailId;
	private Integer claimId;
	private Integer approverId;
	private String approvalStatus;
	private String comments;
}
