package com.chen.formdroid.fdcore.template.fields.textfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.fdcore.annotations.InputField;
import com.chen.formdroid.fdcore.internal.AbsInputField;
import com.chen.formdroid.fdcore.internal.AbsInputFieldViewController;
import com.chen.formdroid.fdcore.template.fields.textfield.viewcontrollers.LabelViewController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chen on 3/27/15.
 */
@InputField(type ="label")
public class LabelField extends AbsInputField<CharSequence> {

    @JsonCreator
    public LabelField(@JsonProperty("fieldId")String fieldId,
                      @JsonProperty("name")String name) {
        super(fieldId, name);
    }

    public LabelField() {
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
    public boolean clearToDefault() {
        this.value = "";
        return true;
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
        LabelField label = new LabelField(fieldId, getName());
        label.setValue(this.getValue());
        return label;
    }
}
