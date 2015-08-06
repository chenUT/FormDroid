package com.chen.formdroid;

import android.app.Application;
import android.content.Context;

import com.chen.formdroid.form.internal.AbsInputField;
import com.chen.formdroid.form.internal.AbsInputFieldViewController;
import com.chen.formdroid.form.internal.InputFieldFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chen on 3/28/15.
 */
public class FormDroidApp extends Application{

    public static Context _appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //init app context
        _appContext = getApplicationContext();

        //this will guarantee that root scope is alive, all static fileds get initialized and have a context reference
        initFields();

        //this is the last step of initialization
        FormContext.getInstance().init(_appContext);


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
        List<Class<? extends AbsInputFieldViewController>> viewCtrls = new LinkedList<>();
        if(registerReplaceViewControllers(viewCtrls)){
            InputFieldFactory.registerReplaceViewControllers(viewCtrls);
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
     * this is used if user want to replace a view from a existing inputfield
     * @param vCtrls
     * @return
     */
    protected boolean registerReplaceViewControllers(final List<Class<? extends AbsInputFieldViewController>> vCtrls){
        return false;
    }

}
