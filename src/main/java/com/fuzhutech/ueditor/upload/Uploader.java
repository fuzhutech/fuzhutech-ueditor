package com.fuzhutech.ueditor.upload;

import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.Base64Uploader;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Uploader extends com.baidu.ueditor.upload.Uploader{

    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        super(request, conf);
    }

    public final State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        State state = null;

        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName),
                    this.conf);
        } else {
            state = BinaryUploader.save(this.request, this.conf);
        }

        return state;
    }
}
