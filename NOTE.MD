### 项目中遇到的坑
***
- maven打包时报错：无效的目标发行版 17
  - 原因：在父模块中未指定 java 版本
  - 解决：  
  > 在父模块POM的 `properties` 中添加
    `<java.version>17</java.version>`
- 自动注入同级模块的 `service` 时找不到   
  - 原因：两个模块的包路径不同
  - 解决：  
  > 在出现异常的启动类上更改
    `@SpringBootApplication`  
    改为  
    `@SpringBootApplication(scanBasePackages = {"com.blog", "com.blog"})`
- 获取分类中的文章数时语法错误
  - 原因：计数时使用方法错误，返回类型错误
  - 解决：
  > 可以使用 `mapper` 类的 `selectCount()` 方法获取类型为 `queryWrapper`
  > ``` 
  > articleService.getBaseMapper().selectCount(
  > articleService.lambdaQuery()
  > .eq(Article::getCategoryId, categoryId)
  > .getWrapper()));//用这个方法获取queryWrapper
  > ```
- MybatisPlus 查询 Category 表出现字段找不到的错误
  - 原因：使用别人的数据库后自己添加了字段
  - 解决：
  > 删除自己添加的字段
- MybatisPlus 分页查询时会返回所有列，即分页不生效
  - 原因：未进行分页配置
  - 解决：
  > ```
  > @Bean
  > public MybatisPlusInterceptor mybatisPlusInterceptor() {
  >   MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
  >   mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
  >   return mybatisPlusInterceptor;}
  > ```
- 类的属性中有类，未进行查找
  - 原因：对类中的属性类，忘记处理
  - 解决：
  > 文章数据需要标签，查找标签需要查询文章标签表，再查询标签表
- redisBean 未找到，类型错误
  - 原因：`@Resource` 注入时检测变量名，`StringRedisTemplate` 无法注入变量 `redisTemplate`
  - 解决：
  > 将 `redisTemplate` 改为 `stringRedisTemplate`
- 使用 `redisHash` 存储时出现类型转换错误
  - 原因：不明
  - 解决：
  > 改为使用有序集合 `sortedset`
- 直接调用使用 `@Bean` 注解的方法。请改用依赖项注入。
  - 原因：使用类中的 `Bean` 方法
  - 解决：
  > 取消 `@Bean` 注解
- can not use this method for “getEntity“
  - 原因：链式调用 `wrapper` 时报错
  - 解决：
  > 不使用链式调用的 `wrapper` 或直接使用 `one()` 方法
- 无法获取当前文章的下一个或上一个文章
  - 原因：文章表主键不连续，无法定义查询边界
  - 解决：
  > 存储所有文章为列表以主键排序，再获取当前文章在列表中的索引，以此获得上下文章索引
- 爆红：此处不允许使用声明变量
  - 原因：使用 `do-while` 时无法声明变量
  - 解决：
  > 在循环体前后加花括号
- redis 报错 `java.io.EOFException: null`
  - 原因：使用错误的 `redisTemplate`
  - 解决：
  > 当键值对为简单的字符串时应使用 `StringRedisTemplate`
- 无法获取 redis中 的文章与文章浏览量: WRONGTYPE Operation against a key holding the wrong kind of value
  - 原因：低级错误! `key` 写错成了 `BLOG_VIEW_COUNT`
  - 解决：
  > `key` 改为 `ARTICLE_VIEW_COUNT`
- 传入参数 `map` 固定非空，导致判断程序失效
  - 原因：判断语句为判断 `map` 是否为 `null`
  - 解决：
  > 应使用 `isEmpty()` 判断
- 构建时无法找到实体类
  - 原因：使用 `EasyCode` 自动创建 `entity、service、serviceImpl` 时路径配置错误
  - 解决：
  > 修改各个模板的包路径
- 无法获取一周内访问量
  - 原因：看不懂接口文档，不知道到底需要什么
  - 解决：
  > 无法解决
- 添加文章后文章标签表未添加对应关系
  - 原因：添加文章与标签的关系时被重复检测跳过
  - 解决：
  > 将方法参数从未添加标签列表改为文章标签列表
- 查询条件正确，却无法查询到数据
  - 原因：查询时分页后还有匹配条件
  - 解决：
  > 在分页前添加条件
- MybatisPlus 每次查询都会添加匹配条件，参数越来越多
  - 原因：每次查询使用的是同一个 `query`
  - 解决：
  > 将全局变量的 `this.lambdaQuery()` 删除，改为每次查询各自使用
- url 参数请求无法用 `@RequestBody`
  - 原因：前端请求参数的方式错误
  - 解决：
  > 删除 `@RequestBody`
- `StringRedisTemplate` 无法直接存储对象
  - 原因：需要存储 String 类型
  - 解决：
  > 使用 `RedisTemplate<String, Object>`
- `RedisTemplate` 无法存入非序列化对象
  - 原因：存入的 `LoginUser` 的字段 `User` 类未被序列化
  - 解决：
  > 将 `User` 实现接口 `Serializable`
- jwt 解析后取 `id` 使用 `asString()` 后取空值
  - 原因：未知
  - 解决：
  > 使用 `toString()`
- redis 中同一个键会自动在前面增加乱码以添加同键不同值的多条数据
  - 原因：redis 存取时未进行序列化配置
  - 解决：
  > 创建 `RedisConfiguration` 配置 redis 序列化
- 请求异常时返回的状态码和信息错误
  - 原因：未配置认证和授权失败处理器
  - 解决：
  >```
  > httpSecurity.exceptionHandling(conf -> conf
  >.accessDeniedHandler(this::handle)          //授权失败处理器
  >.authenticationEntryPoint(this::commence))  //认证失败处理器
  >```
  > 处理器方法中使用
  >`response.getWriter().write(Result.failure().asJsonString());`
- 退出登录未经过 `controller` 层无法正确退出
  - 原因：默认使用 `security` 自带的 `logout` 过滤器
  - 解决：
  > 取消 `security` 自带的 `logout` 过滤器
  > `httpSecurity.logout(AbstractHttpConfigurer::disable)`
- 配置的统一异常处理类未生效
  - 原因：`@RestControllerAdvice` 只能捕获 `Controller` 层的异常
  - 解决：
  > 在 `filter` 层使用 `HandlerExceptionResolver`,抛出异常时：
  > ``` 
  > handlerExceptionResolver.resolveException(request, response, null, new RuntimeException("登录已过期"));
  > return;
  > ```
- Type definition error: 
  [simple type, class org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory$EnhancedResultObjectProxyImpl]
  - 原因：在进行实体类代理过程中会添加一个 `handler` 的属性，故导致解析失败
  - 解决：
  > 在实体类前，即你返回需要 jackson 解析的类，前面添加注解，让 Jackson 序列化时忽略 `handler` 属性
- There is no getter for property named 'param' in 'class com.blog.entity.vo.request.ArticleConditionReq'
  - 原因：未检测到对应参数
  - 解决：
  > 在 `mapper` 方法参数对象前添加 `@Param("param")`
- AOP 切入报空指针异常
  - 原因：使用 AOP 注解的方法为 `private`
  - 解决：
  > 方法权限改为 `public/protected`
- No qualifying bean of type 'org.springframework.mail.MailSender'
  - 原因：未添加依赖
  - 解决：
  > pom 文件添加 `spring-boot-starter-mail`
- org.springframework.data.redis.serializer.SerializationException
  - 原因：`Redis` 序列化与 `SpringBoot` 序列化冲突
  - 解决：
  > 不使用 `User` 类的 `Access(chain = true)`
- Injection of resource dependencies failed
  - 原因：通常为自动注入异常，项目中为从 `SecurityContextHolder` 获取用户信息时
  - 解决：
  > 将对象获取放在方法体内
- It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
  - 原因：通过 AOP 切面时，对传入的内容转换成 JSON 时报错，方法请求参数中有 `Request` 或 `Response`
  - 解决：
  > 方法中的 `request` 使用其他工具类的 `RequestContextHolder` 获取
- No validator could be found for constraint '.....' validating type 'java.lang.Integer'
  - 原因：`@NotEmpty` 注解只能支持字符串类型的字段
  - 解决：
  > 非字符串类型字段校验使用 `@NotNull`
- 前端退出登录请求多次但未收到响应数据,状态码为`200`
  - 原因：项目使用了全局异常捕获,但捕获的异常没有处理,导致返回数据为空
  - 解决：
  > 添加全局异常捕获范围
- getOutputStream() has already been called for this response
  - 原因：一条请求不能多次获取 response 或 request
  - 解决：
  > 不要在切面注解下的方法使用 response 或 request
- java: 找不到符号
  - 原因：项目目录移动后出现编译错误
  - 解决：
  > 若是 `maven` 项目则先使用 `clean` 再 `package`
- 400 Bad Request
  - 原因：请求参数个数或类型错误
- 403 Forbidden
  - 原因：访问路径没有权限(大部分), `login` 接口中实现的用户校验失败
- 405 Method Not Allowed
  - 原因：提交类型方法错误
- 415 Unsupported Media Type
  - 原因：服务器无法处理请求附带的媒体格式
---  
### 项目技巧
- 项目中不应该使用常量，要进行字面值处理，即把所有需要用到的常量放在一个类里
> 在这个项目中创建了 `SystemConstants` 类用于存放常量
- 使用反射实现两个类属性的传递
> 使用 `clazz.getConstructor().newInstance()` 获取目标类  
  使用 `BeanUtils.copyProperties()` 方法拷贝
- 使用`@Lazy`注解解决循环注入问题
> 在service实现类中经常需要引用其他 `service` 实现类，因此非常容易出现循环引入。    
> 通过在自动注入的字段上使用 `@Lazy` 注释进行懒加载，当被使用时创建代理类，真正加载时才是真创建。  
> ```
> @Lazy  
> @Resource
> private ArticleService articleService;
> ```
- 多表查询时将其他表先进行存储，可以减少数据库访问次数，优化查询消耗
> 当多表查询时当前表每个数据查询其他表都要访问一次数据库，这时可以将其他表的数据先进行存储，
  使用时可以访问存储的数据而不用请求数据库，从而减少请求数据库的次数
- 全局日期格式配置
> 在 Application.yml 文件中配置`spring.jackson.date-format`
- 列表类型转换
> 例子:
> ```
> articleList.stream()
> //map 加目标类 Builder 组合完成
> .map(article -> ArticleRankResp.builder()
> .articleTitle(article.getArticleTitle())
> .viewCount(articleMap.get(article.getId()).intValue())
> .build())
> .sorted(Comparator.comparingInt(ArticleRankResp::getViewCount)
> .reversed()).collect(Collectors.toList());
> ```
- 添加标签时应注意不要重复添加
> 新添加的数据在数据库中可能存在并且不能重复，这时需要判断，数据库中已有的数据应跳过，不进行存储
- MybatisPlus 需要使用事务异常回滚时需要配置
> 开启spring事务需要在启动类上加上 
> `@EnableTransactionManagement`  
> 在需要开启事务的方法上加上注解  
> `@Transactional`  
> 默认报 `RuntimeException` 异常时回滚  
> 使用 `@Transactional(rollbackFor = Exception.class)` 更改检测的异常
- 项目启动时从数据库中读取数据存入redis，防止写锁
> 实现 CommendLineRunner 接口，重写run方法
- securityConfig
> requestMatchers 参数为匹配的接口路径  
  anonymous 接在后面表示路径访问用户为匿名用户  
  permitAll 接在后面表示路径访问用户为所有用户  
  authenticated 接在后面表示路径访问用户为认证用户  
- 将一个list返回map
>例子:
>```
>commentList.stream().collect(
>Collectors.toMap(Comment::getId, Comment::getFromUid));
>```
- 单表简单查询使用mybatis，多表复杂查询使用mybatis-plus
> `map-underscore-to-camel-case: true` 开启表字段转类属性自动驼峰命名  
  `lazy-loading-enabled: true`  开启懒加载查询，需要数据库数据时才交互
- 前端请求参数类型query与body
> Query 查询参数，将参数拼在请求地址上
  Body 请求体参数，请求体中的数据，可以传递对象 (一般json都是body传参）
- mybatis使用模糊查询时使用CONCAT方法拼接
> `like concat('%',#{param.keyword},'%')`
- java一个方法传入不同的类，并且获取类中的一些共同参数
> 使用多态，有共同参数的多个类继承同一个类
- 获取当前请求用户的信息
> 控制层参数添加:  
> `@AuthenticationPrincipal LoginUser loginUser`  
> 方法体内使用:  
> `(LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()`
- MultipartFile类型保存到本地
> 设置路径后使用: file.transferTo(new File(路径));
- 用户上传的文件应使用OSS对象存储云服务器
- 使用 validation 校验请求参数，在请求体字段上添加注解，并在 controller 请求方法中的请求参数前添加 @Valid  注解开启校验
- 使用正则表达式匹配异常信息，默认异常信息为接口内传出的信息，若为空则输出 INTERNAL_SERVER_ERROR 正则表达式为 ".*?[\u4E00-\u9FA5].*?"
- 方法上注解设置响应状态码
> 使用 `@ResponseStatus(HttpStatus.?)`
- AOP 获取方法参数
> `joinPoint.getArgs()` 获取的是按照注解下方法的形参顺序排列的参数集合
- AOP 注解 @AfterThrowing
> value:值为切面注解的方法名; throwing:值为方法抛出的异常类的名称,需要在 @AfterThrowing 注解的方法中添加异常类形参
```
@AfterThrowing(value = "pt()", throwing = "e")
public void printLog(JoinPoint point, Exception e) { ...
```
