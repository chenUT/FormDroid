package com.chen.formdroid;

import android.content.Context;

import com.chen.formdroid.listeners.IDataModelListener;
import com.chen.formdroid.listeners.IEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chen on 3/26/15.
 */
public class RootScope {
    private static RootScope _instance;
    private Context _appContext;
    private Map<String, Object> _globalDataMap;
    private List<IEventListener> _eventListeners;
    private Map<String, IDataModelListener> _dataModelListeners;

    private RootScope(){
        //concurrenthashmap to ensure thread safe
        _globalDataMap = new ConcurrentHashMap<String, Object>();
        _eventListeners = new LinkedList<IEventListener>();
        _dataModelListeners = new ConcurrentHashMap<String, IDataModelListener>();
    }

    protected static synchronized RootScope getInstance(){
        if(_instance == null)
            _instance = new RootScope();

        return _instance;
    }

    protected void init(Context context){
        this._appContext = context;
    }

    public Context getContext(){
        return this._appContext;
    }

    public Object getDataModel(String dataModelKey){
        return _globalDataMap.get(dataModelKey);
    }
}
