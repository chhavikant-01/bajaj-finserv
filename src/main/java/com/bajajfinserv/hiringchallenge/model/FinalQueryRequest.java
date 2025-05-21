package com.bajajfinserv.hiringchallenge.model;

public class FinalQueryRequest {
    private String finalQuery;
    
    public FinalQueryRequest() {
    }
    
    public FinalQueryRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }
    
    public String getFinalQuery() {
        return finalQuery;
    }
    
    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }
} 