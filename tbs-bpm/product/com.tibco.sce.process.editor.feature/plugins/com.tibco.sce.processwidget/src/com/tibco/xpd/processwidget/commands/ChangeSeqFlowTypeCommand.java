/**
 * ChangeTaskTypeCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * <b>ChangeSeqFlowTypeCommand</b>
 * <p>
 * Wrapper for change flow type command.
 * </p>
 * <p>
 * This not only changes the task type but also...
 * </p>
 * <p>
 * <li>Resets the Line color to new type's colour IF current colour is default
 * for current type</li>
 * </p>
 * .
 * <p>
 * <b>NOTE: This command can only be used for sequence flow model objects that
 * have associated SequenceFlowAdapter.
 * 
 */
public class ChangeSeqFlowTypeCommand {

    public static Command create(EditingDomain editingDomain,
            EObject seqFlowModelObject, SequenceFlowType newFlowType) {

        SequenceFlowAdapter flowAdapter =
                ProcessWidgetCommandUtil
                        .getSequenceFlowAdapter(seqFlowModelObject);

        CompoundCommand ccmd = new CompoundCommand();

        ccmd.setLabel(Messages.ChangeSeqFlowTypeCommand_SetFlowType_menu);

        SequenceFlowType currFlowType = flowAdapter.getFlowType();

        // If the current name is the default name then
        // swap it to the new type default name.
        String name = flowAdapter.getName();
        String currTaskTypeStr = currFlowType.toString();

        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(flowAdapter);

        // If the current line color is default for current type
        // Then switch the fill color to default for new type.
        String colorId = currFlowType.getGetDefaultLineColorId();
        WidgetRGB defRGB = colors.getGraphicalNodeColor(null, colorId);
        WidgetRGB flowRGB = colors.getGraphicalNodeColor(flowAdapter, colorId);
        if (flowRGB.equals(defRGB)) {
            // Swap fill color for default for new color.
            flowRGB =
                    colors.getGraphicalNodeColor(null, newFlowType
                            .getGetDefaultLineColorId());

            ccmd.append(flowAdapter.getSetLineColorCommand(editingDomain,
                    flowRGB.toString()));
        }

        ccmd.append(flowAdapter.getSetFlowTypeCommand(editingDomain,
                newFlowType));

        return ccmd;
    }

    /**
     * Use {@link ChangeSeqFlowTypeCommand}.create() to create command.
     */
    private ChangeSeqFlowTypeCommand() {
    }

}
