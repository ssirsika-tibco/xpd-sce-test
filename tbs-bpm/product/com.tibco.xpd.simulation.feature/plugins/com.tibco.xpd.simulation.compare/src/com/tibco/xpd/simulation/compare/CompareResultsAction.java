/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Opens a simulation comparison editor. Instances of this class would be
 * created as an object action delegate or full blown action.
 * 
 * @author nwilson, Jan Arciuchiewicz
 */
public class CompareResultsAction extends BaseSelectionListenerAction implements
        IObjectActionDelegate {

    /** Action identifier. */
    private static final String ID = ComparePlugin.PLUGIN_ID
            + ".CompareResultsAction"; //$NON-NLS-1$

    // TODO JA: Obtain value from parameters.
    private static final String SIMULATION_FOLDER = "Simulation"; //$NON-NLS-1$

    /** The resources to compare. */
    private IResource[] resources;

    /** The target workbench part for this action. */
    private IWorkbenchPart targetPart;

    /**
     * Constructor.
     */
    public CompareResultsAction() {
        super(Messages.CompareResultsAction_CompareSimulationResultsLabel);
        setId(ID);
        setImageDescriptor(ComparePlugin
                .getImageDescriptor("icons/SimulationCompare.gif"));//$NON-NLS-1$
    }

    /**
     * @param action
     *            The action being displayed.
     * @param targetPart
     *            The active workbench part.
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(
     *      org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart(final IAction action,
            final IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }

    /**
     * @param action
     *            The action selected.
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(final IAction action) {
        if (resources != null) {
            IWorkbenchPage page;
            if (targetPart != null) {
                page = targetPart.getSite().getPage();
            } else {
                IWorkbench wb = PlatformUI.getWorkbench();
                if (wb != null
                        && wb.getActiveWorkbenchWindow() != null
                        && wb.getActiveWorkbenchWindow().getActivePage() != null) {
                    page = wb.getActiveWorkbenchWindow().getActivePage();
                } else {
                    return;
                }
            }

            IEditorInput input = new CompareEditorInput(resources);
            String editorId = "com.tibco.xpd.simulation.compare.editor"; //$NON-NLS-1$
            try {
                IDE.openEditor(page, input, editorId);
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Runs this action.
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        run(this);
    }

    /**
     * @param action
     *            The action.
     * @param selection
     *            The new selection.
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(
     *      org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        HashSet<IResource> results = new HashSet<IResource>();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            for (Iterator i = sel.iterator(); i.hasNext();) {
                Object next = i.next();
                if (next instanceof IFile) {
                    IFile resource = (IFile) next;
                    if ("sim".equals(resource.getFileExtension())) { //$NON-NLS-1$
                        results.add(resource);
                    }
                }
                if (next instanceof Process) {
                    Process process = (Process) next;
                    results.addAll(getSimulationResults(process));
                }
            }
        }
        resources = new IResource[results.size()];
        results.toArray(resources);
    }

    protected boolean updateSelection(IStructuredSelection selection) {
        if (selection.size() == 1
                && selection.getFirstElement() instanceof Process) {
            selectionChanged(this, selection);
            return true;
        }
        return false;
    }

    private Collection<IResource> getSimulationResults(Process model) {
        Set<IResource> results = new LinkedHashSet<IResource>();
        WorkingCopy wc = (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                .getWorkingCopyFor(model);
        if (wc != null){
            IContainer simFolder = wc.getEclipseResources().get(0).getProject()
                    .getFolder(SIMULATION_FOLDER);
            String packageName = model.getPackage().getName().trim();
            String processName = model.getName().trim();
            IPath processPath = new Path(packageName).append(processName);
            IFolder resultsFolderPath = simFolder.getFolder(processPath);
            try {
                IResource[] members = resultsFolderPath.members();
                for (int i = 0; i < members.length; i++) {
                    IResource res = members[i];
                    if ("sim".equals(res.getFileExtension())) { //$NON-NLS-1$
                        String name = res.getClass().getName();
                        if (res instanceof IResource) {
                            results.add(res);
                        }
                    }
                }
            } catch (CoreException e) {
                // Folder doesn't exist. Ignore.
            }
        }
        return results;
    }

}
