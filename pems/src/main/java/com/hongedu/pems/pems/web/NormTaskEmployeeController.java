
package com.hongedu.pems.pems.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.util.shiro.ShiroEmployee;

import net.sf.json.util.NewBeanInstanceStrategy;

import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.Norm;
import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTaskEmployee;
import com.hongedu.pems.pems.entity.NormTaskEmployeeDetail;
import com.hongedu.pems.pems.entity.NormTaskProject;
import com.hongedu.pems.pems.entity.TaskProjectEmployee;
import com.hongedu.pems.pems.entity.TaskStationTemplate;
import com.hongedu.pems.pems.service.EmployeeService;
import com.hongedu.pems.pems.service.NormCategoryService;
import com.hongedu.pems.pems.service.NormService;
import com.hongedu.pems.pems.service.NormTaskEmployeeDetailService;
import com.hongedu.pems.pems.service.NormTaskEmployeeService;
import com.hongedu.pems.pems.service.NormTaskProjectService;
import com.hongedu.pems.pems.service.NormTaskService;
import com.hongedu.pems.pems.service.TaskProjectEmployeeService;
import com.hongedu.pems.pems.service.TaskStationTemplateService;


/**
 * @author  
 * el_bp_norm_task_employee 表对应的controller
 * 2018/01/31 02:37:12
 */
@Controller
@RequestMapping("/admin/normTaskEmployee")
public class NormTaskEmployeeController {
	private final static Logger logger= LoggerFactory.getLogger(NormTaskEmployeeController.class);
	
	@Autowired
	private NormTaskEmployeeService normTaskEmployeeService;
	@Autowired
	private NormService normService;
	@Autowired
	private NormTaskProjectService normTaskProjectService;
	@Autowired
	private TaskProjectEmployeeService taskProjectEmployeeService;
	@Autowired
	private NormCategoryService normCategoryService;
	@Autowired
	private NormTaskService normTaskService;
	@Autowired
	private NormTaskEmployeeDetailService normTaskEmployeeDetailService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskStationTemplateService taskStationTemplateService;
	
	/**
	 * 查询NormTaskEmployee详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNormTaskEmployee",method=RequestMethod.POST)
	public JsonResult findNormTaskEmployee(
			@RequestParam(required=true)java.lang.Integer normTaskEmployeeId) {
		try {
			NormTaskEmployee normTaskEmployee = normTaskEmployeeService.findById(normTaskEmployeeId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", normTaskEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑NormTaskEmployee
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editNormTaskEmployee",method=RequestMethod.GET)
	public String editNormTaskEmployee(
			java.lang.Integer normTaskEmployeeId,
			Model model) {
		if(normTaskEmployeeId != null){
			NormTaskEmployee normTaskEmployee = normTaskEmployeeService.findById(normTaskEmployeeId);
			model.addAttribute("normTaskEmployee", normTaskEmployee);
		}
		return "admin/normTaskEmployee/editNormTaskEmployee";
	}
	
	/**
	 * 保存评分   ？  归档结算总分版本
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveNormTaskEmployee",method=RequestMethod.POST)
	public JsonResult saveNormTaskEmployee(HttpServletRequest request,
			NormTaskEmployee normTaskEmployee,java.lang.Integer normTaskId,java.lang.Integer employeeId
			) {
		try {
			String[] eachEmployeeIds = request.getParameterValues("normTaskEmployeeId");
			String[] scores = request.getParameterValues("score");
			for (int i = 0; i < scores.length; i++) {
				String score = scores[i];
				String normTaskEmployeeId=eachEmployeeIds[i];
				if(StringUtils.isNotEmpty(score)){
					normTaskEmployee.setScore(Integer.valueOf(score));
				}
				normTaskEmployee.setNormTaskEmployeeId(Integer.valueOf(normTaskEmployeeId));
				normTaskEmployeeService.update(normTaskEmployee);
				//calculate(employeeId,normTaskId);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", normTaskId);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	
	/**
	 * 每次评分  计算得分
	 * @param request
	 * @param normTaskEmployee
	 * @param normTaskId
	 * @param employeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveScore",method=RequestMethod.POST)
	public JsonResult saveScore(HttpServletRequest request,
			NormTaskEmployee normTaskEmployee
		
			) {
		try {
			String[] eachEmployeeIds = request.getParameterValues("normTaskEmployeeId");
			String[] scores = request.getParameterValues("score");
			for (int i = 0; i < scores.length; i++) {
				String score = scores[i];
				String normTaskEmployeeId=eachEmployeeIds[i];
				if(StringUtils.isNotEmpty(score)){
					normTaskEmployee.setScore(Integer.valueOf(score));
				}
				normTaskEmployee.setNormTaskEmployeeId(Integer.valueOf(normTaskEmployeeId));
				normTaskEmployeeService.update(normTaskEmployee);
				//calculate(employeeId,normTaskId);
			}
			//员工考核任务表  根据主键查询
			NormTaskEmployee entity = normTaskEmployeeService.findById(eachEmployeeIds[0]);
			Integer normTaskId = entity.getNormTaskId();
			Integer employeeId = entity.getEmployeeId();
			//获取评分人类型
			Integer normType = entity.getNormType();
			//获取考核指标Id
			Integer normCategoryId = entity.getNormCategoryId();
			//根据考核指标id查询考核指标
			NormCategory normCategory = normCategoryService.findById(normCategoryId);
			float totalScore=0;
			if(normType==1){
				//人事评分
				float hrWeight = (float)normCategory.getWeight()/100;
				List<Norm> hrNormList = normService.findNormByNormCatId(normCategoryId);
				List<NormTaskEmployee> normTaskEmployeeList=normTaskEmployeeService.findOneScoreDetail(normTaskId, employeeId,normType);
			    for (NormTaskEmployee normTaskEmployee2 : normTaskEmployeeList) {
			    	for (Norm norm1 : hrNormList) {
						if(norm1.getNormId().equals(normTaskEmployee2.getNormId())){
							Integer score = normTaskEmployee2.getScore();
							if(score != null){
								totalScore += ((float)score*norm1.getWeight()/100);
							}
						}
					}
				}	
			totalScore=totalScore*hrWeight;
			//保存人事得分
			
			
			}
			if(normType==2){
				//判断岗位是否全部评分
				List<NormTaskEmployee> normTaskEmployeeList=normTaskEmployeeService.findOneScoreDetail(normTaskId, employeeId,normType);
				for (NormTaskEmployee normTaskEmployee2 : normTaskEmployeeList) {
					if(normTaskEmployee2.getScoreType()==2&&normTaskEmployee2.getScore()==null){
						//将岗位
					}
				}
				
				
			}
			if(normType==3){
				//人事评分
			}
			
			
			
			
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 归档操作
	 * @param normTaskEmployeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveAll",method=RequestMethod.POST)
	public JsonResult saveAll(
			@RequestParam(required=true)java.lang.Integer normTaskId,NormTask entity) {
		try {
			//查询所有考核人员
			List<NormTaskEmployeeDetail> nTaskEmployeeDetails= normTaskEmployeeDetailService.findAllEmployee(normTaskId);
			if(!nTaskEmployeeDetails.isEmpty()){
				for (NormTaskEmployeeDetail normTaskEmployeeDetail : nTaskEmployeeDetails) {
					calculate(normTaskEmployeeDetail);
				}
			}
			entity.setStatus(4);
			normTaskService.update(entity);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	
	/**
	 * 计算考核总分
	 * @param employeeId
	 * @param normTaskId
	 */
	public void calculate(NormTaskEmployeeDetail entity) {
		Integer employeeId = entity.getEmployeeId();
		Integer normTaskId = entity.getNormTaskId();
		//查询员工  获取直属领导
		Employee emp = employeeService.findById(employeeId);
		//查询权重
		
		//根据考核任务查询考核模板
		TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,emp.getStationId());
		Integer normTempId = taskStationTemplate.getNormTemplateId();
		
		//查询考核指标
		
		List<NormCategory> normCategoryList = normCategoryService.findByNormTempId(normTempId);
		
		//查询具体得分
		
		List<NormTaskEmployee> normTaskEmployeeList=normTaskEmployeeService.findScoreDetail(normTaskId, employeeId);
		
		//查询考核项目
		Integer type=1;
		List<NormTaskProject> normTaskProjects = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		
		//查询员工工作内容 
		type=2;
		List<NormTaskProject> normTaskProjectList2 = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		/*if(normTaskProjectList2!=null){
			for (NormTaskProject normTaskProject : normTaskProjectList2) {
				taskProjectEmployeeService.findTaskPeojectEmployee(employeeId,normTaskId);
			}
		}*/
		
		//查询互评人员
		
		//List<TaskProjectEmployee> taskProjectEmployees=taskProjectEmployeeService.findTaskPeojectEmployee(employeeId,normTaskId);
		
		//人事
		float hrWeight=0;             //人力评分权重
		List<Norm> hrNormList=null;     //人力考核指标
		//岗位
		List<Norm> gwNormList=null;     // 岗位考核指标
		float gwWeight=0;             // 岗位考核权重
		float gwSelfWeight=0;         // 岗位自评权重
		float gwLevelWeight=0;        // 岗位上级评分权重
		
		//项目
		//List<Norm> xmNormList=null;     // 项目考核指标
		float xmWeight=0;             // 项目考核权重
		float xmSelfWeight=0;         // 项目自评权重
		float xmLevelWeight=0;        // 项目上级评分权重
		float xmEachWeight=0;         // 项目互评权重
		//获取考核指标权重
		if(!normCategoryList.isEmpty()){
			for (NormCategory normCategory : normCategoryList) {
				if(normCategory.getType()==1){
					hrNormList = normService.findNormByNormCatId(normCategory.getNormCategoryId());
					hrWeight=(float)normCategory.getWeight()/100;
				}else if(normCategory.getType()==2){
					gwNormList = normService.findNormByNormCatId(normCategory.getNormCategoryId());
					gwWeight=(float)normCategory.getWeight()/100;
					gwSelfWeight=(float)normCategory.getSelfWeight()/100;
					gwLevelWeight=(float)normCategory.getLevelWeight()/100;
				}else if(normCategory.getType()==3){
					/*xmNormList = normService.findNormByNormCatId(normCategory.getNormCategoryId());*/
					xmWeight=(float)normCategory.getWeight()/100;
					xmSelfWeight=(float)normCategory.getSelfWeight()/100;
					xmLevelWeight=(float)normCategory.getLevelWeight()/100;
					xmEachWeight=(float)normCategory.getEachWeight()/100;
				}
			}
		}
		
		//计算人事得分
		float hrScore = hrScore(normTaskEmployeeList, hrNormList);	
			
		//计算岗位得分	
		float gwSelfScore = gwScore(1, normTaskEmployeeList, gwNormList);
		float gwLevelScore = gwScore(3, normTaskEmployeeList, gwNormList);	
		
		//项目分
		float xmSelfScore=0;
		float xmEachScore=0;
		float xmLevelScore=0;
		/*if(!normTaskProjects.isEmpty()){
			xmSelfScore = xmScore(1,normTaskEmployeeList, normTaskProjects, xmNormList);
			xmEachScore = xmEachScore(2,normTaskEmployeeList, normTaskProjects, xmNormList,taskProjectEmployees);
			xmLevelScore = xmScore(3,normTaskEmployeeList, normTaskProjects, xmNormList);
		}*/
		if(!normTaskProjects.isEmpty()){
			xmSelfScore = xmScore(1,normTaskEmployeeList, normTaskProjects, normTaskProjectList2);
			xmEachScore = xmEachScore(2,normTaskEmployeeList, normTaskProjects, normTaskProjectList2,employeeId,normTaskId);
			xmLevelScore = xmScore(3,normTaskEmployeeList, normTaskProjects, normTaskProjectList2);
		}
		
		
		System.out.println("人力"+hrScore);
		System.out.println("岗位自评"+gwSelfScore);
		System.out.println("岗位上级"+gwLevelScore);
		System.out.println("项目自评"+xmSelfScore);
		System.out.println("项目互评"+xmEachScore);
		System.out.println("项目上级"+xmLevelScore);
		float hScore=hrScore*hrWeight;
		float gwsScore=gwSelfScore*gwSelfWeight;
		float gwlScore=gwLevelScore*gwLevelWeight;
		float xmsScore=xmSelfScore*xmSelfWeight;
		float xmeScore=xmEachScore*xmEachWeight;
		float xmlScore=xmLevelScore*xmLevelWeight;
		
		entity.setHrScore(hrScore*hrWeight);                       //人力得分
		entity.setDeptSelfScore(gwSelfScore*gwSelfWeight*gwWeight);         //岗位自评
		entity.setDeptLevelScore(gwLevelScore*gwLevelWeight*gwWeight);      //岗位上级得分
		entity.setProjectSelfScore(xmSelfScore*xmSelfWeight*xmWeight);
		entity.setProjectEachScore(xmEachScore*xmEachWeight*xmWeight);
		entity.setProjectLevelScore(xmLevelScore*xmLevelWeight*xmWeight);
		entity.setScore(hScore+(gwsScore+gwlScore)*gwWeight+(xmsScore+xmeScore+xmlScore)*xmWeight);
		
		
		normTaskEmployeeDetailService.update(entity);
		
	}

	

	private float hrScore(List<NormTaskEmployee> normTaskEmployeeList, List<Norm> hrNormList) {
		float totalScore = 0;
		if(!normTaskEmployeeList.isEmpty() && !hrNormList.isEmpty()){
			for (NormTaskEmployee normTaskEmployee : normTaskEmployeeList) {
				if(normTaskEmployee.getNormType() == 1){ //人力
					for (Norm norm : hrNormList) {
						if((normTaskEmployee.getNormId()).equals(norm.getNormId())){
							Integer score = normTaskEmployee.getScore();
							if(score != null){
								totalScore += ((float)score*norm.getWeight()/100);
							}
						}
					}
					
				}
			}
		}
		return totalScore;
	}

	private float gwScore(int scoreType, List<NormTaskEmployee> normTaskEmployeeList, List<Norm> gwNormList) {
		float totalScore=0;           // 岗位1.自评得分  3.上级评分
		if(!normTaskEmployeeList.isEmpty() && !gwNormList.isEmpty()){
			for (NormTaskEmployee normTaskEmployee : normTaskEmployeeList) {	
				if((normTaskEmployee.getNormType() == 2) &&//岗位
						(normTaskEmployee.getScoreType() == scoreType)){ //打分分类：1自评、3上级
					for (Norm norm : gwNormList) {
						if((normTaskEmployee.getNormId()).equals(norm.getNormId())){
							Integer score = normTaskEmployee.getScore();
							if(score!=null){
								totalScore += ((float)score*norm.getWeight()/100);
							}
						}
					}
				}
			}
		}
		return totalScore;
	}
	
	private float xmScore(int scoreType,List<NormTaskEmployee> normTaskEmployeeList, List<NormTaskProject> normTaskProjects,
			List<NormTaskProject> normTaskProjectList2) {
		float totalScore=0; 
		//计算项目得分	
		if(!normTaskEmployeeList.isEmpty() && !normTaskProjects.isEmpty() && !normTaskProjectList2.isEmpty()){
			for (NormTaskEmployee normTaskEmployee : normTaskEmployeeList) {
				if(normTaskEmployee.getNormType() == 3){//项目
					for (int i = 0; i < normTaskProjects.size(); i++) {  //考核项目
						if( normTaskProjects.get(i).getProjectId().equals(normTaskEmployee.getProjectId())){ //判断同一项目
							if(normTaskEmployee.getScoreType() == scoreType ){ //项目1.自评  3.上级评
								for (NormTaskProject normTaskProject : normTaskProjectList2) {
									if((normTaskEmployee.getWorkContentId()).equals(normTaskProject.getNormTaskProjectId())){
										float normTaskProjectWeight=(float)(normTaskProject.getWeight2())/100;      //考核指标权重
										float projectWeight=(float)(normTaskProjects.get(i).getWeight())/100;      //考核项目权重
										Integer score = normTaskEmployee.getScore();
										if(score!=null){
											totalScore+=((float)score*normTaskProjectWeight*projectWeight);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return totalScore;
	}
	
	private float xmEachScore(int scoreType,List<NormTaskEmployee> normTaskEmployeeList, List<NormTaskProject> normTaskProjects,
			List<NormTaskProject> normTaskProjectList2,Integer employeeId,Integer normTaskId) {
		float totalScore=0;
		float projectScore=0;
		//计算项目得分	
		if(!normTaskEmployeeList.isEmpty() && !normTaskProjects.isEmpty() && !normTaskProjectList2.isEmpty()){
			for (NormTaskProject normTaskProject : normTaskProjects) {  // 循环考核项目集合
				projectScore = oneXmEachScore(scoreType, normTaskEmployeeList, normTaskProjectList2, employeeId,normTaskId,
						normTaskProject);
				totalScore+=projectScore;
			}
		}
		return totalScore;
	}

	private float oneXmEachScore(int scoreType, List<NormTaskEmployee> normTaskEmployeeList,List<NormTaskProject> normTaskProjectList2,
			 Integer employeeId,Integer normTaskId,  NormTaskProject normTaskProject) {
		float oneScore=0;
		float oneContentScore=0;
		for (NormTaskProject normTaskProject1 : normTaskProjectList2) {  //循环工作内容表
			if(normTaskProject1.getProjectId().equals(normTaskProject.getProjectId())){
				List<TaskProjectEmployee> findTaskPeojectEmployee = taskProjectEmployeeService.findTaskPeojectEmployee(employeeId,normTaskId,normTaskProject1.getNormTaskProjectId(),normTaskProject1.getProjectId());
				oneContentScore = onWorkContent(scoreType, normTaskEmployeeList, normTaskProjectList2, normTaskProject,
						 normTaskProject1, findTaskPeojectEmployee);
				oneScore+=oneContentScore;
			}
		}
		return oneScore;
	}

	private float onWorkContent(int scoreType, List<NormTaskEmployee> normTaskEmployeeList,
			List<NormTaskProject> normTaskProjectList2, NormTaskProject normTaskProject,
			NormTaskProject normTaskProject1, List<TaskProjectEmployee> findTaskPeojectEmployee) {
		Integer employeeCount = 0;
		float oneWorkScore=0;
		for (TaskProjectEmployee taskProjectEmployee : findTaskPeojectEmployee) {  //循环互评人员表
			employeeCount ++;
			for (NormTaskEmployee normTaskEmployee : normTaskEmployeeList) {  //循环评分表
				if(normTaskEmployee.getScoreType()==scoreType && normTaskEmployee.getScoreEmployeeId().equals(taskProjectEmployee.getEachEmployeeId())
						&& normTaskEmployee.getWorkContentId().equals(normTaskProject1.getNormTaskProjectId())){
					float projectWeight=(float)(normTaskProject.getWeight())/100;
					float normTaskProjectWeight=(float)(normTaskProject1.getWeight2())/100;
					Integer score = normTaskEmployee.getScore();
					if(score!=null){
						oneWorkScore+=((float)score*normTaskProjectWeight*projectWeight);
						System.out.println(score+"*"+normTaskProjectWeight+"*"+projectWeight);
					}
				}
			}
		}
		if(employeeCount==0){
			employeeCount=1;
		}
		return oneWorkScore/employeeCount;
	}

	/*private float onEmp(int scoreType, List<NormTaskEmployee> normTaskEmployeeList,
			List<NormTaskProject> normTaskProjectList2, NormTaskProject normTaskProject, float oneContentScore,
			NormTaskProject normTaskProject1, TaskProjectEmployee taskProjectEmployee) {
		Integer employeeCount = 0;
		for (NormTaskEmployee normTaskEmployee : normTaskEmployeeList) {  //循环评分表
			employeeCount ++;
			if (normTaskProject.getProjectId().equals(normTaskEmployee.getProjectId())
					&& normTaskEmployee.getScoreEmployeeId().equals(taskProjectEmployee.getEachEmployeeId())) { // 判断同一工作内容
				if (normTaskEmployee.getScoreType() == scoreType) { // 项目自评
					for (NormTaskProject normTaskProject2 : normTaskProjectList2) {
						if ((normTaskEmployee.getWorkContentId())
								.equals(normTaskProject1.getNormTaskProjectId())) {
							float normWeight = (float) (normTaskProject2.getWeight2()) / 100; // 考核指标权重
							float projectWeight = (float) (normTaskProject.getWeight()) / 100; // 考核项目权重
							System.out.println("考核指标权重"+normWeight);
							System.out.println("考核项目权重"+projectWeight);
							Integer score = normTaskEmployee.getScore(); // 评分
							if (score != null) {
								oneContentScore += ((float) score * normWeight * projectWeight);
								System.out.println(score+"*"+normWeight+"*"+projectWeight);
							}
						}
					}
				}
			}
		}
		if(employeeCount==0){
			employeeCount=1;
		}
		return oneContentScore/employeeCount;
	}*/

	
	
	
	/**
	 * 删除NormTaskEmployee
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteNormTaskEmployee",method=RequestMethod.POST)
	public JsonResult deleteNormTaskEmployee(
			@RequestParam(required=true)java.lang.Integer normTaskEmployeeId) {
		try {
			normTaskEmployeeService.delete(normTaskEmployeeId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询NormTaskEmployee表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/normTaskEmployeeList",method=RequestMethod.GET)
	public String normTaskEmployeeList(
			java.lang.Integer normTaskId,java.lang.Integer employeeId,Integer typeF,
			Model model) {
		String passFlag="";
		ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Employee employee2 = employeeService.findById(employeeId);
		
		//查询审核状态
		List<NormTaskEmployeeDetail> list = normTaskEmployeeDetailService.findScoreList(normTaskId, employeeId);
		if(! list.isEmpty()){
			passFlag = list.get(0).getPassFlag();
		}
		
		//根据考核任务及岗位查询考核模板
		TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,employee2.getStationId());
		Integer normTempId = taskStationTemplate.getNormTemplateId();
		//查询权重
		List<NormCategory> normCategoryList = normCategoryService.findByNormTempId(normTempId);
		//人力岗位指标列表
		Integer normType=null;
		normType=1;
		List<NormTaskEmployee> hrScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		
		//查询岗位
		normType=2;
		List<Norm> gwNormList=new ArrayList<>();
		List<NormTaskEmployee> gwScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
	    if(!gwScoreList.isEmpty() && gwScoreList.get(0).getNormCategoryId()!=null){
	    	gwNormList=normService.findNormByNormCatId(gwScoreList.get(0).getNormCategoryId());
	    }
		/*
		   List<NormTaskEmployee> gwLevelList=new ArrayList<>();
		   for (NormTaskEmployee normTaskEmployee : gwScoreList) {
			Integer scoreType = normTaskEmployee.getScoreType();
			if(scoreType!=null&&scoreType==1){
				gwLevelList.add(normTaskEmployee);
			}
		}*/
		//查询项目
		normType=3;
		// 员工考核项目
		Integer type=1;
		List<NormTaskProject> normTaskProjectList = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		//查询员工工作内容 
		type=2;
		List<NormTaskProject> normTaskProjectList2 = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		
		// 互评员工名
		for (NormTaskProject normTaskProject : normTaskProjectList2) {
			Integer normTaskProjectId = normTaskProject.getNormTaskProjectId();
			List<TaskProjectEmployee> eachEmployeeList=taskProjectEmployeeService.findEmp(employeeId,normTaskProjectId);
			normTaskProject.setEachEmp(eachEmployeeList);
		}
		
		
		List<Norm> xmNormList=new ArrayList<>();
		List<NormTaskEmployee> xmScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		if(!xmScoreList.isEmpty()){
	    	xmNormList=normService.findNormByNormCatId(xmScoreList.get(0).getNormCategoryId());
	    }
		
		model.addAttribute("hrScoreList", hrScoreList);
		model.addAttribute("normTaskProjectList", normTaskProjectList);
		model.addAttribute("normTaskProjectList2", normTaskProjectList2);
		model.addAttribute("xmNormList", xmNormList);
		model.addAttribute("gwNormList", gwNormList);
		model.addAttribute("gwScoreList", gwScoreList);
		model.addAttribute("xmScoreList", xmScoreList);
		model.addAttribute("employeeId", employeeId);
		model.addAttribute("normTaskId", normTaskId);
		model.addAttribute("normCategoryList", normCategoryList);
		model.addAttribute("typeF", typeF);
		model.addAttribute("passFlag", passFlag);
		model.addAttribute("employee2", employee2);
		model.addAttribute("EMPLOYEE", EMPLOYEE.getEmployeeId());
		return "admin/task/normTaskEmployeeList";
	}
	
	
	/**
	 * 互评页面展示
	 * @param normTaskId
	 * @param employeeId
	 * @param typeF
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/eachScoreList",method=RequestMethod.GET)
	public String eachScoreList(
			java.lang.Integer normTaskId,java.lang.Integer employeeId,Integer typeF,
			Model model) {
		ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Employee employee2 = employeeService.findById(employeeId);
		
		//根据考核任务及岗位查询考核模板
		TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,employee2.getStationId());
		Integer normTempId = taskStationTemplate.getNormTemplateId();
		
		//查询权重
		List<NormCategory> normCategoryList = normCategoryService.findByNormTempId(normTempId);
		
		//人力岗位指标列表
		Integer normType=null;
		//normType=1;
		//List<NormTaskEmployee> hrScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		
		//查询岗位
		normType=2;
		//List<Norm> gwNormList=new ArrayList<>();
		//List<NormTaskEmployee> gwScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		/*if(!gwScoreList.isEmpty()&&gwScoreList.get(0).getNormCategoryId()!=null){
			gwNormList=normService.findNormByNormCatId(gwScoreList.get(0).getNormCategoryId());
		}*/
		//查询项目
		normType=3;
		// 员工考核项目
		Integer type=1;
		List<NormTaskProject> normTaskProjectList = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		//查询员工工作内容 
		type=2;
		List<NormTaskProject> normTaskProjectList2 = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		//需要互评的项目
		Set<NormTaskProject> normTaskProjects=new HashSet<>();
		// 互评员工名
		for (NormTaskProject normTaskProject : normTaskProjectList2) {
			Integer normTaskProjectId = normTaskProject.getNormTaskProjectId();
			List<TaskProjectEmployee> eachEmployeeList=taskProjectEmployeeService.findEmp(employeeId,normTaskProjectId);
			normTaskProject.setEachEmp(eachEmployeeList);
			for (TaskProjectEmployee taskProjectEmployee : eachEmployeeList) {
				for (NormTaskProject normTaskProject2 : normTaskProjectList) {
					if(taskProjectEmployee.getEachEmployeeId().equals(EMPLOYEE.getEmployeeId()) && normTaskProject2.getProjectId().equals(taskProjectEmployee.getProjectId())){
						normTaskProjects.add(normTaskProject2);
					}
				}
			}
		}
		
		//List<Norm> xmNormList=new ArrayList<>();
		//根据互评人查询互评信息
		List<NormTaskEmployee> xmScoreList=normTaskEmployeeService.findEachScore(normTaskId,employeeId,normType,EMPLOYEE.getEmployeeId());
		/*if(!xmScoreList.isEmpty()){
			xmNormList=normService.findNormByNormCatId(xmScoreList.get(0).getNormCategoryId());
		}*/
		
		//model.addAttribute("hrScoreList", hrScoreList);
		model.addAttribute("normTaskProjectList", normTaskProjectList);
		model.addAttribute("normTaskProjectList2", normTaskProjectList2);
		model.addAttribute("normTaskProjects", normTaskProjects);
		//model.addAttribute("xmNormList", xmNormList);
		//model.addAttribute("gwNormList", gwNormList);
		//model.addAttribute("gwScoreList", gwScoreList);
		model.addAttribute("xmScoreList", xmScoreList);
		model.addAttribute("employeeId", employeeId);
		model.addAttribute("normTaskId", normTaskId);
		model.addAttribute("normCategoryList", normCategoryList);
		model.addAttribute("typeF", typeF);
		model.addAttribute("employee2", employee2);
		model.addAttribute("EMPLOYEE", EMPLOYEE.getEmployeeId());
		return "admin/task/eachScoreList";
	}
	/**
	 * 上级评分页面展示
	 * @param normTaskId
	 * @param employeeId
	 * @param typeF
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/levelScoreList",method=RequestMethod.GET)
	public String levelScoreList(
			java.lang.Integer normTaskId,java.lang.Integer employeeId,Integer typeF,
			Model model) {
		ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Employee employee2 = employeeService.findById(employeeId);
		NormTask normTask = normTaskService.findById(normTaskId);
		//根据考核任务及岗位查询考核模板
		TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,employee2.getStationId());
		Integer normTempId = taskStationTemplate.getNormTemplateId();
		//查询权重
		List<NormCategory> normCategoryList = normCategoryService.findByNormTempId(normTempId);
		//人力岗位指标列表
		Integer normType=null;
		normType=1;
		List<NormTaskEmployee> hrScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		
		//查询岗位
		normType=2;
		List<Norm> gwNormList=new ArrayList<>();
		List<NormTaskEmployee> gwScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
	    if(!gwScoreList.isEmpty()&&gwScoreList.get(0).getNormCategoryId()!=null){
	    	gwNormList=normService.findNormByNormCatId(gwScoreList.get(0).getNormCategoryId());
	    }
		/*
		   List<NormTaskEmployee> gwLevelList=new ArrayList<>();
		   for (NormTaskEmployee normTaskEmployee : gwScoreList) {
			Integer scoreType = normTaskEmployee.getScoreType();
			if(scoreType!=null&&scoreType==1){
				gwLevelList.add(normTaskEmployee);
			}
		}*/
		//查询项目
		normType=3;
		// 员工考核项目
		Integer type=1;
		List<NormTaskProject> normTaskProjectList = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		//查询员工工作内容 
		type=2;
		List<NormTaskProject> normTaskProjectList2 = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		
		// 互评员工名
		for (NormTaskProject normTaskProject : normTaskProjectList2) {
			Integer normTaskProjectId = normTaskProject.getNormTaskProjectId();
			List<TaskProjectEmployee> eachEmployeeList=taskProjectEmployeeService.findEmp(employeeId,normTaskProjectId);
			normTaskProject.setEachEmp(eachEmployeeList);
		}
		
		
		List<Norm> xmNormList=new ArrayList<>();
		List<NormTaskEmployee> xmScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		if(!xmScoreList.isEmpty()){
	    	xmNormList=normService.findNormByNormCatId(xmScoreList.get(0).getNormCategoryId());
	    }
		
		model.addAttribute("hrScoreList", hrScoreList);
		model.addAttribute("normTaskProjectList", normTaskProjectList);
		model.addAttribute("normTaskProjectList2", normTaskProjectList2);
		model.addAttribute("xmNormList", xmNormList);
		model.addAttribute("gwNormList", gwNormList);
		model.addAttribute("gwScoreList", gwScoreList);
		model.addAttribute("xmScoreList", xmScoreList);
		model.addAttribute("employeeId", employeeId);
		model.addAttribute("normTaskId", normTaskId);
		model.addAttribute("normCategoryList", normCategoryList);
		model.addAttribute("typeF", typeF);
		model.addAttribute("employee2", employee2);
		model.addAttribute("EMPLOYEE", EMPLOYEE.getEmployeeId());
		return "admin/task/levelScoreList";
	}
	
	/**
	 * 已经归档 评分页面
	 * @param normTaskId
	 * @param employeeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hasNormTaskEmployeeList",method=RequestMethod.GET)
	public String hasNormTaskEmployeeList(
			java.lang.Integer normTaskId,java.lang.Integer employeeId,String taskName,
			Model model) {
		Employee employee2 = employeeService.findEmpById(employeeId);
		NormTask normTask = normTaskService.findById(normTaskId);
		//根据考核任务及岗位查询考核模板
		TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,employee2.getStationId());
		Integer normTempId = taskStationTemplate.getNormTemplateId();
		//查询权重
		List<NormCategory> normCategoryList = normCategoryService.findByNormTempId(normTempId);
		//人力岗位指标列表
		Integer normType=null;
		normType=1;
		List<NormTaskEmployee> hrScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		
		//查询岗位
		normType=2;
		List<Norm> gwNormList=new ArrayList<>();
		List<NormTaskEmployee> gwScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		if(!gwScoreList.isEmpty()&&gwScoreList.get(0).getNormCategoryId()!=null){
			gwNormList=normService.findNormByNormCatId(gwScoreList.get(0).getNormCategoryId());
		}
		/*
		   List<NormTaskEmployee> gwLevelList=new ArrayList<>();
		   for (NormTaskEmployee normTaskEmployee : gwScoreList) {
			Integer scoreType = normTaskEmployee.getScoreType();
			if(scoreType!=null&&scoreType==1){
				gwLevelList.add(normTaskEmployee);
			}
		}*/
		//查询项目
		normType=3;
		// 员工考核项目
				Integer type=1;
				List<NormTaskProject> normTaskProjectList = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
				//查询员工工作内容 
				type=2;
				List<NormTaskProject> normTaskProjectList2 = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
		// 互评员工名
		for (NormTaskProject normTaskProject : normTaskProjectList2) {
			Integer normTaskProjectId = normTaskProject.getNormTaskProjectId();
			List<TaskProjectEmployee> eachEmployeeList=taskProjectEmployeeService.findEmp(employeeId,normTaskProjectId);
			normTaskProject.setEachEmp(eachEmployeeList);
		}
		
		
		List<Norm> xmNormList=new ArrayList<>();
		List<NormTaskEmployee> xmScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
		if(!xmScoreList.isEmpty()){
			xmNormList=normService.findNormByNormCatId(xmScoreList.get(0).getNormCategoryId());
		}
		model.addAttribute("hrScoreList", hrScoreList);
		model.addAttribute("normTaskProjectList", normTaskProjectList);
		model.addAttribute("normTaskProjectList2", normTaskProjectList2);
		model.addAttribute("xmNormList", xmNormList);
		model.addAttribute("gwNormList", gwNormList);
		model.addAttribute("gwScoreList", gwScoreList);
		model.addAttribute("xmScoreList", xmScoreList);
		model.addAttribute("employeeId", employeeId);
		model.addAttribute("normTaskId", normTaskId);
		model.addAttribute("employee2", employee2);
		model.addAttribute("taskName", taskName);
		model.addAttribute("normTask", normTask);
		model.addAttribute("normCategoryList", normCategoryList);
		return "admin/task/hasNormTaskEmployeeList";
	}
}
