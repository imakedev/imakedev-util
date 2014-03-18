package th.co.aoe.imake.thebluecode.backoffice.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import th.co.aoe.imake.pst.constant.ServiceConstant;
import th.co.aoe.imake.thebluecode.backoffice.dto.MobileTemplate;
import th.co.aoe.imake.thebluecode.backoffice.dto.ReportTemplate;
import th.co.aoe.imake.thebluecode.backoffice.dto.TemCodeGroup;
import th.co.aoe.imake.thebluecode.backoffice.form.ReportForm;
import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;

@Controller
@RequestMapping(value={"/export"})
@SessionAttributes(value={"reportForm"})
public class ExportController {
	 @Autowired
	 private TheBlueCodeService theBlueCodeService;
	 private static final Logger logger = LoggerFactory.getLogger(ServiceConstant.LOG_APPENDER);
	 private static SimpleDateFormat format_billCycle = new SimpleDateFormat("dd_MM_yyyy");
	 private static DateFormat dFormat = new SimpleDateFormat("hh,mm,ss", new Locale("en","EN"));
	// MM/DD/YYYY HH:MM:SS
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}) 
	 public void export(HttpServletRequest request, HttpServletResponse response)
	    {
	    	
	    	String id=request.getParameter("id");
	    	logger.info("id =="+id);
	    
	        HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet("Result");
	     
	        int indexRow = 0;  
		    HSSFCellStyle cellStyle = wb.createCellStyle();
		    HSSFCellStyle cellStyle2 = wb.createCellStyle();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		  
		    cellStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());     
		    cellStyle.setWrapText(true);
		    
		    cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setWrapText(true); 
		   
		 
					//Header 5
			HSSFRow row = sheet.createRow(indexRow);
			HSSFCell cell = row.createCell((short)0);
				   int index=0;
				    cell = row.createCell((short)index++);	    
				    cell.setCellValue("ต้นทาง");
				     cell.setCellStyle(cellStyle);	   
				    cell = row.createCell((short)index++);	    
				    cell.setCellValue("เครือข่ายต้นทาง");
				    cell.setCellStyle(cellStyle);  
				   
				    cell = row.createCell((short)index++);	    
				    cell.setCellValue("ปลายทาง");
				    cell.setCellStyle(cellStyle);
				    
				    cell = row.createCell((short)index++);	    
				    cell.setCellValue("เครือข่ายปลายทาง");
				    cell.setCellStyle(cellStyle);
				   /* 
				    cell = row.createCell((short)index++);	    
				    cell.setCellValue("Department");
				    cell.setCellStyle(cellStyle); */
				     
				    /*List<String> axisHeaders =(List<String>) vresultMessage.getResultListObj().get(1);
				    for (String string : axisHeaders) {
				    	  cell = row.createCell((short)index++);	    
						    cell.setCellValue(string);
						    cell.setCellStyle(cellStyle);
					}*/
				    
				  /*  cell = row.createCell((short)index++);	    
				    cell.setCellValue("Test Date");
				    cell.setCellStyle(cellStyle); 
				    
				    cell = row.createCell((short)index++);	    
				    cell.setCellValue("Report");
				    cell.setCellStyle(cellStyle); 
				    
				    cell = row.createCell((short)index++);	    
				    cell.setCellValue("Status");
				    cell.setCellStyle(cellStyle); 
				    
				    cell = row.createCell((short)index++);	    
				    cell.setCellValue("Response");
				    cell.setCellStyle(cellStyle);  */
				    
				    indexRow++;
				   
				    for(int i=0;i<index;i++){
				    	 sheet.setColumnWidth((short)i,(short)((50*8)/((double)1/20) ));
				    }
				   
				  /*  sheet.setColumnWidth((short)1,(short)((50*8)/((double)1/20) ));
				    sheet.setColumnWidth((short)2,(short)((50*8)/((double)1/20) ));
				    sheet.setColumnWidth((short)3,(short)((50*8)/((double)1/20) )); */
				 //  List<MissTestResult> results= (List<MissTestResult>) vresultMessage.getResultListObj().get(0);
				   int rowIndex=1;
				   String status="";
				   String responseToUser="";
				 //  for (MissTestResult result : results) {
					   row = sheet.createRow(indexRow);
					    indexRow++;
					    index=0;
					    cell = row.createCell((short)index++);	    
					    cell.setCellValue("08999999998");
					    cell.setCellStyle(cellStyle2);
					     
					    cell = row.createCell((short)index++);	    
					    cell.setCellValue("AIS");
					    cell.setCellStyle(cellStyle2); 
					    
					    cell = row.createCell((short)index++);	    
					    cell.setCellValue("08888888888");
					    cell.setCellStyle(cellStyle2);
					    
					/*    cell = row.createCell((short)index++);	    
					    cell.setCellValue(result.getMissCandidate().getMcaLastName());
					    cell.setCellStyle(cellStyle2);
					    
					    cell = row.createCell((short)index++);	    
					    cell.setCellValue(result.getMissCandidate().getMcaPosition());
					    cell.setCellStyle(cellStyle2);
					    
					    cell = row.createCell((short)index++);	    
					    cell.setCellValue(result.getMissCandidate().getMcaDepartment());
					    cell.setCellStyle(cellStyle2); */
					     
					 /*  List<String> axisValues = result.getAxisValues();
					   for (String string : axisValues) {
						   cell = row.createCell((short)index++);	    
						    cell.setCellValue(string);
						    cell.setCellStyle(cellStyle2);
					   }*/
					   
					    
					 /*   cell = row.createCell((short)index++);	 
					    if(result.getMtrStartTime()!=null)
					    	cell.setCellValue(format3.format(result.getMtrStartTime()));
					    else
					    	cell.setCellValue("");
					    cell.setCellStyle(cellStyle2);*/ 
					    
					  /*  cell = row.createCell((short)index++);	    
					    cell.setCellValue("");
					    cell.setCellStyle(cellStyle2); */
					    
					   // cell = row.createCell((short)index++);	 
					    //status="";
					    /*if(result.getMtrStatus()!=null){
					    	if(result.getMtrStatus().equals("0")){
					    		status="Not finished";
					    	}else if(result.getMtrStatus().equals("1")){
					    		status="Finished";
					    	}else if(result.getMtrStatus().equals("2")){
					    		status="Responded";
					    	}
					    }  */
					  /*  cell.setCellValue("TRU");
					    cell.setCellStyle(cellStyle2);*/ 
					    
					   /* responseToUser=""; 
					    if(result.getMtrRespondedStatus()!=null){
					    	if(result.getMtrRespondedStatus().equals("1") && result.getMtrStatus().equals("0")){
					    		responseToUser="Completed";
					    	}else if(result.getMtrRespondedStatus().equals("0") && result.getMtrStatus().equals("0")){
					    		responseToUser="Pending";
					    	}else if(result.getMtrRespondedStatus().equals("2") ){
					    		responseToUser="Ignored";
					    	}
					    	 if(result.getMtrStatus().equals("0") ){
					    		 responseToUser="";
					    	}
					    } */
					    cell = row.createCell((short)index++);	    
					    cell.setCellValue("TRU");
					    cell.setCellStyle(cellStyle2);  
					     
			//	 } 
	        response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
	        response.setHeader("Content-disposition", "attachment; filename=Report.xls");
	        ServletOutputStream servletOutputStream = null;
	        try
	        {
	            servletOutputStream = response.getOutputStream();
	        }
	        catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	        try
	        {
	            wb.write(servletOutputStream);
	        }
	        catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	        try
	        {
	            servletOutputStream.flush();
	        }
	        catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	        try
	        {
	            servletOutputStream.close();
	        }
	        catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	    }
	 @RequestMapping(value = { "/all2" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
		public  ModelAndView exportAll2(HttpServletRequest request, HttpServletResponse response,@ModelAttribute(value="reportForm") ReportForm reportForm, BindingResult result, Model model)
		
		    {
		//dummy data
			Map<String,String> revenueData = new HashMap<String,String>();
			revenueData.put("Jan-2010", "$100,000,000");
			revenueData.put("Feb-2010", "$110,000,000");
			revenueData.put("Mar-2010", "$130,000,000");
			revenueData.put("Apr-2010", "$140,000,000");
			revenueData.put("May-2010", "$200,000,000");
		 		return null;//new ModelAndView(new ExcelRevenueReportView(),"revenueData",revenueData);
		    }
	
	 @RequestMapping(value={"/all/{billCycle}/{tcId}/{provider}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}) 
	  public void exportAll(HttpServletRequest request, HttpServletResponse response,@PathVariable String billCycle,@PathVariable Integer tcId,
			  @PathVariable Integer provider)
	    {
	    	Date billCycleDate= null;
	    	try {
	    		billCycleDate=format_billCycle.parse(billCycle);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	 
	        HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet("Result");
		    HSSFCellStyle cellStyle2 = wb.createCellStyle();
		    
		    cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setWrapText(true); 
		   
			 
			//String header[] = {"กลุ่ม ต้นทาง","บริษัท ต้นทาง","เครือข่าย ต้นทาง","หมายเลข ต้นทาง","กลุ่ม ปลายทาง","บริษัท ปลายทาง","เครือข่าย ปลายทาง","หมายเลข ปลายทาง","วันเดือนปี","เวลา","เรียกไป/เรียกจาก","ครั้ง/นาที","จำนวน วินาที","จำนวน นาที","จำนวนเงิน"};
		    String header[] = {"รอบค่าใช้บริการประจำเดือน (วว:ดด:ปปปป)","กลุ่ม ต้นทาง","บริษัท ต้นทาง","เครือข่าย ต้นทาง","หมายเลข ต้นทาง","กลุ่ม ปลายทาง","บริษัท ปลายทาง","เครือข่าย ปลายทาง","หมายเลข ปลายทาง","วันเดือนปี","เวลา","เรียกไป/เรียกจาก","ครั้ง/นาที","จำนวน วินาที","จำนวน นาที","จำนวนเงิน","เวลา 24h"};
			
			int indexRow = 0;
			Row row = sheet.createRow(indexRow);
			row = sheet.createRow(indexRow);
			
			for(int i=0;i<header.length;i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(header[i]);
				cell.setCellStyle(cellStyle2);  
				sheet.autoSizeColumn(i);
			}
			
			indexRow++;
			//System.out.println(list.size());
			
			CellStyle cellStyleTime = wb.createCellStyle();
			HSSFDataFormat dateFormat = wb.createDataFormat();
			
			CellStyle cellStyleDate = wb.createCellStyle();
			cellStyleDate.setDataFormat(dateFormat.getFormat("dd/MM/yyyy"));
			//cellStyleTime.setDataFormat(dateFormat.getFormat("HH:mm:ss a"));
//			cellStyleTime.setDataFormat(dateFormat.getFormat("h:mm AM/PM"));
			cellStyleTime.setDataFormat(dateFormat.getFormat("h:mm:ss AM/PM"));
			
			CellStyle cellStyleDate2 = wb.createCellStyle();
			cellStyleDate2.setDataFormat(dateFormat.getFormat("dd/MM/yyyy h:mm:ss"));
			
			List<ReportTemplate> reportTemplates = theBlueCodeService.listReportTemplates(tcId,billCycleDate, provider);
			 //System.out.println("ReportTemplates size="+ReportTemplates.size());
			for (ReportTemplate template : reportTemplates) {
				row = sheet.createRow(indexRow);
				indexRow++;
				
				Cell cell_ = row.createCell(0);
				Calendar calendar_bill = new GregorianCalendar(new Locale("th","TH"));
				calendar_bill.setTime(template.getBillCycle());
				cell_.setCellValue(calendar_bill); //or template.getBillCycle(); 
				cell_.setCellStyle(cellStyleDate);
				
				Cell cell0 = row.createCell(1);
				cell0.setCellValue(template.getGroupFrom());
				
				Cell cell1 = row.createCell(2);
				cell1.setCellValue(template.getCompanyFrom());
				
				Cell cell2 = row.createCell(3);
				cell2.setCellValue(template.getProviderFrom());
				
				Cell cell3 = row.createCell(4);
				cell3.setCellValue(template.getMsIsdnFrom());
				
				Cell cell4 = row.createCell(5);
				cell4.setCellValue(template.getGroupTo());
				
				Cell cell5 = row.createCell(6);
				cell5.setCellValue(template.getCompanyTo());
				
				Cell cell6 = row.createCell(7);
				cell6.setCellValue(template.getProviderTo());
				
				Cell cell7 = row.createCell(8);
				cell7.setCellValue(template.getMsIsdnTo());
				
				//DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("th","TH"));
				Calendar calendar = new GregorianCalendar(new Locale("th","TH"));
				calendar.setTime(template.getUsedTime());
				
				
				
				Cell cell8 = row.createCell(9);
			//	cell7.setCellValue(dFormat.format(calendar.getTime()));
				//HSSFDateUtil.setCalendar(calendar, wholeDays, millisecondsInDay, use1904windowing)
				cell8.setCellValue(calendar);
				cell8.setCellStyle(cellStyleDate);
			
				
				//dateFormat.g
				Cell cell9 = row.createCell(10);
				cell9.setCellFormula("TIME("+dFormat.format(calendar.getTime())+")"); 
				//cell9.setCellValue(calendar);
				cell9.setCellStyle(cellStyleTime);
				//String header[] = {"กลุ่ม ต้นทาง","บริษัท ต้นทาง","เครือข่าย ต้นทาง","หมายเลข ต้นทาง","กลุ่ม ปลายทาง","บริษัท ปลายทาง","เครือข่าย ปลายทาง","หมายเลข ปลายทาง","วันเดือนปี","เวลา","เรียกไป/เรียกจาก","ครั้ง/นาที","จำนวน วินาที","จำนวนเงิน"};
				Cell cell10 = row.createCell(11);
				cell10.setCellValue(template.getCallTo());
				
				/*calendar.set(Calendar.HOUR, 12);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, template.getUsedCount().intValue());
				calendar.set(Calendar.MILLISECOND, 0);*/
				/*calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 3);
				calendar.set(Calendar.SECOND,0);
				calendar.set(Calendar.MILLISECOND, 0);*/
				Cell cell11 = row.createCell(12); 
				Double usedCount =template.getUsedCount();
				 
				//cellStyleTime.setDataFormat(dateFormat.getFormat("h:mm:ss"));
				cell11.setCellValue(template.getUsedCountStr());
				//cell10.setTime(HSSFDateUtil.getJavaDate(calendar.getTime().getTime()));
		           /*cellText =
		             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		           cellText = cal.get(Calendar.MONTH)+1 + "/" +
		                      cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                      cellText;*/
				//cell10.setCellStyle(cellStyleTimeUsed);
				
				
				Cell cell12 = row.createCell(13); 
				//cellStyleTime.setDataFormat(dateFormat.getFormat("h:mm:ss"));
				if(usedCount!=null)
					cell12.setCellValue(usedCount);
				else{
					cell12.setCellValue(0);
					usedCount=0d;
				}
				
				Cell cell13 = row.createCell(14);   
				//cellStyleTime.setDataFormat(dateFormat.getFormat("h:mm:ss"));
				cell13.setCellValue(usedCount/60);
				
				Cell cell14 = row.createCell(15);
				cell14.setCellValue(template.getPrice());
				
				//add
				Cell cell15 = row.createCell(16);
				//cell15.setCellFormula("TIME("+dFormat2.format(calendar.getTime())+")"); d
				cell15.setCellValue(calendar);
				cell15.setCellStyle(cellStyleDate2);
				
			}
			
			   response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
		        response.setHeader("Content-disposition", "attachment; filename=Report.xls");
		        ServletOutputStream servletOutputStream = null;
		        try
		        {
		            servletOutputStream = response.getOutputStream();
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		        try
		        {
		            wb.write(servletOutputStream);
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		        try
		        {
		            servletOutputStream.flush();
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		        try
		        {
		            servletOutputStream.close();
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
	    }
	 @RequestMapping(value={"/mobile_all/{billCycle}/{tcId}/{provider}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}) 
	  public void exportMobileAll(HttpServletRequest request, HttpServletResponse response,@PathVariable String billCycle,@PathVariable Integer tcId,
			  @PathVariable Integer provider)
	    {
	    	Date billCycleDate= null;
	    	try {
	    		billCycleDate=format_billCycle.parse(billCycle);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	 
	        HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet("Result");
		    HSSFCellStyle cellStyle2 = wb.createCellStyle();
		    HSSFCellStyle cellStyle4 = wb.createCellStyle();
		    HSSFCellStyle cellStyle5 = wb.createCellStyle();
		    cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
					.getIndex());
		    cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    cellStyle2.setWrapText(true); 
		    
		    cellStyle4.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			cellStyle4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle4.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle4.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle4.setWrapText(true);
			//cellStyle4.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			//cellStyle4.setFillPattern(CellStyle.SOLID_FOREGROUND);
			DataFormat dataFormat = wb.createDataFormat();   
			DataFormat dataFormat2 = wb.createDataFormat(); 
			//cellStyle4.setDataFormat(dataFormat.getFormat("#,##0.00"));
			//cellStyle4.setDataFormat(dataFormat.getFormat("###,###.###"));
			//cellStyle4.setDataFormat(dataFormat.getFormat("###,###.00"));
			cellStyle4.setDataFormat(dataFormat.getFormat("###,##0.00"));
			
			
			 cellStyle5.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			 cellStyle5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cellStyle5.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle5.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle5.setBorderRight(HSSFCellStyle.BORDER_THIN);
				cellStyle5.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle5.setWrapText(true);
				//cellStyle5.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				//cellStyle5.setFillPattern(CellStyle.SOLID_FOREGROUND);
				//cellStyle5.setDataFormat(dataFormat.getFormat("#,##0.00"));
				cellStyle5.setDataFormat(dataFormat2.getFormat("###,###")); 
		   
				MobileTemplate mobileTemplate  = theBlueCodeService.listMobileReportTemplates(tcId,billCycleDate, provider);
				List<ReportTemplate> reportTemplates=mobileTemplate.getReportTemplates();
		    String header[] = {"รอบค่าใช้บริการประจำเดือน (วว:ดด:ปปปป)","เลขหมาย"};
		    String header_content[] =mobileTemplate.getHeaders().toArray(new String[mobileTemplate.getHeaders().size()]);
		    List<TemCodeGroup> temCodeGroups = mobileTemplate.getTemCodeGroups();
		    	//	String[] array = list.toArray(new String[list.size()]);
			int indexRow = 0;
			Row row = sheet.createRow(indexRow);
			row = sheet.createRow(indexRow);
			
			for(int i=0;i<header.length;i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(header[i]);
				cell.setCellStyle(cellStyle2);  
				sheet.autoSizeColumn(i);
			}
			
			/*Cell cell9 = row.createCell(10);
			cell9.setCellFormula("TIME("+dFormat.format(calendar.getTime())+")"); 
			//cell9.setCellValue(calendar);
			cell9.setCellStyle(cellStyleTime);*/
			 
			  CellReference cellRef=null;
			List<Integer> column_sum=new ArrayList<Integer>();
			String[] cell_start_array= new String[header_content.length];
			for(int i=0;i<header_content.length;i++) {
				Cell cell = row.createCell(i+2);
				cell.setCellValue(header_content[i]);
				cell.setCellStyle(cellStyle2);  
				sheet.autoSizeColumn(i+2);
				cellRef = new CellReference(row.getRowNum()+1, cell.getColumnIndex());
				cell_start_array[i]=cellRef.formatAsString();
				TemCodeGroup temCodeGroup=	temCodeGroups.get(i);
				if(temCodeGroup.getTcgIsSum()!=null && temCodeGroup.getTcgIsSum().equals("1")){
					column_sum.add(cell.getColumnIndex()); 
				}
				
			}
			Cell cell = row.createCell(header_content.length+2);
			cell.setCellValue("Total");
			cell.setCellStyle(cellStyle2);  
			//sheet.autoSizeColumn(header_content.length+2);
			int column_sum_size = column_sum.size();
			
			indexRow++;
			//System.out.println(list.size());
			
			CellStyle cellStyleTime = wb.createCellStyle();
			HSSFDataFormat dateFormat = wb.createDataFormat();
			
			CellStyle cellStyleDate = wb.createCellStyle();
			cellStyleDate.setDataFormat(dateFormat.getFormat("dd/MM/yyyy"));
			cellStyleDate.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyleDate.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyleDate.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyleDate.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyleDate.setWrapText(true);
			//cellStyleTime.setDataFormat(dateFormat.getFormat("HH:mm:ss a"));
//			cellStyleTime.setDataFormat(dateFormat.getFormat("h:mm AM/PM"));
			cellStyleTime.setDataFormat(dateFormat.getFormat("h:mm:ss AM/PM"));
			
			CellStyle cellStyleDate2 = wb.createCellStyle();
			cellStyleDate2.setDataFormat(dateFormat.getFormat("dd/MM/yyyy h:mm:ss"));
			
		//	List<ReportTemplate> reportTemplates = theBlueCodeService.listMobileReportTemplates(tcId,billCycleDate, provider);
			
			//  System.out.println("ReportTemplates size="+reportTemplates.size());
			 DecimalFormat df = new DecimalFormat("###,###.###");
			 DecimalFormat df2 = new DecimalFormat("###,###");
			 StringBuffer sum_sb=new StringBuffer();
			
			for (ReportTemplate template : reportTemplates) {
				row = sheet.createRow(indexRow);
				indexRow++;
				
				Cell cell_ = row.createCell(0);
				Calendar calendar_bill = new GregorianCalendar(new Locale("en","EN"));
				calendar_bill.setTime(template.getBillCycle());
				cell_.setCellValue(calendar_bill); //or template.getBillCycle(); 
				cell_.setCellStyle(cellStyleDate);
				
				cell_ = row.createCell(1);
				cell_.setCellValue(template.getMsIsdnFrom());
				cell_.setCellStyle(cellStyle4);
				String[] column_content=template.getColumns();
				int column_content_size=column_content.length;
				for (int i = 0; i < column_content_size; i++) {
					cell_ = row.createCell(i+2); 
					 
					if(column_content[i]!=null && column_content[i].length()>0)
					try {
						cell_.setCellValue(df.parse(column_content[i]).doubleValue());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cell_.setCellStyle(cellStyle4);
				} 
				cell_ = row.createCell(column_content.length+2); 
				int row_index=row.getRowNum();
				sum_sb.setLength(0);
				sum_sb.append("sum(");
				if(column_sum_size>0){
					for (int i = 0; i < column_sum_size; i++) {
						cellRef = new CellReference(row_index, column_sum.get(i));
						// System.out.print(cellRef.formatAsString());	
						sum_sb.append(cellRef.formatAsString()+(((i+1)==column_sum_size)?")":","));
					}  
						cell_.setCellFormula(sum_sb.toString()); 
						cell_.setCellStyle(cellStyle4);
				}
				 
			}
			row = sheet.createRow(indexRow);
			indexRow++;
			 cell = row.createCell(1);
			cell.setCellValue("Total");
			cell.setCellStyle(cellStyle2);  
			for(int i=0;i<header_content.length;i++) {
				  cell = row.createCell(i+2);
				  cellRef = new CellReference(row.getRowNum()-1, cell.getColumnIndex());
					// System.out.print(cellRef.formatAsString());	
				  //=SUM(C2:C96) 
				  cell.setCellFormula("sum("+cell_start_array[i]+":"+cellRef.formatAsString()+")"); 
				  cell.setCellStyle(cellStyle4);   
			}
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			evaluator.evaluateAll();
			   response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
		        response.setHeader("Content-disposition", "attachment; filename=Mobile_Report.xls");
		        ServletOutputStream servletOutputStream = null;
		        try
		        {
		            servletOutputStream = response.getOutputStream();
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		        try
		        {
		            wb.write(servletOutputStream);
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		        try
		        {
		            servletOutputStream.flush();
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		        try
		        {
		            servletOutputStream.close();
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
	    }
}
