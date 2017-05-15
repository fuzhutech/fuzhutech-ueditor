package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Uploader {

	private static Logger logger = LoggerFactory.getLogger(Uploader.class);
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
	    //todo:
		logger.info("Uploader doExec");
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
