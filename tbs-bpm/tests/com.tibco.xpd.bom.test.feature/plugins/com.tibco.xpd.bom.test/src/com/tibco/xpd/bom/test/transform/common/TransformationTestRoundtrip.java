package com.tibco.xpd.bom.test.transform.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Common JUNit class for bom, xsd and wsdl transformation operations
 * 
 * @author glewis
 * 
 */
public abstract class TransformationTestRoundtrip extends TransformationTest {
    public abstract void testTransformation() throws Exception;

    protected List<String> goldFileNames = new ArrayList<String>();

    protected List<IFile> wizardGoldFiles = new ArrayList<IFile>();

    protected List<IFile> builderGoldFiles = new ArrayList<IFile>();

    protected SpecialFolder wizardGoldFilesSpecialFolder;

    protected SpecialFolder builderGoldFilesSpecialFolder;

    protected String platformWizardGoldFilesBase =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources";

    protected String platformBuilderGoldFilesBase =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources";

    private Comparator<IFile> fileComparator = new Comparator<IFile>() {
        @Override
        public int compare(IFile arg0, IFile arg1) {
            return arg0.getName().compareTo(arg1.getName());
        }
    };

    public void exportTest() throws Exception {
        exportTest(true);
    }

    public void exportTest(boolean ignoreFormDefaults) throws Exception {
        // bomFile = null; // remove this line when want this back in
        if (bomFile != null) {
            // Collections.sort(wizardGoldFiles, fileComparator);
            // Collections.sort(builderGoldFiles, fileComparator);
            /*
             * XPD-4011: Saket: Setting the derived flag manually for now. Sid:
             * Some BOM validations are disabled for generated (derived) BOMs,
             * especially where BOM is created with soemthing that adds problem
             * markers. We want to mimic this 'normal' behaviour - so set our
             * target bom file to derived.
             */
            /*
             * As from XPD-6029 onwards generated boms are not derived anymore.
             * So we need to mimic the behaviour by setting the bom special
             * folder as generated bom folder. see the note in copyResources()
             * method
             */
            // bomFile.setDerived(true, null);
            ResourcesPlugin
                    .getWorkspace()
                    .getRoot()
                    .refreshLocal(IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());
            TestUtil.buildAndWait();
            TestUtil.waitForJobs();

            /*
             * Sid: check FOR ANY ERROR marker NOT just BDS ones - want to catch
             * anything.
             */

            List<IMarker> errorMarkers = TestUtil.getErrorMarkers(bomFile);

            boolean hasErrorMarker = !errorMarkers.isEmpty();

            assertEquals("There are errors on the BOM "
                    + getErrorMarkerMessages(bomFile, errorMarkers),
                    false,
                    hasErrorMarker);

            List<IStatus> result =
                    XSDUtil.transformBOMToXSD(bomFile,
                            ResourcesPlugin
                                    .getWorkspace()
                                    .getRoot()
                                    .getProject(testProjectName)
                                    .getFullPath()
                                    .append(outputSpecialFolder.getFolder()
                                            .getName()),
                            true,
                            true);
            assertEquals("There were errors on this CDS Equiv' builder transformation process (simulating an export using builder).",
                    0,
                    getErrors(result).size());

            processTransformedFiles(bomFile);

            Collections.sort(transformedFiles, fileComparator);

            IFile goldFile = null;
            IFile transformedFile = null;
            for (int index = 0; index < transformedFiles.size(); index++) {

                /*
                 * Sid: GOld file is the "control" file (the gold-standard) Used
                 * to be wrong way round here which led to misleading error
                 * reports.
                 */
                Iterator<IFile> iterator = builderGoldFiles.iterator();
                boolean found = false;
                while (iterator.hasNext()) {

                    goldFile = iterator.next();

                    transformedFile = transformedFiles.get(index);

                    if (goldFile.getName().equals(transformedFile.getName())) {

                        found = true;
                        break;
                    }
                }

                if (found) {

                    XmlDiff xmlDiff = initXMLDiff(goldFile, transformedFile);

                    /*
                     * DOn't think we should ignore form defaultsEither we
                     * produce files
                     */
                    // if (ignoreFormDefaults) {
                    // xmlDiff.addIgnoreNode(new XmlDiffIgnoreNode(
                    // new XmlDiffNodePath("@attributeFormDefault")));
                    // xmlDiff.addIgnoreNode(new XmlDiffIgnoreNode(
                    // new XmlDiffNodePath("@elementFormDefault")));
                    // xmlDiff.addIgnoreNode(new XmlDiffIgnoreNode(
                    // new XmlDiffNodePath("@form")));
                    // }

                    boolean similar = xmlDiff.similar();
                    Assert.assertTrue(builderGoldFiles.get(index).getName()
                            + ": CDS Equiv' Builder file must be identical to the gold file:\n"
                            + xmlDiff.toString(),
                            similar);
                }
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        copyGoldFileResources();
    }

    /**
     * @throws IOException
     * @throws CoreException
     */
    protected void copyGoldFileResources() throws IOException, CoreException {
        // copyWizardGoldFileResources();
        copyBuilderGoldFileResources();
    }

    /**
     * @param testFileName
     * @param resourceName
     * @param exampleFilesBase
     * @throws IOException
     * @throws CoreException
     */
    protected void copyWizardGoldFileResources() throws IOException,
            CoreException {
        long start = System.currentTimeMillis();
        wizardGoldFilesSpecialFolder =
                TestUtil.createSpecialFolder(project, "Wizard Gold Files", //$NON-NLS-1$
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        for (String goldFileName : goldFileNames) {
            IFile goldFile =
                    wizardGoldFilesSpecialFolder.getFolder()
                            .getFile(goldFileName);
            InputStream modelInputStream =
                    new ResourceSetImpl()
                            .getURIConverter()
                            .createInputStream(URI.createURI(platformWizardGoldFilesBase
                                    + '/' + goldFileName));
            goldFile.create(modelInputStream, true, null);
            goldFile.refreshLocal(IResource.DEPTH_ZERO,
                    new NullProgressMonitor());

            modelInputStream.close();
            wizardGoldFiles.add(goldFile);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Setup Duration for copyWizardGoldFileResources = "
                + duration + "ms");
    }

    /**
     * @param testFileName
     * @param resourceName
     * @param exampleFilesBase
     * @throws IOException
     * @throws CoreException
     */
    protected void copyBuilderGoldFileResources() throws IOException,
            CoreException {
        long start = System.currentTimeMillis();
        builderGoldFilesSpecialFolder =
                TestUtil.createSpecialFolder(project, "Builder Gold Files", //$NON-NLS-1$
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        for (String goldFileName : goldFileNames) {
            IFile goldFile =
                    builderGoldFilesSpecialFolder.getFolder()
                            .getFile(goldFileName);
            InputStream modelInputStream =
                    new ResourceSetImpl()
                            .getURIConverter()
                            .createInputStream(URI.createURI(platformBuilderGoldFilesBase
                                    + '/' + goldFileName));
            goldFile.create(modelInputStream, true, null);
            goldFile.refreshLocal(IResource.DEPTH_ZERO,
                    new NullProgressMonitor());

            modelInputStream.close();
            builderGoldFiles.add(goldFile);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Setup Duration for copyBuilderGoldFileResources = "
                + duration + "ms");
    }
}
