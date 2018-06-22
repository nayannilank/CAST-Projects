/**
 * @name - PropertyUtil.java
 * @author - NNI
 * @date - 10-Jun-2018 4:08:21 PM
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

public class PropertyUtil {
	private static Logger logger = LogManager.getLogger(PropertyUtil.class);
	
	private static String loadPropertyValues(){
		String propertyValueList = null;
		logger.info("Loading CSS Hosts list.");
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(PropertyUtil.class
				.getResourceAsStream("/CSSHostsList.csv"), StandardCharsets.UTF_8));
		try {
			for(String line; (line = fileReader.readLine())!= null; ){
				propertyValueList = line;
			}
			logger.info("Loaded CSS Hosts list : " + propertyValueList);
		} catch (IOException e) {
			logger.error("Error while loading CSSHostsList file " + e.getMessage());
		}
		return propertyValueList;
	}
	
	public static List<String> fetchPropertyValues() {
		List<String> tomcatInstallationsList = new ArrayList<String>();
		String installationList = loadPropertyValues();
		if(installationList != null){
			logger.info("Fetching CSS Hosts list from : " + installationList);
			tomcatInstallationsList = Arrays.asList(installationList.split(","));
		}
		return tomcatInstallationsList;
	}
}
