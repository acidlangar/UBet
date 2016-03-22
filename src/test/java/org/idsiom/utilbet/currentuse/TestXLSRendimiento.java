package org.idsiom.utilbet.currentuse;

import java.io.IOException;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.PyckBO;
import org.idsiom.utilbet.currentuse.xls.SeguimientoPyckXLS;
import org.junit.Test;

public class TestXLSRendimiento {
	
	@Test
	public void testReadExcel() {
		ISeguimientoPyckPersistencia s = new SeguimientoPyckXLS();
		List<PyckBO> list = null;
		try {
			list = s.getPyckPorDefinir();
		} catch (IOException e) {
			System.out.println("Ocurrio un problema inesperado");
			e.printStackTrace();
		}
		
		System.out.println("list.size = " + list.size());
		for(PyckBO p : list) {
			System.out.println(p);
		}
		
	}
}
