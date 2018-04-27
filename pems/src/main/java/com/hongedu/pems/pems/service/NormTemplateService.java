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
import com.hongedu.pems.base.dao.expression.impl.OrderExp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;
import com.hongedu.pems.pems.entity.Dept;
import com.hongedu.pems.pems.entity.NormTemplate;
import com.hongedu.pems.pems.entity.ProjectEmployee;
import com.hongedu.pems.pems.entity.TaskProjectEmployee;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_norm_template 表对应业务实现对象
 * 2018/01/31 02:37:12
 */
@Transactional
@Service
public class NormTemplateService{
	
	private final static Logger logger= LoggerFactory.getLogger(NormTemplateService.class);
	
	@Autowired
	private BaseDaoImpl<NormTemplate> normTemplateDao;
	
	/**
	 * 根据主键查询NormTemplate对象
	 * @param id
	 * @return
	 */
	public NormTemplate findById (Serializable id){
		NormTemplate entity = normTemplateDao.findById(NormTemplate.class, id);
		return entity;
	}
	
	/**
	 * 插入NormTemplate对象
	 * @param entity
	 */
	public void save (NormTemplate entity){
		entity.setCreatTime(new Date());
		normTemplateDao.save(entity);
	}
	
	/**
	 * 修改NormTemplate对象
	 * @param entity
	 */
	public void update (NormTemplate entity){
		normTemplateDao.update(entity);
	}
	
	/**
	 * 删除NormTemplate对象
	 * @param id
	 */
	public void delete (NormTemplate entity){
		entity.setDelFlag("0");
		normTemplateDao.update(entity);
	}
	
	/**
	 * 根据sql查询NormTemplate对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTemplate对象集合
	 */
	public List<NormTemplate> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select NormTemplate.* from NormTemplate");
		List<NormTemplate> list = normTemplateDao.findListBySql(NormTemplate.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询NormTemplate集合数量
	 * @return NormTemplate集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from NormTemplate");
		int count = normTemplateDao.findCountBySql(NormTemplate.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询NormTemplate分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTemplate分页对象
	 */
	public Pager<NormTemplate> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<NormTemplate> pager = new Pager<NormTemplate>(currentPage, pageSize, totalRecord);
		List<NormTemplate> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询NormTemplate对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTemplate对象集合
	 */
	public List<NormTemplate> findList(String name,int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTemplate.class, "del_flag", "=", 1));
		expList.add(new OrderExp(NormTemplate.class, "creat_time", "desc"));
		expList.add(new WhereExp(NormTemplate.class, "norm_temp_name", "like", "%"+name+"%"));
		List<NormTemplate> NormTemplateList = normTemplateDao.findList(NormTemplate.class, expList, currentPage, pageSize);	
		return NormTemplateList;
	}
	
	/**
	 * 根据表达式查询NormTemplate集合数量
	 * @return NormTemplate集合数量
	 */
	public int findCount(String name){
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTemplate.class, "norm_temp_name", "like", "%"+name+"%"));
		int count = normTemplateDao.findCount(NormTemplate.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询NormTemplate分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return NormTemplate分页对象
	 */
	public Pager<NormTemplate> findPage(String name,int currentPage, int pageSize){
		int totalRecord = findCount(name);	
		Pager<NormTemplate> pager = new Pager<NormTemplate>(currentPage, pageSize, totalRecord);
		List<NormTemplate> dataList = findList(name,pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}

	public int saveToPK(NormTemplate normTemplate) {
		normTemplate.setCreatTime(new Date());
		normTemplate.setReferFlag("0");
		normTemplate.setDelFlag("1");
		int saveToPK = normTemplateDao.saveToPK(normTemplate);
		return saveToPK;
	}
    /**
     * 查询所有考评模板
     * @return
     */
	public List<NormTemplate> findAll() {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTemplate.class, "del_flag", "=", 1));
		List<NormTemplate> NormTemplateList = normTemplateDao.findList(NormTemplate.class, expList);
		return NormTemplateList;
	}

	public List<NormTemplate> findTempName(NormTemplate normTemplate) {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(NormTemplate.class, "del_flag", "=", 1));
		expList.add(new WhereExp(NormTemplate.class, "norm_temp_name", "=", normTemplate.getNormTempName()));
		List<NormTemplate> NormTemplateList = normTemplateDao.findList(NormTemplate.class, expList);
		return NormTemplateList;
	}
}


