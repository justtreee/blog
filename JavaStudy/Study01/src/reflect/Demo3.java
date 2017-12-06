package reflect;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

public class Demo3 {
    //reflect :public void f()
    @Test
    public void Test1() throws Exception{
        Person p = new Person();
        Class t = Class.forName("reflect.Person");//加载类
        Method method = t.getMethod("f",null);//反射方法

        method.invoke(p ,null);//这时候第一个参数需要一个对象，因为要调用一个对象的方法，所以在11行new一个对象
    }

    //reflect :public void f(String name, int pwd)
    @Test
    public void Test2() throws Exception{
        Person p = new Person();
        Class t = Class.forName("reflect.Person");
        Method method = t.getMethod("f", String.class, int.class);

        method.invoke(p, "Zhang", 18);
    }

    //reflect :public Class[] f(String name, int[] pwd)
    @Test
    public void Test3() throws Exception{
        Person p = new Person();
        Class t = Class.forName("reflect.Person");
        Method method = t.getMethod("f", String.class, int[].class);

        Class tmp[] = (Class[]) method.invoke(p, "Li", new int[]{1,2,3});//强转出一个可以调用的对象
        System.out.println(tmp[0]);

    }

    //reflect :private void f(InputStream in)
    @Test
    public void Test4() throws Exception{
        Person p = new Person();
        Class t = Class.forName("reflect.Person");
        Method method = t.getDeclaredMethod("f", InputStream.class);//!!!
        method.setAccessible(true);//打开权限
        method.invoke(p, new FileInputStream("C:\\Users\\67329\\Desktop\\新建文本文档.txt"));
    }

    //reflect :public static void f(int num)
    @Test
    public void Test5() throws Exception{
        //Person p = new Person();
        Class t = Class.forName("reflect.Person");
        Method method = t.getMethod("f", int.class);
        method.invoke(null, 23);
        //静态方法可以不需要对象
//        Person p = new Person();
//        method.invoke(p, 23);
        //当然给他对象也没有问题。
    }

    //reflect: public static void main(String[] args)
    @Test
    public void Test6() throws Exception {
        Class t = Class.forName("reflect.Person");
        Method method = t.getMethod("main", String[].class);
        //method.invoke(null, new String[]{"aa","bb"});
        //虽然上一行代码的参数类型看上去没有违反之前的模板，但会报错：错误的参数个数
        //这是因为只需要一个字符串数组参数的main方法接收到了两个参数（分别是aa和bb）
        //其原因是为了兼容jdk1.4的代码
        //以后的jdk为了兼容jdk1.4实现反射的调用，对对象数组的参数调用：
        //1.4:  Method method(String methodName, new Object[]{"aa","bb"} )
        //中的两个参数拆开来，再将参数赋到方法上去: a(String name, String pwd)
        //所以回到main方法中，虽然代码上显示是返回字符串组，但实际上返回了两个拆开的字符串
        //也就是main(String s1,String s2) 也就是参数个数错误

        //#1 让他拆
//        method.invoke(null, new Object[] {new String[]{"aa","bb"}});
        //#2 强转之后不让他拆
        method.invoke(null, (Object) new String[]{"aa","bb"});

//对于所有需要接受数组的方法是，都应该这样小心对待
    }
}
