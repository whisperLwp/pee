package com.hongedu.pems.pems.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongedu.pems.util.json.JsonResult;
import com.hongedu.pems.util.shiro.ShiroEmployee;

@Controller
@RequestMapping("/login")
public class LoginController {
	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult checklogin(
			@RequestParam(required=true)java.lang.String username,
			@RequestParam(required=true)java.lang.String password,
			HttpServletRequest request
			) {
			try {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
				Subject currentUser = SecurityUtils.getSubject();  
				if (!currentUser.isAuthenticated()){
					//使用shiro来验证  
					/*   token.setRememberMe(true); */ 
					currentUser.login(token);//验证角色和权限
				} 
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult(JsonResult.FAILE_CODE, "用户名或密码错误", null);
			}
			return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
	}
	
	@RequestMapping(value="/pems")
	public String login() {
		return "admin/login";
	}
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="/logout",method =RequestMethod.GET)
	@ResponseBody
	public JsonResult logout(){
		ShiroEmployee user =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		if(user!=null) {
			SecurityUtils.getSubject().logout();
		}
		return new JsonResult(JsonResult.SUCCESS_CODE, "", "");
	}

}
