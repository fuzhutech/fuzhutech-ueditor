package com.fuzhutech.ueditor.hunter;

import com.fuzhutech.ueditor.ActionEnter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

public class FileManager extends com.baidu.ueditor.hunter.FileManager {

    private String rootPath = null;

    public FileManager(Map<String, Object> conf) {
        super(conf);

        this.rootPath = (String) conf.get("rootPath");

    }

    @Override
    protected String getPath(File file) {
        String path = file.getAbsolutePath();
        path = path.replace('/', '\\');

        return path.replace(this.rootPath.replace('/', '\\'), "/");
    }
}
