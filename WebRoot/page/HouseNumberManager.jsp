<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="CommonPage.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title><s:text name="COMMON_TITLE"></s:text>-门牌号管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <table id="dg" class="easyui-datagrid"
            data-options="pagination:true,fit:true,rownumbers:true,border:false,singleSelect:true,striped:true,
            url:'${pageContext.request.contextPath}/main/queryHouseNumberListByJson.do',method:'post',toolbar:'#dg-toolbar'">
        <thead>
            <tr>
                <th data-options="field:'now_house_num',width:100,align:'center'">现门牌号码</th>
                <th data-options="field:'old_house_num',width:100,align:'center'">原门牌号码</th>
                <th data-options="field:'now_user_name',width:100,align:'center'">现用户姓名</th>
                <th data-options="field:'now_user_phone',width:120,align:'center'">现用户联系电话</th>
                <th data-options="field:'property_name',width:100,align:'center'">产权主姓名</th>
                <th data-options="field:'property_phone',width:120,align:'center'">产权主联系电话</th>
                <th data-options="field:'house_num_position',width:80,align:'center'">门牌方位</th>
                <th data-options="field:'house_num_point',width:80,align:'center'">门牌坐标</th>
                <th data-options="field:'road_name',width:80,align:'center'">道路名称</th>
                <th data-options="field:'road_direction',width:80,align:'center'">道路走向</th>
                <th data-options="field:'road_level',width:60,align:'center'">道路级别</th>
                <th data-options="field:'road_start_end',width:80,align:'center'">道路起止点</th>
                <th data-options="field:'nature',width:100,align:'center'">用户建筑物名称及性质</th>
                <th data-options="field:'mark',width:100,align:'center'">相邻地名标志物名称</th>
                <th data-options="field:'city',width:300,align:'center'">所属市县区及乡、镇、街道、村、居委会名称</th>
                <th data-options="field:'description',width:100,align:'center'">备注</th>
            </tr>
        </thead>
    </table>
    <div id="dg-toolbar" style="padding:5px;height:auto">
    	<div>
	    	<s:if test="#session.USER_INFO.role_id == 1">
            <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="operation('add','${pageContext.request.contextPath}/main/doAddHouseNumber.do')"><s:text name="BUTTON.ADD"></s:text></a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="operation('edit','${pageContext.request.contextPath}/main/doUpdateHouseNumber.do')"><s:text name="BUTTON.EDIT"></s:text></a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" onclick="operation('del','${pageContext.request.contextPath}/main/doDeleteHouseNumber.do')"><s:text name="BUTTON.DEL"></s:text></a>
	    	</s:if>
            <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'" onclick="exportData()"><s:text name="BUTTON.EXPORT"></s:text></a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'" onclick="print()"><s:text name="BUTTON.PRINT"></s:text></a>
        </div>
        <div>
                                道路名称:
            	<input type="text" id="query_roadname" name="form.road_name" style="width:100px">
        	产权主姓名: 
        		<input type="text" id="query_propertyname" name="form.property_name" style="width:100px">
                                现用户姓名:
            	<input type="text" id="query_nowusername" name="form.now_user_name" style="width:100px">
                                现门牌号: 
            	<input type="text" id="query_nowhousenum" name="form.now_house_num" style="width:100px">
                                门牌方位: 
	            <input type="text" id="query_houseposition" name="form.house_num_position" style="width:100px">
        </div>
        <div>
        	所属市县区及乡、镇、街道、村、居委会名称：
        		<input type="text" id="query_city" name="form.city" style="width:200px">
        	用户建筑物名称及性质: 
        		<input type="text" id="query_nature" name="form.nature" style="width:100px">
           	<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" onclick="query()"><s:text name="BUTTON.SEARCH"></s:text></a>
           	<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" onclick="resetQuery()"><s:text name="BUTTON.RESET"></s:text></a>
        </div>
    </div>
    
    
    <div id="dlg" class="easyui-dialog" style="padding:10px" 
    	data-options="width:650,height:500, iconCls:'icon-base', buttons: '#dlg-buttons', modal:true, closed:true, closable:true">  
	    <form id="dlgFm" action="" method="post">
	    	<input id="dlg_id" type="hidden" style="width:150px;" name="house.id" value=""></input>
	    	<fieldset>
	    		<legend>道路信息</legend>
		    	<table style="border: 1">
	          	 	<tr>  
		                <td align="right" colspan="2">所属市县区及乡、镇、街道、村、居委会名称:</td>  
		                <td align="left"colspan="2">
		                	<input id="dlg_city" type="text" maxlength="256" style="width:250px;" name="house.city" ></input>
		                </td>  
		            </tr>  
		            <tr>  
		                <td align="right">道路名称:</td>  
		                <td align="left">
		                	<input id="dlg_road_name" type="text" maxlength="256" style="width:150px;" name="house.road_name" ></input>
		                </td>  
		                <td align="right">道路走向:</td>  
		                <td align="left">
		                	<input id="dlg_road_direction" type="text" maxlength="256" style="width:150px;" name="house.road_direction" ></input>
		                </td>  
		            </tr>  
		            <tr>  
		                <td align="right">道路级别:</td>  
		                <td align="left">
		                	<input id="dlg_road_level" type="text" maxlength="256" style="width:150px;" name="house.road_level" ></input>
		                </td>  
		                <td align="right">道路起止点:</td>  
		                <td align="left">
		                	<input id="dlg_road_start_end" type="text" maxlength="256" style="width:150px;" name="house.road_start_end" ></input>
		                </td>  
		            </tr>
		            
	            </table>
            </fieldset>
            <br>
            <fieldset>
	    		<legend>门牌信息</legend>
	            <table style="border: 1">
	           	  	<tr>  
		                <td align="right">现门牌号码:</td>  
		                <td align="left">
		                	<input id="dlg_now_house_num" type="text" maxlength="256" style="width:150px;" name="house.now_house_num" ></input>
		                </td>  
		                <td align="right">原门牌号码:</td>  
		                <td align="left">
		                	<input id="dlg_old_house_num" type="text" maxlength="256" style="width:150px;" name="house.old_house_num" ></input>
		                </td>  
		            </tr>  
		            <tr>  
		                <td align="right">门牌方位:</td>  
		                <td align="left">
		                	<input id="dlg_house_num_position" type="text" maxlength="256" style="width:150px;" name="house.house_num_position" ></input>
		                </td>  
		                <td align="right">门牌坐标(经纬度):</td>  
		                <td align="left">
		                	<input id="dlg_house_num_point" type="text" maxlength="256" style="width:150px;" name="house.house_num_point" ></input>
		                </td>  
		            </tr> 
	          	</table>
	       </fieldset>
	        <br>
	       <fieldset>
    	  	   <legend>户主信息</legend>
	           <table style="border: 1">
		            <tr>  
		                <td align="right">产权主姓名:</td>  
		                <td align="left">
		                	<input id="dlg_property_name" type="text" maxlength="256" style="width:150px;" name="house.property_name" ></input>
		                </td>  
		                <td align="right">产权主联系电话:</td>  
		                <td align="left">
		                	<input id="dlg_property_phone" type="text" maxlength="256" style="width:150px;" name="house.property_phone" ></input>
		                </td>  
		            </tr>  
		            <tr>  
		                <td align="right">现用户姓名:</td>  
		                <td align="left">
		                	<input id="dlg_now_user_name" type="text" maxlength="256" style="width:150px;" name="house.now_user_name" ></input>
		                </td>  
		                <td align="right">现用户联系电话:</td>  
		                <td align="left">
		                	<input id="dlg_now_user_phone" type="text" maxlength="256" style="width:150px;" name="house.now_user_phone" ></input>
		                </td>  
		            </tr>  
       			</table>
          </fieldset>
           <br>
          <fieldset>
    	  	   <legend>其他信息</legend>
	           <table style="border: 1">
	           		<tr>  
		                <td align="right">用户建筑物名称及性质:</td>  
		                <td align="left">
		                	<input id="dlg_nature" type="text" maxlength="256" style="width:150px;" name="house.nature" ></input>
		                </td>  
		                <td align="right">相邻地名标志物名称:</td>  
		                <td align="left">
		                	<input id="dlg_mark" type="text" maxlength="256" style="width:150px;" name="house.mark" ></input>
		                </td>  
		            </tr>
		            <tr>  
		                <td align="right">备注:</td>  
		                <td align="left">
		                	<input id="dlg_description" type="text" maxlength="256" style="width:150px;" name="house.description" ></input>
		                </td>  
		                <td align="right"></td>  
		                <td align="left">
		                </td>   
		            </tr>    
	           </table>
           </fieldset>
	    </form>
	</div>  
	<div id="dlg-buttons">  
   		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  onclick="save()"><s:text name="BUTTON.SAVE"></s:text></a>  
   		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'"  onclick="javascript:$('#dlg').dialog('close')"><s:text name="BUTTON.CANCEL"></s:text></a>  
 	</div>  
 	
 	<div class="easyui-dialog"
    	data-options="width:0,height:0, closed:true, closable:true">  
 		<form id="exportFm" action="${pageContext.request.contextPath}/main/doHouseNumberExport.do" method="post">
            	<input type="hidden" id="query_roadname2" name="form.road_name">
        		<input type="hidden" id="query_propertyname2" name="form.property_name">
            	<input type="hidden" id="query_nowusername2" name="form.now_user_name">
            	<input type="hidden" id="query_nowhousenum2" name="form.now_house_num">
	            <input type="hidden" id="query_houseposition2" name="form.house_num_position">
        		<input type="hidden" id="query_city2" name="form.city">
        		<input type="hidden" id="query_nature2" name="form.nature">
 		</form>
 	</div>
  </body>
  <script type="text/javascript">
  
  	/**
  	*导出
  	*/
  	function exportData() {
  		$("#query_roadname2").val($("#query_roadname").val());
  		$("#query_propertyname2").val($("#query_propertyname").val());
  		$("#query_nowusername2").val($("#query_nowusername").val());
  		$("#query_nowhousenum2").val($("#query_nowhousenum").val());
  		$("#query_city2").val($("#query_city").val());
  		$("#query_nature2").val($("#query_nature").val());
  		$("#query_houseposition2").val($("#query_houseposition").val());
  		
  		$("#exportFm").form("submit",{
  				url : "${pageContext.request.contextPath}/main/doHouseNumberExport.do",
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(code){
					alertMessageByCode($.parseJSON(code));
				}
		});
  	}
  	/**
  	*打印
  	*/
  	function print() {
  		$("#query_roadname2").val($("#query_roadname").val());
  		$("#query_propertyname2").val($("#query_propertyname").val());
  		$("#query_nowusername2").val($("#query_nowusername").val());
  		$("#query_nowhousenum2").val($("#query_nowhousenum").val());
  		$("#query_city2").val($("#query_city").val());
  		$("#query_nature2").val($("#query_nature").val());
  		$("#query_houseposition2").val($("#query_houseposition").val());
  		
  		$("#exportFm").form("submit",{
  				url : "${pageContext.request.contextPath}/main/goPrint.do",
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(code){
					alertMessageByCode($.parseJSON(code));
				}
		});
  	}
  	
  	/**
	 * 查询
	 */
	function query() {
		$("#dg").datagrid({
			pageNumber : 1,
			queryParams : {
				"form.road_name" : $("#query_roadname").val(),
				"form.property_name" : $("#query_propertyname").val(),
				"form.now_user_name" : $("#query_nowusername").val(),
				"form.now_house_num" : $("#query_nowhousenum").val(),
				"form.city" : $("#query_city").val(),
				"form.nature" : $("#query_nature").val(),
				"form.house_num_position" : $("#query_houseposition").val()
			}
		});
	}
	
	var url;
	function operation(type,url) {
		this.url = url;
		clearForm("dlgFm");
		switch(type) {
		case "add" :
			openDialog("dlg","新增");
			break;
		case "edit" :
			var row = $("#dg").datagrid("getSelected");
			if(row) {
				loadForm(row);
				openDialog("dlg","修改");
			} else {
				alertMessageByCode("unSelect");
			}
			break;
		case "del" :
			var row = $("#dg").datagrid("getSelected");
			if(row) {
				$.messager.confirm("确认框","你确定要删除该行数据吗?", function(r) {
					if(r) {
						$.post(
							url,
							{"house.id":row.id},
							function(code) {
								alertMessageByCode(code);
								reloadDataGrid("dg");
							},"json"
						); 
					}
				});
			} else {
				alertMessageByCode("unSelect");
			}
			break;
		}
	}
	
	/**
	 * 保存表单
	 */
	function save() {
		$("#dlgFm").form("submit", {
			url : url,
			onSubmit : function() {
				return $(this).form("validate");
			},
			success : function(code) {
				alertMessageByCode($.parseJSON(code));
				reloadDataGrid("dg");
				$("#dlg").dialog("close");
			}
		});
	}
	
	/**
	 * 为表单加载数据
	 * @param row
	 */
	function loadForm(row) {
		$.post(
			"${pageContext.request.contextPath}/main/queryHouseNumberByJson.do",
			{"form.house_id":row.id},
			function(data){
				if(data != null) {
					$("#dlg_id").val(data.id);
					$("#dlg_city").val(data.city);
					$("#dlg_road_name").val(data.road_name);
					$("#dlg_road_direction").val(data.road_direction);
					$("#dlg_road_level").val(data.road_level);
					$("#dlg_road_start_end").val(data.road_start_end);
					$("#dlg_now_house_num").val(data.now_house_num);
					$("#dlg_old_house_num").val(data.old_house_num);
					$("#dlg_house_num_point").val(data.house_num_point);
					$("#dlg_property_name").val(data.property_name);
					$("#dlg_property_phone").val(data.property_phone);
					$("#dlg_now_user_name").val(data.now_user_name);
					$("#dlg_now_user_phone").val(data.now_user_phone);
					$("#dlg_nature").val(data.nature);
					$("#dlg_mark").val(data.mark);
					$("#dlg_description").val(data.description);
					$("#dlg_house_num_position").val(data.house_num_position);
				}
			},"json"
		);
	}
  </script>
</html>
