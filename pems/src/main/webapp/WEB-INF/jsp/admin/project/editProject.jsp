<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/project/project.js"></script>

	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="projectId" type="hidden" value="${project.projectId}"></input>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>项目名称</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="projectName" value="${project.projectName}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<%-- <div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label">直接部门</label>
			<div class="col-sm-10">
				<select class="form-control input-sm projectMod s_dept" name="deptCode">
			    <option value="-1">--请选部门--</option>
				<c:forEach var="entity" items="${deptList}" varStatus="status">
				    <option value="${entity.deptCode}" ${entity.deptCode==project.deptCode?'selected="selected"':''}   >${entity.deptName}</option>
			         <c:if test="${! empty entity.list }">
				          <c:forEach var="entity2" items="${entity.list }">
				              <option value="${entity2.deptCode}" ${entity2.deptCode==project.deptCode?'selected="selected"':''}>&nbsp;&nbsp;&nbsp;&nbsp;${entity2.deptName}</option>
				          </c:forEach>
			          </c:if>
				</c:forEach>
			</select>
		  </div>
		</div> --%>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>状态</label>
			<div class="col-sm-10">
				<input type="radio" class="status" name="status" <c:if test="${project.status==1 }">checked="checked"</c:if> value="1" />未开始
			    <input type="radio" class="status" name="status" <c:if test="${project.status==null || project.status==2  }">checked="checked"</c:if> value="2" />进行中
			    <input type="radio" class="status" name="status" <c:if test="${project.status==3 }">checked="checked"</c:if> value="3" />已完成
			</div>
		</div>
		<%-- <div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>创建人</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="creator" value="${project.creator}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div> --%>
	</form>
	<div class="modal-footer d_modal_footer">
		<div class="d_subject_button">
			<button type="button" class="btn btn-primary" id="save_btn">保存</button>
			<button type="button" class="btn btn-default " onclick="history.go(-1);">返回</button>
		</div>
	</div>
