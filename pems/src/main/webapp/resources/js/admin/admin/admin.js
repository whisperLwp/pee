$(function(){
	$("#save_btn").bind("click", Admin.saveData);
});
var Admin={
	//弹出编辑对话框
	showEdit : function(adminId){
		//弹框方式
		/*$(".formError").remove();
		if(!adminId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(adminId);
		}
		$('#editModal').modal({keyboard: false});*/
		//页面跳转方式
		if(!adminId) adminId = "";
		document.location.href = ctx + "/admin/admin/editAdmin?adminId="+adminId;
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
	fillData : function(adminId){
		this.getData(adminId, function(data){
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
    		url: ctx+"/admin/admin/saveAdmin",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				//document.location.href = ctx + "/admin/adminList";
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
	getData : function(adminId, callback){
		$.ajax({
    		url: ctx+"/admin/admin/findAdmin",
    		type: "POST",
    		async: true,
    		data: {"adminId" : adminId},
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
	deleteData : function (adminId, delFlag) {
		$.ajax({
    		url: ctx+"/admin/admin/deleteAdmin",
    		type: "POST",
    		async: true,
    		data: {"adminId" : adminId},
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