<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/normTaskEmployee/normTaskEmployee.js"></script>

	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="normTaskEmployeeId" type="hidden" value="${normTaskEmployee.normTaskEmployeeId}"></input>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normTaskId</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="normTaskId" value="${normTaskEmployee.normTaskId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>employeeId</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="employeeId" value="${normTaskEmployee.employeeId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normCategoryId</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="normCategoryId" value="${normTaskEmployee.normCategoryId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normId</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="normId" value="${normTaskEmployee.normId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>scoreEmployeeId</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="scoreEmployeeId" value="${normTaskEmployee.scoreEmployeeId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>projectId</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="projectId" value="${normTaskEmployee.projectId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>score</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="score" value="${normTaskEmployee.score}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normType</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="normType" value="${normTaskEmployee.normType}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>scoreType</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="scoreType" value="${normTaskEmployee.scoreType}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
	</form>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" id="save_btn">保存</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
	</div>
