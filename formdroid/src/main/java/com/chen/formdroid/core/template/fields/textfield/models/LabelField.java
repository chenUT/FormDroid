package com.chen.formdroid.core.template.fields.textfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.textfield.viewcontrollers.LabelViewController;

/**
 * Created by chen on 3/27/15.
 */
@InputField(type ="label")
public class LabelField extends TextField{

    public LabelField() {
    }

    public LabelField(String fieldId) {
        super(fieldId);
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new LabelViewController(this, frag);
    }
}
