package com.tibco.xpd.om.test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * <p>
 * <i>Created: 31 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
abstract public class AbstractOMTestCase extends TestCase {

    protected static String TEST_PROJECT_NAME = "OMTestProject"; //$NON-NLS-1$
    protected static String TEST_FILE_NAME = "testFile.om"; //$NON-NLS-1$

    protected final ResourceSet rs;
    protected final TransactionalEditingDomain ed;
    protected final TransactionalCommandStack stack;
    protected final OMFactory factory;

    protected URI testResourceURI;
    protected IProject project;

    /**
     * Normally tests produces markers and this framework will check for them.
     * (see expectedMarkers). But there are test cases where parts of the system
     * will be tested, t5aht do not produce markers (e.g. set certain flags). In
     * this cases this flag has to be set to false.
     */
    protected boolean checkMarkers = true;

    // protected boolean showStatus = true;

    protected AbstractOMTestCase() {
        rs = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();
        ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        stack = (TransactionalCommandStack) ed.getCommandStack();
        factory = OMFactory.eINSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        switchToModelingPerspective();

        project = TestUtil.createProject(TEST_PROJECT_NAME);
        testResourceURI = URI.createPlatformResourceURI(project.getFullPath()
                .append(TEST_FILE_NAME).toPortableString(), true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        final Resource resource = rs.getResource(testResourceURI, false);
        if (resource != null && resource.isLoaded()) {
            ed.runExclusive(new Runnable() {
                public void run() {
                    resource.unload();
                }
            });
        }
        TestUtil.removeProject(project.getName());
        super.tearDown();
    }

    /**
     * Close the Welcome Page to see what happens on the workbench window.
     */
    protected void closeWelcomePage() {
        try {
            IViewReference welcomeViewRef = PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage()
                    .findViewReference("org.eclipse.ui.internal.introview"); //$NON-NLS-1$
            if (welcomeViewRef != null) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().hideView(welcomeViewRef);
            }
        } catch (Exception ignore) {
        }
    }

    // /**
    // * Selects a tree item in the project explorer view.
    // *
    // * @param segments
    // */
    // protected void selectPathInProjectExplorer(Object[] segments) {
    // IViewPart explorer = getProjectView();
    // if (explorer instanceof CommonNavigator) {
    // TreePath selectPath = new TreePath(segments);
    // ((CommonNavigator) explorer).setLinkingEnabled(true);
    // ISelectionProvider selectionProvider = explorer.getSite()
    // .getSelectionProvider();
    // selectionProvider.setSelection(new TreeSelection(selectPath));
    // } else if (explorer instanceof PackageExplorerPart) {
    // TreePath selectPath = new TreePath(segments);
    // ((PackageExplorerPart) explorer).setLinkingEnabled(true);
    // ISelectionProvider selectionProvider = explorer.getSite()
    // .getSelectionProvider();
    // selectionProvider.setSelection(new TreeSelection(selectPath));
    // }
    // }

    /**
     * Finds and return the property view.
     * 
     * @return
     */
    @SuppressWarnings("nls")
    protected IViewPart getProjectView() {
        IViewPart viewPart = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().findView(
                        "org.eclipse.ui.navigator.ProjectExplorer");
        if (viewPart == null) {
            viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().findView(
                            "org.eclipse.jdt.ui.PackageExplorer");
        }
        return viewPart;
    }

    /**
     * @param resourceURI
     * @param progressMonitor
     * @return
     */
    @SuppressWarnings("nls")
    public static Resource createResource(URI resourceURI,
            IProgressMonitor progressMonitor) {
        TransactionalEditingDomain editingDomain = XpdResourcesPlugin
                .getDefault().getEditingDomain();
        progressMonitor.beginTask("Craating Organization Model.", 3);
        final ResourceSet resourceSet = editingDomain.getResourceSet();
        final Resource resource = resourceSet.createResource(resourceURI);
        AbstractTransactionalCommand command = new AbstractTransactionalCommand(
                editingDomain, "Create Organization Model",
                Collections.EMPTY_LIST) {
            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {

                List<EObject> model = createInitialModel();
                for (EObject modelObject : model) {
                    resource.getContents().add(modelObject);
                }

                try {
                    resource.save(getSaveOptions());
                } catch (IOException e) {

                    fail("Unable to store model."); //$NON-NLS-1$
                }
                return CommandResult.newOKCommandResult();
            }

            /**
             * @generated
             */
            @Override
            public boolean canUndo() {
                // Don't want this command to be undo-able
                return false;
            }

        };
        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new SubProgressMonitor(progressMonitor, 1), null);
        } catch (ExecutionException e) {
            fail("Unable to create model."); //$NON-NLS-1$
        }

        return resource;
    }

    /**
     * @return
     */
    private static List<EObject> createInitialModel() {
        OMFactory f = OMFactory.eINSTANCE;
        OrgModel orgModel = f.createOrgModel();

        // createStandardTypes(orgModel);

        return Collections.singletonList((EObject) orgModel);
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map getSaveOptions() {
        Map saveOptions = new HashMap();
        saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
        saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
                Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
        return saveOptions;
    }

    protected void switchToModelingPerspective() {
        forceOpenPerspective("com.tibco.modeling.perspective"); //$NON-NLS-1$
    }

    /**
     * @param perspectiveId
     */
    private void forceOpenPerspective(String perspectiveId) {

        IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench()
                .getPerspectiveRegistry().getPerspectives();
        for (int i = 0; i < perspectives.length; i++) {
            IPerspectiveDescriptor desc = perspectives[i];
            if (desc.getId().equals(perspectiveId)) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().setPerspective(desc);
                return;
            }
        }
    }

}
