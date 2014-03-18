package th.co.aoe.imake.thebluecode.backoffice.dto;

import java.io.Serializable;

public class TemCodeMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	private String tcgId;
	private String tcmCode;
	private String tcmMode;
	
	public String getTcgId() {
		return tcgId;
	}
	public void setTcgId(String tcgId) {
		this.tcgId = tcgId;
	}
	public String getTcmCode() {
		return tcmCode;
	}
	public void setTcmCode(String tcmCode) {
		this.tcmCode = tcmCode;
	}
	public String getTcmMode() {
		return tcmMode;
	}
	public void setTcmMode(String tcmMode) {
		this.tcmMode = tcmMode;
	} 
	
}
