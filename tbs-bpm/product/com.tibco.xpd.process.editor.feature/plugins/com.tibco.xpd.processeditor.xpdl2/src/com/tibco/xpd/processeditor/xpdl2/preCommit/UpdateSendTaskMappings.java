/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
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
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * class to generate mappings (only once at the beginning when the pageflow is
 * generated from start message event) for a send task in a generated pageflow
 * 
 * 
 * @author bharge
 * @since 3.3 (16 Mar 2010)
 */
public class UpdateSendTaskMappings extends UpdateJavaScriptMappingsCommand {

    private EditingDomain editingDomain;

    private Activity sendTaskActivity;

    /**
     * @param editingDomain
     * @param obj
     */
    public UpdateSendTaskMappings(EditingDomain editingDomain,
            Activity sendTaskActivity) {
        super(editingDomain, sendTaskActivity);
        this.editingDomain = editingDomain;
        this.sendTaskActivity = sendTaskActivity;
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
        if (null != sendTaskActivity) {
            TaskSend taskSend =
                    ((Task) sendTaskActivity.getImplementation()).getTaskSend();
            updateSendTaskMappings(sendTaskActivity, taskSend);
        }
        return;
    }

    /**
     * @param activity
     * @param taskSend
     */
    protected void updateSendTaskMappings(Activity activity, TaskSend taskSend) {
        if (taskSend.getMessage() != null) {
            Message clearedMessage = getClearedMessage(taskSend.getMessage());
            this.appendAndExecute(SetCommand.create(editingDomain,
                    taskSend,
                    Xpdl2Package.eINSTANCE.getTaskSend_Message(),
                    clearedMessage));
            List<DataMapping> mappingsForActivity =
                    createMappingsForActivity(activity);
            this.appendAndExecute(AddCommand.create(editingDomain,
                    clearedMessage,
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    mappingsForActivity));
        }
    }

    /**
     * @param msg
     * @return
     */
    protected Message getClearedMessage(Message msg) {
        Command cpyCmd = CopyCommand.create(editingDomain, msg);
        cpyCmd.execute();
        Message newMessage = (Message) cpyCmd.getResult().iterator().next();

        // Clear out the old mappings from the copy
        if (!(newMessage.getDataMappings().isEmpty())) {
            newMessage.getDataMappings().clear();
        }
        return newMessage;
    }

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
