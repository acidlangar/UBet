package org.idsiom.utilbet.bo;

import java.util.ArrayList;
import java.util.List;

public class DefApuestaCombinadaBO extends DefApuestaBO {
	private TypeApuestaBO tipo = TypeApuestaBO.COMBINADA;

	private List<DefApuestaSencillaBO> apuestasSencillas = new ArrayList<DefApuestaSencillaBO>();
	
	@Override
	public TypeApuestaBO getTipo() {
		return this.tipo;
	}

	public List<DefApuestaSencillaBO> getApuestasSencillas() {
		return apuestasSencillas;
	}

	public void setApuestasSencillas(List<DefApuestaSencillaBO> apuestasSencillas) {
		this.apuestasSencillas = apuestasSencillas;
	}

	public void setTipo(TypeApuestaBO tipo) {
		this.tipo = tipo;
	}
	
	public void addApuestaSencilla(DefApuestaSencillaBO apuestaSencilla) {
		this.apuestasSencillas.add(apuestaSencilla);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		for(DefApuestaSencillaBO as : apuestasSencillas) {
			sb.append(as);
		}
		
		result = sb.toString();
		sb = null;
		
		return result;
	}

	
	
	
	
}
