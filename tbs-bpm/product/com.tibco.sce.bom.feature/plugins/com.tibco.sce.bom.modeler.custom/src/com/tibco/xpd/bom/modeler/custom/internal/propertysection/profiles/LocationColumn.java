/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.profiles;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;

/**
 * A non-editable file location column for the {@link Profile}s and
 * {@link Stereotype}s table.
 * 
 * @author njpatel
 * 
 */
/* public */class LocationColumn extends AbstractColumn {

    private final ILabelProvider workbenchLabelProvider;

    /**
     * File location column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     */
    public LocationColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        super(editingDomain, viewer, SWT.NONE,
                Messages.LocationColumn_locationColumn_title, 450);
        workbenchLabelProvider = new WorkbenchLabelProvider();
        setShowImage(true);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        // Can't edit this column
        return null;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        // Can't edit this column
        return null;
    }

    @Override
    protected String getText(Object element) {
        String txt = null;

        if (element instanceof EObject) {
            EObject eo = (EObject) element;
            if (eo.eIsProxy()) {
                URI uri = ((InternalEObject) eo).eProxyURI().trimFragment();
                if (uri != null) {
                    String value = uri.toString();
                    if (uri.isPlatformResource()) {
                        value = uri.toPlatformString(true);
                    }
                    txt =
                            String
                                    .format(Messages.LocationColumn_unresolvedReferenceWithFilePath_shortdesc,
                                            value);
                }
            } else {
                IFile file = getFile(eo);
                if (file != null) {
                    txt = file.getFullPath().makeRelative().toString();
                }
            }
        }

        return txt != null ? txt
                : Messages.LocationColumn_unresolvedReference_label;
    }

    @Override
    protected Image getImage(Object element) {
        Image img = null;
        if (element instanceof EObject) {
            IFile file = getFile((EObject) element);
            if (file != null) {
                img = workbenchLabelProvider.getImage(file);
            }
        }
        return img != null ? img : null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        // Can't edit this column
        return null;
    }

    @Override
    protected void dispose() {
        workbenchLabelProvider.dispose();
        super.dispose();
    }

    /**
     * Get the file that contains the given EObject.
     * 
     * @param eo
     * @return
     */
    private IFile getFile(EObject eo) {
        IFile file = null;
        if (eo != null && eo.eResource() != null) {
            file = WorkspaceSynchronizer.getFile(eo.eResource());
        }
        return file;
    }
}