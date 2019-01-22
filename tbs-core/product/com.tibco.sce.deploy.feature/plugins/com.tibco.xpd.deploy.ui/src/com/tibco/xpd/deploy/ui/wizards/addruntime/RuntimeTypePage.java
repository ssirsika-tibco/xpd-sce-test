/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addruntime;

import java.util.List;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Page for selecting the runtime type.
 * <p>
 * <i>Created: 29 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RuntimeTypePage extends BaseAddRuntimeWizardPage {

    private ComboViewer runtimeTypeViewer;

    private boolean initialised = false;

    /**
     * Creates page's instance.
     */
    public RuntimeTypePage() {
        super("RuntimeTypePage"); //$NON-NLS-1$
        setTitle(Messages.RuntimeTypePage_Page_title);
        setDescription(Messages.RuntimeTypePage_Page_description);
    }

    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        Group runtimeTypeGroup = new Group(composite, SWT.NULL);
        runtimeTypeGroup.setText(Messages.RuntimeTypePage_RuntimeTypeGroup_label);
        GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
        runtimeTypeGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        runtimeTypeGroup.setLayout(paramGroupLayout);

        ServerManager serverManager = getAddRuntimeWizard().getServerManager();

        createLabel(runtimeTypeGroup, Messages.RuntimeTypePage_RuntimeType_label);
        Combo serverTypeCombo = createCombo(runtimeTypeGroup);
        runtimeTypeViewer = new ComboViewer(serverTypeCombo);
        runtimeTypeViewer.setContentProvider(new ArrayContentProvider());
        runtimeTypeViewer.setLabelProvider(new AdapterFactoryLabelProvider(
                serverManager.getAdapterFactory()));
        runtimeTypeViewer.setInput(serverManager.getServerContainer()
                .getRuntimeTypes());
        runtimeTypeViewer.addFilter(new ReferencedRuntimeTypeFilter());
        runtimeTypeViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        RuntimeType runtimeType = (RuntimeType) ((IStructuredSelection) event
                                .getSelection()).getFirstElement();
                        getAddRuntimeWizard().getRuntimeParametersPage()
                                .setRuntimeType(runtimeType);
                        setPageComplete(validatePage(true));
                    }
                });

        setPageComplete(validatePage(false));
        setControl(composite);
    }

    private void initializePageDefaults() {

        // set default runtime type
        RuntimeType defaultRuntimeType = getAddRuntimeWizard()
                .getServerManager().getServerContainer().getRuntimeTypeById(
                        ServerManager.DEFAULT_RUNTIME_TYPE_ID);
        if (defaultRuntimeType != null) {
            runtimeTypeViewer.setSelection(new StructuredSelection(
                    defaultRuntimeType));
        }

    }

    private boolean validatePage(boolean showMessages) {
        setMessage(null);
        setErrorMessage(null);

        if (getRuntimeType() == null) {
            if (showMessages) {
                setErrorMessage(Messages.RuntimeTypePage_UnselectedRuntimeType_message);
            }
            runtimeTypeViewer.getCombo().setFocus();
            return false;
        }
        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);

        return true;
    }

    public RuntimeType getRuntimeType() {
        return (RuntimeType) ((IStructuredSelection) runtimeTypeViewer
                .getSelection()).getFirstElement();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible == true && !initialised) {
            initializePageDefaults();
            initialised = true;
        }
        super.setVisible(visible);
    }

    private class ReferencedRuntimeTypeFilter extends ViewerFilter {
        @SuppressWarnings("unchecked")
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof RuntimeType) {
                ServerManager serverManager = getAddRuntimeWizard()
                        .getServerManager();
                RuntimeType runtimeType = (RuntimeType) element;
                for (ServerType serverType : (List<ServerType>) serverManager
                        .getServerContainer().getServerTypes()) {
                    if (runtimeType.equals(serverType.getRuntimeType())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

}
