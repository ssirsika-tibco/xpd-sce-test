package com.tibco.xpd.core.createtestwizards.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.core.createtestwizards.CreateBaseTestPage;
import com.tibco.xpd.core.createtestwizards.generatordata.ValidationTestJavaClassGeneratorData;
import com.tibco.xpd.core.createtestwizards.generators.ValidationTestJavaClassGenerator;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * Sorter tool to manually sort a list of Validation Problem Marker Info for a
 * given test
 * 
 * TODO: The description of the Test gets lost in the migration
 * 
 * @author mtorres
 * 
 */
public class TestSorterMigrationTool {

	private AbstractBaseValidationTest testClassToMigrate;

	private String testName;

	private Class testSuperClass;

	private String testClassName;

	private String testPlugInId;

	private String packageId;

	private String baseResourcePath;

	String[] resourceInfoPathArray;

	String filePath;

	private String getTestName() {
		return testName;
	}

	public Class getTestSuperClass() {
		return testSuperClass;
	}

	public String getTestClassName() {
		return testClassName;
	}

	public String getTestPlugInId() {
		return testPlugInId;
	}

	public String getPackageId() {
		return packageId;
	}

	public String getBaseResourcePath() {
		return baseResourcePath;
	}

	public String[] getResourceInfoPathArray() {
		return resourceInfoPathArray;
	}

	public String getFilePath() {
		return filePath;
	}

	public TestSorterMigrationTool(
			AbstractBaseValidationTest testClassToMigrate, String testName,
			Class testSuperClass, String testClassName, String testPlugInId,
			String packageId, String[] resourceInfoPathArray,
			String baseResourcePath, String filePath) {
		this.testClassToMigrate = testClassToMigrate;
		this.testName = testName;
		this.testSuperClass = testSuperClass;
		this.testClassName = testClassName;
		this.testPlugInId = testPlugInId;
		this.packageId = packageId;
		this.resourceInfoPathArray = resourceInfoPathArray;
		this.baseResourcePath = baseResourcePath;
		this.filePath = filePath;
	}

	public void migrate() {
		String generateTestClassContent = generateTestClassContent(Arrays
				.asList(getValidationProblemMarkerInfos()));
		writeToFile(generateTestClassContent);
	}

	private String generateTestClassContent(
			List<ValidationsTestProblemMarkerInfo> markerInfosList) {

		ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[markerInfosList
				.size()];
		Collections.sort(markerInfosList);

		for (int i = 0; i < markerInfosList.size(); i++) {
			ValidationsTestProblemMarkerInfo validationsTestProblemMarkerInfo = markerInfosList
					.get(i);
			markerInfos[i] = validationsTestProblemMarkerInfo;
		}

		//
		// Create a class to pass all data to the BaseTestJavaClassGenerator.
		String testDescription = "";

		ValidationTestJavaClassGeneratorData generatorData = new ValidationTestJavaClassGeneratorData(
				getTestName(), getTestClassName(),
				testSuperClass.getSimpleName(), getTestSuperClass()
						.getPackage().getName(),
				CreateBaseTestPage.toJavaName(getTestClassName()),
				getTestPlugInId(), getPackageId(), getBaseResourcePath(),
				getResourceInfoPathArray(), markerInfos, true, testDescription);

		//
		// Generate the base test class.
		ValidationTestJavaClassGenerator baseTestClassGenerator = new ValidationTestJavaClassGenerator();

		String baseTestClass = baseTestClassGenerator.generate(generatorData);

		return baseTestClass;
	}

	private ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
		if (testClassToMigrate != null) {
			return testClassToMigrate.getSortedValidationProblemMarkerInfos();
		}
		System.out
				.println("No Validation Test Problem Marker Infos found to migrate");
		return new ValidationsTestProblemMarkerInfo[0];
	}

	private void writeToFile(String fileContent) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					getFilePath()));
			out.write(fileContent);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
