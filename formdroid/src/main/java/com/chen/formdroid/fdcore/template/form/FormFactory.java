package com.chen.formdroid.fdcore.template.form;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.FormDroidApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by chen on 4/10/15.
 */
public class FormFactory {
    private static FormFactory _instance;

    private IFormParser mParser;

    private FormFactory(){
    }

    public static synchronized FormFactory getInstance(){
        if(_instance == null){
            _instance = new FormFactory();
        }
       return _instance;
    }

    /**
     * this will only read form from cache or sharedPreference
     * @param formId
     * @return
     */
    public Form getFormById(String formId){
        Form result = FormContext.getInstance().getForm(formId);
        return result;
    }

    /**
     *   this will create and initialize a form using the parser set by @link{FormFactory#setParser()}
     */
    public Form newForm(){
        if(null == this.mParser){
            throw new RuntimeException("no custom parser is specified");
        }
        Form form = this.mParser.getForm();
        if(form != null){
            initFormToCache(form);
        }
        return form;
    }

    public Form loadFromAssets(String assetsName){
        try {
            InputStream in = null;
            try {
                in = FormDroidApp._appContext.getAssets().open(assetsName);
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                in.close();
                String jsonStr = new String(buffer, "UTF-8");
                return loadFormByJson(jsonStr);
            }
            finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * this method will load the exsiting form if the system storage has it otherwise it create a new one
     * TODO a better way to extract formId
     * @param jsonStr
     * @return
     */
    public Form loadFormByJson(String jsonStr){
         try {
            Form form = FormContext.getMapper().readValue(jsonStr, Form.class);
            if(form == null){
                return null;
            }
            String formId = form.getFormId();

            //this will search both cache and sharedPreference persistence to retrieve existing value
            Form oldForm = getFormById(formId);
            if(oldForm != null){
                FormContext.getInstance().addToCache(oldForm);
                return form;
            }
             //register in global cache and persistence for new created form
            initFormToCache(form);
            return form;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO change to public when CvsFormParser is implemented
    public Form newFormByCvs(List<String[]> cvsContent){
        mParser = new CvsFormParser(cvsContent);
        Form form = mParser.getForm();
        if(form!=null){
            initFormToCache(form);
        }
        return form;
    }

    /**
     * this will create a new form and delete the old one with the same form id if exists
     * @param jsonStr
     * @return
     */
    public Form newFormByJson(String jsonStr){
        mParser = new JsonFormParser(jsonStr);
        Form form = mParser.getForm();
        if(form!=null){
            initFormToCache(form);
        }
        return form;
    }

    public void setParser(IFormParser customParser){
        this.mParser = customParser;
    }

    /**
     * add form to cache, including both memory cache and sharedPreference persistence cache
     * @param form form object
     */
    private void initFormToCache(Form form){
        FormContext.getInstance().addToPersistence(form.getFormId(), form.toJsonString());
        //register in global cache
        FormContext.getInstance().addToCache(form);
    }
}
