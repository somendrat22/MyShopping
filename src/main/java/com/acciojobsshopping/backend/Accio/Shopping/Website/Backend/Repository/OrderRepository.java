package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Repository;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Orders;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {


    @Transactional
    @Modifying
    @Query(value = "insert into orders_order_items (orders_id, order_items_pid) values (:oid, :pid)", nativeQuery = true)
    public void insertOrderVsProduct(int oid, int pid);


    @Query(value = "select * from orders where users_uid =:uid", nativeQuery = true)
    public List<Orders> getAllOrdersByUserId(int uid);

    @Query(value = "select * from orders where users_uid =:uid and is_delivered = false", nativeQuery = true)
    public List<Orders> getAllNonDeliveredOrdersByUserId(int uid);

    @Query(value = "select * from orders where users_uid =:uid and is_delivered = true", nativeQuery = true)
    public List<Orders> getAllDeliveredOrdersByUserId(int uid);

    @Transactional
    @Modifying
    @Query(value = "delete from orders where id =:oid and users_uid =:uid", nativeQuery = true)
    public void deleteOrderByOrderIdAndUserId(int uid, int oid);

    @Query(value = "select *  from orders where id =:oid and users_uid =:uid", nativeQuery = true)
    public Orders getOrderByOrderIdAndUserId(int uid, int oid);

    @Transactional
    @Modifying
    @Query(value = "delete from orders_order_items where orders_id =:oid and order_items_pid =:pid ", nativeQuery = true)
    public void deleteOrderVsProduct(int oid, int pid);


}
