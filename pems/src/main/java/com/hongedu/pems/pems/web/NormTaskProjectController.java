
package com.hongedu.pems.pems.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.util.shiro.ShiroEmployee;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.Norm;
import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTaskEmployee;
import com.hongedu.pems.pems.entity.NormTaskProject;
import com.hongedu.pems.pems.entity.Project;
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
 * el_bp_norm_task_project 表对应的controller
 * 2018/01/31 02:37:12
 */
@Controller
@RequestMapping("/admin/normTaskProject")
public class NormTaskProjectController {
	private final static Logger logger= LoggerFactory.getLogger(NormTaskProjectController.class);
	
	@Autowired
	private NormTaskProjectService normTaskProjectService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskProjectEmployeeService taskProjectEmployeeService;
	@Autowired
	private ProjectEmployeeService projectEmployeeService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private NormTaskService normTaskService;
	@Autowired
	private NormCategoryService normCategoryService;
	@Autowired
	private NormService normService;
	@Autowired
	private NormTaskEmployeeService normTaskEmployeeService;
	@Autowired
	private NormTaskEmployeeDetailService normTaskEmployeeDetailService;
	@Autowired
	private TaskStationTemplateService taskStationTemplateService;
	
	/**
	 * 查询NormTaskProject详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNormTaskProject",method=RequestMethod.POST)
	public JsonResult findNormTaskProject(
			@RequestParam(required=true)java.lang.Integer normTaskProjectId) {
		try {
			NormTaskProject normTaskProject = normTaskProjectService.findById(normTaskProjectId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", normTaskProject);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑NormTaskProject
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editNormTaskProject",method=RequestMethod.GET)
	public String editNormTaskProject(
			java.lang.Integer normTaskProjectId,
			Model model) {
		if(normTaskProjectId != null){
			NormTaskProject normTaskProject = normTaskProjectService.findById(normTaskProjectId);
			model.addAttribute("normTaskProject", normTaskProject);
		}
		return "admin/normTaskProject/editNormTaskProject";
	}
	
	/**
	 * 保存NormTaskProject
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveNormTaskProject",method=RequestMethod.POST)
	public JsonResult saveNormTaskProject(
			NormTaskProject normTaskProject
			) {
		try {
			if(normTaskProject.getNormTaskProjectId() == null){
				normTaskProjectService.save(normTaskProject);
			}else{
				normTaskProjectService.update(normTaskProject);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 保存工作内容
	 * @param request
	 * @param normTaskProject
	 * @param normTaskEmployee
	 * @param normTaskId
	 * @param employeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveProjectForEmp",method=RequestMethod.POST)
	public JsonResult saveProjectForEmp(
			HttpServletRequest request,
			NormTaskEmployee normTaskEmployee,
			@RequestParam(required=true)java.lang.Integer normTaskId,
			@RequestParam(required=true)java.lang.Integer employeeId,
			@RequestParam(required=true)java.lang.Integer projectId
			) {
		try {
			ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
			normTaskEmployeeDetailService.updatePass(EMPLOYEE.getEmployeeId(),normTaskId);
			String[] workContents = request.getParameterValues("workContent");
			String[] weights = request.getParameterValues("weight2");
			NormTaskProject normTaskProject =new NormTaskProject();
			Integer xmId=null;        //项目考核指标分类Id
			Employee emp = employeeService.findById(employeeId);
			
			//根据考核任务及岗位查询考核模板
			TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,emp.getStationId());
			Integer normTempId = taskStationTemplate.getNormTemplateId();
			
			//根据模板查询考核指标分类
			List<NormCategory> normCategoryList=normCategoryService.findByNormTempId(normTempId);
			
			//循环遍历考评指标分类
			for (NormCategory normCategory : normCategoryList) {
				if(normCategory.getNormCategoryName().equals("项目考核指标分类")){
					xmId = normCategory.getNormCategoryId();
				}
			}	
			
			//保存考核项目
			saveNormProject(normTaskId, employeeId, projectId, normTaskProject);
			
			//保存工作项内容
		    saveNormTaskPro(normTaskEmployee, normTaskId, employeeId, projectId, xmId, emp, normTaskProject,
					workContents, weights);
			
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	/**
	 * 保存编辑工作内容
	 * @param request
	 * @param normTaskProject
	 * @param normTaskEmployee
	 * @param normTaskId
	 * @param employeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveContent",method=RequestMethod.POST)
	public JsonResult saveContent(
			HttpServletRequest request,
			NormTaskEmployee normTaskEmployee,
			@RequestParam(required=true)java.lang.Integer normTaskId,
			@RequestParam(required=true)java.lang.Integer employeeId,
			@RequestParam(required=true)java.lang.Integer projectId
			) {
		try {
			ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
			normTaskEmployeeDetailService.updatePass(EMPLOYEE.getEmployeeId(),normTaskId);
			String[] workContents = request.getParameterValues("workContent");
			String[] weights = request.getParameterValues("weight2");
			String[] normTaskProjectIds = request.getParameterValues("normTaskProjectId");
			NormTaskProject normTaskProject =new NormTaskProject();
			Integer xmId=null;        //项目考核指标分类Id
			Employee emp = employeeService.findById(employeeId);
			//根据考核任务及岗位查询考核模板
			TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,emp.getStationId());
			Integer normTempId = taskStationTemplate.getNormTemplateId();
			
			//根据模板查询考核指标分类
			List<NormCategory> normCategoryList=normCategoryService.findByNormTempId(normTempId);
			
			//循环遍历考评指标分类
			for (NormCategory normCategory : normCategoryList) {
				if(normCategory.getNormCategoryName().equals("项目考核指标分类")){
					xmId = normCategory.getNormCategoryId();
				}
			}	
			
			//保存工作项内容
			updataNormTaskPro(normTaskEmployee, normTaskId, employeeId, projectId, xmId, emp, normTaskProject,
					workContents, weights ,normTaskProjectIds);
			
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}

	private void updataNormTaskPro(NormTaskEmployee normTaskEmployee, Integer normTaskId, Integer employeeId,
			Integer projectId, Integer xmId, Employee emp, NormTaskProject normTaskProject, String[] workContents,
			String[] weights, String[] normTaskProjectIds) {
		Integer normType;
		Integer scoreType;
		int j = normTaskProjectIds.length;
		for (int i = 0; i < workContents.length; i++) {
			normTaskProject.setEmployeeId(employeeId);
			normTaskProject.setNormTaskId(normTaskId);
			normTaskProject.setProjectId(projectId);
			normTaskProject.setWorkContent(workContents[i]);
			normTaskProject.setWeight2(Integer.valueOf(weights[i]));
			normTaskProject.setType(2);                                      // 类型  2  为工作内容
			if( i < j) {
				normTaskProject.setNormTaskProjectId(Integer.valueOf(normTaskProjectIds[i]));
				normTaskProjectService.update(normTaskProject);
			}else{
				normTaskProject.setNormTaskProjectId(null);
				int normTaskProjectId = normTaskProjectService.saveToPK(normTaskProject);
				//自评      *员工考核任务归档明细表*
				normType=3;    //考核类型   3.项目考评
				scoreType=1;   //评分人类型  1.自评 
				
				saveParam(normTaskEmployee, normTaskId, employeeId, projectId, xmId, normType, scoreType,
						normTaskProjectId,employeeId);
				//上级评       *员工考核任务归档明细表*
				/*scoreType=3;    //评分人类型  3.上级评
				saveParam(normTaskEmployee, normTaskId, employeeId, projectId, xmId, normType, scoreType,
						normTaskProjectId,emp.getLevelEmployeeId());*/
			}
		
		}
		
	}

	private void saveNormProject(java.lang.Integer normTaskId, java.lang.Integer employeeId,
			java.lang.Integer projectId, NormTaskProject normTaskProject) {
		normTaskProject.setEmployeeId(employeeId);
		normTaskProject.setNormTaskId(normTaskId);
		normTaskProject.setProjectId(projectId);
		normTaskProject.setType(1);                                      // 类型  1  为项目
		normTaskProjectService.save(normTaskProject);
	}

	private void saveNormTaskPro(NormTaskEmployee normTaskEmployee, java.lang.Integer normTaskId,
			java.lang.Integer employeeId, java.lang.Integer projectId, Integer xmId, Employee emp,
			NormTaskProject normTaskProject, String[] workContents, String[] weights) {
		Integer normType;
		Integer scoreType;
		for (int i = 0; i < workContents.length; i++) {
			normTaskProject.setEmployeeId(employeeId);
			normTaskProject.setNormTaskId(normTaskId);
			normTaskProject.setProjectId(projectId);
			normTaskProject.setWorkContent(workContents[i]);
			normTaskProject.setWeight2(Integer.valueOf(weights[i]));
			normTaskProject.setType(2);                                      // 类型  1  为工作内容
			int normTaskProjectId = normTaskProjectService.saveToPK(normTaskProject);
			 //自评      *员工考核任务归档明细表*
			normType=3;    //考核类型   3.项目考评
			scoreType=1;   //评分人类型  1.自评 
			
			saveParam(normTaskEmployee, normTaskId, employeeId, projectId, xmId, normType, scoreType,
					normTaskProjectId,employeeId);
			//上级评       *员工考核任务归档明细表*
			/*scoreType=3;    //评分人类型  3.上级评
			saveParam(normTaskEmployee, normTaskId, employeeId, projectId, xmId, normType, scoreType,
					normTaskProjectId,emp.getLevelEmployeeId());*/
		
		}
	}

	private void saveParam(NormTaskEmployee normTaskEmployee, java.lang.Integer normTaskId,
			java.lang.Integer employeeId, java.lang.Integer projectId, Integer xmId, Integer normType,
			Integer scoreType, int normTaskProjectId,int scoreEmployeeId) {
		normTaskEmployee.setEmployeeId(employeeId);
		normTaskEmployee.setNormTaskId(normTaskId);
		normTaskEmployee.setNormType(normType);
		normTaskEmployee.setCreateTime(new Date());
		normTaskEmployee.setScoreEmployeeId(scoreEmployeeId);
		normTaskEmployee.setScoreType(scoreType);
		normTaskEmployee.setProjectId(projectId);
		normTaskEmployee.setNormCategoryId(xmId);
		normTaskEmployee.setWorkContentId(normTaskProjectId);
		normTaskEmployeeService.save(normTaskEmployee);
	}
	
	/**
	 * 跳转编辑工作内容
	 * @param normTaskEmployee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/editContent",method=RequestMethod.POST)
	public JsonResult editContent(Integer employeeId,Integer normTaskId,Integer projectId){
		try {
			//根据考评任务和考评人员查询工作内容
			Map<String,Object> resultMap=new HashMap<>();
			List<NormTaskProject> project = normTaskProjectService.findContentById(employeeId, normTaskId, projectId,1);
			List<NormTaskProject> normTaskProjects = normTaskProjectService.findContentById(employeeId, normTaskId, projectId,2);
			resultMap.put("normTaskProjects", normTaskProjects);
			resultMap.put("normTaskId", normTaskId);
			resultMap.put("employeeId", employeeId);
			resultMap.put("projectId", projectId);
			resultMap.put("project", project);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		    return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
		
	}
	
	/**
	 * 删除工作内容
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteNormTaskProject",method=RequestMethod.POST)
	public JsonResult deleteNormTaskProject(
			@RequestParam(required=true)java.lang.Integer normTaskProjectId) {
		try {
			normTaskProjectService.delete(normTaskProjectId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
		    return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	/**
	 * 删除互评项目
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteEachProject",method=RequestMethod.POST)
	public JsonResult deleteEachProject(
			@RequestParam(required=true)java.lang.Integer normTaskId,
			@RequestParam(required=true)java.lang.Integer normTaskProjectId,
			@RequestParam(required=true)java.lang.Integer employeeId,
			@RequestParam(required=true)java.lang.Integer projectId) {
		try {
			ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
			normTaskEmployeeDetailService.updatePass(EMPLOYEE.getEmployeeId(),normTaskId);
			//删除项目
			normTaskProjectService.delEachProject(normTaskId,employeeId, projectId);
			//删除互评表
			taskProjectEmployeeService.delEachProject(normTaskId,employeeId, projectId);
			//删除任务明细表
			normTaskEmployeeService.delEachProject(normTaskId,employeeId, projectId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询NormTaskProject表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/normTaskProjectList",method=RequestMethod.GET)
	public String normTaskProjectList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required=true)java.lang.Integer normTaskId,
			@RequestParam(required=true)java.lang.Integer employeeId,
			@RequestParam(required=true)java.lang.Integer normTaskTmployeeDetailId,
			Model model) {
		ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Pager<NormTaskProject> page = normTaskProjectService.findPage(employeeId,normTaskId,currentPage, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("normTaskId",normTaskId);
		model.addAttribute("employeeId",employeeId);
		model.addAttribute("normTaskTmployeeDetailId",normTaskTmployeeDetailId);
		model.addAttribute("EMPLOYEE",EMPLOYEE);
		return "admin/task/normTaskProjectList";
	}
	/**
	 * 查询所有未被分配的项目
	 * @param normTaskId
	 * @param employeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/projectForNormTask",method=RequestMethod.POST)
	public JsonResult projectForNormTask(
			@RequestParam(required=true)java.lang.Integer normTaskId,
			@RequestParam(required=true)java.lang.Integer employeeId) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Project> projectList=projectService.findAllProject(normTaskId,employeeId);
			resultMap.put("projectList", projectList);
			resultMap.put("normTaskId", normTaskId);
			resultMap.put("employeeId", employeeId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 保存项目权重
	 * @param request
	 * @param normTaskProject
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveWeight",method=RequestMethod.POST)
	public JsonResult saveWeight(HttpServletRequest request,NormTaskProject normTaskProject) {
		try {
			String[] normTaskProjectIds = request.getParameterValues("normTaskProjectId");
			String[] weights = request.getParameterValues("weight");
			for (int i = 0; i < normTaskProjectIds.length; i++) {
				String normTaskProjectId = normTaskProjectIds[i];
				String weight=weights[i];
				normTaskProject.setNormTaskProjectId(Integer.valueOf(normTaskProjectId));
				normTaskProject.setWeight(Integer.valueOf(weight));
				normTaskProjectService.update(normTaskProject);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	
	/*
	 * 
	 *  //  无工作内容
	    @ResponseBody
		@RequestMapping(value="/saveProjectForEmp",method=RequestMethod.POST)
		public JsonResult saveProjectForEmp(
				HttpServletRequest request,
				NormTaskProject normTaskProject,
				NormTaskEmployee normTaskEmployee,
				@RequestParam(required=true)java.lang.Integer normTaskId,
				@RequestParam(required=true)java.lang.Integer employeeId
				) {
			try {
				Integer xmId=null;
				Integer normType=null;
				Integer scoreType=null;
				 //保存任务表    *员工考核任务归档明细表*	
				NormTask normtask = normTaskService.findById(normTaskId);
				Integer normTempId = normtask.getNormTempId();
				Employee emp = employeeService.findById(employeeId);
				//根据模板查询考核指标分类
				List<NormCategory> normCategoryList=normCategoryService.findByNormTempId(normTempId);
				
				//循环遍历考评指标分类
				for (NormCategory normCategory : normCategoryList) {
					if(normCategory.getNormCategoryName().equals("项目考核指标分类")){
						xmId = normCategory.getNormCategoryId();
					}
				}	
				List<Norm> xmNormList=normService.findNormByNormCatId(xmId);
				
				String[] projectIds = request.getParameterValues("projectId");
				for (int i = 0; i < projectIds.length; i++) {
					String projectId = projectIds[i];
					normTaskProject.setEmployeeId(employeeId);
					normTaskProject.setNormTaskId(normTaskId);
					normTaskProject.setProjectId(Integer.valueOf(projectId));
					normTaskProjectService.save(normTaskProject);
					
					 //自评
					normType=3;
					scoreType=1;
					extracted(normTaskEmployee, normTaskId, employeeId, normType, scoreType, employeeId,
							xmNormList,Integer.valueOf(projectId),xmId);
					
					//上级评
					scoreType=3;
					extracted(normTaskEmployee, normTaskId, employeeId, normType, scoreType, emp.getLevelEmployeeId(),
							xmNormList,Integer.valueOf(projectId),xmId);
				}
				return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
			}
		}
	  
	
	
	
	   private void extracted(NormTaskEmployee normTaskEmployee, java.lang.Integer normTaskId,
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
