<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="CommonPage.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title><s:text name="COMMON_TITLE"></s:text>-门牌号数据导入</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
	<div id="dlg" class="easyui-dialog" title="数据导入"
		data-options="modal:true,resizable:false,closable:false"
		style="width:400px;padding: 10px">
		<form id="upLoadForm" action="<%=root%>/main/doHouseNumberImport.do"
					method="post" enctype="multipart/form-data">
			<fieldset>
				<legend>数据导入</legend>
				<table style="border: 1">
	                <tr>
	                    <td align="right">模板下载:</td>
	                    <td align="left">
	                    	<a target="_blank" href="<%=root%>/main/downloadFile.do?fileKey=template.importfile">数据导入模板.xls</a>
	                    </td>
	                </tr>
	                <tr>
	                    <td align="right">选择文件:</td>
	                    <td align="left">
	                    	<input class="easyui-validatebox" type="file" name="excel" data-options="required:true"></input>
	                    </td>
	                </tr>
	                <tr>
	                    <td colspan="2" align="center">
	                    	<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-upload'" onclick="importFile();">上传</a>
	                    	<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" onclick="clearForm()">清除</a>
	                    </td>
	                </tr>
	            </table>
			</fieldset>
		</form>
	</div>
	
	<script type="text/javascript">
		function importFile() {
			var url = $("#upLoadForm").attr("action");
			showprogressbar();
			$("#upLoadForm").form("submit", {
				url : url,
				onSubmit : function() {
				},
				success : function(code) {
					$.messager.progress("close");
					alertMessageByCode($.parseJSON(code));
				}
			});
		}
		function showprogressbar() {
			$.messager.progress({ 
		        title: "请等待...", 
		        text: "数据导入中......" 
		    });
		}
	</script>
</body>
</html>
