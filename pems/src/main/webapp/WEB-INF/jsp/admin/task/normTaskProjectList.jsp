<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
	#noassociationSelect, #associationSelect{
		width:250px;
	}
	.content{
		padding-top: 10px;
	}
	.d_deleteBtn{
	float:none !important;
	}
</style>

<script src="${ctx }/resources/js/admin/task/normTaskProject.js"></script>
	<%-- <c:if test="${! empty page.dataList }"> --%>
	<form class="form-inline" role="form" id="editWeightForm">
		<!-- <div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="" value="" class="form-control input-sm" id="" placeholder="请输入">
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		</div> -->
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<c:if test="${EMPLOYEE.employeeId==employeeId }">
		<div class="form-group  btn-group-sm z_margin_tb" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="NormTaskProject.chooseProject(${normTaskId},${employeeId});"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增工作内容</button>
		</div>
		<div class="form-group  btn-group-sm" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="NormTaskProject.editWeight();"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>更新权重</button>
		</div>
		</c:if>
		<c:if test="${EMPLOYEE.employeeId!=employeeId }">
		   <c:if test="${!empty page.dataList}">
			<div class="form-group  btn-group-sm z_margin_tb" role="group" aria-label="...">
			  <button type="button" class="btn btn-default" onclick="NormTaskProject.pass(${normTaskTmployeeDetailId},${normTaskId},${employeeId })"><span class="glyphicon glyphicon-pawn" aria-hidden="true"></span>一键审核</button>
			</div>
			</c:if>
		</c:if>
		<button type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<div class="btn-group-sm z_margin_tb" style="border: 2px solid #ccc;background-color: #fbf3e7;padding-left: 10px;color:#777;">
			<h4>操作须知</h4>
			<p>
			<b class="z_common_star">*</b>1.点击新增工作内容，选择您参与的项目，书写工作内容并赋予权重（工作内容权重之和为100%）<br>
			<b class="z_common_star">*</b>2.请在项目权重文本框内输入权重（参与项目权重之和为100%）<br>
			<b class="z_common_star">*</b>3.点击选择互评人员，您可选择互评人员<br>
			<b class="z_common_star">*</b>4.点击删除工作项目，你将连带删除项目下所有工作内容<br>
			<b class="z_common_star">*</b>5.点击编辑工作内容，您可对工作内容进行修改操作<br>
			<b class="z_common_star">*</b>6.当工作内容已经选择互评人时，不允许进行编辑操作<br>
			</p>
		</div>
		<c:if test="${EMPLOYEE.employeeId!=employeeId}">
		<c:if test="${!empty page.dataList}">
		    <div><h4 align="center">被审核人：${page.dataList.get(0).employee.realName }</h4></div>
		</c:if>
		</c:if>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>项目名称</th>
						<th>考评任务</th>
						<!-- <th>被考评人</th> -->
						<!-- <th>状态</th> -->
						<th>项目权重</th>
						<th>工作内容</th>
						<th>工作内容权重</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${page.dataList}" varStatus="status">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<c:if test="${entity.type==1 }">
							<td>${entity.project.projectName}</td>
							</c:if>
							<c:if test="${entity.type==2 }">
							<td></td>
							</c:if>
							<td>${entity.normTask.normTaskName}</td>
							<%-- <td>${entity.employee.realName}</td> --%>
							<c:if test="${entity.type==1}">
								<td>
									<input class="s_weight"  name="weight" size="5px" <c:if test="${entity.weight==null }">style="border:1px solid red;"</c:if> value="${entity.weight}"/>%
									<input type="hidden" name="normTaskProjectId" value="${entity.normTaskProjectId}"/>
								</td>
							</c:if>
							<c:if test="${entity.type==2}">
								<td>
								</td>
							</c:if>
							<td>${entity.workContent}</td>
							<td>
								${entity.weight2}
							</td>
							<%-- <td>${entity.status}</td> --%>
							<td>
							<c:if test="${entity.type==2  && EMPLOYEE.employeeId==employeeId}">
							<button type="button" class="btn btn-info btn-sm" onclick="NormTaskProject.showEachEmployee('${entity.projectId}','${entity.employeeId }','${entity.normTaskId }','${entity.normTaskProjectId}',1);">
							  <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>选择互评人员
							</button>
							</c:if>
							<c:if test="${entity.type==2  && EMPLOYEE.employeeId!=employeeId}">
							<button type="button" class="btn btn-info btn-sm" onclick="NormTaskProject.showEachEmployee('${entity.projectId}','${entity.employeeId }','${entity.normTaskId }','${entity.normTaskProjectId}',0);">
							  <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>查看互评人员
							</button>
							</c:if>
							<c:if test="${entity.type==1 && EMPLOYEE.employeeId==employeeId}">
							<button type="button" class="btn btn-default  btn-sm btn-success" onclick="NormTaskProject.editData('${entity.projectId}','${entity.employeeId }','${entity.normTaskId }');">
							  <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>编辑工作内容
							</button>
							</c:if>
							<c:if test="${entity.type==1 && EMPLOYEE.employeeId==employeeId}">
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="NormTaskProject.deleteData('${entity.normTaskProjectId}','${entity.employeeId }','${entity.normTaskId }','${entity.projectId}','${normTaskTmployeeDetailId}');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除工作项目
							</button>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
 			<!-- 分页区域 -->
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</div>
	</form>
<%-- </c:if> --%>
<%-- <c:if test="${empty page.dataList }">该员工还未添加工作内容</c:if> --%>

	<!-- 互评人员 弹出框 -->
	<div class="modal" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加互评人员</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="editForm">
						<table class="table-edit" width="100%" align="center">
						<tr>
							<td>
								<input type="hidden" name="normTaskProjectId" id="normTaskProjectId" />
								<input type="hidden" name="normTaskId" id="normTaskId" />
								<input type="hidden" name="employeeId" id="employeeId" />
								<input type="hidden" name="projectId" id="projectId" />
								<select id="noassociationSelect"  multiple="multiple" size="15"></select>
							</td>
							<td class="s_btn">
								<input type="button" value="添加>>" id="toRight">
								<br/>
								<br/>
								<input type="button" value="<<移除" id="toLeft">
							</td>
							<td>
								<select id="associationSelect" name="eachEmployeeId" multiple="multiple" size="15"></select>
							</td>
						</tr>
					</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary s_btn" id="save_btn">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 选择考核项目弹出框 -->
	<div class="modal" id="chooseProject" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加项目工作内容</h4>
					
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="addProForm">
					<select id="project" name="projectId" class=" input-sm">
					    <option value="-1">--请选项目--</option>
					</select>
					<table class="table table-striped" id="t_pbox">
					<button type="button" class="btn btn-sm btn-success glyphicon" onclick="addWorkContent(this);"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加工作内容(ctrl＋↓)</button>
					<div id="c_projectId"></div>
					<div class="workContent"></div>
			         	</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="editSave_btn">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 编辑工作内容弹出框 -->
	<div class="modal" id="editContent" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="s_projectName">编辑工作内容</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="contentForm">
					<table class="table table-striped" id="t_pbox">
					<button type="button" class="btn btn-sm btn-success glyphicon" onclick="editWorkContent(this);"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加工作内容(ctrl＋↓)</button>
					<div id="s_projectId"></div>
					<div class="editWorkContent"></div>
			        </table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="saveContent_btn">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		$(function(){
			//页面加载完成后，为上面的两个按钮绑定事件，实现移动效果
			$("#toRight").click(function(){
				$("#associationSelect").append($("#noassociationSelect option:selected"));
				$("#associationSelect option").attr("selected","selected");
			});
			$("#toLeft").click(function(){
				$("#noassociationSelect").append($("#associationSelect option:selected"));
			});
			$("#associationBtn").click(function(){
				//需要为隐藏域赋值---定区id
				var rows = $("#grid").datagrid("getSelections");
				var id = rows[0].id;
				$("#customerFixedAreaId").val(id);
				//在提交表单之前，需要选中右侧下拉框中的option
				$("#associationSelect option").attr("selected","selected");
				$("#customerForm").submit();
			});
		})
		//新增工作内容
		function addWorkContent(obj){
    	workDiv="<div class='content'><input class='input-sm col-md-7 validate[required]' type='text' name='workContent' placeholder='请填写工作内容'/>"+
				"<input type='text' class='s_weight2 input-sm  col-md-2 validate[required]' name='weight2' placeholder='请填写权重'/>"+"<span>%</span>"+
				"<button class='btn btn-sm btn-danger glyphicon glyphicon-trash d_deleteBtn col-md-2' type='button' onclick='del(this)' style='padding-bottom:3px;'>删除(ctrl＋↑) </button></div>"
    	$(".workContent").append(workDiv);
    	
        }
		//编辑工作内容
		function editWorkContent(obj){
    	workDiv="<div class='content'><input class='input-sm col-md-7 validate[required]' type='text' name='workContent' placeholder='请填写工作内容'/>"+
				"<input type='text' class='s_weight2 input-sm validate[required] col-md-2' name='weight2' placeholder='请填写权重'/>"+"<span>%</span>"+
				"<button class='btn btn-sm btn-danger glyphicon glyphicon-trash d_deleteBtn col-md-2' type='button' onclick='del(this)' style='padding-bottom:3px;'>删除(ctrl＋↑) </button></div>"
    	$(".editworkContent").append(workDiv);
    	
        }
		function del(obj){
	    	$(obj).closest(".content").remove();
	    	var normTaskProjectId=$(obj).closest(".content").find("#normTaskProjectId").val();
		    	$.ajax({
	        		url: ctx+"/admin/normTaskProject/deleteNormTaskProject",
	        		type: "POST",
	        		async: true,
	        		data: {"normTaskProjectId" : normTaskProjectId},
	        		success: function(data){
	        			if(data.errorCode =="000000"){
	        				Dialog.showSuccess("操作成功");
	        			}
	        			else{
	        				Dialog.showError(data.errorMessage);
	        			}
	        		},
	        		error: function(){
	        		}
	        	});
	    }
		document.onkeydown = function(event){
		    // 捕捉键盘事件
		    if (event.ctrlKey && event.keyCode == 40){
			    addWorkContent();
			    editWorkContent();
			}
		    if (event.ctrlKey && event.keyCode == 38){
		    	del($(".content:last-child .d_deleteBtn"));
			}
		}	
	</script>