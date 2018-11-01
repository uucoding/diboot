# 介绍

diboot-mobile 是diboot开发平台为适应移动端开发，而研发的一套开发效率高，复用性号，交互性好的一个基础移动端基础项目。

## 用到的相关工具
* vue-cli 辅助开发流程，提高开发效率。
* stylus 整合样式代码，减少代码量，提高复用性。
* es-lint 检查代码语法，并自动优化相关写法，使用的标准为airbnb标准（有些选项有所更改）。
* vue-router 控制页面路由，便于构建移动端单页应用，提高用户体验。
* axios 与后端的数据交互，在diboot-mobile中我们还集成了token授权，使得登录授权，以及OAuth授权等开发更加便捷，开箱即用。
* vee-validate 表单校验工具，diboot-mobile中已经配置了vee-validate，只需要按照文档中的示例来使用即可。
* moment 对于日期时间的处理。
* weixin-js-sdk 如果是开发与微信平台相关的移动应用，可以使用该sdk提供的接口调用js-sdk。
