

function goToAction(label_value) {
	$("div[label="+label_value+"]").focus(function() {
	});
}

/**
 * 跳转ACTION
 * @param url
 */
function goAction(url) {
	window.location.href = url;
}

/**
 * 清理表单数据
 * @param formId
 */
function clearForm(formId){
	$("#" + formId).form("clear");
}

/**
 * 加载下拉框数据
 * @param id
 * @param url
 */
function loadSelect(id,url) {
	$("#" + id).combobox("reload",url);
}
/**
 * 加载多个下拉框的数据
 * @param ids
 * @param urls
 */
function loadSelects(ids,urls) {
	for(var i=0; i<ids.length; i++) {
		$("#" + ids[i]).combobox("reload",urls[i]);
	}
}

/**
 * 打开模态窗口并设置标题
 * @param id
 * @param title
 */
function openDialog(id,title) {
	$("#" + id).dialog("setTitle",title).dialog("open");
}

/**
 * 打开操作提示框
 * @param code
 */
function alertMessageByCode(code) {
	if(code == "400") {
		$.messager.alert("提示", "操作错误");
	} else if(code == "500") {
		$.messager.alert("提示", "操作成功");
	} else if(code == "600") {
		$.messager.alert("提示", "服务器无响应");
	} else if(code == "701") {
		$.messager.alert("提示", "数据有重复");
	} else if(code == "unSelect") {
		$.messager.alert("提示", "请选择一行数据");
	} 
	else {
		$.messager.alert("提示", code);
	}
}

/**
 * 重载DataGrid的数据
 * @param id
 */
function reloadDataGrid(id) {
	$("#" + id).datagrid("reload");
}

/**
 * 清除搜索表单数据
 */
function resetQuery() {
	$("input[id^='query_']").val("");
	$("select[id^='query_']").combobox("setValue","");
}
