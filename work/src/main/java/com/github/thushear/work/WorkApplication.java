package com.github.thushear.work;

import com.github.thushear.work.config.HystrixConfiguration;
import com.github.thushear.work.service.HystrixService;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;


@Import({HystrixConfiguration.class})
@SpringBootApplication
public class WorkApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(WorkApplication.class, args);
		HystrixService hystrixService = applicationContext.getBean(HystrixService.class);
		System.err.println(hystrixService.getHystrix());


	}
}
