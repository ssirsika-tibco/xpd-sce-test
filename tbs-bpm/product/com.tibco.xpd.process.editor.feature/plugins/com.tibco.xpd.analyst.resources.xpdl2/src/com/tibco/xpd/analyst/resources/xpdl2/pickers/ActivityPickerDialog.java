/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectInclusionFilter;
import com.tibco.xpd.ui.resources.StatusInfo;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.util.XpdlUtils;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Tree-view activity picker dialog. It provides a static method
 * <code>getActivityFromPicker(shell, activity)</code> to select a request
 * activity from the workspace
 * 
 * @author agondal
 * @since 22 Jan 2013
 */
public class ActivityPickerDialog extends ElementTreeSelectionDialog implements
        ISelectionStatusValidator {

    /**
     * @param parent
     * @param labelProvider
     * @param contentProvider
     */
    public ActivityPickerDialog(Shell parent, ILabelProvider labelProvider,
            ITreeContentProvider contentProvider) {

        super(parent, labelProvider, contentProvider);

        Set<EClass> classes = new HashSet<EClass>();
        classes.add(Xpdl2Package.eINSTANCE.getPackage());
        classes.add(Xpdl2Package.eINSTANCE.getProcess());
        classes.add(Xpdl2Package.eINSTANCE.getActivity());
        addFilter(new EObjectInclusionFilter(classes));

        setTitle(Messages.ActivityPicker_TITLE);
        setMessage(Messages.ActivityPicker_MESSAGE);

        setValidator(this);
    }

    /**
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     * 
     * @param selection
     * @return
     */
    @Override
    public IStatus validate(Object[] selection) {
        IStatus status = new StatusInfo(Status.ERROR, null);
        if (selection.length == 1) {
            Object item = selection[0];
            if (item instanceof Activity) {
                status = new StatusInfo();
            }
        }
        return status;
    }

    /**
     * Select request activity using tree-view activity picker dialog
     * 
     * @param shell
     * @param activity
     * 
     * @return selectedActivity
     */
    public static Activity getActivityFromPicker(Shell shell, Activity activity) {

        return getActivityFromPicker(shell, activity, null);
    }

    /**
     * Select request activity using tree-view activity picker dialog, and sets
     * the initial selection to the object provided.
     * 
     * @param shell
     * @param activity
     * @param initialSelection
     * @return selectedActivity
     */
    public static Activity getActivityFromPicker(Shell shell,
            Activity activity, Object initialSelection) {

        Activity selectedActivity = null;

        if (activity != null) {

            ActivityPickerDialog picker =
                    new ActivityPickerDialog(shell,
                            new ActivityPickerLabelProvider(),
                            new ActivityPickerContentProvider());

            IWorkspaceRoot workspaceRoot =
                    ResourcesPlugin.getWorkspace().getRoot();

            picker.setInput(workspaceRoot);

            picker.setInitialSelection(initialSelection == null ? activity
                    .getProcess().getPackage() : initialSelection);

            if (picker.open() == ActivityPickerDialog.OK) {
                if (picker.getFirstResult() instanceof Activity) {

                    selectedActivity = (Activity) picker.getFirstResult();

                    if (!ProcessUIUtil.checkAndAddProjectReference(shell,
                            activity,
                            selectedActivity)) {

                        selectedActivity = null;
                    }
                }
            }
        }

        return selectedActivity;
    }

    /**
     * Label provider for the activity picker dialog
     * 
     */
    static class ActivityPickerLabelProvider extends LabelProvider {

        private WorkbenchLabelProvider provider = new WorkbenchLabelProvider();

        @Override
        public Image getImage(Object element) {

            if (element instanceof EObject) {
                return WorkingCopyUtil.getImage((EObject) element);
            } else if (provider != null) {
                return provider.getImage(element);
            }

            return super.getImage(element);
        }

        @Override
        public String getText(Object element) {
            if (element instanceof EObject) {
                return WorkingCopyUtil.getText((EObject) element);
            } else if (provider != null) {
                return provider.getText(element);
            }

            return super.getText(element);
        }
    }

    /**
     * Content provider for the activity picker dialog
     * 
     */
    static class ActivityPickerContentProvider implements ITreeContentProvider {

        @Override
        public Object[] getChildren(Object parentElement) {
            return getElements(parentElement);
        }

        @Override
        public Object getParent(Object element) {

            if (element instanceof Package) {

                return WorkingCopyUtil.getProjectFor((Package) element);

            } else if (element instanceof Process) {

                return ((Process) element).getPackage();

            } else if (element instanceof Activity) {

                return ((Activity) element).getProcess();
            }

            return null;
        }

        @Override
        public boolean hasChildren(Object element) {

            if (element instanceof IProject || element instanceof Process
                    || element instanceof Package) {

                Object[] children = getElements(element);
                return children != null && children.length > 0;
            }

            return false;
        }

        @Override
        public Object[] getElements(Object inputElement) {

            if (inputElement instanceof IWorkspaceRoot) {
                /*
                 * Only return XPD projects
                 */
                List<IProject> xpdProjects = new ArrayList<IProject>();

                for (IProject project : ((IWorkspaceRoot) inputElement)
                        .getProjects()) {
                    try {
                        if (project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                            if (!project.getName().startsWith(".")) { //$NON-NLS-1$

                                xpdProjects.add(project);

                            }
                        }
                    } catch (CoreException e) {
                    }
                }

                return xpdProjects.toArray();

            } else if (inputElement instanceof IProject) {

                // return xpdl packages
                return getXpdlPackagesInProject((IProject) inputElement);

            }

            else if (inputElement instanceof Package) {

                // Only return business processes

                List<Process> processes = new ArrayList<Process>();

                for (Process process : ((Package) inputElement).getProcesses()) {

                    if (Xpdl2ModelUtil.isBusinessProcess(process)) {

                        processes.add(process);
                    }
                }

                return processes.toArray();

            } else if (inputElement instanceof Process) {

                // Only return api activities

                List<Activity> activities = new ArrayList<Activity>();

                activities
                        .addAll(ReplyActivityUtil
                                .getAllIncomingRequestActivities(((Process) inputElement)));

                return activities.toArray();
            }

            return null;
        }

        @Override
        public void dispose() {
            // Do nothing
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // Do nothing
        }

    }

    /**
     * Get xpdl2 packages in the given project
     * 
     * @param project
     * @return xpld2packages
     */
    private static Object[] getXpdlPackagesInProject(IProject project) {

        List<IResource> xpdlFiles = new ArrayList<IResource>();

        // Get xpdl files in the project
        if (project != null) {

            xpdlFiles =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(project,
                                    Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                    XpdlUtils.XPDL_FILE_EXTENSION,
                                    false);
        }

        List<Package> xpdl2Packages = new ArrayList<Package>();

        for (IResource xpdlFile : xpdlFiles) {

            Package xpdl2Package = null;

            // get the corresponding xpdl2 package
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
            if (wc != null) {
                xpdl2Package = (Package) wc.getRootElement();
            } else {
                ResourceSet rs =
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet();
                URI uri =
                        URI.createPlatformResourceURI(xpdlFile.getFullPath()
                                .toPortableString(), true);
                Resource resource = rs.getResource(uri, true);
                xpdl2Package =
                        ((DocumentRoot) resource.getContents().get(0))
                                .getPackage();
            }
            if (xpdl2Package != null) {
                xpdl2Packages.add(xpdl2Package);
            }
        }

        return xpdl2Packages.toArray();
    }

}
