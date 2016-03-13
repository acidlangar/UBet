package org.idsiom.utilbet.currentuse.interlocutor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.util.UtilProperties;
import org.idsiom.utilbet.history.fromoddsportal.Cons;
import org.idsiom.utilbet.history.fromoddsportal.UtilSelenium;
import org.idsiom.utilbet.util.UtilFecha;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OddsPortalInterCurrentUseImpl implements IOddsPortalCurrentUseInterlocutor {

	private WebDriver driver;

	private static OddsPortalInterCurrentUseImpl instance;

	public static OddsPortalInterCurrentUseImpl getInstance() throws IOException {
		if (instance == null) {
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
		loginOddPortal.sendKeys(utilProperties.getProp().getProperty("oddsportal.user"));

		WebElement pwdOddPortal = driver.findElement(By.id("login-password"));
		pwdOddPortal.sendKeys(utilProperties.getProp().getProperty("oddsportal.pwd"));

		WebElement buttonOddPortal = driver.findElement(By.name("login-submit"));
		buttonOddPortal.click();

	}

	static Logger logger = Logger.getLogger(OddsPortalInterCurrentUseImpl.class);

	/*
	 * Si el boolean es true, la fecha desde es hoy, en caso contrario la que
	 * indique el usuario
	 * 
	 * @see org.idsiom.utilbet.currentuse.interlocutor.
	 * IOddsPortalCurrentUseInterlocutor#getPs(java.lang.Boolean)
	 */
	public ListPartidosSerializable getPs(Boolean reutilizarHistoria) throws Exception {

		String urlMyMatches = Cons.URL_MYMATCHES_CURRENT; // Guardara el String
															// del URL del dia
															// especifico
		String fechaDesdeStr;
		// String fechaHastaStr;
		GregorianCalendar gcFDesde;
		GregorianCalendar gcFHasta;
		Scanner in;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// GregorianCalendar gc = new GregorianCalendar();

		ListPartidosSerializable resultFinal = new ListPartidosSerializable();
		in = new Scanner(System.in);

		Date date;

		if (reutilizarHistoria) {
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
		// gcFHasta.setTime(date);

		System.out.println("fechaDesde: " + sdf.format(gcFDesde.getTime()));
		System.out.println("fechaHasta: " + sdf.format(gcFHasta.getTime()));

		GregorianCalendar gcHoy_0000 = new GregorianCalendar();

		gcHoy_0000.set(Calendar.HOUR_OF_DAY, 0);
		gcHoy_0000.set(Calendar.MINUTE, 0);
		gcHoy_0000.set(Calendar.SECOND, 0);
		gcHoy_0000.set(Calendar.MILLISECOND, 0);

		List<CurrentPOddsPortal> listAux;
		Boolean makePause = false;
		while (gcFDesde.before(gcFHasta)) {
			System.out.println(sdf.format(gcFDesde.getTime()));

			listAux = this.getPs(sdf.format(gcFDesde.getTime()), urlMyMatches + sdf.format(gcFDesde.getTime()) + "/",
					makePause);

			if (listAux.size() > 0) {

				if (gcFDesde.before(gcHoy_0000)) {
					resultFinal.getPartidosHistory().addAll(listAux);
				} else {
					resultFinal.getListaPsHoyFuturo().addAll(listAux);
				}

				gcFDesde.add(GregorianCalendar.DAY_OF_YEAR, 1);
				makePause = false;
			} else {
				System.out.println("No se encontraron partidos para la fecha, " + sdf.format(gcFDesde.getTime())
						+ ", pulse 1 si desea reintentar... ");
				// seguirIntAux = in.nextInt();

				// if(seguirIntAux == 1) {
				makePause = true;
				// } else {
				// gcFDesde.add(GregorianCalendar.DAY_OF_YEAR,1);
				// makePause = false;
				// }
			}

		}

		in.close();
		in = null;

		return resultFinal;
	}

	private List<CurrentPOddsPortal> getPs(String fechaStr, String urlDay, Boolean makePause) throws Exception {

		List<CurrentPOddsPortal> partidosResult = new ArrayList<CurrentPOddsPortal>();
		System.out.println("Se llamara al metodo de verificacion de la paginacion");
		List<String> urlsConPaginacion = this.getUrlsConPaginacion(urlDay);

		Boolean urlCargado;
		for (String urlIndividual : urlsConPaginacion) {

			urlCargado = false;

			// http://stackoverflow.com/questions/5709204/random-element-is-no-longer-attached-to-the-dom-staleelementreferenceexception
			// http://stackoverflow.com/questions/17174515/element-is-no-longer-attached-to-the-dom
			while (!urlCargado) {
				try {

					try {
						System.out.println("Obteniendo " + urlIndividual);
						driver.get(urlIndividual);
					} catch (org.openqa.selenium.UnhandledAlertException ex) {
						System.out.println(
								"Se detecto una Alerta inesperda, se ignorara. - Detalle :: " + ex.getAlertText());

					}

					// Todos los tr de la tabla partidos
					String xPathPag = ".//*[@id='table-matches']/*/*/tr"; // .//*[@id='table-matches']/*/*/tr

					List<WebElement> listTRs = driver.findElements(By.xpath(xPathPag));

					List<WebElement> listTHs;
					List<WebElement> listTDs;

					String pais = null;
					String liga = null;
					CurrentPOddsPortal bo = new CurrentPOddsPortal();

					System.out.println("Cant de Rows :: " + listTRs.size());
					int i = 1;
					int lenTr = 0;
					for (WebElement tr : listTRs) {
						lenTr = 0;
						if (tr != null) {
							lenTr = tr.getText().trim().length();
						}

						if (tr != null && lenTr > 0) {
							listTHs = tr.findElements(By.tagName("th"));

							if (listTHs != null && listTHs.size() > 0) {

								//
								String aux = tr.getText().trim();
								String array[] = aux.split("\n»\n");

								if (array.length >= 2) {
									pais = array[0].trim();
									liga = array[1].trim();
								} else if (array.length == 1) {
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

								if (listTDs.size() == 6) { // Juego planificado
									bo.setFecha(fechaStr + " " + listTDs.get(0).getText());
									bo.setEquipos(listTDs.get(1).getText());
									bo.setC1(Double.valueOf(listTDs.get(2).getText()));
									bo.setcX(Double.valueOf(listTDs.get(3).getText()));
									bo.setC2(Double.valueOf(listTDs.get(4).getText()));

								} else if (listTDs.size() == 7) { // Juego
																	// finalizado
																	// o
																	// post
									bo.setFecha(fechaStr + " " + listTDs.get(0).getText());
									bo.setEquipos(listTDs.get(1).getText());

									//System.out.println(listTDs);

									String horaAux = listTDs.get(0).getText();
									if (horaAux.contains(":")) {
										bo.setrStr(listTDs.get(2).getText());
									}

									if (!listTDs.get(3).getText().trim().equals("-")) {
										bo.setC1(Double.valueOf(listTDs.get(3).getText()));
										bo.setcX(Double.valueOf(listTDs.get(4).getText()));
										bo.setC2(Double.valueOf(listTDs.get(5).getText()));
									} else {
										bo.setC1(0.0);
										bo.setcX(0.0);
										bo.setC2(0.0);
									}

								}

								partidosResult.add(bo);
							}
						} // validacion de que la linea no sea vacia
						System.out.print(i + " ; ");

						i++;
					} // for trs

					urlCargado = true;
				} catch (org.openqa.selenium.StaleElementReferenceException sere) {
					urlCargado = false;
					System.out.println("Se presento el problema de Stale, se reintentara cargar");
					System.out.println(sere.getMessage());
				}

			} // while urlCargado
		} // for urlsPaginacion

		return partidosResult;
	}

	/*
	 * Este metodo devuelve una lista de todos los urls a llamar En caso de
	 * tener muchos partidos se usa paginacion, entonces para un mismo dia
	 * habria varios urls a llamar
	 */
	private List<String> getUrlsConPaginacion(String urlDay) {
		List<String> arrayUrls = new ArrayList<String>();
		Boolean existePaginacion = false;
		int lastPage;

		// Averiguar si tiene paginacion
		this.driver.get(urlDay);

		try {
			Thread.sleep(3000);
		} catch (Exception ex) {
			// Dejado libre intencionalmente
		}

		WebElement we = null;
		try {
			we = this.driver.findElement(By.id("pagination"));
			if (we != null) {
				existePaginacion = true;
			}
		} catch(NoSuchElementException nsee) {
			System.out.println(nsee.getMessage());
			existePaginacion = false;
		}
		

		// ---- En caso afirmativo
		if (existePaginacion) {
			// ---- ---- Se busca el num de la ultima pagina
			List<WebElement> listWe = we.findElements(By.tagName("a"));
			System.out.println(we.getText());
			System.out.println(listWe.size());
			WebElement weLast = listWe.get(4);
			lastPage = Integer.parseInt(weLast.getAttribute("x-page"));

			// ---- ---- Se cicla desde 1 a ultimo num y se contruyen todos los
			// urls
			for (int i = 1; i <= lastPage; i++) {
				arrayUrls.add(urlDay + "#/page/" + i);
				System.out.println(urlDay + "#/page/" + i);
			}
		} else {
			// ---- En caso negativo
			// ---- ---- La lista es solamente formada por el url de parametro
			// de entrada
			arrayUrls.add(urlDay);
		}
		return arrayUrls;
	}

	private String getUrlFinal() {
		String result = "";

		result = Cons.URL_BASE_CURRENT;

		return result;
	}

}
