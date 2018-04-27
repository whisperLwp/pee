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
import com.hongedu.pems.pems.entity.TaskProjectEmployee;
import com.hongedu.pems.pems.entity.TaskStationTemplate;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_task_station_template 表对应业务实现对象
 * 2018/04/18 09:30:38
 */
@Transactional
@Service
public class TaskStationTemplateService{
	
	private final static Logger logger= LoggerFactory.getLogger(TaskStationTemplateService.class);
	
	@Autowired
	private BaseDaoImpl<TaskStationTemplate> taskStationTemplateDao;
	
	/**
	 * 根据主键查询TaskStationTemplate对象
	 * @param id
	 * @return
	 */
	public TaskStationTemplate findById (Serializable id){
		TaskStationTemplate entity = taskStationTemplateDao.findById(TaskStationTemplate.class, id);
		return entity;
	}
	
	/**
	 * 插入TaskStationTemplate对象
	 * @param entity
	 */
	public void save (TaskStationTemplate entity){
		taskStationTemplateDao.save(entity);
	}
	
	/**
	 * 修改TaskStationTemplate对象
	 * @param entity
	 */
	public void update (TaskStationTemplate entity){
		taskStationTemplateDao.update(entity);
	}
	
	/**
	 * 删除TaskStationTemplate对象
	 * @param id
	 */
	public void delete (Serializable id){
		taskStationTemplateDao.delete(TaskStationTemplate.class, id);
	}
	
	/**
	 * 根据sql查询TaskStationTemplate对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return TaskStationTemplate对象集合
	 */
	public List<TaskStationTemplate> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select TaskStationTemplate.* from TaskStationTemplate");
		List<TaskStationTemplate> list = taskStationTemplateDao.findListBySql(TaskStationTemplate.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询TaskStationTemplate集合数量
	 * @return TaskStationTemplate集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from TaskStationTemplate");
		int count = taskStationTemplateDao.findCountBySql(TaskStationTemplate.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询TaskStationTemplate分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return TaskStationTemplate分页对象
	 */
	public Pager<TaskStationTemplate> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<TaskStationTemplate> pager = new Pager<TaskStationTemplate>(currentPage, pageSize, totalRecord);
		List<TaskStationTemplate> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询TaskStationTemplate对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return TaskStationTemplate对象集合
	 */
	public List<TaskStationTemplate> findList(int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		List<TaskStationTemplate> TaskStationTemplateList = taskStationTemplateDao.findList(TaskStationTemplate.class, expList, currentPage, pageSize);	
		return TaskStationTemplateList;
	}
	
	/**
	 * 根据表达式查询TaskStationTemplate集合数量
	 * @return TaskStationTemplate集合数量
	 */
	public int findCount(){
		List<Exp> expList = new ArrayList<Exp>();
		int count = taskStationTemplateDao.findCount(TaskStationTemplate.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询TaskStationTemplate分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return TaskStationTemplate分页对象
	 */
	public Pager<TaskStationTemplate> findPage(int currentPage, int pageSize){
		int totalRecord = findCount();	
		Pager<TaskStationTemplate> pager = new Pager<TaskStationTemplate>(currentPage, pageSize, totalRecord);
		List<TaskStationTemplate> dataList = findList(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}

	public TaskStationTemplate findTemp(Integer normTaskId, Integer stationId) {
		TaskStationTemplate taskStationTemplate = new TaskStationTemplate();
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskStationTemplate.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(TaskStationTemplate.class, "station_id", "=", stationId));
		List<TaskStationTemplate> TaskStationTemplateList = taskStationTemplateDao.findList(TaskStationTemplate.class, expList);	
		if(!TaskStationTemplateList.isEmpty()){
			taskStationTemplate = TaskStationTemplateList.get(0);
		}
		return taskStationTemplate;
	}

	public List<TaskStationTemplate> findStationByTaskId(Integer normTaskId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskStationTemplate.class, "norm_task_id", "=", normTaskId));
		List<TaskStationTemplate> TaskStationTemplateList = taskStationTemplateDao.findList(TaskStationTemplate.class, expList);
		return TaskStationTemplateList;
	}

	public void deleteNormTask(Integer normTaskId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(TaskStationTemplate.class, "norm_task_id", "=", normTaskId));
		taskStationTemplateDao.delete(TaskStationTemplate.class, expList);
	}
}


