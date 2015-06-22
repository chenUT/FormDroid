package com.chen.formdroid.utils;

/**
 * Created by chen on 3/28/15.
 */
public class StringUtils {
   private StringUtils(){}

   public static boolean isEmptyOrWhiteSpace(String str){
       return str == null || isEmpty(str.trim());
   }

   public static boolean isEmpty(CharSequence str){
       if(str != null && str.length() > 0) {
           return false;
       }
       return true;
   }

    public static String removeLast(String string, String last) {
        int lastIndex = string.lastIndexOf(last);
        if (lastIndex < 0) return string;
        return string.substring(0, lastIndex);
    }

    public static String lowerFirstChar(String string){
        if(isEmpty(string)) return string;
        Character tmpChar = string.charAt(0);
        String tmpString = string.substring(1);
        String tmpCharStr = tmpChar.toString().toLowerCase();
        return tmpCharStr + tmpString;
    }
}
