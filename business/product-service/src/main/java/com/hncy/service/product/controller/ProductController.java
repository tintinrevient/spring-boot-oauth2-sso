package com.hncy.service.product.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import com.hncy.service.product.model.Product;
import com.hncy.service.product.dao.ProductRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/product")
    public List<Product> getAllProducts() {
        logger.info("product service is called.");
        return productRepository.findAll();
    }

    @RequestMapping(value = "/product/{id}")
    public List<Product> findById(@PathVariable("id") int id) {
        logger.info("product service is called.");
        return productRepository.findById(id);
    }
}