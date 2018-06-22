/**
 * @name - FileUtils.java
 * @author - NNI
 * @date - 07-Jun-2018 9:22:10 PM
 */
package com.cast.tools.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtils {
	private static Logger logger = Logger.getLogger(FileUtils.class);
	
	public static void writeToCSVFile(List<String> values, String csvFileName) {
		PrintWriter csvWriter = null;
		try {
			csvWriter = new PrintWriter(new BufferedWriter(new FileWriter(csvFileName, true)));
			for(String value : values) {
				csvWriter.println(value);
			}
		} catch (IOException e) {
			logger.error("Error while writing to file.");
			logger.error(e.getLocalizedMessage());
		}finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
	}
}
