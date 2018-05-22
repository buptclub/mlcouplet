package com.baidubupt.coupletserver.server;

import com.baidubupt.coupletserver.util.Utils;
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

        String privateHost = serverConfig.getHost();
        if (privateHost == null || privateHost.isEmpty()) {
            privateHost = Utils.getLocalhostByNetworkInterface();
        }

        // 绑定端口和地址
        Connector connector = new Connector("HTTP/1.1");
        connector.setPort(serverConfig.getPort());
        connector.setAttribute("address", privateHost);
        tomcat.getService().addConnector(connector);

        // 启用本地监听
        if (!isLocalhostAddress(privateHost)) {
            Connector localConnector = new Connector("HTTP/1.1");
            localConnector.setPort(serverConfig.getPort());
            localConnector.setAttribute("address", "localhost");
            tomcat.getService().addConnector(localConnector);
        }

        // 添加项目
        tomcat.addWebapp("/", getWebAppDirectory().getAbsolutePath());

        // 启动
        tomcat.start();
        LOGGER.info("start server at http://{}:{}", privateHost, serverConfig.getPort());
        LOGGER.info("start server at http://{}:{}", "localhost", serverConfig.getPort());

        tomcat.getServer().await();
    }

    private boolean isLocalhostAddress(String privateHost) {
        return privateHost.equalsIgnoreCase("localhost") || privateHost.equalsIgnoreCase("127.0.0.1");
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