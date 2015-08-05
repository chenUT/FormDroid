package com.chen.formdroid.core.template.fields.selectfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.internal.AbsInputField;
import com.chen.formdroid.core.internal.AbsInputFieldViewController;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 * the stored value of SelecterField is the id of the selectedOption
 *
 * Created by chen.liu on 8/5/2015.
 */
@InputField(type = "select_one")
public class SelectOneField extends AbsInputField<ChoiceOption>{

    public SelectOneField() {
    }

    public SelectOneField(@JsonProperty("fieldId") String fieldId) {
        super(fieldId);
    }

    @JsonProperty("options")
    private List<ChoiceOption> choiceOptionList;

    @Override
    public ChoiceOption getValue() {
        return this.value;
    }

    @Override
    public void setValue(ChoiceOption o) {
        this.value = o;
    }

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return null;
    }

    public void setChoiceOptionList(List<ChoiceOption> choiceOptionList){
        this.choiceOptionList = choiceOptionList;
    }

    @Override
    protected AbsInputField<ChoiceOption> cloneWithNewId(String fieldId) {
        SelectOneField selectOneField = new SelectOneField(fieldId);
        selectOneField.setChoiceOptionList(choiceOptionList);
        return selectOneField;
    }
}
