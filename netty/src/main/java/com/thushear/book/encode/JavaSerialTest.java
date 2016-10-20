package com.thushear.book.encode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by kongming on 2016/3/18.
 */

/**
 * java 序列化
 */
public class JavaSerialTest {

    public static void main(String[] args) throws IOException {
        User user = new User();
        user.buildUsername("thushear").buildUserId(1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(baos);
        os.writeObject(user);
        os.flush();
        os.close();

        byte[] b = baos.toByteArray();
        System.out.println("the jdk serial length is : " + b.length);
        System.out.println("============");
        System.out.println("the byte aray serial is " + user.codeC().length);
    }
}
