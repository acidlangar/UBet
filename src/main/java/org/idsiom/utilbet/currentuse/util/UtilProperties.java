package org.idsiom.utilbet.currentuse.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Session;

import org.idsiom.utilbet.mail.pruebas.ConfigProperties;

public class UtilProperties {
	private Properties properties;
	
	private Session session;
	
	private static UtilProperties instance;
	
	private UtilProperties() throws IOException {
		init();
	}
	
	public static UtilProperties getInstance() throws IOException {
		if(instance == null) {
			instance = new UtilProperties();
		}
		
		return instance;
	}
	
	public Session getSession() {
		return this.session;
	}

    public void init() throws IOException {
    	properties = new Properties();
       
    	ConfigProperties config = new ConfigProperties();
    	
		properties = config.getPropValues();
		
    	
    	session = Session.getDefaultInstance(properties);
    }
    
    public Properties getProp() {
    	return this.properties;
    }
	
}
