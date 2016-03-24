package org.idsiom.utilbet.currentuse.bo;

public class PyckBO {
	private CurrentPOddsPortal partido;
	
	private EstadoPyck estado;
	
	private ResultadoPartidoBO pyck;
	
	private int stake;
	
	private Boolean acierto;

	
	
	public int getStake() {
		return stake;
	}

	public void setStake(int stake) {
		this.stake = stake;
	}

	public CurrentPOddsPortal getPartido() {
		return partido;
	}

	public void setPartido(CurrentPOddsPortal partido) {
		this.partido = partido;
	}

	public ResultadoPartidoBO getPyck() {
		return pyck;
	}

	public void setPyck(ResultadoPartidoBO pyck) {
		this.pyck = pyck;
	}

	public Boolean getAcierto() {
		return acierto;
	}

	public void setAcierto(Boolean acierto) {
		this.acierto = acierto;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		sb.append("stake = ");
		sb.append(this.stake);
		sb.append("; buscado = ");
		sb.append(this.pyck);
		sb.append("; fecha = ");
		sb.append(this.partido.getFecha());
		sb.append(", equipos = ");
		sb.append(this.partido.getEquipos());
		sb.append(", liga = ");
		sb.append(this.partido.getLeague());
		sb.append(", country = ");
		sb.append(this.partido.getCountry());
		
		
		result = sb.toString();
		sb = null;
		
		return result;
	}

	public double getOdds() {
		
		if(this.pyck.equals(ResultadoPartidoBO.LOCAL)) {
			return this.partido.getC1();
		} else if(this.pyck.equals(ResultadoPartidoBO.EMPATE)) {
			return this.partido.getcX();
		} else {
			return this.partido.getC2();
		}
		
		
	}

	public EstadoPyck getEstado() {
		return estado;
	}

	public void setEstado(EstadoPyck estado) {
		this.estado = estado;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof PyckBO) {
			PyckBO pyck = (PyckBO)obj;
			return this.partido.equals(pyck.getPartido());
		} else {
			return false;
		}
		
		
	}
	
}
