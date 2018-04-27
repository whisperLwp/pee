$(function(){
	$("#save_btn").bind("click", TaskStationTemplate.saveData);
});
var TaskStationTemplate={
	//弹出编辑对话框
	showEdit : function(taskStationTemplateId){
		//弹框方式
		$(".formError").remove();
		if(!taskStationTemplateId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(taskStationTemplateId);
		}
		$('#editModal').modal({keyboard: false});
		//页面跳转方式
		//if(!taskStationTemplateId) taskStationTemplateId = "";
		//document.location.href = ctx + "/taskStationTemplate/editTaskStationTemplate?taskStationTemplateId="+taskStationTemplateId;
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
	fillData : function(taskStationTemplateId){
		this.getData(taskStationTemplateId, function(data){
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
    		url: ctx+"/admin/taskStationTemplate/saveTaskStationTemplate",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				//document.location.href = ctx + "/taskStationTemplate/taskStationTemplateList";
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
	getData : function(taskStationTemplateId, callback){
		$.ajax({
    		url: ctx+"/admin/taskStationTemplate/findTaskStationTemplate",
    		type: "POST",
    		async: true,
    		data: {"taskStationTemplateId" : taskStationTemplateId},
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
	deleteData : function (taskStationTemplateId, delFlag) {
		$.ajax({
    		url: ctx+"/admin/taskStationTemplate/deleteTaskStationTemplate",
    		type: "POST",
    		async: true,
    		data: {"taskStationTemplateId" : taskStationTemplateId},
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