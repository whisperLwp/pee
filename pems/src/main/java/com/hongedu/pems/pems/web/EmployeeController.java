
package com.hongedu.pems.pems.web;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
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
import com.hongedu.pems.util.shiro.ShiroEmployee;
import com.hongedu.pems.pems.entity.Dept;
import com.hongedu.pems.pems.entity.DeptVo;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.Station;
import com.hongedu.pems.pems.service.DeptService;
import com.hongedu.pems.pems.service.EmployeeService;
import com.hongedu.pems.pems.service.StationService;


/**
 * @author  
 * el_sys_employee 表对应的controller
 * 2018/01/31 02:37:13
 */
@Controller
@RequestMapping("/admin/employee")
public class EmployeeController {
	private final static Logger logger= LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private StationService stationService;
	
	/**
	 * 查询Employee详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findEmployee",method=RequestMethod.POST)
	public JsonResult findEmployee(
			@RequestParam(required=true)java.lang.Integer employeeId) {
		try {
			Employee employee = employeeService.findById(employeeId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", employee);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑Employee
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editEmployee",method=RequestMethod.GET)
	public String editEmployee(
			java.lang.Integer employeeId,
			Model model) {
		if(employeeId != null){
			Employee employee = employeeService.findById(employeeId);
			model.addAttribute("employee", employee);
		}
		List<DeptVo> deptList = deptService.findDeptOption();
		List<Station> stationList = stationService.findAllStation();
		model.addAttribute("deptList", deptList);
		model.addAttribute("stationList", stationList);
		return "admin/employee/editEmployee";
	}
	
	/**
	 * 保存Employee
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveEmployee",method=RequestMethod.POST)
	public JsonResult saveEmployee(
			Employee employee
			) {
		try {
			Integer employeeId=null;
			List<Employee> manager = employeeService.findEmpByRoleId(4);//总经理
			if(!manager.isEmpty()){
				Employee employee2 = manager.get(0);
				employeeId = employee2.getEmployeeId();
			}
			Station entity = new Station();
			if(employee.getEmployeeId()==null){
				if(employee.getLevelEmployeeId()==null){
					employee.setLevelEmployeeId(employeeId);
					employeeService.save(employee);
				}else{
					employeeService.save(employee);
				}
			}else{
				if(employee.getLevelEmployeeId()==null){
					employee.setLevelEmployeeId(employeeId);
				    employeeService.update(employee);
				}else{
					employeeService.update(employee);
				}
			}
			Integer stationId = employee.getStationId();
			entity.setStationId(stationId);
			entity.setReferFlag("0");
			stationService.update(entity);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除Employee
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteEmployee",method=RequestMethod.POST)
	public JsonResult deleteEmployee(
			@RequestParam(required=true)java.lang.Integer employeeId) {
		try {
			employeeService.delete(employeeId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询Employee表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/employeeList",method=RequestMethod.GET)
	public String employeeList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false,defaultValue="-1") Integer deptCode,
			Model model) {
		Pager<Employee> page=null;
		if(deptCode==-1){
			page = employeeService.findPage(name,currentPage, pageSize);
		}else{
			page = employeeService.findPage(name,deptCode,currentPage, pageSize);
		}
		List<DeptVo> deptList = deptService.findDeptOption();
		model.addAttribute("deptList", deptList);
		model.addAttribute("page", page);
		model.addAttribute("deptId", deptCode);
		model.addAttribute("name", name);
		return "admin/employee/employeeList";
	}
	
	/**
	 * 查询直属上级
	 * @param deptId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findLevel",method=RequestMethod.POST)
	public JsonResult findLevel(
			@RequestParam(required=true)java.lang.Integer deptId,
			@RequestParam(required=true)java.lang.Integer employeeId,
			@RequestParam(required=true)java.lang.Integer roleId
			) {
		try {
			Dept dept=new Dept();
			Employee employee = new Employee();
			dept = deptService.findLevel(String.valueOf(deptId));
			if(roleId==3||dept.getpDeptId().isEmpty()){  //普通员工查询部门负责人
				dept=dept;
			}else if(roleId==2){ //部门负责人查询父部门负责人
				dept = deptService.findLevel(dept.getpDeptId());
			}
		return new JsonResult(JsonResult.SUCCESS_CODE, "", dept);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	/**
	 * 重置密码
	 * @param employeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/reset/{employeeId}",method=RequestMethod.GET)
	public JsonResult resetPassword(
			@PathVariable("employeeId")Integer employeeId) {
		try {
			if(employeeId!=null){
				
				employeeService.resetPassword(employeeId);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
		} catch (Exception e) {
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/editPassword",method=RequestMethod.POST)
	public String editPassword(
			@RequestParam(required=false,defaultValue="")String oldPassword,
			@RequestParam(required=false,defaultValue="")String newPassword
			) {
		ShiroEmployee user =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Integer employeeId = user.getEmployeeId();
		Employee chickuser = employeeService.findById(employeeId);
		String encodeStr=DigestUtils.md5Hex(oldPassword);
		if(encodeStr.equals(chickuser.getPassword())){
			employeeService.editPassword(employeeId,newPassword);
		}else{
			return "false";
		}
		
		return "success";
		
	}
}
