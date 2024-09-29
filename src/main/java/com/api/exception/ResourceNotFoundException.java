package com.api.exception;

/*
    Let us say i give id=1 in url and data does not exist. I get an exception saying no data found
    Instead I want a message saying registration does not exist
    Here we are creating a custom exception named ResourceNotFoundException
     */

//Here ResourceNotFoundException is a child class and RuntimeException parent class
//Super keyword will call method in parent class


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
