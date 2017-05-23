package com.fuzhutech.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.StorageManager;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class BinaryUploader extends com.baidu.ueditor.upload.BinaryUploader {

    public static State save(HttpServletRequest request,
                             Map<String, Object> conf) {
        //FileItemStream fileStream = null;
        MultipartFile multipartFile = null;

        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        // 判断 request 是否有文件上传,即多部分请求
        if (!multipartResolver.isMultipart(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }


        try {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Iterator<String> names = multiRequest.getFileNames();

            while (names.hasNext()) {
                multipartFile = multiRequest.getFile(names.next());

                if (multipartFile != null)
                    break;
            }

            if (multipartFile == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }

            String savePath = (String) conf.get("savePath");
            //String originFileName = fileStream.getName();
            String originFileName = multipartFile.getOriginalFilename();
            String suffix = FileType.getSuffixByFilename(originFileName);

            originFileName = originFileName.substring(0,
                    originFileName.length() - suffix.length());
            savePath = savePath + suffix;

            long maxSize = (Long) conf.get("maxSize");

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }

            savePath = PathFormat.parse(savePath, originFileName);

            String physicalPath = conf.get("rootPath") + savePath;

            //InputStream is = fileStream.openStream();
            InputStream is = multipartFile.getInputStream();
            State storageState = StorageManager.saveFileByInputStream(is,
                    physicalPath, maxSize);
            is.close();

            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }

            return storageState;
        } /*catch (FileUploadException e) {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        } */ catch (IOException ignored) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

}
