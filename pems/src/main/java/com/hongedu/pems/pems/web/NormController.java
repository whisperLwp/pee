
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

import com.hongedu.pems.pems.entity.Norm;
import com.hongedu.pems.pems.service.NormService;


/**
 * @author  
 * el_bp_norm 表对应的controller
 * 2018/01/31 02:37:11
 */
@Controller
@RequestMapping("/admin/norm")
public class NormController {
	private final static Logger logger= LoggerFactory.getLogger(NormController.class);
	
	@Autowired
	private NormService normService;
	
	/**
	 * 查询Norm详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNorm",method=RequestMethod.POST)
	public JsonResult findNorm(
			@RequestParam(required=true)java.lang.Integer normId) {
		try {
			Norm norm = normService.findById(normId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", norm);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑Norm
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editNorm",method=RequestMethod.GET)
	public String editNorm(
			java.lang.Integer normId,
			Model model) {
		if(normId != null){
			Norm norm = normService.findById(normId);
			model.addAttribute("norm", norm);
		}
		return "admin/template/editNorm";
	}
	
	/**
	 * 保存Norm
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveNorm",method=RequestMethod.POST)
	public JsonResult saveNorm(
			Norm norm
			) {
		try {
			if(norm.getNormId() == null){
				normService.save(norm);
			}else{
				normService.update(norm);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除Norm
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteNorm",method=RequestMethod.POST)
	public JsonResult deleteNorm(
			@RequestParam(required=true)java.lang.Integer normId) {
		try {
			normService.delete(normId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询Norm表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/normList",method=RequestMethod.GET)
	public String normList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<Norm> page = normService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/template/normList";
	}
}
