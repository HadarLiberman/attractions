package com.example.attractionsapp.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class Comment {

    final public static String COLLECTION_NAME = "comments";

    public Comment() {
        attractionId = "";
        userId = "";
        comment = "";
    }

    String attractionId;
    String userId;
    String comment;
    Long createDate;


    public Comment(String attractionId, String userId, String comment) {
        this.attractionId = attractionId;
        this.userId = userId;
        this.comment = comment;
        this.createDate = new Long(0);
    }

    public String getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(String attractionId) {
        this.attractionId = attractionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("attractionId", attractionId);
        result.put("userId", userId);
        result.put("comment", comment);
        result.put("createDate", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map) {
        this.attractionId = (String) map.get("attractionId");
        this.userId= (String) map.get("userId");
        this.comment= (String) map.get("comment");
        Timestamp ts = (Timestamp) map.get("createDate");
        this.createDate = ts.getSeconds();

    }
}
