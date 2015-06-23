package com.chen.formdroid.core.internal;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chen.formdroid.core.fragments.FormDialogFragmentInternal;
import com.chen.formdroid.core.template.fields.AbsDialogField;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogResultItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
public abstract class AbsDialogFieldViewController<T extends AbsDialogField> extends AbsInputFieldViewController<T> {

    public static final int RESULT_CODE_SUCCESS = 1;
    public static final int RESULT_CODE_CANCEL = 2;

    public static final int  REQUEST_CODE_ADD = 100;
    public static final int  REQUEST_CODE_UPDATE = 101;
    public static final int  REQUEST_CODE_DELETE = 102;

    public AbsDialogFieldViewController(T field, Fragment frag) {
        super(field, frag);
    }

    /**
     * override this to tell the dialog whether it will create a clear button
     * @return true if the dialog need a clear button to clear all input fields
     */
    public boolean hasNeutralButton(){
        return true;
    }

    /**
     * this method gives the title of the dialog
     * by default it is the dialog title setting of the dialog field
     * @return
     */
    public String getDialogTitle(){
        return getField().getDialogTitle();
    }

    public List<AbsInputField> getFields(){
        return this.getField().getFields();
    }

    /**
     * the result view is how a dialog result item will displayed in a dialog field
     * nothing shows up if it returns null
     * @param field same as getField()
     * @param resultIndex the index of the result inside dialog field,
     *                    because the dialog result is a list of absinputfield,
     *                    this index will help us to find the given result item
     *
     * @return
     */
    public View getResultItemView(DialogResultItem field, int resultIndex){
        return null;
    }

    /**
     * internal method called when dialog has result
     * @param requestCode
     * @param resultCode
     */
    final void onDialogResultInternal(int requestCode, int resultCode){
        switch (resultCode){
            case FormDialogFragmentInternal.DIALOG_RESULT_POSITIVE:
                onDialogResult(getField(), requestCode, RESULT_CODE_SUCCESS);
                break;
            case FormDialogFragmentInternal.DIALOG_RESULT_NEGATIVE:
                onDialogResult(getField(), requestCode, RESULT_CODE_CANCEL);
                break;
        }
    }

    public List<AbsInputFieldViewController> getDialogViewControllers(Fragment fragment){
        List<AbsInputField> fields = this.getFields();
        List<AbsInputFieldViewController> result = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            AbsInputField field = fields.get(i);
            result.add(field.getViewControllerInternal(fragment));
        }
        return result;
    }

    /**
     * this method will be called when the dialog returns a result to the controller
     * @param requestCode
     * @param resultCode
     */
    protected abstract void onDialogResult(@NonNull T field, int requestCode, int resultCode);
}
