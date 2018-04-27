
package com.hongedu.pems.pems.web;

import java.util.List;

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
import com.hongedu.pems.pems.entity.DeptVo;
import com.hongedu.pems.pems.entity.Project;
import com.hongedu.pems.pems.service.DeptService;
import com.hongedu.pems.pems.service.ProjectService;


/**
 * @author  
 * el_bp_project 表对应的controller
 * 2018/01/31 02:37:12
 */
@Controller
@RequestMapping("/admin/project")
public class ProjectController {
	private final static Logger logger= LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DeptService deptService;
	
	/**
	 * 查询Project详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findProject",method=RequestMethod.POST)
	public JsonResult findProject(
			@RequestParam(required=true)java.lang.Integer projectId) {
		try {
			Project project = projectService.findById(projectId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", project);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑Project
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editProject",method=RequestMethod.GET)
	public String editProject(
			java.lang.Integer projectId,
			Model model) {
		if(projectId != null){
			Project project = projectService.findById(projectId);
			model.addAttribute("project", project);
		}
		/*List<DeptVo> deptList = deptService.findDeptOption();
		model.addAttribute("deptList", deptList);*/
		return "admin/project/editProject";
	}
	
	/**
	 * 保存Project
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveProject",method=RequestMethod.POST)
	public JsonResult saveProject(
			Project project
			) {
		try {
			ShiroEmployee employee = (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
			if(project.getProjectId() == null){
				project.setDeptCode(employee.getDeptId());
				projectService.save(project);
			}else{
				projectService.update(project);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除Project
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteProject",method=RequestMethod.POST)
	public JsonResult deleteProject(
			@RequestParam(required=true)java.lang.Integer projectId) {
		try {
			projectService.delete(projectId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询Project表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/projectList",method=RequestMethod.GET)
	public String projectList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required=false,defaultValue="")String name,
			@RequestParam(required=false,defaultValue="")Integer status,
			Model model) {
		ShiroEmployee employee = (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Integer roleId = employee.getRoleId();
		String deptCode = employee.getDeptId();
		Pager<Project> page=null;
		if(roleId==1){
			deptCode="";
			page = projectService.findPage(name,status,deptCode,currentPage, pageSize);
		}else{
			page = projectService.findPage(name,status,deptCode,currentPage, pageSize);
		}
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute("name", name);
		return "admin/project/projectList";
	}
}
