package com.blueteak.fbleads.extraction;

import java.util.ArrayList;
import java.util.List;

import com.blueteak.csvutil.CSVReader;
import com.blueteak.csvutil.CSVWriter;
import com.blueteak.fblead.request.FBLeadRequest;
import com.blueteak.fbleads.constants.FbExtractConstants;
import com.blueteak.fbleads.helpers.FBLeadsJDBC;
import com.blueteak.fbleads.helpers.FBResponseProcessor;
import com.blueteak.fbleads.helpers.FBUpdateScriptProcessResponse;
import com.blueteak.fbleads.helpers.FbLeadsExtractHelper;

public class FbLeadsExtract {

	private CSVReader csvReader;
	private FbLeadsExtractHelper fbLeadsHelper;
	private FBResponseProcessor fbResponseProcessor;
	private FBLeadsJDBC fbLeadsJDBC;
	private FBUpdateScriptProcessResponse fbUpdateScriptProcessResponse;

	public FbLeadsExtract() {
		this.csvReader = new CSVReader();
		this.fbLeadsHelper = new FbLeadsExtractHelper();
		this.fbResponseProcessor = new FBResponseProcessor();
		this.fbLeadsJDBC = new FBLeadsJDBC();
		this.fbUpdateScriptProcessResponse = new FBUpdateScriptProcessResponse();
	}

	public static void main(String[] args) throws Exception {
	
		FbLeadsExtract fbLeadsExtract = new FbLeadsExtract();
		List<FBLeadRequest> fbLeadReqList = new ArrayList<FBLeadRequest>();
		
		// Extract records from CSV
		fbLeadsExtract.fbLeadsHelper.readFiles(fbLeadsExtract.csvReader, fbLeadReqList);
		System.out.println("################### File Read done ######################");
		
//		// check if existing in DB
		fbLeadReqList = fbLeadsExtract.fbLeadsJDBC.isFBLeadExists(fbLeadReqList);
		System.out.println("################### JDBC Check done ######################");
		
		CSVWriter csvWriter = new CSVWriter(fbLeadReqList);
		String genCSVFileName = FbExtractConstants.COMBINED_CSV_LOCATION + "/" + FbExtractConstants.COMBINED_CSV_FILENAME + java.time.LocalDate.now() + "-"
				+ fbLeadsExtract.fbLeadsHelper.getRandomNumberString() + ".csv";
		csvWriter.writeCSV(genCSVFileName);
	
		
		// generate log file
		String genFileName = FbExtractConstants.EXTRACTION_LOCATION + "/" + FbExtractConstants.EXTRACTED_FILENAME + java.time.LocalDate.now() + "-"
				+ fbLeadsExtract.fbLeadsHelper.getRandomNumberString() + ".txt";
		fbLeadsExtract.fbResponseProcessor.processResponse(fbLeadReqList, genFileName);
		System.out.println("################### Process Response done ######################");
		
		// generate log file
		String updateScriptFileName = FbExtractConstants.EXTRACTION_CREATED_DATE_LOCATION + "/" + FbExtractConstants.EXTRACTED_CREATED_DATE_FILENAME + java.time.LocalDate.now() + "-"
						+ fbLeadsExtract.fbLeadsHelper.getRandomNumberString() + ".sql";
		fbLeadsExtract.fbUpdateScriptProcessResponse.processResponse(fbLeadReqList, updateScriptFileName);
		System.out.println("################### SQL :: Process  Response done ######################");
	}
	
}
