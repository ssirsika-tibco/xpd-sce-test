/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.legacy.wm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.n2.common.attributefacade.util.AttributefacadeResourceFactoryImpl;
import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;
import com.tibco.xpd.n2.brm.BRMGenerator;
import com.tibco.xpd.n2.brm.utils.BRMSchemaUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.util.ProjectUtil;


/**
 * 
 * Test Work List Facade runtime artifacts generation from designtime .wlf
 * resource.
 * 
 * Sid ACE-245: This ex-AMX-BPM test moved to SCE tests as it had to be changed
 * for changed
 * {@link BRMGenerator#generateWlfModel(org.eclipse.core.resources.IProject, String)}
 * method. As we took the trouble to make it work, then may as well include it
 * in SCE tests!
 * 
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

        Resource wlfResource = BRMGenerator.getInstance()
                .generateWlfModel(wlfResourceInfo.getProject(),
                        "1.0.0.123456789"); // $NON-NLS-1$ //$NON-NLS-1$
        assertTrue(wlfResource != null);

        // Compare with gold file.
        IPath wlfFilePath = genOutFolder.getFullPath()
                .append(BRMGenerator.WORKLISTFACADE_FILENAME); // $NON-NLS-1$

        saveXmlFileToWorkspace(wlfFilePath, wlfResource.getContents().get(0));
        
        IFile generatedWlfFile = genOutFolder
                .getFile(BRMGenerator.WORKLISTFACADE_FILENAME);
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
     * Saves emf model in XML format (with 'xml' extension).
     * 
     * @param filePath
     *            path to the file. (It should ane with 'xml' extension.)
     * @param documentRoot
     *            the document root element.
     */
    private IStatus saveXmlFileToWorkspace(IPath filePath,
            EObject documentRoot) {
        
        Resource.Factory resourceFactory =
                new AttributefacadeResourceFactoryImpl();

        final Map<String, Object> extensionToFactoryMap =
                Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        final Object previousXMLFactory =
                extensionToFactoryMap
                        .get(BRMGenerator.WLF_MODULE_FILE_EXTENSION);
        try {
            /*
             * Temporarily register "xml" extension so EMF resource is created
             * using specific factory.
             */
            extensionToFactoryMap.put(BRMGenerator.WLF_MODULE_FILE_EXTENSION,
                    resourceFactory);
            final URI resourceURI =
                    URI.createPlatformResourceURI(filePath.toPortableString(),
                            true);
            final ResourceSet rs = new ResourceSetImpl();
            final Resource resource = rs.createResource(resourceURI);
            resource.getContents().add(documentRoot);
            resource.save(N2Utils.getDefaultXMLSaveOptions());
            IFile file =
                    ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);

            IContainer parent = file.getParent();

            if (null != parent && parent.isAccessible()) {

                parent.refreshLocal(IResource.DEPTH_INFINITE, null);
            }

            // if (file.isAccessible()) {
            // file.refreshLocal(IResource.DEPTH_ZERO, null);
            // file.setDerived(true);
            // }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* Revert previous registry settings. */
            extensionToFactoryMap.put(BRMGenerator.WLF_MODULE_FILE_EXTENSION,
                    previousXMLFactory);
        }
        return Status.OK_STATUS;
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

        if (testFile == null) {
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
            testStream = new ResourceSetImpl().getURIConverter()
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
                return new Status(IStatus.ERROR, "com.tibco.xpd.sce.test", //$NON-NLS-1$
                        errorResults.toString());
            } else {
                errorResults
                        .append(String
                                .format("Gold file '%1$s' and Test file '%2$s' match! ", //$NON-NLS-1$
                                        goldFile.getFullPath(),
                                        testFile.getFullPath()));
                return new Status(IStatus.OK, "com.tibco.xpd.sce.test", //$NON-NLS-1$
                        errorResults.toString());
            }

        } catch (Exception e) {
            return new Status(IStatus.ERROR, "com.tibco.xpd.sce.test", //$NON-NLS-1$
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
