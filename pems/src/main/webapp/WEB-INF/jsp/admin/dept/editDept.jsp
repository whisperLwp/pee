<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/resources/js/common/jquery-ui.js"></script>
<link rel="stylesheet" href="${ctx}/resources/css/common/jquery-ui.css"> 
<link rel="stylesheet" href="${ctx}/resources/css/common/jquery-ui.min.css"> 
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
<script src="${ctx }/resources/js/admin/dept/dept.js"></script>

	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="deptId" type="hidden" value="${dept.deptId}"></input>
		
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>部门名称</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="deptName" value="${dept.deptName}" type="text" placeholder="请输入部门名称" maxlength="40"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>备注</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="remark" value="${dept.remark}" type="text" placeholder="请输入备注" maxlength="100"></input>
			</div>
		</div>
		
		<c:if test="${edit!='edit' }">
		
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>部门负责人</label>
			<div class="col-sm-10">
			    <select class="form-control input-sm projectMod s_dept combobox validate[required]" id="combobox" name="deptUserId">
					<option value="-1"></option>
					<c:forEach var="entity" items="${employeeList }">
						<option
							${dept.deptUserId==entity.employeeId ? 'selected="selected"':''}
							 value="${entity.employeeId}">${entity.realName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label">上级部门</label>
			<div class="col-sm-10">
			    <select class="form-control input-sm projectMod s_dept combobox" name="pDeptId" id="combobox">
					<option value="1"></option>
					<c:forEach var="entity" items="${deptList }">
						<option
							${employee.deptId==entity.deptId ? 'selected="selected"':''}
							 value="${entity.deptCode}">${entity.deptName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		</c:if>
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