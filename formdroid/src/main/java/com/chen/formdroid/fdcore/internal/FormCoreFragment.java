package com.chen.formdroid.fdcore.internal;

import com.chen.formdroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.fdcore.template.form.Form;
import com.chen.formdroid.utils.FDViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormCoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormCoreFragment extends Fragment {

    static final String FORM_ID_BUNDLE_KEY = "formIdBundleKey";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PageRootFragment.
     */
    public static FormCoreFragment newInstance(String formId) {
        FormCoreFragment fragment = new FormCoreFragment();
        Bundle args = new Bundle();
        args.putString(FORM_ID_BUNDLE_KEY, formId);
        fragment.setArguments(args);
        return fragment;
    }

    // the fragment initialization parameters
    private String mFormId;
    //keep a copy of the form object
    private Form mForm;
    //list of current view controllers
    private List<AbsInputFieldViewController> mViewControllerList;

    private View mRootLayoutWrapper = null;
    private LinearLayout mRootLayout = null;

    public FormCoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mFormId = getArguments().getString(FORM_ID_BUNDLE_KEY);
            //get form object from global form cache
            this.mForm = FormContext.getInstance().getForm(this.mFormId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mViewControllerList == null) {
            mViewControllerList = new ArrayList<>(mForm.getFields().size());
        }
        else{
            mViewControllerList.clear();
        }

        mRootLayoutWrapper = inflater.inflate(R.layout.page_base_scroll_fragment, container, false);
        mRootLayout = (LinearLayout)mRootLayoutWrapper.findViewById(R.id.page_linear_trable);
        //start the task to fill out content view
        new CreateViewTask().execute();
        return mRootLayoutWrapper;
    }

    private View getFormViewWrapper(){
        return this.mRootLayoutWrapper;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for(AbsInputFieldViewController viewCtrl : mViewControllerList) {
            viewCtrl.onViewDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //release the reference when fragment is destroyed
        this.mForm = null;
        this.mViewControllerList.clear();
        this.mViewControllerList = null;
    }

    public String getFormId(){
        return this.mFormId;
    }

    /**
     *this task is used to create view from each viewcontroller in background
     */
    private class CreateViewTask extends AsyncTask<Void, Void, Void>{
        private List<Integer> mViewIdList;

        /**
         *  for safety reason we inflate all the views in UI thread,
         *  this is done after all the viewcontrollers have been created
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            mViewIdList = new ArrayList<>();
            //wait until its inflated
            while(mRootLayout == null);

            for(AbsInputFieldViewController viewCtrl : mViewControllerList) {
                int tmpViewId = FDViewUtils.generateViewId();
                while(mViewIdList.contains(tmpViewId)){
                    tmpViewId = FDViewUtils.generateViewId();
                }
                mViewIdList.add(tmpViewId);
                View tmpFieldView = viewCtrl.getViewInternal(tmpViewId);
                mRootLayout.addView(tmpFieldView);
            }
            //invalidate the root layout after adding fields
            mRootLayout.invalidate();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //first fill the viewControllerList
            //create viewcontroller in background thread as it may take a bit time
            for(AbsInputField field : mForm.getFields()){
                AbsInputFieldViewController ctrl = field.getViewControllerInternal(FormCoreFragment.this);
                if(ctrl != null) {
                    mViewControllerList.add(ctrl);
                    //register controller to otto bus
                }
            }
            return null;
        }
    }
}
