<p align="center"><img src="http://image.leeyom.top/blog/180210/dc3bHI35c6.png"></p>
<p align="center">
  <a href="#"><img src="https://img.shields.io/badge/devTool-IDEA-yellow.svg" alt=""></a>
  <a href="#"><img src="https://travis-ci.org/Alamofire/Alamofire.svg?branch=master" alt=""></a>
  <a href="#"><img src="https://img.shields.io/packagist/l/doctrine/orm.svg" alt="LICENSE"></a>
  <a href="#"><img src="https://img.shields.io/badge/platform-OSX%7CWin%7CLinux-blue.svg" alt=""></a>
  <a href="#"><img src="https://badges.frapsoft.com/os/v1/open-source.svg?v=103" alt=""></a>   	
  <a href="#"><img src="https://img.shields.io/badge/language-java-blue.svg" alt=""></a>  
</p>

# 项目结构
## 项目目录
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
├── ssm-web -- 存放前台页面，客户端交互相关的controller
|    ├── src
|    ├── pom.xml -- 打包方式：war
├── pom.xml --打包方式为pom，一些公用的依赖
```

## 模块依赖关系

![20180211151831567174772.png](http://image.leeyom.top/20180211151831567174772.png)

# 过程

更具体的内容可以参考我博客文章：[《Maven的多模块 Spring MVC + Spring + Mybatis 项目的搭建》](http://www.leeyom.top/2017/08/01/tech-maven-multi-module-ssm/)，这里就不详述了。
