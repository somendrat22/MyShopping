package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowCartDTO {
    List<Product> products;
    int totalPrice;
    int totalItems;
}
