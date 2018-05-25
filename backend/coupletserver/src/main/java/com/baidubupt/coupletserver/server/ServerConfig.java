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

    /** single instance **/
    private static volatile ServerConfig instance;

    private int port;
    private String host;
    private String pythonPath;
    private String coupletGeneratePythonFilePath;
    private String tmpFileDirectory;
    private String modelPath;
    private String vocabsPath;

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

    public String getVocabsPath() {
        return vocabsPath;
    }

    public void setVocabsPath(String vocabsPath) {
        this.vocabsPath = vocabsPath;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getPythonPath() {
        return pythonPath;
    }

    public void setPythonPath(String pythonPath) {
        this.pythonPath = pythonPath;
    }

    public String getCoupletGeneratePythonFilePath() {
        return coupletGeneratePythonFilePath;
    }

    public String getTmpFileDirectory() {
        return tmpFileDirectory;
    }

    public void setTmpFileDirectory(String tmpFileDirectory) {
        this.tmpFileDirectory = tmpFileDirectory;
    }

    public void setCoupletGeneratePythonFilePath(String coupletGeneratePythonFilePath) {
        this.coupletGeneratePythonFilePath = coupletGeneratePythonFilePath;
    }

    public static ServerConfig getOrParse(String[] args) throws Exception {
        if (instance == null) {
            synchronized (ServerConfig.class) {
                if (instance == null) {
                    instance = parse(args);
                }
            }
        }

        return instance;
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
        serverConfig.setPythonPath(properties.getProperty("python.path", "python"));
        serverConfig.setCoupletGeneratePythonFilePath(properties.getProperty("couplet.python.file", "paddlepaddle/test.py"));
        serverConfig.setModelPath(properties.getProperty("model.path", "paddlepaddle/model/pass_00040.tar.gz"));
        serverConfig.setTmpFileDirectory(properties.getProperty("tmp.file.dir", "/tmp"));
        serverConfig.setVocabsPath(properties.getProperty("vocabs.path", "paddlepaddle/data/vocabs.txt"));

        return serverConfig;
    }

}
