package com.chen.formdroid;

import android.content.Context;
import android.content.SharedPreferences;

import com.chen.formdroid.core.internal.InputFieldFactory;
import com.chen.formdroid.core.template.form.Form;
import com.chen.formdroid.exceptions.InvalidFormIdException;
import com.chen.formdroid.iListeners.IDataModelListener;
import com.chen.formdroid.iListeners.IEventListener;
import com.chen.formdroid.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chen on 3/26/15.
 */
//TODO move persistance calls to a background intetnservice or thread if network is involved.
public class FormContext {

    private final static String SHARED_PREF_KEY = "com.chen.formdroid.persistance";
    private final static String SHARED_PREF_KEY_FORM_STRING = SHARED_PREF_KEY +".form.string";

    //Global static object mapper used to serialize and deserialize json object
    private final static ObjectMapper _mapper = new ObjectMapper();
    public static ObjectMapper getMapper(){
        return _mapper;
    }

    private FormContext(){}

    public static final List<IEventListener> _eventListeners= new LinkedList<>();
    public static final Map<String, IDataModelListener> _dataModelListeners= new ConcurrentHashMap<>();

    private static final Map<String, Form>  _globalFormCache = new ConcurrentHashMap<>();

    //TODO scan annotation to find all input fields
    private static void initGlobalDataMap(){
    }

    private static FormContext _instance;
    private Context _appContext;

    public static synchronized FormContext getInstance(){
        if(_instance == null)
            _instance = new FormContext();

        return _instance;
    }

    protected void init(Context context){
        this._appContext = context;
        //init the FormContext
        initMapper();
    }

    /**
     * Note: This must be called after all fields have been initialized
     */
    private static void initMapper(){
        //fill the mapper with registered class type
        InputFieldFactory.registerMapper(_mapper);
        _mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        _mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void addToPersistance(String formId, String formJson){
        SharedPreferences pref = this._appContext.getSharedPreferences(SHARED_PREF_KEY_FORM_STRING, Context.MODE_PRIVATE);
        //apply this asynchronously
        pref.edit().putString(formId, formJson).apply();
    }

    public void deleteForm(String formId){
        removeFromCache(formId);
        SharedPreferences pref = this._appContext.getSharedPreferences(SHARED_PREF_KEY_FORM_STRING, Context.MODE_PRIVATE);
        pref.edit().remove(formId).apply();
    }

    public void persisForm(String formId) throws InvalidFormIdException {
        Form result = getFromCache(formId);
        if(result == null){
           throw new InvalidFormIdException("FormId is invalid");
        }
        String formString = result.toJsonString();
        addToPersistance(formId, formString);
    }

    public void addToCache(Form form){
        //we first free the memory of previous form
        if(_globalFormCache.containsKey(form.getFormId())){
            removeFromCache(form.getFormId());
        }
        _globalFormCache.put(form.getFormId(), form);
    }

    public Form getForm(String formId){
        Form result = getFromCache(formId);
        if(result != null){
            return result;
        }

        //try persistance
        result = getFromPersistance(formId);
        return result;
    }

    public void removeFromCache(String formId){
        _globalFormCache.remove(formId);
    }

    /**
     * this will recreate the form from its last update in shared pref
     * @param formId
     * @return
     */
    private Form getFromPersistance(String formId){
        SharedPreferences pref = this._appContext.getSharedPreferences(SHARED_PREF_KEY_FORM_STRING, Context.MODE_PRIVATE);
        String formString = pref.getString(formId, "");
        if(StringUtils.isEmpty(formString)){
            return null;
        }
        try {
            Form form = getMapper().readValue(formString, Form.class);
            return form;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Form getFromCache(String formId){
        if(_globalFormCache.containsKey(formId)) {
            return _globalFormCache.get(formId);
        }
        return null;
    }
}
