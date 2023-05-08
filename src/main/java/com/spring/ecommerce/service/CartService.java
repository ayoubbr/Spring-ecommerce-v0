package com.spring.ecommerce.service;

import com.spring.ecommerce.configuration.JwtRequestFilter;
import com.spring.ecommerce.dao.CartDao;
import com.spring.ecommerce.dao.ProductDao;
import com.spring.ecommerce.dao.UserDao;
import com.spring.ecommerce.entity.Cart;
import com.spring.ecommerce.entity.Product;
import com.spring.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    public Cart addToCart(Integer productId) {
        Product product = productDao.findById(productId).get();
        String username = JwtRequestFilter.CURRENT_USER;
        User user = null;
        if (username != null) {
            user = userDao.findById(username).get();
        }
        if (product != null && user != null) {
            Cart cart = new Cart(product, user);
            return cartDao.save(cart);
        }
        return null;
    }

    public List<Cart> getCartDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).get();
        return cartDao.findByUser(user);
    }
}
