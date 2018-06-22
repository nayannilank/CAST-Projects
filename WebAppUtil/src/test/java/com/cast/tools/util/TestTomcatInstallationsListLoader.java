/**
 * @name - TestTomcatInstallationsListLoader.java
 * @author - NNI
 * @date - 07-Jun-2018 2:59:00 PM
 */
package com.cast.tools.util;

import org.testng.annotations.Test;

public class TestTomcatInstallationsListLoader {
	
	@Test
	public static void testFetchTomcatInstallationsList() {
		PropertyLoader.fetchPropertyValues();
	}

}
