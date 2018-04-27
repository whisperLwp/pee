<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/project/project.js"></script>
<script src="${ctx }/resources/js/admin/project/projectEmployee.js"></script>
	<form class="form-inline" role="form">
		<div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="" value="" class="form-control input-sm" id="" placeholder="请输入">
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		</div>
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<div class="form-group  btn-group-sm" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="Project.showEdit();"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</button>
		</div>
		
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>项目名称</th>
						<th>状态</th>
						<th>创建人</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${page.dataList}" varStatus="status">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<td>${entity.projectName}</td>
							<td>${entity.status}</td>
							<td>${entity.creator}</td>
							<td><fmt:formatDate type="both" value="${entity.createTime}" /></td>
							<td>
							<%-- <button type="button" class="btn btn-default btn-sm" onclick="Project.showEdit('${entity.projectId}');">
							  <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
							</button> --%>
							<button type="button" class="btn btn-default btn-sm" onclick="ProjectEmployee.addEmployee('${entity.projectId}');">
								  <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>选择互评人员
								</button>
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="Project.deleteData('${entity.projectId}');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
							</button>
							</td>
						</tr>
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
					<h4 class="modal-title" id="myModalLabel">编辑用户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="editForm">
						<input name="projectId" type="hidden" ></input>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>projectName</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="projectName" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>status</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="status" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>creator</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="creator" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="save_btn">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
