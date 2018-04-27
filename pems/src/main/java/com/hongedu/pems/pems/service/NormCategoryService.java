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
import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.entity.ProjectEmployee;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_norm_category 表对应业务实现对象
 * 2018/01/31 02:37:11
 */
@Transactional
@Service
public class NormCategoryService{
	
	private final static Logger logger= LoggerFactory.getLogger(NormCategoryService.class);
	
	@Autowired
	private BaseDaoImpl<NormCategory> normCategoryDao;
	
	/**
	 * 根据主键查询NormCategory对象
	 * @param id
	 * @return
	 */
	public NormCategory findById (Serializable id){
		NormCategory entity = normCategoryDao.findById(NormCategory.class, id);
		return entity;
	}
	
	/**
	 * 插入NormCategory对象
	 * @param entity
	 */
	public void save (NormCategory entity){
		normCategoryDao.save(entity);
	}
	/**
	 * 插入NormCategory对象
	 * @param entity
	 */
	public int saveToPK (NormCategory entity){
		int pk = normCategoryDao.saveToPK(entity);
		return pk;
	}
	
	/**
	 * 修改NormCategory对象
	 * @param entity
	 */
	public void update (NormCategory entity){
		normCategoryDao.update(entity);
	}
	
	/**
	 * 删除NormCategory对象
	 * @param id
	 */
	public void delete (Serializable id){
		normCategoryDao.delete(NormCategory.class, id);
	}
	
	/**
	 * 根据sql查询NormCategory对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormCategory对象集合
	 */
	public List<NormCategory> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select NormCategory.* from NormCategory");
		List<NormCategory> list = normCategoryDao.findListBySql(NormCategory.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询NormCategory集合数量
	 * @return NormCategory集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from NormCategory");
		int count = normCategoryDao.findCountBySql(NormCategory.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询NormCategory分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormCategory分页对象
	 */
	public Pager<NormCategory> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<NormCategory> pager = new Pager<NormCategory>(currentPage, pageSize, totalRecord);
		List<NormCategory> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询NormCategory对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormCategory对象集合
	 */
	public List<NormCategory> findList(int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		List<NormCategory> NormCategoryList = normCategoryDao.findList(NormCategory.class, expList, currentPage, pageSize);	
		return NormCategoryList;
	}
	
	/**
	 * 根据表达式查询NormCategory集合数量
	 * @return NormCategory集合数量
	 */
	public int findCount(){
		List<Exp> expList = new ArrayList<Exp>();
		int count = normCategoryDao.findCount(NormCategory.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询NormCategory分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormCategory分页对象
	 */
	public Pager<NormCategory> findPage(int currentPage, int pageSize){
		int totalRecord = findCount();	
		Pager<NormCategory> pager = new Pager<NormCategory>(currentPage, pageSize, totalRecord);
		List<NormCategory> dataList = findList(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
    /**
     * 查询所有考核指标类型
     * @return
     */
	public List<NormCategory> findAllNormCategory() {
		List<Exp> expList = new ArrayList<Exp>();
		List<NormCategory> NormCategoryList = normCategoryDao.findList(NormCategory.class, expList);	
		return NormCategoryList;
	}
	
    /**
     * 查询考核指标分类
     * @param normTempId
     * @return
     */
	public List<NormCategory> findByNormTempId(Integer normTempId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormCategory.class, "norm_template_id", "=", normTempId));
		List<NormCategory> NormCategoryList = normCategoryDao.findList(NormCategory.class, expList);
		return NormCategoryList;
	}
}


