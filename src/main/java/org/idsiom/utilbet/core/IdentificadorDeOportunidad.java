package org.idsiom.utilbet.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import org.idsiom.utilbet.bo.PartidoParaApostarBO;
import org.idsiom.utilbet.constantes.ConstantesDeConfiguracion;

public class IdentificadorDeOportunidad implements IIdentificadorDeOportunidad {

	class DiferencialComparator implements java.util.Comparator<PartidoParaApostarBO> {

		public int compare(PartidoParaApostarBO arg0, PartidoParaApostarBO arg1) {
			
			Double maxDif0, minDif0, maxDif1, minDif1; 
			
			//Buscar el max y min Diferencial del 0
			ArrayList<Double> auxArra0 = new ArrayList<Double>();
			auxArra0.add( arg0.getMultiplicadorGanador1() );
			auxArra0.add( arg0.getMultiplicadorEmpate() );
			auxArra0.add( arg0.getMultiplicadorGanador2() );
			
			Collections.sort(auxArra0);                             // Sort
			maxDif0 = auxArra0.get(auxArra0.size()-1);
			minDif0 = auxArra0.get(0);
			
			  
			//Buscar el max y min Diferencial del 1
			ArrayList<Double> auxArra1 = new ArrayList<Double>();
			auxArra1.add( arg1.getMultiplicadorGanador1() );
			auxArra1.add( arg1.getMultiplicadorEmpate() );
			auxArra1.add( arg1.getMultiplicadorGanador2() );
			
			Collections.sort(auxArra1);                             // Sort
			maxDif1 = auxArra1.get(auxArra1.size()-1);
			minDif1 = auxArra1.get(0);
			
			
			Double dif0 = maxDif0 - minDif0;
			Double dif1 = maxDif1 - minDif1;
			
			
			if(minDif0.equals(minDif1)) {  // Si son iguales
				
				// Si los diferenciales de los favoritos son iguales, escoger el que tenga mayor diferencias
				if(dif0.equals(dif1)) {
					return 0;
				} else if(dif0 < dif1) {  			// Si 0 es mas riesgoso que uno  
					return -1;
				} else {  // Si 1 es riesgoso que cero
					return 1;
				}		
				
				
			} else if(minDif0 < minDif1) {  			
				return -1;
			} else {  
				return 1;
			}			

			
		}
		
	}
	
	class JuegoArriesgadoComparator implements Comparator<PartidoParaApostarBO> {

		public int compare(PartidoParaApostarBO arg0, PartidoParaApostarBO arg1) {
			Double maxDif0, minDif0, maxDif1, minDif1; 
			
			//Buscar el max y min Diferencial del 0
			ArrayList<Double> auxArra0 = new ArrayList<Double>();
			auxArra0.add( arg0.getMultiplicadorGanador1() );
			auxArra0.add( arg0.getMultiplicadorEmpate() );
			auxArra0.add( arg0.getMultiplicadorGanador2() );
			
			Collections.sort(auxArra0);                             // Sort
			maxDif0 = auxArra0.get(auxArra0.size()-1);
			minDif0 = auxArra0.get(0);
			
			  
			//Buscar el max y min Diferencial del 1
			ArrayList<Double> auxArra1 = new ArrayList<Double>();
			auxArra1.add( arg1.getMultiplicadorGanador1() );
			auxArra1.add( arg1.getMultiplicadorEmpate() );
			auxArra1.add( arg1.getMultiplicadorGanador2() );
			
			Collections.sort(auxArra1);                             // Sort
			maxDif1 = auxArra1.get(auxArra1.size()-1);
			minDif1 = auxArra1.get(0);
			
			
			Double dif0 = maxDif0 - minDif0;
			Double dif1 = maxDif1 - minDif1;
			
			
			if(dif0.equals(dif1)) {  // Si son iguales
				return 0;
			} else if(dif0 < dif1) {  			// Si 0 es mas riesgoso que uno  
				return -1;
			} else {  // Si 1 es riesgoso que cero
				return 1;
			}
			

			
		}
		
	}
	
	public List<PartidoParaApostarBO> ordenarApuestasPorDiferencia(
			List<PartidoParaApostarBO> apuestasDisponibles) {
		
		DiferencialComparator comparator = new DiferencialComparator();
		Collections.sort(apuestasDisponibles, comparator);
		
		return apuestasDisponibles;
	}

	public List<PartidoParaApostarBO> primerasApuestasPorDiferencia(
			List<PartidoParaApostarBO> apuestasDisponibles, GregorianCalendar fecha,
			Integer cantidadDeseada) {
		List<PartidoParaApostarBO> listaResult = new ArrayList<PartidoParaApostarBO>();
		Double diferencialAnterior = null;
		
		DiferencialComparator comparator = new DiferencialComparator();
		Collections.sort(apuestasDisponibles, comparator);
		
		String fechaDeseada = ConstantesDeConfiguracion.dateFormat_diaMesAno.format(fecha.getTime());
		String fechaApuesta;
		
		for(PartidoParaApostarBO apuesta : apuestasDisponibles) {
			fechaApuesta = ConstantesDeConfiguracion.dateFormat_diaMesAno.format(apuesta.getDiaHoraJuego().getTime());
			if( fechaDeseada.equals(fechaApuesta) ) {
				if(listaResult.size() < cantidadDeseada) {
					listaResult.add(apuesta);
					diferencialAnterior = apuesta.getDiferencialMultiplicador();
					
					System.out.println("Agregado porque " + listaResult.size() + " menor a " + cantidadDeseada);
					
				} else {
					if(diferencialAnterior.equals( apuesta.getDiferencialMultiplicador() )) {
						listaResult.add(apuesta);
						
						System.out.println("Agregado porque " + diferencialAnterior + " igual " + apuesta.getDiferencialMultiplicador());
					}
				}
			}
		}
		
		Collections.sort(listaResult, comparator);
		
		return listaResult;
	}

	public List<PartidoParaApostarBO> primerasApuestasPorDiferenciaMayorA(
			List<PartidoParaApostarBO> apuestasDisponibles, GregorianCalendar fecha,
			Integer cantidadDeseada, Double minimoGanancia) {
		List<PartidoParaApostarBO> listaResult = new ArrayList<PartidoParaApostarBO>();
		Double diferencialAnterior = 0.0;
		
		DiferencialComparator comparator = new DiferencialComparator();
		Collections.sort(apuestasDisponibles, comparator);
		
		String fechaDeseada = ConstantesDeConfiguracion.dateFormat_diaMesAno.format(fecha.getTime());
		String fechaApuesta;
		
		for(PartidoParaApostarBO apuesta : apuestasDisponibles) {
			fechaApuesta = ConstantesDeConfiguracion.dateFormat_diaMesAno.format(apuesta.getDiaHoraJuego().getTime());
			if( fechaDeseada.equals(fechaApuesta) ) {
				if(listaResult.size() < cantidadDeseada && apuesta.getMinGanancia() > minimoGanancia) {
					listaResult.add(apuesta);
					diferencialAnterior = apuesta.getDiferencialMultiplicador();
					
					System.out.println("Agregado porque " + listaResult.size() + " menor a " + cantidadDeseada);
					
				} else {
					
					if(diferencialAnterior.equals( apuesta.getDiferencialMultiplicador() )) {
						listaResult.add(apuesta);
						
						System.out.println("Agregado porque " + diferencialAnterior + " igual " + apuesta.getDiferencialMultiplicador());
					}
				}
			}
		}
		
		Collections.sort(listaResult, comparator);
		
		return listaResult;
	}

	public List<PartidoParaApostarBO> apuestasMasParejas(
			List<PartidoParaApostarBO> apuestasDisponibles, GregorianCalendar fecha,
			Integer cantidad, Double minimoGanancia) {
		List<PartidoParaApostarBO> listaResult = new ArrayList<PartidoParaApostarBO>();
		Double diferencialAnterior = 0.0;
		
		JuegoArriesgadoComparator comparator = new JuegoArriesgadoComparator();
		Collections.sort(apuestasDisponibles, comparator);
		
		String fechaDeseada = ConstantesDeConfiguracion.dateFormat_diaMesAno.format(fecha.getTime());
		String fechaApuesta;
		
		for(PartidoParaApostarBO apuesta : apuestasDisponibles) {
			fechaApuesta = ConstantesDeConfiguracion.dateFormat_diaMesAno.format(apuesta.getDiaHoraJuego().getTime());
			if( fechaDeseada.equals(fechaApuesta) 
					&& apuesta.getMinGanancia() >= minimoGanancia 
					&& ( apuesta.getMultiplicadorGanador1() != apuesta.getMultiplicadorGanador2() )
					&& ( apuesta.getMultiplicadorEmpate() != apuesta.getMultiplicadorGanador1() ) ) {
				if(listaResult.size() < cantidad ) {
					listaResult.add(apuesta);
					diferencialAnterior = apuesta.getDiferencialMultiplicador();
					
					System.out.println("Agregado porque " + listaResult.size() + " menor a " + cantidad);
					
				} else {
					
					if(diferencialAnterior.equals( apuesta.getDiferencialMultiplicador() )) {
						listaResult.add(apuesta);
						
						System.out.println("Agregado porque " + diferencialAnterior + " igual " + apuesta.getDiferencialMultiplicador());
					}
				}
			}
		}
		
		Collections.sort(listaResult, comparator);
		
		return listaResult;
	}

}
