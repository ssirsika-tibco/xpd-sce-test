/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.cycletypegadget;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Click Gadget policy for cycling gateway type.
 * 
 * @author aallway
 * @since 3.2
 */
public class CycleGatewayTypeGadgetPolicy extends
        AbstractCycleObjectTypeGadgetPolicy {

    public CycleGatewayTypeGadgetPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain,
            EditPolicyEnablementProvider policyEnabledmentProvider) {
        super(adapterFactory, editingDomain, policyEnabledmentProvider);
    }

    @Override
    protected Command createCycleNextObjectTypeCommand(
            EditingDomain editingDomain, EditPart hostEditPart, Object newType) {
        if (hostEditPart instanceof GatewayEditPart
                && newType instanceof GatewayType) {
            GatewayEditPart gateway = (GatewayEditPart) hostEditPart;

            return new EMFCommandWrapper(editingDomain, gateway
                    .getGatewayAdapter()
                    .getSetGatewayTypeCommand(editingDomain,
                            (GatewayType) newType));

        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected Object getCurrentType(EditPart hostEditPart) {
        if (hostEditPart instanceof GatewayEditPart) {
            GatewayEditPart gateway = (GatewayEditPart) hostEditPart;

            return gateway.getGatewayAdapter().getGatewayType();
        }
        return null;
    }

    @Override
    protected Object[] getObjectTypes(EditPart hostEditPart) {
        if (hostEditPart instanceof GatewayEditPart) {
            GatewayEditPart gateway = (GatewayEditPart) hostEditPart;

            /*
             * XPD-2516: Add the task type to set if not excluded by some
             * processEditorConfiguration/ObjectTypeExclusion.
             */
            List<GatewayType> types = new ArrayList<GatewayType>();

            Set<ProcessEditorObjectType> excludedObjectTypes =
                    gateway.getProcessWidget().getExcludedObjectTypes();

            addTypeIfNotExcluded(types,
                    excludedObjectTypes,
                    GatewayType.EXCLUSIVE_DATA_LITERAL);
            addTypeIfNotExcluded(types,
                    excludedObjectTypes,
                    GatewayType.PARALLEL_LITERAL);
            addTypeIfNotExcluded(types,
                    excludedObjectTypes,
                    GatewayType.INCLUSIVE_LITERAL);
            addTypeIfNotExcluded(types,
                    excludedObjectTypes,
                    GatewayType.EXLCUSIVE_EVENT_LITERAL);
            addTypeIfNotExcluded(types,
                    excludedObjectTypes,
                    GatewayType.COMPLEX_LITERAL);

            return types.toArray();
        }

        return null;
    }

    /**
     * Add the gateway type to set if not excluded by some
     * processEditorConfiguration/ObjectTypeExclusion.
     * 
     * @param types
     * @param type
     * @param excludedObjectTypes
     */
    private void addTypeIfNotExcluded(List<GatewayType> types,
            Set<ProcessEditorObjectType> excludedObjectTypes, GatewayType type) {
        if (!excludedObjectTypes.contains(type.getProcessEditorObjectType())) {
            types.add(type);
        }
    }
}