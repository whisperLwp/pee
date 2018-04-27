<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/template/normCategory.js"></script>

	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="normCategoryId" type="hidden" value="${normCategory.normCategoryId}"></input>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>考核指标类型名成</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="normCategoryName" value="${normCategory.normCategoryName}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>人力资源指标权重</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="weight" value="${normCategory.weight}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>自评指标权重</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="selfWeight" value="${normCategory.selfWeight}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>上级指标权重</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="levelWeight" value="${normCategory.levelWeight}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>互评指标权重</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="eachWeight" value="${normCategory.eachWeight}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>指标模板</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="normTemplateId" value="${normCategory.normTemplateId}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>指标分类</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="type" value="${normCategory.type}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
	</form>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" id="save_btn">保存</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
	</div>
