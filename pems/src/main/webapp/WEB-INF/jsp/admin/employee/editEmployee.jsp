<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
  .custom-combobox {
    position: relative;
    display: inline-block;
  }
  .custom-combobox-toggle {
    position: absolute;
    top: 0;
    bottom: 0;
    margin-left: -1px;
    padding: 0;
    /* 支持： IE7 */
    *height: 1.7em;
    *top: 0.1em;
  }
  .custom-combobox-input {
    margin: 0;
    padding: 0.3em;
  }
  </style>
<script src="${ctx }/resources/js/admin/employee/employee.js"></script>

	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="employeeId" id="employeeId" type="hidden" value="${employee.employeeId}"></input>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>员工登录名</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="employeeName" value="${employee.employeeName}" type="text" placeholder="请输入用户名" maxlength="50"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>真实姓名</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="realName" value="${employee.realName}" type="text" placeholder="请输入真实姓名" maxlength="10"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>性别</label>
			<div class="col-sm-10">
			     <input type="radio" name="sexFlag" ${employee.sexFlag==1?'checked':'' } value="1">男
			     <input type="radio" name="sexFlag" ${employee.sexFlag==0?'checked':'' } value="0">女
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>身份证号</label>
			<div class="col-sm-10">
				<input class="form-control validate[required,custom[isCardNo]]"  name="idCard" value="${employee.idCard}" type="text" placeholder="请输入身份证号" maxlength="19"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>角色</label>
			<div class="col-sm-10">
			     <input type="radio" class="roleId" name="roleId" checked="checked" ${employee.roleId==3?'checked':'' } value="3">普通员工
			     <input type="radio" class="roleId" name="roleId" ${employee.roleId==1?'checked':'' } value="1">管理员
			     <input type="radio" class="roleId" name="roleId" ${employee.roleId==2?'checked':'' } value="2">部门负责人
			     <input type="radio" class="roleId" name="roleId" ${employee.roleId==4?'checked':'' } value="4">总经理
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>是否参加考评</label>
			<div class="col-sm-10">
			     <input type="radio" name="peFlag" checked="checked" ${employee.peFlag==1?'checked':'' } value="1">是
			     <input type="radio" name="peFlag" ${employee.peFlag==0?'checked':'' } value="0">否
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label">直接部门</label>
			<div class="col-sm-10">
				<select class="form-control input-sm projectMod s_dept" name="deptId">
			    <option value="-1">--请选部门--</option>
				<c:forEach var="entity" items="${deptList}" varStatus="status">
				    <option value="${entity.deptCode}" ${entity.deptCode==employee.deptId?'selected="selected"':''}   >${entity.deptName}</option>
			         <c:if test="${! empty entity.list }">
				          <c:forEach var="entity2" items="${entity.list }">
				              <option value="${entity2.deptCode}" ${entity2.deptCode==employee.deptId?'selected="selected"':''}>&nbsp;&nbsp;&nbsp;&nbsp;${entity2.deptName}</option>
				          </c:forEach>
			          </c:if>
				</c:forEach>
			</select>
		  </div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label">直属领导</label>
			<div class="col-sm-10 s_level">
				<input type="hidden" name="levelEmployeeId" readonly="readonly" value="${employee.levelEmployeeId}">${employee.levelEmployeeName}</input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label">所属岗位</label>
			<div class="col-sm-10">
				<select class="form-control input-sm combobox" name="stationId" id="combobox">
				<option value="-1"></option>
				<c:forEach var="entity" items="${stationList}" varStatus="status">
				    <option
							${employee.stationId==entity.stationId ? 'selected="selected"':''}
							 value="${entity.stationId}">${entity.stationName}</option>
				</c:forEach>
			</select>
		  </div>
		</div>
		
		<!-- <div class="form-group" style="display: none;"> -->
		
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>电话</label>
			<div class="col-sm-10">
				<input class="form-control validate[required,custom[mobilephone]]"  name="phoneNum" value="${employee.phoneNum}" type="text" placeholder="请输入电话" maxlength="20"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>邮箱</label>
			<div class="col-sm-10">
				<input class="form-control validate[required,custom[email]]"  name="mailNum" value="${employee.mailNum}" type="text" placeholder="请输入邮箱" maxlength="20"></input>
			</div>
		</div>
	</form>
	<div class="modal-footer d_modal_footer">
		<div class="d_subject_button">
			<button type="button" class="btn btn-primary" id="save_btn">保存</button>
			<button type="button" class="btn btn-default " onclick="history.go(-1);">返回</button>
		</div>
	</div>
<script>
  (function( $ ) {
	    $.widget( "custom.combobox", {
	      _create: function() {
	        this.wrapper = $( "<span>" )
	          .addClass( "custom-combobox" )
	          .insertAfter( this.element );
	 
	        this.element.hide();
	        this._createAutocomplete();
	        this._createShowAllButton();
	      },
	 
	      _createAutocomplete: function() {
	        var selected = this.element.children( ":selected" ),
	          value = selected.val() ? selected.text() : "";
	 
	        this.input = $( "<input>" )
	          .appendTo( this.wrapper )
	          .val( value )
	          .attr( "title", "" )
	          .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
	          .autocomplete({
	            delay: 0,
	            minLength: 0,
	            source: $.proxy( this, "_source" )
	          })
	          .tooltip({
	            tooltipClass: "ui-state-highlight"
	          });
	 
	        this._on( this.input, {
	          autocompleteselect: function( event, ui ) {
	            ui.item.option.selected = true;
	            this._trigger( "select", event, {
	              item: ui.item.option
	            });
	          },
	 
	          autocompletechange: "_removeIfInvalid"
	        });
	      },
	 
	      _createShowAllButton: function() {
	        var input = this.input,
	          wasOpen = false;
	 
	        $( "<a>" )
	          .attr( "tabIndex", -1 )
	          .attr( "title", "显示所有" )
	          .tooltip()
	          .appendTo( this.wrapper )
	          .button({
	            icons: {
	              primary: "ui-icon-triangle-1-s"
	            },
	            text: false
	          })
	          .removeClass( "ui-corner-all" )
	          .addClass( "custom-combobox-toggle ui-corner-right" )
	          .mousedown(function() {
	            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
	          })
	          .click(function() {
	            input.focus();
	 
	            // 如果已经可见则关闭
	            if ( wasOpen ) {
	              return;
	            }
	 
	            // 传递空字符串作为搜索的值，显示所有的结果
	            input.autocomplete( "search", "" );
	          });
	      },
	 
	      _source: function( request, response ) {
	        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
	        response( this.element.children( "option" ).map(function() {
	          var text = $( this ).text();
	          if ( this.value && ( !request.term || matcher.test(text) ) )
	            return {
	              label: text,
	              value: text,
	              option: this
	            };
	        }) );
	      },
	 
	      _removeIfInvalid: function( event, ui ) {
	 
	        // 选择一项，不执行其他动作
	        if ( ui.item ) {
	          return;
	        }
	 
	        // 搜索一个匹配（不区分大小写）
	        var value = this.input.val(),
	          valueLowerCase = value.toLowerCase(),
	          valid = false;
	        this.element.children( "option" ).each(function() {
	          if ( $( this ).text().toLowerCase() === valueLowerCase ) {
	            this.selected = valid = true;
	            return false;
	          }
	        });
	 
	        // 找到一个匹配，不执行其他动作
	        if ( valid ) {
	          return;
	        }
	 
	        // 移除无效的值
	        this.input
	          .val( "" )
	          .attr( "title", value + " didn't match any item" )
	          .tooltip( "open" );
	        layer.msg("无输入内容")
	        this.element.val( "" );
	        return;
	        this._delay(function() {
	          this.input.tooltip( "close" ).attr( "title", "" );
	        }, 2500 );
	        this.input.data( "ui-autocomplete" ).term = "";
	      },
	 
	      _destroy: function() {
	        this.wrapper.remove();
	        this.element.show();
	      }
	    });
	  })( jQuery );
	 
	  $(function() {
	    $( ".combobox" ).combobox();
	    $( "#toggle" ).click(function() {
	      $( "#combobox" ).toggle();
	    });
	  });
  </script>