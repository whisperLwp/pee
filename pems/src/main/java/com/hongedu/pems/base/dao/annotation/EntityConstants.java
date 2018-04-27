package com.hongedu.pems.base.dao.annotation;

public class EntityConstants {
	
	public static final String USER = "U";// 用户
	public static final String ADMINISTRATOR = "A";// 管理员
	public static final String TEACHER = "T";// 教师
	public static final String STUDENT = "S";// 学生
	public static final String DELETE = null;// 被删除
	public static final String EXIST = "1";// 存在
	public static final String FEMALE = "0";// 女
	public static final String MALE = "1";// 男
	public static final String FIRST_CODE = "101";// 第一个code的值
	public static final String FIRST_SEQUENCE = "1";// 第一个sequence的值
	public static final String NO_REFER = "0";// 未被引用
	public static final String IS_REFER = "1";// 被引用
	public static final String QUIT_STUDY = "0";// 休学
	public static final String NORMAL_STUDY = "1";// 正常上学
	public static final String DEFAULT_PAGE_NUMBER = "1";// 默认首次进入的页码
	public static final String DEFAULT_PAGE_SIZE = "15";// 默认每页的数量
	public static final String RESOURCE_DIR = "/fileUpload";// 用户上传资源根目录
	public static final String HEAD_IMAGE_DIR = RESOURCE_DIR + "/headImage/";// 用户头像存储目录
	public static final String COURSE_COVER_DIR = RESOURCE_DIR + "/coverUrl/";// 课程封面存放目录
	public static final String COURSE_RESOURCE_DIR = RESOURCE_DIR + "/adminResource/";// 管理员资源存储目录
	public static final String IMAGE_RESOURCE_DIR = RESOURCE_DIR + "/imageResource/";// 图片资源存储目录
	public static final String PHYSICS_URL = "physicsUrl";// 绝对路径(物理地址)
	public static final String WEB_URL = "webUrl";// 相对路径(URL地址)
	public static final String CHARACTER_ENCODING = "UTF-8";// 文件编码
	public static final String IS_AJAX = "IsAjax";// 异步请求
	public static final String IMAGE_NAME = "imageName";

}
