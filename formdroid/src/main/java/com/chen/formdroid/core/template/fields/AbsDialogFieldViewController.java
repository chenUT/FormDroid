package com.chen.formdroid.core.template.fields;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by chen on 4/23/15.
 */
public abstract class AbsDialogFieldViewController<T extends AbsDialogField> extends AbsInputFieldViewController<T>{
    public AbsDialogFieldViewController(T field, Fragment frag) {
        super(field, frag);
    }

    public abstract View getResultView(T field, int resultIndex);
    public abstract void onDialogResult(Intent data, int requestCode, int resultCode);
}
