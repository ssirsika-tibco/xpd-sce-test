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

    protected IAction createAction(String actionId,
            IWorkbenchPartDescriptor partDescriptor) {
        IWorkbenchPage workbenchPage = partDescriptor.getPartPage();

        if (actionId.equals("refactorToAssociationClass")) { //$NON-NLS-1$
            return new RefactorToAssocClassDiagramAction(workbenchPage);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_CLASS)) { //$NON-NLS-1$
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_CLASS, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_PACKAGE)) { //$NON-NLS-1$
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_PACKAGE, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_PRIMITIVE_TYPE)) { //$NON-NLS-1$
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_PRIMITIVE_TYPE, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ENUMERATION)) { //$NON-NLS-1$
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_ENUMERATION, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ATTRIBUTE)) { //$NON-NLS-1$
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_ATTRIBUTE, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_OPERATION)) { //$NON-NLS-1$
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_OPERATION, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ENUMERATION_LIT)) { //$NON-NLS-1$
            return new CreateBOMElementDiagramAction(workbenchPage,
                    BOMActionIds.ACTION_ADD_ENUMERATION_LIT, null);
        }

        if (actionId.equals(BOMActionIds.ACTION_SHOW_HIDE_BADGE)) { //$NON-NLS-1$
            return new ShowHideBadgeDiagramAction(workbenchPage);
        }

        // Checks after this point will need to know if we are in a Global Data
        // BOM before knowing if to provide the action
        boolean isGlobalDataBOM = false;
        IEditorPart editor = workbenchPage.getActiveEditor();
        if (editor != null) {
            WorkingCopy wc = (WorkingCopy) editor.getAdapter(WorkingCopy.class);
            if (wc != null && wc.getRootElement() instanceof Model) {
                Model model = (Model) wc.getRootElement();
                isGlobalDataBOM =
                        BOMGlobalDataUtils.hasGlobalDataProfile(model);
            }
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_IDENTIFIER)) { //$NON-NLS-1$
            if (isGlobalDataBOM) {
                return new CreateBOMElementDiagramAction(workbenchPage,
                        BOMActionIds.ACTION_ADD_CASE_IDENTIFIER, null);
            }
            // Returning null will stop it appearing on the menu
            return null;
        }
        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_CLASS)) { //$NON-NLS-1$
            if (isGlobalDataBOM) {
                return new CreateBOMElementDiagramAction(workbenchPage,
                        BOMActionIds.ACTION_ADD_CASE_CLASS, null);
            }
            return null;
        }
        if (actionId.equals(BOMActionIds.ACTION_ADD_GLOBAL_CLASS)) { //$NON-NLS-1$
            if (isGlobalDataBOM) {
                return new CreateBOMElementDiagramAction(workbenchPage,
                        BOMActionIds.ACTION_ADD_GLOBAL_CLASS, null);
            }
            return null;
        }
        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_STATE)) { //$NON-NLS-1$
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
