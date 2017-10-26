package com.github.thushear.data.dto;

import lombok.Data;

/**
 * Created by kongming on 2017/10/26.
 */
@Data
public class CustomerDTO {

    private String name;

    private String cardNo;


    public CustomerDTO(String name, String cardNo) {
        this.name = name;
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "name='" + name + '\'' +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }
}
