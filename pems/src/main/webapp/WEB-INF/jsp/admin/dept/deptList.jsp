<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/dept/dept.js"></script>
	<form class="form-inline" role="form">
		<div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="deptName" value="${deptName}" class="form-control input-sm" id="" placeholder="请输入部门名称">
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		</div>
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<shiro:hasAnyRoles name="admin">
		<div class="form-group  btn-group-sm" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="Dept.showEdit();"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</button>
		</div>
		</shiro:hasAnyRoles>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>部门名称</th>
						<th>备注</th>
						<!-- <th>上级部门Id</th> -->
						<th>部门负责人</th>
						<shiro:hasAnyRoles name="admin">
						<th>操作</th>
						</shiro:hasAnyRoles>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${page.dataList}" varStatus="status">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<td>${entity.deptName}</td>
							<td>${entity.remark}</td>
							<%-- <td>${entity.pDeptId}</td> --%>
							<td>${entity.employee.realName}</td>
							<td>
							<shiro:hasAnyRoles name="admin">
							<button type="button" class="btn btn-success btn-sm" onclick="Dept.showEdit('${entity.deptId}');">
							  <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>编辑
							</button>
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="Dept.deleteData('${entity.deptId}');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
							</button>
							</shiro:hasAnyRoles>
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
						<input name="deptId" type="hidden" ></input>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>deptName</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="deptName" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>remark</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="remark" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>pdeptId</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="pdeptId" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>deptUserId</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="deptUserId" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
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