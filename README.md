# mlcouplet

Auto generate couplet by using baidu ML framework paddlepaddle

# 环境

```
- 64 位操作系统
- JDK 1.8
- python 2.7
- pip install numpy
- pip install paddlepaddle
```

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
