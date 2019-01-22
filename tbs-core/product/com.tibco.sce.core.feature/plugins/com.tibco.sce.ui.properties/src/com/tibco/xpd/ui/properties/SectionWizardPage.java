/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabItem;

import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.properties.internal.util.XpdPropertiesUtil;

/**
 * @author wzurek
 * 
 */
public class SectionWizardPage extends AbstractXpdWizardPage implements DisposeListener {

    private ITabItem tabDescriptor = null;

    private AbstractXpdSection[] xpdSections = null;

    private AdapterFactory adapterFactory;

    private boolean flipCall = false;

    private boolean refreshOnSetVisible = false;

    /**
     * Constructor.
     * 
     * @param name
     * @param descriptor
     * @param inputContainerProvider
     */
    public SectionWizardPage(String name, ITabItem descriptor,
            XpdSectionInputContainerProvider inputContainerProvider) {
        super(name);
        this.xpdSections = new AbstractXpdSection[0];

        this.tabDescriptor = descriptor;
        if (descriptor != null) {
            ISection[] sections = XpdPropertiesUtil.getSections(descriptor);

            if (sections != null) {
                AbstractXpdSection sect = null;

                List<AbstractXpdSection> sectionsToAdd =
                        new ArrayList<AbstractXpdSection>();

                // Add the XpdSections to the list, setting the section
                // container
                // while doing so
                for (int x = 0; x < sections.length; x++) {
                    if (sections[x] instanceof AbstractXpdSection) {
                        sect = (AbstractXpdSection) sections[x];

                        // Check if this section can be added to the wizard page
                        if (sect.canShowInWizard()) {
                            sect
                                    .setXpdSectionContainerProvider(inputContainerProvider);
                            sectionsToAdd.add(sect);
                        }
                    }
                }

                if (!sectionsToAdd.isEmpty()) {
                    // Copy the XpdSections to XpdSections
                    xpdSections =
                            sectionsToAdd
                                    .toArray(new AbstractXpdSection[sectionsToAdd
                                            .size()]);
                } else {
                    throw new IllegalArgumentException(
                            "No XPD Sections in this tab."); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * Constructor.
     * 
     * @param name
     * @param sections
     */
    public SectionWizardPage(String name, AbstractXpdSection[] sections) {
        super(name);
        this.xpdSections =
                sections != null ? sections : new AbstractXpdSection[0];

    }

    /**
     * Get the tab descriptor.
     * 
     * @return
     */
    public ITabItem getTabDescriptor() {
        return tabDescriptor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {
        XpdWizardToolkit toolkit = new XpdWizardToolkit(parent);
        Composite ctrl = toolkit.createComposite(parent);
        ctrl.setLayout(new GridLayout());

        for (AbstractXpdSection section : xpdSections) {
            Control control = section.createControls(ctrl, toolkit);
            int style =
                    (section.shouldUseExtraSpace()) ? GridData.FILL_BOTH
                            : GridData.FILL_HORIZONTAL;
            GridData data = new GridData(style);
            data.heightHint = section.getMinimumHeight();
            control.setLayoutData(data);
        }

        setControl(ctrl);

        ctrl.addDisposeListener(this);
    }

    /**
     * Set the input of this wizard page.
     * 
     * @param input
     */
    public void setInput(EObject input) {
        if (adapterFactory instanceof IDisposable) {
            ((IDisposable) adapterFactory).dispose();
        }
        adapterFactory =
                new ComposedAdapterFactory(EMFEditPlugin
                        .getComposedAdapterFactoryDescriptorRegistry());
        AdapterFactoryEditingDomain ed =
                new AdapterFactoryEditingDomain(adapterFactory,
                        new BasicCommandStack());
        for (int i = 0; i < xpdSections.length; i++) {
            if (xpdSections[i] instanceof AbstractEObjectSection) {
                ((AbstractEObjectSection) xpdSections[i]).setEditingDomain(ed);
            }
            xpdSections[i].setInput(Collections.singleton(input));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt
     * .events.DisposeEvent)
     */
    public void widgetDisposed(DisposeEvent e) {
        if (adapterFactory instanceof IDisposable) {
            ((IDisposable) adapterFactory).dispose();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.DialogPage#dispose()
     */
    public void dispose() {
        super.dispose();
        for (AbstractXpdSection section : xpdSections) {
            section.dispose();
        }
    }

    /**
     * The <code>WizardPage</code> implementation of this
     * <code>IWizardPage</code> method returns <code>true</code> if this page is
     * complete (<code>isPageComplete</code>) and there is a next page to flip
     * to. Subclasses may override (extend or reimplement).
     * 
     * @see #getNextPage
     * @see #isPageComplete
     */
    public boolean canFlipToNextPage() {
        boolean b = false;
        try {
            setFlipCall(true);
            b = isPageComplete() && getNextPage() != null;
        } finally {
            setFlipCall(false);
        }
        return b;
    }

    /**
     * Set the flag to indicate can flip to next page has been called.
     * 
     * @param value
     */
    private void setFlipCall(boolean value) {
        flipCall = value;
    }

    /**
     * Check whether a call to can flip to next page is called.
     * 
     * @return
     */
    public boolean isFlipCall() {
        return flipCall;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.WizardPage#getPreviousPage()
     */
    public IWizardPage getPreviousPage() {
        IWizard wizard2 = getWizard();
        if (wizard2 == null) {
            return null;
        }
        return wizard2.getPreviousPage(this);
    }

    /**
     * Set whether a refresh should be performed when this page is stepped onto
     * by the user.
     * 
     * @param refreshOnSetVisible
     * @since 3.1
     */
    public void setRefreshOnSetVisible(boolean refreshOnSetVisible) {
        this.refreshOnSetVisible = refreshOnSetVisible;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        
        if (refreshOnSetVisible && visible) {
            for (int i = 0; i < xpdSections.length; i++) {
                xpdSections[i].refresh();
            }            
        }
        
        return;
    }
    
}
