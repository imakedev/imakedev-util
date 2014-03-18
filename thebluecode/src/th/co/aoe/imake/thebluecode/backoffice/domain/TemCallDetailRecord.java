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
	
	@Column(name = "TCDR_TYPE")
	private String tcdrType;
	
	@Column(name = "GPRS_PRD_QNT")
	private Double gprsPrdQnt;
	
	@Column(name = "TCDR_CODE")
	private String featureCode;
	
	@Column(name = "TCDR_DATA_TYPE")
	private String tcdrDataType;
	
	@Column(name = "TCDR_AMT")
	private Double tcdrAmt;
	
	public String getTcdrDataType() {
		return tcdrDataType;
	}

	public void setTcdrDataType(String tcdrDataType) {
		this.tcdrDataType = tcdrDataType;
	}

	@Column(name = "TCDR_DURATION_TIME")
	private String durationTime;
	
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

	public String getTcdrType() {
		return tcdrType;
	}

	public void setTcdrType(String tcdrType) {
		this.tcdrType = tcdrType;
	}

	public Double getGprsPrdQnt() {
		return gprsPrdQnt;
	}

	public void setGprsPrdQnt(Double gprsPrdQnt) {
		this.gprsPrdQnt = gprsPrdQnt;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	public Double getTcdrAmt() {
		return tcdrAmt;
	}

	public void setTcdrAmt(Double tcdrAmt) {
		this.tcdrAmt = tcdrAmt;
	}
	

//	public TemType getTemType() {
//		return temType;
//	}
//
//	public void setTemType(TemType temType) {
//		this.temType = temType;
//	}

}
