package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Repository;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Users;
import jakarta.transaction.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {





    public Users findByUserName(String userName);


    @Query(value = "select count(*) from users where is_admin_approved = true", nativeQuery = true)
    public List<Object []> countOfAdminsAvailable();

    @Transactional
    @Modifying
    @Query(value = "update users set is_admin_approved = true where uid =:uid", nativeQuery = true)
    public int approveAdmin(int uid);
}
