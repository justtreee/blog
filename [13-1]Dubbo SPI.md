# 一、从 Java SPI 说起
> 本节主要参考：
> [Java SPI及Demo](https://juejin.im/post/5c66b65d6fb9a04a0164dbea)
> [官方文档-Dubbo SPI](https://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html)
## 什么是Java SPI

### 介绍

通俗点说：一个功能以jar包的形式向外提供能力，可以在这个功能的 `META-INF/services/` 目录中创建以接口全限定名命名的文件，然后再这个文件中添加这个接口的各个实现，其中的实现就是该功能想要提供出去的能力。当调用方需要该功能时，就可以通过`ServiceLoader` 类动态加载 `META-INF` 中的实现类；

通过这样一个接口命名的文件，实现服务提供服务发现的机制。

> 在面向对象中我们推荐基于接口编程，模块之间基于接口编程，这样的好处显而易见，不在代码中进行硬编码，不同的实现者按照接口规范实现自己内部操作，然后在使用的时候再根据 SPI 的规范去获取对应的服务提供者的服务实现。通过 SPI 服务加载机制进行服务的注册和发现，可以有效的避免在代码中将服务提供者写死。从而可以基于接口编程，实现模块间的解耦。
> 
> 引用链接：https://juejin.im/post/5c66b65d6fb9a04a0164dbea


### 使用
用简化代码举例：

1. 服务提供方

首先定义向外提供服务的接口。
```java
// com.demo.spi.service.OrderService
public interface OrderService {
    int getOrderCountById(int id);
}
```

而其实现可以有多种：
```java
public class AgencyOrderServiceImpl implements OrderService {
    public int getOrderCountById(int id) {
        System.out.println("agency order count is 20");
        return 20;
    }
}
```
或者
```java
public class CustomerOrderServiceImpl implements OrderService {
    public int getOrderCountById(int id) {
        System.out.println("cutomer order count is 10");
        return 10;
    }
}
```
实现可以有多种，但暴露与否，可以配置：
`META-INF/services/com.demo.spi.service.OrderService`
```
com.demo.spi.impl.AgencyOrderServiceImpl
com.demo.spi.impl.CustomerOrderServiceImpl
```
通过**接口全限定名命名的文件**，写入想要暴露出来的相应接口实现，就可以在之后的调用方中调用到，可以是多个，也可以只有一个。

2. 服务调用方
将服务提供方的代码项目打包成jar包。导入到一个服务调用方的项目中。

```java
public class testSPI {
    public static void main(String[] args) {
        ServiceLoader<OrderService> orderServices = ServiceLoader.load(OrderService.class);
        for (OrderService orderService : orderServices) {
            orderService.getOrderCountById(1);
        }
    }
}

```

运行就会通过暴露接口的方法，打印相应的值如果`META-INF/services/com.demo.spi.service.OrderService`中配置了两个方法就会遍历到两个接口实现，输出两行。也就是说`ServiceLoader`动态的通过jar包中的接口配置找到了接口相应的实现类，并且把他记载到了内存中，我们就可以直接调用项目1中提供的两个实现类，并且正确输出。


**实用案例**
> common-logging Apache最早提供的日志的门面接口。只有接口，没有实现。具体方案由各提供商实现， 发现日志提供商是通过扫描 `META-INF/services/org.apache.commons.logging.LogFactory`配置文件，通过读取该文件的内容找到日志提工商实现类。只要我们的日志实现里包含了这个文件，并在文件里      制定LogFactory工厂接口的实现类即可。 JDBC jdbc4.0以前， 开发人员还需要基于Class.forName("xxx")的方式来装载驱动。 创建连接： DriverManage.getConnection()中，有Connection con = aDriver.driver.connect(url, info); driver成员变量，是java.sql.Driver接口，Java对外公开的一个加载驱动接口，Java并未实现，至于实现这个接口由各个Jdbc厂商去实现。 如MySQL，mysql-connector-java-5.1.38.jar包下面META-INF.services包下有个java.sql.Driver文件打开文件有下面两行 com.mysql.jdbc.Driver com.mysql.fabric.jdbc.FabricMySQLDriver     
> 
> 链接：https://juejin.im/post/5c66b65d6fb9a04a0164dbea


### 原理
> 主要讨论 `ServiceLoader` 的实现原理
> 本节参考链接：[ServiceLoader源代码分析](https://www.jianshu.com/p/a6073e9f8cb4)


### 优缺点
**优点：**
使用Java SPI机制的优势是实现解耦，使得第三方服务模块的装配控制的逻辑与调用者的业务代码分离，而不是耦合在一起。应用程序可以根据实际业务情况启用框架扩展或替换框架组件。

**缺点：**
虽然ServiceLoader也算是使用的延迟加载，但是基本只能通过遍历全部获取，也就是接口的实现类全部加载并实例化一遍。如果你并不想用某些实现类，它也被加载并实例化了，这就造成了浪费。获取某个实现类的方式不够灵活，只能通过Iterator形式获取，不能根据某个参数来获取对应的实现类。

多个并发多线程使用ServiceLoader类的实例是不安全的。

# 二、dubbo SPI 又是什么
> 本节主要参考：
> > [官方文档-Dubbo SPI](https://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html)
dubbo 也用了 spi 思想，不过没有用 jdk 的 spi 机制，是自己实现的一套 spi 机制。

## 1. Dubbo SPI 的简单使用
使用上和Java SPI 很接近

先来简单的看一下，dubbo spi最基础的用法怎么用。

- 第一步：建立一个接口和多个实现类。
- 第二步：在META-INF/dubbo/internal目录下建立配置文件
- 第三步：通过ExtensionLoader等进行调用。

我们以dubbo自带的代码作为例子，示例如下：

首先定义接口
> com.alibaba.dubbo.common.extensionloader.ext1.SimpleExt
```java
package com.alibaba.dubbo.common.extensionloader.ext1;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

/**
 * Simple extension, has no wrapper
 */
@SPI("impl1")
public interface SimpleExt {
    // @Adaptive example, do not specify a explicit key.
    @Adaptive
    String echo(URL url, String s);

    @Adaptive({"key1", "key2"})
    String yell(URL url, String s);

    // no @Adaptive
    String bang(URL url, int i);
}
```

通过配置文件配置这个接口的各个实现
> META-INF/dubbo/internal/com.alibaba.dubbo.common.extensionloader.ext1.SimpleExt
```txt
# Comment 1
impl1=com.alibaba.dubbo.common.extensionloader.ext1.impl.SimpleExtImpl1#Hello World
impl2=com.alibaba.dubbo.common.extensionloader.ext1.impl.SimpleExtImpl2  # Comment 2
   impl3=com.alibaba.dubbo.common.extensionloader.ext1.impl.SimpleExtImpl3 # with head space
```

通过ExtensionLoader等进行调用。这里以@Test为例
```java
    @Test
    public void test_getExtension() throws Exception {
        assertTrue(ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension("impl1") instanceof SimpleExtImpl1);
        assertTrue(ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension("impl2") instanceof SimpleExtImpl2);
    }
```

**注意点：**

1. 接口得加上@SPI注解。
2. 配置文件的目录是固定的，但是不是一个，具体几个，往下看~
3. 配置文件名为接口全名。
4. 配置文件内容为Key和接口实现类全路径的一个映射。

可以看到，基础使用非常简单。可以先看看这部分的源码是怎么做的呢。

先合理的猜测一下，可能是通过配置文件根据类的全路径限定名加载，并实例化，然后放在一个Map，通过映射名获取。

# 三、Dubbo SPI 源码阅读

从上面的`@Test`我们可以看到`getExtensionLoader`，从这里出发。

## 1. 第一步：根据接口类获取对应的Loader实例：

```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#getExtensionLoader
public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
    if (type == null)
        throw new IllegalArgumentException("Extension type == null");
    if (!type.isInterface()) {
        throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
    }
    if (!withExtensionAnnotation(type)) {
        throw new IllegalArgumentException("Extension type(" + type +
                ") is not extension, because WITHOUT @" + SPI.class.getSimpleName() + " Annotation!");
    }
    ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type); // 静态缓存中获取
    if (loader == null) {
        EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
        loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
    }
    return loader;
}
```

从静态缓存中，根据接口类，获取对应的Loader，如果不存在，则创建，放在缓存里。

流程很简单，至于Loader具体创建流程，在后续会仔细说明。

## 2. 第二步：根据实现类在配置文件内的映射名称获取具体实现类：
然后我们再看获得了loader之后是怎么load出Extension的：
也就是`@Test`中的
```java
ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension("impl1")
```
具体看看其中的getExtension
```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#getExtension
public T getExtension(String name) {
    if (name == null || name.length() == 0)
        throw new IllegalArgumentException("Extension name == null");
    if ("true".equals(name)) {
        // 如果映射名是true，则返回默认扩展类
        return getDefaultExtension();
    }

    // 通过putIfAbsent保证Holder的单例，通过双重检查保证instance的单例
    Holder<Object> holder = cachedInstances.get(name);
    if (holder == null) {
        cachedInstances.putIfAbsent(name, new Holder<Object>());
        holder = cachedInstances.get(name);
    }
    Object instance = holder.get();
    if (instance == null) {
        synchronized (holder) {
            instance = holder.get();
            if (instance == null) {
                // 重要的createExtension
                instance = createExtension(name);
                holder.set(instance);
            }
        }
    }
    return (T) instance;
}
```
1. 如果映射名是true，则返回默认扩展类。--> 默认扩展类是什么？等会看。
2. 通过putIfAbsent保证Holder的单例，通过双重检查保证instance的单例。--> 有个问题，为什么不直接通过putIfAbsent直接对instance创建呢，跟第一步一样？ 
    
    > 暂留个疑问在这里，目前我能分析出来的是，减少多次createExtension()，提高性能。(感觉有点勉强)；
3. 来看看重要的createExtension方法。

### (1) createExtension

```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#createExtension
private T createExtension(String name) {
    // 1.  获取这个接口所有的扩展实现类
    Class<?> clazz = getExtensionClasses().get(name);
    if (clazz == null) {
        throw findException(name);
    }
    try {
        T instance = (T) EXTENSION_INSTANCES.get(clazz);
        if (instance == null) {
            EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
            instance = (T) EXTENSION_INSTANCES.get(clazz);
        }
        injectExtension(instance);
        Set<Class<?>> wrapperClasses = cachedWrapperClasses;
        if (wrapperClasses != null && !wrapperClasses.isEmpty()) {
            for (Class<?> wrapperClass : wrapperClasses) {
                instance = injectExtension((T) wrapperClass.getConstructor(type).newInstance(instance));
            }
        }
        return instance;
    } catch (Throwable t) {
        throw new IllegalStateException("Extension instance(name: " + name + ", class: " +
                type + ")  could not be instantiated: " + t.getMessage(), t);
    }
}
```

#### i. 获取接口所有扩展实现类 getExtensionClasses

```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#getExtensionClasses
private Map<String, Class<?>> getExtensionClasses() {
    // 典型的双重检查
    Map<String, Class<?>> classes = cachedClasses.get();
    if (classes == null) {
        synchronized (cachedClasses) {
            classes = cachedClasses.get();
            if (classes == null) {
                classes = loadExtensionClasses();
                cachedClasses.set(classes);
            }
        }
    }
    return classes;
}
```
还是典型的双重检查，后续不再赘述。看看`LoadExtensionClasses`，见名知意，就是加载扩展实现类。
`LoadExtensionClasses()`紧随其后；

```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#loadExtensionClasses
private Map<String, Class<?>> loadExtensionClasses() {
    // 1. 获取SPI注解的默认的扩展名称
    final SPI defaultAnnotation = type.getAnnotation(SPI.class);
    if (defaultAnnotation != null) {
        String value = defaultAnnotation.value();
        if ((value = value.trim()).length() > 0) {
            String[] names = NAME_SEPARATOR.split(value);
            if (names.length > 1) {
                throw new IllegalStateException("more than 1 default extension name on extension " + type.getName()
                        + ": " + Arrays.toString(names));
            }
            if (names.length == 1) cachedDefaultName = names[0];
        }
    }
    Map<String, Class<?>> extensionClasses = new HashMap<String, Class<?>>();
    // 2. 从以下三个目录里加载类
    loadDirectory(extensionClasses, DUBBO_INTERNAL_DIRECTORY);
    loadDirectory(extensionClasses, DUBBO_DIRECTORY);
    loadDirectory(extensionClasses, SERVICES_DIRECTORY);
    return extensionClasses;
}
```

做了两件事。
1. 获取SPI注解的默认的扩展名称。这也是上文提到的默认扩展类的来源。SPI注解里的value值(`@SPI("impl1")`)，也就对应默认扩展类。
2. 从各个目录里加载类。为以下三个类，也就是说配置文件放在以下三个目录内，都能够找到。

```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#SERVICES_DIRECTORY
private static final String SERVICES_DIRECTORY = "META-INF/services/";
private static final String DUBBO_DIRECTORY = "META-INF/dubbo/";
private static final String DUBBO_INTERNAL_DIRECTORY = DUBBO_DIRECTORY + "internal/";
```
也就是META-INF里的三个文件，尤其以internal中为例，里面描述的就是，我们加上了@SPI注解接口之后的，对应实现类的全限定类名。
#### ii. 具体加载某个文件内的代码
我们看看是如何加载的，点选`loadDirectory(extensionClasses, SERVICES_DIRECTORY);`中的`loadDirectory`。

```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#loadDirectory
private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir) {
    String fileName = dir + type.getName();
    try {
        Enumeration<java.net.URL> urls;
        // 找到类加载器，然后通过文件名转换成URL资源，对URL资源进行迭代。
        // 1. 找到类加载器
        ClassLoader classLoader = findClassLoader();
        if (classLoader != null) {
            // 2. 通过文件名转换成URL资源
            urls = classLoader.getResources(fileName);
        } else {
            urls = ClassLoader.getSystemResources(fileName);
        }
        if (urls != null) {
            while (urls.hasMoreElements()) {
                // 3. 对URL资源进行迭代
                java.net.URL resourceURL = urls.nextElement();
                loadResource(extensionClasses, classLoader, resourceURL);
            }
        }
    } catch (Throwable t) {
        // ...省略...
    }
}
// com.alibaba.dubbo.common.extension.ExtensionLoader#loadResource
private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, java.net.URL resourceURL) {
    try {
        // 从文件内读取数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), "utf-8"));
        try {
            // 每次读取一行
            String line;
            while ((line = reader.readLine()) != null) {
                // 遇到#(注释开头)则不做处理。
                final int ci = line.indexOf('#');
                if (ci >= 0) line = line.substring(0, ci);
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        // 通过 "=" 分隔符区分，前者为name，后者为line(同样也是类全路径)
                        String name = null;
                        int i = line.indexOf('=');
                        if (i > 0) {
                            name = line.substring(0, i).trim();
                            line = line.substring(i + 1).trim();
                        }
                        if (line.length() > 0) {
                            // 根据line，通过ClassForName获取Class对象
                            // 调用loadClass方法
                            loadClass(extensionClasses, resourceURL, Class.forName(line, true, classLoader), name);
                        }
                    } catch (Throwable t) {
                        // ...省略...
                    }
                }
            }
        } finally {
            reader.close();
        }
    } catch (Throwable t) {
        // ...省略...
}
```
1. 找到类加载器，然后通过文件名转换成URL资源，对URL资源进行迭代。
2. 从文件内读取数据，每次读取一行。
    - 遇到`#`(注释开头)则不做处理。
    - 通过`=`分隔符区分，前者为`name`，后者为`line`(同样也是类全路径)。
    - 根据`line`，通过`ClassForName`获取Class对象。
    - 调用`loadClass`方法。

#### iii. 加载类 loadClass
```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#loadClass
private void loadClass(Map<String, Class<?>> extensionClasses, java.net.URL resourceURL, Class<?> clazz, String name) throws NoSuchMethodException {
        // 1. 校验clazz是否是type的子类和子接口
        if (!type.isAssignableFrom(clazz)) {
            // ...省略
        }
        // 2.0 如果clazz上面有Adaptive注解，则将其缓存起来。
        if (clazz.isAnnotationPresent(Adaptive.class)) {
            if (cachedAdaptiveClass == null) {
                cachedAdaptiveClass = clazz;
            } else if (!cachedAdaptiveClass.equals(clazz)) {
                // 2.1 如果发现已有缓存，则对比是否相同，如果不相同，则报错
                // throw new ...省略
            }
        // 3.0 判断是否是isWrapperClass，见名知意，是否是包装类
        } else if (isWrapperClass(clazz)) {
            Set<Class<?>> wrappers = cachedWrapperClasses;
            // 3.1 如果是的话，也将其单独缓存起来。
            if (wrappers == null) {
                cachedWrapperClasses = new ConcurrentHashSet<Class<?>>();
                wrappers = cachedWrapperClasses;
            }
            // 3.2 但是这里不同的是，可以缓存多个包装类。
            wrappers.add(clazz);
        } else {
            // 4.0 终于到了其他的正常分支内了
            clazz.getConstructor();
            // 4.1 校验名字是否为空
            if (name == null || name.length() == 0) {
                name = findAnnotationName(clazz);
                if (name.length() == 0) {
                    throw new IllegalStateException("No such extension name for the class " + clazz.getName() + " in the config " + resourceURL);
                }
            }
            // 4.2 Dubbo配置文件内允许一个实现类对应多个name，name之间用,分隔即可
            String[] names = NAME_SEPARATOR.split(name);
            if (names != null && names.length > 0) {
                Activate activate = clazz.getAnnotation(Activate.class);
                if (activate != null) {
                    cachedActivates.put(names[0], activate);
                }
                // 4.3 names循环，分别放在两个map内，一个是name -> class，一个是class -> name
                for (String n : names) {
                    if (!cachedNames.containsKey(clazz)) {
                        // name -> class
                        cachedNames.put(clazz, n);
                    }
                    Class<?> c = extensionClasses.get(n);
                    if (c == null) {
                        // class -> name
                        extensionClasses.put(n, clazz);
                    } else if (c != clazz) {
                        throw new IllegalStateException("Duplicate extension " + type.getName() + " name " + n + " on " + c.getName() + " and " + clazz.getName());
                    }
                }
            }
        }
    }
```
先将入参说明一下
- `extensionClasses` 是缓存配置名和对应类的map对象。
- 然后是URL资源`resourceURL`
- 接口实现类的Class对象`clazz`
- 接口实现类的配置名称`name`
- 内部的全局变量`type`是接口Class文件。

1. 校验clazz是否是type的子类和子接口
2. 如果clazz上面有Adaptive注解，则将其缓存起来。如果发现已有缓存，则对比是否相同，如果不相同，则报错。根据报错信息可以看到，dubbo里，每个接口只允许有一个实现类注解Adaptive。
    - 那么Adaptive注解干嘛的呢？ 现在只知道，带有Adaptive注解的实现类被单独缓存起来了。
3. 判断是否是isWrapperClass，见名知意，是否是包装类。如果是的话，也将其单独缓存起来。但是这里不同的是，可以缓存多个包装类。
    - 看看isWrapperClass， 很有意思，直接通过实现类的class，获取带有type类型的构造器，如果不报错，就返回成功，报错就返回失败。也就是说，如果实现类中含有带有接口类型的构造器，就认为是包装类。
    - 至于这个包装类干嘛用的。同样，后面再看。先弄清楚整个流程。
4. 终于到了其他的正常分支内了
    - 校验名字是否为空
    - 对`name`做了个`split()`，这就奇怪了，为什么要对name做分隔呢，看看NAME_SEPARATOR就知道了。
    - `Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");`Dubbo配置文件内允许一个实现类对应多个name，name之间用,分隔即可。通过,分隔的每个名称都能获取到对应的类。
    - 下面就很简单了，names循环，分别放在两个map内，一个是name -> class，一个是class -> name (存放第一个name)。

#### iiii. 回顾入口 ExtensionLoader.createExtension()
```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#createExtension
private T createExtension(String name) {
    // 通过getExtensionClasses拿到class对象
    Class<?> clazz = getExtensionClasses().get(name);
    if (clazz == null) {
        throw findException(name);
    }
    try {
        // EXTENSION_INSTANCES，缓存class文件和对应Object的映射， 这样避免重复实例化
        T instance = (T) EXTENSION_INSTANCES.get(clazz);
        if (instance == null) {
            EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
            instance = (T) EXTENSION_INSTANCES.get(clazz);
        }
        // 见名知意，注入
        injectExtension(instance);
        Set<Class<?>> wrapperClasses = cachedWrapperClasses;
        if (wrapperClasses != null && !wrapperClasses.isEmpty()) {
            for (Class<?> wrapperClass : wrapperClasses) {
                instance = injectExtension((T) wrapperClass.getConstructor(type).newInstance(instance));
            }
        }
        return instance;
    } catch (Throwable t) {
        throw new IllegalStateException("Extension instance(name: " + name + ", class: " +
                type + ")  could not be instantiated: " + t.getMessage(), t);
    }
}
```
1. 好的，通过getExtensionClasses拿到class对象后继续看。
2. 又有一个缓存来了。EXTENSION_INSTANCES，缓存class文件和对应Object的映射， 这样避免重复实例化。
3. 实例化完成后，来了个injectExtension，同样，见名知意，注入。来一起看看，注入什么。

```java
private T injectExtension(T instance) {
    try {
        if (objectFactory != null) {
            // 拿到实例的所有方法
            for (Method method : instance.getClass().getMethods()) {
                // 对于以Set开头，参数只有一位的公共方法进行处理
                if (method.getName().startsWith("set")
                        && method.getParameterTypes().length == 1
                        && Modifier.isPublic(method.getModifiers())) {
                    /**
                     * Check {@link DisableInject} to see if we need auto injection for this property
                     */
                    if (method.getAnnotation(DisableInject.class) != null) {
                        continue;
                    }
                    // 通过set的method获取属性名
                    Class<?> pt = method.getParameterTypes()[0];
                    try {
                        // 获取属性类
                        String property = method.getName().length() > 3 ? method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4) : "";
                        // 通过属性名属性类，从objectFactory里面拿到属性实例
                        Object object = objectFactory.getExtension(pt, property);
                        if (object != null) {
                            // 然后通过反射调用set方法进行注入。
                            method.invoke(instance, object);
                        }
                    } catch (Exception e) {
                        // 省略
                    }
                }
            }
        }
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
    }
    return instance;
}
```
`ObjectFactory`是什么呢？ 对象工厂，用来创建对象的，这个在哪里实例化的呢。其实在创建Load实例的时候就创建了。到时候一起详细说说。

- 然后拿到实例的所有方法，对于以Set开头，参数只有一位的公共方法进行处理。
- 通过set的method获取属性名，加上属性类。
- 通过属性名属性类， 从objectFactory里面拿到属性实例。
- 然后通过反射调用set方法进行注入。

是不是就是一个简约版本的IOC，就是对属性值完成一个注入。

> 这里有一个问题。如果出现了循环依赖，是否能够解决？--> 留在后面解答。

```java
// com.alibaba.dubbo.common.extension.ExtensionLoader#createExtension
private T createExtension(String name) {
    // ...
    injectExtension(instance);
    Set<Class<?>> wrapperClasses = cachedWrapperClasses;
    if (wrapperClasses != null && !wrapperClasses.isEmpty()) {
        for (Class<?> wrapperClass : wrapperClasses) {
            instance = injectExtension((T) wrapperClass.getConstructor(type).newInstance(instance));
        }
    }
    // ...
}
```
注入完了以后，出现了`cachedWrapperClasses`，是不是很熟悉。这就是上文提到的，如果加载的时候，发现是包装类，就会缓存到`cachedWrapperClasses`.

那这里把包装类拿来干嘛呢？ 
```java
for (Class<?> wrapperClass : wrapperClasses) {
    instance = injectExtension((T) wrapperClass.getConstructor(type).newInstance(instance));
}
```
这段代码什么意思呢？ 意思就是把包装类拿到带有`type`的构造器，然后将我们之前的`instance`当做构造参数，进行实例化。然后做依赖注入。然后赋值给`instance`。

每个包装类都这样来一遍。

画个图示意一下，假如原有`instance`是`impl1`，有两个包装类`wrapper1`，`wrapper2`。

那么整个流程示例如下：
【TODO】
![]()

> 可能有疑问了，这个包装类有什么用呢？ 

整个SPI的创建过程就结束了。逻辑不复杂，就是写的方法层次多了点，每个层次都做了额外的增强功能。

方法简单梳理一下：
![](/images/13/dubbo-spi.png)

还有几个问题：

1. 获取默认扩展类是什么？  想必已经知道了。
2. 包装类的作用是什么？ 对比Spring就很明显了。Spring的两大特性。IOC -AOP。 包装类就是做了一些aop的事情。在后续遇到了具体例子会说说在dubbo里的运用。
3. @Adaptive作用什么？
4. 这dubbo的SPI似乎看起来也就这样啊，跟工厂区别也不大啊。。