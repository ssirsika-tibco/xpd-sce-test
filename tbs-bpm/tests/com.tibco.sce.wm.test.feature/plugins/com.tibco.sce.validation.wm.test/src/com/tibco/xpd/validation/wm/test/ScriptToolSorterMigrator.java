package com.tibco.xpd.validation.wm.test;

import com.tibco.xpd.core.createtestwizards.tools.TestSorterMigrationTool;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseExtendedValidationTest;

public class ScriptToolSorterMigrator {

	private static String filePath = "C:\\src\\svn\\technical\\xpd\\trunk\\product\\tests\\com.tibco.xpd.wm.test.feature\\plugins\\com.tibco.xpd.validation.wm.test\\src\\com\\tibco\\xpd\\validation\\wm\\test"; //$NON-NLS-1$

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RQLScriptValidNavigationValidationTest testClassToMigrate = new RQLScriptValidNavigationValidationTest();
		TestResourceInfo[] testResources = testClassToMigrate
				.getTestResources();
		String baseResourcePath = null;
		String[] resourceInfoPathArray = new String[testResources.length];
		if (testResources.length > 0) {
			baseResourcePath = testResources[0]
					.getBaseTestPluginResourceFolder();
			for (int i = 0; i < testResources.length; i++) {
				resourceInfoPathArray[i] = testResources[i]
						.getBaseTokenisedPath();
			}
		}
		TestSorterMigrationTool mt = new TestSorterMigrationTool(
				testClassToMigrate, testClassToMigrate.getTestName(),
				AbstractRQLScriptValidatorTest.class,
				testClassToMigrate.getClass().getSimpleName(),
				testClassToMigrate.getTestPlugInId(),
				"com.tibco.xpd.validation.wm.test", resourceInfoPathArray, //$NON-NLS-1$
				baseResourcePath,
				filePath
						+ "\\" //$NON-NLS-1$
						+ testClassToMigrate.getClass().getSimpleName() + ".java"); //$NON-NLS-1$
		mt.migrate();
	}

}
