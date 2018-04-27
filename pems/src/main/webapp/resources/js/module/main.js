/*$(function(){
	$(".retry-btn").each(function(){
		$(this).on("click", function(){
			var obj = $(this).closest(".content").find(".content-repry");
			if(obj.is(":hidden")){
				obj.show();
			}else{
				obj.hide();
			}
		});
		
	});
	$(".retrysave-btn").each(function(){
		$(this).on("click", function(){
			$(this).closest(".content-repry").hide();
			$(this).closest(".content-repry").find("textarea").val("");
		});
	});
	$(".retrylist-btn").each(function(){
		$(this).on("click", function(){
			var obj = $(this).closest(".content").find(".content-repry-list");
			if(obj.is(":hidden")){
				obj.show();
			}else{
				obj.hide();
			}
		});
	});
});*/

var Dialog = {
	showSuccess : function (message){
		layer.msg(message);
	},
	showError : function (message){
		layer.msg(message);
	},
	showConfirm : function(callback, message){
		if(!message) message = "确认执行当前操作？";
		layer.confirm( message, {
			btn: ['是', '否'] //可以无限个按钮
			}, function(index, layero){
				callback();
				 layer.closeAll();
			}, function(index){
				 layer.closeAll();
			});
	},
	showConfirm1 : function(callback, message){
		if(!message) message = "确认执行当前操作？";
		layer.confirm( message, {
			btn: ['是', '否'] //可以无限个按钮
			}, function(index, layero){
				callback();
				 layer.closeAll();
			}, function(index){
				 layer.closeAll();
			});
	}
	
}


Date.prototype.format=function(fmt) {        
    var o = {        
    "M+" : this.getMonth()+1, //月份        
    "d+" : this.getDate(), //日        
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时        
    "H+" : this.getHours(), //小时        
    "m+" : this.getMinutes(), //分        
    "s+" : this.getSeconds(), //秒        
    "q+" : Math.floor((this.getMonth()+3)/3), //季度        
    "S" : this.getMilliseconds() //毫秒        
    };        
    var week = {        
    "0" : "\u65e5",        
    "1" : "\u4e00",        
    "2" : "\u4e8c",        
    "3" : "\u4e09",        
    "4" : "\u56db",        
    "5" : "\u4e94",        
    "6" : "\u516d"       
    };        
    if(/(y+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));        
    }        
    if(/(E+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
    }        
    for(var k in o){        
        if(new RegExp("("+ k +")").test(fmt)){        
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));        
        }        
    }        
    return fmt; 
    
    
  //列表复选框-全选事件
    $("#theadInput").on('click', function() {
        $("tbody input:checkbox").prop("checked", $(this).prop('checked'));
    })
    //列表复选框-单选事件
    $("tbody input:checkbox").on('click', function() {
        //当选中的长度等于checkbox的长度的时候,就让控制全选反选的checkbox设置为选中,否则就为未选中
        if($("tbody input:checkbox").length === $("tbody input:checked").length) {
            $("#theadInput").prop("checked", true);
        } else {
            $("#theadInput").prop("checked", false);
        }
    })
    
} 