package org.idsiom.utilbet.bo;

public class PronosticoMillonesBO {
	private String liga;
	private String fechaMDA;  //smalldatetime
	private String nomEqL;
	private String nomEqV;
	private String pronostico;
	private Integer porcConfianza;
	private String pagoPaginas;
	private String pagoCalculado;
	
	private String pagoPaginas_1;
	private String pagoPaginas_2;
	private String pagoPaginas_X;
	private Integer porcConf_1;
	private Integer porcConf_2;
	private Integer porcConf_X;
	
	
	
	public Integer getPorcConf_1() {
		return porcConf_1;
	}

	public void setPorcConf_1(Integer porcConf_1) {
		this.porcConf_1 = porcConf_1;
	}

	public Integer getPorcConf_2() {
		return porcConf_2;
	}

	public void setPorcConf_2(Integer porcConf_2) {
		this.porcConf_2 = porcConf_2;
	}

	public Integer getPorcConf_X() {
		return porcConf_X;
	}

	public void setPorcConf_X(Integer porcConf_X) {
		this.porcConf_X = porcConf_X;
	}

	public String getPagoPaginas_1() {
		return pagoPaginas_1;
	}

	public void setPagoPaginas_1(String pagoPaginas_1) {
		this.pagoPaginas_1 = pagoPaginas_1;
	}

	public String getPagoPaginas_2() {
		return pagoPaginas_2;
	}

	public void setPagoPaginas_2(String pagoPaginas_2) {
		this.pagoPaginas_2 = pagoPaginas_2;
	}

	public String getPagoPaginas_X() {
		return pagoPaginas_X;
	}

	public void setPagoPaginas_X(String pagoPaginas_X) {
		this.pagoPaginas_X = pagoPaginas_X;
	}

	private Integer acierto;
	private String pronosticoDobles;
	private Integer porcConfianzaDobles;
	private String pagoPaginasDobles;
	private String pagoCalculadoDobles;
	private Integer aciertoDobles;
	
	public PronosticoMillonesBO() {
		
	}
	
	public PronosticoMillonesBO(
			String liga,
			String fechaMDA,  //smalldatetime
			String nomEqL,
			String nomEqV,
			String pronostico,
			Integer porcConfianza,
			String pagoPaginas,
			String pagoCalculado,
			Integer acierto,
			String pronosticoDobles,
			Integer porcConfianzaDobles,
			String pagoPaginasDobles,
			String pagoCalculadoDobles,
			Integer aciertoDobles
			) {
		this.liga = liga;
		this.fechaMDA = fechaMDA;
		this.nomEqL = nomEqL;
		this.nomEqV = nomEqV;
		this.pronostico = pronostico;
		this.porcConfianza = porcConfianza;
		this.pagoPaginas = pagoPaginas;
		this.pagoCalculado = pagoCalculado;
		this.acierto = acierto;
		this.pronosticoDobles = pronosticoDobles;
		this.porcConfianzaDobles = porcConfianzaDobles;
		this.pagoPaginasDobles = pagoPaginasDobles;
		this.pagoCalculadoDobles = pagoCalculadoDobles;
		this.aciertoDobles = aciertoDobles;
	}
	
	public String getLiga() {
		return liga;
	}
	public void setLiga(String liga) {
		this.liga = liga;
	}
	public String getFechaMDA() {
		return fechaMDA;
	}
	public void setFechaMDA(String fechaMDA) {
		this.fechaMDA = fechaMDA;
	}
	public String getNomEqL() {
		return nomEqL;
	}
	public void setNomEqL(String nomEqL) {
		this.nomEqL = nomEqL;
	}
	public String getNomEqV() {
		return nomEqV;
	}
	public void setNomEqV(String nomEqV) {
		this.nomEqV = nomEqV;
	}
	public String getPronostico() {
		return pronostico;
	}
	public void setPronostico(String pronostico) {
		this.pronostico = pronostico;
	}
	public Integer getPorcConfianza() {
		return porcConfianza;
	}
	public void setPorcConfianza(Integer porcConfianza) {
		this.porcConfianza = porcConfianza;
	}
	public String getPagoPaginas() {
		return pagoPaginas;
	}
	public void setPagoPaginas(String pagoPaginas) {
		this.pagoPaginas = pagoPaginas;
	}
	public String getPagoCalculado() {
		return pagoCalculado;
	}
	public void setPagoCalculado(String pagoCalculado) {
		this.pagoCalculado = pagoCalculado;
	}
	public Integer getAcierto() {
		return acierto;
	}
	public void setAcierto(Integer acierto) {
		this.acierto = acierto;
	}
	public String getPronosticoDobles() {
		return pronosticoDobles;
	}
	public void setPronosticoDobles(String pronosticoDobles) {
		this.pronosticoDobles = pronosticoDobles;
	}
	public Integer getPorcConfianzaDobles() {
		return porcConfianzaDobles;
	}
	public void setPorcConfianzaDobles(Integer porcConfianzaDobles) {
		this.porcConfianzaDobles = porcConfianzaDobles;
	}
	public String getPagoPaginasDobles() {
		return pagoPaginasDobles;
	}
	public void setPagoPaginasDobles(String pagoPaginasDobles) {
		this.pagoPaginasDobles = pagoPaginasDobles;
	}
	public String getPagoCalculadoDobles() {
		return pagoCalculadoDobles;
	}
	public void setPagoCalculadoDobles(String pagoCalculadoDobles) {
		this.pagoCalculadoDobles = pagoCalculadoDobles;
	}
	public Integer getAciertoDobles() {
		return aciertoDobles;
	}
	public void setAciertoDobles(Integer aciertoDobles) {
		this.aciertoDobles = aciertoDobles;
	}	
	
	public String getStringInsert() {
		StringBuffer sb = new StringBuffer(2500);
		String result;
		
		sb.append("INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],");
		sb.append("[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto],[PronosticoDobles],[PorcConfianzaDobles],");
		sb.append("[PagoPaginasDobles],[PagoCalculadoDobles],[AciertoDobles],[PagoPaginas_1],[PorcConfianza_1],");
		sb.append("[PagoPaginas_2],[PorcConfianza_2],[PagoPaginas_X],[PorcConfianza_X]) VALUES (");
				
		// 1,  //Liga
		sb.append("'");
		sb.append(this.liga);
		sb.append("', ");
		
		// '12/12/2014', // Fecha
		sb.append("'");
		sb.append(this.fechaMDA);
		sb.append("', ");
		
		// 'Dinamo Zagreb', //NomEqL
		sb.append("'");
		sb.append(this.nomEqL);
		sb.append("', ");
		
		// 'Slaven Belupo', //NomEqV
		sb.append("'");
		sb.append(this.nomEqV);
		sb.append("', ");
		
		// '1', //Pronostico
		sb.append("'");
		sb.append(this.pronostico);
		sb.append("', ");
		
		// 80, //PorcConfianza
		sb.append(this.porcConfianza);
		sb.append(", ");
		
		// CONVERT(DECIMAL(18,3),REPLACE('1,16', ',', '.')), //PagoPaginas
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		if(this.pagoPaginas.trim().equals("-")) {
			sb.append("0");
		} else {
			sb.append(this.pagoPaginas);
		}
		sb.append("', ',', '.')), ");
		
		// CONVERT(DECIMAL(18,3),REPLACE('1,25', ',', '.')), //PagoCalculado
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		sb.append(this.pagoCalculado);
		sb.append("', ',', '.')), ");
		
		// 1, //Acierto
		sb.append(this.acierto);
		sb.append(", ");
		
		// '1X', //PronosticoDobles
		sb.append("'");
		sb.append(this.pronosticoDobles);
		sb.append("', ");
		
		// 95, //PorcConfianzaDobles
		sb.append(this.porcConfianzaDobles);
		sb.append(", ");
		
		// CONVERT(DECIMAL(18,3),REPLACE('1,1', ',', '.')), //PagoPaginasDobles
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		sb.append(this.pagoPaginasDobles);
		sb.append("', ',', '.')), ");
		
		// CONVERT(DECIMAL(18,3),REPLACE('1,05', ',', '.')), //PagoCalculadoDobles
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		sb.append(this.pagoCalculadoDobles);
		sb.append("', ',', '.')), ");
		
		// 1 , //AciertoDobles
		sb.append(this.aciertoDobles);
		sb.append(", ");
		
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		sb.append(this.pagoPaginas_1);
		sb.append("', ',', '.')), ");
		
		sb.append(this.porcConf_1);
		sb.append(", ");
		
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		sb.append(this.pagoPaginas_2);
		sb.append("', ',', '.')), ");
		
		sb.append(this.porcConf_2);
		sb.append(", ");
		
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		sb.append(this.pagoPaginas_X);
		sb.append("', ',', '.')), ");
		
		sb.append(this.porcConf_X);
		
		
		sb.append(")");
		
		
		result = sb.toString();
		sb = null;
		
		return result;
		
	}
	
	private String getStringInsertSimple() { // dejado private para utilizar el de arriba
		StringBuffer sb = new StringBuffer(160);
		String result;
		
		sb.append("INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],");
		sb.append("[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]");
		sb.append(") VALUES (");
		
		// 1,  //Liga
		sb.append("'");
		sb.append(this.liga);
		sb.append("', ");
		
		// '12/12/2014', // Fecha
		sb.append("'");
		sb.append(this.fechaMDA);
		sb.append("', ");
		
		// 'Dinamo Zagreb', //NomEqL
		sb.append("'");
		sb.append(this.nomEqL);
		sb.append("', ");
		
		// 'Slaven Belupo', //NomEqV
		sb.append("'");
		sb.append(this.nomEqV);
		sb.append("', ");
		
		// '1', //Pronostico
		sb.append("'");
		sb.append(this.pronostico);
		sb.append("', ");
		
		// 80, //PorcConfianza
		sb.append(this.porcConfianza);
		sb.append(", ");
		
		// CONVERT(DECIMAL(18,3),REPLACE('1,16', ',', '.')), //PagoPaginas
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		sb.append(this.pagoPaginas);
		sb.append("', ',', '.')), ");
		
		// CONVERT(DECIMAL(18,3),REPLACE('1,25', ',', '.')), //PagoCalculado
		sb.append(" CONVERT(DECIMAL(18,3),REPLACE('");
		sb.append(this.pagoCalculado);
		sb.append("', ',', '.')), ");
		
		// 1, //Acierto
		sb.append(this.acierto);
		
		sb.append(")");
		
		
		result = sb.toString();
		sb = null;
		
		return result;
		
	}
	
	/*
	INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto],[PronosticoDobles],[PorcConfianzaDobles],[PagoPaginasDobles],[PagoCalculadoDobles],[AciertoDobles]) VALUES (1,'12/12/2014','Dinamo Zagreb','Slaven Belupo','1',80,CONVERT(DECIMAL(18,3), REPLACE('1,16', ',', '.')),CONVERT(DECIMAL(18,3), REPLACE('1,25', ',', '.')),1,'1X',95,CONVERT(DECIMAL(18,3), REPLACE('1,1', ',', '.')),CONVERT(DECIMAL(18,3), REPLACE('1,05', ',', '.')),1)
	*/
}
