/**
 * @name - LocationListLoader.java
 * @author - NNI
 * @date - 07-Jun-2018 11:42:55 am
 */
package com.cast.tools.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PropertyLoader {
	private static Logger logger = LogManager.getLogger(PropertyLoader.class);
	
	private static String loadPropertyValues(){
		String propertyValueList = null;
		logger.info("Loading Tomcat installations list.");
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(PropertyLoader.class
				.getResourceAsStream("/TomcatInstallationsList.csv"), StandardCharsets.UTF_8));
		try {
			for(String line; (line = fileReader.readLine())!= null; ){
				propertyValueList = line;
			}
			logger.info("Loaded tomcat installation list : " + propertyValueList);
		} catch (IOException e) {
			logger.error("Error while loading TomcatInstallationsList file " + e.getMessage());
		}
		return propertyValueList;
	}
	
	public static List<String> fetchPropertyValues() {
		List<String> tomcatInstallationsList = new ArrayList<String>();
		String installationList = loadPropertyValues();
		if(installationList != null){
			logger.info("Fetching installations lists from : " + installationList);
			tomcatInstallationsList = Arrays.asList(installationList.split(","));
		}
		return tomcatInstallationsList;
	}
}
