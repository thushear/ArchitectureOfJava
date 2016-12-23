package org.thushear.tut.nashorn;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.Arrays;

/**
 * Created by kongming on 2016/3/14
 * Calling java methods from javascript with nashorn.
 */
public class Nashorn2 {


    public static String fun1(String name){
        System.out.format("hello from %s",name);
        return "greeting from java";
    }


    public static String fun2(Object o){
        System.out.println(o.getClass());
        return "type :" + o.getClass();
    }

    public static void fun3(ScriptObjectMirror mirror){
        System.out.println(mirror.getClassName() + ":" + Arrays.toString(mirror.getOwnKeys(true)));
    }


    public static void fun4(ScriptObjectMirror person){
        System.out.println("Full name is : " + person.callMember("getFullName"));
    }



    public static void main(String[] args) throws Exception {

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        engine.eval(new FileReader("res/nashorn2.js"));

    }

}
