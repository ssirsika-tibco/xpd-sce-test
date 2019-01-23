/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewType;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.DescriptionStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section for the description tab of {@link Attribute} {@link OrgElement}, Note
 * and Text objects.
 * 
 * @author njpatel
 * 
 */
public class DescriptionSection extends AbstractTransactionalSection implements
        IFilter {

    private static final String DESCRIPTION_TYPE = "Description"; //$NON-NLS-1$

    private Font tableHeaderFont;

    private Text descTxt;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        Label label =
                toolkit.createLabel(root,
                        Messages.DescriptionSection_description_label);
        label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        label.setForeground(ColorConstants.darkGray);
        tableHeaderFont = new Font(root.getDisplay(), "Arial", 10, //$NON-NLS-1$
                SWT.BOLD);
        label.setFont(tableHeaderFont);

        descTxt = toolkit.createText(root, "", SWT.MULTI | SWT.WRAP, //$NON-NLS-1$
                "description-text"); //$NON-NLS-1$
        descTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        manageControlUpdateOnDeactivate(descTxt);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        if (obj == descTxt) {
            EObject input = getInput();
            String value = descTxt.getText();
            EAttribute eAttr = null;
            if (input instanceof View) {
                // Change to Note/Text details
                DescriptionStyle style = getDescriptionStyle((View) input);
                if (style != null) {
                    cmd =
                            SetCommand.create(getEditingDomain(),
                                    style,
                                    NotationPackage.eINSTANCE
                                            .getDescriptionStyle_Description(),
                                    value);
                }
            } else {
                if (input instanceof OrgElement) {
                    eAttr = OMPackage.eINSTANCE.getOrgElement_Description();
                } else if (input instanceof Attribute) {
                    eAttr = OMPackage.eINSTANCE.getAttribute_Description();
                }

                if (eAttr != null) {
                    cmd =
                            SetCommand.create(getEditingDomain(),
                                    input,
                                    eAttr,
                                    value != null && value.length() > 0 ? value
                                            : SetCommand.UNSET_VALUE);
                }
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (tableHeaderFont != null) {
            tableHeaderFont.dispose();
            tableHeaderFont = null;
        }
        super.dispose();
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EditPart) {
            EObject eo =
                    (EObject) ((EditPart) object).getAdapter(EObject.class);

            if (eo instanceof View) {
                View view = (View) eo;

                if (DESCRIPTION_TYPE.equals(view.getType())) {
                    view = ViewUtil.getViewContainer(view);
                }

                String type = view.getType();
                if (type != null
                        && (type.equals(ViewType.NOTE) || type
                                .equals(ViewType.TEXT))) {
                    return view;
                }
                return null;
            }
            return eo;
        }

        return super.resollveInput(object);
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        String desc = null;
        if (input instanceof OrgElement) {
            desc = ((OrgElement) input).getDescription();
        } else if (input instanceof Attribute) {
            desc = ((Attribute) input).getDescription();
        } else if (input instanceof View) {
            DescriptionStyle style = getDescriptionStyle((View) input);
            if (style != null) {
                desc = style.getDescription();
            }
        }

        updateText(descTxt, desc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);
        return eo instanceof OrgElement || eo instanceof Attribute
                || eo instanceof View;
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
