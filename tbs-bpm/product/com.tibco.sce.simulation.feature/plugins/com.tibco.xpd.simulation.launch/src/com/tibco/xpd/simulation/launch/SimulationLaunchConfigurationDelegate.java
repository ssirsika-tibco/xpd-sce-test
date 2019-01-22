/*
 * * * MODULE: $RCSfile: SimulationLaunchConfigurationDelegate.java $ *
 * $Revision: 1.0 $ * $Date: 2006-01-12 $ * * DESCRIPTION: * * * ENVIRONMENT:
 * Java - Platform independent * * COPYRIGHT: (c) 2006 TIBCO Software Inc, All
 * Rights Reserved. * * MODIFICATION HISTORY: * * $Log: $ *
 */
package com.tibco.xpd.simulation.launch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.model.WorkflowModel;
import com.tibco.xpd.simulation.preprocess.ProcessManager;
import com.tibco.xpd.v2.xpdl1.validation.utils.Xpdl1Util;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IssueInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * LaunchConfigurrationDelegate for launching simulations.
 * 
 * @see org.eclipse.debug.core.model.LaunchConfigurationDelegate
 * @author jarciuch
 */
public class SimulationLaunchConfigurationDelegate extends
        LaunchConfigurationDelegate {

    private static final String BPMN_VALIDATION_PROVIDER_ID = "bpmn1"; //$NON-NLS-1$

    /**
     * Status code for which a UI dialog informs that there are errors in
     * resource is registered.
     */
    protected static final IStatus simProcessValidationErrorStatus =
            new Status(IStatus.INFO,
                    "com.tibco.xpd.simulation.launch", 100, "", null); //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Status code for which a UI dialog informs that save is needed for a file.
     */
    protected static final IStatus simProcessSaveNeededStatus =
            new Status(IStatus.INFO,
                    "com.tibco.xpd.simulation.launch", 101, "", null); //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Status code for which a UI dialog informs that lauch exception was
     * thrown.
     */
    protected static final IStatus simProcessLaunchExceptionStatus =
            new Status(IStatus.INFO,
                    "com.tibco.xpd.simulation.launch", 102, "", null); //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Status code for which a UI dialog informs that another simulation is
     * running.
     */
    protected static final IStatus simProcessLaunchAnotherSimulationRunning =
            new Status(IStatus.INFO,
                    "com.tibco.xpd.simulation.launch", 103, "", null); //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Status code for which a UI dialog informs that simulation environment was
     * not selected.
     */
    protected static final IStatus simProcessLaunchNoSimulationDestination =
            new Status(IStatus.INFO,
                    "com.tibco.xpd.simulation.launch", 104, "", null); //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Status code for which a UI is prepared for simulation.
     */
    protected static final IStatus simProcessLaunchPrepareUI =
            new Status(IStatus.INFO,
                    "com.tibco.xpd.simulation.launch", 105, "", null); //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Status code for which a UI is prepared for simulation.
     */
    protected static final IStatus simProcessLaunchConfigProblem =
            new Status(IStatus.INFO,
                    "com.tibco.xpd.simulation.launch", 106, "", null); //$NON-NLS-1$//$NON-NLS-2$

    /**
     * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration,
     *      java.lang.String, org.eclipse.debug.core.ILaunch,
     *      org.eclipse.core.runtime.IProgressMonitor)
     */
    public void launch(ILaunchConfiguration config, String mode,
            ILaunch launch, IProgressMonitor monitor) throws CoreException {
        try {
            if (validateConfiguration(config)) {
                IProject project = getProjectFromConfig(config);
                IFile xpdlFile = getPackageFileFromConfig(config, project);
                Process process = getProcessFromConfig(config, xpdlFile);
                // Run Simulation
                runSimulation(xpdlFile, process, config);
            } else {
                throw new InvalidSimulationLaunchConfigException(
                        Messages.SimulationLaunchConfigurationDelegate_Invalid);
            }
        } catch (InvalidSimulationLaunchConfigException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Returns project from launch configuration.
     * 
     * @param config
     *            lauch configuration.
     * @return IProject containing file with process.
     * @throws CoreException
     * @throws InvalidSimulationLaunchConfigException
     */
    private IProject getProjectFromConfig(ILaunchConfiguration config)
            throws CoreException, InvalidSimulationLaunchConfigException {
        String projectName =
                config
                        .getAttribute(SimulationLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                                (String) null);
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = workspaceRoot.getProject(projectName);
        if (project.exists()) {
            // open if necessary
            if (!project.isOpen())
                project.open(null);
            return project;
        }
        String message =
                MessageFormat
                        .format(Messages.SimulationLaunchConfigurationDelegate_Project,
                                new Object[] { projectName });
        throw new InvalidSimulationLaunchConfigException(message);
    }

    /**
     * Returns file with package from launch configuration.
     * 
     * @param config
     * @param project
     * @return
     * @throws CoreException
     * @throws InvalidSimulationLaunchConfigException
     */
    private IFile getPackageFileFromConfig(ILaunchConfiguration config,
            IProject project) throws CoreException,
            InvalidSimulationLaunchConfigException {
        String packagePath =
                config
                        .getAttribute(SimulationLaunchConfigurationConstants.ATTR_PACKAGE,
                                (String) null);
        IFile file = project.getFile(new Path(packagePath));
        if (file.exists()) {
            return file;
        }
        String message =
                MessageFormat
                        .format(Messages.SimulationLaunchConfigurationDelegate_File,
                                new Object[] { packagePath, project.getName() });
        throw new InvalidSimulationLaunchConfigException(message);

    }

    /**
     * Returns process object from launch configuration.
     * 
     * @param config
     * @param xpdlFile
     * @return
     * @throws CoreException
     * @throws InvalidSimulationLaunchConfigException
     */
    private Process getProcessFromConfig(ILaunchConfiguration config,
            IFile xpdlFile) throws CoreException,
            InvalidSimulationLaunchConfigException {
        String processId =
                config
                        .getAttribute(SimulationLaunchConfigurationConstants.ATTR_PROCESS_ID,
                                (String) null);
        XpdProjectResourceFactory factory =
                XpdResourcesPlugin.getDefault()
                        .getXpdProjectResourceFactory(xpdlFile.getProject());
        WorkingCopy wc = factory.getWorkingCopy(xpdlFile);
        Package xpdlPackage = (Package) wc.getRootElement();
        Process process = XpdlSearchUtil.findProcess(xpdlPackage, processId);
        if (process != null) {
            return process;
        }
        String message =
                MessageFormat
                        .format(Messages.SimulationLaunchConfigurationDelegate_Process,
                                new Object[] { processId, xpdlFile.getName() });
        throw new InvalidSimulationLaunchConfigException(message);
    }

    private boolean validateConfiguration(ILaunchConfiguration config)
            throws CoreException {
        return true;
    }

    protected IProject[] getProjectsForProblemSearch(
            ILaunchConfiguration configuration, String mode)
            throws CoreException {
        IProject processProject;
        try {
            processProject = getProjectFromConfig(configuration);
        } catch (InvalidSimulationLaunchConfigException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        if (processProject != null) {
            return new IProject[] { processProject };
        }
        return null;
    }

    protected void runSimulation(IFile xpdlFile, Process xpdlProcess,
            ILaunchConfiguration config) throws CoreException {

        final WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(xpdlProcess);

        // Carry on if the package is NOT dirty
        if (!wc.isWorkingCopyDirty()) {
            /*
             * TODO JA repositoryLocation and xpdlPath needs to be reworked to
             * get process root and xpdl file path relative to process root.
             * 
             * @see code below. URL repositoryLocation = null; try {
             * repositoryLocation = XpdResourcesPlugin.getDefault()
             * .getProcessRoot(xpdlFile.getProject()).getLocation()
             * .toFile().toURL(); } catch (MalformedURLException e1) {
             * e1.printStackTrace(); } String xpdlPath =
             * XpdResourcesPlugin.getDefault
             * ().getProjectProcessRootRelativeLocation(xpdlFile).toString();
             */

            /*
             * URL repositoryLocation = null; try { repositoryLocation =
             * xpdlFile.getParent().getLocation().toFile().toURL(); } catch
             * (MalformedURLException e1) { e1.printStackTrace(); }
             * 
             * String xpdlPath = xpdlFile.getName();
             */

            /*
             * JA This needs to be done in UI thread because some of listener of
             * the model are executing UI code.
             */
            Display display = PlatformUI.getWorkbench().getDisplay();
            display.syncExec(new Runnable() {
                public void run() {
                    ProcessManager.INSTANCE.generateSimData((Package) wc
                            .getRootElement(), wc.getEditingDomain());
                }
            });
            // Save the working copy
            try {
                wc.save();
            } catch (IOException e) {
                e.printStackTrace();
            }

            URL xslt = Xpdl1Util.INSTANCE.getDowngradeXSLT();
            IPath path = LaunchPlugin.getDefault().getStateLocation();
            IPath filePath = path.append("temp.xpdl");; //$NON-NLS-1$
            File xpdl1Path = filePath.toFile();
            try {
                Source source = new StreamSource(xslt.openStream());
                Thread current = Thread.currentThread();
                ClassLoader oldLoader = current.getContextClassLoader();
                try {
                    TransformerFactory factory =
                            TransformerFactory.newInstance();
                    Transformer transformer = factory.newTransformer(source);
                    Source xpdl2 =
                            new StreamSource(xpdlFile.getLocation().toFile());
                    Result xpdl1 = new StreamResult(xpdl1Path);
                    transformer.transform(xpdl2, xpdl1);
                } finally {
                    current.setContextClassLoader(oldLoader);
                }

                // create model
                // WorkflowModel model =
                // ProcessManager.INSTANCE.createWorkflowModel(
                // repositoryLocation, xpdlPath, xpdlProcess);
                URL tempRepository = path.toFile().toURL();
                WorkflowModel model =
                        ProcessManager.INSTANCE
                                .createWorkflowModel(tempRepository,
                                        "temp.xpdl", xpdlProcess); //$NON-NLS-1$

                // run simulation
                //
                // Mind the process definition have to be valid here. This mean
                // validation is done and there is no error markers on resource.
                //
                SimulationControler cntrl =
                        LaunchPlugin.getSimulationControler();
                cntrl.runSimulation(wc, model);
            } catch (final Throwable ex) {
                // TODO change to UI handler
                /*
                 * ex.printStackTrace(); String dialogTitle = "Unable to launch
                 * simulation."; String message = "Unable to launch
                 * simulation."; String errorMessage = "Process definition is
                 * invalid. Please see details below."; String pluginId =
                 * SimulationUIPlugin.ID; IStatus status = new
                 * Status(IStatus.ERROR, pluginId, 100, errorMessage, ex); Shell
                 * activeShell = Display.getCurrent().getActiveShell();
                 * ErrorDialog .openError(activeShell, dialogTitle, message,
                 * status);
                 */
                ex.printStackTrace();
            }
        }

    }

    public boolean buildForLaunch(ILaunchConfiguration configuration,
            String mode, IProgressMonitor monitor) throws CoreException {
        return false;
    }

    /**
     * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#finalLaunchCheck(org.eclipse.debug.core.ILaunchConfiguration,
     *      java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
     */
    public boolean finalLaunchCheck(ILaunchConfiguration config, String mode,
            IProgressMonitor monitor) throws CoreException {

        // check if there is executing existing simulation.
        if (LaunchPlugin.getSimulationControler().isActiveExperiment()) {
            IStatusHandler anotherRunningHandler =
                    DebugPlugin
                            .getDefault()
                            .getStatusHandler(simProcessLaunchAnotherSimulationRunning);
            if (anotherRunningHandler != null) {
                anotherRunningHandler
                        .handleStatus(simProcessLaunchAnotherSimulationRunning,
                                null);
            }
            return false;
        }

        IProject project;
        IFile xpdlFile;
        Process xpdlProcess;
        try {
            project = getProjectFromConfig(config);
            xpdlFile = getPackageFileFromConfig(config, project);
            xpdlProcess = getProcessFromConfig(config, xpdlFile);
        } catch (InvalidSimulationLaunchConfigException e) {
            return handleConfigProblem(e.getMessage());
        }

        if (!DestinationUtil.isValidationDestinationEnabled(xpdlProcess,
                SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
            IStatusHandler noSimulationDestinationHandler =
                    DebugPlugin
                            .getDefault()
                            .getStatusHandler(simProcessLaunchNoSimulationDestination);
            if (noSimulationDestinationHandler != null) {
                noSimulationDestinationHandler
                        .handleStatus(simProcessLaunchNoSimulationDestination,
                                null);
            }
            return false;
        }

        // Save working copy is needed (if dirty) before error checking.
        if (!saveWorkingCopy(xpdlProcess, xpdlFile)) {
            return false;
        }

        boolean allowContinue = super.finalLaunchCheck(config, mode, monitor);
        if (!allowContinue) {
            return false;
        }
        int errorsCount = callValidationFramework(xpdlFile);
        if (errorsCount != 0) {
            boolean continueLaunch = true;
            IStatusHandler errorHandler =
                    DebugPlugin.getDefault()
                            .getStatusHandler(simProcessValidationErrorStatus);
            if (errorHandler != null) {

                Map paramMap = new HashMap();
                paramMap.put(LaunchPlugin.NUMBER_OF_ERRORS_KEY, new Integer(
                        errorsCount));
                continueLaunch =
                        ((Boolean) errorHandler
                                .handleStatus(simProcessValidationErrorStatus,
                                        paramMap)).booleanValue();
            }
            if (!continueLaunch) {
                return false;
            }
        }

        // prepere UI (workbench) for simulation
        boolean continueLaunch = true;
        IStatusHandler prepareUIHandler =
                DebugPlugin.getDefault()
                        .getStatusHandler(simProcessLaunchPrepareUI);
        if (prepareUIHandler != null) {

            Map paramMap = new HashMap();
            paramMap.put(LaunchPlugin.WORKFLOW_PROCESS_KEY, xpdlProcess);
            paramMap.put(LaunchPlugin.LAUNCH_MODE_KEY, mode);
            paramMap.put(LaunchPlugin.LAUNCH_CONFIGURATION_KEY, config);
            continueLaunch =
                    ((Boolean) prepareUIHandler
                            .handleStatus(simProcessLaunchPrepareUI, paramMap))
                            .booleanValue();
        }
        if (!continueLaunch) {
            return false;
        }
        return true;
    }

    /**
     * Save working copy if needed.
     * 
     * @param selectedEObject
     * @param xpdlResource
     * @return true if process should progress.
     * @throws CoreException
     */
    private boolean saveWorkingCopy(EObject selectedEObject,
            IResource xpdlResource) throws CoreException {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(selectedEObject);
        if (wc.isWorkingCopyDirty()) {

            boolean continueLaunch = true;
            IStatusHandler saveNeededHandler =
                    DebugPlugin.getDefault()
                            .getStatusHandler(simProcessSaveNeededStatus);
            if (saveNeededHandler != null) {
                Map paramMap = new HashMap();
                paramMap.put(LaunchPlugin.PACKAGE_WORKING_COPY_KEY, wc);

                continueLaunch =
                        ((Boolean) saveNeededHandler
                                .handleStatus(simProcessSaveNeededStatus,
                                        paramMap)).booleanValue();
                return continueLaunch;
            } else {
                return true;
            }

        }
        return true;
    }

    /**
     * Validate resource against bpmn and simulation.
     * 
     * @param object
     */
    private int callValidationFramework(IResource resource) {
        int result;
        Collection<Destination> destinations = new ArrayList<Destination>();
        destinations.add(ValidationActivator.getDefault()
                .getDestination(BPMN_VALIDATION_PROVIDER_ID));
        destinations
                .add(ValidationActivator
                        .getDefault()
                        .getDestination(SimulationConstants.SIMULATION_DEST_ENV_1_2_ID));
        Validator validator = new Validator(destinations);
        try {
            Collection<IIssue> issues = validator.validate(resource);
            int errorsCount = 0;
            int warningCount = 0;
            int infoCount = 0;

            for (IIssue issue : issues) {
                switch (issue.getSeverity()) {
                case IMarker.SEVERITY_ERROR:
                    errorsCount++;
                    break;
                case IMarker.SEVERITY_WARNING:
                    warningCount++;
                    break;
                case IMarker.SEVERITY_INFO:
                    infoCount++;
                    break;
                default:
                    break;
                }
            }

            result = errorsCount;
        } catch (Throwable ex) {
            ex.printStackTrace();
            result = 0;
        }

        return result;
    }

    /**
     * Handles problem with configuration.
     * 
     * @param message
     * @return true if running process should proceed.
     */
    private boolean handleConfigProblem(String message) {

        boolean continueLaunch = false;
        IStatusHandler configProblemHandler =
                DebugPlugin.getDefault()
                        .getStatusHandler(simProcessLaunchConfigProblem);
        if (configProblemHandler != null) {
            Map paramMap = new HashMap();
            paramMap.put(LaunchPlugin.PROBLEM_MESSAGE_KEY, message);
            try {
                continueLaunch =
                        ((Boolean) configProblemHandler
                                .handleStatus(simProcessSaveNeededStatus,
                                        paramMap)).booleanValue();
            } catch (CoreException e) {
                e.printStackTrace();
            }
            return continueLaunch;
        } else {
            return false;
        }
    }
}
