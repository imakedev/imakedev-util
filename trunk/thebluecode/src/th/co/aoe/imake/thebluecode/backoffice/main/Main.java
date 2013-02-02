package th.co.aoe.imake.thebluecode.backoffice.main;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;
import th.co.imake.tem.migratedata.form.CDRTemplate;

public class Main {
	public static DateFormat dateFormatTrue = new SimpleDateFormat("dd/MM/yy HHmmss", new Locale("th", "TH"));
	public static void main(String[] args) throws Exception {
		final ApplicationContext springContext = new ClassPathXmlApplicationContext(
				new String[] {
						"th/co/aoe/imake/thebluecode/backoffice/main/spring-data.xml" });

		//((Component) springContext.getBean("top")).start();
		TheBlueCodeService temService = (TheBlueCodeService)springContext.getBean("theBlueCodeService");
		List list=temService.listReportTemplates(3, new Date());
		System.out.println(list);
		// *and t1.tc_group_name='กลุ่ม C' and  t1.tc_id=3  and tcdr.tcdrMsIsdnFrom='057777777'
		 //  and t1.tp_name='AIS'*/
		/*List list=temService.listReportTemplates("กลุ่ม C", 3, "057777777");
		for (int i = 0; i < args.length; i++) {
			
		}
		TemGroup temGroup =new TemGroup();
		temGroup.setTgName("As");
		TemGroup temGroup2 =new TemGroup();
		temGroup2.setTgName("AX");
		TemGroup temGroup3 =new TemGroup();
		temGroup3.setTgName("A");
		List<TemGroup> temGroups =new ArrayList<TemGroup>(); 
		temGroups.add(temGroup);
		temGroups.add(temGroup2);
		temGroups.add(temGroup3);
		List<GroupTemplate> groupTemplates =new ArrayList<GroupTemplate>(); 
		GroupTemplate t1=new GroupTemplate();
		t1.setGroup("กลุ่ม A");
		t1.setCompany("บริษัท 11");
		t1.setNo("87888888");
		t1.setPackageType("A");
		groupTemplates.add(t1);
			บริษัท 1	87888888	A
		กลุ่ม A	บริษัท 1	21111111	B
		กลุ่ม A	บริษัท 2	21111112	B
		กลุ่ม A	บริษัท 2	87888889	D

		temService.importGroup(groupTemplates);
		System.out.println();*/
		/*CDRTemplate cdrTemplate =new  CDRTemplate();
		cdrTemplate.setMsIsdnFrom("0848810484");
		cdrTemplate.setMsIsdnTo("0846926751");
		cdrTemplate.setMsIsdnToLocation("Mobile in BKK. Area");
		cdrTemplate.setMsIsdnFromProvider("TRUE");
		//19/10/55 185012 Mobile in BKK. Area
		Date usedDate=null;
		try {
			usedDate = dateFormatTrue.parse("19/10/55"+" "+"185012");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cdrTemplate.setUsedDate(usedDate);
		Time usedTime = new Time(usedDate.getTime());
		cdrTemplate.setUsedTime(usedTime); 
		
		cdrTemplate.setPrice(9d);
		cdrTemplate.setUsedCount(180d);*/
	/*	System.out.println(" "+cdrTemplate.getMsIsdnFrom()+" to "+cdrTemplate.getMsIsdnTo()
				+"  date "+cdrTemplate.getUsedDate()+" time "+cdrTemplate.getUsedTime()+" price "+cdrTemplate.getPrice()+" usedCount "+cdrTemplate.getUsedCount());*/
	/*	List<CDRTemplate> cdrTemplates =new ArrayList<CDRTemplate>(1);
		cdrTemplates.add(cdrTemplate);
		temService.importCDR(cdrTemplates,null);*/
//		saveTemProvider(temService);
//		searchTemProvider(temService);
//		
//		saveTemType(temService);
//		searchTemType(temService);
//		
//		saveTemCompany(temService);
//		searchTemCompany(temService);
//		
//		saveTemMsIsdn(temService);
//		searchTemMsIsdn(temService);
		
//		saveTemSpecialList(temService);
//		searchTemSpecialList(temService);
		
//		saveTemCallDetailRecord(temService);
//		searchTemCallDetailRecord(temService);
//		MigrateData migrateData = new MigrateData();
//		migrateData.migrateData(temService);
		
	}
	
	/*public static void saveTemProvider(TemService temService) {
		TemProvider temProvider = new TemProvider();
		temProvider.setTpName("True");
		TemProvider temProvider2 = new TemProvider();
		temProvider2.setTpName("Dtac");
		TemProvider temProvider3 = new TemProvider();
		temProvider3.setTpName("AIS");
		temService.insertTemProvider(temProvider);
		temService.insertTemProvider(temProvider2);
		temService.insertTemProvider(temProvider3);
	}
	
	public static void searchTemProvider(TemService temService) {
		TemProvider temProvider = new TemProvider();
		Paging paging = new Paging();
		List list = temService.searchTemProvider(temProvider, paging);
		if(list != null && list.size() == 2) {
			List listObj = (List)list.get(0);
			for(int i=0;i<listObj.size();i++) {
				temProvider = (TemProvider)listObj.get(i);
				System.out.println(temProvider.getTpId()+" : "+temProvider.getTpName());
			}
		}
	}
	
	public static void saveTemType(TemService temService) {
		TemType temType = new TemType();
		temType.setTtName("Call");
		TemType temType2 = new TemType();
		temType2.setTtName("SMS");
		TemType temType3 = new TemType();
		temType3.setTtName("Data");
		temService.insertTemType(temType);
		temService.insertTemType(temType2);
		temService.insertTemType(temType3);
	}
	
	public static void searchTemType(TemService temService) {
		TemType temType = new TemType();
		Paging paging = new Paging();
		List list = temService.searchTemType(temType, paging);
		if(list != null && list.size() == 2) {
			List listObj = (List)list.get(0);
			for(int i=0;i<listObj.size();i++) {
				temType = (TemType)listObj.get(i);
				System.out.println(temType.getTtId()+" : "+temType.getTtName());
			}
		}
	}
	
	public static void saveTemCompany(TemService temService) {
		TemCompany temCompany = new TemCompany();
		temCompany.setTcName("VLink");
		temService.insertTemCompany(temCompany);
	}
	
	public static void searchTemCompany(TemService temService) {
		TemCompany temCompany = new TemCompany();
		Paging paging = new Paging();
		List list = temService.searchTemCompany(temCompany, paging);
		if(list != null && list.size() == 2) {
			List listObj = (List)list.get(0);
			for(int i=0;i<listObj.size();i++) {
				temCompany = (TemCompany)listObj.get(i);
				System.out.println(temCompany.getTcId()+" : "+temCompany.getTcName());
			}
		}
	}
	
	public static void saveTemMsIsdn(TemService temService) {
		TemMsIsdn temMsIsdn = new TemMsIsdn();
		TemCompany temCompany = new TemCompany();
		temCompany.setTcId(1);
		temMsIsdn.setTemCompany(temCompany);
		temMsIsdn.setMsIsdn("027197999");
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		temMsIsdn.setOnTheBill(new java.sql.Date(calendar.getTime().getTime()));
		temService.insertTemMsIsdn(temMsIsdn);
	}
	
	public static void searchTemMsIsdn(TemService temService) {
		TemMsIsdn temMsIsdn = new TemMsIsdn();
		Paging paging = new Paging();
		List list = temService.searchTemMsIsdn(temMsIsdn, paging);
		if(list != null && list.size() == 2) {
			List listObj = (List)list.get(0);
			for(int i=0;i<listObj.size();i++) {
				temMsIsdn = (TemMsIsdn)listObj.get(i);
				System.out.println(temMsIsdn.getMsIsdn()+" : "+temMsIsdn.getOnTheBill());
			}
		}
	}
	
	public static void saveTemSpecialList(TemService temService) {
		TemSpecialList temSpecialList = new TemSpecialList();
		TemSpecialListPk temSpecialListPk = new TemSpecialListPk();
		temSpecialListPk.setTslMsisdn("0800000002");
		temSpecialListPk.setTslMsisdnFriend("0869999999");
		temSpecialList.setTemSpecialListPk(temSpecialListPk);
		temService.insertTemSpecialList(temSpecialList);
	}
	
	public static void searchTemSpecialList(TemService temService) {
		TemSpecialList temSpecialList = new TemSpecialList();
		Paging paging = new Paging();
		List list = temService.searchTemSpecialList(temSpecialList, paging);
		if(list != null && list.size() == 2) {
			List listObj = (List)list.get(0);
			for(int i=0;i<listObj.size();i++) {
				temSpecialList = (TemSpecialList)listObj.get(i);
				TemSpecialListPk temSpecialListPk = temSpecialList.getTemSpecialListPk();
				System.out.println(temSpecialListPk.getTslMsisdn()+" : "+temSpecialListPk.getTslMsisdnFriend());
			}
		}
	}
	
	public static void saveTemCallDetailRecord(TemService temService) {
		TemMsIsdn msIsdnFrom = new TemMsIsdn();
		msIsdnFrom.setMsIsdn("0812138998");
		TemMsIsdn msIsdnTo = new TemMsIsdn();
		msIsdnTo.setMsIsdn("027197999");
		TemCallDetailRecord temCallDetailRecord = new TemCallDetailRecord();
		TemCallDetailRecordPk temCallDetailRecordPk = new TemCallDetailRecordPk();
		temCallDetailRecordPk.setTcdrMsIsdnFrom("0812138998");
		temCallDetailRecordPk.setTcdrUsedTime(new Timestamp(new Date().getTime()));
		temCallDetailRecordPk.setTtId(1);
		temCallDetailRecord.setTcdrMsIsdnTo(msIsdnTo);
		temCallDetailRecord.setTcdrUsedCount(1.0);
		temCallDetailRecord.setTemCallDetailRecordPk(temCallDetailRecordPk);
		temService.insertTemCallDetailRecord(temCallDetailRecord);
	}
	
	public static void searchTemCallDetailRecord(TemService temService) {
		TemCallDetailRecord temCallDetailRecord = new TemCallDetailRecord();
		Paging paging = new Paging();
		List list = temService.searchTemCallDetailRecord(temCallDetailRecord, paging);
		if(list != null && list.size() == 2) {
			List listObj = (List)list.get(0);
			System.out.println(listObj.size());
			for(int i=0;i<listObj.size();i++) {
				temCallDetailRecord = (TemCallDetailRecord)listObj.get(i);
				TemCallDetailRecordPk temCallDetailRecordPk = temCallDetailRecord.getTemCallDetailRecordPk();
				System.out.println(temCallDetailRecordPk.getTcdrMsIsdnFrom()+" : "+temCallDetailRecord.getTcdrMsIsdnTo().getMsIsdn()+" : "+temCallDetailRecordPk.getTcdrUsedTime());
			}
		}
	}
	
	public static void saveTemMsIsdnPackageDetail(TemService temService) {
		TemMsIsdnPackageDetail temMsIsdnPackageDetail = new TemMsIsdnPackageDetail();
		TemMsIsdnPackageDetailPk temMsIsdnPackageDetailPk = new TemMsIsdnPackageDetailPk();
		temMsIsdnPackageDetailPk.setMsIsdn("0800000002");
		temMsIsdnPackageDetailPk.setTpdId(1);
		temMsIsdnPackageDetail.setTemMsIsdnPackageDetailPk(temMsIsdnPackageDetailPk);
		temService.insertTemMsIsdnPackageDetail(temMsIsdnPackageDetail);
	}
	
	public static void searchTemMsIsdnPackageDetail(TemService temService) {
		TemMsIsdnPackageDetail temMsIsdnPackageDetail = new TemMsIsdnPackageDetail();
		Paging paging = new Paging();
		List list = temService.searchTemMsIsdnPackageDetail(temMsIsdnPackageDetail, paging);
		if(list != null && list.size() == 2) {
			List listObj = (List)list.get(0);
			for(int i=0;i<listObj.size();i++) {
				temMsIsdnPackageDetail = (TemMsIsdnPackageDetail)listObj.get(i);
				TemMsIsdnPackageDetailPk temMsIsdnPackageDetailPk = temMsIsdnPackageDetail.getTemMsIsdnPackageDetailPk();
				System.out.println(temMsIsdnPackageDetailPk.getMsIsdn()+" : "+temMsIsdnPackageDetailPk.getTpdId());
			}
		}
	}*/
}
