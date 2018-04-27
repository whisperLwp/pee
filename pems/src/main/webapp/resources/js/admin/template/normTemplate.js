$(function(){
	$("#addsave_btn").bind("click", NormTemplate.saveData);
	$("#editsave_btn").bind("click", NormTemplate.editData);
});
var flag=true;
var NormTemplate={
	
	//新增模板
	add : function(normTempId){
		//页面跳转方式
		if(!normTempId) normTempId = "";
		document.location.href = ctx + "/admin/normTemplate/addNormTemplate?normTempId="+normTempId;
	},	
	//编辑模板
	edit : function(normTempId,type){
		//页面跳转方式
		if(!normTempId) normTempId = "";
		document.location.href = ctx + "/admin/normTemplate/editNormTemplate?normTempId="+normTempId+"&type="+type;
	},	
	//清除弹出框数据
	clearData : function(){
		$("#editForm select").each(function() {
			$(this).find("option:first").prop("selected", 'selected');
		})
		$("#editForm input").each(function() {
			$(this).val("");
		})
	},
	//填充弹出框数据
	fillData : function(normTempId){
		this.getData(normTempId, function(data){
			for (dataAttr in data)  
			  {  
			   	 if($("#editForm [name="+dataAttr+"]")){
			   		$("#editForm [name="+dataAttr+"]").val(data[dataAttr]);
			   	 }
			  } 
		});
	},
	//保存数据
	saveData : function(){
		var validFlag = true;
		//考核指标分类权重之和不等于100%
		
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".categoryWeight"), "考核指标分类权重总和不等于100%");
		//人力考核指标分类明细权重总和不等于100%
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".hrdetailWeight"), "hr权重明细总和不等于100%");
		
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".g_weight"), "岗位权重总和不等于100%");
		
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".gwdetailWeight"), "岗位权重明细总和不等于100%");
		
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".x_weight"), "项目权重不等于100%");
		
		
		if(!$("form#addForm").validationEngine("validate")) return ;  
		if(validFlag){
			if(flag){
				flag=false;
				$.ajax({
					url: ctx+"/admin/normTemplate/saveNormTemplate",
					type: "POST",
					async: true,
					data: $("#addForm").serialize(),
					success: function(data){
						if(data.errorCode =="000000"){
							Dialog.showSuccess("保存成功");
							document.location.reload();
							document.location.href = ctx + "/admin/normTemplate/normTemplateList";
						}
						else{
							Dialog.showError(data.errorMessage);
						}
					},
					error: function(){
						flag=true;
					}
				});
			}else{
				layer.msg("请耐心等待");
			}
		}else{
			layer.msg("你输入的权重有误");
			return false;
		}
	},	
	//修改数据
	editData : function(){
		
		//alert($("#addForm").serialize());
		$("#save_btn").attr("disabled", true); 
		
		var validFlag = true;
		//考核指标分类权重之和不等于100%
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".categoryWeight"), "考核指标分类权重总和不等于100%");
		//人力考核指标分类明细权重总和不等于100%
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".hrdetailWeight"), "hr权重明细总和不等于100%");
		
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".g_weight"), "岗位权重总和不等于100%");
		
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".gwdetailWeight"), "岗位权重明细总和不等于100%");
		
		if(validFlag) validFlag = NormTemplate.valdateSumWeight($(".x_weight"), "项目权重不等于100%");
		
		if(!$("form#editForm").validationEngine("validate")) return ;  
		//alert($("#editForm").serialize());
		if(validFlag){
			if(flag){
				flag=false;
				$.ajax({
					url: ctx+"/admin/normTemplate/saveNormTemplate",
					type: "POST",
					async: true,
					data: $("#editForm").serialize(),
					success: function(data){
						if(data.errorCode =="000000"){
							Dialog.showSuccess("保存成功");
							document.location.reload();
							document.location.href = ctx + "/admin/normTemplate/normTemplateList";
						}
						else{
							Dialog.showError(data.errorMessage);
						}
					},
					error: function(){
						flag=true;
					}
				});
			}else{
				layer.msg("正在保存，请耐心等待",{time:2000});
				return false;
			}
		}else{
			layer.msg("你输入的权重有误",{time:2000});
			return false;
		}
	},

	valdateSumWeight : function(defTag, message){
		defTag.removeClass("warning");
		var sum = 0;
		defTag.each(function(){
			var val = $(this).val();
			sum += parseInt(val);
		})
		if(sum!=100 && sum!=0 ){
			layer.msg(message)
			defTag.addClass("warning");
			return false;
		}else{
			return true;
		}
	},
	//获取数据
	getData : function(normTempId, callback){
		$.ajax({
    		url: ctx+"/admin/normTemplate/findNormTemplate",
    		type: "POST",
    		async: true,
    		data: {"normTempId" : normTempId},
    		success: function(data){
    			if(data.errorCode =="000000"){
    				callback(data.data);
    				return;
    			}
    			else{
    				Dialog.showError(data.errorMessage);
    			}
    		},
    		error: function(){
    		}
    	});
	},
	//删除数据
	deleteData : function (normTempId, delFlag) {
		Dialog.showConfirm(function() {
			$.ajax({
				url: ctx+"/admin/normTemplate/deleteNormTemplate",
				type: "POST",
				async: true,
				data: {"normTempId" : normTempId},
				success: function(data){
					if(data.errorCode =="000000"){
						Dialog.showSuccess("操作成功");
						document.location.reload();
					}
					else{
						Dialog.showError(data.errorMessage);
					}
				},
				error: function(){
				}
			});
		});
	}
}