package org.idsiom.utilbet.bo;

import java.util.ArrayList;

public class CuadroPrevioApuestasBO {
	private ArrayList<CodigosPartidoBO> listaPartidos;
	private MetodoDeApuestasBO metodo;
	
	public CuadroPrevioApuestasBO() {
		listaPartidos = new ArrayList<CodigosPartidoBO>();
	}
	
	public ArrayList<CodigosPartidoBO> getListaPartidos() {
		return listaPartidos;
	}
	public void setListaPartidos(ArrayList<CodigosPartidoBO> listaPartidos) {
		this.listaPartidos = listaPartidos;
	}
	public MetodoDeApuestasBO getMetodo() {
		return metodo;
	}
	public void setMetodo(MetodoDeApuestasBO metodo) {
		this.metodo = metodo;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Metodo :: ");
		sb.append(this.metodo);
		sb.append(". Lista de Partidos :: ");
		int i = 1;
		for(CodigosPartidoBO partido : listaPartidos) {
			sb.append("\\n");
			sb.append(i);
			sb.append(" - ");
			sb.append(partido);
			
			i++;
		}
		
		String result = sb.toString();
		sb = null;
		
		return result;
	}
}
