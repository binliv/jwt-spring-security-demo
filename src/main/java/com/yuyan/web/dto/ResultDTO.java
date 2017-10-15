package com.yuyan.web.dto;

public class ResultDTO {
    private String message;
    private int result;

    public ResultDTO() {
    }

    public ResultDTO(String message, int result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
