package com.hongedu.pems.pems.web;

import java.text.SimpleDateFormat;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongedu.pems.pems.entity.Employee;
import com.hongedu.pems.pems.entity.Norm;
import com.hongedu.pems.pems.entity.NormCategory;
import com.hongedu.pems.pems.entity.NormDetail;
import com.hongedu.pems.pems.entity.NormTask;
import com.hongedu.pems.pems.entity.NormTaskEmployee;
import com.hongedu.pems.pems.entity.NormTaskProject;
import com.hongedu.pems.pems.entity.TaskProjectEmployee;
import com.hongedu.pems.pems.entity.TaskStationTemplate;
import com.hongedu.pems.pems.service.EmployeeService;
import com.hongedu.pems.pems.service.NormCategoryService;
import com.hongedu.pems.pems.service.NormService;
import com.hongedu.pems.pems.service.NormTaskEmployeeDetailService;
import com.hongedu.pems.pems.service.NormTaskEmployeeService;
import com.hongedu.pems.pems.service.NormTaskProjectService;
import com.hongedu.pems.pems.service.NormTaskService;
import com.hongedu.pems.pems.service.TaskProjectEmployeeService;
import com.hongedu.pems.pems.service.TaskStationTemplateService;
import com.hongedu.pems.util.export.ExportExcel;


@Controller
@RequestMapping("/admin/exportExcel")
public class ExportExcelConller {
	    private static HSSFWorkbook wb = new HSSFWorkbook();  
	 
	    private static HSSFSheet sheet = wb.createSheet();  
	    
	    
	    @Autowired
		private NormTaskEmployeeService normTaskEmployeeService;
		@Autowired
		private NormService normService;
		@Autowired
		private NormTaskProjectService normTaskProjectService;
		@Autowired
		private TaskProjectEmployeeService taskProjectEmployeeService;
		@Autowired
		private NormCategoryService normCategoryService;
		@Autowired
		private NormTaskService normTaskService;
		@Autowired
		private NormTaskEmployeeDetailService normTaskEmployeeDetailService;
		@Autowired
		private EmployeeService employeeService;
		@Autowired
		private TaskStationTemplateService taskStationTemplateService;
	 
	    @SuppressWarnings({ "unchecked", "deprecation" })
	    @ResponseBody
		@RequestMapping(value="/exportExcel",method=RequestMethod.POST)
	    public  void main(java.lang.Integer normTaskId,java.lang.Integer employeeId) { 
	    	
	        
	    	Employee employee2 = employeeService.findEmpById(employeeId);
			NormTask normTask = normTaskService.findById(normTaskId);
			//根据考核任务及岗位查询考核模板
			TaskStationTemplate taskStationTemplate = taskStationTemplateService.findTemp(normTaskId,employee2.getStationId());
			Integer normTempId = taskStationTemplate.getNormTemplateId();
			//查询权重
			List<NormCategory> normCategoryList = normCategoryService.findByNormTempId(normTempId);
			//人力岗位指标列表
			Integer normType=null;
			normType=1;
			List<NormTaskEmployee> hrScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
			
			//查询岗位
			normType=2;
			List<Norm> gwNormList=new ArrayList<>();
			List<NormTaskEmployee> gwScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
			if(!gwScoreList.isEmpty()&&gwScoreList.get(0).getNormCategoryId()!=null){
				gwNormList=normService.findNormByNormCatId(gwScoreList.get(0).getNormCategoryId());
			}
			//查询项目
			normType=3;
			// 员工考核项目
			Integer type=1;
			List<NormTaskProject> normTaskProjectList = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
			//查询员工工作内容 
			type=2;
			List<NormTaskProject> normTaskProjectList2 = normTaskProjectService.findNormTaskProjectByEmpIdAndTaskId(employeeId, normTaskId,type);
			// 互评员工名
			for (NormTaskProject normTaskProject : normTaskProjectList2) {
				Integer normTaskProjectId = normTaskProject.getNormTaskProjectId();
				List<TaskProjectEmployee> eachEmployeeList=taskProjectEmployeeService.findEmp(employeeId,normTaskProjectId);
				normTaskProject.setEachEmp(eachEmployeeList);
			}
			
			
			List<Norm> xmNormList=new ArrayList<>();
			List<NormTaskEmployee> xmScoreList=normTaskEmployeeService.findScore(normTaskId,employeeId,normType);
			if(!xmScoreList.isEmpty()){
				xmNormList=normService.findNormByNormCatId(xmScoreList.get(0).getNormCategoryId());
			}
	    	
	    	int param=0;
	    	ExportExcel exportExcel = new ExportExcel(wb, sheet);  
	 
	        // 创建单元格样式  
	        HSSFCellStyle cellStyle = cellStyle();  
	        HSSFCellStyle cellStyle1 = cellStyle1();  
	 
	        // 设置单元格字体  
	        HSSFFont font = wb.createFont();  
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	        font.setFontName("宋体");  
	        font.setFontHeight((short) 200);  
	        cellStyle.setFont(font);  
	 
	        // 创建报表头部  
	        exportExcel.createNormalHead(normTask.getNormTaskName(),param, 4);   //第一行
	        param++;                                                             //全局变量  行加1                                
	        // 设置第二行  
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String[] params = new String[] { employee2.getRealName(), employee2.getDeptName(),employee2.getStation().getStationName(),employee2.getLevelEmployeeName(),simpleDateFormat.format(normTask.getStartTime()) ,simpleDateFormat.format(normTask.getEndTime())};  
	        exportExcel.createNormalTwoRow(params, 4,param);  
	        param++;
	        /*****************人事**********************/
	        param = hrExport(hrScoreList, param, exportExcel, cellStyle, cellStyle1);
	        /*****************岗位**********************/
	        param = gwExport(gwNormList, gwScoreList, param, exportExcel, cellStyle, cellStyle1);
	        /****************项目**********************/
	        xmExport(normTaskProjectList, normTaskProjectList2, xmScoreList, param, exportExcel, cellStyle);
	        exportExcel.outputExcel("D:\\拒绝件统计.xls");  
	 
	    }

		private void xmExport(List<NormTaskProject> normTaskProjectList, List<NormTaskProject> normTaskProjectList2,
				List<NormTaskEmployee> xmScoreList, int param, ExportExcel exportExcel, HSSFCellStyle cellStyle) {
			exportExcel.createCtaegory("项目评分("+xmScoreList.get(0).getNormCategory().getWeight()+")%",param, 4);
	        param++;
	        for(int i = 0 ; i < normTaskProjectList.size(); i ++){
	        	//项目名称
	        	exportExcel.createCtaegory((i+1)+"."+normTaskProjectList.get(i).getProject().getProjectName(),param, 4);
	            param++;
	        	HSSFRow row4 = sheet.createRow(param);
	        	HSSFCell xmcell = row4.createCell(0);  
	        	xmcell.setCellStyle(cellStyle);  
	        	xmcell.setCellValue(new HSSFRichTextString("工作内容"));
	        	sheet.addMergedRegion(new Region(param, (short) 0, param, (short) 2));
	        	param++;
		        
		        HSSFCell xmcell1 = row4.createCell(3);  
		        xmcell1.setCellStyle(cellStyle);  
		        xmcell1.setCellValue(new HSSFRichTextString("评分人"));
		        HSSFCell xmcell2 = row4.createCell(4);  
		        xmcell2.setCellStyle(cellStyle);  
		        xmcell2.setCellValue(new HSSFRichTextString("得分"));
		        HSSFCell categoryCell1=null;
		        for (NormTaskProject normTaskProject : normTaskProjectList2) {
					
				}
		        for (NormTaskProject normTaskProject : normTaskProjectList2) {  //循环工作内容列表
		        	if(normTaskProjectList.get(i).getProjectId().equals(normTaskProject.getProjectId())){
		        		int c=0;
			        	for(NormTaskEmployee normTaskEmployee : xmScoreList){
		        			for (TaskProjectEmployee taskProjectEmployee : normTaskProject.getEachEmp()) {
		        				if(normTaskEmployee.getWorkContentId().equals(normTaskProject.getNormTaskProjectId())&&normTaskEmployee.getScoreType()==2&&taskProjectEmployee.getEachEmployeeId().equals(normTaskEmployee.getScoreEmployeeId())){
		        					c++;	
		        				}
		        			}
		        		}
		        		HSSFRow cellrow = sheet.createRow(param);
		        		categoryCell1 = cellrow.createCell(0);
		        		categoryCell1.setCellValue(new HSSFRichTextString(normTaskProject.getWorkContent()  
		        				.toString()));
		        	//自评
		        		categoryCell1 = cellrow.createCell(3);
		        		categoryCell1.setCellValue(new HSSFRichTextString("自评"  
		        				.toString()));
		        		sheet.addMergedRegion(new Region(param, (short) 0, param+c, (short) 2));
		        		param++;
		        		categoryCell1 = cellrow.createCell(4);
		        		for(NormTaskEmployee normTaskEmployee : xmScoreList){
		        			if(normTaskEmployee.getWorkContentId().equals(normTaskProject.getNormTaskProjectId())&&normTaskEmployee.getScoreType()==1){
		        				categoryCell1.setCellValue(new HSSFRichTextString( normTaskEmployee.getScore() 
				        				.toString()));
		        			}
		        			
		        		}
		        	//互评
		        		for(NormTaskEmployee normTaskEmployee : xmScoreList){
		        			for (TaskProjectEmployee taskProjectEmployee : normTaskProject.getEachEmp()) {
		        				if(normTaskEmployee.getWorkContentId().equals(normTaskProject.getNormTaskProjectId())&&normTaskEmployee.getScoreType()==2&&taskProjectEmployee.getEachEmployeeId().equals(normTaskEmployee.getScoreEmployeeId())){
		        					HSSFRow xecellrow = sheet.createRow(param);
		        					categoryCell1 = xecellrow.createCell(3);
		        					categoryCell1.setCellValue(new HSSFRichTextString( taskProjectEmployee.getEmployee().getRealName() 
		        							.toString()));
		        					categoryCell1 = xecellrow.createCell(4);
		        					categoryCell1.setCellValue(new HSSFRichTextString( normTaskEmployee.getScore()
		        							.toString()));
		        					param++;	
		        				}
		        			}
		        		}
		        	
		        	}
		        }
	           
	        
	        
	        }
		}

		private int gwExport(List<Norm> gwNormList, List<NormTaskEmployee> gwScoreList, int param,
				ExportExcel exportExcel, HSSFCellStyle cellStyle, HSSFCellStyle cellStyle1) {
			exportExcel.createCtaegory("岗位评分("+gwScoreList.get(0).getNormCategory().getWeight()+")%",param, 4);
	        param++;
	        //HSSFRow row3 = sheet.createRow(4+hrScoreList.size()+1);  
	        HSSFRow row3 = sheet.createRow(param);  
	        param++; 
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
	        
	        HSSFCell categoryCell1 = null;
	        HSSFCell weightCell2 = null;
	        HSSFCell detailCell3 = null;
	        HSSFCell scoreCell4 = null;
	        HSSFCell scoreCell5 = null;
	        
	        for (int i = 0; i < gwNormList.size(); i ++) {  
	        	//HSSFRow cellrow = sheet.createRow(6+i+hrScoreList.size());
	        	HSSFRow cellrow = sheet.createRow(param);
	        	param++;
	        	categoryCell1 = cellrow.createCell(0);
	        	weightCell2 = cellrow.createCell(1);
	        	detailCell3 = cellrow.createCell(2);
	        	scoreCell4 = cellrow.createCell(3);
	        	scoreCell5 = cellrow.createCell(4);
	        	categoryCell1.setCellStyle(cellStyle);
	        	weightCell2.setCellStyle(cellStyle);  
	        	detailCell3.setCellStyle(cellStyle1);  
	        	scoreCell4.setCellStyle(cellStyle);  
	        	scoreCell5.setCellStyle(cellStyle);  
	        	categoryCell1.setCellValue(new HSSFRichTextString(gwNormList.get(i).getNormName()  
                        .toString()));
	        	weightCell2.setCellValue(new HSSFRichTextString(gwNormList.get(i).getWeight()  
	        			.toString()));
	        	String detail = "";
        		for (NormDetail cell : gwNormList.get(i).getNormDetailList()) {
        			detail += cell.getDesrc()+ '\n';
				}
        		detailCell3.setCellValue(new HSSFRichTextString(detail  
	        			.toString()));
	        	for (NormTaskEmployee normTaskEmployee : gwScoreList) {
					if(normTaskEmployee.getNormId().equals(gwNormList.get(i).getNormId()) && normTaskEmployee.getScoreType()==1){
						scoreCell4.setCellValue(new HSSFRichTextString(normTaskEmployee.getScore()  
			        			.toString()));
					}
					if(normTaskEmployee.getNormId().equals(gwNormList.get(i).getNormId()) && normTaskEmployee.getScoreType()==3){
						scoreCell5.setCellValue(new HSSFRichTextString(normTaskEmployee.getScore()  
								.toString()));
					}
				}
	        	
	        	
	        	
	        }
			return param;
		}

		private int hrExport(List<NormTaskEmployee> hrScoreList, int param, ExportExcel exportExcel,
				HSSFCellStyle cellStyle, HSSFCellStyle cellStyle1) {
			if(!hrScoreList.isEmpty()){
	        	//创建人事考评
	        	exportExcel.createCtaegory("人事评分("+hrScoreList.get(0).getNormCategory().getWeight()+")%",param, 4);
	        	param++;
	        	HSSFRow row2 = sheet.createRow(param);  
	        	
	        	HSSFCell cell0 = row2.createCell(0);  
	        	cell0.setCellStyle(cellStyle);  
	        	cell0.setCellValue(new HSSFRichTextString("考评项"));
	        	
	        	HSSFCell cell1 = row2.createCell(1);  
	        	cell1.setCellStyle(cellStyle);  
	        	cell1.setCellValue(new HSSFRichTextString("权重"));
	        	
	        	HSSFCell cell2 = row2.createCell(2);  
	        	cell2.setCellStyle(cellStyle);  
	        	cell2.setCellValue(new HSSFRichTextString("考核标准"));
	        	sheet.setColumnWidth((short)2,(short)30000);
	        	
	        	HSSFCell cell3 = row2.createCell(3);  
	        	cell3.setCellStyle(cellStyle);  
	        	cell3.setCellValue(new HSSFRichTextString("人事评分"));
	        	
	        	// 合并人事评分栏  
	        	sheet.addMergedRegion(new Region(3, (short) 3, 3, (short) 4));
	        	
	        	HSSFCell categoryCell = null;
	        	HSSFCell weightCell = null;
	        	HSSFCell detailCell = null;
	        	HSSFCell scoreCell = null;
	        	// 创建不同的LIST的列标题  
	        	for (int i = 0; i < hrScoreList.size(); i ++) { 
	        		HSSFRow cellrow = sheet.createRow(param);
	        		param++;
	        		categoryCell = cellrow.createCell(0);
	        		weightCell = cellrow.createCell(1);
	        		detailCell = cellrow.createCell(2);
	        		scoreCell = cellrow.createCell(3);
	        		categoryCell.setCellStyle(cellStyle);
	        		weightCell.setCellStyle(cellStyle);  
	        		scoreCell.setCellStyle(cellStyle);  
	        		detailCell.setCellStyle(cellStyle1);  
	        		categoryCell.setCellValue(new HSSFRichTextString(hrScoreList.get(i).getNorm().getNormName()  
	        				.toString()));
	        		weightCell.setCellValue(new HSSFRichTextString(hrScoreList.get(i).getNorm().getWeight()  
	        				.toString()));
	        		String detail = "";
	        		for (NormDetail cell : hrScoreList.get(i).getNormDetailList()) {
	        			detail += cell.getDesrc()+ '\n';
					}
	        		detailCell.setCellValue(new HSSFRichTextString(detail  
	        				.toString()));
	        		scoreCell.setCellValue(new HSSFRichTextString(hrScoreList.get(i).getScore()  
	        				.toString()));
	        		sheet.addMergedRegion(new Region(3+(i+1), (short) 3, 3+(i+1), (short) 4));
	        	}
	        	
	        }
			return param;
		}

		private HSSFCellStyle cellStyle() {
			HSSFCellStyle cellStyle = wb.createCellStyle();  
	 
	        // 指定单元格居中对齐  
	        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	 
	        // 指定单元格垂直居中对齐  
	        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	        
	        // 指定当单元格内容显示不下时自动换行  
	        cellStyle.setWrapText(true);
			return cellStyle;
		} 
		
		private HSSFCellStyle cellStyle1() {
			HSSFCellStyle cellStyle = wb.createCellStyle();  
			
			// 指定单元格居中对齐  
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
			
			// 指定单元格垂直居中对齐  
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
			
			// 指定当单元格内容显示不下时自动换行  
			cellStyle.setWrapText(true);
			return cellStyle;
		}  

}
