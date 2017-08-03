我想大多数做java开发的同学对SSM框架的搭建已经都轻车熟路了，但是大家搭建的大多数是单模块的，网上也有非常多的例子，但是如何用maven去构建多module的ssm项目呢？下面来手把手的，利用IntelliJ IDEA搭建一次多module的ssm项目，给大家一个参考。

<!-- more -->

## 为什么要搭建多模块的maven项目？

首先抛出这么一个问题，我们为什么要搭建多模块的maven项目呢？以多模块的方式组织项目，其实也是maven一直倡导的。做开发的同学都知道的一个概念就是“低耦合，高内聚”，将项目划分多模块，可以极大的增大代码的重用性，防止随着项目的增大，pom文件越来越臃肿。

对于一般的java项目我们一般是这么分层的：

* dao层负责数据库的交互。
* service层主要处理业务逻辑。
* web层负责与客户端交互。
* pojo层存放的是实体类。
* common层存放我们常用的一些公用的工具类等。

对应的，在一个项目中，我们会看到一些包名：

* com.leeyom.ssm.dao
* com.leeyom.ssm.service
* com.leeyom.ssm.web
* com.leeyom.ssm.common
* com.leeyom.ssm.pojo

这样整个项目的框架就清晰了，但随着项目的进行，你可能会遇到如下问题：

1. 这个应用可能需要有一个前台和一个后台管理端，你发现大部分dao，一些service，和大部分util是在两个应用中均可用。
2. pom.xml中的依赖列表越来越长以重用的，但是，由于目前只有一个项目（WAR），你不得不新建一个项目依赖这个WAR，这变得非常的恶心，因为在Maven中配置对WAR的依赖远不如依赖JAR那样简单明了，而且你根本不需要com.leeyom.ssm.web。有人修改了dao，提交到svn并且不小心导致build失败了，你在编写service的代码，发现编译不过，只能等那人把dao修复了，你才能继续进行，很多人都在修改，到后来你根本就不清楚哪个依赖是谁需要的，渐渐的，很多不必要的依赖被引入。甚至出现了一个依赖有多个版本存在。
3. build整个项目的时间越来越长，尽管你只是一直在web层工作，但你不得不build整个项目。
4. 某个模块，比如util，你只想让一些经验丰富的人来维护，可是，现在这种情况，每个开发者都能修改，这导致关键模块的代码质量不能达到你的要求。

我们会发现，其实这里实际上没有遵守一个设计模式原则：“高内聚，低耦合”。虽然我们通过包名划分了层次，并且你还会说，这些包的依赖都是单向的，没有包的环依赖。这很好，但还不够，因为就构建层次来说，所有东西都被耦合在一起了。因此我们需要使用Maven划分模块。
    
> 以上的讲解是参考：[Maven最佳实践：划分模块](http://juvenshun.iteye.com/blog/305865)

## 项目结构

### 项目整体目录

```
ssm-demo
├── ssm-common -- 公共模块，主要存放一些工具类
|    ├── src
|    ├── pom.xml -- 打包方式：jar
├── ssm-dao -- 存放mybatis相关的mapper接口
|    ├── src
|    ├── pom.xml -- 打包方式：jar
├── ssm-pojo -- 存放实体类
|    ├── src
|    ├── pom.xml -- 打包方式：jar
├── ssm-service -- 存放业务逻辑类
|    ├── src
|    ├── pom.xml -- 打包方式：jar
├── ssm-web -- 存放前台页面，客户端交互相关的handler
|    ├── src
|    ├── pom.xml -- 打包方式：war
├── pom.xml --打包方式为pom，一些公用的依赖
```

### 各模块的依赖关系

![](http://og1m51u2s.bkt.clouddn.com/15015988179183.jpg)

## Maven多模块项目创建
1. 先创建一个空的maven项目(ssm-demo)作为父级项目，创建成功后，**<span style="color:red">删除掉src目录!</span>**。![](http://og1m51u2s.bkt.clouddn.com/15016006142785.jpg)![](http://og1m51u2s.bkt.clouddn.com/15016007759468.jpg)![](http://og1m51u2s.bkt.clouddn.com/15016009256846.jpg)

2. 右击我们刚刚创建的父级项目**ssm-demo**，选择 `new --> Module`,创建`ssm-common`模块。![](http://og1m51u2s.bkt.clouddn.com/15016011837649.jpg)填写Artifactld![](http://og1m51u2s.bkt.clouddn.com/15016013559961.jpg)点击Finish，就可以建好`ssm-common`模块。![](http://og1m51u2s.bkt.clouddn.com/15016014089277.jpg)

 打开`ssm-common`下面的pom.xml文件，添加该模块的打包方式为jar:
 
 ```xml
    <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>ssm</artifactId>
        <groupId>com.leeyom.ssm</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>ssm-common</artifactId>
    <packaging>jar</packaging>
</project>
```

3. 同理，跟创建`ssm-common`模块一样，继续创建`ssm-pojo`、`ssm-service`、`ssm-dao`、`ssm-web`等相应的模块。但是我们需要注意的一点是：在我们的依赖模块关系图里，我们知道`ssm-web`模块的打包方式是war包，那我们需要将他的打包方式设置为war包，而不是jar了，为什么要设置成war呢？因为我们的war包最终是要放到tomcat web容器中跑的，你打包成jar怎么放web容器里面跑，是不是？同时`ssm-web`依赖`ssm-service`和`ssm-common`两个模块，我在这里就拿`ssm-web`模块的pom文件讲解下：

 ```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <parent>
        <artifactId>ssm</artifactId>
        <groupId>com.leeyom.ssm</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>ssm-web</artifactId>
    <!--打包方式为war-->
    <packaging>war</packaging>
    <name>ssm-web Maven Webapp</name>
    <url>http://maven.apache.org</url>
    <dependencies>
        <!--junit单元测试，因为父级的pom中junit的scope为test，所以在其他的module要用junit使用单元测试要单独添加junit依赖-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.9</version>
            <scope>test</scope>
        </dependency>
        <!--添加 ssm-common module依赖，因为有时候我们需要一些工具类去处理一些字符串啊，格式化json等等，所以依赖该模块-->
        <dependency>
            <groupId>com.leeyom.ssm</groupId>
            <artifactId>ssm-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--添加 ssm-service module依赖，需要调用业务层处理业务-->
        <dependency>
            <groupId>com.leeyom.ssm</groupId>
            <artifactId>ssm-service</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
    <build>
        <finalName>ssm-web</finalName>
        <plugins>
            <!--tomcat7插件-->
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>${tomcat7-maven-plugin.version}</version>
                <configuration>
                    <!--项目访问路径，如果你设置为根路径，那么访问地址为: http://localhost:8080/ -->
                    <!--如果你改为ssm，那么访问路径变为: http://localhost:8080/ssm/-->
                    <path>${tomcat-path.version}</path>
                    <!--tomcat访问端口-->
                    <port>${tomcat-port.version}</port>
                    <uriEncoding>${tomcat-uri-encoding.version}</uriEncoding>
                    <!--tomcat管理界面路径，固定-->
                    <url>http://localhost:8080/manager/html</url>
                    <server>tomcat7</server>
                    <username>admin</username>
                    <password>admin</password>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```
 
 知道`ssm-web`模块的pom文件的处理方式，那剩下的`ssm-pojo`、`ssm-dao`、`ssm-service`自然就很清楚了吧！具体的话可以看我上传到[github](https://github.com/wangleeyom/ssm-demo)的项目代码。

4. 重点分析父级 pom.xml 文件，下面贴出父级的完整的pom.xml文件，分析都写在pom文件中。 
 
 ```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.leeyom.ssm</groupId>
    <artifactId>ssm</artifactId>
    <!--父级项目的打包方式为pom，需要跟其子模块的打包方式区别开来-->
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <!--聚合所有的子module，父级的pom聚合他下面的所有的子模块，而子模块可以继承父级pom里面的依赖，这就是maven的聚合与继承-->
    <modules>
        <module>ssm-web</module>
        <module>ssm-service</module>
        <module>ssm-common</module>
        <module>ssm-dao</module>
        <module>ssm-pojo</module>
    </modules>
    <!--jar包版本控制-->
    <properties>
        <!--spring版本号-->
        <spring.version>4.1.7.RELEASE</spring.version>
        <!--mybatis版本号-->
        <mybatis.version>3.3.0</mybatis.version>
        <!--mybatis与spring集成版本号-->
        <mybatis-spring.version>1.2.3</mybatis-spring.version>
        <!--mybatis分页插件版本号-->
        <pagehelper.version>4.1.4</pagehelper.version>
        <!--junit单元测试版本号-->
        <junit.version>4.9</junit.version>
        <!--mysql数据库连接驱动版本号-->
        <mysql-connector.version>5.1.37</mysql-connector.version>
        <!--数据库连接池版本号-->
        <druid.version>1.1.2</druid.version>
        <!-- log4j日志文件管理包版本号 -->
        <slf4j.version>1.7.7</slf4j.version>
        <log4j.version>1.2.17</log4j.version>
        <!--servlet相关版本号-->
        <javaee-api.version>7.0</javaee-api.version>
        <jstl.version>1.2</jstl.version>
        <jsp-api.version>2.3.1</jsp-api.version>
        <servlet-api.version>3.1.0</servlet-api.version>
        <!--文件上传组件版本号-->
        <commons-fileupload.version>1.3.1</commons-fileupload.version>
        <commons-io.version>2.4</commons-io.version>
        <commons-codec.version>1.9</commons-codec.version>
        <!--apache工具包-->
        <commons-lang3.version>3.3.2</commons-lang3.version>
        <!--json格式化组件版本号-->
        <json-lib.version>2.4</json-lib.version>
        <fastjson.version>1.2.35</fastjson.version>
        <gson.version>2.2.4</gson.version>
        <!--插件版本号-->
        <tomcat7-maven-plugin.version>2.2</tomcat7-maven-plugin.version>
        <maven-compiler-plugin.version>3.6.2</maven-compiler-plugin.version>
        <!--其他配置-->
        <jdk.version>1.7</jdk.version>
        <tomcat-port.version>8089</tomcat-port.version>
        <tomcat-uri-encoding.version>UTF-8</tomcat-uri-encoding.version>
        <tomcat-path.version>/ssm-demo</tomcat-path.version>
    </properties>
    <dependencies>
        <!--spring核心包-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-oxm</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!-- mybatis核心包 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>${mybatis.version}</version>
        </dependency>
        <!-- mybatis集成spring包 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>${mybatis-spring.version}</version>
        </dependency>
        <!-- Mysql数据库链接jar包 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql-connector.version}</version>
            <scope>runtime</scope>
        </dependency>
        <!-- mybatis分页插件 -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>${pagehelper.version}</version>
        </dependency>
        <!--阿里巴巴德鲁伊数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>${druid.version}</version>
        </dependency>
        <!-- JSTL标签类 -->
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>${jstl.version}</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>${servlet-api.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>${jsp-api.version}</version>
            <scope>provided</scope>
        </dependency>
        <!-- java ee jar 包 -->
        <dependency>
            <groupId>javax</groupId>
            <artifactId>javaee-api</artifactId>
            <version>${javaee-api.version}</version>
            <scope>provided</scope>
        </dependency>
        <!--单元测试-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <!--日志管理-->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>${log4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <!-- json格式化组件 -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>${gson.version}</version>
        </dependency>
        <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <version>${json-lib.version}</version>
            <classifier>jdk15</classifier>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${fastjson.version}</version>
        </dependency>
        <!-- 上传组件包 -->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>${commons-fileupload.version}</version>
        </dependency>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>${commons-io.version}</version>
        </dependency>
        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
            <version>${commons-codec.version}</version>
        </dependency>
        <!--apache工具包-->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>${commons-lang3.version}</version>
        </dependency>
    </dependencies>
    <build>
        <finalName>ssm</finalName>
        <plugins>
            <!--编译插件-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <!--设置source和target版本，解决IDEA自动重置Language Level和JavaCompiler版本的问题-->
                    <source>${jdk.version}</source>
                    <target>${jdk.version}</target>
                    <encoding>UTF-8</encoding>
                    <showWarnings>true</showWarnings>
                    <!--如果lib目录下面有jar包，将lib目录已有的jar包打包进war-->
                    <compilerArguments>
                        <extdirs>src\main\webapp\WEB-INF\lib</extdirs>
                    </compilerArguments>
                </configuration>
            </plugin>
            <!--tomcat7插件-->
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>${tomcat7-maven-plugin.version}</version>
                <configuration>
                    <!--项目访问路径，如果你设置为根路径，那么访问地址为: http://localhost:8080/ -->
                    <!--如果你改为ssm，那么访问路径变为: http://localhost:8080/ssm/-->
                    <path>${tomcat-path.version}</path>
                    <!--tomcat访问端口-->
                    <port>${tomcat-port.version}</port>
                    <uriEncoding>${tomcat-uri-encoding.version}</uriEncoding>
                    <!--tomcat管理界面路径，固定-->
                    <url>http://localhost:8080/manager/html</url>
                    <server>tomcat7</server>
                    <username>admin</username>
                    <password>admin</password>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
 ```

## SSM框架的整合

通过上面搭建maven好的maven多模块项目的骨架，现在就可以开始ssm项目的整合了，我的开发环境和所用的三大框架的版本如下：

开发环境是：
  - IntelliJ IDEA 2017.1
  - JDK 1.8.0_45
  - Tomcat 7
  - Maven 3.3.9
  - MySQL 5.7.16  

三大框架版本：
  - Spring 4.1.7.RELEASE
  - Spring MVC 4.1.7.RELEASE
  - MyBatis 3.3.0

核心配置文件： 

| 文件名 | 所属目录 | 描述 |
| :-: | :-: | :-: |
| mybatis-config.xml | ssm-web/src/main/resources/spring | mybatis分页插件pagehelper配置文件 |
| spring-mvc.xml | ssm-web/src/main/resources/spring | 配置spring mvc，比如配置视图解析器、文件上传、spring mvc 注解等等。 |
| spring-mybatis.xml | ssm-web/src/main/resources/spring | spring与mybatis的整合文件，数据源、自动扫描、事务管理等都是在这里配置。 |
| xxxMapper.xml | ssm-web/src/main/resources/mapper | 这个就是通过[mybatis-generator](https://github.com/wangleeyom/mybatis-generator)自动生成的mapper数据库映射文件。 |
| jdbc.properties | ssm-web/src/main/resources/ | 这个不多说，配置数据库连接信息 |
| log4j.properties | ssm-web/src/main/resources/ | 日志配置文件，方便调试bug，打印日志，需要配置此项 |


### Spring 与 mybatis 的整合

所有的ssm整合配置项都是在`ssm-web`这个子模块下面进行配置，先来看一下`ssm-web`模块的目录结构：![](http://og1m51u2s.bkt.clouddn.com/15017651453821.jpg)


1. **建立`jdbc.properties`数据库属性文件**
 
 ```java
jdbc.url=jdbc:mysql://localhost:3306/ssm-demo?useUnicode=true&characterEncoding=utf8
jdbc.username=root
jdbc.password=root
```
 因为我们这边用的是阿里巴巴的**德鲁伊数据库连接池**，并不需要配置jdbc.driver，如果你用的是c3p0或者dbcp数据库连接池，是要配置jdbc.driver。

2. **创建`spring-mybatis.xml`核心配置文件**

 该配置文件也是spring整合mybatis的核心配置文件，事务管理，数据源，自动扫描都是在这里配置，具体的详情，可以看配置文件里面的注释。

 ```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
                        http://www.springframework.org/schema/context
                        http://www.springframework.org/schema/context/spring-context-3.1.xsd">
    <!-- 自动扫描 -->
    <context:component-scan base-package="com.leeyom"/>
    <!-- 引入配置文件 -->
    <bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="location" value="classpath:jdbc.properties"/>
    </bean>
    <!--使用阿里巴巴的德鲁伊数据源，这里使用官方给出的参考，德鲁伊官方配置参考：https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_DruidDataSource%E5%8F%82%E8%80%83%E9%85%8D%E7%BD%AE-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <!-- 基本属性 url、user、password -->
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <!-- 配置初始化大小、最小、最大 -->
        <property name="initialSize" value="1"/>
        <property name="minIdle" value="1"/>
        <property name="maxActive" value="20"/>
        <!-- 配置获取连接等待超时的时间 -->
        <property name="maxWait" value="60000"/>
        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="60000"/>
        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="300000"/>
        <property name="validationQuery" value="SELECT 'x'"/>
        <property name="testWhileIdle" value="true"/>
        <property name="testOnBorrow" value="false"/>
        <property name="testOnReturn" value="false"/>
        <!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->
        <property name="poolPreparedStatements" value="true"/>
        <property name="maxPoolPreparedStatementPerConnectionSize" value="20"/>
        <!-- 配置监控统计拦截的filters -->
        <property name="filters" value="stat"/>
    </bean>
    <!-- spring和MyBatis整合 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--mybatis分页插件-->
        <property name="configLocation" value="classpath:spring/mybatis-config.xml"></property>
        <!-- 自动扫描mapping.xml文件 -->
        <property name="mapperLocations" value="classpath:mapper/*.xml"></property>
    </bean>
    <!-- DAO接口所在包名，Spring会自动查找其下的类 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.leeyom.dao"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
    </bean>
    <!-- 事务管理 -->
    <bean id="transactionManager"
         class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
</beans>
```

3. **创建`mybatis-config.xml`分页插件配置文件**
  
  mybatis分页插件`PaheHelper`是一个非常好用的分页插件，也可以通过配置文件的形式整合到mybatis中，具体怎么使用可以参考我以前写的文章:[**mybatis 分页插件PageHelper使用及总结。**](http://leeyom.top/%2F2016%2F12%2F05%2Fmybatis-%E5%88%86%E9%A1%B5%E6%8F%92%E4%BB%B6PageHelper%E4%BD%BF%E7%94%A8%E5%8F%8A%E6%80%BB%E7%BB%93%2F)具体的配置如下：

 ```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<settings>
		<setting name="mapUnderscoreToCamelCase" value="true" />
	</settings>
	<!-- 配置分页插件 -->
	<plugins>
		<plugin interceptor="com.github.pagehelper.PageHelper">
			<!-- 指定数据库方言 -->
			<property name="dialect" value="mysql" />
		</plugin>
	</plugins>
</configuration>
```
4. **创建Log4j日志配**
为了方便查看控制台打印的日志，需要配置log4j日志配置文件：

 ```java
#定义LOG输出级别
log4j.rootLogger=INFO,Console,File  
#定义日志输出目的地为控制台
log4j.appender.Console=org.apache.log4j.ConsoleAppender  
log4j.appender.Console.Target=System.out  
#可以灵活地指定日志输出格式，下面一行是指定具体的格式
log4j.appender.Console.layout=org.apache.log4j.PatternLayout  
log4j.appender.Console.layout.ConversionPattern=[%c] - %m%n  
#文件大小到达指定尺寸的时候产生一个新的文件
log4j.appender.File=org.apache.log4j.RollingFileAppender  
#指定输出目录
log4j.appender.File.File=logs/ssm-demo.log  
#定义文件最大大小
log4j.appender.File.MaxFileSize=10MB  
#输出所以日志，如果换成DEBUG表示输出DEBUG以上级别日志
log4j.appender.File.Threshold=ALL  
log4j.appender.File.layout=org.apache.log4j.PatternLayout  
log4j.appender.File.layout.ConversionPattern=[%p] [%d{yyyy-MM-dd HH\:mm\:ss}][%c]%m%n  
```


5. **测试spring与mybatis的整合情况**
 
 创建测试表`t_user`，表结构如下：
 
 ```sql
 DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `u_id` int(100) NOT NULL AUTO_INCREMENT,
  `u_name` varchar(100) DEFAULT NULL,
  `u_password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS = 1;
```
 再往该表中随便插入两条条数据：

 ```sql
INSERT INTO `t_user` VALUES ('1', 'leeyom', 'root'), ('2', '小明', 'admin');
```

 利用[mybatis-generator](https://github.com/wangleeyom/mybatis-generator)生成`UserMapper.java`、`User.java`、`UserMapper.xml`等相关的文件。文件是生成了，但是得放到对的位置，`UserMapper.java`放到`ssm-dao`模块下面，因为该层主要跟数据库进行交互，所以，dao层的接口文件就是放在该模块下面。`User.java`放到`ssm-pojo`模块下面，该模块主要存放实体类的bean，而`UserMapper.xml`文件
则放在`ssm-web`模块的resources资源目录下面的`mapper`文件夹下面，放在此模块下面的原因是，该映射文件将打包一起部署到web服务器上面，如果放到`ssm-dao`模块，`ssm-dao`的打包方式是jar，到时候会出现引用到不该数据库映射文件。最后我们还需要在`ssm-service`模块创建接口类`UserService.java`

 ```java
package com.leeyom.service.user;
import com.leeyom.pojo.User;
public interface UserService {
    public User getUserById(Integer userId);
}
```

 接口的实现类`UserServiceImpl.java`

 ```java
package com.leeyom.service.user.impl;
import com.leeyom.dao.user.UserMapper;
import com.leeyom.pojo.User;
import com.leeyom.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
 ```
 整个的目录如下:
 ![](http://og1m51u2s.bkt.clouddn.com/15017702169074.jpg)

 下面进行测试，在`ssm-web`模块的的`test/java`目录下面，新建测试类`TestMybatis.java`

 ```java
import com.leeyom.pojo.User;
import com.leeyom.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class TestMybatis {
    private ApplicationContext ac = null;
    private UserService userService = null;
    @Before
    public void before() {
        ac = new ClassPathXmlApplicationContext("classpath:spring/spring-mybatis.xml");
        userService = (UserService) ac.getBean("userService");
    }
    @Test
    public void testMybtis() {
        User user = userService.getUserById(1);
        System.out.println(user.getuName() + "------>" + user.getuPassword());
    }
}
```
 
 如果打印信息如下，说明是spring与mybatis整合成功
 
 ![](http://og1m51u2s.bkt.clouddn.com/15017709844151.jpg)

那其实现在就有个问题，为什么`ssm-service`模块可以调用到`ssm-dao`模块呢？这就是我们之前说的，模块之间的依赖的关系，因为`ssm-service`的pom文件中有依赖到`ssm-dao`和`ssm-common`两个模块，所以在`ssm-service`模块可以调用到`ssm-dao`中的类，这也就验证了我们之前的各模块的依赖关系图。


### 整合Spring mvc

spring 与 mybatis已经整合完毕，接下来Spring mvc的整合。
![](http://og1m51u2s.bkt.clouddn.com/15017723644703.jpg)


1. **创建spring-mvc.xml配置文件**

 该配置文件的存放路径依旧是`ssm-web`模块的资源文件夹`resources/spring`下面，主要的内容如下：
 
 ```xml
 <?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 自动装配 -->
    <context:component-scan base-package="com.leeyom.controller" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        <context:include-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
    </context:component-scan>

    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"></property>
    </bean>

    <!-- 文件上传 -->
    <bean id="multipartResolver"
          class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <!-- 默认编码 -->
        <property name="defaultEncoding" value="utf-8"/>
        <!-- 文件大小最大值 -->
        <property name="maxUploadSize" value="10485760000"/>
        <!-- 内存中的最大值 -->
        <property name="maxInMemorySize" value="40960"/>
    </bean>

    <!--启用该标签代表 spring mvc 不拦截css、js、jpg等相关的静态资源-->
    <mvc:default-servlet-handler/>

    <!-- 启用spring mvc 注解 -->
    <mvc:annotation-driven></mvc:annotation-driven>
</beans>
 ```

2. **配置ssm-web模块下面的web.xml文件**
 千万别忘记配置web.xml文件，否则的话，在实际的生产测试环境下，之前的整合都白费了，将不起作用，具体内容如下：
 
 ```xml
 <?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0">
    <display-name>Archetype Created Web Application</display-name>
    <!-- Spring和mybatis的配置文件 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/spring-mybatis.xml</param-value>
    </context-param>
    <!-- 编码过滤器 -->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <async-supported>true</async-supported>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- Spring监听器 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- 防止Spring内存溢出监听器 -->
    <listener>
        <listener-class>org.springframework.web.util.IntrospectorCleanupListener</listener-class>
    </listener>

    <!-- Spring MVC -->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring/spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
        <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    <welcome-file-list>
        <welcome-file>/index.jsp</welcome-file>
    </welcome-file-list>
</web-app>
 ```
 
3. **在ssm-web模块的java文件夹下面创建`UserHandler.java`测试handler类**

 ```java
@Controller
public class UserHandler {
    @Autowired
    UserService userService;
    /**
     * description: 测试 spring mvc
     * author: leeyom
     * date: 2017-07-31 10:47
     * Copyright © 2017 by leeyom
     */
    @RequestMapping(value = "/getUserById", method = {RequestMethod.POST, RequestMethod.GET})
    public String getUserById(HttpServletRequest request, Model model) {
        //参数
        Integer userId = StringUtils.notNull(request.getParameter("userId")) ? Integer.parseInt(request.getParameter("userId")) : 1;
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "test";
    }
}
 
 ```

4. **在ssm-web模块的WEB-INF文件夹下面创建test.jsp页面**

 ```html
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试spring mvc</title>
</head>
<body>
    <h1> hello world! </h1> ${user.uName}
</body>
</html>
 ```

6. 经过以上的准备，我们现在就可以编译整个项目，编译的结果如下，就说明编译成功，否则就是编译失败。 

 ![](http://og1m51u2s.bkt.clouddn.com/15017725961376.jpg)
 ![](http://og1m51u2s.bkt.clouddn.com/15017726289381.jpg)

7. 通过整个项目的编译，**ssm-common**，**ssm-pojo**，**ssm-dao**，**ssm-service**分别被达成jar，**ssm-web**被打包成war，最后我们就是要把war通过tomcat容器跑起来。
 ![](http://og1m51u2s.bkt.clouddn.com/15017730044316.jpg)
 ![](http://og1m51u2s.bkt.clouddn.com/15017730986546.jpg)

8. 访问[localhost:8089/ssm-demo/getUserById?userId=1](localhost:8089/ssm-demo/getUserById?userId=1)，如果页面出现如下的熟悉界面，恭喜你，三大框架整合成功！！！
 ![](http://og1m51u2s.bkt.clouddn.com/15017732128821.jpg)


 ## 总结
到此为止我们**Maven的多模块 Spring MVC + Spring + Mybatis 项目的搭建**就已经完成，后期就可以在此基础上添加更多的功能。写这篇文章的目的是看到很多的单模块的ssm项目的搭建，却很少看到多模块的ssm项目搭建，所以就在此抛砖迎玉，重新搭建了一番，中间如果有什么不懂的，或者我写的不对的，大家都可以讨论。如果你觉得有用，就帮忙点个star吧！
源码地址：https://github.com/wangleeyom/ssm-demo


