package com.hongedu.pems.util.shiro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.service.EmployeeService;


public class MyShiroRealm extends AuthorizingRealm {

	@Resource(name = "employeeService")
	private EmployeeService employeeService;
	

	/*
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		String username = token.getUsername();
		String password =  String.valueOf(token.getPassword());
		String encodeStr=DigestUtils.md5Hex(password);
		Employee employee;
		try {
			ShiroEmployee su=null;
			employee = employeeService.selectLogin(username, encodeStr);
			if(employee.getEmployeeId()!=null){
				su = new ShiroEmployee(employee.getEmployeeId(), employee.getEmployeeName(), employee.getRealName(), 
						employee.getPassword(), employee.getIdCard(), employee.getDeptId(), employee.getSexFlag(), employee.getBirthDate(), 
						employee.getWorkDate(), employee.getPhoneNum(), employee.getMailNum(), employee.getLevelEmployeeId(), employee.getRoleId());
			}
				return new SimpleAuthenticationInfo(su,password,getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException();
		}
		
	}

	/*
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Set<String> roles = new HashSet<String>();
		ShiroEmployee su = (ShiroEmployee) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		// 添加角色
		if(su.getRoleId()==4) {
			roles.add("nope");
		}else if(su.getRoleId()==3) {
			roles.add("people");
		}else if(su.getRoleId()==1) {
			roles.add("admin");
		}else if(su.getRoleId()==2) {
			roles.add("level");
		}
		info.setRoles(roles);
		return info;
	}
}
