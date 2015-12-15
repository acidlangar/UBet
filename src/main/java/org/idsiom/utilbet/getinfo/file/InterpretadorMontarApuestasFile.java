package org.idsiom.utilbet.getinfo.file;

import java.util.ArrayList;


import org.idsiom.utilbet.bo.CodigosPartidoBO;
import org.idsiom.utilbet.bo.CuadroPrevioApuestasBO;
import org.idsiom.utilbet.bo.DefApuestaBO;
import org.idsiom.utilbet.bo.DefApuestaCombinadaBO;
import org.idsiom.utilbet.bo.DefApuestaSencillaBO;
import org.idsiom.utilbet.bo.GrupoApuestasBO;
import org.idsiom.utilbet.bo.MetodoDeApuestasBO;
import org.idsiom.utilbet.bo.ResultPartidoBO;
import org.idsiom.utilbet.bo.PartidoParaApostarBO;

public class InterpretadorMontarApuestasFile implements InterpretadorLinea<CuadroPrevioApuestasBO> {
	private boolean metodoCargado = false;
	private ArrayList<CodigosPartidoBO> listaPartidos = new ArrayList<CodigosPartidoBO>();
	private MetodoDeApuestasBO metodoEnConstruccion;
	private DefApuestaBO defApuestaEnConstruccion;
	private Integer cantPartidos = null;
	private CodigosPartidoBO partidoEnConstruccion;
	private Integer contadorAux = 0;
	
	private Integer cod1Aux = null;
	private Integer cod2Aux = null;
	private Integer cod3Aux = null;
	private Double multiplicador1 = null;
	private Double multiplicador2 = null;

	public void interpretarLinea(String strLine) {
		String[] arrayStr;
		String[] arrayStr2;
		String[] arrayStr3;
		
		String descMetodo;
		Integer idMetodo;
		
		System.out.println("---->>>>>   interpretarLinea(String strLine) = " + strLine);
		
		// Valida que no sea vacia
		if(strLine.trim().length() == 0) {
			return;
		}
		
		if(strLine.trim().startsWith("FIN M")) {
			System.out.println("COLOCADO TRUE");
			this.metodoCargado = true;
			
			return;
		}
		
		// En caso que ya fue cargado el metodo, se procede a realizar la lectura de los partidos dentro del cuadro
		if(this.metodoCargado) {
		
			System.out.println("BANDERA");
			
			if(strLine.trim().startsWith("LISTA -")) {
				
				System.out.println("BANDERA - 1");
				
				String strCantPartidos = strLine.split("-")[1];
				cantPartidos = Integer.parseInt(strCantPartidos);
				
				partidoEnConstruccion = new CodigosPartidoBO();
				
				contadorAux = 0;
				
				return;
			}
			
			if(strLine.trim().length() == 0) {
				System.out.println("BANDERA - 2");
				
				return;
			}
			
			if(partidoEnConstruccion != null && partidoEnConstruccion.getNombreLiga() == null && cantPartidos != null && contadorAux == 0) {
				System.out.println("BANDERA - 3");
				
				partidoEnConstruccion.setNombreLiga( strLine.trim() );
				
				contadorAux++;  // 1
				
				cod1Aux = null;
				cod2Aux = null;
				cod3Aux = null;
				multiplicador1 = null;
				multiplicador2 = null;
				
				return;
			}
			
			if(contadorAux == 1) {
				System.out.println("BANDERA - 4");
				
				String cod1Str = strLine.split("-")[0];
				cod1Aux = Integer.parseInt(cod1Str.trim());
				
				contadorAux++; 
				
				return;
			}
			
			if(contadorAux == 2) {
				System.out.println("BANDERA - 5");
				
				String mult1Str = strLine.split("-")[1];
				multiplicador1 = Double.parseDouble(mult1Str.trim());
				
				contadorAux++; 
				
				return;
			}
			
			if(contadorAux == 3) {
				System.out.println("BANDERA - 6");
				
				String cod2Str = strLine.split("-")[0];
				String mult2Str = strLine.split("-")[2];
				cod2Aux = Integer.parseInt(cod2Str.trim());
				multiplicador2 = Double.parseDouble(mult2Str.trim());
				
				contadorAux++; 
				
				return;
			}
			
			if(contadorAux == 4) {
				System.out.println("BANDERA - 7");
				
				String cod3Str = strLine.split("-")[0];
				cod3Aux = Integer.parseInt(cod3Str.trim());
				
				contadorAux = 0;
				
				if(multiplicador1 > multiplicador2) {
					partidoEnConstruccion.setCodigoFavorito(cod2Aux);
					partidoEnConstruccion.setCodigoPerdedor(cod1Aux);
				} else {
					partidoEnConstruccion.setCodigoFavorito(cod1Aux);
					partidoEnConstruccion.setCodigoPerdedor(cod2Aux);
				}
				partidoEnConstruccion.setCodigoEmpate(cod3Aux);
				
				System.out.println("Partido a agregar a lista = " + partidoEnConstruccion);
				
				this.listaPartidos.add(partidoEnConstruccion);
				partidoEnConstruccion = new CodigosPartidoBO();
				
				System.out.println("Lista por el momento :: " + this.listaPartidos);
				
				return;
			}
			
			
		}
		
		// Pica por el caracter "-"
		arrayStr = strLine.split("-");
		
		// "METODO 1 - TRIPLES EMPATES Y UN PERDEDOR"   []
		if(arrayStr[0].startsWith("METODO ")) {
			arrayStr2 = arrayStr[0].split(" ");
			
			idMetodo = Integer.parseInt(arrayStr2[1]);
			descMetodo = arrayStr[1].trim();
			
			this.metodoEnConstruccion = new MetodoDeApuestasBO();
			this.metodoEnConstruccion.setIdMetodo(idMetodo);
			this.metodoEnConstruccion.setDescripcion(descMetodo);
			
			this.defApuestaEnConstruccion = null;
			
			return;
		} else {
			// AP - 1.P,2.E,3.E,4.E
			if(arrayStr[0].startsWith("AP ")) {
				arrayStr2 = arrayStr[1].trim().split(",");
				
				// arrayStr2 = ["1.P","2.E","3.E","4.E"]
				if(arrayStr2.length > 1) { // Es una def de apuesta combinada
					
					DefApuestaCombinadaBO aux = new DefApuestaCombinadaBO();
					DefApuestaSencillaBO auxAS; 
					
					for(String da : arrayStr2) {
						//System.out.println("A leer " + da);
						
						arrayStr3 = da.split("\\.");  // arrayStr3 = ["1","P"]
						
						/*
						for(int i = 0; i < arrayStr3.length; i++) {
							System.out.println("arrayStr3[" + i + "] = " + arrayStr3[i]);
						}
						*/
						
						auxAS = new DefApuestaSencillaBO();
						auxAS.setIdPartido( Integer.parseInt( arrayStr3[0].trim() ));
						auxAS.setApuesta( ResultPartidoBO.getObjFromAbrev( arrayStr3[1].trim() ) );
						
						aux.addApuestaSencilla(auxAS);
					}
					
					this.defApuestaEnConstruccion = aux;
					
				} else if(arrayStr2.length == 1) { // Es una def de apuesta simple
					DefApuestaSencillaBO auxAS; 
					
					arrayStr3 = arrayStr2[0].split("\\.");  // arrayStr3 = ["1","P"]
					
					
					auxAS = new DefApuestaSencillaBO();
					auxAS.setIdPartido( Integer.parseInt( arrayStr3[0].trim() ));
					auxAS.setApuesta( ResultPartidoBO.getObjFromAbrev( arrayStr3[1].trim() ) );
						
					this.defApuestaEnConstruccion = auxAS;
					
				} else { 
					System.out.println("HAY PROBLEMAS!!!!, NO SE ENCONTRARON DEFINICIONES DE APUESTAS ESPERADAS ");
				}
				
				this.metodoEnConstruccion.addDefApuesta(defApuestaEnConstruccion);
				
				return;
			}
		}
		
	}

	public CuadroPrevioApuestasBO getResultadoInterpretacion() {
		CuadroPrevioApuestasBO result = new CuadroPrevioApuestasBO();
		
		result.setMetodo(metodoEnConstruccion);
		result.setListaPartidos(listaPartidos);
		
		System.out.println("getResultadoInterpretacion() - listaPartidos = " + result.getListaPartidos());
		
		return result;
	}

}
