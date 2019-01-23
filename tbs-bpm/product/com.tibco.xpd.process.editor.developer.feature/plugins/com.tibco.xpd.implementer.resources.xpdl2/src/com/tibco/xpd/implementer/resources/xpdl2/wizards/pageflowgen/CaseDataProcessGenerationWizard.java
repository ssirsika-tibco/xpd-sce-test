/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.wizards.pageflowgen;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;

/**
 * Wizard that comes up when the user desires to generate a business
 * service/case action/service process from a case class in a bom. This wizard
 * basically takes the user thru a wizard page
 * {@link com.tibco.xpd.implementer.resources.xpdl2.wizards.pageflowgen.CaseDataGenerationWizardPage}
 * to select/create a xpdl file on the available projects in the workspace where
 * the business service/case action/service process will be generated. The
 * business service/case action/service process being generated will come with a
 * predefined template.
 * 
 * <p>
 * This should not be confused with
 * {@link com.tibco.xpd.processeditor.xpdl2.wizards.GenerateNewPageflowWizard}
 * </>
 * 
 * @author bharge
 * @since 15 Oct 2013
 */
public class CaseDataProcessGenerationWizard extends Wizard {

    protected List<IProject> studioProjects;

    List<Class> caseClassSelectionList;

    CaseDataGenerationWizardPage pageflowWizardPage;

    private String wizardPageTitle;

    private String wizardPageDesc;

    /**
     * Constructor to create the business/case service wizard for one or more
     * case classes
     * 
     * @param interestedStudioProjects
     * @param caseClassSelectionList
     */
    public CaseDataProcessGenerationWizard(List<IProject> studioProjects,
            List<Class> caseClassSelectionList, ProcessWidgetType processType,
            String wizardPageTitle) {

        if (ProcessWidgetType.CASE_SERVICE.equals(processType)) {
            setWindowTitle(Messages.GenerateCaseService_title);
            this.wizardPageDesc = Messages.GenerateCaseService_wizardPage_desc;

        } else {
            setWindowTitle(Messages.GeneratePageflow_wizard_title);
            this.wizardPageDesc = Messages.GeneratePageflow_wizardPage_desc;
        }
        this.studioProjects = studioProjects;
        this.caseClassSelectionList = caseClassSelectionList;
        this.wizardPageTitle = wizardPageTitle;

    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {

        pageflowWizardPage =
                new CaseDataGenerationWizardPage(
                        Messages.GeneratePageflow_wizardPageName,
                        Xpdl2ResourcesConsts.XPDL_EXTENSION,
                        caseClassSelectionList, wizardPageTitle, wizardPageDesc);

        pageflowWizardPage.setTitle(wizardPageTitle);
        pageflowWizardPage.setDescription(wizardPageDesc);
        addPage(pageflowWizardPage);
        setPageflowWizardPage(pageflowWizardPage);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {

        WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {

                performOperation(monitor);
            }
        };

        try {
            getContainer().run(true, true, op);
            return true;
        } catch (InvocationTargetException e) {

            IStatus status = null;

            if (e.getCause() instanceof CoreException) {
                status = ((CoreException) e.getCause()).getStatus();
            } else {
                status =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                e.getLocalizedMessage(), e.getCause());
            }
            Activator.getDefault().getLogger().log(status);

            ErrorDialog.openError(getShell(),
                    Messages.GeneratePagelow_error_dialog_title,
                    Messages.GeneratePagelow_error_dialog_msg,
                    status);
        } catch (InterruptedException e) {
            // do nothing
        }

        return false;
    }

    /**
     * @param monitor
     */
    protected void performOperation(IProgressMonitor monitor) {

    }

    /**
     * @param pageflowWizardPage
     *            the pageflowWizardPage to set
     */
    public void setPageflowWizardPage(
            CaseDataGenerationWizardPage pageflowWizardPage) {

        this.pageflowWizardPage = pageflowWizardPage;
    }

    /**
     * @return the pageflowWizardPage
     */
    public CaseDataGenerationWizardPage getPageflowWizardPage() {

        return pageflowWizardPage;
    }

}
