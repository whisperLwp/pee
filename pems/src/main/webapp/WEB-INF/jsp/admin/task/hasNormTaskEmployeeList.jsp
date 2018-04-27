<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
     .s_score {
	    border:1px solid #C0C0C0;
		text-align:left;
		background-color:#D3D3D3;
		width:100px;
		readonly:expression(this.readOnly=true);
	    }
	 .table{
	 	margin-bottom: 0px;
	 }
</style>
   <button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
	  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
	</button>
    <div class="btn-group-sm z_margin_tb" style="border: 1px solid #ccc;background-color: #fbf3e7;padding-left: 10px;color:#777;">
			<h4>计算评分公式如下：</h4>
			<p>
			<b class="z_common_star">*</b>1.总分=人力资源考评分+岗位考评分+项目考评分<br>
			<b class="z_common_star">*</b>2.人力资源考评分=人力资源分值*人力资源权重<br>
			<b class="z_common_star">*</b>3.岗位考评分=（自评分值*自评权重+上级分值*上级权重）*岗位权重<br>
			<b class="z_common_star">*</b>3.互评分值=（互评人1分值+互评人2分值…+互评人n分值）/n<br>
			</p>
		</div>
   
	<h3 style="text-align: center;font-weight: bold;">${taskName }</h3>

	<h4 style="border-bottom: 1px solid #000;padding-bottom: 5px;margin-bottom: 0px;">
	&nbsp;&nbsp;&nbsp;&nbsp;<strong>姓名：</strong>${employee2.realName }
	&nbsp;&nbsp;&nbsp;&nbsp;<strong>部门：</strong>${employee2.deptName }
	&nbsp;&nbsp;&nbsp;&nbsp;<strong>岗位：</strong>${employee2.station.stationName }
	&nbsp;&nbsp;&nbsp;&nbsp;<strong>直属上级：</strong>${employee2.levelEmployeeName }
	&nbsp;&nbsp;&nbsp;&nbsp;<strong>考核开始时间：</strong><fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${normTask.startTime }" />
	&nbsp;&nbsp;&nbsp;&nbsp;<strong>考核截止时间：</strong><fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${normTask.endTime }" /></h4>
<script src="${ctx }/resources/js/admin/task/normTaskEmployee.js"></script>
	<form class="form-inline" role="form" id="editForm">
		<div class="table-responsive">
		
			<!-- 人事 -->
			<c:if test="${! empty hrScoreList}">
			<table class="table table-striped" border="1" cellspacing="0">
				<%-- <h5>人事评分（${hrScoreList.get(0).normCategory.weight }%）</h5> --%>
				<thead>
					<tr style="background-color: #ccc;">
						<th colspan="4">人事评分（${hrScoreList.get(0).normCategory.weight }%）</th>
					</tr>
					<tr>
						<th>考评项</th>
						<th>权重(%)</th>
						<th>考评标准</th>
						<th>人事评分</th>
					</tr>
				</thead>
				<tbody>
				    <c:forEach var="entity" items="${hrScoreList }">
					    <tr>
					        <td>${entity.norm.normName }</td>
					        <td>${entity.norm.weight }%</td>
					        <td>
						        <c:forEach var="detail" items="${entity.normDetailList }">
						           ${detail.desrc }<br>
						        </c:forEach>
					        </td>
					        <td>
					           ${entity.score }
					           <input type="hidden" class="s_score validate[required]" size="5" name="normTaskEmployeeId" value="${entity.normTaskEmployeeId }"/>
					        </td>
					    </tr>
				    </c:forEach>
				</tbody>
			</table> 
			</c:if>
			<!-- 岗位 -->
			
			<table class="table table-striped" border="1" cellspacing="0">
				<%-- <h5>岗位评分（${gwScoreList.get(0).normCategory.weight }%）</h5> --%>
				<thead>
					<tr style="background-color: #ccc;">
						<th colspan="5">岗位评分（${gwScoreList.get(0).normCategory.weight }%）</th>
					</tr>
					<tr>
						<th>考评项</th>
						<th>权重(100%)</th>
						<th>考评标准</th>
						<th>员工自评(${gwScoreList.get(0).normCategory.selfWeight }%)</th>
						<th>直属上级(${gwScoreList.get(0).normCategory.levelWeight }%)</th>
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
					                            <input type="hidden" class="s_score validate[required]" class="s_score validate[required]" size="5" name="normTaskEmployeeId" value="${entity2.normTaskEmployeeId }"/>
									        	${entity2.score }
									        </td>
								        </c:if>
						        	    <c:if test="${entity2.scoreType==3 }">
									        <td>
					                            <input type="hidden" class="s_score validate[required]" size="5" name="normTaskEmployeeId" value="${entity2.normTaskEmployeeId }"/>
									        	${entity2.score }
									        </td>
								        </c:if>
							        </c:if>
						        </c:forEach>
					    </tr>
				    </c:forEach>
				</tbody>
			</table>
			
			<!-- 项目 -->
			<c:if test="${! empty xmScoreList }">
			<%-- <h5>项目评分（${xmScoreList.get(0).normCategory.weight }%）</h5> --%>
			<table class="table table-striped" border="1" cellspacing="0" style="border-top-width: 2px;border-bottom-width: 0px;">
				<tr style="background-color: #ccc;">
					<th colspan="5">项目评分（${xmScoreList.get(0).normCategory.weight }%）</th>
				</tr>
			</table>
			<c:if test="${! empty normTaskProjectList }">
			<c:forEach var="project" items="${normTaskProjectList }" varStatus="index" >
			<%-- <h5>${project.project.projectName }项目考评(${project.weight }%)</h5> --%>
			<table class="table table-striped" border="1" cellspacing="0" style="border-top-width: 0px;">
				<thead>
					<tr style="background-color: #f0f0f0;">
						<th colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;${index.index+1 }.${project.project.projectName }项目考评<${project.weight }%></th>
					</tr>
					<tr>
						<th>工作内容</th>
						<th>评分人</th>
						<th>得分</th>
					</tr>
				</thead>


				<tbody>
						<c:forEach var="project2" items="${normTaskProjectList2 }" varStatus="s">
							<c:if test="${project2.projectId==project.projectId }">
								<!-- 合并单元格计数 -->
								<c:set var="len" value="0"/>
								<c:forEach var="emp" items="${project2.eachEmp }" varStatus="ss">
							     <tr>
							         <c:forEach var="entity" items="${xmScoreList }">
										<c:if test="${entity.scoreType==2 && entity.workContentId==project2.normTaskProjectId && entity.scoreEmployeeId==emp.employee.employeeId}">
								        <c:set var="len" value="${len+1 }"/>
									</c:if>
									</c:forEach>
						         </tr>
								</c:forEach>
								<!-- 自评 -->
								<tr>
									<td rowspan="${len+1}">${project2.workContent }《${project2.weight2 }%》</td>
									<td>自评(${xmScoreList.get(0).normCategory.selfWeight }%)</td>
									<c:forEach var="entity" items="${xmScoreList }">
										<c:if test="${entity.scoreType==1 && entity.workContentId==project2.normTaskProjectId}">
									        <input type="hidden" class="validate[required]" size="5" name="normTaskEmployeeId" value="${entity.normTaskEmployeeId }"/>
								        	<td>${entity.score }</td>
										</c:if>
									</c:forEach>
								</tr>
								<!-- 上级评 -->
								<%-- <tr>
									<td>上级评(${xmScoreList.get(0).normCategory.levelWeight }%)</td>
									<c:forEach var="entity" items="${xmScoreList }">
										<c:if test="${entity.scoreType==3 && entity.workContentId==project2.normTaskProjectId }">
								        <input type="hidden" class="validate[required]" size="5" name="normTaskEmployeeId" value="${entity.normTaskEmployeeId }"/>
							        	<td>${entity.score }</td>
									</c:if>
									</c:forEach>
								</tr> --%>
								<!-- 互评 -->
								<c:forEach var="emp" items="${project2.eachEmp }" varStatus="ss">
							     <tr>
								     <%-- <td>${emp.employee.realName }(互评权重${xmScoreList.get(0).normCategory.eachWeight }%)</td> --%>
								     <td>${emp.employee.realName }</td>
							         <c:forEach var="entity" items="${xmScoreList }">
										<c:if test="${entity.scoreType==2 && entity.workContentId==project2.normTaskProjectId && entity.scoreEmployeeId==emp.employee.employeeId}">
								        <c:set var="len" value="${len+1 }"/>
								        <input type="hidden" class="validate[required]" size="5" name="normTaskEmployeeId" value="${entity.normTaskEmployeeId }"/>
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
	</form>
	
