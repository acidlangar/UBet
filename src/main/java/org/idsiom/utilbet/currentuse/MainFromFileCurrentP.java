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
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.PyckBO;
import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.EstadoPyck;
import org.idsiom.utilbet.currentuse.interlocutor.IOddsPortalCurrentUseInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.IPyckioInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;
import org.idsiom.utilbet.currentuse.xls.CurrentDesviacionXLS;
import org.idsiom.utilbet.currentuse.xls.SeguimientoPyckXLS;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.idsiom.utilbet.mail.pruebas.MainSendMail;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.*;

public class MainFromFileCurrentP {

	static Logger logger = Logger.getLogger(MainFromFileCurrentP.class);

	public static String RUTA_ARCHIVO = "C:\\DEVTOOLS";
	
	private static IPyckioInterlocutor interlocutorPyckio;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");

	    IOddsPortalCurrentUseInterlocutor interlocutor = OddsPortalInterCurrentUseImpl.getInstance();
	    
	    
	    ISeguimientoPyckPersistencia segPyck = new SeguimientoPyckXLS();
	    
	    
		//IOddsPortalCurrentUseInterlocutor interlocutor = new InterlocutorPruebaProximosJuegosImpl();
		Boolean sinGanancias = true;

		Boolean newPyckMontado;
		while (sinGanancias) {
			newPyckMontado = false;	
			
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
					
					// Buscar los pycks que se encuentran pendientes de resolver.
					List<PyckBO> listPycksPendientes = segPyck.getPyckPorDefinir();
					List<PyckBO> pycksConResultados = new ArrayList<PyckBO>();
					if(listPycksPendientes != null) {
						
						logger.info("Hay Pycks pendientes. listPycksPendientes.size() = " + listPycksPendientes.size());
						
						for(PyckBO pyck : listPycksPendientes) {
							
							logger.info("Se buscara resultado de :: pyck.toString() = " + pyck.toString());
							
							pyck = determinarResultado(pyck, lNews);
							
							
							
							if(pyck != null) {
								logger.info("Pyck despues de determinar el Resultado = " + pyck.toString());
								pycksConResultados.add(pyck);
							} else {
								logger.info("Pyck despues de determinar el Resultado = " + null);
							}
							
						}
					} else {
						logger.info("No hay Pycks pendientes");
					}
					
					if(pycksConResultados.size() > 0) {
						segPyck.guardarResultadosPycks(pycksConResultados);
					}
					
					listPycksPendientes = segPyck.getPyckPorDefinir();
					double rendAcumulado = segPyck.getRendimientoAcumulado();
					double odds;
					
					if(rendAcumulado <= 0) {
						sinGanancias = true;
						System.out.println("Como no hay ganancias hay que seguir");
					} else {
						sinGanancias = false;
						System.out.println("Como ya hay ganancias, hay que parar");
					}
					
					
					// Si no hay Pycks por Definir
					if((listPycksPendientes == null || listPycksPendientes.size() == 0)
							&& rendAcumulado <= 0) {
						
						System.out.println("No hay pycks pendientes... se revisara si hay prox juegos en " + TIPO_TO_SEGUIR);
						
						List<CurrentPOddsPortal> listPNotificar = validarSeguimiento( lNews );
						
						System.out.println("La cantidad de juegos es " + listPNotificar.size() + "   dentro de los proximos " + MINS_PROXIMIDAD);
						
						Thread.sleep(5000);
						
						List<PartidoPyckioBO> list = null;
						interlocutorPyckio = null;
						if(listPNotificar.size() > 0) {
							interlocutorPyckio = PickioInterlocutorImpl.getInstance();
							list = interlocutorPyckio.getPartidosPorHora(0L);
						}
						
						for (CurrentPOddsPortal pop : listPNotificar) {
							PartidoPyckioBO pEquivalente = interlocutorPyckio.findTraduction( list, pop );
							
							if(pEquivalente != null) {
								
								System.out.println("Fue encontrado equivalente para " + pop.toString() + " el mismo es " + pEquivalente.toString());
								logger.info("Fue encontrado equivalente para " + pop.toString() + " el mismo es " + pEquivalente.toString());
								
								ResultadoPartidoBO resultBuscado;
								
								if(RESULT_BUSCADO.equals("BUSCANDO_VISITANTE")) {
									resultBuscado = ResultadoPartidoBO.VISITANTE;
									odds = pop.getC2();
								} else if(RESULT_BUSCADO.equals("BUSCANDO_LOCAL")) {
									resultBuscado = ResultadoPartidoBO.LOCAL;
									odds = pop.getC1();
								} else {
									resultBuscado = ResultadoPartidoBO.EMPATE;
									odds = pop.getcX();
								}
								
								
								int stake = determinarStake(odds, rendAcumulado);
								
								newPyckMontado = false;
								try {
									newPyckMontado = interlocutorPyckio.montarPick(pEquivalente, resultBuscado, stake);
								} catch(Exception ex) {
									ex.printStackTrace();
									logger.error(ex,ex);
								}
								
								if(newPyckMontado) {
									PyckBO pyck = new PyckBO();
									pyck.setEstado(EstadoPyck.POR_DEFINIR);
									pyck.setPyck(resultBuscado);
									pyck.setPartido(pop);
									pyck.setStake(stake);
									
									segPyck.guardarApuesta(pop, pEquivalente, pyck);
									
									break;
								}
								
								
							} else {
								System.out.println("No fue encontrado equivalente para " + pop.toString());
								logger.info("No fue encontrado equivalente para " + pop.toString());
							}
							
						} // for de cada partido proximo de oddsportal
						
						if(interlocutorPyckio != null) {
							interlocutorPyckio.close();
						}
							
						
					}
					
					/*
					List<CurrentPOddsPortal> listPNotificar = validarSeguimiento( lNews );
					if(listPNotificar.size() > 0) {
						prepararPycks(listPNotificar);
						
						System.out.println("Entre a la parte de notificar");
						notificar( listPNotificar );
					} else {
						System.out.println("La validacion de notificacion no se ejecuto");
					}
					*/
						
					ObjectOutputStream oos = new ObjectOutputStream(
							new FileOutputStream(fichero));
					oos.writeObject(lAux);
					System.out.println(" Archivo creado exitosamente :: "
							+ fichero.getAbsolutePath());

					// Se escribe directamente en el archivo excel
					ICurrentDesviacionPersistencia currentDesvPersistencia = new CurrentDesviacionXLS(); 
					currentDesvPersistencia.writeExcelFile(listDefinitiva);
					
					oos.close();
					oos = null;
					lAux = null;
					
					
					if(newPyckMontado) {
						try {
							// Esperar 110 mins relacionados a los 90 min del juego, mas los 15min del descanso, mas 5 del descuento 
							Thread.sleep(110 * 60 * 1000);
						} catch (InterruptedException e) {
							logger.error(e, e);
						}
					}
					
					

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
				System.out.println("A la espera de dos mins para la prox ejecucion....  ;)");
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}

		} // while sin ganancias

	}
	
	
	private static int determinarStake(double odds, double rendAcumulado) {
		int stakeNecesitado;
		double aux = ( Math.abs(rendAcumulado) + 1 ) / (odds - 1);
		
		int auxInt = (int)aux;
		
		if(aux > auxInt) {
			stakeNecesitado = auxInt + 1;
		} else {
			stakeNecesitado = auxInt;
		}
		
		stakeNecesitado = Math.max(stakeNecesitado, 1);
		stakeNecesitado = Math.min(stakeNecesitado, 10);
		
		return stakeNecesitado;
	}


	/*
	 * Determina si el pyck fue acertado, y se evaluan los juegos en la lista de lNews.
	 * Si el juego no fue encontrado en la lista, se devuelve null
	 * 
	 * Si el juego fue suspendido o no fue terminado, simplemente indica como suspendido
	 * */
	private static PyckBO determinarResultado(PyckBO pyck, ListPartidosSerializable lNews) {
		for(CurrentPOddsPortal pOP : lNews.getListaPsHoyFuturo()) {
			
			logger.info( "pyck.getPartido.getEquipos = " + pyck.getPartido().getEquipos() + "   pOP.getEquipos = " + pOP.getEquipos()
			      + "    equals = " + pyck.getPartido().equals(pOP) );
			
			if(pyck.getPartido().equals(pOP)) {
				
				ResultadoPartidoBO resultadoPartido = null;
				// Saber Quien Gano en OddsPortal, L, V, E, Suspendido
				
				if(pOP.getResultFinal() == 'O') {
					pyck.setEstado(EstadoPyck.SUSPENDIDO);
					return pyck;
				}
				
				
				
				if(pOP.getResultFinal() == '1') {
					resultadoPartido = ResultadoPartidoBO.LOCAL;
				}
				
				if(pOP.getResultFinal() == '2') {
					resultadoPartido = ResultadoPartidoBO.VISITANTE;
				}
				
				if(pOP.getResultFinal() == 'X') {
					resultadoPartido = ResultadoPartidoBO.EMPATE;
				}
				
				pyck.setEstado(EstadoPyck.FINALIZADO);
				if(pyck.getPyck().equals(resultadoPartido)) {
					pyck.setAcierto(true);
				} else {
					pyck.setAcierto(false);
				}
				
				return pyck;
			}
		}
		
		return null;
		
	}
	

	private static void prepararPycks(List<CurrentPOddsPortal> listPNotificar) {
		// TODO Auto-generated method stub
		//interlocutorPyckio
		
		// Si el partido esta dentro de los proximos 20min, procedemos
		for(CurrentPOddsPortal pOP : listPNotificar) { 
			if(empiezaEnProxMins(pOP)) {
				try {
					interlocutorPyckio.montarPick(pOP,ResultadoPartidoBO.LOCAL,3);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
