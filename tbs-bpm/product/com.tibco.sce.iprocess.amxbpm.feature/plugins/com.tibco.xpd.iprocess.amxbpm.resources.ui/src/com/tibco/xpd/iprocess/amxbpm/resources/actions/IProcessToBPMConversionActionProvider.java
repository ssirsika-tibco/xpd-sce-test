package com.tibco.xpd.iprocess.amxbpm.resources.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.iprocess.amxbpm.resources.IProcessAMXBPMResourcePlugin;
import com.tibco.xpd.iprocess.amxbpm.resources.ui.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.resources.wizards.convert.ConvertIProcessToAMXBPMWizard;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.IResourceFilter;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdl2.edit.util.XpdlUtils;

/**
 * 
 * Action provider for Convert Studio iProcess to AMX BPM Context Menu.
 * 
 * Conversion Menu is available only on following selections:
 * 
 * 1. Single XPD Project set for iProcess Destination.
 * 
 * 2. Single Process Package Special Folder from Project having iProcess
 * Destination
 * 
 * 3. Single/Multiple Xpdl file if it is in an Project having iProcess
 * destination
 * 
 * Invoking Menu Shows Error with appropriate text, in Message Dialog in
 * following scenarios :
 * 
 * 1. If multiple Xpdl's are selected from same project and the Project does not
 * have iProcess Destination.
 * 
 * 2. Selected iProcess destination Project does not have any Xpdl's at all
 * 
 * Disables the Convert Action, when there are multiple XPDL files selected ,
 * but they belong to different Project.
 * 
 * @author aprasad
 * @since 09-Apr-2014
 */
public class IProcessToBPMConversionActionProvider extends CommonActionProvider {

    /**
     * 
     */
    private final String IPROCESS_DESTINATION = "iProcess"; //$NON-NLS-1$

    /**
     * Parent Project of the XPDL files on which the Menu is invoked.
     */
    private IProject sourceProject = null;

    private Collection<IResource> alliProcessXpdlsToConvert = null;

    @SuppressWarnings("deprecation")
    @Override
    public void fillContextMenu(IMenuManager menu) {

        try {

            alliProcessXpdlsToConvert = new ArrayList<IResource>();

            ISelection selection = getContext().getSelection();

            boolean selResourceFromDiffParent = false;

            if (selection.isEmpty()) {
                return;
            }
            /* Implement Certain Checks here */
            /*
             * 1. Menu should be available on Project set for iProcess
             * Destination
             */
            if (selection instanceof IStructuredSelection) {

                IStructuredSelection strSel = (IStructuredSelection) selection;

                boolean isIProcDestEnabled = true;

                if (strSel.size() == 1) {

                    Object firstElement = strSel.getFirstElement();

                    if (firstElement instanceof IProject) {

                        sourceProject = (IProject) firstElement;

                        /* Check if this project is set for iProcess Destination */

                        isIProcDestEnabled =
                                doesProjectHaveIProcessDestination(sourceProject);
                        if (isIProcDestEnabled) {
                            /* get all the xpdls in the project */
                            alliProcessXpdlsToConvert
                                    .addAll(getAllXpdlsInSourceProject(sourceProject));
                        }

                    } else {

                        if (firstElement instanceof IFile) {
                            /*
                             * As the Extension restricts to only XPDL files, we
                             * only need to check the destination here
                             */

                            IFile selFile = (IFile) firstElement;
                            sourceProject = selFile.getProject();
                            isIProcDestEnabled =
                                    doesProjectHaveIProcessDestination(sourceProject);
                            /* all the file */
                            alliProcessXpdlsToConvert.add(selFile);

                        } else if (firstElement instanceof SpecialFolder) {
                            SpecialFolder specialFolder =
                                    (SpecialFolder) firstElement;
                            sourceProject = specialFolder.getProject();

                            /*
                             * If we have selected a Processes special folder
                             * check if Project of that Special folder has
                             * iProcess Destination enabled.
                             */
                            isIProcDestEnabled =
                                    doesProjectHaveIProcessDestination(sourceProject);
                            if (isIProcDestEnabled) {

                                IFolder folder = specialFolder.getFolder();

                                if (folder instanceof IContainer) {
                                    /* add all the xpdl's in the special folder */
                                    alliProcessXpdlsToConvert
                                            .addAll(getAllXpdlsInSpecialFolder(folder));
                                }
                            }
                        }
                    }
                } else {
                    /*
                     * Check all selected Resource belong to same Source
                     * project, Disable convertAction if not the case
                     */
                    Iterator selectedElementsItr = strSel.iterator();

                    IProject srcProj = null;

                    while (selectedElementsItr.hasNext()) {

                        Object next = selectedElementsItr.next();

                        if (next instanceof IProject) {
                            /*
                             * If multiple projects are selected we should not
                             * show the IPS-to-BPM context menu, return
                             * immediately.
                             */
                            return;
                        } else if (next instanceof IFile) {
                            /* add the file */
                            alliProcessXpdlsToConvert.add((IFile) next);

                            IProject currProject = ((IFile) next).getProject();

                            if (srcProj == null) {

                                srcProj = currProject;

                            } else if (!srcProj.equals(currProject)) {

                                selResourceFromDiffParent = true;
                            }

                        }
                    }
                    sourceProject = srcProj;
                }

                /*
                 * Menu Should only be available on Projects with
                 * iprocessDestinationEnabled
                 */
                if (!isIProcDestEnabled) {
                    return;
                }
            }

            /* Append Menu if everything is fine */

            IAction convertAction = getConvertAction();
            /*
             * Disable when multiple Resources are selected and they belong to
             * different Project
             */
            convertAction.setEnabled(!selResourceFromDiffParent);

            menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, convertAction);
        } catch (CoreException e) {

            IProcessAMXBPMResourcePlugin
                    .getDefault()
                    .getLogger()
                    .log(new Status(IStatus.ERROR,
                            IProcessAMXBPMResourcePlugin.PLUGIN_ID,
                            e.getMessage()));
        }
    }

    /**
     * gets all the Xpdl files in the passed Project
     * 
     * @param sourceProject2
     *            the project
     * @return all the Xpdl files in the passed source Project.
     * @throws CoreException
     */
    private Collection<IResource> getAllXpdlsInSourceProject(
            IProject sourceProject) throws CoreException {

        Collection<IResource> allXpdlResources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderKind(sourceProject,
                                Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                new IResourceFilter() {

                                    @Override
                                    public boolean includeResource(
                                            IResource resource) {
                                        /*
                                         * We are interested only in xpdl files.
                                         */
                                        if (XpdlUtils.XPDL_FILE_EXTENSION
                                                .equals(resource
                                                        .getFileExtension())) {
                                            return true;
                                        }
                                        return false;
                                    }
                                });

        return allXpdlResources;
    }

    /**
     * gets all the Xpdl files in the passed Container
     * 
     * @param container
     *            the container whose Xpdl files are to be fetched
     * @return all the Xpdl files present in the passed container
     * @throws CoreException
     */
    private Collection<IResource> getAllXpdlsInSpecialFolder(
            IContainer container) throws CoreException {

        Collection<IResource> allXpdlResources =
                SpecialFolderUtil.getAllDeepResourcesInContainer(container,
                        new IResourceFilter() {

                            @Override
                            public boolean includeResource(IResource resource) {
                                /*
                                 * We are interested only in xpdl files.
                                 */
                                if (XpdlUtils.XPDL_FILE_EXTENSION
                                        .equals(resource.getFileExtension())) {
                                    return true;
                                }
                                return false;
                            }
                        });

        return allXpdlResources;
    }

    /**
     * Checks if the Project has "iProcess" Destination.
     * <p>
     * Note that we Check the destination by Names and not by id, this is done
     * on purpose because the the final product will not have any IPs plug-ins
     * and hence we cannot fetch the Destination by ID.
     * 
     * @param project
     *            the project whose destination is to be asserted.
     * @return <code> true</code> if the project has iProcess destination,
     *         <code>false</code> otherwise.
     */
    private boolean doesProjectHaveIProcessDestination(IProject project) {

        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        if (projectConfig != null) {

            ProjectDetails projectDetails = projectConfig.getProjectDetails();
            if (projectDetails != null) {

                Destinations globalDestinations =
                        projectDetails.getGlobalDestinations();
                if (globalDestinations != null) {

                    List<com.tibco.xpd.resources.projectconfig.Destination> destinationList =
                            globalDestinations.getDestination();

                    for (com.tibco.xpd.resources.projectconfig.Destination destination : destinationList) {

                        if (IPROCESS_DESTINATION.equals(destination.getType())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Get the Action for the Context menu action provider.
     * 
     * @return
     */
    private IAction getConvertAction() {

        return new Action() {

            @Override
            public String getText() {

                return Messages.ConvertToAMXBPMActionProvider_ConvertIprocessBpmMenuText;
            }

            @Override
            public ImageDescriptor getImageDescriptor() {

                return IProcessAMXBPMResourcePlugin
                        .getImageDescriptor("icons/iProcessImportIcon.png"); //$NON-NLS-1$
            }

            @Override
            public String getToolTipText() {

                return Messages.ConvertToAMXBPMActionProvider_ConvertIprocessBpmMenuTooltip;
            }

            @Override
            public void run() {

                invokeConversionWizard();
            }

            /**
             * Invokes the conversion Wizard
             */
            private void invokeConversionWizard() {

                if (sourceProject != null) {

                    /* show error if the project has not Xpdls to convert */
                    if (alliProcessXpdlsToConvert == null
                            || alliProcessXpdlsToConvert.isEmpty()) {

                        MessageDialog
                                .openError(getActionSite().getViewSite()
                                        .getShell(),
                                        Messages.IProcessToBPMConversionActionProvider_ErrMsg_NoIProcessFilesToConvertTitle,
                                        Messages.IProcessToBPMConversionActionProvider_NoIProcessInProjectOrFolderError_msg);
                        return;

                    }

                    if (!doesProjectHaveIProcessDestination(sourceProject)) {
                        MessageDialog
                                .openError(getActionSite().getViewSite()
                                        .getShell(),
                                        Messages.IProcessToBPMConversionActionProvider_CannotPerformUserActionDialog_title,
                                        Messages.IProcessToBPMConversionActionProvider_SourceProjectNotIProcessDialog_desc);
                        return;
                    }

                    /*
                     * Invoke the wizard
                     */
                    ConvertIProcessToAMXBPMWizard convertIProcessToAMXBPMWizard =
                            new ConvertIProcessToAMXBPMWizard(
                                    alliProcessXpdlsToConvert, sourceProject);

                    Dialog dialog =
                            new WizardDialog(getActionSite().getViewSite()
                                    .getShell(), convertIProcessToAMXBPMWizard);
                    dialog.open();
                }
            }
        };
    }
}
