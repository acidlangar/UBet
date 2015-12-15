package org.idsiom.utilbet.bo;

import java.util.ArrayList;

public class MetodoDeApuestasBO {
	private Integer idMetodo;
	private String descripcion;
	private ArrayList<DefApuestaBO> definicionesApuestas = new ArrayList<DefApuestaBO>();
	
	public Integer getIdMetodo() {
		return idMetodo;
	}
	public void setIdMetodo(Integer idMetodo) {
		this.idMetodo = idMetodo;
	}
	public ArrayList<DefApuestaBO> getDefinicionesApuestas() {
		return definicionesApuestas;
	}
	public void setDefinicionesApuestas(ArrayList<DefApuestaBO> definicionesApuestas) {
		this.definicionesApuestas = definicionesApuestas;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void addDefApuesta(DefApuestaBO def) {
		this.definicionesApuestas.add(def);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		String result;
		
		sb.append("idMetodo = ");
		sb.append(this.getIdMetodo());
		sb.append("; desc = ");
		sb.append(this.getDescripcion());
		
		for(DefApuestaBO def : this.getDefinicionesApuestas()) {
			sb.append("\n");
			sb.append(def);
		}
		
		result = sb.toString();
		sb = null;
		
		return result;
	}
	
	
}
