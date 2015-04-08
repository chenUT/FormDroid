package com.chen.formdroid;

import android.content.Context;

import com.chen.formdroid.core.template.fields.InputFieldFactory;
import com.chen.formdroid.iListeners.IDataModelListener;
import com.chen.formdroid.iListeners.IEventListener;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chen on 3/26/15.
 */
public class FormContext {

    //Global static object mapper used to serialize and deserialize json object
    private final static ObjectMapper _mapper = new ObjectMapper();
    public static ObjectMapper getMapper(){
        return _mapper;
    }

    private FormContext(){}

    public static final List<IEventListener> _eventListeners= new LinkedList<IEventListener>();
    public static final Map<String, IDataModelListener> _dataModelListeners= new ConcurrentHashMap<String, IDataModelListener>();

    //scan annotation to find all input fields
    private static void initGlobalDataMap(){
    }

    private static FormContext _instance;
    private Context _appContext;


    protected static synchronized FormContext getInstance(){
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
     * Note: This must be called after all fields have been initilized
     */
    private static void initMapper(){
        //fill the mapper with registered class types
        InputFieldFactory.registerMapper(_mapper);
    }

    public Context getContext(){
        return this._appContext;
    }

}
