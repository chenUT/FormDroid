package com.chen.formdroid.core.template.fields;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chen.formdroid.core.template.fields.textfield.models.DialogField;

/**
 * Created by chen on 4/23/15.
 */
public abstract class AbsDialogFieldViewController extends AbsInputFieldViewController<AbsDialogField>{
    public AbsDialogFieldViewController(AbsDialogField field, Fragment frag) {
        super(field, frag);
    }

    public abstract View getResultView(DialogField field, int resultIndex);
    public abstract void onDialogResult(Intent data, int requestCode, int resultCode);
}
