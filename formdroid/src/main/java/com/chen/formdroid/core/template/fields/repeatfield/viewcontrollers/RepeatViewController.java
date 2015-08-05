package com.chen.formdroid.core.template.fields.repeatfield.viewcontrollers;

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
import com.chen.formdroid.core.template.fields.repeatfield.models.RepeatField;
import com.chen.formdroid.core.template.fields.repeatfield.models.RepeatResultItem;
import com.chen.formdroid.utils.StringUtils;

import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
public class RepeatViewController extends AbsDialogFieldViewController<RepeatField> {

    private LinearLayout mRootView;

    private LinearLayout mResultWrapper;

    public RepeatViewController(RepeatField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    protected View getResultItemView(final RepeatResultItem item) {
        String formId = ((FormCoreFragment)getFragment()).getFormId();
        String result ="";
        for(AbsInputField f : item.getResultFields()){
            result += f.toString() +"\n";
        }

        LinearLayout resultView = (LinearLayout)getInflater().inflate(R.layout.inputfield_dialog_result_simple_display_view, null, false);

        Button edit = (Button)resultView.findViewById(R.id.edit_button);
        edit.setOnClickListener(new OpenDialogButtonListener(formId, item.getIndex()));

        //disable the edit button if not mutable
        if(!getField().isMutable()){
            edit.setEnabled(false);
        }

        Button deleteButton = (Button)resultView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new DeleteResultItemButtonListener(item.getIndex()));

        TextView simpleText = (TextView)resultView.findViewById(R.id.simple_text);
        simpleText.setText(result);

        return resultView;
    }

    @Override
    public void onDialogResult(final RepeatResultItem resultItem, int resultCode) {
        if(RESULT_CODE_CREATE_NEW == resultCode) {
            //this will assign an id to the resultItem
            getField().addResultItem(resultItem);
        }
        if(RESULT_CODE_CANCEL != resultCode){
            initViewValue(getField());
        }
        mRootView.invalidate();
        //do nothing for other operations
    }

    @Override
    public View getView(final RepeatField mField, Fragment mFrag) {
        String formId = ((FormCoreFragment)mFrag).getFormId();
        mRootView =(LinearLayout)getInflater().inflate(R.layout.inputfield_dialog, (ViewGroup) mFrag.getView(), false);
        //open dialog button
        Button button = (Button)mRootView.findViewById(R.id.dialog_action);
        mField.getFields();
        button.setOnClickListener(new OpenDialogButtonListener(formId, RepeatResultItem.ITEM_INDEX_NEW));
        String fieldName = mField.getName();
        if(!StringUtils.isEmptyOrWhiteSpace(getField().getName())){
            button.setText(StringUtils.getResourceString(R.string.dialog_create_button_default_prefix) + " " + fieldName);
        }
        initViewValue(mField);
        return mRootView;
    }

    @Override
    protected void initViewValue(final RepeatField field) {
        initResultList(field, mRootView);
    }

    private void initResultList(){
        if(mRootView != null) {
            initResultList(getField(), mRootView);
        }
    }

    private void initResultList(final RepeatField field, ViewGroup root){
        if(mResultWrapper == null) {
            mResultWrapper = (LinearLayout) root.findViewById(R.id.dialog_result_wrapper);
        }
        else{
            //drop all current views
            mResultWrapper.removeAllViews();
        }
        List<RepeatResultItem> items = field.getValue();
        if(items != null) {
            //results view
            for (int i = 0; i < items.size(); i++) {
                RepeatResultItem tmpItem =  items.get(i);
                if(tmpItem.getIndex() != i){
                    tmpItem.setIndex(i);
                }
                mResultWrapper.addView(getResultItemView(items.get(i)));
            }
        }
    }

    private class OpenDialogButtonListener implements View.OnClickListener{

        private final String formId;
        private final int index;

        public OpenDialogButtonListener(String formId, int index){
            this.index = index;
            this.formId = formId;
        }

        @Override
        public void onClick(View v) {
            FormDialogFragment.showDialog(getFragment(),index, RepeatViewController.this);
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
            initResultList();
        }
    }

    private void removeResultItem(int index){
        List<RepeatResultItem> items =  getField().getValue();
        items.remove(index);
    }

    /**
     *
     * @param data
     * @return true data has been updated
     *          false data fail to update
     */
    private boolean updateFieldData(RepeatField field, RepeatResultItem data){
        //if its an update we do the update other we add back to result
        for (RepeatResultItem value : field.getValue()) {
                return true;
        }
        return false;
    }

}
