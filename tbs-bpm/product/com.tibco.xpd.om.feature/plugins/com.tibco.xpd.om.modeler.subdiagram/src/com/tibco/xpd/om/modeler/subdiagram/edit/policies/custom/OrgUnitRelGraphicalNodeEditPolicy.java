package com.tibco.xpd.om.modeler.subdiagram.edit.policies.custom;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.Properties;
import org.eclipse.gmf.runtime.diagram.ui.requests.ChangePropertyValueRequest;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.View;

/**
 * 
 * Extends GraphicalNodeEDitPolicy so that we can override the
 * getRoutingAdjustment() method which resets a Tree style to the connector
 * style defined in the preference store. We don't want this to happen.
 * 
 * @author rgreen
 * 
 */
public class OrgUnitRelGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

    @Override
    protected Command getRoutingAdjustment(IAdaptable connection,
            String connectionHint, Routing currentRouterType, EditPart target) {
        Command cmd = null;
        if (connectionHint == null || target == null
                || target.getModel() == null
                || ((View) target.getModel()).getElement() == null)
            return null;
        // check if router needs to change type due to reorient.
        String targetHint = ViewUtil.getSemanticElementClassId((View) target
                .getModel());
        Routing newRouterType = null;
        if (target instanceof ITreeBranchEditPart
                && connectionHint.equals(targetHint)) {
            newRouterType = Routing.TREE_LITERAL;
            ChangePropertyValueRequest cpvr = new ChangePropertyValueRequest(
                    StringStatics.BLANK, Properties.ID_ROUTING, newRouterType);
            Command cmdRouter = target.getCommand(cpvr);
            if (cmdRouter != null)
                cmd = cmd == null ? cmdRouter : cmd.chain(cmdRouter);
        } else {
            // if (currentRouterType.equals(Routing.TREE_LITERAL)) {
            // IPreferenceStore store = (IPreferenceStore)
            // ((IGraphicalEditPart) getHost())
            // .getDiagramPreferencesHint().getPreferenceStore();
            // newRouterType = Routing.get(store
            // .getInt(IPreferenceConstants.PREF_LINE_STYLE));
            // }
        }
        if (newRouterType != null) {
            // add commands for line routing. Convert the new connection and
            // also the targeted connection.
            ICommand spc = new SetPropertyCommand(getEditingDomain(),
                    connection, Properties.ID_ROUTING, StringStatics.BLANK,
                    newRouterType);
            Command cmdRouter = new ICommandProxy(spc);
            if (cmdRouter != null) {
                cmd = cmd == null ? cmdRouter : cmd.chain(cmdRouter);
            }
        }
        return cmd;
    }

    private TransactionalEditingDomain getEditingDomain() {
        return ((IGraphicalEditPart) getHost()).getEditingDomain();
    }

}
