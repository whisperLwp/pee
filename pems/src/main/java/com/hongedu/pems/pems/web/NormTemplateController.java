
package com.hongedu.pems.pems.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongedu.pems.util.json.JsonResult;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.pems.entity.Norm;
import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.entity.NormDetail;
import com.hongedu.pems.pems.entity.NormTemplate;
import com.hongedu.pems.pems.service.NormCategoryService;
import com.hongedu.pems.pems.service.NormDetailService;
import com.hongedu.pems.pems.service.NormService;
import com.hongedu.pems.pems.service.NormTemplateService;


/**
 * @author  
 * el_bp_norm_template 表对应的controller
 * 2018/01/31 02:37:12
 */
@Controller
@RequestMapping("/admin/normTemplate")
public class NormTemplateController {
	private final static Logger logger= LoggerFactory.getLogger(NormTemplateController.class);
	
	@Autowired
	private NormTemplateService normTemplateService;
	@Autowired
	private NormCategoryService normCategoryService;
	@Autowired
	private NormService normService;
	@Autowired
	private NormDetailService normDetailService;
	
	/**
	 * 查询NormTemplate详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNormTemplate",method=RequestMethod.POST)
	public JsonResult findNormTemplate(
			@RequestParam(required=true)java.lang.Integer normTempId) {
		try {
			NormTemplate normTemplate = normTemplateService.findById(normTempId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", normTemplate);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 新增考评模板
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/addNormTemplate",method=RequestMethod.GET)
	public String addNormTemplate(
			java.lang.Integer normTempId,
			Model model) {
		if(normTempId != null){
			NormTemplate normTemplate = normTemplateService.findById(normTempId);
			model.addAttribute("normTemplate", normTemplate);
		}
		List<NormCategory> normCategoryList=normCategoryService.findAllNormCategory();
		model.addAttribute("normCategoryList", normCategoryList);
		return "admin/template/addNormTemplate";
	}
	
	/**
	 * 编辑考评模板
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editNormTemplate",method=RequestMethod.GET)
	public String editNormTemplate(
			java.lang.Integer normTempId,
			java.lang.Integer type,
			Model model) {
		NormTemplate normTemplate=null;
		List<Norm> hrNormList = new ArrayList<>();
		List<Norm> gwNormList = new ArrayList<>();
		List<Norm> xmNormList = new ArrayList<>();
		if(normTempId != null){
			normTemplate = normTemplateService.findById(normTempId);
		}
		//根据模板查询考核指标
		List<NormCategory> normCategoryList = normCategoryService.findByNormTempId(normTempId);
		for (NormCategory normCategory : normCategoryList) {
			Integer normCategoryId = normCategory.getNormCategoryId();
			if(normCategory.getType()==1){
				hrNormList = normService.findNormByNormCatId(normCategoryId);
			}else if(normCategory.getType()==2){
				gwNormList = normService.findNormByNormCatId(normCategoryId);
			}else if(normCategory.getType()==3){
				xmNormList = normService.findNormByNormCatId(normCategoryId);
			}
		}
		hrNormList = normDeatilList(hrNormList);
		gwNormList = normDeatilList(gwNormList);
		xmNormList = normDeatilList(xmNormList);
		model.addAttribute("normTemplate", normTemplate);
		model.addAttribute("normCategoryList", normCategoryList);
		model.addAttribute("hrNormList", hrNormList);
		model.addAttribute("gwNormList", gwNormList);
		model.addAttribute("xmNormList", xmNormList);
		model.addAttribute("type",type);
		return "admin/template/editNormTemplate";
	}

	private List<Norm> normDeatilList(List<Norm> hrNormList) {
		for (Norm norm : hrNormList) {
			Integer normId = norm.getNormId();
			List<NormDetail> normDetailList = normDetailService.findNormDetailByNormId(normId);
			norm.setNormDetailList(normDetailList);
		}
		return hrNormList;
	}
	
	/**
	 * 保存NormTemplate
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveNormTemplate",method=RequestMethod.POST)
	public JsonResult saveNormTemplate(HttpServletRequest request,NormCategory normCategory,Norm norm,
			NormTemplate normTemplate,NormDetail normDetail,Integer typeFlag
			) {
		//获取考核指标类型名称 权重
		String hc=request.getParameter("hrFlag");                
		String hcw=request.getParameter("hrWeight");
		//获取考核指标名称 权重
		String[] ht = request.getParameterValues("hrNormText");
		String[] hw = request.getParameterValues("hrNormWeight");
		//获取考核指标明细
		String[] hd = request.getParameterValues("hrNormDetail");
		String[] hrNDIds = request.getParameterValues("hrNDId");
		String[] hrNIds = request.getParameterValues("hrNId");
		
		//获取岗位考核指标标题  权重  明细
		String gc=request.getParameter("gwFlag");
		String gcw=request.getParameter("gwWeight");
		String gcsw=request.getParameter("gwSelfW");
		String gclw=request.getParameter("gwLevelW");
		String[] gt = request.getParameterValues("gwNormText");
		String[] gw = request.getParameterValues("gwNormWeight");
		String[] gd = request.getParameterValues("gwNormDetail");
		String[] gwNDIds = request.getParameterValues("gwNDId");
		String[] gwNIds = request.getParameterValues("gwNId");
		
		//获取项目考核指标标题  权重  明细
		String xc=request.getParameter("xmFlag");
		String xcw=request.getParameter("xmWeight");
		String xcsw=request.getParameter("xmSelfW");
		/*String xclw=request.getParameter("xmLevelW");*/
		String xclw="0";
		String xcew=request.getParameter("xmEachW");
		String[] xt = request.getParameterValues("xmNormText");
		String[] xw = request.getParameterValues("xmNormWeight");
		String[] xd = request.getParameterValues("xmNormDetail");
		
		try {
			
			
				
			
			if(normTemplate.getNormTempId() == null){
				List<NormTemplate> normTemplates = normTemplateService.findTempName(normTemplate);
				if(normTemplates.size()==0){
					//保存模板得到模板Id
					int normTempId=normTemplateService.saveToPK(normTemplate);
					//保存人力相关内容
					saveHr(normCategory, norm, normDetail, hc, hcw, ht, hw, hd, normTempId);
					//保存岗位相关内容
					saveGw(norm, normDetail, gc, gcw, gcsw, gclw, gt, gw, gd, normTempId);
					//保存项目相关内容
					saveXm(norm, normDetail, xc, xcw, xcsw, xclw, xcew, xt, xw, xd, normTempId);
				}else{
					return new JsonResult(JsonResult.FAILE_CODE, "模板名称不能重复", null);
				}
				
				
			}else{
				if(typeFlag==1){
					normTemplateService.delete(normTemplate);
				}
				List<NormTemplate> normTemplates = normTemplateService.findTempName(normTemplate);
				if(normTemplates.size()==0){
				normTemplate.setNormTempId(null);
				int normTempId=normTemplateService.saveToPK(normTemplate);
				//保存人力相关内容
				saveHr(normCategory, norm, normDetail, hc, hcw, ht, hw, hd, normTempId);
				//保存岗位相关内容
				saveGw(norm, normDetail, gc, gcw, gcsw, gclw, gt, gw, gd, normTempId);
				//保存项目相关内容
				saveXm(norm, normDetail, xc, xcw, xcsw, xclw, xcew, xt, xw, xd, normTempId);
				}else{
					return new JsonResult(JsonResult.FAILE_CODE, "模板名称不能重复", null);
				}
				/*//保存人力相关内容
				updateHr(request, normCategory, norm, normTemplate, normDetail, hc, hcw, ht, hw, hd, hrNDIds, hrNIds);
				//保存岗位相关内容
				updateGw(request, normCategory, norm, normTemplate, normDetail, gt, gw, gd, gwNDIds, gwNIds, gc, gcw,
						gcsw, gclw);
				//保存项目相关内容
				updateXm(request, normTemplate, xc, xcw, xcsw, xclw, xcew);*/
				
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}

	private void updateXm(HttpServletRequest request, NormTemplate normTemplate, String xc, String xcw, String xcsw,
			String xclw, String xcew) {
		if(StringUtils.isNotEmpty(xc)){
			NormCategory normCategory3=new NormCategory();
			normCategory3.setNormTemplateId(normTemplate.getNormTempId());
			normCategory3.setNormCategoryName(xc);
			normCategory3.setWeight(Integer.valueOf(xcw));
			normCategory3.setLevelWeight(Integer.valueOf(xclw));
			normCategory3.setSelfWeight(Integer.valueOf(xcsw));
			normCategory3.setEachWeight(Integer.valueOf(xcew));
			Integer xmNCId = Integer.valueOf(request.getParameter("xmNCId"));
			normCategory3.setNormCategoryId(xmNCId);
			normCategory3.setType(3);
			//保存考核指标分类
			normCategoryService.update(normCategory3);
		}
	}

	private void updateGw(HttpServletRequest request, NormCategory normCategory, Norm norm, NormTemplate normTemplate,
			NormDetail normDetail, String[] gt, String[] gw, String[] gd, String[] gwNDIds, String[] gwNIds, String gc,
			String gcw, String gcsw, String gclw) {
		if(StringUtils.isNotEmpty(gc)){
			NormCategory normCategory2=new NormCategory();
			normCategory2.setNormTemplateId(normTemplate.getNormTempId());
			normCategory2.setNormCategoryName(gc);
			normCategory2.setWeight(Integer.valueOf(gcw));
			normCategory2.setLevelWeight(Integer.valueOf(gclw));
			normCategory2.setSelfWeight(Integer.valueOf(gcsw));
			Integer gwNCId = Integer.valueOf(request.getParameter("gwNCId"));
			normCategory2.setNormCategoryId(gwNCId);
			normCategory2.setType(2);
			//保存考核指标分类
			normCategoryService.update(normCategory2);
			upDateDetail(norm, normDetail, gt, gw, gd, gwNDIds, gwNIds, gwNCId);
			
		}
	}

	private void upDateDetail(Norm norm, NormDetail normDetail, String[] ht, String[] hw, String[] hd, String[] hrNDIds,
			String[] hrNIds, Integer gwNCId) {
		int j=hrNIds.length;
		int k=hrNDIds.length;
		if(ht!=null&&ht.length!=0){
			int[] normIds=new int[ht.length];
			for (int i = 0; i < ht.length; i++) {
				Norm norm1=new Norm();
				String HnormName = ht[i];
				String HnormWeight = hw[i];
				norm1.setNormName(HnormName);
				norm1.setWeight(Integer.valueOf(HnormWeight));
				norm1.setNormCategoryId(gwNCId);
				if(i<j){
					normIds[i]=Integer.valueOf(hrNIds[i]);
					norm1.setNormId(Integer.valueOf(hrNIds[i]));
					normService.update(norm1);
				}else{
					int normId = normService.saveToPK(norm1);
					normIds[i]=normId;
				}
			}
			int count=0;
			for (int i = 0; i < hd.length; i++) {
				NormDetail normDetail1 = new NormDetail();
				if(hd[i].equals("@")){
					k++;
					count++;
				}else{
					normDetail1.setDesrc(hd[i]);
					normDetail1.setNormId(normIds[count]);
					for (int l = 0; l < hrNDIds.length; l++) {
						if(hrNDIds[l].equals("@")){
							
						}
						
					}
					normDetailService.save(normDetail);
				}
				
			}
		}
	}

	private void updateHr(HttpServletRequest request, NormCategory normCategory, Norm norm, NormTemplate normTemplate,
			NormDetail normDetail, String hc, String hcw, String[] ht, String[] hw, String[] hd, String[] hrNDIds,
			String[] hrNIds) {
		if(StringUtils.isNotEmpty(hc)){
			normCategory.setNormTemplateId(normTemplate.getNormTempId());
			normCategory.setNormCategoryName(hc);
			normCategory.setType(1);
			normCategory.setWeight(Integer.valueOf(hcw));
			Integer hrNCId = Integer.valueOf(request.getParameter("hrNCId"));
			normCategory.setNormCategoryId(hrNCId);
			//保存考核指标分类
			normCategoryService.update(normCategory);
			upDateDetail(norm, normDetail, ht, hw, hd, hrNDIds, hrNIds, hrNCId);
			
		}
	}

	private void saveHr(NormCategory normCategory, Norm norm, NormDetail normDetail, String hc, String hcw, String[] ht,
			String[] hw, String[] hd, int normTempId) {
		if(StringUtils.isNotEmpty(hc)){
			normCategory.setNormTemplateId(normTempId);
			normCategory.setNormCategoryName(hc);
			normCategory.setType(1);
			normCategory.setWeight(Integer.valueOf(hcw));
			//保存考核指标分类
			int hrId = normCategoryService.saveToPK(normCategory);
			addParam(norm, normDetail, ht, hw, hd, hrId);
			
		}
	}

	private void saveGw(Norm norm, NormDetail normDetail, String gc, String gcw, String gcsw, String gclw, String[] gt,
			String[] gw, String[] gd, int normTempId) {
		if(StringUtils.isNotEmpty(gc)){
			NormCategory normCategory2=new NormCategory();
			normCategory2.setNormTemplateId(normTempId);
			normCategory2.setNormCategoryName(gc);
			normCategory2.setWeight(Integer.valueOf(gcw));
			normCategory2.setLevelWeight(Integer.valueOf(gclw));
			normCategory2.setSelfWeight(Integer.valueOf(gcsw));
			normCategory2.setType(2);
			//保存考核指标分类
			int gwId = normCategoryService.saveToPK(normCategory2);
			addParam(norm, normDetail, gt, gw, gd, gwId);
			
		}
	}

	private void saveXm(Norm norm, NormDetail normDetail, String xc, String xcw, String xcsw, String xclw, String xcew,
			String[] xt, String[] xw, String[] xd, int normTempId) {
		if(StringUtils.isNotEmpty(xc)){
			NormCategory normCategory3=new NormCategory();
			normCategory3.setNormTemplateId(normTempId);
			normCategory3.setNormCategoryName(xc);
			normCategory3.setWeight(Integer.valueOf(xcw));
			normCategory3.setLevelWeight(Integer.valueOf(xclw));
			normCategory3.setSelfWeight(Integer.valueOf(xcsw));
			normCategory3.setEachWeight(Integer.valueOf(xcew));
			normCategory3.setType(3);
			//保存考核指标分类
			int xwId = normCategoryService.saveToPK(normCategory3);
			//保存考核指标及考核指标明细
			addParam(norm, normDetail, xt, xw, xd, xwId);
		}
	}

	private void addParam(Norm norm, NormDetail normDetail, String[] xt, String[] xw, String[] xd, int xwId) {
		if(xt!=null&&xt.length!=0){
			int[] normIds=new int[xt.length];
			for (int i = 0; i < xt.length; i++) {
				String XnormName = xt[i];
				String XnormWeight = xw[i];
				norm.setNormName(XnormName);
				norm.setWeight(Integer.valueOf(XnormWeight));
				norm.setNormCategoryId(xwId);
				int normId = normService.saveToPK(norm);
				normIds[i]=normId;
			}
			int count=0;
			for (int i = 0; i < xd.length; i++) {
				if(xd[i].equals("@")){
					count++;
				}else{
					normDetail.setDesrc(xd[i]);
					int a=normIds[count];
					normDetail.setNormId(normIds[count]);
					normDetailService.save(normDetail);
				}
				
			}
		}
	}
	
	/**
	 * 删除NormTemplate
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteNormTemplate",method=RequestMethod.POST)
	public JsonResult deleteNormTemplate(
			NormTemplate normTemplate) {
		try {
			normTemplateService.delete(normTemplate);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询NormTemplate表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/normTemplateList",method=RequestMethod.GET)
	public String normTemplateList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			@RequestParam(required=false,defaultValue="")String name,
			Model model) {
		Pager<NormTemplate> page = normTemplateService.findPage(name,currentPage, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("name", name);
		return "admin/template/normTemplateList";
	}
}
