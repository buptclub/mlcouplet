package com.baidubupt.coupletserver;

import com.baidubupt.coupletserver.server.ServerConfig;
import com.baidubupt.coupletserver.server.TomcatServer;

public class CoupletServer {

    public static void main(String...args) throws Exception {
        ServerConfig serverConfig = ServerConfig.getOrParse(args);
        new TomcatServer(serverConfig).start();
    }

}
