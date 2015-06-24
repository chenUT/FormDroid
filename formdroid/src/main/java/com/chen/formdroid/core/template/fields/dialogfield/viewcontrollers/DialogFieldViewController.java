package com.chen.formdroid.core.template.fields.dialogfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.formdroid.R;
import com.chen.formdroid.core.internal.AbsDialogFieldViewController;
import com.chen.formdroid.core.internal.AbsInputField;
import com.chen.formdroid.core.internal.FormCoreFragment;
import com.chen.formdroid.core.internal.FormDialogFragment;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogField;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogResultItem;
import com.chen.formdroid.utils.StringUtils;

import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
public class DialogFieldViewController extends AbsDialogFieldViewController<DialogField> {

    private LinearLayout mRootView;

    private LinearLayout mResultWrapper;

    public DialogFieldViewController(DialogField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    protected View getResultItemView(final DialogResultItem item) {
        String formId = ((FormCoreFragment)getFragment()).getFormId();
        String result ="";
        for(AbsInputField f : item.getResultFields()){
            result += f.toString() +"\n";
        }

        LinearLayout resultView = (LinearLayout)getInflater().inflate(R.layout.inputfield_dialog_result_simple_display_view, null, false);

        Button edit = (Button)resultView.findViewById(R.id.edit_button);
        edit.setOnClickListener(new OpenDialogButtonListener(formId, getField().getFieldId(),item.getIndex()));

        Button deleteButton = (Button)resultView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new DeleteResultItemButtonListener(item.getIndex()));

        TextView simpleText = (TextView)resultView.findViewById(R.id.simple_text);
        simpleText.setText(result);

        return resultView;
    }

    @Override
    public void onDialogResult(final DialogResultItem resultItem, int resultCode) {
        if(RESULT_CODE_CREATE_NEW == resultCode) {
            //this will assign an id to the resultItem
            getField().addResultItem(resultItem);
        }
        if(RESULT_CODE_CANCEL != resultCode){
            initResultList(getField(), mRootView);
        }
        mRootView.invalidate();
        //do nothing for other operations
    }

    @Override
    public View getView(final DialogField mField, Fragment mFrag) {
        String formId = ((FormCoreFragment)mFrag).getFormId();
        mRootView =(LinearLayout)getInflater().inflate(R.layout.inputfield_dialog, (ViewGroup) mFrag.getView(), false);
        //open dialog button
        Button button = (Button)mRootView.findViewById(R.id.dialog_action);
        mField.getFields();
        button.setOnClickListener(new OpenDialogButtonListener(formId, mField.getFieldId(), DialogResultItem.ITEM_INDEX_NEW));
        String fieldName = mField.getName();
        if(!StringUtils.isEmptyOrWhiteSpace(getField().getName())){
            button.setText(StringUtils.getResourceString(R.string.dialog_create_button_default_prefix) + " " + fieldName);
        }

        initResultList(mField, mRootView);
        return mRootView;
    }

    @Override
    protected void initViewValue(final DialogField field) {

    }

    private void initResultList(final DialogField field, ViewGroup root){
        if(mResultWrapper == null) {
            mResultWrapper = (LinearLayout) root.findViewById(R.id.dialog_result_wrapper);
        }
        else{
            //drop all current views
            mResultWrapper.removeAllViews();
        }
        List<DialogResultItem> items = field.getValue();
        if(items != null) {
            //results view
            for (int i = 0; i < items.size(); i++) {
                DialogResultItem tmpItem =  items.get(i);
                if(tmpItem.getIndex() != i){
                    tmpItem.setIndex(i);
                }
                mResultWrapper.addView(getResultItemView(items.get(i)));
            }
        }
    }

    private class OpenDialogButtonListener implements View.OnClickListener{

        private final String formId;
        private final String fieldId;
        private final int index;

        public OpenDialogButtonListener(String formId, String fieldId, int index){
            this.index = index;
            this.fieldId = fieldId;
            this.formId = formId;
        }

        @Override
        public void onClick(View v) {
            FormDialogFragment.showDialog(getFragment(),formId, fieldId, index);
        }
    }

    private class DeleteResultItemButtonListener implements View.OnClickListener{
        private final int index;
        public DeleteResultItemButtonListener(int index){
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            removeResultItem(index);
        }
    }

    private void removeResultItem(int index){
        List<DialogResultItem> items =  getField().getValue();
        items.remove(index);
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
