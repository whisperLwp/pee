
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

import com.hongedu.pems.pems.entity.NormDetail;
import com.hongedu.pems.pems.service.NormDetailService;


/**
 * @author  
 * el_bp_norm_detail 表对应的controller
 * 2018/01/31 02:37:11
 */
@Controller
@RequestMapping("/admin/normDetail")
public class NormDetailController {
	private final static Logger logger= LoggerFactory.getLogger(NormDetailController.class);
	
	@Autowired
	private NormDetailService normDetailService;
	
	/**
	 * 查询NormDetail详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNormDetail",method=RequestMethod.POST)
	public JsonResult findNormDetail(
			@RequestParam(required=true)java.lang.Integer normDetailId) {
		try {
			NormDetail normDetail = normDetailService.findById(normDetailId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", normDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑NormDetail
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editNormDetail",method=RequestMethod.GET)
	public String editNormDetail(
			java.lang.Integer normDetailId,
			Model model) {
		if(normDetailId != null){
			NormDetail normDetail = normDetailService.findById(normDetailId);
			model.addAttribute("normDetail", normDetail);
		}
		return "admin/normDetail/editNormDetail";
	}
	
	/**
	 * 保存NormDetail
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveNormDetail",method=RequestMethod.POST)
	public JsonResult saveNormDetail(
			NormDetail normDetail
			) {
		try {
			if(normDetail.getNormDetailId() == null){
				normDetailService.save(normDetail);
			}else{
				normDetailService.update(normDetail);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除NormDetail
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteNormDetail",method=RequestMethod.POST)
	public JsonResult deleteNormDetail(
			@RequestParam(required=true)java.lang.Integer normDetailId) {
		try {
			normDetailService.delete(normDetailId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询NormDetail表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/normDetailList",method=RequestMethod.GET)
	public String normDetailList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<NormDetail> page = normDetailService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/normDetail/normDetailList";
	}
}
