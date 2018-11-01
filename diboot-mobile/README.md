1. 关于项目开发配置都在项目根目录下的vue.config.js中
2. 关于项目运行配置都在config/index.js中
3. 启动项目使用yarn serve，Mac下如果以80端口启动，则需要sudo
4. Mac下使用sudo权限后，安装相关依赖失败的话，需要使用sudo chown -R user node_modules 命令改变文件所有者
5. 企业微信授权需要使用企业微信回调授权地址在微信开发者工具中进行访问，应用初始化的时候，会等待获取token，获取到token后才会开始运行，这样可以避免在拿到token之前发出请求从而导致相应的错误出现
6. 增加了put，delete等的请求方式，调用方式同get和post
7. 每个组件中样式都是用stylus来编写，在style标签上不要忘记添加属性：lang="stylus" rel="stylesheet/stylus"
8. stylus中（src/common/stylus/）：
    1. 对于一个业务中通用的样式，可以就写在比如postsale这个业务文件夹下面，相关图片也可以直接放在下面。
    2. 需要使用mixin的地方，就使用mixin，多看下base.styl下，你们也可以添加mixin到这下面，相当于freemarker中的macro
    3. custom下用于存放对于mint-ui这个UI组件的统一样式更改
    4. views下存放某些页面组件通用的样式
9. filter过滤器设置：统一添加到filter/index.js中，按照现在的格式来添加，在项目初始化的时候，会自动加载进去
10. 项目启动之前要做的事情（以前需要写到main.js中的），现在统一放到utils/start.js中
11. 表单验证使用的是vee-validate，[github地址](https://github.com/baianat/vee-validate)，相关功能已经封装到utils/validator.js，更改消息内容可以在该文件里changeMessage方法中扩展，添加其他校验规则可以在addRules方法中扩展