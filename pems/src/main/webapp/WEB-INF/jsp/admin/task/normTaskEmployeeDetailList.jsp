<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
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
<script src="${ctx }/resources/js/admin/task/normTaskEmployeeDetail.js"></script>
	<form class="form-inline" role="form">
	    <input type="hidden" name="normTaskId" />
		<!-- <div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="" value="" class="form-control input-sm" id="" placeholder="请输入员工姓名">
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		</div> -->
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<shiro:hasAnyRoles name="admin">
		<div class="form-group  btn-group-sm z_margin_tb" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="NormTaskEmployeeDetail.addEmpl(${normTaskId},'${deptId }');"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增被考评人员</button>
		</div>
		<div class="form-group  btn-group-sm" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="NormTaskEmployeeDetail.sendMail();"><span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span>一键通知未评分人员</button>
		</div>
		<input type="hidden" value="${mails }" id="mails">
		</shiro:hasAnyRoles>
		<button type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<%-- <h3 style="text-align: center;font-weight: bold;">${resultMap.admin.get(0).normTask.normTaskName}</h3>
		<h3 style="text-align: center;font-weight: bold;">${resultMap.self.get(0).normTask.normTaskName}</h3> --%>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<!-- <th>序号</th> -->
						<th>考评任务</th>
						<th>员工</th>
						<shiro:hasAnyRoles name="admin">
						<th>完成状态</th>
						</shiro:hasAnyRoles>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${resultMap.admin}" varStatus="status">
						<tr>
							<%-- <td>${status.index+1 }</td> --%>
							<td>${entity.normTask.normTaskName}</td>
							<td>${entity.employee.realName}</td>
							<shiro:hasAnyRoles name="admin">
							<td>
							
							<span <c:if test="${entity.hrF ==1 }">title="已完成"</c:if><c:if test="${entity.hrF ==0 }">title="未完成"</c:if> style="background: #46b8da; padding: 5px;border-radius:5px;opacity:<c:if test="${entity.hrF ==0 }">0.5</c:if>;color:#ffff;">hr</span>
							<span <c:if test="${entity.gsF ==1 }">title="已完成"</c:if> <c:if test="${entity.gsF ==0 }">title="未完成"</c:if> style="background: #46b8da; padding: 5px;border-radius:5px;opacity:<c:if test="${entity.gsF ==0 }">0.5</c:if>;color:#ffff;">岗位自评</span>
							<span <c:if test="${entity.glF ==1 }">title="已完成"</c:if> <c:if test="${entity.glF ==0 }">title="未完成"</c:if> style="background: #46b8da; padding: 5px;border-radius:5px;opacity:<c:if test="${entity.glF ==0 }">0.5</c:if>;color:#ffff;">岗位上级</span>
							<span <c:if test="${entity.xsF ==1 }">title="已完成"</c:if> <c:if test="${entity.xsF ==0 }">title="未完成"</c:if> style="background: #46b8da; padding: 5px;border-radius:5px;opacity:<c:if test="${entity.xsF ==0 }">0.5</c:if>;color:#ffff;">项目自评</span>
							<%-- <span style="background: #46b8da; padding: 5px;border-radius:5px;opacity:<c:if test="${entity.xlF ==1 }">1.5</c:if><c:if test="${entity.xlF ==0 }">0.5</c:if>;color:#ffff;">项目上级</span> --%>
							<span <c:if test="${entity.xeF ==1 }">title="已完成"</c:if> <c:if test="${entity.xeF ==0 }">title="未完成"</c:if> style="background: #46b8da; padding: 5px;border-radius:5px;opacity:<c:if test="${entity.xeF ==0 }">0.5</c:if>;color:#ffff;">项目互评</span>
							
							 </td>
							 </shiro:hasAnyRoles>
							<td>
							<c:if test="${entity.hrF==1 }">
							<button type="button" class="btn btn-success btn-sm" onclick="NormTaskEmployeeDetail.score('${entity.normTaskId}','${entity.employeeId}','0');">
							  <span class="glyphicon glyphicon-check" aria-hidden="true"></span>人力评完成
							</button>
							</c:if>
							<c:if test="${entity.hrF==0 }">
							<button type="button" class="btn btn-info btn-sm" onclick="NormTaskEmployeeDetail.score('${entity.normTaskId}','${entity.employeeId}','0');">
							  <span class="glyphicon glyphicon-hourglass" aria-hidden="true"></span>人力评待评
							</button>
							</c:if>
							<shiro:hasAnyRoles name="admin">
							<button type="button" class="btn btn-warning btn-sm" onclick="NormTaskEmployeeDetail.hasScore('${entity.normTaskId}','${entity.employeeId}','${entity.normTask.normTaskName }');">
							  <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>评分详情
							</button>
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="NormTaskEmployeeDetail.deleteData('${entity.normTaskTmployeeDetailId}','${entity.employeeId }','${entity.normTaskId }');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除被考核人
							</button>
							</shiro:hasAnyRoles>
							</td>
						</tr>
					</c:forEach>
					<!-- <th>自评</th> -->
					<c:forEach var="entity" items="${resultMap.self}" varStatus="status">
						<tr>
							<%-- <td>${status.index+1 }</td> --%>
							<td>${entity.normTask.normTaskName}</td>
							<td>${entity.employee.realName}</td>
							<td>
							<%-- <c:if test="${entity.gsF==1 && entity.xsF==1 }">
							<button type="button" class="btn btn-success btn-sm" onclick="NormTaskEmployeeDetail.score('${entity.normTaskId}','${entity.employeeId}','1');">
							  <span class="glyphicon glyphicon-check" 
							  aria-hidden="true"></span>自评完成
							</button>
							</c:if> --%>
							<button type="button" class="btn btn-success btn-sm" onclick="NormTaskEmployeeDetail.score('${entity.normTaskId}','${entity.employeeId}','1');">
							  <span class="glyphicon glyphicon-edit" 
							  aria-hidden="true"></span>自评
							</button>
							<button type="button" class="btn btn-warning btn-sm" onclick="NormTaskEmployeeDetail.chooseProject('${entity.normTaskTmployeeDetailId}','${entity.normTaskId}','${entity.employeeId }');">
							  <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>选择考核项目
							</button>
							<c:if test="${entity.passFlag==0 }">
							<button type="button" class="btn btn-info btn-sm" onclick="">
							  <span class="glyphicon glyphicon-hourglass" aria-hidden="true"></span>待审核
							</button>
							</c:if>
							<c:if test="${entity.passFlag==2 }">
							<button type="button" class="btn btn-danger btn-sm" onclick="">
							  <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>审核未通过
							</button>
							</c:if>
						</tr>
					</c:forEach>
					<!-- <th>下级评分人员</th> -->
					<c:forEach var="entity1" items="${resultMap.level}" varStatus="status">
						<c:forEach var="entity2" items="${entity1 }">
						<tr>
							<%-- <td>${status.index+1 }</td> --%>
							<td>${entity2.normTask.normTaskName}</td>
							<td>${entity2.employee.realName}</td>
							<td>
							<c:if test="${entity2.passFlag==1 }">
							<c:if test="${entity2.glF==1 }">
							<button type="button" class="btn btn-success btn-sm" onclick="NormTaskEmployeeDetail.Levelscore('${entity2.normTaskId}','${entity2.employeeId}','3');">
							  <span class="glyphicon glyphicon-education" aria-hidden="true"></span>给下级评分完成
							</button>
							</c:if>
							<c:if test="${entity2.glF==0 }">
							<button type="button" class="btn btn-info btn-sm" onclick="NormTaskEmployeeDetail.Levelscore('${entity2.normTaskId}','${entity2.employeeId}','3');">
							  <span class="glyphicon glyphicon-hourglass" aria-hidden="true"></span>给下级评分待评
							</button>
							</c:if>
							</c:if>
							<c:if test="${entity2.passFlag==2 }">
							<button type="button" class="btn btn-danger  btn-sm" onclick="NormTaskEmployeeDetail.chooseProject('${entity2.normTaskTmployeeDetailId}','${entity2.normTaskId}','${entity2.employeeId }');">
							  <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>审核未通过
							</button>
							</c:if>
							<c:if test="${entity2.passFlag==0 }">
							<button type="button" class="btn btn-info  btn-sm" onclick="NormTaskEmployeeDetail.chooseProject('${entity2.normTaskTmployeeDetailId}','${entity2.normTaskId}','${entity2.employeeId }');">
							  <span class="glyphicon glyphicon-education" aria-hidden="true"></span>审核
							</button>
							</c:if>
							</td>
						</tr>
						</c:forEach>
					</c:forEach>
					<!-- <th>互评人员</th> -->
					<c:forEach var="entity1" items="${resultMap.each}" varStatus="status">
						<c:forEach var="entity2" items="${entity1 }">
						<c:if test="${entity2.passFlag==1 }">
						<tr>
							<%-- <td>${status.index+1 }</td> --%>
							<td>${entity2.normTask.normTaskName}</td>
							<td>${entity2.employee.realName}</td>
							<td>
							<c:if test="${entity2.xeF==1 }">
							<button type="button" class="btn btn-success btn-sm" onclick="NormTaskEmployeeDetail.EachScore('${entity2.normTaskId}','${entity2.employeeId}','2');">
							  <span class="glyphicon glyphicon-retweet" aria-hidden="true"></span>给他人互评完成
							</button>
							</c:if>
							<c:if test="${entity2.xeF==0 }">
							<button type="button" class="btn btn-info btn-sm" onclick="NormTaskEmployeeDetail.EachScore('${entity2.normTaskId}','${entity2.employeeId}','2');">
							  <span class="glyphicon glyphicon-hourglass" aria-hidden="true"></span>给他人互评待评
							</button>
							</c:if>
						</tr>
						</c:if>
						</c:forEach>
					</c:forEach>
					
				</tbody>
			</table>
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
				    <div class="form-group btn-group-sm z_margin_tb">
						<!-- 部门名称 -->
						<select class="form-control input-sm projectMod" id="dept" style="width: 25%; display:inline;" >
						    <option value="">--请选部门--</option>
						</select>
						<button type="button" onclick="NormTaskEmployeeDetail.addEmpl(${normTaskId});" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
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