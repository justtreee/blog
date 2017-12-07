package generic;

/**
 * @Author Treee
 * @E-mail ss673290035ss@gmail.com
 * @Date -2017/12/6-
 */
// 自定义带泛型的方法
// 但demo2中那样的声明很繁琐，
// 可以直接声明在类上，作用在整个类上
public class Demo3<T,E,K> {
    public void testa(){
        a((T) "aaa");
    }

    public T a(T t){
        //调用时就确定类具体的类型，避免了强转
        return null;
    }

    public void b(T t, E e, K k){

    }

    public static <T> void c(T t){
        //注意静态方法的声明
    }

}
