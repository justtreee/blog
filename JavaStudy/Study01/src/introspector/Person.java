package introspector;

public class Person {   //这样一个就是一个javabean
    public String name;
    private String pwd;
    private int age;    //单独的这些仅仅是字段

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //只有设置了get set才能将上面的字段称之为属性


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //一个类的属性由get或set方法决定

    public String getAb(){
        return null;
    }
    //这个时候，这个类里面显示出来了4个属性，但是实际上是5个
    //因为所有的对象都是从object类继承过来的，而object有一个属性getClass
    //这样喝起来就有5个属性在这个Person类中。
}
