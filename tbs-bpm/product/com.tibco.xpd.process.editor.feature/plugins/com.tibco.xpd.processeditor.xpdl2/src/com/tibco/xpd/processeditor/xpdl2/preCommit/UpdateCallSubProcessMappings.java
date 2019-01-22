/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class to generate mappings (only once at the beginning when the pageflow is
 * generated from start type none event) for a call sub-process in a generated
 * pageflow
 * 
 * @author sajain
 * @since Jan 20, 2015
 */
public class UpdateCallSubProcessMappings extends
        UpdateJavaScriptMappingsCommand {

    /**
     * Editing domain.
     */
    private EditingDomain editingDomain;

    /**
     * Call sub-process activity.
     */
    private Activity callSubProcessActivity;

    /**
     * Class to generate mappings (only once at the beginning when the pageflow
     * is generated from start type none event) for a call sub-process in a
     * generated pageflow.
     * 
     * @param editingDomain
     * @param obj
     */
    public UpdateCallSubProcessMappings(EditingDomain editingDomain,
            Activity callSubProcessActivity) {
        super(editingDomain, callSubProcessActivity);
        this.editingDomain = editingDomain;
        this.callSubProcessActivity = callSubProcessActivity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommand#execute
     * ()
     */
    @Override
    public void execute() {
        if (null != callSubProcessActivity) {
            SubFlow subFlow =
                    ((SubFlow) callSubProcessActivity.getImplementation());
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
            SubFlow clearedSubFlow = getClearedSubFlow(subFlow);
            this.appendAndExecute(SetCommand.create(editingDomain,
                    activity.getImplementation(),
                    Xpdl2Package.eINSTANCE.getSubFlow(),
                    clearedSubFlow));
            List<DataMapping> mappingsForActivity =
                    createMappingsForActivity(activity);
            this.appendAndExecute(AddCommand.create(editingDomain,
                    subFlow,
                    Xpdl2Package.eINSTANCE.getSubFlow_DataMappings(),
                    mappingsForActivity));
        }
    }

    /**
     * Get cleared sub-flow (i.e., sub-flow minus the existing mappings).
     * 
     * @param msg
     * @return
     */
    protected SubFlow getClearedSubFlow(SubFlow subFlow) {
        Command cpyCmd = CopyCommand.create(editingDomain, subFlow);
        cpyCmd.execute();
        SubFlow newSubFlow = (SubFlow) cpyCmd.getResult().iterator().next();

        // Clear out the old mappings from the copy
        if (!(newSubFlow.getDataMappings().isEmpty())) {
            newSubFlow.getDataMappings().clear();
        }
        return newSubFlow;
    }

    @Override
    protected List<DataMapping> createMappingsForActivity(Activity activity) {
        List<DataMapping> dataMappings = new ArrayList<DataMapping>();
        // create mappings for all formal parameters and process level data
        // fields
        Process process = activity.getProcess();
        Collection<DataField> dataFields = process.getDataFields();
        for (DataField df : dataFields) {
            DataMapping inMapping =
                    createInMapping(activity.getName(),
                            df.getName(),
                            ModeType.IN_LITERAL,
                            DirectionType.IN_LITERAL);
            if (null != inMapping) {
                dataMappings.add(inMapping);
            }
        }
        return dataMappings;
    }

    /**
     * @param eObj
     * @param dataMappingName
     * @param modeType
     * @param mapList
     */
    @Override
    protected DataMapping createInMapping(String name, String dataMappingName,
            ModeType modeType, DirectionType directionType) {
        if (ModeType.IN_LITERAL.equals(modeType)
                || ModeType.INOUT_LITERAL.equals(modeType)) {
            DataMapping dataMapping =
                    Xpdl2Factory.eINSTANCE.createDataMapping();

            String formalString = getFormalString(name, dataMappingName);
            dataMapping.setFormal(formalString);
            Expression expression =
                    Xpdl2ModelUtil.createExpression(dataMappingName);
            expression.setScriptGrammar(getScriptGrammar());
            dataMapping.setActual(expression);
            dataMapping.setDirection(directionType);
            return dataMapping;
        }
        return null;
    }

}