package reflect;

import org.junit.Test;

import java.lang.reflect.Field;

public class Demo4 {
    //反射字段: public String name = "AAAA";
    @Test
    public void Test1() throws Exception{
        Person p = new Person();
        Class c  = Class.forName("reflect.Person");
        Field f = c.getField("name"); //获取字段
//        String name = (String)f.get(p); //获得p对象的name字段字符串
//        System.out.println(name);
//
//        Class type = f.getType();
//        System.out.println(type);
//        //反射还可以获得字段的类型

        //所以以上的注释内容就有一种更严谨的写法
        Object value = f.get(p); //获得字段的值
        Class type = f.getType(); //获得字段的类型
        if(type.equals(String.class)){
            String svalue = (String) value;
            System.out.println(svalue);
        }


        //设置字段的值
        f.set(p, "sadasdsad");
        System.out.println(p.name);

    }

    //反射字段: private int pwd = 123;
    @Test
    public void Test2() throws Exception{
        Person p = new Person();
        Class c  = Class.forName("reflect.Person");

        Field f = c.getDeclaredField("pwd");
        f.setAccessible(true);

        System.out.println(f.get(p));
    }



    //反射字段: private static int age = 23;
    @Test
    public void Test3() throws Exception{
        Person p = new Person();
        Class c  = Class.forName("reflect.Person");

        Field f = c.getDeclaredField("age");
        f.setAccessible(true);
        System.out.println(f.get(p));
    }
}
