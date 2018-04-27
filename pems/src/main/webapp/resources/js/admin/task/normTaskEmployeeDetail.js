$(function(){
	$("#save_btn").bind("click", NormTaskEmployeeDetail.saveData);
	$("#editSave_btn").bind("click", NormTaskEmployeeDetail.editSaveData);
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
var NormTaskEmployeeDetail={
	//弹出编辑对话框
	showEdit : function(normTaskTmployeeDetailId){
		//弹框方式
		$(".formError").remove();
		if(!normTaskTmployeeDetailId){//新增
			this.clearData();
		}
		else{//修改
			this.fillData(normTaskTmployeeDetailId);
		}
		$('#editModal').modal({keyboard: false});
		//页面跳转方式
		//if(!normTaskTmployeeDetailId) normTaskTmployeeDetailId = "";
		//document.location.href = ctx + "/normTaskEmployeeDetail/editNormTaskEmployeeDetail?normTaskTmployeeDetailId="+normTaskTmployeeDetailId;
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
	fillData : function(normTaskTmployeeDetailId){
		this.getData(normTaskTmployeeDetailId, function(data){
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
    		url: ctx+"/admin/normTaskEmployeeDetail/saveNormTaskEmployeeDetail",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				//document.location.href = ctx + "/normTaskEmployeeDetail/normTaskEmployeeDetailList";
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
	getData : function(normTaskTmployeeDetailId, callback){
		$.ajax({
    		url: ctx+"/admin/normTaskEmployeeDetail/findNormTaskEmployeeDetail",
    		type: "POST",
    		async: true,
    		data: {"normTaskTmployeeDetailId" : normTaskTmployeeDetailId},
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
	deleteData : function (normTaskTmployeeDetailId,employeeId,normTaskId, delFlag) {
        Dialog.showConfirm(function() {
        	$.ajax({
        		url: ctx+"/admin/normTaskEmployeeDetail/deleteNormTaskEmployeeDetail",
        		type: "POST",
        		async: true,
        		data: {
        			"normTaskTmployeeDetailId" : normTaskTmployeeDetailId,
        			"employeeId" : employeeId,
        			"normTaskId" : normTaskId
        		},
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
	
	//添加考评人员  弹出框
	addEmpl : function (normTaskId) {
		$(".formError").remove();
		this.getEmpl(normTaskId);
		$('#editModal').modal({keyboard: false});
	},
	//查询员工
	getEmpl : function(normTaskId ,callback){
		var deptCode = $("#dept").val();
		$.ajax({
    		url: ctx+"/admin/normTaskEmployeeDetail/EmpForNormTask",
    		type: "POST",
    		async: true,
    		data: {
    			"normTaskId" : normTaskId,
    			"deptCode" : deptCode,
    		},
    		dataType:"json",
    		success: function(data){
    			//alert(JSON.stringify( data ));
    			//alert(data.data.employeeList);
    			var normTaskId="<input type='hidden' name='normTaskId'  value='"+data.data.normTaskId+"'>"
    			$("#c_expertId").append(normTaskId);
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
	
	//保存考评人员
	editSaveData : function(){
		//alert($("#addForm").serialize());
		$.ajax({
			url: ctx+"/admin/normTaskEmployeeDetail/saveTaskEmp",
			type: "POST",
			async: true,
			data: $("#addForm").serialize(),
			success: function(data){
				if(data.errorCode =="000000"){
					Dialog.showSuccess("保存成功");
					var array=[];
					$(data.data).each(function(){
						array.push(this.realName);
					});
					alert(array);
					//document.location.reload();
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
	
	//跳转考核项目工作内容页面
	chooseProject : function (normTaskTmployeeDetailId,normTaskId,employeeId) {
		document.location.href = ctx + "/admin/normTaskProject/normTaskProjectList?normTaskTmployeeDetailId="+normTaskTmployeeDetailId+"&normTaskId="+normTaskId+"&employeeId="+employeeId;
	},
	
	//自評评分跳转页面
	score : function (normTaskId,employeeId,type) {
		document.location.href = ctx + "/admin/normTaskEmployee/normTaskEmployeeList?normTaskId="+normTaskId+"&employeeId="+employeeId+"&typeF="+type;
	},
	//互评页面
	EachScore : function (normTaskId,employeeId,type) {
		document.location.href = ctx + "/admin/normTaskEmployee/eachScoreList?normTaskId="+normTaskId+"&employeeId="+employeeId+"&typeF="+type;
	},
	//上级评分页面
	Levelscore : function (normTaskId,employeeId,type) {
		document.location.href = ctx + "/admin/normTaskEmployee/levelScoreList?normTaskId="+normTaskId+"&employeeId="+employeeId+"&typeF="+type;
	},
	//跳转页面  已经归档
	hasScore : function (normTaskId,employeeId,taskName) {
		document.location.href = ctx + "/admin/normTaskEmployee/hasNormTaskEmployeeList?normTaskId="+normTaskId+"&employeeId="+employeeId+"&taskName="+taskName;
	},
    //结算总分
	calculate : function(normTaskTmployeeDetailId,normTaskId,employeeId) {
		//alert();
		$.ajax({
			url: ctx+"/admin/normTaskEmployee/calculate",
			type: "POST",
			async: true,
			data: {
				"normTaskTmployeeDetailId" :normTaskTmployeeDetailId,
				"normTaskId" : normTaskId,
				"employeeId" : employeeId
			},
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
	sendMail : function() {
		var mails=$("#mails").val();
		//alert(mails);
		$.ajax({
			url: ctx+"/admin/normTask/sendMail",
			type: "POST",
			async: true,
			data: {
				"mails" :mails,
			},
			success: function(data){
				if(data.errorCode =="000000"){
					Dialog.showSuccess("邮件发送成功");
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
	exportExcel : function(normTaskId,employeeId) {
		
		//alert("123");
		$.ajax({
			url: ctx+"/admin/exportExcel/exportExcel",
			type: "POST",
			async: true,
			data: {
				"normTaskId" :normTaskId,
				"employeeId" :employeeId,
			},
			success: function(data){
				if(data.errorCode =="000000"){
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
	}
}