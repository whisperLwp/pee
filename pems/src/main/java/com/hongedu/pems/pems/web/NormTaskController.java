
package com.hongedu.pems.pems.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.hongedu.pems.util.mail.MailManager;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTemplate;
import com.hongedu.pems.pems.entity.Station;
import com.hongedu.pems.pems.entity.TaskStationTemplate;
import com.hongedu.pems.pems.service.NormTaskService;
import com.hongedu.pems.pems.service.NormTemplateService;
import com.hongedu.pems.pems.service.StationService;
import com.hongedu.pems.pems.service.TaskStationTemplateService;


/**
 * @author  
 * el_bp_norm_task 表对应的controller
 * 2018/01/31 02:37:12
 */
@Controller
@RequestMapping("/admin/normTask")
public class NormTaskController {
	private final static Logger logger= LoggerFactory.getLogger(NormTaskController.class);
	
	@Autowired
	private NormTaskService normTaskService;
	@Autowired
	private NormTemplateService normTemplateService;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private StationService stationService;
	@Autowired
	private TaskStationTemplateService taskStationTemplateService;

	
	/**
	 * 查询NormTask详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNormTask",method=RequestMethod.POST)
	public JsonResult findNormTask(
			@RequestParam(required=true)java.lang.Integer normTaskId) {
		try {
			NormTask normTask = normTaskService.findById(normTaskId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", normTask);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 添加NormTask
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/addNormTask",method=RequestMethod.GET)
	public String addNormTask(
			java.lang.Integer normTaskId,
			Model model) {
		//查询模板
		List<NormTemplate> NormTemplateList=normTemplateService.findAll();
		if(normTaskId != null){
			NormTask normTask = normTaskService.findById(normTaskId);
			List<TaskStationTemplate> taskStationTemplates = taskStationTemplateService.findStationByTaskId(normTaskId);
			model.addAttribute("normTask", normTask);
			model.addAttribute("taskStationTemplates", taskStationTemplates);
		}
		List<Station> stationList = stationService.findAllStation();
		model.addAttribute("NormTemplateList", NormTemplateList);
		model.addAttribute("stationList", stationList);
		return "admin/task/addNormTask";
	}
	
	/**
	 * 
	 * @param normTaskId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/editNormTask",method=RequestMethod.GET)
	public String editNormTask(
			java.lang.Integer normTaskId,
			String type,
			Model model) {
		//查询模板
		List<NormTemplate> NormTemplateList=normTemplateService.findAll();
		if(normTaskId != null){
			NormTask normTask = normTaskService.findById(normTaskId);
			List<TaskStationTemplate> taskStationTemplates = taskStationTemplateService.findStationByTaskId(normTaskId);
			model.addAttribute("normTask", normTask);
			model.addAttribute("taskStationTemplates", taskStationTemplates);
		}
		List<Station> stationList = stationService.findAllStation();
		model.addAttribute("NormTemplateList", NormTemplateList);
		model.addAttribute("stationList", stationList);
		model.addAttribute("type", type);
		return "admin/task/editNormTask";
	}
	
	/**
	 * 保存NormTask
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveNormTask",method=RequestMethod.POST)
	public JsonResult saveNormTask(
			NormTask normTask,HttpServletRequest request
			) {
		
		try {
			if(normTask.getNormTaskId() == null){
				int normTaskId = normTaskService.saveToPK(normTask);
				saveTaskStationTemp(request,normTaskId);
			}else{
				normTaskService.update(normTask);
				taskStationTemplateService.deleteNormTask(normTask.getNormTaskId());
				saveTaskStationTemp(request,normTask.getNormTaskId());
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}

	private void saveTaskStationTemp(HttpServletRequest request,int normTaskId) {
		String[] stationIds = request.getParameterValues("station");
		String[] tempIds = request.getParameterValues("normTempId");
		Station entity2 = new Station();
		int count=0;
		for (int i = 0; i < tempIds.length; i++) {
			NormTemplate entity=new NormTemplate();
			TaskStationTemplate taskStationTemplate = new TaskStationTemplate();
			if(tempIds[i].equals("@")){
				count++;
			}else {
				entity.setReferFlag("1");
				entity.setNormTempId(Integer.valueOf(tempIds[i]));
				normTemplateService.update(entity);         //考评模板被引用 改变模板引用状态
				taskStationTemplate.setNormTaskId(normTaskId);
				taskStationTemplate.setNormTemplateId(Integer.valueOf(tempIds[i]));
				taskStationTemplate.setStationId(Integer.valueOf(stationIds[count]));
				entity2.setStationId(Integer.valueOf(stationIds[count]));
				entity2.setReferFlag("0");
				stationService.update(entity2);
				taskStationTemplateService.save(taskStationTemplate);
			}
			 
		}
	}
	
	/**
	 * 删除NormTask
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteNormTask",method=RequestMethod.POST)
	public JsonResult deleteNormTask(
			NormTask normTask) {
		try {
			normTaskService.delete(normTask);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	/**
	 * 取消归档
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/resetNormTask",method=RequestMethod.POST)
	public JsonResult restNormTask(
			NormTask normTask) {
		try {
			normTaskService.resetTask(normTask);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询NormTask表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/normTaskList",method=RequestMethod.GET)
	public String normTaskList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required=false,defaultValue="")String name,
			Model model) {
		Pager<NormTask> page = normTaskService.findPage(name,currentPage, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("name", name);
		return "admin/task/normTaskList";
	}
	/**
	 * 查询已归档考核任务
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hasNormTaskList",method=RequestMethod.GET)
	public String hasNormTaskList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required=false,defaultValue="")String name,
			Model model) {
		Pager<NormTask> page = normTaskService.findHasPage(name,currentPage, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("name", name);
		return "admin/task/hasNormTaskList";
	}
	
	
	/**
	 * 给没有完成任务的人发邮件
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/sendMail",method=RequestMethod.POST)
	public JsonResult sendMail(String mails) {
		try {
			mails=mails.substring(1, mails.length()-1);
			String[] split = mails.split(",");
			for (String string2 : split) {
				if(StringUtils.isNotEmpty(string2)&&!string2.equals("null")){
					mailManager.sendMail("您有未完成的考核任务需要完成，快去看看吧","你有未完成的任务",string2.trim());
				}
			}
			
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
		return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
}
