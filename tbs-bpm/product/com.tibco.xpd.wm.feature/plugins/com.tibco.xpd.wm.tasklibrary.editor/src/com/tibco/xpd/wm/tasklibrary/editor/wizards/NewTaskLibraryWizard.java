/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.wizards;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.ui.properties.wizards.BasicNewBpmResourceWizard;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditor;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New task library wizard.
 * 
 * @author aallway
 * @since 3.2
 */
public class NewTaskLibraryWizard extends BasicNewBpmResourceWizard {

    private TaskLibraryProjectSelectPage projectPage;

    private TaskLibraryPackageInformationPage infoPage;

    private int CX_OBJECT_MARGIN = ProcessWidgetConstants.TASK_WIDTH_SIZE / 2;

    private int CY_OBJECT_MARGIN = ProcessWidgetConstants.TASK_HEIGHT_SIZE / 2;

    /**
     * 
     */
    public NewTaskLibraryWizard() {
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);

        setWindowTitle(Messages.NewTaskLibraryWizard_NewTaskLibrary_title);

        setDefaultPageImageDescriptor(TaskLibraryEditorPlugin
                .getImageDescriptor(TaskLibraryEditorContstants.IMG_TASKLIBRARY_WIZARD));
    }

    @Override
    public void addPages() {
        // Project and package file selection page
        projectPage = new TaskLibraryProjectSelectPage();
        projectPage.init(getSelection());
        addPage(projectPage);

        // Package information page
        infoPage = new TaskLibraryPackageInformationPage();
        addPage(infoPage);

        return;
    }

    @Override
    public boolean performFinish() {
        // If the package is going to be created in a subfolder within a
        // packages folder that does not exist then we need to create it
        if (projectPage.createPackageFolder()) {

            //
            // Make sure the base packager is created.
            if (projectPage.getPackageObject() == null) {
                projectPage.createPackage();
            }

            Package xpdlPackage = projectPage.getPackageObject();

            //
            // Add the extra parts to it
            // (A default process, pool and lane with a single task in it.

            //
            // Update the packge object with data from pkg info page.
            infoPage.updatePackageObject(xpdlPackage);

            addDefaultTaskLibraryToPackage(xpdlPackage);

            //
            // Create the file from the package.
            String packageFileName =
                    projectPage.getPackagesFolderContainer().getFullPath()
                            + "/" + projectPage.getPackageFileName(); //$NON-NLS-1$

            URI uri = URI.createPlatformResourceURI(packageFileName);
            try {
                IFolder folder =
                        (IFolder) projectPage.getPackagesFolderContainer();
                IFile file = folder.getFile(projectPage.getPackageFileName());

                ResourceSet rset = new ResourceSetImpl();
                Resource resource = rset.createResource(uri);

                DocumentRoot docRoot =
                        Xpdl2Factory.eINSTANCE.createDocumentRoot();

                docRoot.setPackage(xpdlPackage);
                resource.getContents().add(docRoot);

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                resource.save(os, null);
                file.create(new ByteArrayInputStream(os.toByteArray()),
                        true,
                        new NullProgressMonitor());

                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault().getWorkingCopy(file);
                Package pck = (Package) wc.getRootElement();

                if (pck.getProcesses().isEmpty()) {
                    selectAndReveal(pck);
                } else {
                    Process finalTaskLibrary = pck.getProcesses().get(0);
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().openEditor(new ProcessEditorInput(
                                    wc, finalTaskLibrary),
                                    TaskLibraryEditor.EDITOR_ID);
                    selectAndReveal(finalTaskLibrary);
                }
            } catch (Exception ex) {
                TaskLibraryEditorPlugin.getDefault().getLogger().error(ex,
                        "Created but failed to access Task Library."); //$NON-NLS-1$
            }
            return true;
        }
        return false;
    }

    /**
     * Given an empty(ish) package, add a default task library to it.
     * 
     * @param xpdlPackage
     * @return process representing the task library.
     */
    private Process addDefaultTaskLibraryToPackage(Package xpdlPackage) {
        Process taskLibrary = createTaskLibrary(xpdlPackage);

        xpdlPackage.getProcesses().add(taskLibrary);

        Pool pool = createTaskLibraryPool(taskLibrary);
        xpdlPackage.getPools().add(pool);

        Lane lane = createTaskLibraryLane(pool, false);
        pool.getLanes().add(lane);

        if (false) {
            Artifact annot =
                    createTaskLibraryAnnotation(lane, new Point(
                            CX_OBJECT_MARGIN, CY_OBJECT_MARGIN));
            xpdlPackage.getArtifacts().add(annot);
        }

        Activity task =
                createTaskLibraryTask(lane, new Point(CX_OBJECT_MARGIN * 2,
                        CY_OBJECT_MARGIN * 2));
        taskLibrary.getActivities().add(task);

        // MR 39949 - begin
        addProjectDestinationsToProcess(taskLibrary);
        // MR 39949 - end

        return taskLibrary;
    }

    private void addProjectDestinationsToProcess(Process processObject) {
        Command command = null;
        CompoundCommand cCmd = new CompoundCommand();
        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        if (null != projectPage.getPackagesFolderContainer()) {
            IProject project =
                    projectPage.getPackagesFolderContainer().getProject();
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (null != config) {
                if (null != config.getProjectDetails()) {
                    Destinations globalDestinations =
                            config.getProjectDetails().getGlobalDestinations();
                    if (null != globalDestinations) {
                        EList<Destination> destinations =
                                globalDestinations.getDestination();
                        if (destinations.size() > 0) {
                            for (Destination destination : destinations) {
                                String type = destination.getType();
                                command =
                                        DestinationUtil
                                                .getSetDestinationEnabledCommand(editingDomain,
                                                        processObject,
                                                        type,
                                                        true);
                                cCmd.append(command);
                            }
                        } else {
                            String destinationFromPreferences =
                                    UserInfoUtil.getProjectPreferences(project)
                                            .getDestination();
                            command =
                                    DestinationUtil
                                            .getSetDestinationEnabledCommand(editingDomain,
                                                    processObject,
                                                    destinationFromPreferences,
                                                    true);
                        }
                    }
                    if (!cCmd.isEmpty() && cCmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cCmd);
                    }
                }
            }
        }

    }

    /**
     * @param xpdlPackage
     * @return The process for the task library.
     */
    private Process createTaskLibrary(Package xpdlPackage) {
        Process taskLibrary = Xpdl2Factory.eINSTANCE.createProcess();

        String taskLibraryName = xpdlPackage.getName() + "-" //$NON-NLS-1$
                + Messages.NewTaskLibraryWizard_NewTaskLibraryNameSuffix_label;

        setDisplayNameAndName(taskLibrary, taskLibraryName);

        taskLibrary.setProcessHeader(Xpdl2Factory.eINSTANCE
                .createProcessHeader());

        Xpdl2ModelUtil.setOtherAttribute(taskLibrary,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType(),
                XpdModelType.TASK_LIBRARY);

        return taskLibrary;
    }

    /**
     * @param taskLibrary
     * 
     * @return Create the pool for the task library.
     */
    private Pool createTaskLibraryPool(Process taskLibrary) {
        Pool pool = Xpdl2Factory.eINSTANCE.createPool();
        setDisplayNameAndName(pool,
                Messages.NewTaskLibraryWizard_DefaultPoolName_label);

        NodeGraphicsInfo ngi =
                createNodeGraphicsInfo(ProcessWidgetColors.POOL_HEADER_FILL,
                        ProcessWidgetColors.POOL_HEADER_LINE,
                        null,
                        null,
                        null);
        ngi.setIsVisible(true);

        pool.getNodeGraphicsInfos().add(ngi);

        pool.setProcessId(taskLibrary.getId());

        return pool;
    }

    private Lane createTaskLibraryLane(Pool pool, boolean isAlternateLane) {
        Lane lane = Xpdl2Factory.eINSTANCE.createLane();

        setDisplayNameAndName(lane, Messages.NewTaskLibraryWizard_TaskSet_label);

        NodeGraphicsInfo ngi =
                createNodeGraphicsInfo(isAlternateLane ? ProcessWidgetColors.LANE_ALTERNATE_FILL
                        : ProcessWidgetColors.LANE_FILL,
                        ProcessWidgetColors.LANE_LINE,
                        null,
                        new Dimension(0, 300),
                        null);
        lane.getNodeGraphicsInfos().add(ngi);

        return lane;
    }

    /**
     * @param lane
     * @return New text annotation.
     */
    private Artifact createTaskLibraryAnnotation(Lane lane, Point pos) {
        Artifact annot = Xpdl2Factory.eINSTANCE.createArtifact();
        annot.setArtifactType(ArtifactType.ANNOTATION_LITERAL);

        NodeGraphicsInfo ngi =
                createNodeGraphicsInfo(null,
                        ProcessWidgetColors.NOTE_LINE,
                        pos,
                        new Dimension(0, 20),
                        lane.getId());
        ngi.setWidth(0);
        annot.getNodeGraphicsInfos().add(ngi);

        annot
                .setTextAnnotation(Messages.NewTaskLibraryWizard_AddTasksHereAnnotation_longdesc);
        return annot;
    }

    /**
     * @param lane
     * @param userLiteral
     * @return a new task for the given type setup fo rthe given lane.
     */
    private Activity createTaskLibraryTask(Lane lane, Point pos) {
        TaskType tt = TaskType.NONE_LITERAL;

        Activity task =
                ElementsFactory.createTask(pos,
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        lane.getId(),
                        tt,
                        ProcessWidgetColors
                                .getInstance(ProcessWidgetType.TASK_LIBRARY)
                                .getGraphicalNodeColor(null,
                                        tt.getGetDefaultFillColorId())
                                .toString(),
                        ProcessWidgetColors
                                .getInstance(ProcessWidgetType.TASK_LIBRARY)
                                .getGraphicalNodeColor(null,
                                        tt.getGetDefaultLineColorId())
                                .toString());
        return task;
    }

    /**
     * Create a new NodeGraphicsInfo element with the given settings any
     * parameter passed as null will not be added to node graphics info.
     * 
     * @param fillColorId
     * @param lineColorId
     * @param location
     * @param size
     *            Width = 0 to not set width, Height = 0 to not set height.
     * @param laneId
     * 
     * @return New NodeGraphicsInfo
     */
    private NodeGraphicsInfo createNodeGraphicsInfo(String fillColorId,
            String lineColorId, Point location, Dimension size, String laneId) {
        NodeGraphicsInfo ngi = Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo();

        if (fillColorId != null) {
            WidgetRGB color =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.TASK_LIBRARY)
                            .getGraphicalNodeColor(null, fillColorId);
            ngi.setFillColor(color.toString());
        }

        if (lineColorId != null) {
            WidgetRGB color =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.TASK_LIBRARY)
                            .getGraphicalNodeColor(null, lineColorId);
            ngi.setBorderColor(color.toString());
        }

        if (location != null) {
            Coordinates coords = Xpdl2Factory.eINSTANCE.createCoordinates();
            coords.setXCoordinate(location.x);
            coords.setYCoordinate(location.y);
            ngi.setCoordinates(coords);
        }

        if (size != null) {
            if (size.width > 0) {
                ngi.setWidth(size.width);
            }

            if (size.height > 0) {
                ngi.setHeight(size.height);
            }
        }

        if (laneId != null) {
            ngi.setLaneId(laneId);
        }

        return ngi;
    }

    /**
     * Set the display (label) name and token name (tokenised version of the
     * given display name) of the given named element.
     * 
     * @param namedElement
     * @param displayName
     */
    private void setDisplayNameAndName(NamedElement namedElement,
            String displayName) {
        Xpdl2ModelUtil.setOtherAttribute(namedElement,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                displayName);

        namedElement.setName(NameUtil.getInternalName(displayName, false));
        return;
    }
}
