package th.co.aoe.imake.thebluecode.backoffice.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;
import th.co.aoe.imake.thebluecode.backoffice.utils.ImakeFile;
import th.co.imake.tem.migratedata.form.CDRTemplate;
import th.co.imake.tem.migratedata.form.GroupTemplate;

import com.google.gson.Gson;

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
	@Autowired
	 private TheBlueCodeService theBlueCodeService;
	//public static DateFormat dateFormatTrue = new SimpleDateFormat("dd/MM/yy hhmmss", new Locale("th", "TH"));
	
	public static DateFormat dateFormatTrue = new SimpleDateFormat("dd/MM/yy HHmmss", new Locale("th", "TH"));
	public static DateFormat dateFormatTOT = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", new Locale("th", "TH")); 
		private static SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat format2 = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	public static DateFormat dateFormatTrue_ext = new SimpleDateFormat(
			"HH:mm:ss");
	public static Pattern date_pattern = Pattern.compile("\\d{2}/\\d{2}/\\d{2} \\d{6} \\w.*");
	public static Pattern time_pattern = Pattern.compile("\\d{1,2}:\\d{2}:\\d{2}");
	
	public static Pattern patternTOT = Pattern.compile("\\d{1,2}:\\d{2}:\\d{2} \\d{2}/\\d{2}/\\d{4} \\w.*");
	public static NumberFormat format = NumberFormat.getNumberInstance();
	static{
		format.setGroupingUsed(false);
	}
	// private static final Logger logger =
	// LoggerFactory.getLogger(ServiceConstant.LOG_APPENDER);
	private static final Logger logger = LoggerFactory
			.getLogger(UploadController.class);
	// private static Logger logger = Logger.getRootLogger();
	/*
	 * @Autowired private MissExamService missExamService;
	 */
	private static ResourceBundle bundle;
	static {
		bundle = ResourceBundle.getBundle("config");
	}

	@RequestMapping(value = { "/upload/{module}/{id}" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public String doUpload(HttpServletRequest request, Model model,
			@PathVariable String module, @PathVariable String id) {
		String ndPathFileGen = null;
		ImakeFile missFile = new ImakeFile();
		String hotLink = "";
		String s = "";
		String pathFolder = "";
		String ndFilePath = "";
		/*
		 * logger.debug("xxxxxxxxxxxxxxxxxxxxxxxx="+request.getParameter("test"))
		 * ; Map m =request.getParameterMap(); for (Iterator iterator =
		 * m.keySet().iterator(); iterator.hasNext();) { String type = (String)
		 * iterator.next(); String[] key=(String[])m.get(type);
		 * System.out.println("key="+type+",value="+m.get(type)); for (int i =
		 * 0; i < key.length; i++) {
		 * System.out.println(" xxxxxxxxxxx ="+key[i]); } }
		 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipart = multipartRequest.getFile("userfile");
		if (multipart != null) {
			String contentType = multipart.getContentType();
			s = multipart.getOriginalFilename();
			logger.info("fileName ===> " + s);
			logger.info("contentType ===> " + contentType);
			s = FilenameUtils.getName(s);
			logger.info("fileName2 ===> " + s);
			String monthStr = "";
			String yearStr = "";

			// String pathFolder="";
			FileOutputStream fos = null;
			try {
				byte[] filesize = multipart.getBytes();
				logger.info("xxxxxxxxxxxxx=" + filesize.length);
				if (filesize.length > 0) {
					long current = System.currentTimeMillis();
					org.joda.time.DateTime dt1 = new org.joda.time.DateTime(
							new Date().getTime());

					monthStr = dt1.getMonthOfYear() + "";
					yearStr = dt1.getYear() + "";
					monthStr = monthStr.length() > 1 ? monthStr : "0"
							+ monthStr;

					pathFolder = yearStr + "_" + monthStr + "";
					ndFilePath = bundle.getString(module + "Path") + pathFolder;
					String path = ndFilePath;
					createDirectoryIfNeeded(path);
					String filename = s;// multipart.getOriginalFilename();
					String[] filenameSplit = filename.split("\\.");
					String extension = "";
					if (filenameSplit != null && filenameSplit.length > 0) {
						extension = filenameSplit[filenameSplit.length - 1];
					}
					hotLink = current + "" + genToken();
					ndPathFileGen = hotLink + "." + extension;
					pathFolder = pathFolder + "/" + ndPathFileGen;
					// FileInputStream fin= new FileInputStream(file)
					fos = new FileOutputStream(path + "/" + ndPathFileGen);
					fos.write(filesize);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (fos != null)
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			/*
			 * if(module.equals("mcLogo")){ MissAccount missAccount = new
			 * MissAccount(); missAccount.setMaId(Long.parseLong(id));
			 * missAccount.setMaCustomizeLogoFileName(s);
			 * missAccount.setMaCustomizeLogoHotlink(hotLink);
			 * missAccount.setMaCustomizeLogoPath(pathFolder);
			 * missExamService.updateMissAccountLogo(missAccount); }else
			 * if(module.equals("companyLogo")){ MissAccount missAccount = new
			 * MissAccount(); missAccount.setMaId(Long.parseLong(id));
			 * missAccount.setMaCustomizeLogoFileName(s);
			 * missAccount.setMaCustomizeLogoHotlink(hotLink);
			 * missAccount.setMaCustomizeLogoPath(pathFolder);
			 * missExamService.updateMissAccountLogo(missAccount); }else
			 * if(module.equals("candidateImg")){ MissCandidate missCandidate =
			 * new MissCandidate(); missCandidate.setMcaId(Long.parseLong(id));
			 * missCandidate.setMcaPictureFileName(s);
			 * missCandidate.setMcaPictureHotlink(hotLink);
			 * missCandidate.setMcaPicturePath(pathFolder);
			 * missExamService.updateMissCandidatePhoto(missCandidate); }else
			 * if(module.equals("contactImg")){
			 * 
			 * MissContact missContact = new MissContact();
			 * missContact.setMcontactId(Long.parseLong(id));
			 * missContact.setMcontactPictureFileName(s);
			 * missContact.setMcontactPictureHotlink(hotLink);
			 * missContact.setMcontactPicturePath(pathFolder);
			 * missExamService.updateMissContactPhoto(missContact); }else
			 * if(module.equals("attachManual")){ MissManual missManual = new
			 * MissManual(); MissSery missSery=new MissSery();
			 * missSery.setMsId(Long.parseLong(id));
			 * missManual.setMissSery(missSery);
			 * missManual.setMmId(Long.parseLong(id));
			 * missManual.setMmFileName(s); missManual.setMmHotlink(hotLink);
			 * missManual.setMmPath(pathFolder);
			 * missExamService.updateMissManual(missManual); }else
			 * if(module.equals("questionImg")){ MissAttach missAttach = new
			 * MissAttach(); //missAttach.setMatId((Long.parseLong(id));
			 * missAttach.setMatFileName(s); missAttach.setMatHotlink(hotLink);
			 * missAttach.setMatPath(pathFolder);
			 * missAttach.setMatRef(Long.parseLong(id));
			 * missAttach.setMatModule(module);
			 * missExamService.updateMissAttach(missAttach); }else
			 * if(module.equals("template")){ MissSeriesAttach missSeriesAttach
			 * = new MissSeriesAttach();
			 * missSeriesAttach.setMsatRef1(Long.parseLong(id));
			 * missSeriesAttach.setMsatModule(module);
			 * missSeriesAttach.setMsatHotlink(hotLink);
			 * missSeriesAttach.setMsatPath(pathFolder);
			 * missSeriesAttach.setMsatFileName(s);
			 * missExamService.updateMissSeriesAttach(missSeriesAttach); }else
			 * if(module.equals("evaluation")){ //String[] ids=id.split("_");
			 * MissSeriesAttach missSeriesAttach = new MissSeriesAttach();
			 * missSeriesAttach.setMsatRef1(Long.parseLong(id));
			 * missSeriesAttach.setMsatModule(module);
			 * missSeriesAttach.setMsatHotlink(hotLink);
			 * missSeriesAttach.setMsatPath(pathFolder);
			 * missSeriesAttach.setMsatFileName(s);
			 * missSeriesAttach.setRootPath(bundle.getString(module+"Path"));
			 * missExamService.updateMissSeriesAttach(missSeriesAttach); }
			 */
		}
		// return missCandidate;
		missFile.setHotlink(hotLink);
		missFile.setFilename(s);
		missFile.setFilepath(pathFolder);
		Gson gson = new Gson();
		gson.toJson(missFile);
		// return hotLink;
		return gson.toJson(missFile);
	}

	@RequestMapping(value = { "/getfile/{module}/{id}/{hotlink}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public void getFile(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String module,
			@PathVariable String id, @PathVariable String hotlink) {
		// String hotlink = request.getQueryString();
		// String []adminview = hotlink.split("&mode=");
		// System.out.println(" adminview size="+adminview);

		// String filePath =
		// "/usr/local/Work/TestDownload/1338218105884kqyoujf6uwhsqqwgwqitedq89kpl01u8nitc.jpg";

		String content_type = "image/jpeg";
		String content_disposition = "";
		String path = bundle.getString(module + "Path");
		String ndPathFileGen = "";
		// path+"/"+ndPathFileGen
		/*
		 * if(module.equals("mcLogo")){ MissAccount missAccount=
		 * missExamService.findMissAccountById(Long.parseLong(id));
		 * ndPathFileGen=path+missAccount.getMaCustomizeLogoPath(); }else
		 * if(module.equals("companyLogo")){ MissAccount
		 * missAccount=missExamService.findMissAccountById(Long.parseLong(id));
		 * ndPathFileGen=path+missAccount.getMaCustomizeLogoPath(); }else
		 * if(module.equals("candidateImg")){ MissCandidate missCandidate
		 * =missExamService.findMissCandidateById(Long.parseLong(id));
		 * ndPathFileGen=path+missCandidate.getMcaPicturePath(); }else
		 * if(module.equals("contactImg")){ MissContact
		 * missContact=missExamService.findMissContactById(Long.parseLong(id));
		 * ndPathFileGen=path+missContact.getMcontactPicturePath(); }else
		 * if(module.equals("attachManual")){ MissManual
		 * missManual=missExamService.findMissManualById(Long.parseLong(id));
		 * ndPathFileGen=path+missManual.getMmPath();
		 * content_type="application/pdf";
		 * content_disposition="attachment; filename="
		 * +missManual.getMmFileName(); }else if(module.equals("questionImg")){
		 * MissAttach missAttach
		 * =missExamService.findMissAttachById(module,Long.
		 * parseLong(id),hotlink); ndPathFileGen=path+missAttach.getMatPath();
		 * }else if(module.equals("template")){ // jasper MissSeriesAttach
		 * missSeriesAttach
		 * =missExamService.findMissSeriesAttachSearch(module,Long
		 * .parseLong(id),null,hotlink);
		 * ndPathFileGen=path+missSeriesAttach.getMsatPath(); content_type="";
		 * content_disposition
		 * ="attachment; filename="+missSeriesAttach.getMsatFileName(); }else
		 * if(module.equals("evaluation")){ // String[] ids=id.split("_");
		 * MissSeriesAttach missSeriesAttach
		 * =missExamService.findMissSeriesAttachSearch
		 * (module,Long.parseLong(id),null,hotlink); //MissSeriesAttach
		 * missSeriesAttach
		 * =missExamService.findMissSeriesAttachSearch(module,Long
		 * .parseLong(ids[0]),Long.parseLong(ids[1]),hotlink);
		 * ndPathFileGen=path+missSeriesAttach.getMsatPath();
		 * content_type="application/vnd.ms-excel";
		 * content_disposition="attachment; filename="
		 * +missSeriesAttach.getMsatFileName(); } else if(module.equals("doc")){
		 * // String[] ids=id.split("_"); MissDoc missDoc
		 * =missExamService.findMissDocById(Long.parseLong(id));
		 * //MissSeriesAttach missSeriesAttach
		 * =missExamService.findMissSeriesAttachSearch
		 * (module,Long.parseLong(ids[0]),Long.parseLong(ids[1]),hotlink);
		 * ndPathFileGen=path+missDoc.getMdDocPath();
		 * content_type="application/pdf";
		 * content_disposition="attachment; filename="
		 * +missDoc.getMdDocFileName(); }
		 */
		// String filePath = bundle.getString(module+"Path")+hotlink+".jpg";
		// String fileName = null;

		// String filenameStr ="เทสfชาติชาย.jpg";//
		// fileName.trim().replaceAll(" ","_");
		// response.setHeader("Content-Type",
		// "application/octet-stream; charset=tis620");
		if (content_type.length() > 0)
			response.setHeader("Content-Type", content_type);
		if (content_disposition.length() > 0)
			response.addHeader("Content-Disposition", content_disposition);
		// logger.debug(" filenameStr==>"+filenameStr);
		/*
		 * response.addHeader("content-disposition",
		 * "attachment; filename=\"\u0e01เทสfชาติชาย.jpg\"");
		 */
		/*
		 * response.addHeader("content-disposition",
		 * "inline; filename="+filenameStr.trim());
		 */
		File file = new File(ndPathFileGen);

		boolean fileExists = file.exists();
		if (fileExists) {
			InputStream in = null;
			OutputStream out = null;
			try {
				out = response.getOutputStream();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			InputStream stream = null;
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
			} catch (Exception e) {
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

	@RequestMapping(value = { "/import/{module}/{id}" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public String doImport(HttpServletRequest request, Model model,
			@PathVariable String module, @PathVariable String id) {
		
		String provider_id=request.getParameter("provider_id");
		//System.out.println("provider_id="+provider_id);
		/*String ndPathFileGen = null;
		ImakeFile missFile = new ImakeFile();
		String hotLink = "";
		
		String pathFolder = "";
		String ndFilePath = "";*/
		String s = "";
		List result=null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipart = multipartRequest.getFile("userfile");
		if (multipart != null) {
			String contentType = multipart.getContentType();
			s = multipart.getOriginalFilename();
			logger.info("fileName ===> " + s);
			logger.info("contentType ===> " + contentType);
			s = FilenameUtils.getName(s);
			logger.info("fileName2 ===> " + s);
			String monthStr = "";
			String yearStr = "";

			// String pathFolder="";
			// FileOutputStream fos = null;
			try {
				byte[] filesize = multipart.getBytes();
				// InputStream in=

				logger.info("xxxxxxxxxxxxx=" + filesize.length);
				if (filesize.length > 0) {
					long current = System.currentTimeMillis();
					/*
					 * org.joda.time.DateTime dt1 = new org.joda.time.DateTime
					 * (new Date().getTime());
					 * 
					 * monthStr= dt1.getMonthOfYear()+""; yearStr=
					 * dt1.getYear()+""; monthStr =
					 * monthStr.length()>1?monthStr:"0"+monthStr;
					 * 
					 * pathFolder=yearStr+"_"+monthStr+""; ndFilePath =
					 * bundle.getString(module+"Path")+pathFolder; String path
					 * =ndFilePath; createDirectoryIfNeeded(path);
					 */
					String filename = s;// multipart.getOriginalFilename();
					String[] filenameSplit = filename.split("\\.");
					String extension = "";
					if (filenameSplit != null && filenameSplit.length > 0) {
						extension = filenameSplit[filenameSplit.length - 1];
					}

					boolean isXLSX = extension.equalsIgnoreCase("xlsx") ? true
							: false;
					if(provider_id.equalsIgnoreCase("True")){ 
						result=readTrueTemplate(multipart.getInputStream(), isXLSX);
						//System.out.println(" size      ="+result.size());
						if(result!=null && result.size()==2){
							if(((String)result.get(0)).equals("1")){
								List<CDRTemplate> xxx =(List<CDRTemplate>)result.get(1);
								//System.out.println(theBlueCodeService);
								try{
								int records=theBlueCodeService.importCDR(xxx);
								//System.out.println(records);
								}catch(Exception ex){
									ex.printStackTrace();
								}
								/*System.out.println(" xxx size      ="+xxx.size());
								System.out.println(" CDRTemplate     ="+xxx.get(0).getClass());
								for (int i = 0; i < xxx.size(); i++) {  
									CDRTemplate cdrTemplate = (CDRTemplate)xxx.get(i);
									System.out.println(" "+cdrTemplate.getMsIsdnFrom()+" to "+cdrTemplate.getMsIsdnTo()
											+"  date "+cdrTemplate.getUsedDate()+" time "+cdrTemplate.getUsedTime()+" price "+cdrTemplate.getPrice()+" usedCount "+cdrTemplate.getUsedCount());
								} */
							}
						}
						
					}else if(provider_id.equalsIgnoreCase("TOT")){ 
						result=readTOTTemplate(multipart.getInputStream(), isXLSX);
						//System.out.println(" size      ="+result.size());
						 
						if(result!=null && result.size()==2){
							if(((String)result.get(0)).equals("1")){
								List<CDRTemplate> xxx =(List<CDRTemplate>)result.get(1);
								//System.out.println(theBlueCodeService);
								try{
								int records=theBlueCodeService.importCDR(xxx);
								//System.out.println(records);
								}catch(Exception ex){
									ex.printStackTrace();
								}
						/*System.out.println(" xxx size      ="+xxx.size());
						System.out.println(" CDRTemplate     ="+xxx.get(0).getClass());
						for (int i = 0; i < xxx.size(); i++) {  
							CDRTemplate cdrTemplate = (CDRTemplate)xxx.get(i);
							System.out.println(" "+cdrTemplate.getMsIsdnFrom()+" to "+cdrTemplate.getMsIsdnTo()
									+"  date "+cdrTemplate.getUsedDate()+" time "+cdrTemplate.getUsedTime()+" price "+cdrTemplate.getPrice()+" usedCount "+cdrTemplate.getUsedCount());
						} */ 
							}
						}
					}else{
						result=new ArrayList(2);
						result.add("2");
						result.add("3");
					}

				/*	hotLink = current + "" + genToken();
					ndPathFileGen = hotLink + "." + extension;
					pathFolder = pathFolder + "/" + ndPathFileGen;*/
					// FileInputStream fin= new FileInputStream(file)
					/*
					 * fos = new FileOutputStream(path+"/"+ndPathFileGen);
					 * fos.write(filesize);
					 */
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				/*
				 * if(fos!=null) try { fos.close(); } catch (IOException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 */
			}
			/*
			 * if(module.equals("mcLogo")){ MissAccount missAccount = new
			 * MissAccount(); missAccount.setMaId(Long.parseLong(id));
			 * missAccount.setMaCustomizeLogoFileName(s);
			 * missAccount.setMaCustomizeLogoHotlink(hotLink);
			 * missAccount.setMaCustomizeLogoPath(pathFolder);
			 * missExamService.updateMissAccountLogo(missAccount); }else
			 * if(module.equals("companyLogo")){ MissAccount missAccount = new
			 * MissAccount(); missAccount.setMaId(Long.parseLong(id));
			 * missAccount.setMaCustomizeLogoFileName(s);
			 * missAccount.setMaCustomizeLogoHotlink(hotLink);
			 * missAccount.setMaCustomizeLogoPath(pathFolder);
			 * missExamService.updateMissAccountLogo(missAccount); }else
			 * if(module.equals("candidateImg")){ MissCandidate missCandidate =
			 * new MissCandidate(); missCandidate.setMcaId(Long.parseLong(id));
			 * missCandidate.setMcaPictureFileName(s);
			 * missCandidate.setMcaPictureHotlink(hotLink);
			 * missCandidate.setMcaPicturePath(pathFolder);
			 * missExamService.updateMissCandidatePhoto(missCandidate); }else
			 * if(module.equals("contactImg")){
			 * 
			 * MissContact missContact = new MissContact();
			 * missContact.setMcontactId(Long.parseLong(id));
			 * missContact.setMcontactPictureFileName(s);
			 * missContact.setMcontactPictureHotlink(hotLink);
			 * missContact.setMcontactPicturePath(pathFolder);
			 * missExamService.updateMissContactPhoto(missContact); }else
			 * if(module.equals("attachManual")){ MissManual missManual = new
			 * MissManual(); MissSery missSery=new MissSery();
			 * missSery.setMsId(Long.parseLong(id));
			 * missManual.setMissSery(missSery);
			 * missManual.setMmId(Long.parseLong(id));
			 * missManual.setMmFileName(s); missManual.setMmHotlink(hotLink);
			 * missManual.setMmPath(pathFolder);
			 * missExamService.updateMissManual(missManual); }else
			 * if(module.equals("questionImg")){ MissAttach missAttach = new
			 * MissAttach(); //missAttach.setMatId((Long.parseLong(id));
			 * missAttach.setMatFileName(s); missAttach.setMatHotlink(hotLink);
			 * missAttach.setMatPath(pathFolder);
			 * missAttach.setMatRef(Long.parseLong(id));
			 * missAttach.setMatModule(module);
			 * missExamService.updateMissAttach(missAttach); }else
			 * if(module.equals("template")){ MissSeriesAttach missSeriesAttach
			 * = new MissSeriesAttach();
			 * missSeriesAttach.setMsatRef1(Long.parseLong(id));
			 * missSeriesAttach.setMsatModule(module);
			 * missSeriesAttach.setMsatHotlink(hotLink);
			 * missSeriesAttach.setMsatPath(pathFolder);
			 * missSeriesAttach.setMsatFileName(s);
			 * missExamService.updateMissSeriesAttach(missSeriesAttach); }else
			 * if(module.equals("evaluation")){ //String[] ids=id.split("_");
			 * MissSeriesAttach missSeriesAttach = new MissSeriesAttach();
			 * missSeriesAttach.setMsatRef1(Long.parseLong(id));
			 * missSeriesAttach.setMsatModule(module);
			 * missSeriesAttach.setMsatHotlink(hotLink);
			 * missSeriesAttach.setMsatPath(pathFolder);
			 * missSeriesAttach.setMsatFileName(s);
			 * missSeriesAttach.setRootPath(bundle.getString(module+"Path"));
			 * missExamService.updateMissSeriesAttach(missSeriesAttach); }
			 */
		}
		// return missCandidate;

	/*	missFile.setHotlink(hotLink);
		missFile.setFilename(s);
		missFile.setFilepath(pathFolder);*/
		Gson gson = new Gson();
		/*gson.toJson(missFile);*/
		// return hotLink;
		return gson.toJson(result);

	}

	private List readTrueTemplate(InputStream in, boolean isXLSX) {
		
		List list = new ArrayList(2);  // 0 =type [0==not success , 1==success] , 1 =data [invalid,result message ]
		List data = new ArrayList();

		// Sameple
		/*
		 * // HSSFWorkbook, File NPOIFSFileSytem fs = new NPOIFSFileSystem(new
		 * File("file.xls")); HSSFWorkbook wb = new HSSFWorkbook(fs.getRoot());
		 * .... fs.close();
		 * 
		 * // HSSFWorkbook, InputStream, needs more memory NPOIFSFileSytem fs =
		 * new NPOIFSFileSystem(myInputStream); HSSFWorkbook wb = new
		 * HSSFWorkbook(fs.getRoot());
		 * 
		 * // XSSFWorkbook, File OPCPackage pkg = OPCPackage.open(new
		 * File("file.xlsx")); XSSFWorkbook wb = new XSSFWorkbook(pkg); ....
		 * pkg.close();
		 * 
		 * // XSSFWorkbook, InputStream, needs more memory OPCPackage pkg =
		 * OPCPackage.open(myInputStream); XSSFWorkbook wb = new
		 * XSSFWorkbook(pkg); .... pkg.close();
		 */
		try {
			// FileInputStream myInput = new FileInputStream(fileName);

			Workbook myWorkBook = null;
			if (isXLSX) {
				myWorkBook = new XSSFWorkbook(in);
			} else {
				POIFSFileSystem myFileSystem = new POIFSFileSystem(in); 
				myWorkBook = new HSSFWorkbook(myFileSystem);
			}
			
			// System.out.println(myWorkBook);

			Sheet sheet1 = myWorkBook.getSheetAt(0);
			// String [] columns={"A","B","C","D","E"};
			int[] types = { 0, 0, 1, 6, 0 };

			CellReference cellRef = null;
			int columnIndex = 0;
			int rowNum = 0;
			int end_col=5;
			Cell cell=null;
			for (Row row : sheet1) {
				//for (Cell cell : row) {
				for (int j=0;j<end_col;j++) {
					cell=row.getCell(j);
					columnIndex = cell.getColumnIndex();
					rowNum = row.getRowNum();
					if (rowNum > 0) {
						cellRef = new CellReference(rowNum, columnIndex);
						// check type
						if (types[columnIndex] == 6 && cell.getCellType() == 0) { // ok

						} else if (types[columnIndex] == cell.getCellType()) { // ok

						} else { // not ok
							data.add(cellRef.formatAsString());
						}

						// check data
						if (columnIndex == 2) {
							Matcher matcher = date_pattern.matcher(cell
									.getRichStringCellValue().getString());
							boolean isMatches = matcher.matches();
							if (!isMatches)
								data.add(cellRef.formatAsString());

						}
					}
				}
			}
			if(data.size()>0){ //not success
				list.add("0");
				list.add(data);
			}else{ //success
				list.add("1");
				list.add(getTrueValue(sheet1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		/*System.out.println(" in invalid " + inValid.size());
		for (int i = 0; i < inValid.size(); i++) {
			System.out.println(inValid.get(i).toString());
		}*/
		return list;
	}
	 private List getGroupValue( Sheet sheet1){ 
		 List list=new ArrayList(); 
			int rowNum = 0;
			Cell cell=null;
			for (Row row : sheet1) {
				//for (Cell cell : row) {
					//columnIndex = cell.getColumnIndex();
					rowNum = row.getRowNum();
					if (rowNum > 0) { 
						GroupTemplate groupTemplate = new GroupTemplate();
						cell=row.getCell(0);
						groupTemplate.setGroup(cell.toString());
						cell=row.getCell(1);
						groupTemplate.setCompany(cell.toString());
						cell=row.getCell(2); 
						groupTemplate.setNo("0"+format.format(cell.getNumericCellValue()));
						cell=row.getCell(3);
						groupTemplate.setPackageType(cell.toString());
				 
						list.add(groupTemplate);
			          }
			}
		return list;
	 }
	 private List getTrueValue( Sheet sheet1){
		// Sheet sheet1 = myWorkBook.getSheetAt(0);
			// String [] columns={"A","B","C","D","E"};
		 List list=new ArrayList();
			//int[] types = { 0, 0, 1, 6, 0 };

		//	CellReference cellRef = null;
			//int columnIndex = 0;
			int rowNum = 0;
			Cell cell=null;
			for (Row row : sheet1) {
				//for (Cell cell : row) {
					//columnIndex = cell.getColumnIndex();
					rowNum = row.getRowNum();
					if (rowNum > 0) {
						cell=row.getCell(0);
						CDRTemplate cdrTemplate = new CDRTemplate();
						cdrTemplate.setMsIsdnFrom("0"+format.format(cell.getNumericCellValue()));
						cell=row.getCell(1);
						cdrTemplate.setMsIsdnTo("0"+format.format(cell.getNumericCellValue()));
						cell=row.getCell(2);
						  //System.out.println(cell.getRichStringCellValue().getString()+" , type="+Cell.CELL_TYPE_STRING);
			              String[] address=cell.getRichStringCellValue().getString().split(" ");
			              if(address.length>2){
			              	//System.out.println("Date used "+address[0]); // check format date
			              	// 21/11/55151337 Ubonratchathani invalide
			              	//System.out.println("Time used "+address[1]);
			            	Date usedDate=null;
							try {
								usedDate = dateFormatTrue.parse(address[0]+" "+address[1]);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							cdrTemplate.setUsedDate(usedDate);
							Time usedTime = new Time(usedDate.getTime());
							cdrTemplate.setUsedTime(usedTime);
			              	String toStr="";
			              	for(int i=2;i<address.length;i++){
			              		toStr=toStr+address[i]+" ";
			              	}
			              	cdrTemplate.setMsIsdnToLocation(toStr);
			              }
			              	//System.out.println("To "+toStr.trim());
			              	
						cell=row.getCell(3); // 00:04:00 
		                  //D457 - Sun Dec 31 00:04:00 ICT 1899 , type= date 0
		              	String usedCount=dateFormatTrue_ext.format(cell.getDateCellValue());
		              	Double used = 0.0; 
							String[] useSplit = usedCount.toString().trim().split(":");
							// 
							used = ((Double.parseDouble(useSplit[0])*360)+(Double.parseDouble(useSplit[1])*60)+(Double.parseDouble(useSplit[2])));
							//used = ((Double.parseDouble(useSplit[0])*60)+(Double.parseDouble(useSplit[1]))+(Double.parseDouble(useSplit[2])/100));
						 
						cdrTemplate.setUsedCount(used);
		              	//System.out.println(aoeStr);
		              	
						cell=row.getCell(4); 
						cdrTemplate.setUsedType("call");
						cdrTemplate.setPrice(cell.getNumericCellValue());
						cdrTemplate.setMsIsdnFromProvider("TRUE");
						list.add(cdrTemplate);
			          }
			}
		return list; 
	  }

		private List readTOTTemplate(InputStream in, boolean isXLSX) {
			
			List list = new ArrayList(2);  // 0 =type [0==not success , 1==success] , 1 =data [invalid,result message ]
			List data = new ArrayList();
			int end_col=6;
			try { 
				Workbook myWorkBook = null;
				if (isXLSX) {
					myWorkBook = new XSSFWorkbook(in);
				} else {
					POIFSFileSystem myFileSystem = new POIFSFileSystem(in); 
					myWorkBook = new HSSFWorkbook(myFileSystem);
				} 
				Sheet sheet1 = myWorkBook.getSheetAt(0);
				// String [] columns={"A","B","C","D","E"};
				int[] types = { 0, 0, 1, 1, 0,0 };

				CellReference cellRef = null;
				int columnIndex = 0;
				int rowNum = 0;
				Cell cell=null;
				
				for (Row row : sheet1) {
					//for (Cell cell : row) {
					for (int j=0;j<end_col;j++) {
						cell =row.getCell(j);
						columnIndex = cell.getColumnIndex(); 
						rowNum = row.getRowNum();
						if (rowNum > 0) {
							cellRef = new CellReference(rowNum, columnIndex);
							// check type
							  if (types[columnIndex] == cell.getCellType()) { // ok

							} else { // not ok
								data.add(cellRef.formatAsString());
							}

							// check data
							if (columnIndex == 2) {
								Matcher matcher = patternTOT.matcher(cell
										.getRichStringCellValue().getString());
								boolean isMatches = matcher.matches();
								if (!isMatches)
									data.add(cellRef.formatAsString());

							}
						}
					}
				}
				if(data.size()>0){ //not success
					list.add("0");
					list.add(data);
				}else{ //success
					list.add("1");
					list.add(getTOTValue(sheet1));
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			/*System.out.println(" in invalid " + inValid.size());
			for (int i = 0; i < inValid.size(); i++) {
				System.out.println(inValid.get(i).toString());
			}*/
			return list;
		}
		 private List getTOTValue( Sheet sheet1){
			// Sheet sheet1 = myWorkBook.getSheetAt(0);
				// String [] columns={"A","B","C","D","E"};
			 List list=new ArrayList();
				//int[] types = { 0, 0, 1, 1, 0,0 };

			//	CellReference cellRef = null;
				//int columnIndex = 0;
				int rowNum = 0;
				Cell cell=null;
				for (Row row : sheet1) {
					//for (Cell cell : row) {
						//columnIndex = cell.getColumnIndex();
						rowNum = row.getRowNum();
						if (rowNum > 0) {
							cell=row.getCell(0);
							CDRTemplate cdrTemplate = new CDRTemplate();
							cdrTemplate.setMsIsdnFrom("0"+format.format(cell.getNumericCellValue()));
							cell=row.getCell(1);
							cdrTemplate.setMsIsdnTo("0"+format.format(cell.getNumericCellValue()));
							cell=row.getCell(2);
							  //System.out.println(cell.getRichStringCellValue().getString()+" , type="+Cell.CELL_TYPE_STRING);
				              String[] address=cell.getRichStringCellValue().getString().split(" ");
				              if(address.length>2){
				              	//System.out.println("Date used "+address[0]); // check format date
				              	// 21/11/55151337 Ubonratchathani invalide
				              	//System.out.println("Time used "+address[1]);
				            	Date usedDate=null;
								try {
									usedDate = dateFormatTOT.parse(address[0]+" "+address[1]);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								cdrTemplate.setUsedDate(usedDate);
								Time usedTime = new Time(usedDate.getTime());
								cdrTemplate.setUsedTime(usedTime);
				              	String toStr="";
				              	for(int i=2;i<address.length;i++){
				              		toStr=toStr+address[i]+" ";
				              	}
				              	cdrTemplate.setMsIsdnToLocation(toStr);
				              }
				              	//System.out.println("To "+toStr.trim());
				              	
							cell=row.getCell(4); // 00:04:00 
			                  //D457 - Sun Dec 31 00:04:00 ICT 1899 , type= date 0
			              	//String usedCount=dateFormatTrue_ext.format(cell.getDateCellValue());
			              	Double used = (cell.getNumericCellValue()*60);  
							cdrTemplate.setUsedCount(used);
			              	//System.out.println(aoeStr);
			              	
							cell=row.getCell(5); 
							cdrTemplate.setUsedType("call");
							cdrTemplate.setPrice(cell.getNumericCellValue());
							cdrTemplate.setMsIsdnFromProvider("TOT");
							list.add(cdrTemplate);
				          }
				}
			return list; 
		  }
		 private List readGroupTemplate(InputStream in, boolean isXLSX) {
				
				List list = new ArrayList(2);  // 0 =type [0==not success , 1==success] , 1 =data [invalid,result message ]
				List data = new ArrayList();
 
				try { 
					Workbook myWorkBook = null;
					if (isXLSX) {
						myWorkBook = new XSSFWorkbook(in);
					} else {
						POIFSFileSystem myFileSystem = new POIFSFileSystem(in); 
						myWorkBook = new HSSFWorkbook(myFileSystem);
					}
					
					// System.out.println(myWorkBook);

					Sheet sheet1 = myWorkBook.getSheetAt(0);
					// String [] columns={"A","B","C","D","E"};
					int[] types = { 1, 1, 0, 1 };

					CellReference cellRef = null;
					int columnIndex = 0;
					int rowNum = 0;
					int end_col=3;
					Cell cell=null;
					for (Row row : sheet1) {
						//for (Cell cell : row) {
						for (int j=0;j<end_col;j++) {
							cell=row.getCell(j);
							columnIndex = cell.getColumnIndex();
							rowNum = row.getRowNum();
							if (rowNum > 0) {
								cellRef = new CellReference(rowNum, columnIndex);
								// check type
								if (types[columnIndex] == 6 && cell.getCellType() == 0) { // ok

								} else if (types[columnIndex] == cell.getCellType()) { // ok

								} else { // not ok
									data.add(cellRef.formatAsString());
								}

								// check data
								/*if (columnIndex == 2) {
									Matcher matcher = date_pattern.matcher(cell
											.getRichStringCellValue().getString());
									boolean isMatches = matcher.matches();
									if (!isMatches)
										data.add(cellRef.formatAsString());

								}*/
							}
						}
					}
					if(data.size()>0){ //not success
						list.add("0");
						list.add(data);
					}else{ //success
						list.add("1");
						list.add(getGroupValue(sheet1));
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (in != null)
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				/*System.out.println(" in invalid " + inValid.size());
				for (int i = 0; i < inValid.size(); i++) {
					System.out.println(inValid.get(i).toString());
				}*/
				return list;
			}
		 @RequestMapping(value = { "/importGroup" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
			@ResponseBody
		public String doImportGroup(HttpServletRequest request, Model model ) {
				 
				/*String ndPathFileGen = null;
				ImakeFile missFile = new ImakeFile();)
				String hotLink = "";
				
				String pathFolder = "";
				String ndFilePath = "";*/
				String s = "";
				List result=null;
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile multipart = multipartRequest.getFile("userfile");
				if (multipart != null) {
					String contentType = multipart.getContentType();
					s = multipart.getOriginalFilename();
					logger.info("fileName ===> " + s);
					logger.info("contentType ===> " + contentType);
					s = FilenameUtils.getName(s);
					logger.info("fileName2 ===> " + s);
					String monthStr = "";
					String yearStr = "";

					// String pathFolder="";
					// FileOutputStream fos = null;
					try {
						byte[] filesize = multipart.getBytes();
						// InputStream in=

						logger.info("xxxxxxxxxxxxx=" + filesize.length);
						if (filesize.length > 0) {
							long current = System.currentTimeMillis();
							/*
							 * org.joda.time.DateTime dt1 = new org.joda.time.DateTime
							 * (new Date().getTime());
							 * 
							 * monthStr= dt1.getMonthOfYear()+""; yearStr=
							 * dt1.getYear()+""; monthStr =
							 * monthStr.length()>1?monthStr:"0"+monthStr;
							 * 
							 * pathFolder=yearStr+"_"+monthStr+""; ndFilePath =
							 * bundle.getString(module+"Path")+pathFolder; String path
							 * =ndFilePath; createDirectoryIfNeeded(path);
							 */
							String filename = s;// multipart.getOriginalFilename();
							String[] filenameSplit = filename.split("\\.");
							String extension = "";
							if (filenameSplit != null && filenameSplit.length > 0) {
								extension = filenameSplit[filenameSplit.length - 1];
							}

							boolean isXLSX = extension.equalsIgnoreCase("xlsx") ? true
									: false; 
						result=readGroupTemplate(multipart.getInputStream(), isXLSX);
								//System.out.println(" size      ="+result.size());
								if(result!=null && result.size()==2){
									if(((String)result.get(0)).equals("1")){
										List<GroupTemplate> xxx =(List<GroupTemplate>)result.get(1);
										/*System.out.println(" xxx size      ="+xxx.size());
										System.out.println(" CDRTemplate     ="+xxx.get(0).getClass());
										for (int i = 0; i < xxx.size(); i++) {  
											GroupTemplate groupTemplate = (GroupTemplate)xxx.get(i);
											System.out.println(" "+groupTemplate.getGroup()+" , "+groupTemplate.getCompany()
													+"  , "+groupTemplate.getNo()+" , "+groupTemplate.getPackageType());
										} */
										//System.out.println(theBlueCodeService);
										try{
										int records=theBlueCodeService.importGroup(xxx);
										//System.out.println(records);
										}catch(Exception ex){
											ex.printStackTrace();
										}
									}
								}
								
						 

						/*	hotLink = current + "" + genToken();
							ndPathFileGen = hotLink + "." + extension;
							pathFolder = pathFolder + "/" + ndPathFileGen;*/
							// FileInputStream fin= new FileInputStream(file)
							/*
							 * fos = new FileOutputStream(path+"/"+ndPathFileGen);
							 * fos.write(filesize);
							 */
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {
						/*
						 * if(fos!=null) try { fos.close(); } catch (IOException e) { //
						 * TODO Auto-generated catch block e.printStackTrace(); }
						 */
					}
					/*
					 * if(module.equals("mcLogo")){ MissAccount missAccount = new
					 * MissAccount(); missAccount.setMaId(Long.parseLong(id));
					 * missAccount.setMaCustomizeLogoFileName(s);
					 * missAccount.setMaCustomizeLogoHotlink(hotLink);
					 * missAccount.setMaCustomizeLogoPath(pathFolder);
					 * missExamService.updateMissAccountLogo(missAccount); }else
					 * if(module.equals("companyLogo")){ MissAccount missAccount = new
					 * MissAccount(); missAccount.setMaId(Long.parseLong(id));
					 * missAccount.setMaCustomizeLogoFileName(s);
					 * missAccount.setMaCustomizeLogoHotlink(hotLink);
					 * missAccount.setMaCustomizeLogoPath(pathFolder);
					 * missExamService.updateMissAccountLogo(missAccount); }else
					 * if(module.equals("candidateImg")){ MissCandidate missCandidate =
					 * new MissCandidate(); missCandidate.setMcaId(Long.parseLong(id));
					 * missCandidate.setMcaPictureFileName(s);
					 * missCandidate.setMcaPictureHotlink(hotLink);
					 * missCandidate.setMcaPicturePath(pathFolder);
					 * missExamService.updateMissCandidatePhoto(missCandidate); }else
					 * if(module.equals("contactImg")){
					 * 
					 * MissContact missContact = new MissContact();
					 * missContact.setMcontactId(Long.parseLong(id));
					 * missContact.setMcontactPictureFileName(s);
					 * missContact.setMcontactPictureHotlink(hotLink);
					 * missContact.setMcontactPicturePath(pathFolder);
					 * missExamService.updateMissContactPhoto(missContact); }else
					 * if(module.equals("attachManual")){ MissManual missManual = new
					 * MissManual(); MissSery missSery=new MissSery();
					 * missSery.setMsId(Long.parseLong(id));
					 * missManual.setMissSery(missSery);
					 * missManual.setMmId(Long.parseLong(id));
					 * missManual.setMmFileName(s); missManual.setMmHotlink(hotLink);
					 * missManual.setMmPath(pathFolder);
					 * missExamService.updateMissManual(missManual); }else
					 * if(module.equals("questionImg")){ MissAttach missAttach = new
					 * MissAttach(); //missAttach.setMatId((Long.parseLong(id));
					 * missAttach.setMatFileName(s); missAttach.setMatHotlink(hotLink);
					 * missAttach.setMatPath(pathFolder);
					 * missAttach.setMatRef(Long.parseLong(id));
					 * missAttach.setMatModule(module);
					 * missExamService.updateMissAttach(missAttach); }else
					 * if(module.equals("template")){ MissSeriesAttach missSeriesAttach
					 * = new MissSeriesAttach();
					 * missSeriesAttach.setMsatRef1(Long.parseLong(id));
					 * missSeriesAttach.setMsatModule(module);
					 * missSeriesAttach.setMsatHotlink(hotLink);
					 * missSeriesAttach.setMsatPath(pathFolder);
					 * missSeriesAttach.setMsatFileName(s);
					 * missExamService.updateMissSeriesAttach(missSeriesAttach); }else
					 * if(module.equals("evaluation")){ //String[] ids=id.split("_");
					 * MissSeriesAttach missSeriesAttach = new MissSeriesAttach();
					 * missSeriesAttach.setMsatRef1(Long.parseLong(id));
					 * missSeriesAttach.setMsatModule(module);
					 * missSeriesAttach.setMsatHotlink(hotLink);
					 * missSeriesAttach.setMsatPath(pathFolder);
					 * missSeriesAttach.setMsatFileName(s);
					 * missSeriesAttach.setRootPath(bundle.getString(module+"Path"));
					 * missExamService.updateMissSeriesAttach(missSeriesAttach); }
					 */
				}
				// return missCandidate;

			/*	missFile.setHotlink(hotLink);
				missFile.setFilename(s);
				missFile.setFilepath(pathFolder);*/
				Gson gson = new Gson();
				/*gson.toJson(missFile);*/
				// return hotLink;
				return gson.toJson(result);

			}
	private void createDirectoryIfNeeded(String directoryName) {
		File theDir = new File(directoryName);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			// boolean cancreate = theDir.mkdir();
			theDir.mkdir();
		}

	}

	private String genToken() {
		StringBuffer sb = new StringBuffer();
		for (int i = 36; i > 0; i -= 12) {
			int n = Math.min(12, Math.abs(i));
			sb.append(org.apache.commons.lang3.StringUtils.leftPad(Long
					.toString(Math.round(Math.random() * Math.pow(36, n)), 36),
					n, '0'));
		}
		return sb.toString();
	}
}
