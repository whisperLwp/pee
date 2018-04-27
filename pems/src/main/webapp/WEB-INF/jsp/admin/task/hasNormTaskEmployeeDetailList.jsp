<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/task/normTaskEmployeeDetail.js"></script>
	<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
	  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
	</button>
	<c:if test="${! empty page.dataList}">
	<h3 style="text-align: center;font-weight: bold;">${page.dataList.get(0).normTask.normTaskName}</h3>
	</c:if>
	<form class="form-inline" role="form">
		<div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="name" value="${name }" class="form-control input-sm" id="" placeholder="请输入员工姓名">
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		</div>
		<shiro:hasAnyRoles name="admin">
		<div class="form-group  btn-group-sm" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick=""><span class="glyphicon glyphicon-export" aria-hidden="true"></span>导出全部</button>
		</div>
		</shiro:hasAnyRoles>
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>员工</th>
						<!-- <th>考评任务</th> -->
						<th>人力资源评分</th>
						<th>岗位自评分</th>
						<th>岗位上级评分</th>
						<th>项目自评分</th>
						<!-- <th>项目上级评分</th> -->
						<th>项目互评分</th>
						<th>总分</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${page.dataList}" varStatus="status">
						<c:if test="${! empty employeeList }">
						<c:forEach var="entity2" items="${employeeList }">
						<c:if test="${entity.employee.employeeId==entity2.employeeId }">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<td>${entity.employee.realName}</td>
							<%-- <td>${entity.normTask.normTaskName}</td> --%>
							<td>${entity.hrScore}</td>
							<td>${entity.deptSelfScore}</td>
							<td>${entity.deptLevelScore}</td>
							<td>${entity.projectSelfScore}</td>
							<%-- <td>${entity.projectLevelScore}</td> --%>
							<td>${entity.projectEachScore}</td>
							<td>${entity.score}</td>
							<td>
							<button type="button" class="btn btn-info btn-sm" onclick="NormTaskEmployeeDetail.hasScore('${entity.normTaskId}','${entity.employeeId}','${entity.normTask.normTaskName }');">
							  <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>评分详情
							</button>
							<shiro:hasAnyRoles name="admin">
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="NormTaskEmployeeDetail.deleteData('${entity.normTaskTmployeeDetailId}');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
							</button>

							</shiro:hasAnyRoles>
							</td>
						</tr>
						</c:if>
						</c:forEach>
						</c:if>
						<c:if test="${empty employeeList }">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<td>${entity.employee.realName}</td>
							<%-- <td>${entity.normTask.normTaskName}</td> --%>
							<td>${entity.hrScore}</td>
							<td>${entity.deptSelfScore}</td>
							<td>${entity.deptLevelScore}</td>
							<td>${entity.projectSelfScore}</td>
							<%-- <td>${entity.projectLevelScore}</td> --%>
							<td>${entity.projectEachScore}</td>
							<td>${entity.score}</td>
							<td>
							<button type="button" class="btn btn-info btn-sm" onclick="NormTaskEmployeeDetail.hasScore('${entity.normTaskId}','${entity.employeeId}','${entity.normTask.normTaskName }');">
							  <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>评分详情
							</button>
							<shiro:hasAnyRoles name="admin">
							<button type="button" class="btn btn-sm btn-warning" onclick="NormTaskEmployeeDetail.exportExcel('${entity.normTaskId}','${entity.employeeId}')">
							  <span class="glyphicon glyphicon-export" aria-hidden="true"></span>导出详情
							</button>
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="NormTaskEmployeeDetail.deleteData('${entity.normTaskTmployeeDetailId}');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
							</button>
							</shiro:hasAnyRoles>
							</td>
						</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			<input type="hidden" name="normTaskId" value="${normTaskId }">
 			<!-- 分页区域 -->
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</div>
	</form>

	
	<!--添加考评人员 弹出框 -->
	<div class="modal" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增考评员工</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="addForm">
					<div id="c_expertId"></div>
						<table class="table table-striped" id="t_cbox">
							<tr>
								<th>序号</th>
								<th>员工名称</th>
							</tr>
							<!-- <tr>
								<td>1</td>
								<td>员工名称</td>
							</tr> -->
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
					<h4 class="modal-title" id="myModalLabel">选择考核项目</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="addForm">
					<div id="c_projectId"></div>
						<table class="table table-striped" id="t_pbox">
							<tr>
								<th>序号</th>
								<th>项目名称</th>
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