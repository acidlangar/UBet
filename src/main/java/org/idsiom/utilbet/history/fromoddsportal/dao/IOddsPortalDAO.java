package org.idsiom.utilbet.history.fromoddsportal.dao;

import java.io.File;

import org.idsiom.utilbet.history.fromoddsportal.bo.POddsPortalBO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author isaac.ortiz
 */
public interface IOddsPortalDAO {

	public void escribir(String msj, String file);
	
	public void insertar(POddsPortalBO objIn, File fileInput) throws java.sql.SQLException, Exception;

	public void escribirEncabezado(File fOut);

}
