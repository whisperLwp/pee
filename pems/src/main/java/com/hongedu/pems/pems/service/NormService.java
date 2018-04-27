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
import com.hongedu.pems.pems.entity.Norm;
import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.entity.NormDetail;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_norm 表对应业务实现对象
 * 2018/01/31 02:37:11
 */
@Transactional
@Service
public class NormService{
	
	private final static Logger logger= LoggerFactory.getLogger(NormService.class);
	
	@Autowired
	private BaseDaoImpl<Norm> normDao;
    @Autowired
    private NormDetailService normDetailService;
    
	private int pk;
	
	/**
	 * 根据主键查询Norm对象
	 * @param id
	 * @return
	 */
	public Norm findById (Serializable id){
		Norm entity = normDao.findById(Norm.class, id);
		return entity;
	}
	
	/**
	 * 插入Norm对象
	 * @param entity
	 */
	public void save (Norm entity){
		normDao.save(entity);
	}
	/**
	 * 插入Norm对象
	 * @param entity
	 * @return 主键
	 */
	public int saveToPK(Norm entity) {
		pk = normDao.saveToPK(entity);
		return pk;
	}
	/**
	 * 修改Norm对象
	 * @param entity
	 */
	public void update (Norm entity){
		normDao.update(entity);
	}
	
	/**
	 * 删除Norm对象
	 * @param id
	 */
	public void delete (Serializable id){
		normDao.delete(Norm.class, id);
	}
	
	/**
	 * 根据sql查询Norm对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Norm对象集合
	 */
	public List<Norm> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select Norm.* from Norm");
		List<Norm> list = normDao.findListBySql(Norm.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询Norm集合数量
	 * @return Norm集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from Norm");
		int count = normDao.findCountBySql(Norm.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询Norm分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Norm分页对象
	 */
	public Pager<Norm> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<Norm> pager = new Pager<Norm>(currentPage, pageSize, totalRecord);
		List<Norm> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询Norm对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Norm对象集合
	 */
	public List<Norm> findList(int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		List<Norm> NormList = normDao.findList(Norm.class, expList, currentPage, pageSize);	
		return NormList;
	}
	
	/**
	 * 根据表达式查询Norm集合数量
	 * @return Norm集合数量
	 */
	public int findCount(){
		List<Exp> expList = new ArrayList<Exp>();
		int count = normDao.findCount(Norm.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询Norm分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Norm分页对象
	 */
	public Pager<Norm> findPage(int currentPage, int pageSize){
		int totalRecord = findCount();	
		Pager<Norm> pager = new Pager<Norm>(currentPage, pageSize, totalRecord);
		List<Norm> dataList = findList(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
     * 根据考核指标类型查询考核指标
     * @param hrId
     * @return
     */
	public List<Norm> findNormByNormCatId(Integer categoryId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Norm.class, "norm_category_id", "=", categoryId));
		List<Norm> NormList = normDao.findList(Norm.class, expList);
		for (Norm norm : NormList) {
			List<NormDetail> normDetailList=normDetailService.findNormDetailByNormId(norm.getNormId());
			norm.setNormDetailList(normDetailList);
		}
		return NormList;
	}
	
}


