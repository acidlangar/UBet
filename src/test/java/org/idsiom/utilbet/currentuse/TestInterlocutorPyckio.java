package org.idsiom.utilbet.currentuse;

import java.io.IOException;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.interlocutor.PickioInterlocutorImpl;
import org.junit.Test;


public class TestInterlocutorPyckio {

	@Test
	public void testProxPartidos() throws IOException {
		PickioInterlocutorImpl inter =  PickioInterlocutorImpl.getInstance();
		
		List<PartidoPyckioBO> list = inter.getPartidosPorHora(0L);
		
		for(PartidoPyckioBO p : list) {
			System.out.println("__________>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " );
			System.out.println("__________ " );
			System.out.println("__________ " );
			
			System.out.println( p.toString() );
			
			System.out.println(" " );
			
		}
		
	}
	
}
