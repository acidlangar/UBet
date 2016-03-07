package org.idsiom.utilbet.history.fromoddsportal;

import java.util.ArrayList;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.TipoPartidoPorCuotas;
import org.idsiom.utilbet.history.fromoddsportal.objutil.LgTempObjUtil;

public class Cons {
	/*  --- original
	public static final String[] T = {"2003-2004", "2004", "2004"};
	public static final String[] L = {"primera-division", "mls", "tippeligaen"};
	public static final String[] C = {"spain", "usa", "norway"};
	*/
	
	// ---  accusys 
	//public static final String[] T = {"2005"};
	public static final String[] L = {"primeira-liga"}; // 
	public static final String[] C = {"portugal"};  // 
	
	public static final String URL_BASE = "http://www.oddsportal.com/soccer/Z/Z-Z/results/";
	
	public static final String URL_BASE_CURRENT = "http://www.oddsportal.com/matches/soccer/";
	
	// http://www.oddsportal.com/matches/my-matches/20160104/
	public static final String URL_MYMATCHES_CURRENT = "http://www.oddsportal.com/matches/my-matches/";
	
	// http://www.oddsportal.com/soccer/usa/mls-2004/results/
	//http://www.oddsportal.com/soccer/norway/tippeligaen-2004/results/
	//england/premier-league-2003-2004/results/
	//http://www.oddsportal.com/soccer/netherlands/eredivisie-2003-2004/results/
	//http://www.oddsportal.com/soccer/sweden/allsvenskan-2004/results/
	//
	
	public static List<LgTempObjUtil> getLgTemps() {
		List<LgTempObjUtil> result = new ArrayList<LgTempObjUtil>();
		
		//for(int i = 2003; i <= 2014; i++) {
		for(int i = 2005; i <= 2005; i++) {
			result.add( new  LgTempObjUtil (
								C[0],
								L[0],
								Integer.toString(i) + "-" + Integer.toString(i+1),//Integer.toString(i),//Integer.toString(i) + "-" + Integer.toString(i+1),
								false
					));
		}
		
		return result;
	}
	
	
	
}
