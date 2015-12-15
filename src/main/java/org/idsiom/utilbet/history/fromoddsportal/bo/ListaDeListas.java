package org.idsiom.utilbet.history.fromoddsportal.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaDeListas implements Serializable {
	private static final long serialVersionUID = 4534799893773626835L;
	
	private List<ListaPs> list = new ArrayList<ListaPs>();

	public List<ListaPs> getList() {
		return list;
	}

	public void setList(List<ListaPs> list) {
		this.list = list;
	}
	
	
	
	
}
