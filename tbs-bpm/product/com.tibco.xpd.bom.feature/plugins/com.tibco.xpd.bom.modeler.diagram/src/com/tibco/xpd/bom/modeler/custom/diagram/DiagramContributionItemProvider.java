package com.tibco.xpd.bom.modeler.custom.diagram;

import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.part.MutateClassTypeAction;
import com.tibco.xpd.bom.modeler.diagram.part.MutatePropertyTypeAction;

/**
 * Provider for Global Data-specific class menu item contributions.
 * 
 * TODO Consider the fact that this is not a
 * {@link org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction} descended
 * action; unsure whether this will cause issues later on.
 * 
 * @author patkinso
 * @since 11 Oct 2013
 */
public class DiagramContributionItemProvider extends
        AbstractContributionItemProvider {

    public final static String ACTION_CLASS_MUTATE_TO_CASE =
            "mutateClassToCaseAction"; //$NON-NLS-1$

    public final static String ACTION_CLASS_MUTATE_TO_GLOBAL =
            "mutateClassToGlobalAction"; //$NON-NLS-1$

    public final static String ACTION_CLASS_MUTATE_TO_LOCAL =
            "mutateClassToLocalAction"; //$NON-NLS-1$

    public final static String ACTION_PROPERTY_MUTATE_TO_CASE_STATE =
            "mutatePropertyToCaseStateAction"; //$NON-NLS-1$

    public final static String ACTION_PROPERTY_MUTATE_TO_ATTRIBUTE =
            "mutatePropertyToAttributeAction"; //$NON-NLS-1$

    public final static String ACTION_PROPERTY_MUTATE_TO_CASE_IDENTIFIER =
            "mutatePropertyToCaseIdentifierAction"; //$NON-NLS-1$

    /**
     * @see org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider#createAction(java.lang.String,
     *      org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor)
     * 
     * @param actionId
     * @param partDescriptor
     * @return
     */
    @Override
    protected IAction createAction(String actionId,
            IWorkbenchPartDescriptor partDescriptor) {

        IWorkbenchPage workbenchPage = partDescriptor.getPartPage();

        if (actionId.equals(ACTION_CLASS_MUTATE_TO_CASE)) {
            return new MutateClassTypeAction(workbenchPage, StereotypeKind.CASE);
        } else if (actionId.equals(ACTION_CLASS_MUTATE_TO_GLOBAL)) {
            return new MutateClassTypeAction(workbenchPage,
                    StereotypeKind.GLOBAL);
        } else if (actionId.equals(ACTION_CLASS_MUTATE_TO_LOCAL)) {
            // There is no stereotype for Local Classes so use null
            return new MutateClassTypeAction(workbenchPage, null);
        } else if (actionId.equals(ACTION_PROPERTY_MUTATE_TO_CASE_STATE)) {
            // Pass in the Case State Stereotype as that is what we want to
            // offer the convert to
            return new MutatePropertyTypeAction(workbenchPage,
                    StereotypeKind.CASE_STATE);
        } else if (actionId.equals(ACTION_PROPERTY_MUTATE_TO_ATTRIBUTE)) {
            // There is no stereotype for standard attributes so use null
            return new MutatePropertyTypeAction(workbenchPage, null);
        } else if (actionId.equals(ACTION_PROPERTY_MUTATE_TO_CASE_IDENTIFIER)) {
            // Pass in the Case Identifier Stereotype as that is what we want to
            // offer the convert to, the default from the tool selection is
            // always an auto case identifier, so we offer the same here
            return new MutatePropertyTypeAction(workbenchPage,
                    StereotypeKind.AUTO_CASE_IDENTIFIER);
        }

        return super.createAction(actionId, partDescriptor);
    }

}
