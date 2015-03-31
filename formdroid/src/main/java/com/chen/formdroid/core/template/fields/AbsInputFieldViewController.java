package com.chen.formdroid.core.template.fields;


import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by chen on 3/29/15.
 */
public abstract class AbsInputFieldViewController<T extends AbsInputField> {
    private final T mField;
    //keep a reference of the current fragment
    private final Fragment mFrag;

    public AbsInputFieldViewController(T field, Fragment frag){
        this.mField = field;
        this.mFrag = frag;
    }

    public final View getViewInternal(){
        return getView(mField, mFrag );
    }

    protected abstract View getView(T mField, Fragment mFrag);
}
