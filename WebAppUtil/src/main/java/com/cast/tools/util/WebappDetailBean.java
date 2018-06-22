/**
 * @name - JarDetailBean.java
 * @author - NNI
 * @date - 07-Jun-2018 6:30:16 PM
 */
package com.cast.tools.util;

import java.util.Date;

public class WebappDetailBean {
	
	private String webAppName;
	private String webAppModifyDate;
	private String type;
	
	public WebappDetailBean(String jarName, String jarModifyDate, String type) {
		this.webAppName = jarName;
		this.webAppModifyDate = jarModifyDate;
		this.type = type;
	}

	public String getJarName() {
		return webAppName;
	}
	
	public void setJarName(String jarName) {
		this.webAppName = jarName;
	}
	
	public String getJarModifyDate() {
		return webAppModifyDate;
	}
	
	public void setJarModifyDate(String jarModifyDate) {
		this.webAppModifyDate = jarModifyDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return webAppName + "," + webAppModifyDate + "," + type;
	}
	
	
}
