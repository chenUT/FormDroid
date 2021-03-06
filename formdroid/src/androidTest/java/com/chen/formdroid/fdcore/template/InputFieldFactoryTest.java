package com.chen.formdroid.fdcore.template;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.chen.formdroid.fdcore.internal.InputFieldFactory;
import com.chen.formdroid.utils.StringUtils;

/**
 * Created by chen on 3/28/15.
 */
public class InputFieldFactoryTest extends InstrumentationTestCase {
   public void testFactoryInit(){
       Log.d("factory test","start dummy");
       InputFieldFactory.init();
       Log.d("factory test","end dummy");


       String a = "testFieldField";
       int index = a.toLowerCase().lastIndexOf("field");
       Log.d("formDroid", a.substring(0, index).toLowerCase());

       String ab ="abcDef";
       Log.d("formDroid", StringUtils.lowerFirstChar(ab));
   }
}
