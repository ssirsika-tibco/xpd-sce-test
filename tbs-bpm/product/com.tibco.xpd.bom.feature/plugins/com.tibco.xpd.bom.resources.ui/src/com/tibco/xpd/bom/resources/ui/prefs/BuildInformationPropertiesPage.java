/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.prefs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.utils.BOMUtils;

/**
 * Build version information properties page for the Business Data Project. This
 * will display the currently set timestamp in the project preference.
 * 
 * @author njpatel
 */
public class BuildInformationPropertiesPage extends PropertyPage implements
        IWorkbenchPropertyPage {

    public BuildInformationPropertiesPage() {
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout(2, false));

        Label lbl = new Label(root, SWT.NONE);
        lbl.setText(Messages.BuildInformationPropertiesPage_buildVersionTimestamp_label);

        Text txt = new Text(root, SWT.NONE | SWT.READ_ONLY | SWT.BORDER);
        txt.setText(getBuildVersion());
        txt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        // Show description in second column
        new Label(root, SWT.NONE);
        Label desc = new Label(root, SWT.WRAP);
        desc.setText(Messages.BuildInformationPropertiesPage_buildVersionTimestamp_longdesc);
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        layoutData.widthHint = 125;
        desc.setLayoutData(layoutData);

        return root;
    }

    /**
     * Get the currently selected Business Data project's build version.
     * 
     * @return
     */
    private String getBuildVersion() {
        IAdaptable element = getElement();
        if (element instanceof IProject) {
            IProject project = (IProject) element;

            if (BOMUtils.isBusinessDataProject(project)) {
                return BOMUtils.getBuildVersionTimeStamp(project);
            }
        }
        return ""; //$NON-NLS-1$
    }

}
