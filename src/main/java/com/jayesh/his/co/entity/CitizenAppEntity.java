package com.jayesh.his.co.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "CITIZEN_APPS")
public class CitizenAppEntity {

	@Id
	@GeneratedValue
	private Integer appId;
	private String fullName;
	private String email;
	private Long phno;
	private Long ssn;
	private String gender;
	private LocalDate dob;
	private String stateName;
	@CreationTimestamp
	private LocalDate createDate;
	@UpdateTimestamp
	private LocalDate updateDate;
	@Column(updatable = false)
	private String createdBy;
	@Column(insertable = false)
	private String updatedBy;
}
