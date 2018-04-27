<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/station/station.js"></script>

	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="stationId" type="hidden" value="${station.stationId}"></input>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>岗位名称</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="stationName" value="${station.stationName}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>描述</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="remark" value="${station.remark}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
	</form>
	<div class="modal-footer d_modal_footer">
		<div class="d_subject_button">
			<button type="button" class="btn btn-primary" id="save_btn">保存</button>
			<button type="button" class="btn btn-default " onclick="history.go(-1);">返回</button>
		</div>
	</div>
