package com.baidubupt.coupletserver.server;

public interface LifeCycle {

    /**
     * 启动
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 关闭
     * @throws Exception
     */
    void shutdown() throws Exception;

}
