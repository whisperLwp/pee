<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
	 .table{
	 	margin-bottom: 0px;
	 }
</style>
<script src="${ctx }/resources/js/admin/task/normTaskEmployee.js"></script>
    <button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
	  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
	</button>
	<div><h4 align="center">被考核人：${employee2.realName }</h4></div>
	<form class="form-inline" role="form" id="editForm">
		<input type="hidden" name="normTaskId" value="${normTaskId }">
		<div class="modal-footer">
			<button type="button" class="btn btn-primary save_btn" id="save_btn">保存</button>
	    </div>
		<div class="table-responsive">
			
			<c:if test="${! empty xmScoreList }">
			<h5>项目评分[${xmScoreList.get(0).normCategory.weight }%]</h5>
			<c:forEach var="project" items="${normTaskProjects }">
			<c:forEach var="pro" items="${normTaskProjectList }">
			   <c:if test="${pro.projectId==project.projectId }">
			       <h5>${pro.project.projectName }项目考评(${pro.weight }%)</h5>
			   </c:if>
			</c:forEach>
			<table class="table table-striped" border="1" cellspacing="0">
				<thead>
					<tr>
						<th>工作内容</th>
						<th>评分人</th>
						<th>得分(0-10分)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project2" items="${normTaskProjectList2 }">
						<c:if test="${project2.projectId==project.projectId }">
							<!-- 合并单元格计数 -->
							<c:if test="${typeF==2 }">
								<c:set var="len" value="0" />
								<c:forEach var="emp" items="${project2.eachEmp }" varStatus="ss">
									<tr>
										<c:forEach var="entity" items="${xmScoreList }">
											<c:if
												test="${entity.scoreType==2 && entity.workContentId==project2.normTaskProjectId && entity.scoreEmployeeId==emp.employee.employeeId}">
												<c:set var="len" value="${len+1 }" />
											</c:if>
										</c:forEach>
									</tr>
								</c:forEach>
							</c:if>
							<!-- 互评 -->
							<c:if test="${typeF==2 }">
								<c:forEach var="emp" items="${project2.eachEmp }" >
									<tr>
										<!-- 互評人為本人 -->
									    <c:if test="${EMPLOYEE==emp.employee.employeeId }">
									        <td rowspan="2">${project2.workContent }<${project2.weight2 }%></td>
											<%-- <td>${emp.employee.realName }(互评权重${xmScoreList.get(0).normCategory.eachWeight }%)</td> --%>
											<td>${emp.employee.realName }</td>
											<c:forEach var="entity" items="${xmScoreList }">
												<c:if
													test="${entity.scoreType==2 && entity.workContentId==project2.normTaskProjectId && entity.scoreEmployeeId==emp.employee.employeeId}">
													<input type="hidden" class="validate[required]" size="5"
														name="normTaskEmployeeId"
														value="${entity.normTaskEmployeeId }" />
													<td><input type="text" class="validate[required]"
														size="5" name="score" onblur="validate(this)" value="${entity.score }" /></td>
												</c:if>
											</c:forEach>
										</c:if>
									</tr>
								</c:forEach>
							</c:if>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:forEach>
		</c:if>	
		</div>
		<div class="modal-footer">
		<button type="button" class="btn btn-primary save_btn" id="save_btn">保存</button>
	</div>
	</form>
	