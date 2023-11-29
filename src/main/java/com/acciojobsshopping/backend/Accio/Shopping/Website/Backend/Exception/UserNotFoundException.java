package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String mssg){
        super(mssg);
    }
}
