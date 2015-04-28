package com.chen.formdroid.core.template.form;

import com.chen.formdroid.FormContext;

import java.io.IOException;

/**
 * Created by chen on 4/10/15.
 */
public class FormFactory {
    private FormFactory(){}

    public static Form newFormByJson(String jsonStr){
        try {
            Form form = FormContext.getMapper().readValue(jsonStr, Form.class);
            //register in global cache
            FormContext.getInstance().addFormCache(form);
            return form;
        } catch (IOException e) {
            e.printStackTrace();
            String error = e.getMessage();
            return null;
        }
    }
}