<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<script src="${ctx }/resources/js/admin/task/normTask.js"></script>
    <c:if test="${type==2 }">
    <h4 align="center" style="color:red ;">温馨提示：您已经选用的岗位已经被引用，不能修改，只支持新增岗位模板，如需修改岗位模板，只能删除此次考核</h4>
    </c:if>
	<form class="form-horizontal" role="form" id="editForm">
		<button style="margin-top: 10px;" type="button" class="btn btn-default  btn-sm" onclick="history.go(-1);">
		  <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>返回上一页
		</button>
		<input name="normTaskId" type="hidden" value="${normTask.normTaskId}"></input>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>考核任务名称</label>
			<div class="col-sm-10">
				<input class="form-control validate[required]"  name="normTaskName" value="${normTask.normTaskName}" type="text" placeholder="请输入100字以内字符" maxlength="100"></input>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>状态</label>
			<div class="col-sm-10">
			    <input type="radio" name="status" checked="checked" value="1" />已开始
				<input type="radio" name="status" value="0" />未开始
			</div>
		</div>
		
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>考核开始时间</label>
			<div class="col-sm-10">
				<input class="datainp wicon" id="inpstartTime" type="text" placeholder="考核开始时间" name="startTime" value="<fmt:formatDate value='${normTask.startTime}' pattern='yyyy-MM-dd'/>" readonly> 
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>考核结束时间</label>
			<div class="col-sm-10">
				<input class="datainp wicon" id="inpendTime" type="text" placeholder="考核结束时间"  name="endTime" value="<fmt:formatDate value='${normTask.endTime}' pattern='yyyy-MM-dd'/>" readonly>
			</div>
		</div>
		<div class="form-group">
			<label for="disabledSelect" class="col-sm-2 control-label"><b class="z_common_star">*</b>评分截止时间</label>
			<div class="col-sm-10">
				<input class="datainp wicon" id="inpstart" type="text" placeholder="请选择考核归档时间" name="scoreTime" value="<fmt:formatDate value='${normTask.scoreTime}' pattern='yyyy-MM-dd'/>" readonly> 
			</div>
		</div>
		<c:forEach var="entity2" items="${stationList }">
			<c:set var="choose" value="0" />
			<c:if test="${type==2 }">
			<c:forEach var="entity3" items="${taskStationTemplates }">
			<c:if test="${entity3.stationId==entity2.stationId }">
			<c:set var="choose" value="1" />
			<div class="form-group">
				<label for="disabledSelect" style="color: black" class="col-sm-2 control-label">
				${entity2.stationName }
				<input type="checkbox" onclick="return false" name="station" checked="checked" value="${entity2.stationId }"/>
				</label>
				<div class="col-sm-10 s_select">
				<select class="form-control combobox input-sm" name="normTempId" >
				    <option value="-1"></option>
					<c:forEach var="entity2" items="${NormTemplateList}" varStatus="status">
					    <c:if test="${entity2.normTempId==entity3.normTemplateId }">
					    <option value="${entity2.normTempId}" selected="selected">${entity2.normTempName }</option>
					    </c:if>
					</c:forEach>
				</select>
				</div>
				<input type="hidden" name="normTempId" value="@">
			</div>
			</c:if>
			</c:forEach>
			</c:if>
			<c:if test="${type==1 }">
			<c:forEach var="entity3" items="${taskStationTemplates }">
			<c:if test="${entity3.stationId==entity2.stationId }">
			<c:set var="choose" value="1" />
			<div class="form-group">
				<label for="disabledSelect" style="color: red" class="col-sm-2 control-label">
				${entity2.stationName }
				<input type="checkbox" class="s_station" name="station" checked="checked" value="${entity2.stationId }"/>
				</label>
				<div class="col-sm-10 s_select">
				<select class="form-control combobox input-sm" name="normTempId" id="combobox">
				    <option value="-1"></option>
					<c:forEach var="entity2" items="${NormTemplateList}" varStatus="status">
					    <option value="${entity2.normTempId}" ${entity2.normTempId==entity3.normTemplateId?'selected="selected"':''}>${entity2.normTempName }</option>
					</c:forEach>
				</select>
				</div>
				<input type="hidden" name="normTempId" value="@">
			</div>
			</c:if>
			</c:forEach>
			</c:if>
			<c:if test="${choose==0}">
		   		<div class="form-group">
				<label for="disabledSelect" style="color: red" class="col-sm-2 control-label">
				${entity2.stationName }
				<input type="checkbox" class="s_station" name="station" checked="checked" value="${entity2.stationId }"/>
				</label>
				<div class="col-sm-10 s_select" >
				<select class="form-control combobox input-sm s_sel" name="normTempId" id="combobox" style="display: none;">
				    <option value="-1"></option>
					<c:forEach var="entity2" items="${NormTemplateList}" varStatus="status">
					    <option value="${entity2.normTempId}">${entity2.normTempName }</option>
					</c:forEach>
				</select>
				</div>
				<input type="hidden" name="normTempId" value="@">
			</div>
		    </c:if>
		</c:forEach>
		<input type="hidden" name="employeeId" value="${employeeId }">
		<input type="hidden" name="normTaskId" value="${normTaskId }">
	</form>
	
	<div class="modal-footer d_modal_footer">
		<div class="d_subject_button">
			<button type="button" class="btn btn-primary" id="editSave_btn">保存</button>
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
	  
	  
	  $(".s_station").click(function(){
		   if(!$(this).prop("checked")){
		      $(this).parent().next().hide();
		      $(this).closest(".form-group").find("select[name='normTempId']").prop("disabled",true);
		      $(this).closest(".form-group").find("select[name='normTempId']").val("1");
		      $(this).closest(".form-group").find("input[name='normTempId']").prop("disabled",true);
		      
	       }else{
	    	  $(this).closest(".form-group").find("select[name='normTempId']").prop("disabled",false)
	    	  $(this).closest(".form-group").find("input[name='normTempId']").prop("disabled",false);
			      
	    	  $(this).parent().next().show(); 
	       }
	   })
  </script>