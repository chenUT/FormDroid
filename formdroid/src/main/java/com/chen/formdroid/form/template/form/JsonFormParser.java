package com.chen.formdroid.form.template.form;

import com.chen.formdroid.FormContext;

import java.io.IOException;

/**
 * Created by chen on 8/5/15.
 */
public class JsonFormParser implements IFormParser{

    private String jsonStr="";

    public JsonFormParser(String jsonStr){
        this.jsonStr = jsonStr;
    }

    @Override
    public Form getForm() {
        try {
            Form form = FormContext.getMapper().readValue(jsonStr, Form.class);
            return form;
        } catch (IOException e) {
            e.printStackTrace();
            String error = e.getMessage();
            return null;
        }
    }
}
