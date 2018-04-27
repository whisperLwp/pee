
package com.hongedu.pems.pems.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.hongedu.pems.pems.entity.DeptVo;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.Norm;
import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTaskEmployee;
import com.hongedu.pems.pems.entity.NormTaskEmployeeDetail;
import com.hongedu.pems.pems.entity.Project;
import com.hongedu.pems.pems.entity.TaskProjectEmployee;
import com.hongedu.pems.pems.entity.TaskStationTemplate;
import com.hongedu.pems.pems.service.DeptService;
import com.hongedu.pems.pems.service.EmployeeService;
import com.hongedu.pems.pems.service.NormCategoryService;
import com.hongedu.pems.pems.service.NormService;
import com.hongedu.pems.pems.service.NormTaskEmployeeDetailService;
import com.hongedu.pems.pems.service.NormTaskEmployeeService;
import com.hongedu.pems.pems.service.NormTaskService;
import com.hongedu.pems.pems.service.NormTemplateService;
import com.hongedu.pems.pems.service.ProjectService;
import com.hongedu.pems.pems.service.TaskProjectEmployeeService;
import com.hongedu.pems.pems.service.TaskStationTemplateService;


/**
 * @author  
 * el_bp_norm_task_employee_detail 表对应的controller
 * 2018/01/31 02:37:12
 */
@Controller
@RequestMapping("/admin/normTaskEmployeeDetail")
public class NormTaskEmployeeDetailController {
	private final static Logger logger= LoggerFactory.getLogger(NormTaskEmployeeDetailController.class);
	
	@Value("#{prop.localhost}")
	public String localhost;
	
	@Autowired
	private NormTaskEmployeeDetailService normTaskEmployeeDetailService;
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
	private ProjectService projectService;
	@Autowired
	private TaskProjectEmployeeService taskProjectEmployeeService;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private DeptService deptService;
	@Autowired
	private TaskStationTemplateService taskStationTemplateService;
	
	/**
	 * 查询NormTaskEmployeeDetail详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNormTaskEmployeeDetail",method=RequestMethod.POST)
	public JsonResult findNormTaskEmployeeDetail(
			@RequestParam(required=true)java.lang.Integer normTaskTmployeeDetailId) {
		try {
			NormTaskEmployeeDetail normTaskEmployeeDetail = normTaskEmployeeDetailService.findById(normTaskTmployeeDetailId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", normTaskEmployeeDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑NormTaskEmployeeDetail
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editNormTaskEmployeeDetail",method=RequestMethod.GET)
	public String editNormTaskEmployeeDetail(
			java.lang.Integer normTaskTmployeeDetailId,
			Model model) {
		if(normTaskTmployeeDetailId != null){
			NormTaskEmployeeDetail normTaskEmployeeDetail = normTaskEmployeeDetailService.findById(normTaskTmployeeDetailId);
			model.addAttribute("normTaskEmployeeDetail", normTaskEmployeeDetail);
		}
		return "admin/normTaskEmployeeDetail/editNormTaskEmployeeDetail";
	}
	
	/**
	 * 保存NormTaskEmployeeDetail
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveNormTaskEmployeeDetail",method=RequestMethod.POST)
	public JsonResult saveNormTaskEmployeeDetail(
			NormTaskEmployeeDetail normTaskEmployeeDetail
			) {
		try {
			if(normTaskEmployeeDetail.getNormTaskTmployeeDetailId() == null){
				normTaskEmployeeDetailService.save(normTaskEmployeeDetail);
			}else{
				normTaskEmployeeDetailService.update(normTaskEmployeeDetail);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除NormTaskEmployeeDetail
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteNormTaskEmployeeDetail",method=RequestMethod.POST)
	public JsonResult deleteNormTaskEmployeeDetail(
			NormTaskEmployeeDetail entity
			) {
		try {
			normTaskEmployeeDetailService.delete(entity.getNormTaskTmployeeDetailId());
			normTaskEmployeeService.delnNoTEmp(entity);
			
			
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询NormTaskEmployeeDetail表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/normTaskEmployeeDetailList",method=RequestMethod.GET)
	public String normTaskEmployeeDetailList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required=true)java.lang.Integer normTaskId,
			Model model) {
		List<Object> level=new ArrayList<>();
		List<Object> each=new ArrayList<>();
		Set<String> mails = new HashSet<>();
		ShiroEmployee employee = (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Integer roleId=employee.getRoleId();
		Integer employeeId=employee.getEmployeeId();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(roleId==1){  //如果是管理员  查看所有员工
			
			employeeId=0;
			List<NormTaskEmployeeDetail> list1 = normTaskEmployeeDetailService.findScoreList( normTaskId,employeeId);
			//判断评分状态
			mails=scoreFlag(normTaskId, list1, mails);
			
		    resultMap.put("admin", list1);
		}else if(roleId==2 || roleId==4){  //  领导  
			List<NormTaskEmployeeDetail> list2 = normTaskEmployeeDetailService.findScoreList( normTaskId,employeeId);
			mails=scoreFlag(normTaskId, list2, mails);
			resultMap.put("self", list2);
			List<Employee> employees = employeeService.findEmployeeByLevel(employeeId);
			for (Employee emp : employees) {
				Integer employeeId1 = emp.getEmployeeId();
				List<NormTaskEmployeeDetail> list3 = normTaskEmployeeDetailService.findScoreList( normTaskId,employeeId1);
				mails=scoreFlag(normTaskId, list3, mails);
				level.add(list3);
				resultMap.put("level", level);
			}
			List<TaskProjectEmployee> taskProjectEmployees= taskProjectEmployeeService.findEmpByEachEmp(normTaskId,employeeId);
			if(!taskProjectEmployees.isEmpty()){
				for (TaskProjectEmployee taskProjectEmployee : taskProjectEmployees) {
					Integer employeeId2 = taskProjectEmployee.getEmployeeId();
					List<NormTaskEmployeeDetail> list4 = normTaskEmployeeDetailService.findScoreList( normTaskId,employeeId2);
					mails=scoreEachFlag(normTaskId, list4, mails);
					each.add(list4);
					resultMap.put("each", each);
				}
			}
			
			
		}else if(roleId == 3){  //普通员工
			List<NormTaskEmployeeDetail> list1 = normTaskEmployeeDetailService.findScoreList( normTaskId,employeeId);
			resultMap.put("self", list1);
			//非管理员根据互评人查询
			List<TaskProjectEmployee> taskProjectEmployees= taskProjectEmployeeService.findEmpByEachEmp(normTaskId,employeeId);
			if(!taskProjectEmployees.isEmpty()){
				for (TaskProjectEmployee taskProjectEmployee : taskProjectEmployees) {
					Integer employeeId2 = taskProjectEmployee.getEmployeeId();
					List<NormTaskEmployeeDetail> list4 = normTaskEmployeeDetailService.findScoreList( normTaskId,employeeId2);
					mails=scoreEachFlag(normTaskId, list4, mails);
					each.add(list4);
					resultMap.put("each", each);
				}
			}
		}
		
		model.addAttribute("employeeId", employeeId);
		model.addAttribute("mails", mails);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("normTaskId",normTaskId);
		return "admin/task/normTaskEmployeeDetailList";
	}

	public Set<String> scoreFlag(java.lang.Integer normTaskId, List<NormTaskEmployeeDetail> list1,Set<String> mails) {
		
		for (NormTaskEmployeeDetail normTaskEmployeeDetail : list1) {  //循环考核归档明细表
			List<Integer> hr=new ArrayList<>();
			List<Integer> gs=new ArrayList<>();
			List<Integer> gl=new ArrayList<>();
			List<Integer> xs=new ArrayList<>();
			List<Integer> xl=new ArrayList<>();
			List<Integer> xe=new ArrayList<>();
			int hrF=1;
			int gsF=1;
			int glF=1;
			int xsF=1;
			int xeF=1;
			int xlF=1;
			//查询评分详情
			List<NormTaskEmployee> normTaskEmployees=normTaskEmployeeService.findScoreDetail(normTaskId, normTaskEmployeeDetail.getEmployeeId());
			for (NormTaskEmployee normTaskEmployee : normTaskEmployees) {
				if(normTaskEmployee.getNormType()==1 && normTaskEmployee.getScore()==null){ //判断人力资源是否评分
					hr.add(0);
				}else if(normTaskEmployee.getNormType()==2 && normTaskEmployee.getScoreType()==1 && normTaskEmployee.getScore()==null){ //判断岗位自评
					gs.add(0);
					mails.add(normTaskEmployee.getEmployee().getMailNum());
				}else if(normTaskEmployee.getNormType()==2 && normTaskEmployee.getScoreType()==3 && normTaskEmployee.getScore()==null){ //判断岗位上级是否评分
					gl.add(0);
					mails.add(normTaskEmployee.getEmployee().getMailNum());
				}else if(normTaskEmployee.getNormType()==3 && normTaskEmployee.getScoreType()==1 && normTaskEmployee.getScore()==null){ //判断项目自评
					xs.add(0);
					mails.add(normTaskEmployee.getEmployee().getMailNum());
				}else if(normTaskEmployee.getNormType()==3 && normTaskEmployee.getScoreType()==2 && normTaskEmployee.getScore()==null){ //判断项目互评
					xe.add(0);
					mails.add(normTaskEmployee.getEmployee().getMailNum());
				}else if(normTaskEmployee.getNormType()==3 && normTaskEmployee.getScoreType()==3 && normTaskEmployee.getScore()==null){ //判断项目上级评
					mails.add(normTaskEmployee.getEmployee().getMailNum());
					xl.add(0);
				}else if(normTaskEmployee.getNormType()==3 && normTaskEmployee.getScoreType()==1 && normTaskEmployee.getScore()!=null){ //判断项目自评
					xs.add(1);
				}else if(normTaskEmployee.getNormType()==3 && normTaskEmployee.getScoreType()==2 && normTaskEmployee.getScore()!=null){ //判断项目互评
					xe.add(1);
				}
				
			}
			if(!hr.isEmpty() && hr.contains(0)){
				hrF=0;
			}
			if(!gs.isEmpty() && gs.contains(0)){
				gsF=0;
			}
			if(!gl.isEmpty() && gl.contains(0)){
				glF=0;
			}
			if(xs.isEmpty() || xs.contains(0)){
				xsF=0;
			}
			if(xe.isEmpty() || xe.contains(0)){
				xeF=0;
			}
			if(xl.isEmpty() || xl.contains(0)){
				xlF=0;
			}
			normTaskEmployeeDetail.setGlF(glF);
			normTaskEmployeeDetail.setGsF(gsF);
			normTaskEmployeeDetail.setHrF(hrF);
			normTaskEmployeeDetail.setXeF(xeF);
			normTaskEmployeeDetail.setXlF(xlF);
			normTaskEmployeeDetail.setXsF(xsF);
			}
		return mails;
	}
	public Set<String> scoreEachFlag(java.lang.Integer normTaskId, List<NormTaskEmployeeDetail> list1,Set<String> mails) {
		ShiroEmployee employee = (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		for (NormTaskEmployeeDetail normTaskEmployeeDetail : list1) {  //循环考核归档明细表
			List<Integer> xe=new ArrayList<>();
			int xeF=1;
			//查询评分详情
			List<NormTaskEmployee> normTaskEmployees=normTaskEmployeeService.findEachScoreDetail(normTaskId, normTaskEmployeeDetail.getEmployeeId(),employee.getEmployeeId());
			for (NormTaskEmployee normTaskEmployee : normTaskEmployees) {
				if(normTaskEmployee.getNormType()==3 && normTaskEmployee.getScoreType()==2 && normTaskEmployee.getScore()==null){ //判断项目互评
					xe.add(0);
					mails.add(normTaskEmployee.getEmployee().getMailNum());
				}
				
			}
			if(!xe.isEmpty() && xe.contains(0)){
				xeF=0;
			}
			normTaskEmployeeDetail.setXeF(xeF);
		}
		return mails;
	}
	
	/**
	 * 已经归档数据
	 * @param currentPage
	 * @param pageSize
	 * @param normTaskId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hasNormTaskEmployeeDetailList",method=RequestMethod.GET)
	public String hasNormTaskEmployeeDetailList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required=true,defaultValue="")java.lang.Integer normTaskId,
			@RequestParam(required=true,defaultValue="")java.lang.String name,
			Model model) {
		Pager<NormTaskEmployeeDetail> page=null;
		ShiroEmployee employee = (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Integer roleId=employee.getRoleId();
		Integer employeeId=employee.getEmployeeId();
		List<Employee> employeeList = new ArrayList<>();
		if(roleId==1){  //如果是管理员  查看所有员工
			employeeId=0;
			page = normTaskEmployeeDetailService.findPage(name,normTaskId,employeeId,currentPage, pageSize);
		}else if(roleId==2){
			String deptId = employee.getDeptId();
			employeeList = employeeService.findEmployeeByDept(deptId);
			employeeId=0;
			page = normTaskEmployeeDetailService.findPage(name,normTaskId,employeeId,currentPage, pageSize);
		}else{
			page = normTaskEmployeeDetailService.findPage(name,normTaskId,employeeId,currentPage, pageSize);
		}
		model.addAttribute("page", page);
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("normTaskId",normTaskId);
		model.addAttribute("name",name);
		return "admin/task/hasNormTaskEmployeeDetailList";
	}
	
	/**
	 * 查询所有员工未被分配到此项目的员工
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/EmpForNormTask",method=RequestMethod.POST)
	public JsonResult EmpForPro(
			@RequestParam(required=true)java.lang.Integer normTaskId,String deptCode) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Employee> employeeList=employeeService.findAllNoEmp(normTaskId,deptCode);
			List<DeptVo> deptList = deptService.findDeptOption();
			resultMap.put("employeeList", employeeList);
			resultMap.put("deptList", deptList);
			resultMap.put("normTaskId", normTaskId);
			resultMap.put("deptCode", deptCode);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	//保存对应关系
	@ResponseBody
	@RequestMapping(value="/saveTaskEmp",method=RequestMethod.POST)
	public JsonResult saveProEmp(HttpServletRequest request,NormTaskEmployeeDetail detail,
			NormTaskEmployee normTaskEmployee,
			@RequestParam(required=true)java.lang.Integer normTaskId) {
		try {
			Integer hrId=null;
			Integer gwId=null;
			NormTask entity=new NormTask();
			entity.setNormTaskId(normTaskId);
			entity.setReferFlag("1");
			normTaskService.update(entity);     //修改考核任务引用状态
			String[] employeeIds = request.getParameterValues("employeeId");
			List<Employee> noTemplateEmp = new ArrayList<>();
			for (String employeeId : employeeIds) {
				//根据考核任务查询考核模板
				Employee emp = employeeService.findById(employeeId);
				TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,emp.getStationId());
				Integer normTempId = taskStationTemplate.getNormTemplateId();
				if(normTempId!=null){
					
					//查询员工  获取直属领导
					
					detail.setEmployeeId(Integer.valueOf(employeeId));
					detail.setNormTaskId(normTaskId);
					String contextPath = request.getContextPath();
					String url=localhost+contextPath+"login/pems";
					mailManager.sendMail("您有新的考核任务需要完成，快去看看吧","",emp.getMailNum());
					/*mailManager.sendMail("您有新的考核任务需要完成，快去看看吧","<html lang='zh-CN'><head ><meta charset='utf-8'></head><body>点击此链接修改密码<a href='"+url+"'>【点击进入登录页面】</a></body></html>",emp.getMailNum());*/
					//保存考评任务详情   *员工考核任务归档明细表*
					normTaskEmployeeDetailService.save(detail);
					
					//根据模板查询考核指标分类
					List<NormCategory> normCategoryList=normCategoryService.findByNormTempId(normTempId);
					
					//循环遍历考评指标分类
					for (NormCategory normCategory : normCategoryList) {
						if(normCategory.getNormCategoryName().equals("人力资源指标分类")){
							
							//获取人力考核指标Id
							hrId = normCategory.getNormCategoryId();
						}else if(normCategory.getNormCategoryName().equals("岗位考核指标分类")){
							
							//获取岗位考核指标Id
							gwId = normCategory.getNormCategoryId();
						}else if(normCategory.getNormCategoryName().equals("项目考核指标分类")){
							Integer xmId = normCategory.getNormCategoryId();
						}
					}
					Integer scoreEmpId=null;
					Integer normType=null;
					Integer scoreType=null;
					//查询人力考核指标
					if(hrId != null){
						scoreEmpId=Integer.valueOf(employeeId);
						normType=1;
						scoreType=1;
						extracted(normTaskEmployee, normTaskId, hrId,normType, employeeId,scoreEmpId,scoreType);
					}
					if(gwId != null){
						//自评
						normType=2;
						scoreType=1;
						scoreEmpId=Integer.valueOf(employeeId);
						extracted(normTaskEmployee, normTaskId, gwId,normType, employeeId,scoreEmpId,scoreType);
						//上级评
						scoreType=3;
						scoreEmpId=emp.getLevelEmployeeId();
						extracted(normTaskEmployee, normTaskId, gwId,normType, employeeId,scoreEmpId,scoreType);
					}
				}else{
					noTemplateEmp.add(emp);
				}
			}
			
			return new JsonResult(JsonResult.SUCCESS_CODE, "", noTemplateEmp);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
    //保存员工考核任务表公共方法  
	private void extracted(NormTaskEmployee normTaskEmployee, java.lang.Integer normTaskId, Integer Id, java.lang.Integer normType,
			String employeeId,Integer scoreEmpId,Integer scoreType) {
		List<Norm> hrNormList=normService.findNormByNormCatId(Id);
		for (Norm norm : hrNormList) {
			Integer normId = norm.getNormId();
			normTaskEmployee.setNormId(normId);
			normTaskEmployee.setEmployeeId(Integer.valueOf(employeeId));
		    normTaskEmployee.setNormTaskId(normTaskId);
		    normTaskEmployee.setNormType(normType);
		    normTaskEmployee.setCreateTime(new Date());
		    normTaskEmployee.setScoreEmployeeId(scoreEmpId);
		    normTaskEmployee.setScoreType(scoreType);
		    normTaskEmployee.setNormCategoryId(Id);
			normTaskEmployeeService.save(normTaskEmployee);
		}
	}
	/**
	 * 保存员工考核项目关系
	 * @param normTaskId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveProjectTsak",method=RequestMethod.POST)
	public JsonResult saveProjectTsak(
			@RequestParam(required=true)java.lang.Integer normTaskId,
			@RequestParam(required=true)java.lang.Integer employeeId) {
		try {
			
			
		   return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询所有项目
	 * @param projectId
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value="/projectForNormTask",method=RequestMethod.POST)
	public JsonResult projectForNormTask(
			) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Project> projectList=projectService.findAllProject();
			resultMap.put("projectList", projectList);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}*/
	
	
	/*@RequestMapping(value="/showProject",method=RequestMethod.GET)
	public String showProject(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<Project> page = projectService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/task/taskProjectList";
	}*/
	
	@ResponseBody
	@RequestMapping(value="/pass",method=RequestMethod.POST)
	public JsonResult pass(
			NormTaskEmployeeDetail entity) {
		try {
			normTaskEmployeeDetailService.update(entity);
			if(entity.getPassFlag().equals("1")){
				Employee employee = employeeService.findById(entity.getEmployeeId());
				Set<Integer> set = new HashSet<>();
				List<TaskProjectEmployee> findEachEmpForEmail = taskProjectEmployeeService.findEachEmpForEmail(entity.getEmployeeId(), entity.getNormTaskId());
				for (TaskProjectEmployee taskProjectEmployee : findEachEmpForEmail) {
					set.add(taskProjectEmployee.getEachEmployeeId());
				}
				for (Integer eachEmp : set) {
					Employee emp = employeeService.findById(eachEmp);
					mailManager.sendMail("员工"+employee.getRealName()+"需要你互评，快去看看吧","",emp.getMailNum());
				}
			}
			
			
			
			return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
}
