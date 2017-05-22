package com.fuzhutech;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigManager extends com.baidu.ueditor.ConfigManager{
    private String configPath;

    private ConfigManager(String rootPath, String contextPath, String uri, String configPath) throws FileNotFoundException, IOException {
        super(rootPath,configPath,uri);
        this.configPath = configPath;
    }

    public static ConfigManager getInstance(String rootPath, String contextPath, String uri, String configPath) {

        try {
            return new ConfigManager(rootPath, contextPath, uri, configPath);
        } catch (Exception e) {
            return null;
        }

    }

    protected String getConfigPath() {
        if (null == this.configPath)
            this.configPath = super.getConfigPath();
        return this.configPath;
    }
}
