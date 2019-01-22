/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.exports.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Wizard page for the BOM export wizard to run build if auto-build is disabled.
 * 
 * @author njpatel
 * 
 */
public class BuildPage extends AbstractXpdWizardPage {

    public BuildPage(String pageName) {
        super(pageName);
        setTitle(Messages.BuildPage_buildRequired_page_title);
        setMessage(Messages.BuildPage_buildRequired_page_message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());

        setPageComplete(false);

        Label lbl = new Label(root, SWT.WRAP);
        lbl
                .setText(Messages.BuildPage_buildRequired_page_longDesc);
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.widthHint = 150;
        lbl.setLayoutData(data);

        final Button btn = new Button(root, SWT.NONE);
        btn.setText(Messages.BuildPage_runBuild_button);
        data = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
        data.verticalIndent = 10;
        btn.setLayoutData(data);
        btn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                final Boolean[] complete = new Boolean[] { false };
                try {
                    getContainer().run(true,
                            true,
                            new WorkspaceModifyOperation() {

                                @Override
                                protected void execute(IProgressMonitor monitor)
                                        throws CoreException,
                                        InvocationTargetException,
                                        InterruptedException {
                                    runBuild(monitor);
                                    complete[0] = true;
                                }

                            });
                } catch (InvocationTargetException e1) {
                    Activator
                            .getDefault()
                            .getLogger()
                            .error(e1,
                                    Messages.BuildPage_ExceptionInBuild_error_message);
                } catch (InterruptedException e1) {
                    // Do nothing - operation has been cancelled
                }

                if (complete[0]) {
                    setPageComplete(true);
                    // Should not be allowed to run build again
                    btn.setEnabled(false);
                    // Move to next page immediately
                    IWizardPage page = getWizard().getNextPage(BuildPage.this);
                    if (page != null) {
                        getContainer().showPage(page);
                    }
                } else {
                    setPageComplete(false);
                }
            }
        });

        setControl(root);
    }

    /**
     * Run a full build on the workspace.
     * 
     * @param monitor
     * @throws CoreException
     */
    private void runBuild(IProgressMonitor monitor) throws CoreException {
        ResourcesPlugin.getWorkspace()
                .build(IncrementalProjectBuilder.FULL_BUILD, monitor);
    }

}
