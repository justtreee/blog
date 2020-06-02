[toc]

# 一、多线程并发

## synchronized的对象定义为final
【例子】对于一个变量`mCameraSoundForced`，将其作为synchronized的对象，但是没有定义为final，而且在函数1中有改变其值，那么对于函数2和3来讲就有可能sync不同的对象，从而起不到同步作用。

【分析】使用该变量作为锁对象本来就是不正规的，应该为此`private final new Object()`，使用final的同步对象作为synchronized的对象，然后再各个函数内操作改变变量`mCameraSoundForced`

【原理】
概括：【**TODO**】

参考链接
- [final关键词在多线程环境中的使用](https://blog.csdn.net/xiaoxiaoxuanao/article/details/52573859)
- [从Java内存模型理解synchronized、volatile和final关键字](https://blog.csdn.net/fuzhongmin05/article/details/60464835)
- [volatile、synchronized、final原理浅析](https://juejin.im/post/5df74e6c51882512756e8fa1)
- 额外深入: [深入理解 Java 内存模型（六）——final](https://www.infoq.cn/article/java-memory-model-6/)
- 延申: [为什么要指令重排序？](https://juejin.im/post/5b0b56f6f265da0dd6488083)

## 同步锁对象不明确
【例子】使用this对象锁时，this指向的可能是其内部类对象。下面的例子为同一个类，this为三个不同的对象

【分析】这个问题体现的是同步锁的不是一个对象；使用同步锁对象时需要将其声明为final，并确保同步锁的是同一对象

【原理】使用自定义任意对象进行同步锁 不同线程必须为同一对象，否则仍旧是异步运行的。而存在内部类时，使用this会导致指向内部类，使得多个线程之间，一个持有大类，一个持有内部类，并不是同一个锁（同一个对象）

参考链接：
- [synchronized(this/.class/Object),synchronize方法区别](https://www.jianshu.com/p/4c1ed2048985)

## 同步锁范围过大导致死锁

【例子】同步锁范围过大有可能导致两个线程试图以不同的顺序来获得相同的锁，进而死锁
```java
public void funA() {
    synchronized(mA) {
        //...
        funcB();
    }
}

void funcB() {
    synchronized(mB) {
    }
}
```

【分析】在log中（暂无图），会出现
> `thread-1`先拿到 `mA` 的锁，在等待 `mB` ，`thread-2` 可能在别处先拿到 `mB` 的锁，在等待 `mA` 即2个thread死锁

【解决】缩小同步锁范围，不要出现两个锁嵌套
```java
public void funA() {
    synchronized(mA) {
        //...
    }
    funcB();
}

void funcB() {
    synchronized(mB) {
    }
}
```

## 多线程环境下单例模式的规范写法
编写的单例模式不够规范，下面介绍两种规范写法供大家参考

**【写法一】**
- 参考代码 
```java
public class Singleton {
    private static volatile Singleton singleton;
    private Singleton() {}
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton(); // 参考代码第8行
                }
            }
        }
        return singleton;
    }
}

```
- 注意事项（WHY）：
1. **private构造函数**：以确保无法通过该类的构造函数来实例化该类的对象，只能通过该类提供的静态方法getInstance()来得到该类的唯一实例。
2. **private、static 、volatile关键字**：其中volatile的作用是防止重排序
参考代码第8行有可能发生如下重排序。

> 重排序前
```cpp
memory = allocate(); // 1. 分配对象的内存空间
ctorInstance(memory); // 2. 初始化对象
instance = memory； // 3. 设置instance指向刚分配的内存地址
```
> 可能发生重排序后：
```cpp
memory = allocate(); // 1. 分配对象的内存空间
instance = memory； // 3. 设置instance指向刚分配的内存地址
// 注意，此时对象还没有被初始化！
ctorInstance(memory); // 2. 初始化对象
```
比如线程A在参考代码第8行执行了步骤1和步骤3，但步骤2还没有执行完
这时线程B执行到了第5行，判断sInstance不为空，就直接返回了一个未初始化完的sInstance

3. double check提高执行效率：
    - 第1次判空：单例模式只需要创建一次实例，如果后面再次调用getInstance()时，则直接返回之前创建的实例，因此大部分时间不需要执行同步块里的代码，提高了性能  
    - 第2次判空：防止创建多个实例

4. synchronized块：保证线程安全
5. 懒加载：延迟加载，只在getInstance()第一次被调用时才实例化

- 参考链接：
  1. [单例模式-双检锁就稳了？](https://juejin.im/post/5e70d5fbf265da572c54aafb)

【写法二】
- 参考代码：
```java
public class Singleton() {
    private Singleton() {}
    public static Singleton getInstance() {
        return SingletonInstance.INSTANCE;
    }
    private static class SingletonInstance {
        private final static Singleton INSTANCE = new Singleton();
    }
}
```

- 注意事项（WHY）：
1. private构造函数：作用同上
2. 线程安全：静态内部类变量INSTANCE只会在第一次调用getInstance()，加载类SingletonInstance时初始化，保证了线程安全 
3. 懒加载：作用同上  

## 多线程更新list后，获取数据为空
> 关键: ArrayList 并不是线程安全

**【案例】**
代码场景：两个线程，两处代码，一处代码是`Arraylist.add()`，由线程1执行，另一处代码是`ArrayList.get()`，由线程2执行。发生空指针。

**【分析】**
How ？ 线程如何调用才会导致出现箭头处的空指针呢？？

估计是Add操作并不是原子操作，看下JDK的实现：
```java
// java.util.ArrayList#add(E)
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}
```

出现了一个问题， 即操作的时候是先将`size`增加了1，还是先将值赋值进正确的位置？

看下Java字节码是如何执行的

- 首先准备demo代码
```java
public class incre {
	public void test() {
		int[] list = new int[32];
		int i = 8;
		list[i++] = 10; // 主要看自增操作与赋值操作的先后顺序，直觉来说应该是自增
	}
}
```
- 编译并查看
```shell
javac Increment.java
javap -verbose Increment
```

- 得到字节码的执行序列：
```shell
Classfile /D:/My Documents/Desktop/javademo/incre.class
  Last modified 2020年4月22日; size 258 bytes
  MD5 checksum aeecc843a0de5a71613407b83abf58cf
  Compiled from "incre.java"
public class incre
  minor version: 0
  major version: 55
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #2                          // incre
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 2, attributes: 1
Constant pool:
   #1 = Methodref          #3.#11         // java/lang/Object."<init>":()V
   #2 = Class              #12            // incre
   #3 = Class              #13            // java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LineNumberTable
   #8 = Utf8               test
   #9 = Utf8               SourceFile
  #10 = Utf8               incre.java
  #11 = NameAndType        #4:#5          // "<init>":()V
  #12 = Utf8               incre
  #13 = Utf8               java/lang/Object
{
  public incre();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 1: 0

  public void test();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=3, locals=3, args_size=1
         0: bipush        32
         2: newarray       int
         4: astore_1
         5: bipush        8     // 赋值 i = 8
         7: istore_2
         8: aload_1
         9: iload_2
        10: iinc          2, 1  // 自增 i++
        13: bipush        10    // 赋值 = 10
        15: iastore
        16: return
      LineNumberTable:
        line 3: 0
        line 4: 5
        line 5: 8
        line 6: 16
}
SourceFile: "incre.java"
```

测试结论是先增加Size，再放元素进数组。

因此回到本案例，线程1 `add` 元素，执行到将`size++` 后，CPU切成线程2执行，去取list.size(), 此时取到的是线程1 将Size增大1 的Size。但是元素并没有就位。线程2继续get元素，所以get到空了。

**【修复】**
1. 将线程抛至同一个线程操作，使得其能同步处理
2. 将ArrayList修改成线程安全的CopyOnWriteArrayList,保证数据的安全性

**【结论】**
1. 多线程场景下，尽量使用线程安全的对象进行数据操作，如`vector`,`CopyOnWriteArrayList`
2. 多线程场景下，需要考虑一下加锁，线程同步等措施，保证无稳定性的前提下，确保业务数据正确。

**【参考链接】**
- 读多写少的场景，使用基于synchronized修饰的vector效率就不高了，所以需要基于读写分离思想实现的juc容器：[并发容器之CopyOnWriteArrayList](https://juejin.im/post/5aeeb55f5188256715478c21)