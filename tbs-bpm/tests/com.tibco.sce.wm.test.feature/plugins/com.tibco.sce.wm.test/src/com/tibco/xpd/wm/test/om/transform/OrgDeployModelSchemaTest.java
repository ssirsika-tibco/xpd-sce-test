/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.wm.test.om.transform;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.xmlunit.OrgDeployModelDiff;
import com.tibco.xpd.om.transform.de.actions.ExportToDEAction;
import com.tibco.xpd.wm.test.WMTestActivator;

/**
 * The test designed to validate the generated de model [Org Deploy model]
 * against the schema. The Test is meant to import the project , do necessary
 * initialisations and use Navigator Action 'Export To Tibco Directory Engine'
 * on the OM file, to generate the Org Deploy Model.The generated Org Deploy
 * Model '*.xml' is then validated against the Schema. Once the generated Deploy
 * Model file is validated it is compared with the the gold file.
 * 
 * @author aprasad
 * @since 17-Oct-2013
 */
public class OrgDeployModelSchemaTest extends AbstractBuildingBaseResourceTest {
    /**
     * Deploy Model Gold File
     */
    private static final String ORG_MODEL_GOLD_FILE = "OrgModel.xml"; //$NON-NLS-1$

    /**
     * Organization Model File
     */
    private static final String ORG_MODEL_FILE = "OrgModel.om"; //$NON-NLS-1$

    public static final String PREF_DONT_ASK_AGAIN_FOR_SAVE =
            "dont_ask_save_always"; //$NON-NLS-1$

    private static final String SCHEMA_BASE_URI =
            "platform:/plugin/com.tibco.xpd.om.transform.de/model/"; //$NON-NLS-1$

    /**
     * Name of the Schema file
     */
    private static final String SCHEMA_NAME = "directory-model-2.0.xsd"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {
        return "OrgDeployModelSchemaTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/OrgDeployModelSchemaTest", "OrgModelProject/Organization/OrgModel.om"), //$NON-NLS-1$ //$NON-NLS-2$$NON-NLS-2$
                        // Gold File
                        new TestResourceInfo(
                                "resources/OrgDeployModelSchemaTest", "OrgModelProject/GoldFiles/OrgModel.xml") //$NON-NLS-1$ //$NON-NLS-2$$NON-NLS-2$
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
        return "com.tibco.xpd.wm.test"; //$NON-NLS-1$
    }

    public void testOrgDeployModelSchemaTest() throws Exception {
        // Check all files created correctly.
        // 1. Creates a Project with given Organization Model resource/s.
        checkTestFilesCreated();
        IFile testFile = getTestFile(ORG_MODEL_FILE);
        // generate Deploy Org Model
        // 2. Use ExportToDE action on the OM resource to generate the Deploy
        // model.
        StringBuilder failureLog = generateDeployModel(testFile);
        // 3. Validate it against the schema.
        try {
            validateGeneratedDeployModel(testFile);
        } catch (Exception e) {
            failureLog.insert(0,
                    "Deploy Org Model Validation against schema failed:\n" //$NON-NLS-1$
                            + e.getLocalizedMessage());
        }
        // 4. Also Compare it with Gold deploy file.
        IFile goldFile = getTestFile(ORG_MODEL_GOLD_FILE);

        failureLog.append(compareGoldAndTest(goldFile, testFile));

        if (failureLog.length() != 0) {
            failureLog.insert(0, "Deploy Org Model generation test failed:\n"); //$NON-NLS-1$
            fail(failureLog.toString());
        }

        return;
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
    private Object compareGoldAndTest(IFile goldFile, IFile testFile)
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
            // get generated Org Deploy model file , kept at relative location
            // to the Org Model file.
            testStream =
                    new ResourceSetImpl()
                            .getURIConverter()
                            .createInputStream(URI.createURI(testFile
                                    .getFullPath().removeFileExtension()
                                    .addFileExtension("xml").toPortableString())); //$NON-NLS-1$
            InputSource testSource = new InputSource(testStream);

            OrgDeployModelDiff xpdlDiff =
                    new OrgDeployModelDiff(goldSource, testSource);

            if (!xpdlDiff.similar()) {
                errorResults
                        .append(String
                                .format("Gold file '%1$s' and Test file '%2$s' do not match: ", //$NON-NLS-1$
                                        goldFile.getName(),
                                        testFile.getFullPath()
                                                .removeFileExtension()
                                                .addFileExtension("xml") //$NON-NLS-1$
                                                .lastSegment()));
                xpdlDiff.appendMessage(errorResults);
                errorResults
                        .append("-------------------------------------------------------------------\n\n"); //$NON-NLS-1$

            }

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (goldStream != null) {
                goldStream.close();
            }
            if (testStream != null) {
                testStream.close();
            }
        }

        return errorResults;
    }

    /**
     * Validated the generated Deploy Org Model file against the schema.
     * 
     * @param modelFile
     *            , Organization Model file for which the deploy model is
     *            generated.
     * @throws IOException
     * @throws SAXException
     * 
     */
    private void validateGeneratedDeployModel(IFile modelFile)
            throws IOException, SAXException {
        InputStream schemaInputStream =
                new ResourceSetImpl().getURIConverter()
                        .createInputStream(URI.createURI(SCHEMA_BASE_URI
                                + SCHEMA_NAME));
        InputStream genFileInputStream =
                new ResourceSetImpl().getURIConverter()
                        .createInputStream(URI.createURI(modelFile
                                .getFullPath().removeFileExtension()
                                .addFileExtension("xml").toPortableString())); //$NON-NLS-1$
        Source schemaSource = new StreamSource(schemaInputStream, SCHEMA_NAME);
        Source[] schemas = new Source[] { schemaSource };
        Source document = new StreamSource(genFileInputStream);
        TestUtil.validateAgainstXMLSchema(schemas, document);
    }

    /**
     * Generates the Deploy Organization Model for the given Organization Model.
     * 
     * @param testFile
     *            , Organization Model File to generate Deploy Model
     * @return
     */
    private StringBuilder generateDeployModel(final IFile testFile) {
        StringBuilder failureLog = new StringBuilder();

        /*
         * To avoid the save dialog, set preference
         * 'PREF_DONT_ASK_AGAIN_FOR_SAVE' to true and then save all editors.
         */

        IPreferenceStore prefStore =
                WMTestActivator.getDefault().getPreferenceStore();
        prefStore.setDefault(PREF_DONT_ASK_AGAIN_FOR_SAVE, true);

        PlatformUI.getWorkbench().saveAllEditors(false);
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .closeAllEditors(false);
        /*
         * create action to run the context menu for generating the Org Deploy
         * Model
         */

        StructuredSelection selection =
                new StructuredSelection(new IAdaptable() {

                    @Override
                    public Object getAdapter(Class adapter) {
                        if (IFile.class.equals(adapter)) {
                            return testFile;
                        } else if (OrgDeployModelSchemaTest.class
                                .equals(adapter)) {
                            return testFile;
                        }
                        return null;
                    }
                });
        // Create Export as DE Model Action
        final BaseSelectionListenerAction delegate = new ExportToDEAction();
        // set selection
        delegate.selectionChanged(selection);
        // initiate export action
        delegate.run();

        PlatformUI.getWorkbench().saveAllEditors(false);
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .closeAllEditors(false);
        return failureLog;
    }
}
