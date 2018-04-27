<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script src="${ctx }/resources/js/common/highcharts.js"></script>

<!-- 管理员首页 -->


<shiro:hasAnyRoles name="admin">
<div class="clearfix">
<div class="col-sm-6">
	<div class="panel panel-default ">
	  <div class="panel-heading" style="text-align: center;font-weight: bold;color:blue ; ">当前绩效考核</div>
	  <div class="panel-body" id="container">
		<!-- <image src="https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1523335362&di=9acf20387783fdfc2e88c84da86d709b&src=http://imgs.soufun.com/news/2015_03/23/house/1427075861306_000.jpg"></image> -->
	  </div>
	</div>
	
</div>
</shiro:hasAnyRoles>


<!-- 个人首页 -->
<c:if test="${role==2 || role==3 }">
<div class="col-sm-6">
	<div class="panel panel-default ">
	  <div class="panel-heading" style="text-align: center;font-weight: bold;color:blue ; ">考核项目</div>
	  <div class="panel-body">
	  	<table >
			<tbody>
			<c:forEach var="entity" items="${projects }" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td>${entity.projectName }</td>
			</tr>
			</c:forEach>
			<!-- <tr>
				<td>123</td>
			</tr> -->	
			</tbody>
			</table>		    
	  </div>
	</div>
	
	<div class="panel panel-default ">
	  <div class="panel-heading" style="text-align: center;font-weight: bold;color:blue ; ">我的绩效</div>
	  <div class="panel-body">
	  	<c:forEach var = "entity" items="${detailList }">
	  	<a href="${ctx }/admin/normTaskEmployee/normTaskEmployeeList?normTaskId=${entity.normTaskId}&employeeId=${entity.employeeId}&typeF=${typeF}"> 查看评分详情</a>
	   	</c:forEach> 
	  </div>
	</div>
</div>
</c:if>
<div class="col-sm-6">
	<div class="panel panel-default ">
	  <div class="panel-heading" style="text-align: center;font-weight: bold;color:blue ; ">待办任务</div>
	  <div class="panel-body">
	    <table >
			<tbody>
			<c:forEach var="entity" items="${taskList }" varStatus="status">
			<tr>
			    <td><h3 style="background: #fff">${status.index+1 }:<h3></td>
				<td>${entity.normTaskName }<a href="${ctx }/admin/normTaskEmployeeDetail/normTaskEmployeeDetailList?normTaskId=${entity.normTaskId}"> 待办任务详情</a></td>
			</tr>
			</c:forEach>
			</tbody>
			</table>
	  </div>
	</div>
</div>
</div>
<div style="position:absolute; top:540px; margin-left:150px;">
<h4 style="text-align: center;font-weight: bold;color:red ; " >本次考核意在实现企业的目标和提高员工的业绩，促进企业和员工的共同进步，与薪酬奖金分配无直接关系</h4>
</div>



<script>
  $(function () {
	 initPieData();
	
});
	function initPie(data,txt){
		//alert(JSON.stringify( data ));
		$('#container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: txt,
	        },
	        colors:[
	                'red',//第一个颜色，欢迎加入Highcharts学习交流群294191384
	                'blue',//第二个颜色
	                'yellow',//第三个颜色
	               '#1aadce', //。。。。
	                   '#492970',
	                   '#f28f43', 
	                   '#77a1e5', 
	                   '#c42525', 
	                   '#a6c96a'
	              ],
	        tooltip: {
	            headerFormat: '{series.name}<br>',
	            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'green'
	                    }
	                }
	            }
	        },
	        xAxis: {
	        	categories: ['已完成', '未完成']
	        	},
	        series: [{
	            type: 'pie',
	            name: '考评状况占比',
	            data: data,
	            	/* [
	                ['已完成人数',   50.0],
	                ['未完成人数',       50.0],
	            ] */
	        }]
	    });
		
	}
	
   function initPieData(){
	   var arr = [];
	   $.ajax({
   		url: ctx+"/admin/pieData",
   		type: "POST",
   		async: true,
   		success: function(data){
   			if(data.errorCode =="000000"){
   			    //定义一个数组
                browsers = [],
                txt="",
                //迭代，把异步获取的数据放到数组中
                $.each(data.data,function(i,d){
                    browsers.push([d.name,d.num]);
                    txt=d.txt;
                });
   				initPie(browsers,txt);
   			}
   			else{
   				Dialog.showError(data.errorMessage);
   			}
   		},
   		error: function(){
   		}
   	});
	   //
	   
   }
</script>