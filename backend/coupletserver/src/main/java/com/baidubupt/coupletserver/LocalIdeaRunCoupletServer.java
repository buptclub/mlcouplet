package com.baidubupt.coupletserver;

import com.baidubupt.coupletserver.server.ServerConfig;
import com.baidubupt.coupletserver.server.TomcatServer;

public class LocalIdeaRunCoupletServer {

    public static void main(String...args) throws Exception {
        ServerConfig serverConfig = ServerConfig.getOrParse(args);

        serverConfig.setVocabsPath("../../core/data/vocabs.txt");
        serverConfig.setTmpFileDirectory("/tmp");
        serverConfig.setModelPath("../../core/model/pass_00040.tar.gz");
        serverConfig.setCoupletGeneratePythonFilePath("../../core/test.py");
        serverConfig.setPythonPath("python");

        new TomcatServer(serverConfig).start();
    }

}
