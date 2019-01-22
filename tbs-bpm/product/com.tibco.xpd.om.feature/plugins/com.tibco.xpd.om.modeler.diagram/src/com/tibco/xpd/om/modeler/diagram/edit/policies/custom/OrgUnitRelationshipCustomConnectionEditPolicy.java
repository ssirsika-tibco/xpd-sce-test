package com.tibco.xpd.om.modeler.diagram.edit.policies.custom;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.GroupRequestViaKeyboard;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipEditPart;

/**
 * This is mainly a copy of:
 * 
 * org.eclipse.gmf.runtime.diagram.ui.internal.editpolicies.ConnectionEditPolicy
 * 
 * BUT access to that class is discouraged and therefore we can't really extend
 * from it directly.
 * 
 * We want to modify the createDeleteSemanticCommand.
 * 
 */
public class OrgUnitRelationshipCustomConnectionEditPolicy extends
        org.eclipse.gef.editpolicies.ConnectionEditPolicy {

    private static final String DELETE_FROM_DIAGRAM_DLG_TITLE = DiagramUIMessages.PromptingDeleteAction_DeleteFromDiagramDialog_Title;

    private static final String DELETE_FROM_DIAGRAM_DLG_MESSAGE = DiagramUIMessages.PromptingDeleteAction_DeleteFromDiagramDialog_Message;

    private static final String DELETE_FROM_MODEL_DLG_TOGGLE_LABEL = DiagramUIMessages.MessageDialogWithToggle_DoNotPromptAgainToggle_label;

    /**
     * Returns a delete command to honour the supplied request. Calls
     * {@link #createDeleteSemanticCommand(GroupRequest)}if
     * {@link #shouldDeleteSemantic()}returns <tt>true</tt>; othwerise
     * {@link #createDeleteViewCommand(GroupRequest)}is called.
     */
    protected final Command getDeleteCommand(GroupRequest deleteRequest) {
        boolean isDeleteFromKeyBoard = deleteRequest instanceof GroupRequestViaKeyboard;

        if (shouldDeleteSemantic()) {
            return createDeleteSemanticCommand(deleteRequest);
        } else {

            boolean proceedToDeleteView = true;
            if (isDeleteFromKeyBoard) {
                GroupRequestViaKeyboard groupRequestViaKeyboard = (GroupRequestViaKeyboard) deleteRequest;
                if (groupRequestViaKeyboard.isShowInformationDialog()) {
                    proceedToDeleteView = showPrompt();
                    groupRequestViaKeyboard.setShowInformationDialog(false);
                    if (!(proceedToDeleteView))
                        return UnexecutableCommand.INSTANCE;
                }
            }

            return createDeleteViewCommand(deleteRequest);
        }

    }

    /**
     * Return <tt>true</tt> if either the connections source or target editparts
     * are canonical; otherwise <tt>false</tt>.
     */
    protected boolean shouldDeleteSemantic() {

        Assert.isTrue(getHost() instanceof AbstractConnectionEditPart);

        AbstractConnectionEditPart cep = (AbstractConnectionEditPart) getHost();

        if (cep instanceof ConnectionEditPart) {
            if (!((ConnectionEditPart) cep).isSemanticConnection()) {
                return false;
            }
        }

        boolean isCanonical = false;
        if (cep.getSource() != null)
            isCanonical = IsCanonical(cep.getSource());
        if (cep.getTarget() != null)
            isCanonical = isCanonical && IsCanonical(cep.getTarget());
        return isCanonical;

    }

    /**
     * Return <tt>true</tt> if the supplied editpart is canonical; otherwise,
     * <tt>false</tt>.
     */
    private boolean IsCanonical(EditPart ep) {
        EditPart parent = ep.getParent();
        return parent instanceof GraphicalEditPart ? ((GraphicalEditPart) parent)
                .isCanonical()
                : false;
    }

    /**
     * Return a command to delete the host's view.
     * 
     * @param deleteRequest
     *            the original delete request.
     */
    protected Command createDeleteViewCommand(GroupRequest deleteRequest) {

        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();

        return new ICommandProxy(new DeleteCommand(editingDomain,
                (View) getHost().getModel()));
    }

    /**
     * Return a command to delete the host's semantic element. This method is
     * called if the host is canonical.
     * 
     * @see #shouldDeleteSemantic()
     * @param deleteRequest
     *            the original delete request.
     */
    protected Command createDeleteSemanticCommand(GroupRequest deleteRequest) {

        // This is our custom code. Only allow a delete if an
        // OrgUnitRelationship is between OrgUnits if DIFFERENT Organizations.
        EditPart connectionEP = getHost();

        if (connectionEP instanceof OrgUnitRelationshipEditPart) {
            OrgUnitRelationshipEditPart ourEP = (OrgUnitRelationshipEditPart) connectionEP;

            EObject eo = ourEP.resolveSemanticElement();

            if (eo != null && eo instanceof OrgUnitRelationship) {
                OrgUnitRelationship our = (OrgUnitRelationship) eo;

                OrgUnit trgOU = our.getTo();
                OrgUnit srcOU = our.getFrom();

                if (trgOU.eContainer() == srcOU.eContainer()) {
                    return null;
                }
            }
        }

        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();

        EditCommandRequestWrapper semReq = null;
        if (deleteRequest instanceof GroupRequestViaKeyboard) {
            GroupRequestViaKeyboard grDeleteRequest = (GroupRequestViaKeyboard) deleteRequest;
            semReq = new EditCommandRequestWrapper(new DestroyElementRequest(
                    editingDomain, grDeleteRequest.isShowInformationDialog()),
                    deleteRequest.getExtendedData());
        } else {
            semReq = new EditCommandRequestWrapper(new DestroyElementRequest(
                    editingDomain, false), deleteRequest.getExtendedData());

        }
        Command semanticCmd = getHost().getCommand(semReq);
        if (semanticCmd != null && semanticCmd.canExecute()) {
            CompoundCommand cc = new CompoundCommand();
            cc.add(semanticCmd);
            return cc;
        }
        return null;
    }

    /**
     * Performs the delete action on the selected objects.
     */
    private boolean showPrompt() {
        boolean prompt = ((IPreferenceStore) ((IGraphicalEditPart) getHost())
                .getDiagramPreferencesHint().getPreferenceStore())
                .getBoolean(IPreferenceConstants.PREF_PROMPT_ON_DEL_FROM_DIAGRAM);

        if (prompt)
            if (showMessageDialog())
                return true;
            else
                return false;

        return true;

    }

    /**
     * launches the prompting dialogBox on deletion of elements from the diagram
     * for the end user.
     * 
     * @return boolean true if user pressed YES; false otherwise
     */
    private boolean showMessageDialog() {
        MessageDialogWithToggle dialog = MessageDialogWithToggle
                .openYesNoQuestion(Display.getCurrent().getActiveShell(),
                        DELETE_FROM_DIAGRAM_DLG_TITLE,
                        DELETE_FROM_DIAGRAM_DLG_MESSAGE,
                        DELETE_FROM_MODEL_DLG_TOGGLE_LABEL, false,
                        (IPreferenceStore) ((IGraphicalEditPart) getHost())
                                .getDiagramPreferencesHint()
                                .getPreferenceStore(),
                        IPreferenceConstants.PREF_PROMPT_ON_DEL_FROM_DIAGRAM);

        if (dialog.getReturnCode() == IDialogConstants.YES_ID)
            return true;
        else
            return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.ComponentEditPolicy#getCommand(org.eclipse
     * .gef.Request)
     */
    public Command getCommand(Request request) {
        if (request instanceof GroupRequestViaKeyboard) {
            return getDeleteCommand((GroupRequest) request);
        }
        return super.getCommand(request);
    }

}
