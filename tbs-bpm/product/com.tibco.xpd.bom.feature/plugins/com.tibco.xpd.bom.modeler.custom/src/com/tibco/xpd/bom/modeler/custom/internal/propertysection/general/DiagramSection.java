/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * General section for a {@link Diagram}. This will allow the setting of the
 * Diagram name.
 * 
 * @author njpatel
 * 
 */
public class DiagramSection extends AbstractGeneralSection {

    private Text nameTxt;

    @Override
    protected boolean shouldDisplay(EObject eo) {
        return eo instanceof Diagram && BomUIUtil.isUserDiagram((Diagram) eo);
    }

    @Override
    protected Command doGetCommand(Object obj) {
        if (obj == nameTxt) {
            String text = nameTxt.getText();
            if (text.length() > 0) {
                return SetCommand.create(getEditingDomain(),
                        getInput(),
                        NotationPackage.eINSTANCE.getDiagram_Name(),
                        text);
            } else {
                // Refresh to reset the diagram name
                refresh();
            }
        }
        return null;
    }

    @Override
    protected EObject resollveInput(Object object) {
        Diagram diagram = null;
        if (object instanceof Diagram) {
            diagram = (Diagram) object;
        } else if (object instanceof EditPart) {
            Object model = ((EditPart) object).getModel();
            if (model instanceof Diagram) {
                diagram = (Diagram) model;
            } else if (model instanceof View) {
                // In case of badge on the user diagram
                EObject element = ((View) model).getElement();
                if (element instanceof Diagram) {
                    diagram = (Diagram) element;
                }
            }
        }

        return diagram;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof Diagram && nameTxt != null
                && !nameTxt.isDisposed()) {
            updateText(nameTxt, ((Diagram) input).getName());
        }
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.DiagramSection_diagram_label);
        nameTxt = toolkit.createText(root, "");//$NON-NLS-1$
        nameTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        manageControlUpdateOnDeactivate(nameTxt);
        return root;
    }

}
