package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String mssg){
        super(mssg);
    }
}
