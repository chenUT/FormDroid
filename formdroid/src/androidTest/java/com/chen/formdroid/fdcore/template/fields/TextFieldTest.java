package com.chen.formdroid.fdcore.template.fields;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.fdcore.template.fields.textfield.models.TextField;

import junit.framework.Assert;

import java.io.IOException;

/**
 * Created by chen on 11/26/15.
 */
public class TextFieldTest {
    public void testFieldToJsonString(){
        TextField tf = new TextField("a", "aa");

        tf.setValue("test");
        tf.setHint("hint");

        String json = tf.toJsonString();

        try {
            TextField nTF = FormContext.getMapper().readValue(json, TextField.class);

            Assert.assertEquals("aa", nTF.getName());
            Assert.assertEquals("test", nTF.getValue());
            Assert.assertEquals("a", nTF.getFieldId());
            Assert.assertEquals("hint", nTF.getHint());

        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
