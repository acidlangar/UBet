package org.idsiom.utilbet.history.fromoddsportal.bo;

import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.history.fromoddsportal.dao.OddsPortalDAOImpl;

/**
 * Objeto que representa a un partido proveniente de la pagina oddsportal.com
 * debe contener desde los equipos, las cuotas, como los goles.
 * */
public class POddsPortalBO implements Serializable {
	
	private static Logger logger = Logger.getLogger(POddsPortalBO.class);
	
	private static final long serialVersionUID = -7012231608728211091L;

	private String country;
	
	private String league;
	
	private String temp;
	
	private String fecha;
	
	private String eqL;
	
	private String eqV;

	private Integer gL;   // = 2 
	
	private Integer gV;   // = 3
	
	private String  rStr; // = 2:1 ET
	
	private Double c1;    // = 2.39
	
	private Double cX;    // = 3.00
	
	private Double c2;    // = 2.68

	public String getEqL() {
		return eqL;
	}

	public void setEqL(String eqL) {
		this.eqL = eqL;
	}

	public String getEqV() {
		return eqV;
	}

	public void setEqV(String eqV) {
		this.eqV = eqV;
	}

	public String getFecha() {
		return fecha;
	}
	
	public Long getFechaLong() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM YYYY HH:mm", new Locale("en", "EN"));
		
		try {

            Date date = (Date) formatter.parse(fecha);
            
            return date.getTime();

        } catch (ParseException e) {
        	logger.error(e,e);
        	return null;
        }

	}
	
	public String getFechaFormatoBD() {
		int dia = 0;
		int mes = 1;
		int ano = 2;
		int hora = 3;
		String sep = " ";
		String f = fecha;
		
		f = f.split(sep)[mes] + "/" + f.split(sep)[dia] + "/" + f.split(sep)[ano] + " " + f.split(sep)[hora];
		f = f.replaceFirst("Jan/", "01/");
		f = f.replaceFirst("Feb/", "02/");
		f = f.replaceFirst("Mar/", "03/");
		f = f.replaceFirst("Apr/", "04/");
		f = f.replaceFirst("May/", "05/");
		f = f.replaceFirst("Jun/", "06/");
		f = f.replaceFirst("Jul/", "07/");
		f = f.replaceFirst("Aug/", "08/");
		f = f.replaceFirst("Sep/", "09/");
		f = f.replaceFirst("Oct/", "10/");
		f = f.replaceFirst("Nov/", "11/");
		f = f.replaceFirst("Dec/", "12/");
		
		return f;

	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Integer getgL() {
		return gL;
	}

	public void setgL(Integer gL) {
		this.gL = gL;
	}

	public Integer getgV() {
		return gV;
	}

	public void setgV(Integer gV) {
		this.gV = gV;
	}

	public String getrStr() {
		return rStr;
	}

	public void setrStr(String rStr) {
		this.rStr = rStr;
	}

	public Double getC1() {
		return c1;
	}

	public void setC1(Double c1) {
		this.c1 = c1;
	}

	public Double getcX() {
		return cX;
	}

	public void setcX(Double cX) {
		this.cX = cX;
	}

	public Double getC2() {
		return c2;
	}

	public void setC2(Double c2) {
		this.c2 = c2;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		sb.append("country = ");
		sb.append(this.country);
		sb.append("; league = ");
		sb.append(this.league);
		sb.append("; temp = ");
		sb.append(this.temp);
		sb.append("; fecha = ");
		sb.append(this.fecha);
		sb.append(", eqL = ");
		sb.append(this.eqL);
		sb.append(", eqV = ");
		sb.append(this.eqV);
		sb.append(", gL = ");
		sb.append(this.gL);
		sb.append(", gV = ");
		sb.append(this.gV);
		sb.append(", c1 = ");
		sb.append(this.c1);
		sb.append(", cX = ");
		sb.append(this.cX);
		sb.append(", c2 = ");
		sb.append(this.c2);
		sb.append(", rStr = ");
		sb.append(this.rStr);
		
		result = sb.toString();
		sb = null;
		
		return result;
		
	}
}
