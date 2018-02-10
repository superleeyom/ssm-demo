<p align="center">
  <a href="#"><img src="https://img.shields.io/badge/devTool-IDEA-yellow.svg" alt=""></a>
  <a href="#"><img src="https://travis-ci.org/Alamofire/Alamofire.svg?branch=master" alt=""></a>
  <a href="#"><img src="https://img.shields.io/packagist/l/doctrine/orm.svg" alt="LICENSE"></a>
  <a href="#"><img src="https://img.shields.io/badge/platform-OSX%7CWin%7CLinux-blue.svg" alt=""></a>
  <a href="#"><img src="https://badges.frapsoft.com/os/v1/open-source.svg?v=103" alt=""></a>   	
  <a href="#"><img src="https://img.shields.io/badge/language-java-blue.svg" alt=""></a>  
</p>

我想大多数做java开发的同学对SSM框架的搭建已经都轻车熟路了，但是大家搭建的大多数是单模块的，网上也有非常多的例子，但是如何用maven去构建多module的ssm项目呢？下面来手把手的，利用IntelliJ IDEA搭建一次多module的ssm项目，给大家一个参考。

## 项目结构

### 项目目录

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

### 模块依赖关系

![](http://og1m51u2s.bkt.clouddn.com/15015988179183.jpg)

## 过程

更具体的内容可以参考我博客文章：[《Maven的多模块 Spring MVC + Spring + Mybatis 项目的搭建》](http://www.leeyom.top/2017/08/01/tech-maven-multi-module-ssm/)，这里就不详述了。
