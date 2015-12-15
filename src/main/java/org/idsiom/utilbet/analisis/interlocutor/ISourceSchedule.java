package org.idsiom.utilbet.analisis.interlocutor;

import java.util.List;

import org.idsiom.utilbet.analisis.bo.POPScheduleBO;

public interface ISourceSchedule {
	public List<POPScheduleBO> getNextSchedule(String country, String lig) throws Exception;
}
