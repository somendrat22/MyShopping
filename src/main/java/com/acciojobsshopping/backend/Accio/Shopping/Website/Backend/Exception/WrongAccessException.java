package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception;

public class WrongAccessException extends  RuntimeException {

    public WrongAccessException(String mssg){
        super(mssg);
    }
}
