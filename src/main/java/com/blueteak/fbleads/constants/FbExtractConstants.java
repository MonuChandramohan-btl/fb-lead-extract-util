package com.blueteak.fbleads.constants;

public class FbExtractConstants {

	public static final String PLACE_TO_READ = "src\\main\\resources\\PlaceFilesToExtract";

	public static final String EXTRACTION_LOCATION = "src\\main\\resources\\ExtractedFiles";

	public static final String EXTRACTED_FILENAME = "fb_Leads_Extracted_";

	public static final String EXTRACTION_CREATED_DATE_LOCATION = "src\\main\\resources\\ExtractedFileCreatedDateScripts";

	public static final String EXTRACTED_CREATED_DATE_FILENAME = "fb_Leads_Extracted_created_date";

	public static final String COMBINED_CSV_LOCATION = "src\\main\\resources\\CombinedCSV";

	public static final String COMBINED_CSV_FILENAME = "fb_combined_csv_";

	public static boolean IS_SYNC_RES = false;

	public static final String DB_URL = "jdbc:sqlserver://15.0.4.4:1433;databaseName=dms-leads";

	public static final String USER = "sa";

	public static final String PASS = "h32YP9cw4N2VJrX8sd";

	public static final String FB_SELECT_QUERY = "select CUST_NAME ,EMAIL ,NOTES ,PHONE,SRC from T_LEAD " 
			+ "where "
			+ "CREATED_DATE_TIME >= '%1$s' and " 
			+ "CUST_NAME =  '%2$s' "
			+ "and EMAIL  =  '%3$s' and"
			+ " CONVERT(VARCHAR(MAX), NOTES) = '%4$s' and "
			+ "PHONE = '%5$s' and  src='%6$s' ";

	public static final String FB_UPDATE_QUERY = "update T_LEAD set CREATED_DATE_TIME =  '%1$s' "
			+ "where CREATED_DATE_TIME >= '%2$s' " + "and CUST_NAME =  '%3$s' and EMAIL  =  '%4$s' "
			+ "and CONVERT(VARCHAR(MAX), NOTES) = '%5$s' and PHONE = '%6$s' and  src='%7$s' ";

	public class QueryConstants {

		public static final String SRC = "FB";

	}

	public class SelectQueryConstants {

		public static final String CREATED_DATE = "2023-01-23";
	}

	public class FilterResponseConstants {

		public static final String AFTER_DATE = "18-Jan-2023";

		public static final String BEFORE_DATE = "23-Jan-2023";
	}

	public class UpdateQueryConstants {

		public static final String EXECUTED_DATE = "2023-01-23";
	}
}
