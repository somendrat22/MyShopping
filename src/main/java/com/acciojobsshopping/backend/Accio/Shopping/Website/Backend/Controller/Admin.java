package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Controller;


import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.AddProductDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.GeneralMessageDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.UnAuthorized;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.UserNotFoundException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.WrongAccessException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service.ProductService;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Admin Service Endpoints", description = "This controller contains all the endpoints that Admin Can use.")
@RestController

@RequestMapping("/api/admin")
public class Admin {

    @Autowired
    ProductService productService;

    @Autowired
    UsersService usersService;


    @Operation(
            summary = "Add Product to products table.",
            description = "This endpoint can let admin level user to product to the Products Table",
            tags = { "tutorials", "post" })

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product successfully got added in to the db", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Admin username does not exist", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "User is not authorized to use this endpoint",content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "application/json")} )
    })

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
    public ResponseEntity getAllProducts(@Parameter(description = "Username is required such that authorization can be done.", required = true) @RequestParam String userName){
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
