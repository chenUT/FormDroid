package com.chen.formdroid.form.template.fields.selectfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.view.View;

import com.chen.formdroid.form.internal.AbsInputFieldViewController;
import com.chen.formdroid.form.template.fields.selectfield.models.SelectOneField;

/**
 * Created by chen.liu on 8/5/2015.
 */
public class SelectOneViewController extends AbsInputFieldViewController<SelectOneField>{

    public SelectOneViewController(SelectOneField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    protected View getView(SelectOneField field, Fragment frag) {
        return null;
    }

    @Override
    protected void initViewValue(SelectOneField field) {

    }
}
