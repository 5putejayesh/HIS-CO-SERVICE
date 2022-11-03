package com.jayesh.his.co.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CO_TRIGGERS ")
public class CoTriggerEntity {
	
	@Id
	private Integer triggerId;
	private Integer eligId;	
	private String triggerStatus;
	@Lob
	private byte[] fileData;

}
