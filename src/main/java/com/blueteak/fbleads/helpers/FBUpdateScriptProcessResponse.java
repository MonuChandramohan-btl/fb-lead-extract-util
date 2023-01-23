package com.blueteak.fbleads.helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.blueteak.fblead.request.FBLeadRequest;
import com.blueteak.fbleads.constants.FbExtractConstants;
import com.blueteak.fbleads.constants.FbExtractConstants.QueryConstants;
import com.blueteak.fbleads.constants.FbExtractConstants.UpdateQueryConstants;

public class FBUpdateScriptProcessResponse {

	private FbLeadsExtractHelper fbLeadsHelper;

	public FBUpdateScriptProcessResponse() {
		this.fbLeadsHelper = new FbLeadsExtractHelper();
	}

	public void processResponse(List<FBLeadRequest> fbLeadReqList, String udpateScriptFileName)
			throws IOException, ParseException {
		File myObj = this.fbLeadsHelper.getFile(udpateScriptFileName);
		FileWriter fileWriter = new FileWriter(myObj);
		try {
			int count = 0;
			for (FBLeadRequest fbLeadReq : fbLeadReqList) {
				System.out.println("Count :: " + count++);
//				Date createdDate = DateUtil.getDate(fbLeadReq.getCreatedDateTime());
//				Date afterDate = DateUtil.genAfterDate();
//				Date beforeDate = DateUtil.genBeforeDate();
//				if (createdDate.after(afterDate) && createdDate.before(beforeDate)) {
					String updateQuery = String.format(FbExtractConstants.FB_UPDATE_QUERY,
							fbLeadReq.getCreatedDateTime(), UpdateQueryConstants.EXECUTED_DATE ,
							fbLeadReq.getCustomerFullName(), fbLeadReq.getEmail(),
							fbLeadReq.getModel(), fbLeadReq.getPhoneNumber(),
							QueryConstants.SRC);
					fileWriter.write(updateQuery + "  \n");
				}

//			}
		} finally {
			fileWriter.close();
		}
	}
}
