package com.docudile.app.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by FrancAnthony on 3/2/2016.
 */
public class ModTagRequestDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private List<String> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}