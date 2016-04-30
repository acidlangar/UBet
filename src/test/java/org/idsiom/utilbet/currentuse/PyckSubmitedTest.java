package org.idsiom.utilbet.currentuse;

import java.io.IOException;
import java.util.Scanner;

import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;
import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;
import org.idsiom.utilbet.currentuse.interlocutor.exception.InteraccionException;
import org.junit.Test;

public class PyckSubmitedTest {

	@Test
	public void pyckSubmitedTest() {
		PickioInterlocutorImpl interlocutor;
		try {
			interlocutor = PickioInterlocutorImpl.getInstance();
			try {
				System.out.println(interlocutor.getCantidadPycksActivos());
			} catch (InteraccionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Scanner s = new Scanner(System.in);
			
			System.out.println("Waiteing... ");
			s.nextInt();
			System.out.println("Continued...");
			
			
			System.out.println("Resultado final = " + interlocutor.montar(ResultadoPartidoBO.LOCAL, 2));
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
}
