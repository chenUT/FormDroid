package com.chen.formdroid.core.template.fields.textfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.textfield.viewcontrollers.LabelViewController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chen on 3/27/15.
 */
@InputField(type ="label")
public class LabelField extends AbsInputField<CharSequence> {

    @JsonCreator
    public LabelField(@JsonProperty("fieldId")String fieldId) {
        super(fieldId);
    }

    @Override
    public void setValue(CharSequence o) {
        if (o == null) {
            this.value = o;
        } else {
            this.value = String.valueOf(o);
        }
    }
    @Override
    public boolean clear() {
        this.value = "";
        return false;
    }

    @Override
    public CharSequence getValue() {
        return this.value;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new LabelViewController(this, frag);
    }

    @Override
    protected AbsInputField<CharSequence> cloneWithNewId(String fieldId) {
        LabelField label = new LabelField(fieldId);
        label.setValue(this.getValue());
        return label;
    }
}
