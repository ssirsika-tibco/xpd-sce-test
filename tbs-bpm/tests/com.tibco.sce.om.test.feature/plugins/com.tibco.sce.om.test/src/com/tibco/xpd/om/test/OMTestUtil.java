/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.test;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Utility class for OM tests.
 * 
 * <p>
 * <i>Created: 26 Feb 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class OMTestUtil {
    /**
     * Prevents instantiation.
     */
    private OMTestUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates EMF Resource with optional initial content and saves to URI
     * location. It uses global resource set, transaction and eclipse operation
     * history.
     * 
     * @param resourceURI
     *            the URI of resource to be created.
     * @param initialModel
     *            collection of root EObjects or null if no initial model should
     *            be created.
     * @param progressMonitor
     *            the progress monitor of null if no progress monitor is
     *            necessary.
     * @return
     */
    @SuppressWarnings("nls")
    public static Resource createAndSaveResource(URI resourceURI,
            final Collection<? extends EObject> initialModel,
            IProgressMonitor progressMonitor) {
        if (progressMonitor == null) {
            progressMonitor = new NullProgressMonitor();
        }
        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        progressMonitor.beginTask("Craating resource.", 3);
        final ResourceSet resourceSet = editingDomain.getResourceSet();
        final Resource resource = resourceSet.createResource(resourceURI);
        AbstractTransactionalCommand command =
                new AbstractTransactionalCommand(editingDomain,
                        "Create Organization Model", Collections.EMPTY_LIST) {
                    @Override
                    protected CommandResult doExecuteWithResult(
                            IProgressMonitor monitor, IAdaptable info)
                            throws ExecutionException {

                        if (initialModel != null) {
                            resource.getContents().addAll(initialModel);
                        }

                        try {
                            resource.save(getSaveOptions());
                        } catch (IOException e) {

                            throw new RuntimeException(
                                    "Unable to store model.", e); //$NON-NLS-1$
                        }
                        return CommandResult.newOKCommandResult();
                    }

                    @Override
                    public boolean canUndo() {
                        // Don't want this command to be undo-able
                        return false;
                    }

                };
        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new SubProgressMonitor(progressMonitor, 1),
                    null);
        } catch (ExecutionException e) {
            throw new RuntimeException("Unable to create model.", e); //$NON-NLS-1$
        }

        return resource;
    }

    /**
     * Creates EMF Resource with optional initial content. Resource will only be
     * created in memory. This method uses global resource set, transaction and
     * eclipse operation history.
     * 
     * @param resourceURI
     *            the URI of resource to be created.
     * @param initialModel
     *            collection of root EObjects or null if no initial model should
     *            be created.
     * @param progressMonitor
     *            the progress monitor of null if no progress monitor is
     *            necessary.
     * @return
     */
    @SuppressWarnings("nls")
    public static Resource createResource(URI resourceURI,
            final Collection<? extends EObject> initialModel,
            IProgressMonitor progressMonitor) {
        if (progressMonitor == null) {
            progressMonitor = new NullProgressMonitor();
        }
        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        progressMonitor.beginTask("Craating resource.", 3);
        final ResourceSet resourceSet = editingDomain.getResourceSet();
        final Resource resource = resourceSet.createResource(resourceURI);
        AbstractTransactionalCommand command =
                new AbstractTransactionalCommand(editingDomain,
                        "Create Organization Model", Collections.EMPTY_LIST) {
                    @Override
                    protected CommandResult doExecuteWithResult(
                            IProgressMonitor monitor, IAdaptable info)
                            throws ExecutionException {

                        if (initialModel != null) {
                            resource.getContents().addAll(initialModel);
                        }
                        return CommandResult.newOKCommandResult();
                    }

                    @Override
                    public boolean canUndo() {
                        // Don't want this command to be undo-able
                        return false;
                    }

                };
        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new SubProgressMonitor(progressMonitor, 1),
                    null);
        } catch (ExecutionException e) {
            throw new RuntimeException("Unable to create model.", e); //$NON-NLS-1$
        }

        return resource;
    }

    /**
     * Returns typical set of options applied during EMF Resource save.
     * 
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

    /**
     * Finds and return the property view.
     * 
     * @return
     */
    @SuppressWarnings("nls")
    public static CommonNavigator getProjectView() {
        IViewPart viewPart =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage()
                        .findView("org.eclipse.ui.navigator.ProjectExplorer");

        if (viewPart instanceof CommonNavigator) {
            return (CommonNavigator) viewPart;
        }
        return null;
    }

    /**
     * Selects a tree item in the project explorer view.
     * 
     * @param segments
     */
    public static void selectPathInProjectExplorer(Object[] segments) {
        CommonNavigator projectExplorer = getProjectView();
        TreePath selectPath = new TreePath(segments);
        if (projectExplorer != null) {
            projectExplorer.setLinkingEnabled(true);
            ISelectionProvider selectionProvider =
                    projectExplorer.getSite().getSelectionProvider();
            selectionProvider.setSelection(new TreeSelection(selectPath));
        }
    }

    /**
     * Close the Welcome Page to see what happens on the workbench window.
     */
    public static void closeWelcomePage() {
        try {
            IViewReference welcomeViewRef =
                    PlatformUI
                            .getWorkbench()
                            .getActiveWorkbenchWindow()
                            .getActivePage()
                            .findViewReference("org.eclipse.ui.internal.introview"); //$NON-NLS-1$
            if (welcomeViewRef != null) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().hideView(welcomeViewRef);
            }
        } catch (Exception e) {
            // Intentionally ignored.
            e.printStackTrace();
        }
    }

    /**
     * Create a test meta model with diagram(s). This uses
     * {@link OrganizationModelDiagramEditorUtil} to create the Organization
     * Model (the utility used by the actual new Organization wizard).
     * 
     * @param addDefaultMetaModel
     *            <code>true</code> to apply the default meta model
     * @param applyStandardType
     *            <code>true</code> to apply the Standard Organization Type to
     *            the Organization.
     * @return
     * @throws PartInitException
     */
    public static OrgModel createModelWithDiagram(URI uri,
            Boolean addDefaultMetaModel, Boolean applyStandardType)
            throws PartInitException {
        // Create a default Organization Model with the default embedded schema
        Resource resource;
        OrgModel model = null;
        try {
            resource =
                    OrganizationModelDiagramEditorUtil.createDiagram(uri,
                            new NullProgressMonitor(),
                            getModelCreationParams(addDefaultMetaModel,
                                    applyStandardType));
            TestCase.assertNotNull("Resource was not created", resource); //$NON-NLS-1$
            EList<EObject> contents = resource.getContents();
            TestCase.assertEquals("Number of elements in the Resource", //$NON-NLS-1$
                    3,
                    contents.size());

            for (EObject content : contents) {
                if (content instanceof OrgModel) {
                    model = (OrgModel) content;
                }
            }
            TestCase
                    .assertNotNull("No organization model found in the resource", //$NON-NLS-1$
                            model);
            TestCase
                    .assertEquals("Number of Organization meta model in the model", //$NON-NLS-1$
                            0,
                            model.getMetamodels().size());
            TestCase.assertNotNull("No embedded meta model found", model //$NON-NLS-1$
                    .getEmbeddedMetamodel());

            // Wait for all background jobs to finish
            TestUtil.waitForJobs();

            // Open the diagram in an editor
            OrganizationModelDiagramEditorUtil.openDiagram(resource);

            // Wait for all background jobs to finish
            TestUtil.waitForJobs();
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return model;
    }

    /**
     * Get the Organization Model creation parameters to add the standard
     * embedded meta model and set the org model to the standard type.
     * 
     * @param addDefaultMetaModel
     * @param applyStandardType
     * @return
     */
    private static Map<String, Object> getModelCreationParams(
            Boolean addDefaultMetaModel, Boolean applyStandardType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params
                .put(OrganizationModelDiagramEditorUtil.CREATE_DEFAULT_METAMODEL_PARAM,
                        addDefaultMetaModel);
        params
                .put(OrganizationModelDiagramEditorUtil.APPLY_STANDARD_TYPE_PARAM,
                        applyStandardType);

        return params;
    }
}
