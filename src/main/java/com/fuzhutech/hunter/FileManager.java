package com.fuzhutech.hunter;

import java.io.File;
import java.util.Map;

public class FileManager extends com.baidu.ueditor.hunter.FileManager {

    private String rootPath = null;

    public FileManager(Map<String, Object> conf) {
        super(conf);

        this.rootPath = (String) conf.get("rootPath");
    }

    private String getPath(File file) {
        String path = file.getAbsolutePath();
        path = path.replace('/', '\\');

        return path.replace(this.rootPath.replace('/', '\\'), "/");
    }
}
