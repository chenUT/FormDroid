package com.chen.formdroid.fdcore.template.fields.repeatfield.models;



import com.chen.formdroid.fdcore.internal.AbsInputField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * item to hold dialog result
 * TODO:should we create a abstract class or interface for this?
 * Created by chen on 5/18/15.
 */
public class RepeatResultItem {

    public static final int ITEM_INDEX_NEW = -1;

    private List<AbsInputField> resultFields;

    @JsonIgnore
    private int index;

    public RepeatResultItem(){
       this(-1);
    }

    public RepeatResultItem(int index){
        resultFields = new ArrayList<>();
        this.index = index;
    }

    public List<AbsInputField> getResultFields() {
        return resultFields;
    }

    public void setResultFields(List<AbsInputField> resultFields) {
        this.resultFields = resultFields;
    }

    public void clear(){
        for(AbsInputField field : resultFields){
            field.clearToDefault();
        }
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
