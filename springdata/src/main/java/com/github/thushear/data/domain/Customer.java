package com.github.thushear.data.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * Created by kongming on 2017/10/26.
 */
@Entity
@Data
@Slf4j
@NamedQueries( @NamedQuery(name = "Customer.findByCID",query = "select new com.github.thushear.data.dto.CustomerDTO(a.lastName,b.cardNo) from Customer a,IdCard b where a.id=b.cId and a.id=:cId") )
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String lastName;

    private String firstName;


    protected Customer(){}

    public Customer(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public String toString() {

        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
