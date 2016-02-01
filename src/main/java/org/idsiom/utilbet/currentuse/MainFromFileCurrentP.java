package org.idsiom.utilbet.currentuse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.interlocutor.IOddsPortalCurrentUseInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.idsiom.utilbet.history.fromoddsportal.Cons;


public class MainFromFileCurrentP {

	static Logger logger = Logger.getLogger(MainFromFileCurrentP.class);

	public static String RUTA_ARCHIVO = "C:\\DEVTOOLS";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");

		IOddsPortalCurrentUseInterlocutor interlocutor = new OddsPortalInterCurrentUseImpl();

		Boolean seguir = true;

		while (seguir) {

			System.out.println("Procederemos a leer el archivo serializado!!!");

			File fichero = new File(RUTA_ARCHIVO + "/PartidosCurrent.srz");
			if (!fichero.exists()) {
				System.out.println("El archivo no existe... "
						+ fichero.getAbsolutePath());
				return;
			}

			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(fichero));

				// Se lee el primer objeto
				Object aux;
				aux = ois.readObject();

				if (aux instanceof ListPartidosSerializable) {

					ListPartidosSerializable lAux = (ListPartidosSerializable) aux;

					System.out.println("Cant Partidos Historia = "
							+ lAux.getPartidosHistory().size());

					ListPartidosSerializable lNews = interlocutor.getPs(true);

					List<CurrentPOddsPortal> listDefinitiva = new ArrayList<CurrentPOddsPortal>();

					listDefinitiva.addAll(lAux.getPartidosHistory());
					listDefinitiva.addAll(lNews.getListaPsHoyFuturo());
					
					List<CurrentPOddsPortal> listPNotificar = validarSeguimiento( lNews );
					if(listPNotificar.size() > 0) {
						notificar( listPNotificar );
					}
						
					ObjectOutputStream oos = new ObjectOutputStream(
							new FileOutputStream(fichero));
					oos.writeObject(lAux);
					System.out.println(" Archivo creado exitosamente :: "
							+ fichero.getAbsolutePath());

					// Se escribe directamente en el archivo excel
					MainCurrentUseFromOP.writeExcelFile(listDefinitiva);

					oos.close();
					oos = null;
					lAux = null;

				} else {
					System.out.println("No corresponde a instancia esperada");
				}

				ois.close();

			} catch (ClassNotFoundException e) {
				logger.error(e, e);
			} catch (FileNotFoundException e) {
				logger.error(e, e);
			} catch (IOException e) {
				logger.error(e, e);
			} catch (Exception e) {
				logger.error(e, e);
			}

			try {
				// Espera hasta la proxima ejecucion para busqueda del Archivo.
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private static void notificar(List<CurrentPOddsPortal> listPNotificar) {
		// TODO Auto-generated method stub
		
	}

	private static List<CurrentPOddsPortal> validarSeguimiento(ListPartidosSerializable lNews) {
		List<CurrentPOddsPortal> proxPartidos = lNews.getListaPsHoyFuturo();
		List<CurrentPOddsPortal> resultPartidos = new ArrayList<CurrentPOddsPortal>();
		
		Boolean existeProximo = false;
		
		for(CurrentPOddsPortal p : proxPartidos) {
			if(p.getType() == Cons.tipoToSeguir) {
				if(empiezaEnProxMins(p)) {
					existeProximo = true;
				}
				
				if(existeProximo) {
					resultPartidos.add(p);
				}
				
			}
			
		}
		
		return resultPartidos;
	}

	public static boolean empiezaEnProxMins(CurrentPOddsPortal p) {
		
		Long esteMomento = new Date().getTime();
		
		if( esteMomento < p.getFechaLong() ) {
			if((p.getFechaLong() - esteMomento) < 20*60*1000 ) {
				return true;
			}
		}
		
		
		return false;
	}

}
