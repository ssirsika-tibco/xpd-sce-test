/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.AssociationDirectionType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aallway
 * 
 */
public class AssociationPropertySection extends
        AbstractNamedDiagramObjectSection {

    private Button direction[];

    public AssociationPropertySection() {
        super(Xpdl2Package.eINSTANCE.getAssociation());
        instrumentationPrefixName = "Association"; //$NON-NLS-1$
    }

    @Override
    protected void objectTypeCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        toolkit.createLabel(parent,
                Messages.AssociationPropertySection_Direction_label);

        Composite dirParent = toolkit.createComposite(parent);
        dirParent.setLayout(new GridLayout(4, false));

        direction = new Button[4];

        direction[0] =
                toolkit.createButton(dirParent,
                        Messages.AssociationPropertySection_None_label,
                        SWT.RADIO);
        direction[0].setData(AssociationDirectionType.NONE_LITERAL);
        direction[1] =
                toolkit.createButton(dirParent,
                        Messages.AssociationPropertySection_SrcTgt_label,
                        SWT.RADIO);
        direction[1].setData(AssociationDirectionType.TO_LITERAL);
        direction[2] =
                toolkit.createButton(dirParent,
                        Messages.AssociationPropertySection_TgtSrc_label,
                        SWT.RADIO);
        direction[2].setData(AssociationDirectionType.FROM_LITERAL);
        direction[3] =
                toolkit.createButton(dirParent,
                        Messages.AssociationPropertySection_Both_label,
                        SWT.RADIO);
        direction[3].setData(AssociationDirectionType.BOTH_LITERAL);

        direction[0].setData("name", "buttonNone"); //$NON-NLS-1$ //$NON-NLS-2$
        direction[1].setData("name", "buttonToTarget"); //$NON-NLS-1$ //$NON-NLS-2$
        direction[2].setData("name", "buttonToSource"); //$NON-NLS-1$ //$NON-NLS-2$
        direction[3].setData("name", "buttonBoth"); //$NON-NLS-1$ //$NON-NLS-2$

        for (Button btn : direction) {
            manageControl(btn);
        }

    }

    @Override
    protected Command objectTypeGetCommand(Object obj) {
        Association ass = getAssociation();
        if (ass != null) {
            if (obj instanceof Button) {
                AssociationDirectionType type =
                        (AssociationDirectionType) ((Button) obj).getData();

                CompoundCommand cmd = new CompoundCommand();
                cmd
                        .setLabel(Messages.AssociationPropertySection_SetAssDirection_menu);
                cmd.append(SetCommand.create(getEditingDomain(),
                        ass,
                        Xpdl2Package.eINSTANCE
                                .getAssociation_AssociationDirection(),
                        type));
                return cmd;
            }
        }
        return null;
    }

    @Override
    protected String objectTypeGetDescriptor() {
        return Messages.AssociationPropertySection_Association_label;
    }

    @Override
    protected void objectTypeRefresh() {
        Association ass = getAssociation();
        if (ass != null) {
            AssociationDirectionType type = ass.getAssociationDirection();

            boolean isCompensationAss =
                    Xpdl2ModelUtil.isCompensationAssociation(ass);

            for (Button btn : direction) {
                if (btn.getData().equals(type)) {
                    btn.setSelection(true);
                } else {
                    btn.setSelection(false);
                }

                /*
                 * Don't allow user to change direction for compensation
                 * associations.
                 */
                btn.setEnabled(!isCompensationAss);
            }
        }

        return;
    }

    /**
     * Return current input as an Association object.
     * 
     * @return
     */
    private Association getAssociation() {
        Object o = getInput();
        if (o instanceof Association) {
            return (Association) o;
        }
        return null;
    }
}
