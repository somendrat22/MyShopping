package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Table(name = "users")
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int uid;

    @Column(unique = true)
    String userName;

    String password;

    String role;

    String address;





    @Column(length = 10, unique = true)
    Long phoneNumber;

    @Column(unique = true)
    String email;

    boolean isAdminApproved;



}
