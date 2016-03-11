package org.idsiom.utilbet.currentuse.interlocutor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.util.UtilProperties;
import org.idsiom.utilbet.history.fromoddsportal.Cons;
import org.idsiom.utilbet.history.fromoddsportal.UtilSelenium;
import org.idsiom.utilbet.util.UtilFecha;
import org.jboss.netty.handler.timeout.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;




public class OddsPortalInterCurrentUseImpl implements IOddsPortalCurrentUseInterlocutor {

	private WebDriver driver;
	
	
	private static OddsPortalInterCurrentUseImpl instance;
	
	public static OddsPortalInterCurrentUseImpl getInstance() throws IOException {
		if(instance == null) {
			instance = new OddsPortalInterCurrentUseImpl(); 
		}
		
		return instance;
	}
	
	private OddsPortalInterCurrentUseImpl() throws IOException {
		this.driver = UtilSelenium.getInstanciaWD();
		
		String urlBaseInicial = getUrlFinal();
		System.out.println("buscando :: " + urlBaseInicial);
		driver.get(urlBaseInicial);
		
		UtilProperties utilProperties = UtilProperties.getInstance(); 
		
		// Proceder a logear contra OddsPortal
		// login-username, login-password, button with name = login-submit
		WebElement loginOddPortal = driver.findElement(By.id("login-username"));
		System.out.println("Login de Oddsportal :: " + utilProperties.getProp().getProperty("oddsportal.user"));
		loginOddPortal.sendKeys( utilProperties.getProp().getProperty("oddsportal.user") );
		
		WebElement pwdOddPortal = driver.findElement(By.id("login-password"));
		pwdOddPortal.sendKeys( utilProperties.getProp().getProperty("oddsportal.pwd") );
		
		WebElement buttonOddPortal = driver.findElement(By.name("login-submit"));
		buttonOddPortal.click();
		
		
	}

	static Logger logger = Logger.getLogger(OddsPortalInterCurrentUseImpl.class);
	
	/*
	 * Si el boolean es true, la fecha desde es hoy, en caso contrario la que indique el usuario
	 * @see org.idsiom.utilbet.currentuse.interlocutor.IOddsPortalCurrentUseInterlocutor#getPs(java.lang.Boolean)
	 */
	public ListPartidosSerializable getPs(Boolean reutilizarHistoria) throws Exception {
		
		
			
		String urlMyMatches = Cons.URL_MYMATCHES_CURRENT; // Guardara el String del URL del dia especifico
		String fechaDesdeStr;
		//String fechaHastaStr;
		GregorianCalendar gcFDesde;
		GregorianCalendar gcFHasta;
		Scanner in ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//GregorianCalendar gc = new GregorianCalendar();
		
		ListPartidosSerializable resultFinal = new ListPartidosSerializable();
		in = new Scanner(System.in);
		
		
		Date date;
		
		if(reutilizarHistoria) {
			gcFDesde = new GregorianCalendar();
		} else {
			System.out.println("Ingrese la fecha desde yyyyMMdd: ");
			fechaDesdeStr = in.next();
			
				date = (Date) sdf.parse(fechaDesdeStr);
				gcFDesde = new GregorianCalendar();
				gcFDesde.setTime(date);
				
		}
		
		date = UtilFecha.sumarRestarDiasFecha(new Date(), 2);
		
		gcFHasta = new GregorianCalendar();
		//gcFHasta.setTime(date);
		
		
		System.out.println( "fechaDesde: " + sdf.format(gcFDesde.getTime()) );
		System.out.println( "fechaHasta: " + sdf.format(gcFHasta.getTime()) );
		
		GregorianCalendar gcHoy_0000 = new GregorianCalendar();
		
		gcHoy_0000.set(Calendar.HOUR_OF_DAY,0);
		gcHoy_0000.set(Calendar.MINUTE,0);
		gcHoy_0000.set(Calendar.SECOND,0);
		gcHoy_0000.set(Calendar.MILLISECOND,0);
		
		
		List<CurrentPOddsPortal> listAux;
		Boolean makePause = false;
		while( gcFDesde.before(gcFHasta) ) {
			System.out.println( sdf.format(gcFDesde.getTime()) );

			listAux = this.getPs(sdf.format(gcFDesde.getTime()), urlMyMatches + sdf.format(gcFDesde.getTime()) + "/", makePause);
			
			if(listAux.size() > 0) {
				
				if(gcFDesde.before(gcHoy_0000)) {
					resultFinal.getPartidosHistory().addAll( listAux );
				} else {
					resultFinal.getListaPsHoyFuturo().addAll( listAux );
				}
				
				
				gcFDesde.add(GregorianCalendar.DAY_OF_YEAR,1);
				makePause = false;
			} else {
				System.out.println("No se encontraron partidos para la fecha, " + sdf.format(gcFDesde.getTime()) + ", pulse 1 si desea reintentar... ");
				//seguirIntAux = in.nextInt();
				
				//if(seguirIntAux == 1) {
					makePause = true;
				//} else {
				//	gcFDesde.add(GregorianCalendar.DAY_OF_YEAR,1);
				//	makePause = false;
				//}
			}
			
		}
		
		in.close();
		in = null;
		
		return resultFinal;
	}
	
	
	private List<CurrentPOddsPortal> getPs(String fechaStr, String urlDay, Boolean makePause) throws Exception {
		
		List<CurrentPOddsPortal> partidosResult = new ArrayList<CurrentPOddsPortal>();
		
		try { 
			/*
			 Buscando manejar time for 
			 <div id="top-right-social-column">
				<div id="top-right-social-column-child">
					<div class="g-plusone" data-href="http://www.oddsportal.com/" data-size="medium"></div>
					<div class="fb-like" data-href="http://www.facebook.com/OddsPortal" data-layout="button_count" data-action="like" data-show-faces="true" data-share="false" style="margin-top:3px;"></div>
				</div>
			</div>
		
			 */
			//WebElement myDynamicElement = (new WebDriverWait(this.driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("top-right-social-column")));
			
			System.out.println("this.driver.manage().timeouts().toString() = " + driver.manage().timeouts().toString());
			
			try {
				Thread.sleep(3000);
			} catch(Exception ex) {
				// No hacer nada intencional
			}
			
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
			
			
		} catch(TimeoutException ex) {
			ex.printStackTrace();
			
			return partidosResult;
		}
		
		
		try {
			driver.get(urlDay);
		} catch(org.openqa.selenium.UnhandledAlertException ex) {
			System.out.println("Se detecto una Alerta inesperda, se ignorara. - Detalle :: " + ex.getAlertText());
			
		}
		
		/*
		if(makePause) {
			System.out.println("Pulse 1 despues de cargada la pagina, y los partidos");
			
			try {
				Thread.sleep(3000);
			} catch(Exception ex) { 
				// Se deja vacio de manera intencional
			}
			
			
		}*/
		
		
		// Todos los tr de la tabla partidos
		String xPathPag = ".//*[@id='table-matches']/*/*/tr";  // .//*[@id='table-matches']/*/*/tr
		
		
		List<WebElement> listTRs = driver.findElements(By.xpath(xPathPag));
		
		List<WebElement> listTHs;
		List<WebElement> listTDs;
		
		
		
		
		String pais = null;
		String liga = null;
		CurrentPOddsPortal bo = new CurrentPOddsPortal();
		
		for(WebElement tr : listTRs) {
			listTHs = tr.findElements(By.tagName("th"));
			if(listTHs != null && listTHs.size() > 0) {
				
				//  
				String aux = tr.getText().trim();
				String array[] = aux.split("\n»\n");
				
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
				
				bo = new CurrentPOddsPortal();
				
				bo.setCountry(pais);
				bo.setLeague(liga);
				
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
