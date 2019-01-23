/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.cycletypegadget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.commands.internal.SelectEditPartsCommand;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.BpmnClickOrDragGadgetAnchorFactory;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.policies.ShapeWithDescGenericClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo.GADGET_SHAPE;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetPolicy;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.ClickOrDragGadgetRequest;

/**
 * Base class for cycling object type on simple click of a gadget.
 * <p>
 * The subclass need only provide methods for getting list of object types,
 * current object type and command to set object to a given type.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractCycleObjectTypeGadgetPolicy extends
        AbstractClickOrDragGadgetPolicy {

    public static final String EDIT_POLICY_ID =
            "cycleObjectType.click.gadget.policy"; //$NON-NLS-1$

    private static final String CYCLE_OBJECT_TYPE_GADGET_TYPE =
            "CYCLE_OBJECT_TYPE_GADGET_TYPE"; //$NON-NLS-1$

    private EditingDomain editingDomain;

    private AdapterFactory adapterFactory;

    public AbstractCycleObjectTypeGadgetPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain,
            EditPolicyEnablementProvider policyEnabledmentProvider) {
        super(editingDomain, BpmnClickOrDragGadgetAnchorFactory.INSTANCE,
                policyEnabledmentProvider, EDIT_POLICY_ID);
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;
    }

    /**
     * @param hostEditPart
     * 
     * @return The valid object types for the given edit part.
     */
    protected abstract Object[] getObjectTypes(EditPart hostEditPart);

    /**
     * @param hostEditPart
     * 
     * @return The current type of the given edit part.
     */
    protected abstract Object getCurrentType(EditPart hostEditPart);

    /**
     * @param editingDomain
     * @param hostEditPart
     * @param newType
     * 
     * @return Command to change the given edit part to teh given type.
     */
    protected abstract Command createCycleNextObjectTypeCommand(
            EditingDomain editingDomain, EditPart hostEditPart, Object newType);

    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    @Override
    protected final Command createGadgetClickedCommand(
            String clickOrDragInfoType, ClickOrDragGadgetRequest creq) {
        if (creq.getClickOrDragGadgetInfo() instanceof AbstractClickOrDragGadgetInfo) {
            if (CYCLE_OBJECT_TYPE_GADGET_TYPE.equals(clickOrDragInfoType)) {
                Object[] types = getObjectTypes(creq.getHostEditPart());
                Object currType = getCurrentType(creq.getHostEditPart());

                if (types != null && types.length > 0) {
                    int typeIdx = -1; // default to 0 (below) if not found.

                    if (currType != null) {
                        for (int i = 0; i < types.length; i++) {
                            if (currType.equals(types[i])) {
                                typeIdx = i;
                                break;
                            }
                        }
                    }

                    typeIdx++;

                    if (typeIdx >= types.length) {
                        typeIdx = 0;
                    }

                    Object newType = types[typeIdx];
                    if (!newType.equals(currType)) {
                        Command cycleTypeCmd =
                                createCycleNextObjectTypeCommand(editingDomain,
                                        creq.getHostEditPart(),
                                        newType);

                        if (cycleTypeCmd != null && cycleTypeCmd.canExecute()) {
                            //
                            // Sid:
                            // There can be a problem (not of our making) with
                            // property sheets for the selected object (say a
                            // task).
                            //
                            // If there are different properties tabs for the
                            // new
                            // task type then the General Tab normally detects
                            // the
                            // change in task type and performs a refreshTabs().
                            //
                            // However if the last tab selected for a task was
                            // NOT
                            // general and the task is deselected and reselected
                            // then the general section is not necessarily
                            // reloaded.
                            //
                            // This means that when we change the typ[e the tabs
                            // don't get changed. Therefore in order to force a
                            // refresh we will perform force a reselect of the
                            // edipt
                            // part which should filter up thru the system.
                            CompoundCommand cmd =
                                    new CompoundCommand(cycleTypeCmd.getLabel());

                            cmd.add(cycleTypeCmd);

                            // In order for selection change not to be ignored
                            // we
                            // have to unset and reset the selection.
                            List<EditPart> emptySel = Collections.emptyList();
                            cmd.add(new SelectEditPartsCommand(getHost()
                                    .getViewer(), emptySel));

                            List<EditPart> selection =
                                    Collections.singletonList(getHost());
                            cmd.add(new SelectEditPartsCommand(getHost()
                                    .getViewer(), selection));

                            return cmd;
                        }
                    }
                }

                return UnexecutableCommand.INSTANCE;
            }
        }
        return null;
    }

    @Override
    protected Command createGadgetDraggedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        return null;
    }

    @Override
    protected List<AbstractClickOrDragGadgetInfo> getClickOrDragGadgetInfos() {
        List<AbstractClickOrDragGadgetInfo> infos =
                new ArrayList<AbstractClickOrDragGadgetInfo>();

        // Return the link events that reference this event
        // Get a list of all error events in same process.
        Image gadgetImage =
                ProcessWidgetPlugin.getDefault().getImageRegistry()
                        .get(ProcessWidgetConstants.IMG_CYCLETYPE_GADGET_ICON);

        infos.add(new ShapeWithDescGenericClickOrDragGadgetInfo(
                CYCLE_OBJECT_TYPE_GADGET_TYPE,
                Messages.AbstractCycleObjectTypeGadgetPolicy_ClickToCycleType_tooltip,
                gadgetImage, GADGET_SHAPE.SQUARE));
        return infos;
    }

    @Override
    protected boolean canDrag(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        return false;
    }

}
