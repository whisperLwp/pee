
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

import com.hongedu.pems.pems.entity.Admin;
import com.hongedu.pems.pems.service.AdminService;


/**
 * @author  
 * el_sys_admin 表对应的controller
 * 2018/01/31 02:37:12
 */
@Controller
@RequestMapping("/admin/admin")
public class AdminController {
	private final static Logger logger= LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 查询Admin详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findAdmin",method=RequestMethod.POST)
	public JsonResult findAdmin(
			@RequestParam(required=true)java.lang.Integer adminId) {
		try {
			Admin admin = adminService.findById(adminId);
			return new JsonResult(JsonResult.SUCCESS_CODE, "", admin);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 编辑Admin
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editAdmin",method=RequestMethod.GET)
	public String editAdmin(
			java.lang.Integer adminId,
			Model model) {
		if(adminId != null){
			Admin admin = adminService.findById(adminId);
			model.addAttribute("admin", admin);
		}
		return "admin/admin/editAdmin";
	}
	
	/**
	 * 保存Admin
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveAdmin",method=RequestMethod.POST)
	public JsonResult saveAdmin(
			Admin admin
			) {
		try {
			if(admin.getAdminId() == null){
				adminService.save(admin);
			}else{
				adminService.update(admin);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 删除Admin
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteAdmin",method=RequestMethod.POST)
	public JsonResult deleteAdmin(
			@RequestParam(required=true)java.lang.Integer adminId) {
		try {
			adminService.delete(adminId);
		return new JsonResult(JsonResult.SUCCESS_CODE, "", null);
		} catch (Exception e) {
			e.printStackTrace();
						return new JsonResult(JsonResult.FAILE_CODE, "系统异常", null);
		}
	}
	
	/**
	 * 查询Admin表单页面
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/adminList",method=RequestMethod.GET)
	public String adminList(
			@RequestParam(required=false,defaultValue="1")Integer currentPage,
			@RequestParam(required=false,defaultValue="10")Integer pageSize,
			Model model) {
		Pager<Admin> page = adminService.findPage(currentPage, pageSize);
		model.addAttribute("page", page);
		return "admin/admin/adminList";
	}
}
