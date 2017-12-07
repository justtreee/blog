package generic;

/**
 * @Author Treee
 * @E-mail ss673290035ss@gmail.com
 * @Date -2017/12/6-
 */
// 自定义带泛型的方法

public class Demo2 {
    public void testa(){
        a("aaa");
    }

    public <T> T a(T t){
        //调用时就确定类具体的类型，避免了强转
        return null;
    }

    public <T,E,K> void b(T t, E e, K k){

    }


}
