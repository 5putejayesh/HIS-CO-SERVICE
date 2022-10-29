package com.jayesh.his.co.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DC_CASES")
public class DcCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer caseId;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long caseNo;
	private Integer appId;
	private Integer planId;
	
	
}
