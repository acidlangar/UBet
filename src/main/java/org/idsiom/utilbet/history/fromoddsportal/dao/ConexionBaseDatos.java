package org.idsiom.utilbet.history.fromoddsportal.dao;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;


/**
 *
 * @author isaac.ortiz
 */
public class ConexionBaseDatos {

    static Logger logger = Logger.getLogger(ConexionBaseDatos.class);

    private static AccesoBD baseDeDatos = new AccesoBD();

    private static Boolean conexionActiva = false;

    public static void abrirConexion(String servidor, String puerto, String usuarioConexion, String passwordConexion) throws Exception {
        //PropertyConfigurator.configure("log4j.properties");

        String sURL_DBServer;

        // Version Tradicional
        //sURL_DBServer = "jdbc:jtds:sqlserver://172.28.195.143:7427/COE_EFECTIVO;loginTimeout=60;socketTimeout=60;appName=COEAGENCIA;instance=prod";
        //sURL_DBServer = "jdbc:jtds:sqlserver://172.28.195.152:1433/COE_EFECTIVO;loginTimeout=60;socketTimeout=60;appName=COEAGENCIA";
        sURL_DBServer = "jdbc:jtds:sqlserver://*1:*2/ID_BT;instance=SQLEXPRESS;user=*3;password=*4";
    
        sURL_DBServer = sURL_DBServer.replace("*1", servidor);
        sURL_DBServer = sURL_DBServer.replace("*2", puerto);
        sURL_DBServer = sURL_DBServer.replace("*3", usuarioConexion);
        sURL_DBServer = sURL_DBServer.replace("*4", passwordConexion);
        
        baseDeDatos.setURL(sURL_DBServer);
        baseDeDatos.setTipoBaseDatos(AccesoBD.SQLSERVER);
        //baseDeDatos.setUserID();
        //baseDeDatos.setPassword();

        conexionActiva = baseDeDatos.obtenerConexion();

        if(!conexionActiva)
           throw new Exception("Falla al abrir Conexion");
    }

    public static void cerrarConexion() throws SQLException {
        
        baseDeDatos.fieldConexionDB.close();
        
        baseDeDatos.fieldConexionDB = null;

    }

    public static void ejecutarProcedimiento(String strParaEjecutar) throws SQLException, Exception {
        ResultSet rs = null;
        rs = baseDeDatos.ejecStoredProc("PACK_PASEBOVEDA", strParaEjecutar);

        if(rs == null) {
            throw new Exception("El ResultSet es NULL - query = " + strParaEjecutar);
        }

        if (rs.next()) {
                try {
                    if (rs.getInt("Exito") != 1) {
                        logger.debug("Ocurrio un problema inesperado");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                logger.debug("Ocurrio un problema inesperado 2");
            }
        
    }


    public static int buscarCantidad(String strParaEjecutar) throws SQLException, Exception {
        int cantidad = 0;
        ResultSet rs = null;
        rs = baseDeDatos.ejecStoredProc("PACK_PASEBOVEDA", strParaEjecutar);

        if(rs == null) {
            throw new Exception("El ResultSet es NULL - query = " + strParaEjecutar);
        }

        if (rs.next()) {
                try {
                    cantidad = rs.getInt("Cantidad");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                logger.debug("Ocurrio un problema inesperado 2");
            }

       return cantidad;
    }


   


}
