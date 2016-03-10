package org.idsiom.utilbet.currentuse;

import java.io.IOException;

import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;
import org.junit.Test;

public class TestInterlocutorPyckio {

	@Test
	public void testProxPartidos() throws IOException {
		PickioInterlocutorImpl inter =  PickioInterlocutorImpl.getInstance();
		
		inter.getPartidosPorHora(0L);
		
	}
	
}
