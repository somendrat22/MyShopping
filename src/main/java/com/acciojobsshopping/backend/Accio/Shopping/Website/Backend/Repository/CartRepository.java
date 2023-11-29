package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Repository;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Cart;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {



    @Transactional
    @Modifying
    @Query(value = "update cart set total_items =:totalItems where c_id =:cid", nativeQuery = true)
    public void updateTotalItems(int cid, int totalItems);


    @Transactional
    @Modifying
    @Query(value = "update cart set total_price =:totalPrice where c_id =:cid", nativeQuery = true)
    public void updateTotalPrice(int cid, int totalPrice);

    @Query(value = "select * from cart where users_uid =:uid", nativeQuery = true)
    public Cart getCartByUserId(int uid);

    @Transactional
    @Modifying
    @Query(value = "insert into cart_products (cart_c_id, products_pid) values (:cid, :pid)", nativeQuery = true)
    public void insertProductInCart(int cid, int pid);


    @Query(value = "select products_pid from cart_products where cart_c_id =:cid", nativeQuery = true)
    public List<Object []> getAllProductsByCartID(int cid);


    @Transactional
    @Modifying
    @Query(value = "delete from cart_products where cart_c_id =:cid and products_pid =:pid", nativeQuery = true)
    public void removeProductFromUserCart(int cid, int pid);
}
