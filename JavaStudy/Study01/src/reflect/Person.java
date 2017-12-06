package reflect;

import java.io.InputStream;
import java.util.List;

public class Person {
    public String name = "AAAA";
    private int pwd = 123;
    private static int age = 23;

    public Person()
    {
        System.out.println("Person");
    }
    public Person(String name){
        System.out.println(name);
    }
    public Person(String name, int pwd){
        System.out.println(name + " pwd:" + pwd);
    }
    private Person(List l)
    {
        System.out.println("List");
    }

    //设置被反射的多种方法
    public void f(){
        System.out.println("call f()");
    }
    public void f(String name, int pwd){
        System.out.println(name+": "+pwd);
    }
    public Class[] f(String name, int[] pwd){
        return new Class[]{String.class};
    }
    private void f(InputStream in){
        System.out.println(in);
    }
    public static void f(int num){
        System.out.println(num);
    }


    public static void main(String[] args){
        System.out.println("main");
    }
}
