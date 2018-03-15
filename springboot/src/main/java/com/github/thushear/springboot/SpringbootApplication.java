package com.github.thushear.springboot;

import com.github.thushear.springboot.conf.MapConfProp;
import com.github.thushear.springboot.react.EchoHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.function.server.*;

@SpringBootApplication
@EnableConfigurationProperties({MapConfProp.class})
@EnableAsync
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}


	@Bean
	public RouterFunction<ServerResponse> monoEcho(EchoHandler echoHandler){
		return RouterFunctions.route(RequestPredicates.POST("/echo"),echoHandler::echo);
	}
}
