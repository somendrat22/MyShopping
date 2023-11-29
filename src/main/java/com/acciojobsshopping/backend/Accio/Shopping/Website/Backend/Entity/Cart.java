package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "cart")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cId;

    @OneToOne
    Users users;

    int totalPrice;

    int totalItems;

    @OneToMany
    List<Product> products;

}
