package th.co.aoe.imake.thebluecode.backoffice.service;

import java.util.Date;
import java.util.List;

import th.co.aoe.imake.thebluecode.backoffice.domain.TemCallDetailRecord;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemCompany;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemGroup;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemMsIsdn;
import th.co.aoe.imake.thebluecode.backoffice.dto.MobileTemplate;
import th.co.aoe.imake.thebluecode.backoffice.dto.ReportTemplate;
import th.co.imake.tem.migratedata.form.CDRTemplate;
import th.co.imake.tem.migratedata.form.GroupTemplate;

public interface TheBlueCodeService {
	public List <TemCallDetailRecord> getTemCallDetailRecord();
	
	public int[] importCDR(List<CDRTemplate> temCallDetailRecords);
	public int[] importGroup(List<GroupTemplate> temGroups);
	
	public List <TemGroup> getGroup();
	public List <TemCompany> getTemCompanyByGroup(String tgName);
	public List <TemMsIsdn> getTemMsIsdnByCompany(Integer tcId);
	
	public List<String[]> getBillCycle(Integer tcId);
	public List<String[]> listProvider(Integer tcId,Date billCycle);
	
	public List<ReportTemplate> listReportTemplates(Integer tcId,Date billCycle,Integer provider);
	public MobileTemplate listMobileReportTemplates(Integer tcId,Date billCycle,Integer provider);
}
