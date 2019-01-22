/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Report additional data that will be added to a user task's process to match
 * the newly referenced pageflow process.
 * 
 * @author aallway
 * @since 3.2
 */
public class ReportAdditionalPageflowDataWizard extends Wizard {
    private Activity userTaskActivity;

    private Process pageflowProcess;

    private List<ProcessRelevantData> dataToAdd;

    public ReportAdditionalPageflowDataWizard(Activity userTaskActivity,
            Process pageflowProcess, List<ProcessRelevantData> dataToAdd) {
        super();
        this.userTaskActivity = userTaskActivity;
        this.pageflowProcess = pageflowProcess;
        this.dataToAdd = dataToAdd;

        setWindowTitle(Messages.ReportAdditionalPageflowDataWizard_CreateDataToMatchPageflowDialog_title);

        addPage(new ReportAdditionalPageflowDataWizardPage());
    }

    @Override
    public boolean canFinish() {
        return true;
    }

    @Override
    public boolean performFinish() {
        return true;
    }

    private class ReportAdditionalPageflowDataWizardPage extends AbstractXpdWizardPage {

        protected ReportAdditionalPageflowDataWizardPage() {
            super("report.additional.data.pageflow.wizard.displayinfo"); //$NON-NLS-1$
            setTitle(Messages.ReportAdditionalPageflowDataWizard_ExtraDataWizardPage_title);
            setDescription(Messages.ReportAdditionalPageflowDataWizard_ExtraDataWizardPage_longdesc);

        }

        public void createControl(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            FillLayout fl = new FillLayout();
            fl.marginHeight = 10;
            fl.marginWidth = 5;
            root.setLayout(fl);

            ScrolledComposite scroller =
                    new ScrolledComposite(root, SWT.H_SCROLL | SWT.V_SCROLL);

            Label text = new Label(scroller, SWT.NONE);

            StringBuilder buff = new StringBuilder();

            buff
                    .append(Messages.ReportAdditionalPageflowDataWizard_ExtraData_label
                            + "\n\n"); //$NON-NLS-1$

            for (ProcessRelevantData p : dataToAdd) {
                buff.append("    " + WorkingCopyUtil.getText(p) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            buff.append("\n\n"); //$NON-NLS-1$

            text.setText(buff.toString());

            scroller.setContent(text);

            scroller.setExpandHorizontal(true);
            scroller.setExpandVertical(true);

            Point sz = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            scroller.setMinWidth(sz.x);
            scroller.setMinHeight(sz.y);

            setControl(root);

            return;
        }

    }

}
