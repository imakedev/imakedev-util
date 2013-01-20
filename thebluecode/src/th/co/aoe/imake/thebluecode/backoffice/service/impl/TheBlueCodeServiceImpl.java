package th.co.aoe.imake.thebluecode.backoffice.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import th.co.aoe.imake.thebluecode.backoffice.domain.TemCallDetailRecord;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemCallDetailRecordPk;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemCompany;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemGroup;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemMsIsdn;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemProvider;
import th.co.aoe.imake.thebluecode.backoffice.dto.ReportTemplate;
import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;
import th.co.imake.tem.migratedata.form.CDRTemplate;
import th.co.imake.tem.migratedata.form.GroupTemplate;
@Service
@Transactional
public class TheBlueCodeServiceImpl implements TheBlueCodeService { 
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
	public int importCDR(List<CDRTemplate> temCallDetailRecords) {
		// TODO Auto-generated method stub
		 EntityManager em = entityManager.createEntityManager(); 
		 EntityTransaction tx=em.getTransaction();
		 int i=0;
		 TemProvider provider_true=new TemProvider(1,"TRUE");
		 TemProvider provider_dtac=new TemProvider(2,"DTAC");
		 TemProvider provider_ais=new TemProvider(1,"AIS");
		 TemProvider provider_tot=new TemProvider(1,"TOT");
		 
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
				temCallDetailRecord.setTcdrMsIsdnTo(cdrTemplate.getMsIsdnTo());
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
				temCallDetailRecordPk.setTtId(1);
				temCallDetailRecord.setTemCallDetailRecordPk(temCallDetailRecordPk);
				temCallDetailRecord.setTcdrSource(0);
				temCallDetailRecord.setTcdrValue(cdrTemplate.getPrice());
				temCallDetailRecord.setTcdrCallTo(cdrTemplate.getMsIsdnToLocation());
			//	System.out.println("temCallDetailRecord ="+temCallDetailRecord);
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
		return i;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor={RuntimeException.class})
	public int importGroup(List<GroupTemplate> temGroups) {
		// TODO Auto-generated method stub
		 EntityManager em = entityManager.createEntityManager(); 
		 EntityTransaction tx=em.getTransaction();
		//System.out.println(tx);
		 int i=0;
	 try{
		 tx.begin();
		  
		 	for (GroupTemplate groupTemplate : temGroups) {
		 		TemGroup temGroup =new TemGroup();
		 		temGroup.setTgName(groupTemplate.getGroup());
		 		em.merge(temGroup);
		 		Query query =em.createQuery("select temCompany from TemCompany temCompany where temCompany.tcName=:tcName " +
		 				"and temCompany.tgName=:tgName");
		 		query.setParameter("tcName", groupTemplate.getCompany());
		 		query.setParameter("tgName", groupTemplate.getGroup());
		    	List obj=query.getResultList(); 
		    	if(obj.size()==0){
		    		TemCompany temCompany =new TemCompany();
			 		temCompany.setTcName(groupTemplate.getCompany());
			 		temCompany.setTgName(groupTemplate.getGroup());
			 		em.persist(temCompany);
		    	}
		    	 query =em.createQuery("select temCompany from TemCompany temCompany where temCompany.tcName=:tcName " +
			 				"and temCompany.tgName=:tgName");
			 		query.setParameter("tcName", groupTemplate.getCompany());
			 		query.setParameter("tgName", groupTemplate.getGroup());
			 		 obj=query.getResultList(); 
			 		 if(obj.size()>0){
			 			TemMsIsdn TemMsIsdn =new TemMsIsdn();
			 			TemMsIsdn.setMsIsdn(groupTemplate.getNo());
			 			TemMsIsdn.setTemCompany((TemCompany)obj.get(0));
			 			em.merge(TemMsIsdn);
			 		 }
			 		if ((i % 500) == 0) {
			 			 tx.commit();
				          em.clear();          
				          tx.begin();
				      }
			 		i++;
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
		return i;
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
	public List<ReportTemplate> listReportTemplates(String tgName,Integer tcId,String msIsdn){		
		// TODO Auto-generated method stub
		List<ReportTemplate>  reportTemplates= null;
		StringBuffer sb=new StringBuffer();
		
		sb.append("select t1.tc_group_name group_from," +
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
				"        tcdr.tcdr_value " +
				"		from TEM_CALL_DETAIL_RECORD tcdr left join" +
				"	(select isdn.msisdn, company.tc_id, company.tc_name," +
				"     company.tc_group_name,provider.tp_name " +
				"     from TEM_MSISDN isdn left join TEM_COMPANY company " +
				"     on isdn.tc_id=company.tc_id left join TEM_PROVIDER provider " +
				"     on isdn.tp_id=provider.tp_id) t1 on " +
				"	 tcdr.tcdrmsisdnfrom=t1.msisdn left join" +
				"	(select isdn.msisdn, company.tc_name, company.tc_group_name," +
				"     provider.tp_name from TEM_MSISDN isdn left join " +
				"    TEM_COMPANY company on isdn.tc_id=company.tc_id left join " +
				"	TEM_PROVIDER provider on isdn.tp_id=provider.tp_id) t2 on " +
				"    tcdr.tcdr_msisdn_to=t2.msisdn where tcdr.ttid=1 and " +
				"	tcdr.tcdr_source=0 ");
		
		    if(tgName!=null && tgName.trim().length()>0){
		    	sb.append("   and t1.tc_group_name=:tgName");
		    }
		    if(tcId!=null && tcId.intValue()!=0){
		    	sb.append("   and t1.tc_id=:tcId");
		    }
		    if(msIsdn!=null && msIsdn.trim().length()>0){
		    	sb.append("   and tcdr.tcdrMsIsdnFrom=:msIsdn");
		    }
		    /*and t1.tc_group_name='กลุ่ม C' and  t1.tc_id=3  and tcdr.tcdrMsIsdnFrom='057777777'
		    		   and t1.tp_name='AIS'*/
			List list=null;
			DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("th","TH"));
			Calendar calendar = new GregorianCalendar(new Locale("th","TH"));
		 
	EntityManager em = entityManager.createEntityManager();  
		    try {
		    	Query query =em.createNativeQuery(sb.toString());
		    	if(tgName!=null && tgName.trim().length()>0){
		    		query.setParameter("tgName", tgName); 
			    }
			    if(tcId!=null && tcId.intValue()!=0){
			    	query.setParameter("tcId", tcId.intValue());  
			    }
			    if(msIsdn!=null && msIsdn.trim().length()>0){
			    	query.setParameter("msIsdn", msIsdn);   
			    }
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
}
