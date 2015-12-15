package org.idsiom.utilbet.history.fromoddsportal.objutil;

public class LgTempObjUtil {
	private String country;
	
	private String lg;
	
	private String temp;
	
	public LgTempObjUtil(String country, String lg, String temp, Boolean dosAnios) {
		this.country = country;
		this.lg = lg;
		this.temp = temp;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLg() {
		return lg;
	}

	public void setLg(String lg) {
		this.lg = lg;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
		public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<");
		sb.append(this.country);
		sb.append("; ");
		sb.append(this.lg);
		sb.append("; ");
		sb.append(this.temp);
		sb.append(">");
		
		String result = sb.toString();
		sb = null;
		
		return result;
	}
	
	
}
