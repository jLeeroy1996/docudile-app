package com.docudile.app.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by FrancAnthony on 3/2/2016.
 */
public class ModTagRequestDto {

    @JsonProperty
    private String tagType;

    @JsonProperty
    private String displayName;

    @JsonProperty
    private List<String> data;

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}