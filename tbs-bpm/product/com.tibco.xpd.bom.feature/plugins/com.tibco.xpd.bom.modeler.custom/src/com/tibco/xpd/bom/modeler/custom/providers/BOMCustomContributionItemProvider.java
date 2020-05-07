/**
 * 
 */
package com.tibco.xpd.bom.modeler.custom.providers;

import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.modeler.custom.actions.BOMActionIds;
import com.tibco.xpd.bom.modeler.custom.actions.CreateBOMElementDiagramAction;
import com.tibco.xpd.bom.modeler.custom.actions.RefactorToAssocClassDiagramAction;
import com.tibco.xpd.bom.modeler.custom.actions.ShowHideBadgeDiagramAction;
import com.tibco.xpd.resources.WorkingCopy;

/**
 * @author rgreen
 * 
 */
public class BOMCustomContributionItemProvider extends
        AbstractContributionItemProvider {

    @Override
    protected IAction createAction(String actionId,
            IWorkbenchPartDescriptor partDescriptor) {
        IWorkbenchPage workbenchPage = partDescriptor.getPartPage();

        if (actionId.equals("refactorToAssociationClass")) { //$NON-NLS-1$
            return new RefactorToAssocClassDiagramAction(workbenchPage);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_CLASS)) {
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_CLASS, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_PACKAGE)) {
            /* Sid ACE-3537 Hide sub-package creation options as they are not supported in ACE. */
            // return new CreateBOMElementDiagramAction(workbenchPage, BOMActionIds.ACTION_ADD_PACKAGE, null);
            return null;
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_PRIMITIVE_TYPE)) {
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_PRIMITIVE_TYPE, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ENUMERATION)) {
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_ENUMERATION, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ATTRIBUTE)) {
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_ATTRIBUTE, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_OPERATION)) {
            // ACE-1704: Remove BOM unsupported features from UI.
            // return new CreateBOMElementDiagramAction(workbenchPage, BOMActionIds.ACTION_ADD_OPERATION, null);
            // Returning null will stop it appearing on the menu
            return null;
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ENUMERATION_LIT)) {
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_ENUMERATION_LIT, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_SHOW_HIDE_BADGE)) {
            return new ShowHideBadgeDiagramAction(workbenchPage);
        }

        // Checks after this point will need to know if we are in a Global Data
        // BOM before knowing if to provide the action
        boolean isGlobalDataBOM = false;
        IEditorPart editor = workbenchPage.getActiveEditor();
        if (editor != null) {
            WorkingCopy wc = editor.getAdapter(WorkingCopy.class);
            if (wc != null && wc.getRootElement() instanceof Model) {
                Model model = (Model) wc.getRootElement();
                isGlobalDataBOM =
                        BOMGlobalDataUtils.hasGlobalDataProfile(model);
            }
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_IDENTIFIER)) {
            if (isGlobalDataBOM) {
                return new CreateBOMElementDiagramAction(workbenchPage,
                        BOMActionIds.ACTION_ADD_CASE_IDENTIFIER, null);
            }
            // Returning null will stop it appearing on the menu
            return null;
        }
        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_CLASS)) {
            if (isGlobalDataBOM) {
                return new CreateBOMElementDiagramAction(workbenchPage,
                        BOMActionIds.ACTION_ADD_CASE_CLASS, null);
            }
            return null;
        }
        if (actionId.equals(BOMActionIds.ACTION_ADD_GLOBAL_CLASS)) {
            if (isGlobalDataBOM) {
                return new CreateBOMElementDiagramAction(workbenchPage,
                        BOMActionIds.ACTION_ADD_GLOBAL_CLASS, null);
            }
            return null;
        }
        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_STATE)) {
            if (isGlobalDataBOM) {
                return new CreateBOMElementDiagramAction(workbenchPage,
                        BOMActionIds.ACTION_ADD_CASE_STATE, null);
            }
            // Returning null will stop it appearing on the menu
            return null;
        }

        return super.createAction(actionId, partDescriptor);
    }

}
