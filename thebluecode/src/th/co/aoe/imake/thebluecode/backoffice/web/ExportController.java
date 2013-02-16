package th.co.aoe.imake.thebluecode.backoffice.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.Row;
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
import th.co.aoe.imake.thebluecode.backoffice.dto.ReportTemplate;
import th.co.aoe.imake.thebluecode.backoffice.form.ReportForm;
import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;

@Controller
@RequestMapping(value={"/export"})
@SessionAttributes(value={"reportForm"})
public class ExportController {
	 @Autowired
	 private TheBlueCodeService theBlueCodeService;
	 private static final Logger logger = LoggerFactory.getLogger(ServiceConstant.LOG_APPENDER);
	 //private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	 private static SimpleDateFormat format_billCycle = new SimpleDateFormat("dd_MM_yyyy");
	 private static DateFormat dFormat = new SimpleDateFormat("hh,mm,ss", new Locale("en","EN"));
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}) 
	 public void export(HttpServletRequest request, HttpServletResponse response)
	    {
	    	
	    	String id=request.getParameter("id");
	    	logger.info("id =="+id);
	    	/*String msId=request.getParameter("mcaSeries");
	    	//System.out.println("request id ="+mtrIds);
	    	MissTestResult missTestResult =new MissTestResult();
	    	missTestResult.setMtrIds(mtrIds);
	    	missTestResult.setMsId(Long.parseLong(msId));
	    	 
	    	   VResultMessage vresultMessage = missExamService.searchMissTestResult(missTestResult);*/
	    	 
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
	// @RequestMapping(value = { "/all" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
//	public  void exportAll(HttpServletRequest request, HttpServletResponse response,@ModelAttribute(value="reportForm") ReportForm reportForm, BindingResult result, Model model)
	
	    {
	    	
	    	//String id=request.getParameter("id");d
	    	//logger.info("id =="+id);
	    	//System.out.println(billCycle +","+tcId);
	    	Date billCycleDate= null;
	    	try {
	    		billCycleDate=format_billCycle.parse(billCycle);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	//System.out.println(billCycleDate);
	    //	System.out.println(reportForm.getTgName());
	    	/*String msId=request.getParameter("mcaSeries");
	    	//System.out.println("request id ="+mtrIds);
	    	MissTestResult missTestResult =new MissTestResult();
	    	missTestResult.setMtrIds(mtrIds);
	    	missTestResult.setMsId(Long.parseLong(msId));
	    	 
	    	   VResultMessage vresultMessage = missExamService.searchMissTestResult(missTestResult);*/
	    	 
	        HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet("Result");
	     
	        //int indexRow = 0;  
		   // HSSFCellStyle cellStyle = wb.createCellStyle();
		    HSSFCellStyle cellStyle2 = wb.createCellStyle();
		   /* cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		  
		    cellStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());     
		    cellStyle.setWrapText(true);*/
		    
		    cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		    cellStyle2.setWrapText(true); 
		   
		// xxxxxxxxxxx

			 
			//String header[] = {"กลุ่ม ต้นทาง","บริษัท ต้นทาง","เครือข่าย ต้นทาง","หมายเลข ต้นทาง","กลุ่ม ปลายทาง","บริษัท ปลายทาง","เครือข่าย ปลายทาง","หมายเลข ปลายทาง","วันเดือนปี","เวลา","เรียกไป/เรียกจาก","ครั้ง/นาที","จำนวน วินาที","จำนวน นาที","จำนวนเงิน"};
		    String header[] = {"รอบค่าใช้บริการประจำเดือน (วว:ดด:ปปปป)","กลุ่ม ต้นทาง","บริษัท ต้นทาง","เครือข่าย ต้นทาง","หมายเลข ต้นทาง","กลุ่ม ปลายทาง","บริษัท ปลายทาง","เครือข่าย ปลายทาง","หมายเลข ปลายทาง","วันเดือนปี","เวลา","เรียกไป/เรียกจาก","ครั้ง/นาที","จำนวน วินาที","จำนวน นาที","จำนวนเงิน"};
			
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
			
			/*CellStyle cellStyleTimeUsed = wb.createCellStyle();
			
			cellStyleTimeUsed.setDataFormat(dateFormat.getFormat("00:mm:ss")); //h:mm:ss
*/			
			//String tgName=null;
			//Integer tcId=null;
			//String msIsdn=null;
			List<ReportTemplate> ReportTemplates = theBlueCodeService.listReportTemplates(tcId,billCycleDate, provider);
			 //System.out.println("ReportTemplates size="+ReportTemplates.size());
			for (ReportTemplate template : ReportTemplates) {
				row = sheet.createRow(indexRow);
				indexRow++;
				
				//ReportTemplate template = (ReportTemplate)list.get(i);
				Cell cell_ = row.createCell(0);
				Calendar calendar_bill = new GregorianCalendar(new Locale("th","TH"));
				calendar_bill.setTime(template.getBillCycle());
				cell_.setCellValue(calendar_bill); //or template.getBillCycle(); 
				cell_.setCellStyle(cellStyleDate);
				//cell_.setCellValue(billCycleDate); //or template.getBillCycle();
				
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
		/*	//File file = new File("D:/temp.xls");
			File file = new File("/tmp/temp.xls");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			wb.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();*/
		
		// xxxxxxxxxxxxxx
					//Header 5
			/*HSSFRow row = sheet.createRow(indexRow);
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
				    cell.setCellStyle(cellStyle);*/
				  
				    
				  /*  indexRow++;
				   
				    for(int i=0;i<index;i++){
				    	 sheet.setColumnWidth((short)i,(short)((50*8)/((double)1/20) ));
				    }*/
				   
				  /*  sheet.setColumnWidth((short)1,(short)((50*8)/((double)1/20) ));
				    sheet.setColumnWidth((short)2,(short)((50*8)/((double)1/20) ));
				    sheet.setColumnWidth((short)3,(short)((50*8)/((double)1/20) )); */
				 //  List<MissTestResult> results= (List<MissTestResult>) vresultMessage.getResultListObj().get(0);
				/*   int rowIndex=1;
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
					    
					 
					   
					    
					 
					  
					    cell = row.createCell((short)index++);	    
					    cell.setCellValue("TRU");
					    cell.setCellStyle(cellStyle2);  */
					     
			//	 } 
	     
	    }
}
