package com.chen.formdroid.core.template.fields.textfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;

/**
 * Created by chen on 3/27/15.
 */
@InputField(Type ="label")
public class LabelField extends TextField{

    public LabelField(String fieldId) {
        super(fieldId);
    }

    @Override
    protected AbsInputFieldViewController getViewController(Fragment frag) {
        return null;
    }
}
