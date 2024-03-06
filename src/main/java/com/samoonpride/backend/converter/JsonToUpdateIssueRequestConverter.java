package com.samoonpride.backend.converter;

import com.samoonpride.backend.dto.request.UpdateIssueRequest;
import com.samoonpride.backend.enums.IssueStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;

public class JsonToUpdateIssueRequestConverter implements Converter<JSONObject, UpdateIssueRequest> {
    @Override
    public UpdateIssueRequest convert(MappingContext<JSONObject, UpdateIssueRequest> mappingContext) {
        JSONObject source = mappingContext.getSource();
        UpdateIssueRequest updateIssueRequest = new UpdateIssueRequest();
        updateIssueRequest.setIssueId(source.getInt("issueId"));
        if (source.isNull("duplicateIssueId")) {
            updateIssueRequest.setDuplicateIssueId(null);
        } else {
            updateIssueRequest.setDuplicateIssueId(source.getInt("duplicateIssueId"));
        }
        updateIssueRequest.setAssigneeIds(getAssigneeIds(source.getJSONArray("assigneeIds")));
        updateIssueRequest.setTitle(source.getString("title"));
        updateIssueRequest.setLatitude(source.getFloat("latitude"));
        updateIssueRequest.setLongitude(source.getFloat("longitude"));
        updateIssueRequest.setStatus(source.getEnum(IssueStatus.class, "status"));
        return updateIssueRequest;
    }

    private List<Integer> getAssigneeIds(JSONArray jsonArray) {
        List<Integer> assigneeIds = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            assigneeIds.add(jsonArray.getInt(i));
        }
        return assigneeIds;
    }
}
