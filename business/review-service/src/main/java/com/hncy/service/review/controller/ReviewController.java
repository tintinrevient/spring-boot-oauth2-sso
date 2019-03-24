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
import java.util.ArrayList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

@RestController
@Component
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @RequestMapping(value = "/review")
    @HystrixCommand(fallbackMethod = "getDefaultReviews")
    public List<Review> getAllReviews() {
        logger.info("review service is called.");
        return reviewRepository.findAll();
    }

    @RequestMapping(value = "/review/search/findByProduct")
    @HystrixCommand(fallbackMethod = "getDefaultReviews")
    public List<Review> getReviewsByProduct(@RequestParam("product") int product) {
        logger.info("review service is called.");
        return reviewRepository.findByProduct(product);
    }

    public List<Review> getDefaultReviews() {
        logger.info("fallback review service is called.");
        return new ArrayList<Review>();
    }

    public List<Review> getDefaultReviews(int product) {
        logger.info("fallback review service is called.");
        return new ArrayList<Review>();
    }
}