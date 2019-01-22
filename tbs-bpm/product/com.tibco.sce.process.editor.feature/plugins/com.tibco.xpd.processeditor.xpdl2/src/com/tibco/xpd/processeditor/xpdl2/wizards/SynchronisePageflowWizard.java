/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Syncrhonise pageflow wizard (doesn't do the synchronise, just displays the
 * info on what will be done.
 * 
 * @author aallway
 * @since 3.2
 */
public class SynchronisePageflowWizard extends Wizard {
    private Activity userTaskActivity;

    private Process pageflowProcess;

    private List<FormalParameter> paramsToAdd;

    private List<FormalParameter> paramsToRemove;

    private List<FormalParameter> paramsToModify;

   
    public SynchronisePageflowWizard(Activity userTaskActivity,
            Process pageflowProcess, List<FormalParameter> paramsToAdd,
            List<FormalParameter> paramsToModify,
            List<FormalParameter> paramsToRemove) {
        super();
        this.userTaskActivity = userTaskActivity;
        this.pageflowProcess = pageflowProcess;
        this.paramsToAdd = paramsToAdd;
        this.paramsToModify = paramsToModify;
        this.paramsToRemove = paramsToRemove;

        setWindowTitle(Messages.SynchronisePageflowWizard_SynchPageflowDialog_title);

        addPage(new SynchronisePageflowWizardPage());
    }

    @Override
    public boolean canFinish() {
        // If there is something to do then we can finish
        if (!paramsToAdd.isEmpty() || !paramsToModify.isEmpty()
                || !paramsToRemove.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean performFinish() {
        return true;
    }

   
    private class SynchronisePageflowWizardPage extends AbstractXpdWizardPage {

        protected SynchronisePageflowWizardPage() {
            super("synchronise.pageflow.wizard.displayinfo"); //$NON-NLS-1$
            setTitle(Messages.SynchronisePageflowWizard_SynchPageflowParamPage_title);
            setDescription(Messages.SynchronisePageflowWizard_SynchPageflowDialog_longdesc);
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
                    .append(Messages.SynchronisePageflowWizard_AddPageflowParamsDesc_label
                            + "\n\n"); //$NON-NLS-1$

            for (FormalParameter p : paramsToAdd) {
                buff.append("    " + WorkingCopyUtil.getText(p) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            buff.append("\n\n"); //$NON-NLS-1$

            buff
                    .append(Messages.SynchronisePageflowWizard_ModifyPageflowParams_label
                            + "\n\n"); //$NON-NLS-1$

            for (FormalParameter p : paramsToModify) {
                buff.append("    " + WorkingCopyUtil.getText(p) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            buff.append("\n\n"); //$NON-NLS-1$

            buff
                    .append(Messages.SynchronisePageflowWizard_RemovePageflowParams_label
                            + "\n\n"); //$NON-NLS-1$

            for (FormalParameter p : paramsToRemove) {
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
