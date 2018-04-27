<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/template/normCategory.js"></script>
	<form class="form-inline" role="form">
		<div class="form-group btn-group-sm z_margin_tb">
			<label class="sr-only" for="name">查询条件</label> 
			<input type="text" name="" value="" class="form-control input-sm" id="" placeholder="请输入">
			<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
		</div>
		<ul class="nav nav-list"> <li class="divider"></li> </ul>
		<div class="form-group  btn-group-sm" role="group" aria-label="...">
		  <button type="button" class="btn btn-default" onclick="NormCategory.showEdit();"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</button>
		</div>
		
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>序号</th>
						<th>考核指标类型</th>
						<th>人力资源指标权重</th>
						<th>上级指标权重</th>
						<th>自评指标权重</th>
						<th>互评指标权重</th>
						<th>指标模板</th>
						<th>指标分类</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${page.dataList}" varStatus="status">
						<tr>
							<td>${status.index+1 + (page.currentPage-1)*page.pageSize }</td>
							<td>${entity.normCategoryName}</td>
							<td>${entity.weight}</td>
							<td>${entity.selfWeight}</td>
							<td>${entity.levelWeight}</td>
							<td>${entity.eachWeight}</td>
							<td>${entity.normTemplateId}</td>
							<td>${entity.type}</td>
							<td>
							<button type="button" class="btn btn-default btn-sm" onclick="NormCategory.showEdit('${entity.normCategoryId}');">
							  <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>编辑
							</button>
							<button type="button" class="btn btn-default  btn-sm btn-danger" onclick="NormCategory.deleteData('${entity.normCategoryId}');">
							  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
							</button>
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
						<input name="normCategoryId" type="hidden" ></input>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normCategoryName</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="normCategoryName" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>weight</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="weight" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>selfWeight</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="selfWeight" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>levelWeight</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="levelWeight" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>eachWeight</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="eachWeight" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>normTemplateId</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="normTemplateId" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>type</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="type" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
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