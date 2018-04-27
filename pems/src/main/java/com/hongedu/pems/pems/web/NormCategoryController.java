
package com.hongedu.pems.pems.web;

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

import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.service.NormCategoryService;


/**
 * @author  
 * el_bp_norm_category 表对应的controller
 * 2018/01/31 02:37:11
 */
@Controller
@RequestMapping("/admin/normCategory")
public class NormCategoryController {
	private final static Logger logger= LoggerFactory.getLogger(NormCategoryController.class);
	
	@Autowired
	private NormCategoryService normCategoryService;
	
	/**
	 * 查询NormCategory详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNormCategory",method=RequestMethod.POST)
	public JsonResult findNormCategory(
			@RequestParam(required=true)java.lang.Integer normCategoryId) {
		try {
			NormCategory normCategory = normCategoryService.findById(normCategoryId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", normCategory);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑NormCategory
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editNormCategory",method=RequestMethod.GET)
	public String editNormCategory(
			java.lang.Integer normCategoryId,
			Model model) {
		if(normCategoryId != null){
			NormCategory normCategory = normCategoryService.findById(normCategoryId);
			model.addAttribute("normCategory", normCategory);
		}
		return "admin/template/editNormCategory";
	}
	
	/**
	 * 保存NormCategory
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveNormCategory",method=RequestMethod.POST)
	public JsonResult saveNormCategory(
			NormCategory normCategory
			) {
		try {
			if(normCategory.getNormCategoryId() == null){
				normCategoryService.save(normCategory);
			}else{
				normCategoryService.update(normCategory);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除NormCategory
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteNormCategory",method=RequestMethod.POST)
	public JsonResult deleteNormCategory(
			@RequestParam(required=true)java.lang.Integer normCategoryId) {
		try {
			normCategoryService.delete(normCategoryId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询NormCategory表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/normCategoryList",method=RequestMethod.GET)
	public String normCategoryList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<NormCategory> page = normCategoryService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/template/normCategoryList";
	}
}
