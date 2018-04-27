package com.hongedu.pems.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	// 模板路径

	public static ExcelUtil getInstance(){
		return new ExcelUtil();
	}
	
	/**
	 * 读excel
	 * 
	 * @param path
	 * @throws Exception
	 */
	public String[][] read(InputStream ins) {
		try {
			Workbook wkbook = WorkbookFactory.create(ins);
			// 获取第一个表格!
			Sheet sheet = wkbook.getSheetAt(0);

			int rowNum = sheet.getLastRowNum();
			int colNum = sheet.getDefaultColumnWidth();
			String[][] results = new String[rowNum][colNum];
			// 第一行是标题,从第二行开始读取
			for (int i = 1; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;
				// if(isEmpty(row,colNum)){
				//	continue;
//				}
				for (int j = 0; j < colNum; j++) {
					Cell cell = row.getCell(j);
					results[i - 1][j] =  getCellValue(cell);
				}
			}
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean isEmpty(Row row,int colNum){
		for (int j = 0; j < colNum; j++) {
			Cell cell = row.getCell(j);
			if(!StringUtils.isEmpty(getCellValue(cell))){
				return false;
			}
		}
		return true;
	}
	/**
	 * <p>Title: getCellValue</p> 
	 * <p>Description: </p>  
	 * @time 下午5:38:48 
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {  
		if(cell == null)return "";
        String cellValue = "";  
        DecimalFormat df = new DecimalFormat("#");  
        switch (cell.getCellType()) {  
        case HSSFCell.CELL_TYPE_STRING:  
            cellValue = cell.getRichStringCellValue().getString().trim();  
            break;  
        case HSSFCell.CELL_TYPE_NUMERIC:  
        	if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			}
            cellValue = df.format(cell.getNumericCellValue()).toString();  
            break;  
        case HSSFCell.CELL_TYPE_BOOLEAN:  
            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();  
            break;  
        case HSSFCell.CELL_TYPE_FORMULA:  
            cellValue = cell.getCellFormula();  
            break;  
        default:  
            cellValue = "";  
        }  
        return cellValue;  
    } 
	
	/**
	 * 写excel
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void write(String downloadPath,String templatePath, List<String[]> params){
		try {
			InputStream ins = new FileInputStream(templatePath);
			Workbook wkbook = WorkbookFactory.create(ins);

			Sheet sheet = wkbook.getSheetAt(0); // 读取第一个工作簿
			// Sheet sheet = wkbook.cloneSheet(0);
			Row row;
			Cell cell = null;
			// 添加的起始行
			for (int i = 0; i < params.size(); i++) {
				CellStyle style = getStyle(wkbook);

				row = sheet.createRow(i + 1);
				for (int j = 0; j < params.get(i).length; j++) {
					myCreateCell(j, params.get(i)[j], row, cell, style);
				}
			}
			FileOutputStream os = new FileOutputStream(downloadPath);
			wkbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeStream(OutputStream out,List<String[]> params){
		try {
			Workbook wkbook = new HSSFWorkbook();

			Sheet sheet = wkbook.createSheet(); // 读取第一个工作簿
			// Sheet sheet = wkbook.cloneSheet(0);
			Row row;
			Cell cell = null;
			// 添加的起始行
			for (int i = 0; i < params.size(); i++) {
				CellStyle style = getStyle(wkbook);
				
				row = sheet.createRow(i);
				for (int j = 0; j < params.get(i).length; j++) {
					String value = params.get(i)[j];
					value = StringUtils.isEmpty(value)?"":value;
//					sheet.setColumnWidth(i, value.getBytes().length*2*256);
					sheet.setColumnWidth(i, 5000);
					
					myCreateCell(j, value, row, cell, style);
				}
			}
			wkbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void todo() throws Exception {
		// excel模板路径
		File fi = new File("e:\\1.xlsx");
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
		// 读取excel模板
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		// 读取了模板内所有sheet内容
		HSSFSheet sheet = wb.getSheetAt(0);
		// 在相应的单元格进行赋值
		HSSFCell cell = sheet.getRow(1).getCell(3);
		cell.setCellValue("测试");
		HSSFCell cell2 = sheet.getRow(3).getCell(3);
		cell2.setCellValue("数据");
		HSSFCell cell3 = sheet.getRow(0).getCell(0);
		cell3.setCellValue("大标题");
		// 修改模板内容导出新模板
		FileOutputStream out = new FileOutputStream("e:/2.xlsx");
		wb.write(out);
		out.close();
	}

	private void myCreateCell(int cellnum, String value, Row row,
			Cell cell, CellStyle style,Sheet sheet) {
		
		
		cell = row.createCell((short) cellnum);
		cell.setCellValue(String.valueOf(value));
		cell.setCellStyle(style);
	}
	
	private void myCreateCell(int cellnum, String value, Row row,
			Cell cell, CellStyle style) {
		myCreateCell(cellnum,  value,  row, cell,  style,null);
	}

	private CellStyle getStyle(Workbook workbook) {
		// 设置字体;
		Font font = workbook.createFont();
		// 设置字体大小;
		font.setFontHeightInPoints((short) 12);
		// 设置字体名字;
		font.setFontName("Courier New");
		// font.setItalic(true);
		// font.setStrikeout(true);
		// 设置样式;
		CellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(CellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(CellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(CellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(CellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return style;
	}

	public static void main(String[] args) {
//		try {
//			InputStream ins = new FileInputStream("E:\\1.xlsx");
//			// System.out.println(read(ins));
//
//			//write("E:\\2.xlsx", new String[][] { { "1", "2" }, { "3", "4" } });
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		System.out.println(BaseExcel.class.getClassLoader().getResource(".").getFile());
	}
	
	
	/*
	////////////////////////////
	
	
	

		*//**
		 * @功能：手工构建一个简单格式的Excel
		 *//*
		private static List<Student> getStudent() throws Exception
		{
			List list = new ArrayList();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");

			Student user1 = new Student(1, "张三", 16, df.parse("1997-03-12"));
			Student user2 = new Student(2, "李四", 17, df.parse("1996-08-12"));
			Student user3 = new Student(3, "王五", 26, df.parse("1985-11-12"));
			list.add(user1);
			list.add(user2);
			list.add(user3);

			return list;
		}

		public static void main2(String[] args) throws Exception
		{
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("学生表一");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("学号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("姓名");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("年龄");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
			cell.setCellValue("生日");
			cell.setCellStyle(style);

			// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
			List list = this.getStudent();

			for (int i = 0; i < list.size(); i++)
			{
				row = sheet.createRow((int) i + 1);
				Student stu = (Student) list.get(i);
				// 第四步，创建单元格，并设置值
				row.createCell((short) 0).setCellValue((double) stu.getId());
				row.createCell((short) 1).setCellValue(stu.getName());
				row.createCell((short) 2).setCellValue((double) stu.getAge());
				cell = row.createCell((short) 3);
				cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu
						.getBirth()));
			}
			// 第六步，将文件存到指定位置
			try
			{
				FileOutputStream fout = new FileOutputStream("E:/students.xls");
				wb.write(fout);
				fout.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		*/
		
		private void exportExcel(List<T> list) {
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("学生表一");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("学号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("姓名");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("年龄");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
			cell.setCellValue("生日");
			cell.setCellStyle(style);

			// 第五步，写入实体数据 实际应用中这些数据从数据库得到，

	/*		for (int i = 0; i < list.size(); i++)
			{
				row = sheet.createRow((int) i + 1);
				T t = (T) list.get(i);
				// 第四步，创建单元格，并设置值
				row.createCell((short) 0).setCellValue((double) stu.getId());
				row.createCell((short) 1).setCellValue(stu.getName());
				row.createCell((short) 2).setCellValue((double) stu.getAge());
				cell = row.createCell((short) 3);
				cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu
						.getBirth()));
			}*/
			// 第六步，将文件存到指定位置
			try
			{
				FileOutputStream fout = new FileOutputStream("E:/students.xls");
				wb.write(fout);
				fout.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			
		}
		
	
	
	
}
