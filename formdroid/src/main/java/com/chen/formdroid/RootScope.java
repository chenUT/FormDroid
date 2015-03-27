package com.chen.formdroid;

import android.content.Context;

import com.chen.formdroid.iListeners.IDataModelListener;
import com.chen.formdroid.iListeners.IEventListener;

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


    private RootScope(){
        //concurrenthashmap to ensure thread safe

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
}
