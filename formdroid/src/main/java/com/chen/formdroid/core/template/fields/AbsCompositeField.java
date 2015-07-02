package com.chen.formdroid.core.template.fields;

import com.chen.formdroid.core.internal.AbsInputField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 4/20/15.
 */
public abstract class AbsCompositeField<T> extends AbsInputField<T>{

    public AbsCompositeField(@JsonProperty("fieldId") String fieldId) {
        super(fieldId);
    }

    @JsonProperty
    private List<AbsInputField> fields;

    public AbsCompositeField() {
    }

    public List<AbsInputField> getFields() {
        return fields;
    }

    public AbsInputField getFieldById(String fieldId){
        for(AbsInputField field : fields){
            if(field.getFieldId().equals(fieldId)){
                return field;
            }
        }
        return null;
    }

    public void addField(AbsInputField field){
        //lazy init
        if(fields == null){
            this.fields = new ArrayList<>();
        }
        this.fields.add(field);
    }

    public void setFields(List<AbsInputField> fields) {
        this.fields = fields;
    }
}
