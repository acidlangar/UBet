package org.idsiom.utilbet.currentuse.bo;

public enum TipoPartidoPorCuotas {
	LOCAL_50,
	VISITANTE_50,
	LOCAL_PAREJOS,
	VISITANTE_PAREJOS,
	LOCAL_58,
	VISITANTE_58,
	LOCAL_PAREJOS_2,
	VISITANTE_PAREJOS_2,
	LOCAL_SUPER_FAV,
	LOCAL_MUY_FAV,
	LOC_FAV,
	OTRO;
	
	
	public static TipoPartidoPorCuotas getClasificacionPorCuotas(Double c1, Double cX, Double c2) {
		TipoPartidoPorCuotas row = null;
		
		if ((c1 >= 1.7 && c1 <=2) && (cX > c1 && cX < c2) ) {
			
			row = LOCAL_50;
		}
		else if ((c2 >= 1.7 && c2 <=2) && (cX > c2 && cX < c1) ) {
			
			row = VISITANTE_50;
		}
		else if ((c1 > 2 && cX > 2 && c2 > 2) && (c2 > c1 && c2 < cX) ) {
			
			row = LOCAL_PAREJOS;
		}
		else if ((c1 > 2 && cX > 2 && c2 > 2) && (c1 > c2 && c1 < cX) ) {
			
			row = VISITANTE_PAREJOS;
		}
		else if ((c1 >= 1.47 && c1 <1.7) && (cX > c1 && cX < c2) ) {
			
			row = LOCAL_58;
		}
		else if ((c2 >= 1.47 && c2 <1.7) && (cX > c2 && cX < c1) ) {
			
			row = VISITANTE_58;
		}
		else if ((c1 > 2 && cX > 2 && c2 > 2) && (c2 > c1 && c2 >= cX) ) {
							
				row = LOCAL_PAREJOS_2;
		}
		else if ((c1 > 2 && cX > 2 && c2 > 2) && (c1 > c2 && c1 >= cX) ) {
			
			row = VISITANTE_PAREJOS_2;
		}
		else if ((c1 > 1 && c1 <=1.2) && (cX > c1 && cX < c2) ) {
			
			row = LOCAL_SUPER_FAV;
		}
		else if ((c1 > 1.2 && c1 <=1.4) && (cX > c1 && cX < c2) ) {
			
			row = LOCAL_MUY_FAV;
		}
		else if ((c1 > 1.4 && c1 <1.7) && (cX > c1 && cX < c2) ) {

			row = LOC_FAV;
		}
		else {
			row = OTRO;
		}
		
		return row;
	}
	
}
