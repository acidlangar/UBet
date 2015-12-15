package org.idsiom.utilbet.history.fromoddsportal.interlocutor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.common.oddsportal.UtilOP;
import org.idsiom.utilbet.history.fromoddsportal.Cons;
import org.idsiom.utilbet.history.fromoddsportal.UtilSelenium;
import org.idsiom.utilbet.history.fromoddsportal.bo.POddsPortalBO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OddsPortalInterlocutorImpl implements IOddsPortalSource {
	private WebDriver driver;

	static Logger logger = Logger.getLogger(OddsPortalInterlocutorImpl.class);

	public List<POddsPortalBO> getPs(String country, String lig, String temp) throws Exception {
		List<String> urlsInternos = new ArrayList<String>();
		List<POddsPortalBO> result = null;
		this.driver = UtilSelenium.getInstanciaWD();
		String urlBaseInicial = getUrlFinal(country, lig, temp);

		System.out.println("buscando :: " + urlBaseInicial);
		this.driver.get(urlBaseInicial);

		urlsInternos.add(urlBaseInicial);

		String xPathPag = ".//*[@id='table-matches']/a";  
		List<WebElement> listAs = this.driver.findElements(By.xpath(xPathPag));

		System.out.println(listAs.size());
		// for(WebElement we : listAs) {

		for (int i = 2; i < listAs.size() - 2; i++) {
			StringBuffer sb = new StringBuffer();

			sb.append(urlBaseInicial);
			sb.append("#/page/");
			sb.append(i);
			sb.append("/");

			urlsInternos.add(sb.toString());
			sb = null;
		}

		result = new ArrayList<POddsPortalBO>();
		for (String url : urlsInternos) {

			

				System.out.println("---------->>>>>>>");
				System.out.println(url);

				this.driver.get(url);

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

				POddsPortalBO bo;

				String fechaAnterior = "";
				int contFila = 1;
				for (WebElement weFila : weFilasList) {
					contFila++;
					try {
					
					if (weFila.getAttribute("class")
							.equals("center nob-border")) {
						// System.out.println("---------->>>>>>  Fecha");
						fechaAnterior = weFila.findElement(By.tagName("span"))
								.getText();
					}

					if (weFila.getAttribute("class").endsWith("deactivate")) {
						// System.out.println("---------->>>>>>  Partido");
						bo = new POddsPortalBO();

						bo.setCountry(country);
						bo.setLeague(lig);
						bo.setTemp(temp);

						WebElement weHora = weFila.findElement(By
								.xpath("./td[1]"));

						WebElement weEquipos = weFila.findElement(By
								.xpath("./td[2]"));
						// System.out.println("Equipos :: " +
						// weEquipos.getText() );

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

						WebElement weResultStr = weFila.findElement(By
								.xpath("./td[3]"));
						bo.setrStr(weResultStr.getText());

						if(weResultStr.getText().trim().length() > 0) {
							Integer auxArr2[] = getGs(weResultStr.getText());
							
							if(auxArr2 != null) {
								bo.setgL(auxArr2[0]);
								bo.setgV(auxArr2[1]);
		
								WebElement weC1 = weFila.findElement(By
										.xpath("./td[4]"));
								bo.setC1(Double.parseDouble(weC1.getText()));
		
								WebElement weCX = weFila.findElement(By
										.xpath("./td[5]"));
								bo.setcX(Double.parseDouble(weCX.getText()));
		
								WebElement weC2 = weFila.findElement(By
										.xpath("./td[6]"));
								bo.setC2(Double.parseDouble(weC2.getText()));
		
								result.add(bo);
							} else {
								logger.info("Registro " + contFila + " de " + url + " no tomado en cuenta porque el resultado no se reconoce resultado esta vacio " + weResultStr.getText());
							}
						} else {
							logger.info("Registro " + contFila + " de " + url + " no tomado en cuenta porque resultado esta vacio ");
						}
					}

				} catch (Exception ex) {
					String msjError;
					if(weFila == null) {
						msjError = "No fue posible procesar " + url + ", en la fila " + contFila + ", porque weFila es NULL ";
					} else {
						msjError = "No fue posible procesar " + url + ", en la fila " + contFila 
								+ ",  weFila.toString = " + weFila.toString() 
								+ ",  weFila.getText = " + weFila.getText()
								+ ",  weFila.getAttribute(class) = " + weFila.getAttribute("class")
								+ ",  weFila.getAttribute(ccs) = " + weFila.getAttribute("ccs");
					}
					
					logger.error(msjError, ex);
					
					this.driver.close();
					this.driver = null;
					urlsInternos = null;
					
					throw new Exception("No fue posible procesar " + url);

				} 	
					
				} // for por filas

			

		} // for url-interno

		this.driver.close();
		this.driver = null;
		urlsInternos = null;

		return result;
	}

	private Integer[] getGs(String rFinal) {
		Integer[] array = { -1, -1 };
		
		if(rFinal.equalsIgnoreCase("award.")) {
			return null;
		}

		String soloG = rFinal.trim().split(" ")[0];

		array[0] = Integer.parseInt(soloG.split(":")[0]);
		array[1] = Integer.parseInt(soloG.split(":")[1]);

		return array;
	}

	

	private String getUrlFinal(String country, String lig, String temp) {
		String result = "";

		result = Cons.URL_BASE.replaceFirst("Z", country);

		result = result.replaceFirst("Z", lig);

		result = result.replaceFirst("Z", temp);
		System.out.println(result);

		return result;
	}

	public Integer getCantPagsTemp(String country, String lig, String temp) {

		return null;
	}

}
