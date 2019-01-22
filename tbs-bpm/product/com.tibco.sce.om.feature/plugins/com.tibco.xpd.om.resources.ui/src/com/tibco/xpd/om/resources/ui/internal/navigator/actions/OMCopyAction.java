/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.provider.TransientItemProvider;
import com.tibco.xpd.om.resources.ui.clipboard.OMClipboardHelper;
import com.tibco.xpd.om.resources.ui.internal.Messages;

/**
 * Copy action for the OM elements in the project explorer.
 * 
 * @author njpatel
 * 
 */
public class OMCopyAction extends CopyAction {

    public OMCopyAction(TransactionalEditingDomain ed) {
        super(ed);
    }

    @Override
    public Command createCommand(Collection<?> selection) {
        CompoundCommand ccmd = new CompoundCommand();

        if (selection != null && !selection.isEmpty()) {
            List<EObject> elems = new ArrayList<EObject>();

            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                Object next = iter.next();
                if (next instanceof EObject) {
                    elems.add((EObject) next);
                }
            }

            /*
             * If the selected semantic elements have corresponding notation
             * views that create a copy in the clipboard of these elements. This
             * will allow the user to paste into the editor after copying in the
             * project explorer.
             */
            if (!elems.isEmpty()) {
                List<View> views = new ArrayList<View>();
                for (EObject elem : elems) {
                    View view = getNotationObject(elem);
                    if (view != null) {
                        views.add(view);
                    }
                }

                if (!views.isEmpty()) {
                    ICommand copyCommand = OMClipboardHelper
                            .getInstance()
                            .getCopyCommand(
                                    (TransactionalEditingDomain) getEditingDomain(),
                                    Messages.OMCopyAction_copy_action,
                                    views.get(0), views);

                    if (copyCommand != null) {
                        ccmd
                                .append(new EMFOperationCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        copyCommand));
                    }
                }
            }
        }
        Command cmd = super.createCommand(selection);
        if (cmd != null) {
            ccmd.append(cmd);
        }
        return ccmd;
    }

    @Override
    public boolean updateSelection(IStructuredSelection selection) {
        /*
         * Disallow copy of OrgModel, logical group or human resource type.
         */
        if (selection != null && !selection.isEmpty()) {
            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                Object next = iter.next();

                if (next instanceof OrgModel
                        || next instanceof TransientItemProvider
                        || (next instanceof ResourceType && ((ResourceType) next)
                                .isHumanResourceType())) {
                    return false;
                }
            }
        }
        return super.updateSelection(selection);
    }

    /**
     * Get the notation view of the given semantic element.
     * 
     * @param eo
     * @return {@link View} or <code>null</code> if no notation found.
     */
    private View getNotationObject(EObject eo) {
        if (eo != null) {
            ECrossReferenceAdapter adapter = ECrossReferenceAdapter
                    .getCrossReferenceAdapter(eo);
            if (adapter != null) {
                Collection<Setting> references = adapter
                        .getInverseReferences(eo);

                if (references != null) {
                    for (Setting ref : references) {
                        if (ref.getEStructuralFeature() == NotationPackage.eINSTANCE
                                .getView_Element()
                                && ref.getEObject() instanceof View) {
                            return (View) ref.getEObject();
                        }
                    }
                }
            }
        }
        return null;
    }
}
