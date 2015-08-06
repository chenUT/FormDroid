package com.chen.formdroid.form.template.fields.repeatfield.models;


import android.support.v4.app.Fragment;

import com.chen.formdroid.form.annotations.InputField;
import com.chen.formdroid.form.internal.AbsInputField;
import com.chen.formdroid.form.internal.AbsInputFieldViewController;
import com.chen.formdroid.form.template.fields.AbsRepeatField;
import com.chen.formdroid.form.template.fields.repeatfield.viewcontrollers.RepeatViewController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
@InputField(type = "repeat")
public class RepeatField extends AbsRepeatField {
    public RepeatField(){
        super();
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean mutable = true;

    @JsonCreator
    public RepeatField(@JsonProperty("fieldId") String fieldId, @JsonProperty("name")String name) {
        super(fieldId, name);
    }

    public boolean isMutable() {
        return mutable;
    }

    @Override
    public List<RepeatResultItem> getValue() {
        return this.value;
    }

    @Override
    public AbsInputField<List<RepeatResultItem>> cloneWithNewId(String fieldId) {
        RepeatField result = new RepeatField(fieldId, getName());
        //clone the fields
        //we will clone the subfields with a id plus its index within this repeat group
        for(int i =0; i< getFields().size(); i++){
            AbsInputField tmp = getFields().get(i);
            AbsInputField cloned = tmp.clone(fieldId+"#"+i);
            result.addField(getFields().get(i));
        }
        return result;
    }

    @Override
    public void setValue(List<RepeatResultItem> o) {
       this.value = o;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new RepeatViewController(this, frag);
    }

    public void addResultItem(final RepeatResultItem resultItem){
        if(this.value == null){
            this.value = new ArrayList<>();
        }
        int index = this.value.size();
        resultItem.setIndex(index);
        this.value.add(resultItem);
    }
}
