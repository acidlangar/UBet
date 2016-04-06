package org.idsiom.utilbet.currentuse;

import java.io.IOException;

import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;
import org.idsiom.utilbet.currentuse.interlocutor.exception.InteraccionException;
import org.junit.Test;

public class TestCantActivos {

	@Test 
	public void getCantJuegosActivos() {
		PickioInterlocutorImpl interlocutor;
		try {
			interlocutor = PickioInterlocutorImpl.getInstance();
			try {
				System.out.println(interlocutor.getCantidadPycksActivos());
			} catch (InteraccionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
