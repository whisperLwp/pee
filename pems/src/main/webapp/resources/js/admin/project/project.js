$(function(){
	$("#save_btn").bind("click", Project.saveData);
});
var Project={
	//弹出编辑对话框
	showEdit : function(projectId){
		//弹框方式
		/*$(".formError").remove();
		if(!projectId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(projectId);
		}
		$('#editModal').modal({keyboard: false});*/
		//页面跳转方式
		if(!projectId) projectId = "";
		document.location.href = ctx + "/admin/project/editProject?projectId="+projectId;
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
	fillData : function(projectId){
		this.getData(projectId, function(data){
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
    		url: ctx+"/admin/project/saveProject",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				document.location.href = ctx + "/admin/project/projectList";
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
	getData : function(projectId, callback){
		$.ajax({
    		url: ctx+"/admin/project/findProject",
    		type: "POST",
    		async: true,
    		data: {"projectId" : projectId},
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
	deleteData : function (projectId, delFlag) {
        Dialog.showConfirm(function() {
        	$.ajax({
        		url: ctx+"/admin/project/deleteProject",
        		type: "POST",
        		async: true,
        		data: {"projectId" : projectId},
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