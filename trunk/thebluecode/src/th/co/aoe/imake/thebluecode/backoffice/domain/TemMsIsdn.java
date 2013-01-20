package th.co.aoe.imake.thebluecode.backoffice.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "TEM_MSISDN")
public class TemMsIsdn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "MSISDN")
	private String msIsdn;
	@ManyToOne
	@JoinColumn(name = "TC_ID")
	private TemCompany temCompany;
	@ManyToOne
	@JoinColumn(name = "TP_ID")
	private TemProvider temProvider;
	@Column(name = "ON_THE_BILL")
	private Date onTheBill;

	public String getMsIsdn() {
		return msIsdn;
	}

	public void setMsIsdn(String msIsdn) {
		this.msIsdn = msIsdn;
	}

	public TemCompany getTemCompany() {
		return temCompany;
	}

	public void setTemCompany(TemCompany temCompany) {
		this.temCompany = temCompany;
	}

	public Date getOnTheBill() {
		return onTheBill;
	}

	public void setOnTheBill(Date onTheBill) {
		this.onTheBill = onTheBill;
	}

	public TemProvider getTemProvider() {
		return temProvider;
	}

	public void setTemProvider(TemProvider temProvider) {
		this.temProvider = temProvider;
	}
	
}
