package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDTO {

    List<Product> products;

    int totalItems;

    int totalBill;
}
