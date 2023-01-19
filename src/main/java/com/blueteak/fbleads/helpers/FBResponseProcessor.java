package com.blueteak.fbleads.helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.blueteak.fblead.request.FBLeadRequest;
import com.blueteak.fbleads.constants.FbExtractConstants;
import com.blueteak.fbleads.constants.FbExtractConstants.FilterResponseConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FBResponseProcessor {

	private FbLeadsExtractHelper fbLeadsHelper;

	public FBResponseProcessor() {
		this.fbLeadsHelper = new FbLeadsExtractHelper();
	}

	public void processResponse(List<FBLeadRequest> fbLeadReqList, String genFileName) throws IOException, ParseException {
		File myObj = this.fbLeadsHelper.getFile(genFileName);
		FileWriter fileWriter = new FileWriter(myObj);
		ObjectMapper objMapper = new ObjectMapper();
		try {
			int count = 0;
			for (FBLeadRequest fbLeadReq : fbLeadReqList) {
				System.out.println("Count :: " + count++);
				Date createdDate = DateUtil.getDate(fbLeadReq.getCreatedDateTime());
				Date afterDate = DateUtil.genAfterDate();
				Date beforeDate = DateUtil.genBeforeDate();
				String processedJson = "";
				if (createdDate.after(afterDate) && createdDate.before(beforeDate)) {
					if (FbExtractConstants.IS_SYNC_RES) {
						processedJson = generateSyncResponse(objMapper, fbLeadReq);
					} else {
						processedJson = objMapper.writeValueAsString(fbLeadReq);
					}
					System.out.println(processedJson);
					fileWriter.write(processedJson + "  \n");
				}
			}
		} finally {
			fileWriter.close();
		}
	}

	
	
	private String generateSyncResponse(ObjectMapper objMapper, FBLeadRequest fbLeadReq)
			throws JsonProcessingException {
		String processedJson;
		ArrayNode array = objMapper.createArrayNode();

		ObjectNode phoneNumber = objMapper.createObjectNode();
		phoneNumber.put("name", "Phone Number");
		phoneNumber.put("value", fbLeadReq.getPhoneNumber());
		array.add(phoneNumber);

		ObjectNode conditional_question_2 = objMapper.createObjectNode();
		conditional_question_2.put("name", "conditional_question_2");
		conditional_question_2.put("value", fbLeadReq.getQuestion2());
		array.add(conditional_question_2);

		ObjectNode email = objMapper.createObjectNode();
		email.put("name", "Email");
		email.put("value", fbLeadReq.getEmail());
		array.add(email);

		ObjectNode fullName = objMapper.createObjectNode();
		fullName.put("name", "Full Name");
		fullName.put("value", fbLeadReq.getCustomerFullName());
		array.add(fullName);

		ObjectNode conditional_question_1 = objMapper.createObjectNode();
		conditional_question_1.put("name", "conditional_question_1");
		conditional_question_1.put("value", fbLeadReq.getQuestion1());
		array.add(conditional_question_1);

		ObjectNode model = objMapper.createObjectNode();
		model.put("name", "which_model_are_you_interested_in");
		model.put("value", fbLeadReq.getModel());
		array.add(model);

		ObjectNode event = objMapper.createObjectNode();
		event.put("event", array);

		processedJson = objMapper.writeValueAsString(event);
		return processedJson;
	}
}
