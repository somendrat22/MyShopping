package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddUserDTO {

    String userName;

    String password;

    String email;

    Long phoneNumber;

    String address;

    Role role;


}
