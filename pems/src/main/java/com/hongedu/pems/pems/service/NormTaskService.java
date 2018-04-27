package com.hongedu.pems.pems.service;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongedu.pems.base.dao.expression.Exp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTemplate;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_norm_task 表对应业务实现对象
 * 2018/01/31 02:37:12
 */
@Transactional
@Service
public class NormTaskService{
	
	private final static Logger logger= LoggerFactory.getLogger(NormTaskService.class);
	
	@Autowired
	private BaseDaoImpl<NormTask> normTaskDao;
	
	/**
	 * 根据主键查询NormTask对象
	 * @param id
	 * @return
	 */
	public NormTask findById (Serializable id){
		NormTask entity = normTaskDao.findById(NormTask.class, id);
		return entity;
	}
	
	/**
	 * 插入NormTask对象
	 * @param entity
	 */
	public void save (NormTask entity){
		entity.setCreateTime(new Date());
		entity.setDelFlag("1");
		entity.setReferFlag("0");
		normTaskDao.save(entity);
	}
	
	public int saveToPK(NormTask entity) {
		entity.setReferFlag("0");
		entity.setDelFlag("1");
		int saveToPK = normTaskDao.saveToPK(entity);
		return saveToPK;
	}
	
	/**
	 * 修改NormTask对象
	 * @param entity
	 */
	public void update (NormTask entity){
		entity.setFinishTime(new Date());
		normTaskDao.update(entity);
	}
	
	/**
	 * 删除NormTask对象
	 * @param id
	 */
	public void delete (NormTask entity){
		entity.setDelFlag("0");
		normTaskDao.update(entity);
	}
	
	/**
	 * 根据sql查询NormTask对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTask对象集合
	 */
	public List<NormTask> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select NormTask.* from NormTask");
		List<NormTask> list = normTaskDao.findListBySql(NormTask.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询NormTask集合数量
	 * @return NormTask集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from NormTask");
		int count = normTaskDao.findCountBySql(NormTask.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询NormTask分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTask分页对象
	 */
	public Pager<NormTask> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<NormTask> pager = new Pager<NormTask>(currentPage, pageSize, totalRecord);
		List<NormTask> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询NormTask对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTask对象集合
	 */
	public List<NormTask> findList(String name,int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTask.class, "status", "=", 1));
		expList.add(new WhereExp(NormTask.class, "del_flag", "=", 1));
		expList.add(new WhereExp(NormTask.class, "norm_task_name", "like", "%"+name+"%"));
		List<NormTask> NormTaskList = normTaskDao.findList(NormTask.class, expList, currentPage, pageSize);	
		return NormTaskList;
	}
	
	/**
	 * 根据表达式查询NormTask集合数量
	 * @return NormTask集合数量
	 */
	public int findCount(String name){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTask.class, "status", "=", 1));
		expList.add(new WhereExp(NormTask.class, "del_flag", "=", 1));
		expList.add(new WhereExp(NormTask.class, "norm_task_name", "like", "%"+name+"%"));
		int count = normTaskDao.findCount(NormTask.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询NormTask分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTask分页对象
	 */
	public Pager<NormTask> findPage(String name,int currentPage, int pageSize){
		int totalRecord = findCount(name);	
		Pager<NormTask> pager = new Pager<NormTask>(currentPage, pageSize, totalRecord);
		List<NormTask> dataList = findList(name,pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<NormTask> findHasPage(String name,int currentPage, int pageSize){
		int totalRecord = findHasCount(name);	
		Pager<NormTask> pager = new Pager<NormTask>(currentPage, pageSize, totalRecord);
		List<NormTask> dataList = findHasList(name,pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}

	private List<NormTask> findHasList(String name,int currentPage, int pageSize) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTask.class, "status", "=", 4));
		expList.add(new WhereExp(NormTask.class, "del_flag", "=", 1));
		expList.add(new WhereExp(NormTask.class, "norm_task_name", "like", "%"+name+"%"));
		List<NormTask> NormTaskList = normTaskDao.findList(NormTask.class, expList, currentPage, pageSize);	
		return NormTaskList;
	}

	private int findHasCount(String name) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTask.class, "status", "=", 4));
		expList.add(new WhereExp(NormTask.class, "del_flag", "=", 1));
		expList.add(new WhereExp(NormTask.class, "norm_task_name", "like", "%"+name+"%"));
		int count = normTaskDao.findCount(NormTask.class, expList);	
		return count;
	}

	public void resetTask(NormTask normTask) {
		normTask.setStatus(1);
		normTaskDao.update(normTask);
	}

	public List<NormTask> findNormTask() {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTask.class, "status", "=", 1));
		expList.add(new WhereExp(NormTask.class, "del_flag", "=", 1));
		List<NormTask> NormTaskList = normTaskDao.findList(NormTask.class, expList);	
		return NormTaskList;
	}
}


