package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.AddUserDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.LoginRequestDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.LoginResponseDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.ShowCartDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Cart;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Users;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.AdminNotAvailableException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.UserNotFoundException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.WrongAccessException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.WrongCredentialsException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Repository.UsersRepository;
import jakarta.persistence.TransactionRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;


    public Users findUserByName(String userName){
        return usersRepository.findByUserName(userName);
    }


    public Users signUp(AddUserDTO userDTO){
        List<Object []> adminCount = usersRepository.countOfAdminsAvailable();
        int count = Integer.parseInt(adminCount.get(0)[0].toString());



        String role = userDTO.getRole().toString();


        Users user = new Users();
        user.setUserName(userDTO.getUserName());
        user.setAddress(userDTO.getAddress());
        user.setRole(userDTO.getRole().toString());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAdminApproved(false);

        if(count == 0 && role.equals("Admin")){
            if(userDTO.getPassword().equals("Accio123")){
                user.setAdminApproved(true);
            }
        }

        usersRepository.save(user);
        return user;
    }

    public LoginResponseDTO logIn(LoginRequestDTO loginRequestDTO){
        String userName = loginRequestDTO.getUserName();
        Users user = usersRepository.findByUserName(userName);
        if(user != null){
            if(user.getPassword().equals(loginRequestDTO.getPassword())){
                return new LoginResponseDTO("Success");
            }else{
                throw new WrongCredentialsException("user Password is wrong !!");
            }
        }else{
            throw new UserNotFoundException("Username is wrong !!");
        }
    }

    public Users getUserById(int uid){
        return usersRepository.findById(uid).orElse(null);
    }


    public boolean isAdmin(String userName){
        Users user = usersRepository.findByUserName(userName);
        if(user == null){
            throw new UserNotFoundException("User does not exist");
        }

        if(user.isAdminApproved() == true){
            return true;
        }

        return false;
    }

    public void approveAdmin(String adminUserName, int uid){
        if(isAdmin(adminUserName)){
            try{
                usersRepository.approveAdmin(uid);
            }catch(TransactionRequiredException tr){

            }

        }else{
            throw new WrongAccessException("User does not required permission");
        }
    }




}
