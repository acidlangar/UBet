package org.idsiom.utilbet.mail.pruebas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.idsiom.utilbet.currentuse.MainCurrentUseFromOP;

public class ConfigProperties {
		 
	public Properties getPropValues() throws IOException {
 
		Properties prop = new Properties();
		String propFileName =  MainCurrentUseFromOP.RUTA_ARCHIVO + "\\config.properties";
		
		File f = new File(propFileName);
		System.out.println("buscando archivo ... " + f.getAbsolutePath());
		
		if(f.exists()) {
			InputStream inputStream = new FileInputStream(f);
	 
			if (inputStream != null) {
				prop.load(inputStream);
			} 
			
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in " + f.getAbsolutePath());
		}
 
 
		return prop;
	}
	
}
