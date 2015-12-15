package org.idsiom.utilbet.analisis.bo;

public class PosicionTableBO {
	
	private Integer posicion;
	
	private String eq;
	
	private Integer numGames;
	
	private Integer numW;
	
	private Integer numD;
	
	private Integer numL;
	
	private Integer gf;
	
	private Integer gc;
	
	private Integer dg;
	
	private Integer ptos;

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public String getEq() {
		return eq;
	}

	public void setEq(String eq) {
		this.eq = eq;
	}

	public Integer getNumGames() {
		return numGames;
	}

	public void setNumGames(Integer numGames) {
		this.numGames = numGames;
	}

	public Integer getNumW() {
		return numW;
	}

	public void setNumW(Integer numW) {
		this.numW = numW;
	}

	public Integer getNumD() {
		return numD;
	}

	public void setNumD(Integer numD) {
		this.numD = numD;
	}

	public Integer getNumL() {
		return numL;
	}

	public void setNumL(Integer numL) {
		this.numL = numL;
	}

	public Integer getGf() {
		return gf;
	}

	public void setGf(Integer gf) {
		this.gf = gf;
	}

	public Integer getGc() {
		return gc;
	}

	public void setGc(Integer gc) {
		this.gc = gc;
	}

	public Integer getDg() {
		return dg;
	}

	public void setDg(Integer dg) {
		this.dg = dg;
	}

	public Integer getPtos() {
		return ptos;
	}

	public void setPtos(Integer ptos) {
		this.ptos = ptos;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		sb.append("<pos=");
		sb.append(this.posicion);
		sb.append("; eq=");
		sb.append(this.eq);
		sb.append("; numGames=");
		sb.append(this.numGames);
		sb.append("; numW=");
		sb.append(this.numW);
		sb.append("; numD=");
		sb.append(this.numD);
		sb.append("; numL=");
		sb.append(this.numL);
		sb.append("; GF=");
		sb.append(this.gf);
		sb.append("; GC=");
		sb.append(this.gc);
		sb.append("; GD=");
		sb.append(this.dg);
		sb.append("; PTOS=");
		sb.append(this.ptos);
		sb.append(">");
		
		result = sb.toString();
		sb = null;
		
		return result;
	}
}
