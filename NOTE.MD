### 项目中遇到的坑
***
- maven打包时报错：无效的目标发行版 17
  - 原因：  
    在父模块中未指定java版本
  - 解决：  
    在父模块POM的properties中添加
    ><java.version>17</java.version>
- 自动注入同级模块的service时找不到   
  - 原因：  
    两个模块的包路径不同
  - 解决：  
    在出现异常的启动类上更改
    >@SpringBootApplication  
    改为
    @SpringBootApplication(scanBasePackages = {"com.framework", "com.blog"})
    
---  
### 项目技巧
- 项目中不应该使用常量，要进行字面值处理，即把所有需要用到的常量放在一个类里
  >在这个项目中我创建了SystemConstants类用于存放常量
- 使用反射实现两个类属性的传递
  >使用clazz.getConstructor().newInstance()获取目标类  
  使用BeanUtils.copyProperties()方法拷贝