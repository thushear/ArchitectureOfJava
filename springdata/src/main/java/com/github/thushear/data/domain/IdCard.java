package com.github.thushear.data.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by kongming on 2017/10/26.
 */
@Entity
@Data
public class IdCard {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String cardNo;

    private Long cId;

    protected IdCard(){}

    public IdCard(String cardNo, Long cId) {
        this.cardNo = cardNo;
        this.cId = cId;
    }


    @Override
    public String toString() {
        return "IdCard{" +
                "id=" + id +
                ", cardNo='" + cardNo + '\'' +
                ", cId=" + cId +
                '}';
    }
}
