package com.tibco.xpd.wm.tasklibrary.editor.wizards.task;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.CreationWizard;
import com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.wm.tasklibrary.editor.util.TaskLibraryTaskUtil;
import com.tibco.xpd.wm.tasklibrary.editor.wizards.TaskLibraryAndProjectSelectionPage;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New Task wizard
 * 
 * @author aallway
 * @since 3.2
 */
public class AbstractNewTaskWizard extends CreationWizard implements
        INewWizard, TemplateFactory {

    // Package selection page
    private TaskLibraryAndProjectSelectionPage selectProjectPage;

    private String[] pageTitles =
            new String[] { Messages.NewTaskWizard_TaskDetails_title,
                    Messages.NewTaskWizard_TaskDescription_title };

    private String[] pageDescriptions =
            new String[] { Messages.NewTaskWizard_TaskDetails_longdesc,
                    Messages.NewTaskWizard_TaskDescription_longdesc };

    private IWorkbench workbench;

    private Activity actTemplate;

    private TaskType initialTaskType;

    /**
     * New Type Declaration wizard
     */
    public AbstractNewTaskWizard(TaskType initialTaskType) {
        super();
        this.initialTaskType = initialTaskType;
        selectProjectPage = new TaskLibraryAndProjectSelectionPage();
        init(this, selectProjectPage);
        setWindowTitle(Messages.NewTaskWizard_NewTask_title);

        setDefaultPageImageDescriptor(TaskLibraryEditorPlugin
                .getImageDescriptor(TaskLibraryEditorContstants.IMG_TASK_WIZARD));

    }

    private EObject getEContainer(
            TaskLibraryAndProjectSelectionPage locationPage) {
        EObject container = selectProjectPage.getEContainer();
        if (container instanceof Package) {
            container =
                    TaskLibraryFactory.INSTANCE
                            .getTaskLibrary((Package) container);
        }
        return container;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#performFinish()
     */
    public boolean performFinish() {
        EObject container = getEContainer(selectProjectPage);

        if (container != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            if (wc != null) {
                Command cmd = getCommand();
                wc.getEditingDomain().getCommandStack().execute(cmd);

                // Select the new type declaration in the Project Explorer
                if (input != null) {
                    selectAndReveal(input);
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public Command getCommand() {
        CompoundCommand cmd = new CompoundCommand();
        if (finaliseTemplateActivity(cmd)) {

            Command c = super.getCommand();
            cmd.setLabel(c.getLabel());
            cmd.append(c);
            return cmd;

        }
        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        selectProjectPage.init(selection);

        // Now we have context of a process we can update the template activity
        // with the default lane info.
        finishTemplateObjectInitialisation(selection.toList());

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory#createTemplate
     * ()
     */
    public EObject createTemplate() {
        if (actTemplate == null) {
            actTemplate =
                    TaskLibraryTaskUtil.createDefaultTask(initialTaskType);
        }

        return actTemplate;
    }

    private void finishTemplateObjectInitialisation(List selectedElements) {
        if (actTemplate == null) {
            createTemplate();
        }

        if (getInputContainer() instanceof EObject) {
            Process process = Xpdl2ModelUtil.getProcess(getInputContainer());
            if (process != null) {
                //
                // Default the lane id to first lane IF there is only one -
                // otherwise force the user to select one.
                List<Lane> lanes = Xpdl2ModelUtil.getProcessLanes(process);
                if (lanes != null && lanes.size() == 1) {
                    Lane lane = lanes.get(0);

                    NodeGraphicsInfo ngi =
                            Xpdl2ModelUtil
                                    .getOrCreateNodeGraphicsInfo(actTemplate);
                    ngi.setLaneId(lane.getId());
                } 

                // 
                // Set the name to something unique.
                String finalName =
                        TaskLibraryTaskUtil.getUniqueTaskName(process,
                                Xpdl2ModelUtil.getDisplayName(actTemplate));

                Xpdl2ModelUtil.setOtherAttribute(actTemplate,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        finalName);

                actTemplate.setName(NameUtil.getInternalName(finalName, false));

                //
                // If the selection is all participants then set the task
                // participants.
                if (selectedElements.size() > 0
                        && areAllPerformers(selectedElements)) {

                    Performers performersElement =
                            Xpdl2Factory.eINSTANCE.createPerformers();
                    actTemplate.setPerformers(performersElement);

                    EList<Performer> performers =
                            performersElement.getPerformers();

                    for (Object o : selectedElements) {
                        if (o instanceof NamedElement) {
                            NamedElement el = (NamedElement) o;

                            Performer perf =
                                    Xpdl2Factory.eINSTANCE.createPerformer();
                            perf.setValue(el.getId());

                            performers.add(perf);
                        }
                    }
                }
                
            }
        }

        return;
    }

    /**
     * @param selectedElements
     * @return true if all the selected elements are participants OR performer
     *         fields.
     */
    private boolean areAllPerformers(List selectedElements) {
        for (Object sel : selectedElements) {
            if (!(sel instanceof Participant)) {
                if ((sel instanceof ProcessRelevantData)) {
                    DataType type = ((ProcessRelevantData) sel).getDataType();
                    if (!(type instanceof BasicType)
                            || !BasicTypeType.PERFORMER_LITERAL
                                    .equals(((BasicType) type).getType())) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Add the finishing touches to the new Task activity object
     * 
     * @param cmd
     * @return true on success false if command should be abandoned.
     */
    private boolean finaliseTemplateActivity(CompoundCommand cmd) {

        NodeGraphicsInfo ngi =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(actTemplate);

        EditingDomain editingDomain =
                WorkingCopyUtil.getEditingDomain(getInputContainer());
        Lane lane =
                Xpdl2ModelUtil.getLane((Process) getInputContainer(), ngi
                        .getLaneId());
        if (lane == null || editingDomain == null) {
            return false;
        }

        NodeGraphicsInfo laneNgi =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane,
                        editingDomain,
                        cmd);

        //
        // Set the colour to the default for final type
        TaskType taskType = TaskObjectUtil.getTaskType(actTemplate);

        String fillColorId = taskType.getGetDefaultFillColorId();
        String lineColorId = taskType.getGetDefaultLineColorId();

        ngi.setFillColor(ProcessWidgetColors
                .getInstance(ProcessWidgetType.TASK_LIBRARY)
                .getGraphicalNodeColor(null, fillColorId).toString());
        ngi.setBorderColor(ProcessWidgetColors
                .getInstance(ProcessWidgetType.TASK_LIBRARY)
                .getGraphicalNodeColor(null, lineColorId).toString());

        //
        // Position task in lane - first build up a picture of what's there
        // already.
        Coordinates newCoords = Xpdl2Factory.eINSTANCE.createCoordinates();

        int reqdLaneHeight =
                TaskLibraryTaskUtil.placeTaskInLane(actTemplate,
                        ProcessWidgetConstants.TASK_WIDTH_SIZE,
                        ProcessWidgetConstants.TASK_HEIGHT_SIZE,
                        lane,
                        newCoords);

        ngi.setCoordinates(newCoords);

        ngi.setWidth(ProcessWidgetConstants.TASK_WIDTH_SIZE);
        ngi.setHeight(ProcessWidgetConstants.TASK_HEIGHT_SIZE);

        // 
        // Extend lane to accommodate if necessary.
        //
        if (reqdLaneHeight > laneNgi.getHeight()) {
            cmd.append(SetCommand.create(editingDomain,
                    laneNgi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                    new Double(reqdLaneHeight)));
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getPageDescription(int)
     */
    public String getPageDescription(int index) {
        if (index > -1 && index < pageDescriptions.length) {
            return pageDescriptions[index];
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getPageTitle(int)
     */
    public String getPageTitle(int index) {
        if (index > -1 && index < pageTitles.length) {
            return pageTitles[index];
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory#createCommand
     * (org.eclipse.emf.ecore.EObject, org.eclipse.jface.wizard.IWizardPage)
     */
    public Command createCommand(EObject input, IWizardPage locationPage) {
        EObject container =
                getEContainer((TaskLibraryAndProjectSelectionPage) locationPage);

        if (container instanceof Process) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            if (wc != null) {

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.NewTaskWizard_AddTask_menu);
                cmd.append(AddCommand.create(wc.getEditingDomain(),
                        (Process) container,
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        input));
                return cmd;
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getWorkbench()
     */
    public IWorkbench getWorkbench() {
        return workbench;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.XpdSection.XpdSectionContainer#getInputContainer
     * ()
     */
    public EObject getInputContainer() {
        return getEContainer(selectProjectPage);
    }

}
