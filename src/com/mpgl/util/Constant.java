package com.mpgl.util;

import java.text.SimpleDateFormat;

/**
 * 常量类
 * 
 * @author user
 * 
 */
public interface Constant {

	/**
	 * 项目根路径
	 */
	String PROJECT_ROOT = System.getProperty("user.dir");

	/**
	 * 模版文件夹路径
	 */
	String TEMPLATE_PATH = "template/";

	/**
	 * 默认日期格式
	 */
	SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * DataGrid数据集KEY
	 */
	String ROW_KEY = "rows";

	/**
	 * DataGrid数据集总条数
	 */
	String ROW_TOTAL = "total";

	/**
	 * SESSION中用户信息存放KEY
	 */
	String USER = "USER_INFO";

	/**
	 * SESSION登录错误信息存放Key
	 */
	String LOGIN = "LOGIN_ERROR";

	/**
	 * 状态码-错误
	 */
	String CODE_EOF = "400";

	/**
	 * 状态码-成功
	 */
	String CODE_WIN = "500";

	/**
	 * 无响应
	 */
	String CODE_NAN = "600";

	/**
	 * 导入数据 列名数组
	 * 
	 * @author user
	 * 
	 */
	public interface ImportColumn {
		/**
		 * 门牌数据导入列名数组
		 */
		String[] HOUSE_COLUMNS = { "city", "road_name", "road_direction",
				"road_level", "road_start_end", "house_num_position",
				"house_num_point", "property_name", "property_phone",
				"now_user_name", "now_user_phone", "now_house_num",
				"old_house_num", "nature", "mark", "description" };
	}

	/**
	 * 导出数据 列名数组
	 * 
	 * @author user
	 * 
	 */
	public interface ExportColumn {
		/**
		 * 门牌数据导出列名数组
		 */
		String[] HOUSE_COLUMNS = { "所属市县区及乡、镇、街道、村、居委会名称", "道路名称", "道路走向",
				"道路级别", "道路起止点", "门牌方位", "门牌坐标", "产权主姓名", "产权主联系电话", "现用户姓名",
				"现用户联系电话", "现门牌号码", "原门牌号码", "用户建筑物名称及性质", "相邻地名标志物名称", "备注" };

		int[] HOUSE_WIDTHS = { 500, 200, 200, 200, 200, 200, 200, 200, 200,
				200, 200, 200, 200, 200, 200, 200 };

		/**
		 * 门牌数据导出列名Key数组
		 */
		String[] HOUSE_KEYS = { "city", "road_name", "road_direction",
				"road_level", "road_start_end", "house_num_position",
				"house_num_point", "property_name", "property_phone",
				"now_user_name", "now_user_phone", "now_house_num",
				"old_house_num", "nature", "mark", "description" };
	}

}