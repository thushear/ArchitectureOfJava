package com.github.thushear.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kongming on 2016/8/24.
 */
public class BeanUtil {

    public static Map<String,Object> transBean2Map(Object obj){
        if (obj == null) {
            return null;
        }
        Map<String,Object> map = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String key = propertyDescriptor.getName();

                if (!"class".equals(key)){

                    Method getter = propertyDescriptor.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key,value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}

class BeanTest{

    private long id;

    private String name;

    private Date createdDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

class BeanUtilTest{



    public static void main(String[] args) {
        BeanTest beanTest = new BeanTest();
        beanTest.setId(1l);
        beanTest.setName("thushear");
        beanTest.setCreatedDate(new Date());
        Map<String,Object> map = BeanUtil.transBean2Map(beanTest);
        System.out.println(map);

    }

}