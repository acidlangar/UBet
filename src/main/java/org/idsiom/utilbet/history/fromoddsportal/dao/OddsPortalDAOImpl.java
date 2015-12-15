package org.idsiom.utilbet.history.fromoddsportal.dao;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.idsiom.utilbet.history.fromoddsportal.bo.POddsPortalBO;

/**
 *
 * @author isaac.ortiz
 */
public class OddsPortalDAOImpl implements IOddsPortalDAO {
	
	private static Logger logger = Logger.getLogger(OddsPortalDAOImpl.class);

    public void insertar(POddsPortalBO objIn, File fileOutput) throws java.sql.SQLException, Exception {
        // Construir el String para el llamado a la tabla espejo
        StringBuffer sb = new StringBuffer(1024);

        String sCallSP;

        sb.append("ins_partido ");
        sb.append("'");
        sb.append(objIn.getCountry());
        sb.append("', ");  // @pCountry VARCHAR(25), 
        sb.append("'");
                sb.append(objIn.getLeague());
                sb.append("', '");  // @pLeague VARCHAR(50),
        sb.append(objIn.getTemp());
        sb.append("', ");  // @pTemp VARCHAR(25), 

        sb.append("'");
        sb.append(objIn.getFechaFormatoBD());
        sb.append("', ");  // @pFecha SMALLDATETIME,

        sb.append("'");
        sb.append(objIn.getEqL());
        sb.append("', '");  // @pEqL VARCHAR(50),

        sb.append(objIn.getEqV());
        sb.append("', ");  // @pEqV VARCHAR(50),
        sb.append(objIn.getgL());
        sb.append(", ");  // @pGL INT,
        sb.append(objIn.getgV());
        sb.append(", '");  // @pGV INT,
        sb.append(objIn.getrStr());
        sb.append("', ");  // @pRStr VARCHAR(10),
        sb.append(objIn.getC1());
        sb.append(", ");  // @pC1 FLOAT,
        sb.append(objIn.getcX());
        sb.append(", ");  // @pCX FLOAT,

        sb.append(objIn.getC2());
        
        sCallSP = sb.toString();
        sb = null;

        //logger.error("EXECUTE @RC = [ID_BT].[dbo]." + sCallSP);
        
        escribir("EXECUTE @RC = [ID_BT].[dbo]." + sCallSP, fileOutput.getAbsolutePath());
        
        //ConexionBaseDatos.ejecutarProcedimiento(sCallSP);
    }

    public void escribir(String msj, String file) {
    	/** FORMA 1 DE ESCRITURA **/
    	BufferedWriter fichero = null;
    	try {

    		//fichero = new FileWriter(file);
    		fichero = new BufferedWriter(new FileWriter(file, true));

    		fichero.write(msj + "\n");
    		fichero.write("\n");
    		
    		fichero.close();

    	} catch (Exception ex) {
    		logger.error("Mensaje de la excepción: " + ex.getMessage(), ex);
    	}
	
    }

	public void escribirEncabezado(File fOut) {
		escribir("USE ID_BT\nGO", fOut.getAbsolutePath());
		
		escribir("DECLARE @RC int", fOut.getAbsolutePath());
		
	}
        
    /*
    public int cantidadArchivosProcesados() throws java.sql.SQLException, Exception {
        return ConexionBaseDatos.buscarCantidad("p_Aux_Banelco_cantArchivos");
    }

    public int cantidadRegistrosProcesados(String nombreArchivo, int ano) throws java.sql.SQLException, Exception {
         StringBuffer sb = new StringBuffer(256);
         

         sb.append("p_Aux_Banelco_cantRegs ");
         sb.append("'");
         sb.append(nombreArchivo);
         sb.append("', ");
         sb.append(ano);

         String query = sb.toString();
         sb = null;

         logger.debug( "El query a ejecutar es = " + query );
         
         return ConexionBaseDatos.buscarCantidad(query);


    }


	@Override
	public int fillSaldosAtm() throws SQLException, Exception {
		StringBuffer sb = new StringBuffer(256);
        

        sb.append("p_Aux_Banelco_fill_saldoatm ");
        
        String query = sb.toString();
        sb = null;

        logger.debug( "El query a ejecutar es = " + query );
        
        return ConexionBaseDatos.buscarCantidad(query);
	}


	@Override
	public int limpiezaInicial() throws SQLException, Exception {
		StringBuffer sb = new StringBuffer(256);
        
        sb.append("p_Aux_Banelco_del_preview ");
        
        String query = sb.toString();
        sb = null;

        logger.debug( "El query a ejecutar es = " + query );
        
        return ConexionBaseDatos.buscarCantidad(query);
	}

*/
	
}
