package th.co.aoe.imake.thebluecode.backoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEM_GROUP")
public class TemGroup  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "TG_NAME", unique=true)
	private String tgName;

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}
	
	
}
