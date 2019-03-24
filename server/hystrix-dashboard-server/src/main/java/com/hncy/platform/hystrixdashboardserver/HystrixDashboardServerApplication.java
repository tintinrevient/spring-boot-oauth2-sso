package com.hncy.platform.hystrixdashboardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@EnableEurekaClient
@SpringBootApplication
public class HystrixDashboardServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboardServerApplication.class, args);
	}

}
