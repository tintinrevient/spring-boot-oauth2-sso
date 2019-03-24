package com.hncy.service.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hncy.service.product.model.Product;
import java.util.List;

public interface ProductRepository  extends JpaRepository<Product, Integer> {
    public List<Product> findAll();
    public List<Product> findById(int id);
}
