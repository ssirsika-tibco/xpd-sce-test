/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * GatewayObjectUtil
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Nov 2009)
 */
public class GatewayObjectUtil {

    /**
     * Get the type of the gateway
     * 
     * @param act
     * 
     * @return gatewayType or <code>null</code> if not a gateway
     */
    public static GatewayType getGatewayType(Activity act) {

        Route route = act.getRoute();
        if (route == null) {
            return null;
        }

        JoinSplitType gt = Xpdl2ModelUtil.safeGetGatewayType(act);
        if (gt == null) {
            return GatewayType.EXCLUSIVE_DATA_LITERAL;
        }
        switch (gt.getValue()) {

        case JoinSplitType.PARALLEL:
            return GatewayType.PARALLEL_LITERAL;
        case JoinSplitType.COMPLEX:
            return GatewayType.COMPLEX_LITERAL;
        case JoinSplitType.INCLUSIVE:
            return GatewayType.INCLUSIVE_LITERAL;
        case JoinSplitType.EXCLUSIVE:
            if (route.isSetExclusiveType()) {
                ExclusiveType exclusiveType =
                        Xpdl2ModelUtil.safeGetExclusiveType(act);
                if (ExclusiveType.EVENT == exclusiveType) {
                    return GatewayType.EXLCUSIVE_EVENT_LITERAL;
                } else if (ExclusiveType.DATA == exclusiveType) {
                    return GatewayType.EXCLUSIVE_DATA_LITERAL;
                }
            }
        default:
            return GatewayType.EXCLUSIVE_DATA_LITERAL;
        }
    }

    /**
     * @param ed
     * @param activity
     * @param gatewayType
     * 
     * @return Command to set gateway type of the gateway activity.
     */
    public static Command getSetGatewayTypeCommand(EditingDomain ed,
            Activity activity, GatewayType gatewayType) {
        Route route = Xpdl2Factory.eINSTANCE.createRoute();

        JoinSplitType type;

        switch (gatewayType.getValue()) {
        case GatewayType.COMPLEX:
            type = JoinSplitType.COMPLEX_LITERAL;
            break;
        case GatewayType.INCLUSIVE:
            type = JoinSplitType.INCLUSIVE_LITERAL;
            break;
        case GatewayType.PARALLEL:
            type = JoinSplitType.PARALLEL_LITERAL;
            break;
        case GatewayType.EXCLUSIVE_EVENT:
            type = JoinSplitType.EXCLUSIVE_LITERAL;
            route.setExclusiveType(ExclusiveType.EVENT);
            break;
        case GatewayType.EXLCUSIVE_DATA:
            route.setExclusiveType(ExclusiveType.DATA);
            route.setMarkerVisible(true);
        default:
            type = JoinSplitType.EXCLUSIVE_LITERAL;
            break;
        }
        route.setGatewayType(type);

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2GatewayAdapter_SetTypeGateway_menu);
        cmd.append(SetCommand.create(ed, activity, Xpdl2Package.eINSTANCE
                .getActivity_Route(), route));
        return cmd;
    }
}
