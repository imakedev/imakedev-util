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

import th.co.aoe.imake.thebluecode.backoffice.form.PackageImportForm;
import th.co.aoe.imake.thebluecode.backoffice.service.PSTService;
import th.co.aoe.imake.thebluecode.backoffice.utils.IMakeDevUtils;
@Controller
@RequestMapping(value={"/package"})
@SessionAttributes(value={"packageImportForm"})
public class PackageImportController {
	 @Autowired
	 private PSTService pstService;
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String init(Model model)
	    {
		 PackageImportForm packageImportForm = null;
		 packageImportForm = new PackageImportForm();
		 packageImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	     model.addAttribute("packageImportForm", packageImportForm);
	     model.addAttribute("message", ""); 
	     return "backoffice/template/package_import";
	    }
	  @RequestMapping(value={"/action/{section}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	    public String doAction(HttpServletRequest request, @PathVariable String section, @ModelAttribute(value="packageImportForm") PackageImportForm packageImportForm, BindingResult result, Model model)
	    {
	        String message = "";
	        String  message_class="";
	        
	        packageImportForm = new PackageImportForm(); 
	        packageImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	        packageImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
		    model.addAttribute("packageImportForm", packageImportForm);
		    model.addAttribute("message", message); 
		    model.addAttribute("message_class", message_class);
		    return "backoffice/template/package_import";
	    }
	  

}
