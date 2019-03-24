package com.hncy.service.review.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.hncy.service.review.model.Review;
import com.hncy.service.review.dao.ReviewRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

@RestController
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @RequestMapping(value = "/review")
    public List<Review> getAllReviews() {
        logger.info("review service is called.");
        return reviewRepository.findAll();
    }

    @RequestMapping(value = "/review/search/findByProduct")
    public List<Review> getReviewsByProduct(@RequestParam("product") int product) {
        logger.info("review service is called.");
        return reviewRepository.findByProduct(product);
    }
}