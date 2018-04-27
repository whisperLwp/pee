
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

import com.hongedu.pems.pems.entity.TaskProjectEmployee;
import com.hongedu.pems.pems.service.TaskProjectEmployeeService;


/**
 * @author  
 * el_bp_task_project_employee 表对应的controller
 * 2018/02/24 02:40:05
 */
@Controller
@RequestMapping("/admin/taskProjectEmployee")
public class TaskProjectEmployeeController {
	private final static Logger logger= LoggerFactory.getLogger(TaskProjectEmployeeController.class);
	
	@Autowired
	private TaskProjectEmployeeService taskProjectEmployeeService;
	
	/**
	 * 查询TaskProjectEmployee详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findTaskProjectEmployee",method=RequestMethod.POST)
	public JsonResult findTaskProjectEmployee(
			@RequestParam(required=true)java.lang.Integer taskProjectEmployeeId) {
		try {
			TaskProjectEmployee taskProjectEmployee = taskProjectEmployeeService.findById(taskProjectEmployeeId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", taskProjectEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑TaskProjectEmployee
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editTaskProjectEmployee",method=RequestMethod.GET)
	public String editTaskProjectEmployee(
			java.lang.Integer taskProjectEmployeeId,
			Model model) {
		if(taskProjectEmployeeId != null){
			TaskProjectEmployee taskProjectEmployee = taskProjectEmployeeService.findById(taskProjectEmployeeId);
			model.addAttribute("taskProjectEmployee", taskProjectEmployee);
		}
		return "admin/taskProjectEmployee/editTaskProjectEmployee";
	}
	
	/**
	 * 保存TaskProjectEmployee
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveTaskProjectEmployee",method=RequestMethod.POST)
	public JsonResult saveTaskProjectEmployee(
			TaskProjectEmployee taskProjectEmployee
			) {
		try {
			if(taskProjectEmployee.getTaskProjectEmployeeId() == null){
				taskProjectEmployeeService.save(taskProjectEmployee);
			}else{
				taskProjectEmployeeService.update(taskProjectEmployee);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除TaskProjectEmployee
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteTaskProjectEmployee",method=RequestMethod.POST)
	public JsonResult deleteTaskProjectEmployee(
			@RequestParam(required=true)java.lang.Integer taskProjectEmployeeId) {
		try {
			taskProjectEmployeeService.delete(taskProjectEmployeeId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询TaskProjectEmployee表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/taskProjectEmployeeList",method=RequestMethod.GET)
	public String taskProjectEmployeeList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<TaskProjectEmployee> page = taskProjectEmployeeService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/taskProjectEmployee/taskProjectEmployeeList";
	}
}
