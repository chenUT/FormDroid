package com.chen.formdroid.core.template.fields.textfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.textfield.models.LabelField;
import com.chen.formdroid.core.template.fields.textfield.models.TextField;

/**
 * Created by chen on 3/29/15.
 */
public class LabelViewController extends AbsInputFieldViewController<LabelField>{

    private TextView mTextView;

    public LabelViewController(LabelField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    public View getView(AbsInputField mField, Fragment mFrag) {
        return null;
    }

    @Override
    public void validateView() {
        if(mTextView != null) {
            mTextView.setText(getField().getValue());
        }
    }
}
