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
public class Ailment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ailmentId;
	private String ailmentName;
}
