package com.mpgl.main.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.mpgl.base.BaseAction;
import com.mpgl.util.ConfigProperty;
import com.mpgl.util.Constant;

/**
 * 文件下载
 * 
 * @author user
 * 
 */
public class FileDownloadAction extends BaseAction {

	/**
	 * 日志
	 */
	private static Logger log = Logger.getLogger(FileDownloadAction.class);

	private InputStream ins;

	private String fileKey;

	private String fileName;

	@Override
	public String execute() {
		fileName = ConfigProperty.newInstance().getProvalue(this.fileKey);
		try {
			String path = ServletActionContext.getServletContext().getRealPath(
					"/")
					+ Constant.TEMPLATE_PATH + fileName;
			ins = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			log.error("文件下载错误:", e);
		}
		return SUCCESS;
	}

	public InputStream getIns() {
		return ins;
	}

	public void setIns(InputStream ins) {
		this.ins = ins;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getFileName() {
		try {
			fileName = new String(fileName.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			log.error("导出文件名转码错误", e);
		}
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
