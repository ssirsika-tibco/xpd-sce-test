package com.tibco.xpd.bom.test.transform.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.ElementQualifier;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.uml2.uml.Class;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.xmlunit.TypeReferenceNodeValueComparator;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.xsd.XsdDiff;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Common JUNit class for bom, xsd and wsdl transformation operations
 * 
 * @author glewis
 * 
 */
public abstract class TransformationTest extends TestCase {

    protected String testProjectName = this.getClass().getName();

    protected String platformExampleFilesBase =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources";

    protected List<String> modelFileNames;

    protected final ResourceSet rs;

    protected final TransactionalEditingDomain ed;

    protected final TransactionalCommandStack stack;

    protected URI testResourceURI;

    protected IProject project;

    protected WorkingCopy workingCopy;

    protected List<IFile> modelFiles;

    protected String modelLocation;

    protected long timeStart = 0;

    protected long timeFinish = 0;

    protected List<IFile> transformedFiles = new ArrayList<IFile>();

    protected SpecialFolder outputSpecialFolder;

    protected IFile bomFile = null;

    protected Comparator<Node> typeReferenceComparator =
            new TypeReferenceNodeValueComparator();

    /**
	 * 
	 */
    public TransformationTest() {
        rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        stack = (TransactionalCommandStack) ed.getCommandStack();
    }

    /**
     * @param projectName
     */
    protected void createBPMProject() {
        long start = System.currentTimeMillis();
        // crating project, special folder, and copy example files to it
        project = TestUtil.createProject(testProjectName);

        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (projectConfig != null) {
            ProjectDetails projDetails =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            projectConfig.setProjectDetails(projDetails);
            Destinations createDestinations =
                    ProjectConfigFactory.eINSTANCE.createDestinations();
            projDetails.setGlobalDestinations(createDestinations);
            Destination destination =
                    ProjectConfigFactory.eINSTANCE.createDestination();
            destination.setType("BPM"); //$NON-NLS-1$
            createDestinations.getDestination().add(destination);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Setup Duration for createBPMProject = " + duration
                + "ms");
        TestUtil.waitForJobs();
    }

    /**
     * @param testFileName
     * @param resourceName
     * @param exampleFilesBase
     * @throws IOException
     * @throws CoreException
     */
    protected void copyResources() throws IOException, CoreException {
        long start = System.currentTimeMillis();
        outputSpecialFolder =
                TestUtil.createSpecialFolder(project, "Business Object Models", //$NON-NLS-1$
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        /*
         * Some BOM validations are disabled for generated (derived) BOMs,
         * especially where BOM is created with something that adds problem
         * markers. To mimic this 'normal' behaviour - we set our target bom
         * folder as Generated BOM Folder type. As from XPD-6029 onwards
         * generated boms are not derived anymore.
         */
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        config.getSpecialFolders().markAsGenerated(outputSpecialFolder,
                BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE);

        for (String modelFileName : modelFileNames) {
            if (modelFileName.indexOf("/") != -1) {
                String[] splitModelNames = modelFileName.split("/");
                String tmpModelFileName =
                        splitModelNames[splitModelNames.length - 1];

                if (splitModelNames.length > 1) {
                    IFolder insertFolder = outputSpecialFolder.getFolder();
                    for (String tmpDirectory : splitModelNames) {
                        if (!tmpDirectory.equals(tmpModelFileName)) {
                            if (!insertFolder.exists(new Path(tmpDirectory))) {
                                try {
                                    insertFolder.getFolder(new Path(
                                            tmpDirectory)).create(true,
                                            true,
                                            null);
                                    insertFolder =
                                            insertFolder.getFolder(new Path(
                                                    tmpDirectory));
                                } catch (CoreException e) {
                                }
                            } else {
                                insertFolder =
                                        insertFolder.getFolder(new Path(
                                                tmpDirectory));
                            }
                        }
                    }
                }
            }
            IFile modelFile =
                    outputSpecialFolder.getFolder().getFile(modelFileName);
            InputStream modelInputStream =
                    new ResourceSetImpl()
                            .getURIConverter()
                            .createInputStream(URI.createURI(platformExampleFilesBase
                                    + '/' + modelFileName));
            modelFile.create(modelInputStream, true, null);
            modelFile.refreshLocal(IResource.DEPTH_ZERO,
                    new NullProgressMonitor());
            modelLocation =
                    modelFile.getLocation().removeLastSegments(1)
                            .toPortableString();
            modelInputStream.close();
            // migrate(modelFile);
            modelFiles.add(modelFile);
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println("Setup Duration for copyResources = " + duration
                + "ms");

        start = System.currentTimeMillis();

        for (IFile modelFile : modelFiles) {
            /*
             * XPD-4011: Saket: Setting the derived flag manually for now.
             */

            /*
             * As from XPD-6029 onwards generated boms are not derived anymore.
             * So we need to mimic the behaviour by setting the bom special
             * folder as generated bom folder. see the note in copyResources()
             * method
             */
            // modelFile.setDerived(true, null);
            migrate(modelFile);
        }

        duration = System.currentTimeMillis() - start;
        System.out.println("Setup Duration for migrate = " + duration + "ms");
    }

    /**
     * Migrate the given bom model.
     * 
     * @param bomFile
     * @throws CoreException
     */
    protected void migrate(IFile bomFile) throws CoreException {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
        if (wc instanceof AbstractWorkingCopy) {
            EObject root = wc.getRootElement();
            if (root == null && ((AbstractWorkingCopy) wc).isInvalidVersion()) {
                ((AbstractWorkingCopy) wc).migrate();
            }
        }
    }

    /**
	 * 
	 */
    protected void setupPreferencesAndWait() {
        long start = System.currentTimeMillis();
        IPreferenceStore store =
                com.tibco.xpd.bom.gen.ui.Activator.getDefault()
                        .getPreferenceStore();
        store.setValue(BomGenPreferenceConstants.P_ENABLE_XSD_VALIDATION, true);
        store.setValue(BomGenPreferenceConstants.P_ENABLE_WSDL_VALIDATION, true);

        /*
         * Set re-generate BOM on project import to true, so that if BOMs are
         * imported in any test then they should always be re-generated.
         */
        store.setValue(BomGenPreferenceConstants.P_ENABLE_REGENERATE_BOM_ON_PROJECT_IMPORT,
                true);

        TestUtil.waitForJobs();

        long duration = System.currentTimeMillis() - start;
        System.out.println("Setup Duration for setupPreferencesAndWait = "
                + duration + "ms");
    }

    /**
     * @param testProjectName
     */
    protected void clean() {
        TestUtil.waitForJobs();
        long start = System.currentTimeMillis();
        IProject proj =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(testProjectName);
        if (proj.exists()) {
            TestUtil.removeProject(proj.getName());
        }

        TestUtil.waitForBuilds(300);

        long duration = System.currentTimeMillis() - start;
        System.out.println("Setup Duration for clean = " + duration + "ms");
    }

    /**
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        timeStart = System.currentTimeMillis();

        super.setUp();

        createBPMProject();

        setupPreferencesAndWait();

        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                try {
                    copyResources();
                } catch (IOException e) {
                    fail(e.getLocalizedMessage());
                }
            }

        }, null);

        ResourcesPlugin.getWorkspace()
                .build(IncrementalProjectBuilder.CLEAN_BUILD,
                        new NullProgressMonitor());
        ResourcesPlugin.getWorkspace()
                .build(IncrementalProjectBuilder.FULL_BUILD,
                        new NullProgressMonitor());
        TestUtil.waitForJobs();

        long duration = System.currentTimeMillis() - timeStart;
        System.out.println("Setup Duration = " + duration + "ms");
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        clean();
        super.tearDown();
        timeFinish = System.currentTimeMillis();
        long duration = timeFinish - timeStart;
        System.out.println("Duration = " + duration + "ms");
    }

    /**
     * @param file
     * @return
     */
    protected void processTransformedFiles(IFile file) {
        if (bomFile != null && file.equals(bomFile)) {
            transformedFiles.clear();
            Collection<String> transformedFileNames =
                    XSDUtil.getOutputFileNamesForBOMFile(file);
            for (String fileName : transformedFileNames) {
                IFile tmpFile =
                        outputSpecialFolder.getFolder().getFile(fileName);
                transformedFiles.add(tmpFile);
            }
        }
    }

    /**
     * @param transformedFile
     * @param goldFile
     * @return
     * @throws IOException
     * @throws CoreException
     * @throws SAXException
     */
    protected XmlDiff initXMLDiff(IFile transformedFile, IFile goldFile)
            throws IOException, CoreException, SAXException {
        XmlDiff xsdDiff =
                new XsdDiff(
                        new InputStreamReader(transformedFile.getContents()),
                        new InputStreamReader(goldFile.getContents()));

        return xsdDiff;
    }

    /**
     * Import XSD.
     * 
     * @param source
     * @param bomFilePath
     * @return
     */
    protected List<IStatus> importXSDtoBOM(File source, IPath bomFilePath) {
        System.out.println("-->[" + getClass().getName()
                + "]importXSDtoBOM called...");
        long start = System.currentTimeMillis();
        try {
            List<IStatus> ret =
                    XSDUtil.transformXSDToBOM(source, bomFilePath, null);

            /*
             * Sid: Some BOM validations are disabled for generated (derived)
             * BOMs, especially where BOM is created with something that adds
             * problem markers. We want to mimic this 'normal' behaviour - so
             * set our target bom file to dervied.
             */

            /*
             * As from XPD-6029 onwards generated boms are not derived anymore.
             * So we need to mimic the behaviour by setting the bom special
             * folder as generated bom folder. see the note in copyResources()
             * method
             */
            return ret;

        } finally {
            System.out.println("-->[" + getClass().getName()
                    + "]importXSDtoBOM completed: "
                    + (System.currentTimeMillis() - start));
        }
        // Issues issues = XSDUtil.transformXSDToBOM(source, bomFilePath);
        //
        //
        // List<IStatus> result = new ArrayList<IStatus>();
        //
        // for (Issue issue : issues.getWarnings()) {
        // result.add(new Status(IStatus.WARNING, "com.tibco.xpd.bom.test",
        // issue.getMessage()));
        // }
        //
        // for (Issue issue : issues.getErrors()) {
        // result.add(new Status(IStatus.ERROR, "com.tibco.xpd.bom.test",
        // issue.getMessage()));
        // }
        //
        // return result;
    }

    /**
     * Export to XSD. SID XPD-5118. The exportBOMtoXSD() method takes a Boolean
     * parameter ignoreStereotypes which used to be a parameter that was taken
     * by the BOM2XSDTransformer class (via
     * com.tibco.xpd.bom.xsdtransform.api.XSDUtil).
     * <p>
     * Since creation of these tests the ignoreStereoTypes functionality was
     * removed from BOM2XSDTransformer / XSDUtil. However, as a coincidence, new
     * methods in those classes that took the SAME Boolean parameters but with
     * DIFFERENT meanings was created.
     * <p>
     * So re-ordered parameters to ENSURE that old code thinking param 3 was
     * boolean=ignoreStereotypes was fixed.
     * 
     * @param source
     * @param usePrefValidation
     * @param xsdFilePath
     * @param isLocalWorkspaceTarget
     *            true target (FOLDER) path is a Workspace path (rather than
     *            absolute file system path.
     * 
     * @return Status result list from transform
     */
    protected List<IStatus> exportBOMtoXSD(IFile source,
            boolean usePrefValidation, IPath targetFolderPath,
            boolean isLocalWorkspaceTarget) {
        System.out.println("-->[" + getClass().getName()
                + "]exportBOMtoXSD called...");
        long start = System.currentTimeMillis();
        try {
            return XSDUtil.transformBOMToXSD(source,
                    targetFolderPath,
                    usePrefValidation,
                    isLocalWorkspaceTarget);
        } finally {
            System.out.println("-->[" + getClass().getName()
                    + "]exportBOMtoXSD completed: "
                    + (System.currentTimeMillis() - start));
        }
    }

    /**
     * Export to XSD.
     * 
     * @param source
     * @param xsdFilePath
     * @param ignoreStereotypes
     * @param usePrefValidation
     * @return
     */
    protected List<IStatus> incrementalExportBOMtoXSD(Class umlClass,
            String xsdFilePath, boolean preserveSchemas,
            boolean ignoreStereotypes, boolean usePrefValidation) {
        System.out.println("-->[" + getClass().getName()
                + "]incrementalExportBOMtoXSD called...");
        long start = System.currentTimeMillis();
        List<IStatus> result = new ArrayList<IStatus>();
        try {
            XSDUtil.incrementalTransformBOMToXSD(umlClass,
                    xsdFilePath,
                    preserveSchemas,
                    result,
                    usePrefValidation);
        } finally {
            System.out.println("-->[" + getClass().getName()
                    + "]incrementalExportBOMtoXSD completed: "
                    + (System.currentTimeMillis() - start));
        }
        return result;
    }

    /**
     * Import WSDL.
     * 
     * @param source
     * @param bomFilePath
     * @return List of errors (can be empty)
     */
    protected List<IStatus> importWSDLtoBOM(final File source,
            final IPath bomFilePath) {

        final List<IStatus> result = new ArrayList<IStatus>();

        System.out.println("-->[" + getClass().getName()
                + "]importWSDLtoBOM called...");
        long start = System.currentTimeMillis();

        try {
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    result.addAll(WSDLTransformUtil.transformWSDLtoBOM(source,
                            bomFilePath,
                            null));
                }
            },
                    null);
        } catch (CoreException e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        } finally {
            System.out.println("-->[" + getClass().getName()
                    + "]importWSDLtoBOM completed: "
                    + (System.currentTimeMillis() - start));
        }

        TestUtil.waitForJobs();

        return result;
    }

    /**
     * Export to WSDL.
     * 
     * @param source
     * @param wsdlFilePath
     * @return
     */
    protected List<IStatus> exportBOMtoWSDL(IFile source, IPath wsdlFilePath) {

        System.out.println("-->[" + getClass().getName()
                + "]exportBOMtoWSDL called...");
        long start = System.currentTimeMillis();
        try {
            return WSDLTransformUtil.transformBOMtoWSDL(source,
                    wsdlFilePath,
                    null);
            // Issues issues =
            // new XtendExportTransformer()
            // .transform(source, wsdlFilePath);
            //
            // List<IStatus> result = new ArrayList<IStatus>();
            // for (Issue issue : issues.getWarnings()) {
            // result.add(new Status(IStatus.WARNING,
            // "com.tibco.xpd.bom.test", issue.getMessage()));
            // }
            // for (Issue issue : issues.getErrors()) {
            // result.add(new Status(IStatus.ERROR, "com.tibco.xpd.bom.test",
            // issue.getMessage()));
            // }
            // return result;
        } finally {
            System.out.println("-->[" + getClass().getName()
                    + "]exportBOMtoWSDL completed: "
                    + (System.currentTimeMillis() - start));
        }
    }

    /**
     * Get the error status objects from the given list.
     * 
     * @param status
     * @return
     */
    protected List<IStatus> getErrors(List<IStatus> status) {
        List<IStatus> errors = new ArrayList<IStatus>();
        if (status != null) {
            for (IStatus st : status) {
                if (st.getSeverity() == IStatus.ERROR) {
                    errors.add(st);
                }
            }
        }
        return errors;
    }

    /**
     * xsd:element list comparison qualifier (add to xmlDiff to allow
     * xsd:element list to be ordered different as long as same @name'd / or
     * 
     * @ref'd elements are present and the same.
     * 
     * @author aallway
     * @since 15 Apr 2011
     */
    private static final class XsdNameOrRefQualifier implements
            ElementQualifier {
        @Override
        public boolean qualifyForComparison(Element control, Element test) {
            /*
             * xsd:element's with the same @name or same
             * 
             * @ref are equivalent for comparison
             */
            String controlName = control.getAttribute("name");
            if (controlName != null && controlName.length() > 0) {
                String testName = test.getAttribute("name");

                if (controlName.equals(testName)) {
                    return true;
                }
            }

            String controlRef = control.getAttribute("ref");
            if (controlRef != null && controlRef.length() > 0) {
                String testRef = test.getAttribute("ref");

                if (controlRef.equals(testRef)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Sid: For convenience, can now be passed markers. This used to c heck for
     * specific kind of marker, but in reality we should be checking for ANY
     * error level marker regardless of what feature may have raised it.
     * 
     * @param file
     * @param errorMarkers
     * 
     * @return String representing the give problem marker messages.,
     */
    protected String getErrorMarkerMessages(IFile file,
            Collection<IMarker> errorMarkers) {
        String errMsg = ""; //$NON-NLS-1$
        try {
            for (IMarker marker : errorMarkers) {
                errMsg += marker.getAttribute(IMarker.MESSAGE);
                errMsg += "\r\n"; //$NON-NLS-1$
            }
        } catch (CoreException e) {
        }
        return errMsg;
    }

}
