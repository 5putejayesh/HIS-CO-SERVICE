package com.jayesh.his.co.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayesh.his.co.binding.CoResponse;
import com.jayesh.his.co.entity.CoTriggerEntity;
import com.jayesh.his.co.entity.EligDtls;
import com.jayesh.his.co.repo.CoTriggerRepo;
import com.jayesh.his.co.repo.EligDtlsRepo;
import com.jayesh.his.co.service.CoService;
import com.jayesh.his.co.util.EmailUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class CoServiceImpl implements CoService {

	@Autowired
	private EligDtlsRepo eligDtlsRepo;
	@Autowired
	private CoTriggerRepo triggerRepo;
	@Autowired
	private EmailUtils emailUtils;
	
	
	@Override
	public CoResponse processCoTriggers()  {

		List<CoTriggerEntity> pendingTriggers = triggerRepo.findByTriggerStatus("Pending");
		CoResponse response=new CoResponse();
		response.setTotalTriggers(Long.valueOf(pendingTriggers.size()));
		Long success=0L;
		Long failed=0L;
		for (CoTriggerEntity trigger : pendingTriggers) {
			Optional<EligDtls> eligdtlsEntity = eligDtlsRepo.findById(trigger.getEligId());
			if (eligdtlsEntity.isPresent()) {
				EligDtls eligDtls = eligdtlsEntity.get();
				
				
				
				
				//generate pdf
				try {
					generatePdfAndSendMail(eligDtls,trigger);
					success++;
				} catch (Exception e) {
					e.printStackTrace();
					failed++;
				}
				
				
				
				
			}
		}
		response.setFailedTrigger(failed);
		response.setSuccessTriggers(success);
		
		return response;
	}


	private void generatePdfAndSendMail(EligDtls dtls,CoTriggerEntity triggerEntity) throws Exception {

		Document document = new Document(PageSize.A4);
		
			File file = new File("CoTrigger" + dtls.getEligId() + ".pdf");
			FileOutputStream fileOutputStream=null;
			try {
				fileOutputStream = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// PdfWriter.getInstance(document, response.getOutputStream());
			PdfWriter.getInstance(document, fileOutputStream);
			document.open();
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			font.setSize(18);
			font.setColor(Color.BLUE);

			Paragraph p = createPpdfPara(font);

			PdfPTable table = createPdfTable();

			writePdfHeader(table);

			writePdfData(dtls, table);

			document.add(p);
			document.add(table);
			document.close();
			
			
			fileOutputStream.close();
			String mailBody = "Dear" + dtls.getHoldersName() + ",PFA plan details";

			emailUtils.sendEmail(dtls.getEmail(), "HIS Pland Details", mailBody,
					file);
			updateCoTriggerRecord(triggerEntity,file);
			file.delete();
		
		
	}

	private PdfPTable createPdfTable() {
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 3.5f, 1.5f, 1.5f, 3.0f, 3.0f, 1.5f, 3.0f });
		table.setSpacingBefore(10);
		return table;
	}

	private Paragraph createPpdfPara(Font font) {
		Paragraph p = new Paragraph("Correspondence Notice", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		return p;
	}

	private void writePdfData(EligDtls dtls, PdfPTable table) {

		table.addCell(dtls.getHoldersName());
		table.addCell(dtls.getPlanName());
		table.addCell(dtls.getPlanStatus());
		table.addCell(dtls.getPlanStartDate().toString());
		table.addCell(dtls.getPlanEndDate().toString());
		table.addCell(StringUtils.isNotEmpty(String.valueOf(dtls.getBenefitAmt()))?String.valueOf(dtls.getBenefitAmt()):"NA");
		table.addCell(StringUtils.isNotEmpty(dtls.getDenielReason())?dtls.getDenielReason():"NA");
	}

	private void writePdfHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA);
		headerFont.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("citizen Name", headerFont));

		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Name", headerFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Status", headerFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Start Date", headerFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan End Date", headerFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Benefit Amount", headerFont));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Deniel Reason", headerFont));
		table.addCell(cell);
	}

	
	private void updateCoTriggerRecord(CoTriggerEntity triggerEntity, File file) throws IOException {
		FileInputStream fis= new FileInputStream(file);
			 
			//Blob fileBolb = new SerialBlob(IOUtils.toByteArray(fis));
			
			byte [] fileArray=new byte[(byte)file.length()];
			
			fis.read(fileArray);
			
			triggerEntity.setFileData(fileArray);
			triggerEntity.setTriggerStatus("Processed");
			triggerRepo.save(triggerEntity);
			fis.close();

		
		
		
	}

}
