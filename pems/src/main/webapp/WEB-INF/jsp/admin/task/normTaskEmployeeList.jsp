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
    <button type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
	  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
	</button>
	<div><h4 align="center">被考核人：${employee2.realName }</h4></div>
	<form class="form-inline" role="form" id="editForm">
		<div class="modal-footer">
		<button type="button" class="btn btn-primary save_btn" id="save_btn">保存</button>
	    </div>
		
		<div class="table-responsive">
			<input type="hidden" name="normTaskId" value="${normTaskId }">
			<!-- 人事 -->
			<shiro:hasAnyRoles name="admin">
			<c:if test="${! empty hrScoreList}">
			<table class="table table-striped" border="1" cellspacing="0">
				<thead>
				<tr style="background-color: #ccc;">
					<th colspan="4">人事评分[${hrScoreList.get(0).normCategory.weight }%]</th>
				</tr>
					<tr>
						<th>考评项</th>
						<th>权重(%)</th>
						<th>考评标准</th>
						<th>人事评分(0-10分)</th>
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
					           <input type="text" class="validate[required]" onblur="validate(this)" size="5" name="score" value="${entity.score }"/>
					           <input type="hidden" class="validate[required]" class="validate[required]" size="5" name="normTaskEmployeeId" value="${entity.normTaskEmployeeId }"/>
					        </td>
					    </tr>
				    </c:forEach>
				</tbody>
			</table>
			</c:if> 
			</shiro:hasAnyRoles>
			<!-- 岗位 -->
			<c:if test="${typeF==1 }">
			<table class="table table-striped" border="1" cellspacing="0">
				<thead>
				<tr style="background-color: #ccc;">
					<th colspan="5">岗位评分[${gwScoreList.get(0).normCategory.weight }%]</th>
				</tr>
					<tr>
						<th>考评项</th>
						<th>权重</th>
						<th>考评标准</th>
						<%-- <th>员工自评(${gwScoreList.get(0).normCategory.selfWeight }%)</th> --%>
						<th>员工自评(${gwScoreList.get(0).normCategory.selfWeight }%)（0-10分）</th>
						<th>直属上级(${gwScoreList.get(0).normCategory.levelWeight }%)（0-10分）</th>
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
					                            <input type="hidden" class="validate[required]" class="validate[required]" size="5" name="normTaskEmployeeId" value="${entity2.normTaskEmployeeId }"/>
									        	<input type="text" class="validate[required]" onblur="validate(this)" size="5" name="score" value="${entity2.score }"/>
									        </td>
								        </c:if>
						        	    <c:if test="${entity2.scoreType==3 }">
									        <td>
					                            ${entity2.score }
									        </td>
								        </c:if>
							        </c:if>
						        </c:forEach>
					    </tr>
				    </c:forEach>
				</tbody>
			</table>
			</c:if>
			
			<!-- 复杂项目表格 -->
			<%-- 
			<h5>项目评分（${xmScoreList.get(0).normCategory.weight }%）</h5>
			<table class="table table-striped"  border="1" cellspacing="0">
				<thead>
					<tr>
						<th>项目</th>
						<th>工作内容</th>
						<th>评分人</th>
						<th>得分</th>
					</tr>
				</thead>
				<tbody>
				    <tr >
				        <td rowspan="4">项目1</td>
				      	<td rowspan="2">用户管理模块</td>
				      	<td >自评</td>
				    </tr>
				    <tr >
				      	<td >上级评</td>
				    </tr>
				    
				     <tr >
				      	<td rowspan="2">系统管理模块</td>
				      	<td >自评</td>
				    </tr>
				     <tr >
				      	<td >上级评</td>
				    </tr>
				</tbody>
			</table>
			 --%>
			
			<!-- 单一项目表格 -->
			
			<c:if test="${typeF!=0 && ! empty xmScoreList && passFlag==1}">
			<table class="table table-striped" border="1" cellspacing="0" style="border-top-width: 2px;border-bottom-width: 0px;">
				<tr style="background-color: #ccc;">
					<th colspan="5">项目评分[${xmScoreList.get(0).normCategory.weight }%]</th>
				</tr>
			</table>
			<c:if test="${! empty normTaskProjectList }">
			<c:forEach var="project" items="${normTaskProjectList }" varStatus="index">
			<table class="table table-striped" border="1" cellspacing="0" style="border-top-width: 0px;">
				<thead>
				<tr style="background-color: #f0f0f0;">
					<th colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;${index.index+1 }.${project.project.projectName }项目考评(${project.weight }%)</th>
				</tr>
					<tr>
						<th>工作内容</th>
						<th>评分人</th>
						<th>评分（0-10分）</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project2" items="${normTaskProjectList2 }"
						varStatus="s">
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
							<!-- 自评 -->
							<tr>
							    <c:if test="${typeF==2 }">
								    <td rowspan="${len+2 }">${project2.workContent }(${project2.weight2 }%)</td> 
								</c:if>
								<c:if test="${EMPLOYEE==employeeId }">
								<td rowspan="${len+1 }">${project2.workContent }<${project2.weight2 }%></td>
								<td>自评(${xmScoreList.get(0).normCategory.selfWeight }%)</td>
								<c:forEach var="entity" items="${xmScoreList }">
									<c:if
										test="${entity.scoreType==1 && entity.workContentId==project2.normTaskProjectId}">
										<input type="hidden" class="validate[required]" size="5"
											name="normTaskEmployeeId"
											value="${entity.normTaskEmployeeId }" />
										<td><input type="text" onblur="validate(this)" class="validate[required]"
											size="5" name="score" value="${entity.score }" /></td>
									</c:if>
								</c:forEach>
								</c:if>
							</tr>
							<%-- <!-- 上级评 -->
							<shiro:hasAnyRoles name="level">
							<tr>
								<td>上级评(${xmScoreList.get(0).normCategory.levelWeight }%)</td>
								<c:forEach var="entity" items="${xmScoreList }">
									<c:if
										test="${entity.scoreType==3 && entity.workContentId==project2.normTaskProjectId }">
										<input type="hidden" class="validate[required]" size="5"
											name="normTaskEmployeeId"
											value="${entity.normTaskEmployeeId }" />
										<td><input type="text" class="validate[required]"
											size="5" name="score" value="${entity.score }" /></td>
									</c:if>
								</c:forEach>
							</tr>
							</shiro:hasAnyRoles> --%>
							<!-- 互评 -->
							<%-- <c:if test="${typeF==2 }">
								<c:forEach var="emp" items="${project2.eachEmp }" varStatus="ss">
									<tr>
										<!-- 互評人為本人 -->
									    <c:if test="${EMPLOYEE==emp.employee.employeeId }">
											<td class="s_realName">${emp.employee.realName }(互评权重${xmScoreList.get(0).normCategory.eachWeight }%)</td>
											<c:forEach var="entity" items="${xmScoreList }">
												<c:if
													test="${entity.scoreType==2 && entity.workContentId==project2.normTaskProjectId && entity.scoreEmployeeId==emp.employee.employeeId}">
													<input type="hidden" class="validate[required]" size="5"
														name="normTaskEmployeeId"
														value="${entity.normTaskEmployeeId }" />
													<td><input type="text" class="validate[required]"
														size="5" name="score" value="${entity.score }" /></td>
												</c:if>
											</c:forEach>
										</c:if>
									</tr>
								</c:forEach>
							</c:if> --%>
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
		<!-- <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> -->
	</div>
	</form>
	
	<script>
	//检验输入的值
	/* function validate(obj){
    	var oldScore = $(obj).attr("value");
		var newScore = $(obj).val();
    	alert(newScore);
		var reg = /^[0-9|10]$/;
		if(!reg.test(newScore)){
		   //输入不合法  
		   layer.msg("请输入0——10的整数",{time:1000});
		   $(obj).val("");
		}
		
    } */
	
	
	</script>
	
