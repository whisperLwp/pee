package com.hongedu.pems.pems.service;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongedu.pems.base.dao.expression.Exp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.Project;
import com.hongedu.pems.pems.entity.ProjectEmployee;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_project_employee 表对应业务实现对象
 * 2018/01/31 02:37:12
 */
@Transactional
@Service
public class ProjectEmployeeService{
	
	private final static Logger logger= LoggerFactory.getLogger(ProjectEmployeeService.class);
	
	@Autowired
	private BaseDaoImpl<ProjectEmployee> projectEmployeeDao;
	@Autowired
	private EmployeeService employeeService;

	private Integer employeeId;
	
	/**
	 * 根据主键查询ProjectEmployee对象
	 * @param id
	 * @return
	 */
	public ProjectEmployee findById (Serializable id){
		ProjectEmployee entity = projectEmployeeDao.findById(ProjectEmployee.class, id);
		return entity;
	}
	
	/**
	 * 插入ProjectEmployee对象
	 * @param entity
	 */
	public void save (ProjectEmployee entity){
		projectEmployeeDao.save(entity);
	}
	
	/**
	 * 修改ProjectEmployee对象
	 * @param entity
	 */
	public void update (ProjectEmployee entity){
		projectEmployeeDao.update(entity);
	}
	
	/**
	 * 删除ProjectEmployee对象
	 * @param id
	 */
	public void delete (Serializable id){
		projectEmployeeDao.delete(ProjectEmployee.class, id);
	}
	
	/**
	 * 根据sql查询ProjectEmployee对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return ProjectEmployee对象集合
	 */
	public List<ProjectEmployee> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select ProjectEmployee.* from ProjectEmployee");
		List<ProjectEmployee> list = projectEmployeeDao.findListBySql(ProjectEmployee.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询ProjectEmployee集合数量
	 * @return ProjectEmployee集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from ProjectEmployee");
		int count = projectEmployeeDao.findCountBySql(ProjectEmployee.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询ProjectEmployee分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return ProjectEmployee分页对象
	 */
	public Pager<ProjectEmployee> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<ProjectEmployee> pager = new Pager<ProjectEmployee>(currentPage, pageSize, totalRecord);
		List<ProjectEmployee> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询ProjectEmployee对象集合
	 * @param name 
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return ProjectEmployee对象集合
	 */
	public List<ProjectEmployee> findList(String name, int projectId,int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(ProjectEmployee.class, "project_id", "=", projectId));
		expList.add(new WhereExp(Employee.class, "real_name", "like", "%"+name+"%"));
		List<ProjectEmployee> ProjectEmployeeList = projectEmployeeDao.findList(ProjectEmployee.class, expList, currentPage, pageSize);	
		return ProjectEmployeeList;
	}
	
	/**
	 * 根据表达式查询ProjectEmployee集合数量
	 * @param name 
	 * @return ProjectEmployee集合数量
	 */
	public int findCount(String name, int projectId){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(ProjectEmployee.class, "project_id", "=", projectId));
		expList.add(new WhereExp(Employee.class, "real_name", "like", "%"+name+"%"));
		int count = projectEmployeeDao.findCount(ProjectEmployee.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询ProjectEmployee分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return ProjectEmployee分页对象
	 */
	public Pager<ProjectEmployee> findPage(String name,int projectId,int currentPage, int pageSize){
		int totalRecord = findCount(name,projectId);	
		Pager<ProjectEmployee> pager = new Pager<ProjectEmployee>(currentPage, pageSize, totalRecord);
		List<ProjectEmployee> dataList = findList(name,projectId,pager.getFromIndex() , pageSize);
		
		pager.setDataList(dataList);
		return pager;
	}
    /**
     * 查询项目互评没有关联的人员
     * @param projectId
     * @param normTaskProjectId 
     * @return
     */
	public List<ProjectEmployee> findEmployee(Integer projectId, Integer normTaskProjectId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.employee_id from el_bp_project_employee p where NOT "+
				"EXISTS(select null from el_bp_task_project_employee tp where "+ 
				"tp.each_employee_id=p.employee_id and tp.norm_task_project_id='"+normTaskProjectId+"')AND p.project_id='"+projectId+"'");
		List<ProjectEmployee> employeeList = projectEmployeeDao.findListBySql(ProjectEmployee.class, sql.toString());	
		if(!employeeList.isEmpty()){
			for (ProjectEmployee pEmployee : employeeList) {
				employeeId = pEmployee.getEmployeeId(); 
				Employee employee = employeeService.findById(employeeId);
				String realName = employee.getRealName();
				if(StringUtils.isNotEmpty(realName)){
					pEmployee.setRealName(realName);
					pEmployee.setEmployeeId(employee.getEmployeeId());
				}
				
			}
		}
		return employeeList;
	}
	
}


