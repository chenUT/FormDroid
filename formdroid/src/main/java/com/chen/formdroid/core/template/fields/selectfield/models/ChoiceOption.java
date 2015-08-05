package com.chen.formdroid.core.template.fields.selectfield.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chen.liu on 8/5/2015.
 */
public class ChoiceOption {

    private int id;

    @JsonProperty("text")
    private String stringContent;

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
