package com.chen.formdroid.core.template.fields;

import android.util.Log;

import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.textfield.models.LabelField;
import com.chen.formdroid.exceptions.InputFieldTypeMismatchException;
import com.chen.formdroid.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chen on 3/28/15.
 * Internal Class
 */
public final class InputFieldFactory {
    //constants
    public static final String FIELD_ID_KEY = "fieldId";
    public static final String POSITION_ID_KEY = "posId";
    public static final String NAME_KEY = "name";
    public static final String ENABLED_KEY = "enabled";
    public static final String MEDIA_URI_KEY = "mediaUri";
    public static final String SHOW_NAME_KEY = "showName";
    public static final String HAS_VOICE_KEY = "hasVoice";
    public static final String VISIBLE_KEY = "visible";
    public static final String DATABASE_KEY = "dbUri";
    public static final String VALUE_KEY = "value";

    //cache to store reflection objects
    private static final Map<String, Class<? extends AbsInputField>> _inputFieldMap = new ConcurrentHashMap<String, Class<? extends AbsInputField>>();

    //cache to store possible overridden viewcontroller
    private static final Map<String, Class<? extends AbsInputFieldViewController>> _fieldReplaceViewControllerMap = new ConcurrentHashMap<String, Class<? extends AbsInputFieldViewController>>();

    static{
        Log.d("formDroid", "registering internal components");
        registerField(LabelField.class);
        Log.d("formDroid", "end registering internal components");
    }

    private InputFieldFactory(){}

    static void registerField(final Class<? extends AbsInputField> fieldClass) throws InputFieldTypeMismatchException {
        //first check if this is a inputfield type
        if(!AbsInputField.class.isAssignableFrom(fieldClass)){
            //throw out exception
            throw new InputFieldTypeMismatchException();
        }
        Annotation annotation = fieldClass.getAnnotation(InputField.class);
        InputField fieldAnnotation = (InputField)annotation;
        String jsonKey = fieldAnnotation.JsonKey();
        if(StringUtils.isEmptyOrWhiteSpace((jsonKey))){
            //by default json key is class name without field in lower case
            String className = fieldClass.getSimpleName();
            if(className.toLowerCase().endsWith("field")){
                jsonKey = StringUtils.removeLast(className, "field");
            }
        }
        //we replace the old one if it exists
        if(_inputFieldMap.containsKey(jsonKey)){
           _inputFieldMap.remove(jsonKey);
        }
        _inputFieldMap.put(jsonKey, fieldClass);
    }

    public static void registerFields(final List<Class<? extends AbsInputField>> fields){
        if(fields == null){
            return;
        }

        for(Class<? extends AbsInputField> field : fields){
           registerField(field);
        }
    }

    static void registerReplaceViewController(final Class<? extends AbsInputFieldViewController> replaceViewController){
       //get the name of the super class
       //note that custom view controller should be direct child of original view controller.
       String viewControllerName = replaceViewController.getSuperclass().getName();
       if(!StringUtils.isEmpty(viewControllerName)) {
           if (_fieldReplaceViewControllerMap.containsKey(viewControllerName)) {
               _fieldReplaceViewControllerMap.remove(viewControllerName);
           }
           //if there is any custom viewcontroller we set it here and return it if necessary
           _fieldReplaceViewControllerMap.put(viewControllerName, replaceViewController);
       }
    }

    public static void registerReplaceViewControllers(final List<Class<? extends AbsInputFieldViewController>> replaceVCtrls){
        if(replaceVCtrls == null){
            return;
        }

        for(Class<? extends AbsInputFieldViewController> replaceVCtrl : replaceVCtrls){
            registerReplaceViewController(replaceVCtrl);
        }
    }

    //static block will be executed when this is called.
    public static void init(){
        Log.d("formDroid", "inputfactory init");
    }
}
