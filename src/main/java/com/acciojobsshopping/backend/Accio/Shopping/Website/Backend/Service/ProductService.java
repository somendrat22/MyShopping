package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.DTO.RequestDTO.AddProductDTO;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Exception.WrongAccessException;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    @Autowired
    ProductRepository productRepository;



    @Autowired
    UsersService usersService;

    public void addProduct(AddProductDTO addProductDTO){
        if(usersService.isAdmin(addProductDTO.getUserName())){
            Product product = new Product();
            product.setProductName(addProductDTO.getProductName());
            product.setCategory(addProductDTO.getCategory());
            product.setPrice(addProductDTO.getPrice());
            product.setQuantity(addProductDTO.getQuantity());
            product.setDescription(addProductDTO.getDescription());
            productRepository.save(product);
        }else{
            throw  new WrongAccessException("User does not have admin access");
        }
    }


    public List<Product> getAllProducts(String userName){
        if(usersService.isAdmin(userName)){

            List<Product> list  = productRepository.findAll();
            return list;
        }else{
            throw new WrongAccessException("User is not authorized to see these details");
        }
    }

    public Product getProductById(int pid){
        return productRepository.findById(pid).orElse(null);
    }
}
