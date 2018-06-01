package com.github.thushear.utils;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**

 */
public class ToStringTest {

    public static void main(String[] args) {

        Record record = new Record();
        record.setId(1);record.setName("thushear");


    }
}
@Data
class Record {

    Integer id;

    String name;

}

