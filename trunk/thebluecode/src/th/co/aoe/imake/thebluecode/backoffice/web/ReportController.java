package th.co.aoe.imake.thebluecode.backoffice.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import th.co.aoe.imake.thebluecode.backoffice.form.ReportForm;
import th.co.aoe.imake.thebluecode.backoffice.utils.IMakeDevUtils;

@Controller
@RequestMapping(value={"/report"}) 
@SessionAttributes(value={"reportForm"})
public class ReportController {
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String eptNormReport(Model model)
	    {
		 ReportForm reportForm  =new ReportForm();
	            
		 String message="";
		 String message_class="";
		/* if(false){
			 message = "Update success !";
			 message_class="success";
		 }*/
		 reportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	      //  reportForm.setPageCount(IMakeDevUtils.calculatePage(reportForm.getPaging().getPageSize(), Integer.parseInt(vresultMessage.getMaxRow())));
	        model.addAttribute("reportForm", reportForm);
	        model.addAttribute("message", message); 
	        model.addAttribute("message_class", message_class);
	       
	        return "backoffice/template/report";
	    }
}
