package com.chen.formdroid.fdcore.template;

import android.test.InstrumentationTestCase;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.fdcore.internal.InputFieldFactory;
import com.chen.formdroid.fdcore.template.fields.textfield.models.TextField;
import com.fasterxml.jackson.core.JsonParseException;

import junit.framework.Assert;

import org.w3c.dom.Text;

import java.io.IOException;

/**
 * Created by chen on 4/10/15.
 */
public class InputFieldTest extends InstrumentationTestCase{
    public void testFieldToJsonString(){
        TextField tf = new TextField("a", "aa");
        tf.setValue("test");

        String json = tf.toJsonString();

        try {
            TextField nTF = FormContext.getMapper().readValue(json, TextField.class);

            Assert.assertEquals("aa", nTF.getName());
            Assert.assertEquals("test", nTF.getValue());
            Assert.assertEquals("a", nTF.getFieldId());

        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
