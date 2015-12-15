package org.idsiom.utilbet.interlocutor;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.idsiom.utilbet.exception.InteraccionException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import org.idsiom.utilbet.bo.DefApuestaCombinadaBO;
import org.idsiom.utilbet.bo.DefApuestaSencillaBO;
import org.idsiom.utilbet.bo.PartidoParaApostarBO;

public class InterlocutorBetCrisImpl implements IInterlocutorCasaDeBets {

	private static InterlocutorBetCrisImpl _instancia;
	
	private static WebDriver _driver; 
	
	private static String _ulrDeseado = "http://www.betcris.com/pagina-ingreso";
	
	//-------------->>
	// Valores insertados por el usuario
	private static String _mailLogin = "VE85732";
	private static String _claveLogin = "siomcris11";
	
	//-------------->>
	// IDs del sitio web
	private static String _textFieldLogin = "account";
	private static String _textFieldPass = "password";
	
	private static int SEGS_ESPERA_CARGA = 6;
	
	private InterlocutorBetCrisImpl() {
		//_driver = new FirefoxDriver();
		
		File pathToBinary = new File("/usr/bin/firefox/firefox");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		_driver = new FirefoxDriver(ffBinary,firefoxProfile);
		
	}
	
	
	/**
	 * Metodo que sustituye al constructor para poder instanciar la clase
	 * @param montoInicial - corresponde al monto Inicial que fue agregado en la pagina. Basicamete la pagina 
	 *                       tiene maneras particulares de calcular el monto disponible, el monto capital,
	 *                       que estan pensados para enganar a la gente y hacerlos perder dinero. En este caso
	 *                       necesitamos calcular el monto  disponible por lo que es importante tener el monto
	 *                       inicial claro.
	 * @return
	 */
	public static InterlocutorBetCrisImpl getInstance() {
		if(_instancia == null) {
			_instancia = new InterlocutorBetCrisImpl();
		}
		
		return _instancia;
	}
	
	/**
	 * Metodo que devuelve la instancia del driver, la idea es que exista un solo browser
	 * ejecutandose
	 * @return
	 */
	private WebDriver getInstanceDriver() {
		if( _driver != null ) {
			return _driver;
		}
		
		File pathToBinary = new File("/usr/bin/firefox/firefox");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		_driver = new FirefoxDriver(ffBinary,firefoxProfile);
		
		
		return _driver;
	}
	
	/**
	 * Metodo que indica si se encuentra logeado a la pagina con la session activa
	 * @return
	 */
	private Boolean isUserLoggedIn() {
		boolean result = false;
		
		// *************************************                       Aqui hay que buscar la manera de saber si el usuario est� logeado o no...
		WebElement cuentaLogeado;
		// Preguntar por el div con la class = account_header_user_email
		try {
			cuentaLogeado = (WebElement) (_driver.findElement(By.id("ctl00_userID")));
		} catch(org.openqa.selenium.NoSuchElementException ex) {
			return false;
		}
		 
		System.out.println("Cuenta Logeado = " + cuentaLogeado.getText());
		
		if(cuentaLogeado.getText().equals("VE85732")) {
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Este metodo inicia la session del usuario en la página
	 * @throws InteraccionMercadoException
	 */
	private void logearseEnLaWeb() throws InteraccionException {
		_driver = getInstanceDriver();
		
		_driver.get(_ulrDeseado);
        
        WebElement element = _driver.findElement(By.id(_textFieldLogin));  
        element.sendKeys(_mailLogin);
        
        WebElement elementPass = _driver.findElement(By.id(_textFieldPass));  
        elementPass.sendKeys(_claveLogin);

        
        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();
        
        esperar(SEGS_ESPERA_CARGA);
        
        if(isUserLoggedIn()) {
        	// Proceso Exitoso
        	return;
        } else {
        	esperar(SEGS_ESPERA_CARGA);
        	
        	if(isUserLoggedIn()) {
	        	// Proceso Exitoso
	        	return;
	        } else {
	        	throw new InteraccionException("No fue posible hacer el login en la " + _ulrDeseado + ", Con el usuario deseado " + _mailLogin);
	        	
	        	
	        }
        }
	}
	
	
	public static void esperar(int segundos) {
		try {
        	//System.out.println("Esperando la carga de la p�gina... Implica que luego se buscara una mejor manera de hacerlo");
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public List<PartidoParaApostarBO> consultarApuestasDisponibles(Boolean paraHoy) throws InteraccionException {
		List<PartidoParaApostarBO> result;
		_driver = getInstanceDriver();
		
		if(!isUserLoggedIn()) {
			logearseEnLaWeb();
		}
		
		// 
		WebElement element = _driver.findElement(By.id("FUTBOL")); 
		element.click();
		
		// Buscar todos los elementos checbox que esten dentro del CatFUTBOL_block
		List<WebElement> checks = _driver.findElement(By.id("CatFUTBOL_block")).findElements(By.className("checkBox"));
		for(WebElement futbolCheck : checks) {
			futbolCheck.click();
		}
		
		element = _driver.findElement(By.id("ctl00_mainContent_btnContinueTop")); 
		element.click();
		
		//-------------------->>>>>>>>>>>
		// Esperar la carga de las apuestas en la página
		esperar(6);
		
		//class title
		List<WebElement> barraLigas = _driver.findElements(By.className("title"));
		int i = 0;
		for(WebElement barra : barraLigas) {
			if(i > 0) {
				barra.click();
			}
			
			i++;
		}
		
		System.out.println("Total de iteraciones = " + i);
		
		//------------------------------>>>>>>>>>>
		// Con todas las apuestas desplegadas, recorrer para ir construyendo los objetos
		
		result = new ArrayList<PartidoParaApostarBO>();
		HashMap<Integer,PartidoParaApostarBO> mapApuestas = new HashMap<Integer,PartidoParaApostarBO>();
			
			//String xPath = "//table[@id='" + matchupBox.getAttribute("id") + "']//td";
			String xPath = "//table//td";
			
			System.out.println("      ");
			System.out.println("  ************************************    ");
			System.out.println("  ************************************    ");
			System.out.println("  ************************************    ");
			System.out.println("      ******   xPath usado :: " + xPath);
			
			List<WebElement> tds = _driver.findElements(By.xpath(xPath));
			
			for(WebElement td : tds) {
				if( td.getAttribute("id") != null && td.getAttribute("id").startsWith("match_") ) {
					List<WebElement> elements = td.findElements(By.className("rotCell"));
					
					if(elements.size() == 3) {
						PartidoParaApostarBO apuestaBO = new PartidoParaApostarBO();
						
						//------>>>>
						// Codigos
						apuestaBO.setCodigoGanador1( elements.get(0).getText() );
						apuestaBO.setCodigoGanador2( elements.get(1).getText() );
						apuestaBO.setCodigoEmpate( elements.get(2).getText() );
						
						//------>>>>
						//Nombres
						elements = td.findElements(By.className("teamCell"));
						apuestaBO.setNombreGanador1( elements.get(0).getText() );
						if(apuestaBO.getNombreGanador1().startsWith("Equipos")) {
							apuestaBO.setNombreGanador1( apuestaBO.getNombreGanador1().substring(6, apuestaBO.getNombreGanador1().length()-1) );
						}
						apuestaBO.setNombreGanador2( elements.get(1).getText() );
						
						//------>>>>
						// Multiplicadores
						elements = td.findElements(By.className("selectCell"));
						double[] mults = new double[3];
						int cantM = 0, iteM = 0;
						for(WebElement we : elements) {
							
							//if(we.findElement(By.className("labelT")).getText().equalsIgnoreCase("Resultado")) {
							if( iteM == 2 || iteM == 5 || iteM == 8 ) {
								String auxM;
								
								if( iteM == 2 ) {
									auxM = we.findElements(By.tagName("span")).get(1).getText();
								} else {
									auxM = we.findElements(By.tagName("span")).get(0).getText();
								}
								
								//System.out.println("auxM = " + auxM);
								mults[cantM] =   Double.parseDouble(auxM);
							
								cantM++;
							}
							
							iteM++;
						}
						
						apuestaBO.setMultiplicadorGanador1( mults[0] );
						apuestaBO.setMultiplicadorGanador2( mults[1] );
						apuestaBO.setMultiplicadorEmpate( mults[2] );
						
						mapApuestas.put(Integer.parseInt( apuestaBO.getCodigoGanador1() ), apuestaBO);
						System.out.println(" Apuesta agregada al MAP :: " + apuestaBO.getCodigoGanador1());
					}
				}
			}
			
		// Ahora Procedemos hacer recorrido por las filas para determinar el nombre de la liga, y la fecha y hora	
		
		//matchupBox
		List<WebElement> elements = _driver.findElements(By.className("matchupBox"));
		
		for(WebElement we : elements) {
			List<WebElement> trs = we.findElements(By.tagName("tr"));
			
			String ultNombreLiga = "";
			String ultDiaDelMes = "";
			String ultHora = "";
			Integer ultKey;
			
			for(WebElement tr : trs) {
				if( tr.getAttribute("class").equalsIgnoreCase("banner") ) {
					String aux = tr.getText();
					String arrayStr[] = aux.split("-");
					ultDiaDelMes = arrayStr[arrayStr.length-1];
					
					aux = "";
					for(int j = 0; j < arrayStr.length-1; j++) {
						aux = aux.concat(arrayStr[j]);
						aux = aux.concat(" - ");
					}
					
					
					ultNombreLiga = aux;
				}
				
				if( tr.getAttribute("class" ).equalsIgnoreCase("oddRow") || tr.getAttribute("class" ).equalsIgnoreCase("evenRow") ) {
					ultHora = tr.findElement(By.className("leftColumn")).getText();
					
					ultKey = Integer.parseInt(tr.findElements( By.className("rotCell") ).get(0).getText());
					
					PartidoParaApostarBO a = mapApuestas.get( ultKey );
                    if(a != null) {
                        if(a.getNombreLiga() == null || a.getDiaHoraJuego() == null ) {
                            a.setNombreLiga( ultNombreLiga );
                            try {
								a.setDiaHoraJuego( InterlocutorBetCrisImpl.getFechaFromStr( ultDiaDelMes, ultHora ) );
								
								result.add( a );
	                            
	                            mapApuestas.remove( ultKey );
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								
							}
                            
                            
                        }
                    }
				}
			}
		}
			
			
		System.out.println("NroTotal de Ligas = " + i);
		
		System.out.println("El MAP :: ");
		for( Integer key : mapApuestas.keySet() ) {
			System.out.println("key = " + key + "; apuesta = " + mapApuestas.get(key));
		}
		
		for( PartidoParaApostarBO bo : result ) {
			System.out.println(bo);
		}
		
		System.out.println("El num Total de apuestas :: " + result.size());
		
		return result;
	}
	
	public static GregorianCalendar getFechaFromStr(String fechaStr, String horaStr) throws Exception {
		GregorianCalendar result;
		
		String[] arrayMesDia = fechaStr.trim().split(" ");
		
		for(String s : arrayMesDia) {
			System.out.println("s = " + s);
		}
		
		String mesEnLetras = arrayMesDia[0];
		Integer dia = Integer.parseInt(arrayMesDia[1]);
		Integer mes;
		Integer ano = Calendar.getInstance().get(Calendar.YEAR);
		
		if("ENERO".startsWith(mesEnLetras.toUpperCase())) {
			mes = 1;
		} else if("FEBRERO".startsWith(mesEnLetras.toUpperCase())) {
			mes = 2;
		} else if("MARZO".startsWith(mesEnLetras.toUpperCase())) {
			mes = 3;
		} else if("ABRIL".startsWith(mesEnLetras.toUpperCase())) {
			mes = 4;
		} else if("MAYO".startsWith(mesEnLetras.toUpperCase())) {
			mes = 5;
		} else if("JUNIO".startsWith(mesEnLetras.toUpperCase())) {
			mes = 6;
		} else if("JULIO".startsWith(mesEnLetras.toUpperCase())) {
			mes = 7;
		} else if("AGOSTO".startsWith(mesEnLetras.toUpperCase())) {
			mes = 8;
		} else if("SEPTIEMBRE".startsWith(mesEnLetras.toUpperCase())) {
			mes = 9;
		} else if("OCTUBRE".startsWith(mesEnLetras.toUpperCase())) {
			mes = 10;
		} else if("NOVIEMBRE".startsWith(mesEnLetras.toUpperCase())) {
			mes = 11;
		} else if("DICIEMBRE".startsWith(mesEnLetras.toUpperCase())) {
			mes = 12;
		} else {
			throw new Exception("Mes Incompatible!!! " + mesEnLetras);
		}
		
		String[] array1Hora = horaStr.trim().split(" ");
		String[] array2Hora = array1Hora[0].split(":");
		
		int hour = Integer.parseInt(array2Hora[0]);
		int min = Integer.parseInt(array2Hora[1]);
		
		if(array1Hora[1].equalsIgnoreCase("PM")) {
			switch(hour) {
				case 1: hour = 13; break;
				case 2: hour = 14; break;
				case 3: hour = 15; break;
				case 4: hour = 16; break;
				case 5: hour = 17; break;
				case 6: hour = 18; break;
				case 7: hour = 19; break;
				case 8: hour = 20; break;
				case 9: hour = 21; break;
				case 10: hour = 22; break;
				case 11: hour = 23; break;
				case 12: hour = 24; break;
			}
		}
		
		result = new GregorianCalendar(ano, mes-1, dia);
		result.set(GregorianCalendar.HOUR_OF_DAY,hour);
		result.set(GregorianCalendar.MINUTE,min);
		
		return result;
	}


	
	public void montarApuestaCombinada(DefApuestaCombinadaBO apCombinada, HashSet<String> ligas) throws InteraccionException {
		_driver = getInstanceDriver();
		
		if(!isUserLoggedIn()) {
			logearseEnLaWeb();
		}
		
		WebElement select = _driver.findElement(By.id("ddWagerType"));
	    List<WebElement> options = select.findElements(By.tagName("option"));
	    for(WebElement option : options){
	        if(option.getText().equals("Combinada")) {
	            option.click();
	            break;
	        }
	    }
		
		
		// 
		WebElement element = _driver.findElement(By.id("FUTBOL")); 
		element.click();
		
		String txtWeb;
		String paisLiga;
		String paisWeb;
		String divisionLiga;
		String divisionWeb;
		
		int cant = 0;
		boolean salir = false;
		// Buscar todos los elementos checbox que esten dentro del CatFUTBOL_block
		List<WebElement> lis = _driver.findElement(By.id("CatFUTBOL_block")).findElements(By.tagName("li"));
		for(WebElement li : lis) {
			if(salir) break; 
			
			txtWeb = li.findElement(By.className("eventLink")).getText();	
			for( String liga : ligas ) {
				System.out.println("COMPARAR ::" + liga + "  CON  ::" + txtWeb );
				
				if(txtWeb.split("-").length >= 1) {
					paisLiga = liga.split("-")[0].trim();
					paisWeb = txtWeb.split("-")[0].trim();
					
					
					if(liga.split("-").length > 1) {
						divisionLiga = liga.split("-")[1].trim();
					} else {
						divisionLiga = "...";
					}
				
					if(txtWeb.split("-").length > 1) {
						divisionWeb = txtWeb.split("-")[1].trim();
					} else {
						divisionWeb = "...";
					}
					
					
					System.out.println( "paisLiga :: " + paisLiga + "    paisWeb :: " + paisWeb);
					System.out.println( "divisionLiga :: " + divisionLiga + "     divisionWeb :: " + divisionWeb );
					if( 
							( paisLiga.startsWith( paisWeb ) && divisionLiga.startsWith( divisionWeb ))
							
					) {
						System.out.println("si");
						
						li.findElement(By.className("checkBox")).click();
						
						cant++;
					
						System.out.println("Se hizo un check - ligas.size() :: " + ligas.size() + "; cant :: " + cant);
						
						if(ligas.size() == cant) {
							salir = true;
							
							break;
						}
						
					} else {
						System.out.println("NADA");
					}	
				}
			}
		}
		
		element = _driver.findElement(By.id("ctl00_mainContent_btnContinueTop")); 
		element.click();
		
		//-------------------->>>>>>>>>>>
		// Esperar la carga de las apuestas en la página
		esperar(6);
		
		//class title
		List<WebElement> barraLigas = _driver.findElements(By.className("title"));
		int i = 0;
		for(WebElement barra : barraLigas) {
			if(i > 0) {
				barra.click();
			}
			
			i++;
		}
		
		System.out.println("Total de iteraciones = " + i);
		
		
		// SE DESEA MONTAR LA SIGUIENTE APUESTA COMBINADA
		System.out.println("------------->>>>>>>>>>   SE DESEA MONTAR LA SIGUIENTE APUESTA COMBINADA  >>>>>>>>>>>>...");
		for( DefApuestaSencillaBO apInterna : apCombinada.getApuestasSencillas() ) {
			String xPath = "//td[@class = 'rightColumn' and "
				+ "("
				+ "./table/tbody/tr[1]/td[1]/span = '" + apInterna.getIdPartido() + "'"
				+ " or "
				+ "./table/tbody/tr[2]/td[1]/span = '" + apInterna.getIdPartido() + "'"
				+ " or "
				+ "./table/tbody/tr[3]/td[1]/span = '" + apInterna.getIdPartido() + "'"
				+ ")"
			+"]";
			
			
			WebElement td = _driver.findElement(By.xpath(xPath));
			System.out.println("El id = " + td.getAttribute("id") + ", internamente tiene al = " + apInterna.getIdPartido());
			
			xPath = "//*[@id='" + td.getAttribute("id") + "']/table/tbody/tr[./td/span='" + apInterna.getIdPartido() + "']/td[5]/label/input";
			WebElement checkApuesta = _driver.findElement(By.xpath(xPath));
			checkApuesta.click();
		}
		
		
		/*
		String xPath = "//table//tr";
		
		System.out.println("      ");
		System.out.println("  ************************************    ");
		System.out.println("  ************************************    ");
		System.out.println("  ************************************    ");
		System.out.println("      ******   xPath usado :: " + xPath);
		
		
		List<WebElement> trs = _driver.findElements(By.xpath(xPath));
		
		int partidosSeleccionados = 0;
		int partidosDeseados = 4;
		
		for(WebElement weTr : trs) {
			
			WebElement rotCell;
			try {
				rotCell = weTr.findElement(By.className("rotCell"));
			} catch (org.openqa.selenium.NoSuchElementException ex) {
				rotCell = null;
			}
			
			if( rotCell	 != null ) {
				for( DefApuestaSencillaBO apInterna : apCombinada.getApuestasSencillas() ) {
					System.out.println("Buscando :: " 
							+ apInterna.getIdPartido().toString()
							+ ". En :: " 
							+ weTr.getText());
					if( rotCell.getText().equalsIgnoreCase( apInterna.getIdPartido().toString() )
					){
						System.out.println("Encontrado");
						
						List<WebElement> checks = weTr.findElements(By.className("chkbox"));
						checks.get( checks.size() - 1 ).click();
						
						partidosSeleccionados++;
						
						break;
					}
				}
				
				
			}
			
			if(partidosSeleccionados == partidosDeseados) {
				break;
			}
		}
		*/
		
	}

}
