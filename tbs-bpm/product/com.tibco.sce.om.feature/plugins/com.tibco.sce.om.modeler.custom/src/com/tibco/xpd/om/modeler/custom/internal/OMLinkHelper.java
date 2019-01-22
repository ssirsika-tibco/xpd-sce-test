/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.internal;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;
import com.tibco.xpd.ui.projectexplorer.AbstractProjectExplorerLinkHelper;

/**
 * Organization Model editor link helper.
 * 
 * @author njpatel
 * 
 */
public class OMLinkHelper extends AbstractProjectExplorerLinkHelper {

    private final TransactionalEditingDomain ed = XpdResourcesPlugin
            .getDefault().getEditingDomain();

    @Override
    protected Object findMainSelection(IEditorInput editorInput) {
        if (editorInput instanceof URIEditorInput) {
            URI uri = ((URIEditorInput) editorInput).getURI();
            if (uri != null) {
                EObject eo = ed.getResourceSet().getEObject(uri, false);
                if (eo instanceof View) {
                    eo = ((View) eo).getElement();
                }
                return eo;
            }
        }
        return null;
    }

    @Override
    protected boolean isChild(Object selObject, Object mainSelection) {
        if (selObject != mainSelection) {
            if (selObject instanceof OrgModel && mainSelection instanceof IFile) {
                return true;
            }

            if (selObject instanceof EObject
                    && mainSelection instanceof EObject) {
                if (((EObject) selObject).eContainer() == mainSelection) {
                    return true;
                }

                return isChild(((EObject) selObject).eContainer(),
                        mainSelection);
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ui.navigator.ILinkHelper#activateEditor(org.eclipse.ui.
     * IWorkbenchPage, org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void activateEditor(IWorkbenchPage page,
            IStructuredSelection selection) {
        Object element = selection.getFirstElement();

        if (element instanceof OrgUnit || element instanceof Position) {
            EObject eo = (EObject) element;

            // Find the parent organization
            while (eo != null && !(eo instanceof Organization)) {
                eo = eo.eContainer();
            }
            element = eo;
        }

        if (element instanceof Organization) {
            WorkingCopy wc = WorkingCopyUtil
                    .getWorkingCopyFor((EObject) element);

            if (wc instanceof AbstractGMFWorkingCopy) {
                List<Diagram> diagrams = ((AbstractGMFWorkingCopy) wc)
                        .getDiagrams();

                for (Diagram diagram : diagrams) {
                    if (diagram.getElement() == element) {
                        URI uri = EcoreUtil.getURI(diagram);
                        URIEditorInput input = new URIEditorInput(uri);

                        IEditorPart editor = page.findEditor(input);

                        if (editor != null) {
                            page.bringToTop(editor);
                        }
                        break;
                    }
                }
            }
        }
    }
}
