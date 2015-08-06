package com.chen.formdroid.form.template.form;

import android.text.TextUtils;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.form.internal.AbsInputField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by chen on 4/7/15.
 *
 * Form is the top level of the entire object hierarchy
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Form {
    //a form must have an id of its own
    //default is a random uuid
    //TODO should we make this field a requirement?
    @JsonProperty
    private String formId = UUID.randomUUID().toString();

    @JsonProperty
    private List<AbsInputField> fields;

    @JsonProperty
    private boolean required = false;

    @JsonProperty
    private String title;

    public Form(){}

    public String getFormId() {
        return formId;
    }

    public List<AbsInputField> getFields() {
        return fields;
    }

    public void setFields(List<AbsInputField> fields) {
        this.fields = fields;
    }

    public void addField(AbsInputField field){
        if(this.fields == null){
            this.fields = new ArrayList<>();
        }
        this.fields.add(field);
    }

    //if title is missing fall to formId
    public String getTitle() {
        if(TextUtils.isEmpty(title)){
            return this.formId;
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String toJsonString(){
        try {
            String jsonStr = FormContext.getMapper().writeValueAsString(this);
            return jsonStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            String et = e.getMessage();
            return "";
        }
    }
}
