package com.spring.ecommerce.service;

import com.spring.ecommerce.configuration.JwtRequestFilter;
import com.spring.ecommerce.dao.CartDao;
import com.spring.ecommerce.dao.OrderDetailDao;
import com.spring.ecommerce.dao.ProductDao;
import com.spring.ecommerce.dao.UserDao;
import com.spring.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    private static final String ORDER_PLACED = "Placed";
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CartDao cartDao;

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o : productQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();
            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(orderInput.getFullName(),
                    orderInput.getFullAddress(), orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(), ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user);
            //empty the cart
            if (!isSingleProductCheckout) {
                List<Cart> carts = cartDao.findByUser(user);
                carts.stream().forEach(x -> cartDao.delete(x));
            }
            orderDetailDao.save(orderDetail);
        }
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();
        return orderDetailDao.findByUser(user);
    }

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (status.equals("all")) {
            orderDetailDao.findAll().forEach(x -> orderDetails.add(x));
        } else {
            orderDetailDao.findByOrderStatus(status).forEach(x -> orderDetails.add(x));
        }
        return orderDetails;
    }

    public void makeOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();
        if (orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }
    }
}
