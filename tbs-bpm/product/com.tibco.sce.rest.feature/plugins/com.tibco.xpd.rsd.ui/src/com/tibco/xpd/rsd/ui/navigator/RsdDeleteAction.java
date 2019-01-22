/*
 * Copyright (c) TIBCO Software Inc. 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.navigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rsd.RsdPackage;

/**
 * Project explorer delete action for the RSD Model.
 * 
 * @author jarciuch
 * @since 4 Mar 2015
 * 
 */
public class RsdDeleteAction extends DeleteAction {

    private static final Collection<? extends EStructuralFeature> DELETABLE_FEATURES =
            Arrays.asList(RsdPackage.eINSTANCE.getService_Resources(),
                    RsdPackage.eINSTANCE.getResource_Methods());

    public RsdDeleteAction(EditingDomain domain) {
        super(domain, /* removeAllReferences= */true);
        setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Command createCommand(Collection<?> selection) {
        if (!canDelete(selection)) {
            return UnexecutableCommand.INSTANCE;
        }
        Command cmd = super.createCommand(selection);
        String label = getLabel(cmd, selection);
        if (cmd instanceof AbstractCommand && label != null) {
            ((AbstractCommand) cmd).setLabel(label);
        }
        return cmd;
    }

    /**
     * Returns <code>true</code> if the selection can be deleted.
     * 
     * @param selection
     * @return
     */
    private boolean canDelete(Collection<?> selection) {
        if (selection != null && !selection.isEmpty()) {
            boolean anythingToDelete = false;
            List<Object> unwrapped = new ArrayList<>();
            for (Object o : selection) {
                Object object = AdapterFactoryEditingDomain.unwrap(o);
                unwrapped.add(object);
                if (object instanceof EObject) {
                    final EObject eObject = (EObject) object;
                    final EStructuralFeature containingFeature =
                            eObject.eContainingFeature();
                    if (containingFeature != null
                            && DELETABLE_FEATURES.contains(containingFeature)) {
                        anythingToDelete = true;
                        continue;
                    } else {
                        return false;
                    }
                }
            }
            if (anythingToDelete) {
                Command deleteCmd =
                        DeleteCommand.create(getEditingDomain(), unwrapped);
                return deleteCmd.canExecute();
            }
        }
        return false;
    }

    /**
     * Gets better label for a delete command.
     * 
     * @param cmd
     * @param selection
     */
    private String getLabel(Command cmd, Collection<?> selection) {
        return cmd.getLabel();
    }

}
