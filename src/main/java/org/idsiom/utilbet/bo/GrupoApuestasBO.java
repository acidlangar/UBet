package org.idsiom.utilbet.bo;

import java.util.ArrayList;

public class GrupoApuestasBO {
	private Integer idGrupo;
	private ArrayList<PartidoParaApostarBO> apuestas;
	
	public GrupoApuestasBO() {
		apuestas = new ArrayList<PartidoParaApostarBO>();
	}
	
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public ArrayList<PartidoParaApostarBO> getApuestas() {
		return apuestas;
	}
	public void setApuestas(ArrayList<PartidoParaApostarBO> apuestas) {
		this.apuestas = apuestas;
	}
	public void addApuesta(PartidoParaApostarBO apuesta) {
		this.apuestas.add(apuesta);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("-------->>>  G = ");
		sb.append(idGrupo);
		sb.append( "\n" );
		sb.append("; ");
		for(PartidoParaApostarBO a : apuestas) {
			sb.append(a);
		}
		
		String result = sb.toString();
		sb = null;
		
		return result;
	}
	
	
}
