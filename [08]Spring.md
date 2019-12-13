# 八、Spring

## [Spring IOC、DI、AOP原理和实现](https://blog.csdn.net/mdcmy/article/details/8542277)

### 1. Spring IOC原理
[解释1：](https://zhuanlan.zhihu.com/p/31070962)
> IOC的意思是控件反转也就是由容器控制程序之间的关系，这也是spring的优点所在，把控件权交给了外部容器，之前的写法，由程序代码直接操控，而现在控制权由应用代码中转到了外部容器，控制权的转移是所谓反转。换句话说之前用new的方式获取对象，现在由spring给你至于怎么给你就是di了。

[解释2：](https://blog.csdn.net/mdcmy/article/details/8542277)  
>  IOC的意思是控件反转也就是由容器控制程序之间的关系，把控件权交给了外部容器，之前的写法，由程序代码直接操控，而现在控制权由应用代码中转到了外部容器，控制权的转移是所谓反转。网上有一个很形象的比喻：

>我们是如何找女朋友的？常见的情况是，我们到处去看哪里有长得漂亮身材又好的mm，然后打听她们的兴趣爱好、qq号、电话号、ip号、iq号………，想办法认识她们，投其所好送其所要，然后嘿嘿……这个过程是复杂深奥的，我们必须自己设计和面对每个环节。传统的程序开发也是如此，在一个对象中，如果要使用另外的对象，就必须得到它（自己new一个，或者从JNDI中查询一个），使用完之后还要将对象销毁（比如Connection等），对象始终会和其他的接口或类藕合起来。那么IoC是如何做的呢？有点像通过婚介找女朋友，在我和女朋友之间引入了一个第三者：婚姻介绍所。婚介管理了很多男男女女的资料，我可以向婚介提出一个列表，告诉它我想找个什么样的女朋友，比如长得像李嘉欣，身材像林熙雷，唱歌像周杰伦，速度像卡洛斯，技术像齐达内之类的，然后婚介就会按照我们的要求，提供一个mm，我们只需要去和她谈恋爱、结婚就行了。简单明了，如果婚介给我们的人选不符合要求，我们就会抛出异常。整个过程不再由我自己控制，而是有婚介这样一个类似容器的机构来控制。Spring所倡导的开发方式就是如此，所有的类都会在spring容器中登记，告诉spring你是个什么东西，你需要什么东西，然后spring会在系统运行到适当的时候，把你要的东西主动给你，同时也把你交给其他需要你的东西。所有的类的创建、销毁都由 spring来控制，也就是说控制对象生存周期的不再是引用它的对象，而是spring。对于某个具体的对象而言，以前是它控制其他对象，现在是所有对象都被spring控制，所以这叫控制反转。


### 2. 什么是DI机制？
[解释1：](https://zhuanlan.zhihu.com/p/31070962)
> 这里说DI又要说到IOC，依赖注入（Dependecy Injection）和控制反转（Inversion of Control）是同一个概念，具体的讲：当某个角色 需要另外一个角色协助的时候，在传统的程序设计过程中，通常由调用者来创建被调用者的实例。但在spring中 创建被调用者的工作不再由调用者来完成，因此称为控制反转。创建被调用者的工作由spring来完成，然后注入调用者 因此也称为依赖注入。  
> spring以动态灵活的方式来管理对象 ， 注入的四种方式：  1. 接口注入  2. Setter方法注入   3. 构造方法注入  4.注解注入(@Autowire)

[解释2：](https://blog.csdn.net/mdcmy/article/details/8542277)  
>  IoC的一个重点是在系统运行中，动态的向某个对象提供它所需要的其他对象。这一点是通过DI（Dependency Injection，
依赖注入）来实现的。比如对象A需要操作数据库，以前我们总是要在A中自己编写代码来获得一个Connection对象，有了 
spring我们就只需要告诉spring，A中需要一个Connection，至于这个Connection怎么构造，何时构造，A不需要知道。
在系统运行时，spring会在适当的时候制造一个Connection，然后像打针一样，注射到A当中，这样就完成了对各个对象之间关系
的控制。A需要依赖 Connection才能正常运行，而这个Connection是由spring注入到A中的，依赖注入的名字就这么来的。

----

### 3. 什么是 AOP 面向切面编程
- AOP，即面向切面编程，采用横向抽取机制，取代了传统的纵向继承体系重复性代码。是什么意思呢？
- 我们知道，使用面向对象编程有一些弊端，当需要为多个不具有继承关系的对象引入同一个公共行为是，例如日志，安全检测等，我们只有在每一个对象中引入公共行为，这样程序中就出现了很多重复代码，加大了程序的维护难度。所以有了面向对象编程的补充AOP，它关注的方向是横向的，而不是面向对象那样的纵向。

[解释1:](https://blog.csdn.net/simonchi/article/details/10537453)  
> IOC依赖注入，和AOP面向切面编程，这两个是Spring的灵魂。  
> 主要用到的设计模式有工厂模式和代理模式。  
> - IOC就是典型的工厂模式，通过sessionfactory去注入实例。  
> - AOP就是典型的代理模式的体现。

- **在Spring中使用AspecJ实现AOP**  
1. 我们一个需要被拦截增强的bean（也就是需要面向的切入点，切面），这个bean可能是满足业务需要的核心逻辑，例如其中的test方法封装这核心业务，如果我们想在这个test前后加入日志调试，那直接修改源码肯定是不合适的。但spring的aop能做到这点。
	```java
	public class Book {
    	public void test(){
        	System.out.println("Book test.....");
    	}
	}
	```
2. 创建增强类，采用的是基于`@AspectJ`的注解，例如前置增强`@Before`、后置增强，环绕增强等。
	```java
	public class MyBook {
    public void before1(){
     		System.out.println("前置增强.....");
    }//预计先输出这个，再输出Book中的test
	```
3. 之后再在xml配置文件中作出声明，测试就能成功。


## Spring IoC容器的初始化(TODO)



## [Spring AOP 的实现机制](http://importnew.com/28342.html)（TODO）
实现的两种代理实现机制，JDK动态代理和CGLIB动态代理。
### 代理机制-CGLIB
1. 静态代理
	- 静态代理在使用时,需要定义接口或者父类
	- 被代理对象与代理对象一起实现相同的接口或者是继承相同父类

>但是我们知道，实现接口，则必须实现它所有的方法。方法少的接口倒还好，但是如果恰巧这个接口的方法有很多呢，例如List接口。 更好的选择是： 使用动态代理！
2. JDK动态代理
	- 动态代理对象特点:
	- 代理对象,不需要实现接口
	- 代理对象的生成,是利用JDK的API,动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
	----
	- JDK实现代理只需要使用newProxyInstance方法
	- JDK动态代理局限性
	- 其代理对象必须是某个接口的实现，它是通过在运行期间床i教案一个接口的实现类来完成目标对象的代理。但事实上并不是所有类都有接口，对于没有实现接口的类，便无法使用该方方式实现动态代理。
> 如果Spring识别到所代理的类没有实现Interface，那么就会使用CGLib来创建动态代理，原理实际上成为所代理类的子类。
3. Cglib动态代理
	
	- 上面的静态代理和动态代理模式都是要求目标对象是实现一个接口的目标对象,Cglib代理,也叫作子类代理,是基于asm框架，实现了无反射机制进行代理，利用空间来换取了时间，代理效率高于jdk ,它是在内存中构建一个子类对象从而实现对目标对象功能的扩展. 它有如下特点:
	- JDK的动态代理有一个限制,就是使用动态代理的对象必须实现一个或多个接口,如果想代理没有实现接口的类,就可以使用Cglib实现.
	- Cglib是一个强大的高性能的代码生成包,它可以在运行期扩展java类与实现java接口.它广泛的被许多AOP的框架使用,例如Spring AOP和synaop,为他们提供方法的interception(拦截)
	- Cglib包的底层是通过使用一个小而块的字节码处理框架ASM来转换字节码并生成新的类.不鼓励直接使用ASM,因为它要求你必须对JVM内部结构包括class文件的格式和指令集都很熟悉.
	- 目标对象的方法如果为final/static,那么就不会被拦截,即不会执行目标对象额外的业务方法.

	----
对比JDK动态代理和CGLib代理，在实际使用中发现CGLib在创建代理对象时所花费的时间却比JDK动态代理要长，所以CGLib更适合代理不需要频繁实例化的类。


------------

## [Bean的加载](https://www.jianshu.com/p/5fd1922ccab1)
整个bean加载的过程步骤相对繁琐，主要步骤有以下几点：
1. 转换beanName
	要知道平时开发中传入的参数name可能只是别名，也可能是FactoryBean，所以需要进行解析转换，一般会进行以下解析：  
	（1）消除修饰符，比如name="&test"，会去除&使name="test"；  
	（2）取alias表示的最后的beanName，比如别名test01指向名称为test02的bean则返回test02。

2. 从缓存中加载实例
实例在Spring的同一个容器中只会被创建一次，后面再想获取该bean时，就会尝试从缓存中获取；如果获取不到的话再从singletonFactories中加载。

3. 实例化bean
缓存中记录的bean一般只是最原始的bean状态，这时就需要对bean进行实例化。如果得到的是bean的原始状态，但又要对bean进行处理，这时真正需要的是工厂bean中定义的factory-method方法中返回的bean，上面源码中的getObjectForBeanInstance就是来完成这个工作的。

4. 检测parentBeanFacotory
从源码可以看出如果缓存中没有数据会转到父类工厂去加载，源码中的!containsBeanDefinition(beanName)就是检测如果当前加载的xml配置文件中不包含beanName所对应的配置，就只能到parentBeanFacotory去尝试加载bean。

5. 存储XML配置文件的GernericBeanDefinition转换成RootBeanDefinition之前的文章介绍过XML配置文件中读取到的bean信息是存储在GernericBeanDefinition中的，但Bean的后续处理是针对于RootBeanDefinition的，所以需要转换后才能进行后续操作。

6. 初始化依赖的bean
这里应该比较好理解，就是bean中可能依赖了其他bean属性，在初始化bean之前会先初始化这个bean所依赖的bean属性。

7. 创建bean
Spring容器根据不同scope创建bean实例。
整个流程就是如此，下面会讲解一些重要步骤的源码。

## Bean生命周期
- [Spring Bean的生命周期](https://cnblogs.com/zrtqsk/p/3735273.html)
- [Bean的生命周期](http://importnew.com/22350.html)
- [Spring中Bean的生命周期是怎样的？zhihu](https://zhihu.com/question/38597960)

	![1](https://pic1.zhimg.com/80/v2-baaf7d50702f6d0935820b9415ff364c_hd.jpg)
-----


### 1.  实例化Bean
对于BeanFactory容器，当客户向容器请求一个尚未初始化的bean时，或初始化bean的时候需要注入另一个尚未初始化的依赖时，容器就会调用createBean进行实例化。 对于ApplicationContext容器，当容器启动结束后，便实例化所有的bean。 容器通过获取BeanDefinition对象中的信息进行实例化。并且这一步仅仅是简单的实例化，并未进行依赖注入。 实例化对象被包装在BeanWrapper对象中，BeanWrapper提供了设置对象属性的接口，从而避免了使用反射机制设置属性。
### 2. 设置对象属性（依赖注入）
实例化后的对象被封装在BeanWrapper对象中，并且此时对象仍然是一个原生的状态，并没有进行依赖注入。 紧接着，Spring根据BeanDefinition中的信息进行依赖注入。 并且通过BeanWrapper提供的设置属性的接口完成依赖注入。
### 3. 注入Aware接口
紧接着，Spring会检测该对象是否实现了xxxAware接口，并将相关的xxxAware实例注入给bean。
### 4. BeanPostProcessor
当经过上述几个步骤后，bean对象已经被正确构造，但如果你想要对象被使用前再进行一些自定义的处理，就可以通过BeanPostProcessor接口实现。 该接口提供了两个函数：postProcessBeforeInitialzation( Object bean, String beanName ) 当前正在初始化的bean对象会被传递进来，我们就可以对这个bean作任何处理。 这个函数会先于InitialzationBean执行，因此称为前置处理。 所有Aware接口的注入就是在这一步完成的。postProcessAfterInitialzation( Object bean, String beanName ) 当前正在初始化的bean对象会被传递进来，我们就可以对这个bean作任何处理。 这个函数会在InitialzationBean完成后执行，因此称为后置处理。
### 5. InitializingBean与init-method
当BeanPostProcessor的前置处理完成后就会进入本阶段。 InitializingBean接口只有一个函数：afterPropertiesSet()这一阶段也可以在bean正式构造完成前增加我们自定义的逻辑，但它与前置处理不同，由于该函数并不会把当前bean对象传进来，因此在这一步没办法处理对象本身，只能增加一些额外的逻辑。 若要使用它，我们需要让bean实现该接口，并把要增加的逻辑写在该函数中。然后Spring会在前置处理完成后检测当前bean是否实现了该接口，并执行afterPropertiesSet函数。当然，Spring为了降低对客户代码的侵入性，给bean的配置提供了init-method属性，该属性指定了在这一阶段需要执行的函数名。Spring便会在初始化阶段执行我们设置的函数。init-method本质上仍然使用了InitializingBean接口。
### 6. DisposableBean和destroy-method
和init-method一样，通过给destroy-method指定函数，就可以在bean销毁前执行指定的逻辑。


## Spring是如果解决循环依赖
- 第1种，解决构造器中对其它类的依赖，创建A类需要构造器中初始化B类，创建B类需要构造器中初始化C类，创建C类需要构造器中又要初始化A类，因而形成一个死循环，Spring的解决方案是，把创建中的Bean放入到一个“当前创建Bean池”中，在初始化类的过程中，如果发现Bean类已存在，就抛出一个“BeanCurrentInCreationException”的异常

- 第2种，解决setter对象的依赖，就是说在A类需要设置B类，B类需要设置C类，C类需要设置A类，这时就出现一个死循环，spring的解决方案是，初始化A类时把A类的初始化Bean放到缓存中，然后set B类，再把B类的初始化Bean放到缓存中，然后set  C类，初始化C类需要A类和B类的Bean，这时不需要初始化，只需要从缓存中取出即可.该种仅对single作用的Bean起作用，因为prototype作用的Bean，Spring不对其做缓存
