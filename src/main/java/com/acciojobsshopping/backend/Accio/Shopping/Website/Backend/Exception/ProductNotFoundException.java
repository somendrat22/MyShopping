package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String mssg){
        super(mssg);
    }

}
