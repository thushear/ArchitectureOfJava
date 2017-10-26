package com.github.thushear.data.repo;

import com.github.thushear.data.domain.Customer;
import com.github.thushear.data.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by kongming on 2017/10/26.
 */

public interface CustomerRepository extends JpaRepository<Customer,Long> {


    List<Customer> findByLastName(String lastName);


    CustomerDTO findByCID(@Param("cId") Long cId);

}
