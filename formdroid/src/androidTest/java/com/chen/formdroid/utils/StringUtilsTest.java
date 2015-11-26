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
            isEmpty = StringUtils.isEmptyOrWhiteSpace("");
            Assert.assertTrue(isEmpty);
            isEmpty = StringUtils.isEmptyOrWhiteSpace(null);
            Assert.assertTrue(isEmpty);
            isEmpty = StringUtils.isEmptyOrWhiteSpace("  ");
            Assert.assertTrue(isEmpty);
        }
        catch (Exception e){
            Log.d("formDroidTest", e.getStackTrace().toString());
        }
    }

    public void testIsEmpty() throws Exception {
        try {
            boolean isEmpty = StringUtils.isEmpty("a");
            Assert.assertFalse(isEmpty);
            isEmpty = StringUtils.isEmpty("");
            Assert.assertTrue(isEmpty);
            isEmpty = StringUtils.isEmpty(null);
            Assert.assertTrue(isEmpty);
            isEmpty = StringUtils.isEmpty("  ");
            Assert.assertFalse(isEmpty);
        }
        catch (Exception e){
            Log.d("formDroidTest", e.getStackTrace().toString());
        }
    }

    public void testRemoveLast() throws Exception {
        String str ="testtest";
        String result = StringUtils.removeLast(str, "test");
        Assert.assertEquals("test", result);

        str ="testtest";
        result = StringUtils.removeLast(str, "abc");
        Assert.assertEquals("testtest", result);
    }

    public void testLowerFirstChar() throws Exception {
        String in = "Abc";
        String result = StringUtils.lowerFirstChar(in);
        Assert.assertEquals("abc", result);

        in = "abc";
        result = StringUtils.lowerFirstChar(in);
        Assert.assertEquals("abc", result);
    }
}
