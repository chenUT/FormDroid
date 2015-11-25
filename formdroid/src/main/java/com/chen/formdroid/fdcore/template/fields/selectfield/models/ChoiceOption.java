package com.chen.formdroid.fdcore.template.fields.selectfield.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chen.liu on 8/5/2015.
 */
public class ChoiceOption {

    @JsonProperty("text")
    private String stringContent;

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

}
