
package com.hongedu.pems.pems.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongedu.pems.util.json.JsonResult;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.pems.entity.DeptVo;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.Project;
import com.hongedu.pems.pems.entity.ProjectEmployee;
import com.hongedu.pems.pems.service.DeptService;
import com.hongedu.pems.pems.service.EmployeeService;
import com.hongedu.pems.pems.service.ProjectEmployeeService;
import com.hongedu.pems.pems.service.ProjectService;


/**
 * @author  
 * el_bp_project_employee 表对应的controller
 * 2018/01/31 02:37:12
 */
@Controller
@RequestMapping("/admin/projectEmployee")
public class ProjectEmployeeController {
	private final static Logger logger= LoggerFactory.getLogger(ProjectEmployeeController.class);
	
	@Autowired
	private ProjectEmployeeService projectEmployeeService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DeptService deptService;
	
	/**
	 * 查询ProjectEmployee详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findProjectEmployee",method=RequestMethod.POST)
	public JsonResult findProjectEmployee(
			@RequestParam(required=true)java.lang.Integer projectId) {
		try {
			ProjectEmployee projectEmployee = projectEmployeeService.findById(projectId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", projectEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑ProjectEmployee
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editProjectEmployee",method=RequestMethod.GET)
	public String editProjectEmployee(
			java.lang.Integer projectId,
			Model model) {
		if(projectId != null){
			ProjectEmployee projectEmployee = projectEmployeeService.findById(projectId);
			model.addAttribute("projectEmployee", projectEmployee);
		}
		return "admin/project/editProjectEmployee";
	}
	
	/**
	 * 保存ProjectEmployee
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveProjectEmployee",method=RequestMethod.POST)
	public JsonResult saveProjectEmployee(
			ProjectEmployee projectEmployee
			) {
		try {
			if(projectEmployee.getProjectId() == null){
				projectEmployeeService.save(projectEmployee);
			}else{
				projectEmployeeService.update(projectEmployee);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除ProjectEmployee
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteProjectEmployee",method=RequestMethod.POST)
	public JsonResult deleteProjectEmployee(
			@RequestParam(required=true)java.lang.Integer projectId) {
		try {
			projectEmployeeService.delete(projectId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询ProjectEmployee表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value="/projectEmployeeList",method=RequestMethod.GET)
	public String projectEmployeeList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<ProjectEmployee> page = projectEmployeeService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/project/projectEmployeeList";
	}*/
	
	/**
	 * 根据项目Id查询项目成员
	 * @param currentPage
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ProEmpList",method=RequestMethod.GET)
	public String ProEmpList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required = true) Integer projectId,
			@RequestParam(required = true,defaultValue="") String name,
			Model model) {
		Pager<ProjectEmployee> page = projectEmployeeService.findPage(name,projectId,currentPage, pageSize);
		Project project = projectService.findById(projectId);
		model.addAttribute("page", page);
		model.addAttribute("project", project);
		model.addAttribute("name", name);
		return "admin/project/projectEmployeeList";
	}
	/**
	 * 查询所有没有在此项目中的人员
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/EmpForPro",method=RequestMethod.POST)
	public JsonResult EmpForPro(
			@RequestParam(required=true)java.lang.Integer projectId,String deptCode) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Employee> employeeList=employeeService.findAllEmp(projectId,deptCode);
			List<DeptVo> deptList = deptService.findDeptOption();
			resultMap.put("employeeList", employeeList);
			resultMap.put("projectId", projectId);
			resultMap.put("deptList", deptList);
			resultMap.put("deptCode", deptCode);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 保存项目员工关系
	 * @param projectId
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value="/saveProEmp",method=RequestMethod.POST)
	public JsonResult saveProEmp(HttpServletRequest request,ProjectEmployee projectEmployee,
			@RequestParam(required=true)java.lang.Integer projectId) {
		try {
			String[] employeeId = request.getParameterValues("employeeId");
			for (int i = 0; i < employeeId.length; i++) {
				projectEmployee.setEmployeeId(Integer.parseInt(employeeId[i]));
				projectEmployee.setProjectId(projectId);
				projectEmployeeService.save(projectEmployee);
			}
			
			return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	
}
