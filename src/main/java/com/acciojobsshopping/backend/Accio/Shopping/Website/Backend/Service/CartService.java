package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.ResponseDto.ShowCartDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Cart;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.ProductNotFoundException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.UserNotFoundException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {


    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UsersService usersService;

    public void createCart(Cart c){
        cartRepository.save(c);
    }

    public void addProductInCart(int pid, int uid){

        if(productService.getProductById(pid) == null){
            throw new ProductNotFoundException(String.format("Product with pid %d does not exist in our system", pid));
        }

        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException(String.format("User with uid %d does not exist in our system", uid));
        }

        Cart c = cartRepository.getCartByUserId(uid);

        if(c == null){
            c = new Cart();
            c.setUsers(usersService.getUserById(uid));
            createCart(c);
        }
        Product product = productService.getProductById(pid);
        int productPrice = product.getPrice();
        int totalQuantity = c.getTotalItems() + 1;
        cartRepository.updateTotalItems(c.getCId(), totalQuantity);
        cartRepository.updateTotalPrice(c.getCId(), c.getTotalPrice() + productPrice);
        cartRepository.insertProductInCart(c.getCId(), product.getPid());
    }


    public ShowCartDTO showUserCart(int uid){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException("User does not exist in the database");
        }

        Cart c = cartRepository.getCartByUserId(uid);

        List<Object []> productDB = cartRepository.getAllProductsByCartID(c.getCId());

        List<Product> products = new ArrayList<>();

        for(Object [] obj : productDB){
            Product p = productService.getProductById(Integer.parseInt(obj[0].toString()));
            products.add(p);
        }

        ShowCartDTO showCartDTO = new ShowCartDTO();
        showCartDTO.setProducts(products);
        showCartDTO.setTotalItems(c.getTotalItems());
        showCartDTO.setTotalPrice(c.getTotalPrice());
        return showCartDTO;

    }


    public void removeProductFromUserCart(int uid, int pid){
        if(usersService.getUserById(uid) == null){
            throw new UserNotFoundException(String.format("User with uid %d does not exist in system", uid));
        }

        Cart c = cartRepository.getCartByUserId(uid);

        Product p = productService.getProductById(pid);

        cartRepository.updateTotalPrice(c.getCId(), c.getTotalPrice() - p.getPrice());

        cartRepository.updateTotalItems(c.getCId(), c.getTotalItems() - 1);

        cartRepository.removeProductFromUserCart(c.getCId(), pid);

    }
}
