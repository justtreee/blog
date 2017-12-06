package reflect;
//反射：加载类，解剖类
public class Demo {
    public static void main(String[] args) throws ClassNotFoundException{

        //反射的三种加载方法
        Class c1 = Class.forName("reflect.Person");
        //相当于将硬盘上的某个类的字节码加载到内存中，并封装。
        Class c2 = new Person().getClass();

        Class c3 = Person.class;

    }
}
