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
import com.hongedu.pems.pems.entity.Admin;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_sys_admin 表对应业务实现对象
 * 2018/01/31 02:37:12
 */
@Transactional
@Service
public class AdminService{
	
	private final static Logger logger= LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private BaseDaoImpl<Admin> adminDao;
	
	/**
	 * 根据主键查询Admin对象
	 * @param id
	 * @return
	 */
	public Admin findById (Serializable id){
		Admin entity = adminDao.findById(Admin.class, id);
		return entity;
	}
	
	/**
	 * 插入Admin对象
	 * @param entity
	 */
	public void save (Admin entity){
		adminDao.save(entity);
	}
	
	/**
	 * 修改Admin对象
	 * @param entity
	 */
	public void update (Admin entity){
		adminDao.update(entity);
	}
	
	/**
	 * 删除Admin对象
	 * @param id
	 */
	public void delete (Serializable id){
		adminDao.delete(Admin.class, id);
	}
	
	/**
	 * 根据sql查询Admin对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Admin对象集合
	 */
	public List<Admin> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select Admin.* from Admin");
		List<Admin> list = adminDao.findListBySql(Admin.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询Admin集合数量
	 * @return Admin集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from Admin");
		int count = adminDao.findCountBySql(Admin.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询Admin分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Admin分页对象
	 */
	public Pager<Admin> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<Admin> pager = new Pager<Admin>(currentPage, pageSize, totalRecord);
		List<Admin> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询Admin对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Admin对象集合
	 */
	public List<Admin> findList(int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		List<Admin> AdminList = adminDao.findList(Admin.class, expList, currentPage, pageSize);	
		return AdminList;
	}
	
	/**
	 * 根据表达式查询Admin集合数量
	 * @return Admin集合数量
	 */
	public int findCount(){
		List<Exp> expList = new ArrayList<Exp>();
		int count = adminDao.findCount(Admin.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询Admin分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Admin分页对象
	 */
	public Pager<Admin> findPage(int currentPage, int pageSize){
		int totalRecord = findCount();	
		Pager<Admin> pager = new Pager<Admin>(currentPage, pageSize, totalRecord);
		List<Admin> dataList = findList(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
}


