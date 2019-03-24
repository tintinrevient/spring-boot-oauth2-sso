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
import java.util.ArrayList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

@RestController
@Component
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/product")
    @HystrixCommand(fallbackMethod = "getDefaultProducts")
    public List<Product> getAllProducts() {
        logger.info("product service is called.");
        return productRepository.findAll();
    }

    @RequestMapping(value = "/product/{id}")
    @HystrixCommand(fallbackMethod = "getDefaultProducts")
    public List<Product> findById(@PathVariable("id") int id) {
        logger.info("product service is called.");
        return productRepository.findById(id);
    }

    public List<Product> getDefaultProducts() {
        logger.info("fallback product service is called.");
        return new ArrayList<Product>();
    }

    public List<Product> getDefaultProducts(int id) {
        logger.info("fallback product service is called.");
        return new ArrayList<Product>();
    }
}