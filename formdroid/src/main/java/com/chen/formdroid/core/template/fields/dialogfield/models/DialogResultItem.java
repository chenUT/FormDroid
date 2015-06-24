package com.chen.formdroid.core.template.fields.dialogfield.models;



import com.chen.formdroid.core.internal.AbsInputField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * item to hold dialog result
 * TODO:should we create a abstract class or interface for this?
 * Created by chen on 5/18/15.
 */
public class DialogResultItem {

    public static final int ITEM_INDEX_NEW = -1;

    private List<AbsInputField> resultFields;

    @JsonIgnore
    private int index;

    public DialogResultItem(){
       this(-1);
    }

    public DialogResultItem(int index){
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
            field.clear();
        }
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
