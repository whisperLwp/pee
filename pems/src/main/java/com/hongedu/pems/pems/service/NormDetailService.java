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
import com.hongedu.pems.pems.entity.NormDetail;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_norm_detail 表对应业务实现对象
 * 2018/01/31 02:37:11
 */
@Transactional
@Service
public class NormDetailService{
	
	private final static Logger logger= LoggerFactory.getLogger(NormDetailService.class);
	
	@Autowired
	private BaseDaoImpl<NormDetail> normDetailDao;
	
	/**
	 * 根据主键查询NormDetail对象
	 * @param id
	 * @return
	 */
	public NormDetail findById (Serializable id){
		NormDetail entity = normDetailDao.findById(NormDetail.class, id);
		return entity;
	}
	
	/**
	 * 插入NormDetail对象
	 * @param entity
	 */
	public void save (NormDetail entity){
		normDetailDao.save(entity);
	}
	
	/**
	 * 修改NormDetail对象
	 * @param entity
	 */
	public void update (NormDetail entity){
		normDetailDao.update(entity);
	}
	
	/**
	 * 删除NormDetail对象
	 * @param id
	 */
	public void delete (Serializable id){
		normDetailDao.delete(NormDetail.class, id);
	}
	
	/**
	 * 根据sql查询NormDetail对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormDetail对象集合
	 */
	public List<NormDetail> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select NormDetail.* from NormDetail");
		List<NormDetail> list = normDetailDao.findListBySql(NormDetail.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询NormDetail集合数量
	 * @return NormDetail集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from NormDetail");
		int count = normDetailDao.findCountBySql(NormDetail.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询NormDetail分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormDetail分页对象
	 */
	public Pager<NormDetail> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<NormDetail> pager = new Pager<NormDetail>(currentPage, pageSize, totalRecord);
		List<NormDetail> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询NormDetail对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormDetail对象集合
	 */
	public List<NormDetail> findList(int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		List<NormDetail> NormDetailList = normDetailDao.findList(NormDetail.class, expList, currentPage, pageSize);	
		return NormDetailList;
	}
	
	/**
	 * 根据表达式查询NormDetail集合数量
	 * @return NormDetail集合数量
	 */
	public int findCount(){
		List<Exp> expList = new ArrayList<Exp>();
		int count = normDetailDao.findCount(NormDetail.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询NormDetail分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormDetail分页对象
	 */
	public Pager<NormDetail> findPage(int currentPage, int pageSize){
		int totalRecord = findCount();	
		Pager<NormDetail> pager = new Pager<NormDetail>(currentPage, pageSize, totalRecord);
		List<NormDetail> dataList = findList(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
    /**
     * 根据normId查询明细
     * @param normId
     * @return
     */
	public List<NormDetail> findNormDetailByNormId(Integer normId) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormDetail.class, "norm_id", "=", normId));
		List<NormDetail> NormDetailList = normDetailDao.findList(NormDetail.class, expList);	
		return NormDetailList;
	}
}


