/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.editor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CopyToClipboardCommand;
import org.eclipse.emf.edit.command.CutToClipboardCommand;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Global action handler for: copy, cut, paste operations on the RSD.
 * 
 * @author jarciuch
 * @since 20 Feb 2015
 */
public class CopyPasteActionHandler extends AbstractGlobalActionHandler {

    private static final Collection<EClass> SUPPORTED_SOURCE_ETYPES = Arrays
            .asList(RsdPackage.eINSTANCE.getMethod(),
                    RsdPackage.eINSTANCE.getResource());

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(IGlobalActionContext context) {
        if (isEmptySelection(context)) {
            return false;
        }
        TransactionalEditingDomain ed = getEditingDomain(context);
        if (ed == null) {
            return false;
        }
        if (GlobalActionId.CUT.equals(context.getActionId())) {
            Collection<?> sources =
                    getSources(context, SUPPORTED_SOURCE_ETYPES);
            if (!sources.isEmpty()) {
                Command command = CutToClipboardCommand.create(ed, sources);
                return command.canExecute();
            }
        } else if (GlobalActionId.COPY.equals(context.getActionId())) {
            Collection<?> sources =
                    getSources(context, SUPPORTED_SOURCE_ETYPES);
            if (!sources.isEmpty()) {
                Command command = CopyToClipboardCommand.create(ed, sources);
                return command.canExecute();
            }
        } else if (GlobalActionId.PASTE.equals(context.getActionId())) {
            Object target = getTarget(context);
            if (target != null) {
                Command command =
                        PasteFromClipboardCommand.create(ed, target, null);
                return command.canExecute();
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICommand getCommand(IGlobalActionContext context) {
        TransactionalEditingDomain ed = getEditingDomain(context);
        assert ed != null;
        if (GlobalActionId.CUT.equals(context.getActionId())) {
            Collection<?> sources =
                    getSources(context, SUPPORTED_SOURCE_ETYPES);
            Command command = CutToClipboardCommand.create(ed, sources);
            CompositeCommand cc =
                    new CompositeCommand(
                            Messages.CopyPasteActionHandler_CutCmd_label);
            cc.add(new EMFCommandOperation(ed, command));
            return cc;
        } else if (GlobalActionId.COPY.equals(context.getActionId())) {
            Collection<?> sources =
                    getSources(context, SUPPORTED_SOURCE_ETYPES);
            Command command = CopyToClipboardCommand.create(ed, sources);
            CompositeCommand cc =
                    new CompositeCommand(
                            Messages.CopyPasteActionHandler_CopyCmd_label);
            cc.add(new EMFCommandOperation(ed, command));
            return cc;
        } else if (GlobalActionId.PASTE.equals(context.getActionId())) {
            Object target = getTarget(context);
            Command command =
                    PasteFromClipboardCommand.create(ed, target, null);
            CompositeCommand cc =
                    new CompositeCommand(
                            Messages.CopyPasteActionHandler_PasteCmd_label);
            cc.add(new EMFCommandOperation(ed, command));
            return cc;
        }
        return null;
    }

    /**
     * Gets a collection of sources, only if selected elements are of the same
     * type and one of the supported EClasses and empty collection otherwise.
     * 
     * @param context
     *            the action context (containing selection).
     * @param supported
     *            a collection of supported EClasses.
     * @return a collection of sources, only if selected elements are of the
     *         same type and one of the supported EClasses and empty collection
     *         otherwise.
     */
    private Collection<?> getSources(IGlobalActionContext context,
            Collection<EClass> supported) {
        ISelection selection = context.getSelection();
        if (selection instanceof IStructuredSelection
                && !((IStructuredSelection) selection).isEmpty()) {
            List<?> elements = ((IStructuredSelection) selection).toList();
            EClass commonType = getSupportedClass(elements.get(0), supported);
            if (commonType != null) {
                Set<EClass> supporetedType = Collections.singleton(commonType);
                for (Object o : elements) {
                    if (commonType != getSupportedClass(o, supporetedType)) {
                        return Collections.emptyList();
                    }
                    ;
                }
                return elements;
            }
        }
        return Collections.emptyList();
    }

    /**
     * Returns first EClass from the supported collection which o is an instance
     * of. Returns <code>null</code> if none of the class is the o EClass.
     * 
     * @param o
     *            the object to check.
     * @param supported
     *            a collection of supported EClasses
     * @return first EClass from the supported collection which o is an instance
     *         of. Returns <code>null</code> if none of the class is the o
     *         EClass.
     */
    private EClass getSupportedClass(Object o, Collection<EClass> supported) {
        if (o instanceof EObject) {
            for (EClass ec : supported) {
                if (ec.isInstance(o)) {
                    return ec;
                }
            }
        }
        return null;
    }

    /**
     * Gets the target for the action.
     * 
     * @param context
     *            the action context (containing current selection).
     * @return the first element in the context selection or <code>null</code>
     *         if nothing is selected.
     */
    private Object getTarget(IGlobalActionContext context) {
        Object target = null;
        ISelection selection = context.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;
            if (ss.size() == 1) {
                target = ss.getFirstElement();
            }
        }
        return target;
    }

    /**
     * Gets an {@link TransactionalEditingDomain} for the context.
     */
    private TransactionalEditingDomain getEditingDomain(
            IGlobalActionContext context) {
        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    /**
     * Checks if the context selection is empty.
     */
    private boolean isEmptySelection(IGlobalActionContext context) {
        if (context.getSelection() instanceof IStructuredSelection) {
            return ((IStructuredSelection) context.getSelection()).isEmpty();
        }
        return true;
    }

}
