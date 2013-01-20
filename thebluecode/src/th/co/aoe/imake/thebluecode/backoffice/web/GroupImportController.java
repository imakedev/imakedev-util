package th.co.aoe.imake.thebluecode.backoffice.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import th.co.aoe.imake.pst.constant.ServiceConstant;
import th.co.aoe.imake.thebluecode.backoffice.form.GroupImportForm;
import th.co.aoe.imake.thebluecode.backoffice.utils.IMakeDevUtils;

@Controller
@RequestMapping(value={"/group"})
@SessionAttributes(value={"groupImportForm"})
public class GroupImportController {


	/* @Autowired
	 private PSTService pstService;*/
	 private static final Logger logger = LoggerFactory.getLogger(ServiceConstant.LOG_APPENDER);
	 @RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String init(Model model)
	    {
		 GroupImportForm groupImportForm = null;
	      /*  if(model.containsAttribute("groupImportForm"))
	        	groupImportForm = (GroupImportForm)model.asMap().get("groupImportForm");
	        else*/
		 groupImportForm = new GroupImportForm();
	        
	        	/*groupImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	        	groupImportForm.getPstBreakDown().setPagging(groupImportForm.getPaging());
	        VResultMessage vresultMessage = pstService.searchPstBreakDown(groupImportForm.getPstBreakDown());*/
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
		 groupImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	      //  groupImportForm.setPageCount(IMakeDevUtils.calculatePage(groupImportForm.getPaging().getPageSize(), Integer.parseInt(vresultMessage.getMaxRow())));
	        model.addAttribute("groupImportForm", groupImportForm);
	        model.addAttribute("message", message); 
	        model.addAttribute("message_class", message_class);
	        return "backoffice/template/group_import";
	    }
	 	  
	  @RequestMapping(value={"/action/{section}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	    public String doAction(HttpServletRequest request, @PathVariable String section, @ModelAttribute(value="groupImportForm") GroupImportForm groupImportForm, BindingResult result, Model model)
	    {
	        String mode = groupImportForm.getMode();
	        String message = "";
	        String  message_class="";
	        Long id = null;
	      /* if(mode != null)
	            if(mode.equals(IMakeDevUtils.MODE_NEW))
	            {
	                id = pstService.savePstBreakDown(groupImportForm.getPstBreakDown());
	                groupImportForm.getPstBreakDown().setPbdId(id);
	                groupImportForm.setMode(IMakeDevUtils.MODE_EDIT);
	                message = "Save success !";
	                message_class="success";
	            } else
	            if(mode.equals(IMakeDevUtils.MODE_EDIT))
	            {
	            	pstService.updatePstBreakDown(groupgroupgroupImportForm.getPstBreakDown());
	                id = groupImportForm.getPstBreakDown().getPbdId();
	                message = "Update success !";
	                message_class="success";
	            }*/
	       
	       groupImportForm = new GroupImportForm(); 
	       groupImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
	     //  groupImportForm.getPstBreakDown().setPagging(groupImportForm.getPaging());
		   //     VResultMessage vresultMessage = pstService.searchPstBreakDown(groupImportForm.getPstBreakDown());
		    /*    logger.debug("vresultMessage="+vresultMessage);
		        logger.debug("getResultListObj="+vresultMessage.getResultListObj());
		        model.addAttribute("pstBreakDowns", vresultMessage.getResultListObj());*/
	       groupImportForm.getPaging().setPageSize(IMakeDevUtils.PAGE_SIZE);
		     //   groupImportForm.setPageCount(IMakeDevUtils.calculatePage(groupImportForm.getPaging().getPageSize(), Integer.parseInt(vresultMessage.getMaxRow())));
		        model.addAttribute("groupImportForm", groupImportForm);
		        model.addAttribute("message", message); 
		        model.addAttribute("message_class", message_class);
		        return "backoffice/template/group_import";
	        // return "backoffice/template/break_down_management";
	    }
	  

}
