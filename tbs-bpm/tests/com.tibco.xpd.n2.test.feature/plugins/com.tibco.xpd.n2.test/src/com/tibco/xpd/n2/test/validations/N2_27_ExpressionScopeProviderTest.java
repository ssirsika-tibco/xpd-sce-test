/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.test.validations;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Tests ExpressionScopeProvider. Change a package data field that fixes script
 * validation problems and any marker containing that data field should not be
 * there after the fix.
 * 
 * 
 * @author agondal
 * @since 12 Sep 2013
 */
public class N2_27_ExpressionScopeProviderTest extends
        AbstractBaseValidationTest {

    public void testExpressionScopeProviderTest() throws Exception {
        // Check all files created correctly.
        checkTestFilesCreated();

        /*
         * With package field 'Field1', there are problem markers with text
         * containing "Variable a not defined", change this data field to 'a' to
         * fix the problems. After the change, fail test if there is still any
         * problem marker containing the text.
         */

        IFile testFile = getTestFile("Package1.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            EditingDomain ed = wc.getEditingDomain();
            Package pckg = (Package) wc.getRootElement();

            DataField dataFiled = pckg.getDataFields().get(0);

            String fieldName = "a"; //$NON-NLS-1$
            Command cmd =
                    Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                            dataFiled,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            fieldName);

            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }

            waitForValidationToFinish();

            List<ValidationsTestProblemMarkerInfo> resourceMarkersAfterFix =
                    getProblemMarkers(testFile);

            String markerText = "Variable a not defined"; //$NON-NLS-1$
            String msgProblemStillExist =
                    "The following problem marker still exists: \n %1$s"; //$NON-NLS-1$

            wc.save();

            for (ValidationsTestProblemMarkerInfo marker : resourceMarkersAfterFix) {
                if (marker.getProblemText().contains(markerText)) {
                    fail(String.format(msgProblemStillExist, marker));
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {
        return "ExpressionScopeProviderTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ExpressionValidationRulesTest", "BpmProject/Process Packages{processes}/Package1.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$                        
                };

        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     * 
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
     * 
     * @return
     */
    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        // TODO Auto-generated method stub
        return null;
    }
}
