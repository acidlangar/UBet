/**
 * 
 */
package org.idsiom.utilbet.currentuse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.junit.Test;

/**
 * @author Edgar.Rodriguez
 *
 */
public class MainCurrentUse_DateTest {
	
	public List<CurrentPOddsPortal> createMockList() {
		
		return null;
		
	}
	
	@Test
	public void testWriteExcel() {
		
		System.out.println("Prueba date");
		String fechaDesdeStr;
		String fechaHastaStr;
		GregorianCalendar gcFDesde;
		GregorianCalendar gcFHasta;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		
		// pedir fecha desde 
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese la fecha desde yyyyMMdd: ");
		fechaDesdeStr = in.next();
		
		Date date;
		try {
			date = (Date) sdf.parse(fechaDesdeStr);
			gcFDesde = new GregorianCalendar();
			gcFDesde.setTime(date);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		
		// pedir fecha hasta
		System.out.println("Ingrese la fecha hasta yyyyMMdd: ");
		fechaHastaStr = in.next();
		
		try {
			date = (Date) sdf.parse(fechaHastaStr);
			gcFHasta = new GregorianCalendar();
			gcFHasta.setTime(date);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println( "fechaDesde: " + sdf.format(gcFDesde.getTime()) );
		System.out.println( "fechaHasta: " + sdf.format(gcFHasta.getTime()) );
		System.out.println( "fechaDesde: " + gcFDesde.getTime().getTime() );
		System.out.println( "fechaHasta: " + gcFHasta.getTime().getTime() );
		
		
		
		Boolean aux = gcFDesde.before(gcFHasta);
		
		System.out.println(aux);
		while( aux ) {
			System.out.println( sdf.format(gcFDesde.getTime()) );
			
			gcFDesde.add(GregorianCalendar.DAY_OF_YEAR,1);
			aux = gcFDesde.before(gcFHasta);
		}
		
		System.out.println("Fin de prueba");
	}

}
