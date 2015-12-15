package org.idsiom.utilbet.history.fromoddsportal.dao;


import java.sql.*;

import org.apache.log4j.Logger;


/**
 * Clase AccesoBD. Representa un objeto que realiza la conexi�n a la Base de Datos.
 *Esta clase esta basada en los paquetes para la conexi�n a la base de datos provisto por el IDS Server (java2 e ids).
 *Dichos paquetes contienen una colecci�n de clases "pure java" basadas en el jdbc de sun.
 * @Since (9/20/00 11:56:28 AM)
 * @author Carlos Moh, Fabiola
 */
public class AccesoBD {
    private String esquemaBD = "";
    public java.sql.Connection fieldConexionDB = null;
    private String fieldDriverDB = null;
    private String fieldUrlDB = null;
    private String fieldUsuarioDB = null;
    private String fieldClaveDB = null;
    private int  fieldTipoDriverDB = 0;
    private boolean bSalidaPorConsola = false;
    public static final int JDBC = 0;
    public static final int JTURBO = 1;
    public static final int IDS = 2;
    public static final int IDS_ORACLE = 3;
    public static final String SQLSERVER = "SQLSERVER";
    public static final String ORACLE = "ORACLE";
    public static final int EXECUTE_QUERY = 0;
    public static final int EXECUTE_SP_RESULTSET = 1;
    public static final int EXECUTE_SP_MULTIRESULTSET = 2;
    public static final int EXECUTE_SP_BOOLEAN = 3;
    public java.lang.Object objExcepcion = null;
    private CallableStatement objStm;
    private String tipoBaseDatos;
    
    private static Logger logger = Logger.getLogger(AccesoBD.class);
    
    
    public AccesoBD() {
        super();
        
    }
    
    //-------------------------------->
    // Getter y Setters
    //-------------------------------->

    public Connection getConnection() {
        return fieldConexionDB;
    }

    public java.lang.String getDriver() {
        return fieldDriverDB;
    }

    public int getFieldTipoDriverDB() {
        return fieldTipoDriverDB;
    }

    public  java.sql.PreparedStatement getStatement(String sentencia) throws java.sql.SQLException {
        return fieldConexionDB.prepareStatement(sentencia);
    }

    public java.lang.String getURL() {
        return fieldUrlDB;
    }

    public java.lang.String getUserID() {
        return fieldUsuarioDB;
    }

    public void setDriver(java.lang.String value) {
        this.fieldDriverDB = value;
    }

    public void setFieldTipoDriverDB(int newFieldTipoDriverDB) {
        fieldTipoDriverDB = newFieldTipoDriverDB;
    }

    public void setPassword(java.lang.String value) {
        this.fieldClaveDB = value;
    }

    public void setURL(java.lang.String value) {
        this.fieldUrlDB = value;
    }
    
    public void setUserID(java.lang.String value) {
        this.fieldUsuarioDB = value;
    }
    
    public void setEsquemaBD(String e) {
        this.esquemaBD = e;
    }
    
    public String getEsquemaBD() {
        return this.esquemaBD;
    }
    
    public String getTipoBaseDatos() {
        return tipoBaseDatos;
    }
    
    public void setTipoBaseDatos(String tipoBaseDatos) {
        this.tipoBaseDatos = tipoBaseDatos;
    }

    //-------------------------------->
    // Metodos Accesorios de la Clase
    //-------------------------------->

    public String toString() {
        return (this.fieldDriverDB + "-" + this.fieldUrlDB+ "-" + fieldUsuarioDB + "-" +fieldClaveDB);
    }
    
    public void systemOut(Object objString) {
        if (bSalidaPorConsola) {
            logger.debug("Clase AccesoBD: \t\t" + objString);
        }
    }
    
    public void error(String descripcion, java.lang.Exception e) {
        systemOut(descripcion + e);
    }
    
    public void setBSalidaPorConsola(boolean b) {
        this.bSalidaPorConsola = b;
    }
    
    //-------------------------------->
    // Metodos de Manejo de las Conexiones
    //-------------------------------->

    public boolean obtenerConexion() {
        String sURL_BD = fieldUrlDB;
        
        try {
            //-------------------------------->
            // Instanciacion del Driver JTDS
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            //-------------------------------->
            // Validamos si se necesita un dominio para la conexion
            //if(fieldDominioDB != null) {
            //    sURL_BD = sURL_BD + ";domain=" + fieldDominioDB;
            //}

            //-------------------------------->
            // Creacion de la conexion fisica a la BD
            fieldConexionDB = DriverManager.getConnection(sURL_BD);

            //-------------------------------->            
            //verifica si se estableci� la conexi�n con la base de datos
            if (fieldConexionDB == null) {
                systemOut("La conexion no pudo ser establecida");
                return false;
            } else {
                systemOut("Termin� de Realizar la Conexi�n con la Base de Datos " );
            }
            
            //-------------------------------->
            getConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            getConnection().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            error("obtenerConexionBD - ",e);
            
            logger.error(e,e);
            
            return false;
        } catch (Error e) {
            e.printStackTrace();
            
            logger.error(e,e);
            
            systemOut("obtenerConexionBD: " + e);
            systemOut("obtenerConexionBD: " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public void setAutoCommit(boolean b) {
        try {
            this.getConnection().setAutoCommit(b);
        } catch (Exception e) {
            systemOut("Exception al configurar la transacci�n: " + e);
            liberarConexion();
            obtenerConexion();
            try {
                this.getConnection().setAutoCommit(b);
            } catch(java.sql.SQLException ex) {
                systemOut("fue imposible configurar la transacci�n: " + ex);
            }
        } catch (Error e) {
            systemOut("Error al configurar la transacci�n: " + e);
            liberarConexion();
            obtenerConexion();
            try {
                this.getConnection().setAutoCommit(b);
            } catch(java.sql.SQLException ex) {
                systemOut("fue imposible configurar la transacci�n: " + ex);
            }
        }
    }
    
    public boolean cerrarStatement() {
        try {
            objStm.close();
            systemOut("Statement cerrado");
            objExcepcion = null;
        } catch (Exception e1) { // dado que no puedo cerrar el statement, me reconecto
            systemOut("ERROR CERRANDO STATEMENT:" + e1);
            liberarConexion();
            return (obtenerConexion());
        }
        return true;
    }

    public void liberarConexion() {
        try {
            fieldConexionDB.close();
            systemOut("Liberando Conexion");
        } catch (java.lang.Exception e) {
            systemOut("liberarConexion - Error en desconexi�n");
        }
        return;
    }
    
    //-------------------------------->
    // Metodos de ajuste de Procedimientos
    //-------------------------------->
    
    private String ajustarStoreProcedure(String sProcedure) {
        int iPos = sProcedure.indexOf( " ");
        String sSP = "";
        String sName = sProcedure.substring(0,iPos); //nombre
        String sParametros = sProcedure.substring(iPos+1); //parametros

        //-------------------------------->
        if (!sParametros.equalsIgnoreCase("")) { //Hay parametros
            sSP = "{call " + sName + "(" + sParametros + ")}";
        } else {
            sSP = "{call " + sName + "}";
        }
        
        //-------------------------------->
        systemOut(sSP);
        return sSP;
    }
    
    private String ajustarStoreProcedure(String paquete, String sProcedure) {
        return this.ajustarStoreProcedure(sProcedure);
    }

    //-------------------------------->
    // Metodos Publicos de ejecucion de Procedimientos
    //-------------------------------->
    /*
    public boolean ejecutarSP(int tipo, String sprocedimiento, Vector vretorno){
            return this.boolExecuteSP(tipo, sprocedimiento, vretorno);
    }
    */
    
    public boolean ejecStoredProc2(String paquete, String stpr){
            return this.boolExecuteStoredProcedure(paquete,stpr);
    }
    
    public ResultSet ejecStoredProc(String paquete, String stpr){
            return this.rsExecuteStoredProcedure(paquete,stpr);
    }
    
    public String ejecStoredProcStr(String paquete, String stpr){
            return this.strExecuteStoredProcedure(paquete,stpr);
    }
    
    //-------------------------------->
    // Metodos Privados de ejecucion de Procedimientos
    //-------------------------------->
/*
    private boolean boolExecuteSP(int tipo, String sprocedimiento, Vector vretorno) {
        ResultSet rs = null;
        if  (tipo == EXECUTE_QUERY) {
            rs = ejecquery(sprocedimiento);
            vretorno.addElement(rs);
            return true;
        }
        if  (tipo == EXECUTE_SP_RESULTSET) {
            rs = ejecStoredProc("",sprocedimiento);
            vretorno.addElement(rs);
            return true;
        }
        if 	(tipo == EXECUTE_SP_MULTIRESULTSET) {
            Vector vdatos = null;
            vdatos = obtenerResultSets(sprocedimiento);
            for (int i=0;(i<vdatos.size());i++) {
                vretorno.addElement(vdatos.elementAt(i));
            }
            return true;
        }
        if (tipo == EXECUTE_SP_BOOLEAN) {
            return ejecStoredProc2("",sprocedimiento);
        }
        return false;
    }
    */
    private ResultSet rsExecuteStoredProcedure(String paquete, String stpr) {
        boolean bEjecutar = true;
        int iIntento = 0;
        ResultSet rs = null;
        while (bEjecutar && iIntento < 2) {
            try {
                systemOut("storeprocedure: " + stpr);
                if ((iIntento == 0) && (fieldTipoDriverDB == IDS_ORACLE)) {
                    int iPos = stpr.indexOf(" ");
                    String sParametros = stpr.substring(iPos+1); //parametros
                    if (sParametros.trim().equalsIgnoreCase("")) {
                        stpr += " ?,?";
                    } else {
                        stpr += ",?,?";
                    }
                }
                CallableStatement cs = fieldConexionDB.prepareCall(ajustarStoreProcedure(paquete,stpr));
                boolean query = false;
                if (fieldTipoDriverDB == IDS_ORACLE) {
                    cs.registerOutParameter(2, java.sql.Types.VARCHAR);
                    objStm = cs;
                    query = cs.execute();
                    //verifica si ocurri� error
                    String sError = null;
                    sError = cs.getString(2);
                    if (sError == null) {
                        systemOut("el parametro Error es nulo. No retorno error");
                    } else {
                        systemOut("Error retornado: "+ sError);
                        return null;
                    }
                } else { // para SQLSERVER
                    objStm = cs;
                    query = cs.execute();
                }
                
                if (query) {
                    rs = cs.getResultSet();
                } else {
                    rs = getNextResultSet();
                }
                
                objExcepcion = null;
                bEjecutar = false;
            } catch (java.sql.SQLException e) {
                error("ejectStoredProc - Error de Base de Datos: " + e.getErrorCode(), e);
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                if (e.getErrorCode() == 0) {
                    //error en la conexion de red
                    try {
                        boolean bEnabled = getConnection().getAutoCommit();
                        if (bEnabled) {
                            //me reconecto e intento de nuevo la operacion
                            liberarConexion();
                            obtenerConexion();
                            getConnection().setAutoCommit(bEnabled);
                        }
                    } catch (Exception e1) {
                        error("ejectStoredProc: ", e1);
                        objExcepcion = e;
                        return null;
                    }
                } else if (e.getErrorCode() == 1222) {
                    systemOut("locktimeOut error");
                } else {
                    objExcepcion = e;
                    return null;
                }
            } catch (Exception e) {
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                error("ejectStoredProc: ", e);
                objExcepcion = e;
                return null;
            } catch (Error e) {
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                systemOut("ejectStoredProc: " + e);
                objExcepcion = e;
                return null;
            }
            iIntento++;
            
        }//while
        return rs;
    }
    
    private boolean boolExecuteStoredProcedure(String paquete, String stpr) {
        boolean bEjecutar = true;
        int iIntento = 0;
        
        while (bEjecutar && iIntento < 2) {
            try {
                systemOut("storeprocedure: " + stpr);
                if ((iIntento == 0) && (fieldTipoDriverDB == IDS_ORACLE)) {
                    int iPos = stpr.indexOf(" ");
                    String sParametros = stpr.substring(iPos+1); //parametros
                    if (sParametros.trim().equalsIgnoreCase("")) {
                        stpr += " ?";
                    } else {
                        stpr += ",?";
                    }
                }
                CallableStatement cs = fieldConexionDB.prepareCall(ajustarStoreProcedure(paquete,stpr));
                if (fieldTipoDriverDB == IDS_ORACLE) {
                    cs.registerOutParameter(1, java.sql.Types.VARCHAR);
                    cs.execute();
                    //verifica si ocurri� alg�n error
                    String sError = null;
                    sError = cs.getString(1);
                    if (sError == null) {
                        systemOut("el parametro Error es nulo. No retorno error");
                    } else {
                        systemOut("Error retornado: "+ sError);
                        cs.close();
                        return false;
                    }
                } else {
                    cs.execute();
                }
                cs.close();
                objExcepcion = null;
                return true;
            } catch (java.sql.SQLException e) {
                error("ejectStoredProc - Error de Base de Datos: " + e.hashCode(), e);
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                if (e.getErrorCode() == 0) {
                    //error en la conexion de red
                    try {
                        boolean bEnabled = getConnection().getAutoCommit();
                        if (bEnabled) {
                            //me reconecto e intento de nuevo la operacion
                            liberarConexion();
                            obtenerConexion();
                            getConnection().setAutoCommit(bEnabled);
                        }
                    } catch (Exception e1) {
                        error("ejectStoredProc: ", e1);
                        objExcepcion = e;
                        return false;
                    }
                } else if (e.getErrorCode() == 1222) {
                    systemOut("locktimeOut error");
                    
                } else {
                    objExcepcion = e;
                    return false;
                }
            } catch (Exception e) {
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                error("ejectStoredProc: ", e);
                objExcepcion = e;
                return false;
            } catch (Error e) {
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                systemOut("ejectStoredProc: " + e);
                objExcepcion = e;
                return false;
            }
            iIntento++;
        }//while
        return false;
    }
    
    private String strExecuteStoredProcedure(String paquete, String stpr) {
        boolean bEjecutar = true;
        int iIntento = 0;
        String retorno = "false";
        
        while (bEjecutar && iIntento < 2) {
            try {
                systemOut("storeprocedure: " + stpr);
                if ((iIntento == 0) && (fieldTipoDriverDB == IDS_ORACLE)) {
                    int iPos = stpr.indexOf(" ");
                    String sParametros = stpr.substring(iPos+1); //parametros
                    if (sParametros.trim().equalsIgnoreCase("")) {
                        stpr += " ?";
                    } else {
                        stpr += ",?";
                    }
                }
                CallableStatement cs = fieldConexionDB.prepareCall(ajustarStoreProcedure(paquete,stpr));
                if (fieldTipoDriverDB == IDS_ORACLE) {
                    cs.registerOutParameter(1, java.sql.Types.VARCHAR);
                    cs.execute();
                    //verifica si ocurri� alg�n error
                    String sError = null;
                    sError = cs.getString(1);
                    if (sError == null) {
                        systemOut("el parametro Error es nulo. No retorno error");
                    } else {
                        systemOut("Error retornado: "+ sError);
                        cs.close();
                        return retorno;
                    }
                } else {
                    cs.execute();
                }
                cs.close();
                objExcepcion = null;
                return "";
            } catch (java.sql.SQLException e) {
                
                error("ejectStoredProc Prueba - Error de Base de Datos: " + e.hashCode(), e);
                
                
                //A2C Incidencia estado de envio
                String str = e.toString();
                int pos = str.indexOf("@@");
                
                
                String strError = e.toString();
                String codError = strError.substring(pos);
                
                
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                if (e.getErrorCode() == 0) {
                    //error en la conexion de red
                    try {
                        boolean bEnabled = getConnection().getAutoCommit();
                        if (bEnabled) {
                            //me reconecto e intento de nuevo la operacion
                            liberarConexion();
                            obtenerConexion();
                            getConnection().setAutoCommit(bEnabled);
                        }
                    } catch (Exception e1) {
                        error("ejectStoredProc: ", e1);
                        objExcepcion = e;
                        return retorno;
                    }
                } else if (e.getErrorCode() == 1222) {
                    systemOut("locktimeOut error");
                } else if (codError.equalsIgnoreCase("@@60027")) {
                    systemOut("Error @@60027");
                    return codError;
                } else if (codError.equalsIgnoreCase("@@60028")) {
                    systemOut("Error @@60028");
                    return codError;
                } else if (codError.equalsIgnoreCase("@@60045")) {
                    systemOut("Error @@60045");
                    return codError;
                } else if (codError.equalsIgnoreCase("@@60046")) {
                    systemOut("Error @@60046");
                    return codError;
                }else {
                    objExcepcion = e;
                    return retorno;
                }
            } catch (Exception e) {
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                error("ejectStoredProc: ", e);
                objExcepcion = e;
                return retorno;
            } catch (Error e) {
                //se debe cerrar el statement de todos modos
                cerrarStatement();
                systemOut("ejectStoredProc: " + e);
                objExcepcion = e;
                return retorno;
            }
            iIntento++;
        }//while
        return retorno;
    }

    /*
    private ResultSet ejecquery(String query) {
        ResultSet rs = null;
        PreparedStatement p = null;
        systemOut("query: " + query);
        try {
            p= getStatement(query);
            rs = p.executeQuery();
            objExcepcion = null;
        } catch (java.sql.SQLException e) {
            //se debe cerrar el statement de todos modos
            cerrarStatement();
            error("EjectQuery - Error de Base de Datos: ", e);
            objExcepcion = e;
            return null;
        } catch (Exception e) {
            //se debe cerrar el statement de todos modos
            cerrarStatement();
            error("EjectQuery : ", e);
            objExcepcion = e;
            return null;
        } catch (Error e) {
            //se debe cerrar el statement de todos modos
            cerrarStatement();
            systemOut("EjectQuery : " + e);
            objExcepcion = e;
        }
        return rs;
    }
    */
    private ResultSet getNextResultSet() {
        ResultSet rs = null;
        try {
            boolean bQuery = objStm.getMoreResults();
            int iCount = objStm.getUpdateCount();
            while (iCount >= 0) {
                bQuery = objStm.getMoreResults();
                iCount = objStm.getUpdateCount();
            }
            if (bQuery) {
                rs = objStm.getResultSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return rs;
    }

    /*
    private Vector obtenerResultSets(String stpr) {
        Vector vResultSets = null;
        return vResultSets;
    }
    */

}
