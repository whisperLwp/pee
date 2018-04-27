package com.hongedu.pems.pems.service;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongedu.pems.base.dao.expression.Exp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;
import com.hongedu.pems.pems.entity.ProjectEmployee;
import com.hongedu.pems.pems.entity.TaskProjectEmployee;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_task_project_employee 表对应业务实现对象
 * 2018/02/24 02:40:05
 */
@Transactional
@Service
public class TaskProjectEmployeeService{
	
	private final static Logger logger= LoggerFactory.getLogger(TaskProjectEmployeeService.class);
	
	@Autowired
	private BaseDaoImpl<TaskProjectEmployee> taskProjectEmployeeDao;
	
	/**
	 * 根据主键查询TaskProjectEmployee对象
	 * @param id
	 * @return
	 */
	public TaskProjectEmployee findById (Serializable id){
		TaskProjectEmployee entity = taskProjectEmployeeDao.findById(TaskProjectEmployee.class, id);
		return entity;
	}
	
	/**
	 * 插入TaskProjectEmployee对象
	 * @param entity
	 */
	public void save (TaskProjectEmployee entity){
		taskProjectEmployeeDao.save(entity);
	}
	
	/**
	 * 修改TaskProjectEmployee对象
	 * @param entity
	 */
	public void update (TaskProjectEmployee entity){
		taskProjectEmployeeDao.update(entity);
	}
	
	/**
	 * 删除TaskProjectEmployee对象
	 * @param id
	 */
	public void delete (Serializable id){
		taskProjectEmployeeDao.delete(TaskProjectEmployee.class, id);
	}
	
	/**
	 * 根据sql查询TaskProjectEmployee对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return TaskProjectEmployee对象集合
	 */
	public List<TaskProjectEmployee> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select TaskProjectEmployee.* from TaskProjectEmployee");
		List<TaskProjectEmployee> list = taskProjectEmployeeDao.findListBySql(TaskProjectEmployee.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询TaskProjectEmployee集合数量
	 * @return TaskProjectEmployee集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from TaskProjectEmployee");
		int count = taskProjectEmployeeDao.findCountBySql(TaskProjectEmployee.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询TaskProjectEmployee分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return TaskProjectEmployee分页对象
	 */
	public Pager<TaskProjectEmployee> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<TaskProjectEmployee> pager = new Pager<TaskProjectEmployee>(currentPage, pageSize, totalRecord);
		List<TaskProjectEmployee> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询TaskProjectEmployee对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return TaskProjectEmployee对象集合
	 */
	public List<TaskProjectEmployee> findList(int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		List<TaskProjectEmployee> TaskProjectEmployeeList = taskProjectEmployeeDao.findList(TaskProjectEmployee.class, expList, currentPage, pageSize);	
		return TaskProjectEmployeeList;
	}
	
	/**
	 * 根据表达式查询TaskProjectEmployee集合数量
	 * @return TaskProjectEmployee集合数量
	 */
	public int findCount(){
		List<Exp> expList = new ArrayList<Exp>();
		int count = taskProjectEmployeeDao.findCount(TaskProjectEmployee.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询TaskProjectEmployee分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return TaskProjectEmployee分页对象
	 */
	public Pager<TaskProjectEmployee> findPage(int currentPage, int pageSize){
		int totalRecord = findCount();	
		Pager<TaskProjectEmployee> pager = new Pager<TaskProjectEmployee>(currentPage, pageSize, totalRecord);
		List<TaskProjectEmployee> dataList = findList(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}

	/**
	 * 查询项目互评已经关联的人员
	 * @param projectId
	 * @return
	 */
	public List<TaskProjectEmployee> findHasEmployee(Integer normTaskProjectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskProjectEmployee.class, "norm_task_project_id", "=", normTaskProjectId));
		List<TaskProjectEmployee> ProjectEmployeeList = taskProjectEmployeeDao.findList(TaskProjectEmployee.class, expList);	
		return ProjectEmployeeList;
	}

	public void delByNormTaskProjectId(Integer normTaskProjectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskProjectEmployee.class, "norm_task_project_id", "=",normTaskProjectId));
		taskProjectEmployeeDao.delete(TaskProjectEmployee.class, expList);
		
	}
	/**
	 * 删除互评项目关系
	 * @param normTaskProjectId
	 */
	public void delEachProject(Integer normTaskId, Integer employeeId, Integer projectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskProjectEmployee.class, "project_id", "=",projectId));
		expList.add(new WhereExp(TaskProjectEmployee.class, "norm_task_id", "=",normTaskId));
		expList.add(new WhereExp(TaskProjectEmployee.class, "employee_id", "=",employeeId));
		taskProjectEmployeeDao.delete(TaskProjectEmployee.class, expList);
		
	}

	public List<TaskProjectEmployee> findEmp(Integer employeeId, Integer normTaskProjectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskProjectEmployee.class, "norm_task_project_id", "=", normTaskProjectId));
		expList.add(new WhereExp(TaskProjectEmployee.class, "employee_id", "=", employeeId));
		List<TaskProjectEmployee> ProjectEmployeeList = taskProjectEmployeeDao.findList(TaskProjectEmployee.class, expList);	
		return ProjectEmployeeList;
	}

	public List<TaskProjectEmployee> findTaskPeojectEmployee(Integer employeeId, Integer normTaskId,Integer normTaskProjectId,Integer projectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskProjectEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(TaskProjectEmployee.class, "project_id", "=", projectId));
		expList.add(new WhereExp(TaskProjectEmployee.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(TaskProjectEmployee.class, "norm_task_project_id", "=", normTaskProjectId));
		List<TaskProjectEmployee> ProjectEmployeeList = taskProjectEmployeeDao.findList(TaskProjectEmployee.class, expList);	
		return ProjectEmployeeList;
	}
    /**
     * 查询需要“我”互评人员
     * @param employeeId
     * @param employeeId2 
     * @return
     */
	public List<TaskProjectEmployee> findEmpByEachEmp(Integer normTaskId, Integer employeeId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT `taskProjectEmployee`.employee_id,employee.employee_id employee__employee_id,"
				+ "employee.real_name employee__real_name,employee.dept_id employee__dept_id FROM "
				+ "el_bp_task_project_employee AS `taskProjectEmployee` LEFT JOIN el_sys_employee `employee` "
				+ "ON `taskProjectEmployee`.each_employee_id = `employee`.employee_id WHERE 1 = 1 "
				+ "AND `taskProjectEmployee`.norm_task_id = '"+normTaskId+"' "
				+ "AND `taskProjectEmployee`.each_employee_id = '"+employeeId+"'");

		List<TaskProjectEmployee> list = taskProjectEmployeeDao.findListBySql(TaskProjectEmployee.class, sql.toString());	
		return list;
		
		/*List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskProjectEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(TaskProjectEmployee.class, "each_employee_id", "=", employeeId));
		List<TaskProjectEmployee> ProjectEmployeeList = taskProjectEmployeeDao.findList(TaskProjectEmployee.class, expList);	
		return ProjectEmployeeList;*/
	}
	
	/**
	 * 查询需要互评的员工
	 * @param employeeId
	 * @param normTaskId
	 * @return
	 */
	public List<TaskProjectEmployee> findEachEmpForEmail(Integer employeeId, Integer normTaskId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskProjectEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(TaskProjectEmployee.class, "employee_id", "=", employeeId));
		List<TaskProjectEmployee> ProjectEmployeeList = taskProjectEmployeeDao.findList(TaskProjectEmployee.class, expList);	
		return ProjectEmployeeList;
	}

}


