
package com.hongedu.pems.pems.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.hongedu.pems.pems.entity.Dept;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.service.DeptService;
import com.hongedu.pems.pems.service.EmployeeService;


/**
 * @author  
 * el_sys_dept 表对应的controller
 * 2018/01/31 02:37:13
 */
@Controller
@RequestMapping("/admin/dept")
public class DeptController {
	private final static Logger logger= LoggerFactory.getLogger(DeptController.class);
	
	@Autowired
	private DeptService deptService;
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 查询Dept详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findDept",method=RequestMethod.POST)
	public JsonResult findDept(
			@RequestParam(required=true)java.lang.Integer deptId) {
		try {
			Dept dept = deptService.findById(deptId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", dept);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑Dept
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editDept",method=RequestMethod.GET)
	public String editDept(
			java.lang.Integer deptId,
			Model model) {
		List<Dept> deptList=deptService.findDept();
		if(deptId != null){
			Dept dept = deptService.findById(deptId);
			model.addAttribute("dept", dept);
			model.addAttribute("edit", "edit");
		}
		List<Employee> employeeList=employeeService.findEmpByRoleId(2);
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("deptList", deptList);
		return "admin/dept/editDept";
	}
	
	/**
	 * 保存Dept
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveDept",method=RequestMethod.POST)
	public JsonResult saveDept(
			Dept dept
			) {
		try {
			if(dept.getDeptId() == null ){
				Integer deptC=null;
				String deptCode=deptService.findDeptCode(dept.getpDeptId());
				if(StringUtils.isNotEmpty(deptCode)){
					deptC=Integer.valueOf(deptCode);
				}
				if(dept.getDeptId() == null && StringUtils.isEmpty(dept.getpDeptId())){
					if(StringUtils.isEmpty(deptCode)){
						dept.setDeptCode("1001");
					}else {
						dept.setDeptCode(String.valueOf(deptC+1));
					}
					deptService.save(dept);
				}else if(StringUtils.isNotEmpty(dept.getpDeptId())){
					if(StringUtils.isEmpty(deptCode)){
						dept.setpDeptId(dept.getpDeptId());
						String code=dept.getpDeptId()+"001";
						dept.setDeptCode(code);
					}else {
						dept.setpDeptId(dept.getpDeptId());
						dept.setDeptCode(String.valueOf(deptC+1));
					}
					deptService.save(dept);
				}
			}else{
				deptService.update(dept);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除Dept
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteDept",method=RequestMethod.POST)
	public JsonResult deleteDept(
			@RequestParam(required=true)java.lang.Integer deptId) {
		try {
			deptService.delete(deptId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询Dept表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/deptList",method=RequestMethod.GET)
	public String deptList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required = false, defaultValue = "") String deptName,
			Model model) {
		Pager<Dept> page = deptService.findPage(deptName,currentPage, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("deptName", deptName);
		return "admin/dept/deptList";
	}
}
