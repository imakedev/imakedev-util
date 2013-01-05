package th.co.aoe.imake.thebluecode.backoffice.web;

import javax.servlet.http.HttpServletRequest;

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

import th.co.aoe.imake.pst.constant.ServiceConstant;
import th.co.aoe.imake.thebluecode.backoffice.form.PackageImportForm;
import th.co.aoe.imake.thebluecode.backoffice.service.PSTService;
import th.co.aoe.imake.thebluecode.backoffice.utils.IMakeDevUtils;
@Controller
@RequestMapping(value={"/package"})
@SessionAttributes(value={"packageImportForm"})
public class PackageImportController {


	 @Autowired
	 private PSTService pstService;
	 private static final Logger logger = LoggerFactory.getLogger(ServiceConstant.LOG_APPENDER);
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String init(Model model)
	    {
		 PackageImportForm packageImportForm = null;
	      /*  if(model.containsAttribute("packageImportForm"))
	        	packageImportForm = (PackageImportForm)model.asMap().get("packageImportForm");
	        else*/
		 packageImportForm = new PackageImportForm();
	        
	        	/*packageImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	        	packageImportForm.getPstBreakDown().setPagging(packageImportForm.getPaging());
	        VResultMessage vresultMessage = pstService.searchPstBreakDown(packageImportForm.getPstBreakDown());*/
	        /*logger.debug("vresultMessage="+vresultMessage);
	        logger.debug("getResultListObj="+vresultMessage.getResultListObj());
	        model.addAttribute("pstBreakDowns", vresultMessage.getResultListObj());
*/	       
		 packageImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	      //  packageImportForm.setPageCount(IMakeDevUtils.calculatePage(packageImportForm.getPaging().getPageSize(), Integer.parseInt(vresultMessage.getMaxRow())));
	        model.addAttribute("packageImportForm", packageImportForm);
	        model.addAttribute("message", ""); 
	        return "backoffice/template/package_import";
	    }
	 	  
	  @RequestMapping(value={"/action/{section}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	    public String doAction(HttpServletRequest request, @PathVariable String section, @ModelAttribute(value="packageImportForm") PackageImportForm packageImportForm, BindingResult result, Model model)
	    {
	        String mode = packageImportForm.getMode();
	        String message = "";
	        String  message_class="";
	        Long id = null;
	      /* if(mode != null)
	            if(mode.equals(IMakeDevUtils.MODE_NEW))
	            {
	                id = pstService.savePstBreakDown(packageImportForm.getPstBreakDown());
	                packageImportForm.getPstBreakDown().setPbdId(id);
	                packageImportForm.setMode(IMakeDevUtils.MODE_EDIT);
	                message = "Save success !";
	                message_class="success";
	            } else
	            if(mode.equals(IMakeDevUtils.MODE_EDIT))
	            {
	            	pstService.updatePstBreakDown(packageImportForm.getPstBreakDown());
	                id = packageImportForm.getPstBreakDown().getPbdId();
	                message = "Update success !";
	                message_class="success";
	            }*/
	        
	        packageImportForm = new PackageImportForm(); 
	        packageImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	     //  packageImportForm.getPstBreakDown().setPagging(packageImportForm.getPaging());
		   //     VResultMessage vresultMessage = pstService.searchPstBreakDown(packageImportForm.getPstBreakDown());
		    /*    logger.debug("vresultMessage="+vresultMessage);
		        logger.debug("getResultListObj="+vresultMessage.getResultListObj());
		        model.addAttribute("pstBreakDowns", vresultMessage.getResultListObj());*/
	        packageImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
		     //   packageImportForm.setPageCount(IMakeDevUtils.calculatePage(packageImportForm.getPaging().getPageSize(), Integer.parseInt(vresultMessage.getMaxRow())));
		        model.addAttribute("packageImportForm", packageImportForm);
		        model.addAttribute("message", message); 
		        model.addAttribute("message_class", message_class);
		        return "backoffice/template/package_import";
	        // return "backoffice/template/break_down_management";
	    }
	  

}
