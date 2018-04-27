package com.hongedu.pems.base.dao;


import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
import com.hongedu.pems.base.dao.expression.Exp;
import com.hongedu.pems.base.dao.expression.impl.OrderExp;
import com.hongedu.pems.base.dao.expression.impl.WhereExp;


/**
 * baseDao辅助工具类
 * 供baseDaoImpl单独使用
 * @author zyb
 */
public class BaseDaoUtil {
	
	/** 设置一些操作的常量 */
	protected static final String SQL_INSERT = "insert";
	protected static final String SQL_UPDATE = "update";
	protected static final String SQL_DELETE = "delete";
	
	/**
	 * 组装SQL
	 * @param entity
	 * @param sqlFlag 类型：增、删、改
	 * @return 组装sql
	 */
	protected static <T> String makeSql(T entity, String sqlFlag) {
		StringBuffer sql = new StringBuffer();
		Field[] fields = getFields(entity);
		String tableName = getTableProperties(entity.getClass(), "name");
		if (sqlFlag.equals(SQL_INSERT)) {
			sql.append(" INSERT INTO " + tableName);
			sql.append("(");
			for (int i = 0; fields != null && i < fields.length; i++) {
				fields[i].setAccessible(true); // 暴力反射
				String column = fields[i].getName();
				sql.append(lowerStrToUnderline(column)).append(",");
			}
			sql = sql.deleteCharAt(sql.length() - 1);
			sql.append(") VALUES (");
			for (int i = 0; fields != null && i < fields.length; i++) {
				sql.append("?,");
			}
			sql = sql.deleteCharAt(sql.length() - 1);
			sql.append(")");
		} else if (sqlFlag.equals(SQL_UPDATE)) {
			String pk = getTableProperties(entity.getClass(), "pk");

			sql.append(" UPDATE " + tableName + " SET ");
			for (int i = 0; fields != null && i < fields.length; i++) {
				fields[i].setAccessible(true); // 暴力反射
				String column = fields[i].getName();
				if (column.equals(pk)) { // id 代表主键
					continue;
				}
				ManyToOne meta = fields[i].getAnnotation(ManyToOne.class);  
	        	if(meta != null) continue;
				sql.append(lowerStrToUnderline(column)).append("=").append("?,");
			}
			sql = sql.deleteCharAt(sql.length() - 1);
			pk = lowerStrToUnderline(pk);
			sql.append(" WHERE " + pk + "=?");
		} else if (sqlFlag.equals(SQL_DELETE)) {
			String pk = getTableProperties(entity.getClass(), "pk");
			pk = lowerStrToUnderline(pk);
			sql.append(" DELETE FROM " + tableName + " WHERE " + pk + "=?");
		}
//		System.out.println("SQL=" + sql);
		return sql.toString();

	}

	/**
	 * 设置参数
	 * @param entity
	 * @param sqlFlag 类型：增、删、改
	 * @return 参数值集合
	 */
	protected static <T>  Object[] setArgs(T entity, String sqlFlag) {
		Field[] fields = getFields(entity);
		if (sqlFlag.equals(SQL_INSERT)) {
			Object[] args = new Object[fields.length];
			for (int i = 0; args != null && i < args.length; i++) {
				try {
					fields[i].setAccessible(true); // 暴力反射
					args[i] = fields[i].get(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return args;
		} else if (sqlFlag.equals(SQL_UPDATE)) {
			List<Object> tempList = new ArrayList<>();
			//Object[] tempArr = new Object[fields.length];
			for (int i = 0; i<fields.length; i++) {
				try {
					ManyToOne meta = fields[i].getAnnotation(ManyToOne.class);  
		        	if(meta != null) continue;
					fields[i].setAccessible(true); // 暴力反射
					tempList.add(fields[i].get(entity));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Object[] tempArr = new Object[tempList.size()];
			tempList.toArray(tempArr);
			Object[] args = new Object[tempList.size()];
			System.arraycopy(tempArr, 1, args, 0, tempArr.length - 1); // 数组拷贝
			args[args.length - 1] = tempArr[0];
			return args;
		} else if (sqlFlag.equals(SQL_DELETE)) {
			Object[] args = new Object[1]; // 长度是1
			fields[0].setAccessible(true); // 暴力反射
			try {
				args[0] = fields[0].get(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return args;
		}
		return null;

	}

	/**
	 * 设置参数类型（写的不全，只是一些常用的）
	 * @param entity
	 * @param sqlFlag 类型：增、删、改
	 * @return 参数类型码集合
	 */
	protected static <T>  Integer[] setArgTypes(T entity, String sqlFlag) {
		Field[] fields = getFields(entity);
		if (sqlFlag.equals(SQL_INSERT)) {
			Integer[] argTypes = new Integer[fields.length];
			try {
				for (int i = 0; argTypes != null && i < argTypes.length; i++) {
					fields[i].setAccessible(true); // 暴力反射
					argTypes[i] = getColumnType(fields[i].get(entity).getClass().getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return argTypes;
		} else if (sqlFlag.equals(SQL_UPDATE)) {
			Integer[] tempArgTypes = new Integer[fields.length];
			Integer[] argTypes = new Integer[fields.length];
			try {
				for (int i = 0; tempArgTypes != null && i < tempArgTypes.length; i++) {
					fields[i].setAccessible(true); // 暴力反射
					tempArgTypes[i] = getColumnType(fields[i].get(entity).getClass().getName());
				}
				System.arraycopy(tempArgTypes, 1, argTypes, 0, tempArgTypes.length - 1); // 数组拷贝
				argTypes[argTypes.length - 1] = tempArgTypes[0];

			} catch (Exception e) {
				e.printStackTrace();
			}
			return argTypes;

		} else if (sqlFlag.equals(SQL_DELETE)) {
			Integer[] argTypes = new Integer[1]; // 长度是1
			try {
				fields[0].setAccessible(true); // 暴力反射
				if (fields[0].get(entity).getClass().getName().equals("java.lang.String")) {
					argTypes[0] = Types.VARCHAR;
				} else if (fields[0].get(entity).getClass().getName().equals("java.lang.Integer")) {
					argTypes[0] = Types.INTEGER;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return argTypes;
		}
		return null;
	}

	/**
	 * 获取pojo表注解属性
	 * @param pojoClass 泛型类型
	 * @param annoPram 注解属性key
	 * @return 注解属性value
	 */
	public static  String getTableProperties(Class<?> pojoClass, String annoPram) {
		if (pojoClass.isAnnotationPresent(Table.class)) {
			Table table = (Table) pojoClass.getAnnotation(Table.class);
			if (StringUtils.equals(annoPram, "name")) {
				return table.name();
			} else if (StringUtils.equals(annoPram, "pk")) {
				return table.pk();
			}
			return null;
		} else {
			return lowerStrToUnderline(pojoClass.getSimpleName());
		}
	}

	/**
	 * 驼峰法转下划线
	 * @param str 源字符串
	 * @return 目标字符串 
	 */
	protected static String lowerStrToUnderline(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		StringBuilder sb = new StringBuilder(str);
		char c;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c >= 'A' && c <= 'Z') {
				sb.replace(i + count, i + count + 1, (c + "").toLowerCase());
				sb.insert(i + count, "_");
				count++;
			}
		}
		return sb.toString();
	}
	
    /**
     * 下划线转驼峰法
     * @param line 源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    protected static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
	
	/**
	 * 解析过滤pojo字段
	 * @param entity pojo对象
	 * @return 过滤后字段集合
	 */
    protected static <T>  Field[] getFields(T entity) {
		try {
			Field[] selfFields = entity.getClass().getDeclaredFields();
			Field[] superFields = entity.getClass().getSuperclass().getDeclaredFields();
			Field[] fields = new Field[selfFields.length + superFields.length];
			System.arraycopy(selfFields, 0, fields, 0, selfFields.length);
			System.arraycopy(superFields, 0, fields, selfFields.length, superFields.length);
			List<Field> tmp = new ArrayList<Field>();
			for (Field f : fields) {
				f.setAccessible(true);
				// 过滤空值
				if (f.get(entity) == null)
					continue;
				// 过滤序列号
				if (StringUtils.equals(f.getName(), "serialVersionUID"))
					continue;
				
				tmp.add(f);
			}
			return tmp.toArray(new Field[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取主键的值
	 * @param entity pojo对象
	 * @return 主键值
	 */
	protected static <T>  Object getPkValue(T entity) {
		Object obj = null;
		try {
			String pk = getTableProperties(entity.getClass(), "pk");
			Field f = entity.getClass().getDeclaredField(pk);
			f.setAccessible(true);
			obj = f.get(entity);
			if (obj instanceof Integer) {
				return Integer.valueOf(obj.toString());
			} else if (obj instanceof String) {
				return obj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获取主键的类型码
	 * @param entity pojo对象
	 * @return 主键类型码
	 */
	protected static <T>  Integer getPkType(T entity) {
		try {
			String pk = getTableProperties(entity.getClass(), "pk");
			Field f = entity.getClass().getDeclaredField(pk);
			f.setAccessible(true);
			return getColumnType(f.get(entity).getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static <T> int[] getColumnTypes(T entity){
		Field[] fields = getFields(entity);
		int[] columnTypes = new int[fields.length];
		try {
			for (int i=0; i<fields.length; i++) {
				columnTypes[i] = getColumnType(fields[i].get(entity).getClass().getName());
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columnTypes;
	}
	
	/**
	 * 获取字段的类型码
	 * @param columnType 字段的java数据类型
	 * @return sql类型码
	 */
	protected static <T>  Integer getColumnType(String columnType) {
		if (columnType.equals("java.lang.String")) {
			return Types.VARCHAR;
		} else if (columnType.equals("java.lang.Double")) {
			return Types.DECIMAL;
		} else if (columnType.equals("java.lang.Integer")) {
			return Types.INTEGER;
		} else if (columnType.equals("java.util.Date")) {
			return Types.DATE;
		} else {
			return null;
		}
	}

	/**
	 * 将map对象转换为bean
	 * @param 查询结果集
	 * @param entityClass 泛型类型
	 * @param tableMapList map对象集合
	 * @return pojo对象集合
	 */
	protected static <T> List<T> mapToBean(Class<T> entityClass, List<Map<String, Object>> tableMapList) {
		try {
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> tableMap : tableMapList) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String key : tableMap.keySet()) {
					//过滤空值
					if(tableMap.get(key) == null) continue;
					map.put(underline2Camel(key, true), tableMap.get(key));
				}
				mapList.add(map);
			}
			
			List<T> beanList = new ArrayList<T>();
			for (Map<String, Object> map : mapList) {
				T entity = entityClass.newInstance();
				BeanUtils.populate(entity, map);
				
				Field[] fields = entityClass.getDeclaredFields();  
	            for(Field field : fields){  
	                //获取字段中包含fieldMeta的注解  
	            	ManyToOne meta = field.getAnnotation(ManyToOne.class);  
	            	if(meta == null) continue;
	            	Object obj = field.getType().newInstance();
	            	BeanUtils.populate(obj, genJoinMap(map, field));
	            	
	            	field.setAccessible(true);
	            	field.set(entity, obj);
	            }  
	            
	            beanList.add(entity);
			}
			return beanList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
	}
	
	private static Map<String, Object> genJoinMap(Map<String, Object> sourceMap,Field field){
//		String table = getTableProperties(entityClass, "name");
		
		Map<String, Object> targetMap = new HashMap<String, Object>();
		for (String key : sourceMap.keySet()) {
			if(key.contains(underline2Camel(field.getName(), true))){
				targetMap.put(lowerCaseFirst(key.replaceAll(underline2Camel(field.getName(), true), "")), sourceMap.get(key));
			}
		}
		return targetMap;
	}
	
	/**
	 * 生成对象的sql查询语句
	 * @param result 查询结果集
	 * @param entityClass 对象类型
	 * @return sql查询语句
	 */
	public static String genQuerySqlByBean(String searchResult, Class<?> entityClass, List<Exp> expList){
		try {
			StringBuffer selectSql = new StringBuffer();
			StringBuffer fromSql = new StringBuffer();
			String mainTable = getTableProperties(entityClass, "name");
			String mainAlias = getAlias(entityClass);
			selectSql.append("select ").append("distinct ").append(mainAlias).append(".* ");
			fromSql.append("from ").append(mainTable).append(" as ").append(mainAlias).append(" ");
			
			Field[] fields = entityClass.getDeclaredFields();  
			for(Field field : fields){
	            //获取字段中包含fieldMeta的注解  
	        	ManyToOne meta = field.getAnnotation(ManyToOne.class);  
	        	if(meta == null) continue;
	        	
	        	if(!hasSearchResult(meta.result(), searchResult)) continue;
	        	String joinTable = getTableProperties(field.getType(), "name");
	        	String joinAlias = "`"+field.getName()+"`";
				String pk = getTableProperties(field.getType(), "pk");
				String fk = meta.joinKey();
				selectSql.append(",").append(genJoinSelectSql(field)).append(" ");
//				selectSql.append(",").append(joinTable).append(".* ");
				fromSql.append("left join ").append(joinTable).append(" ").append(joinAlias).append(" on ").append(mainAlias).append(".").append(fk).append("=").append(joinAlias).append(".").append(lowerStrToUnderline(pk)).append(" ");
			} 
			String sql = selectSql.append(fromSql).append(genExpSql(expList)).toString();
			return sql;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String genJoinSelectSql(Field joinField){
		StringBuffer selectSql = new StringBuffer(); 
		Field[] fields = joinField.getType().getDeclaredFields();
		String table = getTableProperties(joinField.getType(), "name");
		String alias = joinField.getName();
		for(Field field : fields){
			if (StringUtils.equals(field.getName(), "serialVersionUID"))
				continue;
			if(field.getAnnotations().length != 0)
				continue;
			selectSql.append(alias).append(".").append(lowerStrToUnderline(field.getName())).append(" ").append(alias).append("__").append(lowerStrToUnderline(field.getName())).append(",");
		} 
		if(!selectSql.toString().isEmpty()){
			return selectSql.substring(0, selectSql.length() - 1);
		}
		return selectSql.toString();
	}
	
	
	public static String genCountSqlByBean(Class<?> entityClass, List<Exp> expList){
		try {
			StringBuffer selectSql = new StringBuffer();
			StringBuffer fromSql = new StringBuffer();
			String mainTable = getTableProperties(entityClass, "name");
			String mainPk = getTableProperties(entityClass, "pk");
			String mainAlias = getAlias(entityClass);
			selectSql.append("select count(distinct ").append(mainAlias).append(".").append(lowerStrToUnderline(mainPk)).append(") ");
			fromSql.append("from ").append(mainTable).append(" as ").append(mainAlias).append(" ");
			
			Field[] fields = entityClass.getDeclaredFields();  
	        for(Field field : fields){  
	            //获取字段中包含fieldMeta的注解  
	        	ManyToOne meta = field.getAnnotation(ManyToOne.class);  
	        	if(meta == null) continue;
	        	String joinTable = getTableProperties(field.getType(), "name");
	        	String joinAlias = "`"+field.getName()+"`";
				String pk = getTableProperties(field.getType(), "pk");
				String fk = meta.joinKey();
				fromSql.append("left join ").append(joinTable).append(" ").append(joinAlias).append(" on ").append(mainAlias).append(".").append(fk).append("=").append(joinAlias).append(".").append(lowerStrToUnderline(pk)).append(" ");
	        } 
	        
			return selectSql.append(fromSql).append(genExpSql(expList)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		
//		List<Exp> list = new ArrayList<Exp>();
//		list.add(new WhereExp(User.class, "user_id", "=", "1"));
//		list.add(new OrderExp(User.class, "user_id", "desc"));
//		System.out.println(genQuerySqlByBean("",User.class, list));
//		System.out.println(genCountSqlByBean(User.class, list));
//		
		
	}
	/**
	 * 生成条件表达式
	 * @param expList 表达式集合
	 * @return sql
	 */
	protected static String genExpSql(List<Exp> expList){
		if(expList == null || expList.isEmpty()) return "";
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		StringBuffer orderSql = new StringBuffer();
		for (Exp exp : expList) {
			String tableAlis = getAlias(exp.genExpClass());
			//生成条件表达式
			if(exp instanceof WhereExp){
				whereSql.append(exp.genExpSql(tableAlis));
			}
			//生成排序表达式
			else if(exp instanceof OrderExp){
				orderSql.append(exp.genExpSql(tableAlis));
			}
		}
		if(StringUtils.isNotEmpty(orderSql.toString())){
			orderSql.insert(0, " order by ");
			if(orderSql.toString().endsWith(",")){
				orderSql = new StringBuffer(orderSql.substring(0, orderSql.length()-1));
			}
		}
		return whereSql.append(orderSql).toString();
	}
	
	//首字母小写
	private static String lowerCaseFirst(String s){
		if(Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	protected static String getAlias(Class<?> entityClass){
		String alias = entityClass.getSimpleName();
		return "`"+lowerCaseFirst(alias)+"`";
	}
	
	//判断查询结果集是否存在,true存在，false不存在
	private static boolean hasSearchResult(String entityResult, String searchResult){
		if(StringUtils.isEmpty(searchResult)){
			return true;
		}
		if(StringUtils.isNotEmpty(entityResult) && entityResult.contains(searchResult)){
			return true;
		}
		return false;
	}
}
