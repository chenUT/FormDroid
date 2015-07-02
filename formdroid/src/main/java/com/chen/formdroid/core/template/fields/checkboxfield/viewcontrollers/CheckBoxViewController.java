package com.chen.formdroid.core.template.fields.checkboxfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;

import com.chen.formdroid.R;
import com.chen.formdroid.core.internal.AbsInputField;
import com.chen.formdroid.core.internal.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.checkboxfield.models.CheckBoxInputField;

/**
 * Created by Chen.Liu on 7/2/2015.
 */
public class CheckBoxViewController extends AbsInputFieldViewController<CheckBoxInputField>{

    private CheckBox mCheckBox;

    public CheckBoxViewController(CheckBoxInputField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    protected View getView(CheckBoxInputField field, Fragment frag) {
        View view  = getInflater().inflate(R.layout.inputfield_checkbox, null, false);
        mCheckBox = (CheckBox)view.findViewById(R.id.checkbox);
        mCheckBox.setText(field.getName());
        mCheckBox.setOnClickListener(v -> {
            getField().setValue(((CheckBox)v).isChecked());
            ((CheckBox) v).setText(field.getName());
        });
        initViewValue(field);
        return view;
    }

    @Override
    protected void initViewValue(CheckBoxInputField field) {
       mCheckBox.setChecked(field.getValue());
    }

}
