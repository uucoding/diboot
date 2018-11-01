# 使用diboot-framework

## 新项目中使用
新起项目请通过我们的项目初始化页面配置生成项目初始代码:
1. 打开[diboot官网](http://diboot.com)，进入[项目配置下载](http://diboot.com/)页面
2. 按照自己的项目需求进行配置，提交后下载项目基础代码（含diboot-framework依赖）
3. 将项目代码导入到您的IDEA或Eclipse等IDE中
4. 在导入成功后，启用刷新gradle下载依赖jar包。
5. 如您启用了"[diboot开发助理](http://devtools.diboot.com)"，项目初次运行时开发助理会自动创建framework所依赖的数据库表，
   否则需要您手动执行framework下的install.sql文件到您的数据库。
   
## 已有项目中使用
1. 使用maven和gradle相关包管理工具
    * 到[maven中央仓库](http://mvnrepository.com/)搜索diboot-framework的坐标，进行配置下载
2. 没有使用maven和gradle相关包管理工具的项目
    * 进入[Github项目主页](https://github.com/dibo-software)进行下载相关版本的jar包，
    * 选择相关framework版本包下载，添加到已有项目下
3. 手动执行framework下的install.sql文件到您的数据库（可基于您的需求选择执行所需的部分表）