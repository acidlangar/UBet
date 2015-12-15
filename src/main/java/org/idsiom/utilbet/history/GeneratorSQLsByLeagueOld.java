package org.idsiom.utilbet.history;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.idsiom.utilbet.bo.PronosticoMillonesBO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import static org.idsiom.utilbet.history.ConstantesPrueba.*;

/*
* Se encarga de leer la info desde el WEB y crear los archivos .sql que proximamente deben ser ejecutados en 
* la base de datos para tener la historia correspondiente.
*/
public class GeneratorSQLsByLeagueOld {

	/**
	* Metodo principal que se ejecuta
	*/
	private static void main(String[] args) {  // Colocado private dado que la presente clase es una version ejecutable de la clase.
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
		 
		 
		 int iteracion = 1;
		 int iteracionLiga = 1;
		 String nameliga;
		 String[] arrayStrAux;
		 System.out.println("Driver instanciado");
	     
		 List<String> urlsLigas = getUrlsLigas(driver);
		 
		 for(String liga : urlsLigas) {
			driver.get(liga);
			arrayStrAux = liga.split("/");
			nameliga = arrayStrAux[ arrayStrAux.length - 1 ];
			
			// Obtener la cantidad de paginas
			List<String> urlsInternos = getUrlsNums(driver);
			iteracion = 1;
			for(String urlToProcess : urlsInternos) {
				System.out.println("urlToProcess :: " + urlToProcess + "; nameliga = " + nameliga + "; iteracion = " + iteracion);
				
				procesarUrlInterno(driver, urlToProcess, nameliga, iteracion);
				
				iteracion++;
				
				//if(iteracion >= 3) break;
			}
			
			iteracionLiga++;
			
			if(iteracionLiga >= 3) {
				System.out.println("Actualmente se sale para el motivo de las pruebas...");
				break;
			}
			
		 }
		
	}
	
	
	/*
	 * Permite obtener las partes del url para generar los archivos, tanto el nombre de la liga, como 
	 * la fecha del pronostico, por ejemplo, de este url :: http://www.losmillones.com/futbol/espana-1/2014-15-nat.html
	 * debe devolver  nameFile = 2014-15-nat y nameLiga = espana-1
	 * */
	private static String getNameFiles(String url) {
		String array[] = url.split("/");
		int lon = array.length;
		
		String nameFile = array[lon-1].split("\\.")[0];
		
		return nameFile;
		
	}
	
	private static void procesarUrlInterno(WebDriver driver, String url, String liga, int iteracion) {
			String nameFile = null;
			Boolean imprimirExtenso = false;
	     	 
	    	 System.out.println( "  " );
	    	 System.out.println( "  " );
	    	 System.out.println( "------------------------------------------------->>>>>>>>>>>>>  " );
	    	 System.out.println( "Buscar url :: " + url );
	    	 driver.get(url);
	    	 
	    	 nameFile = getNameFiles(url);
	    	 
	    	 File fileDir = new File("insert_sql");
	    	 if( !fileDir.exists() ) {
	    		 if( !fileDir.mkdir()) {
	    			 return;
	    		 }
	    	 }
	    	 
	    	 File file = new File("insert_sql/" + liga);
	    	 if( !file.exists() ) {
	    		 if( !file.mkdir()) {
	    			 return;
	    		 }
	    	 }
	    	 
	    	 if(imprimirExtenso) {
	    		 try {
	 				PrintWriter out = new PrintWriter("insert_sql/" + liga + "/" + liga + "_" + nameFile + "_pagesource_" + ".txt");
	 				out.println( "Buscar url :: " + url );
	 				out.println( "PageSource :: " );	
	 				out.println( driver.getPageSource() );
	 				out.close();
	 			} catch (FileNotFoundException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	    	 }
	    	 
			
			try {
				//PrintWriter out = new PrintWriter(liga + "/" + liga + "_" + nameFile +  "_table"  + ".txt");
				//String xpathExpression = "id('content')/x:div[1]/x:div/x:div[2]/x:table[2]";
				String xpathExpression = "id('content')/div[1]/div/div[2]/table[2]";
				String xpathExpressLiga = "id('content')/div[1]/div/div[2]/h3/table/tbody/tr[1]/td[1]";
				String xpathExpressFecha = "id('content')/div[1]/div/div[2]/div[1]/h5";
				Boolean tableExitosa = false;
				WebElement table = null;
				WebElement we = null;
				WebElement weFecha = null;
				try {
					table = driver.findElement(By.xpath(xpathExpression));
					we = driver.findElement(By.xpath(xpathExpressLiga));
					weFecha = driver.findElement(By.xpath(xpathExpressFecha));
					
					//out.println( we.getText() );
					//out.println( weFecha.getText() );
					//out.println( table.getTagName() );
					//out.println( table.getText() );
					
					//out.close();
					
					tableExitosa = true;
				} catch (  org.openqa.selenium.InvalidSelectorException isex) {
					isex.printStackTrace();
					
					//out.println( "No fue encontrado el elemento :: " +  xpathExpression );
					//out.close();
					
					
				}
				
				if(tableExitosa) {
					//------------------------->>>>>>>>>>>>>
					// Interpretar el String de la fecha
					// Jornada 23/2014 del sábado 14 de febrero de 2015
					String fechaAux = obtenerFecha(weFecha.getText());
					
					
					PrintWriter out2 = new PrintWriter("insert_sql/" + liga + "/" + liga + "_" + nameFile +  "_insert"  + ".sql");
					
					List<WebElement> trs = table.findElements(By.tagName("tr"));
					
					for(WebElement tr : trs) {
						if(isTrTipoInsert1(tr)) {
							out2.println("--insert into ...");
							out2.println("--" + tr.getText());
							out2.println( convert(tr, liga, fechaAux).getStringInsert() );
						}
						
					}
					out2.close();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 
	    	try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	     
	}
	
	private static String obtenerFecha(String fecha) {
		// Jornada 23/2014 del sábado 14 de febrero de 2015
		
		String result = null;
		String mes = "-1";
		
		System.out.println(" ********************       " + fecha.split(" ")[6]);
		if(fecha.split(" ")[6].startsWith("ene")) {
			mes = "01";
		} else if(fecha.split(" ")[6].startsWith("febr")) {
			mes = "02";
		} else if(fecha.split(" ")[6].startsWith("mar")) {
			mes = "03";
		} else if(fecha.split(" ")[6].startsWith("abr")) {
			mes = "04";
		} else if(fecha.split(" ")[6].startsWith("may")) {
			mes = "05";
		} else if(fecha.split(" ")[6].startsWith("jun")) {
			mes = "06";
		} else if(fecha.split(" ")[6].startsWith("jul")) {
			mes = "07";
		} else if(fecha.split(" ")[6].startsWith("ago")) {
			mes = "08";
		} else if(fecha.split(" ")[6].startsWith("sep")) {
			mes = "09";
		} else if(fecha.split(" ")[6].startsWith("oct")) {
			mes = "10";
		} else if(fecha.split(" ")[6].startsWith("nov")) {
			mes = "11";
		} else if(fecha.split(" ")[6].startsWith("dic")) {
			mes = "12";
		} 
		
		result = fecha.split(" ")[4] + "/" +  mes  + "/" + fecha.split(" ")[8];
		
		return result;
	}
	
	private static Boolean isTrTipoInsert1(WebElement tr) {
		Boolean result = false;
		
		List<WebElement> tds = tr.findElements(By.tagName("td"));
		System.out.println("---------------------->>>>>>>>>>>>>>>");
		System.out.println("----------  " + tr.getText());
		System.out.println("tds.size() = " + tds.size());
		System.out.println("tds[0].getText() = " + tds.get(0).getText());
		
		if(tds.size() == 16) {
			try {
				Integer.parseInt(tds.get(0).getText());
				result = true;
			} catch(Exception ex) {
				// Se deja vacio intencionalmente...
				return result;
			}
		}
		
		return result;
	}
	
	private static PronosticoMillonesBO convert(WebElement tr, String liga, String fecha) {
		PronosticoMillonesBO result = new PronosticoMillonesBO();
		
		result.setLiga(liga);
		
		result.setFechaMDA(fecha);
		
		List<WebElement> tds = tr.findElements(By.tagName("td"));
		result.setNomEqL( tds.get(2).getText() );
		result.setNomEqV( tds.get(4).getText() );
		
		result.setPronostico( tds.get(5).getText() );
		
		int aciertoAux = 1; // true
		try {
			if(tds.get(5).findElement(By.tagName("font")) != null) {
				if(tds.get(5).findElement(By.tagName("font")).getCssValue("color").toString().startsWith("rgba(255, 0, 0") ) { // Color Rojo
					aciertoAux = 0; // false
				}
			}

		} catch( org.openqa.selenium.NoSuchElementException ex) {
			// Se deja vacio intencionalmente porque se considera que siempre se procesan valores pasados 
		}
		
		result.setAcierto(aciertoAux);
		
		String aux = tds.get(6).getText();
		result.setPorcConfianza( Integer.parseInt(aux.substring(0, aux.length() - 1)) );
		result.setPagoPaginas( tds.get(7).getText() );
		result.setPagoCalculado(tds.get(8).getText());
		
		return result;
	}

	
	private static List<String> getUrlsLigas(WebDriver driver) {
		List<String> urlsLigas = new ArrayList<String>();
		
		// Entrar en la pagina base
		driver.get(getUrlBase());
		
		// Obtener los urls bases por liga
		String xPathExpression = "id('content')/div[1]/div/div[1]/div/div/div/div/div/div";
		WebElement tableMenu = driver.findElement(By.xpath(xPathExpression));
		
		List<WebElement> lis = tableMenu.findElements(By.tagName("a"));
		
		int i = 0;
		for(WebElement we : lis) {
			if(i > 2) {
				System.out.println( we.getAttribute("href") );
				urlsLigas.add(we.getAttribute("href"));
				
			}
			
			i++;
		}
		
		return urlsLigas;
	}
	
	private static List<String> getUrlsNums(WebDriver driver) {
			List<String> urlsNums = new ArrayList<String>();
			String xPathExprNums = "id('content')/div[1]/div/div[3]/div/div/div/table[1]";
			WebElement weTableNums = driver.findElement(By.xpath(xPathExprNums));
			List<WebElement> asNums = weTableNums.findElements(By.tagName("a"));
			
			for(WebElement we : asNums) {
				urlsNums.add(we.getAttribute("href"));
			}
			
			return urlsNums;
	}
	
	private static String getUrlBase() {
		int siteBase = 0;
		
		if(siteBase == 0) {
			return WEB_MILL_LIG;
		} else {
			return WEB_LOCAL;
		}
	
	}

}
