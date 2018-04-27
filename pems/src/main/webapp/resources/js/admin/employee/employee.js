$(function(){
	$("#save_btn").bind("click", Employee.saveData);
	
	//获取部门负责人
	$(".s_dept").click(function(){
		var deptCode = $(this).val();
		var employeeId=$("#employeeId").val();
		var roleId = $('input[name="roleId"]:checked' ).val();
		$(".s_level").html("");
		if(deptCode!=-1){
		
			$.ajax({
	    		url: ctx+"/admin/employee/findLevel",
	    		type: "POST",
	    		async: true,
	    		data: {"deptId" : deptCode,
	    			   "employeeId" : employeeId,
	    			   "roleId" : roleId
	    		},
	    		success: function(data){
	    			if(data.errorCode =="000000"){
	    				var array = [];
	    				array.push("")
	    				$(".s_level").html("<input type='hidden' name = 'levelEmployeeId'  readonly = 'true' value = '"+data.data.employee.employeeId+"' type='text' >"+data.data.employee.realName+"</input>");
	    				$(".s_level").append(array.join(""));
	    			}
	    			else{
	    				Dialog.showError(data.errorMessage);
	    			}
	    		},
	    		error: function(){
	    		}
	    	});
		
		}
	});
	
	
});
var Employee={
	//弹出编辑对话框
	showEdit : function(employeeId){
		//弹框方式
		/*$(".formError").remove();
		if(!employeeId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(employeeId);
		}
		$('#editModal').modal({keyboard: false});*/
		//页面跳转方式
		if(!employeeId) employeeId = "";
		document.location.href = ctx + "/admin/employee/editEmployee?employeeId="+employeeId;
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
	fillData : function(employeeId){
		this.getData(employeeId, function(data){
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
    		url: ctx+"/admin/employee/saveEmployee",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				//document.location.reload();
    				document.location.href = ctx + "/admin/employee/employeeList";
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
	getData : function(employeeId, callback){
		$.ajax({
    		url: ctx+"/admin/employee/findEmployee",
    		type: "POST",
    		async: true,
    		data: {"employeeId" : employeeId},
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
	deleteData : function (employeeId, delFlag) {
		Dialog.showConfirm(function() {
			$.ajax({
				url: ctx+"/admin/employee/deleteEmployee",
				type: "POST",
				async: true,
				data: {"employeeId" : employeeId},
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
	
	//重置密码
	reset : function(employeeId){
		layer.confirm("确认重置此人密码吗?重置密码为000000", {btn: ["确定","取消"]},function(){
			$.ajax({
				url: ctx+"/admin/employee/reset/"+employeeId,
				type: "GET",
				async: false,
				success : function(data){
					if(data.errorCode =="000000"){
						layer.msg("重置密码成功",{time:1000});
						window.location.href=ctx+"/admin/employee/employeeList"
					}
				},
				error: function(){
					return false;
				}
			});
		});
	},
	
}