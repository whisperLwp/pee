$(function(){
	$("#save_btn").bind("click", NormTaskProject.saveData);
	$("#editSave_btn").bind("click", NormTaskProject.editSaveData);
	$("#saveContent_btn").bind("click", NormTaskProject.saveContent);
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
var NormTaskProject={
	//弹出添加互评人员对话框
		showEachEmployee : function(projectId,employeeId,normTaskId,normTaskProjectId,flag){
		//弹框方式
		if(flag==0){
			$(".s_btn").hide();
		}
		$(".formError").remove();
		this.getProjectEmployee(projectId,employeeId,normTaskId,normTaskProjectId);
		$('#editModal').modal({keyboard: false});
		//页面跳转方式
		//if(!normTaskProjectId) normTaskProjectId = "";
		//document.location.href = ctx + "/normTaskProject/editNormTaskProject?normTaskProjectId="+normTaskProjectId;
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
	fillData : function(normTaskProjectId){
		this.getData(normTaskProjectId, function(data){
			for (dataAttr in data)  
			  {  
			   	 if($("#editForm [name="+dataAttr+"]")){
			   		$("#editForm [name="+dataAttr+"]").val(data[dataAttr]);
			   	 }
			  } 
		});
	},
	//保存数据 互评人员
	saveData : function(){
		$("#associationSelect option").attr("selected","selected");
		//alert($("#editForm").serialize());
		$.ajax({
    		url: ctx+"/admin/taskProjectEmployee/saveEachEmployee",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				//document.location.href = ctx + "/normTaskProject/normTaskProjectList";
    			}
    			else{
    				Dialog.showError(data.errorMessage);
    			}
    		},
    		error: function(){
    		}
    	});
	},
	//保存编辑工作内容数据
	saveContent : function(){
		if(!$("form#contentForm").validationEngine("validate")) return ;
		var sum=new Number();
		$(".s_weight2").each(function(){
			var a=$(this).val();
			var i=new Number($(this).val());
			sum+=i;
		})
		if(sum!=100){
			alert("请保持选择项目权重之和为100")
			return false;
		}
		$.ajax({
			url: ctx+"/admin/normTaskProject/saveContent",
			type: "POST",
			async: true,
			data: $("#contentForm").serialize(),
			success: function(data){
				if(data.errorCode =="000000"){
					layer.msg("请给工作内容选择互评人员，如果不选择，您此处互评分为0");
					Dialog.showSuccess("保存成功");
					document.location.reload();
				}
				else{
					Dialog.showError(data.errorMessage);
				}
			},
			error: function(){
			}
		});
	},
	
	//保存项目
	editSaveData : function(){
		if(!$("form#addProForm").validationEngine("validate")) return ;
		if($("#project").val()==-1){
			alert("请选择考核项目");
			return;
		}
		var sum=new Number();
		$(".s_weight2").each(function(){
			var a=$(this).val();
			var i=new Number($(this).val());
			sum+=i;
		})
		if(sum!=100){
			alert("请保持选择项目权重之和为100")
			return false;
		}
		$.ajax({
			url: ctx+"/admin/normTaskProject/saveProjectForEmp",
			type: "POST",
			async: true,
			data: $("#addProForm").serialize(),
			success: function(data){
				if(data.errorCode =="000000"){
					alert("接下来，请您给工作内容选择互评人员，如果不选择，系统将默认互评分为0");
					document.location.reload();
					//document.location.href = ctx + "/normTaskProject/normTaskProjectList";
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
	getData : function(normTaskProjectId, callback){
		$.ajax({
    		url: ctx+"/admin/normTaskProject/findNormTaskProject",
    		type: "POST",
    		async: true,
    		data: {"normTaskProjectId" : normTaskProjectId},
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
	deleteData : function (normTaskProjectId,employeeId,normTaskId,projectId, normTaskTmployeeDetailId,delFlag) {
        alert(normTaskTmployeeDetailId);
		Dialog.showConfirm(function() {
        	$.ajax({
        		url: ctx+"/admin/normTaskProject/deleteEachProject",
        		type: "POST",
        		async: true,
        		data: {"normTaskProjectId" : normTaskProjectId,
        			"employeeId" : employeeId,
        			"normTaskId" : normTaskId,
        			"projectId" : projectId,
        		},
        		success: function(data){
        			if(data.errorCode =="000000"){
        				Dialog.showSuccess("操作成功");
        				document.location.href = ctx + "/admin/normTaskProject/normTaskProjectList?normTaskTmployeeDetailId="+normTaskTmployeeDetailId+"&normTaskId="+normTaskId+"&employeeId="+employeeId;
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
	//选择考评项目  弹出框
	chooseProject : function (normTaskId,employeeId) {
		//alert("123");
		$(".formError").remove();
		$(".workContent").html("");
		$("#project").empty();
		$("#project").append("<option value='-1'>请选择项目</option>");
		this.getProject(normTaskId,employeeId);
		$('#chooseProject').modal({keyboard: false});
	},
	getProject : function (normTaskId,employeeId){
		$.ajax({
    		url: ctx+"/admin/normTaskProject/projectForNormTask",
    		type: "POST",
    		async: true,
    		data :{
    			"normTaskId" : normTaskId,
    			"employeeId" : employeeId
    		},
    		dataType:"json",
    		success: function(data){
    			//alert(JSON.stringify( data ));
    			var normTaskId="<input type='hidden' name='normTaskId'  value='"+data.data.normTaskId+"'>"+
    			"<input type='hidden' name='employeeId'  value='"+data.data.employeeId+"'>"
    			$("#c_projectId").append(normTaskId);
    			$(data.data.projectList).each(function(){
    				$("#project").append("<option value='"+this.projectId+"'>"+this.projectName+"</option>");
    			});
    			
    			
    			/*var array = [];
    			var i=0;
    			array.push("<thead><tr><th><input type='checkbox' id='theadInput' /></th><th>序号</th><th>项目名称</th></tr></thead>")
    			$(data.data.projectList).each(function(){
    				i++;
    				array.push("<tbody><tr><td><input type='checkbox' name='projectId' value='"+this.projectId+"'/></td><td>"+i+"</td><td>");
    				array.push(this.projectName);
    				array.push("</td></tr></tbody>")
    			});
    			$("#t_pbox").html("");
   			    $("#t_pbox").append(array.join(""));*/
    		},
    		error: function(){
    		}
    	});
	},
	
	//获取项目人员
	getProjectEmployee : function (projectId,employeeId,normTaskId,normTaskProjectId) {
		//alert(normTaskProjectId);
		$("#noassociationSelect").html("")
		$("#associationSelect").html("")
		$.ajax({
    		url: ctx+"/admin/taskProjectEmployee/eachEmployee",
    		type: "POST",
    		async: true,
    		data :{
    			"normTaskId" : normTaskId,
    			"employeeId" : employeeId,
    			"projectId" : projectId,
    			"normTaskProjectId" : normTaskProjectId,
    		},
    		dataType:"json",
    		success: function(data){
    			//alert(JSON.stringify( data ));
                $("#normTaskProjectId").val(data.data.normTaskProjectId);
                $("#employeeId").val(data.data.employeeId);
                $("#normTaskId").val(data.data.normTaskId);
                $("#projectId").val(data.data.projectId);
    			//没有添加互评人员
                $(data.data.noEmployeeList).each(function(){
                	if(this.employeeId!=data.data.employeeId){//判断  不显示自己
                		$("#noassociationSelect").append("<option value='"+this.employeeId+"'>"+this.realName+"</option>");
                	}
    			})
    			//已经添加互评人员
    			$(data.data.hasEmployeeList).each(function(){
    				$("#associationSelect").append("<option value='"+this.employee.employeeId+"'>"+this.employee.realName+"</option>");
    			})
    			
    		},
    		error: function(){
    		}
    	});
	},
	//保存权重  并校验相加之和为100
	editWeight  : function() {
		//alert( $("#editWeightForm").serialize());
		var sum=new Number();
		$(".s_weight").each(function(){
			var i=new Number($(this).val());
			sum+=i;
		})
		if(sum!=100){
			alert("请保持选择项目权重之和为100")
			return false;
		}
		$.ajax({
			url: ctx+"/admin/normTaskProject/saveWeight",
			type: "POST",
			async: true,
			data: $("#editWeightForm").serialize(),
			success: function(data){
				if(data.errorCode =="000000"){
					Dialog.showSuccess("保存成功");
					document.location.reload();
					//document.location.href = ctx + "/normTaskProject/normTaskProjectList";
				}
				else{
					Dialog.showError(data.errorMessage);
				}
			},
			error: function(){
			}
		});
	},
	//编辑工作内容 弹出框
	editData : function (projectId,employeeId,normTaskId) {
		//alert("123");
		$(".formError").remove();
		$(".editWorkContent").html("");
		$("#project").empty();
		this.getContent(projectId,employeeId,normTaskId);
		$('#editContent').modal({keyboard: false});
	},
	getContent : function(projectId,employeeId,normTaskId) {
		$("#s_projectName").html("");
		$.ajax({
			url: ctx+"/admin/normTaskProject/editContent",
			type: "POST",
			async: true,
			data : {
				"projectId" : projectId,
				"employeeId" : employeeId,
				"normTaskId" : normTaskId
			},
			success: function(data){
				var normTaskId="<input type='hidden' name='normTaskId'  value='"+data.data.normTaskId+"'>"+
    			"<input type='hidden' name='employeeId'  value='"+data.data.employeeId+"'>"+
    			"<input type='hidden' name='projectId'  value='"+data.data.projectId+"'>"
    			$("#s_projectId").append(normTaskId);
				var array = [];
				var projectName="";
				$(data.data.project).each(function(){
					projectName = this.project.projectName;
				})
				$(data.data.normTaskProjects).each(function(){
					array.push("<div class='content'><input type='hidden' id='normTaskProjectId' name='normTaskProjectId' value='"+this.normTaskProjectId+"'/><input class='input-sm col-md-7' type='text' name='workContent' value='"+this.workContent+"'/>");
				    array.push("<input type='text' class='s_weight2 input-sm  col-md-2' name='weight2' value='"+this.weight2+"' placeholder='请填写权重'/>%");
				    if(this.referFlag!=1){
				    array.push("<button class='btn btn-sm btn-danger glyphicon glyphicon-trash col-md-2 d_deleteBtn' type='button' onclick='del(this)' style='padding-bottom:3px;'>删除 </button></div>");
				    } 
				});
				$("#s_projectName").append("修改工作内容《"+projectName+"》");
				$(".editWorkContent").append(array.join(""));

			},
			error: function(){
			}
		});
	},
	pass : function(normTaskTmployeeDetailId,normTaskId,employeeId){
		Dialog.showConfirm1 (
			function() {//通过审核
				NormTaskProject.ispass(normTaskTmployeeDetailId,normTaskId,employeeId,1);
			},
			function() {//未通过审核
				NormTaskProject.ispass(normTaskTmployeeDetailId,normTaskId,employeeId,2);
			}
		);
	},
	ispass : function(normTaskTmployeeDetailId,normTaskId,employeeId,passFlag){
		$.ajax({
			url: ctx+"/admin/normTaskEmployeeDetail/pass",
			type: "POST",
			async: true,
			data : {
				"normTaskTmployeeDetailId" : normTaskTmployeeDetailId,
				"normTaskId" : normTaskId,
				"employeeId" : employeeId,
				"passFlag" : passFlag
			},
			success: function(data){
				Dialog.showSuccess("操作成功");
				document.location.href = ctx + "/admin/normTaskEmployeeDetail/normTaskEmployeeDetailList?normTaskId="+normTaskId;
			},
			error: function(){
			}
		});
	}
	
}