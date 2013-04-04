package th.co.aoe.imake.thebluecode.backoffice.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEM_CALL_DETAIL_RECORD")
public class TemCallDetailRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private TemCallDetailRecordPk temCallDetailRecordPk;
//	@ManyToOne
	
	@Column(name = "TCDR_USED_COUNT")
	private Double tcdrUsedCount;
	@Column(name = "TCDR_SOURCE")
	private Integer tcdrSource;
	@Column(name = "TCDR_VALUE")
	private Double tcdrValue;
	@Column(name = "TCDR_CALL_TO")
	private String tcdrCallTo;
	
	@Column(name = "TCDR_BILL_CYCLE")
	private Timestamp tcdrBillCycle; 
//	@ManyToOne
//	@Column(name = "TT_ID")
//	private TemType temType;

	public TemCallDetailRecordPk getTemCallDetailRecordPk() {
		return temCallDetailRecordPk;
	}

	public void setTemCallDetailRecordPk(
			TemCallDetailRecordPk temCallDetailRecordPk) {
		this.temCallDetailRecordPk = temCallDetailRecordPk;
	}

	/*public String getTcdrMsIsdnTo() {
		return tcdrMsIsdnTo;
	}

	public void setTcdrMsIsdnTo(String tcdrMsIsdnTo) {
		this.tcdrMsIsdnTo = tcdrMsIsdnTo;
	}*/

	public Double getTcdrUsedCount() {
		return tcdrUsedCount;
	}

	public void setTcdrUsedCount(Double tcdrUsedCount) {
		this.tcdrUsedCount = tcdrUsedCount;
	}

	public Integer getTcdrSource() {
		return tcdrSource;
	}

	public void setTcdrSource(Integer tcdrSource) {
		this.tcdrSource = tcdrSource;
	}

	public Double getTcdrValue() {
		return tcdrValue;
	}

	public void setTcdrValue(Double tcdrValue) {
		this.tcdrValue = tcdrValue;
	}

	public String getTcdrCallTo() {
		return tcdrCallTo;
	}

	public void setTcdrCallTo(String tcdrCallTo) {
		this.tcdrCallTo = tcdrCallTo;
	}

	public Timestamp getTcdrBillCycle() {
		return tcdrBillCycle;
	}

	public void setTcdrBillCycle(Timestamp tcdrBillCycle) {
		this.tcdrBillCycle = tcdrBillCycle;
	}

//	public TemType getTemType() {
//		return temType;
//	}
//
//	public void setTemType(TemType temType) {
//		this.temType = temType;
//	}

}
