$(function(){
	$("#save_btn").bind("click", NormTask.saveData);
	$("#editSave_btn").bind("click", NormTask.saveData);
	
});
var NormTask={
	//弹出编辑对话框
	addEdit : function(normTaskId){
		//页面跳转方式
		if(!normTaskId) normTaskId = "";
		document.location.href = ctx + "/admin/normTask/addNormTask?normTaskId="+normTaskId;
	},	
	editEdit : function(normTaskId,type){
		//页面跳转方式
		if(!normTaskId) normTaskId = "";
		document.location.href = ctx + "/admin/normTask/editNormTask?normTaskId="+normTaskId+"&type="+type;
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
	fillData : function(normTaskId){
		this.getData(normTaskId, function(data){
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
		var flag = false;
		$(".s_sel").each(function(){
			if($(this).val()==-1){
				layer.msg("您有未选择考核模板的岗位");
				flag = true;
			}
		})
		/*alert($("#editForm").serialize());
		if(($("#editForm").serialize()).contains('-1')){
			alert("3333")
		}*/
		if(flag) return;
		if(!$("form#editForm").validationEngine("validate")) return ;  
		$.ajax({
    		url: ctx+"/admin/normTask/saveNormTask",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("保存成功");
    				document.location.reload();
    				document.location.href = ctx + "/admin/normTask/normTaskList";
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
	getData : function(normTaskId, callback){
		$.ajax({
    		url: ctx+"/admin/normTask/findNormTask",
    		type: "POST",
    		async: true,
    		data: {"normTaskId" : normTaskId},
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
	deleteData : function (normTaskId, delFlag) {
        Dialog.showConfirm(function() {
        	$.ajax({
        		url: ctx+"/admin/normTask/deleteNormTask",
        		type: "POST",
        		async: true,
        		data: {"normTaskId" : normTaskId},
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
	//取消归档
	resetData : function (normTaskId, delFlag) {
		layer.confirm("确认取消归档吗？取消归档后员工将可以修改评分", {btn: ["确定","取消"]},function(){
			$.ajax({
				url: ctx+"/admin/normTask/resetNormTask",
				type: "POST",
				async: true,
				data: {"normTaskId" : normTaskId},
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
	
	//查询员工考核任务归档明细
	showEmpl : function(normTaskId) {
		if(!normTaskId) normTaskId = "";
		document.location.href = ctx + "/admin/normTaskEmployeeDetail/normTaskEmployeeDetailList?normTaskId="+normTaskId;
	},
	//已经归档员工考核任务归档明细
	showHasEmpl : function(normTaskId) {
		if(!normTaskId) normTaskId = "";
		document.location.href = ctx + "/admin/normTaskEmployeeDetail/hasNormTaskEmployeeDetailList?normTaskId="+normTaskId;
	},
	//归档
	saveScore : function(normTaskId) {
		layer.confirm("确认归档吗？归档之后所有员工将不能进行评分", {btn: ["确定","取消"]},function(){
			$.ajax({
	    		url: ctx+"/admin/normTaskEmployee/saveAll",
	    		type: "POST",
	    		async: true,
	    		data: {"normTaskId" : normTaskId},
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
	    	})
		});
		
	}
	
}



$(function(){
	//时间控件
    //实现日期选择联动
	var start = {
		format: 'YYYY-MM-DD',
		minDate: '1900-01-01 23:59:59', //设定最小日期为当前日期
		//festival:true,
		/*maxDate: $.nowDate({DD:0}),*/ //最大日期
		choosefun: function(elem,datas){
		    end.minDate = datas; //开始日选好后，重置结束日的最小日期
		    endDates();
		},
	};
	var end = {
		format: 'YYYY-MM-DD',
		minDate: '1900-01-01 23:59:59',  //设定最小日期为当前日期
		//festival:true,
		maxDate: '2099-06-16 23:59:59', //最大日期
		choosefun: function(elem,datas){
		    start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
		}
	};
	function endDates() {
		end.trigger = false;
		$("#inpend").jeDate(end);
	}
	$("#inpstart").jeDate(start);
	$("#inpend").jeDate(end);
//------------------------------------------------------	
	//实现日期选择联动
	var startTime = {
		format: 'YYYY-MM-DD',
		minDate: '1900-01-01 23:59:59', //设定最小日期为当前日期
		choosefun: function(elem,datas){
		    endTime.minDate = datas; //开始日选好后，重置结束日的最小日期
		    endDateTimes();
		},
	};
	var endTime = {
		format: 'YYYY-MM-DD',
		minDate: '1900-01-01 23:59:59',  //设定最小日期为当前日期
		maxDate: '2099-06-16 23:59:59', //最大日期
		choosefun: function(elem,datas){
		    startTime.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
		}
	};
	function endDateTimes() {
		endTime.trigger = false;
		$("#inpendTime").jeDate(endTime);
	}
	$("#inpstartTime").jeDate(startTime);
	$("#inpendTime").jeDate(endTime);
});