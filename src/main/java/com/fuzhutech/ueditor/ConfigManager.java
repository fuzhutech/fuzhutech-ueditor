package com.fuzhutech.ueditor;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigManager extends com.baidu.ueditor.ConfigManager {

    private String configPath;

    protected ConfigManager(String rootPath, String contextPath, String uri, String configPath) throws FileNotFoundException, IOException {
        this.rootPath = rootPath.replace("\\", "/");
        this.configPath = configPath;

        this.initEnv();
    }

    public static ConfigManager getInstance(String rootPath, String contextPath, String uri, String configPath) {

        try {
            return new ConfigManager(rootPath, contextPath, uri, configPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    //@Override 重写原有的initEnv和getConfigPath方法
    protected void initEnv() throws FileNotFoundException, IOException {

        String configContent = this.readFile(this.getConfigPath());

        try {
            JSONObject jsonConfig = new JSONObject(configContent);
            this.jsonConfig = jsonConfig;
        } catch (Exception e) {
            this.jsonConfig = null;
        }
    }

    //@Override 重写原有的initEnv和getConfigPath方法
    protected String getConfigPath() {
        return this.configPath;
    }
}
