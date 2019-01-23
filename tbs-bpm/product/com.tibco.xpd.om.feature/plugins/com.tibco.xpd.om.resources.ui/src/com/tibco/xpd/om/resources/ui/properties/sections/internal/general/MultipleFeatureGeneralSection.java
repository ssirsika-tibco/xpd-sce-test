/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.core.om.MultipleFeature;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.MultiplicityUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * General properties section for a {@link MultipleFeature} object. This will
 * display the multiplicity control.
 * 
 * @author njpatel
 * 
 */
public class MultipleFeatureGeneralSection extends AbstractGeneralSection {

    private Text multiplicityTxt;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);
        createLabel(root, toolkit,
                Messages.MultipleFeatureGeneralSection_multiplicity_label);
        multiplicityTxt = toolkit.createText(root, "", "multiplicity-text"); //$NON-NLS-1$ //$NON-NLS-2$
        setLayoutData(multiplicityTxt);
        manageControlUpdateOnDeactivate(multiplicityTxt);
        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        if (obj == multiplicityTxt) {
            EObject input = getInput();
            if (input instanceof MultipleFeature) {
                cmd = MultiplicityUtil.createSetMultiplicityCommand(
                        getEditingDomain(), (MultipleFeature) input,
                        multiplicityTxt.getText());

                if (cmd == null) {
                    // Invalid input as command was not created so refresh
                    // section to re-display current multiplicity value
                    doRefresh();
                }
            }
        }
        return cmd;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof MultipleFeature) {
            updateText(multiplicityTxt, MultiplicityUtil
                    .getText((MultipleFeature) input));
        }
    }
}
