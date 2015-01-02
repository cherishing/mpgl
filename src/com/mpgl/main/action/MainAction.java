package com.mpgl.main.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.mpgl.base.BaseAction;
import com.mpgl.main.service.MainService;
import com.mpgl.pdf.PDFUtil;
import com.mpgl.poi.ExcelUtil;
import com.mpgl.util.ConfigProperty;
import com.mpgl.util.Constant;
import com.mpgl.vo.HouseVo;
import com.mpgl.vo.MenuVo;
import com.mpgl.vo.RoleVo;
import com.mpgl.vo.UserVo;

public class MainAction extends BaseAction {

	/**
	 * 日志
	 */
	private static Logger log = Logger.getLogger(MainAction.class);

	/**
	 * Service
	 */
	private MainService mainService;

	/* 用户相关属性 */

	/**
	 * UserVo
	 */
	private UserVo user;

	/**
	 * 门牌Vo
	 */
	private HouseVo house;

	/**
	 * 单个用户数据
	 */
	private Map<String, Object> userMap;

	/**
	 * DataGrid用户数据
	 */
	private Map<String, Object> userGrid;

	/**
	 * 单个门牌数据
	 */
	private Map<String, Object> houseMap;

	/**
	 * DataGrid门牌数据
	 */
	private Map<String, Object> houseNumberGrid;

	/**
	 * 字典表数据
	 */
	private List<Map<String, Object>> dictionaryList;

	/**
	 * Combobox角色列表数据
	 */
	private List<Map<String, Object>> roleCombobox;

	/* 文件上传用到的相关属性 */

	private File excel;

	private String excelFileName;

	private String excelContentType;

	/* 导出文件相关属性 */
	/**
	 * 返回的文件输入流
	 */
	private InputStream ins;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 状态码
	 */
	private String code = Constant.CODE_EOF;

	/**
	 * 跳转到欢迎界面
	 * 
	 * @return
	 */
	public String goWelcome() {
		return SUCCESS;
	}

	/*-----------登录相关--------------*/
	/**
	 * 跳转到登录界面
	 * 
	 * @return
	 */
	public String goLogin() {
		session.remove(Constant.LOGIN);
		return SUCCESS;
	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	public String goLoginOut() {
		session.remove(Constant.LOGIN);
		session.remove(Constant.USER);
		return SUCCESS;
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	public String login() {
		user = mainService.login(form);
		if (user == null) {
			session.put(Constant.LOGIN, "用户名或者密码错误!");
			return INPUT;
		}
		session.put(Constant.USER, user);
		RoleVo role = mainService.getRoleByUser(user);
		List<MenuVo> menus = mainService.getMenuByRole(role);
		role.setMenuList(menus);
		user.setRoleVo(role);
		return SUCCESS;
	}

	/*-----------系统管理相关--------------*/
	/**
	 * 跳转用户管理界面
	 * 
	 * @return
	 */
	public String goUserManager() {
		return SUCCESS;
	}

	/**
	 * 新增用户数据
	 * 
	 * @return
	 */
	public String doAddUser() {
		if (mainService.checkUserName(user)) {
			user.setCreate_time(Constant.DEFAULT_DATE_FORMAT.format(new Date()));
			mainService.addUser(user);
			code = Constant.CODE_WIN;
		} else {
			code = "用户帐号已存在";
		}
		return SUCCESS;
	}

	/**
	 * 更新用户数据
	 * 
	 * @return
	 */
	public String doUpdateUser() {
		user.setUpdate_time(Constant.DEFAULT_DATE_FORMAT.format(new Date()));
		mainService.updateUser(user);
		code = Constant.CODE_WIN;
		return SUCCESS;
	}

	/**
	 * 删除用户数据
	 * 
	 * @return
	 */
	public String doDeleteUser() {
		mainService.deleteUser(user);
		code = Constant.CODE_WIN;
		return SUCCESS;
	}

	/**
	 * 查询单个用户信息[JSON]
	 * 
	 * @return
	 */
	public String queryUserByJson() {
		userMap = mainService.queryUser(this.form);
		return SUCCESS;
	}

	/**
	 * 查询用户列表[JSON]
	 * 
	 * @return
	 */
	public String queryUserDataGridByJson() {
		userGrid = mainService.queryUserDataGrid(this.getForm());
		return SUCCESS;
	}

	/**
	 * 查询角色列表[JSON]
	 * 
	 * @return
	 */
	public String queryRoleListByJson() {
		roleCombobox = mainService.queryRoleList();
		return SUCCESS;
	}

	/**
	 * 查询字典表数据[JSON]
	 * 
	 * @return
	 */
	public String queryDictionaryByJson() {
		dictionaryList = mainService.queryDictionaryByKey(form);
		return SUCCESS;
	}

	/*-----------门牌号管理相关--------------*/
	/**
	 * 跳转到门牌号管理界面
	 * 
	 * @return
	 */
	public String goHouseNumberManager() {
		return SUCCESS;
	}

	/**
	 * 新增门牌数据
	 * 
	 * @return
	 */
	public String doAddHouseNumber() {
		mainService.addHouseNumber(house);
		code = Constant.CODE_WIN;
		return SUCCESS;
	}

	/**
	 * 修改门牌数据
	 * 
	 * @return
	 */
	public String doUpdateHouseNumber() {
		mainService.updateHouseNumber(house);
		code = Constant.CODE_WIN;
		return SUCCESS;
	}

	/**
	 * 删除门牌数据
	 * 
	 * @return
	 */
	public String doDeleteHouseNumber() {
		mainService.deleteHouseNumber(house);
		code = Constant.CODE_WIN;
		return SUCCESS;
	}

	/**
	 * 查询门牌数据[JSON]
	 * 
	 * @return
	 */
	public String queryHouseNumberListByJson() {
		this.houseNumberGrid = mainService.queryHouseNumberDataGrid(this
				.getForm());
		return SUCCESS;
	}

	/**
	 * 查询单个门牌号数据[JSON]
	 * 
	 * @return
	 */
	public String queryHouseNumberByJson() {
		houseMap = mainService.queryHouseNumber(this.form);
		return SUCCESS;
	}

	/**
	 * 跳转到门牌号导入界面
	 * 
	 * @return
	 */
	public String goHouseNumberImport() {
		return SUCCESS;
	}

	/**
	 * 导入门牌号数据
	 * 
	 * @return
	 */
	public String doHouseNumberImport() {
		try {
			String[] columns = Constant.ImportColumn.HOUSE_COLUMNS;
			ExcelUtil excelUtil = new ExcelUtil(excelFileName, excel);
			List<Map<String, Object>> list = excelUtil.getData(columns, 0);
			mainService.insertHouseNumberByList(list);
			code = Constant.CODE_WIN;
		} catch (Exception e) {
			log.error("导入门牌数据错误:", e);
			code = Constant.CODE_EOF;
		}
		return SUCCESS;
	}

	/**
	 * 导出
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String doHouseNumberExport() {
		try {
			List<Map<String, Object>> list = mainService
					.queryHouseNumberList(this.form);
			fileName = ConfigProperty.newInstance().getProvalue(
					"export.filename1");
			ExcelUtil excelUtil = new ExcelUtil();
			ins = excelUtil.exportStream(Constant.ExportColumn.HOUSE_COLUMNS,
					Constant.ExportColumn.HOUSE_KEYS, list);
			code = Constant.CODE_WIN;
		} catch (Exception e) {
			log.error("导出门牌数据错误:", e);
			code = Constant.CODE_EOF;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 跳转打印页面 inline
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String goPrint() {
		try {
			List<Map<String, Object>> list = mainService
					.queryHouseNumberList(this.form);
			fileName = ConfigProperty.newInstance().getProvalue(
					"print.filename1");
			PDFUtil pdfUtil = new PDFUtil();
			ins = pdfUtil.exportStream(Constant.ExportColumn.HOUSE_COLUMNS,
					Constant.ExportColumn.HOUSE_KEYS,
					Constant.ExportColumn.HOUSE_WIDTHS, list);
			code = Constant.CODE_WIN;
		} catch (Exception e) {
			log.error("打印数据错误:", e);
			code = Constant.CODE_EOF;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询用户密码
	 * 
	 * @return
	 */
	public String doQueryPwd() {
		user = (UserVo) session.get(Constant.USER);
		return SUCCESS;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String doUpdatePassword() {
		try {
			UserVo user = (UserVo) session.get(Constant.USER);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", user.getId());
			map.put("password", this.getForm().getPassword());
			mainService.updateUserPassword(map);
			user.setPassword(this.getForm().getPassword());
			code = Constant.CODE_WIN;
		} catch (Exception e) {
			log.error("修改密码错误:", e);
			code = Constant.CODE_EOF;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public MainService getMainService() {
		return mainService;
	}

	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

	public File getExcel() {
		return excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getExcelContentType() {
		return excelContentType;
	}

	public void setExcelContentType(String excelContentType) {
		this.excelContentType = excelContentType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getUserGrid() {
		return userGrid;
	}

	public void setUserGrid(Map<String, Object> userGrid) {
		this.userGrid = userGrid;
	}

	public List<Map<String, Object>> getRoleCombobox() {
		return roleCombobox;
	}

	public void setRoleCombobox(List<Map<String, Object>> roleCombobox) {
		this.roleCombobox = roleCombobox;
	}

	public Map<String, Object> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, Object> userMap) {
		this.userMap = userMap;
	}

	public List<Map<String, Object>> getDictionaryList() {
		return dictionaryList;
	}

	public void setDictionaryList(List<Map<String, Object>> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}

	public Map<String, Object> getHouseNumberGrid() {
		return houseNumberGrid;
	}

	public void setHouseNumberGrid(Map<String, Object> houseNumberGrid) {
		this.houseNumberGrid = houseNumberGrid;
	}

	public HouseVo getHouse() {
		return house;
	}

	public void setHouse(HouseVo house) {
		this.house = house;
	}

	public Map<String, Object> getHouseMap() {
		return houseMap;
	}

	public void setHouseMap(Map<String, Object> houseMap) {
		this.houseMap = houseMap;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		MainAction.log = log;
	}

	public InputStream getIns() {
		return ins;
	}

	public void setIns(InputStream ins) {
		this.ins = ins;
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
