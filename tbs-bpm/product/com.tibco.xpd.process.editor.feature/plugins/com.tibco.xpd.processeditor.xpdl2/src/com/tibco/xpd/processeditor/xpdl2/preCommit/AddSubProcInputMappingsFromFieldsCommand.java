/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.Collection;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class to generate sub-process DataMapper input mappings according to the datafields (assumed to be have been
 * previously generated from the invoked sub-process' formal parameters.
 * 
 * Sid ACE-3321: There was a lot of stuff in super-class that we really don't need or want (more to do with generating
 * mappings for incoming request message activities than generating of biz services like this class is used for). So
 * quite a few changes now that we don' tneed that AND we need to generate DataMapper grammar mappings.
 * 
 * Also renamed from UpdateCallSubProcessMappings because it really is VERY specific to generate async sub-proc call.
 * 
 * @author sajain
 * @since Jan 20, 2015
 */
public class AddSubProcInputMappingsFromFieldsCommand extends CompoundCommand {

    /**
     * Editing domain.
     */
    private EditingDomain editingDomain;

    /**
     * Call sub-process activity.
     */
    private Activity callSubProcessActivity;

    /**
     * Class to generate mappings (only once at the beginning when the pageflow is generated from start type none event)
     * for a call sub-process in a generated pageflow.
     * 
     * @param editingDomain
     * @param obj
     */
    public AddSubProcInputMappingsFromFieldsCommand(EditingDomain editingDomain, Activity callSubProcessActivity) {
        super();
        this.editingDomain = editingDomain;
        this.callSubProcessActivity = callSubProcessActivity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommand#execute ()
     */
    @Override
    public void execute() {
        if (null != callSubProcessActivity) {
            SubFlow subFlow = ((SubFlow) callSubProcessActivity.getImplementation());
            updateSubFlowMappings(callSubProcessActivity, subFlow);
        }
        return;
    }

    /**
     * Update call sub-process activity's sub flow mappings.
     * 
     * @param activity
     * @param subFlow
     */
    protected void updateSubFlowMappings(Activity activity, SubFlow subFlow) {
        if (subFlow != null) {

            /*
             * Remove any existing data mappings (shouldn't be any but we will just in case)
             */
            if (!subFlow.getDataMappings().isEmpty()) {
                this.appendAndExecute(RemoveCommand.create(editingDomain, subFlow.getDataMappings()));
            }

            Object currentInputMappings = Xpdl2ModelUtil.getOtherElement(subFlow,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings());

            if (currentInputMappings != null) {
                this.appendAndExecute(Xpdl2ModelUtil.getRemoveOtherElementCommand(editingDomain,
                        subFlow,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings(),
                        currentInputMappings));
            }

            /*
             * Create input mappings, for generate asynch call sub-process (the only use case at the moment) this is all
             * we need.
             */
            ScriptDataMapper inputMappings = createInputMappingsForActivity(activity);

            if (inputMappings != null) {
                this.appendAndExecute(Xpdl2ModelUtil.getAddOtherElementCommand(editingDomain,
                        subFlow,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings(),
                        inputMappings));
            }
        }
    }

    /**
     * Generate the InputMappings object for the given sub-process call activity.
     * 
     * @param activity
     * @return the InputMappings object for the given sub-process call activity or <code>null</code> if there are no
     *         input mappings.
     */
    private ScriptDataMapper createInputMappingsForActivity(Activity activity) {
        ScriptDataMapper inputMappings = XpdExtensionFactory.eINSTANCE.createScriptDataMapper();

        inputMappings.setMapperContext(ProcessEditorConstants.DATAMAPPER_CONTEXT_PROCESS_TO_SUBPROCESS);
        inputMappings.setMappingDirection(DirectionType.IN_LITERAL);

        /*
         * Generate mappings for the data fields that will have been created for each input parameter.
         */
        Process process = activity.getProcess();
        Collection<DataField> dataFields = process.getDataFields();
        for (DataField dataField : dataFields) {
            DataMapping inMapping = createInMapping(dataField);
            if (null != inMapping) {
                inputMappings.getDataMappings().add(inMapping);
            }
        }

        return inputMappings.getDataMappings().size() > 0 ? inputMappings : null;
    }

    /**
     * Create an individual data mapping or the give datafield to same named input parameter in sub-process.
     * 
     * @param dataField
     * 
     * @return the data mapping
     */
    protected DataMapping createInMapping(DataField dataField) {

        DataMapping dataMapping = Xpdl2Factory.eINSTANCE.createDataMapping();

        dataMapping.setFormal(dataField.getName());

        Expression expression = Xpdl2ModelUtil.createExpression(dataField.getName());
        expression.setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
        dataMapping.setActual(expression);

        dataMapping.setDirection(DirectionType.IN_LITERAL);

        Xpdl2ModelUtil.setOtherAttribute(dataMapping,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId(),
                ProcessEditorConstants.DATAMAPPER_ACTIVITY_INTERFACE_CONTRIBUTOR_ID);

        Xpdl2ModelUtil.setOtherAttribute(dataMapping,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId(),
                ProcessEditorConstants.DATAMAPPER_PROCESS_TO_SUBPROCESS_CONTRIBUTOR_ID);

        return dataMapping;
    }

    /**
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     *
     * @return
     */
    @Override
    public boolean canExecute() {
        return true;
    }

}