package org.idsiom.utilbet.common.oddsportal;

import org.apache.log4j.Logger;

public class UtilOP {
	
	static Logger logger = Logger
			.getLogger(UtilOP.class);
					
	public static String[] getArrayEqs(String country, String weEquiposText)
			throws Exception {
		String[] array = { "", "" };
		
		Boolean ligaEsperadaConEquiposRaros = false;
		
		
		if (country.equalsIgnoreCase("norway") 
				|| country.equalsIgnoreCase("usa")
				) {
			ligaEsperadaConEquiposRaros = true;
		}
		
		if(weEquiposText.split("-").length > 2 && !ligaEsperadaConEquiposRaros) {
			throw new Exception("Eq inesperado con guion interno :: "
					+ weEquiposText);
		}
		
		try {
			if (country.equalsIgnoreCase("norway")
					&& weEquiposText.split("-").length > 2) {
				// "Ham-Kam"
				if (weEquiposText.split("-")[0].startsWith("Ham")) {
					array[0] = weEquiposText.split("-")[0] + "-"
							+ weEquiposText.split("-")[1];
					array[1] = weEquiposText.split("-")[2];
				} else {
					array[0] = weEquiposText.split("-")[0];
					array[1] = weEquiposText.split("-")[1] + "-"
							+ weEquiposText.split("-")[2];
				}

			} else {
				array[0] = weEquiposText.split("-")[0];
				array[1] = weEquiposText.split("-")[1];
			}
			
			if (country.equalsIgnoreCase("usa")
					&& weEquiposText.split("-").length > 2) {
				// "Ham-Kam"
				if (weEquiposText.split("-")[0].startsWith("All")) {
					array[0] = weEquiposText.split("-")[0] + "-"
							+ weEquiposText.split("-")[1];
					array[1] = weEquiposText.split("-")[2];
				} else {
					array[0] = weEquiposText.split("-")[0];
					array[1] = weEquiposText.split("-")[1] + "-"
							+ weEquiposText.split("-")[2];
				}

			} else {
				array[0] = weEquiposText.split("-")[0];
				array[1] = weEquiposText.split("-")[1];
			}
	
		} catch(java.lang.ArrayIndexOutOfBoundsException ex) {
			logger.error("No pudo acceder a alguno de los eqs . weEquiposText = " + weEquiposText,ex);
			return null;
		}
		
		return array;
	}
}
