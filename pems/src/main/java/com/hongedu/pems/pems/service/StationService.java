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
import com.hongedu.pems.pems.entity.Project;
import com.hongedu.pems.pems.entity.Station;
import com.hongedu.pems.util.page.Pager;

import com.hongedu.pems.base.dao.BaseDaoImpl;

/**
 * @author  
 * el_bp_station 表对应业务实现对象
 * 2018/04/17 04:16:35
 */
@Transactional
@Service
public class StationService{
	
	private final static Logger logger= LoggerFactory.getLogger(StationService.class);
	
	@Autowired
	private BaseDaoImpl<Station> stationDao;
	
	/**
	 * 根据主键查询Station对象
	 * @param id
	 * @return
	 */
	public Station findById (Serializable id){
		Station entity = stationDao.findById(Station.class, id);
		return entity;
	}
	
	/**
	 * 插入Station对象
	 * @param entity
	 */
	public void save (Station entity){
		entity.setDelFlag("1");
		entity.setReferFlag("1");
		stationDao.save(entity);
	}
	
	/**
	 * 修改Station对象
	 * @param entity
	 */
	public void update (Station entity){
		stationDao.update(entity);
	}
	
	/**
	 * 删除Station对象
	 * @param id
	 */
	public void delete (Serializable id){
		stationDao.delete(Station.class, id);
	}
	
	/**
	 * 根据sql查询Station对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Station对象集合
	 */
	public List<Station> findListBySql(int currentPage, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select Station.* from Station");
		List<Station> list = stationDao.findListBySql(Station.class, sql.toString(), currentPage, pageSize);	
		return list;
	}
	
	/**
	 * 根据sql查询Station集合数量
	 * @return Station集合数量
	 */
	public int findCountBySql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from Station");
		int count = stationDao.findCountBySql(Station.class, sql.toString());	
		return count;
	}
	
	/**
	 * 根据sql查询Station分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Station分页对象
	 */
	public Pager<Station> findPageBySql(int currentPage, int pageSize){
		int totalRecord = findCountBySql();	
		Pager<Station> pager = new Pager<Station>(currentPage, pageSize, totalRecord);
		List<Station> dataList = findListBySql(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}
	
	/**
	 * 根据表达式查询Station对象集合
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Station对象集合
	 */
	public List<Station> findList(int currentPage, int pageSize){
		List<Exp> expList = new ArrayList<Exp>();
		List<Station> StationList = stationDao.findList(Station.class, expList, currentPage, pageSize);	
		return StationList;
	}
	
	/**
	 * 根据表达式查询Station集合数量
	 * @return Station集合数量
	 */
	public int findCount(){
		List<Exp> expList = new ArrayList<Exp>();
		int count = stationDao.findCount(Station.class, expList);	
		return count;
	}
	
	/**
	 * 根据表达式查询Station分页对象
	 * @param currentPage 当前页
	 * @param pageSize 分页数
	 * @return Station分页对象
	 */
	public Pager<Station> findPage(int currentPage, int pageSize){
		int totalRecord = findCount();	
		Pager<Station> pager = new Pager<Station>(currentPage, pageSize, totalRecord);
		List<Station> dataList = findList(pager.getFromIndex() , pageSize);	
		pager.setDataList(dataList);
		return pager;
	}

	public List<Station> findAllStation() {
		List<Exp> expList = new ArrayList<Exp>();
		expList.add(new WhereExp(Station.class, "del_flag", "=", "1"));
		List<Station> StationList = stationDao.findList(Station.class, expList);	
		return StationList;
	}
}


