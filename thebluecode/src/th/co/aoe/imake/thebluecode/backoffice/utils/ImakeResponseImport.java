package th.co.aoe.imake.thebluecode.backoffice.utils;

import java.io.Serializable;

public class ImakeResponseImport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] headers;
	private XLSResponse[] xlsResponses;
	public String[] getHeaders() {
		return headers;
	}
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}
	public XLSResponse[] getXlsResponses() {
		return xlsResponses;
	}
	public void setXlsResponses(XLSResponse[] xlsResponses) {
		this.xlsResponses = xlsResponses;
	}
	

}
