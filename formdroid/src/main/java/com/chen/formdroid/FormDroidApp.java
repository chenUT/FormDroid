package com.chen.formdroid;

import android.app.Application;

import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.InputFieldFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chen on 3/28/15.
 */
public class FormDroidApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //this will guarantee that root scope is alive and have a context reference
        initFields();

        //this is the last step of initialization
        FormContext.getInstance().init(getApplicationContext());
    }

    private final void initFields(){
        //initialize all fields
        InputFieldFactory.init();
        //register all external fields
        List<Class<? extends AbsInputField>> fields = new LinkedList<>();
        if(registerAllFields(fields)){
            InputFieldFactory.registerFields(fields);
        }
        //register view controller for existing input fields if necessary
        List<Class<? extends AbsInputFieldViewController>> vCtrls = new LinkedList<>();
        if(registerReplaceViewControllers(vCtrls)){
            InputFieldFactory.registerReplaceViewControllers(vCtrls);
        }
    }

    /**
     * Override to register addition fields
     * @param fields
     * @return
     */
    protected boolean registerAllFields(final List<Class<? extends AbsInputField>> fields){
        return false;
    }

    /**
     * Override to register viewcontroller which extends existing controller with existing inputfield
     * @param vCtrls
     * @return
     */
    protected boolean registerReplaceViewControllers(final List<Class<? extends AbsInputFieldViewController>> vCtrls){
        return false;
    }

}
