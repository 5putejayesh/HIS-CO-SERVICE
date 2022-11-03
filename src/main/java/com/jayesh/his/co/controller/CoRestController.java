package com.jayesh.his.co.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jayesh.his.co.binding.CoResponse;
import com.jayesh.his.co.service.CoService;

@RestController
public class CoRestController {

	@Autowired
	private CoService coService;
	
	@GetMapping("/process")
	public ResponseEntity<CoResponse> processPendingTriggers() throws SerialException, SQLException, IOException{
		CoResponse response = coService.processCoTriggers();
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
