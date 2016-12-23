package org.thushear.tut.nashorn;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by kongming on 2016/3/14.
 */
public class Nashorn1 {


    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader("res/nashorn.js"));

        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("fun1","peter parker");
        System.out.println(result);
        System.out.println(result.getClass());

        invocable.invokeFunction("fun2",new Date());
        invocable.invokeFunction("fun2", LocalDateTime.now());

    }
}
