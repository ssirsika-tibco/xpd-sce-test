/**
 * 
 */
package com.tibco.xpd.n2.wsdlgen.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.util.WSDLFileComparator;
import com.tibco.xpd.implementer.resources.xpdl2.wizards.srvctask.ServiceTaskWsdlCreationWizard;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateOneWaySendTaskMappings;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * This test verifies the WSDL generated for the One way send activities i.e
 * Send Task, Intermediate Throw message, End Event.
 * 
 * @author rsomayaj
 * 
 */
public abstract class AbstractWSDLGenForNonAPICompareTest extends TestCase {

    private final static String SEND_TASK_NAME = "SendTask"; //$NON-NLS-1$

    private final static String CATCH_INTERMEDIATE_EVENT_NAME =
            "CatchMessageEvent"; //$NON-NLS-1$

    private final static String END_EVENT = "EndEvent"; //$NON-NLS-1$

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        List<String> urls = new ArrayList<String>();
        urls.add("resources/XPD365/XPD365.zip"); //$NON-NLS-1$
        urls.add("resources/XPD365/ComparingProject.zip"); //$NON-NLS-1$

        // Import project XPD-365 into the workspace.
        // Import project ComparingProject into workspace.
        ProjectImporter createPluginProjectImporter =
                ProjectImporter
                        .createPluginProjectImporter(WSDLGenTestPluginActivator.PLUGIN_ID,
                                urls);
        assertTrue(createPluginProjectImporter.performImport());
        TestUtil.buildAndWait();

    }

    public void doTest() throws Exception {
        // Get ExternalRefProcess from ExternalReferenceProcess.xpdl
        IFile xpdlFile = getXpdlFileTested();
        assertNotNull("XPDL file should not be null", xpdlFile); //$NON-NLS-1$
        assertTrue("XPDL File is not accessible", xpdlFile.isAccessible()); //$NON-NLS-1$
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        assertNotNull(workingCopy);
        EObject rootElement = workingCopy.getRootElement();
        if (rootElement instanceof com.tibco.xpd.xpdl2.Package) {
            com.tibco.xpd.xpdl2.Package xpdlPackage =
                    (com.tibco.xpd.xpdl2.Package) rootElement;
            Process process = xpdlPackage.getProcesses().get(0);

            assertNotNull("Proces is Null", process); //$NON-NLS-1$
            Activity sendTaskAct = getActivity(process, SEND_TASK_NAME);

            assertNotNull("Send Task is Null", sendTaskAct); //$NON-NLS-1$
            runWizard(workingCopy, sendTaskAct);
            compareWsdls(process.getName() + "-" + sendTaskAct.getName()); //$NON-NLS-1$

            Activity throwIntermediateMessage =
                    getActivity(process, CATCH_INTERMEDIATE_EVENT_NAME);
            assertNotNull("Throw intermediate message is Null", //$NON-NLS-1$
                    throwIntermediateMessage);
            runWizard(workingCopy, throwIntermediateMessage);
            compareWsdls(process.getName() + "-" //$NON-NLS-1$
                    + throwIntermediateMessage.getName());

            Activity endEventActivity = getActivity(process, END_EVENT);
            assertNotNull("End Event is Null", endEventActivity); //$NON-NLS-1$
            runWizard(workingCopy, endEventActivity);
            compareWsdls(process.getName() + "-" + endEventActivity.getName()); //$NON-NLS-1$
        }

    }

    protected void compareWsdls(String wsdlName) throws Exception {
        IPath wsdlFilePath =
                new Path(wsdlName)
                        .addFileExtension(Activator.WSDL_FILE_EXTENSION);

        IFile generatedWSDL = getGeneratedFile(wsdlFilePath);
        assertTrue(generatedWSDL.isAccessible());
        IFile goldFile = getGoldFile(wsdlFilePath);
        assertTrue(goldFile.isAccessible());
        WSDLFileComparator wsdlFileComparator = new WSDLFileComparator();

        IStatus status =
                wsdlFileComparator.compareContents(goldFile.getContents(),
                        generatedWSDL.getContents(),
                        null);

        if (status != null) {
            fail("Generate File:" + generatedWSDL.getName() //$NON-NLS-1$
                    + "  Difference ->  " + status.getMessage()); //$NON-NLS-1$
        }
    }

    /**
     * @param wsdlFilePath
     * @return
     */
    protected IFile getGoldFile(IPath wsdlFilePath) {
        IPath path =
                new Path("ComparingProject/Service Descriptors") //$NON-NLS-1$
                        .append(wsdlFilePath);
        return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
    }

    /**
     * @param wsdlFilePath
     * @return
     */
    protected IFile getGeneratedFile(IPath wsdlFilePath) {
        IPath path =
                new Path("XPD365/Service Descriptors").append(wsdlFilePath); //$NON-NLS-1$
        return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
    }

    /**
     * @param activity
     */
    protected void runWizard(final WorkingCopy workingCopy,
            final Activity activity) {
        final CompoundCommand cmd = new CompoundCommand();
        Display.getCurrent().syncExec(new Runnable() {

            public void run() {
                IProject project = getXpdlFileTested().getProject();
                ServiceTaskWsdlCreationWizard wizard =
                        new ServiceTaskWsdlCreationWizard(Xpdl2ModelUtil
                                .getPackage(activity), project, activity);
                WizardDialog dialog =
                        new WizardDialog(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(), wizard);

                dialog.create();
                ActivityMessageProvider activityMessage =
                        ActivityMessageProviderFactory.INSTANCE
                                .getMessageProvider(activity);
                wizard.performFinish();
                if (dialog.getReturnCode() == Dialog.OK) {
                    String portTypeName = activity.getProcess().getName();
                    String operationName = activity.getName();
                    String wsdlFileName = wizard.getWSDLFileName();
                    WsdlServiceKey key =
                            new WsdlServiceKey(null, null, null, portTypeName,
                                    operationName, wsdlFileName);
                    cmd.append(activityMessage
                            .getAssignWebServiceCommand(workingCopy
                                    .getEditingDomain(),
                                    activity.getProcess(),
                                    activity,
                                    wsdlFileName,
                                    true,
                                    key));

                    cmd.append(getUpdateMappingsCommand(workingCopy, activity));

                    if (cmd.canExecute())
                        try {
                            {
                                workingCopy.getEditingDomain()
                                        .getCommandStack().execute(cmd);
                                workingCopy.save();
                            }
                        } catch (IOException e) {
                            fail(e.getMessage());
                        }
                }
            }

        });
    }

    /**
     * @param workingCopy
     * @param activity
     * @return
     */
    protected Command getUpdateMappingsCommand(WorkingCopy workingCopy,
            Activity activity) {
        return new UpdateOneWaySendTaskMappings(workingCopy.getEditingDomain(),
                activity);
    }

    /**
     * @param process
     * @return
     */
    private Activity getActivity(Process process, String activityName) {
        EObject object =
                EMFSearchUtil.findInList(process.getActivities(),
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        activityName);
        if (object != null) {
            return (Activity) object;
        }
        return null;
    }

    @Override
    protected void tearDown() throws Exception {
        IProject[] projects =
                ResourcesPlugin.getWorkspace().getRoot().getProjects();

        for (IProject project : projects) {
            project.delete(true, null);
        }

    }

    protected abstract IFile getXpdlFileTested();
}
