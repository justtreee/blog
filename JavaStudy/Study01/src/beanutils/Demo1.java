package beanutils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Treee
 * @E-mail ss673290035ss@gmail.com
 * @Date -2017/12/4-
 */

//为了简化bean的操作，apache开发了beanutils代替自带的库
//使用beanutils操作bean属性（第一次操作第三方框架）
//注意要导入beanutils和logging
public class Demo1 {
    @Test
    public void test1() throws InvocationTargetException, IllegalAccessException {
        Person p = new Person();
        BeanUtils.setProperty(p, "name", "XCC");
        System.out.println(p.getName());
    }

    //假设一个场景：网页上用户提交了一个表单
    @Test
    public void test2() throws InvocationTargetException, IllegalAccessException {
        String name = "aaa";
        String pwd = "123";
        String age = "34";
        //将表单获得的字符串封装到一个对象中
        Person p = new Person();
        BeanUtils.setProperty(p, "name", name);
        BeanUtils.setProperty(p, "pwd", pwd);
        BeanUtils.setProperty(p, "age", age);
        //不过这个默认转化只支持八种基本数据类型
        System.out.println(p.getName());
        System.out.println(p.getPwd());
        System.out.println(p.getAge());

    }
    //自已定义转换：以字符串转日期为例
    //给Beanutils注册一个日期转换器
    @Test
    public void test3() throws InvocationTargetException, IllegalAccessException {
        String name = "aaa";
        String pwd = "123";
        String age = "34";
        String birthday = "1980-09-09";

        //给Beanutils注册一个日期转换器
        ConvertUtils.register(new Converter() {
            @Override
            public <T> T convert(Class<T> aClass, Object o) {
                if (o == null) {
                    return null;
                }
                if (!(o instanceof String)) {
                    throw new ConversionException("String Only!");
                    //抛出异常提示上一层
                }
                String str = (String) o;
                if (str.trim().equals("")) {
                    return null;//提高健壮性。如果输入为空
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return (T) df.parse(str);
                } catch (ParseException e) {
                    throw new RuntimeException(e);  //抛出异常
                }
            }
        }, Date.class);
        //将表单获得的字符串封装到一个对象中
        Person p = new Person();
        BeanUtils.setProperty(p, "name", name);
        BeanUtils.setProperty(p, "pwd", pwd);
        BeanUtils.setProperty(p, "age", age);
        BeanUtils.setProperty(p, "birthday", birthday);
        System.out.println(p.getName());
        System.out.println(p.getPwd());
        System.out.println(p.getAge());
        System.out.println(p.getBirthday());

        //Tue Sep 09 00:00:00 CST 1980
    }

    //当然beanutils其实自带了转换器
    @Test
    public void test4() throws InvocationTargetException, IllegalAccessException {
        String name = "aaa";
        String pwd = "123";
        String age = "34";
        String birthday = "1980-09-09";//1980-09-09

        ConvertUtils.register(new DateLocaleConverter(), Date.class);
        //但这个是有bug的，没有检测空输入的异常情况

        //将表单获得的字符串封装到一个对象中
        Person p = new Person();
        BeanUtils.setProperty(p, "name", name);
        BeanUtils.setProperty(p, "pwd", pwd);
        BeanUtils.setProperty(p, "age", age);
        BeanUtils.setProperty(p, "birthday", birthday);
        System.out.println(p.getName());
        System.out.println(p.getPwd());
        System.out.println(p.getAge());
        System.out.println(p.getBirthday());
    }

    //客户端提交的数据通常会用map封装
    @Test
    public void test5() throws InvocationTargetException, IllegalAccessException {
        //request
        Map map = new HashMap();
        map.put("name", "aaa");
        map.put("pwd", "123");
        map.put("age", "30");
        map.put("birthday", "1980-10-10");
        ConvertUtils.register(new DateLocaleConverter(), Date.class);

        Person p = new Person();
        BeanUtils.populate(p, map); //用map集合中的值填充bean的属性

        System.out.println(p.getName());
        System.out.println(p.getPwd());
        System.out.println(p.getAge());
        System.out.println(p.getBirthday());
    }
}
