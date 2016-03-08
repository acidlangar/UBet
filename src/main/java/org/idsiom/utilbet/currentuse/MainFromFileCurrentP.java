package org.idsiom.utilbet.currentuse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.interlocutor.IOddsPortalCurrentUseInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.IPyckioInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.idsiom.utilbet.history.fromoddsportal.Cons;
import org.idsiom.utilbet.mail.pruebas.MainSendMail;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.*;

public class MainFromFileCurrentP {

	static Logger logger = Logger.getLogger(MainFromFileCurrentP.class);

	public static String RUTA_ARCHIVO = "C:\\DEVTOOLS";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");

	    IOddsPortalCurrentUseInterlocutor interlocutor = OddsPortalInterCurrentUseImpl.getInstance();
	    IPyckioInterlocutor interlocutorPyckio = PickioInterlocutorImpl.getInstance();
	    
	    
		//IOddsPortalCurrentUseInterlocutor interlocutor = new InterlocutorPruebaProximosJuegosImpl();
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
						System.out.println("Entre a la parte de notificar");
						notificar( listPNotificar );
					} else {
						System.out.println("La validacion de notificacion no se ejecuto");
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
		MainSendMail senderMail; 
		try {
			senderMail = new MainSendMail();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		
		String mensaje;
		
		StringBuffer sb = new StringBuffer(512);
		
		for(CurrentPOddsPortal p : listPNotificar) {
			sb.append(p.getFecha());
			sb.append("   ");
			sb.append(p.getCountry());
			sb.append("   ");
			sb.append(p.getLeague());
			sb.append("   ");
			sb.append(p.getEquipos());
			sb.append("\n\n");
		}
		
		
		mensaje = sb.toString();
		sb = null;
		
		
		senderMail.send("ESTE SI, NEXT GAMES " + listPNotificar.size() + " - " + RESULT_BUSCADO, mensaje);
		
	}

	private static List<CurrentPOddsPortal> validarSeguimiento(ListPartidosSerializable lNews) {
		List<CurrentPOddsPortal> proxPartidos = lNews.getListaPsHoyFuturo();
		List<CurrentPOddsPortal> resultPartidos = new ArrayList<CurrentPOddsPortal>();
		
		Boolean existeProximo = false;
		
		for(CurrentPOddsPortal p : proxPartidos) {
			System.out.println(p);
			System.out.println(p.getType());
			
			if(p.getType() == TIPO_TO_SEGUIR) {
				
				System.out.println("empiezaEnProxMins(p) = " + empiezaEnProxMins(p));
				
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
		
		Date now = new Date();
		Long esteMomento = (now).getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		
		String strNow = sdf.format(now.getTime());
		String strFPartido = p.getFecha();
		
		System.out.println("esteMomento " + strNow        + "   " + esteMomento );
		System.out.println("partido     " + strFPartido   + "   " + p.getFechaLong());
        System.out.println(" esteMomento < p.getFechaLong() " + (esteMomento < p.getFechaLong()));
		
		if( esteMomento < p.getFechaLong() ) {
			
			//System.out.println("resta " + (p.getFechaLong() - esteMomento));
			System.out.println(MINS_PROXIMIDAD*60*1000);
			
			if((p.getFechaLong() - esteMomento) < MINS_PROXIMIDAD*60*1000 ) {
				return true;
			}
		} 
		
		
		return false;
	}

}
