package com.hongedu.pems.pems.service;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongedu.pems.base.dao.expression.Exp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;
import com.hongedu.pems.pems.entity.Dept;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_sys_employee 表对应业务实现对象
 * 2018/01/31 02:37:13
 */
@Transactional
@Service
public class EmployeeService{
	
	private final static Logger logger= LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
	private BaseDaoImpl<Employee> employeeDao;
	@Autowired
	private DeptService deptService;
	
	/**
	 * 根据主键查询Employee对象
	 * @param id
	 * @return
	 */
	public Employee findById (Serializable id){
		Employee entity = employeeDao.findById(Employee.class, id);
		if(entity.getLevelEmployeeId()!=null){
			Employee employee = employeeDao.findById(Employee.class, entity.getLevelEmployeeId());
			if(employee!=null){
				entity.setLevelEmployeeName(employee.getRealName());
			}
		}
		return entity;
	}
	
	/**
	 * 查询员工详细信息 部门  上级领导
	 * @param id
	 * @return
	 */
	public Employee findEmpById (Serializable id){
		Employee employee = new Employee();
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "employee_id", "=", id));
		List<Employee> EmployeeList = employeeDao.findList(Employee.class, expList);	
		if(!EmployeeList.isEmpty()){
			employee = EmployeeList.get(0);
			if(employee.getLevelEmployeeId()!=null){
				Employee employee1 = employeeDao.findById(Employee.class, employee.getLevelEmployeeId());
				if(employee1!=null){
					employee.setLevelEmployeeName(employee1.getRealName());
				}
			}
			if(StringUtils.isNotEmpty(employee.getDeptId())){
				 String deptName=deptService.findDeptByCode(employee.getDeptId());
				 if(deptName!=null){
					  employee.setDeptName(deptName);
				  }
			}
			
		}
		
		return employee;
	}
	
	/**
	 * 插入Employee对象
	 * @param entity
	 */
	public void save (Employee entity){
		String encodeStr=DigestUtils.md5Hex("000000");
		entity.setPassword(encodeStr);
		employeeDao.save(entity);
	}
	
	/**
	 * 修改Employee对象
	 * @param entity
	 */
	public void update (Employee entity){
		employeeDao.update(entity);
	}
	
	/**
	 * 删除Employee对象
	 * @param id
	 */
	public void delete (Serializable id){
		employeeDao.delete(Employee.class, id);
	}
	
	/**
	 * 根据sql查询Employee对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Employee对象集合
	 */
	public List<Employee> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select Employee.* from Employee");
		List<Employee> list = employeeDao.findListBySql(Employee.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询Employee集合数量
	 * @return Employee集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from Employee");
		int count = employeeDao.findCountBySql(Employee.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询Employee分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Employee分页对象
	 */
	public Pager<Employee> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<Employee> pager = new Pager<Employee>(currentPage, pageSize, totalRecord);
		List<Employee> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询Employee对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Employee对象集合
	 */
	public List<Employee> findList(String name,int deptId,int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "real_name", "like", "%"+name+"%"));
		expList.add(new WhereExp(Employee.class, "dept_id", "like", ""+deptId+"%"));
		List<Employee> EmployeeList = employeeDao.findList(Employee.class, expList, currentPage, pageSize);	
		return EmployeeList;
	}
	
	/**
	 * 根据表达式查询Employee集合数量
	 * @return Employee集合数量
	 */
	public int findCount(String name,int deptId){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "real_name", "like", "%"+name+"%"));
		expList.add(new WhereExp(Employee.class, "dept_id", "like", ""+deptId+"%"));
		int count = employeeDao.findCount(Employee.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询Employee分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Employee分页对象
	 */
	public Pager<Employee> findPage(String name,int deptId,int currentPage, int pageSize){
		int totalRecord = findCount(name,deptId);	
		Pager<Employee> pager = new Pager<Employee>(currentPage, pageSize, totalRecord);
		List<Employee> dataList = findList(name,deptId,pager.getFromIndex() , pageSize);
		for (Employee employee : dataList) {
			Integer levelEmployeeId = employee.getLevelEmployeeId();
			String deptId2 = employee.getDeptId();
			if(levelEmployeeId!=null){
			  Employee entity = employeeDao.findById(Employee.class, levelEmployeeId);
			 
			  if(entity!=null){
				  employee.setLevelEmployeeName(entity.getRealName());
			  }
			}
			if(StringUtils.isNotEmpty(deptId2)){
				 String deptName=deptService.findDeptByCode(deptId2);
				 if(deptName!=null){
					  employee.setDeptName(deptName);
				  }
			}
		}
		pager.setDataList(dataList);
		return pager;
	}
    /**
     * 查询所有没在此项目中的员工
     * @param projectId
     * @return
     */
	public List<Employee> findAllEmp(Integer projectId ,String deptCode) {
		/*List<Exp> expList = new ArrayList<Exp>();
		List<Employee> EmployeeList = employeeDao.findList(Employee.class, expList);	
		return EmployeeList;*/
		StringBuffer sql = new StringBuffer();
		sql.append("select p.employee_id , p.real_name from el_sys_employee p where NOT EXISTS "
				+ "(select null from el_bp_project_employee pe where pe.employee_id=p.employee_id "
				+ "AND pe.project_id='"+projectId+"') and ifnull(p.dept_id,'')  LIKE '"+deptCode+"%'");
		List<Employee> employeeList = employeeDao.findListBySql(Employee.class, sql.toString());	
		return employeeList;
	}
	
	public List<Employee> findAllNoEmp(Integer normTaskId ,String deptId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.employee_id , p.real_name from el_sys_employee p where NOT EXISTS "
				+ "(select null from el_bp_norm_task_employee_detail pe where pe.employee_id=p.employee_id "
				+ "AND pe.norm_task_id='"+normTaskId+"') and p.pe_flag='1' and ifnull(p.dept_id,'')  LIKE '"+deptId+"%'");
		List<Employee> employeeList = employeeDao.findListBySql(Employee.class, sql.toString());	
		return employeeList;
	}

	public Pager<Employee> findPage(String name, Integer currentPage, Integer pageSize) {
		int totalRecord = findCount(name);	
		Pager<Employee> pager = new Pager<Employee>(currentPage, pageSize, totalRecord);
		List<Employee> dataList = findList(name,pager.getFromIndex() , pageSize);
		for (Employee employee : dataList) {
			Integer levelEmployeeId = employee.getLevelEmployeeId();
			String deptId2 = employee.getDeptId();
			if(levelEmployeeId!=null){
			  Employee entity = employeeDao.findById(Employee.class, levelEmployeeId);
			  if(entity!=null){
				  employee.setLevelEmployeeName(entity.getRealName());
			  }
			}
			if(StringUtils.isNotEmpty(deptId2)){
				 String deptName=deptService.findDeptByCode(deptId2);
				 if(deptName!=null){
					  employee.setDeptName(deptName);
				  }
			}
		}
		pager.setDataList(dataList);
		return pager;
	}

	private List<Employee> findList(String name, int currentPage, Integer pageSize) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "real_name", "like", "%"+name+"%"));
		List<Employee> EmployeeList = employeeDao.findList(Employee.class, expList, currentPage, pageSize);	
		return EmployeeList;
	}

	private int findCount(String name) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "real_name", "like", "%"+name+"%"));
		int count = employeeDao.findCount(Employee.class, expList);	
		return count;
	}
    
	/*
	 *   验证登录 
	 */
	public Employee selectLogin(String username, String password) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "employee_name", "=", username));
		expList.add(new WhereExp(Employee.class, "password", "=", password));
		List<Employee> EmployeeList = employeeDao.findList(Employee.class, expList);
		Employee employee  = new Employee();
		if(EmployeeList!=null && EmployeeList.size()==1){
			employee=EmployeeList.get(0);
			return employee;
		}
		return employee;
	}
    /**
     * 重置密码
     * @param employeeId
     */
	public void resetPassword(Integer employeeId) {
		Employee entity = new Employee();
		entity.setEmployeeId(employeeId);
		String encodeStr=DigestUtils.md5Hex("000000");
		entity.setPassword(encodeStr);
		employeeDao.update(entity);
		
	}
    /**
     * 根据直属领导查询员工
     * @param employeeId
     * @return
     */
	public List<Employee> findEmployeeByLevel(Integer employeeId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "level_employee_id", "=", employeeId));
		List<Employee> EmployeeList = employeeDao.findList(Employee.class, expList);	
		return EmployeeList;
	}
    /**
     * 查询角色为领导的
     * @return
     */
	public List<Employee> findEmpByRoleId(Integer roleId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "role_id", "=", roleId));
		List<Employee> EmployeeList = employeeDao.findList(Employee.class, expList);	
		return EmployeeList;
	}

	public void editPassword(Integer employeeId, String newPassword) {
		Employee entity = new Employee();
		entity.setEmployeeId(employeeId);
		String encodeStr=DigestUtils.md5Hex(newPassword);
		entity.setPassword(encodeStr);
		employeeDao.update(entity);
	}

	public List<Employee> findEmployeeByDept(String deptId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "dept_id", "like", ""+deptId+"%"));
		List<Employee> EmployeeList = employeeDao.findList(Employee.class, expList);	
		return EmployeeList;
	}
}


