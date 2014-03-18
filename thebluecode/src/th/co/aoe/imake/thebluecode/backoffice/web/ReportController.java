package th.co.aoe.imake.thebluecode.backoffice.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import th.co.aoe.imake.thebluecode.backoffice.domain.TemGroup;
import th.co.aoe.imake.thebluecode.backoffice.form.ReportForm;
import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;
import th.co.aoe.imake.thebluecode.backoffice.utils.IMakeDevUtils;

@Controller
@RequestMapping(value={"/report"}) 
@SessionAttributes(value={"reportForm"})
public class ReportController {
	 @Autowired
	 private TheBlueCodeService theBlueCodeService;
	 private static SimpleDateFormat format_billCycle = new SimpleDateFormat("dd_MM_yyyy");
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String eptNormReport(Model model)
	    {
		 ReportForm reportForm  =new ReportForm();
	            
		 String message="";
		 String message_class="";
		 reportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	        model.addAttribute("reportForm", reportForm);
	        model.addAttribute("message", message); 
	        model.addAttribute("message_class", message_class);
	        return "backoffice/template/report";
	    }
	 @RequestMapping(value = { "/listCompany" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
		public  @ResponseBody List listComapany(HttpServletRequest request, @ModelAttribute(value="reportForm") ReportForm reportForm, BindingResult result, Model model
				) {
		return  theBlueCodeService.getTemCompanyByGroup(reportForm.getTgName());
	 }
	 @RequestMapping(value = { "/listBillCycle" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
		public  @ResponseBody List listBillCycle(HttpServletRequest request, @ModelAttribute(value="reportForm") ReportForm reportForm, BindingResult result, Model model
				) {
		return  theBlueCodeService.getBillCycle(reportForm.getTcId());
	 }
	 @RequestMapping(value = { "/listProvider" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
		public  @ResponseBody List listProvider(HttpServletRequest request, @ModelAttribute(value="reportForm") ReportForm reportForm, BindingResult result, Model model
				) {
		Date billCycleDate= null;
    	try {
    		//System.out.println("reportForm.getBillCycle()->"+reportForm.getBillCycle());
    		if(!reportForm.getBillCycle().equals("-1"))
    			billCycleDate=format_billCycle.parse(reportForm.getBillCycle());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return  theBlueCodeService.listProvider(reportForm.getTcId(),billCycleDate);
	 }
	 @RequestMapping(value = { "/listmaster" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	 public  @ResponseBody List listmaster(Model model,HttpServletRequest request)
	    {
		 try{
		 String type=request.getParameter("type");
		 List result=null;
		 if(type!=null && type.equals("1")){
			 List<TemGroup> groups=theBlueCodeService.getGroup();
			 result=groups;
		 }
		 return result;
		 }catch(Exception ex){
			ex.printStackTrace(); 
		 }
		 return null;
	    }
}
