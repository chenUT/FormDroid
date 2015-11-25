package com.chen.formdroid.fdcore.template.fields.selectfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.fdcore.annotations.InputField;
import com.chen.formdroid.fdcore.internal.AbsInputField;
import com.chen.formdroid.fdcore.internal.AbsInputFieldViewController;
import com.chen.formdroid.fdcore.template.fields.selectfield.viewcontrollers.SelectOneViewController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 * the stored value of SelecterField is the id of the selectedOption
 *
 * Created by chen.liu on 8/5/2015.
 */
@InputField(type = "selectOne")
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

    @JsonIgnore
    public void setValue(String text){
        for(int i = 0; i< choiceOptionList.size(); i++){
            if(text.equals(choiceOptionList.get(i).getStringContent())){
                this.value = choiceOptionList.get(i);
                break;
            }
        }
    }

    @Override
    public boolean clearToDefault() {
        this.value = null;
        return true;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new SelectOneViewController(this, frag);
    }

    public void setChoiceOptionList(List<ChoiceOption> choiceOptionList){
        this.choiceOptionList = choiceOptionList;
    }

    public List<ChoiceOption> getChoiceOptionList(){
        return this.choiceOptionList;
    }

    @Override
    protected AbsInputField<ChoiceOption> cloneWithNewId(String fieldId) {
        SelectOneField selectOneField = new SelectOneField(fieldId, getName());
        selectOneField.setChoiceOptionList(choiceOptionList);
        return selectOneField;
    }
}
