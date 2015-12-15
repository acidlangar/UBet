package org.idsiom.utilbet.history;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.idsiom.utilbet.bo.PronosticoMillonesBO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.idsiom.utilbet.history.ConstantesPrueba.*;

public class MainPruebaUB {

	//public static void main(String[] args) {
	private static void main(String[] args) {  // Fue colocado como private para evitar futuras equivocaciones y pasar hacer las ejecuciones en el GeneratorSQLsByLeague
		 WebDriver driver = new FirefoxDriver();
		 Boolean impresionExtensa = false; // En caso de que la variable sea true imprime tres archivos por cada html, en caso contrario solamente el .sql
		 System.out.println("Paso 1");
	     
	     
	     // SITE 
	     int site_elegido = 2;  // Prueba por defecto  2 = Millones ; 3 = Local
	     
	     for(String arg : args) {
	    	 if(arg.startsWith("site=")) {
	    		 site_elegido = Integer.parseInt(arg.split("=")[1]); 
	    	 }
	     }
	     
	     // Obtener el url base
	     List<String> urlsPrueba = getUrlsPrueba(site_elegido);
	     int i = 0;
	     String nameFile = null, nameLiga = null;
	     for(String url : urlsPrueba) {
	    	 
	    	 System.out.println( "  " );
	    	 System.out.println( "  " );
	    	 System.out.println( "------------------------------------------------->>>>>>>>>>>>>  " );
	    	 System.out.println( "Buscar url :: " + url );
	    	 driver.get(url);
	    	 
	    	 getNameFiles(url, nameFile, nameLiga) ;
	    	 
	    	 if(impresionExtensa) {
		    	 try {
					PrintWriter out = new PrintWriter("/" + nameLiga + "/" + nameFile + "_pagesource_"  + i + ".txt");
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
				PrintWriter out = new PrintWriter("/" + nameLiga + "/" + nameFile + "_table"  + i + ".txt");
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
					
					out.println( we.getText() );
					out.println( weFecha.getText() );
					out.println( table.getTagName() );
					out.println( table.getText() );
					
					out.close();
					
					tableExitosa = true;
				} catch (  org.openqa.selenium.InvalidSelectorException isex) {
					isex.printStackTrace();
					
					out.println( "No fue encontrado el elemento :: " +  xpathExpression );
					out.close();
					
					
				}
				
				if(tableExitosa) {
					//------------------------->>>>>>>>>>>>>
					// Interpretar el String de la fecha
					// Jornada 23/2014 del sábado 14 de febrero de 2015
					String fechaAux = obtenerFecha(weFecha.getText());
					
					
					PrintWriter out2 = new PrintWriter("/" + nameLiga + "/" + nameFile + "_insert_"  + i + ".sql");
					
					List<WebElement> trs = table.findElements(By.tagName("tr"));
					
					for(WebElement tr : trs) {
						if(isTrTipoInsert1(tr)) {
							out2.println("--insert into ...");
							out2.println("--" + tr.getText());
							out2.println( convert(tr, we.getText(), fechaAux).getStringInsert() );
						}
						
					}
					out2.close();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 
	    	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	
	    	i++; 
	     }
	     
	}
	
	/*
	 * Permite obtener las partes del url para generar los archivos, tanto el nombre de la liga, como 
	 * la fecha del pronostico, por ejemplo, de este url :: http://www.losmillones.com/futbol/espana-1/2014-15-nat.html
	 * debe devolver  nameFile = 2014-15-nat y nameLiga = espana-1
	 * */
	private static void getNameFiles(String url, String nameFile, String nameLiga) {
		String array[] = url.split("/");
		int lon = array.length;
		
		nameFile = array[lon-1].split(".")[0];
		nameLiga = array[lon-2];
		
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
				int val = Integer.parseInt(tds.get(0).getText());
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
		
		/*
		 
		 <tr>
0 -- <td width="15" align="center">1</td>
1 -- <td width="5"></td>
2 -- <td><a href=partidos/2014-23-p01-nat.html>Barcelona</a></td>
3 -- <td align="center">&nbsp;-&nbsp;</td>
4 -- <td><a href=partidos/2014-23-p01-nat.html>Levante</a></td>
5 -- <td width= 22" align="center">1</td>
6 -- <td width= 26" align="right">74%</td>
7 -- <td width="36" align="right"><font color="#ff0000">1,07</font></td>
8 -- <td width="30" align="right"><font color="#8ebd00">1,35</font></td>
9 -- <td width="4"></td>
10 -- <td width="3" bgcolor="#ececec"></td>
11 -- <td width="26" align="center">1X</td>
12 -- <td width="26" align="right">94%</td>
13 -- <td width="32" align="right">1,10</td>
14 -- <td width="30" align="right"><font color="#8ebd00">1,06</font></td>
15 -- <td width="2"></td>
</tr> 
		 */

		/*
		if(liga.startsWith("Espa")) {
			result.setLiga(1);
		} else {
			result.setLiga(-1);
		}
		*/
		
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
			/*
			if(tds.get(5).findElement(By.tagName("font")) != null) {
				System.out.println("--------------------    Pronostico = " + tds.get(5).findElement(By.tagName("font")).getCssValue("color") );
			} else {
				System.out.println("--------------------    FUE NULL EL TEMA DEL PRIMER COLOER"  );
			}*/
		} catch( org.openqa.selenium.NoSuchElementException ex) {
			// Se deja vacio intencionalmente porque se considera que siempre se procesan valores pasados 
		}
		
		result.setAcierto(aciertoAux);
		
		String aux = tds.get(6).getText();
		result.setPorcConfianza( Integer.parseInt(aux.substring(0, aux.length() - 1)) );
		result.setPagoPaginas( tds.get(7).getText() );
		result.setPagoCalculado(tds.get(8).getText());
		
		/*
		result.setPronosticoDobles( tds.get(11).getText() );
		aux = tds.get(12).getText();
		result.setPorcConfianza( Integer.parseInt(aux.substring(0, aux.length() - 1)) );
		result.setPagoPaginas( tds.get(13).getText() );
		result.setPagoCalculado(tds.get(14).getText());
		*/
		
		return result;
	}

	private static List<String> getUrlsPrueba(int site_elegido) {
		List<String> urlsPrueba = new ArrayList<String>();
		
		if( site_elegido == 1 ) {
			System.out.println("site_elegido incorrecto");
			
			return null;
		} else if( site_elegido == 2 ) {
			urlsPrueba.add(WEB_MILL_LIG);
			
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "22"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "21"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "20"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "19"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "18"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "17"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "16"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "15"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "14"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "13"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "12"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "11"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "10"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "09"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "08"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "07"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "06"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "05"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "04"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "03"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "02"));
			urlsPrueba.add(WEB_MILL_LIG_BASE.replace("{*}", "01"));
		} else {
			urlsPrueba.add(WEB_LOCAL);
		}
		
		return urlsPrueba;
	}

}
