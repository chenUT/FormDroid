package com.chen.formdroid.core.template.fields.dialogfield.models;


import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsDialogField;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.dialogfield.viewcontrollers.DialogFieldViewController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
@InputField(type = "dialog")
public class DialogField extends AbsDialogField {
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean mutable = true;

    @Override
    public boolean isMutable() {
        return mutable;
    }

    @Override
    public List<AbsInputField> getValue() {
        return this.value;
    }

    @Override
    public void setValue(List<AbsInputField> value) {
        this.value = value;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new DialogFieldViewController(this,frag);
    }
}
