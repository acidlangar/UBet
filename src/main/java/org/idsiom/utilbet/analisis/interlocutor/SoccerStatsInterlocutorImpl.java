package org.idsiom.utilbet.analisis.interlocutor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.analisis.AnalisisCons;
import org.idsiom.utilbet.analisis.bo.PosicionTableBO;
import org.idsiom.utilbet.analisis.bo.TRankingP;
import org.idsiom.utilbet.history.fromoddsportal.UtilSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SoccerStatsInterlocutorImpl implements ISourceTables {

	private WebDriver driver;

	static Logger logger = Logger.getLogger(SoccerStatsInterlocutorImpl.class);
	
	public List<TRankingP> obtenerTs(String country) {
		this.driver = UtilSelenium.getInstanciaWD();
		String urlBaseInicial = getUrlFinal(country);
		
		List<TRankingP> result = new ArrayList<TRankingP>();
		
		logger.info("buscando :: " + urlBaseInicial);
		this.driver.get(urlBaseInicial);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WebElement> tablasWE = this.driver.findElements(By.id("btable"));
		
		logger.info("Tamano de lista ::  " + tablasWE.size());
		
		for(WebElement table : tablasWE) {
			logger.info(table.getText());
			
			TRankingP t = new TRankingP();
			
			List<WebElement> posicionesWE = table.findElements(By.className("odd"));
			
			logger.info("Cantidad de posiciones ::  " + posicionesWE.size());
			for(WebElement posicionWE : posicionesWE) {
				PosicionTableBO pt = new PosicionTableBO();
				
				String auxStr = posicionWE.findElement(By.xpath("./td[1]")).getText();
				pt.setPosicion( Integer.parseInt(auxStr) );
				
				auxStr = posicionWE.findElement(By.xpath("./td[2]")).getText();
				pt.setEq( auxStr );
				
				auxStr = posicionWE.findElement(By.xpath("./td[3]")).getText();
				pt.setNumGames( Integer.parseInt(auxStr) );
				
				auxStr = posicionWE.findElement(By.xpath("./td[4]")).getText();
				pt.setNumW( Integer.parseInt(auxStr) );
				
				auxStr = posicionWE.findElement(By.xpath("./td[5]")).getText();
				pt.setNumD( Integer.parseInt(auxStr) );
				
				auxStr = posicionWE.findElement(By.xpath("./td[6]")).getText();
				pt.setNumL( Integer.parseInt(auxStr) );
				
				auxStr = posicionWE.findElement(By.xpath("./td[7]")).getText();
				pt.setGf( Integer.parseInt(auxStr) );
				
				auxStr = posicionWE.findElement(By.xpath("./td[8]")).getText();
				pt.setGc( Integer.parseInt(auxStr) );
				
				auxStr = posicionWE.findElement(By.xpath("./td[9]")).getText();
				pt.setDg( Integer.parseInt(auxStr) );
				
				auxStr = posicionWE.findElement(By.xpath("./td[10]")).getText();
				pt.setPtos( Integer.parseInt(auxStr) );
				
				
				t.getListaPosiciones().add(pt);
			}
			
			result.add(t);
		}
		
		this.driver.close();
		this.driver = null;

		return result;
	}

	private String getUrlFinal(String country) {
		String result = "";

		result = AnalisisCons.SS_URL_BASE.replaceFirst("Z", country);

		System.out.println(result);

		return result;
	}

}
