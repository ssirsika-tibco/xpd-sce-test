/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.validationtestwizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IMarkerResolution;

import com.tibco.xpd.core.createtestwizards.CreateBaseTestPage;
import com.tibco.xpd.core.createtestwizards.CreateBaseTestWizard;
import com.tibco.xpd.core.createtestwizards.generatordata.ValidationTestJavaClassGeneratorData;
import com.tibco.xpd.core.createtestwizards.generators.ValidationTestJavaClassGenerator;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.validation.provider.IIssue;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class CreateValidationTestWizard extends CreateBaseTestWizard {

    protected CreateValidationTestPage validationTestPage;

    public CreateValidationTestWizard(
            Collection<IResource> selectedStudioResources) {
        super(selectedStudioResources);
        setWindowTitle(Messages.CreateValidationTestWizard_CreateValidationTest_title);
    }

    @Override
    public void addPages() {
        super.addPages();

        validationTestPage =
                new CreateValidationTestPage(selectedStudioResources);

        addPage(validationTestPage);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.core.createtestwizards.CreateBaseTestWizard#
     * generateTestClassContent(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, org.eclipse.core.runtime.IPath,
     * java.util.Collection)
     */
    @Override
    protected String generateTestClassContent(String testName,
            String testClassName, String testPackageId, String testPluginId,
            IPath baseTestPluginResourcePath,
            Collection<String> testResourceInfoPaths) {

        /*
         * Collect all the selected problem markers and their quick fixes into a
         * list of validation marker infos.
         */
        List<IMarker> selectedMarkers =
                validationTestPage.getSelectedProblemMarkers();
        Map<IMarker, IMarkerResolution> selectedQuickFixes =
                validationTestPage.getSelectedMarkerQuickFix();

        List<ValidationsTestProblemMarkerInfo> markerInfosList = new ArrayList<ValidationsTestProblemMarkerInfo>();
        
        for (IMarker marker : selectedMarkers) {
            try {
                IMarkerResolution quickFixClass =
                        selectedQuickFixes.get(marker);

                markerInfosList.add(new ValidationsTestProblemMarkerInfo(
                                marker.getResource().getFullPath()
                                        .toPortableString(), (String) marker
                                        .getAttribute(IIssue.ID),
                                (String) marker.getAttribute(IMarker.LOCATION),
                                marker.getAttribute(IMarker.MESSAGE, ""), //$NON-NLS-1$
                                quickFixClass != null ? quickFixClass
                                        .getLabel() : "")); //$NON-NLS-1$
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        
        Collections.sort(markerInfosList);
        
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[markerInfosList.size()];

        
        for (int i = 0; i < markerInfosList.size(); i++) {
			ValidationsTestProblemMarkerInfo validationsTestProblemMarkerInfo = markerInfosList.get(i);
			markerInfos[i] = validationsTestProblemMarkerInfo;
		}

        // 
        // Create a class to pass all data to the BaseTestJavaClassGenerator.
        String[] resourceInfoPathArray =
                testResourceInfoPaths.toArray(new String[testResourceInfoPaths
                        .size()]);
        String testDescription = null;
        if (validationTestPage.descText != null) {
            testDescription = validationTestPage.descText.getText();
        } else {
            testDescription = "";
        }
        Class testSuperClass = getTestSuperClass();

        ValidationTestJavaClassGeneratorData generatorData =
                new ValidationTestJavaClassGeneratorData(testName,
                        testClassName, testSuperClass.getSimpleName(),
                        testSuperClass.getPackage().getName(),
                        CreateBaseTestPage.toJavaName(testClassName),
                        testPluginId, testPackageId, baseTestPluginResourcePath
                                .toString(), resourceInfoPathArray,
                        markerInfos, validationTestPage.isCheckProblemsExists,
                        testDescription);

        //
        // Generate the base test class.
        ValidationTestJavaClassGenerator baseTestClassGenerator =
                new ValidationTestJavaClassGenerator();

        String baseTestClass = baseTestClassGenerator.generate(generatorData);

        return baseTestClass;
    }

    @Override
    protected Class getTestSuperClass() {
        return AbstractBaseValidationTest.class;
    }

}
