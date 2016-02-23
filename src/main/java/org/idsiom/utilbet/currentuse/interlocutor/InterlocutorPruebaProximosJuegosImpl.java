package org.idsiom.utilbet.currentuse.interlocutor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;

public class InterlocutorPruebaProximosJuegosImpl implements IOddsPortalCurrentUseInterlocutor {

	public ListPartidosSerializable getPs(Boolean reutilizarHistoria)
			throws Exception {
		ListPartidosSerializable lista = new ListPartidosSerializable();
		List<CurrentPOddsPortal> listaPsHoyFuturo = new ArrayList<CurrentPOddsPortal>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		
		CurrentPOddsPortal p1;
		GregorianCalendar gc;
		Long timeGame;
		for(int i = 1; i < 10; i++) {
			p1 = new CurrentPOddsPortal();
			p1.setC1(2.77);
			p1.setC2(2.66);
			p1.setcX(3.22);
			p1.setCountry("Venezuela");
			p1.setEquipos("local " + i + " vs visitante " + i);
			
			gc = new GregorianCalendar();
			timeGame = gc.getTimeInMillis() + (i * 5 * 60 * 1000);
			
			gc.setTimeInMillis(timeGame);
			
			p1.setFecha(sdf.format(gc.getTime()));
			
			p1.setLeague("Liga Profesional");
			p1.setrStr("");
			
			listaPsHoyFuturo.add(p1);
			
		}
		
		
		lista.setListaPsHoyFuturo(listaPsHoyFuturo);
		
		return lista;
	}

}
