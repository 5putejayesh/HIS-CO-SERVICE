package com.jayesh.his.co.entity;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	private Blob fileData;

}
