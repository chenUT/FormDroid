package com.chen.formdroid.form.internal;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.formdroid.R;
import com.chen.formdroid.utils.StringUtils;

/**
 * Created by chen on 3/29/15.
 */
public abstract class AbsInputFieldViewController<T extends AbsInputField> {

    /**
     * keep a reference to the injected field
     */
    private final T mField;
    /**
     *  keep a reference of the current fragment
     */
    private final Fragment mFrag;

    private int mViewId;

    private View innerViewRef;

    //inject inputField(model) to view controller
    public AbsInputFieldViewController(T field, Fragment frag){
        this.mField = field;
        this.mFrag = frag;
    }

    protected Activity getActivity(){
        return this.mFrag.getActivity();
    }

    protected Fragment getFragment(){
        return this.mFrag;
    }

    protected LayoutInflater getInflater(){
//        return (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        return LayoutInflater.from(getActivity());
        return getActivity().getLayoutInflater();
    }

    /**
     * This is called by {@link FormCoreFragment}
     * and {@link FormDialogFragment}
     * to inflate the views in target fragment
     * @param viewId
     * @return
     */
    final View getViewInternal(int viewId){
        setViewId(viewId);
        View tmpView = getView(mField, mFrag);
        tmpView.setId(viewId);
        this.innerViewRef = tmpView;
        return this.innerViewRef;
    }

    protected final T getField(){
        return this.mField;
    }

    public int getViewId() {
        return mViewId;
    }

    private void setViewId(int mViewId) {
        this.mViewId = mViewId;
    }

    /**
     * By default validate view through logic will reinitialize the value in view
     *
     * However this may be different from {@link AbsInputFieldViewController#initViewValue(AbsInputField)} that this method will also reset the ui components
     */
    protected void invalidateView(T field){
        initViewValue(field);
    }

    /**
     * This is called when view is destroyed
     */
    public void onViewDestroy(){}

    /**
     * This is not a editable view and for show up purpose only
     * Commonly used by dialogInput to display current dialog result items
     * By default, this returns a textview in the format of {@link AbsInputField}.getName(): {@link AbsInputField}.getValue().toString()
     * @return
     */
    public final View getDisplayView(){
        View root = getDisplayView(getField(), getFragment());
        if(root != null){
            return root;
        }
        root = getInflater().inflate(R.layout.inputfield_displayview_default, (ViewGroup) mFrag.getView(), false);
        TextView text = (TextView)root.findViewById(R.id.inputfield_displayview_text);
        if(StringUtils.isEmptyOrWhiteSpace(mField.getName())){
            text.setText(mField.getValue().toString());
        }
        else{
            text.setText(mField.getName()+": "+mField.getValue().toString());
        }
        return root;
    }

    public void clear(){
        this.mField.clear();
        invalidateView(this.mField);
    }

    /**
     * subclass can override this for customized behavior
     * @param field
     * @param frag
     * @return
     */
    protected View getDisplayView(final T field, Fragment frag){
        return null;
    }

    protected abstract View getView(final T field, Fragment frag);
    /**
     * Override this method to initialize the value in views
     */
    protected abstract void initViewValue(final T field);
}
