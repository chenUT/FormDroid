package com.chen.formdroid.fdcore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chen on 3/28/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InputField {
    //by default if no name specified then the json key will be the name of the class in lower case without "Field"
    String type() default "";
}
