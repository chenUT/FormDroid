package com.chen.formdroid.core.template;

import android.test.InstrumentationTestCase;

import com.chen.formdroid.form.template.fields.textfield.models.TextField;

/**
 * Created by chen on 4/10/15.
 */
public class InputFieldTest extends InstrumentationTestCase{
    public void testFieldToJsonString(){
        TextField tf = new TextField("0");
    }
}
