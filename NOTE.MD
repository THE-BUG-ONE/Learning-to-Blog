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
    是用别人的数据库并自己添加了字段
  - 解决：
    删除自己添加的字段
- mybatisPlus分页查询是会返回所有列，即分页不生效
  - 原因：
    未进行分页配置
  - 解决：
  > @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
      MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
      mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
      return mybatisPlusInterceptor;
    }
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