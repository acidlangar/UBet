UPDATE PARTIDO SET TIPOPARTIDO = 99


/*else if ((item.getC1() >= 1.7 && item.getC1() <=2) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 1
	WHERE 
		(CUOTA1 >= 1.7 AND CUOTA1 <= 2)
		AND (COUTAX > CUOTA1 AND COUTAX < CUOTA2)
					

/*else if ((item.getC2() >= 1.7 && item.getC2() <=2) && (item.getcX() > item.getC2() && item.getcX() < item.getC1()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 2
	WHERE
		(CUOTA2 >= 1.7 AND CUOTA2 <= 2)
		AND (COUTAX > CUOTA2 AND COUTAX < CUOTA1)
					
/*else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC2() > item.getC1() && item.getC2() < item.getcX()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 3
	WHERE
		CUOTA1 > 2
		AND COUTAX > 2
		AND CUOTA2 > 2
		AND CUOTA2 > CUOTA1
		AND CUOTA2 < COUTAX
					
					
/*else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC1() > item.getC2() && item.getC1() < item.getcX()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 4
	WHERE
		CUOTA1 > 2
		AND COUTAX > 2
		AND CUOTA2 > 2
		AND CUOTA1 > CUOTA2
		AND CUOTA1 < COUTAX
					
/*else if ((item.getC1() >= 1.47 && item.getC1() <1.7) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 5
	WHERE
		CUOTA1 >= 1.47
		AND CUOTA1 < 1.7
		AND COUTAX > CUOTA1
		AND COUTAX < CUOTA2

/*else if ((item.getC2() >= 1.47 && item.getC2() <1.7) && (item.getcX() > item.getC2() && item.getcX() < item.getC1()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 6
	WHERE
		CUOTA2 >= 1.47
		AND CUOTA2 < 1.7
		AND COUTAX > CUOTA2
		AND COUTAX < CUOTA1
					
/*else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC2() > item.getC1() && item.getC2() >= item.getcX()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 7
	WHERE
		CUOTA1 > 2
		AND COUTAX > 2
		AND CUOTA2 > 2
		AND CUOTA2 > CUOTA1
		AND CUOTA2 >= COUTAX
									

/*else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC1() > item.getC2() && item.getC1() >= item.getcX()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 8
	WHERE
		CUOTA1 > 2
		AND COUTAX > 2
		AND CUOTA2 > 2
		AND CUOTA1 > CUOTA2
		AND CUOTA1 >= COUTAX
					
/*else if ((item.getC1() > 1 && item.getC1() <=1.2) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 9
	WHERE
		CUOTA1 > 1
		AND CUOTA1 <= 1.2
		AND COUTAX > CUOTA1
		AND COUTAX < CUOTA2
		

/*else if ((item.getC1() > 1.2 && item.getC1() <=1.4) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 10
	WHERE
		CUOTA1 > 1.2
		AND CUOTA1 <= 1.4
		AND COUTAX > CUOTA1
		AND COUTAX < CUOTA2


/*if ((item.getC1() > 1.4 && item.getC1() <1.7) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {*/
UPDATE PARTIDO SET TIPOPARTIDO = 11
	WHERE
		CUOTA1 > 1.4
		AND CUOTA1 < 1.7
		AND COUTAX > CUOTA1
		AND COUTAX < CUOTA2
	
