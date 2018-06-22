/**
 * @name - SnapshotUtil.java
 * @author - NNI
 * @date - 10-Jun-2018 4:18:20 PM
 */
package com.cast.tools.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SnapshotUtil {
	
	private static Logger logger = LogManager.getLogger(SnapshotUtil.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Starting to fetch snapshot details.");
		List<String> cssHostLists = PropertyUtil.fetchPropertyValues();
		File allServerReportCSV = new File("./CSS-AllServerReport.csv");
		if(!allServerReportCSV.exists()) {
			try {
				logger.info("Creating CSV report file : CSS-AllServerReport.csv");
				allServerReportCSV.createNewFile();
				allServerReportCSV.setWritable(true, false);
				logger.info("Created CSV report file : CSS-AllServerReport.csv");
				logger.info("Writing headers in : " + allServerReportCSV.getName());
				List<String> headers = new ArrayList<String>();
				headers.add("CSS_SERVER,SCHEMA,SNAPSHOT_ID,SNAPSHOT_NAME,SNAPSHOT_DATE");
				FileUtils.writeToCSVFile(headers, allServerReportCSV.getName());
				for(String cssHost : cssHostLists) {
					List<String> centralSchemaList = DBUtils.getListOfCentralSchema(cssHost);
					if((centralSchemaList != null) && (!centralSchemaList.isEmpty())) {
						List<String> snapshotDetails = DBUtils.getSnapShotDetails(cssHost, centralSchemaList);
						FileUtils.writeToCSVFile(snapshotDetails, allServerReportCSV.getName());
					}
				}
			} catch (IOException e) {
				logger.error("Error while creating file : " + allServerReportCSV.getName());
			}
		}
	}

}
