/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.GatewayObjectUtil;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;

/**
 * Activity Adapter<br>
 * This adapter is for activities that has 'Route' mark
 * 
 * @author wzurek
 */
public class Xpdl2GatewayAdapter extends Xpdl2BaseFlowObjectAdapter
        implements GatewayAdapter {

    private OclBasedNotifier implListener;

    @Override
    public void setTarget(Notifier newTarget) {
        super.setTarget(newTarget);

        if (implListener != null) {
            implListener.getTarget().eAdapters().remove(implListener);
        } else {
            implListener = new OclBasedNotifier("self.route", //$NON-NLS-1$
                    Xpdl2Package.eINSTANCE.getActivity());
            implListener.addListener(new OclQueryListener() {

                @Override
                public void oclNotifyChanged(EObject base, Object target,
                        Notification notification) {
                    /*
                     * Sid XPD-8302 - pass message in so can ignore adapter
                     * removal
                     */
                    fireDiagramNotification(notification);
                }
            });

        }

        if (getTarget() != null) {
            getTarget().eAdapters().add(implListener);
        }

    }

    @Override
    public GatewayType getGatewayType() {
        return getGatewayType(getActivity());
    }

    public static GatewayType getGatewayType(Activity act) {
        return GatewayObjectUtil.getGatewayType(act);
    }

    @Override
    public Command getSetGatewayTypeCommand(EditingDomain ed,
            GatewayType gatewayType) {

        Activity act = getActivity();

        return GatewayObjectUtil.getSetGatewayTypeCommand(ed, act, gatewayType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.
     * Xpdl2BaseFlowAdapter
     * #getDeleteCommand(org.eclipse.emf.edit.domain.EditingDomain)
     */
    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        CompoundCommand cmd = new CompoundCommand();

        cmd.setLabel(Messages.Xpdl2GatewayAdapter_DeleteGateway_menu);

        Command delCmd = super.getDeleteCommand(editingDomain);

        if (delCmd == null) {
            return UnexecutableCommand.INSTANCE;
        }

        cmd.append(delCmd);
        return cmd;
    }

    @Override
    public boolean isXORDataMarkerVisible() {
        Activity act = getActivity();
        if (act != null) {
            Route route = act.getRoute();
            if (route != null) {
                return route.isMarkerVisible();
            }

        }
        return false;
    }

}
