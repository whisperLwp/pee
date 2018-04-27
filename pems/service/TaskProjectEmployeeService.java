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
}


