package com.chen.formdroid.core.internal;


import com.chen.formdroid.core.annotations.InputField;
import com.chen.formdroid.core.template.fields.dialogfield.models.DialogField;
import com.chen.formdroid.core.template.fields.textfield.models.LabelField;
import com.chen.formdroid.core.template.fields.textfield.models.TextField;
import com.chen.formdroid.exceptions.InputFieldTypeMismatchException;
import com.chen.formdroid.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chen on 3/28/15.
 * Internal Class
 */
public final class InputFieldFactory {
    //cache to store reflection objects
    private static final Map<String, Class<? extends AbsInputField>> _inputFieldMap = new ConcurrentHashMap<>();

    //cache to store possible overridden viewcontroller
    private static final Map<String, Class<? extends AbsInputFieldViewController>> _fieldReplaceViewControllerMap = new ConcurrentHashMap<>();

    static void registerField(final Class<? extends AbsInputField> fieldClass) throws InputFieldTypeMismatchException {
        //first check if this is a inputfield type
        if(!AbsInputField.class.isAssignableFrom(fieldClass)){
            //throw out exception
            throw new InputFieldTypeMismatchException();
        }
        Annotation annotation = fieldClass.getAnnotation(InputField.class);
        InputField fieldAnnotation = (InputField)annotation;
        String fieldTypeKey = fieldAnnotation.type();
        if(StringUtils.isEmptyOrWhiteSpace((fieldTypeKey))){
            //by default json key is class name without field in lower case
            String className = fieldClass.getSimpleName();
            if(className.toLowerCase().endsWith("field")){
                fieldTypeKey = StringUtils.removeLast(className, "field");
            }
        }
        //we replace the old one if it exists
        if(_inputFieldMap.containsKey(fieldTypeKey)){
           _inputFieldMap.remove(fieldTypeKey);
        }
        _inputFieldMap.put(fieldTypeKey, fieldClass);
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

    static Class<? extends AbsInputFieldViewController> getReplaceViewController(String key){
        if(_fieldReplaceViewControllerMap.containsKey(key)){
            return _fieldReplaceViewControllerMap.get(key);
        }
        return null;
    }

    //register internal fields
    static{
        registerField(LabelField.class);
        registerField(TextField.class);
        registerField(DialogField.class);

        //TODO do we register abstract type?
//        registerField(AbsDialogField.class);
//        registerField(AbsCompositeField.class);
    }

    //static block will be executed when this is called.
    public static void init(){
    }

//  fill in the jackson ObjectMapper using the already initialized local _inputFieldMap
    public static void registerMapper(ObjectMapper mapper){
        Iterator iter =  _inputFieldMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry pair = (Map.Entry)iter.next();
            mapper.registerSubtypes(new NamedType((Class<?>)pair.getValue(), ""+pair.getKey()));
        }
    }
}