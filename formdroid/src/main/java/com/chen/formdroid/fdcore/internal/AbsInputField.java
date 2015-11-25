package com.chen.formdroid.fdcore.internal;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.android.IAndroidViewModel;
import com.chen.formdroid.fdcore.logics.LogicAction;
import com.chen.formdroid.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by chen on 3/27/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
public abstract class AbsInputField<T> implements IAndroidViewModel {
    //id of this field this will fall to name if not specified
    @JsonProperty
    private final String fieldId;

    //name for this field this should be unique unless in a repeat field
    @JsonProperty
    private final String name;

    /**top level properties**/

    //position id indicate the position of this field in the form structure
    @JsonProperty
    private String posId;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean enabled = true;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean required = false;
    //if allow empty, the field will be carried as part of the result object regardless of its content value
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean allowEmpty = false;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean visible = true;

    @JsonProperty("logic")
    private List<LogicAction> logicList;

    @JsonIgnore
    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    @JsonIgnore
    public String getFieldId() {
        return fieldId;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonIgnore
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @JsonIgnore
    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    @JsonIgnore
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @JsonIgnore
    public List<LogicAction> getLogicList() {
        return logicList;
    }

    public void setLogicList(List<LogicAction> logicList) {
        this.logicList = logicList;
    }

    //value of this field
    @JsonProperty
    protected T value;
    /**end of top level properties**/


    // field must have an Id
    // otherwise return null
    // TODO how to check this?
    // this is a must in order to get jackson working
    public AbsInputField(){
        this("", "");
    }

    @JsonCreator
    public AbsInputField(@JsonProperty("fieldId") String fieldId,
                         @JsonProperty("name")String name){
        if(TextUtils.isEmpty(fieldId)) {
            fieldId = name;
        }
        this.fieldId = fieldId;
        this.name = name;
    }

    public String toJsonString() {
        try {
            return FormContext.getMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @JsonIgnore
    public boolean isEmpty(){
        return value == null || StringUtils.isEmptyOrWhiteSpace(value.toString());
    }

    @JsonIgnore
    public boolean isFinished(){
        if(isRequired() && isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(isEmpty()){
            return "";
        }
        return this.value.toString();
    }

    @JsonIgnore
    public final AbsInputField<T> clone(String fieldId){
        AbsInputField result = cloneWithNewId(fieldId);
        result.allowEmpty = this.isAllowEmpty();
        result.enabled = this.isEnabled();
        result.required = this.isRequired();
        result.visible = this.isVisible();
        result.posId = this.getPosId();
        result.value = this.getValue();
        return result;
    }

    /**
     * TODO separate controller creation from input field, maybe change to annotation based IoC injection, so inputfield models can be used cross platform
     *
     * NOTE: this runs on background thread within {@link FormCoreFragment.CreateViewTask}
     * runs on main thread in {@link AbsRepeatFieldViewController#getDialogViewControllers}
     */
    //view engine fragment will call this to get correct viewcontroller
    @JsonIgnore
    final AbsInputFieldViewController getViewControllerInternal(Fragment frag){
       AbsInputFieldViewController vCtrl = getViewController(frag);
       Class<? extends AbsInputFieldViewController> replaceClass = getReplaceViewControllerClass(vCtrl);
       if(replaceClass != null){
           //free the memory
           vCtrl = null;
           //create a new view controller using reflection if there is a replacement
           try {
               Class<?>[] args = {AbsInputField.class, Fragment.class};
               Constructor<?> cons = replaceClass.getConstructor(args);
               Object[] arguments = {this, frag};
               return (AbsInputFieldViewController)cons.newInstance(arguments);
           } catch (NullPointerException e){
               e.printStackTrace();
           } catch (InvocationTargetException e) {
               e.printStackTrace();
           } catch (InstantiationException e) {
               e.printStackTrace();
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           } catch (NoSuchMethodException e) {
               e.printStackTrace();
           }
       }
       //return default controller by default
       return vCtrl;
    }

    /**
     * by default it does nothing
     * @param logicAction current logic action
     * @param targetField the field that this action is applied on
     */
    protected void processLogic(LogicAction logicAction, AbsInputField targetField){}

    //-----------  abstract methods -----------------
    public abstract T getValue();
    public abstract void setValue(T o);

    //clearToDefault the value of the field and return result as successful or not
    public abstract boolean clearToDefault();

    /**
     * this should not be called, and should be override only.
     * @param fieldId
     * @return
     */
    protected abstract AbsInputField<T> cloneWithNewId(String fieldId);

    //----------- private methods -------------------
    private Class<? extends AbsInputFieldViewController> getReplaceViewControllerClass(AbsInputFieldViewController vCtrl){
        String vCtrlName = vCtrl.getClass().getName();
        Class<? extends AbsInputFieldViewController> newVCtrlClass = InputFieldFactory.getReplaceViewController(vCtrlName);
        return newVCtrlClass;
    }
}
