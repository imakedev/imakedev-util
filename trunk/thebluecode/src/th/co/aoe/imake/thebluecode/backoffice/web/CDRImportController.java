package th.co.aoe.imake.thebluecode.backoffice.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import th.co.aoe.imake.thebluecode.backoffice.form.CDRImportForm;
import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;
import th.co.aoe.imake.thebluecode.backoffice.utils.IMakeDevUtils;

@Controller
@RequestMapping(value={"/cdr"})
@SessionAttributes(value={"cdrImportForm"})
public class CDRImportController {

	 @Autowired
	 private TheBlueCodeService theBlueCodeService;
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String init(Model model)
	    {
		// theBlueCodeService.getTemCallDetailRecord();
		 CDRImportForm cdrImportForm = null;
		 cdrImportForm = new CDRImportForm();
	    
		 String message="";
		 String message_class="";
		 cdrImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	     model.addAttribute("cdrImportForm", cdrImportForm);
	     model.addAttribute("message", message); 
	     model.addAttribute("message_class", message_class);
	     return "backoffice/template/cdr_import";
	    }
	 	  
	  @RequestMapping(value={"/action/{section}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	    public String doAction(HttpServletRequest request, @PathVariable String section, @ModelAttribute(value="cdrImportForm") CDRImportForm cdrImportForm, BindingResult result, Model model)
	    {
	        String message = "";
	        String  message_class="";
	     
	       cdrImportForm = new CDRImportForm(); 
	       cdrImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	       cdrImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
		   model.addAttribute("cdrImportForm", cdrImportForm);
		   model.addAttribute("message", message); 
		   model.addAttribute("message_class", message_class);
		   return "backoffice/template/cdr_import";
	    }
}
