package com.chen.formdroid.exceptions;

/**
 * Created by chen on 3/28/15.
 */
public class InputFieldNotRegisteredException extends RuntimeException{
   public InputFieldNotRegisteredException(){
        super("Input field not registered, make sure call register in field constructor");
   }
}
