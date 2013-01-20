package th.co.aoe.imake.thebluecode.backoffice.utils;

import java.io.Serializable;

public class XLSResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String[] columns;

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	
}
