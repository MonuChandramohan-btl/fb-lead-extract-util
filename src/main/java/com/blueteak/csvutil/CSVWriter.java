package com.blueteak.csvutil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.blueteak.fblead.request.FBLeadRequest;

public class CSVWriter{


	static volatile boolean isHeadedAdded = false;
	private String writeCsvFilename = null;
	private List<FBLeadRequest> fbLeadReqList = new ArrayList<>();

	public CSVWriter(String writeCsvFilename, List<FBLeadRequest> fbLeadReqList) {
		this.writeCsvFilename = writeCsvFilename;
		this.fbLeadReqList = fbLeadReqList;
	}
	
	/**
	 * @param writeCsvFilename
	 * @param leadResList
	 * 
	 */
	public void writeCSV() {
		System.out.println("Started writing csv..");
		ICsvBeanWriter beanWriter = null;
		try {
			beanWriter = new CsvBeanWriter(new FileWriter(CSVUtil.getAbsPath() +"/Recovered_"+  writeCsvFilename, isHeadedAdded),
					CsvPreference.STANDARD_PREFERENCE);
			final String[] header = new String[] {  "LEADID", "CREATEDDATETIME", "MODEL", "QUESTION1",
					"QUESTION2", "CUSTOMERFULLNAME" ,"EMAIL","PHONENUMBER"};
			final String[] mapping = new String[] { "leadId", "createdDateTime", "model", "question1",
					"question2", "customerFullName" ,"email","phoneNumber"};

			if (!isHeadedAdded) {
				beanWriter.writeHeader(header);
				isHeadedAdded = true;
			}

			// write the beans data
			for (FBLeadRequest c : fbLeadReqList) {
				beanWriter.write(c, mapping);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				beanWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("writing csv completed..");
		}
	}

}
