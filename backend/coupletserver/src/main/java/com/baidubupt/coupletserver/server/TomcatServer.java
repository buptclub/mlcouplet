package com.baidubupt.coupletserver.server;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TomcatServer extends AbstractServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServer.class);

    protected ServerConfig serverConfig;

    protected Tomcat tomcat;

    public TomcatServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
        this.tomcat = new Tomcat();
    }

    @Override
    public void start() throws Exception {
        tomcat.setBaseDir(getTempDirectory().getAbsolutePath());

        // 绑定端口和地址
        Connector connector = new Connector("HTTP/1.1");
        connector.setPort(serverConfig.getPort());
        connector.setAttribute("address", serverConfig.getHost());
        tomcat.setConnector(connector);

        // 添加项目
        tomcat.addWebapp("/", getWebAppDirectory().getAbsolutePath());

        // 启动
        tomcat.start();
        LOGGER.info("start server at http://{}:{}", serverConfig.getHost(), serverConfig.getPort());

        tomcat.getServer().await();
    }

    @Override
    public void shutdown() throws Exception {
        if (tomcat != null) {
            tomcat.stop();
            tomcat.destroy();
            tomcat = null;
        }

        LOGGER.info("stop server");
    }

}