package com.tibco.xpd.xpdl2.edit.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Provides method for opening, activiating editor and then selecting and
 * highlighing given EObject
 * 
 * @author aallway
 * 
 */
public class RevealProcessDiagramEObject {

    /**
     * Given just an xpdl EObject for a process diagram object, open the process
     * editor for it, activate it then reveal, highlight and select the object.
     * 
     * @param eObj
     * @return success or failure (could not open editor or couldn't find visual
     *         part for eobject)
     */
    public static boolean revealEObject(IWorkbenchSite site, EObject eObject,
            boolean selectObject) {
        return revealEObject(site.getPage(), eObject, selectObject);
    }

    public static boolean revealEObject(IWorkbenchPage page, EObject eObject,
            boolean selectObject) {

        /*
         * Try and open the top level object first (otherwie the package editor
         * can get opened as well as the process editor for the actual object).
         */
        try {
            EObject editorObject = eObject;

            IConfigurationElement facConfig =
                    XpdResourcesUIActivator.getEditorFactoryConfigFor(eObject);

            if (facConfig == null) {
                editorObject =
                        Xpdl2ModelUtil.getAncestor(eObject, Process.class);

                if (editorObject == null) {
                    editorObject =
                            Xpdl2ModelUtil.getAncestor(eObject,
                                    ProcessInterface.class);

                }

                if (editorObject != null) {
                    facConfig =
                            XpdResourcesUIActivator
                                    .getEditorFactoryConfigFor(editorObject);
                }
            }

            if (facConfig != null) {
                String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$

                if (editorID != null && editorID.length() > 0) {
                    EditorInputFactory f =
                            (EditorInputFactory) facConfig
                                    .createExecutableExtension("factory"); //$NON-NLS-1$
                    if (f != null) {
                        IEditorInput input = f.getEditorInputFor(editorObject);

                        if (input != null) {
                            IEditorPart editor =
                                    page.openEditor(input, editorID);

                            if (editor instanceof IGotoEObject) {
                                return ((IGotoEObject) editor)
                                        .gotoEObject(selectObject, eObject);

                            }
                        }
                    }
                }
            }

        } catch (CoreException e) {
        }

        /*
         * Ok, not an eObject with it's own editor, just open the editor for the
         * file.
         */
        IFile file = WorkingCopyUtil.getFile(eObject);

        if (file != null && file.exists()) {

            try {
                IEditorPart editor = IDE.openEditor(page, file);

                if (editor instanceof IGotoEObject) {
                    return ((IGotoEObject) editor).gotoEObject(selectObject,
                            eObject);

                }
            } catch (PartInitException e) {
                // e.printStackTrace();
            }
        }

        return false;
    }
}
