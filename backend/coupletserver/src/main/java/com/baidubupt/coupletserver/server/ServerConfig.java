package com.baidubupt.coupletserver.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {

    /**
     * 默认配置文件路径
     */
    public static final String DEFAULT_CONFIG_PATH = System.getProperty("config", "conf/conf.properties");

    private int port;
    private String host;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public static ServerConfig parse(String[] args) throws Exception {
        String configPath = DEFAULT_CONFIG_PATH;
        if (args != null && args.length > 0) {
            configPath = args[0];
        }

        File configFile = new File(configPath);
        try (InputStream inputStream = new FileInputStream(configFile)) {
            return parse(inputStream);
        }
    }

    public static ServerConfig parse(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        if (inputStream != null) {
            properties.load(inputStream);
        }

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(Integer.parseInt(properties.getProperty("server.port", "9000")));
        serverConfig.setHost(properties.getProperty("server.host", "localhost"));
        return serverConfig;
    }

}
