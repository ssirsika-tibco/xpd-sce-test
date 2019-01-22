/**
 * RefactorAsIndependentSubProcCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.InlineSubProcessUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessDiagramUtils;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * RefactorIndiSubInlineCommand
 * 
 * EMF Command to inline independent sub-process task (i.e. inline the content
 * of the referenced sub-process in place of the given sub-process task..
 * 
 */
public class RefactorIndiSubInlineCommand implements Command {

    // Command delegate.
    private CompoundCommand delegateCmd = new CompoundCommand();

    // Editing domain providing model edit framework.
    EditingDomain editingDomain = null;

    // Validation (canExecute()) was previously attempted.
    private boolean validationAttempted = false;

    // Result of previous validation.
    private int validationResult = 0;

    private List<EObject> inlinedContentObjects = new ArrayList<EObject>();

    // Info passed to / from refactor wizard.
    private RefactorIndiSubInlineInfo refactorInfo =
            new RefactorIndiSubInlineInfo();

    /**
     * Prepares refactor of the given objects into an independent sub-proc.
     * 
     * @param objects
     *            List of model activities and artifacts to refactor into
     *            sub-proc.
     */
    public RefactorIndiSubInlineCommand(EditingDomain editingDomain,
            Activity subProcTask,
            DiagramModelImageProvider callingProcessImageProvider,
            DiagramModelImageProvider subProcessImageProvider) {
        this.editingDomain = editingDomain;

        delegateCmd = new CompoundCommand();

        EObject procOrInterface =
                TaskObjectUtil.getSubProcessOrInterface(subProcTask);
        if (procOrInterface instanceof Process) {
            Process subProcess = (Process) procOrInterface;

            refactorInfo.subProcess = subProcess;
            refactorInfo.subProcessTask = subProcTask;
            refactorInfo.inlineAsEmbeddedSubProc = false;
            refactorInfo.callingProcessImageProvider =
                    callingProcessImageProvider;
            refactorInfo.subProcessImageProvider = subProcessImageProvider;

            delegateCmd
                    .setLabel(Messages.RefactorIndiSubInlineCommand_InlineIndiSubproc_menu2);

        } else {
            delegateCmd.append(UnexecutableCommand.INSTANCE);
        }

    }

    public List<EObject> getCreatedObjects() {
        return inlinedContentObjects;
    }

    /**
     * Validates that the refactor to independent sub-proc can be performed for
     * selected objects.
     * <p>
     * On failure returns the reason for the failure.
     * </p>
     * 
     * @return >=0 flags or -1 on fatal error.
     */
    private int validateRefactor() {
        // Only need to do validation once.
        if (validationAttempted) {
            return validationResult;
        }

        validationAttempted = true;

        validationResult = 0;

        if (refactorInfo.subProcess == null
                || refactorInfo.subProcessTask == null) {
            validationResult = -1;

        } else {
        }

        return validationResult;
    }

    /**
     * Return the command that will refactor sub-proc content in place of
     * calling task.
     * 
     * @return
     */
    private Command getRefactorCommand() {
        CompoundCommand cmd = new CompoundCommand();

        Package tgtPackage =
                Xpdl2ModelUtil.getPackage(refactorInfo.subProcessTask);

        Process callingProcess = refactorInfo.subProcessTask.getProcess();

        //
        // Get a list of the sub-process objects (activities, transitions,
        // artifacts and associations) that need to be moved up into calling
        // process.
        // If in-lining into embedded then do not remove start/end events.
        boolean removeStartEndEvents = true;
        if (refactorInfo.inlineAsEmbeddedSubProc) {
            removeStartEndEvents = false;
        }

        List<EObject> originalSubProcObjects =
                InlineSubProcessUtil.getInlineSubProcObjects(editingDomain,
                        refactorInfo.subProcess,
                        refactorInfo.inlineSubProcAnalysis,
                        removeStartEndEvents,
                        (refactorInfo.subProcess.getPackage() != tgtPackage),
                        tgtPackage,
                        null);

        // Grab a copy of the sub-process objects (we don't want to affect the
        // orginals!
        Command cpyCmd =
                CopyCommand.create(editingDomain, originalSubProcObjects);
        if (!cpyCmd.canExecute()) {
            throw new RuntimeException(
                    "Refactor Inline Sub-Process: Failed to generate copy of sub-process objects"); //$NON-NLS-1$
        }
        cpyCmd.execute();

        Collection<EObject> copyOfSubProcObjects =
                (Collection<EObject>) cpyCmd.getResult();

        //
        // Original sub-process objects may have been spread over multiple
        // lanes. Compress the unused space above/below objects in lanes.
        InlineSubProcessUtil
                .adjustSubProcObjectsLocations(refactorInfo.subProcess,
                        copyOfSubProcObjects);

        EObject subProcTaskContainer =
                InlineSubProcessUtil
                        .getTaskContainer(refactorInfo.subProcessTask);

        //
        // Add the sub-proc participants to the copy of sub-proc objects. We
        // can leave getAddDiagramObjectsCommand() to handle these.
        Collection<Participant> partics =
                getSubProcParticipants(copyOfSubProcObjects);

        copyOfSubProcObjects.addAll(partics);

        Activity startOfInlinedFlowAct = null;
        Activity endOfInlinedFlowAct = null;

        FlowContainer flowContainer =
                refactorInfo.subProcessTask.getFlowContainer();

        //
        // Remove the sub-process task we're replacing.
        removeSubProcessTask(cmd);

        //
        // If in-lining into embedded sub-process then replace sub-proc task
        // with embedded sub-process. And paste contents into there.
        if (refactorInfo.inlineAsEmbeddedSubProc) {
            // Create new embedded sub-proc.
            Activity embeddedSubProc =
                    createEmbeddedSubProc(callingProcess,
                            subProcTaskContainer,
                            copyOfSubProcObjects);

            // Add the embedded sub-process to the container of the original
            // sub-process task.

            cmd.append(AddCommand.create(editingDomain,
                    flowContainer,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    embeddedSubProc));

            //
            // Paste the sub-proc objects into the embedded sub-process.
            ProcessPasteCommand addSubProcContentCmd =
                    Xpdl2ProcessDiagramUtils
                            .getAddDiagramObjectsCommand(editingDomain,
                                    callingProcess,
                                    embeddedSubProc,
                                    new Point(
                                            ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN,
                                            ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN),
                                    copyOfSubProcObjects,
                                    null,
                                    false,
                                    false);
            cmd.append(addSubProcContentCmd);

            // Paste into embdeded sub-process will have created new copies of
            // objects, so from now on we'll work on those copies.
            copyOfSubProcObjects = addSubProcContentCmd.getPasteObjects();

            startOfInlinedFlowAct = embeddedSubProc;
            endOfInlinedFlowAct = embeddedSubProc;

            // As far as our caller is concerned our new embedded sub-proc task
            // is all of the new content it needs to know about.
            inlinedContentObjects.add(embeddedSubProc);

        } else {
            //
            // We are inlining the sub-process content directly into the
            // sub-process tasks's container.
            //
            // Paste the sub-proc objects into the embedded sub-process.
            Rectangle bnds =
                    Xpdl2ModelUtil.getObjectBounds(refactorInfo.subProcessTask);

            ProcessPasteCommand addSubProcContentCmd =
                    Xpdl2ProcessDiagramUtils
                            .getAddDiagramObjectsCommand(editingDomain,
                                    callingProcess,
                                    subProcTaskContainer,
                                    new Point(bnds.x, bnds.y),
                                    copyOfSubProcObjects,
                                    null,
                                    false,
                                    false);
            cmd.append(addSubProcContentCmd);

            // Paste into container will have created new copies of
            // objects, so from now on we'll work on those copies.
            copyOfSubProcObjects = addSubProcContentCmd.getPasteObjects();

            startOfInlinedFlowAct =
                    (Activity) addSubProcContentCmd.getFlowStartActivity();
            endOfInlinedFlowAct =
                    (Activity) addSubProcContentCmd.getFlowEndActivity();

            // As far as our caller is concerned our new embedded sub-proc task
            // is all of the new content it needs to know about.
            inlinedContentObjects
                    .addAll(addSubProcContentCmd.getPasteObjects());

        }

        //
        // Copy up the sub-process data fields and change references in
        // sub-proc content activities of formal params we have mapped from
        // calling process to the data that they were mapped from.
        Map<ProcessRelevantData, ProcessRelevantData> fieldMap =
                InlineSubProcessUtil.getSubProcFields(editingDomain,
                        callingProcess,
                        refactorInfo.subProcessTask,
                        refactorInfo.subProcess,
                        tgtPackage,
                        null);

        CompoundCommand copyFieldsCmd =
                InlineSubProcessUtil
                        .getAddFieldsToCallingProcCommand(editingDomain,
                                callingProcess,
                                refactorInfo.subProcess,
                                copyOfSubProcObjects,
                                fieldMap);
        if (copyFieldsCmd != null && copyFieldsCmd.getCommandList().size() > 0) {
            cmd.append(copyFieldsCmd);
        }

        // Reconnect the existing sequence flow to / from the original
        // sub-process task to / from the start/end of inlined sub-proc content.
        // Also reconnect associations/message flows if possible.
        Command reconnCmd =
                InlineSubProcessUtil.reconnectConnections(editingDomain,
                        refactorInfo.subProcessTask.getId(),
                        flowContainer,
                        startOfInlinedFlowAct,
                        endOfInlinedFlowAct);

        if (reconnCmd != null && cmd.getCommandList().size() > 0) {
            cmd.append(reconnCmd);
        }

        return cmd;
    }

    /**
     * Delete the sub-process task and remove any associations/message flows
     * connected to it.
     * 
     * @param cmd
     */
    private void removeSubProcessTask(CompoundCommand cmd) {
        cmd.append(RemoveCommand.create(editingDomain,
                refactorInfo.subProcessTask));

        return;
    }

    /**
     * @param copyOfSubProcObjects
     * @return
     */
    private Collection<Participant> getSubProcParticipants(
            Collection<EObject> copyOfSubProcObjects) {
        Collection<Participant> partics = Collections.EMPTY_LIST;

        List<Participant> origSubPartics =
                InlineSubProcessUtil
                        .getSubProcParticipants(refactorInfo.subProcessTask
                                .getProcess(),
                                refactorInfo.subProcess,
                                copyOfSubProcObjects);
        if (origSubPartics.size() > 0) {
            Command cpyCmd = CopyCommand.create(editingDomain, origSubPartics);

            if (!cpyCmd.canExecute()) {
                throw new RuntimeException(
                        "Refactor Inline Sub-Process: Failed to generate copy of sub-process participants."); //$NON-NLS-1$
            }

            cpyCmd.execute();

            partics = (Collection<Participant>) cpyCmd.getResult();
        }

        return partics;
    }

    /**
     * @param subProcTaskContainer
     * @param copyOfSubProcObjects
     * @return
     */
    private Activity createEmbeddedSubProc(Process process,
            EObject subProcTaskContainer,
            Collection<EObject> copyOfSubProcObjects) {
        ProcessWidgetType processType = TaskObjectUtil.getProcessType(process);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                TaskType.EMBEDDED_SUBPROCESS_LITERAL
                                        .getGetDefaultFillColorId());
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                TaskType.EMBEDDED_SUBPROCESS_LITERAL
                                        .getGetDefaultLineColorId());

        Rectangle objBnds =
                InlineSubProcessUtil
                        .getSubProcObjectsBounds(copyOfSubProcObjects);

        Rectangle subProcTaskBnds =
                Xpdl2ModelUtil.getObjectBounds(refactorInfo.subProcessTask);

        Dimension embSize =
                new Dimension(
                        objBnds.width
                                + (ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN * 3),
                        objBnds.height
                                + (ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN * 5));

        Point location =
                new Point(subProcTaskBnds.x + (embSize.width / 2),
                        subProcTaskBnds.y + (embSize.height / 2));

        Activity embeddedSubProc =
                ElementsFactory
                        .createTask(location,
                                embSize,
                                (subProcTaskContainer instanceof Lane) ? ((Lane) subProcTaskContainer)
                                        .getId() : null,
                                TaskType.EMBEDDED_SUBPROCESS_LITERAL,
                                fillColor.toString(),
                                lineColor.toString());

        ActivitySet actSet = Xpdl2Factory.eINSTANCE.createActivitySet();
        BlockActivity ba = embeddedSubProc.getBlockActivity();

        ba.setActivitySetId(actSet.getId());

        if (!refactorInfo.subProcessTask.getName()
                .startsWith(TaskType.SUBPROCESS_LITERAL.toString())) {
            // If indi sub-proc doesn't have default name for its type then
            // set name of embedded task to it.
            embeddedSubProc.setName(refactorInfo.subProcessTask.getName());
        }

        return embeddedSubProc;
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canExecute()
     */
    @Override
    public boolean canExecute() {
        boolean ret = false;

        // We have to validate AND complain when asked if we can execute. This
        // is because if we return true from can execute then the command will
        // ALWAYS get added to the undo stack (we can't just return false from
        // canUndo because the entry is still put on command stack but is
        // disabled).

        // So make sure we only message box once in case we get called multiple
        // times.
        if (validationAttempted) {
            // Already done validation once, just return according to last
            // result.
            if (validationResult == -1) {
                return false;
            } else {
                return true;
            }
        }

        validateRefactor();

        if (validationResult != -1) {

            RefactorIndiSubInlineWizard wizard =
                    new RefactorIndiSubInlineWizard(refactorInfo);

            WizardDialog wizDig = new WizardDialog(getShell(), wizard);
            if (wizDig.open() == WizardDialog.OK) {
                ret = true;
            }

        } else {
            ret = false;
        }
        return ret;
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canUndo()
     */
    @Override
    public boolean canUndo() {
        return delegateCmd.canUndo();
    }

    /**
     * @param command
     * @return
     * @see org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command.Command)
     */
    @Override
    public Command chain(Command command) {
        return delegateCmd.chain(command);
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#dispose()
     */
    @Override
    public void dispose() {
        delegateCmd.dispose();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    @Override
    public void execute() {
        Command cmd = getRefactorCommand();

        delegateCmd.appendAndExecute(cmd);
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
     */
    @Override
    public Collection getAffectedObjects() {
        return delegateCmd.getAffectedObjects();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getDescription()
     */
    @Override
    public String getDescription() {
        return delegateCmd.getDescription();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getLabel()
     */
    @Override
    public String getLabel() {
        return delegateCmd.getLabel();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getResult()
     */
    @Override
    public Collection getResult() {
        return delegateCmd.getResult();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    @Override
    public void redo() {
        delegateCmd.redo();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#undo()
     */
    @Override
    public void undo() {
        delegateCmd.undo();
    }

    private Shell getShell() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    }

}
