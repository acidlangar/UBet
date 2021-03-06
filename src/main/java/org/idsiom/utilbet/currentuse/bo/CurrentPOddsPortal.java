package org.idsiom.utilbet.currentuse.bo;

import java.io.Serializable;
import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.MINS_PROXIMIDAD;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.currentuse.interlocutor.exception.ResultPartidoInesperadoException;


public class CurrentPOddsPortal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 875086680033941969L;


	private static Logger logger = Logger.getLogger(CurrentPOddsPortal.class);
	
	
	private String country;
	
	private String league;
	
	//private String temp;
	
	private String fecha;
	
	//private String eqL;
	
	private String equipos;

	private Integer gL;   // = 2 
	
	private Integer gV;   // = 3
	
	private char resultFinal; // 1,X,2      O eb cualquier de los otros casos
	
	private String  rStr; // = 2:1 ET
	
	private Double c1;    // = 2.39
	
	private Double cX;    // = 3.00
	
	private Double c2;    // = 2.68

	public String getEquipos() {
		return equipos;
	}

	public void setEquipos(String equipos) {
		this.equipos = equipos;
	}

	public String getFecha() {
		return fecha;
	}
	
	public GregorianCalendar getFechaGC() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm");
		GregorianCalendar result = new GregorianCalendar();
		
		
		try {
            // En caso que la fecha no cumple con el formato, lo mas probable es que el juego est� 
			// en curso, con una fecha como la siguiente: "20160202 73'"
			if(fecha.length() != 14) {
				// Si no cumple el formato, se devuelve un Long que asegure que no se tome en cuenta el juego
				result.setTimeInMillis((new Date()).getTime() + (3*MINS_PROXIMIDAD * 60 * 1000));
				return result;
			} else {
				// En caso de cumplir el formato, se convierte la fecha y se devuelve la misma
				Date date = (Date) formatter.parse(fecha);
				result.setTime(date);
				
				return result;
			}
			
        } catch (ParseException e) {
        	logger.error(e,e);
        	return null;
        }
	}
	
	public Long getFechaLong() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm");
		
		try {
            // En caso que la fecha no cumple con el formato, lo mas probable es que el juego est� 
			// en curso, con una fecha como la siguiente: "20160202 73'"
			if(fecha.length() != 14) {
				// Si no cumple el formato, se devuelve un Long que asegure que no se tome en cuenta el juego
				return (new Date()).getTime() + (3*MINS_PROXIMIDAD * 60 * 1000);
			} else {
				// En caso de cumplir el formato, se convierte la fecha y se devuelve la misma
				Date date = (Date) formatter.parse(fecha);
	            return date.getTime();
			}
			
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
	
	public String getEquipoLocal() {
		return this.equipos.split("-")[0];
	}

	
	public String getEquipoVisitante() {
		return this.equipos.split("-")[1];
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Integer getgL() {
		return gL;
	}

	public Integer getgV() {
		return gV;
	}

	public String getrStr() {
		return rStr;
	}

	public void setrStr(String rStr) throws ResultPartidoInesperadoException {
		this.rStr = rStr;
		
		// Interpretando el resultado para asignar goles y resultado final
		
		if( rStr.equalsIgnoreCase("postp.") || rStr.equalsIgnoreCase("canc.") || !rStr.contains(":") ) {
			this.gL = -1;
			this.gV = -1;
			this.resultFinal = 'O';
		} else {
			String arrayAux[] = this.rStr.split(" ");
			if(arrayAux.length > 1) {
				if( arrayAux[1].equals("ET") || arrayAux[1].equals("pen.")) {
					this.gL = 0;
					this.gV = 0;
					
					if(this.gL > this.gV) {
						this.resultFinal = '1';
					} else if(this.gL < this.gV) {
						this.resultFinal = '2';
					} else {
						this.resultFinal = 'X';
					}
				} else {
					throw new ResultPartidoInesperadoException("Resultado no esperado :: " + this.rStr);
				}
			} else {
				String array[] = this.rStr.split(":");
				this.gL = Integer.parseInt(array[0]);
				this.gV = Integer.parseInt(array[1]);
				
				if(this.gL > this.gV) {
					this.resultFinal = '1';
				} else if(this.gL < this.gV) {
					this.resultFinal = '2';
				} else {
					this.resultFinal = 'X';
				}	
			}
			
			
		}
		
		
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
	
	public char getResultFinal() {
		return this.resultFinal;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		sb.append("country = ");
		sb.append(this.country);
		sb.append("; league = ");
		sb.append(this.league);
		sb.append("; fecha = ");
		sb.append(this.fecha);
		sb.append(", equipos = ");
		sb.append(this.equipos);
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
		sb.append(", rseult = ");
		sb.append(this.resultFinal);
		
		result = sb.toString();
		sb = null;
		
		return result;
		
	}
	
	public String getCSVFormat() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		Locale locale = new Locale("es","VE");
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(locale);


		sb.append("\"");
		sb.append(this.fecha);
		sb.append("\",");
		
		sb.append("\"");
		sb.append(this.country);
		sb.append("\",");
		
		sb.append("\"");
		sb.append(this.league);
		sb.append("\",");
		
		sb.append("\"");
		sb.append(this.equipos);
		sb.append("\",");
		
		sb.append("\"");
		if(this.rStr != null) {
			sb.append(this.rStr);
		} else {
			sb.append(" - ");
		}
		
		sb.append("\",");
		
		sb.append("\"");
		sb.append(this.resultFinal);
		sb.append("\",");
		
		sb.append("\"");
		if( this.gL != null ) {
			sb.append(this.gL);
		} else {
			sb.append("-1");
		}
		sb.append("\",");
		
		sb.append("\"");
		if( this.gV != null ) {
			sb.append(this.gV);
		} else {
			sb.append("-1");
		}
		sb.append("\",");
		
		sb.append("\"");
		sb.append(df.format(this.c1));
		sb.append("\",");
		
		sb.append("\"");
		sb.append(df.format(this.cX));
		sb.append("\",");
		
		sb.append("\"");
		sb.append(df.format(this.c2));
		sb.append("\"");
		
		result = sb.toString();
		sb = null;
		
		return result;
		
	}

	public TipoPartidoPorCuotas getType() {
		return TipoPartidoPorCuotas.getClasificacionPorCuotas(c1, cX, c2);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CurrentPOddsPortal) {
			CurrentPOddsPortal p = (CurrentPOddsPortal)obj;
			if(p.getCountry().trim().equals(this.country.trim()) 
					&& p.getFecha().trim().equals(this.getFecha().trim()) 
					          && p.getEquipos().trim().equals(this.getEquipos().trim())) {
				System.out.println("Equals OP return true");
				return true;
			} else {
				System.out.println("Equals OP return false 1");
				return false;
			}
		} else {
			System.out.println("Equals OP return false 2");
			return false;
		}
	}

	
	
}
