package com.hongedu.pems.base.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体-数据库映射注解
 * 多对一注解，是用在需要映射的对象属性上
 * @author zyb
 * 
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ManyToOne {
	public enum param{joinKey,result};
	/**表连接的外键字段,是表字段,不是实体属性*/
	public String joinKey() default "";
	/**查询结果集，多个用逗号隔开*/
	public String result() default "";
}

