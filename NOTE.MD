### 项目中遇到的坑
***
- maven打包时报错：无效的目标发行版 17
  - 原因：  
    在父模块中未指定java版本
  - 解决：  
    在父模块POM的properties中添加
  > <java.version>17</java.version>
- 自动注入同级模块的service时找不到   
  - 原因：  
    两个模块的包路径不同
  - 解决：  
    在出现异常的启动类上更改
  > @SpringBootApplication  
    改为
    @SpringBootApplication(scanBasePackages = {"com.framework", "com.blog"})
- 获取分类中的文章数时语法错误
  - 原因：  
    计数时使用方法错误，返回类型错误
  - 解决：
  > 可以使用mapper类的selectCount()方法获取类型为queryWrapper  
    articleService.getBaseMapper().selectCount(
    articleService.lambdaQuery()
    .eq(Article::getCategoryId, categoryId)
    .getWrapper()));//用这个方法获取queryWrapper
- MybatisPlus查询Category表出现字段找不到的错误
  - 原因：  
    使用别人的数据库后自己添加了字段
  - 解决：
    删除自己添加的字段
- MybatisPlus分页查询时会返回所有列，即分页不生效
  - 原因：
    未进行分页配置
  - 解决：
  > @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
      MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
      mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
      return mybatisPlusInterceptor;
    }
- 类的属性中有类，未进行查找
  - 原因：
    对类中的属性类，忘记处理
  - 解决：
  > 文章数据需要标签，查找标签需要查询文章标签表，再查询标签表
- redisBean未找到，类型错误
  - 原因：
    @Resource注入时检测变量名，StringRedisTemplate无法注入变量redisTemplate
  - 解决：
  > 将redisTemplate改为stringRedisTemplate 
- 使用redisHash存储时出现类型转换错误
  - 原因：
    不明
  - 解决：
  > 改为使用有序集合sortedset
- 直接调用使用 @Bean 注解的方法。请改用依赖项注入。
  - 原因：
    使用类中的Bean方法
  - 解决：
  > 取消@Bean注解
- can not use this method for “getEntity“
  - 原因：
    链式调用wrapper时报错
  - 解决：
  > 不使用链式调用的wrapper或直接使用one()方法
- 无法获取当前文章的下一个或上一个文章
  - 原因：
    文章表主键不连续，无法定义查询边界
  - 解决：
  > 存储所有文章为列表以主键排序，再获取当前文章在列表中的索引，以此获得上下文章索引
- 爆红：此处不允许使用声明变量
  - 原因：
    使用do-while时无法声明变量
  - 解决：
  > 在循环体前后加花括号
- redis报错java.io.EOFException: null
  - 原因：
    使用错误的redisTemplate
  - 解决：
  > 当键值对为简单的字符串时应使用StringRedisTemplate
- 无法获取redis中的文章与文章浏览量:WRONGTYPE Operation against a key holding the wrong kind of value
  - 原因：
    低级错误：key写错成了BLOG_VIEW_COUNT
  - 解决：
  > key改为ARTICLE_VIEW_COUNT
- 传入参数map固定非空，导致判断程序失效
  - 原因：
    判断语句为判断map是否为null
  - 解决：
  > 应使用isEmpty()判断
- 构建时无法找到实体类
  - 原因：
    使用EasyCode自动创建entity、service、serviceImpl时路径配置错误
  - 解决：
  > 修改各个模板的包路径
- 无法获取一周内访问量
  - 原因：
    看不懂接口文档，不知道到底需要什么
  - 解决：
  > 无法解决
- 405 Method Not Allowed
  - 原因：
    提交类型方法错误
  - 解决：
  > 提交方法包括[GET,POST,DELETE]等
- 400 Bad Request
  - 原因：
    请求参数个数或类型错误
  - 解决：
  > 接口参数@RequestParam改为@RequestBody
---  
### 项目技巧
- 项目中不应该使用常量，要进行字面值处理，即把所有需要用到的常量放在一个类里
> 在这个项目中我创建了SystemConstants类用于存放常量
- 使用反射实现两个类属性的传递
> 使用clazz.getConstructor().newInstance()获取目标类  
  使用BeanUtils.copyProperties()方法拷贝
- 使用@Lazy注解解决循环注入问题
> 在service实现类中经常需要引用其他service实现类，因此非常容易出现循环引入。    
  通过在自动注入的字段上使用@Lazy注释进行懒加载，当被使用时创建代理类，真正加载时才是真创建。  
  @Lazy
  @Resource
  private ArticleService articleService;
- 多表查询时将其他表先进行存储，可以减少数据库访问次数，优化查询消耗
> 当多表查询时当前表每个数据查询其他表都要访问一次数据库，这时可以将其他表的数据先进行存储，
  使用时可以访问存储的数据而不用请求数据库，从而减少请求数据库的次数
- 全局日期格式配置
> 在Application.yml文件中配置spring.jackson.date-format
- 列表类型转换
> articleList.stream()
  //map加目标类Builder组合完成
  .map(article -> ArticleRankResp.builder()
    .articleTitle(article.getArticleTitle())
    .viewCount(articleMap.get(article.getId()).intValue())
    .build())
  .sorted(Comparator.comparingInt(ArticleRankResp::getViewCount)
  .reversed()).collect(Collectors.toList());
- 添加标签时应注意不要重复添加
> 新添加的数据在数据库中可能存在并且不能重复，这时需要判断，数据库中已有的数据应跳过，不进行存储