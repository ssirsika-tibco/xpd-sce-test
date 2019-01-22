/* 
 ** 
 **  MODULE:             $RCSfile: SimulationMainTab.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-01-12 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.ui.runner;

import java.text.MessageFormat;
import java.util.Collections;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker.ProcessPickerType;
import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.launch.SimulationLaunchConfigurationConstants;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.simulation.ui.SimulationUIConstants;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;
import com.tibco.xpd.ui.resources.TypedElementSelectionValidator;
import com.tibco.xpd.ui.resources.TypedViewerFilter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

public class SimulationLaunchMainTab extends AbstractLaunchConfigurationTab
        implements ILaunchConfigurationTab {

    /** Key for storing processId in control */
    private static final String PROCESS_ID = "ProcessId"; //$NON-NLS-1$

    private Label projectLabel;

    private Text projectText;

    private Button projectButton;

    private ProcessWidgetListener processWidgetListener = new ProcessWidgetListener();

    private Label packageLabel;

    private Text packageText;

    private Button packageButton;

    private Label processLabel;

    private Text processText;

    private Button processButton;

    public SimulationLaunchMainTab() {
    }

    private class ProcessWidgetListener implements ModifyListener,
            SelectionListener {
        public void modifyText(ModifyEvent e) {
            updateLaunchConfigurationDialog();
        }

        public void widgetSelected(SelectionEvent e) {
            Object source = e.getSource();
            if (source == projectButton) {
                browseForProject();
            } else if (source == packageButton) {
                browseForPackage();
            } else if (source == processButton) {
                browseForProcess();
            } else {
                updateLaunchConfigurationDialog();
            }
        }

        public void widgetDefaultSelected(SelectionEvent e) {
        }
    }

    public void createControl(Composite parent) {
        Font font = parent.getFont();
        Composite comp = new Composite(parent, SWT.NONE);
        setControl(comp);
        GridLayout topLayout = new GridLayout();
        topLayout.verticalSpacing = 0;
        comp.setLayout(topLayout);
        comp.setFont(font);

        createProcessEditor(comp);
        createVerticalSpacer(comp, 1);
    }

    private void createProcessEditor(Composite parent) {
        Font font = parent.getFont();
        Group group = new Group(parent, SWT.NONE);
        group.setText(Messages.SimulationLaunchMainTab_ProcessToRun);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        group.setLayoutData(gd);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        group.setLayout(layout);
        group.setFont(font);

        // project
        projectLabel = new Label(group, SWT.NONE);
        projectLabel.setText(Messages.SimulationLaunchMainTab_Project);
        projectLabel.setFont(font);

        projectText = new Text(group, SWT.SINGLE | SWT.BORDER);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        projectText.setLayoutData(gd);
        projectText.setFont(font);
        projectText.addModifyListener(processWidgetListener);

        projectButton = createPushButton(group,
                Messages.SimulationLaunchMainTab_Browse, null);
        projectButton.addSelectionListener(processWidgetListener);

        // package
        packageLabel = new Label(group, SWT.NONE);
        packageLabel.setText(Messages.SimulationLaunchMainTab_Package);
        packageLabel.setFont(font);

        packageText = new Text(group, SWT.SINGLE | SWT.BORDER);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        packageText.setLayoutData(gd);
        packageText.setFont(font);
        packageText.addModifyListener(processWidgetListener);

        packageButton = createPushButton(group,
                Messages.SimulationLaunchMainTab_Browse, null);
        packageButton.addSelectionListener(processWidgetListener);

        // process
        processLabel = new Label(group, SWT.NONE);
        processLabel.setText(Messages.SimulationLaunchMainTab_Process);
        processLabel.setFont(font);

        processText = new Text(group, SWT.SINGLE | SWT.BORDER);
        processText.setEditable(false);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        processText.setLayoutData(gd);
        processText.setFont(font);
        processText.addModifyListener(processWidgetListener);

        processButton = createPushButton(group,
                Messages.SimulationLaunchMainTab_Browse, null);
        processButton.addSelectionListener(processWidgetListener);

    }

    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        IProject project = getContextProject();
        if (project != null) {
            String projectName = project.getName();
            configuration.setAttribute(
                    SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                    projectName);
        }
        IResource packageRes = getContextPackage();
        if (packageRes != null) {
            String packagePath = packageRes.getProjectRelativePath().toString();
            configuration.setAttribute(
                    SimulationLaunchConfigurationConstants.ATTR_PACKAGE,
                    packagePath);
        }
        Process wProcess = getContextProcess();
        if (wProcess != null) {
            configuration.setAttribute(
                    SimulationLaunchConfigurationConstants.ATTR_PROCESS_ID,
                    wProcess.getId());
            configuration.setAttribute(
                    SimulationLaunchConfigurationConstants.ATTR_PROCESS_NAME,
                    wProcess.getName());
        }

    }

    public void initializeFrom(ILaunchConfiguration configuration) {
        updateProjectFromConfig(configuration);
        updatePackageFromConfig(configuration);
        updateProcessFromConfig(configuration);

    }

    protected void updateProjectFromConfig(ILaunchConfiguration config) {
        String projectName = ""; //$NON-NLS-1$
        try {
            projectName = config.getAttribute(
                    SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                    projectName);
        } catch (CoreException e) {
            System.err
                    .println("Problem with taking atribute from launch config!"); //$NON-NLS-1$
            e.printStackTrace();
        }
        projectText.setText(projectName);
    }

    protected void updatePackageFromConfig(ILaunchConfiguration config) {
        String packagePath = ""; //$NON-NLS-1$
        try {
            packagePath = config.getAttribute(
                    SimulationLaunchConfigurationConstants.ATTR_PACKAGE,
                    packagePath);
        } catch (CoreException e) {
            System.err
                    .println("Problem with taking atribute from launch config!"); //$NON-NLS-1$
            e.printStackTrace();
        }
        packageText.setText(packagePath);
    }

    protected void updateProcessFromConfig(ILaunchConfiguration config) {
        String processName = ""; //$NON-NLS-1$
        String processId = ""; //$NON-NLS-1$
        try {
            processName = config.getAttribute(
                    SimulationLaunchConfigurationConstants.ATTR_PROCESS_NAME,
                    processName);
            processId = config.getAttribute(
                    SimulationLaunchConfigurationConstants.ATTR_PROCESS_ID,
                    processId);
        } catch (CoreException e) {
            System.err
                    .println("Problem with taking atribute from launch config!"); //$NON-NLS-1$
            e.printStackTrace();
        }
        processText.setText(processName);
        processText.setData(PROCESS_ID, processId);
    }

    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                projectText.getText().trim());
        configuration.setAttribute(
                SimulationLaunchConfigurationConstants.ATTR_PACKAGE,
                packageText.getText().trim());
        configuration.setAttribute(
                SimulationLaunchConfigurationConstants.ATTR_PROCESS_NAME,
                processText.getText().trim());
        configuration.setAttribute(
                SimulationLaunchConfigurationConstants.ATTR_PROCESS_ID,
                (String) processText.getData(PROCESS_ID));
    }

    /**
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(ILaunchConfiguration)
     */
    public boolean isValid(ILaunchConfiguration config) {

        setErrorMessage(null);
        setMessage(null);

        String name = projectText.getText().trim();
        if (name.length() > 0) {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IStatus status = workspace.validateName(name, IResource.PROJECT);
            if (status.isOK()) {
                IProject project = ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(name);
                if (!project.exists()) {
                    setErrorMessage(MessageFormat
                            .format(
                                    Messages.SimulationLaunchMainTab_Project_Not_Exist_Error,
                                    new String[] { name }));
                    return false;
                }
                if (!project.isOpen()) {
                    setErrorMessage(MessageFormat
                            .format(
                                    Messages.SimulationLaunchMainTab_Project_Closed_Error,
                                    new String[] { name }));
                    return false;
                }
                String packageName = packageText.getText().trim();
                if (packageName.length() == 0) {
                    setErrorMessage(Messages.SimulationLaunchMainTab_Package_Unspecified_Error);
                    return false;
                }
                IFile packageFile = project.getFile(packageName);
                if (!packageFile.exists()) {
                    setErrorMessage(MessageFormat
                            .format(
                                    Messages.SimulationLaunchMainTab_Package_Not_Exist_Error,
                                    new String[] { packageName }));
                    return false;
                }
                String processName = processText.getText().trim();
                if (processName.length() == 0) {
                    setErrorMessage(Messages.SimulationLaunchMainTab_Process_Unspecified_Error);
                    return false;
                }
            } else {
                setErrorMessage(MessageFormat
                        .format(
                                Messages.SimulationLaunchMainTab_Illegal_Project_Name_Error,
                                new String[] { status.getMessage() }));
                return false;
            }
        } else {
            setErrorMessage(Messages.SimulationLaunchMainTab_Project_Unspecified_Error);
            return false;
        }

        /*
         * name = fMainText.getText().trim(); if (name.length() == 0) {
         * setErrorMessage
         * (LauncherMessages.JavaMainTab_Main_type_not_specified_16);
         * //$NON-NLS-1$ return false; }
         */

        return true;
    }

    /**
     * Create some empty space.
     */
    protected void createVerticalSpacer(Composite comp, int colSpan) {
        Label label = new Label(comp, SWT.NONE);
        GridData gd = new GridData();
        gd.horizontalSpan = colSpan;
        label.setLayoutData(gd);
        label.setFont(comp.getFont());
    }

    /**
     * Returns the current Process element context from which to initialize
     * default settings, or <code>null</code> if none.
     * 
     * @return Process element context.
     */
    protected Process getContextProcess() {

        IWorkbenchPage page = null;
        IWorkbenchWindow window = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        if (window != null) {
            page = window.getActivePage();
        }
        if (page != null) {
            ISelection selection = page.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection ss = (IStructuredSelection) selection;
                if (!ss.isEmpty()) {
                    Object obj = ss.getFirstElement();
                    if (obj instanceof IAdaptable) {
                        EObject process = (EObject) ((IAdaptable) obj)
                                .getAdapter(EObject.class);
                        if (process instanceof Process) {
                            return (Process) process;
                        }
                    }
                }
            }
            // get process from active editor
            IEditorPart part = page.getActiveEditor();
            if (part != null) {
                IEditorInput input = part.getEditorInput();
                return (Process) input.getAdapter(Process.class);
            }

        }
        return null;
    }

    /**
     * Returns the current IResource of package context from which to initialize
     * default settings, or <code>null</code> if none.
     * 
     * @return IResource of Package element context.
     */
    protected IResource getContextPackage() {

        IWorkbenchPage page = null;
        IWorkbenchWindow window = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        if (window != null) {
            page = window.getActivePage();
        }
        if (page != null) {
            ISelection selection = page.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection ss = (IStructuredSelection) selection;
                if (!ss.isEmpty()) {
                    Object obj = ss.getFirstElement();
                    if (obj instanceof Package || obj instanceof Process) {
                        WorkingCopy wc = WorkingCopyUtil
                                .getWorkingCopyFor((EObject) obj);
                        return wc.getEclipseResources().get(0);
                    }
                }
            }
        }
        return null;
    }

    /**
     * TODO comment
     */
    protected IProject getContextProject() {

        IWorkbenchPage page = null;
        IResource resource = null;
        IWorkbenchWindow window = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        if (window != null) {
            page = window.getActivePage();
        }
        if (page != null) {
            ISelection selection = page.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection ss = (IStructuredSelection) selection;
                if (!ss.isEmpty()) {
                    Object obj = ss.getFirstElement();
                    if (obj instanceof Package || obj instanceof Process) {
                        WorkingCopy wc = WorkingCopyUtil
                                .getWorkingCopyFor((EObject) obj);
                        resource = wc.getEclipseResources().get(0);
                    } else if (obj instanceof IResource) {
                        resource = (IResource) obj;
                    } else if (obj instanceof IAdaptable) {
                        resource = (IResource) ((IAdaptable) obj)
                                .getAdapter(IResource.class);
                    }
                }
            }
        }
        if (resource != null) {
            return resource.getProject();
        }
        return null;
    }

    public Image getImage() {
        return SimulationUIPlugin.getDefault().getImageRegistry().get(
                SimulationUIConstants.IMG_SIMULATION_RUN);
    }

    public String getName() {
        return Messages.SimulationLaunchMainTab_Main;
    }

    protected void browseForProject() {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        String projectName = projectText.getText();
        IResource initSelection = null;
        if (projectName != null && projectName.length() > 0) {
            initSelection = workspaceRoot.findMember(projectName);
        }

        ProjectPicker picker = new ProjectPicker(getShell());
        picker.setInput(workspaceRoot);
        picker.setInitialSelection(initSelection);

        if (picker.open() == ProjectPicker.OK) {
            IContainer selectedFolder = (IContainer) picker.getFirstResult();
            String projectPath = selectedFolder.getFullPath().toString();
            if (projectPath.startsWith("/")) { //$NON-NLS-1$
                projectPath = projectPath.substring(1);
            }
            projectText.setText(projectPath);
        }

    }

    protected void browseForPackage() {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        String projectName = projectText.getText();
        String packageName = packageText.getText();
        if (projectName == null || projectName.length() < 1) {
            // as there is no project so we can not show any packages
            return;
        }
        IResource initSelection = null;
        if (projectName != null && projectName.length() > 0
                && packageName != null && packageName.length() > 0) {
            initSelection = workspaceRoot.findMember(projectName + "/" //$NON-NLS-1$
                    + packageName);
        }
        IProject specifiedProject = null;
        if (projectName != null && projectName.length() > 0) {
            specifiedProject = workspaceRoot.getProject(projectName);
        }

        PackagePicker picker = new PackagePicker(getShell());
        picker.setInput(specifiedProject);
        picker.setInitialSelection(initSelection);

        if (picker.open() == PackagePicker.OK) {
            IFile file = (IFile) picker.getFirstResult();
            packageText.setText(file.getProjectRelativePath().toString());
        }

    }

    protected void browseForProcess() {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        String projectName = projectText.getText();
        String packageName = packageText.getText();
        IResource initSelection = null;
        if (projectName != null && projectName.length() > 0
                && packageName != null && packageName.length() > 0) {
            initSelection = workspaceRoot.findMember(projectName + "/" //$NON-NLS-1$
                    + packageName);
        }
        /*
         * Process workflowProcess = null; Package pckg = null; if
         * (initSelection != null) { WorkingCopyFactory factory = new
         * Xpdl2WorkingCopyFactory(); WorkingCopy wc =
         * factory.getWorkingCopy(initSelection); pckg = (Package)
         * wc.getRootElement(); String processId = (String)
         * processText.getData(PROCESS_ID); if (processId != null &&
         * processId.length() > 0) { workflowProcess =
         * pckg.getProcess(processId); } }
         */
        ProcessFilterPicker processPicker = new ProcessFilterPicker(getShell(),
                ProcessPickerType.PROCESS, false);
        /*
         * processPicker.setInput(pckg); if (workflowProcess != null) {
         * processPicker.setInitialSelection(workflowProcess); }
         */
        if (processPicker.open() == Window.OK) {
            Process selectedProcess = (Process) processPicker.getFirstResult();
            String procName = selectedProcess.getName();
            String procId = selectedProcess.getId();
            processText.setText(procName);
            processText.setData(PROCESS_ID, procId);
        }
    }

    /**
     * Picker to select an XPD project.
     * 
     * @author njpatel
     * 
     */
    private class ProjectPicker extends BaseObjectPicker {

        public ProjectPicker(Shell parent) {
            super(parent);
            setTitle(Messages.SimulationLaunchMainTab_ProjectSelection);
            setMessage(Messages.SimulationLaunchMainTab_SelectProject);

            addFilter(new XpdNatureProjectsOnly());
            addFilter(new TypedViewerFilter(new Class<?>[] { IProject.class }));
        }

    }

    /**
     * Picker to select a package xpdl file from a processes special folder.
     * 
     * @author njpatel
     * 
     */
    private class PackagePicker extends BaseObjectPicker {

        public PackagePicker(Shell parent) {
            super(parent);
            setTitle(Messages.SimulationLaunchMainTab_packagePicker_title);
            setMessage(Messages.SimulationLaunchMainTab_packagePicker_shortdesc);

            setValidator(new TypedElementSelectionValidator(
                    new Class[] { IFile.class }, false));

            addFilter(new SpecialFoldersOnlyFilter(
                    Collections
                            .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND)));

            String ext = Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT;
            if (ext.startsWith(".")) { //$NON-NLS-1$
                ext = ext.substring(1);
            }
            addFilter(new FileExtensionInclusionFilter(Collections
                    .singleton(ext)));
            addFilter(new NoFileContentFilter());
        }

    }
}
