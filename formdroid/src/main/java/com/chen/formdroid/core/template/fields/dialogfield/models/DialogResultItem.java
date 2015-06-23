package com.chen.formdroid.core.template.fields.dialogfield.models;



import com.chen.formdroid.core.internal.AbsInputField;

import java.util.ArrayList;
import java.util.List;

/**
 * item to hold dialog result
 * TODO:should we create a abstract class or interface for this
 * Created by chen on 5/18/15.
 */
public class DialogResultItem {
    private List<AbsInputField> resultFields;
    private int index;

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
}
