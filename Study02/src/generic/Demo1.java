package generic;

import org.junit.Test;

import javax.xml.bind.annotation.XmlAnyAttribute;
import java.beans.Transient;
import java.util.*;

/**
 * @Author Treee
 * @E-mail ss673290035ss@gmail.com
 * @Date -2017/12/5-
 */
//JDK5以前对象保存到集合中就会失去特性，取出通常要强制转换
//泛型允许程序员在编写集合代码时，就限制集合的处理类型，
// 将原来程序运行是可能发生的问题转变为编译时的问题，
// 形如 List<String> list 再 String s = list.get(0),
// 避免了强制类型转换

public class Demo1 {

    @Test
    public void test1(){
        List<String> list = new ArrayList<String>();
        list.add("aaaa");
        list.add("bbbb");

        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String value = it.next();
            System.out.println(value);
        }
        //增强for,同样的效果
        for(String s : list){
            System.out.println(s);
        }
    }

    @Test
    public void test2(){
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        //如果上行使用hashmap，并不是按照顺序存储的
        // 那么最终输出的键值对也不是按照123顺序的
        //使用linked
        map.put(1,"aa");
        map.put(2,"bb");
        map.put(3,"cc");

        //entryset
        Set<Map.Entry<Integer, String>> set = map.entrySet();
        Iterator<Map.Entry<Integer, String>> it = set.iterator();
        while(it.hasNext()){
            Map.Entry<Integer,String> entry = it.next();
            int key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key +": " + value);
        }

        //最好用增强for循环取
        //但普通的不能对map进行迭代，所以要调用map的方法转为set
        //再针对set迭代
        for(Map.Entry<Integer, String> entry :map.entrySet()){
            int key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key+": "+value);
        }
    }
}
