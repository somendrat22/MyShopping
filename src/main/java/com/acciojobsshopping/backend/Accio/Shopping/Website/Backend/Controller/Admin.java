package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Controller;


import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.AddProductDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.GeneralMessageDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.UnAuthorized;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.UserNotFoundException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.WrongAccessException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service.ProductService;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class Admin {

    @Autowired
    ProductService productService;

    @Autowired
    UsersService usersService;
    @PostMapping("/product/add")
    public ResponseEntity addProduct(@RequestBody AddProductDTO addProductDTO){
        try{
            productService.addProduct(addProductDTO);
            return new ResponseEntity(new GeneralMessageDTO("Product Success full got added"), HttpStatus.CREATED);
        }catch(WrongAccessException wrongAccessException){
            return new ResponseEntity(new UnAuthorized(wrongAccessException.getMessage()), HttpStatus.UNAUTHORIZED);
        }catch(UserNotFoundException userNotFoundException){
            return new ResponseEntity(new GeneralMessageDTO(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/product")
    public ResponseEntity getAllProducts(@RequestParam String userName){
        try{
            List<Product> list  = productService.getAllProducts(userName);
            return new ResponseEntity(list, HttpStatus.OK);
        }catch(WrongAccessException wrongAccessException){
            return new ResponseEntity(new UnAuthorized(wrongAccessException.getMessage()), HttpStatus.UNAUTHORIZED);
        }catch(UserNotFoundException userNotFoundException){
            return new ResponseEntity(new GeneralMessageDTO(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/approve")
    public ResponseEntity approveAdmin(@RequestParam String userName, @RequestParam int uid){
        try{
            usersService.approveAdmin(userName, uid);
            return new ResponseEntity(new GeneralMessageDTO(String.format("Admin with the UserID %d got approved", uid)), HttpStatus.OK);
        }catch(WrongAccessException wrongAccessException){
            return new ResponseEntity(new UnAuthorized(wrongAccessException.getMessage()), HttpStatus.UNAUTHORIZED);
        }catch(UserNotFoundException userNotFoundException){
            return new ResponseEntity(new GeneralMessageDTO(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }



}
