package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String orderName;

    @ManyToOne
    Users users;

    Date estimatedDelivery;

    int totalOrderPrice;


    @ManyToMany
    @Column(unique = false)
    List<Product> orderItems;

    boolean isDelivered;

    int totalOrderItems;

}
