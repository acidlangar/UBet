package org.idsiom.utilbet.currentuse.bo;

public class PyckBO {
	private CurrentPOddsPortal partido;
	
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
	
	
	
}
