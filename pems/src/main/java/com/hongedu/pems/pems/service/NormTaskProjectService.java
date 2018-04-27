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
import com.hongedu.pems.pems.entity.NormTaskProject;
import com.hongedu.pems.pems.entity.NormTemplate;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_norm_task_project 表对应业务实现对象
 * 2018/01/31 02:37:12
 */
@Transactional
@Service
public class NormTaskProjectService{
	
	private final static Logger logger= LoggerFactory.getLogger(NormTaskProjectService.class);
	
	@Autowired
	private BaseDaoImpl<NormTaskProject> normTaskProjectDao;
	
	/**
	 * 根据主键查询NormTaskProject对象
	 * @param id
	 * @return
	 */
	public NormTaskProject findById (Serializable id){
		NormTaskProject entity = normTaskProjectDao.findById(NormTaskProject.class, id);
		return entity;
	}
	
	/**
	 * 插入NormTaskProject对象
	 * @param entity
	 */
	public void save (NormTaskProject entity){
		normTaskProjectDao.save(entity);
	}
	
	/**
	 * 保存对象并返回主键Id
	 * @param entity
	 * @return
	 */
	public int saveToPK(NormTaskProject entity) {
		int saveToPK = normTaskProjectDao.saveToPK(entity);
		return saveToPK;
	}
	
	
	/**
	 * 修改NormTaskProject对象
	 * @param entity
	 */
	public void update (NormTaskProject entity){
		normTaskProjectDao.update(entity);
	}
	/**
	 * 修改被引用状态
	 * @param entity
	 */
	public void updateRefer(NormTaskProject entity) {
		entity.setReferFlag("1");
		normTaskProjectDao.update(entity);
	}

	
	/**
	 * 删除NormTaskProject对象
	 * @param id
	 */
	public void delete (Serializable id){
		normTaskProjectDao.delete(NormTaskProject.class, id);
	}
	
	/**
	 * 根据sql查询NormTaskProject对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskProject对象集合
	 */
	public List<NormTaskProject> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select NormTaskProject.* from NormTaskProject");
		List<NormTaskProject> list = normTaskProjectDao.findListBySql(NormTaskProject.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询NormTaskProject集合数量
	 * @return NormTaskProject集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from NormTaskProject");
		int count = normTaskProjectDao.findCountBySql(NormTaskProject.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询NormTaskProject分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskProject分页对象
	 */
	public Pager<NormTaskProject> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<NormTaskProject> pager = new Pager<NormTaskProject>(currentPage, pageSize, totalRecord);
		List<NormTaskProject> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询NormTaskProject对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskProject对象集合
	 */
	public List<NormTaskProject> findList(int employeeId,int normTaskId,int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskProject.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskProject.class, "norm_task_id", "=", normTaskId));
		List<NormTaskProject> NormTaskProjectList = normTaskProjectDao.findList(NormTaskProject.class, expList, currentPage, pageSize);	
		return NormTaskProjectList;
	}
	
	/**
	 * 根据表达式查询NormTaskProject集合数量
	 * @return NormTaskProject集合数量
	 */
	public int findCount(int employeeId,int normTaskId){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskProject.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskProject.class, "norm_task_id", "=", normTaskId));
		int count = normTaskProjectDao.findCount(NormTaskProject.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询NormTaskProject分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskProject分页对象
	 */
	public Pager<NormTaskProject> findPage(int employeeId,int normTaskId,int currentPage, int pageSize){
		int totalRecord = findCount(employeeId,normTaskId);	
		Pager<NormTaskProject> pager = new Pager<NormTaskProject>(currentPage, pageSize, totalRecord);
		List<NormTaskProject> dataList = findList(employeeId,normTaskId,pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}

	public List<NormTaskProject> findProjectByEmpId(Integer normTaskId,Integer employeeId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskProject.class, "employee_id", "=", employeeId));
		List<NormTaskProject> NormTaskProjectList = normTaskProjectDao.findList(NormTaskProject.class, expList);	
		return NormTaskProjectList;
	}

	public List<NormTaskProject> findNormTaskProjectByEmpIdAndTaskId(Integer employeeId, Integer normTaskId,Integer type) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskProject.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskProject.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskProject.class, "type", "=", type));
		List<NormTaskProject> NormTaskProjectList = normTaskProjectDao.findList(NormTaskProject.class, expList);	
		return NormTaskProjectList;
	}
    /**
     * 根据员工项目考核任务查询工作内容
     * @param employeeId
     * @param normTaskId
     * @param projectId
     * @return
     */
	public List<NormTaskProject> findContentById(Integer employeeId, Integer normTaskId, Integer projectId,Integer type) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskProject.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskProject.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskProject.class, "project_id", "=", projectId));
		expList.add(new WhereExp(NormTaskProject.class, "type", "=", type));
		List<NormTaskProject> NormTaskProjectList = normTaskProjectDao.findList(NormTaskProject.class, expList);	
		return NormTaskProjectList;
	}

	public void delEachProject(Integer normTaskId, Integer employeeId, Integer projectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskProject.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskProject.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskProject.class, "project_id", "=", projectId));
		normTaskProjectDao.delete(NormTaskProject.class, expList);
	}

	
}


