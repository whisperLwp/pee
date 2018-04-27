<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html lang="en">
<head>
<title>宏程教育GEE绩效管理平台</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript" src="${ctx}/resources/js/module/main.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common/html2canvas.js"></script> 
<script src="${ctx}/resources/js/common/jquery-3.2.1.min.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/js/common/bootstrap.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/js/main.js"></script>
<link href="${ctx}/resources/css/common/bootstrap.min.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/css/common/style.css"
	type="text/css" rel="stylesheet" />

 <!-- 引用数据校验插件 -->
<link rel="stylesheet" href="${ctx}/resources/css/common/jquery-ui.css"> 
<link rel="stylesheet" href="${ctx}/resources/js/common/jQueryValidate/css/validationEngine.jquery.css">
<script type="text/javascript" src="${ctx}/resources/js/common/jQueryValidate/js/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common/jQueryValidate/js/jquery.validationEngine.js"></script> 
<script type="text/javascript" src="${ctx}/resources/js/common/jquery-ui.js"></script> 


<!-- 弹出框插件 -->
<script type="text/javascript" src="${ctx}/resources/js/common/layer/2.4/layer.js"></script>


<!--引入时间控件-->
<link rel="stylesheet" href="${ctx}/resources/css/common/jedate.css">
<script src="${ctx}/resources/js/common/jeDate/jquery.jedate.min.js"></script> 

<script>
	var ctx = "${ctx}";
</script>

</head>
<body>
<sitemesh:write property='body' />
</body>
</html>