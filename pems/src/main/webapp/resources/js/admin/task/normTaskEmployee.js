$(function(){
	$(".save_btn").bind("click", NormTaskEmployee.saveData);
	//$("#save_btn").bind("click", NormTaskEmployee.saveOneData);
});
var NormTaskEmployee={
	//弹出编辑对话框
	showEdit : function(normTaskEmployeeId){
		//弹框方式
		$(".formError").remove();
		if(!normTaskEmployeeId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(normTaskEmployeeId);
		}
		$('#editModal').modal({keyboard: false});
		//页面跳转方式
		//if(!normTaskEmployeeId) normTaskEmployeeId = "";
		//document.location.href = ctx + "/normTaskEmployee/editNormTaskEmployee?normTaskEmployeeId="+normTaskEmployeeId;
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
	fillData : function(normTaskEmployeeId){
		this.getData(normTaskEmployeeId, function(data){
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
		//alert($("#editForm").serialize());
		$.ajax({
    		url: ctx+"/admin/normTaskEmployee/saveNormTaskEmployee",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				//alert(JSON.stringify( data.data ));
    				Dialog.showSuccess("保存成功");
    				//document.location.reload();
    				document.location.href = ctx + "/admin/normTaskEmployeeDetail/normTaskEmployeeDetailList?normTaskId="+data.data;
    			}
    			else{
    				Dialog.showError(data.errorMessage);
    			}
    		},
    		error: function(){
    		}
    	});
	},	
	//保存数据
	saveOneData : function(){
		if(!$("form#editForm").validationEngine("validate")) return ;  
		//alert($("#editForm").serialize());
		$.ajax({
			url: ctx+"/admin/normTaskEmployee/saveScore",
			type: "POST",
			async: true,
			data: $("#editForm").serialize(),
			success: function(data){
				if(data.errorCode =="000000"){
					Dialog.showSuccess("保存成功");
					document.location.href = ctx + "/admin/normTaskEmployee/normTaskEmployeeList";
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
	getData : function(normTaskEmployeeId, callback){
		$.ajax({
    		url: ctx+"/admin/normTaskEmployee/findNormTaskEmployee",
    		type: "POST",
    		async: true,
    		data: {"normTaskEmployeeId" : normTaskEmployeeId},
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
	deleteData : function (normTaskEmployeeId, delFlag) {
        Dialog.showConfirm(function() {
        	$.ajax({
        		url: ctx+"/admin/normTaskEmployee/deleteNormTaskEmployee",
        		type: "POST",
        		async: true,
        		data: {"normTaskEmployeeId" : normTaskEmployeeId},
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
	},
	
	
}
//检验输入的值
function validate(obj){
	var oldScore = $(obj).attr("value");
	var newScore = $(obj).val();
	var reg = /^([0-9]|10)$/;
	if(!reg.test(newScore)){
	   //输入不合法  
	   layer.msg("请输入0—10的整数",{time:1000});
	   $(obj).val("");
	}
	
}