package org.idsiom.utilbet.bo;

public class DefApuestaSencillaBO extends DefApuestaBO {
	private TypeApuestaBO tipo = TypeApuestaBO.SENCILLA;

	private Integer idPartido;
	
	private ResultPartidoBO apuesta;
	
public DefApuestaSencillaBO() {
		
	}
	
	public DefApuestaSencillaBO(Integer idPartido) {
		this.idPartido = idPartido;
	}
	
	@Override
	public TypeApuestaBO getTipo() {
		return this.tipo;
	}

	public Integer getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}

	public ResultPartidoBO getApuesta() {
		return apuesta;
	}

	public void setApuesta(ResultPartidoBO apuesta) {
		this.apuesta = apuesta;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		sb.append("partido ");
		sb.append(this.idPartido);
		sb.append(" ");
		sb.append(this.apuesta);
		sb.append("; ");
		
		result = sb.toString();
		sb = null;
		
		return result;
	}
	
	
}
