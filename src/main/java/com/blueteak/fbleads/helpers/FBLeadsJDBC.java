package com.blueteak.fbleads.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.blueteak.fblead.request.FBLeadRequest;
import com.blueteak.fbleads.constants.FbExtractConstants;
import com.blueteak.fbleads.constants.FbExtractConstants.SelectQueryConstants;

public class FBLeadsJDBC {

	public void isFBLeadExists(List<FBLeadRequest> fbLeadReqList) throws Exception {
		Connection connection = null;
		Statement selectStmt = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(FbExtractConstants.DB_URL, FbExtractConstants.USER, FbExtractConstants.PASS);
			int count = 0;
			int isNotPresent = 0;
			for (int i = 0; i < fbLeadReqList.size(); i++) {
				FBLeadRequest fbLeadReq = fbLeadReqList.get(i);
				System.out.println("Count :: " + count++);
				String selectQuery = String.format(FbExtractConstants.FB_SELECT_QUERY,
						SelectQueryConstants.CREATED_DATE, 
						fbLeadReq.getCustomerFullName(), 
						fbLeadReq.getEmail(),
						fbLeadReq.getModel(), 
						fbLeadReq.getPhoneNumber(), 
						SelectQueryConstants.SRC);
				selectStmt = connection.createStatement();
				ResultSet rs = selectStmt.executeQuery(selectQuery);
				
				if (rs.next()) {
					// present
					fbLeadReqList.remove(i);
				} else {
					// not present
					isNotPresent++;
				}
			} 
			System.out.println("Total :: "+ count +" / Not present count in DB :: "+isNotPresent + " Remaining in list :: "+fbLeadReqList.size());
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			try {
				selectStmt.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
