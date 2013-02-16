package th.co.aoe.imake.thebluecode.backoffice.form;

import java.io.Serializable;

public class ReportForm  extends CommonForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String tgName;
	private Integer tcId;
	private String billCycle;
	private String provider;
	public String getTgName() {
		return tgName;
	}
	public void setTgName(String tgName) {
		this.tgName = tgName;
	}
	public Integer getTcId() {
		return tcId;
	}
	public void setTcId(Integer tcId) {
		this.tcId = tcId;
	}
	public String getBillCycle() {
		return billCycle;
	}
	public void setBillCycle(String billCycle) {
		this.billCycle = billCycle;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	} 
	
}
