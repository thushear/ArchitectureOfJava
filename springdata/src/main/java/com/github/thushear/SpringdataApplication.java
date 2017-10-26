package com.github.thushear;

import com.github.thushear.data.domain.Customer;
import com.github.thushear.data.domain.IdCard;
import com.github.thushear.data.repo.CustomerRepository;
import com.github.thushear.data.repo.IdCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringdataApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringdataApplication.class, args);

	}


	@Bean
	public CommandLineRunner testRepo(CustomerRepository customerRepository,IdCardRepository idCardRepository){
		return (args) -> {
			Customer customer = new Customer("li","lei");
			customerRepository.save(customer);
			System.out.println(customerRepository.findAll());
			System.out.println(customerRepository.findByLastName("li"));

			IdCard idCard = new IdCard("c111",1l);
			idCardRepository.save(idCard);
			System.out.println(idCardRepository.findAll());
			System.out.println(customerRepository.findByCID(1l));

		};
	}


}
