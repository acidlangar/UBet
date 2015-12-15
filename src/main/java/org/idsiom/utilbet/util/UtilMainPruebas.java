package org.idsiom.utilbet.util;

public class UtilMainPruebas {

	public static void main(String[] args) {
		System.out.println( LevenshteinDistance.computeLevenshteinDistance("Los Angeles Galaxy", "L.A. Galaxy") );
		System.out.println( "Los Angeles Galaxy".contains("L.A. Galaxy") );
		System.out.println( "Los Angeles Galaxy".contains("Galaxy") );
		System.out.println( LevenshteinDistance.computeLevenshteinDistance("Los Angeles Galaxy", "FC Dallas") );
		System.out.println( "Los Angeles Galaxy".contains("FC Dallas") );
		System.out.println( "Los Angeles Galaxy".contains("Dallas") );
	}

}
