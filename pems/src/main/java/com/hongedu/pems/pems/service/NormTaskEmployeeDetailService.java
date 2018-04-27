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
import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTaskEmployeeDetail;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_norm_task_employee_detail 表对应业务实现对象
 * 2018/01/31 02:37:12
 */
@Transactional
@Service
public class NormTaskEmployeeDetailService{
	
	private final static Logger logger= LoggerFactory.getLogger(NormTaskEmployeeDetailService.class);
	
	@Autowired
	private BaseDaoImpl<NormTaskEmployeeDetail> normTaskEmployeeDetailDao;
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 根据主键查询NormTaskEmployeeDetail对象
	 * @param id
	 * @return
	 */
	public NormTaskEmployeeDetail findById (Serializable id){
		NormTaskEmployeeDetail entity = normTaskEmployeeDetailDao.findById(NormTaskEmployeeDetail.class, id);
		return entity;
	}
	
	/**
	 * 插入NormTaskEmployeeDetail对象
	 * @param entity
	 */
	public void save (NormTaskEmployeeDetail entity){
		entity.setPassFlag("0");
		normTaskEmployeeDetailDao.save(entity);
	}
	
	/**
	 * 修改NormTaskEmployeeDetail对象
	 * @param entity
	 */
	public void update (NormTaskEmployeeDetail entity){
		normTaskEmployeeDetailDao.update(entity);
	}
	
	/**
	 * 删除NormTaskEmployeeDetail对象
	 * @param id
	 */
	public void delete (Serializable id){
		normTaskEmployeeDetailDao.delete(NormTaskEmployeeDetail.class, id);
	}
	
	/**
	 * 根据sql查询NormTaskEmployeeDetail对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskEmployeeDetail对象集合
	 */
	public List<NormTaskEmployeeDetail> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select NormTaskEmployeeDetail.* from NormTaskEmployeeDetail");
		List<NormTaskEmployeeDetail> list = normTaskEmployeeDetailDao.findListBySql(NormTaskEmployeeDetail.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询NormTaskEmployeeDetail集合数量
	 * @return NormTaskEmployeeDetail集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from NormTaskEmployeeDetail");
		int count = normTaskEmployeeDetailDao.findCountBySql(NormTaskEmployeeDetail.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询NormTaskEmployeeDetail分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskEmployeeDetail分页对象
	 */
	public Pager<NormTaskEmployeeDetail> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<NormTaskEmployeeDetail> pager = new Pager<NormTaskEmployeeDetail>(currentPage, pageSize, totalRecord);
		List<NormTaskEmployeeDetail> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询NormTaskEmployeeDetail对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskEmployeeDetail对象集合
	 */
	public List<NormTaskEmployeeDetail> findList(String name,int normTaskId,int employeeId,int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployeeDetail.class, "norm_task_id", "=", normTaskId));
		expList.add(new WhereExp(Employee.class, "real_name", "like", "%"+name+"%"));
		if(employeeId!=0){
			expList.add(new WhereExp(NormTaskEmployeeDetail.class, "employee_id", "=", employeeId));
		}
		List<NormTaskEmployeeDetail> NormTaskEmployeeDetailList = normTaskEmployeeDetailDao.findList(NormTaskEmployeeDetail.class, expList, currentPage, pageSize);	
		return NormTaskEmployeeDetailList;
	}
	
	/**
	 * 根据表达式查询NormTaskEmployeeDetail集合数量
	 * @return NormTaskEmployeeDetail集合数量
	 */
	public int findCount(String name,int normTaskId,int employeeId){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Employee.class, "real_name", "like", "%"+name+"%"));
		if(employeeId!=0){
			expList.add(new WhereExp(NormTaskEmployeeDetail.class, "employee_id", "=", employeeId));
		}
		expList.add(new WhereExp(NormTaskEmployeeDetail.class, "norm_task_id", "=", normTaskId));
		int count = normTaskEmployeeDetailDao.findCount(NormTaskEmployeeDetail.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询NormTaskEmployeeDetail分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTaskEmployeeDetail分页对象
	 */
	public Pager<NormTaskEmployeeDetail> findPage(String name,int normTaskId,int employeeId,int currentPage, int pageSize){
		int totalRecord = findCount(name,normTaskId,employeeId);	
		Pager<NormTaskEmployeeDetail> pager = new Pager<NormTaskEmployeeDetail>(currentPage, pageSize, totalRecord);
		List<NormTaskEmployeeDetail> dataList = findList(name,normTaskId,employeeId,pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
    /**
     * 查询所有考评人员    *归档使用*
     * @param normTaskId
     * @return
     */
	public List<NormTaskEmployeeDetail> findAllEmployee(Integer normTaskId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployeeDetail.class, "norm_task_id", "=", normTaskId));
		List<NormTaskEmployeeDetail> NormTaskEmployeeDetailList = normTaskEmployeeDetailDao.findList(NormTaskEmployeeDetail.class, expList);	
		return NormTaskEmployeeDetailList;
	}

	public List<NormTaskEmployeeDetail> findScoreList(Integer normTaskId, Integer employeeId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployeeDetail.class, "norm_task_id", "=", normTaskId));
		if(employeeId!=0){
			expList.add(new WhereExp(NormTaskEmployeeDetail.class, "employee_id", "=", employeeId));
		}
		List<NormTaskEmployeeDetail> NormTaskEmployeeDetailList = normTaskEmployeeDetailDao.findList(NormTaskEmployeeDetail.class, expList);	
		return NormTaskEmployeeDetailList;
	}
	
	/**
	 * 查询任务   welcom页面使用
	 * @param normTaskId
	 * @param employeeId
	 * @return
	 */
	public List<NormTaskEmployeeDetail> findTaskList(Integer employeeId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployeeDetail.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTask.class, "status", "=", 1));
		List<NormTaskEmployeeDetail> NormTaskEmployeeDetailList = normTaskEmployeeDetailDao.findList(NormTaskEmployeeDetail.class, expList);	
		return NormTaskEmployeeDetailList;
	}

	public void updatePass(Integer employeeId,Integer normTaskId) {
		NormTaskEmployeeDetail entity=new NormTaskEmployeeDetail();
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTaskEmployeeDetail.class, "employee_id", "=", employeeId));
		expList.add(new WhereExp(NormTaskEmployeeDetail.class, "norm_task_id", "=", normTaskId));
		List<NormTaskEmployeeDetail> NormTaskEmployeeDetailList = normTaskEmployeeDetailDao.findList(NormTaskEmployeeDetail.class, expList);	
		if(!NormTaskEmployeeDetailList.isEmpty()){
			Integer normTaskTmployeeDetailId = NormTaskEmployeeDetailList.get(0).getNormTaskTmployeeDetailId();
			entity.setNormTaskTmployeeDetailId(normTaskTmployeeDetailId);
			entity.setPassFlag("0");
			normTaskEmployeeDetailDao.update(entity);
		}
	}
    /**
     * 部门领导查询归档明细表
     * @param normTaskId
     * @param employeeId
     * @param currentPage
     * @param pageSize
     * @return
     */
	/*public Pager<NormTaskEmployeeDetail> findDeptPage(Integer normTaskId, String deptId, Integer currentPage,
			Integer pageSize) {
		List<Employee> employees = employeeService.findEmployeeByDept(deptId);
		int totalRecord = employees.size();	
		List<NormTaskEmployeeDetail> dataList = new ArrayList<>();
		Pager<NormTaskEmployeeDetail> pager= new Pager<>(currentPage, pageSize, totalRecord);
		for (Employee employee : employees) {
			
			dataList = findList(normTaskId,employee.getEmployeeId(),pager.getFromIndex() , pageSize);	
			pager.setDataList(dataList);
		}
		
		return pager;
	}*/
}


