package com.chen.formdroid.fdcore.internal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chen.formdroid.R;
import com.chen.formdroid.fdcore.template.fields.repeatfield.models.RepeatResultItem;
import com.chen.formdroid.utils.FDViewUtils;
import com.chen.formdroid.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that holds dialog view
 * Created by chen on 4/23/15.
 */
public class FormDialogFragment extends DialogFragment{
    private static final String DIALOG_RESULT_ITEM_INDEX="result_item_index";

    static final int DIALOG_RESULT_POSITIVE = 1;
    static final int DIALOG_RESULT_NEGATIVE= 2;

    public static void showDialog(Fragment mFrag, int itemIndex, AbsRepeatFieldViewController dialogCtrl){
        DialogFragment dialogFrag = FormDialogFragment.newInstance(itemIndex, dialogCtrl);
        FragmentTransaction ft = mFrag.getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = mFrag.getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if(prev != null){
           ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFrag.show(ft, "dialog");
        return;
    }

    private static FormDialogFragment newInstance(int itemIndex, AbsRepeatFieldViewController dialogCtrl){
        FormDialogFragment frag = new FormDialogFragment();
        frag.setViewCtrl(dialogCtrl);
        Bundle b = new Bundle();
        b.putInt(DIALOG_RESULT_ITEM_INDEX, itemIndex);
        frag.setArguments(b);
        return frag;
    }

    private AbsRepeatFieldViewController mViewCtrl;

    private List<AbsInputFieldViewController> mDialogFieldCtrls;

    private List<Integer> mViewIdList;

    private int mResultItemIndex;

    private Dialog mDialog;

    /**
     * pass the parent dialog controller to this view
     * @param viewCtrl
     */
    private void setViewCtrl(AbsRepeatFieldViewController viewCtrl) {
        this.mViewCtrl = viewCtrl;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            //retrive form and field information
            String formId = args.getString(FormCoreFragment.FORM_ID_BUNDLE_KEY);
            mResultItemIndex = args.getInt(DIALOG_RESULT_ITEM_INDEX);
            //TODO do we move this step to background asyncTask
            mDialogFieldCtrls = mViewCtrl.getDialogViewControllers(FormDialogFragment.this, mResultItemIndex);
            mViewIdList = new ArrayList<>();
        }//end of if(args != null)
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = getDialogView(getActivity().getLayoutInflater());
        AlertDialog.Builder builder = getBuilder(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogOnShowListener(mViewCtrl.hasNeutralButton()));
        mDialog = dialog;
        return mDialog;
    }

    private View getDialogView(LayoutInflater inflater){
        ViewGroup dialogRootLayout = (ViewGroup)inflater.inflate(R.layout.inputfield_dialog_dialog_wrapper, null, false);
        for(AbsInputFieldViewController viewController : mDialogFieldCtrls){
            int tmpViewId = FDViewUtils.generateViewId();
            while(mViewIdList.contains(tmpViewId)){
                tmpViewId = FDViewUtils.generateViewId();
            }
            View tmpView = viewController.getViewInternal(tmpViewId);
            dialogRootLayout.addView(tmpView);
        }
        return dialogRootLayout;
    }

    /**
     * internal method to get a alertdialog builder
     * @return
     */
    private AlertDialog.Builder getBuilder(View dialogView){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setPositiveButton(R.string.dialog_positive_button, null)
                .setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    if (which == Dialog.BUTTON_NEGATIVE) {
                        if (mViewCtrl != null) {
                            mViewCtrl.onDialogResultInternal(null, DIALOG_RESULT_NEGATIVE);
                        }
                        dialog.dismiss();
                    }
                    }});

        if (StringUtils.isEmptyOrWhiteSpace(mViewCtrl.getDialogTitle())){
            builder.setTitle(mViewCtrl.getDialogTitle());
        }
        if(mViewCtrl.hasNeutralButton()){
            builder.setNeutralButton(R.string.dialog_neutral_button, null);
        }
        return builder;
    }

    private RepeatResultItem getCurrentResultItem(){
        List<AbsInputField> oldFields = mViewCtrl.getFields();
        List<AbsInputField> clonedFields =  new ArrayList<>();
        for(AbsInputField field : oldFields){
            //clone with the same id
            clonedFields.add(field.clone(field.getFieldId()));
            //clearToDefault the original field
            field.clearToDefault();
        }
        RepeatResultItem resutlItem = new RepeatResultItem();
        resutlItem.setIndex(mResultItemIndex);
        resutlItem.setResultFields(clonedFields);
        return  resutlItem;
    }

    //inner class to setup on click listener for positive and clearToDefault button
    private class DialogOnShowListener implements DialogInterface.OnShowListener{

        private final boolean mHasNeutral;
        public DialogOnShowListener(boolean hasNeutralButton){
            this.mHasNeutral = hasNeutralButton;
        }

        @Override
        public void onShow(DialogInterface dialog) {
            Button posButton = ((AlertDialog)getDialog()).getButton(Dialog.BUTTON_POSITIVE);
            posButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RepeatResultItem result = null;
                    if (mResultItemIndex == RepeatResultItem.ITEM_INDEX_NEW) {
                        result = getCurrentResultItem();
                    }
                    mViewCtrl.onDialogResultInternal(result, DIALOG_RESULT_POSITIVE);
                    mDialog.dismiss();
                }});

            if(mHasNeutral){
                Button neutralButton = ((AlertDialog)getDialog()).getButton(Dialog.BUTTON_NEUTRAL);
                neutralButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(AbsInputFieldViewController viewController : mDialogFieldCtrls){
                            viewController.clear();
                        }
                    }});
            }
        }
    }
}
