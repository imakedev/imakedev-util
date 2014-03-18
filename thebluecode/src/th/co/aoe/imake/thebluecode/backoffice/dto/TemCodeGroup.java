package th.co.aoe.imake.thebluecode.backoffice.dto;

import java.io.Serializable;
import java.util.List;

public class TemCodeGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private String tcgId;
	private String tcgProvider;
	private String tcgName;
	private String tcgOrder;
	private String tcgStatus;
	private String tcgColumn;
	private String tcgIsSum;
	private List<TemCodeMapping> temCodeMappings;
	
	public String getTcgId() {
		return tcgId;
	}
	public void setTcgId(String tcgId) {
		this.tcgId = tcgId;
	}
	public String getTcgProvider() {
		return tcgProvider;
	}
	public void setTcgProvider(String tcgProvider) {
		this.tcgProvider = tcgProvider;
	}
	public String getTcgName() {
		return tcgName;
	}
	public void setTcgName(String tcgName) {
		this.tcgName = tcgName;
	}
	public String getTcgOrder() {
		return tcgOrder;
	}
	public void setTcgOrder(String tcgOrder) {
		this.tcgOrder = tcgOrder;
	}
	public String getTcgStatus() {
		return tcgStatus;
	}
	public void setTcgStatus(String tcgStatus) {
		this.tcgStatus = tcgStatus;
	}
	public String getTcgColumn() {
		return tcgColumn;
	}
	public void setTcgColumn(String tcgColumn) {
		this.tcgColumn = tcgColumn;
	}
	public List<TemCodeMapping> getTemCodeMappings() {
		return temCodeMappings;
	}
	public void setTemCodeMappings(List<TemCodeMapping> temCodeMappings) {
		this.temCodeMappings = temCodeMappings;
	}
	public String getTcgIsSum() {
		return tcgIsSum;
	}
	public void setTcgIsSum(String tcgIsSum) {
		this.tcgIsSum = tcgIsSum;
	}
	
}
