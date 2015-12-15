package org.idsiom.utilbet.analisis.interlocutor;

import java.util.List;

import org.idsiom.utilbet.analisis.bo.TRankingP;

public interface ISourceTables {
	public List<TRankingP> obtenerTs(String country);
}
