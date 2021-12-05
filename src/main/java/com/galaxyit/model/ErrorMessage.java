package com.galaxyit.model;

import java.util.Date;

public class ErrorMessage {
    private Date date;
    private String errorMessageDescription;

    public ErrorMessage(Date date, String errorMessageDescription) {
        this.date = date;
        this.errorMessageDescription = errorMessageDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMessageDescription() {
        return errorMessageDescription;
    }

    public void setErrorMessageDescription(String errorMessageDescription) {
        this.errorMessageDescription = errorMessageDescription;
    }
}
