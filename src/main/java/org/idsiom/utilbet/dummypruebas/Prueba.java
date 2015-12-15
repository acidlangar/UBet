package org.idsiom.utilbet.dummypruebas;

import org.idsiom.utilbet.constantes.ConstantesDeConfiguracion;
import org.idsiom.utilbet.interlocutor.InterlocutorBetCrisImpl;

public class Prueba {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(
				ConstantesDeConfiguracion.dateFormatApp.format( 
						InterlocutorBetCrisImpl.getFechaFromStr( "ago 23", "12:53 PM" ).getTime() 
				)		
		);
				

	}

}
