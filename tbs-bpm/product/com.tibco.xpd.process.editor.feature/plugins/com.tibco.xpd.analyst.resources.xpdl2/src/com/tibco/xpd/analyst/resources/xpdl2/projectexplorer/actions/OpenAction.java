/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;

/**
 * Project Explorer's Open action
 * 
 * @author njpatel
 * 
 */
public class OpenAction extends BaseSelectionListenerAction {

    /**
     * The id of this action.
     */
    public static final String ID =
            Xpdl2ResourcesPlugin.PLUGIN_ID + ".OpenAction";//$NON-NLS-1$

    /**
     * Open Action
     */
    public OpenAction() {
        super(Messages.OpenAction_Menu_title);
        setId(ID);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    public void run() {
        IStructuredSelection selection = getStructuredSelection();
        for (Iterator iter = selection.iterator(); iter.hasNext();) {
            Object element = iter.next();

            // Open action only available for a Process
            if (element instanceof NamedElement) {
                NamedElement namedElement = (NamedElement) element;

                IConfigurationElement facConfig =
                        XpdResourcesUIActivator
                                .getEditorFactoryConfigFor(namedElement);

                if (facConfig != null) {

                    String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
                    IWorkbenchPage page =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage();

                    // Open process editor
                    try {
                        EditorInputFactory f =
                                (EditorInputFactory) facConfig
                                        .createExecutableExtension("factory"); //$NON-NLS-1$
                        IEditorInput input = f.getEditorInputFor(namedElement);
                        
                        IEditorPart editor = page.openEditor(input, editorID);
                        if (editor instanceof IGotoEObject) {
                            ((IGotoEObject)editor).gotoEObject(true, namedElement);
                        }
                        
                    } catch (PartInitException e) {
                        // ignore
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Static function to activate the open action. This is used, for example,
     * by the Project Explorer when the Process selection is double-clicked.
     * 
     * @param selection
     */
    public static void run(IStructuredSelection selection) {
        OpenAction openAction = new OpenAction();
        openAction.selectionChanged(selection);

        // Only allow open for Process objects
        if (!selection.isEmpty()) {
            openAction.run();
        }
    }

}
