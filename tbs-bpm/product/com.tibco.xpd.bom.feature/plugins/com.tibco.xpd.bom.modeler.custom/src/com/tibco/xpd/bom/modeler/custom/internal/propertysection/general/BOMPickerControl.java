/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * Extension to the {@link AbstractPickerControl} that provides a hyperlink
 * control for the properties view. This extension selects the item in the BOM
 * editor when a user clicks on the hyperlink.
 * 
 * @author njpatel
 * 
 * @param <T>
 */
public abstract class BOMPickerControl<T extends EObject> extends
        AbstractPickerControl<T> {

    public BOMPickerControl(Composite parent, int style,
            XpdFormToolkit toolkit, EditingDomain editingDomain) {
        super(parent, style, toolkit, editingDomain);
    }

    public BOMPickerControl(Composite parent, int style,
            XpdFormToolkit toolkit, EditingDomain editingDomain,
            boolean showClear) {
        super(parent, style, toolkit, editingDomain, showClear);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.properties.components.AbstractPickerControl#
     * hyperlinkActivated(java.lang.Object)
     */
    protected void hyperlinkActivated(EObject value) {
        if (value != null) {
            if (!openInEditor(value)) {
                selectInProjectExplorer(value);
            }
        }
    }

}
