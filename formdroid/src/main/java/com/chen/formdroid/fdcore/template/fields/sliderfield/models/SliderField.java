package com.chen.formdroid.fdcore.template.fields.sliderfield.models;

import android.support.v4.app.Fragment;

import com.chen.formdroid.fdcore.annotations.InputField;
import com.chen.formdroid.fdcore.internal.AbsInputField;
import com.chen.formdroid.fdcore.internal.AbsInputFieldViewController;
import com.chen.formdroid.fdcore.template.fields.sliderfield.viewcontrollers.SliderViewController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chen on 11/25/15.
 */
@InputField(type = "slider")
public class SliderField extends AbsInputField<Integer>{

    @JsonProperty(defaultValue = "-1", value = "max")
    private int maxValue;

    public SliderField(){super();}

    @JsonCreator
    public SliderField(@JsonProperty("fieldId")String fieldId, @JsonProperty("name")String name) {
        super(fieldId, name);
    }

    @Override
    public Integer getValue() {
        if(this.value == null){
            return 0;
        }
        return this.value;
    }

    @Override
    public void setValue(Integer o) {
        this.value = o;
    }

    @Override
    public boolean clearToDefault() {
        this.value = 0;
        return true;
    }

    @Override
    public AbsInputFieldViewController getViewController(Fragment frag) {
        return new SliderViewController(this, frag);
    }

    @Override
    public AbsInputField<Integer> cloneWithNewId(String fieldId) {
        SliderField field = new SliderField(fieldId, getName());
        field.setMaxValue(this.maxValue);
        return field;
    }

    //max value could not be less than current value
    public int getMaxValue() {
        if(maxValue < 0) {
            return this.value;
        }
        return this.maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
