
package com.hongedu.pems.pems.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.alibaba.fastjson.JSONObject;
import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTaskEmployee;
import com.hongedu.pems.pems.entity.NormTaskEmployeeDetail;
import com.hongedu.pems.pems.entity.PieData;
import com.hongedu.pems.pems.entity.Project;
import com.hongedu.pems.pems.service.NormTaskEmployeeDetailService;
import com.hongedu.pems.pems.service.NormTaskEmployeeService;
import com.hongedu.pems.pems.service.NormTaskService;
import com.hongedu.pems.pems.service.ProjectService;
import com.hongedu.pems.util.json.JsonResult;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.util.shiro.ShiroEmployee;



/**
 * @author  
 * 首页controller
 * 2017/11/07 01:51:51
 */
@Controller
@RequestMapping
public class IndexController {
	private final static Logger logger= LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private NormTaskEmployeeDetailService normTaskEmployeeDetailService;
	@Autowired
	private NormTaskService normTaskService;
	@Autowired
	private NormTaskEmployeeDetailController taskEmployeeDetailController;
	@Autowired
	private NormTaskEmployeeService normTaskEmployeeService;
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {
		try {
			ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
			if(EMPLOYEE==null){
				return "redirect:/login/pems";
			}
			return "/index";
		} catch (Exception e) {
			return "redirect:/login/pems";
		}
		
		
	}
	@RequestMapping(value="welcome", method=RequestMethod.GET)
	public String welcome(Model model) {
		ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		List<Project> projects = projectService.findEmpProject(EMPLOYEE.getEmployeeId());
		List<NormTask> taskList = normTaskService.findNormTask();
		List<NormTaskEmployeeDetail> detailList = normTaskEmployeeDetailService.findTaskList(EMPLOYEE.getEmployeeId());
		model.addAttribute("projects", projects);
		model.addAttribute("taskList", taskList);
		model.addAttribute("detailList", detailList);
		model.addAttribute("role", EMPLOYEE.getRoleId());
		return "welcome";
	}
	
	@RequestMapping("/admin/pieData")
	@ResponseBody
	public JsonResult pieData() {
		try {
			ShiroEmployee EMPLOYEE =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
			List<NormTask> taskList = normTaskService.findNormTask();
			Integer normTaskId=null;
			String taskName="";
			if(!taskList.isEmpty()){
				
				normTaskId = taskList.get(0).getNormTaskId();
				taskName = taskList.get(0).getNormTaskName();
			}
			float noScoreCount=0;
			float count=0;
			if(EMPLOYEE.getRoleId()==1){
				List<NormTaskEmployeeDetail> findScoreList = normTaskEmployeeDetailService.findScoreList( normTaskId,0);
				count=findScoreList.size();
				for (NormTaskEmployeeDetail detail : findScoreList) {
					int flag=0;
					int xm=0;
					int xe=0;
					List<NormTaskEmployee> normTaskEmployees=normTaskEmployeeService.findScoreDetail(normTaskId, detail.getEmployeeId());
					for (NormTaskEmployee normTaskEmployee : normTaskEmployees) {
						if(normTaskEmployee.getScore()==null){
							flag++;
						}
						if(normTaskEmployee.getNormType() == 3){
							xm++;
						}
						if(normTaskEmployee.getScoreType() == 2){
							xe++;
						}	
						
					}
					if(flag>0 || xm == 0 || xe==0){
						noScoreCount ++ ;
					}
					
				}
			}
			float nodata=0;
			float hasdata=0;
			List<PieData> resultList = new ArrayList<>();
			if(count!=0){
				if(noScoreCount==count){
					nodata=100;
					hasdata=0;
				}else{
					nodata=(noScoreCount/count)*100;
					hasdata=100-nodata;
				}
				if(noScoreCount==0){
					nodata=0;
					hasdata=100;
				}
		        resultList.add(new PieData("未评分"+(int)noScoreCount+"人", nodata, taskName));
		        resultList.add(new PieData("已评分"+(int)(count-noScoreCount)+"人", hasdata, taskName+"("+(int)count+"人)"));
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	
}
