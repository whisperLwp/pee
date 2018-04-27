package com.hongedu.pems.base.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体-数据库映射注解
 * name：数据库表名；pk：实体主键属性
 * @author zyb
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
	public enum param{name,pk};
	
	public String name() default "";
	public String pk() default "id";
}

