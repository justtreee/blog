package introspector;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

//使用内省api操作bean的属性
public class Demo1 {
    //得到bean的所有属性
    @Test
    public void Test1() throws Exception {
        BeanInfo info = Introspector.getBeanInfo(Person.class);
        PropertyDescriptor[] pds = info.getPropertyDescriptors();

        for(PropertyDescriptor pd : pds){
            System.out.println(pd.getName());
//            ab
//            age
//            class
//            name
//            pwd
        }
        //BeanInfo info = Introspector.getBeanInfo(Person.class, Object.class);
        // 如果用这条来获取bean的属性的话，就不会多获取object的class
    }

    //操作bean的指定属性
    @Test
    public void Test2() throws Exception {
//        BeanInfo info = Introspector.getBeanInfo(Person.class);
//        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        //以上可以缩写为：
        PropertyDescriptor pd = new PropertyDescriptor("age", Person.class);
        //得到属性的写方法set并赋值
        Method method = pd.getWriteMethod();
        Person p = new Person();
        method.invoke(p, 44);
        //System.out.println(p.getAge());
        //44
        //改为属性的读方法：
        method = pd.getReadMethod();
        System.out.println(method.invoke(p, null));
        //44
    }

    //获取当前操作的属性的类型
    @Test
    public void Test3() throws Exception {

        Person p = new Person();
        PropertyDescriptor pd = new PropertyDescriptor("age", Person.class);
        System.out.println(pd.getPropertyType());
        //int
    }
}
