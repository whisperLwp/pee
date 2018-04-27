   <%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
	.form-horizontal input{
		margin-left:20px;
	}
	.d_table_striped{
		border:1px solid #ddd;
	}
	.d_table_striped th, .d_table_striped td{
		border:1px solid #ddd;
	}
	.d_table_striped th:first-child, .d_table_striped td:first-child{
		width:68px;
	}
	.modal-footer{
		text-align:center;
	}
	.modal-body{
		height: 585px!important;
   		overflow: auto;
	}
	.modal-dialog{
		height: 90%;
	}
</style>
<script src="${ctx }/resources/js/admin/project/projectEmployee.js"></script>
	<form class="form-inline" role="form">
		<div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="name" value="${name }" class="form-control input-sm" id="" placeholder="请输入">
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		    <button style="margin-top: 0px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
			  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
			</button>
		</div>
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<div class="form-group  btn-group-sm" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="ProjectEmployee.addProEmp(${project.projectId});"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加项目程员</button>
		</div>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>项目名称</th>
						<th>员工名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${page.dataList}" varStatus="status">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<td>${entity.project.projectName}</td>
							<td>${entity.employee.realName}</td>
							<td>
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="ProjectEmployee.deleteData('${entity.projectId}');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
							</button>
							</td>
						</tr>
						<input type="hidden" name="projectId" value="${entity.project.projectId }">
					</c:forEach>
				</tbody>
			</table>
 			<!-- 分页区域 -->
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</div>
		
	</form>


	<!-- 弹出框 -->
	<div class="modal" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">员工列表</h4>
				</div>
				<div class="modal-body">
				    <div class="form-group btn-group-sm z_margin_tb">
						<!-- 部门名称 -->
						<select class="form-control input-sm projectMod" id="dept" style="width: 25%; display:inline;" >
						    <option value="">--请选部门--</option>
						</select>
						<button type="button" onclick="ProjectEmployee.addProEmp(${project.projectId});" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
					</div>
					<form class="form-horizontal" role="form" id="addForm">
					<div id="c_expertId"></div>
						<table class="table table-striped" id="t_cbox">
							<tr>
								<th>序号</th>
								<th>员工名称</th>
							</tr>
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
