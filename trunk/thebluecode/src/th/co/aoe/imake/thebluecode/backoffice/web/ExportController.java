package th.co.aoe.imake.thebluecode.backoffice.web;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import th.co.aoe.imake.pst.constant.ServiceConstant;
import th.co.aoe.imake.thebluecode.backoffice.service.PSTService;

@Controller
@RequestMapping(value={"/export"})
public class ExportController {
	 @Autowired
	 private PSTService pstService;
	 private static final Logger logger = LoggerFactory.getLogger(ServiceConstant.LOG_APPENDER);
	 private static SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
}
