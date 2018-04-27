package com.hongedu.pems.util.export;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

public class ComplexExportExcelClient {
	 private static HSSFWorkbook wb = new HSSFWorkbook();  
	 
	    private static HSSFSheet sheet = wb.createSheet();  
	 
	    @SuppressWarnings({ "unchecked", "deprecation" })  
	    public static void main(String[] args) {  
	 
	        ExportExcel exportExcel = new ExportExcel(wb, sheet);  
	 
	        // 创建列标头LIST  
	        /*List fialList = new ArrayList();  
	 
	        fialList.add("申请人未提供任何联系方式");  
	        fialList.add("无工作单位信息且未提供收入来源信息");  
	        fialList.add("有工作单位但未提供单位地址或电话");  
	        fialList.add("家庭地址缺失");  
	        fialList.add("客户身份证明资料缺");  
	        fialList.add("签名缺失或签名不符合要求");  
	        fialList.add("其它");  
	 
	        List errorList = new ArrayList();  
	 
	        errorList.add("客户主动取消");  
	        errorList.add("个人征信不良");  
	        errorList.add("欺诈申请");  
	        errorList.add("申请人基本条件不符");  
	        errorList.add("申请材料不合规");  
	        errorList.add("无法正常完成征信");  
	        errorList.add("重复申请");  
	        errorList.add("其他");  
	 
	        // 计算该报表的列数  
	        int number = 2 + fialList.size() * 2 + errorList.size() * 2;  
	 
	        // 给工作表列定义列宽(实际应用自己更改列数)  
	        for (int i = 0; i < number; i++) {  
	            sheet.setColumnWidth(i, 3000);  
	        }*/  
	 
	        // 创建单元格样式  
	        HSSFCellStyle cellStyle = wb.createCellStyle();  
	 
	        // 指定单元格居中对齐  
	        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	 
	        // 指定单元格垂直居中对齐  
	        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	 
	        // 指定当单元格内容显示不下时自动换行  
	        cellStyle.setWrapText(true);  
	 
	        // 设置单元格字体  
	        HSSFFont font = wb.createFont();  
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	        font.setFontName("宋体");  
	        font.setFontHeight((short) 200);  
	        cellStyle.setFont(font);  
	 
	        // 创建报表头部  
	        exportExcel.createNormalHead("宏程教育科技有限公司绩效考核",0, 4);  
	 
	        // 设置第二行  
	        /*String[] params = new String[] { "1", " 2","3","4","5" ,"6"};  
	        exportExcel.createNormalTwoRow(params, 4); */ 
	        
	        
	        
	        
	        
	        /*****************人事**********************/
	        
	        //创建人事考评
	        int aa=90;
	        exportExcel.createCtaegory("人事评分("+aa+")%",2, 4);
	        
	        HSSFRow row2 = sheet.createRow(3);  
	   	 
	        HSSFCell cell0 = row2.createCell(0);  
	        cell0.setCellStyle(cellStyle);  
	        cell0.setCellValue(new HSSFRichTextString("考评项"));
	        
	        HSSFCell cell1 = row2.createCell(1);  
	        cell1.setCellStyle(cellStyle);  
	        cell1.setCellValue(new HSSFRichTextString("权重"));
	        
	        HSSFCell cell2 = row2.createCell(2);  
	        cell2.setCellStyle(cellStyle);  
	        cell2.setCellValue(new HSSFRichTextString("考核标准"));  
	 
	        HSSFCell cell3 = row2.createCell(3);  
	        cell3.setCellStyle(cellStyle);  
	        cell3.setCellValue(new HSSFRichTextString("人事评分"));
	        
	        // 合并人事评分栏  
	        sheet.addMergedRegion(new Region(3, (short) 3, 3, (short) 4));
	        
	        // 创建人事考评项  
	        List fialList = new ArrayList();  
	 
	        fialList.add("打卡");  
	        fialList.add("出勤");  
	        fialList.add("早退");  
	        fialList.add("纪律");  
	        List fialList1 = new ArrayList();  
	        
	        fialList1.add("1打"+'\n'+"2卡"+'\n'+"4打"+'\n'+"5卡");  
	        fialList1.add("出勤");  
	        fialList1.add("早退");  
	        fialList1.add("纪律");  
	        List fialList2 = new ArrayList();  
	        
	        fialList2.add("30");  
	        fialList2.add("50");  
	        fialList2.add("20");  
	        fialList2.add("10");  
	        List fialList3 = new ArrayList();  
	        
	        fialList3.add("3");  
	        fialList3.add("5");  
	        fialList3.add("2");  
	        fialList3.add("1");  
	        
	        HSSFCell categoryCell = null;
	        HSSFCell weightCell = null;
	        HSSFCell detailCell = null;
	        HSSFCell scoreCell = null;
	        // 创建不同的LIST的列标题  
	        for (int i = 0; i < fialList.size(); i ++) {  
	        	HSSFRow cellrow = sheet.createRow(4+i);
	        	categoryCell = cellrow.createCell(0);
	        	weightCell = cellrow.createCell(1);
	        	detailCell = cellrow.createCell(2);
	        	scoreCell = cellrow.createCell(3);
	        	categoryCell.setCellStyle(cellStyle);
	        	weightCell.setCellStyle(cellStyle);  
	        	detailCell.setCellStyle(cellStyle);  
	        	scoreCell.setCellStyle(cellStyle);  
	        	categoryCell.setCellValue(new HSSFRichTextString(fialList.get(i)  
                        .toString()));
	        	weightCell.setCellValue(new HSSFRichTextString(fialList2.get(i)  
	        			.toString()));
	        	detailCell.setCellValue(new HSSFRichTextString(fialList1.get(i)  
	        			.toString()));
	        	scoreCell.setCellValue(new HSSFRichTextString(fialList3.get(i)  
	        			.toString()));
	        	sheet.addMergedRegion(new Region(3+(i+1), (short) 3, 3+(i+1), (short) 4));
	        }
	        
	        
	        /*****************岗位**********************/
	        int vv=90;
	        exportExcel.createCtaegory("岗位评分("+vv+")%",4+fialList.size(), 4);
	        
	        HSSFRow row3 = sheet.createRow(4+fialList.size()+1);  
		   	 
	        HSSFCell cell4 = row3.createCell(0);  
	        cell4.setCellStyle(cellStyle);  
	        cell4.setCellValue(new HSSFRichTextString("考评项"));
	        
	        HSSFCell cell5 = row3.createCell(1);  
	        cell5.setCellStyle(cellStyle);  
	        cell5.setCellValue(new HSSFRichTextString("权重"));
	        
	        HSSFCell cell6 = row3.createCell(2);  
	        cell6.setCellStyle(cellStyle);  
	        cell6.setCellValue(new HSSFRichTextString("考核标准"));  
	 
	        HSSFCell cell7 = row3.createCell(3);  
	        cell7.setCellStyle(cellStyle);  
	        cell7.setCellValue(new HSSFRichTextString("员工自评"));
	        
	        HSSFCell cell8 = row3.createCell(4);  
	        cell8.setCellStyle(cellStyle);  
	        cell8.setCellValue(new HSSFRichTextString("直属上级"));
	        
	        
	     // 创建岗位考评项  
	        List fialList4 = new ArrayList();  
	 
	        fialList4.add("打卡");  
	        fialList4.add("出勤");  
	        fialList4.add("早退");  
	        fialList4.add("纪律");  
	        List fialList5 = new ArrayList();  
	        
	        fialList5.add("打"+'\n'+"卡");  
	        fialList5.add("出勤");  
	        fialList5.add("早退");  
	        fialList5.add("纪律");  
	        List fialList6 = new ArrayList();  
	        
	        fialList6.add("30");  
	        fialList6.add("50");  
	        fialList6.add("20");  
	        fialList6.add("10");  
	        List fialList7 = new ArrayList();  
	        
	        fialList7.add("3");  
	        fialList7.add("5");  
	        fialList7.add("2");  
	        fialList7.add("1");  
	        
	        HSSFCell categoryCell1 = null;
	        HSSFCell weightCell2 = null;
	        HSSFCell detailCell3 = null;
	        HSSFCell scoreCell4 = null;
	        HSSFCell scoreCell5 = null;
	        
	        for (int i = 0; i < fialList.size(); i ++) {  
	        	HSSFRow cellrow = sheet.createRow(6+i+fialList.size());
	        	categoryCell1 = cellrow.createCell(0);
	        	weightCell2 = cellrow.createCell(1);
	        	detailCell3 = cellrow.createCell(2);
	        	scoreCell4 = cellrow.createCell(3);
	        	scoreCell5 = cellrow.createCell(4);
	        	categoryCell1.setCellStyle(cellStyle);
	        	weightCell2.setCellStyle(cellStyle);  
	        	detailCell3.setCellStyle(cellStyle);  
	        	scoreCell4.setCellStyle(cellStyle);  
	        	scoreCell5.setCellStyle(cellStyle);  
	        	categoryCell1.setCellValue(new HSSFRichTextString(fialList.get(i)  
                        .toString()));
	        	weightCell2.setCellValue(new HSSFRichTextString(fialList2.get(i)  
	        			.toString()));
	        	detailCell3.setCellValue(new HSSFRichTextString(fialList1.get(i)  
	        			.toString()));
	        	scoreCell4.setCellValue(new HSSFRichTextString(fialList3.get(i)  
	        			.toString()));
	        	scoreCell5.setCellValue(new HSSFRichTextString(fialList3.get(i)  
	        			.toString()));
	        }
	        
	        
	        /****************项目**********************/
	        
	        int dd=90;
	        exportExcel.createCtaegory("项目评分("+dd+")%",6+fialList.size()+fialList.size(), 4);
	        
	        
	        
	        
	        
	        
	        
	        /*// 设置列头  
	        HSSFRow row2 = sheet.createRow(2);  
	 
	        HSSFCell cell0 = row2.createCell(0);  
	        cell0.setCellStyle(cellStyle);  
	        cell0.setCellValue(new HSSFRichTextString("机构代码"));  
	 
	        HSSFCell cell1 = row2.createCell(1);  
	        cell1.setCellStyle(cellStyle);  
	        cell1.setCellValue(new HSSFRichTextString("支行名称"));  
	 
	        HSSFCell cell2 = row2.createCell(2);  
	        cell2.setCellStyle(cellStyle);  
	        cell2.setCellValue(new HSSFRichTextString("无效件"));  
	 
	        HSSFCell cell3 = row2.createCell(2 * fialList.size() + 2);  
	        cell3.setCellStyle(cellStyle);  
	        cell3.setCellValue(new HSSFRichTextString("拒绝件"));  
	 
	        HSSFRow row3 = sheet.createRow(3);  
	 
	        // 设置行高  
	        row3.setHeight((short) 800);  
	 
	        HSSFCell row3Cell = null;  
	        int m = 0;  
	        int n = 0;  
	 
	        // 创建不同的LIST的列标题  
	        for (int i = 2; i < number; i = i + 2) {  
	 
	            if (i < 2 * fialList.size() + 2) {  
	                row3Cell = row3.createCell(i);  
	                row3Cell.setCellStyle(cellStyle);  
	                row3Cell.setCellValue(new HSSFRichTextString(fialList.get(m)  
	                        .toString()));  
	                m++;  
	            } else {  
	                row3Cell = row3.createCell(i);  
	                row3Cell.setCellStyle(cellStyle);  
	                row3Cell.setCellValue(new HSSFRichTextString(errorList.get(n)  
	                        .toString()));  
	                n++;  
	            }  
	 
	        }  
	 
	        // 创建最后一列的合计列  
	        row3Cell = row3.createCell(number);  
	        row3Cell.setCellStyle(cellStyle);  
	        row3Cell.setCellValue(new HSSFRichTextString("合计"));  
	 
	        // 合并单元格  
	        HSSFRow row4 = sheet.createRow(4);  
	 
	        // 合并第三行到第五行的第一列  
	        sheet.addMergedRegion(new Region(2, (short) 0, 4, (short) 0));  
	 
	        // 合并第三行到第五行的第二列  
	        sheet.addMergedRegion(new Region(2, (short) 1, 4, (short) 1));  
	 
	        // 合并第三行的第三列到第AA指定的列  
	        int aa = 2 * fialList.size() + 1;  
	        sheet.addMergedRegion(new Region(2, (short) 2, 2, (short) aa));  
	 
	        int start = aa + 1;  
	 
	        sheet.addMergedRegion(new Region(2, (short) start, 2,  
	                (short) (number - 1)));  
	 
	        // 循环合并第四行的行，并且是每2列合并成一列  
	        for (int i = 2; i < number; i = i + 2) {  
	            sheet.addMergedRegion(new Region(3, (short) i, 3, (short) (i + 1)));  
	 
	        }  
	 
	        // 根据列数奇偶数的不同创建不同的列标题  
	        for (int i = 2; i < number; i++) {  
	            if (i < 2 * fialList.size() + 2) {  
	 
	                if (i % 2 == 0) {  
	                    HSSFCell cell = row4.createCell(i);  
	                    cell.setCellStyle(cellStyle);  
	                    cell.setCellValue(new HSSFRichTextString("无效量"));  
	                } else {  
	                    HSSFCell cell = row4.createCell(i);  
	                    cell.setCellStyle(cellStyle);  
	                    cell.setCellValue(new HSSFRichTextString("占比"));  
	                }  
	            } else {  
	                if (i % 2 == 0) {  
	                    HSSFCell cell = row4.createCell(i);  
	                    cell.setCellStyle(cellStyle);  
	                    cell.setCellValue(new HSSFRichTextString("拒绝量"));  
	                } else {  
	                    HSSFCell cell = row4.createCell(i);  
	                    cell.setCellStyle(cellStyle);  
	                    cell.setCellValue(new HSSFRichTextString("占比"));  
	                }  
	            }  
	 
	        }  */
	 
	        // 循环创建中间的单元格的各项的值  
	        /*for (int i = 5; i < number; i++) {  
	            HSSFRow row = sheet.createRow((short) i);  
	            for (int j = 0; j <= number; j++) {  
	                exportExcel  
	                        .cteateCell(wb, row, (short) j,  
	                                HSSFCellStyle.ALIGN_CENTER_SELECTION, String  
	                                        .valueOf(j));  
	            }  
	 
	        }  
	 
	        // 创建最后一行的合计行  
	        String[] cellValue = new String[number - 1];  
	        for (int i = 0; i < number - 1; i++) {  
	            cellValue[i] = String.valueOf(i);  
	 
	        }  
	        exportExcel.createLastSumRow(1, cellValue);*/  
	 
	        exportExcel.outputExcel("D:\\拒绝件统计.xls");  
	 
	    }  

}
