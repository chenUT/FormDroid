package com.chen.formdroid.exceptions;

/**
 * Created by chen on 3/28/15.
 */
public class InputFieldTypeMismatchException extends RuntimeException{
    public InputFieldTypeMismatchException(){
        super("Input field must be a instanceof AbsInputField");
    }
}
