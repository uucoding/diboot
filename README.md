# diboot 基础组件及基础项目介绍
此仓库是diboot相关的基础框架组件和web/rest项目的基础代码仓库，如果您需要: 
 > 使用Diboot和Diboot开发助理，直接从 [生成初始项目](http://devtools.diboot.com/diboot-api/html/start.html) 开始
 > 了解Diboot和Diboot开发助理，[请到这里](http://www.diboot.com/)
 > 了解diboot源码或者加入我们一起维护diboot组件和文档，接着往下看

## 基础框架 diboot-framework
    diboot体系的基础框架，广泛适用于Java Web开发的非常轻量级的封装框架，基于Spring+Mybatis，提供常用开发场景下的代码轻度封装，主要有:
1. CRUD通用封装: 
    > 支持增删改查、批量插入、更新指定字段、动态查询条件等
2. MVC通用封装: 
    > 提供Model、Controller、RestController以及view页面相关的通用功能封装
3. 认证与安全: 
    > 提供基于Apache Shiro的JWT授权认证 以及 XSS、CSRF等安全问题防护的实现
4. 系统参数配置：
    > 支持兼容数据库表或Properties文件存放的系统参数配置实现方案
5. 系统日志记录：
    > 提供系统异常日志、操作日志、跟踪日志的数据库记录实现
6. 常用工具类：
    > 如日期类D、字符串类S、校验类V、Bean工具类、Json工具类、加解密类、Long型id生成器类等
7. 常用基础模型：
    > 提供Web开发常用的用户、角色、菜单、权限、文件、元数据等基础功能的支持

## 基础组件 diboot-components-*
    开发过程中常用的一些功能组件封装，有:
1. 文件处理组件 diboot-components-file: 
    > 支持Excel解析、文件上传下载、图片压缩等的通用封装
2. 信息通知组件 diboot-components-msg: 
    > 支持Email、短信发送等的功能封装
    > 信息通知记录管理，信息模板管理等管理功能和页面    

## Web管理后台基础项目 diboot-web
    集成了PC端web系统开发必备的基础功能的基础项目，基于此，可以快速开始您的web系统相关开发，Diboot开发助理（Web）也是运行于此项目之上。
    
* diboot-web内置了web开发常用的一些功能，比如元数据管理，用户管理，菜单角色权限管理，操作日志管理等等
* diboot-web目前版本基于Spring MVC 5.0 (后续将推出基于Spring Boot 2.x的版本) 和 Freemarker、FastJson，数据库连接池默认为HikariCP。
* diboot-web支持基础版(基于AdminLTE)和专业版(基于Metronic 4.7)
    > Metronic是商业模板，如果需要使用专业版，请先[购买授权](https://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469)，
      然后使用 [diboot初始项目生成](http://devtools.diboot.com/diboot-api/html/start.html) 页面生成基于专业版模板的web初始项目。
* Diboot开发助理（Web）是针对PC端web系统开发的利器，可以让你脱离重复、专注业务、效率倍增
        
## Rest接口开发基础项目 diboot-rest
     集成了rest接口开发必备的基础功能的基础项目，基于此，可以快速开始您的rest接口相关开发，Diboot开发助理（rest）也是运行于此项目之上。
  
* diboot-rest预置了rest接口开发常用的一些功能，比如基于用户名密码或JWT的认证、异常处理等
* diboot-rest预置了JSON包装、返回页面等的样例配置
* diboot-rest基于Spring Boot 2.x + Shiro + Freemarker + FastJson，数据库连接池默认为HikariCP
* diboot-web支持基础版(基于AdminLTE)和专业版(基于Metronic 4.7)
* Diboot开发助理（rest）是针对rest接口开发的利器，可以让你脱离重复、专注业务、效率倍增
 
## 移动端H5开发基础项目 diboot-mobile
    diboot-mobile 是为移动端web开发而封装的一套基于Vue.js的基础框架。

* 主要依赖vue.js、vue-router、vue-cli、stylus、es-lint、axios、vee-validate、moment
* 还提供weixin-js-sdk 便于开发与微信平台相关的移动应用时，通过该sdk提供的接口统一调用微信js-sdk
* 基础CRUD类的页面可通过Diboot开发助理（rest）直接生成

## 微信开发相关组件 diboot-wechat-*
  用于微信开发的简单封装，基于 [wexin-java-tools](https://github.com/wechat-group/weixin-java-tools)
1. 微信服务号开发 diboot-wechat-mp: 
    > 对接weixin-java-mp，简化接口的初始化和调用流程
2. 企业微信开发 diboot-wechat-cp: 
    > 对接weixin-java-cp，简化接口的初始化和调用流程
3. 企业微信开发 diboot-wechat-open: 
    > 对接weixin-java-open，简化接口的初始化和调用流程

## diboot相关文档 diboot-docs
    diboot相关的文档，包含以上所有组件项目的文档，基于gitbook维护
