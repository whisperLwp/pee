$(function(){
	$(document).keypress(function(e) {
		if(e.keyCode==13) {
			Login.login();
		}
	 })
	$("#login").bind("click", Login.login);
	
});
var Login={
	//登录
	login : function(){
		//alert("123")
		if(!$("form#editForm").validationEngine("validate")) return ; 
		$.ajax({
			url: ctx+"/login/check",
    		type: "POST",
    		async: true,
    		data: $("#editForm").serialize(),
    		success: function(data){
    			if(data.errorCode =="000000"){
    				Dialog.showSuccess("登录成功");
    				document.location.href = ctx  ;
    			}
    			else{
    				Dialog.showError(data.errorMessage);
    				$("#inputaccount").val("");
    				changeImg();
    			}
    		},
    		error: function(){
    			Dialog.showError(data.errorMessage);
    		}
    	});
	},	
	
}