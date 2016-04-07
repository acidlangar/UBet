package org.idsiom.utilbet.currentuse;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	
	public static void main(String[] args) throws IOException {
		Scanner in;
		int tipoejecucion = 0;
		System.out.println("Hola chamo!!!, te voy a ayudar a elegir los juegos.... ");
		
		in = new Scanner(System.in);
		
		//----->>>>>>
		//-- Esperar para dar la impresión de conversacion... jajajja
		try {
			Thread.sleep(850);
		} catch(Exception ex) {
			// No hacer nada porque realmente no importa...
		}
		
		System.out.println("Para ayudarte, lo primero que vas hacer es 1 si quieres bajar historia, o 2 si quieres hacer seguimiento, 3 montar Pycks directos");

		tipoejecucion = in.nextInt();
		in.close();
		switch(tipoejecucion) {
			case 1:
				MainCurrentUseFromOP.main(args);
				break;

			case 2:
				MainFromFileCurrentP.main(args);
				break;
			
			case 3:
				MainMontarPycks.main(args);
				break;	
				
			default:
				System.out.println("Sos boludo definitivamente... bye bye... vuelve pronto...");
				break;
				
		}
		
		System.out.println("Decide bien tus picks!!, mucha suerte!!!... bye bye... vuelve pronto");
		
		
	}

}
