package com.hncy.service.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hncy.service.review.model.Review;
import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Integer> {
    public List<Review> findAll();
    public List<Review> findByProduct(int product);
}
