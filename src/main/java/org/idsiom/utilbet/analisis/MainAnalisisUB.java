package org.idsiom.utilbet.analisis;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.idsiom.utilbet.analisis.bo.POPScheduleBO;
import org.idsiom.utilbet.analisis.bo.TRankingP;
import org.idsiom.utilbet.analisis.interlocutor.ISourceSchedule;
import org.idsiom.utilbet.analisis.interlocutor.ISourceTables;
import org.idsiom.utilbet.analisis.interlocutor.OddsPortalScheduleInterlocutorImpl;
import org.idsiom.utilbet.analisis.interlocutor.SoccerStatsInterlocutorImpl;

public class MainAnalisisUB {
	
	static Logger logger = Logger.getLogger(MainAnalisisUB.class);

	public static void main(String[] args) {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		
		
		ISourceSchedule sourSche = new OddsPortalScheduleInterlocutorImpl();
		List<POPScheduleBO> lista = null;
		
		// 1. Para cada l configurada
		for(int i = 0; i < AnalisisCons.OP_L.length; i++) {
			try {
				lista = sourSche.getNextSchedule(AnalisisCons.OP_C[i],AnalisisCons.OP_L[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
				
			}
			
			for(POPScheduleBO p : lista) {
				logger.info(p);
			}
			
		}
		
		
		
		// ---->> 1.1. Leer las dos tablas h / v de la liga,  soccerstats
		ISourceTables soucT = new SoccerStatsInterlocutorImpl();
		List<TRankingP> tablas = null;
		for(int i = 0; i < AnalisisCons.OP_C.length; i++) {
			try {
				tablas = soucT.obtenerTs(AnalisisCons.OP_C[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
				
			}
			
			for(TRankingP t : tablas) {
				logger.info(t);
			}
			
		}
		
		
		// ---->> 1.2. Leer los p de la jornada, con sus cuotas desde oddsportal. En funcion de fechas
		
		// ---->> 1.4. Realizar el analisis correspondiente para cada p con las tres entradas.
			

	}

}
