package th.co.aoe.imake.thebluecode.backoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "TEM_PROVIDER")
public class TemProvider implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "TP_ID")
	private Integer tpId;
	@Column(name = "TP_NAME")
	private String tpName;
	public TemProvider(){
	}
	public TemProvider(Integer tpId, String tpName) {
		super();
		this.tpId = tpId;
		this.tpName = tpName;
	}

	public Integer getTpId() {
		return tpId;
	}

	public void setTpId(Integer tpId) {
		this.tpId = tpId;
	}

	public String getTpName() {
		return tpName;
	}

	public void setTpName(String tpName) {
		this.tpName = tpName;
	}

}
