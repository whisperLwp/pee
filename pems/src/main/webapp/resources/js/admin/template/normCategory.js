$(function(){
	$("#save_btn").bind("click", NormCategory.saveData);
});
var NormCategory={
	//弹出编辑对话框
	showEdit : function(normCategoryId){
		//弹框方式
		$(".formError").remove();
		if(!normCategoryId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(normCategoryId);
		}
		$('#editModal').modal({keyboard: false});
		//页面跳转方式
		//if(!normCategoryId) normCategoryId = "";
		//document.location.href = ctx + "/normCategory/editNormCategory?normCategoryId="+normCategoryId;
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
	fillData : function(normCategoryId){
		this.getData(normCategoryId, function(data){
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
		if(!$("form#editForm").validationEngine("validate")) return ;  
		$.ajax({
    		url: ctx+"/admin/normCategory/saveNormCategory",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				//document.location.href = ctx + "/normCategory/normCategoryList";
    			}
    			else{
    				Dialog.showError(data.errorMessage);
    			}
    		},
    		error: function(){
    		}
    	});
	},	
	//获取数据
	getData : function(normCategoryId, callback){
		$.ajax({
    		url: ctx+"/admin/normCategory/findNormCategory",
    		type: "POST",
    		async: true,
    		data: {"normCategoryId" : normCategoryId},
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
	deleteData : function (normCategoryId, delFlag) {
		$.ajax({
    		url: ctx+"/admin/normCategory/deleteNormCategory",
    		type: "POST",
    		async: true,
    		data: {"normCategoryId" : normCategoryId},
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
	}
}