package com.github.thushear.msf.struct;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 协议消息传输 执行体封装
 * Created by kongming on 2016/10/28.
 */
public class Invocation implements Serializable {


    private String className;

    private String methodName;

    private String alias;

    private String[] argsTypes;

    private Class[] argClasses;

    private Object[] args;

    private String interfaceId;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String[] getArgsTypes() {
        return argsTypes;
    }

    public void setArgsTypes(String[] argsTypes) {
        this.argsTypes = argsTypes;
    }

    public Class[] getArgClasses() {
        return argClasses;
    }

    public void setArgClasses(Class[] argClasses) {
        this.argClasses = argClasses;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }


    @Override
    public String toString() {
        return "Invocation{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", alias='" + alias + '\'' +
                ", argsTypes=" + Arrays.toString(argsTypes) +
                ", argClasses=" + Arrays.toString(argClasses) +
                ", args=" + Arrays.toString(args) +
                ", interfaceId='" + interfaceId + '\'' +
                '}';
    }
}
