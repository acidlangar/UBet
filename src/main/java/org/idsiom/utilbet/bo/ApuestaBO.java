package org.idsiom.utilbet.bo;

import java.util.GregorianCalendar;

import org.idsiom.utilbet.constantes.ConstantesDeConfiguracion;

public class ApuestaBO {
	private String nombreLiga;
	private GregorianCalendar diaHoraJuego;
	private String codigoGanador1;
	private String codigoGanador2;
	private String codigoEmpate;
	private String nombreGanador1;
	private String nombreGanador2;
	private double multiplicadorGanador1;
	private double multiplicadorGanador2;
	private double multiplicadorEmpate;
	
	public String getNombreLiga() {
		return nombreLiga;
	}
	public void setNombreLiga(String nombreLiga) {
		this.nombreLiga = nombreLiga;
	}
	public GregorianCalendar getDiaHoraJuego() {
		return diaHoraJuego;
	}
	public void setDiaHoraJuego(GregorianCalendar diaHoraJuego) {
		this.diaHoraJuego = diaHoraJuego;
	}
	public String getCodigoGanador1() {
		return codigoGanador1;
	}
	public void setCodigoGanador1(String codigoGanador1) {
		this.codigoGanador1 = codigoGanador1;
	}
	public String getCodigoGanador2() {
		return codigoGanador2;
	}
	public void setCodigoGanador2(String codigoGanador2) {
		this.codigoGanador2 = codigoGanador2;
	}
	public String getCodigoEmpate() {
		return codigoEmpate;
	}
	public void setCodigoEmpate(String codigoEmpate) {
		this.codigoEmpate = codigoEmpate;
	}
	public String getNombreGanador1() {
		return nombreGanador1;
	}
	public void setNombreGanador1(String nombreGanador1) {
		this.nombreGanador1 = nombreGanador1;
	}
	public String getNombreGanador2() {
		return nombreGanador2;
	}
	public void setNombreGanador2(String nombreGanador2) {
		this.nombreGanador2 = nombreGanador2;
	}
	public double getMultiplicadorGanador1() {
		return multiplicadorGanador1;
	}
	public void setMultiplicadorGanador1(double multiplicadorGanador1) {
		this.multiplicadorGanador1 = multiplicadorGanador1;
	}
	public double getMultiplicadorGanador2() {
		return multiplicadorGanador2;
	}
	public void setMultiplicadorGanador2(double multiplicadorGanador2) {
		this.multiplicadorGanador2 = multiplicadorGanador2;
	}
	public double getMultiplicadorEmpate() {
		return multiplicadorEmpate;
	}
	public void setMultiplicadorEmpate(double multiplicadorEmpate) {
		this.multiplicadorEmpate = multiplicadorEmpate;
	}
	public double getDiferencialMultiplicador() {
		return Math.abs(this.getMultiplicadorGanador2() - this.getMultiplicadorGanador1());
	}
	public double getMinGanancia() {
		
		if(this.multiplicadorGanador1 < this.multiplicadorGanador2) {
			return this.multiplicadorGanador1;
		} else {
			return this.multiplicadorGanador2;
		}
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String result;
		
		if(this.nombreLiga != null) {
			sb.append( this.nombreLiga );
			sb.append( " - " );
		}
		
		if(this.diaHoraJuego != null) {
			sb.append( ConstantesDeConfiguracion.dateFormatApp.format( this.diaHoraJuego.getTime() ));
			sb.append( "\n" );
		}
		
		if(this.codigoGanador1 != null) {
			sb.append( this.codigoGanador1 );
			sb.append( " - " );
		}
		
		if(this.nombreGanador1 != null) {
			sb.append( this.nombreGanador1 );
			sb.append( " - " );
		}
		
		if(this.multiplicadorGanador1 > 0.0) {
			sb.append( this.multiplicadorGanador1 );
			sb.append( "\n" );
		}
		
		if(this.codigoGanador2 != null) {
			sb.append( this.codigoGanador2 );
			sb.append( " - " );
		}
		
		if(this.nombreGanador2 != null) {
			sb.append( this.nombreGanador2 );
			sb.append( " - " );
		}
		
		if(this.multiplicadorGanador2 > 0.0) {
			sb.append( this.multiplicadorGanador2 );
			sb.append( "\n" );
		}
		
		if(this.codigoEmpate != null) {
			sb.append( this.codigoEmpate );
			sb.append( " - " );
		}
		
		if(this.multiplicadorEmpate > 0 ) {
			sb.append( this.multiplicadorEmpate );
		}
		
		sb.append( "\n" );
		
		result = sb.toString();
		sb = null;
		
		return result;
		
	}
	
	
}
