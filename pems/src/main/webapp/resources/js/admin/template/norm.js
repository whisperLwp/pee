$(function(){
	$("#save_btn").bind("click", Norm.saveData);
});
var Norm={
	//弹出编辑对话框
	showEdit : function(normId){
		//弹框方式
		/*$(".formError").remove();
		if(!normId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(normId);
		}
		$('#editModal').modal({keyboard: false});*/
		//页面跳转方式
		if(!normId) normId = "";
		document.location.href = ctx + "/admin/norm/editNorm?normId="+normId;
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
	fillData : function(normId){
		this.getData(normId, function(data){
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
    		url: ctx+"/admin/norm/saveNorm",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				//document.location.href = ctx + "/norm/normList";
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
	getData : function(normId, callback){
		$.ajax({
    		url: ctx+"/admin/norm/findNorm",
    		type: "POST",
    		async: true,
    		data: {"normId" : normId},
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
	deleteData : function (normId, delFlag) {
		$.ajax({
    		url: ctx+"/admin/norm/deleteNorm",
    		type: "POST",
    		async: true,
    		data: {"normId" : normId},
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