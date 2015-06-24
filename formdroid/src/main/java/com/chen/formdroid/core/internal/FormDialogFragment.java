package com.chen.formdroid.core.internal;

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

import com.chen.formdroid.FormContext;
import com.chen.formdroid.R;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogField;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogResultItem;
import com.chen.formdroid.core.template.form.Form;
import com.chen.formdroid.exceptions.InputFieldTypeMismatchException;
import com.chen.formdroid.utils.FDViewUtils;
import com.chen.formdroid.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
public class FormDialogFragment extends DialogFragment{
    private static final String DIALOG_FIELD_ID_KEY ="field_id_key";
    private static final String DIALOG_RESULT_ITEM_INDEX="result_item_index";

    static final int DIALOG_RESULT_POSITIVE = 1;
    static final int DIALOG_RESULT_NEGATIVE= 2;

    public static void showDialog(Fragment mFrag, String formId, String fieldId, int itemIndex){
        DialogFragment dialogFrag = FormDialogFragment.newInstance(formId, fieldId, itemIndex);
        FragmentTransaction ft = mFrag.getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = mFrag.getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if(prev != null){
           ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFrag.show(ft, "dialog");
        return;
    }

    private static FormDialogFragment newInstance(String formId, String fieldId, int itemIndex){
        FormDialogFragment frag = new FormDialogFragment();
        Bundle b = new Bundle();
        b.putString(FormCoreFragment.FORM_ID_BUNDLE_KEY, formId);
        b.putString(DIALOG_FIELD_ID_KEY, fieldId);
        b.putInt(DIALOG_RESULT_ITEM_INDEX, itemIndex);
        frag.setArguments(b);
        return frag;
    }

    private AbsDialogFieldViewController mViewCtrl;

    /**
     * reference of the entire form
     */
    private Form mForm;

    private List<AbsInputFieldViewController> mDialogFieldCtrls;

    private List<Integer> mViewIdList;

    private int mResultItemIndex;

    private Dialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            //retrive form and field information
            String formId = args.getString(FormCoreFragment.FORM_ID_BUNDLE_KEY);
            String fieldId = args.getString(DIALOG_FIELD_ID_KEY);
            mResultItemIndex = args.getInt(DIALOG_RESULT_ITEM_INDEX);
            mForm = FormContext.getInstance().getForm(formId);
            for(AbsInputField field : mForm.getFields()){
                if(fieldId.equals(field.getFieldId())){
                    if(field instanceof DialogField){
                        mViewCtrl = (AbsDialogFieldViewController)field.getViewControllerInternal(this);
                        break;
                    }
                    //this should not happen
                    else{
                        throw new InputFieldTypeMismatchException(field.getName()+" is not a DialogField");
                    }
                }
            }//end of for(AbsInputField field : mForm.getFields())
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
                .setNegativeButton(R.string.dialog_cancel_button, (dialog, which) -> {
                    if (which == Dialog.BUTTON_NEGATIVE) {
                        if (mViewCtrl != null) {
                            mViewCtrl.onDialogResultInternal(null, DIALOG_RESULT_NEGATIVE);
                        }
                        dialog.dismiss();
                    }
                });

        if (StringUtils.isEmptyOrWhiteSpace(mViewCtrl.getDialogTitle())){
            builder.setTitle(mViewCtrl.getDialogTitle());
        }
        if(mViewCtrl.hasNeutralButton()){
            builder.setNeutralButton(R.string.dialog_neutral_button, null);
        }
        return builder;
    }

    private DialogResultItem getCurrentResultItem(){
        List<AbsInputField> oldFields = mViewCtrl.getFields();
        List<AbsInputField> clonedFields =  new ArrayList<>();
        for(AbsInputField field : oldFields){
            //clone with the same id
            clonedFields.add(field.clone(field.getFieldId()));
            //clear the original field
            field.clear();
        }
        DialogResultItem resutlItem = new DialogResultItem();
        resutlItem.setIndex(mResultItemIndex);
        resutlItem.setResultFields(clonedFields);
        return  resutlItem;
    }

    //inner class to setup on click listener for positive and clear button
    private class DialogOnShowListener implements DialogInterface.OnShowListener{

        private final boolean mHasNeutral;
        public DialogOnShowListener(boolean hasNeutralButton){
            this.mHasNeutral = hasNeutralButton;
        }

        @Override
        public void onShow(DialogInterface dialog) {
            Button posButton = ((AlertDialog)getDialog()).getButton(Dialog.BUTTON_POSITIVE);
            posButton.setOnClickListener((view) -> {
                DialogResultItem result = null;
                if(mResultItemIndex == DialogResultItem.ITEM_INDEX_NEW){
                    result = getCurrentResultItem();
                }
                mViewCtrl.onDialogResultInternal(result, DIALOG_RESULT_POSITIVE);
                mDialog.dismiss();
            });
            if(mHasNeutral){
                Button neutralButton = ((AlertDialog)getDialog()).getButton(Dialog.BUTTON_NEUTRAL);
                neutralButton.setOnClickListener((view) -> {
                    for(AbsInputFieldViewController viewController : mDialogFieldCtrls){
                        viewController.clear();
                    }
                    //invalidate all views
                });
            }
        }
    }
}
