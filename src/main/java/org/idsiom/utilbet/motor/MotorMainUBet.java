package org.idsiom.utilbet.motor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.idsiom.utilbet.bo.PartidoParaApostarBO;
import org.idsiom.utilbet.core.IIdentificadorDeOportunidad;
import org.idsiom.utilbet.core.IdentificadorDeOportunidad;
import org.idsiom.utilbet.exception.InteraccionException;
import org.idsiom.utilbet.interlocutor.IInterlocutorCasaDeBets;
import org.idsiom.utilbet.interlocutor.InterlocutorBetCrisImpl;


public class MotorMainUBet {

	/**
	 * @param args
	 * @throws InteraccionException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InteraccionException, IOException {
		// Instanciar el interlocutor web
		IInterlocutorCasaDeBets interlocutor = InterlocutorBetCrisImpl.getInstance();
		
		// Consultar las apuestas disponibles 
		List<PartidoParaApostarBO> apuestasDisponibles = interlocutor.consultarApuestasDisponibles(true);
		
		// Llamar al CORE
		IIdentificadorDeOportunidad identificador = new IdentificadorDeOportunidad();
		
		//-------- pa elegir 
		Integer cantidadDeseada = 16;
		GregorianCalendar fechaDeseada = new GregorianCalendar();
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Indique para cuando desea revisar las apuestas. Hoy (H) o Manana (M) :: ");
		String respUser = br.readLine();
		System.out.println("Respuesta recibida :: " + respUser);
		
		
		if(respUser.trim().equalsIgnoreCase("H")) {
			//------------------------>>>>>>>>>>>>>>
			// Para Hoy
			fechaDeseada.set(Calendar.DAY_OF_YEAR, fechaDeseada.get(Calendar.DAY_OF_YEAR) );
			
			System.out.println("Se revisaran las apuestas para el dia de hoy");
		} else {
			//------------------------>>>>>>>>>>>>>>
			// Para Manana
			fechaDeseada.set(Calendar.DAY_OF_YEAR, fechaDeseada.get(Calendar.DAY_OF_YEAR)+1 );
			System.out.println("Se revisaran las apuestas para el dia de manana");
		}

				
		List<PartidoParaApostarBO> apuestasParejasHoy = identificador.apuestasMasParejas(apuestasDisponibles, fechaDeseada, cantidadDeseada, 2.4);
				
				
		// Imprimir el resultado correspondiente
		System.out.println("*****************           IMPRESION JUEGOS PAREJOS :: DIA 1 :: ");
		for( PartidoParaApostarBO bo : apuestasParejasHoy ) {
			System.out.println(bo);
		}
		
		
		//-------------------->>>>>>
		// SACAR LOS JUEGOS PARA EL SIGUIENTE DIA TAMBIEN
		fechaDeseada.set(Calendar.DAY_OF_YEAR, fechaDeseada.get(Calendar.DAY_OF_YEAR)+1 );
		System.out.println("Se revisaran las apuestas para el dia de manana");
	
		apuestasParejasHoy = identificador.apuestasMasParejas(apuestasDisponibles, fechaDeseada, cantidadDeseada, 2.4);
			
				
		// Imprimir el resultado correspondiente
		System.out.println("*****************           IMPRESION JUEGOS PAREJOS :: DIA 2 :: ");
		for( PartidoParaApostarBO bo : apuestasParejasHoy ) {
			System.out.println(bo);
		}
				
		System.out.println("Fin de ejecucion ");
		
	}

}
