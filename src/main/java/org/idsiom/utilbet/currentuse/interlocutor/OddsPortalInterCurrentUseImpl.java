package org.idsiom.utilbet.currentuse.interlocutor;

import java.util.ArrayList;
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
	
	public List<CurrentPOddsPortal> getPs(Integer cantDiasAtras) throws Exception {
		String urlBaseInicial = getUrlFinal();
		this.driver = UtilSelenium.getInstanciaWD();
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		GregorianCalendar gc = new GregorianCalendar();
		*/
		List<CurrentPOddsPortal> resultFinal = new ArrayList<CurrentPOddsPortal>();
		
		System.out.println("buscando :: " + urlBaseInicial);
		this.driver.get(urlBaseInicial);
		
		boolean seguir = false;
		
		System.out.println("1 para continuar... ");

		int iSeguir;
		Scanner in = new Scanner(System.in);
		
		iSeguir = in.nextInt();
		seguir = ( iSeguir == 1 );
		
		while( seguir ) {
			resultFinal.addAll( this.getPs() );
			
			System.out.println("1 para continuar... ");
			iSeguir = in.nextInt();
			seguir = ( iSeguir == 1 );
		}
		
		/*
		String url;
		url = urlBaseInicial + "/" + sdf.format(gc.getTime());
		resultFinal.addAll( this.getPs(url) );
		
		for(int i = 1; i < cantDiasAtras; i++) {
			gc.roll(Calendar.DAY_OF_YEAR, false);
			url = urlBaseInicial + "/" + sdf.format(gc.getTime());
			
			resultFinal.addAll( this.getPs(url) );
			
		}
		*/
		
		
		return resultFinal;
	}
	
	
	private List<CurrentPOddsPortal> getPs() throws Exception {
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
				
				if(listTDs.size() == 6) {  // Juego planificado
					bo.setFecha( listTDs.get(0).getText() );
					bo.setEquipos( listTDs.get(1).getText() );
					bo.setC1( Double.valueOf(listTDs.get(2).getText()) );
					bo.setcX( Double.valueOf(listTDs.get(3).getText()) );
					bo.setC2( Double.valueOf(listTDs.get(4).getText()) );
					
				} else if(listTDs.size() == 7) {  // Juego finalizado o post
					bo.setFecha( listTDs.get(0).getText() );
					bo.setEquipos( listTDs.get(1).getText() );
					bo.setrStr( listTDs.get(2).getText() );
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

		result = Cons.URL_CURRENT_USE;

		return result;
	}

}
