package com.chen.formdroid.fdcore.template.fields.textfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.fdcore.annotations.InputField;
import com.chen.formdroid.fdcore.internal.AbsInputField;
import com.chen.formdroid.fdcore.internal.AbsInputFieldViewController;
import com.chen.formdroid.fdcore.template.fields.textfield.viewcontrollers.TextViewController;
import com.chen.formdroid.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chen on 3/29/15.
 */
@InputField(type = "text")
public class TextField extends LabelField {

    @JsonProperty
    private String hint;

    public TextField(){
        super();
    }

    @JsonCreator
    public TextField(@JsonProperty("fieldId")String fieldId, @JsonProperty("name")String name) {
        super(fieldId, name);
    }

    @Override
    public AbsInputField<CharSequence> cloneWithNewId(String fieldId) {
        TextField field = new TextField(fieldId, getName());
        field.setHint(this.hint);
        return field;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new TextViewController(this, frag);
    }

    @Override
    public boolean isEmpty(){
        return StringUtils.isEmpty(value);
    }

    public boolean hasHint(){
        return !StringUtils.isEmptyOrWhiteSpace(hint);
    }

    public String getHint(){
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}

