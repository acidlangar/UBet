package org.idsiom.utilbet.analisis.interlocutor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.analisis.AnalisisCons;
import org.idsiom.utilbet.analisis.bo.POPScheduleBO;
import org.idsiom.utilbet.common.oddsportal.UtilOP;
import org.idsiom.utilbet.history.fromoddsportal.UtilSelenium;
import org.idsiom.utilbet.history.fromoddsportal.bo.POddsPortalBO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OddsPortalScheduleInterlocutorImpl implements ISourceSchedule {

	private WebDriver driver;

	static Logger logger = Logger
			.getLogger(OddsPortalScheduleInterlocutorImpl.class);

	public List<POPScheduleBO> getNextSchedule(String country, String lig) throws Exception {
		List<POPScheduleBO> result = new ArrayList<POPScheduleBO>();
		this.driver = UtilSelenium.getInstanciaWD();
		String urlBaseInicial = getUrlFinal(country, lig);

		System.out.println("buscando :: " + urlBaseInicial);
		this.driver.get(urlBaseInicial);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String xPathFilas = ".//*[@id='tournamentTable']/tbody/tr";

		List<WebElement> weFilasList = this.driver.findElements(By
				.xpath(xPathFilas));

		System.out.println("La cantidad de filas encontradas fue :: "
				+ weFilasList.size());

		POPScheduleBO bo;

		String fechaAnterior = "";
		int contFila = 1;
		for (WebElement weFila : weFilasList) {
			contFila++;

			try {

				if (weFila.getAttribute("class").equals("center nob-border")) {
					// System.out.println("---------->>>>>>  Fecha");
					fechaAnterior = weFila.findElement(By.tagName("span"))
							.getText();
				}

				
				String strXeid = weFila.getAttribute("xeid");
				if (strXeid != null && strXeid.length() > 0 && !weFila.getAttribute("class").equals("odd hidden")) {
					// System.out.println("---------->>>>>>  Partido");
					bo = new POPScheduleBO();

					bo.setCountry(country);
					bo.setLeague(lig);
					
					WebElement weHora = weFila.findElement(By.xpath("./td[1]"));

					WebElement weEquipos = weFila.findElement(By
							.xpath("./td[2]"));
					logger.info("fila :: " + contFila  + "  Equipos :: " + weEquipos.getText() );

					bo.setFecha(fechaAnterior + " " + weHora.getText());

					String auxArr[];
					try {
						auxArr = UtilOP.getArrayEqs(country, weEquipos.getText());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}

					bo.setEqL(auxArr[0].trim());
					bo.setEqV(auxArr[1].trim());

					
						
					WebElement weC1 = weFila.findElement(By
							.xpath("./td[3]"));
					bo.setC1(Double.parseDouble(weC1.getText()));

					WebElement weCX = weFila.findElement(By
							.xpath("./td[4]"));
					bo.setcX(Double.parseDouble(weCX.getText()));

					WebElement weC2 = weFila.findElement(By
							.xpath("./td[5]"));
					bo.setC2(Double.parseDouble(weC2.getText()));

					result.add(bo);

					
				}

			} catch (Exception ex) {
				String msjError;
				if (weFila == null) {
					msjError = "No fue posible procesar " + urlBaseInicial
							+ ", en la fila " + contFila
							+ ", porque weFila es NULL ";
				} else {
					msjError = "No fue posible procesar " + urlBaseInicial
							+ ", en la fila " + contFila
							+ ",  weFila.getText = " + weFila.getText();
				}

				logger.error(msjError, ex);

				this.driver.close();
				this.driver = null;
				
				throw new Exception("No fue posible procesar " + urlBaseInicial);

			}

		}

		this.driver.close();
		this.driver = null;

		return result;
	}

	private String getUrlFinal(String country, String lig) {
		String result = "";

		result = AnalisisCons.OP_URL_BASE.replaceFirst("Z", country);

		result = result.replaceFirst("Z", lig);

		System.out.println(result);

		return result;
	}

}
