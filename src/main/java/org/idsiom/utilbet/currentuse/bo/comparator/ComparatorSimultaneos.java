package org.idsiom.utilbet.currentuse.bo.comparator;

import java.util.Comparator;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;

public class ComparatorSimultaneos implements Comparator<CurrentPOddsPortal>{

	private double c1;
	private double cX;
	private double c2;
	
	public ComparatorSimultaneos(double c1, double cX, double c2) {
		this.c1 = c1;
		this.c2 = c2;
		this.cX = cX;
	}
	
	public int compare(CurrentPOddsPortal arg0, CurrentPOddsPortal arg1) {
		double arg0_dif_1 = Math.abs(arg0.getC1() - this.c1);
		double arg0_dif_X = Math.abs(arg0.getcX() - this.cX);
		double arg0_dif_2 = Math.abs(arg0.getC2() - this.c2);
		
		double arg1_dif_1 = Math.abs(arg1.getC1() - this.c1);
		double arg1_dif_X = Math.abs(arg1.getcX() - this.cX);
		double arg1_dif_2 = Math.abs(arg1.getC2() - this.c2);
		
		double totDif0 = arg0_dif_1 + arg0_dif_2 + arg0_dif_X; 
		double totDif1 = arg1_dif_1 + arg1_dif_2 + arg1_dif_X;
		
		if(totDif0 == totDif1) {
			return 1;
		}
		
		if(totDif0 > totDif1) {
			return -1;
		}
		
		return 1;
	}

}
