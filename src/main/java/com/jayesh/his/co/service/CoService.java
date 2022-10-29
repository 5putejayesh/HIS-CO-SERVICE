package com.jayesh.his.co.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import com.jayesh.his.co.entity.CoTriggerEntity;
import com.jayesh.his.co.entity.EligDtls;

public interface CoService {
	
	public String processCoTriggers() throws SerialException, SQLException, IOException;
	public FileInputStream generatePdf(EligDtls dtls);
	public boolean updateCoTriggerRecord(CoTriggerEntity triggerEntity, FileInputStream fis);

}
