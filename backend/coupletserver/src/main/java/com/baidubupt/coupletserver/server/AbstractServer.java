package com.baidubupt.coupletserver.server;

import java.io.File;

public abstract class AbstractServer implements Server {

    public File getTempDirectory() {
        File tmpFolder = new File(TEMP_DIR);
        tmpFolder.deleteOnExit();
        tmpFolder.mkdirs();

        return tmpFolder;
    }

    public File getWebAppDirectory() {
        return new File(WEBAPP_DIR);
    }

}
