package com.chen.formdroid.fdcore.template.fields.selectfield.viewcontrollers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chen.formdroid.R;
import com.chen.formdroid.exceptions.InvalidTemplateException;
import com.chen.formdroid.fdcore.internal.AbsInputFieldViewController;
import com.chen.formdroid.fdcore.template.fields.selectfield.models.ChoiceOption;
import com.chen.formdroid.fdcore.template.fields.selectfield.models.SelectOneField;

import java.util.HashMap;
import java.util.List;

/**
 * Created by chen.liu on 8/5/2015.
 */
public class SelectOneViewController extends AbsInputFieldViewController<SelectOneField>{

    private RadioGroup mSelectGroup;
    private TextView mLabelView;

    public SelectOneViewController(final SelectOneField field, Fragment frag) {
        super(field, frag);
    }

    @Override
    protected View getView(SelectOneField field, Fragment frag) {
        LinearLayout wrapper = (LinearLayout)getInflater().inflate(R.layout.inputfield_selectone, null ,false);

        mSelectGroup = (RadioGroup)wrapper.findViewById(R.id.selectonefield_select_group);
        mLabelView = (TextView)wrapper.findViewById(R.id.selectonefield_label);

        initViewValue(field);
        return wrapper;
    }

    //TODO for some reason this is the only way for radio button group to behave correctly
    /*this offset is added to the indexer so that the correct radio button can be selected*/
    private static final int ID_OFFSET = 2;
    @Override
    protected void initViewValue(final SelectOneField field) {
        List<ChoiceOption> options = field.getChoiceOptionList();
        if(options == null){
            throw new InvalidTemplateException("select one field must have a choice options list presents");
        }
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < options.size(); i++){
            RadioButton rb = new RadioButton(getActivity());
            rb.setText(options.get(i).getStringContent());
            rb.setLayoutParams(params);
            rb.setTag(options.get(i).getStringContent());
            rb.setId(i+ID_OFFSET);
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    field.setValue((String) v.getTag());
                    mSelectGroup.check(v.getId());
                }
            });
            mSelectGroup.addView(rb);
            if(!field.isEmpty()
                    && options.get(i).getStringContent().equals(field.getValue().getStringContent())){
                mSelectGroup.check(rb.getId());
            }
        }
        mLabelView.setText(field.getName());
    }
}
