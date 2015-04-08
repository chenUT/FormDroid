package com.chen.formdroid.core.template.form;

/**
 * Created by chen on 4/7/15.
 *
 * Form is the top level of the entire object hierarchy
 */
public class Form {
    //a form must have an id of its own
    private final long formId;

    public Form(long id){
        this.formId = id;
    }

    public long getFormId() {
        return formId;
    }
}
