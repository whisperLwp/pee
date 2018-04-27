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
import com.hongedu.pems.pems.entity.Dept;
import com.hongedu.pems.pems.entity.NormDetail;
import com.hongedu.pems.pems.entity.NormTaskEmployee;
import com.hongedu.pems.pems.entity.NormTaskEmployeeDetail;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_norm_task_employee 表对应业务实现对象
 * 2018/01/31 02:37:12
 */
@Transactional
@Service
public class NormTaskEmployeeService{
	
	private final static Logger logger= LoggerFactory.getLogger(NormTaskEmployeeService.class);
	
	@Autowired
	private BaseDaoImpl<NormTaskEmployee> normTaskEmployeeDao;
	@Autowired
	private NormDetailService normDetailService;
	
	/**
	 * 根据主键查询NormTaskEmployee对象
	 * @param id
	 * @return
	 */
	public NormTaskEmployee findById (Serializable id){
		NormTaskEmployee entity = normTaskEmployeeDao.findById(NormTaskEmployee.class, id);
		return entity;
	}
	
	/**
	 * 插入NormTaskEmployee对象
	 * @param entity
	 */
	public void save (NormTaskEmployee entity){
		normTaskEmployeeDao.save(entity);
	}
	
	/**
	 * 修改NormTaskEmployee对象
	 * @param entity
	 */
	public void update (NormTaskEmployee entity){
		normTaskEmployeeDao.update(entity);
	}
	
	/**
	 * 删除NormTaskEmployee对象
	 * @param id
	 */
	public void delete (Serializable id){
		normTaskEmployeeDao.delete(NormTaskEmployee.class, id);
	}
	
	/**
	 * 根据sql查询NormTaskEmployee对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskEmployee对象集合
	 */
	public List<NormTaskEmployee> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select NormTaskEmployee.* from NormTaskEmployee");
		List<NormTaskEmployee> list = normTaskEmployeeDao.findListBySql(NormTaskEmployee.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询NormTaskEmployee集合数量
	 * @return NormTaskEmployee集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from NormTaskEmployee");
		int count = normTaskEmployeeDao.findCountBySql(NormTaskEmployee.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询NormTaskEmployee分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskEmployee分页对象
	 */
	public Pager<NormTaskEmployee> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<NormTaskEmployee> pager = new Pager<NormTaskEmployee>(currentPage, pageSize, totalRecord);
		List<NormTaskEmployee> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询NormTaskEmployee对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskEmployee对象集合
	 */
	public List<NormTaskEmployee> findList(int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		List<NormTaskEmployee> NormTaskEmployeeList = normTaskEmployeeDao.findList(NormTaskEmployee.class, expList, currentPage, pageSize);	
		return NormTaskEmployeeList;
	}
	
	/**
	 * 根据表达式查询NormTaskEmployee集合数量
	 * @return NormTaskEmployee集合数量
	 */
	public int findCount(){
		List<Exp> expList = new ArrayList<Exp>();
		int count = normTaskEmployeeDao.findCount(NormTaskEmployee.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询NormTaskEmployee分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskEmployee分页对象
	 */
	public Pager<NormTaskEmployee> findPage(int currentPage, int pageSize){
		int totalRecord = findCount();	
		Pager<NormTaskEmployee> pager = new Pager<NormTaskEmployee>(currentPage, pageSize, totalRecord);
		List<NormTaskEmployee> dataList = findList(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}

	public List<NormTaskEmployee> findScore(Integer normTaskId, Integer employeeId, Integer normType) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_type", "=", normType));
		List<NormTaskEmployee> NormTaskEmployeeList = normTaskEmployeeDao.findList(NormTaskEmployee.class, expList);
		for (NormTaskEmployee normTaskEmployee : NormTaskEmployeeList) {
			Integer normId = normTaskEmployee.getNormId();
			List<NormDetail> normDetailList=normDetailService.findNormDetailByNormId(normId);
			normTaskEmployee.setNormDetailList(normDetailList);
		}
		return NormTaskEmployeeList;
	}
    /**
     * 查询已经分配项互评目人员
     * @param normTempId
     * @param scoreType
     * @return
     */
	public List<NormTaskEmployee> findEachEmpByScoreType(Integer normTaskId, Integer EmployeeId, 
			int scoreType,int projectId,Integer normTaskProjectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskEmployee.class, "score_type", "=", scoreType));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", EmployeeId));
		expList.add(new WhereExp(NormTaskEmployee.class, "project_id", "=", projectId));
		expList.add(new WhereExp(NormTaskEmployee.class, "work_content_id", "=", normTaskProjectId));
		List<NormTaskEmployee> NormTaskEmployeeList = normTaskEmployeeDao.findList(NormTaskEmployee.class, expList);
		return NormTaskEmployeeList;
	}
    /**
     * 删除互评人员
     * @param normTaskId
     * @param scoreType
     * @param EmployeeId
     */
	public void del(Integer normTaskId, Integer employeeId, int scoreType, Integer eachEmployeeId,
			int projectId, Integer normTaskProjectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskEmployee.class, "score_type", "=", scoreType));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskEmployee.class, "project_id", "=", projectId));
		expList.add(new WhereExp(NormTaskEmployee.class, "score_employee_id", "=", eachEmployeeId));
		expList.add(new WhereExp(NormTaskEmployee.class, "work_content_id", "=", normTaskProjectId));
		normTaskEmployeeDao.delete(NormTaskEmployee.class, expList);
	}
	/**
	 * 删除互评项目
	 * @param normTaskId
	 * @param scoreType
	 * @param EmployeeId
	 */
	public void delEachProject(Integer normTaskId, Integer employeeId,
			int projectId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskEmployee.class, "project_id", "=", projectId));
		normTaskEmployeeDao.delete(NormTaskEmployee.class, expList);
	}
    /**
     * 查询评分详情  用于计算
     * @param normTaskId
     * @param employeeId
     * @return
     */
	public List<NormTaskEmployee> findScoreDetail(Integer normTaskId, Integer employeeId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", employeeId));
		List<NormTaskEmployee> NormTaskEmployeeList = normTaskEmployeeDao.findList(NormTaskEmployee.class, expList);
		return NormTaskEmployeeList;
	}
	
	/**
	 * 查询互评是否完成
	 * @param normTaskId
	 * @param employeeId
	 * @return
	 */
	public List<NormTaskEmployee> findEachScoreDetail(Integer normTaskId, Integer employeeId,Integer scoreEmployeeId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskEmployee.class, "score_employee_id", "=", scoreEmployeeId));
		List<NormTaskEmployee> NormTaskEmployeeList = normTaskEmployeeDao.findList(NormTaskEmployee.class, expList);
		return NormTaskEmployeeList;
	}
	/**
	 * 查询单人评分详情  用于计算
	 * @param normTaskId
	 * @param employeeId
	 * @return
	 */
	public List<NormTaskEmployee> findOneScoreDetail(Integer normTaskId, Integer employeeId, Integer normType) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_type", "=", normType));
		List<NormTaskEmployee> NormTaskEmployeeList = normTaskEmployeeDao.findList(NormTaskEmployee.class, expList);
		return NormTaskEmployeeList;
	}
    /**
     * 
     * @param normTaskId
     * @param employeeId  被评分人Id
     * @param normType
     * @param employeeId2 互评人Id
     * @return
     */
    
	public List<NormTaskEmployee> findEachScore(Integer normTaskId, Integer employeeId, Integer normType,
			Integer employeeId2) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskEmployee.class, "score_employee_id", "=", employeeId2));
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_type", "=", normType));
		List<NormTaskEmployee> NormTaskEmployeeList = normTaskEmployeeDao.findList(NormTaskEmployee.class, expList);
		return NormTaskEmployeeList;
	}

	public void delnNoTEmp(NormTaskEmployeeDetail entity) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployee.class, "norm_task_id", "=", entity.getNormTaskId()));
		expList.add(new WhereExp(NormTaskEmployee.class, "employee_id", "=", entity.getEmployeeId()));
		normTaskEmployeeDao.delete(NormTaskEmployee.class, expList);
	}
}


