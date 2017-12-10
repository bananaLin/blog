##前言
  我之前是学SpringMVC的，后面听同学说SpringBoot挺好用，极力推荐我学这个鬼。一开始，在网上找SpringBoot的学习资料，他们博文写得不是说不好，而是不太详细。我就在想我要自己写一篇尽可能详细的文章出来。

##技术栈
- Spring
- Spring Boot
- MyBatis
- MongoDB
- MySQL

##设计模式

MVC

##功能
- 注册（用户完成注册后是默认未激活的，程序有个定时器在检测没有激活的用户，然后发一次邮件提醒用户激活）
- 登录
- 发帖（帖子存在MongoDB）
- 评论 
- 其他功能正在添加中...

##编辑器
- IntellJ IDEA 2017

##目录结构
![图片描述][1]
 


  


##正文
第一步先让我们创建项目吧，打开idea File -> New -> Project。我们是创建Spring Boot项目，所以来到Project这一步是选Spring Initailizr，选好jdk再点next。
![图片描述][2]


然后就来到了一下这个界面，这里是让你填写项目的目录，你喜欢就ok。
![图片描述][3]


接下来就是让你选择需要那些依赖，要把那个Web，MyBaits,MongoDB,数据库（我用的是mysql，所以我勾选了mysql）这些勾选上。
![图片描述][4]


最后是填写项目名字，然后点Finish就完成创建了。


如何整合Spring+SpringBoot+MyBatis+MongoDB
![图片描述][5]


在第一步中，你填的项目目录下，我喜欢建个文件夹叫做Controller,当然用来放Controller了，Entity文件夹放实体类，Service文件是存放业务逻辑层，这个文件下还有ServiceImpl文件夹对应的是存放Service的实现类。

第二步配置，详细的代码我已经 放在github上了 [点击跳转到github][6]。我们的配置写在一个叫做application.yml文件里。你们新建的项目是默认是application.properties文件，但是.yml文件配置起来比.properties文件简洁，所以个人比较喜欢.yml文件。

怎么个简洁法，对比一下你就知道了。.properties配置起来是这样的（这里用配置发送邮件为例子）

```
spring.mail.host=smtp.qq.com
spring.mail.username=用户名
spring.mail.password=密码
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

```
而.yml配置起来是这样的：

```
mail:
    host: smtp.qq.com
    username: //用来发送邮件的账号
    password: //这里是IMAP/SMTP服务的授权密码
    properties:
      mail:
        stmp:
          auth: true
          starttls:
            enable: true
            required: true
    port: 587
```
配置数据源（数据源、MongoDB还有mail都是在spring下），

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    platform: mysql
  jpa:
    show-sql: true
  data:
    mongodb:
      uri: mongodb://localhost:27017/blog  //blog记得换成你取的名字
```
配置MyBaits,它在.yml和spring地位一样高，所以mybatis,spring缩进是一样的。

```
mybatis:
  type-aliases-package: com.example.junior.Entity  //这里是实体类所在的包
  mapper-locations: classpath:/mapper/*.xml  //这里是放sql语句的映射文件
```
还有一个值得注意的地方是JuniorApplication.java，它不仅仅是启动引导类，还是个配置类。所以有一些配置需要写在这里面。

```
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableScheduling //我有个定时器，这个注解是让它发现定时器
@MapperScan(basePackages = "com.example.junior.Dao") //让它去发现你的Dao层
public class JuniorApplication {
	public static void main(String[] args) {
		SpringApplication.run(JuniorApplication.class, args);
	}
}

```
如果你在创建项目的时候，忘记勾选某一些依赖的话不要紧，可以在pom.xml文件里添加依赖。添加完成后在pom.xml右键 点击 Maven -> Reimport就Ok了






##最后
如果有帮助到你的话，请打赏我 0.5元。
支付宝打赏，请扫
![支付宝打赏][7]
微信打赏，请扫
![图片描述][8]


  [1]: /img/bVZUB3
  [2]: /img/bVZUCW
  [3]: /img/bVZUDC
  [4]: /img/bVZUEC
  [5]: /img/bVZUEW
  [6]: https://github.com/bananaLin/blog.git
  [7]: /img/bVZYRB
  [8]: /img/bVZYR7
