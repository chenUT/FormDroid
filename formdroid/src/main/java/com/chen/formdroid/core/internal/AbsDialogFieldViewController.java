package com.chen.formdroid.core.internal;

import android.support.v4.app.Fragment;
import android.view.View;

import com.chen.formdroid.core.template.fields.AbsDialogField;
import com.chen.formdroid.core.template.fields.repeatfield.models.RepeatResultItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
public abstract class AbsDialogFieldViewController<T extends AbsDialogField> extends AbsInputFieldViewController<T> {

    public static final int RESULT_CODE_CREATE_NEW = 1;
    public static final int RESULT_CODE_UPDATE = 2;
    public static final int RESULT_CODE_CANCEL = 3;

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
     * internal method called when dialog has result
     * @param resultItem item as result
     * @param resultCode
     */
    final void onDialogResultInternal(final RepeatResultItem resultItem, int resultCode){
        switch (resultCode){
            case FormDialogFragment.DIALOG_RESULT_POSITIVE:
                if(null != resultItem){
                    onDialogResult(resultItem, RESULT_CODE_CREATE_NEW);
                }
                else {
                    onDialogResult(null, RESULT_CODE_UPDATE);
                }
                break;
            case FormDialogFragment.DIALOG_RESULT_NEGATIVE:
                onDialogResult(null, RESULT_CODE_CANCEL);
                break;
        }
    }

    public List<AbsInputFieldViewController> getDialogViewControllers(Fragment fragment, int resultIndex){
        List<AbsInputField> fields;
        if(resultIndex == RepeatResultItem.ITEM_INDEX_NEW){
           fields = this.getFields();
        }
        else{
            fields = this.getResultItem(resultIndex).getResultFields();
        }
        List<AbsInputFieldViewController> result = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            AbsInputField field = fields.get(i);
            result.add(field.getViewControllerInternal(fragment));
        }
        return result;
    }

    private RepeatResultItem getResultItem(int index){
       return getField().getValue().get(index);
    }

    /**
     * this method will be called when the dialog returns a result to the controller
     * @param resultItem {@link RepeatResultItem}, this will be null if it is a update
     * @param resultCode
     */
    protected abstract void onDialogResult(final RepeatResultItem resultItem, int resultCode);

    /**
     * the result view is how a dialog result item will displayed in a dialog field
     * nothing shows up if it returns null
     * @param item the item object of the result item , it contains the index of the result inside dialog field,
     *             because the dialog result is a list of absinputfield,
     *             this index will help us to find the given result item
     *
     * @return
     */
    protected abstract View getResultItemView(final RepeatResultItem item);
}
