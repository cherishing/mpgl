package com.mpgl.base;

import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.mpgl.vo.Form;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 基本Action
 * 
 * @author user
 * 
 */
public abstract class BaseAction extends ActionSupport implements SessionAware,
		RequestAware, ApplicationAware {

	/**
	 * 服务器 全局变量
	 */
	public Map<String, Object> application;

	/**
	 * result常量， 表示返回上一页
	 */
	public static String LAST = "last";

	/**
	 * DataGrid默认分页参数-页码
	 */
	public String page;

	/**
	 * DataGrid默认分页参数-页尺寸
	 */
	private String rows;

	/**
	 * 表单参数对象
	 */
	public Form form;

	/**
	 * Session
	 */
	public Map<String, Object> session;

	/**
	 * Request
	 */
	public Map<String, Object> request;

	/**
	 * 上一个页面
	 */
	public String lastPage;

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return this.session;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getRequest() {
		return this.request;
	}

	public static String getLAST() {
		return LAST;
	}

	public static void setLAST(String lAST) {
		LAST = lAST;
	}

	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	public Map<String, Object> getApplication() {
		return this.application;
	}

	public Form getForm() {
		if (form == null) {
			form = new Form();
		}
		if (page != null && rows != null) {
			int page_ = Integer.parseInt(page);
			int rows_ = Integer.parseInt(rows);
			if (page_ <= 0) {
				page_ = 1;
			}
			form.setPageIndex((page_ - 1) * rows_);
			form.setPageSize(rows_);
		}
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
}