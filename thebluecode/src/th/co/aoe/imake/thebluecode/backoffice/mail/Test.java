package th.co.aoe.imake.thebluecode.backoffice.mail;

import java.util.ArrayList;
import java.util.List;

public class Test { 
	public static void main(String[] args) {
		/*public MailRunnable(String protocal_,String  host_,String email_ ,String  password_,String  useAuthen_,
				List recipients
				,String subject,String messagebody,String sessionId) {
			prop.put("mail.smtp.host", "SMTP-Relay.pttep.com");   
			prop.put("mail.smtp.port", "25");
			prop.put("protocol", "smtp");
		}*/
		//siripornp@
		List recipients =new ArrayList();
		recipients.add("bangorns@pttep.com");
		MailRunnable mailRunnable = new MailRunnable("smtp","SMTP-Relay.pttep.com","wpsadmin@pttep.com","password","0",
				recipients,"Send Mail","+_+","99","","",null,null,null,null);			
		Thread mailThread = new Thread(mailRunnable);
		mailThread.start(); 
	}
}
