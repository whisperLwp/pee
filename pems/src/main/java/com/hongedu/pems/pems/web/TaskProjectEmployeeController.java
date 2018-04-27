
package com.hongedu.pems.pems.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import com.hongedu.pems.util.mail.MailManager;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.util.shiro.ShiroEmployee;

import net.sf.json.util.NewBeanInstanceStrategy;

import com.hongedu.pems.base.dao.expression.Exp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.Norm;
import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTaskEmployee;
import com.hongedu.pems.pems.entity.NormTaskProject;
import com.hongedu.pems.pems.entity.ProjectEmployee;
import com.hongedu.pems.pems.entity.TaskProjectEmployee;
import com.hongedu.pems.pems.entity.TaskStationTemplate;
import com.hongedu.pems.pems.service.EmployeeService;
import com.hongedu.pems.pems.service.NormCategoryService;
import com.hongedu.pems.pems.service.NormService;
import com.hongedu.pems.pems.service.NormTaskEmployeeDetailService;
import com.hongedu.pems.pems.service.NormTaskEmployeeService;
import com.hongedu.pems.pems.service.NormTaskProjectService;
import com.hongedu.pems.pems.service.NormTaskService;
import com.hongedu.pems.pems.service.ProjectEmployeeService;
import com.hongedu.pems.pems.service.ProjectService;
import com.hongedu.pems.pems.service.TaskProjectEmployeeService;
import com.hongedu.pems.pems.service.TaskStationTemplateService;


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
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectEmployeeService projectEmployeeService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private NormTaskService normTaskService;
	@Autowired
	private NormCategoryService normCategoryService;
	@Autowired
	private NormTaskEmployeeDetailService normTaskEmployeeDetailService;
	@Autowired
	private NormTaskEmployeeService normTaskEmployeeService;
	@Autowired
	private NormTaskProjectService normTaskProjectService;
	@Autowired
	private TaskStationTemplateService taskStationTemplateService;
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
	
	/**
	 * 查询项目对应的人员   查询未分配人员
	 * @param employeeId
	 * @param normTaskId
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/eachEmployee",method=RequestMethod.POST)
	public JsonResult eachEmployee(ProjectEmployee projectEmployee,
			@RequestParam(required=true)java.lang.Integer employeeId,
			@RequestParam(required=true)java.lang.Integer normTaskId,
			@RequestParam(required=true)java.lang.Integer projectId,
			@RequestParam(required=true)java.lang.Integer normTaskProjectId
			) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<ProjectEmployee> noEmployeeList=projectEmployeeService.findEmployee(projectId,normTaskProjectId);
			List<TaskProjectEmployee> hasEmployeeList=taskProjectEmployeeService.findHasEmployee(normTaskProjectId);
			resultMap.put("noEmployeeList", noEmployeeList);
			resultMap.put("hasEmployeeList", hasEmployeeList);
			resultMap.put("normTaskProjectId", normTaskProjectId);
			resultMap.put("employeeId", employeeId);
			resultMap.put("normTaskId", normTaskId);
			resultMap.put("projectId", projectId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 保存互评人关系
	 * @param request
	 * @param taskProjectEmployee
	 * @param normTaskProjectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveEachEmployee",method=RequestMethod.POST)
	public JsonResult saveEachEmployee(HttpServletRequest request,TaskProjectEmployee taskProjectEmployee,
			NormTaskEmployee normTaskEmployee,
			@RequestParam(required=true)java.lang.Integer normTaskProjectId,
			@RequestParam(required=true)java.lang.Integer normTaskId,
			@RequestParam(required=true)java.lang.Integer employeeId,
			@RequestParam(required=true)java.lang.Integer projectId
			) {
		try {
			ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
			normTaskEmployeeDetailService.updatePass(EMPLOYEE.getEmployeeId(),normTaskId);
			String[] eachEmployeeIds = request.getParameterValues("eachEmployeeId");
			Integer xmId=null;
			Integer normType=null;
			Integer scoreType=null;
			 //保存任务表    *员工考核任务归档明细表*	
			Employee emp = employeeService.findById(employeeId);
			TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,emp.getStationId());
			Integer normTempId = taskStationTemplate.getNormTemplateId();
			NormTaskProject entity = new NormTaskProject();
			entity.setNormTaskProjectId(normTaskProjectId);
			normTaskProjectService.updateRefer(entity);
			//根据模板查询考核指标分类
			List<NormCategory> normCategoryList=normCategoryService.findByNormTempId(normTempId);
			
			//循环遍历考评指标分类
			for (NormCategory normCategory : normCategoryList) {
				if(normCategory.getNormCategoryName().equals("项目考核指标分类")){
					xmId = normCategory.getNormCategoryId();
				}
			}	
			taskProjectEmployeeService.delByNormTaskProjectId(normTaskProjectId);
			
			//判断添加或者删除互评人员  互评人员scoreType=2
			//删除的id
			List<Integer> delId=new ArrayList<>();
			//添加的Id
			List<Integer> addId=new ArrayList<>();
			List<Integer> ids=new ArrayList<>();
			
			// 查询已经分配项互评目人员
			List<NormTaskEmployee> hasEmployees = normTaskEmployeeService.findEachEmpByScoreType(normTaskId,employeeId,2,projectId,normTaskProjectId);
			for (NormTaskEmployee e : hasEmployees) {
				ids.add(e.getScoreEmployeeId());
			}
			if(eachEmployeeIds==null){   //互评人为空  删除所有已经分配的人员
				for (NormTaskEmployee hasemp : hasEmployees) {
					normTaskEmployeeService.del(normTaskId,employeeId,2,hasemp.getScoreEmployeeId(),projectId,normTaskProjectId);
				}
			}else{
				List<String> list1 = Arrays.asList(eachEmployeeIds);
				for (NormTaskEmployee hasemp : hasEmployees) {
					if(!list1.contains(hasemp.getScoreEmployeeId()))  {
						normTaskEmployeeService.del(normTaskId,employeeId,2,hasemp.getScoreEmployeeId(),projectId,normTaskProjectId);
					}
				}
				for (String id1 : eachEmployeeIds) {
					if(!ids.contains(id1)){
						addId.add(Integer.parseInt(id1));
					}
				} 
			}
			//遍历添加互评人员   **
			if(addId.size()!= 0){
				for (Integer eachEmployeeId : addId) {
					//Integer eachemployeeId=Integer.parseInt(eachEmployeeId);
					taskProjectEmployee.setEachEmployeeId(eachEmployeeId);
					taskProjectEmployee.setNormTaskProjectId(normTaskProjectId);
					//保存互评人关系   *task_project_employee表*
					taskProjectEmployeeService.save(taskProjectEmployee);
					//互评     /** norm_task_employee */
					saveNormTaskEmp(normTaskEmployee, normTaskProjectId, normTaskId, employeeId, projectId, xmId,
							eachEmployeeId);
				}
			}
		return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}

	private void saveNormTaskEmp(NormTaskEmployee normTaskEmployee, java.lang.Integer normTaskProjectId,
			java.lang.Integer normTaskId, java.lang.Integer employeeId, java.lang.Integer projectId, Integer xmId,
			Integer eachEmployeeId) {
		Integer normType;
		Integer scoreType;
		scoreType=2;
		normType=3;
		normTaskEmployee.setEmployeeId(Integer.valueOf(employeeId));
		normTaskEmployee.setNormTaskId(normTaskId);
		normTaskEmployee.setNormType(normType);
		normTaskEmployee.setCreateTime(new Date());
		normTaskEmployee.setScoreEmployeeId(eachEmployeeId);
		normTaskEmployee.setScoreType(scoreType);
		normTaskEmployee.setProjectId(projectId);
		normTaskEmployee.setNormCategoryId(xmId);
		normTaskEmployee.setWorkContentId(normTaskProjectId);
		normTaskEmployeeService.save(normTaskEmployee);
	}

	/*private void extracted(NormTaskEmployee normTaskEmployee, java.lang.Integer normTaskId,
			java.lang.Integer employeeId, Integer normType, Integer scoreType, Integer scoreEmployeeId,
			List<Norm> xmNormList,Integer projectId,Integer xmId) {
		for (Norm norm : xmNormList) {
			Integer normId = norm.getNormId();
			normTaskEmployee.setNormId(normId);
			normTaskEmployee.setEmployeeId(Integer.valueOf(employeeId));
		    normTaskEmployee.setNormTaskId(normTaskId);
		    normTaskEmployee.setNormType(normType);
		    normTaskEmployee.setCreateTime(new Date());
		    normTaskEmployee.setScoreEmployeeId(scoreEmployeeId);
		    normTaskEmployee.setScoreType(scoreType);
		    normTaskEmployee.setProjectId(projectId);
		    normTaskEmployee.setNormCategoryId(xmId);
			normTaskEmployeeService.save(normTaskEmployee);
		}
	}*/
}
