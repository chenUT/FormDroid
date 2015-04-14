package com.chen.formdroid.utils;

import android.test.InstrumentationTestCase;
import android.util.Log;

import junit.framework.Assert;

/**
 * Created by chen on 3/30/15.
 */
public class StringUtilsTest extends InstrumentationTestCase{
    public void testIsEmptyOrWhiteSpace() {
        try {
            boolean isEmpty = StringUtils.isEmptyOrWhiteSpace("a");
           Assert.assertFalse(isEmpty);
        }
        catch (Exception e){
            Log.d("formDroidTest", e.getStackTrace().toString());
        }
    }

    public void testIsEmpty() throws Exception {

    }

    public void testRemoveLast() throws Exception {

    }

    public void testLowerFirstChar() throws Exception {

    }
}
