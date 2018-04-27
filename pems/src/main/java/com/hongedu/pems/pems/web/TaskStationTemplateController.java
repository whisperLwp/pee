
package com.hongedu.pems.pems.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongedu.pems.util.json.JsonResult;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.pems.entity.TaskStationTemplate;
import com.hongedu.pems.pems.service.TaskStationTemplateService;


/**
 * @author  
 * el_bp_task_station_template 表对应的controller
 * 2018/04/18 09:30:38
 */
@Controller
@RequestMapping("/admin/taskStationTemplate")
public class TaskStationTemplateController {
	private final static Logger logger= LoggerFactory.getLogger(TaskStationTemplateController.class);
	
	@Autowired
	private TaskStationTemplateService taskStationTemplateService;
	
	/**
	 * 查询TaskStationTemplate详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findTaskStationTemplate",method=RequestMethod.POST)
	public JsonResult findTaskStationTemplate(
			@RequestParam(required=true)java.lang.Integer taskStationTemplateId) {
		try {
			TaskStationTemplate taskStationTemplate = taskStationTemplateService.findById(taskStationTemplateId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", taskStationTemplate);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑TaskStationTemplate
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editTaskStationTemplate",method=RequestMethod.GET)
	public String editTaskStationTemplate(
			java.lang.Integer taskStationTemplateId,
			Model model) {
		if(taskStationTemplateId != null){
			TaskStationTemplate taskStationTemplate = taskStationTemplateService.findById(taskStationTemplateId);
			model.addAttribute("taskStationTemplate", taskStationTemplate);
		}
		return "admin/taskStationTemplate/editTaskStationTemplate";
	}
	
	/**
	 * 保存TaskStationTemplate
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveTaskStationTemplate",method=RequestMethod.POST)
	public JsonResult saveTaskStationTemplate(
			TaskStationTemplate taskStationTemplate
			) {
		try {
			if(taskStationTemplate.getTaskStationTemplateId() == null){
				taskStationTemplateService.save(taskStationTemplate);
			}else{
				taskStationTemplateService.update(taskStationTemplate);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除TaskStationTemplate
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteTaskStationTemplate",method=RequestMethod.POST)
	public JsonResult deleteTaskStationTemplate(
			@RequestParam(required=true)java.lang.Integer taskStationTemplateId) {
		try {
			taskStationTemplateService.delete(taskStationTemplateId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询TaskStationTemplate表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/taskStationTemplateList",method=RequestMethod.GET)
	public String taskStationTemplateList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<TaskStationTemplate> page = taskStationTemplateService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/taskStationTemplate/taskStationTemplateList";
	}
}
