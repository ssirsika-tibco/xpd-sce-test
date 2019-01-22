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
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * <b>ChangeGatewayTypeCommand</b>
 * <p>
 * Wrapper for change gateway type command.
 * </p>
 * <p>
 * This not only changes the gateway type but also...
 * </p>
 * <li>If Fill and Line color are default for current type they are switched to
 * default colors for new type</li> <li>Later should do the same for other
 * default things on gateway</li>
 * 
 */
public class ChangeGatewayTypeCommand {

    public static Command create(EditingDomain editingDomain,
            EObject gatewayObject, GatewayType newGatewayType) {

        GatewayAdapter gatewayAdapter =
                ProcessWidgetCommandUtil.getGatewayAdapter(gatewayObject);
        if (gatewayAdapter == null) {
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand ccmd = new CompoundCommand();

        ccmd.setLabel(Messages.ChangeGatewayTypeCommand_SetType_menu);

        GatewayType currGatewayType = gatewayAdapter.getGatewayType();

        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(gatewayAdapter);

        // If the current fill color is default for current type
        // Then switch the fill color to default for new type.
        String colorId = currGatewayType.getGetDefaultFillColorId();
        WidgetRGB defRGB = colors.getGraphicalNodeColor(null, colorId);
        WidgetRGB gatewayRGB =
                colors.getGraphicalNodeColor(gatewayAdapter, colorId);
        if (gatewayRGB.equals(defRGB)) {
            // Swap fill color for default for new color.
            gatewayRGB =
                    colors.getGraphicalNodeColor(null, newGatewayType
                            .getGetDefaultFillColorId());

            ccmd.append(gatewayAdapter.getSetFillColorCommand(editingDomain,
                    gatewayRGB.toString()));
        }

        // If the current fill color is default for current type
        // Then switch the fill color to default for new type.
        colorId = currGatewayType.getGetDefaultLineColorId();
        defRGB = colors.getGraphicalNodeColor(null, colorId);
        gatewayRGB = colors.getGraphicalNodeColor(gatewayAdapter, colorId);
        if (gatewayRGB.equals(defRGB)) {
            // Swap fill color for default for new color.
            gatewayRGB =
                    colors.getGraphicalNodeColor(null, newGatewayType
                            .getGetDefaultLineColorId());

            ccmd.append(gatewayAdapter.getSetLineColorCommand(editingDomain,
                    gatewayRGB.toString()));
        }

        ccmd.append(gatewayAdapter.getSetGatewayTypeCommand(editingDomain,
                newGatewayType));

        return ccmd;
    }

    /**
     * Use {@link ChangeGatewayTypeCommand}.create() to create command.
     */
    private ChangeGatewayTypeCommand() {
    }

}
