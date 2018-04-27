package com.hongedu.pems.util.excel;

import org.springframework.stereotype.Component;

/**
 * excel数据处理接口
 * @author zyb
 *
 */
@Component
public interface ExcelExcuter {
	/**
	 * 导入数据处理
	 * @param pram 行数据，一维数组
	 * @return 导入结果，成功返回空，失败返回失败原因
	 */
	public String impExcute(String[] pram);
}
