1. 安装node。

2. 全局安装 gitbook
```aidl
npm install -g gitbook-cli
```

3. 在终端中进入到相关项目文档中
```aidl
cd diboot-mobile
```

4. 在相应的文档项目中的[README.md]()文件中添加相关文档目录列表，运行gitbook init即可生成新添的文档目录对应的文档文件。
```aidl
gitbook init
```

5. 安装相关依赖和插件
```aidl
gitbook install
```

6. 启动文档项目，浏览器访问 [http:localhost:4000](http://localhost:4000)，即可访问该项目文档。
```aidl
gitbook serve
```

7. 运行gitbook build，即可编译文档，相关目标文件放置到相应文档项目中__book文件夹中。
```aidl
gitbook build
```
