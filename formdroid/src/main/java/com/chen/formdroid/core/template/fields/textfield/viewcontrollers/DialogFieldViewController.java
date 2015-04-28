package com.chen.formdroid.core.template.fields.textfield.viewcontrollers;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chen.formdroid.core.template.fields.AbsDialogField;
import com.chen.formdroid.core.template.fields.AbsDialogFieldViewController;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.textfield.models.DialogField;

/**
 * Created by chen on 4/23/15.
 */
public class DialogFieldViewController extends AbsDialogFieldViewController {
    public DialogFieldViewController(AbsDialogField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    public View getResultView(DialogField field, int resultIndex) {
        return null;
    }

    @Override
    public void onDialogResult(Intent data, int requestCode, int resultCode) {

    }

    @Override
    public View getView(AbsInputField mField, Fragment mFrag) {
        return null;
    }

    @Override
    public void validateView() {

    }
}
