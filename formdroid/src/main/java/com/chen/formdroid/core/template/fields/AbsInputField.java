package com.chen.formdroid.core.template.fields;

import android.app.Fragment;

/**
 * Created by chen on 3/27/15.
 */
public abstract class AbsInputField<T> {
    //id of this field
    private final String fieldId;
    private String posId;

    //top level properties
    private String name;

    private boolean enabled;
    private boolean required;
    //if allow empty, the field will be carried as part of the result object regardless of its content value
    private boolean allowEmpty;
    private boolean visible;

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
    protected T value;

    //field must have an Id
    public AbsInputField(String fieldId){
        this.name = "";
        this.required=false;
        this.allowEmpty = false;
        this.enabled = true;
        this.fieldId = fieldId;
//        registerInputField(this.getClass());
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
    protected abstract Object getValue();
    protected abstract void setValue(T o);

    protected abstract AbsInputFieldViewController getViewController(Fragment frag);
}
