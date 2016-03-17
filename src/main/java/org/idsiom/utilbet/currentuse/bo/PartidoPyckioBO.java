package org.idsiom.utilbet.currentuse.bo;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.MINS_PROXIMIDAD;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.idsiom.utilbet.currentuse.util.LevenshteinDistance;

public class PartidoPyckioBO implements Serializable, Comparable<PartidoPyckioBO> {
    private static final long serialVersionUID = 6171090211553693547L;

	private String fechaStr;
     
     private String deporte;
     
     private String pais;
     
     private String liga;
     
     private String equipoLocal;
     
     private String equipoVisitante;
     
     private Integer valueDist;
     
     /*
     public Long getFechaLong() {
 		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm");
 		
 		try {
             // En caso que la fecha no cumple con el formato, lo mas probable es que el juego está 
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
         	e.printStackTrace();
         	return null;
         }

 	}
    */

	public String getFechaStr() {
		return fechaStr;
	}

	public void setFechaStr(String fechaStr) {
		this.fechaStr = fechaStr;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public String getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(String equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public String getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(String equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}
	
	public Integer getValueDist() {
		return valueDist;
	}

	public void setValueDist(Integer valueDist) {
		this.valueDist = valueDist;
	}

	@Override
    public String toString() {
		String result;
		StringBuffer sb = new StringBuffer();
		
		sb.append("<fecha = ");
		sb.append(this.fechaStr);
		sb.append(", pais = ");
		sb.append(this.pais);
		sb.append(", liga = ");
		sb.append(this.liga);
		sb.append(", EqLocal = ");
		sb.append(this.equipoLocal);
		sb.append(", EqVisitante = ");
		sb.append(this.equipoVisitante);
		sb.append(">");
		
		result = sb.toString();
		sb = null;
		
		return result;
    }

	public GregorianCalendar getFechaGC() {
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm");
		//EEEE MMMM d HH:mm:ss z yyyy
		//15 Mar 14:30
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM HH:mm");
		GregorianCalendar result = new GregorianCalendar();
		
		
		try {
            // En caso que la fecha no cumple con el formato, lo mas probable es que el juego está 
			// en curso, con una fecha como la siguiente: "20160202 73'"
			if(fechaStr.length() != 12) {
				// Si no cumple el formato, se devuelve un Long que asegure que no se tome en cuenta el juego
				result.setTimeInMillis((new Date()).getTime() + (3*MINS_PROXIMIDAD * 60 * 1000));
				return result;
			} else {
				// En caso de cumplir el formato, se convierte la fecha y se devuelve la misma
				Date date = (Date) formatter.parse(fechaStr);
				
				result.setTime(date);
				result.set(GregorianCalendar.YEAR, (new GregorianCalendar()).get(GregorianCalendar.YEAR));
				
				return result;
			}
			
        } catch (ParseException e) {
        	//logger.error(e,e);
        	e.printStackTrace();
        	return null;
        }
	}

	public int compareTo(PartidoPyckioBO arg0) {
		
		int otherVal = arg0.getValueDist();
		
		//ascending order
		return this.valueDist - otherVal;
		
		//descending order
		//return otherVal - this.valueDist;
	}

	public void calValDistancia(CurrentPOddsPortal pop) {
		int distanciaLocal, distanciaVisitante;
		
		distanciaLocal = LevenshteinDistance.computeLevenshteinDistance(this.equipoLocal.trim(), pop.getEquipoLocal().trim());
		distanciaVisitante = LevenshteinDistance.computeLevenshteinDistance(this.equipoVisitante.trim(), pop.getEquipoVisitante().trim());
		
		this.valueDist = distanciaLocal + distanciaVisitante;
	}
     
     
     
}
