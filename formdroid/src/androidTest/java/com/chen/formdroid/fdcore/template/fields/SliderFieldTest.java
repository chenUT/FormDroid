package com.chen.formdroid.fdcore.template.fields;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.fdcore.template.fields.sliderfield.models.SliderField;

import junit.framework.Assert;

import java.io.IOException;

/**
 * Created by chen on 11/26/15.
 */
public class SliderFieldTest {
    public void testFieldToJsonString(){
        SliderField tf = new SliderField("a", "aa");

        tf.setValue(5);
        tf.setMaxValue(10);

        String json = tf.toJsonString();

        try {
            SliderField nTF = FormContext.getMapper().readValue(json, SliderField.class);

            Assert.assertEquals("aa", nTF.getName());
            Assert.assertEquals(5, (int)nTF.getValue());
            Assert.assertEquals("a", nTF.getFieldId());
            Assert.assertEquals(10 , nTF.getMaxValue());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
