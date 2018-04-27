$(function(){
	$("#save_btn").bind("click", Station.saveData);
});
var Station={
	//弹出编辑对话框
	showEdit : function(stationId){
		//弹框方式
		/*$(".formError").remove();
		if(!stationId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(stationId);
		}
		$('#editModal').modal({keyboard: false});*/
		//页面跳转方式
		if(!stationId) stationId = "";
		document.location.href = ctx + "/admin/station/editStation?stationId="+stationId;
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
	fillData : function(stationId){
		this.getData(stationId, function(data){
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
    		url: ctx+"/admin/station/saveStation",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				document.location.href = ctx + "/admin/station/stationList";
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
	getData : function(stationId, callback){
		$.ajax({
    		url: ctx+"/admin/station/findStation",
    		type: "POST",
    		async: true,
    		data: {"stationId" : stationId},
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
	deleteData : function (stationId, delFlag) {
		Dialog.showConfirm(function() {
		$.ajax({
    		url: ctx+"/admin/station/deleteStation",
    		type: "POST",
    		async: true,
    		data: {"stationId" : stationId},
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