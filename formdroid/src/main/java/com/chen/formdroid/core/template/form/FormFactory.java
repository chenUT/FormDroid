package com.chen.formdroid.core.template.form;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.utils.StringUtils;

import java.io.IOException;

/**
 * Created by chen on 4/10/15.
 */
public class FormFactory {
    private FormFactory(){}

    public static Form getFormById(String formId){
        Form result = FormContext.getInstance().getForm(formId);
        return result;
    }

    /**
     * this method will load the exsiting form if the system storage has it otherwise it create a new one
     * TODO a better way to extract formId
     * @param jsonStr
     * @return
     */
    public static Form loadForm(String jsonStr){
         try {
            Form form = FormContext.getMapper().readValue(jsonStr, Form.class);
            if(form == null){
                return null;
            }
            String formId = form.getFormId();
            Form oldForm = getFormById(formId);
            if(oldForm != null){
                FormContext.getInstance().addToCache(oldForm);
                return oldForm;
            }
             //register in global cache and persistance for new created form
            initFormToCache(jsonStr, form);
            return form;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * this will create a new form and delete the old one with the same form id if exists
     * @param jsonStr
     * @return
     */
    public static Form newFormByJson(String jsonStr){
        try {
            Form form = FormContext.getMapper().readValue(jsonStr, Form.class);
            if(form!=null){
                initFormToCache(jsonStr, form);
            }
            return form;
        } catch (IOException e) {
            e.printStackTrace();
            String error = e.getMessage();
            return null;
        }
    }

    private static void initFormToCache(String formString ,Form form){
        FormContext.getInstance().addToPersistance(form.getFormId(), formString);
        //register in global cache
        FormContext.getInstance().addToCache(form);
    }
}
