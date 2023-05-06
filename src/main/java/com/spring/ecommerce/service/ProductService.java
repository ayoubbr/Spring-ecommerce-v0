package com.spring.ecommerce.service;

import com.spring.ecommerce.dao.ProductDao;
import com.spring.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        if (searchKey.equals("")) {
            return productDao.findAll(pageable);
        } else {
            return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable
            );
        }
    }

    public Product getProductDetailsById(Integer productId) {
        return productDao.findById(productId).get();
    }

    public void deleteProductDetails(Integer productId) {
        productDao.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
        if (isSingleProductCheckout) {
            // buy a single product
            List<Product> productList = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            productList.add(product);
            return productList;
        } else {
            // checkout entire cart
        }
        return new ArrayList<>();
    }
}
