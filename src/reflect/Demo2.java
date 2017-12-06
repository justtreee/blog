package reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

//反射（解剖）类的构造函数,创建类的对象
public class Demo2 {
    //反射构造函数
    @Test
    public void test1() throws Exception {
        Class c1 = Class.forName("reflect.Person");
        Constructor c = c1.getConstructor(null);
        Person p = (Person) c.newInstance(null);

        System.out.println(p.name);
        //以上代码与下面的等价
//        Person p = new Person();
//        System.out.println(p.name);
        //但是上面的代码适合于框架
    }

    @Test
    public void test2() throws Exception {
        Class c2 = Class.forName("reflect.Person");
        Constructor c = c2.getConstructor(String.class);
        Person p = (Person) c.newInstance("BBB");

        System.out.println(p.name);
    }

    @Test
    public void test3() throws Exception{
        Class c3 = Class.forName("reflect.Person");
        Constructor c = c3.getConstructor(String.class, int.class);
        Person p = (Person) c.newInstance("CCC", 88888);

    }

    @Test
    public void test4() throws Exception{
        Class c4 = Class.forName("reflect.Person");
        Constructor c = c4.getDeclaredConstructor(List.class);//获取私有构造函数
        c.setAccessible(true); //暴力反射，打开任何函数的访问权限
        Person p = (Person) c.newInstance(new ArrayList());
        //正常情况下私有的类无法被外部访问，但反射可以做到
        System.out.println(p.name);
    }

    @Test
    public void test5() throws Exception{
        Class c5 = Class.forName("reflect.Person");
        Person p = (Person) c5.newInstance();//反射无参的构造函数，那么也就意味着定义对象的时候要有无参构造函数
        System.out.println(p.name);
        //更简短的反射代码来创建对象
        //等效于test

    }
}
