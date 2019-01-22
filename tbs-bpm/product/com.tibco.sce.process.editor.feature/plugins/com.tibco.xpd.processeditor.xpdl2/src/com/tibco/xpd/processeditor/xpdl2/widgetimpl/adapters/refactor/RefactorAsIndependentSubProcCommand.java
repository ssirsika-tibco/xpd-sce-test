/**
 * RefactorAsIndependentSubProcCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.XpdEcoreUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.ReparentListElementCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * RefactorAsIndependentSubProcCommand
 * 
 * EMF Command to refactor selected objects into independent sub-process.
 * 
 */
@SuppressWarnings("unchecked")
public class RefactorAsIndependentSubProcCommand extends
        AbstractRefactorActivitiesAsProcessCommand {

    private SubFlow newIndiSubProcRef;

    /**
     * Sub class can get a handle (to do extra stuff with the SubFlow) after
     * calling super method that creates an instance for SubFlow
     * 
     * @return the newIndiSubProcRef
     */
    public SubFlow getNewIndiSubProcRef() {

        return newIndiSubProcRef;
    }

    private static final int SUBPROCTASK_WIDTH =
            ProcessWidgetConstants.SUBFLOW_WIDTH_SIZE;

    private static final int SUBPROCTASK_HEIGHT =
            ProcessWidgetConstants.SUBFLOW_HEIGHT_SIZE;

    /**
     * @param editingDomain
     * @param objects
     * @param imageProvider
     */
    public RefactorAsIndependentSubProcCommand(EditingDomain editingDomain,
            List<EObject> objects, DiagramModelImageProvider imageProvider) {
        super(editingDomain, objects, new RefactorAsIndiSubprocWizardInfo(
                imageProvider, TaskType.SUBPROCESS_LITERAL.toString()));
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#getTargetSubProcessType()
     * 
     * @return
     */
    @Override
    protected ProcessWidgetType getTargetSubProcessType() {
        return ProcessWidgetType.BPMN_PROCESS;
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#createNewProcess()
     * 
     * @return
     */
    @Override
    protected Process createNewProcess() {
        Process newProcess = Xpdl2Factory.eINSTANCE.createProcess();

        return newProcess;
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#createRefactorWizard()
     * 
     * @return
     */
    @Override
    protected BaseRefactorAsSubProcWizard createRefactorWizard() {
        BaseRefactorAsSubProcWizard wizard =
                new RefactorAsIndiSubProcWizard(getRefactorInfo(),
                        getEditingDomain());
        return wizard;
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#configureProcessInvokeActivity(org.eclipse.emf.common.command.CompoundCommand,
     *      com.tibco.xpd.xpdl2.Activity)
     * 
     * @param cmd
     * @param newProcessInvokeActivity
     * @return
     */
    @Override
    protected Dimension configureProcessInvokeActivity(CompoundCommand cmd,
            Activity newProcessInvokeActivity) {

        /*
         * Add Adhoc configurations to the Re-usable sub process Activity left
         * back in the main process.
         */
        if (singleSelAdHocTaskConfiguration != null) {

            cmd.append(Xpdl2ModelUtil
                    .getSetOtherElementCommand(getEditingDomain(),
                            newProcessInvokeActivity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration(),
                            singleSelAdHocTaskConfiguration));

        }

        newIndiSubProcRef = Xpdl2Factory.eINSTANCE.createSubFlow();

        newIndiSubProcRef.setProcessId(getNewProcess().getId());

        cmd.append(SetCommand.create(getEditingDomain(),
                newProcessInvokeActivity,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                newIndiSubProcRef));

        return new Dimension(SUBPROCTASK_WIDTH, SUBPROCTASK_HEIGHT);
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#configureProcessInvokeActivityForData(org.eclipse.emf.common.command.CompoundCommand,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData,
     *      com.tibco.xpd.xpdl2.FormalParameter, com.tibco.xpd.xpdl2.ModeType)
     * 
     * @param cmd
     * @param oldFieldOrParam
     * @param newFormalParam
     * @param newParamMode
     */
    @Override
    protected void configureProcessInvokeActivityForData(CompoundCommand cmd,
            ProcessRelevantData oldFieldOrParam,
            FormalParameter newFormalParam, ModeType newParamMode) {
        String defaultGrammar =
                ScriptGrammarFactory.getDefaultScriptGrammar(null);

        /*
         * Add the parameter mapping from old field/param to new formal param in
         * sub-proc call step. Add it to new process.
         */

        /*
         * v3 Studio now only ever uses separate In and OUT params so have to
         * define separate ones.
         */
        if (ModeType.IN_LITERAL.equals(newParamMode)
                || ModeType.INOUT_LITERAL.equals(newParamMode)) {
            DataMapping dmIn = Xpdl2Factory.eINSTANCE.createDataMapping();
            dmIn.setDirection(DirectionType.IN_LITERAL);
            dmIn.setFormal(newFormalParam.getName());

            Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
            expression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            oldFieldOrParam.getName());
            expression.setScriptGrammar(defaultGrammar);

            dmIn.setActual(expression);

            cmd.append(AddCommand.create(getEditingDomain(),
                    newIndiSubProcRef,
                    Xpdl2Package.eINSTANCE.getSubFlow_DataMappings(),
                    dmIn));
        }

        if (ModeType.OUT_LITERAL.equals(newParamMode)
                || ModeType.INOUT_LITERAL.equals(newParamMode)) {
            DataMapping dmOut = Xpdl2Factory.eINSTANCE.createDataMapping();
            dmOut.setDirection(DirectionType.OUT_LITERAL);
            dmOut.setFormal(newFormalParam.getName());

            Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
            expression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            oldFieldOrParam.getName());
            expression.setScriptGrammar(defaultGrammar);

            dmOut.setActual(expression);

            cmd.append(AddCommand.create(getEditingDomain(),
                    newIndiSubProcRef,
                    Xpdl2Package.eINSTANCE.getSubFlow_DataMappings(),
                    dmOut));
        }

        return;
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#addParticipants(org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param cmd
     */
    @Override
    protected void addParticipants(CompoundCommand cmd) {
        /* First collect all the participants that are to be copied. */
        List<Participant> particToCopy = new ArrayList<Participant>();

        RefactorAsIndiSubprocWizardInfo refactorInfo = getRefactorInfo();

        for (RefactorReferencedParticipantInfo particInfo : refactorInfo.referencedParticipants) {
            if (particInfo.referencedElseWhere || !particInfo.moveParticipant) {
                particToCopy.add(particInfo.participant);
            }
        }

        /* Copy of the participants and create a map of old to new id. */
        if (particToCopy.size() > 0) {
            HashMap<String, String> copyParticMap =
                    new HashMap<String, String>();

            Command copyCmd =
                    CopyCommand.create(getEditingDomain(), particToCopy);

            copyCmd.execute();

            Collection newPartics = copyCmd.getResult();
            if (newPartics != null) {
                for (Iterator iter = newPartics.iterator(); iter.hasNext();) {
                    Participant newPartic = (Participant) iter.next();
                    /* Create a new id. */
                    String newId = XpdEcoreUtil.generateUUID();

                    copyParticMap.put(newPartic.getId(), newId);

                    newPartic.eSet(Xpdl2Package.eINSTANCE
                            .getUniqueIdElement_Id(), newId);

                    /* Add it to new process. */
                    cmd.append(AddCommand.create(getEditingDomain(),
                            getNewProcess(),
                            Xpdl2Package.eINSTANCE
                                    .getParticipantsContainer_Participants(),
                            newPartic));

                }
            }

            /*
             * Go thru them replacing performer participant id reference with
             * new value.
             */
            for (EObject obj : getAllSelActsAndTrans()) {
                if (obj instanceof Activity) {
                    Activity act = (Activity) obj;

                    List<Performer> performers = act.getPerformerList();

                    for (Performer perf : performers) {
                        String oldId = perf.getValue();

                        String newId = copyParticMap.get(oldId);

                        if (newId != null) {
                            cmd.append(SetCommand
                                    .create(getEditingDomain(),
                                            perf,
                                            Xpdl2Package.eINSTANCE
                                                    .getPerformer_Value(),
                                            newId));
                        }
                    }
                }
            }
        }

        /* Now deal with the participants to move. */
        for (RefactorReferencedParticipantInfo particInfo : refactorInfo.referencedParticipants) {
            if (!particInfo.referencedElseWhere && particInfo.moveParticipant) {
                cmd.append(ReparentListElementCommand
                        .create(getEditingDomain(),
                                particInfo.participant,
                                getNewProcess(),
                                Xpdl2Package.eINSTANCE
                                        .getParticipantsContainer_Participants()));
            }
        }
        return;
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorActivitiesAsProcessCommand#performFinalConfiguration(org.eclipse.emf.common.command.CompoundCommand,
     *      org.eclipse.emf.ecore.EObject)
     * 
     * @param cmd
     * @param object
     */
    @Override
    protected void performFinalConfiguration(CompoundCommand cmd, EObject object) {
        super.performFinalConfiguration(cmd, object);
        /*
         * remove the Adhoc Configurations from the original task.
         */
        if (singleSelAdHocTaskConfiguration != null) {

            if (object instanceof Activity) {

                Activity activity = (Activity) object;

                Object adHocConfig =
                        Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AdHocTaskConfiguration());

                if (adHocConfig != null) {
                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(getEditingDomain(),
                                    activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AdHocTaskConfiguration(),
                                    adHocConfig));
                }
            }
        }
    }

}
