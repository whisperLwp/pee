<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	.d_pagination{
		position: absolute;
	    top: 0;
	    left: 50%;
	    transform: translateX(-50%);
	}
</style>

<script>
	var currentPage = "${page.currentPage}";
	var totalPage = "${page.totalPage}";
	
	function goPage(obj){
		var page;
		if($(obj).hasClass("first_page")){
			page = 1;
		}else if($(obj).hasClass("pre_page")){
			page = parseInt(currentPage)-1;
		}else if($(obj).hasClass("next_page")){
			page = parseInt(currentPage)+1;
		}else if($(obj).hasClass("last_page")){
			page = totalPage;
		}else {
			 page = $(obj).text();
		}
		var html = "<input type='hidden' name='currentPage' value='"+page+"'>";
		var form = $(obj).closest("form");
		$(obj).closest("form").append(html);
		$(obj).closest("form").submit();
		return; 
	}
	
	
</script>


<c:if test="${page.totalRecord == 0}">
	<div class="z_no_wrap">
		<p class="z_no_word">暂无数据</p>
	</div>
</c:if>



<c:if test="${page.totalPage > 1}">
	<div style="width:100%;position:absolute;">
	<ul class="pagination pull-right d_pagination">
		<li><a href="javascript:void(0);" onclick="goPage(this)" class="first_page">首页</a></li>
		<!-- 直接显示页数 -->
		<c:if test="${page.totalPage <= 7}">
			<c:forEach varStatus="status" begin="1" end="${page.totalPage }">
				<li <c:if test="${page.currentPage == status.index}">class="active"</c:if>><a href="javascript:void(0);"  onclick="goPage(this)"  >${status.index}</a></li>
			</c:forEach>
		</c:if>
		
		<c:if test="${page.totalPage > 7}">
			<!-- 显示前三页 -->
			<c:if test="${page.currentPage <= 3}">
				<c:forEach varStatus="status" begin="1" end="3">
					<li <c:if test="${page.currentPage == status.index}">class="active"</c:if>><a href="javascript:void(0);" onclick="goPage(this)" >${status.index }</a></li>
				</c:forEach>
				<li><a href="javascript:void(0);" onclick="goPage(this)" class="next_page">&gt;&gt;</a></li>
			</c:if>
			<!-- 显示后三叶 -->
			<c:if test="${(page.totalPage-page.currentPage)< 3}">
				
				<li><a href="javascript:void(0);"  onclick="goPage(this)" class="pre_page">&lt;&lt;</a></li>
				<c:forEach varStatus="status" begin="${page.totalPage-2}" end="${page.totalPage}">
					<li <c:if test="${page.currentPage == status.index}">class="active"</c:if>><a href="javascript:void(0);" onclick="goPage(this)" >${status.index }</a></li>
				</c:forEach>
			</c:if>
			<!-- 显示中间页 -->
			<c:if test="${(page.currentPage>3) && ((page.totalPage-page.currentPage)>= 3)}">
				<li><a href="javascript:void(0);" onclick="goPage(this)" class="pre_page">&lt;&lt;</a></li>
				<c:forEach varStatus="status" begin="${page.currentPage-1}" end="${page.currentPage+1}">
					<li <c:if test="${page.currentPage == status.index}">class="active"</c:if>><a href="javascript:void(0);" onclick="goPage(this)" >${status.index }</a></li>
				</c:forEach>
				<li><a href="javascript:void(0);" onclick="goPage(this)" class="next_page">&gt;&gt;</a></li>
			</c:if>
		</c:if>
		<li><a href="javascript:void(0);" onclick="goPage(this)" class="last_page">尾页</a></li>
	</ul>
	</div>	

</c:if>