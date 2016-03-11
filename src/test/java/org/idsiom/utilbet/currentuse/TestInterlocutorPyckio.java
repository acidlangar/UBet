package org.idsiom.utilbet.currentuse;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.MINS_PROXIMIDAD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;
import org.junit.Test;


public class TestInterlocutorPyckio {

	@Test
	public void testProxPartidos() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");		
		Date now = new Date();
		Long esteMomento = (now).getTime();
		
		String strTope = sdf.format((esteMomento + (MINS_PROXIMIDAD*60*1000)));
		
		System.out.println("strTope " + strTope );
		
		
		PickioInterlocutorImpl inter =  PickioInterlocutorImpl.getInstance();
		
		
		List<PartidoPyckioBO> list = inter.getPartidosPorHora(0L);
		
		
		
		
		OddsPortalInterCurrentUseImpl interOP = OddsPortalInterCurrentUseImpl.getInstance();
		
		ListPartidosSerializable listSer = interOP.getPs(true);
		
		List<CurrentPOddsPortal> listOP = validarSeguimiento(listSer);
		
	 	System.out.println("LISTA PYCKIO " + list.size());
		for(PartidoPyckioBO p : list) {
			System.out.println("__________>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " );
			
			System.out.println( p.toString() );
			
			System.out.println(" " );
			
		}
		
		System.out.println("LISTA ODDSPORTAL TOT " + listSer.getListaPsHoyFuturo().size());
		System.out.println("LISTA ODDSPORTAL " + listOP.size());
		for(CurrentPOddsPortal pop : listOP) {
			System.out.println( pop.toString()  );
		}
		
		
		
		
	}
	
	
	private static List<CurrentPOddsPortal> validarSeguimiento(ListPartidosSerializable lNews) {
		List<CurrentPOddsPortal> proxPartidos = lNews.getListaPsHoyFuturo();
		List<CurrentPOddsPortal> resultPartidos = new ArrayList<CurrentPOddsPortal>();
		
		Boolean existeProximo = false;
		
		for(CurrentPOddsPortal p : proxPartidos) {
			//System.out.println(p);
			//System.out.println(p.getType());
			
			//if(p.getType() == TIPO_TO_SEGUIR) {
				
				//System.out.println("empiezaEnProxMins(p) = " + empiezaEnProxMins(p));
				
				if(empiezaEnProxMins(p)) {
					existeProximo = true;
				}
				
				if(existeProximo) {
					resultPartidos.add(p);
				}
				
			//}
			
		}
		
		return resultPartidos;
	}

	public static boolean empiezaEnProxMins(CurrentPOddsPortal p) {
		
		Date now = new Date();
		Long esteMomento = (now).getTime();

		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		
		
		String strNow = sdf.format(now.getTime());
		String strFPartido = p.getFecha();
		
		System.out.println("   ");
		System.out.println("   ");
		System.out.println("esteMomento " + strNow        + "   " + esteMomento );
		System.out.println("partido     " + strFPartido   + "   " + p.getFechaLong());
        System.out.println(" esteMomento < p.getFechaLong() " + (esteMomento < p.getFechaLong()));
        System.out.println("esteMomento + (MINS_PROXIMIDAD*60*1000) = " + (esteMomento + (MINS_PROXIMIDAD*60*1000)) );
        System.out.println( esteMomento < p.getFechaLong()); 
        System.out.println(p.getFechaLong()  < esteMomento + (MINS_PROXIMIDAD*60*1000) );
        
		
		if( esteMomento < p.getFechaLong() && (p.getFechaLong()  < esteMomento + (MINS_PROXIMIDAD*60*1000) )) {
			
				return true;
			
		} 
		
		
		return false;
	}
	
}
