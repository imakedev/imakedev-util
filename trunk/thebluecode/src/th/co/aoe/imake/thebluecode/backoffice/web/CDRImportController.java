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
import th.co.aoe.imake.thebluecode.backoffice.form.CDRImportForm;
import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;
import th.co.aoe.imake.thebluecode.backoffice.utils.IMakeDevUtils;

@Controller
@RequestMapping(value={"/cdr"})
@SessionAttributes(value={"cdrImportForm"})
public class CDRImportController {

	 @Autowired
	 private TheBlueCodeService theBlueCodeService;
	 
	/* public CDRImportController(TheBlueCodeService theBlueCodeService) {
		super();
		this.theBlueCodeService = theBlueCodeService;
	}*/

	private static final Logger logger = LoggerFactory.getLogger(ServiceConstant.LOG_APPENDER);
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String init(Model model)
	    {
		 theBlueCodeService.getTemCallDetailRecord();
		 CDRImportForm cdrImportForm = null;
	      /*  if(model.containsAttribute("cdrImportForm"))
	        	cdrImportForm = (CDRImportForm)model.asMap().get("cdrImportForm");
	        else*/
		 cdrImportForm = new CDRImportForm();
	        
	        	/*cdrImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	        	cdrImportForm.getPstBreakDown().setPagging(cdrImportForm.getPaging());
	        VResultMessage vresultMessage = pstService.searchPstBreakDown(cdrImportForm.getPstBreakDown());*/
	        /*logger.debug("vresultMessage="+vresultMessage);
	        logger.debug("getResultListObj="+vresultMessage.getResultListObj());
	        model.addAttribute("pstBreakDowns", vresultMessage.getResultListObj());
*/	     
		 String message="";
		 String message_class="";
		/* if(false){
			 message = "Update success !";
			 message_class="success";
		 }*/
		    cdrImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	      //  cdrImportForm.setPageCount(IMakeDevUtils.calculatePage(cdrImportForm.getPaging().getPageSize(), Integer.parseInt(vresultMessage.getMaxRow())));
	        model.addAttribute("cdrImportForm", cdrImportForm);
	        model.addAttribute("message", message); 
	        model.addAttribute("message_class", message_class);
	        return "backoffice/template/cdr_import";
	    }
	 	  
	  @RequestMapping(value={"/action/{section}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	    public String doAction(HttpServletRequest request, @PathVariable String section, @ModelAttribute(value="cdrImportForm") CDRImportForm cdrImportForm, BindingResult result, Model model)
	    {
	        String mode = cdrImportForm.getMode();
	        String message = "";
	        String  message_class="";
	        Long id = null;
	      /* if(mode != null)
	            if(mode.equals(IMakeDevUtils.MODE_NEW))
	            {
	                id = pstService.savePstBreakDown(cdrImportForm.getPstBreakDown());
	                cdrImportForm.getPstBreakDown().setPbdId(id);
	                cdrImportForm.setMode(IMakeDevUtils.MODE_EDIT);
	                message = "Save success !";
	                message_class="success";
	            } else
	            if(mode.equals(IMakeDevUtils.MODE_EDIT))
	            {
	            	pstService.updatePstBreakDown(cdrImportForm.getPstBreakDown());
	                id = cdrImportForm.getPstBreakDown().getPbdId();
	                message = "Update success !";
	                message_class="success";
	            }*/
	       
	       cdrImportForm = new CDRImportForm(); 
	       cdrImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	     //  cdrImportForm.getPstBreakDown().setPagging(cdrImportForm.getPaging());
		   //     VResultMessage vresultMessage = pstService.searchPstBreakDown(cdrImportForm.getPstBreakDown());
		    /*    logger.debug("vresultMessage="+vresultMessage);
		        logger.debug("getResultListObj="+vresultMessage.getResultListObj());
		        model.addAttribute("pstBreakDowns", vresultMessage.getResultListObj());*/
		        cdrImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
		     //   cdrImportForm.setPageCount(IMakeDevUtils.calculatePage(cdrImportForm.getPaging().getPageSize(), Integer.parseInt(vresultMessage.getMaxRow())));
		        model.addAttribute("cdrImportForm", cdrImportForm);
		        model.addAttribute("message", message); 
		        model.addAttribute("message_class", message_class);
		        return "backoffice/template/cdr_import";
	        // return "backoffice/template/break_down_management";
	    }

	/*public TheBlueCodeService getTheBlueCodeService() {
		return theBlueCodeService;
	}

	public void setTheBlueCodeService(TheBlueCodeService theBlueCodeService) {
		this.theBlueCodeService = theBlueCodeService;
	}*/
	  
}
