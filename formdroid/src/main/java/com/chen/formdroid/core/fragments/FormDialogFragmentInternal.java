package com.chen.formdroid.core.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.R;
import com.chen.formdroid.core.template.fields.AbsDialogFieldViewController;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogField;
import com.chen.formdroid.core.template.form.Form;
import com.chen.formdroid.exceptions.InputFieldTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
public class FormDialogFragmentInternal extends DialogFragment{
    private static final String FORM_ID_KEY="form_id";
    private static final String DIALOG_FIELD_ID_KEY ="field_id_key";

    public static final int DIALOG_RESULT_POSITIVE = 1;
    public static final int DIALOG_RESULT_NEGATIVE= 2;
    public static final int DIALOG_RESULT_CLEAR = 3;

    public static FormDialogFragmentInternal newInstance(String formId, String fieldId){
        FormDialogFragmentInternal frag = new FormDialogFragmentInternal();
        Bundle b = new Bundle();
        b.putString(FORM_ID_KEY,formId);
        b.putString(DIALOG_FIELD_ID_KEY, fieldId);
        frag.setArguments(b);
        return frag;
    }

    private AbsDialogFieldViewController mViewCtrl;
    private Form mForm;

    private List<AbsInputFieldViewController> mDialogFieldCtrls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            //retrive form and field information
            String formId = args.getString(FORM_ID_KEY);
            String fieldId = args.getString(DIALOG_FIELD_ID_KEY);
            mForm = FormContext.getInstance().getForm(formId);
            for(AbsInputField field : mForm.getFields()){
                if(fieldId.equals(field.getFieldId())){
                    if(field instanceof DialogField){
                        mViewCtrl = (AbsDialogFieldViewController)field.getViewControllerInternal(this);
                    }
                    //this should not happen
                    else{
                        throw new InputFieldTypeMismatchException(field.getName()+" is not a DialogField");
                    }
                }
            }//end of for(AbsInputField field : mForm.getFields())
            mDialogFieldCtrls = new ArrayList<>();
        }//end of if(args != null)
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = getBuilder(getDialogView());
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogOnShowListener(mViewCtrl.hasNeutralButton()));
        return dialog;
    }

    private View getDialogView(){
        List<AbsInputField> fields = mViewCtrl.getFields();
        for (int i = 0; i < fields.size(); i++) {
            AbsInputField field = fields.get(i);
            mDialogFieldCtrls.add(field.getViewControllerInternal(FormDialogFragmentInternal.this));

        }
        return null;
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
                    if(which == Dialog.BUTTON_NEGATIVE){
                        if(mViewCtrl != null){
                            mViewCtrl.onDialogResultInternal(getTargetRequestCode(), DIALOG_RESULT_NEGATIVE);
                        }
                        dialog.dismiss();
                    }
                });
        if(mViewCtrl.hasNeutralButton()){
            builder.setNeutralButton(R.string.dialog_neutral_button, null);
        }
        return builder;
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
            posButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewCtrl.onDialogResultInternal(getTargetRequestCode(), DIALOG_RESULT_POSITIVE);
                }
            });
        }
    }
}
