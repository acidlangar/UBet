package org.idsiom.utilbet.currentuse.interlocutor;

import java.io.IOException;

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

}
