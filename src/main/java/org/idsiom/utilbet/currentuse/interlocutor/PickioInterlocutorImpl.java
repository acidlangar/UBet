package org.idsiom.utilbet.currentuse.interlocutor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.currentuse.MainFromFileCurrentP;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;
import org.idsiom.utilbet.currentuse.util.UtilProperties;
import org.idsiom.utilbet.history.fromoddsportal.UtilSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PickioInterlocutorImpl implements IPyckioInterlocutor {
	public static final String PAGE_SIGNIN_PYCKIO = "https://pyckio.com/signin";
	
	static Logger logger = Logger.getLogger(PickioInterlocutorImpl.class);
	
	private static PickioInterlocutorImpl instance;
	private WebDriver driver;
	
	private List<PartidoPyckioBO> listPartidos;
	
	public static PickioInterlocutorImpl getInstance() throws IOException {
		if (instance == null) {
			instance = new PickioInterlocutorImpl();
		}
		
		return instance;
	}
	
	private PickioInterlocutorImpl() throws IOException {
		this.driver = UtilSelenium.getInstanciaWD();
		
		System.out.println("buscando :: " + PAGE_SIGNIN_PYCKIO);
		driver.get(PAGE_SIGNIN_PYCKIO);
		
		UtilProperties utilProperties = UtilProperties.getInstance(); 
	
		//
		// Aceptar cookies
		WebElement buttonCookies = driver.findElement(By.className("cookiesplease-accept"));
		if(buttonCookies != null) {
			buttonCookies.click();
		}
		
		
		// Proceder a logear contra Pyckio
		// 
		WebElement loginPyckio = driver.findElement(By.id("email"));
		System.out.println("Login de Pyckio :: " + utilProperties.getProp().getProperty("pyckio.user"));
		loginPyckio.sendKeys( utilProperties.getProp().getProperty("pyckio.user") );
		
		WebElement pwdPyckio = driver.findElement(By.id("pwd"));
		pwdPyckio.sendKeys( utilProperties.getProp().getProperty("pyckio.pwd") );
		
		WebElement buttonPyckio = driver.findElement(By.id("btn-signin"));
		buttonPyckio.click();
		
	}
	
	
	public void iniciarSesion(String usuario, String clave) {
		// TODO Auto-generated method stub
		/*
id email
id pwd

btn-signin
		 */
	}

	public void montarPick(CurrentPOddsPortal pOP, ResultadoPartidoBO resultBuscado, int i) throws Exception {
		PartidoPyckioBO partidoPIO = this.findTraduction(listPartidos, pOP);
		
		this.montarPick(partidoPIO, resultBuscado, i);
	}
	
	public boolean montarPick(PartidoPyckioBO partidoPIO, ResultadoPartidoBO resultBuscado, int i) throws Exception {
		
		WebElement seleccion = partidoPIO.getWebElement().findElements(By.tagName("td")).get(4);
		WebElement seleccionA = seleccion.findElement(By.tagName("a"));
		System.out.println("********************************************************************************************" );
		System.out.println("****************************** seleccion = " + seleccionA.getText());
		System.out.println("********************************************************************************************* " );
		
		try {
			Thread.sleep(10000);
		} catch(Exception ex) {
			// no hacer nada
		}
		
		logger.info("href antes de la info " + this.driver.getTitle() + "   " + this.driver.getCurrentUrl());
		
		seleccionA.click();
		
		logger.info("href despues de la info " + this.driver.getTitle() + "   " + this.driver.getCurrentUrl());
		
		System.out.println("********************************************************************************************" );
		System.out.println("****************************** SE ACABA DE HACER CLICK EN LA SELECCION " );
		System.out.println("********************************************************************************************* " );
		
		try {
			Thread.sleep(10000);
		} catch(Exception ex) {
			// no hacer nada
		}
		
		int iAux;
		if(resultBuscado.equals(ResultadoPartidoBO.LOCAL)) {
			iAux = 1;
		} else if(resultBuscado.equals(ResultadoPartidoBO.VISITANTE)) {
			iAux = 2;
		} else {
			iAux = 3;
		}
		
		// Buscar por XPath las tres barras... id('1X2')/x:table/x:tbody/x:tr/x:td[2]
		String xPath = ".//*[@id='1X2']/table/tbody/tr[" + iAux + "]/td[2]";
		WebElement barraStakeSeleccionada = null;
		
		Boolean seguir = true;
		int intentos = 0;
		while(seguir) {
			try {
				intentos++;
				barraStakeSeleccionada = driver.findElement(By.xpath(xPath));
				
				System.out.println("barraStakeSeleccionada = " + barraStakeSeleccionada.getText());
				seguir = false;
			} catch(org.openqa.selenium.NoSuchElementException e) {
				
				logger.info("href antes de la info " + this.driver.getTitle() + "   " + this.driver.getCurrentUrl());
				
				seleccionA.click();
				
				logger.info("href despues de la info " + this.driver.getTitle() + "   " + this.driver.getCurrentUrl());
				
				System.out.println("-------------------------------------------------------- " );
				System.out.println("No fue encontrado el elemento  intento " + intentos + " Intentaremos nuevamnte click en " + seleccionA.getText());
				logger.info("No fue encontrado el elemento  intento " + intentos + " Intentaremos nuevamnte click en " + seleccionA.getText());
				System.out.println("-------------------------------------------------------- " );
				System.out.println("-------------------------------------------------------- " );
				
				Thread.sleep(intentos * 2000);
				
				if(intentos > 4) {
					seguir = false;
				}
			}			
		}
		

		if(barraStakeSeleccionada != null) {
			// div/x:label[1]
			xPath = "div/label[" + i + "]";
			barraStakeSeleccionada.findElement(By.xpath(xPath)).click();
			
			// btn btn-default js-sendtwip
			xPath = "//*[@id='event']/div/div/div[2]/div[3]/form/button";
			driver.findElement(By.xpath(xPath)).click();
			
			return true;
		}
		
		return false;
		
		
		
		
	}

	public List<PartidoPyckioBO> getPartidosPorHora(Long momento) {
		listPartidos = new ArrayList<PartidoPyckioBO>();
		
		driver.get("https://pyckio.com/i/#!home/todaygames");
		
		//String xPathPag = "id('mytimeline')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr[td[2]/img[@data-original-title='soccer']]"; //  id('mytimeline')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr[td[2]/img[@data-original-title='soccer']]
		String xPathPag = ".//*[@id='mytimeline']/div/div[2]/div/table/tbody/tr[td[2]/img[@data-original-title='soccer']]"; 
		//String xPathPag = ".//*[@id='mytimeline']";
		
		try {
			Thread.sleep(6000);
		} catch(Exception ex) {
			
		}
		
		List<WebElement> listTRs = this.driver.findElements(By.xpath(xPathPag));
		System.out.println("paso ");
		
		List<WebElement> listTDs;
		PartidoPyckioBO p;
		
		
		for(WebElement tr : listTRs) {
			System.out.println("__________>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " );
			System.out.println("__________ " );
			System.out.println("__________ " );
			listTDs = tr.findElements(By.tagName("td"));
			
			String equipos = listTDs.get(4).getText();
			p = new PartidoPyckioBO();
			
			p.setFechaStr(listTDs.get(0).getText());
			p.setPais( listTDs.get(2).findElement(By.tagName("img")).getAttribute("alt") );
			p.setLiga(listTDs.get(3).getText());
			p.setEquipoLocal( equipos.split("-")[0].trim() );
			p.setEquipoVisitante( equipos.split("-")[1].trim() );
			p.setWebElement(tr);
			
			listPartidos.add(p);
		}
		
		return listPartidos;
	}
	
	public PartidoPyckioBO findTraduction(List<PartidoPyckioBO> list,
			CurrentPOddsPortal pop) {
		PartidoPyckioBO result = null;
		List<PartidoPyckioBO> listHora_Pais = filtrarPorHora_Pais( list, pop );
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		//String fechaOP;
		//String fechaPIO;
		
		//System.out.println(pop);
		//System.out.println("oddsPortal LOCAL = " + pop.getEquipoLocal());
		
		for(PartidoPyckioBO pio : listHora_Pais) {
			//fechaOP = sdf.format(pop.getFechaGC().getTime());
			//fechaPIO = sdf.format(pio.getFechaGC().getTime());
			//System.out.println("op = " + fechaOP + "   ppio = " + fechaPIO + "  ppio : " + pio.getPais() + "   Local = " + pio.getEquipoLocal() );
			if( pio.getEquipoLocal().trim().equals(pop.getEquipoLocal().trim())
					|| pio.getEquipoVisitante().trim().equals(pop.getEquipoVisitante().trim())
					) {
				result = pio;
			}
		}
		
		
		if( listHora_Pais.size() > 0 && result == null ) {
			// Obtener el más parecido
			result = obtenerElMasParecido(listHora_Pais, pop);
		}
		
		
		return result;
	}

	private static PartidoPyckioBO obtenerElMasParecido(
			List<PartidoPyckioBO> listHora_Pais, CurrentPOddsPortal pop) {

		for(PartidoPyckioBO ppio : listHora_Pais) {
			ppio.calValDistancia(pop);
		}
		
		Collections.sort(listHora_Pais);
		
		return listHora_Pais.get(0);
	}

	private static List<PartidoPyckioBO> filtrarPorHora_Pais(
			List<PartidoPyckioBO> list, CurrentPOddsPortal pop) {
		List<PartidoPyckioBO> listHora_Pais = new ArrayList<PartidoPyckioBO>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		String fechaOP;
		String fechaPIO;
		
		for(PartidoPyckioBO ppio : list) {
			fechaOP = sdf.format(pop.getFechaGC().getTime());
			fechaPIO = sdf.format(ppio.getFechaGC().getTime());
			//System.out.println("op = " + fechaOP + "   ppio = " + fechaPIO + "  ppio : " + ppio.getPais() );
			
			if(fechaOP.equals(fechaPIO) && pop.getCountry().equals( ppio.getPais() )) {
				listHora_Pais.add(ppio);
			}
		}
		
		return listHora_Pais;
	}

	public void close() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Se llamo al CLOSE del Interlocutor Pyckio");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		/*
		this.driver.close();
		
		instance = null;
		*/
		
	}
	
	
	

}
