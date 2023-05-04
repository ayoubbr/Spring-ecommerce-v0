package com.spring.ecommerce.dao;

import com.spring.ecommerce.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
}
