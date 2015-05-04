package com.chen.formdroid.core.template.fields;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by chen on 3/29/15.
 */
public abstract class AbsInputFieldViewController<T extends AbsInputField> {
    private final T mField;
    //keep a reference of the current fragment
    private final Fragment mFrag;

    private int mViewId;

    //inject inputField(model) to view controller
    public AbsInputFieldViewController(T field, Fragment frag){
        this.mField = field;
        this.mFrag = frag;
    }

    protected Activity getActivity(){
        return this.mFrag.getActivity();
    }

    protected Context getContext(){
        return this.mFrag.getActivity().getApplicationContext();
    }

    protected Fragment getFragment(){
        return this.mFrag;
    }

    protected LayoutInflater getInflater(){
//        return (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return LayoutInflater.from(getActivity());
    }

    public final View getViewInternal(int viewId){
        View tmpView = getView(mField, mFrag );
        tmpView.setId(viewId);
        return tmpView;
    }

    protected final T getField(){
        return this.mField;
    }

    public int getViewId() {
        return mViewId;
    }

    public void setViewId(int mViewId) {
        this.mViewId = mViewId;
    }

    /**
     * Methods meant to be override
     */
    public void onViewDestroy(){}

    public abstract View getView(AbsInputField mField, Fragment mFrag);
    //call this if the value of the underlining datamodel changed was not through view
    public abstract void validateView();
}
