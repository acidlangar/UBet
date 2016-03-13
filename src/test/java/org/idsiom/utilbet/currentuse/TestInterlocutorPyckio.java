package org.idsiom.utilbet.currentuse;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.MINS_PROXIMIDAD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializablePyckio;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;
import org.junit.Test;


public class TestInterlocutorPyckio {

	public void testCompareFehas() {
		System.out.println("Inicio de prueba");
		try {
			
		
		CurrentPOddsPortal p = new CurrentPOddsPortal();
		p.setFecha("20160312 21:38");
		System.out.println(p.getFecha() + " RESP FINAL  " + String.valueOf(empiezaEnProxMins(p)).toUpperCase());
		
		
		p.setFecha("20160312 21:45");
		System.out.println(p.getFecha() + " RESP FINAL  " + String.valueOf(empiezaEnProxMins(p)).toUpperCase());
		
		p.setFecha("20160312 22:45");
		System.out.println(p.getFecha() + " RESP FINAL  " + String.valueOf(empiezaEnProxMins(p)).toUpperCase());
		
		p.setFecha("20160312 23:45");
		System.out.println(p.getFecha() + " RESP FINAL  " + String.valueOf(empiezaEnProxMins(p)).toUpperCase());
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	@Test
	public void testProxPartidos() throws Exception {
		Boolean useSerializables = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");		
		Date now = new Date();
		Long esteMomento = (now).getTime();
		
		String strTope = sdf.format((esteMomento + (MINS_PROXIMIDAD*60*1000)));
		
		System.out.println("strTope " + strTope );
		List<PartidoPyckioBO> list;
		List<CurrentPOddsPortal> listOP;
		
		if(!useSerializables) {
			/*PickioInterlocutorImpl inter =  PickioInterlocutorImpl.getInstance();
			list = inter.getPartidosPorHora(0L);
			*/
			OddsPortalInterCurrentUseImpl interOP = OddsPortalInterCurrentUseImpl.getInstance();
			ListPartidosSerializable listSer = interOP.getPs(true);
			
			System.out.println("ANTES DE VALIDAR SEGUIMIENTO");
			
			for(CurrentPOddsPortal aux : listSer.getListaPsHoyFuturo()) {
				System.out.println(aux);
			}
			
			listOP = validarSeguimiento(listSer);
			
			listSer.setListaPsHoyFuturo(listOP);
			
			
			ListPartidosSerializable listSer_2 = new ListPartidosSerializable();
			listSer_2.setListaPsHoyFuturo(listOP);
			guardarSerializadoOP(listSer);
			
			System.out.println("qq[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[-------------   " + listSer_2.getListaPsHoyFuturo().size());
			
			ListPartidosSerializablePyckio listPIO = new ListPartidosSerializablePyckio();
			//listPIO.setPartidos(list);
			guardarSerializadoP(listPIO);
			
		} else {
			listOP = leerSerializadoOP().getListaPsHoyFuturo();
			list = leerSerializadoP().getPartidos();
			
		}
		
		/*
	 	System.out.println("LISTA PYCKIO " + list.size());
		for(PartidoPyckioBO p : list) {
			System.out.println("__________>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " );
			
			System.out.println( p.toString() );
			
			System.out.println(" " );
			
		}
		*/
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
				if(empiezaEnProxMins(p)) {
					existeProximo = true;
				}
				
				if(existeProximo) {
					resultPartidos.add(p);
				}
		}
		
		return resultPartidos;
	}

	public static boolean empiezaEnProxMins(CurrentPOddsPortal p) {
		
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar momentoHasta = new GregorianCalendar();

		momentoHasta.add(GregorianCalendar.MINUTE, MINS_PROXIMIDAD);
		
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		System.out.println("  --------------->>>>>>>>>>> ");
		System.out.println("partidoFecha     " + sdf.format( p.getFechaGC().getTime() ));
        System.out.println("Momento Desde :: " + sdf.format(now.getTime())  + " ;   Momento Hasta :: " + sdf.format(momentoHasta.getTime()) );
        System.out.println("C1 :: " + now.before(p.getFechaGC()) );
        System.out.println("C2 :: " + p.getFechaGC().before(momentoHasta) );
        System.out.println("  --->>>>>>>>>>> ");
		*/
		
		if( now.before(p.getFechaGC()) && p.getFechaGC().before(momentoHasta)) {
				return true;
		} 
		
		return false;
	}
	
	private ListPartidosSerializable leerSerializadoOP() {
		ListPartidosSerializable result = null;
		
		File fichero = new File(MainCurrentUseFromOP.RUTA_ARCHIVO + "/Test_Partidos_OP.srz");
		if (!fichero.exists()) {
			System.out.println("El archivo no existe... "
					+ fichero.getAbsolutePath());
			return null;
		}

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));

			// Se lee el primer objeto
			Object aux;
			aux = ois.readObject();

			if (aux instanceof ListPartidosSerializable) {
				result = (ListPartidosSerializable) aux;
			} 
			
			ois.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
	
	
	private ListPartidosSerializablePyckio leerSerializadoP() {
		ListPartidosSerializablePyckio result = null;
		
		File fichero = new File(MainCurrentUseFromOP.RUTA_ARCHIVO + "/Test_Partidos_P.srz");
		if (!fichero.exists()) {
			System.out.println("El archivo no existe... "
					+ fichero.getAbsolutePath());
			return null;
		}

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));

			// Se lee el primer objeto
			Object aux;
			aux = ois.readObject();

			if (aux instanceof ListPartidosSerializablePyckio) {
				result = (ListPartidosSerializablePyckio) aux;
			} 
			
			ois.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return result;

		
		
	}
	
	private void guardarSerializadoOP(ListPartidosSerializable listSer) {
		File dir = new File(MainCurrentUseFromOP.RUTA_ARCHIVO);
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		File fichero = new File(dir.getAbsolutePath() + "/Test_Partidos_OP.srz");
		if(fichero.exists()) {
			fichero.delete();
		}
		
		try { 
			if(!fichero.exists()) {
				fichero.createNewFile();
				System.out.println("Archivo creado vacio exitosamente :: " + fichero.getAbsolutePath());
			} 
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero));
			oos.writeObject(listSer);
			System.out.println(" Archivo creado exitosamente :: " + fichero.getAbsolutePath());
			
			oos.close();
			oos = null;
		} catch(Exception ex) {
			ex.printStackTrace();
		} 
	}
	
	
	private void guardarSerializadoP(ListPartidosSerializablePyckio listPIO) {
		File dir = new File(MainCurrentUseFromOP.RUTA_ARCHIVO);
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		File fichero = new File(dir.getAbsolutePath() + "/Test_Partidos_P.srz");
		if(fichero.exists()) {
			fichero.delete();
		}
		
		try { 
			if(!fichero.exists()) {
				fichero.createNewFile();
				System.out.println("Archivo creado vacio exitosamente :: " + fichero.getAbsolutePath());
			} 
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero));
			oos.writeObject(listPIO);
			System.out.println(" Archivo creado exitosamente :: " + fichero.getAbsolutePath());
			
			oos.close();
			oos = null;
		} catch(Exception ex) {
			ex.printStackTrace();
		} 
	}
	
}
