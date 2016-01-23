package org.idsiom.utilbet.util;

import java.util.Calendar;
import java.util.Date;

public class UtilFecha {
	// Suma los días recibidos a la fecha  
	 public static Date sumarRestarDiasFecha(Date fecha, int dias){
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); // Configuramos la fecha que se recibe
	      calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
	      return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	 }
}
