package com.chen.formdroid.core.template.form;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.core.internal.AbsInputField;
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

    @JsonIgnore
    private long formDbId;

    @JsonProperty
    private List<AbsInputField> fields;

    @JsonProperty
    private boolean required = false;

    @JsonProperty
    private String name;

    public Form(){}

    public String getFormId() {
        return formId;
    }

    public long getFormDbId() {
        return formDbId;
    }

    public void setFormDbId(long formDBId) {
        this.formDbId = formDBId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
