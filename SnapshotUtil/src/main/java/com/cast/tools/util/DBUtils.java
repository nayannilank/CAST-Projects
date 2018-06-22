/**
 * @name - DBUtils.java
 * @author - NNI
 * @date - 09-Jun-2018 4:05:13 PM
 */
package com.cast.tools.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class DBUtils {
	private static Logger logger = Logger.getLogger(DBUtils.class);
	private static final String FETCH_CENTRALS_QUERY = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA "
			+ "WHERE SCHEMA_NAME LIKE '%_central' ORDER BY SCHEMA_NAME";
	
	private static Connection connectDB(String cssHost) {
		logger.info("Connecting to CSS Server : " + cssHost);
		Connection dbConn = null;
		try {
			Class.forName("org.postgresql.Driver");
			String dbURL = "jdbc:postgresql://" + cssHost + ":2280/postgres?user=operator&password=CastAIP&ssl=false";
			logger.debug("URL to connect to DB : " + dbURL);
			dbConn = DriverManager.getConnection(dbURL);
		} catch (ClassNotFoundException e) {
			logger.error("Could not load driver for postgresql : " + e.getLocalizedMessage());
		} catch (SQLException e) {
			logger.error("Could not connect to the db with given credentials : " + e.getLocalizedMessage());
		}
		return dbConn;
	}
	
	private static void disconnectDB(Connection dbConn) {
		try {
			if ((dbConn != null) && (!dbConn.isClosed())) {
				logger.info("Closing DB connection.");
				dbConn.close();
			}
		} catch (SQLException e) {
			logger.error("Error while closing DB Connection : " + e.getLocalizedMessage());
		}
	}
	
	public static List<String> getListOfCentralSchema(String cssHost) {
		List<String> centralSchemaList = new ArrayList<String>();
		Connection dbConn = connectDB(cssHost);
		if(dbConn != null) {
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = dbConn.createStatement();
				rs = stmt.executeQuery(FETCH_CENTRALS_QUERY);
				while(rs.next()) {
					centralSchemaList.add(rs.getString("SCHEMA_NAME"));
				}
			} catch (SQLException e) {
				logger.error("Error while fetching central schema list : " + e.getLocalizedMessage());
			} finally {
				try {
					if(stmt != null) {
						stmt.close();
					}
					if(rs != null) {
						rs.close();
					}
				} catch (SQLException e) {
					logger.error("Error while closing statement or resultset : " + e.getMessage());
				}
			}
		}
		logger.info("Total number of central schema on " + cssHost + " server : " + centralSchemaList.size());
		return centralSchemaList;
	}
	
	public static List<String> getSnapShotDetails(String cssHost, List<String> centralSchemaList){
		List<String> snapshotDetails = new ArrayList<String>();
		Connection dbConn = connectDB(cssHost);
		Statement stmt = null;
		ResultSet rs = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		if(dbConn != null) {
			for(String centralSchema : centralSchemaList) {
				logger.info("Fetching latest snapshot details for schema : " + centralSchema);
				try {
					stmt = dbConn.createStatement();
					String snapshotQuery = "SELECT SNAPSHOT_ID, SNAPSHOT_NAME, SNAPSHOT_DATE FROM " 
							+ centralSchema	+ ".DSS_SNAPSHOTS ORDER BY SNAPSHOT_DATE DESC LIMIT 1";
					logger.debug("Snapshot fetch query : " + snapshotQuery);
					rs = stmt.executeQuery(snapshotQuery);
					while(rs.next()) {
						snapshotDetails.add(cssHost.substring(0, cssHost.indexOf(".")) + "," + centralSchema + "," 
								+ rs.getInt("SNAPSHOT_ID") + "," + rs.getString("SNAPSHOT_NAME") + "," 
								+ formatter.format(rs.getTimestamp("SNAPSHOT_DATE")));
					}
				} catch (SQLException e) {
					logger.error("Error while fetching snapshot details for schema (" + centralSchema + ") : " 
							+ e.getLocalizedMessage());
				}
			}
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing statement or resultset : " + e.getMessage());
			} finally {
				try {
					if((stmt != null) && (!stmt.isClosed())) {
						stmt.close();
					}
					if((rs != null) && (!rs.isClosed())) {
						rs.close();
					}
				} catch (SQLException e) {
					logger.error("Error while closing statement or resultset : " + e.getMessage());
				}
			}
			disconnectDB(dbConn);
		}
		return snapshotDetails;
	}
}
