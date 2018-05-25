# mlcouplet

Auto generate couplet by using baidu ML framework paddlepaddle

# 环境

```
- 64 位操作系统,至少 2G 内存
- 下载模型文件放到 core/model 文件夹下面
- JDK 1.8
- python 2.7
- pip install numpy
- pip install paddlepaddle
```

模型文件地址: [Google Driver](https://drive.google.com/open?id=1xQCHvi4m5GYkD_XDGDyJ_OAcmFDaHdV_)

# 打包

``` bash
cd bin
./package.sh
```

打包后的文件默认放在项目主目录下

# 运行

``` bash
unzip coupletserver-release-all.zip
cd coupletserver-release
./start.sh start
```

通过 http://localhost:9000 来访问

# 关闭

``` bash
./start.sh stop
```
