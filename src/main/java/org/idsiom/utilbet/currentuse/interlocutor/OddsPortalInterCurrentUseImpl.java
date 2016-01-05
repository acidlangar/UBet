package org.idsiom.utilbet.currentuse.interlocutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.history.fromoddsportal.Cons;
import org.idsiom.utilbet.history.fromoddsportal.UtilSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OddsPortalInterCurrentUseImpl implements IOddsPortalCurrentUseInterlocutor {

	private WebDriver driver;

	static Logger logger = Logger.getLogger(OddsPortalInterCurrentUseImpl.class);
	
	public List<CurrentPOddsPortal> getPs() throws Exception {
		String urlBaseInicial = getUrlFinal();
		boolean pedirentero;
		this.driver = UtilSelenium.getInstanciaWD();
		String urlMyMatches = Cons.URL_MYMATCHES_CURRENT; // Guardara el String del URL del dia especifico
		String fechaDesdeStr;
		String fechaHastaStr;
		GregorianCalendar gcFDesde;
		GregorianCalendar gcFHasta;
		Scanner in ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//GregorianCalendar gc = new GregorianCalendar();
		
		List<CurrentPOddsPortal> resultFinal = new ArrayList<CurrentPOddsPortal>();
		
		System.out.println("buscando :: " + urlBaseInicial);
		this.driver.get(urlBaseInicial);
		
		boolean seguir = false;
		
		System.out.println("1 para continuar... ");

		int iSeguir = 0;
		
		in = new Scanner(System.in);
		
		pedirentero = true;
		while(pedirentero) {
			try {
				iSeguir = in.nextInt();
				pedirentero = false;
			} catch(java.util.InputMismatchException emm){
				System.out.println("Error de ingreso de datos, debe ser cero o uno. Linea 1");
				pedirentero = true;
			}
		}
		
		seguir = ( iSeguir == 1 );
		
		System.out.println("Ingrese la fecha desde yyyyMMdd: ");
		fechaDesdeStr = in.next();
		
		Date date;
		try {
			date = (Date) sdf.parse(fechaDesdeStr);
			gcFDesde = new GregorianCalendar();
			gcFDesde.setTime(date);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
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
			return null;
		}
		
		System.out.println( "fechaDesde: " + sdf.format(gcFDesde.getTime()) );
		System.out.println( "fechaHasta: " + sdf.format(gcFHasta.getTime()) );
		System.out.println( "fechaDesde: " + gcFDesde.getTime().getTime() );
		System.out.println( "fechaHasta: " + gcFHasta.getTime().getTime() );
		
		
		
		while( gcFDesde.before(gcFHasta) ) {
			System.out.println( sdf.format(gcFDesde.getTime()) );

			resultFinal.addAll( this.getPs(sdf.format(gcFDesde.getTime()), urlMyMatches + sdf.format(gcFDesde.getTime()) + "/") );

			gcFDesde.add(GregorianCalendar.DAY_OF_YEAR,1);
		}
		
		
		return resultFinal;
	}
	
	
	private List<CurrentPOddsPortal> getPs(String fechaStr, String urlDay) throws Exception {
		
		this.driver.get(urlDay);
		
		// Todos los tr de la tabla partidos
		String xPathPag = ".//*[@id='table-matches']/*/*/tr";  // .//*[@id='table-matches']/*/*/tr
		
		
		List<WebElement> listTRs = this.driver.findElements(By.xpath(xPathPag));
		
		List<WebElement> listTHs;
		List<WebElement> listTDs;
		
		
		List<CurrentPOddsPortal> partidosResult = new ArrayList<CurrentPOddsPortal>();
		
		String pais = null;
		String liga = null;
		CurrentPOddsPortal bo = new CurrentPOddsPortal();
		
		for(WebElement tr : listTRs) {
			listTHs = tr.findElements(By.tagName("th"));
			System.out.println(" ");
			if(listTHs != null && listTHs.size() > 0) {
				logger.info("Es un TITULO y tiene : " + listTHs.size() + "   ::  " + tr.getText());
				
				//  
				String aux = tr.getText().trim();
				String array[] = aux.split("\n»\n");
				
				/*
				tituloAs = tr.findElements(By.tagName("a"));
				System.out.println( "size = " + tituloAs.size() );
				*/
				if( array.length >= 2 ) {
					pais = array[0].trim();
					liga = array[1].trim();
				} else if( array.length == 1 ) {
					liga = array[0].trim();
				} else {
					liga = null;
					pais = null;
				}
				
				
				
				
			} else {
				listTDs = tr.findElements(By.tagName("td"));
				System.out.println("Es un JUEGO y tiene : " + listTDs.size());
				System.out.println(tr.getText());
				
				bo = new CurrentPOddsPortal();
				
				bo.setCountry(pais);
				bo.setLeague(liga);
				//bo.setFecha(fechaStr);
				
				if(listTDs.size() == 6) {  // Juego planificado
					bo.setFecha( fechaStr + " " + listTDs.get(0).getText() );
					bo.setEquipos( listTDs.get(1).getText() );
					bo.setC1( Double.valueOf(listTDs.get(2).getText()) );
					bo.setcX( Double.valueOf(listTDs.get(3).getText()) );
					bo.setC2( Double.valueOf(listTDs.get(4).getText()) );
					
				} else if(listTDs.size() == 7) {  // Juego finalizado o post
					bo.setFecha( fechaStr + " " + listTDs.get(0).getText() );
					bo.setEquipos( listTDs.get(1).getText() );
					
					String horaAux = listTDs.get(0).getText();
					if( horaAux.contains(":") ) {
						bo.setrStr( listTDs.get(2).getText() );
					}
					
					if( !listTDs.get(3).getText().trim().equals("-") ) {
						bo.setC1( Double.valueOf(listTDs.get(3).getText()) );
						bo.setcX( Double.valueOf(listTDs.get(4).getText()) );
						bo.setC2( Double.valueOf(listTDs.get(5).getText()) );
					} else {
						bo.setC1( 0.0 );
						bo.setcX( 0.0 );
						bo.setC2( 0.0 );
					}
					
				}
				
				partidosResult.add(bo);
				logger.info("Es un JUEGO y tiene : " + listTDs.size() + " :: bo = " + bo.toString());
				
			}
		}
		
		
		return partidosResult;
	}

	
	private String getUrlFinal() {
		String result = "";

		result = Cons.URL_BASE_CURRENT;

		return result;
	}

}
