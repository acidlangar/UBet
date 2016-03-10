package org.idsiom.utilbet.currentuse.interlocutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	private static PickioInterlocutorImpl instance;
	private WebDriver driver;
	
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

	public void montarPick(CurrentPOddsPortal pOP, ResultadoPartidoBO local, int i) {
		// TODO Auto-generated method stub
		
	}

	public List<PartidoPyckioBO> getPartidosPorHora(Long momento) {
		List<PartidoPyckioBO> listPartidos = new ArrayList<PartidoPyckioBO>();
		
		System.out.println("Inicio del Metodo");
		
		// click on js-todaygames 
		/*WebElement weTodayGames = this.driver.findElement(By.className("js-todaygames"));
		
		System.out.println("weTodayGames=" + weTodayGames.getText());
		
		weTodayGames.click();
		
		*/
		
		driver.get("https://pyckio.com/i/#!home/todaygames");
		
		
		
		//String xPathPag = "id('mytimeline')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr[td[2]/img[@data-original-title='soccer']]"; //  id('mytimeline')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr[td[2]/img[@data-original-title='soccer']]
		String xPathPag = ".//*[@id='mytimeline']/div/div[2]/div/table/tbody/tr[td[2]/img[@data-original-title='soccer']]"; 
		//String xPathPag = ".//*[@id='mytimeline']";
		
		try {
			Thread.sleep(6000);
		} catch(Exception ex) {
			
		}
		
		System.out.println("Justo antes de la llamada xPath");
		
		//System.out.println( this.driver.findElement(By.xpath(xPathPag)).getText()  );
		
		
		List<WebElement> listTRs = this.driver.findElements(By.xpath(xPathPag));
		System.out.println("paso ");
		
		List<WebElement> listTDs;
		
		
		for(WebElement tr : listTRs) {
			System.out.println("__________>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " );
			System.out.println("__________ " );
			System.out.println("__________ " );
			listTDs = tr.findElements(By.tagName("td"));
			
			System.out.println("fecha :: " + listTDs.get(0).getText());
			System.out.println("Pais :: " + listTDs.get(2).getText());
			System.out.println("liga :: " + listTDs.get(3).getText());
			System.out.println("Equipos :: " + listTDs.get(4).getText());
			
		}
		
		System.out.println("Fin del Metodo");
		
		return listPartidos;
	}

}
