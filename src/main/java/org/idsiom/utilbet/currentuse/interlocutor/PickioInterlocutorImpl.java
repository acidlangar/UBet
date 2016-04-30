package org.idsiom.utilbet.currentuse.interlocutor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;
import org.idsiom.utilbet.currentuse.interlocutor.exception.InteraccionException;
import org.idsiom.utilbet.currentuse.util.LevenshteinDistance;
import org.idsiom.utilbet.currentuse.util.UtilProperties;
import org.idsiom.utilbet.history.fromoddsportal.UtilSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Wait;

public class PickioInterlocutorImpl implements IPyckioInterlocutor {
	public static final String PAGE_SIGNIN_PYCKIO = "https://pyckio.com/signin";

	static Logger logger = Logger.getLogger(PickioInterlocutorImpl.class);

	private static PickioInterlocutorImpl instance;
	private WebDriver driver;

	private List<PartidoPyckioBO> listPartidos;

	private static String basePyckio = "https://pyckio.com/i/#";

	private static String hrefActivePycks = "https://pyckio.com/i/#picks-active";

	// private static String hrefActiveArchive =
	// "https://pyckio.com/i/#picks-archive";

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
		if (buttonCookies != null) {
			buttonCookies.click();
		}

		// Proceder a logear contra Pyckio
		//
		WebElement loginPyckio = driver.findElement(By.id("email"));
		System.out.println("Login de Pyckio :: " + utilProperties.getProp().getProperty("pyckio.user"));
		loginPyckio.sendKeys(utilProperties.getProp().getProperty("pyckio.user"));

		WebElement pwdPyckio = driver.findElement(By.id("pwd"));
		pwdPyckio.sendKeys(utilProperties.getProp().getProperty("pyckio.pwd"));

		WebElement buttonPyckio = driver.findElement(By.id("btn-signin"));
		buttonPyckio.click();

	}

	public void iniciarSesion(String usuario, String clave) {
		// TODO Auto-generated method stub
		/*
		 * id email id pwd
		 * 
		 * btn-signin
		 */
	}

	public int getCantidadPycksActivos() throws InteraccionException {

		// ------------------------>>>>>>>>>>>>>
		// Pulsar el nombre
		// *[@id="auth"]/div/div[1]/div/a tambien serviria buscar la imagen del
		// avatar // Buscar la imagen con el alt de avatar
		String xPath = ".//*[@id='auth']/div/div[1]/div/a";
		WebElement imgAccount = null;

		boolean reintentar = true;
		int intentos = 0;
		while (reintentar) {
			intentos++;
			try {
				imgAccount = this.driver.findElement(By.xpath(xPath));
				reintentar = false;
			} catch (org.openqa.selenium.NoSuchElementException e) {
				logger.info(
						"Se genero un org.openqa.selenium.NoSuchElementException para controlar :: " + e.getMessage());
				System.out.println(
						"Se genero un org.openqa.selenium.NoSuchElementException para controlar :: " + e.getMessage());

				espera();

				if (intentos >= 4) {
					reintentar = false;
				}

			}

		}

		if (imgAccount == null) {
			throw new InteraccionException();
		}

		imgAccount.click();

		espera();

		// ------------------------>>>>>>>>>>>>>
		// Contar la cantidad de registros en la tabla
		// *[@id="picks-active"]/div/table/tbody/tr
		xPath = ".//*[@id='picks-active']/div/table/tbody/tr";

		List<WebElement> listWE = this.driver.findElements(By.xpath(xPath));
		return listWE.size();

	}

	public void montarPick(CurrentPOddsPortal pOP, ResultadoPartidoBO resultBuscado, int i) throws Exception {
		PartidoPyckioBO partidoPIO = this.findTraduction(listPartidos, pOP);

		this.montarPick(partidoPIO, resultBuscado, i);
	}

	public boolean montarPick(PartidoPyckioBO partidoPIO, ResultadoPartidoBO resultBuscado, int i) throws Exception {
		int pycks_activos_preview = 0;
		int pycks_activos_post = 0;

		// ----------------------->>>>>>>>>>>>>
		// ----------- Primero validamos la cantidad de Pycks - Activos Previos
		pycks_activos_preview = this.getCantidadPycksActivos();

		WebElement seleccion = partidoPIO.getWebElement().findElements(By.tagName("td")).get(4);
		WebElement seleccionA = seleccion.findElement(By.tagName("a"));
		System.out.println(
				"********************************************************************************************");
		System.out.println("****************************** seleccion = " + seleccionA.getText());
		System.out.println(
				"********************************************************************************************* ");

		try {
			Thread.sleep(10000);
		} catch (Exception ex) {
			// no hacer nada
		}

		logger.info("href antes de la info " + this.driver.getTitle() + "   " + this.driver.getCurrentUrl());
		logger.info("seleccionA.getAttribute(href) " + seleccionA.getAttribute("href"));

		// String newUrl = "https://pyckio.com/i/" +
		// seleccionA.getAttribute("href");
		String newUrl = seleccionA.getAttribute("href");
		this.driver.get(newUrl);
		// seleccionA.click();

		logger.info("href despues de la info " + this.driver.getTitle() + "   " + this.driver.getCurrentUrl());

		System.out.println(
				"********************************************************************************************");
		System.out
				.println("****************************** SE ACABA DE HACER CLICK EN LA SELECCION CON newUrl " + newUrl);
		System.out.println(
				"********************************************************************************************* ");

		try {
			Thread.sleep(10000);
		} catch (Exception ex) {
			// no hacer nada
		}

		int iAux;
		if (resultBuscado.equals(ResultadoPartidoBO.LOCAL)) {
			iAux = 1;
		} else if (resultBuscado.equals(ResultadoPartidoBO.VISITANTE)) {
			iAux = 2;
		} else {
			iAux = 3;
		}

		// Buscar por XPath las tres barras...
		// id('1X2')/x:table/x:tbody/x:tr/x:td[2]
		String xPath = ".//*[@id='1X2']/table/tbody/tr[" + iAux + "]/td[2]";
		WebElement barraStakeSeleccionada = null;

		Boolean seguir = true;
		int intentos = 0;
		while (seguir) {
			try {
				intentos++;
				barraStakeSeleccionada = driver.findElement(By.xpath(xPath));

				System.out.println("barraStakeSeleccionada = " + barraStakeSeleccionada.getText());
				seguir = false;
			} catch (org.openqa.selenium.NoSuchElementException e) {

				logger.info("href antes de la info " + this.driver.getTitle() + "   " + this.driver.getCurrentUrl());
				logger.info("seleccionA.getAttribute(href) " + seleccionA.getAttribute("href"));

				// seleccionA.click();

				newUrl = seleccionA.getAttribute("href");
				this.driver.get(newUrl);

				logger.info("href despues de la info " + this.driver.getTitle() + "   " + this.driver.getCurrentUrl());

				System.out.println("-------------------------------------------------------- ");
				System.out.println("No fue encontrado el elemento  intento " + intentos
						+ " Intentaremos nuevamnte click en " + seleccionA.getText());
				logger.info("No fue encontrado el elemento  intento " + intentos + " Intentaremos nuevamnte click en "
						+ seleccionA.getText());
				System.out.println("-------------------------------------------------------- ");
				System.out.println("-------------------------------------------------------- ");

				Thread.sleep(intentos * 2000);

				if (intentos > 4) {
					seguir = false;
				}
			}
		}

		if (barraStakeSeleccionada != null) {
			// div/x:label[1]
			xPath = "div/label[" + i + "]";
			barraStakeSeleccionada.findElement(By.xpath(xPath)).click();

			// btn btn-default js-sendtwip
			xPath = "//*[@id='event']/div/div/div[2]/div[3]/form/button";
			driver.findElement(By.xpath(xPath)).click();

			// ----------------------->>>>>>>>>>>>>
			// ----------- Primero validamos la cantidad de Pycks - Activos
			// Previos
			pycks_activos_post = this.getCantidadPycksActivos();

			if ((pycks_activos_preview + 1) == pycks_activos_post) {
				return true;
			} else {

				logger.error("No se montó el Pyck, la cantidad de Pycks activos no fue actualizada");

				return false;
			}

		}

		return false;

	}

	public Boolean montar(ResultadoPartidoBO resultBuscado, int stake) {
		((JavascriptExecutor)this.driver).executeScript("window.scrollTo(0, 0);");
		
		
		int iAux;
		if (resultBuscado.equals(ResultadoPartidoBO.LOCAL)) {
			iAux = 1;
		} else if (resultBuscado.equals(ResultadoPartidoBO.VISITANTE)) {
			iAux = 2;
		} else {
			iAux = 3;
		}

		// Buscar por XPath las tres barras...
		// id('1X2')/x:table/x:tbody/x:tr/x:td[2]
		String xPath = ".//*[@id='1X2']/table/tbody/tr[" + iAux + "]/td[2]";
		WebElement barraStakeSeleccionada = null;

		Boolean seguir = true;
		int intentos = 0;
		while (seguir) {
			try {
				intentos++;
				barraStakeSeleccionada = driver.findElement(By.xpath(xPath));

				System.out.println("barraStakeSeleccionada = " + barraStakeSeleccionada.getText());
				seguir = false;
			} catch (org.openqa.selenium.NoSuchElementException e) {
				logger.error(e, e);
				e.printStackTrace();
				return false;
			}
		}

		if (barraStakeSeleccionada != null) {
			// div/x:label[1]
			do {
				xPath = "div/label[" + stake + "]";
				barraStakeSeleccionada.findElement(By.xpath(xPath)).click();
				
				xPath = "//*[@id='1X2']/div/b";
				String strUnits = driver.findElement(By.xpath(xPath)).getText().trim();
				System.out.println(strUnits);
				if (strUnits.trim().equals("+0 units")) {
					logger.info("No fue seleccionado el stake en la barra");
					System.out.println("No fue seleccionado el stake");
					seguir = true;
				} else {
					seguir = false;
					System.out.println("Fue seleccionado el stake, se puede seguir");
				}
			
			} while(seguir);		
			
			
			// btn btn-default js-sendtwip
			do {
				//xPath = "//*[@id='event']/div/div/div[2]/div[3]/form/button";
				xPath = "//*[@id='event']/div/div/div[2]/div[3]/form/button[contains(@class,'js-sendtwip')]";
				
				((JavascriptExecutor)this.driver).executeScript("window.focus();");
				((JavascriptExecutor)this.driver).executeScript("scrollTo(0, 200);");
				
				
				try {
					Thread.sleep(2000);													
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				driver.findElement(By.xpath(xPath)).click();
				
				xPath = "//*[@id='1X2']/div/b";
				String strUnits = driver.findElement(By.xpath(xPath)).getText().trim();
				System.out.println(strUnits);
				if (strUnits.trim().equals("+0 units")) {
					logger.info("Se inicio el button!!!, porque " + strUnits);
					System.out.println("Se inicio el button!!!, porque " + strUnits);
					seguir = false;
				} else {
					seguir = true;
					System.out.println("Repetir :(, porque " + strUnits);
				}
				
			} while(seguir);
			
			
			// ----------------------->>>>>>>>>>>>>
			// ----------- Esperamos por el mensaje del Alert
			WebDriverWait wait = new WebDriverWait(this.driver, 40);

			try {
				String xPathMsj = "//*[contains(@class, 'js-picksaved')]";
				//String xPathMsj = "//*[contains(@class, 'js-twiperror')]";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathMsj)));

				WebElement weMsj = this.driver.findElement(By.xpath(xPathMsj));
				System.out.println(weMsj.getText());
				logger.info(weMsj.getText());
				return true;

			} catch (TimeoutException ex) {
				logger.error(ex, ex);
				ex.printStackTrace();
				return false;
			}

		}

		return false;

	}

	public List<PartidoPyckioBO> getPartidosPorHora(Long momento) {
		listPartidos = new ArrayList<PartidoPyckioBO>();

		driver.get("https://pyckio.com/i/#!home/todaygames");

		// String xPathPag =
		// "id('mytimeline')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr[td[2]/img[@data-original-title='soccer']]";
		// //
		// id('mytimeline')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr[td[2]/img[@data-original-title='soccer']]
		String xPathPag = ".//*[@id='mytimeline']/div/div[2]/div/table/tbody/tr[td[2]/img[@data-original-title='soccer']]";
		// String xPathPag = ".//*[@id='mytimeline']";

		try {
			Thread.sleep(6000);
		} catch (Exception ex) {

		}

		List<WebElement> listTRs = this.driver.findElements(By.xpath(xPathPag));
		System.out.println("paso ");

		List<WebElement> listTDs;
		PartidoPyckioBO p;

		for (WebElement tr : listTRs) {
			System.out.println("__________>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
			System.out.println("__________ ");
			System.out.println("__________ ");
			listTDs = tr.findElements(By.tagName("td"));

			String equipos = listTDs.get(4).getText();
			p = new PartidoPyckioBO();

			p.setFechaStr(listTDs.get(0).getText());
			p.setPais(listTDs.get(2).findElement(By.tagName("img")).getAttribute("alt"));
			p.setLiga(listTDs.get(3).getText());
			p.setEquipoLocal(equipos.split("-")[0].trim());
			p.setEquipoVisitante(equipos.split("-")[1].trim());
			p.setWebElement(tr);

			listPartidos.add(p);
		}

		return listPartidos;
	}

	public PartidoPyckioBO findTraduction(List<PartidoPyckioBO> list, CurrentPOddsPortal pop) {
		PartidoPyckioBO result = null;
		List<PartidoPyckioBO> listHora_Pais = filtrarPorHora_Pais(list, pop);

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		// String fechaOP;
		// String fechaPIO;

		// System.out.println(pop);
		// System.out.println("oddsPortal LOCAL = " + pop.getEquipoLocal());

		for (PartidoPyckioBO pio : listHora_Pais) {
			// fechaOP = sdf.format(pop.getFechaGC().getTime());
			// fechaPIO = sdf.format(pio.getFechaGC().getTime());
			// System.out.println("op = " + fechaOP + " ppio = " + fechaPIO + "
			// ppio : " + pio.getPais() + " Local = " + pio.getEquipoLocal() );
			if (pio.getEquipoLocal().trim().equals(pop.getEquipoLocal().trim())
					|| pio.getEquipoVisitante().trim().equals(pop.getEquipoVisitante().trim())) {
				result = pio;
			}
		}

		if (listHora_Pais.size() > 0 && result == null) {
			// Obtener el más parecido
			result = obtenerElMasParecido(listHora_Pais, pop);
		}

		return result;
	}

	private static PartidoPyckioBO obtenerElMasParecido(List<PartidoPyckioBO> listHora_Pais, CurrentPOddsPortal pop) {

		for (PartidoPyckioBO ppio : listHora_Pais) {
			ppio.calValDistancia(pop);
		}

		Collections.sort(listHora_Pais);

		return listHora_Pais.get(0);
	}

	private static String obtenerElMasParecido(Set<String> strs, String buscado) {
		int distancia;
		HashMap<Integer, String> mapa = new HashMap<Integer, String>();
		for (String s : strs) {
			distancia = LevenshteinDistance.computeLevenshteinDistance(s.trim(), buscado.trim());
			mapa.put(distancia, s);
		}

		List<Integer> distancias = new ArrayList<Integer>(mapa.keySet());
		Collections.sort(distancias);

		return mapa.get(distancias.get(0));

	}

	private static List<PartidoPyckioBO> filtrarPorHora_Pais(List<PartidoPyckioBO> list, CurrentPOddsPortal pop) {
		List<PartidoPyckioBO> listHora_Pais = new ArrayList<PartidoPyckioBO>();
		List<PartidoPyckioBO> result = new ArrayList<PartidoPyckioBO>();

		Set<String> ligas = new HashSet<String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		String fechaOP;
		String fechaPIO;

		for (PartidoPyckioBO ppio : list) {
			if (ppio != null) {
				try {
					fechaOP = sdf.format(pop.getFechaGC().getTime());
					fechaPIO = sdf.format(ppio.getFechaGC().getTime());
					// System.out.println("op = " + fechaOP + " ppio = " +
					// fechaPIO + " ppio : " + ppio.getPais() );

					if (fechaOP.equals(fechaPIO) && pop.getCountry().equals(ppio.getPais())) {
						listHora_Pais.add(ppio);
						ligas.add(ppio.getLiga()); // Identificar las distintas
													// ligas del mismo pais
					}
				} catch (Exception ex) {
					logger.error("Problema inesperado al evaluar la fecha y el pais de este partido en Pyckio :: "
							+ ppio.toString(), ex);
				}
			}

		}

		// En caso de ser varias ligas, aplicar el algoritmo de distancia a las
		// ligas
		if (ligas.size() > 1) {
			String unicaLiga = obtenerElMasParecido(ligas, pop.getLeague());

			for (PartidoPyckioBO p : listHora_Pais) {
				if (p.getLiga().trim().equals(unicaLiga)) {
					result.add(p);
				}
			}

			return result;
		} else {
			return listHora_Pais;
		}

	}

	private void espera() {
		try {
			Thread.sleep(5000);
		} catch (Exception ex) {

		}
	}

	public void close() {

		try {
			Thread.sleep(10000);
		} catch (Exception ex) {

		}

		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Se llamo al CLOSE del Interlocutor Pyckio");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		this.driver.close();

		instance = null;

	}

}
