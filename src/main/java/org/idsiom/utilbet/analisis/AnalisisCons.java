package org.idsiom.utilbet.analisis;

import java.util.ArrayList;
import java.util.List;

import org.idsiom.utilbet.history.fromoddsportal.objutil.LgTempObjUtil;

public class AnalisisCons {
	public static final String[] OP_L = {"mls", "tippeligaen"};
	public static final String[] OP_C = {"usa", "norway"};
	
	public static final String OP_URL_BASE = "http://www.oddsportal.com/soccer/Z/Z/";
												
	
	
	//--------------------------
	public static final String SS_URL_BASE = "http://www.soccerstats.com/homeaway.asp?league=Z";
	
	
	public static List<LgTempObjUtil> getLgTemps() {
		List<LgTempObjUtil> result = new ArrayList<LgTempObjUtil>();
		
		for(int i = 2014; i <= 2014; i++) {
			result.add( new  LgTempObjUtil (
								OP_C[0],
								OP_L[0],
								Integer.toString(i) + "-" + Integer.toString(i+1),
								false
					));
		}
		
		return result;
	}
}
