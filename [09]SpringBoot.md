# 九、Spring Boot
[Spring Boot 启动、事件通知与配置加载原理](https://my.oschina.net/weiwei02/blog/1592967)  

源码解读`@SpringBootApplication`与`SpringApplication.run`
## 一. @SpringBootApplication

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration	// 从源代码中得知 @SpringBootApplication 被 @SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan 注解
@EnableAutoConfiguration	// 所修饰，换言之 Springboot 提供了统一的注解来替代以上三个注解，简化程序的配置。下面解释一下各注解的功能。
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
	@AliasFor(annotation = EnableAutoConfiguration.class)
	Class<?>[] exclude() default {};

	@AliasFor(annotation = EnableAutoConfiguration.class)
	String[] excludeName() default {};

	@AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
	String[] scanBasePackages() default {};

	@AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
	Class<?>[] scanBasePackageClasses() default {};

}
```
### 1. @SpringBootConfiguration
进入之后可以看到这个注解是继承了 `@Configuration` 的，二者功能也一致，标注当前类是配置类，并会将当前类内声明的一个或多个以@Bean注解标记的方法的实例纳入到srping容器中，并且实例名就是方法名。
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {

}
```
- 以下摘自Spring文档翻译
> `@Configuration` 是一个类级注释，指示对象是一个bean定义的源。 `@Configuration` 类通过 `@Bean` 注解的公共方法声明bean。  
> `@Bean` 注释是用来表示一个方法实例化，配置和初始化是由 Spring IoC 容器管理的一个新的对象。

通俗的讲 `@Configuration` 一般与 `@Bean` 注解配合使用，用 `@Configuration` 注解类等价与 XML 中配置 beans，用  `@Bean` 注解方法等价于 XML 中配置 bean 。举例说明：

- XML配置代码如下：
```xml
<beans>
    <bean id = "userService" class="com.user.UserService">
        <property name="userDAO" ref = "userDAO"></property>
    </bean>
    <bean id = "userDAO" class="com.user.UserDAO"></bean>
</beans>
```
- 等价于`@Bean`注释
```java
@Configuration
public class Config {
    @Bean
    public UserService getUserService(){
        UserService userService = new UserService();
        userService.setUserDAO(null);
        return userService;
    }
    @Bean
    public UserDAO getUserDAO(){
        return new UserDAO();
    }
}
```

### 2. @EnableAutoConfiguration
`@EnableAutoConfiguration`的作用启动自动的配置，`@EnableAutoConfiguration`注解的意思就是Springboot根据你添加的jar包来配置你项目的默认配置，比如根据`spring-boot-starter-web` ，来判断你的项目是否需要添加了`webmvc`和`tomcat`，就会自动的帮你配置web项目中所需要的默认配置。

- 以下摘自Spring文档翻译
> 启用 Spring 应用程序上下文的自动配置，试图猜测和配置您可能需要的bean。自动配置类通常采用基于你的 classpath 和已经定义的 beans 对象进行应用。  
> 被 @EnableAutoConfiguration 注解的类所在的包有特定的意义，并且作为默认配置使用。例如，当扫描 @Entity类的时候它将本使用。通常推荐将 @EnableAutoConfiguration 配置在 root 包下，这样所有的子包、类都可以被查找到。

> Auto-configuration类是常规的 Spring 配置 Bean。它们使用的是 SpringFactoriesLoader 机制（以 EnableAutoConfiguration 类路径为 key）。通常 auto-configuration beans 是 @Conditional beans（在大多数情况下配合 @ConditionalOnClass 和 @ConditionalOnMissingBean 注解进行使用）。

- SpringFactoriesLoader 机制：
> SpringFactoriesLoader会查询包含 META-INF/spring.factories 文件的JAR。 当找到spring.factories文件后，SpringFactoriesLoader将查询配置文件命名的属性。EnableAutoConfiguration的 key 值为org.springframework.boot.autoconfigure.EnableAutoConfiguration。根据此 key 对应的值进行 spring 配置。在 spring-boot-autoconfigure.jar文件中，包含一个 spring.factories 文件
### 3. @ComponentScan
`@ComponentScan`，扫描当前包及其子包下被`@Component`，`@Controller`，`@Service`，`@Repository`注解标记的类并纳入到spring容器中进行管理。是以前的`<context:component-scan>`（以前使用在xml中使用的标签，用来扫描包配置的平行支持）。举个例子，这就是为什么常见入门项目中User类会被Spring容器管理的原因。

- 以下摘自Spring文档翻译
> 为 @Configuration注解的类配置组件扫描指令。同时提供与 Spring XML’s 元素并行的支持。

> 无论是 basePackageClasses() 或是 basePackages() （或其 alias 值）都可以定义指定的包进行扫描。如果指定的包没有被定义，则将从声明该注解的类所在的包进行扫描。

> 注意， 元素有一个 annotation-config 属性（详情：http://www.cnblogs.com/exe19/p/5391712.html），但是 @ComponentScan 没有。这是因为在使用 @ComponentScan 注解的几乎所有的情况下，默认的注解配置处理是假定的。此外，当使用 AnnotationConfigApplicationContext， 注解配置处理器总会被注册，以为着任何试图在 @ComponentScan 级别是扫描失效的行为都将被忽略。

> 通俗的讲，@ComponentScan 注解会自动扫描指定包下的全部标有 @Component注解 的类，并注册成bean，当然包括 @Component 下的子注解@Service、@Repository、@Controller。@ComponentScan 注解没有类似的属性。

### 4. 更多
根据上面的理解，`HelloWorld`的入口类`SpringboothelloApplication`，我们可以使用：
```java
@ComponentScan
//@SpringBootApplication
public class SpringboothelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringboothelloApplication.class, args);
	}
}
```

使用`@ComponentScan`注解代替`@SpringBootApplication`注解，也可以正常运行程序。原因是`@SpringBootApplication`中包含`@ComponentScan`，并且springboot会将入口类看作是一个`@SpringBootConfiguration`标记的配置类，所以定义在入口类Application中的`SpringboothelloApplication`也可以纳入到容器管理。

### 参考链接
- [@SpringBootApplication注解](https://blog.csdn.net/claram/article/details/75125749)
- [springboot快速入门及@SpringBootApplication注解分析](https://jianshu.com/p/4e1cab2d8431)
--------
## 二. SpringApplication.run
> run方法主要用于创建或刷新一个应用上下文，是 Spring Boot的核心。

### 2.1 入口 **run 方法执行流程**  

1. 创建计时器，用于记录SpringBoot应用上下文的创建所耗费的时间。
2. 开启所有的SpringApplicationRunListener监听器，用于监听Sring Boot应用加载与启动信息。
3. 创建应用配置对象(main方法的参数配置) ConfigurableEnvironment
4. 创建要打印的Spring Boot启动标记 Banner
5. 创建 ApplicationContext应用上下文对象，web环境和普通环境使用不同的应用上下文。
6. 创建应用上下文启动异常报告对象 exceptionReporters
7. 准备并刷新应用上下文，并从xml、properties、yml配置文件或数据库中加载配置信息，并创建已配置的相关的单例bean。到这一步，所有的非延迟加载的Spring bean都应该被创建成功。
8. 打印Spring Boot上下文启动耗时到Logger中
9. Spring Boot启动监听
10. 调用实现了*Runner类型的bean的callRun方法，开始应用启动。
11. 如果在上述步骤中有异常发生则日志记录下才创建上下文失败的原因并抛出IllegalStateException异常。

```java
public ConfigurableApplicationContext run(String... args) {
	// 1. 创建计时器，用于记录SpringBoot应用上下文的创建所耗费的时间
	StopWatch stopWatch = new StopWatch();
	stopWatch.start();//stopWatch就是计时器
	ConfigurableApplicationContext context = null;
	Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
	configureHeadlessProperty();
	// 2. 开启所有的SpringApplicationRunListener监听器，用于监听Sring Boot应用加载与启动信息。
	SpringApplicationRunListeners listeners = getRunListeners(args);
	listeners.starting();// 监听器启动 主要用在log方面？
	try {
		ApplicationArguments applicationArguments = new DefaultApplicationArguments(
				args);
		// 3. 创建应用配置对象(main方法的参数配置) ConfigurableEnvironment
		ConfigurableEnvironment environment = prepareEnvironment(listeners,
				applicationArguments);
		configureIgnoreBeanInfo(environment);
		// 4. 创建要打印的Spring Boot启动标记 Banner
		Banner printedBanner = printBanner(environment);
		// 5. 创建 ApplicationContext应用上下文对象，web环境和普通环境使用不同的应用上下文。
		context = createApplicationContext();
		// 6. 创建应用上下文启动异常报告对象 exceptionReporters
		exceptionReporters = getSpringFactoriesInstances(
				SpringBootExceptionReporter.class,
				new Class[] { ConfigurableApplicationContext.class }, context);
		// 7. 准备并创建刷新应用上下文，并从xml、properties、yml配置文件或数据库中加载配置信息，并创建已配置的相关的单例bean。到这一步，所有的非延迟加载的Spring bean都应该被创建成功。
		prepareContext(context, environment, listeners, applicationArguments,
				printedBanner);
		refreshContext(context);// 刷新上下文
		afterRefresh(context, applicationArguments);
		stopWatch.stop();//计时结束
		// 8. 打印Spring Boot上下文启动耗时到Logger中
		if (this.logStartupInfo) {
			new StartupInfoLogger(this.mainApplicationClass)
					.logStarted(getApplicationLog(), stopWatch);
		}
		// 9. Spring Boot启动监听
		listeners.started(context);
		// 10. 调用实现了*Runner类型的bean的callRun方法，开始应用启动。
		callRunners(context, applicationArguments);
	}
	catch (Throwable ex) {
		handleRunFailure(context, ex, exceptionReporters, listeners);
		throw new IllegalStateException(ex);
	}

	try {
		listeners.running(context); //完成listeners监听
	}
	// 11. 如果在上述步骤中有异常发生则日志记录下才创建上下文失败的原因并抛出IllegalStateException异常。
	catch (Throwable ex) {
		handleRunFailure(context, ex, exceptionReporters, null);
		throw new IllegalStateException(ex);
	}
	return context;
}
```

### 2.2 运行事件 深入各方法
> 事件就是Spring Boot启动过程的状态描述，在启动Spring Boot时所发生的事件一般指：
> - 开始启动事件
> - 环境准备完成事件
> - 上下文准备完成事件
> - 上下文加载完成
> - 应用启动完成事件

#### 2.2.1 开始启动运行监听器 SpringApplicationRunListeners
> 上一层调用代码：SpringApplicationRunListeners listeners = getRunListeners(args);

顾名思意，运行监听器的作用就是为了监听 SpringApplication 的run方法的运行情况。在设计上监听器使用观察者模式，以总信息发布器 SpringApplicationRunListeners 为基础平台，将Spring启动时的事件分别发布到各个用户或系统在 META_INF/spring.factories文件中指定的应用初始化监听器中。使用观察者模式，在Spring应用启动时无需对启动时的其它业务bean的配置关心，只需要正常启动创建Spring应用上下文环境。各个业务'监听观察者'在监听到spring开始启动，或环境准备完成等事件后，会按照自己的逻辑创建所需的bean或者进行相应的配置。观察者模式使run方法的结构变得清晰，同时与外部耦合降到最低。

> spring-boot-2.0.3.RELEASE-sources.jar!/org/springframework/boot/context/event/EventPublishingRunListener.java
```java
class SpringApplicationRunListeners {
	...
	// 在run方法业务逻辑执行前、应用上下文初始化前调用此方法
	public void starting() {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.starting();
		}
	}
	// 当环境准备完成，应用上下文被创建之前调用此方法
	public void environmentPrepared(ConfigurableEnvironment environment) {}
	// 在应用上下文被创建和准备完成之后，但上下文相关代码被加载执行之前调用。因为上下文准备事件和上下文加载事件难以明确区分，所以这个方法一般没有具体实现。
	public void contextPrepared(ConfigurableApplicationContext context) {}
	// 当上下文加载完成之后，自定义bean完全加载完成之前调用此方法。
	public void contextLoaded(ConfigurableApplicationContext context) {}

	public void started(ConfigurableApplicationContext context) {}

	public void running(ConfigurableApplicationContext context) {}
	// 当run方法执行完成，或执行过程中发现异常时调用此方法。
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		for (SpringApplicationRunListener listener : this.listeners) {
			callFailedListener(listener, context, exception);
		}
	}

	private void callFailedListener(SpringApplicationRunListener listener,
			ConfigurableApplicationContext context, Throwable exception) {}
		}
	}
}
```

默认情况下Spring Boot会实例化EventPublishingRunListener作为运行监听器的实例。在实例化运行监听器时需要SpringApplication对象和用户对象作为参数。其内部维护着一个事件广播器（被观察者对象集合，前面所提到的在META_INF/spring.factories中注册的初始化监听器的有序集合 ），当监听到Spring启动等事件发生后，就会将创建具体事件对象，并广播推送给各个被观察者。

#### 2.2.2 环境准备 创建应用配置对象 ConfigurableEnvironment
> 上一层调用代码：ConfigurableEnvironment environment = prepareEnvironment(listeners

将通过`ApplicationArguments`将环境`Environment`配置好，并与SpringApplication绑定
```java
private ConfigurableEnvironment prepareEnvironment(
		SpringApplicationRunListeners listeners,
		ApplicationArguments applicationArguments) {
	// 获取或创建环境 Create and configure the environment
	ConfigurableEnvironment environment = getOrCreateEnvironment();
	configureEnvironment(environment, applicationArguments.getSourceArgs());
	// 持续监听
	listeners.environmentPrepared(environment);
	// 将环境与SpringApplication绑定（调用到 binder.java 未看）
	bindToSpringApplication(environment);
	if (this.webApplicationType == WebApplicationType.NONE) {
		environment = new EnvironmentConverter(getClassLoader())
				.convertToStandardEnvironmentIfNecessary(environment);
	}
	ConfigurationPropertySources.attach(environment);
	return environment;
}
```
- 略过Banner的创建
#### 2.2.3 创建应用上下文对象 ApplicationContext
> context = createApplicationContext();

根据`this.webApplicationType`来判断是什么环境，web环境和普通环境使用不同的应用上下文。再使用反射相应实例化。

> spring-boot-2.0.3.RELEASE-sources.jar!/org/springframework/boot/SpringApplication.java
```java
protected ConfigurableApplicationContext createApplicationContext() {
	Class<?> contextClass = this.applicationContextClass;
	if (contextClass == null) {
		try {
			switch (this.webApplicationType) {
			case SERVLET:// 判断
				contextClass = Class.forName(DEFAULT_WEB_CONTEXT_CLASS); // 反射
				break;
			case REACTIVE:
				contextClass = Class.forName(DEFAULT_REACTIVE_WEB_CONTEXT_CLASS);
				break;
			default:
				contextClass = Class.forName(DEFAULT_CONTEXT_CLASS);
			}
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalStateException(
					"Unable create a default ApplicationContext, "
							+ "please specify an ApplicationContextClass",
					ex);
		}
	}
	return (ConfigurableApplicationContext) BeanUtils.instantiateClass(contextClass);
}
```

================================

##### **[Class.forName() 的作用](https://blog.csdn.net/fengyuzhengfan/article/details/38086743)**  
- Class.forName：返回与给定的字符串名称相关联类或接口的 Class 对象。
	- Class.forName(className) 实际上是调用 Class.forName(className,true, this.getClass().getClassLoader())。第二个参数，是指 Class 被 loading 后是不是必须被初始化。可以看出，使用 Class.forName（className）加载类时则已初始化。
	- 所以 Class.forName(className) 可以简单的理解为：获得字符串参数中指定的类，并初始化该类。

- 首先你要明白在 java 里面任何 class 都要装载在虚拟机上才能运行
	1. forName 这句话就是装载类用的 (new 是根据加载到内存中的类创建一个实例，要分清楚)。 
	2. 至于什么时候用，可以考虑一下这个问题，给你一个字符串变量，它代表一个类的包名和类名，你怎么实例化它？  
        ```java
		A a = (A)Class.forName("pacage.A").newInstance();  
		A a = new A();
		```   
		两者是一样的效果。
	3. jvm 在装载类时会执行类的静态代码段，要记住静态代码是和 class 绑定的，class 装载成功就表示执行了你的静态代码了，而且以后不会再执行这段静态代码了。
	4. Class.forName(xxx.xx.xx) 的作用是要求 JVM 查找并加载指定的类，也就是说 JVM 会执行该类的静态代码段。
	5. 动态加载和创建 Class 对象，比如想根据用户输入的字符串来创建对象  
       ```java
	   String str = 用户输入的字符串
       Class t = Class.forName(str);  
       t.newInstance();
	   ```

#### 2.2.4 创建上下文启动异常报告对象 exceptionReporters
> 上一层调用：  
> exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,  
>		new Class[] { > ConfigurableApplicationContext.class }, context);

通过`getSpringFactoriesInstances`创建`SpringBootExceptionReporter`接口的实现，而该接口的实现的就是`FailureAnalyzers`——上下文启动失败原因分析对象。

> spring-boot-2.0.3.RELEASE-sources.jar!/org/springframework/boot/diagnostics/FailureAnalyzers.java
```java
final class FailureAnalyzers implements SpringBootExceptionReporter {
	...
	FailureAnalyzers(ConfigurableApplicationContext context, ClassLoader classLoader) {}

	private List<FailureAnalyzer> loadFailureAnalyzers(ClassLoader classLoader) {}
	private void prepareFailureAnalyzers(List<FailureAnalyzer> analyzers,
			ConfigurableApplicationContext context) {}
	private void prepareAnalyzer(ConfigurableApplicationContext context,
			FailureAnalyzer analyzer) {}

	@Override
	public boolean reportException(Throwable failure) {}
	private FailureAnalysis analyze(Throwable failure, List<FailureAnalyzer> analyzers) {}
	private boolean report(FailureAnalysis analysis, ClassLoader classLoader) {}
}
```

#### 2.2.5 准备上下文 prepareContext
> 上一层调用：prepareContext(context, environment, listeners, applicationArguments,printedBanner);

xml、properties、yml配置文件或数据库中加载的配置信息封装到`applicationArguments`中，并创建已配置的相关的单例bean。到这一步，所有的非延迟加载的Spring bean都应该被创建成功。

```java
private void prepareContext(ConfigurableApplicationContext context,
		ConfigurableEnvironment environment, SpringApplicationRunListeners listeners,
		ApplicationArguments applicationArguments, Banner printedBanner) {
	context.setEnvironment(environment);
	postProcessApplicationContext(context);
	applyInitializers(context);
	listeners.contextPrepared(context);
	if (this.logStartupInfo) {
		logStartupInfo(context.getParent() == null);
		logStartupProfileInfo(context);
	}

	// 创建已配置的相关的单例 bean 
	// Add boot specific singleton beans
	context.getBeanFactory().registerSingleton("springApplicationArguments",
			applicationArguments);
	if (printedBanner != null) {
		context.getBeanFactory().registerSingleton("springBootBanner", printedBanner);
	}

	// Load the sources
	Set<Object> sources = getAllSources();
	Assert.notEmpty(sources, "Sources must not be empty");
	load(context, sources.toArray(new Object[0]));
	listeners.contextLoaded(context);
}
```

#### 2.2.6
> 上一层调用：refreshContext(context);

#### 2.2.7
> 上一层调用：


### 参考链接
- [[深入剖析Spring Boot]启动、事件通知与配置加载原理](https://my.oschina.net/weiwei02/blog/1592967)
- [spring boot实战(第八篇)上下文的创建（TODO）](https://blog.csdn.net/liaokailin/article/details/49010275)


