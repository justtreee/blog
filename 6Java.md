# 六、Java
## 5.1 基本知识
### Java的Integer和int有什么区别
- 最基本的一点区别是：Ingeter是int的包装类，int的初值为0，Ingeter的初值为null。

- 无论如何，Integer与new Integer不会相等。不会经历拆箱过程，new出来的对象存放在堆，而非new的Integer常量则在常量池（在方法区），他们的内存地址不一样，所以为false。
- 两个都是非new出来的Integer，如果数在-128到127之间，则是true,否则为false。因为java在编译Integer i2 = 128的时候,被翻译成：Integer i2 = Integer.valueOf(128);而valueOf()函数会对-128到127之间的数进行缓存。
- 两个都是new出来的,都为false。还是内存地址不一样。
- int和Integer(无论new否)比，都为true，因为会把Integer自动拆箱为int再去比。
### Java中equals和==的区别
- ==可以用来比较基本类型和引用类型，判断内容和内存地址

1. equals只能用来比较引用类型,它只判断内容。该函数存在于老祖宗类 java.lang.Object

java中的数据类型，可分为两类：
1.基本数据类型，也称原始数据类型。byte,short,char,int,long,float,double,boolean
他们之间的比较，应用双等号（==）,比较的是他们的值。
2.复合数据类型(类)
当他们用（==）进行比较的时候，比较的是他们在内存中的存放地址，
所以，除非是同一个new出来的对象，他们的比较后的结果为true，否则比较后结果为false。

- [链接](https://blog.csdn.net/jueblog/article/details/9347791)
### java在静态类中能引用非静态方法吗
- 不能，但main是个例外
- 首先static的成员是在类加载的时候初始化的，JVM的CLASSLOADER的加载，首次主动使用加载，而非static的成员是在创建对象的时候，即new 操作的时候才初始化的；
- 先后顺序是先加载，才能初始化，那么加载的时候初始化static的成员，此时非static的成员还没有被加载必然不能使用，而非static的成员是在类加载之后，通过new操作符创建对象的时候初始化，此时static 已经分配内存空间，所以可以访问！
- 简单点说：静态成员属于类,不需要生成对象就存在了.而非静态需要生成对象才产生.所以静态成员不能直接访问非静态.  
	
[java在静态类中能引用非静态方法吗](https://blog.csdn.net/jiayi_yao/article/details/51346378)

### 面向对象的三个基本特征
- 面向对象的三个基本特征是：封装、继承、多态。  

    ![mianxiangduixiang](http://www.cnitblog.com/images/cnitblog_com/lily/1972/o_OOBase.gif)

- **封装**，也就是把客观事物封装成抽象的类，并且类可以把自己的数据和方法只让可信的类或者对象操作，对不可信的进行信息隐藏。
- **继承**是指这样一种能力：它可以使用现有类的所有功能，并在无需重新编写原来的类的情况下对这些功能进行扩展。  
继承概念的实现方式有三类：实现继承、接口继承和可视继承。
    - 实现继承是指使用基类的属性和方法而无需额外编码的能力；
    - 接口继承是指仅使用属性和方法的名称、但是子类必须提供实现的能力；
    - 可视继承是指子窗体（类）使用基窗体（类）的外观和实现代码的能力。

    在考虑使用继承时，有一点需要注意，那就是两个类之间的关系应该是“属于”关系。例如，Employee 是一个人，Manager 也是一个人，因此这两个类都可以继承 Person 类。但是 Leg 类却不能继承 Person 类，因为腿并不是一个人。

- **多态**，有二种方式，覆盖，重载。  
    - 覆盖，是指子类重新定义父类的虚函数的做法。
    - 重载，是指允许存在多个同名函数，而这些函数的参数表不同（或许参数个数不同，或许参数类型不同，或许两者都不同）。  
重载的实现是：编译器根据函数不同的参数表，对同名函数的名称做修饰，然后这些同名函数就成了不同的函数。对于这两个函数的调用，在编译器间就已经确定了，是静态的。

- 我们知道，**封装**可以隐藏实现细节，使得代码模块化；**继承**可以扩展已存在的代码模块（类）；它们的目的都是为了——代码重用。而 **多态** 则是为了实现另一个目的——接口重用！多态的作用，就是为了类在继承和派生的时候，保证使用“家谱”中任一类的实例的某一属性时的正确调用。

- [Java三大特性封装继承多态总结](https://blog.csdn.net/zjkC050818/article/details/78278658)   
- [面向对象的三个基本特征 和 五种设计原则](https://blog.csdn.net/cancan8538/article/details/8057095)

### 构造器的调用顺序
1. 父类静态代码块
2. 子类静态代码块
3. 父类代码块
4. 父类构造
5. 子类代码块
6. 子类构造
[java 子类继承父类运行顺序](https://blog.csdn.net/xu511739113/article/details/52302498)
### 抽象类和接口的区别
- 抽象类是用来捕捉子类的通用特性的 。它不能被实例化，只能被用作子类的超类。抽象类是被用来创建继承层级里子类的模板。  
- 接口是抽象方法的集合。如果一个类实现了某个接口，那么它就继承了这个接口的抽象方法。这就像契约模式，如果实现了这个接口，那么就必须确保使用这些方法。  
- 什么时候使用抽象类和接口
    - 如果你拥有一些方法并且想让它们中的一些有默认实现，那么使用抽象类吧。
    - 如果你想实现多重继承，那么你必须使用接口。由于Java不支持多继承，子类不能够继承多个类，但可以实现多个接口。因此你就可以使用接口来解决它。
    - 如果基本功能在不断改变，那么就需要使用抽象类。如果不断改变基本功能并且使用接口，那么就需要改变所有实现了该接口的类。
- [抽象类和接口有什么区别，什么情况下会使用抽象类和什么情况你会使用接口](http://www.importnew.com/12399.html)  
### 匿名内部类
- 类名规则 定位$1
    - test方法中的匿名内部类的名字被起为 Test$1

- Anonymous Inner Class (匿名内部类)是否可以extends(继承)其它类，是否可以implements(实现)interface(接口)?
    - 可以继承其他类或实现其他接口。不仅是可以，而是必须!
[匿名内部类](https://blog.csdn.net/lazyer_dog/article/details/50669473)

### long和double类型变量的非原子性
int等不大于32位的基本类型的操作都是原子操作，但是某些jvm对long和double类型的操作并不是原子操作，这样就会造成错误数据的出现。 

错误数据出现的原因是： 
对于long和double变量，把它们作为2个原子性的32位值来对待，而不是一个原子性的64位值， 
这样将一个long型的值保存到内存的时候，可能是2次32位的写操作， 
2个竞争线程想写不同的值到内存的时候，可能导致内存中的值是不正确的结果。


### 异常类的继承结构

在整个Java的异常结构中，实际上有两个最常用的类，分别为Exception和Error，这两个类全都是Throwable的子类。

![1006828-20170614163059821-1343167353](https://user-images.githubusercontent.com/15559340/44645532-830f1a00-aa0a-11e8-92bb-40f851ba1842.png)

- Exception ： 一般标识的是程序中出现的问题，可以直接使用try---catch处理。

- Error ： 一般值得是JVM错误，程序中无法处理。

一般情况下，Exception和Error统称为异常，而算术异常（AtithmeticException）、数字格式化异常（NumberFormatException）等都属于Exception的子类。

> 提示：e.printStatckTrace(); ： 打印的异常信息是最完整的。
## 5.2 String相关

### String、StringBuffer以及StringBuilder的区别
- `for(int i=0;i<10000;i++){string += "hello";` 这句 string += “hello”;的过程相当于将原有的string变量指向的对象内容取出与”hello”作字符串相加操作再存进另一个新的String对象当中，再让string变量指向新生成的对象。整个循环的执行过程，并且每次循环会new出一个StringBuilder对象，然后进行append操作，最后通过toString方法返回String对象。也就是说这个循环执行完毕new出了10000个对象，试想一下，如果这些对象没有被回收，会造成多大的内存资源浪费。

- 那么有人会问既然有了StringBuilder类，为什么还需要StringBuffer类？查看源代码便一目了然，事实上，StringBuilder和StringBuffer类拥有的成员属性以及成员方法基本相同，区别是StringBuffer类的成员方法前面多了一个关键字：synchronized，不用多说，这个关键字是在多线程访问时起到安全保护作用的,也就是说StringBuffer是线程安全的。
- [链接](http://www.importnew.com/18167.html)
### 在java中String类为什么要设计成final
首先String类是用final关键字修饰，这说明String不可继承。再看下面，String类的主力成员字段value是个char[ ]数组，而且是用final修饰的。final修饰的字段创建以后就不可改变。

**1. 为了安全**

在hashmap等映射时体现。


**2. 不可变性支持线程安全**

还有一个大家都知道，就是在并发场景下，多个线程同时读一个资源，是不会引发竟态条件的。只有对资源做写操作才有危险。不可变对象不能被写，所以线程安全。


**3. 不可变性支持字符串常量池**

最后别忘了String另外一个字符串常量池的属性。像下面这样字符串one和two都用字面量"something"赋值。它们其实都指向同一个内存地址。


[在java中String类为什么要设计成final？](https://blog.csdn.net/u013905744/article/details/52414111)
### String.GetHashCode()复杂度
- 如果两个字符串对象是否相等，GetHashCode方法返回相同的值。 但是，有不为每个唯一字符串值是唯一的哈希代码值。 不同的字符串可以返回相同的哈希代码。

- **Java的实现**
	- 可以看到String的hashCode还是很简单的 复杂度为O(n)
	```Java
	public int hashCode() {
		int h = hash;
		if (h == 0 && value.length > 0) {
			char val[] = value;

			for (int i = 0; i < value.length; i++) {
				h = 31 * h + val[i];
			}
			hash = h;
		}
		return h;
	}
	```
	[科普：为什么 String hashCode 方法选择数字31作为乘子](https://segmentfault.com/a/1190000010799123)
	> 31可以被 JVM 优化，31 * i = (i << 5) - i

### String不变性
- 一旦字符串在内存（堆）中创建就不会被改变。记住：所有的String方法都不是改变字符串本身，而是创建一个新的字符串。

- 如果需要自身可以改变的字符串则可以使用StringBuilder和StringBuffer，否则就会浪费大量的时间在垃圾回收上。

- [String不变性(Java)](https://blog.csdn.net/sun_star1chen/article/details/17186151)
### String驻留池
对于以下代码：
```Java
String s1="abc";	String s2="abc";
```
总共创建了几个对象？答案是一个，这两个字符串，我们在使用的时候，它们在内容上没有任何区别，更没有理由使用两份对象，所以 **JVM对字符串对象的创建作了一个优化，即使用了驻留池技术**，当String s1="abc";时，JVM首先会在驻留池寻找，是否存在“abc”这样的一个值，当然刚开始显然是不存在的，所以JVM会在驻留池创建一个对象保存这个字符串，当再次出现String s2="abc";时，这是JVM会在驻留池寻找是否存在“abc”这样的一个值，当然，这个时候已经存在了，所以JVM会把保存该字符串对象的引用直接返回给s2,这样就避免了重复创建对象，减少了内存的开销。

那么对于这句代码：`Sting str=new String("abc");`

不妨这样写
```java
String s="abc";	String str=new String(s);
```
先定义一个字符串常量，然后用这个字符串常量作为字符串构造方法的参数再new出一个字符串出来。在定义字符串常量“abc”时，JVM会在 **驻留池里创建出一个对象** 来保存“abc”（注意这个对象 **不是被new出来的，所以不会被放在堆中** ）当再用s作为构造参数new出一个对象时，会被放在堆内存中，所以一共创建了两个对象。
- 习题：`String  str = new String ("King");`    问： 这句话创建了几个对象？
	- 答案：2个；一个由new 在堆区产生，另一个在驻留池中产生。

- [String驻留池](https://blog.csdn.net/nzh1234/article/details/22181585)

## 5.3 数据结构
- **[各种数据结构的实现](http://wiki.jikexueyuan.com/project/java-collection/linkedhashmap.html)**  
### List --> ArrayList / LinkedList / Vector
> 在Java中List接口有3个常用的实现类，分别是ArrayList、LinkedList、Vector。
- ArrayList内部存储的数据结构是数组存储。数组的特点：元素可以快速访问。每个元素之间是紧邻的不能有间隔，缺点：数组空间不够元素存储需要扩容的时候会开辟一个新的数组把旧的数组元素拷贝过去，比较消性能。从ArrayList中间位置插入和删除元素，都需要循环移动元素的位置，因此数组特性决定了数组的特点：适合随机查找和遍历，不适合经常需要插入和删除操作。
- Vector内部实现和ArrayList一样都是数组存储，最大的不同就是它支持线程的同步，所以访问比ArrayList慢，但是数据安全，所以对元素的操作没有并发操作的时候用ArrayList比较快。
- LinkedList内部存储用的数据结构是链表。链表的特点：适合动态的插入和删除。访问遍历比较慢。另外不支持get，remove，insertList方法。可以当做堆栈、队列以及双向队列使用。LinkedList是线程不安全的。所以需要同步的时候需要自己手动同步，比较费事，可以使用提供的集合工具类实例化的时候同步：具体使用List<String> springokList=Collections.synchronizedCollection(new 需要同步的类)

- [LinkedList, ArrayList等使用场景和性能分析](http://www.cnblogs.com/skywang12345/p/3308900.html)
- [java中List接口的实现类 ArrayList，LinkedList，Vector 的区别 list实现类源码分析](https://blog.csdn.net/qq_30739519/article/details/50877217)

### Map --> HashMap / ConcurrentHashMap / TreeMap / LinkedHashMap
#### 1. HashMap
##### 1.1 结构与参数
系统在初始化HashMap时，会创建一个 长度为 capacity 的 Entry 数组，这个数组里可以存储元素的位置被称为“桶（bucket）”，每个 bucket 都有其指定索引，系统可以根据其索引快速访问该 bucket 里存储的元素。

在HashMap中有两个很重要的参数，容量(Capacity)和负载因子(Load factor)：  
> Capacity就是buckets的数目，Load factor就是buckets填满程度的最大比例。如果对迭代性能要求很高的话不要把`capacity`设置过大，也不要把`load factor`设置过小。当bucket填充的数目（即hashmap中元素的个数）大于`capacity*load factor`时就需要调整buckets的数目为当前的**2**倍。

无论何时，HashMap 的每个“桶”只存储一个元素（也就是一个 Entry），由于 Entry 对象可以包含一个引用变量（就是 Entry 构造器的的最后一个参数）用于指向下一个 Entry，  
因此可能出现的情况是：HashMap 的 bucket 中只有一个 Entry，但这个 Entry 指向另一个 Entry ——这就形成了一个 Entry 链。（也就是冲突了）

![entry](http://dl2.iteye.com/upload/attachment/0017/5449/66679083-1285-397d-860a-83fc41efeedd.jpg)

当 HashMap 的每个 bucket 里存储的 Entry 只是单个 Entry ——也就是没有通过指针产生 Entry 链时（没有哈希冲突），此时的 HashMap 具有最好的性能：当程序通过 key 取出对应 value 时，系统只要先计算出该 key 的 hashCode() 返回值，在根据该 hashCode 返回值找出该 key 在 table 数组中的索引，然后取出该索引处的 Entry，最后返回该 key 对应的 value 即可。

在发生“Hash 冲突”的情况下，单个 bucket 里存储的不是一个 Entry，而是一个 Entry 链，系统只能必须按顺序遍历每个 Entry，直到找到想搜索的 Entry 为止——如果恰好要搜索的 Entry 位于该 Entry 链的最末端（该 Entry 是最早放入该 bucket 中），那系统必须循环到最后才能找到该元素。

当创建 HashMap 时，有一个默认的负载因子（load factor），其默认值为 0.75，这是时间和空间成本上一种折衷：增大负载因子可以减少 Hash 表（就是那个 Entry 数组）所占用的内存空间，但会增加查询数据的时间开销，而查询是最频繁的的操作（HashMap 的 get() 与 put() 方法都要用到查询）；减小负载因子会提高数据查询的性能，但会增加 Hash 表所占用的内存空间。 通常情况下，程序员无需改变负载因子的值。

##### 1.2 put()函数的实现
put函数大致的思路为：

1. 对key的hashCode()做hash，然后再计算index;
2. 如果没碰撞直接放到bucket里；
3. 如果碰撞了，以链表的形式存在buckets后；
4. 如果碰撞导致链表过长(大于等于TREEIFY_THRESHOLD)，就把链表转换成红黑树[参考此链接：Java 8：HashMap的性能提升](http://www.importnew.com/14417.html)；
5. 如果节点已经存在就替换old value(保证key的唯一性)
6. 如果bucket满了(超过load factor*current capacity)，就要resize。

##### 1.3 get()函数的实现
大致思路如下：
1. bucket里的第一个节点，直接命中；
2. 如果有冲突，则通过key.equals(k)去查找对应的entry
- 若为树，则在树中通过key.equals(k)查找，O(logn)；
- 若为链表，则在链表中通过key.equals(k)查找，O(n)。
##### 1.4 hash函数的实现
过程如下图：
![hashindex](https://cloud.githubusercontent.com/assets/1736354/6957712/293b52fc-d932-11e4-854d-cb47be67949a.png)
```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```
高16bit不变，低16bit和高16bit做了一个异或。

这样的一个hash函数实现，主要是权衡了速度与碰撞率。
> 设计者还解释到因为现在大多数的hashCode的分布已经很不错了，就算是发生了碰撞也用`O(logn)`的tree去做了。仅仅异或一下，既减少了系统的开销，也不会造成的因为高位没有参与下标的计算(table长度比较小时)，从而引起的碰撞。

如果发生了碰撞：
> 在Java 8之前的实现中是用链表解决冲突的，在产生碰撞的情况下，进行get时，两步的时间复杂度是O(1)+O(n)。因此，当碰撞很厉害的时候n很大，O(n)的速度显然是影响速度的。
> 因此在Java 8中，利用红黑树替换链表，这样复杂度就变成了O(1)+O(logn)了，这样在n很大的时候，能够比较理想的解决这个问题，在[Java 8：HashMap的性能提升](http://www.importnew.com/14417.html)一文中有性能测试的结果。

**注意：hash和计算下标是不一样的，hash是计算下标过程的一部分**

##### 1.5 RESIZE 的实现
当put时，如果发现目前的bucket占用程度已经超过了Load Factor所希望的比例，为了减少碰撞率，就会执行resize。resize的过程，简单的说就是把bucket扩充为2倍，之后重新计算index，把节点再放到新的bucket中。

> 怎么理解呢？例如我们从16扩展为32时，具体的变化如下所示：

> ![rehash](https://cloud.githubusercontent.com/assets/1736354/6958256/ceb6e6ac-d93b-11e4-98e7-c5a5a07da8c4.png)

>
> 因此元素在重新计算hash之后，因为n变为2倍，那么n-1的mask范围在高位多1bit(红色)，因此新的index就会发生这样的变化：

> ![resize](https://cloud.githubusercontent.com/assets/1736354/6958301/519be432-d93c-11e4-85bb-dff0a03af9d3.png)

>
> 因此，我们在扩充HashMap的时候，不需要重新计算hash，只需要看看原来的hash值新增的那个bit是1还是0就好了，是0的话索引没变，是1的话索引变成“原索引+oldCap”。可以看看下图为16扩充为32的resize示意图：

> ![resize16-32](https://cloud.githubusercontent.com/assets/1736354/6958677/d7acbad8-d941-11e4-9493-2c5e69d084c0.png)

>这个设计确实非常的巧妙，既省去了重新计算hash值的时间，而且同时，由于新增的1bit是0还是1可以认为是随机的，因此resize的过程，均匀的把之前的冲突的节点分散到新的bucket了。

##### 1.6 [Hashmap为什么容量是2的幂次](https://blog.csdn.net/a_long_/article/details/51594159)

最理想的效果是，Entry数组中每个位置都只有一个元素，这样，查询的时候效率最高，不需要遍历单链表，也不需要通过equals去比较K，而且空间利用率最大。那如何计算才会分布最均匀呢？我们首先想到的就是%运算，哈希值%容量=bucketIndex。
 ```java
static int indexFor(int h, int length) {  
    return h & (length-1);  
}  
```
这个等式实际上可以推理出来，2^n转换成二进制就是1+n个0，减1之后就是0+n个1，如16 -> 10000，15 -> 01111，那根据&位运算的规则，都为1(真)时，才为1，那0≤运算后的结果≤15，假设h <= 15，那么运算后的结果就是h本身，h >15，运算后的结果就是最后三位二进制做&运算后的值，最终，就是%运算后的余数，我想，这就是容量必须为2的幂的原因。

##### 1.7 总结

**1. 什么是HashMap？你为什么用到它？**
是基于Map接口的实现，存储键值对时，它可以接收null的键值，是非同步的，HashMap存储着Entry(hash, key, value, next)对象。

**2. 你知道HashMap的工作原理吗？**
通过hash的方式，以键值对<K,V>的方式存储(put)、获取(get)对象。存储对象时，我们将K/V传给put方法时，它调用hashCode计算hash从而得到bucket位置，进一步存储，HashMap会根据当前bucket的占用情况自动调整容量(超过Load Facotr则resize为原来的2倍)。获取对象时，我们将K传给get，它调用hashCode计算hash从而得到bucket位置，并进一步调用equals()方法确定键值对。如果发生碰撞的时候，Hashmap通过链表将产生碰撞冲突的元素组织起来，在Java 8中，如果一个bucket中碰撞冲突的元素超过某个限制(默认是8)，则使用红黑树来替换链表，从而提高速度。

**3. 你知道get和put的原理吗？equals()和hashCode()的都有什么作用？**
通过对key的hashCode()进行hashing，并计算下标`( n-1 & hash)`，从而获得buckets的位置。如果产生碰撞，则利用key.equals()方法去链表或树中去查找对应的节点。

**4. 你知道hash的实现吗？为什么要这样实现？**
在Java 1.8的实现中，是通过hashCode()的高16位异或低16位实现的：(h = k.hashCode()) ^ (h >>> 16)，主要是从速度、功效、质量来考虑的，这么做可以在bucket的n比较小的时候，也能保证考虑到高低bit都参与到hash的计算中，同时不会有太大的开销。

**5. 如果HashMap的大小超过了负载因子(load factor)定义的容量，怎么办？**
如果超过了负载因子(默认0.75)，则会重新resize一个原来长度两倍的HashMap，并且重新调用hash方法。

**6. 什么是哈希冲突？如何解决的？**
以Entry[]数组实现的哈希桶数组，用Key的哈希值取模桶数组的大小可得到数组下标。  
插入元素时，如果两条Key落在同一个桶（比如哈希值1和17取模16后都属于第一个哈希桶），我们称之为哈希冲突。  

JDK的做法是链表法，Entry用一个next属性实现多个Entry以单向链表存放。查找哈希值为17的key时，先定位到哈希桶，然后链表遍历桶里所有元素，逐个比较其Hash值然后key值。  
在JDK8里，新增默认为8的阈值，当一个桶里的Entry超过閥值，就不以单向链表而以红黑树来存放以加快Key的查找速度。
当然，最好还是桶里只有一个元素，不用去比较。所以默认当Entry数量达到桶数量的75%时，哈希冲突已比较严重，就会成倍扩容桶数组，并重新分配所有原来的Entry。扩容成本不低，所以也最好有个预估值。  

**7. HashMap在高并发下引起的死循环**  
- HashMap进行存储时，如果size超过当前最大容量*负载因子时候会发生resize。
- 而这段代码中又调用了transfer()方法，而这个方法实现的机制就是将每个链表转化到新链表，并且链表中的位置发生反转，而这在多线程情况下是很容易造成链表回路，从而发生get()死循环
- 链表头插法的会颠倒原来一个散列桶里面链表的顺序。在并发的时候原来的顺序被另外一个线程a颠倒了，而被挂起线程b恢复后拿扩容前的节点和顺序继续完成第一次循环后，又遵循a线程扩容后的链表顺序重新排列链表中的顺序，最终形成了环。
- 假如有两个线程P1、P2，以及链表 a=》b=》null
1. P1先执行，执行完"Entry<K,V> next = e.next;"代码后发生阻塞，或者其他情况不再执行下去，此时e=a，next=b
2. 而P2已经执行完整段代码，于是当前的新链表newTable[i]为b=》a=》null
3. P1又继续执行"Entry<K,V> next = e.next;"之后的代码，则执行完"e=next;"后，newTable[i]为a《=》b，则造成回路，while(e!=null)一直死循环
- [HashMap在高并发下引起的死循环](https://blog.csdn.net/chenxuegui1234/article/details/39646041)
- [Java面试题：高并发环境下，HashMap可能出现的致命问题。注意：是在jdk8以下版本](https://blog.csdn.net/dgutliangxuan/article/details/78779448)


-----
[Java 集合类实现原理](https://www.jianshu.com/p/0b2ad1952506)
#### 2. ConcurrentHashMap
> ConcurrentHashMap 同样也分为 1.7 、1.8 版，两者在实现上略有不同。

【1.7】  
原理上来说：ConcurrentHashMap 采用了分段锁技术，其中 Segment 继承于 ReentrantLock。不会像 HashTable 那样不管是 put 还是 get 操作都需要做同步处理，理论上 ConcurrentHashMap 支持 CurrencyLevel (Segment 数组数量)的线程并发。每当一个线程占用锁访问一个 Segment 时，不会影响到其他的 Segment。  
![1](https://ws4.sinaimg.cn/large/006tNc79gy1ftj0evlsrgj30dw073gm2.jpg) 

**PUT**

在put时，首先是通过 key 定位到 Segment，之后在对应的 Segment 中进行具体的 put。

虽然 HashEntry 中的 value 是用 volatile 关键词修饰的，但是并不能保证并发的原子性，所以 put 操作时仍然需要加锁处理。

首先第一步的时候会尝试获取锁，如果获取失败肯定就有其他线程存在竞争，则利用 scanAndLockForPut() 自旋获取锁

**GET**

get 逻辑比较简单：

只需要将 Key 通过 Hash 之后定位到具体的 Segment ，再通过一次 Hash 定位到具体的元素上。

由于 HashEntry 中的 value 属性是用 volatile 关键词修饰的，保证了内存可见性，所以每次获取时都是最新值。

ConcurrentHashMap 的 get 方法是非常高效的，因为整个过程都不需要加锁。


【1.8】  

![2](https://ws3.sinaimg.cn/large/006tNc79gy1fthpv4odbsj30lp0drmxr.jpg)  
看起来是不是和 1.8 HashMap 结构类似？  
其中 **抛弃了原有的 Segment 分段锁** ，而采用了 CAS + synchronized 来保证并发安全性。

**PUT**
- 根据 key 计算出 hashcode 。
- 判断是否需要进行初始化。
- f 即为当前 key 定位出的 Node，如果为空表示当前位置可以写入数据，利用 CAS 尝试写入，失败则自旋保证成功。
- 如果当前位置的 hashcode == MOVED == -1,则需要进行扩容。
- 如果都不满足，则利用 synchronized 锁写入数据。
- 如果数量大于 TREEIFY_THRESHOLD 则要转换为红黑树


**GET**
- 根据计算出来的 hashcode 寻址，如果就在桶上那么直接返回值。
- 如果是红黑树那就按照树的方式获取值。
- 就不满足那就按照链表的方式遍历获取值。


--------------

#### 3. 使用LinkedHashMap设计实现一个LRU Cache  
实际上就是 HashMap 和 LinkedList 两个集合类的存储结构的结合。在 LinkedHashMapMap 中，所有 put 进来的 Entry 都保存在哈希表中，但它又额外定义了一个 head 为头结点的空的双向循环链表，每次 put 进来 HashMapEntry ，除了将其保存到对哈希表中对应的位置上外，还要将其插入到双向循环链表的尾部。

```java
public class LRUCache{
private int capacity;
private Map<Integer, Integer> cache;

public LRUCache(int capacity) {
    this.capacity = capacity;
    this.cache = new LinkedHashMap<Integer, Integer> (capacity, (float) 0.75, true){
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    };
}

public void set(int key, int value){
    cache.put(key, value);
}

public int get(int key){
    if(cache.containsKey(key))
        return cache.get(key);
    return -1;
}
```

- [动手实现一个 LRU cache的三个思路](https://crossoverjie.top/2018/04/07/algorithm/LRU-cache/)  
- [如何设计实现一个LRU Cache](https://github.com/Yikun/yikun.github.com/issues/9)
- [LinkedHashMap 的实现原理](http://wiki.jikexueyuan.com/project/java-collection/linkedhashmap.html)
#### 4. 基于HashMap和双向链表的实现LRU Cache
![1](https://cloud.githubusercontent.com/assets/1736354/6984935/92033a96-da60-11e4-8754-66135bb0d233.png)
- [如何设计实现一个LRU Cache？](https://yikun.github.io/2015/04/03/%E5%A6%82%E4%BD%95%E8%AE%BE%E8%AE%A1%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AALRU-Cache%EF%BC%9F/)
```java
public class LRUCache {
    class Node {
        Node pre;
        Node next;
        Integer key;
        Integer val;
        Node(Integer k, Integer v) {
            key = k;
            val = v;
        }
    }
    Map<Integer, Node> map = new HashMap<Integer, Node>();
    // The head (eldest) of the doubly linked list.
    Node head;
    // The tail (youngest) of the doubly linked list.
    Node tail;
    int cap;
    public LRUCache(int capacity) {
        cap = capacity;
        head = new Node(null, null);
        tail = new Node(null, null);
        head.next = tail;
        tail.pre = head;
    }
    public int get(int key) {
        Node n = map.get(key);
        if(n!=null) {
            n.pre.next = n.next;
            n.next.pre = n.pre;
            appendTail(n);
            return n.val;
        }
        return -1;
    }
    public void set(int key, int value) {
        Node n = map.get(key);
        // existed
        if(n!=null) {
            n.val = value;
            map.put(key, n);
            n.pre.next = n.next;
            n.next.pre = n.pre;
            appendTail(n);
            return;
        }
        // else {
        if(map.size() == cap) {
            Node tmp = head.next;
            head.next = head.next.next;
            head.next.pre = head;
            map.remove(tmp.key);
        }
        n = new Node(key, value);
        // youngest node append taill
        appendTail(n);
        map.put(key, n);
    }
    private void appendTail(Node n) {
        n.next = tail;
        n.pre = tail.pre;
        tail.pre.next = n;
        tail.pre = n;
    }
}
```

### Set --> HashSet / TreeSet(TODO)
[Java 集合类实现原理](https://jianshu.com/p/0b2ad1952506)

## 5.4 多线程 并发
### JAVA多线程之线程间的通信方式
1. 同步  
这里讲的同步是指多个线程通过synchronized关键字这种方式来实现线程间的通信。  
由于线程A和线程B持有同一个MyObject类的对象object，尽管这两个线程需要调用不同的方法，但是它们是同步执行的，比如：线程B需要等待线程A执行完了methodA()方法之后，它才能执行methodB()方法。这样，线程A和线程B就实现了 通信。
这种方式，本质上就是“共享内存”式的通信。多个线程需要访问同一个共享变量，谁拿到了锁（获得了访问权限），谁就可以执行。
2. while轮询的方式  
这种方式下，线程A不断地改变条件，线程ThreadB不停地通过while语句检测这个条件(list.size()==5)是否成立 ，从而实现了线程间的通信。但是这种方式会浪费CPU资源。
3. wait/notify机制  
A,B之间如何通信的呢？也就是说，线程A如何知道 list.size() 已经为5了呢？
这里用到了Object类的 wait() 和 notify() 方法。
当条件未满足时(list.size() !=5)，线程A调用wait() 放弃CPU，并进入阻塞状态。---不像②while轮询那样占用CPU
当条件满足时，线程B调用 notify()通知 线程A，所谓通知线程A，就是唤醒线程A，并让它进入可运行状态。
这种方式的一个好处就是CPU的利用率提高了。
但是也有一些缺点：比如，线程B先执行，一下子添加了5个元素并调用了notify()发送了通知，而此时线程A还执行；当线程A执行并调用wait()时，那它永远就不可能被唤醒了。因为，线程B已经发了通知了，以后不再发通知了。这说明：通知过早，会打乱程序的执行逻辑。
4. 管道通信  
而管道通信，更像消息传递机制，也就是说：通过管道，将一个线程中的消息发送给另一个。

[JAVA多线程之线程间的通信方式](http://cnblogs.com/hapjin/p/5492619.html) 

### 线程池ThreadPoolExecutor参数设置  
- ThreadPoolExecutor类可设置的参数主要有：  
- corePoolSize
> 核心线程数，核心线程会一直存活，即使没有任务需要处理。当线程数小于核心线程数时，即使现有的线程空闲，线程池也会优先创建新线程来处理任务，而不是直接交给现有的线程处理。核心线程在allowCoreThreadTimeout被设置为true时会超时退出，默认情况下不会退出。

- maxPoolSize
> 当线程数大于或等于核心线程，且任务队列已满时，线程池会创建新的线程，直到线程数量达到maxPoolSize。如果线程数已等于maxPoolSize，且任务队列已满，则已超出线程池的处理能力，线程池会拒绝处理任务而抛出异常。
- keepAliveTime
> 当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize。如果allowCoreThreadTimeout设置为true，则所有线程均会退出直到线程数量为0。
- allowCoreThreadTimeout
> 是否允许核心线程空闲退出，默认值为false。
- queueCapacity
> 任务队列容量。从maxPoolSize的描述上可以看出，任务队列的容量会影响到线程的变化，因此任务队列的长度也需要恰当的设置。

### 3种常用的线程池
下面是常用的四种线程池，它们都是基于Executor接口的实现类executor：

#### SingleThreadExecutor
单个线程的线程池，即线程池中每次只有一个线程工作，单线程串行执行任务，也就是说只有一个核心线程，所有操作都通过这一个线程来进行
```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
```

#### FixedThreadExecutor

固定数量的线程池，每提交一个任务就是一个线程，直到达到线程池的最大数量，然后后面进入等待队列直到前面的任务完成才继续执行
```java
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```

- FixedThreadPool的corePoolSize和maxiumPoolSize都被设置为创建FixedThreadPool时指定的参数nThreads。
- 0L则表示当线程池中的线程数量操作核心线程的数量时，多余的线程将被立即停止
- 最后一个参数表示FixedThreadPool使用了无界队列LinkedBlockingQueue作为线程池的做工队列，由于是无界的，当线程池的线程数达到corePoolSize后，新任务将在无界队列中等待，因此线程池的线程数量不会超过corePoolSize，同时maxiumPoolSize也就变成了一个无效的参数，并且运行中的线程池并不会拒绝任务


#### CacheThreadExecutor（推荐使用）
可缓存线程池，当线程池大小超过了处理任务所需的线程，那么就会回收部分空闲（一般是60秒无执行）的线程，当有任务来时，又智能的添加新线程来执行。

CachedThreadPool是一个”无限“容量的线程池，它会根据需要创建新线程。下面是它的构造方法：

```java
public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }
```


-------

### 内存可见性与volatile 关键字


线程在工作时，需要将主内存中的数据拷贝到工作内存中。这样对数据的任何操作都是基于工作内存(效率提高)，并且不能直接操作主内存以及其他线程工作内存中的数据，之后再将更新之后的数据刷新到主内存中。  
这里所提到的主内存可以简单认为是堆内存，而工作内存则可以认为是栈内存。

![3](https://camo.githubusercontent.com/f9c7b0fc135f983cc4682576e7b2a3167259101f/68747470733a2f2f7773322e73696e61696d672e636e2f6c617267652f303036744b6654636c7931666d6f75753366706f6b6a33316165306f736a74312e6a7067)
所以在并发运行时可能会出现线程 B 所读取到的数据是线程 A 更新之前的数据。

显然这肯定是会出问题的，因此 volatile 的作用出现了：

> 当一个变量被 volatile 修饰时，任何线程对它的写操作都会立即刷新到主内存中，并且会强制让缓存了该变量的线程中的数据清空，必须从主内存重新读取最新数据。

volatile 修饰之后并不是让线程直接从主内存中获取数据，依然需要将变量拷贝到工作内存中。


- 内存可见性的应用  
当我们需要在两个线程间依据主内存通信时，通信的那个变量就必须的用 volatile 来修饰：  
主线程在修改了标志位使得线程 A 立即停止，如果没有用 volatile 修饰，就有可能出现延迟。  
这里要重点强调，volatile 并不能保证线程安全性！

- 指令重排  
内存可见性只是 volatile 的其中一个语义，它还可以防止 JVM 进行指令重排优化。  
举一个伪代码:`int a=10 ;//1int b=20 ;//2int c= a+b ;//3`  
一段特别简单的代码，理想情况下它的执行顺序是：1>2>3。但有可能经过 JVM 优化之后的执行顺序变为了 2>1>3。  
可以发现不管 JVM 怎么优化，前提都是保证单线程中最终结果不变的情况下进行的。  
这里就能看出问题了，当 flag 没有被 volatile 修饰时，JVM 对 1 和 2 进行重排，导致 value 都还没有被初始化就有可能被线程 B 使用了。
所以加上 volatile 之后可以防止这样的重排优化，保证业务的正确性。

#### volatile关键字是如何保证可见性的?
在单核CPU的情况下，是不存在可见性问题的，如果是多核CPU，可见性问题就会暴露出来。

我们知道线程中运行的代码最终都是交给CPU执行的，而代码执行时所需使用到的数据来自于内存(或者称之为主存)。但是CPU是不会直接操作内存的，每个CPU都会有自己的缓存，操作缓存的速度比操作主存更快。

因此当某个线程需要修改一个数据时，事实上步骤是如下的：

1. 将主存中的数据加载到缓存中

2. CPU对缓存中的数据进行修改

3. 将修改后的值刷新到内存中

问题就出现在第二步，因为每个CPU操作的是各自的缓存，所以不同的CPU之间是无法感知其他CPU对这个变量的修改的，最终就可能导致结果与我们的预期不符。

而使用了volatile关键字之后，情况就有所不同，volatile关键字有两层语义：

1. 立即将缓存中数据写会到内存中

2. 其他处理器通过嗅探总线上传播过来了数据监测自己缓存的值是不是过期了，如果过期了，就会对应的缓存中的数据置为无效。而当处理器对这个数据进行修改时，会重新从内存中把数据读取到缓存中进行处理。

在这种情况下，不同的CPU之间就可以感知其他CPU对变量的修改，并重新从内存中加载更新后的值，因此可以解决可见性问题。

- [volatile关键字是如何保证可见性的](http://www.tianshouzhi.com/api/tutorials/mutithread/286)
- [内存可见性与volatile 关键字](https://github.com/crossoverJie/Java-Interview/blob/master/MD/concurrent/volatile.md)
### happens-before原则

如果Java内存模型中所有的有序性都仅仅靠volatile和synchronized来完成，那么有一些操作将变得很繁琐，但是我们在编写Java代码时并未感觉到这一点，这是因为Java语言中有一个”先行发生（happens-before）”原则。这个原则非常重要，它是判断数据是否存在竞争、线程是否安全的主要依据，依靠这个原则，我们可以通过几条规则就判断出并发环境下两个操作之间是否可能存在冲突的问题。

所谓先行发生原则是指Java内存模型中定义的两项操作之间的偏序关系，如果说操作A先行发生于操作B，那么操作A产生的影响能够被操作b观察到，”影响”包括修改了内存中共享变量的值、发送了消息、调用了方法等。Java内存模型下有一些天然的，不需要任何同步协助器就已经存在的先行发生关系。
### synchronized的实现原理
- synchronized可以保证方法或者代码块在运行时，同一时刻只有一个方法可以进入到临界区，同时它还可以保证共享变量的内存可见性
- 当一个线程访问同步代码块时，它首先是需要得到锁才能执行同步代码，当退出或者抛出异常时必须要释放锁.
- 锁的机制可以参考互斥锁自旋锁。
- [链接](https://blog.csdn.net/chenssy/article/details/54883355)
- [Java中synchronized的实现原理与应用(详细)](https://blog.csdn.net/u012465296/article/details/53022317  )
#### 应用方式
- synchronized关键字最主要有以下3种应用方式，下面分别介绍  
        - 修饰实例方法，作用于当前实例加锁，进入同步代码前要获得当前实例的锁
        - 修饰静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁
        - 修饰代码块，指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。
1. **修饰实例方法**: 由于i++;操作并不具备原子性，该操作是先读取值，然后写回一个新值，相当于原来的值加上1，分两步完成，如果第二个线程在第一个线程读取旧值和写回新值期间读取i的域值，那么第二个线程就会与第一个线程一起看到同一个值，并执行相同值的加1操作，这也就造成了线程安全失败，因此对于increase方法必须使用synchronized修饰，以便保证线程安全。
2. **修饰静态方法**: 由于synchronized关键字修饰的是静态increase方法，与修饰实例方法不同的是，其锁对象是当前类的class对象。注意代码中的increase4Obj方法是实例方法，其对象锁是当前实例对象，如果别的线程调用该方法，将不会产生互斥现象，毕竟锁对象不同，但我们应该意识到这种情况下可能会发现线程安全问题(操作了共享静态变量i)。
3. **修饰代码块**在某些情况下，我们编写的方法体可能比较大，同时存在一些比较耗时的操作，而需要同步的代码又只有一小部分，如果直接对整个方法进行同步操作，可能会得不偿失，此时我们可以使用同步代码块的方式对需要同步的代码进行包裹，这样就无需对整个方法进行同步操作了

#### 实现原理  
- Java对象头
    - 在JVM中，对象在内存中的布局分为三块区域：对象头、实例数据和对齐填充。
    - synchronized使用的锁是存放在Java对象头里面，具体位置是对象头里面的MarkWord，MarkWord里默认数据是存储对象的HashCode等信息，但是会随着对象的运行改变而发生变化，不同的锁状态对应着不同的记录存储方式

- **Java虚拟机对synchronized的优化**  
    - 锁的状态总共有四种，无锁状态、偏向锁、轻量级锁和重量级锁。随着锁的竞争，锁可以从偏向锁升级到轻量级锁，再升级的重量级锁，但是锁的升级是单向的，也就是说只能从低到高升级，不会出现锁的降级。

    1. 偏向锁
    - 偏向锁是Java 6之后加入的新锁，它是一种针对加锁操作的优化手段，经过研究发现，在大多数情况下，锁不仅不存在多线程竞争，而且总是由同一线程多次获得，因此为了减少同一线程获取锁(会涉及到一些CAS操作,耗时)的代价而引入偏向锁。偏向锁的核心思想是，如果一个线程获得了锁，那么锁就进入偏向模式，此时Mark Word 的结构也变为偏向锁结构，当这个线程再次请求锁时，无需再做任何同步操作，即获取锁的过程，这样就省去了大量有关锁申请的操作，从而也就提供程序的性能。所以，对于没有锁竞争的场合，偏向锁有很好的优化效果，毕竟极有可能连续多次是同一个线程申请相同的锁。但是对于锁竞争比较激烈的场合，偏向锁就失效了，因为这样场合极有可能每次申请锁的线程都是不相同的，因此这种场合下不应该使用偏向锁，否则会得不偿失，需要注意的是，偏向锁失败后，并不会立即膨胀为重量级锁，而是先升级为轻量级锁。下面我们接着了解轻量级锁。

    2. 轻量级锁
    - 倘若偏向锁失败，虚拟机并不会立即升级为重量级锁，它还会尝试使用一种称为轻量级锁的优化手段(1.6之后加入的)，此时Mark Word 的结构也变为轻量级锁的结构。轻量级锁能够提升程序性能的依据是“对绝大部分的锁，在整个同步周期内都不存在竞争”，注意这是经验数据。需要了解的是，轻量级锁所适应的场景是线程交替执行同步块的场合，如果存在同一时间访问同一锁的场合，就会导致轻量级锁膨胀为重量级锁。

    3. 自旋锁
    - 轻量级锁失败后，虚拟机为了避免线程真实地在操作系统层面挂起，还会进行一项称为自旋锁的优化手段。这是基于在大多数情况下，线程持有锁的时间都不会太长，如果直接挂起操作系统层面的线程可能会得不偿失，毕竟操作系统实现线程之间的切换时需要从用户态转换到核心态，这个状态之间的转换需要相对比较长的时间，时间成本相对较高，因此自旋锁会假设在不久将来，当前的线程可以获得锁，因此虚拟机会让当前想要获取锁的线程做几个空循环(这也是称为自旋的原因)，一般不会太久，可能是50个循环或100循环，在经过若干次循环后，如果得到锁，就顺利进入临界区。如果还不能获得锁，那就会将线程在操作系统层面挂起，这就是自旋锁的优化方式，这种方式确实也是可以提升效率的。最后没办法也就只能升级为重量级锁了。

    4. 锁消除
    - 消除锁是虚拟机另外一种锁的优化，这种优化更彻底，Java虚拟机在JIT编译时(可以简单理解为当某段代码即将第一次被执行时进行编译，又称即时编译)，通过对运行上下文的扫描，去除不可能存在共享资源竞争的锁，通过这种方式消除没有必要的锁，可以节省毫无意义的请求锁时间，如下StringBuffer的append是一个同步方法，但是在add方法中的StringBuffer属于一个局部变量，并且不会被其他线程所使用，因此StringBuffer不可能存在共享资源竞争的情景，JVM会自动将其锁消除。




### Synchronized 与 ReentrantLock 的区别
#### 相似点
这两种同步方式有很多相似之处，它们都是加锁方式同步，而且都是阻塞式的同步，也就是说当如果一个线程获得了对象锁，进入了同步块，其他访问该同步块的线程都必须阻塞在同步块外面等待，而进行线程阻塞和唤醒的代价是比较高的（操作系统需要在用户态与内核态之间来回切换，代价很高，不过可以通过对锁优化进行改善）。

#### 区别
这两种方式最大区别就是：

- 对于Synchronized来说，它是java语言的关键字，是原生语法层面的互斥，需要jvm实现，不但可以通过一些监控工具监控synchronized的锁定，而且在代码执行时出现异常，JVM会自动释放锁定。

- 而ReentrantLock它是JDK 1.5之后提供的API层面的互斥锁，需要lock()和unlock()方法配合try/finally语句块来完成。

|类别|	synchronized|	Lock|
|-------|---------|-------|
|存在层次|	Java的关键字，在jvm层面上|	是一个类|
|锁的释放|	1、以获取锁的线程执行完同步代码，释放锁 2、线程执行发生异常，jvm会让线程释放锁|	在finally中必须释放锁，不然容易造成线程死锁|
|锁的获取|	假设A线程获得锁，B线程等待。如果A线程阻塞，B线程会一直等待|	分情况而定，Lock有多个锁获取的方式，具体下面会说道，大致就是可以尝试获得锁，线程可以不用一直等待|
|锁状态|	无法判断|	可以判断|
|锁类型|	可重入 不可中断 非公平|	可重入 可判断 可公平（两者皆可）|
|性能|	少量同步|	大量同步|


在资源竞争不是很激烈的情况下，Synchronized的性能要优于ReetrantLock，但是在资源竞争很激烈的情况下，Synchronized的性能会下降几十倍，但是ReetrantLock的性能能维持常态；

**1. Synchronized**

Synchronized经过编译，会在同步块的前后分别形成monitorenter和monitorexit这个两个字节码指令。在执行monitorenter指令时，首先要尝试获取对象锁。如果这个对象没被锁定，或者当前线程已经拥有了那个对象锁，把锁的计算器加1，相应的，在执行monitorexit指令时会将锁计算器就减1，当计算器为0时，锁就被释放了。如果获取对象锁失败，那当前线程就要阻塞，直到对象锁被另一个线程释放为止。

**2. ReentrantLock**

reentrant 锁意味着什么呢？简单来说，它有一个与锁相关的获取计数器，如果拥有锁的某个线程再次得到锁，那么获取计数器就加1，然后锁需要被释放两次才能获得真正释放。这模仿了 synchronized 的语义；如果线程进入由线程已经拥有的监控器保护的 synchronized 块，就允许线程继续进行，当线程退出第二个（或者后续） synchronized 块的时候，不释放锁，只有线程退出它进入的监控器保护的第一个 synchronized 块时，才释放锁。


由于ReentrantLock是java.util.concurrent包(J.U.C)下提供的一套互斥锁，相比Synchronized，ReentrantLock类提供了一些高级功能，主要有以下3项：

1. 等待可中断，持有锁的线程长期不释放的时候，正在等待的线程可以选择放弃等待，这相当于Synchronized来说可以避免出现死锁的情况。

2. 公平锁，多个线程等待同一个锁时，必须按照申请锁的时间顺序获得锁，Synchronized锁非公平锁，ReentrantLock默认的构造函数是创建的非公平锁，可以通过参数true设为公平锁，但公平锁表现的性能不是很好。

3. 锁绑定多个条件，一个ReentrantLock对象可以同时绑定对个对象。

```java
public class SynDemo{
	public static void main(String[] arg){
		Runnable t1=new MyThread();
		new Thread(t1,"t1").start();
		new Thread(t1,"t2").start();
	}
}
class MyThread implements Runnable {
	private Lock lock=new ReentrantLock();
	public void run() {
			lock.lock(); //加锁
			try{
				for(int i=0;i<5;i++)
					System.out.println(Thread.currentThread().getName()+":"+i);
			}finally{
				lock.unlock(); //解锁
			}
	}
}
```

[java的两种同步方式， Synchronized与ReentrantLock的区别](https://blog.csdn.net/chenchaofuck1/article/details/51045134)
[Synchronized与Lock锁的区别](http://hanhailong.com/2016/12/10/Synchronized%E4%B8%8ELock%E9%94%81%E7%9A%84%E5%8C%BA%E5%88%AB/)
### AQS
#### 1. 什么是AQS
AQS：AbstractQueuedSynchronizer，即队列同步器。它是构建锁或者其他同步组件的基础框架（如ReentrantLock、ReentrantReadWriteLock、Semaphore等），JUC并发包的作者（Doug Lea）期望它能够成为实现大部分同步需求的基础。它是JUC并发包中的核心基础组件。

如上所述，AQS管理一个关于状态信息的单一整数，该整数可以表现任何状态。比如， `Semaphore` 用它来表现剩余的许可数，`ReentrantLock` 用它来表现拥有它的线程已经请求了多少次锁；`FutureTask` 用它来表现任务的状态(尚未开始、运行、完成和取消)

AQS通过内置的FIFO同步队列来完成资源获取线程的排队工作，如果当前线程获取同步状态失败（锁）时，AQS则会将当前线程以及等待状态等信息构造成一个节点（Node）并将其加入同步队列，同时会阻塞当前线程，当同步状态释放时，则会把节点中的线程唤醒，使其再次尝试获取同步状态。

使用AQS来实现一个同步器需要覆盖实现如下几个方法，并且使用`getState`、`setState`、`compareAndSetState`这几个方法来设置获取状态 
1. `boolean tryAcquire(int arg)` 
2. `boolean tryRelease(int arg)` 
3. `int tryAcquireShared(int arg)` 
4. `boolean tryReleaseShared(int arg)` 
5. `boolean isHeldExclusively()`

#### 2. AQS在各同步器内的Sync与State实现
**2.1 什么是state机制：**
提供 volatile 变量 state;  用于同步线程之间的共享状态。通过 CAS 和 volatile 保证其原子性和可见性。对应源码里的定义：
```java
//同步状态  
private volatile int state;  
//cas  
protected final boolean compareAndSetState(int expect, int update) {  
    // See below for intrinsics setup to support this  
    return unsafe.compareAndSwapInt(this, stateOffset, expect, update);  
}  
```

**2.2 不同实现类的Sync与State**

基于AQS构建的Synchronizer包括ReentrantLock,Semaphore,CountDownLatch, ReetrantRead WriteLock,FutureTask等，这些Synchronizer实际上最基本的东西就是原子状态的获取和释放，只是条件不一样而已。

**ReentrantLock**

需要记录当前线程获取原子状态的次数，如果次数为零，那么就说明这个线程放弃了锁（也有可能其他线程占据着锁从而需要等待），如果次数大于1，也就是获得了重进入的效果，而其他线程只能被park住，直到这个线程重进入锁次数变成0而释放原子状态。以下为ReetranLock的FairSync的tryAcquire实现代码解析。

**Semaphore**

则是要记录当前还有多少次许可可以使用，到0，就需要等待，也就实现并发量的控制，Semaphore一开始设置许可数为1，实际上就是一把互斥锁。以下为Semaphore的FairSync实现

**CountDownLatch**

闭锁则要保持其状态，在这个状态到达终止态之前，所有线程都会被park住，闭锁可以设定初始值，这个值的含义就是这个闭锁需要被countDown()几次，因为每次CountDown是sync.releaseShared(1),而一开始初始值为10的话，那么这个闭锁需要被countDown()十次，才能够将这个初始值减到0，从而释放原子状态，让等待的所有线程通过。

日常开发中经常会遇到需要在主线程中开启多线程去并行执行任务，并且主线程需要等待所有子线程执行完毕后再进行汇总的场景，它的内部提供了一个计数器，在构造闭锁时必须指定计数器的初始值，且计数器的初始值必须大于0。另外它还提供了一个countDown方法来操作计数器的值，每调用一次countDown方法计数器都会减1，直到计数器的值减为0时就代表条件已成熟，所有因调用await方法而阻塞的线程都会被唤醒。这就是CountDownLatch的内部机制，看起来很简单，无非就是阻塞一部分线程让其在达到某个条件之后再执行。但是CountDownLatch的应用场景却比较广泛，只要你脑洞够大利用它就可以玩出各种花样。最常见的一个应用场景是开启多个线程同时执行某个任务，等到所有任务都执行完再统计汇总结果。

[CountDownLatch闭锁的源码分析](https://juejin.im/entry/5b42ff15f265da0f9313892a)

> CountDownLatch使用例子

> 模拟了一个应用程序启动类，它开始时启动了n个线程类，这些线程将检查外部系统并通知闭锁，并且启动类一直在闭锁上等待着。一旦验证和检查了所有外部服务，那么启动类恢复执行。
**FutureTask**

需要记录任务的执行状态，当调用其实例的get方法时,内部类Sync会去调用AQS的acquireSharedInterruptibly()方法，而这个方法会反向调用Sync实现的tryAcquireShared()方法，即让具体实现类决定是否让当前线程继续还是park,而FutureTask的tryAcquireShared方法所做的唯一事情就是检查状态，如果是RUNNING状态那么让当前线程park。而跑任务的线程会在任务结束时调用FutureTask 实例的set方法（与等待线程持相同的实例），设定执行结果，并且通过unpark唤醒正在等待的线程，返回结果。

- [Java多线程（七）之同步器基础：AQS框架深入分析](https://blog.csdn.net/vernonzheng/article/details/8275624)

#### 3. 其他概念
> 公平锁：每个线程抢占锁的顺序为先后调用lock方法的顺序依次获取锁，类似于排队吃饭。

> 非公平锁：每个线程抢占锁的顺序不定，谁运气好，谁就获取到锁，和调用lock方法的先后顺序无关，类似于堵车时，加塞的那些XXXX。

> ReentrantLock 默认的lock（）方法采用的是非公平锁。

**羊群效应**

这里说一下羊群效应，当有多个线程去竞争同一个锁的时候，假设锁被某个线程占用，那么如果有成千上万个线程在等待锁，有一种做法是同时唤醒这成千上万个线程去去竞争锁，这个时候就发生了羊群效应，海量的竞争必然造成资源的剧增和浪费，因此终究只能有一个线程竞争成功，其他线程还是要老老实实的回去等待。AQS的FIFO的等待队列给解决在锁竞争方面的羊群效应问题提供了一个思路：保持一个FIFO队列，队列每个节点只关心其前一个节点的状态，线程唤醒也只唤醒队头等待线程。其实这个思路已经被应用到了分布式锁的实践中，见：Zookeeper分布式锁的改进实现方案。
### Callable、Future和FutureTask（TODO）

###  JDK8 的 CompletableFuture（TODO）




## 5.5 Java 中的锁
### 锁
- [Java 中的锁](http://www.importnew.com/19472.html)
- 包括：1.公平锁和非公平锁、2.自旋锁、3.锁消除、4.锁粗化、5.可重入锁、6.类锁和对象锁、7.偏向锁、轻量级锁和重量级锁、8.悲观锁和乐观锁、9.共享锁和排它锁、10.读写锁、11.互斥锁、12.无锁

### 锁的粒度
- [并发性能优化 ： 降低锁粒度](http://www.importnew.com/20920.html)
- 当我们需要使用并发时， 常常有一个资源必须被两个或多个线程共享。在这种情况下，就存在一个竞争条件，也就是其中一个线程可以得到锁（锁与特定资源绑定），其他想要得到锁的线程会被阻塞。这个同步机制的实现是有代价的，为了向你提供一个好用的同步模型，JVM 和操作系统都要消耗资源。有三个最重要的因素使并发的实现会消耗大量资源，它们是：
	1. 上下文切换	
    2. 内存同步	
    3. 阻塞
- 介绍一种通过降低锁粒度的技术来减少这些因素。让我们从一个基本原则开始：**不要长时间持有不必要的锁**。
- 在获得锁之前做完所有需要做的事，只把锁用在需要同步的资源上，用完之后立即释放它。详情见链接。

## 5.6 NIO
### 概述
- NIO本身是基于事件驱动思想来完成的，
- 其主要想解决的是BIO的大并发问题： 在使用同步I/O的网络应用中，如果要同时处理多个客户端请求，或是在客户端要同时和多个服务器进行通讯，就必须使用多线程来处理。也就是说，将每一个客户端请求分配给一个线程来单独处理。这样做虽然可以达到我们的要求，但同时又会带来另外一个问题。由于每创建一个线程，就要为这个线程分配一定的内存空间（也叫工作存储器），而且操作系统本身也对线程的总数有一定的限制。如果客户端的请求过多，服务端程序可能会因为不堪重负而拒绝客户端的请求，甚至服务器可能会因此而瘫痪。  
- NIO基于Reactor，当socket有流可读或可写入socket时，操作系统会相应的通知引用程序进行处理，应用再将流读取到缓冲区或写入操作系统。  也就是说，这个时候，已经不是一个连接就要对应一个处理线程了，而是有效的请求，对应一个线程，当连接没有数据时，是没有工作线程来处理的。  
- NIO的最重要的地方是当一个连接创建后，不需要对应一个线程，这个连接会被注册到多路复用器上面，所以所有的连接只需要一个线程就可以搞定，当这个线程中的多路复用器进行轮询的时候，发现连接上有请求的话，才开启一个线程进行处理，也就是一个请求一个线程模式。

> IO和NIO的关键就在你读取的过程中，假设一个情况，你正在读取一个数据，它的长度是500字节，但是目前传输而来的数据只有200字节，还有300在路上。这时候你有两种方式，一种是继续阻塞，等待那些数据到达。不过这显然不是个好方法，因为你不知道那些数据还有多久到达（有可能网络中断了，它们永远不会到达了）。另外一种方法是将已经读取的数据先记录到缓冲中，然后继续等待运行（一般就是再等待接口可读），等数据到达了再拼接起来。而NIO就是帮你搭建了第二种方法的基础，帮你把缓冲等问题处理好了，这样虽然两个读取都是阻塞的，但是第一种阻塞是网络IO的阻塞，第二种阻塞是本地缓冲IO的阻塞，显然第二种更有确定性、更可靠。特别实在读取数据到下一次等等套接字可读的过程中，你还需要做一些其他的响应或处理时，这个线程就要保障不能长时间阻塞，所以NIO是个很好的解决办法。
>
>总的来说NIO只是在BIO上做一个简单封装，让你专注到实现功能中去，不用再考虑网络IO阻塞的问题。

### NIO是怎么工作的
所有的系统I/O都分为两个阶段：等待就绪和操作。举例来说，读函数，分为等待系统可读和真正的读；同理，写函数分为等待网卡可以写和真正的写。

需要说明的是等待就绪的阻塞是不使用CPU的，是在“空等”；而真正的读写操作的阻塞是使用CPU的，真正在"干活"，而且这个过程非常快，属于memory copy，带宽通常在1GB/s级别以上，可以理解为基本不耗时。

**以socket.read()为例子：**

传统的BIO里面socket.read()，如果TCP RecvBuffer里没有数据，**函数会一直阻塞，直到收到数据**，返回读到的数据。

对于NIO，如果TCP RecvBuffer有数据，就把数据从网卡读到内存，并且返回给用户；**反之则直接返回0**，永远不会阻塞。

最新的AIO(Async I/O)里面会更进一步：不但等待就绪是非阻塞的，就连数据从网卡到内存的过程也是异步的。

**换句话说，BIO里用户最关心“我要读”，NIO里用户最关心"我可以读了"，在AIO模型里用户更需要关注的是“读完了”。**

NIO一个重要的特点是：socket主要的读、写、注册和接收函数，在等待就绪阶段都是非阻塞的，真正的I/O操作是同步阻塞的（消耗CPU但性能非常高）。


#### 结合事件模型使用NIO同步非阻塞特性
回忆BIO模型，之所以需要多线程，是因为在进行I/O操作的时候，一是没有办法知道到底能不能写、能不能读，只能"傻等"，即使通过各种估算，算出来操作系统没有能力进行读写，也没法在`socket.read()`和`socket.write()`函数中返回，这两个函数无法进行有效的中断。所以除了多开线程另起炉灶，没有好的办法利用CPU。

NIO的读写函数可以立刻返回，这就给了我们不开线程利用CPU的最好机会：如果一个连接不能读写（`socket.read()`返回`0`或者`socket.write()`返回`0`），我们可以 **把这件事记下来，记录的方式通常是在`Selector`上注册标记位，然后切换到其它就绪的连接（`channel`）继续进行读写。**

下面具体看下如何利用事件模型单线程处理所有I/O请求：

NIO的主要事件有几个：读就绪、写就绪、有新连接到来。

我们首先需要注册当这几个事件到来的时候所对应的处理器。然后在合适的时机告诉事件选择器：我对这个事件感兴趣。对于写操作，就是写不出去的时候对写事件感兴趣；对于读操作，就是完成连接和系统没有办法承载新读入的数据的时；对于accept，一般是服务器刚启动的时候；而对于connect，一般是connect失败需要重连或者直接异步调用connect的时候。

其次，用一个死循环选择就绪的事件，会执行系统调用（Linux 2.6之前是`select`、`poll`，2.6之后是`epoll`（这几个概念后文专门解释），Windows是IOCP），还会阻塞的等待新事件的到来。新事件到来的时候，会在selector上注册标记位，标示可读、可写或者有连接到来。

注意，`select`是阻塞的，无论是通过操作系统的通知（`epoll`）还是不停的轮询(`select`，`poll`)，这个函数是阻塞的。所以你可以放心大胆地在一个while(true)里面调用这个函数而不用担心CPU空转。

程序大概的模样是：

```java
//IO线程主循环:
class IoThread extends Thread{
    public void run(){
        Channel channel;
        while(channel=Selector.select()){//选择就绪的事件和对应的连接
            if(channel.event==accept){
                registerNewChannelHandler(channel);//如果是新连接，则注册一个新的读写处理器
            }
            if(channel.event==write){
                getChannelHandler(channel).channelWritable(channel);//如果可以写，则执行写事件
            }
            if(channel.event==read){
                getChannelHandler(channel).channelReadable(channel);//如果可以读，则执行读事件
            }
        }
    }
    Map<Channel，ChannelHandler> handlerMap;//所有channel的对应事件处理器
}
```
这个程序很简短，也是最简单的Reactor模式：注册所有感兴趣的事件处理器，单线程轮询选择就绪事件，执行事件处理器。

### BIO、NIO两者的主要区别
| BIO（IO） | NIO |
| :--- | ------|
| 面向流 | 面向缓冲 |
| 阻塞IO | 非阻塞IO |
| 无 | 选择器（selector） |
#### 面向流与面向缓冲
Java BIO面向流意味着每次从流中读一个或多个字节，直至读取所有字节，它们没有被缓存在任何地方。此外，它不能前后移动流中的数据。如果需要前后移动从流中读取的数据，需要先将它缓存到一个缓冲区。 

Java NIO的缓冲导向方法略有不同。数据读取到一个它稍后处理的缓冲区，需要时可在缓冲区中前后移动。这就增加了处理过程中的灵活性。但是，还需要检查是否该缓冲区中包含所有您需要处理的数据。而且，需确保当更多的数据读入缓冲区时，不要覆盖缓冲区里尚未处理的数据。

#### 阻塞与非阻塞IO
Java BIO的各种流是阻塞的。这意味着，当一个线程调用read() 或 write()时，该线程被阻塞，直到有一些数据被读取，或数据完全写入。该线程在此期间不能再干任何事情了。

Java NIO的非阻塞模式，使一个线程从某通道发送请求读取数据，但是它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取。而不是保持线程阻塞，所以直至数据变的可以读取之前，该线程可以继续做其他的事情。 非阻塞写也是如此。一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情。 **线程通常将非阻塞IO的空闲时间用于在其它通道上执行IO操作，所以一个单独的线程现在可以管理多个输入和输出通道（channel）。**

### NIO的核心部分
NIO主要有三大核心部分：Channel(通道)，Buffer(缓冲区), Selector。传统IO基于字节流和字符流进行操作，而NIO基于Channel和Buffer(缓冲区)进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。Selector(选择区)用于监听多个通道的事件（比如：连接打开，数据到达）。因此，单个线程可以监听多个数据通道Channel。
#### 2.1 Channel
首先说一下Channel，国内大多翻译成“通道”。Channel和IO中的Stream(流)是差不多一个等级的。只不过Stream是单向的，譬如：InputStream, OutputStream.而Channel是双向的，既可以用来进行读操作，又可以用来进行写操作。 
NIO中的Channel的主要实现有：
- FileChannel
- DatagramChannel
- SocketChannel
- ServerSocketChannel
这里看名字就可以猜出个所以然来：分别可以对应文件IO、UDP和TCP（Server和Client）。
#### 2.2 Buffer
通常情况下，操作系统的一次写操作分为两步：

将数据从用户空间拷贝到系统空间。
从系统空间往网卡写。同理，读操作也分为两步：
1. 将数据从网卡拷贝到系统空间；
2. 将数据从系统空间拷贝到用户空间。
对于NIO来说，缓存的使用可以使用DirectByteBuffer和HeapByteBuffer。如果使用了DirectByteBuffer，一般来说可以减少一次系统空间到用户空间的拷贝。但Buffer创建和销毁的成本更高，更不宜维护，通常会用内存池来提高性能。

如果数据量比较小的中小应用情况下，可以考虑使用heapBuffer；反之可以用directBuffer。
#### 2.3 Selectors 选择器
Java NIO的选择器允许一个单独的线程同时监视多个通道，可以注册多个通道到同一个选择器上，然后使用一个单独的线程来“选择”已经就绪的通道。这种“选择”机制为一个单独线程管理多个通道提供了可能。

Selector运行单线程处理多个Channel，如果你的应用打开了多个通道，但每个连接的流量都很低，使用Selector就会很方便。例如在一个聊天服务器中。要使用Selector, 得向Selector注册Channel，然后调用它的select()方法。这个方法会一直阻塞到某个注册的通道有事件就绪。一旦这个方法返回，线程就可以处理这些事件，事件的例子有如新的连接进来、数据接收等。

#### 2.4 Proactor与Reactor
I/O 复用机制需要事件分发器（event dispatcher）。 事件分发器的作用，即将那些读写事件源分发给各读写事件的处理者，就像送快递的在楼下喊: 谁谁谁的快递到了， 快来拿吧！开发人员在开始的时候需要在分发器那里注册感兴趣的事件，并提供相应的处理者（event handler)，或者是回调函数；事件分发器在适当的时候，会将请求的事件分发给这些handler或者回调函数。

- 在Reactor模式中，事件分发器等待某个事件或者可应用或个操作的状态发生（比如文件描述符可读写，或者是socket可读写），事件分发器就把这个事件传给事先注册的事件处理函数或者回调函数，由后者来做实际的读写操作。
- 在Proactor模式中，事件处理者（或者代由事件分发器发起）直接发起一个异步读写操作（相当于请求），**而实际的工作是由操作系统来完成的**。发起时，需要提供的参数包括用于存放读到数据的缓存区、读的数据大小或用于存放外发数据的缓存区，以及这个请求完后的回调函数等信息。事件分发器得知了这个请求，它默默等待这个请求的完成，然后转发完成事件给相应的事件处理者或者回调。举例来说，在Windows上事件处理者投递了一个异步IO操作（称为overlapped技术），事件分发器等IO Complete事件完成。这种异步模式的典型实现是基于操作系统底层异步API的，**所以我们可称之为“系统级别”的或者“真正意义上”的异步，因为具体的读写是由操作系统代劳的。**

### NIO存在的问题
- 使用NIO != 高性能，当连接数<1000，并发程度不高或者局域网环境下NIO并没有显著的性能优势。

- NIO并没有完全屏蔽平台差异，它仍然是基于各个操作系统的I/O系统实现的，差异仍然存在。使用NIO做网络编程构建事件驱动模型并不容易，陷阱重重。

- 推荐大家使用成熟的NIO框架，如Netty，MINA等。解决了很多NIO的陷阱，并屏蔽了操作系统的差异，有较好的性能和编程模型。

### 适用范围
- BIO方式适用于连接数目比较小且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，JDK1.4以前的唯一选择，但程序直观简单易理解。

- NIO方式适用于连接数目多且连接比较短（轻操作）的架构，比如聊天服务器，并发局限于应用中，编程比较复杂，JDK1.4开始支持。
## 5.7 反射
反射是框架中常用的方法。

当Spring容器处理<bean>元素时，会使用Class.forName("com.programcreek.Foo")来初始化这个类，并再次使用反射获取<property>元素对应的setter方法，为对象的属性赋值。

### 在 Java 的反射中，Class.forName 和 ClassLoader 的区别
在java中Class.forName()和ClassLoader都可以对类进行加载。ClassLoader就是遵循双亲委派模型最终调用启动类加载器的类加载器，实现的功能是“通过一个类的全限定名来获取描述此类的二进制字节流”，获取到二进制流后放到JVM中。Class.forName()方法实际上也是调用的CLassLoader来实现的。

Class.forName(String className)；这个方法的源码是
```java
@CallerSensitive
    public static Class<?> forName(String className)
                throws ClassNotFoundException {
        Class<?> caller = Reflection.getCallerClass();
        return forName0(className, true, ClassLoader.getClassLoader(caller), caller);
    }
```
#### 应用场景
在我们熟悉的Spring框架中的IOC的实现就是使用的ClassLoader。

而在我们使用JDBC时通常是使用Class.forName()方法来加载数据库连接驱动。这是因为在JDBC规范中明确要求Driver(数据库驱动)类必须向DriverManager注册自己。

以MySQL的驱动为例解释：
```java
public class Driver extends NonRegisteringDriver implements java.sql.Driver {  
    static {  
        try {  
            java.sql.DriverManager.registerDriver(new Driver());  
        } catch (SQLException E) {  
            throw new RuntimeException("Can't register driver!");  
        }  
    }  
    /** 
     * Construct a new driver and register it with DriverManager 
     * @throws SQLException 
     *             if a database error occurs. 
     */ 
    public Driver() throws SQLException {  
        // Required for Class.forName().newInstance()  
    }  
}
```
我们看到Driver注册到DriverManager中的操作写在了静态代码块中，这就是为什么在写JDBC时使用Class.forName()的原因了。


- [在 Java 的反射中，Class.forName 和 ClassLoader 的区别](http://www.importnew.com/29389.html#comment-662769)
