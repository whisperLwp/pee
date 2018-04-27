<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/employee/employee.js"></script>
	<form class="form-inline" role="form">
		<div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="name" value="${name }" class="form-control input-sm" id="" placeholder="请输入员工名称">
			<!-- 部门名称 -->
			<select class="form-control input-sm projectMod" name="deptCode">
			    <option value="-1">--请选部门--</option>
				<c:forEach var="entity" items="${deptList}" varStatus="status">
				    <option value="${entity.deptCode}" ${entity.deptCode==deptId?'selected="selected"':''}   >${entity.deptName}</option>
				    <c:if test="${! empty entity.list }">
				          <c:forEach var="entity2" items="${entity.list }">
				              <option value="${entity2.deptCode}" ${entity2.deptCode==deptId?'selected="selected"':''}>&nbsp;&nbsp;&nbsp;&nbsp;${entity2.deptName}</option>
				          </c:forEach>
				    </c:if>
				</c:forEach>
			</select>
			
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		</div>
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<shiro:hasAnyRoles name="admin">
		<div class="form-group  btn-group-sm" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="Employee.showEdit();"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</button>
		</div>
		</shiro:hasAnyRoles>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>真实姓名</th>
						<th>员工登录名</th>
						<!-- <th>密码</th> -->
						<!-- <th>身份证号</th> -->
						<th>直接部门</th>
						<th>所属岗位</th>
						<th>性别</th>
						<!-- <th>生日</th>
						<th>参加工作时间</th> -->
						<th>电话</th>
						<th>邮箱</th>
						<!-- <th>直属领导</th> -->
						<shiro:hasAnyRoles name="admin">
						<th>操作</th>
						</shiro:hasAnyRoles>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${page.dataList}" varStatus="status">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<td>${entity.realName}</td>
							<td>${entity.employeeName}</td>
							<%-- <td>${entity.password}</td> --%>
							<%-- <td>${entity.idCard}</td> --%>
							<td>${entity.deptName}</td>
							<td>${entity.station.stationName}</td>
							<td>${entity.sexFlag==1?'男':'女'}</td>
							<%-- <td><fmt:formatDate type="both" value="${entity.birthDate}" /></td>
							<td><fmt:formatDate type="both" value="${entity.workDate}" /></td> --%>
							<td>${entity.phoneNum}</td>
							<td>${entity.mailNum}</td>
							<%-- <td>${entity.levelEmployeeName}</td> --%>
							<td>
							
							<shiro:hasAnyRoles name="admin">
							<c:if test="${entity.employeeName!='admin' }">
							<button type="button" class="btn btn-success btn-sm" onclick="Employee.showEdit('${entity.employeeId}');">
							  <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>编辑
							</button>
							<button type="button" class="btn btn-info  btn-sm" onclick="Employee.reset('${entity.employeeId}');">
							  <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>重置密码
							</button>
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="Employee.deleteData('${entity.employeeId}');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
							</button>
							</c:if>
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
						<input name="employeeId" type="hidden" ></input>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>employeeName</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="employeeName" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>realName</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="realName" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>password</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="password" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>idCard</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="idCard" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>deptId</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="deptId" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>sexFlag</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="sexFlag" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>phoneNum</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="phoneNum" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>mailNum</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="mailNum" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>levelEmployeeId</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="levelEmployeeId" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
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