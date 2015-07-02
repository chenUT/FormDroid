package com.chen.formdroid.core.template.fields;

import com.chen.formdroid.core.template.fields.dialogfield.models.DialogResultItem;
import com.chen.formdroid.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by chen on 4/22/15.
 */
public abstract class AbsDialogField extends AbsCompositeField<List<DialogResultItem>>{
    @JsonProperty
    public String dialogTitle;

    public AbsDialogField(String fieldId) {
        super(fieldId);
    }

    public AbsDialogField() {
        super();

    }

    public String getDialogTitle() {
        if(StringUtils.isEmptyOrWhiteSpace(this.dialogTitle)){
            return getName();
        }
        return dialogTitle;
    }

    @Override
    public boolean clear() {
        for(DialogResultItem resultField : super.value){
           resultField.clear();
        }
        return false;
    }

    //whether the result of this dialog is mutable
    public abstract boolean isMutable();
}
