package com.pivotusventures.weatherserver.model;

/**
 * Created by shikh on 01-Jun-17.
 * <p>
 * Error model class for showing error message
 */


public class Error {

    private String message;

    public Error() {
    }

    public Error(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
