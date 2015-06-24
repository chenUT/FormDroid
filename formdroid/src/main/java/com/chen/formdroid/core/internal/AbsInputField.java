package com.chen.formdroid.core.internal;

import android.support.v4.app.Fragment;

import com.chen.formdroid.FormContext;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by chen on 3/27/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use=JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
public abstract class AbsInputField<T> {
    //id of this field
    @JsonProperty
    private final String fieldId;
    //position id indicate the position of this field in the form structure
    @JsonProperty
    private String posId;
    //top level properties
    @JsonProperty
    private String name;
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

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    //value of this field
    @JsonProperty
    protected T value;

    // field must have an Id
    // otherwise return null
    // TODO how to check this?
    // this is a must in order to get jackson working
    public AbsInputField(){
        this("");
    }

    @JsonCreator
    public AbsInputField(@JsonProperty("fieldId") String fieldId){
        this.fieldId = fieldId;
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
        return value == null;
    }

    @JsonIgnore
    public final AbsInputField<T> clone(String fieldId){
        AbsInputField result = cloneWithNewId(fieldId);
        result.allowEmpty = this.isAllowEmpty();
        result.name = this.getName();
        result.enabled = this.isEnabled();
        result.required = this.isRequired();
        result.visible = this.isVisible();
        result.posId = this.getPosId();
        result.value = this.getValue();
        return result;
    }

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

    private Class<? extends AbsInputFieldViewController> getReplaceViewControllerClass(AbsInputFieldViewController vCtrl){
        String vCtrlName = vCtrl.getClass().getName();
        Class<? extends AbsInputFieldViewController> newVCtrlClass = InputFieldFactory.getReplaceViewController(vCtrlName);
        return newVCtrlClass;
    }

//    public void fillByJSON(JSONObject tempFieldObj){
//        try {
//            Iterator<?> keys = tempFieldObj.keys();
//            while(keys.hasNext()){
//                String key = String.valueOf(keys.next());
//                if(key.equals(JsonTemplateParser.JSON_CONTENT_VALUE_KEY)){
//                    String content = tempFieldObj.getString(key);
//                    setValue(content);
//                }
//                else if(key.equals(JsonTemplateParser.JSON_FIELD_ID_KEY)){
//                    String knownPositionId = tempFieldObj.getString(key);
//                    if(!knownPositionId.contains("null")&&!TextUtils.isEmpty(knownPositionId)){
//                        setPositionId(knownPositionId);
//                    }
//                }
//                else if(key.equals(JsonTemplateParser.JSON_DISPLAY_NAME_KEY)){
//                    setDisplayName(tempFieldObj.getString(key));
//                }
//                else if(key.equals(JsonTemplateParser.JSON_FIELD_ENABLED_KEY)){
//                    setEnabled(tempFieldObj.getBoolean(key));
//                }
//            }
//        } catch (JSONException e) {
            // TODO: handle exception
//        }
//    }

    //----------- private methods -----------------
//    private void registerInputField(Class<? extends AbsInputField> fieldClass)  {
//        InputFieldFactory.registerField(fieldClass);
//    }

    //-----------  abstract methods -----------------
    public abstract T getValue();
    public abstract void setValue(T o);
    public abstract boolean clear();
    public abstract AbsInputFieldViewController getViewController(Fragment frag);

    /**
     * this should not be called, and should be override only.
     * @param fieldId
     * @return
     */
    protected abstract AbsInputField<T> cloneWithNewId(String fieldId);
}
