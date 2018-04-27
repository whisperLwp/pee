<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
    .norm_category{
    	border: 1px solid #000;
    	padding: 10px 10px 0 10px;
    }
    .norm_category_head{
    	border-bottom: 1px solid #000;
    	padding: 10px 0;
    }
    .norm_category_body{
    	padding: 10px 40px;
    	padding: 10px 40px 0 40px;
    }
     .norm_item{
    	padding-top: 10px;
    }
    .norm_item_body{
    border-bottom: 1px solid #000;
    	padding: 10px 40px;
    }
    .norm_detail{
    	padding-bottom: 10px;
    }
    .z_btn{
    	margin-right:4px;
    }
    .z_input{
    	margin-right:4px;
    }
    .input_text{
            width: 780px;
            height: 28px;
    }
    .input_weight{
            width: 50px;
            height: 28px;
    }
    .title{
	    font-size: 16px;
	    font-weight: bold;
    }
    .warning{
       border:1px solid red;
       background: #fbf3e7;
    }
</style>
<script src="${ctx }/resources/js/admin/template/normTemplate.js"></script>
	 <form class="form-horizontal" role="form" id="addForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="normTempId" type="hidden" value="${normTemplate.normTempId}"></input>
		<div class="btn-group-sm z_margin_tb" style="border: 1px solid #ccc;background-color: #fbf3e7;padding-left: 10px;color:#777;">
			<h4>操作须知</h4>
			<p>
			<b class="z_common_star">*</b>1.您输入权重时请保证是1-100内整数<br>
			<b class="z_common_star">*</b>2.人力资源指标分类 + 岗位考核指标分类 + 项目考核指标分类 权重之和为100%<br>
			<b class="z_common_star">*</b>3.岗位自评+岗位上级评权重之和为100%<br>
			<b class="z_common_star">*</b>4.项目自评+项目上级评+项目互评权重之和为100%<br>
			<b class="z_common_star">*</b>5.自定义添加分类下考核指标的权重之和也为100%<br>
			<b class="z_common_star">*</b>6.考核指标明细是对考核指标的详细说明，可自定义添加多条<br>
			<b class="z_common_star">*</b>7.考核模板一旦被考核任务所引用，将不允许进行修改
			</p>
		</div>
		<div class="norm_category" style="padding-bottom: 10px;">
			<b class="z_common_star">*</b><span class="title">考核模板名称</span>
			<input class="validate[required]"  name="normTempName" value="${normTemplate.normTempName}" type="text"></input>
		</div>
		<!-- 人力资源分类 -->
		<div class="norm_category hr">
			<div class="norm_category_head">
				<button type="button" class="btn btn-success btn-sm glyphicon glyphicon-sort" onclick="showView(this)">收起</button>
				<input type="checkbox" checked="checked" id="hr_checkBox" name="hrFlag" value="人力资源指标分类"/><span class="title">人力资源指标分类</span>
				<input name="hrWeight" onblur="validate(this)" class="validate[required] input_weight categoryWeight hrWeight" type="text"/>%
				<button type="button" class="btn btn-success btn-sm glyphicon glyphicon-plus hraddBtn" onclick="addNormCategory(this)">添加考核指标 </button>
			</div>
			<div class="norm_category_body">
			</div>
		</div>
		
		<!-- 岗位分类 -->
		<div class="norm_category gw">
			<div class="norm_category_head">
				<button type="button" class="btn btn-success btn-sm glyphicon glyphicon-sort" onclick="showView(this)">收起</button>
				<input type="checkbox" checked="checked" name="gwFlag" value="岗位考核指标分类"/><span class="title">岗位考核指标分类</span>
				<input name="gwWeight" class="validate[required] input_weight categoryWeight" onblur="validate(this)" type="text"/>%
				<button type="button" class="btn btn-success btn-sm glyphicon glyphicon-plus" onclick="addNormCategory(this)">添加考核指标 </button><br><br>
			     <input type="checkbox" checked="checked" name="gwSelfFlag" value="gwSelfFlag"/>自评权重
			     <input type="text" name="gwSelfW" class="validate[required] input_weight g_weight" onblur="validate(this)"/>%&nbsp;&nbsp;&nbsp;&nbsp;
			     <input type="checkbox" checked="checked" name="gwLevelFlag" value="gwLeveFlag"/>上级评分权重
			     <input type="text" name="gwLevelW" onblur="validate(this)" class="validate[required] input_weight g_weight"/>%
			</div>
			<div class="norm_category_body">
			</div>
		</div>
		
		<!-- 项目分类 -->
		<div class="norm_category xm">
			<div class="norm_category_head">
				<!-- <button type="button" class="btn btn-success btn-sm" onclick="showView(this)">收起</button> -->
				<input type="checkbox" checked="checked" name="xmFlag" value="项目考核指标分类"><span class="title">项目考核指标分类</span>
				<input name="xmWeight" type="text" onblur="validate(this)" class="validate[required] input_weight categoryWeight"/>%<br><br>
				<!-- <button type="button" onclick="addNormCategory(this)">添加考核指标</button><br> -->
			     <input type="checkbox" checked="checked" name="xmSelfFlag" value="xmSelfFlag"/>自评权重
			     <input type="text" onblur="validate(this)" class="validate[required] input_weight x_weight" name="xmSelfW"/>%&nbsp;&nbsp;&nbsp;&nbsp;
			     <!-- <input type="checkbox" checked="checked" name="xmLevelFlag" value="xmLeveFlag"/>上级评分权重
			     <input type="text" onblur="validate(this)" class="validate[required] input_weight x_weight" name="xmLevelW"/>%&nbsp;&nbsp;&nbsp;&nbsp; -->
			     <input type="checkbox" checked="checked" name="xmEachFlag" value="xmEachFlag"/>互评权重
			     <input type="text" onblur="validate(this)" class="validate[required] input_weight x_weight" name="xmEachW"/>%
			</div>
			<div class="norm_category_body">
			</div>
		</div>
    <div class="modal-footer d_modal_footer">
		<div class="d_subject_button">
			<button type="button" class="btn btn-primary" id="addsave_btn">保存</button>
			<button type="button" class="btn btn-default " onclick="history.go(-1);">返回</button>
		</div>
	</div> 
		
	</form>
<script>
	// 添加考核指标
    function addNormCategory(obj){
    	
		var prefix = getCategory(obj);
		//alert(prefix);
    	$(obj).parent().next().show();
    	var NormCategorydiv = "<div class='norm_item'>"+
		"<div class='norm_item_head'><button type='button' class='btn btn-sm btn-success glyphicon glyphicon-sort' onclick='showView(this)'>收起"+
		"</button><input type='text' class='validate[required] z_input input_text' name='"+prefix+"NormText' placeholder='请填写考核指标标题'/>"+
		"<input name='"+prefix+"NormWeight'class='validate[required] z_input input_weight "+prefix+"detailWeight' onblur='validate(this)' type='text' placeholder='权重'/>%"+
		"<button type='button'class='glyphicon glyphicon-plus btn btn-sm btn-success' onclick='addNormDetail(this)'>添加考核指标明细 </button>"+
		"<button type='button' class='btn btn-sm btn-danger glyphicon glyphicon-trash' onclick='delNorm(this)'>删除 </button></div><div class='norm_item_body'>"+
		"</div><input type='hidden' name='"+prefix+"NormDetail' value='@'/></div>"
		$(obj).closest(".norm_category").find(".norm_category_body").append(NormCategorydiv);
    } 
	//  添加考评指标明细
    function addNormDetail(obj){
    	var prefix = getCategory(obj);
    	var normDetailDiv="<div class='norm_detail'><input name='"+prefix+"NormDetail' class='validate[required] z_input input_text' type='text' placeholder='请填写考核指标明细'/>"+
    	                  "<button type='button' class='btn btn-sm btn-danger glyphicon glyphicon-trash' onclick='delNormDetail(this)'>删除 </button></div>"
    	$(obj).closest(".norm_item").find(".norm_item_body").append(normDetailDiv);
    	
    }
	//删除考评指标明细
    function delNormDetail(obj){
    	$(obj).closest(".norm_detail").remove();
    }
	//删除考评指标
    function delNorm(obj){
    	$(obj).closest(".norm_item").remove();
    }
	//展开 收起  考核指标
    function showView(obj){
		if($(obj).parent().next().children().length > 0){
	    	if($(obj).text()=="收起"){
	    		//$(obj).closest(".norm_category").find(".norm_category_body").hide()
	    		$(obj).parent().next().hide();
	    		$(obj).text("展开");
	    	}else if($(obj).text()=="展开"){
	    		$(obj).parent().next().show();
	    		$(obj).text("收起");
	    	}
		}
    }
	//展开收起 考核指标明细
    /* function showDetail(obj){
		//alert($(obj).parent().next().length);
		if($(".norm_item_body .norm_detail").length > 0){
	    	if($(obj).text()=="收起"){
	    		//$(obj).closest(".norm_category").find(".norm_category_body").hide()
	    		$(obj).parent().next().hide();
	    		$(obj).text("展开");
	    	}else if($(obj).text()=="展开"){
	    		$(obj).parent().next().show();
	    		$(obj).text("收起");
	    	}
		}
     } */
     
     
	//获取类名  进行分类
	function getCategory(obj){
		var normCategory = $(obj).closest(".norm_category");
		if(normCategory.hasClass("hr")){
			return "hr";
		}else if(normCategory.hasClass("gw")){
			return "gw";
		}else if(normCategory.hasClass("xm")){
			return "xm";
		}
		
	}
    //校验输入的权重
    function validate(obj){
    	var oldWeight = $(obj).attr("value");
		var newWeight = $(obj).val();
		var reg = /(^[1-9][0-9]$|^[1-9]$|^100$)/;
		if(!reg.test(newWeight)){
		   //输入不合法  
		   layer.msg("请输入1—100的整数",{time:2000});
		   $(obj).val("");
		}
    }
    $("#hr_checkBox").click(function(){
    	if($(this).prop("checked")){
    		$(".hrWeight").show();
    		$(".hraddBtn").show();
    	}else{
    		$(".hrWeight").hide();
    		$(".hrWeight").val("0");
    		$(".hraddBtn").hide();
    	}
    });
</script>      
