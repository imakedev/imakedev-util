package th.co.aoe.imake.thebluecode.backoffice.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import th.co.aoe.imake.thebluecode.backoffice.domain.TemCallDetailRecord;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemCallDetailRecordPk;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemCompany;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemGroup;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemMsIsdn;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemProvider;
import th.co.aoe.imake.thebluecode.backoffice.dto.MobileTemplate;
import th.co.aoe.imake.thebluecode.backoffice.dto.ReportTemplate;
import th.co.aoe.imake.thebluecode.backoffice.dto.TemCodeGroup;
import th.co.aoe.imake.thebluecode.backoffice.dto.TemCodeMapping;
import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;
import th.co.imake.tem.migratedata.form.CDRTemplate;
import th.co.imake.tem.migratedata.form.GroupTemplate;
@Service
@Transactional
public class TheBlueCodeServiceImpl implements TheBlueCodeService { 
	//private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	//private static final DateFormat format_normal = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final DateFormat dFormat_to = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
	private static final DateFormat dFormat_end= new SimpleDateFormat("dd/MM/yyyy 23:59:59");
	private static final DateFormat query_d_Format_to = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	private static final DateFormat query_d_Format_end= new SimpleDateFormat("yyyy-MM-dd 23:59:59");
	private static final DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//	@Autowired
	@PersistenceUnit(unitName = "hibernatePersistenceUnit") 
    private EntityManagerFactory entityManager;
	 
	  
	@Override
	@Transactional(readOnly=true)
	//@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor={RuntimeException.class})
	public List<TemCallDetailRecord> getTemCallDetailRecord() {
		 EntityManager em = entityManager.createEntityManager();
		
		// System.out.println("em="+em);
		 EntityTransaction tx=em.getTransaction();
		// System.out.println("tx="+em);
		 tx.begin();
		    try {
		    	Query query =em.createQuery("select temCallDetailRecord from TemCallDetailRecord temCallDetailRecord");
		    	List list=query.getResultList();
		    //	System.out.println(list.size());
		    // do some work
		    	tx.commit();
		}
		catch (RuntimeException e) {
		    if (tx != null) tx.rollback();
		    throw e; // or display error message
		}
		finally {
		    em.close();
		}
		// TODO Auto-generated method stub 
		return null; 
		
		//em.persist(employee);
		// em.remove(employee);
		/*update
		 * Employee employee = em.find(Employee.class, 1);
 
  em.getTransaction().begin();
  employee.setNickname("Joe the Plumber");
  em.getTransaction().commit();
		 * 
		 */
		/*
		 * em.getTransaction().begin();
  for (int i = 1; i <= 1000000; i++) {
      Point point = new Point(i, i);
      em.persist(point);
      if ((i % 10000) == 0) {
          em.getTransaction().commit();
          em.clear();          
          em.getTransaction().begin();
      }
  }
  em.getTransaction().commit();
		 */
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor={RuntimeException.class})
	public int[] importCDR(List<CDRTemplate> temCallDetailRecords) {
		// TODO Auto-generated method stub
		 EntityManager em = entityManager.createEntityManager(); 
		 EntityTransaction tx=em.getTransaction();
		 int[] results=new int[2];
		 int i=0;
		 int newRecord=0;
		 int updateRecord=0;
		 TemProvider provider_true=new TemProvider(1,"TRUE");
		 TemProvider provider_dtac=new TemProvider(2,"DTAC");
		 TemProvider provider_ais=new TemProvider(3,"AIS");
		 TemProvider provider_tot=new TemProvider(5,"TOT");
		 TemProvider provider_t_t=new TemProvider(7,"TT&T");
		 TemProvider provider_truemove=new TemProvider(4,"TRUEMOVE");
		 
		 
	 try{
		 tx.begin();
		 	for (CDRTemplate cdrTemplate : temCallDetailRecords) {
		 		
				/*System.out.println(cdrTemplate.getMsIsdnFrom() + "\t"
						+ cdrTemplate.getMsIsdnTo());*/
				if (cdrTemplate.getMsIsdnFrom() != null
						&& cdrTemplate.getMsIsdnFrom().trim().length() > 0) {
					Query query =em.createQuery("select temMsIsdn from TemMsIsdn temMsIsdn where temMsIsdn.msIsdn=:msIsdn " +
			 				"");
			 		query.setParameter("msIsdn", cdrTemplate.getMsIsdnFrom()); 
			    	List obj=query.getResultList(); 
			    	TemMsIsdn temMsIsdn =null;
			    	//System.out.println(obj.size());
			    	if(obj.size()==0){
			    		 temMsIsdn = new TemMsIsdn(); 
			    	}else{
			    		 temMsIsdn =(TemMsIsdn)obj.get(0);
			    		// temMsIsdn.setMsIsdn(cdrTemplate.getMsIsdnFrom());
			    	}  
			    	 temMsIsdn.setMsIsdn(cdrTemplate.getMsIsdnFrom());
					String msIsdnFromProvider=cdrTemplate.getMsIsdnFromProvider();
					//System.out.println("msIsdnFromProvider="+msIsdnFromProvider);
					if(msIsdnFromProvider.equalsIgnoreCase("TRUE")){
						temMsIsdn.setTemProvider(provider_true);
					}else if(msIsdnFromProvider.equalsIgnoreCase("TOT")){
						temMsIsdn.setTemProvider(provider_tot);
					}else if(msIsdnFromProvider.equalsIgnoreCase("DTAC")){
						temMsIsdn.setTemProvider(provider_dtac);
					}else if(msIsdnFromProvider.equalsIgnoreCase("AIS")){
						temMsIsdn.setTemProvider(provider_ais);
					}else if(msIsdnFromProvider.equalsIgnoreCase("TT&T")){
						temMsIsdn.setTemProvider(provider_t_t);
					}else if(msIsdnFromProvider.equalsIgnoreCase("TRUEMOVE")){
						temMsIsdn.setTemProvider(provider_truemove);
					}
						
					em.merge(temMsIsdn); 
				}

				if (cdrTemplate.getMsIsdnTo() != null
						&& cdrTemplate.getMsIsdnTo().trim().length() > 0) { 

					Query query =em.createQuery("select temMsIsdn from TemMsIsdn temMsIsdn where temMsIsdn.msIsdn=:msIsdn " +
			 				"");
			 		query.setParameter("msIsdn", cdrTemplate.getMsIsdnTo()); 
			    	List obj=query.getResultList(); 
			    	TemMsIsdn temMsIsdn =null;
			    	//System.out.println("obj.size() 2  ="+obj.size());
			    	if(obj.size()==0){
			    		 temMsIsdn = new TemMsIsdn();  
			    	}else{
			    		 temMsIsdn =(TemMsIsdn)obj.get(0);
			    		
			    	}  
			    	 temMsIsdn.setMsIsdn(cdrTemplate.getMsIsdnTo());
					/*String msIsdnFromProvider=cdrTemplate.getMsIsdnFromProvider();
				 
					if(msIsdnFromProvider.equalsIgnoreCase("TRUE")){
						temMsIsdn.setTemProvider(provider_true);
					}else if(msIsdnFromProvider.equalsIgnoreCase("TOT")){
						temMsIsdn.setTemProvider(provider_tot);
					}else if(msIsdnFromProvider.equalsIgnoreCase("DTAC")){
						temMsIsdn.setTemProvider(provider_dtac);
					}else if(msIsdnFromProvider.equalsIgnoreCase("AIS")){
						temMsIsdn.setTemProvider(provider_ais);
					}*/
					em.merge(temMsIsdn); 
				
				}

				TemCallDetailRecord temCallDetailRecord = new TemCallDetailRecord();
				
				temCallDetailRecord.setTcdrUsedCount(cdrTemplate.getUsedCount());
				TemCallDetailRecordPk temCallDetailRecordPk = new TemCallDetailRecordPk();
				temCallDetailRecordPk
						.setTcdrMsIsdnFrom(cdrTemplate.getMsIsdnFrom());
				Date date = cdrTemplate.getUsedDate();
				Time time = cdrTemplate.getUsedTime();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.setTimeInMillis(time.getTime());
				//cdrTemplate.getUsedDate(calendar);
				Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
				temCallDetailRecordPk.setTcdrUsedTime(timestamp);
				/*temCallDetailRecordPk.setTcdrUsedTime(new Timestamp(cdrTemplate
						.getUsedTime().getTime()));*/
				//temCallDetailRecordPk.setTtId(1);
				temCallDetailRecordPk.setTtId(Integer.valueOf(cdrTemplate.getTtid()));
				temCallDetailRecordPk.setTcdrMsIsdnTo(cdrTemplate.getMsIsdnTo());
				temCallDetailRecordPk.setTcdrDirection(cdrTemplate.getTcdrDirection());
				temCallDetailRecord.setTemCallDetailRecordPk(temCallDetailRecordPk);
				temCallDetailRecord.setTcdrSource(0);
				temCallDetailRecord.setTcdrValue(cdrTemplate.getPrice());
				temCallDetailRecord.setTcdrCallTo(cdrTemplate.getMsIsdnToLocation());
				//temCallDetailRecord.setTcdrBillCycle(new Timestamp(cdrTemplate.getBillCycle().getTime()));d
			//	temCallDetailRecord.setTcdrBillCycle(new Timestamp(billCycleDate.getTime()));
				temCallDetailRecord.setTcdrBillCycle(new Timestamp(cdrTemplate.getBillCycle().getTime()));
			//	System.out.println("temCallDetailRecord ="+temCallDetailRecord);
				temCallDetailRecord.setTcdrType(cdrTemplate.getTcdrType());
				
				temCallDetailRecord.setDurationTime(cdrTemplate.getDurationTime());
				temCallDetailRecord.setGprsPrdQnt(cdrTemplate.getGprsPrdQnt());
				temCallDetailRecord.setFeatureCode(cdrTemplate.getFeatureCode());
				temCallDetailRecord.setTcdrDataType(cdrTemplate.getTcdrDataType());
				temCallDetailRecord.setTcdrAmt(cdrTemplate.getTcdrAmt());
				Query query =em.createQuery("select count(temCallDetailRecord) from TemCallDetailRecord temCallDetailRecord" +
						" where temCallDetailRecord.temCallDetailRecordPk.tcdrMsIsdnFrom=:tcdrMsIsdnFrom" +
						" and temCallDetailRecord.temCallDetailRecordPk.tcdrMsIsdnTo=:tcdrMsIsdnTo" +
						" and   temCallDetailRecord.temCallDetailRecordPk.tcdrUsedTime=:tcdrUsedTime" +
						" and   temCallDetailRecord.temCallDetailRecordPk.ttId=:ttId"+
						" and   temCallDetailRecord.temCallDetailRecordPk.tcdrDirection=:tcdrDirection");
		 		query.setParameter("tcdrMsIsdnFrom", cdrTemplate.getMsIsdnFrom());
		 		query.setParameter("tcdrMsIsdnTo", cdrTemplate.getMsIsdnTo());
		 		query.setParameter("tcdrUsedTime", timestamp); 
		 		query.setParameter("ttId", temCallDetailRecordPk.getTtId()); 
		 		query.setParameter("tcdrDirection", temCallDetailRecordPk.getTcdrDirection()); 
		 	//	System.out.println("timestamp=>"+timestamp+",temCallDetailRecordPk.getTtId()=>"+temCallDetailRecordPk.getTtId()+",cdrTemplate.getMsIsdnFrom()=>"+cdrTemplate.getMsIsdnFrom()+",cdrTemplate.getMsIsdnTo()=>"+cdrTemplate.getMsIsdnTo());
		    	Long count=(Long)query.getSingleResult(); 
		    	if(count.intValue()>0){
		    		updateRecord++;
		    		/*System.out.println("cdrTemplate.getMsIsdnFrom()-->"+cdrTemplate.getMsIsdnFrom()
		    				+"cdrTemplate.getMsIsdnTo()-->"+cdrTemplate.getMsIsdnTo());*/
		    		System.err.println("-->"+cdrTemplate.getMsIsdnFrom()+","+cdrTemplate.getMsIsdnTo()+","+timestamp+","+temCallDetailRecordPk.getTtId()+","
		    				+temCallDetailRecordPk.getTcdrDirection());
		    		
		    	}
		    	else{
		    		
		    		newRecord++;
		    	}
		    	 
				em.merge(temCallDetailRecord);
				/*@Column(name = "TCDR_VALUE")
				private Double tcdrValue;*/
				/*@Column(name = "TCDR_CALL_TO")
				private String tcdrCallTo;*/
			//	temService.insertTemCallDetailRecord(session, temCallDetailRecord);
				 if ((i % 500) == 0) {
		 			 tx.commit();
			          em.clear();          
			          tx.begin();
			      }
			}
		 	/*for (TemCallDetailRecord temCallDetailRecord : temCallDetailRecords) {
		 		// em.persist(temCallDetailRecord);
		 		 em.merge(temCallDetailRecord);
			    //  if ((i % 10000) == 0) {
		 		  if ((i % 500) == 0) {
		 			 tx.commit();
			          em.clear();          
			          tx.begin();
			      }
			}*/
		 	 
	 }	catch (RuntimeException e) {
			    if (tx != null) tx.rollback();
			    e.printStackTrace();
			    //throw e; // or display error message
			}
			finally {
				tx.commit();
			    em.close();
		} 
	// System.out.println("newRecord->"+newRecord);
	// System.out.println("updateRecord->"+updateRecord);
	 results[0]=newRecord;
	 results[1]=updateRecord;
		return results;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor={RuntimeException.class})
	public int[] importGroup(List<GroupTemplate> temGroups) {
		// TODO Auto-generated method stub
		 EntityManager em = entityManager.createEntityManager(); 
		 EntityTransaction tx=em.getTransaction();
		 int[] results=new int[2];
		//System.out.println(tx);
		 int i=0;
		 int newRecord=0;
		 int indexx=0;
		 int updateRecord=0;
		 boolean isNew=false;
		 Map map =new HashMap();
	 try{
		 tx.begin();
		  
		 	for (GroupTemplate groupTemplate : temGroups) {
		 		 isNew=false;
		 		 if(!map.containsKey(groupTemplate.getNo().trim())){
		 			map.put(groupTemplate.getNo(), "1");
		 		  }else{
		 			 String number= (String)map.get(groupTemplate.getNo().trim());
		 			 map.put(groupTemplate.getNo(),(Integer.valueOf(number)+1)+"");
		 			 System.out.println("groupTemplate.getNo()->"+groupTemplate.getNo());
		 		  }
		 		Query query =em.createQuery("select temGroup from TemGroup temGroup where temGroup.tgName=:tgName ");
		 		query.setParameter("tgName", groupTemplate.getGroup());
		 		List obj=query.getResultList(); 
		    	if(obj.size()==0){
		    		isNew=true;
		    	}
		 		TemGroup temGroup =new TemGroup();
		 		temGroup.setTgName(groupTemplate.getGroup());
		 		em.merge(temGroup);
		 		  query =em.createQuery("select temCompany from TemCompany temCompany where temCompany.tcName=:tcName " +
		 				"and temCompany.tgName=:tgName");
		 		query.setParameter("tcName", groupTemplate.getCompany());
		 		query.setParameter("tgName", groupTemplate.getGroup());
		    	 obj=query.getResultList(); 
		    	if(obj.size()==0){
		    		TemCompany temCompany =new TemCompany();
			 		temCompany.setTcName(groupTemplate.getCompany());
			 		temCompany.setTgName(groupTemplate.getGroup());
			 		em.persist(temCompany);
			 		//newRecord++; 
			 		isNew=true;
		    	}
		    	 query =em.createQuery("select temCompany from TemCompany temCompany where temCompany.tcName=:tcName " +
			 				"and temCompany.tgName=:tgName");
			 		query.setParameter("tcName", groupTemplate.getCompany());
			 		query.setParameter("tgName", groupTemplate.getGroup());
			 		 obj=query.getResultList(); 
			 		 if(obj.size()>0){ 
			 			  Query q1 = em.createQuery(
			 			        "update TemMsIsdn e set e.temCompany.tcId= " +((TemCompany)obj.get(0)).getTcId()+
			 			        		" WHERE e.msIsdn='"+groupTemplate.getNo()+"' "); 
			 			    int updated = q1.executeUpdate(); 
			 			     if(updated==0){
			 			    	TemMsIsdn TemMsIsdn =new TemMsIsdn();
					 			TemMsIsdn.setMsIsdn(groupTemplate.getNo()); 
					 			TemMsIsdn.setTemCompany((TemCompany)obj.get(0));
					 			em.merge(TemMsIsdn); 
					 			indexx++;
			 			     }
			 		 }
			 		if ((i % 500) == 0) {
			 			 tx.commit();
				          em.clear();          
				          tx.begin();
				      }
			 		i++;
			 		if(isNew)
			 			newRecord++;
			 		else 
			 			updateRecord++;
			 			
			}
		 	
		 /*	for (TemGroup temGroup : temGroups) {
		 	 
		    	em.merge(temGroup);

		 		 //em.merge(temGroup);
			    //  if ((i % 10000) == 0) {
		 		  if ((i % 500) == 0) {
		 			 tx.commit();
			          em.clear();          
			          tx.begin();
			      }
			}*/
		 	//tx.commit();
	 }	catch (RuntimeException e) {
			    if (tx != null) tx.rollback();
			    e.printStackTrace();
			   // throw e; // or display error message
			}
			finally {
				tx.commit();
			    em.close();
		} 
	/* System.out.println(" i ---> "+i);
	 System.out.println("indexx->"+indexx);
	 System.out.println("map size-->"+map.size());*/
	 results[0]=newRecord;
	 results[1]=updateRecord;
		return results;
	}


	@Override
	@Transactional(readOnly=true)
	public List<TemGroup> getGroup() {
		// TODO Auto-generated method stub
		List list=null;
		EntityManager em = entityManager.createEntityManager();  
			    try {
			    	Query query =em.createQuery("select temGroup from TemGroup temGroup");
			    	list=query.getResultList(); 
			    }
			catch (RuntimeException e) {
			   e.printStackTrace();
			}
			finally {
			    em.close();
			}
		return list;
	}


	
	@Override
	@Transactional(readOnly=true)
	public List<TemCompany> getTemCompanyByGroup(String tgName) {
		// TODO Auto-generated method stub
		List list=null;
		EntityManager em = entityManager.createEntityManager();  
			    try {
			    	String sql="select temCompany from TemCompany temCompany";
			    	if(tgName!=null && tgName.trim().length()>0)
			    		sql=sql+" where temCompany.tgName =:tgName";
			    	
			    	Query query =em.createQuery(sql);
			    	
			    	if(tgName!=null && tgName.trim().length()>0)
			    	query.setParameter( "tgName", tgName );
			    	 
			    	list=query.getResultList(); 
			    }
			catch (RuntimeException e) {
			   e.printStackTrace();
			}
			finally {
			    em.close();
			}
		return list;
	}


	@Override
	@Transactional(readOnly=true)
	public List<TemMsIsdn> getTemMsIsdnByCompany(Integer tcId) {
		// TODO Auto-generated method stub
		List list=null;
		EntityManager em = entityManager.createEntityManager();  
			    try {
			    	String sql="select temMsIsdn from TemMsIsdn temMsIsdn";
			    	if(tcId!=null && tcId.intValue()!=0)
			    		sql=sql+" where temMsIsdn.temCompany.tcId =:tcId";
			    	
			    	Query query =em.createQuery(sql);
			    	
			    	if(tcId!=null && tcId.intValue()!=0)
			    	query.setParameter( "tcId", tcId );
			    	 
			    	list=query.getResultList(); 
			    }
			catch (RuntimeException e) {
			   e.printStackTrace();
			}
			finally {
			    em.close();
			}
		return list;
	}


	@Override
	@Transactional(readOnly=true)
	public List<ReportTemplate> listReportTemplates(Integer tcId,Date billCycle,Integer provider){		
		// TODO Auto-generated method stub
		List<ReportTemplate>  reportTemplates= null;
		StringBuffer sb=new StringBuffer();
		
		  sb.append("select  company_from.TC_GROUP_NAME group_from," +
		  		" company_from.TC_NAME company_from," +
		  		" provider_from.TP_NAME provider_from," +
		  		" tcdr.tcdrMsIsdnFrom," +
		  		" company_to.TC_GROUP_NAME group_to," +
		  		" company_to.TC_NAME company_to," +
		  		" provider_to.TP_NAME provider_to," + 
		  		" tcdr.tcdr_msisdn_to," +
		  		" tcdr.tcdrUsedTime, " +
		  		" tcdr.tcdr_call_to," +
		  		" tcdr.tcdr_used_count," +
		  		" tcdr.tcdr_value, " +
		  		" tcdr.tcdr_bill_cycle" + 
		  		" from TEM_CALL_DETAIL_RECORD tcdr" + 
		  		" left join TEM_MSISDN isdn_from on  tcdr.tcdrMsIsdnFrom=isdn_from.msisdn" +
		  		" left join TEM_COMPANY company_from   on isdn_from.tc_id=company_from.tc_id" +
		  		" left join TEM_PROVIDER provider_from on isdn_from.tp_id=provider_from.tp_id" +
		  		" left join TEM_MSISDN isdn_to on  tcdr.TCDR_MSISDN_TO=isdn_to.msisdn" +
		  		" left join TEM_COMPANY company_to   on isdn_to.tc_id=company_to.tc_id" +
		  		" left join TEM_PROVIDER provider_to on isdn_to.tp_id=provider_to.tp_id where tcdr.ttid=1 and " +
		  		" tcdr.tcdr_source=0 ");
		/*sb.append("select t1.tc_group_name group_from," +
				"        t1.tc_name company_from," +
				"        t1.tp_name provider_from, " +
				"        tcdr.tcdrMsIsdnFrom, " +
				"        t2.tc_group_name group_to," +
				"        t2.tc_name company_to," +
				"		t2.tp_name provider_to," +
				"		tcdr.tcdr_msisdn_to," +
				"        tcdr.tcdrUsedTime," +
				"        tcdr.tcdr_call_to," +
				"		tcdr.tcdr_used_count," +
				"        tcdr.tcdr_value, " +
				"  tcdr.tcdr_bill_cycle " +
				"		from TEM_CALL_DETAIL_RECORD tcdr left join" +
				"	(select isdn.msisdn, company.tc_id, company.tc_name," +
				"     company.tc_group_name,provider.tp_id,provider.tp_name " +
				"     from TEM_MSISDN isdn left join TEM_COMPANY company " +
				"     on isdn.tc_id=company.tc_id left join TEM_PROVIDER provider " +
				"     on isdn.tp_id=provider.tp_id) t1 on " +
				"	 tcdr.tcdrmsisdnfrom=t1.msisdn left join" +
				"	(select isdn.msisdn, company.tc_name, company.tc_group_name," +
				"     provider.tp_name from TEM_MSISDN isdn left join " +
				"    TEM_COMPANY company on isdn.tc_id=company.tc_id left join " +
				"	TEM_PROVIDER provider on isdn.tp_id=provider.tp_id) t2 on " +
				"    tcdr.tcdr_msisdn_to=t2.msisdn where tcdr.ttid=1 and " +
				"	tcdr.tcdr_source=0 ");*/
		
		   /* if(tgName!=null && tgName.trim().length()>0){
		    	sb.append("   and t1.tc_group_name=:tgName");
		    }*/
		    if(tcId!=null && tcId.intValue()!=0){
		    	//sb.append("   and t1.tc_id=:tcId");
		    	sb.append("   and company_from.tc_id=:tcId");
		    }
		    if(provider!=null && provider.intValue()!=0 && provider.intValue()!=-1){
		    	//sb.append("   and t1.tp_id=:tpId");
		    	sb.append("   and provider_from.tp_id=:tpId");
		    }
		    if(billCycle!=null){
		    
		    	//sb.append("   and tcdr.tcdrMsIsdnFrom=:msIsdn");
		    	//sb.append(" tcdr.tcdr_bill_cycle between STR_TO_DATE('"+billCycleStr+" 00:00:00','/%Y-/%m-\\%d \\%H:\\%i:\\%s') and STR_TO_DATE('"+billCycleStr+" 23:00:00','\\%Y-\\%m-\\%d \\%H:\\%i:\\%s')"); 
		    	//sb.append(" and tcdr.tcdr_bill_cycle between STR_TO_DATE('"+billCycleStr+" 00:00:00') and STR_TO_DATE('"+billCycleStr+" 23:00:00')");
		    	sb.append(" and tcdr.tcdr_bill_cycle between :start and :end ");
		    	 
		    }
		    /*and t1.tc_group_name='กลุ่ม C' and  t1.tc_id=3  and tcdr.tcdrMsIsdnFrom='057777777'
		    		   and t1.tp_name='AIS'*/
			List list=null;
			/*DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("th","TH"));
			Calendar calendar = new GregorianCalendar(new Locale("th","TH"));*/
		 
	EntityManager em = entityManager.createEntityManager();  
		    try {
		    	Query query =em.createNativeQuery(sb.toString());
		    	/*if(tgName!=null && tgName.trim().length()>0){
		    		query.setParameter("tgName", tgName); 
			    }*/
			    if(tcId!=null && tcId.intValue()!=0){
			    	query.setParameter("tcId", tcId.intValue());  
			    }
			    if(provider!=null && provider.intValue()!=0 && provider.intValue()!=-1){
			    	query.setParameter("tpId", provider.intValue());   
			    }
			    if(billCycle!=null){ 
			    /*	String billCycleStr=format.format(billCycle);
			    	System.out.println(billCycleStr);*/
			    	Date date_to=null;
			    	Date date_end=null; 
			    	try {
			    		String ff = dFormat_end.format(billCycle);
			    		//System.out.println("ff="+ff);
						date_end=dFormat.parse(ff);
						 ff = dFormat_to.format(billCycle);
						 date_to=dFormat.parse(ff);
					//	System.out.println("d="+date_end);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	// System.out.println("billCycle="+billCycle);
			    	query.setParameter("start",date_to, TemporalType.TIMESTAMP);
			    	query.setParameter("end",date_end, TemporalType.TIMESTAMP);
			    }
			   /* if(msIsdn!=null && msIsdn.trim().length()>0){
			    	query.setParameter("msIsdn", msIsdn);   
			    }*/
		    	list=query.getResultList(); 
		    	int size=list.size();
		    	reportTemplates=new ArrayList<ReportTemplate>(size);
		    	 DecimalFormat myFormatter = new DecimalFormat("#00.###");
		    	for (int i = 0; i < size; i++) {
		    		java.lang.Object[] listObj=(java.lang.Object[])list.get(i);
		    		ReportTemplate reportTemplate =new ReportTemplate();  
		    		
		    		reportTemplate.setGroupFrom(listObj[0]!=null?(String)listObj[0]:"");//resultSet.getString("group_from"));
					reportTemplate.setCompanyFrom(listObj[1]!=null?(String)listObj[1]:"");//resultSet.getString("company_from"));
					reportTemplate.setProviderFrom(listObj[2]!=null?(String)listObj[2]:"");//resultSet.getString("provider_from"));
					reportTemplate.setMsIsdnFrom(listObj[3]!=null?(String)listObj[3]:"");//resultSet.getString("tcdrMsIsdnFrom"));
					
					reportTemplate.setGroupTo(listObj[4]!=null?(String)listObj[4]:"");//resultSet.getString("group_to"));
					reportTemplate.setCompanyTo(listObj[5]!=null?(String)listObj[5]:"");//resultSet.getString("company_to"));
					reportTemplate.setProviderTo(listObj[6]!=null?(String)listObj[6]:"");//resultSet.getString("provider_from"));
					reportTemplate.setMsIsdnTo(listObj[7]!=null?(String)listObj[7]:"");//resultSet.getString("tcdr_msisdn_to"));
					
					reportTemplate.setUsedTime(listObj[8]!=null?(Timestamp)listObj[8]:null);//resultSet.getTimestamp("tcdrUsedTime"));
					reportTemplate.setCallTo(listObj[9]!=null?(String)listObj[9]:"");//resultSet.getString("tcdr_call_to"));
					reportTemplate.setUsedCount(listObj[10]!=null?(Double)listObj[10]:null);//resultSet.getDouble("tcdr_used_count"));
					reportTemplate.setPrice(listObj[11]!=null?(Double)listObj[11]:null);//resultSet.getDouble("tcdr_value"));
					Double usedCount =reportTemplate.getUsedCount();
					if(usedCount!=null){
						int usedCountInt=usedCount.intValue();
						int seconds = (int) (usedCountInt) % 60 ;
						int minutes = (int) ((usedCountInt / (60)) % 60);
						int hours   = (int) ((usedCountInt / (60*60)) % 24); 
						reportTemplate.setUsedCountStr(myFormatter.format(hours)+":"+myFormatter.format(minutes)+":"+myFormatter.format(seconds));
					} 
		    		if(i<=7){
		    			Date date=(Date)listObj[8];
		    			
		    		//System.out.println(dFormat.format(date));
		    		//Double usedCount =reportTemplate.getUsedCount();
					if(usedCount!=null){
						int usedCountInt=usedCount.intValue();
						int seconds = (int) (usedCountInt) % 60 ;
						int minutes = (int) ((usedCountInt / (60)) % 60);
						int hours   = (int) ((usedCountInt / (60*60)) % 24);
				 
						//System.out.println(myFormatter.format(hours)+":"+myFormatter.format(minutes)+":"+myFormatter.format(seconds));
						reportTemplate.setUsedCountStr(myFormatter.format(hours)+":"+myFormatter.format(minutes)+":"+myFormatter.format(seconds));
						}
		    		} 
		    		reportTemplate.setBillCycle(listObj[12]!=null?(Timestamp)listObj[12]:null);//resultSet.getTimestamp("tcdrUsedTime"));
		    		reportTemplates.add(reportTemplate);
				}
		    }
		catch (RuntimeException e) {
		   e.printStackTrace();
		}
		finally {
		    em.close();
		}
	return reportTemplates;
	 
	}


	@Override
	public List<String[]> getBillCycle(Integer tcId) {
		// TODO Auto-generated method stub
		List<String[]>  billCycles= null;
		StringBuffer sb=new StringBuffer(); 
		/*sb.append("select     DATE_FORMAT(tcdr.tcdr_bill_cycle,'%d_%m_%Y'),  DATE_FORMAT(tcdr.tcdr_bill_cycle,'%d/%m/%Y'), tcdr.tcdr_bill_cycle " +
				" from TEM_CALL_DETAIL_RECORD tcdr left join (select isdn.msisdn, company.tc_id, company.tc_name," +
				"   company.tc_group_name,provider.tp_name  from TEM_MSISDN isdn left join TEM_COMPANY company" +
				"  on isdn.tc_id=company.tc_id left join TEM_PROVIDER provider  on isdn.tp_id=provider.tp_id) t1 on" +
				" tcdr.tcdrmsisdnfrom=t1.msisdn  where tcdr.ttid=1 and" +
				" 	tcdr.tcdr_source=0	and  t1.tc_id="+tcId+" group by  tcdr.tcdr_bill_cycle"); */
		sb.append("select     DATE_FORMAT(tcdr.tcdr_bill_cycle,'%d_%m_%Y'),  DATE_FORMAT(tcdr.tcdr_bill_cycle,'%d/%m/%Y'), tcdr.tcdr_bill_cycle " +
				" from TEM_CALL_DETAIL_RECORD tcdr left join TEM_MSISDN isdn on  tcdr.tcdrmsisdnfrom=isdn.msisdn left join " +
				"  TEM_COMPANY company on isdn.tc_id=company.tc_id  left join " +
				"  TEM_PROVIDER provider  on isdn.tp_id=provider.tp_id  " +
				" where  company.tc_id="+tcId+" group by  tcdr.tcdr_bill_cycle  " +
				"   "); 
		 
		    
		List list=null;
		/*DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("th","TH"));
		Calendar calendar = new GregorianCalendar(new Locale("th","TH"));*/
	 
EntityManager em = entityManager.createEntityManager();  
	    try {
	    	Query query =em.createNativeQuery(sb.toString());
	    	 
	    	list=query.getResultList(); 
	    	int size=list.size();
	    	billCycles=new ArrayList<String[]>(size); 
	    	for (int i = 0; i < size; i++) {
	    		java.lang.Object[] listObj=(java.lang.Object[])list.get(i);
	    		String[] results=new String[2];
	    		results[0]=listObj[0]!=null?(String)listObj[0]:"";
	    		results[1]=listObj[1]!=null?(String)listObj[1]:""; 
	    		billCycles.add(results);
			}
	    }
	catch (RuntimeException e) {
	   e.printStackTrace();
	}
	finally {
	    em.close();
	}
	    return billCycles;
	}


	@Override
	public List<String[]> listProvider(Integer tcId, Date billCycle) {
		// TODO Auto-generated method stub
		List<String[]>  providers= null;
		StringBuffer sb=new StringBuffer();
		
	/*	sb.append("select  t1.tp_id,  t1.tp_name " +
				//"  DATE_FORMAT(tcdr.tcdr_bill_cycle,'%d_%m_%Y'),  DATE_FORMAT(tcdr.tcdr_bill_cycle,'%d/%m/%Y'), tcdr.tcdr_bill_cycle " +
				" from TEM_CALL_DETAIL_RECORD tcdr left join (select isdn.msisdn, company.tc_id, company.tc_name," +
				"   company.tc_group_name,provider.tp_id,provider.tp_name  from TEM_MSISDN isdn left join TEM_COMPANY company" +
				"  on isdn.tc_id=company.tc_id left join TEM_PROVIDER provider  on isdn.tp_id=provider.tp_id) t1 on" +
				" tcdr.tcdrmsisdnfrom=t1.msisdn  where tcdr.ttid=1 and" +
				" 	tcdr.tcdr_source=0	and  t1.tc_id="+tcId+" " );*/
		sb.append("select  provider.tp_id,  provider.tp_name " + 
				" from TEM_CALL_DETAIL_RECORD tcdr left join  " +
				" TEM_MSISDN isdn on  tcdr.tcdrmsisdnfrom=isdn.msisdn left join  " +
				" TEM_COMPANY company on isdn.tc_id=company.tc_id  left join  " +
				" TEM_PROVIDER provider  on isdn.tp_id=provider.tp_id   " +
				" where  company.tc_id="+tcId+" " );
		 
		if(billCycle!=null){
						    	sb.append(" and tcdr.tcdr_bill_cycle between :start and :end ");
		  }
		sb.append("group by   provider.tp_name"); 
		List list=null;
		/*DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("th","TH"));
		Calendar calendar = new GregorianCalendar(new Locale("th","TH"));*/
	
EntityManager em = entityManager.createEntityManager();  
	    try {
	    	Query query =em.createNativeQuery(sb.toString());
	    	 if(billCycle!=null){ 
				    	Date date_to=null;
				    	Date date_end=null; 
				    	try {
				    		String ff = dFormat_end.format(billCycle);
							date_end=dFormat.parse(ff);
							 ff = dFormat_to.format(billCycle);
							 date_to=dFormat.parse(ff);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	query.setParameter("start",date_to, TemporalType.TIMESTAMP);
				    	query.setParameter("end",date_end, TemporalType.TIMESTAMP);
				    }
	    	list=query.getResultList(); 
	    	int size=list.size();
	    	providers=new ArrayList<String[]>(size); 
	    	for (int i = 0; i < size; i++) {
	    		java.lang.Object[] listObj=(java.lang.Object[])list.get(i);
	    		String[] results=new String[2];
	    		results[0]=listObj[0]!=null?(((Integer)listObj[0])+""):"";
	    		results[1]=listObj[1]!=null?(String)listObj[1]:""; 
	    		providers.add(results);
			}
	    }
	catch (RuntimeException e) {
	   e.printStackTrace();
	}
	finally {
	    em.close();
	}
	    return providers;
	}

 
	public List<ReportTemplate> listMobileReportTemplates_BK(Integer tcId,
			Date billCycle, Integer provider) {
		// TODO Auto-generated method stub 
		List<ReportTemplate>  reportTemplates= null;
		List<String>  msisdn_from= null;
		StringBuffer sb=new StringBuffer();
		 
		String query_date_to = query_d_Format_to.format(billCycle);
		String query_date_end = query_d_Format_end.format(billCycle);
		String tcdr_company_from=""; 
		String tcdr_provider_from="";
	 
		    if(tcId!=null && tcId.intValue()!=0){ 
		    	tcdr_company_from="and tcdr_company_from.tc_id="+tcId.intValue(); 
		    }
		    if(provider!=null && provider.intValue()!=0 && provider.intValue()!=-1){ 
		    	tcdr_provider_from=" and tcdr_provider_from.tp_id="+provider.intValue(); 
		    }
		/*
		private static final DateFormat query_d_Format_to = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		private static final DateFormat query_d_Format_end= new SimpleDateFormat("yyyy-MM-dd 23:59:59");*/
		

		  sb.append("select tcdr.tcdrmsisdnfrom"
		  		/* + ",max(tcdr.tcdr_bill_cycle), "+
				  " ( SELECT format(sum(cdr_5.TCDR_USED_COUNT)/60 ,2) "+
				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_5    "+
				 
				  " where cdr_5.tcdrMsisdnFrom "+
				  " =tcdr.tcdrMsisdnFrom "+
				  " and cdr_5.tcdr_code='RCGV42' "+ 
				  " and cdr_5.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+  
				  " and cdr_5.ttId=1 "+
				  " ) as in_group  "+
				  " , "+
				  " "+
				  " ( SELECT format(sum(cdr_4.TCDR_USED_COUNT)/60 ,2) "+
				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_4   "+
				 
				  " where cdr_4.tcdrMsisdnFrom "+
				  " =tcdr.tcdrMsisdnFrom "+
				  " and cdr_4.tcdr_code like 'RVCNW%' "+ 
				  " and cdr_4.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
				  " and cdr_4.ttId=1 "+
				  " ) as ext_group  "+
				  
				  " , "+
				  " "+
				  " (SELECT format(sum(cdr_0.TCDR_USED_COUNT)/60 ,2) "+
				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_0    "+
				  " where cdr_0.tcdrMsisdnFrom= "+
				  " tcdr.tcdrMsisdnFrom  "+
				  " and cdr_0.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
				  " and cdr_0.ttId=1 ) as call_all  "+
				  " "+
				  " , "+
				  " "+
				  " (SELECT sum(cdr_1.TCDR_USED_COUNT) "+
				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_1 "+ 
				  " where cdr_1.tcdrMsisdnFrom=  "+
				  " tcdr.tcdrMsisdnFrom   "+
				  " and cdr_1.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
				  " and cdr_1.ttId=2 ) as sms , "+
				  " "+
				  " (SELECT format(sum(cdr_2.GPRS_PRD_QNT)/1048576,2) "+  
				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_2   "+
				  " where cdr_2.tcdrMsisdnFrom= "+
				  " tcdr.tcdrMsisdnFrom   "+
				  " and cdr_2.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
				  " and (cdr_2.ttId=4 or cdr_2.ttId=5) and cdr_2.tcdr_code like 'RGPRS%' ) as gprs_mb , "+
				  " "+
				  " (SELECT format(sum(cdr_3.TCDR_USED_COUNT)/60,2) "+ 
				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_3   "+
				  " where cdr_3.tcdrMsisdnFrom= "+
				  " tcdr.tcdrMsisdnFrom   "+
				  " and cdr_3.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
				  " and cdr_3.ttId=3 and cdr_3.tcdr_code like 'RGPRS%' ) as gprs_min , "+
				  " "+
				  " ( SELECT format(sum(cdr_6.TCDR_USED_COUNT)/60 ,2) "+
				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_6   "+
				  " where cdr_6.tcdrMsisdnFrom "+
				  " =tcdr.tcdrMsisdnFrom   "+ 
				  " and cdr_6.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
				  " and cdr_6.ttId=1 "+
				  " and cdr_6.tcdr_code='RVTA14' "+
				  " ) as close_group , "+
				  ""+
				  " ( SELECT format(sum(cdr_7.TCDR_USED_COUNT)/60 ,2) "+
				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_7   "+
				  " where cdr_7.tcdrMsisdnFrom "+
				  " =tcdr.tcdrMsisdnFrom   "+ 
				  " and cdr_7.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
				  " and cdr_7.ttId=1 "+
				  " and ( cdr_7.tcdr_code = 'RSNC54' or  cdr_7.tcdr_code = 'RSNC42' )"+
				  " ) as specail_group , "+ 
				  " "+
//				   R12681 VDO Clip
//				  R14942 Cool Club
//				  R15709
//
//				  R10370  เอสเอ็มเอส มาร์เก็ตติ้ง
//				  R12139  Daily Content Subscription
//
//				  R14315
		     
		    " ( SELECT format(sum(cdr_8.TCDR_USED_COUNT)/60 ,2) "+
			  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_8   "+
			  " where cdr_8.tcdrMsisdnFrom "+
			  " =tcdr.tcdrMsisdnFrom   "+ 
			  " and cdr_8.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
			  " and cdr_8.ttId=3 "+
			  " and ( cdr_8.tcdr_code in ( 'R12681' ,'R14942','R10370','R12139')   )"+
			  " ) as content  "+
			  ""+
			  ","+
			  " ( SELECT format(sum(cdr_9.TCDR_USED_COUNT)/60 ,2) "+
			  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_9   "+
			  " where cdr_9.tcdrMsisdnFrom "+
			  " =tcdr.tcdrMsisdnFrom   "+ 
			  " and cdr_9.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
			  " and cdr_9.ttId=1 "+
			  " and ( cdr_9.tcdr_code like 'RSNC%' or  cdr_9.tcdr_code like 'RSNP%' ) and cdr_9.tcdr_code != 'RSNC54' and cdr_9.tcdr_code != 'RSNC42' "+
			  " ) as specail_group_ext  "+ 
 " , "+
 " "+
 " (SELECT sum(cdr_10.TCDR_USED_COUNT) "+
 " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_10 "+ 
 " where cdr_10.tcdrMsisdnFrom=  "+
 " tcdr.tcdrMsisdnFrom   "+
 " and cdr_10.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
 " and cdr_10.ttId=6 ) as mms  "+
 */
			  
				  +" from TEM_CALL_DETAIL_RECORD tcdr "+ 
				  " left join TEM_MSISDN tcdr_isdn_from on  tcdr.tcdrMsIsdnFrom=tcdr_isdn_from.msisdn "+ 
				  " left join TEM_COMPANY tcdr_company_from   on tcdr_isdn_from.tc_id=tcdr_company_from.tc_id "+ 
				  " left join TEM_PROVIDER tcdr_provider_from on tcdr_isdn_from.tp_id=tcdr_provider_from.tp_id  "+  
				  " where tcdr.tcdr_source=0   "+ 
				  ""+tcdr_company_from+"  "+
				  ""+tcdr_provider_from+"  "+
				  " and tcdr.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
				  " group by tcdr.tcdrmsisdnfrom "); 
		    
			List list=null;
			 
	//	 System.out.println(sb.toString());
	EntityManager em = entityManager.createEntityManager();  
		    try {
		    	Query query =em.createNativeQuery(sb.toString());
		    	 
			    if(tcId!=null && tcId.intValue()!=0){
			    	//query.setParameter("tcId", tcId.intValue());  
			    }
			    if(provider!=null && provider.intValue()!=0 && provider.intValue()!=-1){
			    	//query.setParameter("tpId", provider.intValue());   
			    }
			    Timestamp timestamp=null;
			    if(billCycle!=null){  
			    	//System.out.println("billCycle->"+billCycle);
			    	/*Date date = cdrTemplate.getUsedDate();
					Time time = cdrTemplate.getUsedTime();*/
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(billCycle);
				//	calendar.setTimeInMillis(time.getTime());
					//cdrTemplate.getUsedDate(calendar);
					timestamp = new Timestamp(calendar.getTime().getTime());
			    	/* Date date_to=null;
			    	//Date date_end=null; 
			    	 try {
			    		String ff = dFormat_end.format(billCycle);  
						//date_end=dFormat.parse(ff);
						 ff = dFormat_to.format(billCycle);
						  date_to=dFormat.parse(ff); 
					} catch (ParseException e) { 
						e.printStackTrace();
					} */
			    	// System.out.println("billCycle="+billCycle);
			    	//query.setParameter("start",date_to, TemporalType.TIMESTAMP);
			    	//query.setParameter("end",date_end, TemporalType.TIMESTAMP);
			    }
			    
		    	list=query.getResultList(); 
		    	int size=list.size();
		    	//System.out.println("xxx size"+size);
		    	reportTemplates=new ArrayList<ReportTemplate>(size);
		    	msisdn_from=new ArrayList<String>(size);
		    	// DecimalFormat myFormatter = new DecimalFormat("#00.###");
		    	DecimalFormat myFormatter = new DecimalFormat("###.###");
		    	Object obj=null;
		    	for (int i = 0; i < size; i++) {
		    		//java.lang.Object[] listObj=(java.lang.Object[])list.get(i);
		    		ReportTemplate reportTemplate =new ReportTemplate();  
		    		String msisdn=list.get(i)!=null?(String)list.get(i):"";
		    		msisdn_from.add(msisdn);
		    		reportTemplate.setMsIsdnFrom(msisdn); 
		    		reportTemplate.setBillCycle(timestamp);
		    		// in Group
		    		 query =em.createNativeQuery( " SELECT format(sum(cdr_5.TCDR_USED_COUNT)/60 ,2) "+
		   				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_5    "+
		 				 
		 				  " where cdr_5.tcdrMsisdnFrom "+
		 				  " ='"+msisdn+"' "+
		 				  " and ( cdr_5.tcdr_code='RCGV42' or  cdr_5.tcdr_code='RCGC42' ) "+ 
		 				  " and cdr_5.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+  
		 				  " and cdr_5.ttId=1 "+
		 				  "    ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallInGroup(obj!=null?(String)obj:"");
		    		
		    		// ext_group
		    		query =em.createNativeQuery( "  SELECT format(sum(cdr_4.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_4   "+
					 
					  " where cdr_4.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_4.tcdr_code like 'RVCNW%' "+ 
					  " and cdr_4.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  " and cdr_4.ttId=1 "+
					  "   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallExtGroup(obj!=null?(String)obj:"");
		    		
		    		// call_all
		    		query =em.createNativeQuery( "SELECT format(sum(cdr_0.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_0    "+
					  " where cdr_0.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_0.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  " and cdr_0.ttId=1    ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallAll(obj!=null?(String)obj:"");
		    		
		    		// sms
		    		query =em.createNativeQuery( "SELECT sum(cdr_1.TCDR_USED_COUNT) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_1 "+ 
					  " where cdr_1.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_1.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  //" and ( cdr_1.ttId=2  or  cdr_1.tcdr_code like 'R1%' ) ");
		    				" and   cdr_1.tcdr_code like 'R1%'  ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setSms(obj!=null?(myFormatter.format((Double)obj)):"");
		    		
		    		// gprs_mb
		    		query =em.createNativeQuery( "SELECT format(sum(cdr_2.GPRS_PRD_QNT)/1048576,2) "+  
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_2   "+
					  " where cdr_2.tcdrMsisdnFrom"+
					  " ='"+msisdn+"' "+
					  " and cdr_2.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  " and (cdr_2.ttId=4 or cdr_2.ttId=5) and cdr_2.tcdr_code like 'RGPRS%'   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setGprsMB(obj!=null?(String)obj:"");
		    		
		    		// gprs_min
		    		query =em.createNativeQuery( "SELECT format(sum(cdr_3.TCDR_USED_COUNT)/60,2) "+ 
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_3   "+
					  " where cdr_3.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_3.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  " and cdr_3.ttId=3 and cdr_3.tcdr_code like 'RGPRS%'   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setGprsMIN(obj!=null?(String)obj:"");
		    		
		    		// close_group
		    		query =em.createNativeQuery( " SELECT format(sum(cdr_6.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_6   "+
					  " where cdr_6.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_6.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
					  " and cdr_6.ttId=1 "+
					  " and ( cdr_6.tcdr_code='RVTA14' or cdr_6.tcdr_code='RVT2X4' )  "+
					  "   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallCloseGroup(obj!=null?(String)obj:"");
		    		
		    		// specail_group
		    		query =em.createNativeQuery( " SELECT format(sum(cdr_7.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_7   "+
					  " where cdr_7.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_7.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
					  " and cdr_7.ttId=1 "+
					  " and ( cdr_7.tcdr_code = 'RSNC54' or  cdr_7.tcdr_code = 'RSNC42' )"+
					  "   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallSpecailGroup(obj!=null?(String)obj:"");
		    		
		    		// content
		    		query =em.createNativeQuery(" SELECT format(sum(cdr_8.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_8   "+
					  " where cdr_8.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+ 
					  " and cdr_8.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
					  " and cdr_8.ttId=3 "+
					  " and ( cdr_8.tcdr_code in ( 'R12681' ,'R14942','R10370','R12139')   )"+
					  " ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setAmt(obj!=null?(String)obj:"");
		    		
		    		// specail_group_ext
		    		query =em.createNativeQuery(" SELECT format(sum(cdr_9.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_9   "+
					  " where cdr_9.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_9.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
					  " and cdr_9.ttId=1 "+
					  " and ( cdr_9.tcdr_code like 'RSNC%' or  cdr_9.tcdr_code like 'RSNP%' ) and cdr_9.tcdr_code != 'RSNC54' and cdr_9.tcdr_code != 'RSNC42' "+
					  "     ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallSpecailGroupExt(obj!=null?(String)obj:"");
		    		
		    		// mms
		    		query =em.createNativeQuery(" SELECT sum(cdr_10.TCDR_USED_COUNT) "+
		    		 " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_10 "+ 
		    		 " where cdr_10.tcdrMsisdnFrom  "+
		    		  " ='"+msisdn+"' "+
		    		 " and cdr_10.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
		    		 " and cdr_10.ttId=6    ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setMms(obj!=null?(myFormatter.format((Double)obj)):"");
		    			//System.out.println((String)obj); 
		    		/*reportTemplate.setMsIsdnFrom(listObj[0]!=null?(String)listObj[0]:"");//resultSet.getString("tcdrMsIsdnFrom"));
					reportTemplate.setBillCycle(listObj[1]!=null?(Timestamp)listObj[1]:null);//resultSet.getTimestamp("tcdrUsedTime"));
					//System.out.println("xx->" +listObj[2].getClass());
					reportTemplate.setCallInGroup(listObj[2]!=null?(String)listObj[2]:"");
					reportTemplate.setCallExtGroup(listObj[3]!=null?(String)listObj[3]:"");
					reportTemplate.setCallAll(listObj[4]!=null?(String)listObj[4]:"");
					reportTemplate.setSms(listObj[5]!=null?(myFormatter.format((Double)listObj[5])):"");
					reportTemplate.setGprsMB(listObj[6]!=null?(String)listObj[6]:"");
					reportTemplate.setGprsMIN(listObj[7]!=null?(String)listObj[7]:""); 
					reportTemplate.setCallCloseGroup(listObj[8]!=null?(String)listObj[8]:"");
					reportTemplate.setCallSpecailGroup(listObj[9]!=null?(String)listObj[9]:""); 
					reportTemplate.setAmt(listObj[10]!=null?(String)listObj[10]:"");
					
					reportTemplate.setCallSpecailGroupExt(listObj[11]!=null?(String)listObj[11]:"");
					reportTemplate.setMms(listObj[12]!=null?(myFormatter.format((Double)listObj[12])):""); 
					*/
		    		reportTemplates.add(reportTemplate);
				}
		    	
		    }
		catch (RuntimeException e) {
		   e.printStackTrace();
		}
		finally {
		    em.close();
		}
	return reportTemplates;
	} 
	@Override
	@Transactional(readOnly=true)
	public MobileTemplate listMobileReportTemplates(Integer tcId,
			Date billCycle, Integer provider) {
		// TODO Auto-generated method stub 
		MobileTemplate mobileTemplate =new MobileTemplate();
		List<ReportTemplate>  reportTemplates= null;
		List<String>  msisdn_from= null;
		StringBuffer sb=new StringBuffer();
		StringBuffer sb_header=new StringBuffer();
		 
		String query_date_to = query_d_Format_to.format(billCycle);
		String query_date_end = query_d_Format_end.format(billCycle);
		String tcdr_company_from=""; 
		String tcdr_provider_from="";
	 
		    if(tcId!=null && tcId.intValue()!=0){ 
		    	tcdr_company_from="and tcdr_company_from.tc_id="+tcId.intValue(); 
		    }
		    if(provider!=null && provider.intValue()!=0 && provider.intValue()!=-1){ 
		    	tcdr_provider_from=" and tcdr_provider_from.tp_id="+provider.intValue(); 
		    } 
		    sb_header.append("SELECT tcg_id,tcg_provider,tcg_name,tcg_order,tcg_status,tcg_column ,tcg_is_sum FROM BLUECODE_DB.TEM_CODE_GROUP where TCG_STATUS=1 order by TCG_ORDER "
		    		//+ " limit 300 "
		    		);

		  sb.append("select tcdr.tcdrmsisdnfrom"
				  +" from TEM_CALL_DETAIL_RECORD tcdr "+ 
				  " left join TEM_MSISDN tcdr_isdn_from on  tcdr.tcdrMsIsdnFrom=tcdr_isdn_from.msisdn "+ 
				  " left join TEM_COMPANY tcdr_company_from   on tcdr_isdn_from.tc_id=tcdr_company_from.tc_id "+ 
				  " left join TEM_PROVIDER tcdr_provider_from on tcdr_isdn_from.tp_id=tcdr_provider_from.tp_id  "+  
				  " where tcdr.tcdr_source=0   "+ 
				  ""+tcdr_company_from+"  "+
				  ""+tcdr_provider_from+"  "+
				  " and tcdr.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
			//	  " and tcdr.tcdrMsIsdnFrom='0812577791' "+
				  " group by tcdr.tcdrmsisdnfrom "); 
		    
			List list=null;
	//	 System.out.println(sb.toString());
	EntityManager em = entityManager.createEntityManager();  
		    try {
		    	Query query =em.createNativeQuery(sb_header.toString());
		    	list=query.getResultList();
		    	 int size_codegroup=list.size();
		    	List<TemCodeGroup> temCodeGroups=new ArrayList<TemCodeGroup>(size_codegroup);
		    	List<String>  headers=  new ArrayList<String>(size_codegroup);
		    	for (int i = 0; i < size_codegroup; i++) {
		    		java.lang.Object[] listObj=(java.lang.Object[])list.get(i);
		    		 
		    		TemCodeGroup temCodeGroup =new TemCodeGroup();
		    		temCodeGroup.setTcgId(((Integer)listObj[0])+""); 
		    		temCodeGroup.setTcgName((String)listObj[2]);
		    		temCodeGroup.setTcgColumn((String)listObj[5]); 
		    		temCodeGroup.setTcgIsSum(((Integer)listObj[6])+"");
		    		 query =em.createNativeQuery( " SELECT TCG_ID,TCM_CODE,TCM_MODE FROM BLUECODE_DB.TEM_CODE_MAPPING where TCG_ID="+temCodeGroup.getTcgId());
		    		 List list_inner=query.getResultList();
		    		 int inner_size=list_inner.size();
		    		 List<TemCodeMapping> temCodeMappings =new ArrayList<TemCodeMapping>(inner_size); 
		    		 for (int j = 0; j < inner_size ; j++) {
		    			 java.lang.Object[] listObj_inner=(java.lang.Object[])list_inner.get(j);
		    			 TemCodeMapping temCodeMapping =new TemCodeMapping();
		    			 temCodeMapping.setTcgId(temCodeGroup.getTcgId());
		    			 temCodeMapping.setTcmCode((String)listObj_inner[1]);
		    			 temCodeMapping.setTcmMode(((Integer)listObj_inner[2])+"");
		    			 temCodeMappings.add(temCodeMapping);
					}
		    		 temCodeGroup.setTemCodeMappings(temCodeMappings);
		    		temCodeGroups.add(temCodeGroup);
		    		headers.add(temCodeGroup.getTcgName());
		    	} 
		    	mobileTemplate.setHeaders(headers);  
		    	mobileTemplate.setTemCodeGroups(temCodeGroups); 
		    	query =em.createNativeQuery(sb.toString());
			    Timestamp timestamp=null;
			    if(billCycle!=null){  
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(billCycle);
					timestamp = new Timestamp(calendar.getTime().getTime());
			    }
			    
		    	list=query.getResultList(); 
		    	int  size=list.size();
		    	reportTemplates=new ArrayList<ReportTemplate>(size);
		    	msisdn_from=new ArrayList<String>(size);
		    	DecimalFormat myFormatter = new DecimalFormat("###.###");
		    	Object obj=null;
		    	StringBuffer inner_where=new StringBuffer();
		    	StringBuffer inner_in=new StringBuffer();
		    	StringBuffer query_inner=new StringBuffer();
		    	for (int i = 0; i < size; i++) {
		    		//java.lang.Object[] listObj=(java.lang.Object[])list.get(i);
		    		ReportTemplate reportTemplate =new ReportTemplate();  
		    		String msisdn=list.get(i)!=null?(String)list.get(i):"";
		    		msisdn_from.add(msisdn);
		    		reportTemplate.setMsIsdnFrom(msisdn); 
		    		reportTemplate.setBillCycle(timestamp);
		    		 String[] columns_data=new String[size_codegroup];
		    		for (int j = 0; j < size_codegroup; j++) { 
		    			TemCodeGroup temCodeGroup_inner=temCodeGroups.get(j);
		    			List<TemCodeMapping> temCodeMapping_inner_List=temCodeGroup_inner.getTemCodeMappings();
		    			inner_where.setLength(0);
		    			int temCodeMapping_inner_size=temCodeMapping_inner_List.size();
		    		    if(temCodeMapping_inner_size>1){
		    		    	inner_in.setLength(0);
		    		    	for (int k = 0; k < temCodeMapping_inner_size; k++) {
		    		    		TemCodeMapping temCodeMapping_inner=temCodeMapping_inner_List.get(k);
		    		    		
		    		    		inner_in.append("'"+temCodeMapping_inner.getTcmCode()+"'"+((k==(temCodeMapping_inner_size-1))?"":","));
		    		    		// inner_where.append("cdr_inner.tcdr_code like '"+temCodeMapping_inner_List.get(0).getTcmCode()+"'");
							}
		    		    	inner_where.append("cdr_inner.tcdr_code in ("+inner_in.toString()+")");
		    		    }else if(temCodeMapping_inner_size==1){ 
		    		    	if(temCodeMapping_inner_List.get(0).getTcmMode().equals("1")){
		    		    		inner_where.append("cdr_inner.tcdr_code like '"+temCodeMapping_inner_List.get(0).getTcmCode()+"'");
		    		    	}else if(temCodeMapping_inner_List.get(0).getTcmMode().equals("2")){
		    		    		inner_where.append("cdr_inner.ttId=1");
		    		    	}else{  
		    		    		inner_where.append("cdr_inner.tcdr_code='"+temCodeMapping_inner_List.get(0).getTcmCode()+"'");
		    		    	}
		    		    	
		    		    }
		    		    query_inner.setLength(0);
		    		    query_inner.append(" SELECT   "+temCodeGroup_inner.getTcgColumn()+
				   				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_inner    "+ 
				 				  " where cdr_inner.tcdrMsisdnFrom "+
				 				  " ='"+msisdn+"' "+
				 				  " and   "+ inner_where+
				 				  " and cdr_inner.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+  
				 				 // " and cdr_5.ttId=1 "+
				 				  "    ");
		    			//if(temCodeMapping_inner_List.size())
				 			//	  System.out.println("xx->"+query_inner.toString());
		    			query =em.createNativeQuery( query_inner.toString());
		    			obj= query.getSingleResult(); 
		    			
		    			columns_data[j]=obj!=null?(String)obj:"";
		    			//System.out.println(temCodeGroup_inner.getTcgName()+","+columns_data[j]);
					}
		    		reportTemplate.setColumns(columns_data);
		    		/*// in Group
		    		 query =em.createNativeQuery( " SELECT format(sum(cdr_5.TCDR_USED_COUNT)/60 ,2) "+
		   				  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_5    "+
		 				 
		 				  " where cdr_5.tcdrMsisdnFrom "+
		 				  " ='"+msisdn+"' "+
		 				  " and ( cdr_5.tcdr_code='RCGV42' or  cdr_5.tcdr_code='RCGC42' ) "+ 
		 				  " and cdr_5.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+  
		 				  " and cdr_5.ttId=1 "+
		 				  "    ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallInGroup(obj!=null?(String)obj:"");
		    		
		    		// ext_group
		    		query =em.createNativeQuery( "  SELECT format(sum(cdr_4.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_4   "+
					 
					  " where cdr_4.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_4.tcdr_code like 'RVCNW%' "+ 
					  " and cdr_4.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  " and cdr_4.ttId=1 "+
					  "   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallExtGroup(obj!=null?(String)obj:"");
		    		
		    		// call_all
		    		query =em.createNativeQuery( "SELECT format(sum(cdr_0.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_0    "+
					  " where cdr_0.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_0.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  " and cdr_0.ttId=1    ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallAll(obj!=null?(String)obj:"");
		    		
		    		// sms
		    		query =em.createNativeQuery( "SELECT sum(cdr_1.TCDR_USED_COUNT) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_1 "+ 
					  " where cdr_1.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_1.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  //" and ( cdr_1.ttId=2  or  cdr_1.tcdr_code like 'R1%' ) ");
		    				" and   cdr_1.tcdr_code like 'R1%'  ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setSms(obj!=null?(myFormatter.format((Double)obj)):"");
		    		
		    		// gprs_mb
		    		query =em.createNativeQuery( "SELECT format(sum(cdr_2.GPRS_PRD_QNT)/1048576,2) "+  
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_2   "+
					  " where cdr_2.tcdrMsisdnFrom"+
					  " ='"+msisdn+"' "+
					  " and cdr_2.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  " and (cdr_2.ttId=4 or cdr_2.ttId=5) and cdr_2.tcdr_code like 'RGPRS%'   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setGprsMB(obj!=null?(String)obj:"");
		    		
		    		// gprs_min
		    		query =em.createNativeQuery( "SELECT format(sum(cdr_3.TCDR_USED_COUNT)/60,2) "+ 
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_3   "+
					  " where cdr_3.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_3.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
					  " and cdr_3.ttId=3 and cdr_3.tcdr_code like 'RGPRS%'   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setGprsMIN(obj!=null?(String)obj:"");
		    		
		    		// close_group
		    		query =em.createNativeQuery( " SELECT format(sum(cdr_6.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_6   "+
					  " where cdr_6.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_6.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
					  " and cdr_6.ttId=1 "+
					  " and ( cdr_6.tcdr_code='RVTA14' or cdr_6.tcdr_code='RVT2X4' )  "+
					  "   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallCloseGroup(obj!=null?(String)obj:"");
		    		
		    		// specail_group
		    		query =em.createNativeQuery( " SELECT format(sum(cdr_7.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_7   "+
					  " where cdr_7.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_7.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
					  " and cdr_7.ttId=1 "+
					  " and ( cdr_7.tcdr_code = 'RSNC54' or  cdr_7.tcdr_code = 'RSNC42' )"+
					  "   ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallSpecailGroup(obj!=null?(String)obj:"");
		    		
		    		// content
		    		query =em.createNativeQuery(" SELECT format(sum(cdr_8.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_8   "+
					  " where cdr_8.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+ 
					  " and cdr_8.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
					  " and cdr_8.ttId=3 "+
					  " and ( cdr_8.tcdr_code in ( 'R12681' ,'R14942','R10370','R12139')   )"+
					  " ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setAmt(obj!=null?(String)obj:"");
		    		
		    		// specail_group_ext
		    		query =em.createNativeQuery(" SELECT format(sum(cdr_9.TCDR_USED_COUNT)/60 ,2) "+
					  " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_9   "+
					  " where cdr_9.tcdrMsisdnFrom "+
					  " ='"+msisdn+"' "+
					  " and cdr_9.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+
					  " and cdr_9.ttId=1 "+
					  " and ( cdr_9.tcdr_code like 'RSNC%' or  cdr_9.tcdr_code like 'RSNP%' ) and cdr_9.tcdr_code != 'RSNC54' and cdr_9.tcdr_code != 'RSNC42' "+
					  "     ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setCallSpecailGroupExt(obj!=null?(String)obj:"");
		    		
		    		// mms
		    		query =em.createNativeQuery(" SELECT sum(cdr_10.TCDR_USED_COUNT) "+
		    		 " FROM BLUECODE_DB.TEM_CALL_DETAIL_RECORD cdr_10 "+ 
		    		 " where cdr_10.tcdrMsisdnFrom  "+
		    		  " ='"+msisdn+"' "+
		    		 " and cdr_10.tcdr_bill_cycle between '"+query_date_to+"' and '"+query_date_end+"' "+ 
		    		 " and cdr_10.ttId=6    ");
		    		obj= query.getSingleResult(); 
		    		reportTemplate.setMms(obj!=null?(myFormatter.format((Double)obj)):"");
		    		*/
		    		reportTemplates.add(reportTemplate);
				}
		    	
		    }
		catch (RuntimeException e) {
		   e.printStackTrace();
		}
		finally {
		    em.close();
		}
		    mobileTemplate.setReportTemplates(reportTemplates);
	return mobileTemplate;
	} 
}
