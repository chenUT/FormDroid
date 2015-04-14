package com.chen.formdroid.core.template.fields.textfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chen.formdroid.core.template.fields.AbsInputField;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.textfield.models.TextField;
import com.chen.fromdroid.R;

/**
 * Created by chen on 3/29/15.
 */
public class TextViewController extends AbsInputFieldViewController<TextField>{

    private EditText mEditText;

    public TextViewController(TextField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    public View getView(AbsInputField mField, Fragment mFrag) {
        LinearLayout wrapper = (LinearLayout)getInflator().inflate(R.layout.inputfield_textfield, null, false);
        mEditText = (EditText)wrapper.findViewById(R.id.textfield_edit_text);
        mEditText.addTextChangedListener(new SimpleTextFieldWatcher());
        mEditText.setText(getField().getValue());
        mEditText.setHint(getField().getName());
        return wrapper;
    }

    @Override
    public void validateView() {
        mEditText.setHint(getField().getName());
        mEditText.setText(getField().getValue());
    }

    /**
     * Watcher for this EditText
     */
    class SimpleTextFieldWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            getField().setValue(s);
        }
    }
}


