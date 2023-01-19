package com.blueteak.fbleads.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

import com.blueteak.csvutil.CSVReader;
import com.blueteak.fblead.request.FBLeadRequest;
import com.blueteak.fbleads.constants.FbExtractConstants;

public class FbLeadsExtractHelper {

	public void readFiles(CSVReader csvReader, List<FBLeadRequest> fbLeadReqList)
			throws IOException, FileNotFoundException, ParseException {
		File files = new File(FbExtractConstants.PLACE_TO_READ);
		for (File file : files.listFiles()) {
			if (file.isFile()) {
				fbLeadReqList.addAll(csvReader.ReadFromCSV(FbExtractConstants.PLACE_TO_READ + "/" + file.getName()));
				System.out.println("=================CSV READ SUCCESSFULL :: " + file.getName() + "===================");
				System.out.println("##reqCount## " + fbLeadReqList.size());
			}
		}
	}

	public File getFile(String fileLocation) {
		File myObj = null;
		try {
			myObj = new File(fileLocation);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return myObj;
	}
	
	public String getRandomNumberString() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

}
