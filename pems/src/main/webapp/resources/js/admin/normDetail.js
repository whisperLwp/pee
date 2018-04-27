$(function(){
	$("#save_btn").bind("click", NormDetail.saveData);
});
var NormDetail={
	//弹出编辑对话框
	showEdit : function(normDetailId){
		//弹框方式
		$(".formError").remove();
		if(!normDetailId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(normDetailId);
		}
		$('#editModal').modal({keyboard: false});
		//页面跳转方式
		//if(!normDetailId) normDetailId = "";
		//document.location.href = ctx + "/normDetail/editNormDetail?normDetailId="+normDetailId;
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
	fillData : function(normDetailId){
		this.getData(normDetailId, function(data){
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
    		url: ctx+"/admin/normDetail/saveNormDetail",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				//document.location.href = ctx + "/normDetail/normDetailList";
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
	getData : function(normDetailId, callback){
		$.ajax({
    		url: ctx+"/admin/normDetail/findNormDetail",
    		type: "POST",
    		async: true,
    		data: {"normDetailId" : normDetailId},
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
	deleteData : function (normDetailId, delFlag) {
		$.ajax({
    		url: ctx+"/admin/normDetail/deleteNormDetail",
    		type: "POST",
    		async: true,
    		data: {"normDetailId" : normDetailId},
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