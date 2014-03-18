package th.co.aoe.imake.thebluecode.backoffice.dto;

import java.io.Serializable;
import java.util.List;

public class MobileTemplate  implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<ReportTemplate> reportTemplates;
	private List<String> headers;
	private List<TemCodeGroup> temCodeGroups;
	public List<ReportTemplate> getReportTemplates() {
		return reportTemplates;
	}
	public void setReportTemplates(List<ReportTemplate> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}
	public List<String> getHeaders() {
		return headers;
	}
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	public List<TemCodeGroup> getTemCodeGroups() {
		return temCodeGroups;
	}
	public void setTemCodeGroups(List<TemCodeGroup> temCodeGroups) {
		this.temCodeGroups = temCodeGroups;
	}
	 

}
