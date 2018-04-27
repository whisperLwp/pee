<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/task/normTask.js"></script>
	<form class="form-inline" role="form">
		<div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="name" value="${name }" class="form-control input-sm" id="" placeholder="请输入考核任务名称">
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		</div>
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>任务名称</th>
						<!-- <th>状态</th> -->
						<!-- <th>开始时间</th>
						<th>结束时间</th> -->
						<!-- <th>创建时间</th> -->
						<!-- <th>评分截至时间</th> -->
						<th>归档时间</th>
						<!-- <th>考核模板</th> -->
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${page.dataList}" varStatus="status">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<td>${entity.normTaskName}</td>
							<!-- <td>已归档</td> -->
							<%-- <td><fmt:formatDate type="both" value="${entity.startTime}" /></td>
							<td><fmt:formatDate type="both" value="${entity.endTime}" /></td> --%>
							<%-- <td><fmt:formatDate type="both" value="${entity.createTime}" /></td> --%>
							<%-- <td><fmt:formatDate type="both" value="${entity.scoreTime}" /></td> --%>
							<td><fmt:formatDate type="both" value="${entity.finishTime}" pattern="yyyy-MM-dd"/></td>
							<%-- <td>${entity.normTemplate.normTempName}</td> --%>
							<td>
							<button type="button" class="btn btn-info btn-sm" onclick="NormTask.showHasEmpl('${entity.normTaskId}');">
							  <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>查看考评人员
							</button>
							<shiro:hasAnyRoles name="admin">
							<button type="button" class="btn btn-default  btn-sm btn-success" onclick="NormTask.resetData('${entity.normTaskId}');">
							  <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>取消归档
							</button>
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="NormTask.deleteData('${entity.normTaskId}');">
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
						<input name="normTaskId" type="hidden" ></input>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normTaskName</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="normTaskName" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>status</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="status" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normTempId</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="normTempId" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
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