package com.chen.formdroid.core.template.fields;


import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by chen on 3/29/15.
 */
public abstract class AbsInputFieldViewController {
    private final AbsInputField mField;
    //keep a reference of the current fragment
    private final Fragment mFrag;

    //inject inputField(model) to view controller
    public AbsInputFieldViewController(AbsInputField field, Fragment frag){
        this.mField = field;
        this.mFrag = frag;
    }

    public final View getViewInternal(){
        return getView(mField, mFrag );
    }

    protected abstract View getView(AbsInputField mField, Fragment mFrag);
}
