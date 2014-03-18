package th.co.aoe.imake.thebluecode.backoffice.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.aoe.imake.thebluecode.backoffice.domain.TemCallDetailRecord;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemCompany;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemGroup;
import th.co.aoe.imake.thebluecode.backoffice.domain.TemMsIsdn;
import th.co.aoe.imake.thebluecode.backoffice.dto.MobileTemplate;
import th.co.aoe.imake.thebluecode.backoffice.dto.ReportTemplate;
import th.co.aoe.imake.thebluecode.backoffice.service.TheBlueCodeService;
import th.co.imake.tem.migratedata.form.CDRTemplate;
import th.co.imake.tem.migratedata.form.GroupTemplate;
@Service
@Transactional
public class TheBlueCodeServiceImpl_BK implements TheBlueCodeService {
	// BMT idiom
	/*@Resource public Transaction utx;
	@Resource public EntityManagerFactory factory;*/
//	@Autowired
	@PersistenceUnit(unitName = "hibernatePersistenceUnit") 
    private EntityManagerFactory entityManager;
	/* //@PersistenceContext
	  private EntityManager em;
	 
	 @PersistenceContext
	 public void setEntityManager(EntityManager em) {
	 this.em = em;
	 }
	 */
	 //@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor={RuntimeException.class})
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	@Transactional(readOnly=true)
	public List<TemCallDetailRecord> getTemCallDetailRecord() {
		 EntityManager em = entityManager.createEntityManager();
		 System.out.println("em="+em);
		 EntityTransaction tx=em.getTransaction();
		 System.out.println("tx="+em);
		 tx.begin();
		    try {
		    	Query query =em.createQuery("select temCallDetailRecord from TemCallDetailRecord temCallDetailRecord");
		    	List list=query.getResultList();
		    	System.out.println(list.size());
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
		//Changeset 39 | Source/SVN | Assembla 
		
		//EntityManager session = entityManager.gett.createEntityManager(); 
	/*	EntityTransaction tx=em.getTransaction();
		tx.begin(); 
			Query query =em.createQuery("select temCallDetailRecord from TemCallDetailRecord temCallDetailRecord");
		System.out.println(query);
		List list =query.getResultList();
		//entityManager.persist(employee);
		tx.commit();
		System.out.println(list.size());*/
		/*try{
		em.getTransaction().begin();
		//System.out.println(tx);
		Query query = em.createQuery("from TemCallDetailRecord as p ");
	      // query.setParameter("category", category);
		List list = query.getResultList(); 
		System.out.println(list.size());
		em.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		*/
		return null;
		 //return entityManagerFactory.createEntityManager().createQuery("SELECT a FROM Account").getResultList();.
		
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
	public int[] importCDR(List<CDRTemplate> temCallDetailRecords) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int[] importGroup(List<GroupTemplate> temGroups) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TemGroup> getGroup() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TemCompany> getTemCompanyByGroup(String tgName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TemMsIsdn> getTemMsIsdnByCompany(Integer tcId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ReportTemplate> listReportTemplates(Integer tcId,Date billCycle,Integer provider){
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String[]> getBillCycle(Integer tcId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String[]> listProvider(Integer tcId, Date billCycle) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MobileTemplate listMobileReportTemplates(Integer tcId,
			Date billCycle, Integer provider) {
		// TODO Auto-generated method stub
		return null;
	}

	/*public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}*/

	/*public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}*/
}
