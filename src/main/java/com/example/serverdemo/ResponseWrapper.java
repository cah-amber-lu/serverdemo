package com.example.serverdemo;

import java.util.List;

public class ResponseWrapper {

    private List<ApiResponse> response;

    private ElapsedTime elapsedTime;

    public List<ApiResponse> getResponse() {
        return response;
    }

    public void setResponse(List<ApiResponse> response) {
        this.response = response;
    }

    public ElapsedTime getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
