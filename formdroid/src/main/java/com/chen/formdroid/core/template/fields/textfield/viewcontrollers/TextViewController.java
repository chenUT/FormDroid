package com.chen.formdroid.core.template.fields.textfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.formdroid.R;
import com.chen.formdroid.core.template.fields.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.textfield.models.TextField;
import com.chen.formdroid.utils.StringUtils;

import org.w3c.dom.Text;

import java.util.ResourceBundle;

/**
 * Created by chen on 3/29/15.
 */
public class TextViewController extends AbsInputFieldViewController<TextField> {
    private EditText mEditText;
    private TextView mLabel;

    public TextViewController(TextField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    public View getView(final TextField field, Fragment frag) {
        LinearLayout wrapper;
        if(!StringUtils.isEmptyOrWhiteSpace(getField().getName())){
            wrapper =(LinearLayout) getInflater().inflate(R.layout.inputfield_textfield_label, null, false);
            mLabel = (TextView)wrapper.findViewById(R.id.textfield_label);
        }
        else{
            wrapper =(LinearLayout) getInflater().inflate(R.layout.inputfield_textfield, null, false);
        }
        mEditText = (EditText)wrapper.findViewById(R.id.textfield_edit_text);
        mEditText.addTextChangedListener(new SimpleTextFieldWatcher(field));
        invalidateView(field);
        return wrapper;
    }

    @Override
    public void invalidateView(TextField field) {
        super.invalidateView(field);
        if(field.hasHint()){
            mEditText.setHint(field.getHint());
        }
        if(!StringUtils.isEmptyOrWhiteSpace(field.getName())){
            mLabel.setText(field.getName());
        }
    }

    /**
     * this will be called when this view controller get validated through any logic operation
     */
    @Override
    protected void initViewValue(final TextField field){
        mEditText.setText(field.getValue());
    }

    /**
     * Watcher for this EditText
     */
    class SimpleTextFieldWatcher implements TextWatcher{
        private TextField field;
        public SimpleTextFieldWatcher(TextField field){
            super();
            this.field = field;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            field.setValue(s);
        }
    }
}


