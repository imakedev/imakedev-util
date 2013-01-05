package th.co.aoe.imake.thebluecode.backoffice.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import th.co.aoe.imake.pst.constant.ServiceConstant;
import th.co.aoe.imake.thebluecode.backoffice.utils.ImakeFile;

/*import th.co.aoe.makedev.missconsult.exam.service.MissExamService;
import th.co.aoe.makedev.missconsult.xstream.MissAccount;
import th.co.aoe.makedev.missconsult.xstream.MissAttach;
import th.co.aoe.makedev.missconsult.xstream.MissCandidate;
import th.co.aoe.makedev.missconsult.xstream.MissContact;
import th.co.aoe.makedev.missconsult.xstream.MissDoc;
import th.co.aoe.makedev.missconsult.xstream.MissFile;
import th.co.aoe.makedev.missconsult.xstream.MissManual;
import th.co.aoe.makedev.missconsult.xstream.MissSeriesAttach;
import th.co.aoe.makedev.missconsult.xstream.MissSery;*/

//import com.google.gson.Gson;
@Controller
public class UploadController {	
	private static SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	 //private static final Logger logger = LoggerFactory.getLogger(ServiceConstant.LOG_APPENDER);
	 private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	//private static Logger logger = Logger.getRootLogger();
	/*@Autowired
	private MissExamService missExamService;*/
	private static ResourceBundle bundle;
	static{
		bundle =  ResourceBundle.getBundle( "config" );				
	}
	
	 
    @RequestMapping(value={"/upload/{module}/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public  String doUpload(HttpServletRequest request, Model model, @PathVariable String module,@PathVariable String id)
    {
    	 String ndPathFileGen=null;
    	 ImakeFile missFile =new ImakeFile();
    	 String hotLink="";
    	 String s="";
    	 String pathFolder="";
    	 String ndFilePath="";
       /* logger.debug("xxxxxxxxxxxxxxxxxxxxxxxx="+request.getParameter("test"));
        Map m =request.getParameterMap();
        for (Iterator iterator = m.keySet().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			String[] key=(String[])m.get(type);
			System.out.println("key="+type+",value="+m.get(type));
			for (int i = 0; i < key.length; i++) {
				System.out.println(" xxxxxxxxxxx ="+key[i]);
			}
		}*/
    	  MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest)request;
        MultipartFile multipart = multipartRequest.getFile("userfile");
		if(multipart!=null){
                String contentType = multipart.getContentType();
                 s = multipart.getOriginalFilename();
                logger.info("fileName ===> "+s);
                logger.info("contentType ===> "+contentType);
                s = FilenameUtils.getName(s);
                logger.info("fileName2 ===> "+s);
                String monthStr= "";
				  String yearStr="";
				  
				  //String pathFolder="";
                FileOutputStream fos = null;
					try {  
						byte []filesize = multipart.getBytes(); 
						logger.info("xxxxxxxxxxxxx="+filesize.length);
						if(filesize.length>0){									
							long current = System.currentTimeMillis();
						org.joda.time.DateTime    dt1  = new org.joda.time.DateTime (new Date().getTime()); 
							
						  monthStr= dt1.getMonthOfYear()+"";
						  yearStr= dt1.getYear()+"";
						  monthStr = monthStr.length()>1?monthStr:"0"+monthStr;
					
						  pathFolder=yearStr+"_"+monthStr+"";
						  ndFilePath = bundle.getString(module+"Path")+pathFolder;
						  String path =ndFilePath;
						  createDirectoryIfNeeded(path);
						  String filename =s ;// multipart.getOriginalFilename();
						  String []filenameSplit  =filename.split("\\.");
						  String extension ="";
						  if(filenameSplit!=null && filenameSplit.length>0){
							  extension =filenameSplit[filenameSplit.length-1];
						  }
						  hotLink=current+""+genToken();
						 ndPathFileGen =hotLink+"."+extension; 
						 pathFolder=pathFolder+"/"+ndPathFileGen;
					//	 FileInputStream fin= new FileInputStream(file)
						 fos = new FileOutputStream(path+"/"+ndPathFileGen);								
						 fos.write(filesize);
						}
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					finally{
						if(fos!=null)
							try {
								fos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	 
					} 
				/*if(module.equals("mcLogo")){
					 MissAccount missAccount = new MissAccount();
					 missAccount.setMaId(Long.parseLong(id));
					 missAccount.setMaCustomizeLogoFileName(s);
					 missAccount.setMaCustomizeLogoHotlink(hotLink);
					  missAccount.setMaCustomizeLogoPath(pathFolder);
					  missExamService.updateMissAccountLogo(missAccount);
				}else if(module.equals("companyLogo")){
					 MissAccount missAccount = new MissAccount();
					 missAccount.setMaId(Long.parseLong(id));
					 missAccount.setMaCustomizeLogoFileName(s);
					 missAccount.setMaCustomizeLogoHotlink(hotLink);
					  missAccount.setMaCustomizeLogoPath(pathFolder);
					  missExamService.updateMissAccountLogo(missAccount);
				}else if(module.equals("candidateImg")){
					 MissCandidate missCandidate = new MissCandidate();
					 missCandidate.setMcaId(Long.parseLong(id));
					 missCandidate.setMcaPictureFileName(s);
					 missCandidate.setMcaPictureHotlink(hotLink);
					  missCandidate.setMcaPicturePath(pathFolder);
					  missExamService.updateMissCandidatePhoto(missCandidate);
				}else if(module.equals("contactImg")){
								
					 MissContact missContact = new MissContact();
					 missContact.setMcontactId(Long.parseLong(id));
					 missContact.setMcontactPictureFileName(s);
					 missContact.setMcontactPictureHotlink(hotLink);
					  missContact.setMcontactPicturePath(pathFolder);
					  missExamService.updateMissContactPhoto(missContact);
				}else if(module.equals("attachManual")){
					 MissManual missManual = new MissManual();
					 MissSery missSery=new MissSery();
					 missSery.setMsId(Long.parseLong(id));
					 missManual.setMissSery(missSery);
					 missManual.setMmId(Long.parseLong(id));
					 missManual.setMmFileName(s);
					 missManual.setMmHotlink(hotLink);
					  missManual.setMmPath(pathFolder);
					  missExamService.updateMissManual(missManual);
				}else if(module.equals("questionImg")){
					 MissAttach missAttach = new MissAttach();
					 //missAttach.setMatId((Long.parseLong(id));
					 missAttach.setMatFileName(s);
					 missAttach.setMatHotlink(hotLink);
					 missAttach.setMatPath(pathFolder);
					 missAttach.setMatRef(Long.parseLong(id));
					 missAttach.setMatModule(module);
					 missExamService.updateMissAttach(missAttach);
				}else if(module.equals("template")){
					 MissSeriesAttach missSeriesAttach = new MissSeriesAttach();
					 missSeriesAttach.setMsatRef1(Long.parseLong(id));
					 missSeriesAttach.setMsatModule(module);
					 missSeriesAttach.setMsatHotlink(hotLink);
					 missSeriesAttach.setMsatPath(pathFolder);
					 missSeriesAttach.setMsatFileName(s);
					 missExamService.updateMissSeriesAttach(missSeriesAttach);
				}else if(module.equals("evaluation")){
					//String[] ids=id.split("_");
					 MissSeriesAttach missSeriesAttach = new MissSeriesAttach(); 
					 missSeriesAttach.setMsatRef1(Long.parseLong(id));
					 missSeriesAttach.setMsatModule(module);
					 missSeriesAttach.setMsatHotlink(hotLink);
					 missSeriesAttach.setMsatPath(pathFolder);
					 missSeriesAttach.setMsatFileName(s);
					 missSeriesAttach.setRootPath(bundle.getString(module+"Path"));
					 missExamService.updateMissSeriesAttach(missSeriesAttach);
				}*/
		}
       // return missCandidate;
		missFile.setHotlink(hotLink);
		missFile.setFilename(s);
		missFile.setFilepath(pathFolder);
		Gson gson=new Gson();
		gson.toJson(missFile );
	//	return hotLink;
		 return gson.toJson(missFile );
    }
    @RequestMapping(value={"/getfile/{module}/{id}/{hotlink}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public void getFile(HttpServletRequest request,HttpServletResponse response,@PathVariable String module
    		,@PathVariable String id,@PathVariable String hotlink)
    {
    	//String hotlink = request.getQueryString();
		//String []adminview = hotlink.split("&mode=");
		//System.out.println(" adminview size="+adminview);
    	
		//	String filePath = "/usr/local/Work/TestDownload/1338218105884kqyoujf6uwhsqqwgwqitedq89kpl01u8nitc.jpg";
    	 
    	                                          
    	String  content_type= "image/jpeg";
    	String  content_disposition= "";
    	String path= bundle.getString(module+"Path");
    	String ndPathFileGen="";
    	//path+"/"+ndPathFileGen
    	/*if(module.equals("mcLogo")){
    		MissAccount missAccount= missExamService.findMissAccountById(Long.parseLong(id));
    		ndPathFileGen=path+missAccount.getMaCustomizeLogoPath();
		}else if(module.equals("companyLogo")){
			MissAccount missAccount=missExamService.findMissAccountById(Long.parseLong(id));
	    	ndPathFileGen=path+missAccount.getMaCustomizeLogoPath();
		}else if(module.equals("candidateImg")){
			MissCandidate missCandidate =missExamService.findMissCandidateById(Long.parseLong(id));
			 ndPathFileGen=path+missCandidate.getMcaPicturePath();
		}else if(module.equals("contactImg")){
			MissContact missContact=missExamService.findMissContactById(Long.parseLong(id));
			 ndPathFileGen=path+missContact.getMcontactPicturePath();
		}else if(module.equals("attachManual")){
			MissManual missManual=missExamService.findMissManualById(Long.parseLong(id));
			 ndPathFileGen=path+missManual.getMmPath();
			 content_type="application/pdf";
			 content_disposition="attachment; filename="+missManual.getMmFileName();
		}else if(module.equals("questionImg")){
			MissAttach missAttach =missExamService.findMissAttachById(module,Long.parseLong(id),hotlink);
			 ndPathFileGen=path+missAttach.getMatPath();
		}else if(module.equals("template")){ // jasper
			MissSeriesAttach missSeriesAttach =missExamService.findMissSeriesAttachSearch(module,Long.parseLong(id),null,hotlink);
			 ndPathFileGen=path+missSeriesAttach.getMsatPath();
			 content_type="";
			 content_disposition="attachment; filename="+missSeriesAttach.getMsatFileName();
		}else if(module.equals("evaluation")){
		//	String[] ids=id.split("_");
			MissSeriesAttach missSeriesAttach =missExamService.findMissSeriesAttachSearch(module,Long.parseLong(id),null,hotlink);
			//MissSeriesAttach missSeriesAttach =missExamService.findMissSeriesAttachSearch(module,Long.parseLong(ids[0]),Long.parseLong(ids[1]),hotlink);
			 ndPathFileGen=path+missSeriesAttach.getMsatPath();
			 content_type="application/vnd.ms-excel";
			 content_disposition="attachment; filename="+missSeriesAttach.getMsatFileName();
		}
		else if(module.equals("doc")){
			//	String[] ids=id.split("_");
			MissDoc missDoc =missExamService.findMissDocById(Long.parseLong(id));
				//MissSeriesAttach missSeriesAttach =missExamService.findMissSeriesAttachSearch(module,Long.parseLong(ids[0]),Long.parseLong(ids[1]),hotlink);
				 ndPathFileGen=path+missDoc.getMdDocPath();
				 content_type="application/pdf";
				 content_disposition="attachment; filename="+missDoc.getMdDocFileName();
			}*/
    	//String filePath =  bundle.getString(module+"Path")+hotlink+".jpg";
		//	String fileName = null;
			  
			//	String filenameStr ="เทสfชาติชาย.jpg";// fileName.trim().replaceAll(" ","_");
				//response.setHeader("Content-Type", "application/octet-stream; charset=tis620");
    	    if(content_type.length()>0)
				response.setHeader("Content-Type", content_type);
			if(content_disposition.length()>0)
				response.addHeader("Content-Disposition",content_disposition);
			//	logger.debug(" filenameStr==>"+filenameStr);
			/*	response.addHeader("content-disposition",
				        "attachment; filename=\"\u0e01เทสfชาติชาย.jpg\"");*/
			/*	response.addHeader("content-disposition",
				        "inline; filename="+filenameStr.trim());*/
			File file = new File(ndPathFileGen);

			boolean fileExists = file.exists();
			if(fileExists){
				InputStream in = null;
			      OutputStream out=null;
				try {
					out = response.getOutputStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			      InputStream stream  = null;
			      try {   
			    		  stream = new FileInputStream(file);
			    		 
					         in = new BufferedInputStream(stream);
			         while (true) {
			            int data = in.read();
			            if (data == -1) {
			               break;
			            }
			            out.write(data);
			         }
			      }catch (Exception e) {
			    	  e.printStackTrace();
					// TODO: handle exception
				 } finally {
			         if (in != null) {
			            try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			         }
			         if (stream != null) {
			        	 try {
							stream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				         } 
			         if (out != null) {
			            try { 
							  out.flush();
						      out.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			         }
				 }
			    }
    }
    private void createDirectoryIfNeeded(String directoryName)
  	 {
  	   File theDir = new File(directoryName);

  	   // if the directory does not exist, create it
  	   if (!theDir.exists())
  	   {
  		   //boolean cancreate = theDir.mkdir();
  		   theDir.mkdir();
  	   }
  	  
  	 }
      private String genToken(){
  		StringBuffer sb = new StringBuffer();
  	    for (int i = 36; i > 0; i -= 12) {
  	      int n = Math.min(12, Math.abs(i));
  	      sb.append(org.apache.commons.lang3.StringUtils.leftPad(Long.toString(Math.round(Math.random() * Math.pow(36, n)), 36), n, '0'));
  	    }
  	    return sb.toString();
   }
}
