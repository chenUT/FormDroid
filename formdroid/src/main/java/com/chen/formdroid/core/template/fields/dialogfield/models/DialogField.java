package com.chen.formdroid.core.template.fields.dialogfield.models;


import android.support.v4.app.Fragment;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.internal.AbsInputField;
import com.chen.formdroid.core.internal.AbsInputFieldViewController;
import com.chen.formdroid.core.template.fields.AbsCompositeField;
import com.chen.formdroid.core.template.fields.AbsDialogField;
import com.chen.formdroid.core.template.fields.dialogfield.viewcontrollers.DialogFieldViewController;
import com.chen.formdroid.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 4/23/15.
 */
@InputField(type = "dialog")
public class DialogField extends AbsDialogField {
    public DialogField(){
        super();
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean mutable = true;

    @JsonCreator
    public DialogField(@JsonProperty("fieldId") String fieldId) {
        super(fieldId);
    }

    public boolean isMutable() {
        return mutable;
    }

    @Override
    public List<DialogResultItem> getValue() {
        return this.value;
    }

    @Override
    public AbsInputField<List<DialogResultItem>> cloneWithNewId(String fieldId) {
        DialogField result = new DialogField(fieldId);
        //clone the fields
        for(int i =0; i< getFields().size(); i++){
            AbsInputField tmp = getFields().get(i);
            AbsInputField cloned = tmp.clone(fieldId+"#"+i);
            result.addField(getFields().get(i));
        }
        return result;
    }

    @Override
    public void setValue(List<DialogResultItem> o) {
       this.value = o;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new DialogFieldViewController(this, frag);
    }

    public void addResultItem(final DialogResultItem resultItem){
        if(this.value == null){
            this.value = new ArrayList<>();
        }
        int index = this.value.size();
        resultItem.setIndex(index);
        this.value.add(resultItem);
    }
}
