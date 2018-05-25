package com.baidubupt.coupletserver.util;

public final class ProcessUtils {

    /**
     * 调用外部程序
     *
     * @param command
     * @return
     * @throws Exception
     */
    public static Process execute(String command) throws Exception {
        Runtime rt = Runtime.getRuntime();
        return rt.exec(command);
    }

}
