package th.co.aoe.imake.thebluecode.backoffice.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/maintenance"})
public class MaintenanceController {
	@RequestMapping(value={"/init"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	 public String init(Model model)
	    {
	       // return "backoffice/template/maintenance_check_search";
		  return "backoffice/template/empty";
	    }
}
