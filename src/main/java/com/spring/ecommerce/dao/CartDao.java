package com.spring.ecommerce.dao;

import com.spring.ecommerce.entity.Cart;
import com.spring.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer> {
    List<Cart> findByUser(User user);
}
