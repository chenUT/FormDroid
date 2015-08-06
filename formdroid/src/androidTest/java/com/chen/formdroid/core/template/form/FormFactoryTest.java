package com.chen.formdroid.core.template.form;

import android.test.InstrumentationTestCase;

import com.chen.formdroid.form.template.fields.textfield.models.LabelField;
import com.chen.formdroid.form.template.fields.textfield.models.TextField;
import com.chen.formdroid.form.template.form.Form;
import com.chen.formdroid.form.template.form.FormFactory;

import junit.framework.Assert;

/**
 * Created by chen on 4/14/15.
 */
public class FormFactoryTest extends InstrumentationTestCase{
    public void testNewFormByJson(){
        String jsonStr = "{\"formId\":\"a\"}";
        Form f = FormFactory.newFormByJson(jsonStr);
        Assert.assertEquals("a", f.getFormId());
        Assert.assertFalse(f.isRequired());

        jsonStr = "{\"formId\":\"a\", \"required\":true}";
        f = FormFactory.newFormByJson(jsonStr);
        Assert.assertTrue(f.isRequired());

        jsonStr = "{\"required\":true}";
        f = FormFactory.newFormByJson(jsonStr);
        Assert.assertNotNull(f);

        jsonStr = "{\"formId\":\"a\", \"required\":true, \"fields\":[ {\"@type\":\"text\",\"fieldId\":\"fa\"},{\"@type\":\"label\",\"fieldId\":\"fb\",\"name\":\"labelb\"},{\"@type\":\"text\"}]}";

        Form ff = new Form();
        ff.addField(new TextField("fa"));
        ff.addField(new LabelField("fb"));
        jsonStr = ff.toJsonString();
        f = FormFactory.newFormByJson(jsonStr);
        Assert.assertNotNull(f.getFields());
        Assert.assertTrue(f.getFields().size() == 2);
    }
}
