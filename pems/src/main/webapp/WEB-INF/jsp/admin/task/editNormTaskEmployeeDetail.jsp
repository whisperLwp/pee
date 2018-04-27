<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/normTaskEmployeeDetail/normTaskEmployeeDetail.js"></script>

	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="normTaskTmployeeDetailId" type="hidden" value="${normTaskEmployeeDetail.normTaskTmployeeDetailId}"></input>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>employeeId</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="employeeId" value="${normTaskEmployeeDetail.employeeId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normTaskId</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="normTaskId" value="${normTaskEmployeeDetail.normTaskId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>hrScore</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="hrScore" value="${normTaskEmployeeDetail.hrScore}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>deptSelfScore</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="deptSelfScore" value="${normTaskEmployeeDetail.deptSelfScore}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>deptLevelScore</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="deptLevelScore" value="${normTaskEmployeeDetail.deptLevelScore}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>projectSelfScore</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="projectSelfScore" value="${normTaskEmployeeDetail.projectSelfScore}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>projectLevelScore</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="projectLevelScore" value="${normTaskEmployeeDetail.projectLevelScore}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>projectEachScore</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="projectEachScore" value="${normTaskEmployeeDetail.projectEachScore}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>score</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="score" value="${normTaskEmployeeDetail.score}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
	</form>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" id="save_btn">保存</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
	</div>
