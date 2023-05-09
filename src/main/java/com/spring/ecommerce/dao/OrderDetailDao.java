package com.spring.ecommerce.dao;

import com.spring.ecommerce.entity.OrderDetail;
import com.spring.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
    List<OrderDetail> findByUser(User user);

    List<OrderDetail> findByOrderStatus(String status);
}
