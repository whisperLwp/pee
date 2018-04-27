
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

import com.hongedu.pems.pems.entity.Station;
import com.hongedu.pems.pems.service.StationService;


/**
 * @author  
 * el_bp_station 表对应的controller
 * 2018/04/17 04:16:35
 */
@Controller
@RequestMapping("/admin/station")
public class StationController {
	private final static Logger logger= LoggerFactory.getLogger(StationController.class);
	
	@Autowired
	private StationService stationService;
	
	/**
	 * 查询Station详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findStation",method=RequestMethod.POST)
	public JsonResult findStation(
			@RequestParam(required=true)java.lang.Integer stationId) {
		try {
			Station station = stationService.findById(stationId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", station);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑Station
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editStation",method=RequestMethod.GET)
	public String editStation(
			java.lang.Integer stationId,
			Model model) {
		if(stationId != null){
			Station station = stationService.findById(stationId);
			model.addAttribute("station", station);
		}
		return "admin/station/editStation";
	}
	
	/**
	 * 保存Station
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveStation",method=RequestMethod.POST)
	public JsonResult saveStation(
			Station station
			) {
		try {
			if(station.getStationId() == null){
				stationService.save(station);
			}else{
				stationService.update(station);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除Station
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteStation",method=RequestMethod.POST)
	public JsonResult deleteStation(
			@RequestParam(required=true)java.lang.Integer stationId) {
		try {
			stationService.delete(stationId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询Station表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/stationList",method=RequestMethod.GET)
	public String stationList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<Station> page = stationService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/station/stationList";
	}
}
