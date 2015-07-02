package com.chen.formdroid.core.template.fields.checkboxfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.internal.AbsInputField;
import com.chen.formdroid.core.internal.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.checkboxfield.viewcontrollers.CheckBoxViewController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Chen.Liu on 7/2/2015.
 */
@InputField(type = "checkbox")
public class CheckBoxInputField extends AbsInputField<Boolean>{

    public CheckBoxInputField() {
    }

    @JsonCreator
    public CheckBoxInputField(@JsonProperty("fieldId") String fieldId) {
        super(fieldId);
    }

    @Override
    public Boolean getValue() {
        if(this.value == null)
            return false;

        return this.value;
    }

    @Override
    public void setValue(Boolean o) {
        this.value = o;
    }

    @Override
    public boolean clear() {
        this.value = false;
        return true;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new CheckBoxViewController(this, frag);
    }

    @Override
    protected AbsInputField<Boolean> cloneWithNewId(String fieldId) {
        CheckBoxInputField checkbox = new CheckBoxInputField(fieldId);
        return checkbox;
    }
}
