package org.idsiom.utilbet.analisis.bo;

import java.util.ArrayList;
import java.util.List;

public class TRankingP {
	private List<PosicionTableBO> listaPosiciones = new ArrayList<PosicionTableBO>();

	public List<PosicionTableBO> getListaPosiciones() {
		return listaPosiciones;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		sb.append("<T\n");
		for(PosicionTableBO p : listaPosiciones) {
			sb.append(p);
			sb.append("\n");
		}
		sb.append(">\n");
		
		result = sb.toString();
		sb = null;
		
		return result;
		
	}
	
}
