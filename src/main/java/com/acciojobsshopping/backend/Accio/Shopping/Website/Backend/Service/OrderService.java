package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.PlaceOrderDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.BillDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Orders;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Users;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.OrderNotFoundException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.UserNotFoundException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.WrongAccessException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.WrongPrefrenceException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Repository.OrderRepository;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderService {


    @Autowired
    UsersService usersService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MailService mailService;

    public BillDTO placeOrder(PlaceOrderDTO placeOrderDTO){

        List<Integer> productIds = placeOrderDTO.getProducts();

        String userName = placeOrderDTO.getUserName();

        Users user = usersService.findUserByName(userName);

        if(user == null){
            throw new UserNotFoundException("User does not exist in system");
        }

        List<Product> products = new ArrayList<>();

        for(int pid : productIds){
            products.add(productService.getProductById(pid));
        }

        Orders obj = new Orders();

        Date date = new Date();

        date = DateUtils.addDays(date, 7);

        obj.setEstimatedDelivery(date);

        obj.setTotalOrderItems(products.size());

        int totalPrice = 0;

        for(Product p  : products){
            totalPrice += p.getPrice();
        }
        obj.setTotalOrderPrice(totalPrice);

        obj.setUsers(user);

        orderRepository.save(obj);

        for(Product p : products){
            orderRepository.insertOrderVsProduct(obj.getId(), p.getPid());
        }

        mailService.sendOrderCreatedMail(user, obj);

        System.out.println(obj);



        BillDTO bill = new BillDTO();
        bill.setProducts(products);
        bill.setTotalItems(productIds.size());
        bill.setTotalBill(totalPrice);

        return bill;

    }


    public List<Orders> getAllOrdersByUserId(int uid){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException("User does not exist in the system");
        }
        return orderRepository.getAllOrdersByUserId(uid);
    }

    public List<Orders> getAllOrdersByDeliveryPrefrence(int uid, String deliveryPrefrence){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException("User does not exist in the system");
        }
        if(deliveryPrefrence.equals("Delivered")){
            return orderRepository.getAllDeliveredOrdersByUserId(uid);
        }else if(deliveryPrefrence.equals("Not_Delivered")){
            return orderRepository.getAllNonDeliveredOrdersByUserId(uid);
        }else{
            throw new WrongPrefrenceException("Delivery Prefrence is not correct");
        }
    }

    public void cancelOrder(int uid, int oid){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException(String.format("User with user id %d does not exist in the system.", uid));
        }
        if(orderRepository.findById(oid).orElse(null) == null){
            throw new OrderNotFoundException(String.format("Order with order id %d does not exist in the system.", oid));
        }

        Orders ord = orderRepository.getOrderByOrderIdAndUserId(uid, oid); // ord = null

        Users user = usersService.getUserById(uid);
        if(usersService.isAdmin(user.getUserName())){
            ord = orderRepository.findById(oid).orElse(null);
        }
        if(ord == null){
            throw new WrongAccessException(String.format("User with userId %d does not have access to cancel order with orderId %d .", uid, oid));
        }

        List<Product> products = ord.getOrderItems();

        for(Product p : products){
            int pid = p.getPid();
            orderRepository.deleteOrderVsProduct(oid, pid);
        }
        orderRepository.deleteById(oid);
    }
}
