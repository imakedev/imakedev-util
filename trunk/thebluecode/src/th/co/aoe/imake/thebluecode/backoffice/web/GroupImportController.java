package th.co.aoe.imake.thebluecode.backoffice.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import th.co.aoe.imake.thebluecode.backoffice.form.GroupImportForm;
import th.co.aoe.imake.thebluecode.backoffice.utils.IMakeDevUtils;

@Controller
@RequestMapping(value={"/group"})
@SessionAttributes(value={"groupImportForm"})
public class GroupImportController {
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String init(Model model)
	    {
		 GroupImportForm groupImportForm = null;
		 groupImportForm = new GroupImportForm();
		 String message="";
		 String message_class="";
		 groupImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	     model.addAttribute("groupImportForm", groupImportForm);
	     model.addAttribute("message", message); 
	     model.addAttribute("message_class", message_class);
	     return "backoffice/template/group_import";
	    }
	 	  
	  @RequestMapping(value={"/action/{section}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	    public String doAction(HttpServletRequest request, @PathVariable String section, @ModelAttribute(value="groupImportForm") GroupImportForm groupImportForm, BindingResult result, Model model)
	    {
	        String message = "";
	        String  message_class="";
	       groupImportForm = new GroupImportForm(); 
	       groupImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	       groupImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
		   model.addAttribute("groupImportForm", groupImportForm);
		   model.addAttribute("message", message); 
		   model.addAttribute("message_class", message_class);
		   return "backoffice/template/group_import";
	    }
	  

}
