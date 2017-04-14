package com.github.thushear;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring/spring-config.xml")
public class SpringmanApplication {

	public static void main(String[] args) {

		SpringApplication springApplication = new SpringApplication(SpringmanApplication.class);
		springApplication.setAdditionalProfiles();
		springApplication.setBannerMode(Banner.Mode.LOG);
		springApplication.run(args);



	}

}
