/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewType;
import org.eclipse.gmf.runtime.notation.DescriptionStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for the Note Description tab.
 * 
 */
public class NoteTabSection extends AbstractTransactionalSection implements
        IFilter {

    private Text desc;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(1, false));

        Label label =
                toolkit.createLabel(root,
                        Messages.DescriptionPropertySection_desc_label);
        label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        desc = toolkit.createText(root, "", //$NON-NLS-1$
                SWT.WRAP | SWT.MULTI | SWT.V_SCROLL,
                Messages.DescriptionPropertySection_desc_text_label);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.widthHint = 10;
        gd.heightHint = 10;
        desc.setLayoutData(gd);
        manageControlUpdateOnDeactivate(desc);
        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Only process commands for our own description
        if (obj == desc) {
            EObject input = getInput();
            if (input instanceof View) {
                DescriptionStyle style = getDescriptionStyle((View) input);
                if (style != null) {
                    // Create the command to read the data in the dialog and set
                    // it into the description
                    return SetCommand.create(getEditingDomain(),
                            style,
                            NotationPackage.eINSTANCE
                                    .getDescriptionStyle_Description(),
                            desc.getText());
                }
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject eo = getInput();
        // On a refresh we read the existing description and update the dialog
        if (eo instanceof View) {
            DescriptionStyle style = getDescriptionStyle((View) eo);
            if (style != null) {
                desc.setText(style.getDescription());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        // We want to deal with the Notes (NoteEditPart) and Text (TextEditPart)
        // items so check the view type of these
        if (toTest instanceof IAdaptable) {
            EObject eo =
                    (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);

            if (eo instanceof View) {
                View view = (View) eo;
                String type = view.getType();
                if (type != null
                        && (type.equals(ViewType.NOTE) || type
                                .equals(ViewType.TEXT))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof IAdaptable) {
            EObject eo =
                    (EObject) ((IAdaptable) object).getAdapter(EObject.class);

            if (eo instanceof View) {
                View view = (View) eo;
                String type = view.getType();
                if (type != null
                        && (type.equals(ViewType.NOTE) || type
                                .equals(ViewType.TEXT))) {
                    eo = view;
                }
            }
            return eo;
        }
        return super.resollveInput(object);
    }

    /**
     * Get the {@link DescriptionStyle} of the given view.
     * 
     * @param view
     * @return style or <code>null</code> if not found.
     */
    private DescriptionStyle getDescriptionStyle(View view) {
        if (view != null) {
            return (DescriptionStyle) view.getStyle(NotationPackage.eINSTANCE
                    .getDescriptionStyle());
        }
        return null;
    }
}
