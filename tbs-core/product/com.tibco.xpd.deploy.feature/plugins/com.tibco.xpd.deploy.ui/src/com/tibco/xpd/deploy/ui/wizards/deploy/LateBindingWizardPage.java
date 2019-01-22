/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.LateBindingSupport;
import com.tibco.xpd.deploy.model.extension.ResourceBinding;
import com.tibco.xpd.deploy.model.extension.SharedResource;
import com.tibco.xpd.deploy.ui.components.LateBindingControl;
import com.tibco.xpd.deploy.ui.components.LateBindingDefaultControl;

/**
 * The wizard page providing and operating on late binding control. Will provide
 * <p>
 * <i>Created: 10 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class LateBindingWizardPage extends WizardPage {

    private boolean initialised = false;

    private LateBindingControl lateBindingControl;

    private List<URL> currentBoundModules = Collections.emptyList();

    /**
     * @param pageName
     */
    public LateBindingWizardPage() {
        super("LateBinding"); //$NON-NLS-1$
        setTitle("Late Binding Details");
        setDescription("Provide mappings for resources.");
    }

    public void createControl(Composite parent) {
        lateBindingControl = createLateBindingControl(parent);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(lateBindingControl,
                getTitle());
        setControl(lateBindingControl);
    }

    protected LateBindingControl createLateBindingControl(Composite parent) {
        Collection<ResourceBinding> bindings = getBindings();
        Collection<SharedResource> sharedResources = getSharedResources();
        return new LateBindingDefaultControl(parent, bindings, sharedResources) {
            /** {@inheritDoc} */
            @Override
            public void setMessage(String newMessage, int newType) {
                LateBindingWizardPage.this.setMessage(newMessage, newType);
            }

            /** {@inheritDoc} */
            @Override
            protected void setControlComplete(boolean isComplete) {
                super.setControlComplete(isComplete);
                LateBindingWizardPage.this.setPageComplete(isComplete);
            }
        };
    }

    /**
     * @return
     */
    protected Collection<SharedResource> getSharedResources() {
        if (getWizard() instanceof IBindingSupportWizard) {
            Server server = ((IBindingSupportWizard) getWizard()).getServer();
            Object adapter =
                    server.getConnection().getAdapter(LateBindingSupport.class);
            if (server.getConnection() != null
                    && adapter instanceof LateBindingSupport) {
                return ((LateBindingSupport) adapter)
                        .getSharedResources(Collections.emptyMap());
            }
        }
        return Collections.emptyList();
    }

    /**
     * @return
     */
    protected Collection<ResourceBinding> getBindings() {
        if (getWizard() instanceof IBindingSupportWizard) {
            return ((IBindingSupportWizard) getWizard()).getBindings();
        }
        return Collections.emptySet();
    }

    protected void initializePageDefaults() {
        lateBindingControl.initializeControlDefaults();
        initialised = true;

    }

    @Override
    public void setVisible(boolean visible) {
        if (visible == true) {
            if (!initialised) {
                initializePageDefaults();
            }
            Collection<ResourceBinding> bindings = updateBindings();
            if (bindings.isEmpty()) {
                IWizardPage previousPage = getPreviousPage();
                IWizardPage wizardPreviousPage =
                        getWizard().getPreviousPage(this);
                IWizardPage nextPage = getNextPage();
                if (nextPage != null && previousPage != nextPage) { // forward
                    getWizard().getContainer().showPage(nextPage);
                    nextPage.setPreviousPage(wizardPreviousPage);
                    return;
                } else if (wizardPreviousPage != null) { // backward
                    setPreviousPage(wizardPreviousPage);
                    IWizardPage pPage = wizardPreviousPage.getPreviousPage();
                    getWizard().getContainer().showPage(wizardPreviousPage);
                    wizardPreviousPage.setPreviousPage(pPage);
                    return;
                }

            }
        }
        super.setVisible(visible);
    }

    public Collection<ResourceBinding> updateBindings() {
        if (getWizard() instanceof IBindingSupportWizard) {
            IBindingSupportWizard wiz = (IBindingSupportWizard) getWizard();
            List<URL> modulesUrls = wiz.getModulesUrls();
            wiz.setContextModules(modulesUrls);
            currentBoundModules = modulesUrls;

        }
        Collection<ResourceBinding> bindings = getBindings();
        lateBindingControl.updateBindings(bindings);
        return bindings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPageComplete() {
        IBindingSupportWizard wiz = null;
        if (getWizard() instanceof IBindingSupportWizard) {
            wiz = (IBindingSupportWizard) getWizard();
        }
        return super.isPageComplete() && lateBindingControl.isControlComplete()
                && wiz != null
                && currentBoundModules.containsAll(wiz.getModulesUrls());
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

}
