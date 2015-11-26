package com.chen.formdroid.fdcore.template.form;

import android.test.InstrumentationTestCase;

import com.chen.formdroid.fdcore.internal.AbsInputField;
import com.chen.formdroid.fdcore.template.fields.textfield.models.LabelField;
import com.chen.formdroid.fdcore.template.fields.textfield.models.TextField;

import junit.framework.Assert;

/**
 * Created by chen on 4/14/15.
 */
public class FormFactoryTest extends InstrumentationTestCase{
    public void testNewFormByJson(){

        String jsonStr = "{\"formId\":\"a\"}";
        Form f = FormFactory.getInstance().newFormByJson(jsonStr);

        Assert.assertEquals("a", f.getFormId());
        Assert.assertFalse(f.isRequired());

        jsonStr = "{\"formId\":\"a\", \"required\":true}";
        f = FormFactory.getInstance().newFormByJson(jsonStr);
        Assert.assertTrue(f.isRequired());

        jsonStr = "{\"required\":true}";
        f = FormFactory.getInstance().newFormByJson(jsonStr);
        Assert.assertNotNull(f);

//        String jsonStrOriginal = "{\"formId\":\"a\", \"required\":true, \"fields\":[ {\"@type\":\"text\",\"fieldId\":\"fa\"},{\"@type\":\"label\",\"fieldId\":\"fb\",\"name\":\"labelb\"},{\"@type\":\"text\"}]}";
        Form dumyForm = new Form();
        dumyForm.addField(new TextField("a","fa"));
        dumyForm.addField(new LabelField("fb","labelb"));
        jsonStr = dumyForm.toJsonString();
        //json Str should be reusable
        f = FormFactory.getInstance().newFormByJson(jsonStr);

        Assert.assertNotNull(f.getFields());
        Assert.assertTrue(f.getFields().size() == 2);
        for(AbsInputField in : f.getFields()){
            if(in.getFieldId().equals("a")){
                Assert.assertTrue( in instanceof TextField);
                Assert.assertEquals("fa", in.getName().toString());
            }
            if(in.getFieldId().equals("a")){
                Assert.assertTrue( in instanceof TextField);
                Assert.assertEquals("fa", in.getName().toString());
            }
        }
    }
}
