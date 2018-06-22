/**
 * @name - WebAppUtil.java
 * @author - NNI
 * @date - 07-Jun-2018 6:10:15 PM
 */
package com.cast.tools.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class WebAppUtil {

	private static Logger logger = Logger.getLogger(WebAppUtil.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Fetching installation locations.");
		List<String> tomcatInstallationLocations = PropertyLoader.fetchPropertyValues();
		for (String tomcatInstallation : tomcatInstallationLocations) {
			logger.info("Creating file for : " + tomcatInstallation);
			File fileListCSV = new File("./" + tomcatInstallation.substring(tomcatInstallation.lastIndexOf("\\") + 1)
									.replace(" ", "_") + ".csv");
			if(!fileListCSV.exists()) {
				try {
					fileListCSV.createNewFile();
					fileListCSV.setWritable(true, false);
					if(fileListCSV.canWrite()) {
						logger.info("Writing headers in : " + fileListCSV.getName());
						List<String> header = new ArrayList<String>();
						header.add("File Name,Modify Date,Type");
						FileUtils.writeToCSVFile(header, fileListCSV.getName());
						logger.info("Writing directory list in : " + fileListCSV.getName());
						FileUtils.writeToCSVFile(getListOfDirs(tomcatInstallation + "\\webapps"), fileListCSV.getName());
						logger.info("Writing war list in : " + fileListCSV.getName());
						FileUtils.writeToCSVFile(getListOfFiles(tomcatInstallation + "\\webapps"), fileListCSV.getName());
					} else {
						logger.error("File " + fileListCSV.getName() + " is not writable.");
					}
				} catch (IOException e) {
					logger.error("Error while creating file " + fileListCSV.getName() + ", error - " + e.getMessage());
				}				
			}
		}
		
	}
	
	private static List<String> getListOfDirs (String path) {
		List<String> listOfDirs = new ArrayList<String>();
		File baseDir = new File(path);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		for(File dir : baseDir.listFiles()) {
			if(dir.isDirectory()) {
				
				listOfDirs.add((new WebappDetailBean(dir.getName(), formatter.format(dir.lastModified()), "Dir")).toString());
			}
		}
		return listOfDirs;
	}
	
	private static List<String> getListOfFiles (String path) {
		List<String> listOfFiles = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		File baseDir = new File(path);
		for(File file : baseDir.listFiles()) {
			if(file.isFile()) {
				listOfFiles.add(new WebappDetailBean(file.getName(), formatter.format(file.lastModified()), 
						file.getName().substring(file.getName().lastIndexOf(".") + 1)).toString());
			}
		}
		return listOfFiles;
	}
	
	private static List<WebappDetailBean> fetchDeployedJarDetails(List<String> warsList) {
		List<WebappDetailBean> warDetails = new ArrayList<WebappDetailBean>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		for(String war : warsList) {
			File warFile = new File(war);
			if (warFile.exists()) {
				warDetails.add(new WebappDetailBean(warFile.getName(), formatter.format(warFile.lastModified()), "war"));
			}
		}
		return warDetails;
	}

}
