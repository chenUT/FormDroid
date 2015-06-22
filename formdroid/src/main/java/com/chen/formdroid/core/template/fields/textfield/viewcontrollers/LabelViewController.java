package com.chen.formdroid.core.template.fields.textfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.formdroid.R;
import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.textfield.models.LabelField;

import org.w3c.dom.Text;

/**
 * Created by chen on 3/29/15.
 */
public class LabelViewController extends AbsInputFieldViewController<LabelField>{

    private TextView mTextView;

    public LabelViewController(LabelField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    public View getView(final LabelField field,final Fragment frag) {
        LinearLayout wrapper =(LinearLayout) getInflater().inflate(R.layout.inputfield_labelfield, null, false);
        mTextView = (TextView)wrapper.findViewById(R.id.textfield_label);
        return mTextView;
    }

    @Override
    protected void initViewValue(final LabelField field){
        if(mTextView != null) {
            mTextView.setText(field.getValue());
        }
    }
}
