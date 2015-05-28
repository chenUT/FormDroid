package com.chen.formdroid.core.template.fields.dialogfield.models;


import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsCompositeField;
import com.chen.formdroid.core.template.fields.AbsDialogField;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.dialogfield.viewcontrollers.DialogFieldViewController;
import com.chen.formdroid.utils.StringUtils;
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

    public DialogField(String fieldId, DialogField field) {
        super(fieldId, field);
        //replace the value with deep cloned values
        List<DialogResultItem> newResult = new ArrayList<>();
        for(int i = 0; i < field.getValue().size(); i++){
            DialogResultItem tmp = getValue().get(i);
            //should result has an id?
//            newResult.add((DialogResultItem)tmp.cloneWithNewId(fieldId+"#"+i));
            newResult.add((DialogResultItem)tmp.clone());
        }
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
        DialogField field = new DialogField(fieldId, this);
        return field;
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
