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
import com.hongedu.pems.base.dao.expression.impl.OrderExp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;
import com.hongedu.pems.pems.entity.Dept;
import com.hongedu.pems.pems.entity.DeptVo;
import com.hongedu.pems.util.page.Pager;
import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_sys_dept 表对应业务实现对象
 * 2018/01/31 02:37:13
 */
@Transactional
@Service
public class DeptService{
	
	private final static Logger logger= LoggerFactory.getLogger(DeptService.class);
	
	@Autowired
	private BaseDaoImpl<Dept> deptDao;
	
	/**
	 * 根据主键查询Dept对象
	 * @param id
	 * @return
	 */
	public Dept findById (Serializable id){
		Dept entity = deptDao.findById(Dept.class, id);
		return entity;
	}
	
	/**
	 * 插入Dept对象
	 * @param entity
	 */
	public void save (Dept entity){
		deptDao.save(entity);
	}
	
	/**
	 * 修改Dept对象
	 * @param entity
	 */
	public void update (Dept entity){
		deptDao.update(entity);
	}
	
	/**
	 * 删除Dept对象
	 * @param id
	 */
	public void delete (Serializable id){
		deptDao.delete(Dept.class, id);
	}
	
	/**
	 * 根据sql查询Dept对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Dept对象集合
	 */
	public List<Dept> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select Dept.* from Dept");
		List<Dept> list = deptDao.findListBySql(Dept.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询Dept集合数量
	 * @return Dept集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from Dept");
		int count = deptDao.findCountBySql(Dept.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询Dept分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Dept分页对象
	 */
	public Pager<Dept> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<Dept> pager = new Pager<Dept>(currentPage, pageSize, totalRecord);
		List<Dept> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询Dept对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Dept对象集合
	 */
	public List<Dept> findList(String name,int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Dept.class, "dept_name", "like", "%"+name+"%"));
		List<Dept> DeptList = deptDao.findList(Dept.class, expList, currentPage, pageSize);	
		return DeptList;
	}
	
	/**
	 * 根据表达式查询Dept集合数量
	 * @return Dept集合数量
	 */
	public int findCount(String name){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Dept.class, "dept_name", "like", "%"+name+"%"));
		int count = deptDao.findCount(Dept.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询Dept分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Dept分页对象
	 */
	public Pager<Dept> findPage(String name,int currentPage, int pageSize){
		int totalRecord = findCount(name);	
		Pager<Dept> pager = new Pager<Dept>(currentPage, pageSize, totalRecord);
		List<Dept> dataList = findList(name,pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
    /**
     * 查询所有部门
     * @return
     */
	public List<Dept> findDept() {
		List<Exp> expList = new ArrayList<Exp>();
		List<Dept> DeptList = deptDao.findList(Dept.class, expList);	
		return DeptList;
	}
	
	/**
	 * 查询所有部门  封装下拉列表
	 * @return
	 */
	public List<DeptVo> findDeptOption() {
		List<Exp> expList = new ArrayList<Exp>();
		List<Dept> DeptList = deptDao.findList(Dept.class, expList);	
		List<DeptVo> deptVoList = new ArrayList<>();
		for (Dept dept : DeptList) {
			DeptVo deptVo = new DeptVo();
			if(dept.getpDeptId().equals("1")){
				deptVo.setDeptName(dept.getDeptName());
				deptVo.setDeptCode(dept.getDeptCode());
				deptVo.setLevelId("1");
				List<DeptVo> list = new ArrayList<>();
				for (Dept dept2 : DeptList) {
					if(StringUtils.equals(dept2.getpDeptId(), dept.getDeptCode())){
						DeptVo deptVo2 = new DeptVo();
						deptVo2.setDeptCode(dept2.getDeptCode());
						deptVo2.setDeptName(dept2.getDeptName());
						deptVo2.setLevelId("2");
						list.add(deptVo2);
					}
					deptVo.setList(list);
				}
				deptVoList.add(deptVo);
			}
			
		}
		return deptVoList;
	}

	public Dept findLevelById(Integer deptId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Dept.class, "dept_code", "=", deptId));
		List<Dept> DeptList = deptDao.findList(Dept.class, expList);
		Dept dept=null;
		if(!DeptList.isEmpty()){
			dept = DeptList.get(0);
		}
		return dept;
	}
    /**
     * 查询deptCode
     * @return
     */
	public String findDeptCode(String pDeptId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Dept.class, "p_dept_id", "=", pDeptId));
		expList.add(new OrderExp(Dept.class, "dept_code", "desc"));
		List<Dept> DeptList = deptDao.findList(Dept.class, expList);
		String deptCoode=null;
		if(!DeptList.isEmpty()){
			deptCoode = DeptList.get(0).getDeptCode();
		}
		return deptCoode;
	}

	public String findDeptByCode(String deptId2) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Dept.class, "dept_code", "=", deptId2));
		List<Dept> DeptList = deptDao.findList(Dept.class, expList);
		String deptName=null;
		if(!DeptList.isEmpty()){
			deptName = DeptList.get(0).getDeptName();
		}
		return deptName;
	}
    
	/**
	 * 部门负责人查询父级部门负责人
	 * @param getpDeptId
	 * @return
	 */
	public Dept findLevel(String DeptId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Dept.class, "dept_code", "=", DeptId));
		List<Dept> DeptList = deptDao.findList(Dept.class, expList);
		Dept dept=null;
		if(!DeptList.isEmpty()){
			dept = DeptList.get(0);
		}
		return dept;
	}
}


