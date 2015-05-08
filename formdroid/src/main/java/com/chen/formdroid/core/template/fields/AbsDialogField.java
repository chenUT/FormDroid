package com.chen.formdroid.core.template.fields;

import java.util.List;

/**
 * Created by chen on 4/22/15.
 */
public abstract class AbsDialogField extends AbsCompositeField<List<AbsInputField>>{
    @Override
    public boolean clear() {
        for(AbsInputField resultField : super.value){
           resultField.clear();
        }
        return false;
    }
    //whether the result of this dialog is mutable
    public abstract boolean isMutable();
}
