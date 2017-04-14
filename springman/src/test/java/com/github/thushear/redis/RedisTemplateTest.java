package com.github.thushear.redis;

import com.github.thushear.BaseTest;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kongming on 2017/4/14.
 */
public class RedisTemplateTest extends BaseTest {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "lettuceRedisTemplate")
    private RedisTemplate lettuceRedisTemplate;


    @Test
    public void testTemplateApi(){
        List<RedisClientInfo> redisClientInfos = redisTemplate.getClientList();
        System.out.println("redisClientInfos = " + redisClientInfos);
        ValueOperations<String,Integer> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("counter",1);
        Integer val = valueOperations.get("counter");
        System.out.println("val = " + val);
    }


    @Test
    public void testStringTemplate(){
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        for (int i = 0; i < 100; i++) {
            valueOperations.set("foo" + i,i + "");
        }

        System.out.println(valueOperations.get("foo1") + ":" + valueOperations.get("foo2"));

        valueOperations.increment("foo1",2000);
        List list = Lists.newArrayList();
        list.add("foo1");list.add("foo2");list.add("foo3");
        List valueList = valueOperations.multiGet(list);
        System.out.println("valueList = " + valueList);


    }



    @Test
    public void testHashTemplate(){
        Person person = new Person("thushear","Kong");
        HashOperations<String,byte[],byte[]> hashOperations = redisTemplate.opsForHash();
        HashMapper<Object,byte[],byte[]> mapper = new ObjectHashMapper();
        Map<byte[],byte[]> hash = mapper.toHash(person);
        hashOperations.putAll("per",hash);

        Map<byte[],byte[]> readMap = hashOperations.entries("per");
        for (Map.Entry<byte[], byte[]> entry : readMap.entrySet()) {
            System.out.println("key:" + new String(entry.getKey(), Charset.forName("UTF-8")));
            System.out.println("key:" + new String(entry.getValue(), Charset.forName("UTF-8")));
        }

        Person person1 = (Person) mapper.fromHash(readMap);
        System.out.println("person1 = " + person1);

    }

    @Test
    public void testHashOperations(){
        Person person = new Person("thushear","Kong");
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        HashMapper<Object,byte[],byte[]> mapper = new ObjectHashMapper();
        Map<byte[],byte[]> mapHash = mapper.toHash(person);
        System.out.println("mapHash = " + mapHash);
//        hashOperations.putAll("person",mapHash);
//
//        Map<String,String> mapHashGet = hashOperations.entries("person");
//        System.out.println("mapHashGet = " + mapHashGet);
    }





}
class Person {
    String firstName;

    String lastName;

    public Person(){}

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}