<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/resources/js/admin/login/login.js"></script>
	<div class="z_ttms_nav_wrap">
        <div class="z_ttms_nav_top">
            <div class="container">
                <div class="row">
                    <div class="left">
                        <img src="${ctx }/resources/img/index_logo.png" alt="">
                        <span class="z_homepage_logoname">宏程教育GEE绩效管理平台</span>
                    </div>

                </div>
            </div>
        </div>
    </div>
	<div class="z_login_content">
        <div>
            <p class="z_ttms_login_title">登录系统</p>
            <p class="z_ttms_login_title_eng">PEMS Content Manage System</p>
        </div>
        <div class="z_login_box">
            <div class="z_login_inner">
                <form action="" id="editForm">
                    <label for="">
                        <span class="z_login_in">用户名：</span><input class="z_login_user validate[required]" type="text" name="username" >
                    </label>
                    <label for="">
                        <span class="z_login_in">密 码：</span><input class="z_login_user validate[required]" type="password" name="password">
                    </label>
                    <%-- <label for="" class="z_code_box">
                        <span class="z_login_in">验证码：</span><input class="z_enter_code" type="text" name="checkcode" id="inputaccount">
                        <div class="z_code"><img id="img" src="${ctx}/authImage" /></div>
                        <div class="z_nosee"><a href='#' onclick="javascript:changeImg()">看不清？换一张</a></div>
                    </label> --%>
                    <button type="button" class="z_login_submit" id="login">登 录</button>
                </form>
            </div>
        </div>
        <div class="z_arrow"></div>
    </div>
    <script>
	    var h=$(window).height()-120;
	    $(".z_login_content").css("height",h+'px')
	</script>
	<script >
    function changeImg(){
        var img = document.getElementById("img"); 
        img.src = "${ctx}/authImage?date=" + new Date();
    }
   </script>
