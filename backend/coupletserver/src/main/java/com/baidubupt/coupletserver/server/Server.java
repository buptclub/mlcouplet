package com.baidubupt.coupletserver.server;

/**
 * WEB 服务器
 */
public interface Server extends LifeCycle {

    /**
     * Base Folder
     */
    String BASE_DIR = "src/main";

    /**
     * WebApp Folder
     */
    String WEBAPP_DIR = BASE_DIR + "/webapp";

    /**
     * Temp Folder
     */
    String TEMP_DIR = BASE_DIR + "/temp";

}