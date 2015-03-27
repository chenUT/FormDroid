package com.chen.formdroid;

import com.chen.formdroid.iListeners.IDataModelListener;
import com.chen.formdroid.iListeners.IEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chen on 3/26/15.
 */
public class FormContext {
    private FormContext(){}

    //cache to store reflection objects
    public static Map<String, Object> _globalDataMap = new ConcurrentHashMap<String, Object>();
    public static List<IEventListener> _eventListeners= new LinkedList<IEventListener>();
    public static Map<String, IDataModelListener> _dataModelListeners= new ConcurrentHashMap<String, IDataModelListener>();

    public static void init(){

    }
}
