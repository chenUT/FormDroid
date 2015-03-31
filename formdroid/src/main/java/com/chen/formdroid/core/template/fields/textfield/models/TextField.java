package com.chen.formdroid.core.template.fields.textfield.models;

import android.app.Fragment;
import android.util.Log;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;

/**
 * Created by chen on 3/29/15.
 */
@InputField(JsonKey = "text")
public class TextField extends AbsInputField<CharSequence>{

    static{
        Log.d("formDroid", "TextField");
    }

    public TextField(String fieldId) {
        super(fieldId);
    }

    @Override
    protected CharSequence getValue() {
        return this.value;
    }

    @Override
    protected void setValue(CharSequence o) {
        if (o == null) {
            this.value = o;
        } else {
            this.value = String.valueOf(o);
        }
    }

    @Override
    protected AbsInputFieldViewController getViewController(Fragment frag) {
        return null;
    }
}
