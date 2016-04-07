package org.idsiom.utilbet.currentuse;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;
import org.idsiom.utilbet.currentuse.interlocutor.IOddsPortalCurrentUseInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.IPyckioInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;

public class MainMontarPycks {
	
	static Logger logger = Logger.getLogger(MainMontarPycks.class);

	public static void main(String[] args) {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		
		IOddsPortalCurrentUseInterlocutor interlocutor = null;
		ListPartidosSerializable lNews = null;
		
		try {
			interlocutor = OddsPortalInterCurrentUseImpl.getInstance();
			lNews = interlocutor.getPs(true);
			
			try {
				Thread.sleep(5000);
			} catch(Exception ex) {
				
			}
			
		} catch (IOException e) {
			logger.error(e,e);
			return;
		}	catch (Exception e) {
			logger.error(e,e);
			return;
		}
		
		Set<String> ligas = new HashSet<String>();
		List<CurrentPOddsPortal> proxPartidos = lNews.getListaPsHoyFuturo();
		List<CurrentPOddsPortal> partidosFuture = new ArrayList<CurrentPOddsPortal>();
		
		for(CurrentPOddsPortal p : proxPartidos) {
			logger.info("empiezaEnFuture(p) = " + empiezaEnFuture(p) + " :: p = " + p.toString());
				
			if(empiezaEnFuture(p)) {
				partidosFuture.add(p);
				ligas.add(p.getLeague());
				
			}
		}
		
		Console in = System.console();
		String strConsole;
		int incluirLiga = 0; 
		Set<String> ligasSeleccionadas = new HashSet<String>();
		for(String liga : ligas) {
			System.out.println("Liga = " + liga + " . 1 para Incluir. ");
			//Scanner in2 = new Scanner(System.in);
			/*
			if(in2.hasNext()) {
			    incluirLiga = in2.nextInt();
			} */
			strConsole = in.readLine("1 para incluir");
			
			//in2.close();
			
			incluirLiga = Integer.parseInt(strConsole);
			
			if(incluirLiga == 1) {
				ligasSeleccionadas.add(liga);
			}
			
		}
		
		int stake;
		String resultado;
		boolean pedir = true;
		IPyckioInterlocutor interlocutorPyckio;
		
		try {
			interlocutorPyckio = PickioInterlocutorImpl.getInstance();
		} catch (IOException e) {
			logger.error(e,e);
			return;
		}
		
		List<PartidoPyckioBO> list = interlocutorPyckio.getPartidosPorHora(0L);
		
		for(CurrentPOddsPortal p : partidosFuture) {
			if( ligasSeleccionadas.contains(p.getLeague()) ) {
				System.out.println("  ");
				System.out.println("------------------------------->>>>");
				System.out.println(p.toString());
				
				
					
				PartidoPyckioBO pEquivalente = interlocutorPyckio.findTraduction( list, p );
				
				if(pEquivalente != null) {
					
					System.out.println("Fue encontrado equivalente para " + p.toString() + " el mismo es " + pEquivalente.toString());
					logger.info("Fue encontrado equivalente para " + p.toString() + " el mismo es " + pEquivalente.toString());
					
					ResultadoPartidoBO resultBuscado = null;
					
					while(pedir) {
						System.out.println("L - Local; V - Visitante; E - Empate");
						Scanner in2 = new Scanner(System.in);
						
						resultado = null;
						if(in2.hasNext()) {
							resultado = in2.next();
						} 
						in2.close();
						
						resultado = resultado.substring(0, 0);
						
						pedir = false;
						if(resultado.equals("L")) {
							resultBuscado = ResultadoPartidoBO.LOCAL;
						} else if(resultado.equals("V")) {
							resultBuscado = ResultadoPartidoBO.VISITANTE;
						} else if(resultado.equals("E")) {
							resultBuscado = ResultadoPartidoBO.EMPATE;
						} else {
							pedir = true;
						}
						
					}
					

					System.out.println("stake, 1 - 10");
					Scanner in2 = new Scanner(System.in);
					
					stake = 0;
					if(in2.hasNext()) {
						stake = in2.nextInt();
					}
					in2.close();
					
					stake = Math.min(stake, 10);
					stake = Math.max(stake, 1);
					
					boolean newPyckMontado = false;
					try {
						newPyckMontado = interlocutorPyckio.montarPick(pEquivalente, resultBuscado, stake);
					} catch(Exception ex) {
						ex.printStackTrace();
						logger.error(ex,ex);
					}
					
					if(newPyckMontado) {
						System.out.println("Montado Exitosamente");
					}
					
					
				} else {
					System.out.println("No fue encontrado equivalente para " + p.toString());
					logger.info("No fue encontrado equivalente para " + p.toString());
				}
					
				
				
			} // fin if liga
		} // fin for partidos
		
		
		
	}

	private static boolean empiezaEnFuture(CurrentPOddsPortal p) {
		Date now = new Date();
		Long esteMomento = (now).getTime();

		if( esteMomento < p.getFechaLong() ) {
			return true;
		} 
		
		return false;
	}
	
	

}
