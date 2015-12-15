package org.idsiom.utilbet.history.fromoddsportal;

import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class UtilSelenium {
	public static WebDriver getInstanciaWD() {
		WebDriver driver;
		 
		 String sSistemaOperativo = System.getProperty("os.name");
			
			if (sSistemaOperativo.startsWith("Win")) { // Windows
				driver = new FirefoxDriver();
				
			} else { // Linux
				File pathToBinary = new File("/usr/bin/firefox/firefox");
				FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				driver = new FirefoxDriver(ffBinary, firefoxProfile);
			}
		 
			return driver;
	}
}
