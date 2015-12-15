package org.idsiom.utilbet.dummypruebas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Prueba2 {
	
	public static void main(String args[]) {
		System.out.println("Inicio de prueba...");
		
		WebDriver driver = new ChromeDriver(); 
		
		System.out.println("2");
		
        driver.get("http://www.google.com");
        
        System.out.println("3");
        
        System.out.println(driver.getPageSource());
        
        System.out.println("4");

        /*
        FileWriter writer = new FileWriter("d:\\google.html");
        writer.write(page.asXml()); //Save page on file system 
        writer.close(); 
		*/
	}
	
	
	
}
