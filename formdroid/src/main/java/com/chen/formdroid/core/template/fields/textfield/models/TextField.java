package com.chen.formdroid.core.template.fields.textfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.textfield.viewcontrollers.TextViewController;
import com.chen.formdroid.utils.StringUtils;

/**
 * Created by chen on 3/29/15.
 */
@InputField(type = "text")
public class TextField extends AbsInputField<CharSequence> {

    public TextField() {
    }

    public TextField(String fieldId) {
        super(fieldId);
    }

    @Override
    public CharSequence getValue() {
        return this.value;
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
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new TextViewController(this, frag);
    }

    @Override
    public boolean isEmpty(){
        return StringUtils.isEmpty(value);
    }
}

