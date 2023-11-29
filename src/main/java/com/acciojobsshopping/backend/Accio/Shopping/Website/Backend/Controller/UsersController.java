package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Controller;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.AddUserDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.LoginRequestDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.PlaceOrderDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.BillDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.GeneralMessageDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.LoginResponseDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.ShowCartDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Orders;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Users;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.*;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service.CartService;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service.MailService;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service.OrderService;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Service Endpoints", description = "This controller contains all the endpoints that a normal user can use.")

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    MailService mailService;

    @PostMapping("/api/signup")
    public ResponseEntity signUp(@RequestBody AddUserDTO addUserDTO){
        try{
            Users user = usersService.signUp(addUserDTO);
            return new ResponseEntity(user, HttpStatus.CREATED);
        }catch(AdminNotAvailableException adminNotAvailableException){
            return new ResponseEntity(new GeneralMessageDTO(adminNotAvailableException.getMessage()), HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/api/login")
    public ResponseEntity logIn(@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            LoginResponseDTO loginResponseDTO = usersService.logIn(loginRequestDTO);
            return new ResponseEntity(loginResponseDTO, HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(WrongCredentialsException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/api/user/{uid}")
    public ResponseEntity addProductToCart(@PathVariable int uid, @RequestParam int pid){
        try{
            cartService.addProductInCart(pid, uid);
            return new ResponseEntity(new GeneralMessageDTO(String.format("Product with pid %d for user with uid %d got successfully added in the cart", uid, pid)), HttpStatus.CREATED);
        }catch(ProductNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch(UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/api/user/showcart")
    public ResponseEntity getAllProductsFromCart(@RequestParam int uid){
        try{
            ShowCartDTO showCartDTO = cartService.showUserCart(uid);
            return new ResponseEntity(showCartDTO, HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/user/cart/removeitem")
    public ResponseEntity removeItemFromUserCart(@RequestParam int uid, @RequestParam int pid){
        try{
            cartService.removeProductFromUserCart(uid, pid);
            return new ResponseEntity(new GeneralMessageDTO(String.format("Product with pid %d got successfully removed from cart", pid)), HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/api/user/placeorder")
    public ResponseEntity placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        try{
            BillDTO bill = orderService.placeOrder(placeOrderDTO);
            return new ResponseEntity(bill, HttpStatus.CREATED);
        }catch(UserNotFoundException userNotFoundException){
            return new ResponseEntity(new GeneralMessageDTO(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/api/order/{uid}/allorders")
    public ResponseEntity getAllOrders(@PathVariable int uid){
        try{
            List<Orders> orders = orderService.getAllOrdersByUserId(uid);
            return new ResponseEntity(orders, HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/api/order/{uid}/allorders/prefrence")
    public ResponseEntity getAllOrdersByPrefrence(@PathVariable int uid, @RequestParam String deliveryPrefrence){
        try{
            List<Orders> allOrders = orderService.getAllOrdersByDeliveryPrefrence(uid, deliveryPrefrence);
            return new ResponseEntity(allOrders, HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (WrongPrefrenceException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/orders/{uid}/cancelorder/{oid}")
    public ResponseEntity deleteOrderByOrderIdAndUserId(@PathVariable int uid, @PathVariable int oid){
        try{
            orderService.cancelOrder(uid, oid);
            return new ResponseEntity(new GeneralMessageDTO(String.format("Order with orderId %d got cancelled for user with userId %d", oid, uid)), HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch(OrderNotFoundException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch(WrongAccessException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


}
