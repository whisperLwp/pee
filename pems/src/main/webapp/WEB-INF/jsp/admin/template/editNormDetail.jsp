<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/normDetail/normDetail.js"></script>

	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="normDetailId" type="hidden" value="${normDetail.normDetailId}"></input>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>orderNum</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="orderNum" value="${normDetail.orderNum}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>desrc</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="desrc" value="${normDetail.desrc}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>score</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="score" value="${normDetail.score}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>maxScore</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="maxScore" value="${normDetail.maxScore}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
	</form>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" id="save_btn">保存</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
	</div>
