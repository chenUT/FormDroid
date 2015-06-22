package com.chen.formdroid.core.template.fields.dialogfield.models;


import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsCompositeField;
import com.chen.formdroid.core.template.fields.AbsDialogField;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.dialogfield.viewcontrollers.DialogFieldViewController;
import com.chen.formdroid.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
@InputField(type = "dialog")
public class DialogField extends AbsDialogField {
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean mutable = true;

    @JsonCreator
    public DialogField(@JsonProperty("fieldId") String fieldId) {
        super(fieldId);
    }

    @Override
    public AbsCompositeField getResultData() {
        List<AbsInputField> fields = getFields();

        for(int i = 0 ;i<fields.size(); i++){

        }
        return null;
    }

    @Override
    public boolean isMutable() {
        return mutable;
    }

    @Override
    public List<DialogResultItem> getValue() {
        return this.value;
    }

    @Override
    public AbsInputField<List<DialogResultItem>> cloneWithNewId(String fieldId) {
        DialogField result = new DialogField(fieldId);
        //clone the fields
        for(int i =0; i< getFields().size(); i++){
            AbsInputField tmp = getFields().get(i);
            AbsInputField cloned = tmp.clone(fieldId+"#"+i);
            result.addField(getFields().get(i));
        }
        return result;
    }

    @Override
    public void setValue(List<DialogResultItem> o) {
       this.value = o;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new DialogFieldViewController(this,frag);
    }
}
