/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.wm.test.generator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;
import com.tibco.xpd.n2.brm.BRMGenerator;
import com.tibco.xpd.n2.brm.utils.BRMSchemaUtil;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.wm.test.WMTestActivator;

/**
 * 
 * Test Work List Facade runtime artifacts generation from designtime .wlf
 * resource.
 * 
 * @author jarciuch
 * @since 8 Jan 2014
 */
public class WorkListFacadeGenTest extends AbstractBuildingBaseResourceTest {

    private TestResourceInfo wlfResourceInfo = new TestResourceInfo(
            "resources/WorkListFacadeGenTest", //$NON-NLS-1$
            "WLF/Work List Facade{wlf}/WorkListFacade.wlf") { //$NON-NLS-1$
            };

    private TestResourceInfo wlfGenGoldResourceInfo = new TestResourceInfo(
            "resources/WorkListFacadeGenTest", //$NON-NLS-1$
            "WLF/gold/wlf.xml") { //$NON-NLS-1$
            };

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {
        return "WorkListFacadeGenTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { wlfResourceInfo,
                        wlfGenGoldResourceInfo };
        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     * 
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.wm.test"; //$NON-NLS-1$
    }

    public void testGenerateWlfModel() throws Exception {
        IFolder genOutFolder =
                wlfResourceInfo.getProject().getFolder("gen-out"); //$NON-NLS-1$
        ProjectUtil.createFolder(genOutFolder, true, null);
        BRMGenerator.getInstance()
                .generateWlfModel(wlfResourceInfo.getProject(),
                        genOutFolder); // $NON-NLS-1$
        IFile generatedWlfFile = genOutFolder.getFile("wlf.xml"); //$NON-NLS-1$
        assertTrue(generatedWlfFile.exists());

        // Compare with gold file.
        IStatus goldCompareResult =
                compareGoldAndTestXml(wlfGenGoldResourceInfo.getTestFile(),
                        generatedWlfFile);
        if (goldCompareResult.getSeverity() != IStatus.OK) {
            fail(goldCompareResult.getMessage());
        }

        // Validate against schema
        IStatus schemaValidationResult =
                BRMSchemaUtil.validateAgainstBRMXSD(generatedWlfFile);
        if (schemaValidationResult.getSeverity() != IStatus.OK) {
            fail(schemaValidationResult.getMessage());
        }
    }

    /**
     * Compares the Golf File and the Deploy Model file generated for the given
     * test OrgModel file.
     * 
     * @param goldFile
     *            , Deploy Org Model Gold File.
     * @param testFile
     *            , Organization Model file, for which the Deploy Modle is
     *            generated.
     * @return
     */
    private IStatus compareGoldAndTestXml(IFile goldFile, IFile testFile)
            throws IOException {
        StringBuffer errorResults = new StringBuffer();

        if (!goldFile.exists()) {
            errorResults.append(String.format("Gold file '%s' is missing.", //$NON-NLS-1$
                    goldFile.getFullPath()));
            errorResults
                    .append("-------------------------------------------------------------------\n\n"); //$NON-NLS-1$
        }

        if (!testFile.exists()) {
            errorResults.append(String.format("Test file '%s' is missing.", //$NON-NLS-1$
                    testFile.getFullPath()));
            errorResults
                    .append("-------------------------------------------------------------------\n\n"); //$NON-NLS-1$
        }

        InputStream goldStream = null;
        InputStream testStream = null;

        try {

            goldStream = goldFile.getContents();
            InputSource goldSource = new InputSource(goldStream);
            testStream =
                    new ResourceSetImpl().getURIConverter()
                            .createInputStream(URI.createURI(testFile
                                    .getFullPath().toPortableString()));
            InputSource testSource = new InputSource(testStream);

            WlfModelDiff xpdlDiff = new WlfModelDiff(goldSource, testSource);

            if (!xpdlDiff.similar()) {
                errorResults
                        .append(String
                                .format("Gold file '%1$s' and Test file '%2$s' do not match: ", //$NON-NLS-1$
                                        goldFile.getFullPath(),
                                        testFile.getFullPath()));
                xpdlDiff.appendMessage(errorResults);
                errorResults
                        .append("-------------------------------------------------------------------\n\n"); //$NON-NLS-1$
                return new Status(IStatus.ERROR, WMTestActivator.PLUGIN_ID,
                        errorResults.toString());
            } else {
                errorResults
                        .append(String
                                .format("Gold file '%1$s' and Test file '%2$s' match! ", //$NON-NLS-1$
                                        goldFile.getFullPath(),
                                        testFile.getFullPath()));
                return new Status(IStatus.OK, WMTestActivator.PLUGIN_ID,
                        errorResults.toString());
            }

        } catch (Exception e) {
            return new Status(IStatus.ERROR, WMTestActivator.PLUGIN_ID,
                    e.getMessage(), e);
        } finally {
            if (goldStream != null) {
                goldStream.close();
            }
            if (testStream != null) {
                testStream.close();
            }
        }

    }

    private static class WlfModelDiff extends XmlDiff {
        public WlfModelDiff(InputSource control, InputSource test)
                throws SAXException, IOException {
            super(control, test);
            List<XmlDiffNodePath> qualifierPaths =
                    new ArrayList<XmlDiffNodePath>();
            // qualify 'alias' elements on 'attributeName' attribute
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@attributeName")); //$NON-NLS-1$
            XmlDiffSequenceQualifier sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                            new String[] { "alias", }, false),//$NON-NLS-1$
                            qualifierPaths);
            this.addSequenceElementQualifier(sequenceQualifier);
        }

    }

}
