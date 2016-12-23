package org.thushear.tut;

/**
 * Created by kongming on 2016/3/8.
 */
interface Formula {

    double calcute(int a);

    //Default Methods for Interfaces
    default double sqrt(int a){
        return Math.sqrt(a);
    }
}

class FormulaTest{

    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calcute(int a) {
                return sqrt(a * 100);
            }
        };


        System.out.println(  formula.calcute(4) );
        System.out.println(  formula.sqrt(4));

    }
}