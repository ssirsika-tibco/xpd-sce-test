/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author nwilson
 */
public abstract class AbstractTabSection extends AbstractEObjectSection {

    private CTabFolder tabFolder;

    private Collection<String> names;
    
    private Map<String, AbstractXpdSection> subSections;

    public AbstractTabSection(EClass eClass) {
        super(eClass);
        names = new ArrayList<String>();
        subSections = new HashMap<String, AbstractXpdSection>();
    }
    
    protected abstract void addTabSections();
    
    protected final void addTabSection(String name, AbstractXpdSection section) {
        names.add(name);
        subSections.put(name, section);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        CTabItem tabItem;

        addTabSections();
        
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        // Create tabs
        tabFolder = toolkit.createTabFolder(root, SWT.FLAT | SWT.MULTI);
        tabFolder.setBorderVisible(true);
        
        GridData gData = new GridData(GridData.FILL_BOTH);
        gData.widthHint = 10;
        gData.heightHint = 10;
        tabFolder.setLayoutData(gData);
        
        for (String name : names) {
            AbstractXpdSection section = subSections.get(name);
            tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
            tabItem.setText(name);
            tabItem.setControl(section.createControls(tabFolder, toolkit));

        }
        
        return root;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        // Update all subsections
        if (subSections != null) {
            for (AbstractXpdSection section : subSections.values()) {
                section.setInput(items);
            }
        }
    }

    @Override
    protected void doRefresh() {
        // Refresh all subsections
        if (subSections != null && tabFolder != null && !tabFolder.isDisposed()) {
            for (AbstractXpdSection section : subSections.values()) {
                section.refresh();
            }
        }
    }

    @Override
    public Command doGetCommand(Object obj) {
        // Do nothing here as this will be handled by the sub-sections
        return null;
    }

}
