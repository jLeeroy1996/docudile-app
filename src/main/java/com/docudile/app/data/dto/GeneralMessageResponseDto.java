package com.docudile.app.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by franc on 2/10/2016.
 */
public class GeneralMessageResponseDto implements Serializable {

    @JsonProperty
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
