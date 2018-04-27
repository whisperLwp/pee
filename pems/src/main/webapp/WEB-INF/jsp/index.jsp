<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>宏程教育GEE绩效管理平台</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script src="${ctx}/resources/js/common/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/common/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctx}/resources/css/common/bootstrap.min.css" type="text/css" rel="stylesheet" />

<link href="${ctx}/resources/css/common/style.css" type="text/css" rel="stylesheet" />

<!-- 引用数据校验插件 -->
<link rel="stylesheet" href="${ctx}/resources/js/common/jQueryValidate/css/validationEngine.jquery.css">
<script type="text/javascript" src="${ctx}/resources/js/common/jQueryValidate/js/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common/jQueryValidate/js/jquery.validationEngine.js"></script> 

<!-- 弹出框插件 -->
<script type="text/javascript" src="${ctx}/resources/js/common/layer/2.4/layer.js"></script>
<%-- <script type="text/javascript" src="${ctx}/resources/js/module/main.js"></script> --%>

<script>
	var ctx = "${ctx}";
</script>
<style>
	.container-fluid{
		padding-bottom:58px !important;
	}
</style>
</head>
<body>
<!-- 页头 -->
<nav class="navbar z_navbar" role="navigation">
	<div class="container-fluid z_index_topnav">
		<div class="navbar-header">
		  <img src="${ctx }/resources/img/index_logo.png" class="left z_logo_img">
			<a class="navbar-brand z_navbar_logo" href="${ctx }/welcome" target="mainFrame">宏程教育GEE绩效管理平台</a>
		</div>
		<div>
			<ul class="nav navbar-nav navbar-right z_navbar_right">
				<li class="dropdown"><a href="#" class="dropdown-toggle z_top_droptoggle"
					data-toggle="dropdown">
					<shiro:hasAnyRoles name="admin">管理员：</shiro:hasAnyRoles>
					<shiro:hasAnyRoles name="people">员工：</shiro:hasAnyRoles>
					<shiro:hasAnyRoles name="level">负责人：</shiro:hasAnyRoles>
					<shiro:hasAnyRoles name="nope">总经理：</shiro:hasAnyRoles>
					<shiro:principal property="realName"/><b class="caret"></b>
				</a>
					<ul class="dropdown-menu z_nav_toplist">
					    <li><a href="javascript:;" onclick="editPassword();">修改密码</a></li>
						<li><a href="javascript:;" onclick="logout()">退出</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar z_index_left">
	          	<a href="#1" class="nav-header collapsed z_collapsed" data-toggle="collapse">
					部门管理
				<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
				</a>
				<ul id="1" class="nav nav-list collapse z_secondmenu" style="height: 0px;">
					<li class="z_menu_li"><a href="${ctx }/admin/dept/deptList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>部门管理</span></a></li>
				</ul> 
	          	<a href="#2" class="nav-header collapsed z_collapsed" data-toggle="collapse">
					员工管理
				<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
				</a>
				<ul id="2" class="nav nav-list collapse z_secondmenu" style="height: 0px;">
					<li class="z_menu_li"><a href="${ctx }/admin/employee/employeeList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>员工管理</span></a></li>
				</ul> 
	          	<a href="#3" class="nav-header collapsed z_collapsed" data-toggle="collapse">
					岗位管理
				<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
				</a>
				<ul id="3" class="nav nav-list collapse z_secondmenu" style="height: 0px;">
					<li class="z_menu_li"><a href="${ctx }/admin/station/stationList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>岗位管理</span></a></li>
				</ul> 
				<shiro:hasAnyRoles name="admin">
	          	<a href="#4" class="nav-header collapsed z_collapsed" data-toggle="collapse">
					考核指标管理
				<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
				</a>
				</shiro:hasAnyRoles>
				<ul id="4" class="nav nav-list collapse z_secondmenu" style="height: 0px;">
					<li class="z_menu_li"><a href="${ctx }/admin/normTemplate/normTemplateList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>考核模板</span></a></li>
				</ul> 
				<shiro:hasAnyRoles name="level,admin">
	          	<a href="#5" class="nav-header collapsed z_collapsed" data-toggle="collapse">
					考核项目管理
				<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
				</a>
				</shiro:hasAnyRoles>
				<ul id="5" class="nav nav-list collapse z_secondmenu" style="height: 0px;">
					<li class="z_menu_li"><a href="${ctx }/admin/project/projectList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>考核项目</span></a></li>
					<%-- <li class="z_menu_li"><a href="${ctx }/admin/projectEmployee/projectEmployeeList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>项目人员关系</span></a></li> --%>
				</ul> 
	          	<a href="#6" class="nav-header collapsed z_collapsed" data-toggle="collapse">
					考核任务管理
				<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
				</a>
				<ul id="6" class="nav nav-list collapse z_secondmenu" style="height: 0px;">
					<li class="z_menu_li"><a href="${ctx }/admin/normTask/normTaskList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>考核任务</span></a></li>
					<li class="z_menu_li"><a href="${ctx }/admin/normTask/hasNormTaskList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>已归档考核任务</span></a></li>
				</ul> 
				<%-- <shiro:hasAnyRoles name="admin">
	          	<a href="#6" class="nav-header collapsed z_collapsed" data-toggle="collapse">
					管理员管理
				<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
				</a>
				</shiro:hasAnyRoles>
				<ul id="6" class="nav nav-list collapse z_secondmenu" style="height: 0px;">
					<li class="z_menu_li"><a href="${ctx }/admin/admin/adminList" target="mainFrame" onclick="selectMenu(this);" style="font-size:16px;color:#000;text-indent:30px;"><span>管理员管理</span></a></li>
				</ul>  --%>
        </div>
		<div class="col-sm-9  col-md-10 z_index_right">
		
			<ul class="nav nav-tabs z_inde_right_tabs">
				<li role="presentation">位 置：<a href="javascript:;" id="content_title" class="z_ttms_bread">首页</a></li>
			</ul>
			
			<!-- <sitemesh:write property='body' /> -->
			<iframe src="${ctx }/welcome" frameborder="0" style="width:100%;" name="mainFrame" id="mainFrame"  longdesc="no"></iframe>
		</div>
	</div>
</div>
<!-- 修改密码弹出框 -->
	<div class="modal" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改密码</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="editForm">
					   <div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>原密码</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]"  name="oldPassword" type="text" placeholder="请输入原密码" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>修改密码</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]" id="password1" name="password" type="password" placeholder="请输入您要修改的密码" maxlength="100"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>确认密码</label>
							<div class="col-sm-10">
								<input class="form-control validate[required]" id="password2" name="newPassword" type="password" placeholder="请再输入一次" maxlength="100"></input>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="editSave_btn">保存</button>
				</div>
			</div>
		</div>
	</div>
<!-- 页尾 -->

<nav class="navbar navbar-default navbar-fixed-bottom">
  <h3 align="center" style="margin-top: 10px;" > <small> 2017 北京宏程教育科技有限公司 京ICP备17059980号-2</small></h3>
</nav>

<!-- 调整iframe样式 -->
<script type="text/javascript">	
	function selectMenu(obj){
		$("#content_title").text($(obj).find("span").text());
	}
	
</script>
<!-- 调整iframe样式 -->
<script type="text/javascript">	
   $(function(){
        startInit('mainFrame',800);
        $(function(){
        	$(".z_menu_li").click(function(){
        		$(".z_menu_li").removeClass("z_active");
        		$(this).addClass("z_active")
        	})
        })
    })
    var browserVersion = window.navigator.userAgent.toUpperCase();
    var isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
    var isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
    var isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
    var isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
    var isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
    var isIE9More = (! -[1, ] == false);
    function reinitIframe(iframeId, minHeight) {
        try {
            var iframe = document.getElementById(iframeId);
            var bHeight = 0;
            if (isChrome == false && isSafari == false)
                bHeight = iframe.contentWindow.document.body.scrollHeight;

            var dHeight = 0;
            if (isFireFox == true)
                dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
            else if (isIE == false && isOpera == false)
                dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
            else if (isIE == true && isIE9More) {//ie9+
                var heightDeviation = bHeight - eval("window.IE9MoreRealHeight" + iframeId);
                if (heightDeviation == 0) {
                    bHeight += 3;
                } else if (heightDeviation != 3) {
                    eval("window.IE9MoreRealHeight" + iframeId + "=" + bHeight);
                    bHeight += 3;
                }
            }
            else//ie[6-8]、OPERA
                bHeight += 3;

            var height = Math.max(bHeight, dHeight);
            if (height < minHeight){
            	 height = minHeight;
            }
            iframe.style.height = height + "px";
        } catch (ex) { }
    }
    function startInit(iframeId, minHeight) {
        eval("window.IE9MoreRealHeight" + iframeId + "=0");
        window.setInterval("reinitIframe('" + iframeId + "'," + minHeight + ")", 100);
    }
    //编辑密码弹出框
    function editPassword(){
    	$(".formError").remove();
    	$("#editForm input").each(function() {
			$(this).val("");
		})
    	$('#editModal').modal({keyboard: false});
    }
    //退出
    function logout(){
    	$.ajax({
    		url: ctx+"/login/logout",
    		type: "GET",
    		async: true,
    		success: function(data){
    			if(data.errorCode =="000000"){
    				document.location.reload();
    				document.location.href = ctx + "/login/pems";
    			}else{
    				Dialog.showError(data.errorMessage);
    			}
    		},
    		error: function(){
    		}
    	});
    }
    //点击保存修改密码事件
    $(function(){
    	$("#editSave_btn").click(function(){
    		if(!$("form#editForm").validationEngine("validate")) return ;
    		var p1=$("#password1").val();
    		var p2=$("#password2").val();
    		if(p1==p2){
	    		$.ajax({
	        		url: ctx+"/admin/employee/editPassword",
	        		type: "POST",
	        		async: true,
	        		data: $("#editForm").serialize(),
	        		success: function(data){
	        			if(data =="success"){
	        				layer.msg("修改成功");
	        				document.location.reload();
	        			}
	        			else{
	        				Dialog.showError("您输入的密码有误，请核对后重新输入");
	        			}
	        		},
	        		error: function(){
	        		}
	        	});
    		}else{
    			layer.msg("两次密码不一致，请重新输入");
    			return false;
    		}
    	});
    })
</script>
</body>
</html>