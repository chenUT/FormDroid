package com.chen.formdroid.core.template.fields.dialogfield.viewcontrollers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chen.formdroid.R;
import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.AbsCompositeField;
import com.chen.formdroid.core.template.fields.AbsDialogField;
import com.chen.formdroid.core.template.fields.AbsDialogFieldViewController;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogField;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogResultItem;
import com.chen.formdroid.utils.StringUtils;

import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
public class DialogFieldViewController extends AbsDialogFieldViewController<DialogField> {
    public DialogFieldViewController(DialogField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    public View getResultItemView(DialogResultItem item, int resultIndex) {
        return null;
    }

    @Override
    public void onDialogResult(@Nullable DialogField field, int requestCode, int resultCode) {
        if(RESULT_CODE_SUCCESS == resultCode) {

        }
    }

    @Override
    public View getView(DialogField mField, Fragment mFrag) {
        LinearLayout rootView =(LinearLayout)getInflater().inflate(R.layout.inputfield_dialog, (ViewGroup) mFrag.getView(), false);

        //open dialog button
        Button button = (Button)rootView.findViewById(R.id.dialog_action);


        //results view
        LinearLayout resultWrapper = (LinearLayout)rootView.findViewById(R.id.dialog_result_wrapper);

        return rootView;
    }

    @Override
    protected void initViewValue(DialogField field) {
    }

    /**
     *
     * @param data
     * @return true data has been updated
     *          false data fail to update
     */
    private boolean updateFieldData(DialogField field, DialogResultItem data){
        //if its an update we do the update other we add back to result
        for (DialogResultItem value : field.getValue()) {
                return true;
        }
        return false;
    }
}
