/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.deploy.NamedElement;

/**
 * Adds the "Properties" action to the menu.
 * 
 */
public class PropertiesActionProvider extends CommonActionProvider {

    private PropertyDialogAction propertiesAction;
    private ISelectionProvider delegateSelectionProvider;

    @Override
    public void init(final ICommonActionExtensionSite aSite) {

        delegateSelectionProvider = new DelegateSelectionProvider(aSite
                .getViewSite().getSelectionProvider());
        propertiesAction = new PropertyDialogAction(new IShellProvider() {
            public Shell getShell() {
                return aSite.getViewSite().getShell();
            }
        }, delegateSelectionProvider);
        propertiesAction
                .setActionDefinitionId("org.eclipse.ui.file.properties"); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        super.fillContextMenu(menu);
        menu.appendToGroup(ICommonMenuConstants.GROUP_PROPERTIES,
                propertiesAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    @Override
    public void fillActionBars(IActionBars actionBars) {
        super.fillActionBars(actionBars);

        actionBars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(),
                propertiesAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
     */
    @Override
    public void setContext(ActionContext context) {
        super.setContext(context);

        propertiesAction.selectionChanged(delegateSelectionProvider
                .getSelection());

    }

    public class DelegateIAdaptable implements IAdaptable, IWorkbenchAdapter {

        private final Object delegate;

        private DelegateIAdaptable(Object o) {
            delegate = o;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
         */
        @SuppressWarnings("unchecked")
        public Object getAdapter(Class adapter) {
            if (adapter.isInstance(delegate)) {
                return delegate;
            } else if (adapter == IWorkbenchAdapter.class) {
                return this;
            }
            return Platform.getAdapterManager().getAdapter(delegate, adapter);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] getChildren(Object o) {
            return new Object[0];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ImageDescriptor getImageDescriptor(Object object) {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getLabel(Object o) {
            if (delegate instanceof NamedElement) {
                return ((NamedElement) delegate).getName();
            } else if (delegate != null) {
                return delegate.toString();
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParent(Object o) {
            return null;
        }
    }

    private class DelegateSelectionProvider implements ISelectionProvider {

        private final ISelectionProvider delegate;

        private DelegateSelectionProvider(ISelectionProvider s) {
            delegate = s;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
         */
        public void addSelectionChangedListener(
                ISelectionChangedListener listener) {
            delegate.addSelectionChangedListener(listener);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
         */
        public ISelection getSelection() {
            if (delegate.getSelection() instanceof IStructuredSelection) {
                IStructuredSelection sSel = (IStructuredSelection) delegate
                        .getSelection();
                if (sSel.getFirstElement() instanceof IAdaptable) {
                    return sSel;
                }

                return new StructuredSelection(new DelegateIAdaptable(sSel
                        .getFirstElement()));
            }
            return delegate.getSelection();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
         */
        public void removeSelectionChangedListener(
                ISelectionChangedListener listener) {
            delegate.removeSelectionChangedListener(listener);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
         */
        public void setSelection(ISelection selection) {
            delegate.setSelection(selection);

        }

    }

}
