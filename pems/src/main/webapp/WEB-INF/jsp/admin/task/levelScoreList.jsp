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
			<!-- 岗位 -->
			<table class="table table-striped" border="1" cellspacing="0">
				<thead>
				<tr style="background-color: #ccc;">
					<th colspan="5">岗位评分[${gwScoreList.get(0).normCategory.weight }%]</th>
				</tr>
					<tr>
						<th>考评项</th>
						<th>权重</th>
						<th>考评标准</th>
						<th>员工自评(${gwScoreList.get(0).normCategory.selfWeight }%)(0-10分)</th>
						<th>直属上级评分(${gwScoreList.get(0).normCategory.levelWeight }%)(0-10分)</th>
					</tr>
				</thead>
				<tbody>
				    <c:forEach var="entity" items="${gwNormList }">
					    <tr>
					        <td>${entity.normName }</td>
					        <td>${entity.weight }%</td>
					        <td>
						        <c:forEach var="detail" items="${entity.normDetailList }">
						           ${detail.desrc }<br>
						        </c:forEach>
					        </td>
						        <c:forEach var="entity2" items="${gwScoreList }">
							        <c:if test="${entity.normId==entity2.normId }">
								        <c:if test="${entity2.scoreType==1 }">
									        <td>
									        	${entity2.score }
									        </td>
								        </c:if>
						        	    <c:if test="${entity2.scoreType==3 }">
									        <td>
					                            <input type="hidden" class="validate[required]" size="5" name="normTaskEmployeeId" value="${entity2.normTaskEmployeeId }"/>
									        	<input type="text" class="validate[required]" onblur="validate(this)" size="5" name="score" value="${entity2.score }"/>
									        </td>
								        </c:if>
							        </c:if>
						        </c:forEach>
					    </tr>
				    </c:forEach>
				</tbody>
			</table>
			<!-- 单一项目表格 -->
			<c:if test="${! empty xmScoreList }">
			<%-- <h5>项目评分（${xmScoreList.get(0).normCategory.weight }%）</h5> --%>
			<table class="table table-striped" border="1" cellspacing="0" style="border-top-width: 2px;border-bottom-width: 0px;">
				<tr style="background-color: #ccc;">
					<th colspan="5">项目评分[${xmScoreList.get(0).normCategory.weight }%]</th>
				</tr>
			</table>
			<c:if test="${! empty normTaskProjectList }">
			<c:forEach var="project" items="${normTaskProjectList }">
			<%-- <h5>${project.project.projectName }项目考评(${project.weight }%)</h5> --%>
			
			<table class="table table-striped" border="1" cellspacing="0">
				<thead>
				    <tr style="background-color: #f0f0f0;">
						<th colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;${index.index+1 }.${project.project.projectName }项目考评(${project.weight }%)</th>
					</tr>
					<tr>
						<th>工作内容</th>
						<th>评分人</th>
						<th>得分(0-10分)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project2" items="${normTaskProjectList2 }" >
						<c:if test="${project2.projectId==project.projectId }">
							<!-- 合并单元格计数 -->
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
							<!-- 自评 -->
							<tr>
								<td rowspan="${len+1 }">${project2.workContent }<${project2.weight2 }%></td> 
								<td>自评(${xmScoreList.get(0).normCategory.selfWeight }%)</td>
								<c:forEach var="entity" items="${xmScoreList }">
									<c:if
										test="${entity.scoreType==1 && entity.workContentId==project2.normTaskProjectId}">
										<td>${entity.score }</td>
									</c:if>
								</c:forEach>
							</tr>
							<!-- 上级评 -->
							<%-- <tr>
								<td>上级评(${xmScoreList.get(0).normCategory.levelWeight }%)</td>
								<c:forEach var="entity" items="${xmScoreList }">
									<c:if
										test="${entity.scoreType==3 && entity.workContentId==project2.normTaskProjectId }">
										<input type="hidden" class="validate[required]" size="5"
											name="normTaskEmployeeId"
											value="${entity.normTaskEmployeeId }" />
										<td><input type="text" class="validate[required]"
											size="5" onblur="validate(this)" name="score" value="${entity.score }" /></td>
									</c:if>
								</c:forEach>
							</tr> --%>
							<!-- 互评 -->
							<c:forEach var="emp" items="${project2.eachEmp }" varStatus="ss">
								<tr>
									<!-- 互評人為本人 -->
									<%-- <td class="s_realName">${emp.employee.realName }(互评权重${xmScoreList.get(0).normCategory.eachWeight }%)</td> --%>
									<%-- <td class="s_realName">${emp.employee.realName }(互评权重${xmScoreList.get(0).normCategory.eachWeight }%)</td> --%>
									<td class="s_realName">${emp.employee.realName }</td>
									<c:forEach var="entity" items="${xmScoreList }">
										<c:if
											test="${entity.scoreType==2 && entity.workContentId==project2.normTaskProjectId && entity.scoreEmployeeId==emp.employee.employeeId}">
											<td>${entity.score }</td>
										</c:if>
									</c:forEach>
								</tr>
							</c:forEach>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:forEach>
		</c:if>
		</c:if>
		</div>
		<div class="modal-footer">
		<button type="button" class="btn btn-primary save_btn" id="save_btn">保存</button>
	</div>
	</form>
	
	<script >
		/* $(function(){
			$(".s_realName").val();
			alert($(".s_realName").val())
		});
	 */
	
	</script>
	
