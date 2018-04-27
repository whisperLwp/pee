package com.hongedu.pems.util.excel;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class BaseExcel {

	public static final String DOWNLOAD_PATH = "download";// 错误文件下载路径
	public static final String TEMPLATE_PATH = "template";// 模板下载路径
	public static final String EXCEL_SUFFIX = ".xlsx";// 生成下载文件格式

	public static final String ORG_STUDENT_TYPE = "org_student";

	public static final int ORG_STUDENT_COL = 5;

	private String type;// 导入类型

	private Map<String, Object> map = new HashMap<String, Object>();

	public BaseExcel(String type) {
		this.type = type;
	}

	/**
	 * <p>Title: importRecord</p> 
	 * <p>Description: </p>  
	 * @time 下午6:15:47 
	 * @param inputStream
	 * @param excuter 接口
	 * @return
	 */
	public ExcelBean importRecord(InputStream inputStream, ExcelExcuter excuter) {
		ExcelBean excelBean = new ExcelBean();
		String[][] prams = ExcelUtil.getInstance().read(inputStream);
		prams = filterArray(prams);
		boolean matchFlag = validateMatchToTemplate(prams);
		if (!matchFlag) {
			excelBean.setResult("2");
			excelBean.setErrMsg("模板不匹配");
			return excelBean;
		}
		List<String[]> errPrams = new ArrayList<String[]>();

		for (String[] pram : prams) {
			String result = "";
			result = excuter.impExcute(pram);
			if (!StringUtils.isEmpty(result)) {
				String[] errPram = new String[pram.length + 1];
				List<String> _arr = new ArrayList<String>(Arrays.asList(pram));
				Arrays.asList(pram);
				_arr.add(result);
				errPrams.add(_arr.toArray(errPram));
			}
		}
		/**错误条数 */
		int errCount = errPrams.size();
		int count = prams.length;
		if (errCount != 0) {
			String fileName = getFileName();//错误文件名称
			String downloadPath = getDownloadPath(fileName);//错误文件下载路径
			ExcelUtil.getInstance().write(downloadPath, getTemplatePath(), errPrams);//写出失败文件下载路径   模板路径  错误信息
			excelBean.setDownloadPath(fileName);
			excelBean.setErrCount(errCount);
			excelBean.setResult("1");//1--导入表示失败
		} else {
			excelBean.setResult("0");//0--表示导入成功
		}
		excelBean.setCount(count);
		return excelBean;
	}

	// 验证是否匹配模板
	private boolean validateMatchToTemplate(String[][] prams) {
		// try {
		// InputStream ins = new FileInputStream(getTemplatePath());
		// String[][] templatePrams = ExcelUtil.getInstance().read(ins);
		// String[] pram = prams[0];
		// String[] templatePram = templatePrams[0];
		// if(templatePram ==null ){
		// return false;
		// }
		// if(templatePram.length != pram.length){
		// return false;
		// }
		// for(int i=0;i<pram.length;i++){
		// if(templatePram[i] == null){
		// return false;
		// }else if(!templatePram[i].equals(pram[i])){
		// return false;
		// }
		// }
		// return true;
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		//
		// return false;

		return true;
	}
	/**
	 * <p>Title: getTemplateName</p> 
	 * <p>Description: 模板名称</p>  
	 * @time 下午5:29:50 
	 * @return
	 */
	private String getTemplateName() {
		if (type.equals(ORG_STUDENT_TYPE)) {
			return "org_student";
		} else {
			return "";
		}
	}

	private int getPramLength() {
		if (type.equals(ORG_STUDENT_TYPE)) {
			return ORG_STUDENT_COL;
		} else {
			return 0;
		}
	}

	// excel的二维数组过滤
	private String[][] filterArray(String[][] prams) {
		int length = getPramLength();

		List<String[]> resultList = new ArrayList<String[]>();

		List<String[]> pramList = Arrays.asList(prams);
		for (int i = 0; i < pramList.size(); i++) {
			String[] tempPram = pramList.get(i);
			for (int j = 0; j < length; j++) {
				// 过滤空值
				if (!StringUtils.isEmpty(tempPram[j])) {
					// 过滤列，避免提示信息影响列读取
					String[] tempPram2 = new String[length];
					System.arraycopy(tempPram, 0, tempPram2, 0, length);

					resultList.add(tempPram2);
					break;
				}
			}
		}
		String[][] resultPrams = new String[resultList.size()][length];
		return resultList.toArray(resultPrams);
	}

	// 生成错误文件名称
	private String getFileName() {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return type + timestamp + EXCEL_SUFFIX;
	}

	// 错误文件路径
	private String getDownloadPath(String fileName) {
		String classPath = BaseExcel.class.getClassLoader().getResource("").getFile();
		File classesDir = new File(classPath);
		String appPath = classesDir.getParentFile().getParent();
		return appPath + "/" + DOWNLOAD_PATH + "/" + fileName;
	}

	// 模板路径
	private String getTemplatePath() {
		String classPath = BaseExcel.class.getClassLoader().getResource("").getFile();
		File classesDir = new File(classPath);
		String webinfPath = classesDir.getParent();
		return webinfPath + "/" + TEMPLATE_PATH + "/" + getTemplateName() + EXCEL_SUFFIX;
	}

	public void putUserId(String key1, String key2, String userId) {
		Map<String, List<String>> map2 = (Map<String, List<String>>) map.get(key1);
		if (!map2.containsKey(key2)) {
			map2.put(key2, new ArrayList<String>());
		}
		List<String> userIds = map2.get(key2);
		userIds.add(userId);
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public static void main(String[] args) {
		// String[][] a = {{"1"},{""},{""},};
		// String [][] b = filterArray(a);
		// System.out.println(b);

		int[] aa = { 1, 2, 3, 7, 2, 0, 0, 0, 0 };
		int[] bb = new int[5];
		// 参数（要拷贝的数组源，拷贝的开始位子，要拷贝的目标数字，填写的开始位子，拷贝的长度）
		System.arraycopy(aa, 0, bb, 0, 5);
		System.out.println(bb);
	}
}
