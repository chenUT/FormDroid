package com.chen.formdroid.fdcore.template.fields;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.fdcore.template.fields.checkboxfield.models.CheckBoxInputField;

import junit.framework.Assert;

import java.io.IOException;

/**
 * Created by chen on 11/26/15.
 */
public class CheckBoxFieldTest{
    public void testFieldToJsonString(){
        CheckBoxInputField tf = new CheckBoxInputField("a", "aa");
        tf.setValue(true);
        String json = tf.toJsonString();
        try {
            CheckBoxInputField nTF = FormContext.getMapper().readValue(json, CheckBoxInputField.class);
            Assert.assertEquals("aa", nTF.getName());
            Assert.assertEquals(true, (boolean)nTF.getValue());
            Assert.assertEquals("a", nTF.getFieldId());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
