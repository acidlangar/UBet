package org.idsiom.utilbet.getinfo.file;

public interface InterpretadorLinea<ObjetoX> {
	public void interpretarLinea(String strLine);
	
	public ObjetoX getResultadoInterpretacion();
}
