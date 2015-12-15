package org.idsiom.utilbet.motor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.idsiom.utilbet.exception.InteraccionException;
import org.idsiom.utilbet.getinfo.file.InterpretadorLinea;
import org.idsiom.utilbet.getinfo.file.InterpretadorMontarApuestasFile;
import org.idsiom.utilbet.interlocutor.IInterlocutorCasaDeBets;
import org.idsiom.utilbet.interlocutor.InterlocutorBetCrisImpl;
import org.idsiom.utilbet.bo.CodigosPartidoBO;
import org.idsiom.utilbet.bo.CuadroPrevioApuestasBO;
import org.idsiom.utilbet.bo.DefApuestaBO;
import org.idsiom.utilbet.bo.DefApuestaCombinadaBO;
import org.idsiom.utilbet.bo.DefApuestaSencillaBO;
import org.idsiom.utilbet.bo.MetodoDeApuestasBO;
import org.idsiom.utilbet.bo.PartidoParaApostarBO;
import org.idsiom.utilbet.bo.ResultPartidoBO;
import org.idsiom.utilbet.bo.TypeApuestaBO;
//import org.idsiom.utilbet.bo.ResultadoRealPartidoBO;

public class MontarApuestasMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Leer el archivo, con 1. Caracteristca del tipo de Apuesta
		MontarApuestasMain main = new MontarApuestasMain();
		CuadroPrevioApuestasBO cuadroPrevio = main.getCuadroPrevioApuestas();
		DefApuestaCombinadaBO apuestaCombMontar;
		DefApuestaSencillaBO apuestaSencMontar;
		
		System.out.println("Metodo :: " + cuadroPrevio.getMetodo());
		System.out.println("Lista Partidos :: " + cuadroPrevio.getListaPartidos());
		
		boolean combinada = false;
		HashSet<String> listaDeLigas = new HashSet<String>();
		DefApuestaCombinadaBO apCombinada = null;
		DefApuestaSencillaBO apSencilla = null;
		
		// -- Identificar las ligas involucradas
		for(CodigosPartidoBO p : cuadroPrevio.getListaPartidos()) {
			if(p.getNombreLiga().split("-")[1].trim().length() > 0) {
				listaDeLigas.add( p.getNombreLiga().split("-")[0] + " - " + p.getNombreLiga().split("-")[1] );
			} else {
				listaDeLigas.add( p.getNombreLiga().split("-")[0] );
			}
			
		}
		
		// Para cada item del Metodo de apuestas
		for(DefApuestaBO defAp : cuadroPrevio.getMetodo().getDefinicionesApuestas()) {
		// -- Identificar el tipo de apuesta, combinada o sencilla
			if(defAp.getTipo().equals(TypeApuestaBO.COMBINADA)) {
				combinada = true;
				apCombinada = (DefApuestaCombinadaBO)defAp;
				
				apuestaCombMontar = new DefApuestaCombinadaBO();
				
				int i = 0;
				// -- Las apuestas en cuestion (Es decir, los códigos de los checks que se ejecutaran)
				for(DefApuestaSencillaBO aSenc : apCombinada.getApuestasSencillas()) {
					if(aSenc.getApuesta().equals(ResultPartidoBO.FAVORITO)) {
						apuestaCombMontar.addApuestaSencilla(new DefApuestaSencillaBO(  
								cuadroPrevio.getListaPartidos().get(i).getCodigoFavorito()
						));
					} else if(aSenc.getApuesta().equals(ResultPartidoBO.PERDEDOR)) {
						apuestaCombMontar.addApuestaSencilla(new DefApuestaSencillaBO(  
								cuadroPrevio.getListaPartidos().get(i).getCodigoPerdedor()
						));
					} else {
						apuestaCombMontar.addApuestaSencilla(new DefApuestaSencillaBO(  
								cuadroPrevio.getListaPartidos().get(i).getCodigoEmpate()
						));
					}
					
					i++;
				}
			} else {
				combinada = false;
				apSencilla = (DefApuestaSencillaBO)defAp;
				
				// -- Las apuestas en cuestion (Es decir, los códigos de los checks que se ejecutaran)
				throw new Exception("Aun no se ha implementado!!!");
			}
			
		
		// -- Para esta apuesta en cuestion, preparar el web para que se realice la confirmacion
			String respUser;
			
				prepararWeb(apuestaCombMontar, listaDeLigas);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Fue preparado el web de manera correcta S?, en caso afirmativo :: ");
				respUser = br.readLine();
				System.out.println("Respuesta recibida :: " + respUser);
				
				
			if(!respUser.equalsIgnoreCase("S")) {
				System.out.println("No continuo porque no funciono");
				break;
			}
			
			
		// -- Consultar si la confirmacion se realizo, en caso afirmativo ir al siguiente, en caso contrario volver a preparar el web
			
			
			
		}

	}
	
	public static void prepararWeb(DefApuestaCombinadaBO apCombinada, HashSet<String> ligas) throws InteraccionException {
		IInterlocutorCasaDeBets interlocutor = InterlocutorBetCrisImpl.getInstance();
		
		interlocutor.montarApuestaCombinada(apCombinada, ligas);
		
	}
	
	
	public CuadroPrevioApuestasBO getCuadroPrevioApuestas() {
		InterpretadorLinea interpretador = new InterpretadorMontarApuestasFile();
		CuadroPrevioApuestasBO resultado;
		
		try {
			resultado = (CuadroPrevioApuestasBO)this.lecturaGenerica("montarApuesta",
																			interpretador);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return resultado;
	}

	private Object lecturaGenerica(String nameFile,
			InterpretadorLinea interpretador) throws Exception {
		Integer lineaProcesada = 1;
		String rutaCompleta = new File("").getAbsolutePath().toString() + "/data/" + nameFile + ".txt";
		System.out.println("La ruta completa es : " + rutaCompleta);

		System.out.println(new File("").getAbsolutePath().toString());
		
		// Leer el archivo y convertirlo en una lista de ApuestaBO
		File file = new File(rutaCompleta);
		

		// Open the file
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Lectura Fallida");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		String[] arrayLine;

		// Read File Line By Line
		try {
			while ((strLine = br.readLine()) != null) {

				//System.out.println("*** line = " + strLine);

				interpretador.interpretarLinea(strLine);

				lineaProcesada++;

			}
		} catch (IOException e) {
			System.out
					.println("--------------------       FALLA PROCESANDO LINEA = "
							+ lineaProcesada);
			e.printStackTrace();

		} catch (Exception e) {
			System.out
					.println("--------------------       FALLA PROCESANDO LINEA = "
							+ lineaProcesada);
			e.printStackTrace();

		}

		// Close the input stream
		try {
			br.close();
		} catch (IOException e) {
			System.out.println("Problema al hacer el close!!!");
			e.printStackTrace();
		}

		return interpretador.getResultadoInterpretacion();
	}


}
