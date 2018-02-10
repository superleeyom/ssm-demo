<p align="center">
  <a href="#"><img src="https://img.shields.io/badge/devTool-IDEA-yellow.svg" alt=""></a>
  <a href="#"><img src="https://travis-ci.org/Alamofire/Alamofire.svg?branch=master" alt=""></a>
  <a href="#"><img src="https://img.shields.io/packagist/l/doctrine/orm.svg" alt="LICENSE"></a>
  <a href="#"><img src="https://img.shields.io/badge/platform-OSX%7CWin%7CLinux-blue.svg" alt=""></a>
  <a href="#"><img src="https://badges.frapsoft.com/os/v1/open-source.svg?v=103" alt=""></a>   	
  <a href="#"><img src="https://img.shields.io/badge/language-java-blue.svg" alt=""></a>  
</p>

我想大多数做java开发的同学对SSM框架的搭建已经都轻车熟路了，但是大家搭建的大多数是单模块的，网上也有非常多的例子，但是如何用maven去构建多module的ssm项目呢？下面来手把手的，利用IntelliJ IDEA搭建一次多module的ssm项目，给大家一个参考。

<!-- more -->

## 目的

首先抛出这么一个问题，我们为什么要搭建多模块的maven项目呢？以多模块的方式组织项目，其实也是maven一直倡导的。做开发的同学都知道的一个概念就是“低耦合，高内聚”，将项目划分多模块，可以极大的增大代码的重用性，防止随着项目的增大，pom文件越来越臃肿。

对于一般的java项目我们一般是这么分层的：

* dao层负责数据库的交互。
* service层主要处理业务逻辑。
* web层负责与客户端交互。
* pojo层存放的是实体类。
* common层存放我们常用的一些公用的工具类等。

对应的，在一个项目中，我们会看到一些包名：

* `com.leeyom.ssm.dao`
* `com.leeyom.ssm.service`
* `com.leeyom.ssm.web`
* `com.leeyom.ssm.common`
* `com.leeyom.ssm.pojo`

这样整个项目的框架就清晰了，但随着项目的进行，你可能会遇到如下问题：

1. 这个应用可能需要有一个前台和一个后台管理端，你发现大部分dao，一些service，和大部分util是在两个应用中均可用。
2. pom.xml中的依赖列表越来越长以重用的，但是，由于目前只有一个项目（WAR），你不得不新建一个项目依赖这个WAR，这变得非常的恶心，因为在Maven中配置对WAR的依赖远不如依赖JAR那样简单明了，而且你根本不需要com.leeyom.ssm.web。有人修改了dao，提交到svn并且不小心导致build失败了，你在编写service的代码，发现编译不过，只能等那人把dao修复了，你才能继续进行，很多人都在修改，到后来你根本就不清楚哪个依赖是谁需要的，渐渐的，很多不必要的依赖被引入。甚至出现了一个依赖有多个版本存在。
3. build整个项目的时间越来越长，尽管你只是一直在web层工作，但你不得不build整个项目。
4. 某个模块，比如util，你只想让一些经验丰富的人来维护，可是，现在这种情况，每个开发者都能修改，这导致关键模块的代码质量不能达到你的要求。

我们会发现，其实这里实际上没有遵守一个设计模式原则：“高内聚，低耦合”。虽然我们通过包名划分了层次，并且你还会说，这些包的依赖都是单向的，没有包的环依赖。这很好，但还不够，因为就构建层次来说，所有东西都被耦合在一起了。因此我们需要使用Maven划分模块。

> 以上的讲解是参考：[Maven最佳实践：划分模块](http://juvenshun.iteye.com/blog/305865)

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
