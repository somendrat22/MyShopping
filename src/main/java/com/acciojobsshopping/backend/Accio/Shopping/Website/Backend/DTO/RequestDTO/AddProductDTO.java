package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddProductDTO {

    String productName;

    String category;

    String description;

    int quantity;

    int price;

    String userName;



}
