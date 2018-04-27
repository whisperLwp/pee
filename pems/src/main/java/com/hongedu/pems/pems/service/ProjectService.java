package com.hongedu.pems.pems.service;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongedu.pems.base.dao.expression.Exp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;
import com.hongedu.pems.pems.entity.NormTemplate;
import com.hongedu.pems.pems.entity.Project;
import com.hongedu.pems.pems.entity.ProjectEmployee;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.util.shiro.ShiroEmployee;
import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_project 表对应业务实现对象
 * 2018/01/31 02:37:12
 */
@Transactional
@Service
public class ProjectService{
	
	private final static Logger logger= LoggerFactory.getLogger(ProjectService.class);
	
	@Autowired
	private BaseDaoImpl<Project> projectDao;
	
	/**
	 * 根据主键查询Project对象
	 * @param id
	 * @return
	 */
	public Project findById (Serializable id){
		Project entity = projectDao.findById(Project.class, id);
		return entity;
	}
	
	/**
	 * 插入Project对象
	 * @param entity
	 */
	public void save (Project entity){
		entity.setCreateTime(new Date());
		ShiroEmployee user =  (ShiroEmployee) SecurityUtils.getSubject().getPrincipal();
		Integer employeeId = user.getEmployeeId();
		entity.setCreator(employeeId);
		projectDao.save(entity);
	}
	
	/**
	 * 修改Project对象
	 * @param entity
	 */
	public void update (Project entity){
		projectDao.update(entity);
	}
	
	/**
	 * 删除Project对象
	 * @param id
	 */
	public void delete (Serializable id){
		projectDao.delete(Project.class, id);
	}
	
	/**
	 * 根据sql查询Project对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Project对象集合
	 */
	public List<Project> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select Project.* from Project");
		List<Project> list = projectDao.findListBySql(Project.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询Project集合数量
	 * @return Project集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from Project");
		int count = projectDao.findCountBySql(Project.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询Project分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Project分页对象
	 */
	public Pager<Project> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<Project> pager = new Pager<Project>(currentPage, pageSize, totalRecord);
		List<Project> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询Project对象集合
	 * @param status 
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Project对象集合
	 */
	public List<Project> findList(String name,Integer status, String deptCode,int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Project.class, "dept_code", "like", ""+deptCode+"%"));
		expList.add(new WhereExp(Project.class, "status", "=", status));
		expList.add(new WhereExp(Project.class, "project_name", "like", ""+name+"%"));
		List<Project> ProjectList = projectDao.findList(Project.class, expList, currentPage, pageSize);	
		return ProjectList;
	}
	
	/**
	 * 根据表达式查询Project集合数量
	 * @param deptCode 
	 * @param name 
	 * @param status 
	 * @return Project集合数量
	 */
	public int findCount(String name, Integer status, String deptCode){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Project.class, "dept_code", "like", ""+deptCode+"%"));
		expList.add(new WhereExp(Project.class, "status", "=", status));
		expList.add(new WhereExp(Project.class, "project_name", "like", ""+name+"%"));
		int count = projectDao.findCount(Project.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询Project分页对象
	 * @param status 
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Project分页对象
	 */
	public Pager<Project> findPage(String name,Integer status, String deptCode,int currentPage, int pageSize){
		int totalRecord = findCount(name,status,deptCode);	
		Pager<Project> pager = new Pager<Project>(currentPage, pageSize, totalRecord);
		List<Project> dataList = findList(name,status,deptCode,pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
    /**
     * 查询该员工参加的所有项目
     * @param employeeId 
     * @param normTaskId 
     * @return
     */
	public List<Project> findAllProject(Integer normTaskId, Integer employeeId) {
		StringBuffer sql = new StringBuffer();
		/*sql.append("select p.project_id,p.project_name from el_bp_project p where NOT "
				+ "EXISTS(select null from el_bp_norm_task_project tp where "
				+ "tp.project_id=p.project_id and tp.norm_task_id='"+normTaskId+"' and tp.employee_id='"+employeeId+"') ");*/
		sql.append("select p.project_id,p.project_name from el_bp_project p LEFT JOIN el_bp_project_employee pe ON pe.project_id=p.project_id "
                   +" where NOT EXISTS(select null from el_bp_norm_task_project tp where tp.project_id=p.project_id and tp.norm_task_id='"+normTaskId+"' "
                   + "and tp.employee_id='"+employeeId+"') AND pe.employee_id='"+employeeId+"' and p.status='2'");
		List<Project> ProjectList = projectDao.findListBySql(Project.class, sql.toString());	
		return ProjectList;
	}
	/**
	 * 查询该员工参加的项目  welcom页显示
	 * @param employeeId 
	 * @return
	 */
	public List<Project> findEmpProject(Integer employeeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.project_id,p.project_name from el_bp_project p LEFT JOIN el_bp_project_employee pe ON pe.project_id=p.project_id "
				+ "WHERE p.`status` = '2' AND pe.employee_id='"+employeeId+"'");
		List<Project> ProjectList = projectDao.findListBySql(Project.class, sql.toString());	
		return ProjectList;
	}

}


