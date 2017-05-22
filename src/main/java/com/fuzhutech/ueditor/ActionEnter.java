package com.fuzhutech.ueditor;

import javax.servlet.http.HttpServletRequest;

public class ActionEnter extends com.baidu.ueditor.ActionEnter{

    public ActionEnter (HttpServletRequest request, String rootPath, String configFilePath ) {
        super(request,rootPath);

        String contextPath = request.getContextPath();
        this.configManager = ConfigManager.getInstance( rootPath, contextPath, request.getRequestURI(),configFilePath);
    }
}
