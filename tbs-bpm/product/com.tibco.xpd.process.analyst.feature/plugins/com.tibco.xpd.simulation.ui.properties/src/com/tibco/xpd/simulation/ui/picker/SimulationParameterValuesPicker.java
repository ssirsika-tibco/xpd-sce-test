/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.picker;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.provider.SimulationItemProviderAdapterFactory;
import com.tibco.xpd.simulation.ui.properties.PropertiesMessage;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectInclusionFilter;
import com.tibco.xpd.ui.resources.StatusInfo;

/**
 * Picker dialog for picking simulation parameter's value. The proper content
 * and label providers (usually model.edit AdapterFactoryLabelProvider and
 * AdapterFactoryContentProviders) must be provided during construction.
 * 
 * @author jarciuch
 */
public class SimulationParameterValuesPicker extends ElementTreeSelectionDialog implements ISelectionStatusValidator {

    private static AdapterFactory factory = new SimulationItemProviderAdapterFactory();
    /**
     * @param parent The parent shell.
     */
    public SimulationParameterValuesPicker(Shell parent) {
        super(parent, new AdapterFactoryLabelProvider(factory), new AdapterFactoryContentProvider(factory));
        Set<EClass> classes = new HashSet<EClass>();
        classes.add(SimulationPackage.eINSTANCE.getParameterDistribution());
        classes.add(SimulationPackage.eINSTANCE.getEnumerationValueType());
        addFilter(new EObjectInclusionFilter(classes));

        setTitle(PropertiesMessage.getString("SimulationParameterValuesPicker.Title")); //$NON-NLS-1$
        setMessage(PropertiesMessage.getString("SimulationParameterValuesPicker.Message")); //$NON-NLS-1$
        parent.setText(PropertiesMessage.getString("SimulationParameterValuesPicker.Text")); //$NON-NLS-1$

        setValidator(this);

    }
    public IStatus validate(Object[] selection) {
        IStatus status = new StatusInfo(Status.ERROR, null);
        if (selection.length == 1) {
            Object item = selection[0];
            if (item instanceof EnumerationValueType) {
                status = new StatusInfo();
            }
        }
        return status;
    }
}
