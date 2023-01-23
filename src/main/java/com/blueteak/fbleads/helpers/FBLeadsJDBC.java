package com.blueteak.fbleads.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.blueteak.fblead.request.FBLeadRequest;
import com.blueteak.fbleads.constants.FbExtractConstants;
import com.blueteak.fbleads.constants.FbExtractConstants.QueryConstants;

public class FBLeadsJDBC {

	public List<FBLeadRequest> isFBLeadExists(List<FBLeadRequest> fbLeadReqList) throws Exception {
		Connection connection = null;
		Statement selectStmt = null;
		List<FBLeadRequest> newfbLeadReqList = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(FbExtractConstants.DB_URL, FbExtractConstants.USER, FbExtractConstants.PASS);
			int count = 0;
			int isNotPresent = 0;
			for (int i = 0; i < fbLeadReqList.size(); i++) {
				FBLeadRequest fbLeadReq = fbLeadReqList.get(i);
				System.out.println("Count :: " + count++);
				String selectQuery = String.format(FbExtractConstants.FB_SELECT_QUERY,
						fbLeadReq.getCreatedDateTime(), 
						fbLeadReq.getCustomerFullName(), 
						fbLeadReq.getEmail(),
						fbLeadReq.getModel(), 
						fbLeadReq.getPhoneNumber(), 
						QueryConstants.SRC);
				selectStmt = connection.createStatement();
				ResultSet rs = selectStmt.executeQuery(selectQuery);
				
				if (rs.next()) {
					// present
					//newfbLeadReqList.add(fbLeadReq);
				} else {
					// not present
					newfbLeadReqList.add(fbLeadReq);
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
		return newfbLeadReqList;
	}
}
