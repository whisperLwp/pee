$(function(){
	$("#save_btn").bind("click", ProjectEmployee.saveData);
	$("#editSave_btn").bind("click", ProjectEmployee.editSaveData);
	
//列表复选框-全选事件
    
    $(document).on("click","#theadInput",function(){
		 $("tbody input:checkbox").prop("checked", $(this).prop('checked'));
	    })
	    $("tbody input:checkbox").on('click', function() {
	        //当选中的长度等于checkbox的长度的时候,就让控制全选反选的checkbox设置为选中,否则就为未选中
	        if($("tbody input:checkbox").length === $("tbody input:checked").length) {
	            $("#theadInput").prop("checked", true);
	        } else {
	            $("#theadInput").prop("checked", false);
	        }
	    })
	
});
var ProjectEmployee={
	//弹出编辑对话框
	addProEmp : function(projectId){
		//弹框方式
		$(".formError").remove();
		if(projectId){
			this.getData(projectId);
		}
		
		$('#editModal').modal({keyboard: false});
		//页面跳转方式
		//if(!projectId) projectId = "";
		//document.location.href = ctx + "/projectEmployee/editProjectEmployee?projectId="+projectId;
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
	
	//跳转项目成员页面
	addEmployee : function(projectId){
    	if(!projectId) projectId = "";
		document.location.href = ctx + "/admin/projectEmployee/ProEmpList?projectId="+projectId;
	},
	
	//保存数据
	saveData : function(){
		if(!$("form#editForm").validationEngine("validate")) return ;  
		$.ajax({
    		url: ctx+"/admin/projectEmployee/saveProjectEmployee",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				//document.location.reload();
    				document.location.href = ctx + "/admin/projectEmployee/projectEmployeeList";
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
		var deptCode=$("#dept").val();
		$.ajax({
    		url: ctx+"/admin/projectEmployee/EmpForPro",
    		type: "POST",
    		async: true,
    		data: {
    			"projectId" : projectId,
    			"deptCode"  : deptCode
    		},
    		dataType:"json",
    		success: function(data){
    			//alert(JSON.stringify( data ));
    			//alert(data.data.employeeList);
    			var projectId="<input type='hidden' name='projectId'  value='"+data.data.projectId+"'>"
    			$("#c_expertId").append(projectId);
    			var array = [];
    			var i=0;
    			array.push("<thead><tr><th><input type='checkbox' id='theadInput' /></th><th>序号</th><th>员工名称</th></tr></thead>")
    			$(data.data.employeeList).each(function(){
    				i++;
    				array.push("<tbody><tr><td><input type='checkbox' name='employeeId' value='"+this.employeeId+"'/></td><td>"+i+"</td><td>");
    				array.push(this.realName);
    				array.push("</td></tr></tbody>")
    			});
    			$("#dept").html("");
    			$("#dept").append("<option value=''>--请选部门--</option>")
    			$(data.data.deptList).each(function(){
    				if(this.deptCode==data.data.deptCode){
    					$("#dept").append("<option value='"+this.deptCode+"' selected='selected'>"+this.deptName+"</option>");
    				}else{
    					$("#dept").append("<option value='"+this.deptCode+"'>"+this.deptName+"</option>");
    				}
					$(this.list).each(function(){
						if(this.deptCode==data.data.deptCode){
							$("#dept").append("<option value='"+this.deptCode+"' selected='selected'>&nbsp;&nbsp;&nbsp;&nbsp;"+this.deptName+"</option>");
						}else{
							$("#dept").append("<option value='"+this.deptCode+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+this.deptName+"</option>");
						}
					})
    			});
    			$("#t_cbox").html("");
   			    $("#t_cbox").append(array.join(""));
    		},
    		error: function(){
    		}
    	});
	},
	//删除数据
	deleteData : function (projectId, delFlag) {
        Dialog.showConfirm(function() {
        	$.ajax({
        		url: ctx+"/admin/projectEmployee/deleteProjectEmployee",
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
	},
	//保存编辑
	editSaveData : function(){
		/*alert($("#addForm").serialize());*/
		$.ajax({
			url: ctx+"/admin/projectEmployee/saveProEmp",
			type: "POST",
			async: true,
			data: $("#addForm").serialize(),
			success: function(data){
				if(data.errorCode =="000000"){
					Dialog.showSuccess("保存成功");
					document.location.reload();
				}
				else{
					Dialog.showError(data.errorMessage);
				}
			},
			error: function(){
				//Dialog.showError(data.errorMessage);
			}
		});
	},
}