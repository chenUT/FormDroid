package com.chen.formdroid.form.template.fields.selectfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.form.annotations.InputField;
import com.chen.formdroid.form.internal.AbsInputField;
import com.chen.formdroid.form.internal.AbsInputFieldViewController;
import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public SelectOneField(@JsonProperty("fieldId") String fieldId, @JsonProperty("name")String name) {
        super(fieldId, name);
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
        SelectOneField selectOneField = new SelectOneField(fieldId, getName());
        selectOneField.setChoiceOptionList(choiceOptionList);
        return selectOneField;
    }
}
